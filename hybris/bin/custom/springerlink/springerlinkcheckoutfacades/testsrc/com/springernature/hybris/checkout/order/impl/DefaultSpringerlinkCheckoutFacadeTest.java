package com.springernature.hybris.checkout.order.impl;

import com.springernature.hybris.checkout.order.SpringerlinkCommerceCheckoutService;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commerceservices.delivery.DeliveryService;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.verify;

@UnitTest
public class DefaultSpringerlinkCheckoutFacadeTest {

    @Mock
    private SpringerlinkCommerceCheckoutService springerlinkCommerceCheckoutService;

    @Mock
    private CheckoutFacade defaultCheckoutFacade;

    @Mock
    private CartService cartService;

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private ModelService modelService;

    private DefaultSpringerlinkCheckoutFacade defaultSpringerlinkCheckoutFacade;

    private AddressData addressData;

    private CountryData countryData;

    private CartModel cartModel;

    private AddressModel addressModel;

    private CustomerModel userModel;

    protected static class MockAddressModel extends AddressModel
    {
        @Override
        public de.hybris.platform.core.PK getPk()
        {
            return de.hybris.platform.core.PK.fromLong(9999l);
        }
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        defaultSpringerlinkCheckoutFacade = new DefaultSpringerlinkCheckoutFacade();
        defaultSpringerlinkCheckoutFacade.setSpringerlinkCommerceCheckoutService(springerlinkCommerceCheckoutService);
        defaultSpringerlinkCheckoutFacade.setDefaultCheckoutFacade(defaultCheckoutFacade);
        defaultSpringerlinkCheckoutFacade.setCartService(cartService);
        defaultSpringerlinkCheckoutFacade.setDeliveryService(deliveryService);
        defaultSpringerlinkCheckoutFacade.setModelService(modelService);

        countryData = new CountryData();
        countryData.setIsocode("PL");

        addressData = new AddressData();
        addressData.setId("9999");
        addressData.setTown("Koluszki");
        addressData.setCountry(countryData);

        cartModel = new CartModel();
        addressModel = new MockAddressModel();

        final CountryModel countryModel = new CountryModel();
        addressModel.setCountry(countryModel);
        cartModel.setDeliveryAddress(addressModel);

        final ZoneDeliveryModeModel zoneDeliveryModeModel = new ZoneDeliveryModeModel();
        cartModel.setDeliveryMode(zoneDeliveryModeModel);

        final CreditCardPaymentInfoModel paymentInfoModel = new CreditCardPaymentInfoModel();
        paymentInfoModel.setSubscriptionId("subsId");
        cartModel.setPaymentInfo(paymentInfoModel);

        userModel = new CustomerModel();
        userModel.setDefaultShipmentAddress(addressModel);
        cartModel.setUser(userModel);

        given(Boolean.valueOf(defaultCheckoutFacade.hasCheckoutCart())).willReturn(Boolean.TRUE);
        given(cartService.getSessionCart()).willReturn(cartModel);
        given(deliveryService.getSupportedDeliveryAddressesForOrder(Matchers.<AbstractOrderModel> any(), anyBoolean())).willReturn(
                Collections.singletonList(addressModel));
    }

    @Test
    public void testSetDeliveryAndPaymentAddress() {
        defaultSpringerlinkCheckoutFacade.setDeliveryAndPaymentAddress(addressData);
        verify(springerlinkCommerceCheckoutService).setDeliveryAndPaymentAddress(any(CommerceCheckoutParameter.class));
    }

    @Test
    public void testSetNoDeliveryAndPaymentAddress() {
        given(deliveryService.getSupportedDeliveryAddressesForOrder(Matchers.<AbstractOrderModel> any(), anyBoolean())).willReturn(Collections.emptyList());
        defaultSpringerlinkCheckoutFacade.setDeliveryAndPaymentAddress(addressData);
        verify(modelService, Mockito.never()).save(any(AddressModel.class));
    }

}
