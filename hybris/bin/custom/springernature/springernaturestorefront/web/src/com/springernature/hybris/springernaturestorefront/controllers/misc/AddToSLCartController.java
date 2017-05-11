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

import com.springernature.hybris.springernaturefacades.cart.PayPerViewCartFacade;
import com.springernature.hybris.springernaturestorefront.controllers.ControllerConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.AbstractController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddToCartForm;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.util.Config;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/**
 * Controller for Add to Cart functionality which is specific to springerlink buybox checkout flow.
 */
@Controller
public class AddToSLCartController extends AbstractController
{

	private static final String QUANTITY_ATTR = "quantity";
	private static final String TYPE_MISMATCH_ERROR_CODE = "typeMismatch";
	private static final String ERROR_MSG_TYPE = "errorMsg";
	private static final String QUANTITY_INVALID_BINDING_MESSAGE_KEY = "basket.error.quantity.invalid.binding";

	private static final String PARAM_MAC = "mac";
	private static final String PARAM_RURL = "returnurl";

	private static final Logger LOG = Logger.getLogger(AddToSLCartController.class);


	@Resource(name = "payPerViewCartFacade")
	private PayPerViewCartFacade payPerViewCartFacade;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;


	@RequestMapping(value = "/slcart/add", method = RequestMethod.POST)
	public String addToSpringerLinkCart(@RequestParam("productCodePost") final String code, final Model model,
							@Valid final AddToCartForm form, final BindingResult bindingErrors, final HttpServletRequest request)
	{

		if (bindingErrors.hasErrors())
		{
			return getViewWithBindingErrorMessages(model, bindingErrors);
		}

		final String mac = request.getParameter(PARAM_MAC);
        if (StringUtils.isEmpty(mac)) {
            model.addAttribute(ERROR_MSG_TYPE, "basket.information.mac.missing");
            return null;
        }

		final Map<String,String> parameterMap = new HashMap<>();

		for (Map.Entry<String,String[]> entry : request.getParameterMap().entrySet()) {
			if(entry.getValue().length > 1) {
				LOG.warn("Parameter " + entry.getKey() + " contains multiple values.");
			}
			parameterMap.put(entry.getKey(), entry.getValue()[0]);
		}

        if (!verifyMac(mac, parameterMap, Config.getString("ppvMd5SecretKey", null))) {
            model.addAttribute(ERROR_MSG_TYPE, "basket.information.mac.failed");
            return null;
        }

        if (payPerViewCartFacade.hasSessionCart()) {
			payPerViewCartFacade.removeSessionCart();
        }

		try {
			final CartModificationData cartModification = payPerViewCartFacade.addToCart(parameterMap, code);
			model.addAttribute(QUANTITY_ATTR, Long.valueOf(cartModification.getQuantityAdded()));
			model.addAttribute("entry", cartModification.getEntry());
			model.addAttribute("cartCode", cartModification.getCartCode());
			model.addAttribute("isQuote", payPerViewCartFacade.getSessionCart().getQuoteData() != null ? Boolean.TRUE : Boolean.FALSE);

			if (cartModification.getQuantityAdded() == 0L)
			{
				model.addAttribute(ERROR_MSG_TYPE, "basket.information.quantity.noItemsAdded." + cartModification.getStatusCode());
			}

		} catch (final CommerceCartModificationException ex)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug(ex);
			}
			model.addAttribute(ERROR_MSG_TYPE, "basket.error.occurred");

		}

		model.addAttribute("product", productFacade.getProductForCodeAndOptions(code, Arrays.asList(ProductOption.BASIC)));
		return REDIRECT_PREFIX + "/cart/checkout";
	}


    protected boolean verifyMac(final String mac, final Map<String,String> parameters, final String secret)
	{

		final Map<String,String> parameterMap= new TreeMap<>(parameters);

		// mirrors MAC creation of
		// https://github.com/springernature/sprcom-price-service/blob/master/app/controllers/BuyBoxController.scala
		parameterMap.remove(PARAM_MAC);
		parameterMap.remove(PARAM_RURL);

        final StringBuilder md5str = new StringBuilder();
        for (Map.Entry<String,String> entry : parameterMap.entrySet()) {
            md5str.append(entry.getValue());
        }
        md5str.append(secret);
        final String hash = Hex.encodeHexString(DigestUtils.getMd5Digest().digest(md5str.toString().getBytes()));
        return hash.equals(mac);
    }

	protected String getViewWithBindingErrorMessages(final Model model, final BindingResult bindingErrors)
	{
		for (final ObjectError error : bindingErrors.getAllErrors())
		{
			if (error.getCode().equals(TYPE_MISMATCH_ERROR_CODE))
			{
				model.addAttribute(ERROR_MSG_TYPE, QUANTITY_INVALID_BINDING_MESSAGE_KEY);
			}
			else
			{
				model.addAttribute(ERROR_MSG_TYPE, error.getDefaultMessage());
			}
		}
		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	;
}
