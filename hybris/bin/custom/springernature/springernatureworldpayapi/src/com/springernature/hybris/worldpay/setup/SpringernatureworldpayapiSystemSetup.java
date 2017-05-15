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
package com.springernature.hybris.worldpay.setup;

import static com.springernature.hybris.worldpay.constants.SpringernatureworldpayapiConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.springernature.hybris.worldpay.constants.SpringernatureworldpayapiConstants;
import com.springernature.hybris.worldpay.service.SpringernatureworldpayapiService;


@SystemSetup(extension = SpringernatureworldpayapiConstants.EXTENSIONNAME)
public class SpringernatureworldpayapiSystemSetup
{
	private final SpringernatureworldpayapiService springernatureworldpayapiService;

	public SpringernatureworldpayapiSystemSetup(final SpringernatureworldpayapiService springernatureworldpayapiService)
	{
		this.springernatureworldpayapiService = springernatureworldpayapiService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		springernatureworldpayapiService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SpringernatureworldpayapiSystemSetup.class.getResourceAsStream("/springernatureworldpayapi/sap-hybris-platform.png");
	}
}
