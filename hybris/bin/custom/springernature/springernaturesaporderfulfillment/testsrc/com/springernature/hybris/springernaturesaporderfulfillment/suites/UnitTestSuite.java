/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.springernature.hybris.springernaturesaporderfulfillment.suites;


import com.springernature.hybris.springernaturesaporderfulfillment.actions.SendOrderToDataHubActionTest;
import com.springernature.hybris.springernaturesaporderfulfillment.actions.SetCompletionStatusActionTest;
import com.springernature.hybris.springernaturesaporderfulfillment.actions.SetConfirmationStatusActionTest;
import com.springernature.hybris.springernaturesaporderfulfillment.actions.UpdateERPOrderStatusActionTest;
import com.springernature.hybris.springernaturesaporderfulfillment.jobs.OrderCancelRepairJobTest;
import com.springernature.hybris.springernaturesaporderfulfillment.jobs.OrderExchangeRepairJobTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@SuppressWarnings("javadoc")
@RunWith(Suite.class)
@SuiteClasses(
{ UpdateERPOrderStatusActionTest.class, SetConfirmationStatusActionTest.class, SendOrderToDataHubActionTest.class,
		SetCompletionStatusActionTest.class, OrderExchangeRepairJobTest.class, OrderCancelRepairJobTest.class })
public class UnitTestSuite
{
	// Intentionally left blank
}
