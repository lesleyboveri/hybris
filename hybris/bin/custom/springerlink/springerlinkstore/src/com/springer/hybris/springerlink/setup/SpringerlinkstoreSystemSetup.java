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
package com.springer.hybris.springerlink.setup;

import static com.springer.hybris.springerlink.constants.SpringerlinkstoreConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.springer.hybris.springerlink.constants.SpringerlinkstoreConstants;
import com.springer.hybris.springerlink.service.SpringerlinkstoreService;


@SystemSetup(extension = SpringerlinkstoreConstants.EXTENSIONNAME)
public class SpringerlinkstoreSystemSetup
{
	private final SpringerlinkstoreService springerlinkstoreService;

	public SpringerlinkstoreSystemSetup(final SpringerlinkstoreService springerlinkstoreService)
	{
		this.springerlinkstoreService = springerlinkstoreService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		springerlinkstoreService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SpringerlinkstoreSystemSetup.class.getResourceAsStream("/springerlinkstore/sap-hybris-platform.png");
	}
}
