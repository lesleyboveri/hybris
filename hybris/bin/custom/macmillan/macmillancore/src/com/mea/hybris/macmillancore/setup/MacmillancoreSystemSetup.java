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
package com.mea.hybris.macmillancore.setup;

import static com.mea.hybris.macmillancore.constants.MacmillancoreConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.mea.hybris.macmillancore.constants.MacmillancoreConstants;
import com.mea.hybris.macmillancore.service.MacmillancoreService;


@SystemSetup(extension = MacmillancoreConstants.EXTENSIONNAME)
public class MacmillancoreSystemSetup
{
	private final MacmillancoreService macmillancoreService;

	public MacmillancoreSystemSetup(final MacmillancoreService macmillancoreService)
	{
		this.macmillancoreService = macmillancoreService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		macmillancoreService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return MacmillancoreSystemSetup.class.getResourceAsStream("/macmillancore/sap-hybris-platform.png");
	}
}
