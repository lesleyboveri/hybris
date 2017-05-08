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
package com.springernature.hybris.checkout.setup;

import static com.springernature.hybris.checkout.constants.SpringerlinkcheckoutservicesConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.springernature.hybris.checkout.constants.SpringerlinkcheckoutservicesConstants;
import com.springernature.hybris.checkout.service.SpringerlinkcheckoutservicesService;


@SystemSetup(extension = SpringerlinkcheckoutservicesConstants.EXTENSIONNAME)
public class SpringerlinkcheckoutservicesSystemSetup
{
	private final SpringerlinkcheckoutservicesService springerlinkcheckoutservicesService;

	public SpringerlinkcheckoutservicesSystemSetup(final SpringerlinkcheckoutservicesService springerlinkcheckoutservicesService)
	{
		this.springerlinkcheckoutservicesService = springerlinkcheckoutservicesService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		springerlinkcheckoutservicesService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SpringerlinkcheckoutservicesSystemSetup.class.getResourceAsStream("/springerlinkcheckoutservices/sap-hybris-platform.png");
	}
}
