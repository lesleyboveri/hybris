package com.springernature.hybris.checkout.order.impl;

import com.springernature.hybris.checkout.order.SpringerlinkDeliveryAndPaymentAddressStrategy;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by tklostermann on 16.05.2017.
 */
@UnitTest
public class DefaultSpringerlinkCommerceCheckoutServiceTest {

    @Mock
    private SpringerlinkDeliveryAndPaymentAddressStrategy springerlinkDeliveryAndPaymentAddressStrategy;

    private DefaultSpringerlinkCommerceCheckoutService defaultSpringerlinkCommerceCheckoutService;

    private CommerceCheckoutParameter commerceCheckoutParameter;

    @Before
    public void testSetup() {
        MockitoAnnotations.initMocks(this);

        defaultSpringerlinkCommerceCheckoutService = new DefaultSpringerlinkCommerceCheckoutService();
        defaultSpringerlinkCommerceCheckoutService.setSpringerlinkDeliveryAndPaymentAddressStrategy(springerlinkDeliveryAndPaymentAddressStrategy);

        commerceCheckoutParameter = new CommerceCheckoutParameter();

        given(springerlinkDeliveryAndPaymentAddressStrategy.storeDeliveryAndPaymentAddress(Matchers.any())).willReturn(Boolean.TRUE);
    }

    @Test
    public void testSetDeliveryAndPaymentAddress() {
        defaultSpringerlinkCommerceCheckoutService.setDeliveryAndPaymentAddress(commerceCheckoutParameter);
        verify(springerlinkDeliveryAndPaymentAddressStrategy).storeDeliveryAndPaymentAddress(any(CommerceCheckoutParameter.class));
    }
}
