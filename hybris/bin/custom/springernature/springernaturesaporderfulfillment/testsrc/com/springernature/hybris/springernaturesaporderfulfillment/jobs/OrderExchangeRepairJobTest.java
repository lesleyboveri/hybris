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
package com.springernature.hybris.springernaturesaporderfulfillment.jobs;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.sap.orderexchange.outbound.OrderExchangeRepair;
import com.springernature.hybris.springernaturesaporderfulfillment.actions.SendOrderToDataHubAction;
import com.springernature.hybris.springernaturesaporderfulfillment.constants.SpringernaturesaporderfulfillmentConstants;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@SuppressWarnings("javadoc")
@UnitTest
public class OrderExchangeRepairJobTest
{

	private List<OrderProcessModel> bpList;

	@Mock
	private OrderExchangeRepair orderExchangeRepair;

	@Mock
	private CronJobModel cronJobModel;

	@Mock
	private BusinessProcessService businessProcessService;

	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		final OrderProcessModel bp = new OrderProcessModel();
		bpList = new ArrayList<>();
		bpList.add(bp);

	}

	@Test
	public void cronJobTest()
	{
		final OrderExchangeRepairJob repairJob = new OrderExchangeRepairJob();
		repairJob.setOrderExchangeRepair(orderExchangeRepair);
		repairJob.setBusinessProcessService(businessProcessService);
		when(
				orderExchangeRepair.findAllProcessModelsToRepair(SpringernaturesaporderfulfillmentConstants.ORDER_PROCESS_NAME,
						SendOrderToDataHubAction.ERROR_END_MESSAGE)).thenReturn(bpList);

		repairJob.perform(cronJobModel);
		verify(orderExchangeRepair).findAllProcessModelsToRepair(SpringernaturesaporderfulfillmentConstants.ORDER_PROCESS_NAME,
				SendOrderToDataHubAction.ERROR_END_MESSAGE);

	}

}
