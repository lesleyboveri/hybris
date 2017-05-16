package com.springernature.hybris.checkout.order.impl;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyBoolean;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.delivery.DeliveryService;
import de.hybris.platform.commerceservices.order.CommerceCartCalculationStrategy;
import de.hybris.platform.commerceservices.order.CommerceDeliveryModeValidationStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

/**
 * Created by tklostermann on 16.05.2017.
 */
@UnitTest
public class DefaultSpringerlinkDeliveryAndPaymentAddressStrategyTest {

    @Mock
    private ModelService modelService;

    @Mock
    private CommerceCartCalculationStrategy commerceCartCalculationStrategy;

    @Mock
    private CommerceDeliveryModeValidationStrategy commerceDeliveryModeValidationStrategy;

    @Mock
    private DeliveryService deliveryService;

    private DefaultSpringerlinkDeliveryAndPaymentAddressStrategy defaultSpringerlinkDeliveryAndPaymentAddressStrategy;

    private CommerceCheckoutParameter commerceCheckoutParameter;

    private CartModel cartModel;

    private AddressModel addressModel;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        defaultSpringerlinkDeliveryAndPaymentAddressStrategy = new DefaultSpringerlinkDeliveryAndPaymentAddressStrategy();
        defaultSpringerlinkDeliveryAndPaymentAddressStrategy.setModelService(modelService);
        defaultSpringerlinkDeliveryAndPaymentAddressStrategy.setCommerceCartCalculationStrategy(commerceCartCalculationStrategy);
        defaultSpringerlinkDeliveryAndPaymentAddressStrategy.setCommerceDeliveryModeValidationStrategy(commerceDeliveryModeValidationStrategy);
        defaultSpringerlinkDeliveryAndPaymentAddressStrategy.setDeliveryService(deliveryService);

        commerceCheckoutParameter = new CommerceCheckoutParameter();
        commerceCheckoutParameter.setEnableHooks(true);
        commerceCheckoutParameter.setIsDeliveryAddress(false);

        cartModel = new CartModel();
        addressModel = new AddressModel();

        given(deliveryService.getSupportedDeliveryAddressesForOrder(Matchers.any(), anyBoolean())).willReturn(Arrays.asList(addressModel));
        given(commerceCartCalculationStrategy.calculateCart(cartModel)).willReturn(true);
    }

    @Test
    public void testStoreDeliveryAndPaymentAddress()
    {
        commerceCheckoutParameter.setCart(cartModel);
        commerceCheckoutParameter.setAddress(addressModel);

        Assert.assertNull(cartModel.getPaymentAddress());
        Assert.assertNull(cartModel.getDeliveryAddress());
        Assert.assertTrue(defaultSpringerlinkDeliveryAndPaymentAddressStrategy.storeDeliveryAndPaymentAddress(commerceCheckoutParameter));
        Assert.assertNotNull(cartModel.getPaymentAddress());
        Assert.assertNotNull(cartModel.getDeliveryAddress());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStoreDeliveryAndPaymentAddressCartNull()
    {
        commerceCheckoutParameter.setCart(null);
        commerceCheckoutParameter.setAddress(null);

        defaultSpringerlinkDeliveryAndPaymentAddressStrategy.storeDeliveryAndPaymentAddress(commerceCheckoutParameter);
    }

    @Test
    public void testStoreDeliveryAndPaymentAddressAddressNull()
    {
        cartModel.setDeliveryAddress(addressModel);
        cartModel.setPaymentAddress(addressModel);

        commerceCheckoutParameter.setCart(cartModel);
        commerceCheckoutParameter.setAddress(null);

        Assert.assertNotNull(cartModel.getDeliveryAddress());
        Assert.assertNotNull(cartModel.getPaymentAddress());
        Assert.assertTrue(defaultSpringerlinkDeliveryAndPaymentAddressStrategy.storeDeliveryAndPaymentAddress(commerceCheckoutParameter));
        Assert.assertNull(cartModel.getDeliveryAddress());
        Assert.assertNull(cartModel.getPaymentAddress());
    }

}
