package com.springernature.hybris.checkout.order.impl;

import com.springernature.hybris.checkout.order.SpringerlinkCommerceCheckoutService;
import com.springernature.hybris.checkout.order.SpringerlinkDeliveryAndPaymentAddressStrategy;
import de.hybris.platform.commerceservices.enums.SalesApplication;
import de.hybris.platform.commerceservices.order.CommerceCheckoutService;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.commerceservices.service.data.CommerceOrderResult;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.payment.model.PaymentTransactionEntryModel;
import org.springframework.beans.factory.annotation.Required;

import java.math.BigDecimal;

/**
 * Created by tklostermann on 05.05.2017.
 */
public class DefaultSpringerlinkCommerceCheckoutService implements SpringerlinkCommerceCheckoutService {

    private CommerceCheckoutService defaultCommerceCheckoutService;
    private SpringerlinkDeliveryAndPaymentAddressStrategy springerlinkDeliveryAndPaymentAddressStrategy;

    @Override
    public boolean setDeliveryAndPaymentAddress(CommerceCheckoutParameter parameter) {
        return getSpringerlinkDeliveryAndPaymentAddressStrategy().storeDeliveryAndPaymentAddress(parameter);
    }

    @Override
    public boolean setDeliveryAddress(CartModel cartModel, AddressModel addressModel) {
        return getDefaultCommerceCheckoutService().setDeliveryAddress(cartModel, addressModel);
    }

    @Override
    public boolean setDeliveryAddress(CartModel cartModel, AddressModel addressModel, boolean flagAsDeliveryAddress) {
        return getDefaultCommerceCheckoutService().setDeliveryAddress(cartModel, addressModel, flagAsDeliveryAddress);
    }

    @Override
    public boolean setDeliveryAddress(CommerceCheckoutParameter parameter) {
        return getDefaultCommerceCheckoutService().setDeliveryAddress(parameter);
    }

    @Override
    public boolean setDeliveryMode(CartModel cartModel, DeliveryModeModel deliveryModeModel) {
        return getDefaultCommerceCheckoutService().setDeliveryMode(cartModel, deliveryModeModel);
    }

    @Override
    public boolean setDeliveryMode(CommerceCheckoutParameter parameter) {
        return getDefaultCommerceCheckoutService().setDeliveryMode(parameter);
    }

    @Override
    public void validateDeliveryMode(CartModel cartModel) {
        getDefaultCommerceCheckoutService().validateDeliveryMode(cartModel);
    }

    @Override
    public void validateDeliveryMode(CommerceCheckoutParameter parameter) {
        getDefaultCommerceCheckoutService().validateDeliveryMode(parameter);
    }

    @Override
    public boolean setPaymentInfo(CartModel cartModel, PaymentInfoModel paymentInfoModel) {
        return getDefaultCommerceCheckoutService().setPaymentInfo(cartModel, paymentInfoModel);
    }

    @Override
    public boolean setPaymentInfo(CommerceCheckoutParameter parameter) {
        return getDefaultCommerceCheckoutService().setPaymentInfo(parameter);
    }

    @Override
    public PaymentTransactionEntryModel authorizePayment(CartModel cartModel, String securityCode, String paymentProvider) {
        return getDefaultCommerceCheckoutService().authorizePayment(cartModel, securityCode, paymentProvider);
    }

    @Override
    public PaymentTransactionEntryModel authorizePayment(CartModel cartModel, String securityCode, String paymentProvider, BigDecimal amount) {
        return getDefaultCommerceCheckoutService().authorizePayment(cartModel, securityCode, paymentProvider, amount);
    }

    @Override
    public PaymentTransactionEntryModel authorizePayment(CommerceCheckoutParameter parameter) {
        return getDefaultCommerceCheckoutService().authorizePayment(parameter);
    }

    @Override
    public OrderModel placeOrder(CartModel cartModel) throws InvalidCartException {
        return getDefaultCommerceCheckoutService().placeOrder(cartModel);
    }

    @Override
    public OrderModel placeOrder(CartModel cartModel, SalesApplication salesApplication) throws InvalidCartException {
        return getDefaultCommerceCheckoutService().placeOrder(cartModel, salesApplication);
    }

    @Override
    public CommerceOrderResult placeOrder(CommerceCheckoutParameter parameter) throws InvalidCartException {
        return getDefaultCommerceCheckoutService().placeOrder(parameter);
    }

    @Override
    public String getPaymentProvider() {
        return getDefaultCommerceCheckoutService().getPaymentProvider();
    }

    @Override
    public boolean removeDeliveryMode(CartModel cartModel) {
        return getDefaultCommerceCheckoutService().removeDeliveryMode(cartModel);
    }

    @Override
    public boolean removeDeliveryMode(CommerceCheckoutParameter parameter) {
        return getDefaultCommerceCheckoutService().removeDeliveryMode(parameter);
    }

    @Override
    public void calculateCart(CartModel cartModel) {
        getDefaultCommerceCheckoutService().calculateCart(cartModel);
    }

    @Override
    public void calculateCart(CommerceCheckoutParameter parameter) {
        getDefaultCommerceCheckoutService().calculateCart(parameter);
    }

    public CommerceCheckoutService getDefaultCommerceCheckoutService() {
        return defaultCommerceCheckoutService;
    }

    @Required
    public void setDefaultCommerceCheckoutService(CommerceCheckoutService defaultCommerceCheckoutService) {
        this.defaultCommerceCheckoutService = defaultCommerceCheckoutService;
    }

    public SpringerlinkDeliveryAndPaymentAddressStrategy getSpringerlinkDeliveryAndPaymentAddressStrategy() {
        return springerlinkDeliveryAndPaymentAddressStrategy;
    }

    @Required
    public void setSpringerlinkDeliveryAndPaymentAddressStrategy(SpringerlinkDeliveryAndPaymentAddressStrategy springerlinkDeliveryAndPaymentAddressStrategy) {
        this.springerlinkDeliveryAndPaymentAddressStrategy = springerlinkDeliveryAndPaymentAddressStrategy;
    }
}
