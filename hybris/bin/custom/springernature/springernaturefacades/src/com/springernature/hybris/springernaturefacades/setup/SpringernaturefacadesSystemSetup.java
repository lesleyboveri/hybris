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
package com.springernature.hybris.springernaturefacades.setup;

import static com.springernature.hybris.springernaturefacades.constants.SpringernaturefacadesConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.springernature.hybris.springernaturefacades.constants.SpringernaturefacadesConstants;
import com.springernature.hybris.springernaturefacades.service.SpringernaturefacadesService;


@SystemSetup(extension = SpringernaturefacadesConstants.EXTENSIONNAME)
public class SpringernaturefacadesSystemSetup
{
	private final SpringernaturefacadesService springernaturefacadesService;

	public SpringernaturefacadesSystemSetup(final SpringernaturefacadesService springernaturefacadesService)
	{
		this.springernaturefacadesService = springernaturefacadesService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		springernaturefacadesService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SpringernaturefacadesSystemSetup.class.getResourceAsStream("/springernaturefacades/sap-hybris-platform.png");
	}
}
