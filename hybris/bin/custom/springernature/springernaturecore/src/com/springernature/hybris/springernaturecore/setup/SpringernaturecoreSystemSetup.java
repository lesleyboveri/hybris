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
package com.springernature.hybris.springernaturecore.setup;

import static com.springernature.hybris.springernaturecore.constants.SpringernaturecoreConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.springernature.hybris.springernaturecore.constants.SpringernaturecoreConstants;
import com.springernature.hybris.springernaturecore.service.SpringernaturecoreService;


@SystemSetup(extension = SpringernaturecoreConstants.EXTENSIONNAME)
public class SpringernaturecoreSystemSetup
{
	private final SpringernaturecoreService springernaturecoreService;

	public SpringernaturecoreSystemSetup(final SpringernaturecoreService springernaturecoreService)
	{
		this.springernaturecoreService = springernaturecoreService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		springernaturecoreService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SpringernaturecoreSystemSetup.class.getResourceAsStream("/springernaturecore/sap-hybris-platform.png");
	}
}
