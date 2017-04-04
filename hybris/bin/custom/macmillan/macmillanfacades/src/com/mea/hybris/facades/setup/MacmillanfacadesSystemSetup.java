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
package com.mea.hybris.facades.setup;

import static com.mea.hybris.facades.constants.MacmillanfacadesConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.mea.hybris.facades.constants.MacmillanfacadesConstants;
import com.mea.hybris.facades.service.MacmillanfacadesService;


@SystemSetup(extension = MacmillanfacadesConstants.EXTENSIONNAME)
public class MacmillanfacadesSystemSetup
{
	private final MacmillanfacadesService macmillanfacadesService;

	public MacmillanfacadesSystemSetup(final MacmillanfacadesService macmillanfacadesService)
	{
		this.macmillanfacadesService = macmillanfacadesService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		macmillanfacadesService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return MacmillanfacadesSystemSetup.class.getResourceAsStream("/macmillanfacades/sap-hybris-platform.png");
	}
}
