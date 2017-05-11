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
package com.springernature.hybris.springernaturestorefront.tags;

import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.util.Config;
import org.apache.log4j.Logger;

import java.util.Map;


/**
 * JSP EL Functions. This file contains static methods that are used by JSP EL.
 */
public class Functions
{
	private static final Logger LOG = Logger.getLogger(Functions.class);


	private static final String PPV_IMAGE_PREFIX = Config.getString("ppvImageUrlPrefix", "https://static-content.springer.com/cover");

	/**
	 * JSP EL Function to get a primary Image for an Entry
	 *
	 * @param entry
	 *           the entry
	 * @return the image
	 */
	public static ImageData getEntryImage(final OrderEntryData entry)
	{
		final Map<String,String> parameters;
		final String type,url;

		if (entry != null
				&& (parameters = entry.getParameters()) != null
				&& (type = parameters.get("type")) != null)
		{
			if ("chapter".equals(type))
			{

				url = PPV_IMAGE_PREFIX + "/book/" + parameters.get("isxn") + ".jpg";

			} else if ("article".equals(type))
			{

				url = PPV_IMAGE_PREFIX + "/journal/" + extractJournalId(parameters.get("doi")) +".jpg";

			} else
				{
				LOG.warn("No image url heuristics for type: " + type);
				return null;
			}

			final ImageData ret = new ImageData();
			ret.setUrl(url);
			return ret;

		}
		return null;
	}

	private static String extractJournalId(final String doi) {
		return doi.split("/")[1].split("-")[0].replaceAll("s","");
	}

}
