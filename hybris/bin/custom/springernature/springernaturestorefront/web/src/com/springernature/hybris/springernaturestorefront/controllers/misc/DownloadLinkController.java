/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.springernature.hybris.springernaturestorefront.controllers.misc;

import atg.taglib.json.util.XML;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.util.Config;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


/**
 * Controller to handle download links for PPV products.
 */
@Controller
public class DownloadLinkController extends AbstractPageController
{

	private static final Logger LOGGER = Logger.getLogger(DownloadLinkController.class);

    private static final String ORDER_CODE_PATH_VARIABLE_PATTERN = "{orderCode:.*}";

	private static final String API_URL = Config.getString("springerLinkApiUrl", "http://www.live.springer.com/api/access/pdf");
	private static final String API_KEY = Config.getString("springerLinkApiKey", "CASPER_API_KEY");
	private static final String API_VALUE = Config.getString("springerLinkApiValue", "A30EA972-6172-4EB4-8ABC-7A72C82823E0");

	@RequestMapping(value = "/downloadlink/" + ORDER_CODE_PATH_VARIABLE_PATTERN,
            method = RequestMethod.GET)
	@RequireHardLogIn
	public void downloadlink(@PathVariable("orderCode") final String orderCode, final HttpServletResponse response)
			throws Exception
	{

	    final String ret;
        final OrderData orderDetails;

        try
        {
            orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
        }
        catch (final UnknownIdentifierException e)
        {
            LOGGER.warn("Attempted to load an order confirmation that does not exist or is not visible. Redirect to home page.");
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            return;
        }

        if (orderDetails.isGuestCustomer()
                && !StringUtils.substringBefore(orderDetails.getUser().getUid(), "|").equals(
                getSessionService().getAttribute(WebConstants.ANONYMOUS_CHECKOUT_GUID)))
        {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return;
        }

		final List<OrderEntryData> entries;
        final OrderEntryData entry;
        final Map<String,String> parameters;
        final String doi;

        if ((entries = orderDetails.getEntries()) != null
				&& !entries.isEmpty()
				&& (entry = entries.iterator().next()) != null
				&& (parameters = entry.getParameters()) != null
				&& (doi = parameters.get("doi")) != null
				&& doi.length() > 0)
        {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			write(XML.toJSONObject(requestContentApi(doi)).toString(),
                    response);
		}

		return;
	}


	private void write(final String value, final ServletResponse response) throws IOException
    {
	    try {
            response.getOutputStream().write(value.getBytes());
        } finally {
            response.flushBuffer();
            response.getOutputStream().close();
        }
    }

	/**
	 * Call R&D content download api with given doi.
	 *
	 * @param doi
	 * @return
	 * @throws IOException if the file is not available
	 */
	private String requestContentApi(final String doi) throws Exception {

		final String targetURL = API_URL + "/" + URLEncoder.encode(doi, "UTF-8");

		BufferedReader in = null;
		try {
			final HttpClient client = HttpClientBuilder.create().build();
			final HttpGet request = new HttpGet(targetURL);

			request.addHeader(API_KEY, API_VALUE);
			final HttpResponse response = client.execute(request);

			final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			final StringBuffer result = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (final MalformedURLException e) {
			throw e;
		} catch (final IOException e) {
			LOGGER.warn("Product " + doi + " not found at " + targetURL + "; " + e);
			throw e;
		} finally {
			IOUtils.closeQuietly(in);
		}

	}

    @Resource(name = "orderFacade")
    private OrderFacade orderFacade;

}
