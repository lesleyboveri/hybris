package com.springernature.hybris.checkout.order.impl;

import com.springernature.hybris.checkout.order.SpringerlinkCheckoutFacade;
import com.springernature.hybris.checkout.order.SpringerlinkCommerceCheckoutService;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.*;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commerceservices.delivery.DeliveryService;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.servicelayer.model.ModelService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by tklostermann on 05.05.2017.
 */
public class DefaultSpringerlinkCheckoutFacade implements SpringerlinkCheckoutFacade {

    private CheckoutFacade defaultCheckoutFacade;
    private CartService cartService;
    private ModelService modelService;
    private DeliveryService deliveryService;
    private SpringerlinkCommerceCheckoutService springerlinkCommerceCheckoutService;
    private Populator<AddressData, AddressModel> addressReversePopulator;

    @Override
    public boolean setDeliveryAndPaymentAddress(AddressData addressData) {
        final CartModel cartModel = getCart();
        if (cartModel != null)
        {
            AddressModel addressModel = null;
            if (addressData != null)
            {
                addressModel = addressData.getId() == null ? createDeliveryAddressModel(addressData, cartModel)
                        : getDeliveryAddressModelForCode(addressData.getId());
            }

            final CommerceCheckoutParameter parameter = createCommerceCheckoutParameter(cartModel, true);
            parameter.setAddress(addressModel);
            parameter.setIsDeliveryAddress(false);
            return getSpringerlinkCommerceCheckoutService().setDeliveryAndPaymentAddress(parameter);
        }
        return false;
    }

    @Override
    public boolean hasCheckoutCart() {
        return getDefaultCheckoutFacade().hasCheckoutCart();
    }

    protected CartModel getCart()
    {
        return hasCheckoutCart() ? getCartService().getSessionCart() : null;
    }

    @Override
    public CartData getCheckoutCart() {
        return getDefaultCheckoutFacade().getCheckoutCart();
    }

    @Override
    public List<? extends AddressData> getSupportedDeliveryAddresses(boolean visibleAddressesOnly) {
        return getDefaultCheckoutFacade().getSupportedDeliveryAddresses(visibleAddressesOnly);
    }

    @Override
    public AddressData getDeliveryAddressForCode(String code) {
        return getDefaultCheckoutFacade().getDeliveryAddressForCode(code);
    }

    protected AddressModel getDeliveryAddressModelForCode(final String code)
    {
        Assert.notNull(code, "Parameter code cannot be null.");
        final CartModel cartModel = getCart();
        if (cartModel != null)
        {
            for (final AddressModel address : getDeliveryService().getSupportedDeliveryAddressesForOrder(cartModel, false))
            {
                if (code.equals(address.getPk().toString()))
                {
                    return address;
                }
            }
        }
        return null;
    }

    protected AddressModel createDeliveryAddressModel(final AddressData addressData, final CartModel cartModel)
    {
        final AddressModel addressModel = getModelService().create(AddressModel.class);
        getAddressReversePopulator().populate(addressData, addressModel);
        addressModel.setOwner(cartModel);
        return addressModel;
    }

    @Override
    public boolean setDeliveryAddress(AddressData address) {
        return getDefaultCheckoutFacade().setDeliveryAddress(address);
    }

    @Override
    public boolean removeDeliveryAddress() {
        return getDefaultCheckoutFacade().removeDeliveryAddress();
    }

    @Override
    public List<? extends DeliveryModeData> getSupportedDeliveryModes() {
        return getDefaultCheckoutFacade().getSupportedDeliveryModes();
    }

    @Override
    public boolean setDeliveryAddressIfAvailable() {
        return getDefaultCheckoutFacade().setDeliveryAddressIfAvailable();
    }

    @Override
    public boolean setDeliveryModeIfAvailable() {
        return getDefaultCheckoutFacade().setDeliveryModeIfAvailable();
    }

    @Override
    public boolean setPaymentInfoIfAvailable() {
        return getDefaultCheckoutFacade().setPaymentInfoIfAvailable();
    }

    @Override
    public boolean setDeliveryMode(String deliveryModeCode) {
        return getDefaultCheckoutFacade().setDeliveryMode(deliveryModeCode);
    }

    @Override
    public boolean removeDeliveryMode() {
        return getDefaultCheckoutFacade().removeDeliveryMode();
    }

    @Override
    public List<CountryData> getDeliveryCountries() {
        return getDefaultCheckoutFacade().getDeliveryCountries();
    }

    @Override
    public List<CountryData> getBillingCountries() {
        return getDefaultCheckoutFacade().getBillingCountries();
    }

    @Override
    public boolean setPaymentDetails(String paymentInfoId) {
        return getDefaultCheckoutFacade().setPaymentDetails(paymentInfoId);
    }

    @Override
    public List<CardTypeData> getSupportedCardTypes() {
        return getDefaultCheckoutFacade().getSupportedCardTypes();
    }

    @Override
    public CCPaymentInfoData createPaymentSubscription(CCPaymentInfoData paymentInfoData) {
        return getDefaultCheckoutFacade().createPaymentSubscription(paymentInfoData);
    }

    @Override
    public boolean authorizePayment(String securityCode) {
        return getDefaultCheckoutFacade().authorizePayment(securityCode);
    }

    @Override
    public OrderData placeOrder() throws InvalidCartException {
        return getDefaultCheckoutFacade().placeOrder();
    }

    @Override
    public boolean containsTaxValues() {
        return getDefaultCheckoutFacade().containsTaxValues();
    }

    @Override
    public AddressData getAddressDataForId(String addressId, boolean visibleAddressesOnly) {
        return getDefaultCheckoutFacade().getAddressDataForId(addressId, visibleAddressesOnly);
    }

    @Override
    public void prepareCartForCheckout() {
        getDefaultCheckoutFacade().prepareCartForCheckout();
    }

    @Override
    public boolean setDefaultPaymentInfoForCheckout() {
        return getDefaultCheckoutFacade().setDefaultPaymentInfoForCheckout();
    }

    @Override
    public boolean setDefaultDeliveryAddressForCheckout() {
        return getDefaultCheckoutFacade().setDefaultDeliveryAddressForCheckout();
    }

    @Override
    public boolean setCheapestDeliveryModeForCheckout() {
        return getDefaultCheckoutFacade().setCheapestDeliveryModeForCheckout();
    }

    @Override
    public boolean hasShippingItems() {
        return getDefaultCheckoutFacade().hasShippingItems();
    }

    @Override
    public boolean hasPickUpItems() {
        return getDefaultCheckoutFacade().hasPickUpItems();
    }

    protected CommerceCheckoutParameter createCommerceCheckoutParameter(final CartModel cart, final boolean enableHooks)
    {
        final CommerceCheckoutParameter parameter = new CommerceCheckoutParameter();
        parameter.setEnableHooks(enableHooks);
        parameter.setCart(cart);
        return parameter;
    }

    public CheckoutFacade getDefaultCheckoutFacade() {
        return defaultCheckoutFacade;
    }

    @Required
    public void setDefaultCheckoutFacade(CheckoutFacade defaultCheckoutFacade) {
        this.defaultCheckoutFacade = defaultCheckoutFacade;
    }

    public CartService getCartService() {
        return cartService;
    }

    @Required
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public ModelService getModelService() {
        return modelService;
    }

    @Required
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    @Required
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public SpringerlinkCommerceCheckoutService getSpringerlinkCommerceCheckoutService() {
        return springerlinkCommerceCheckoutService;
    }

    @Required
    public void setSpringerlinkCommerceCheckoutService(SpringerlinkCommerceCheckoutService springerlinkCommerceCheckoutService) {
        this.springerlinkCommerceCheckoutService = springerlinkCommerceCheckoutService;
    }

    public Populator<AddressData, AddressModel> getAddressReversePopulator() {
        return addressReversePopulator;
    }

    @Required
    public void setAddressReversePopulator(Populator<AddressData, AddressModel> addressReversePopulator) {
        this.addressReversePopulator = addressReversePopulator;
    }
}
