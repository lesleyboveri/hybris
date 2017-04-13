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
package com.springernature.hybris.sonar.setup;

import static com.springernature.hybris.sonar.constants.SpringernaturesonarConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.springernature.hybris.sonar.constants.SpringernaturesonarConstants;
import com.springernature.hybris.sonar.service.SpringernaturesonarService;


@SystemSetup(extension = SpringernaturesonarConstants.EXTENSIONNAME)
public class SpringernaturesonarSystemSetup
{
	private final SpringernaturesonarService springernaturesonarService;

	public SpringernaturesonarSystemSetup(final SpringernaturesonarService springernaturesonarService)
	{
		this.springernaturesonarService = springernaturesonarService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		springernaturesonarService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SpringernaturesonarSystemSetup.class.getResourceAsStream("/springernaturesonar/sap-hybris-platform.png");
	}
}
