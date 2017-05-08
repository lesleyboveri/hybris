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

import static com.springernature.hybris.checkout.constants.SpringerlinkcheckoutfacadesConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.springernature.hybris.checkout.constants.SpringerlinkcheckoutfacadesConstants;
import com.springernature.hybris.checkout.service.SpringerlinkcheckoutfacadesService;


@SystemSetup(extension = SpringerlinkcheckoutfacadesConstants.EXTENSIONNAME)
public class SpringerlinkcheckoutfacadesSystemSetup
{
	private final SpringerlinkcheckoutfacadesService springerlinkcheckoutfacadesService;

	public SpringerlinkcheckoutfacadesSystemSetup(final SpringerlinkcheckoutfacadesService springerlinkcheckoutfacadesService)
	{
		this.springerlinkcheckoutfacadesService = springerlinkcheckoutfacadesService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		springerlinkcheckoutfacadesService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SpringerlinkcheckoutfacadesSystemSetup.class.getResourceAsStream("/springerlinkcheckoutfacades/sap-hybris-platform.png");
	}
}
