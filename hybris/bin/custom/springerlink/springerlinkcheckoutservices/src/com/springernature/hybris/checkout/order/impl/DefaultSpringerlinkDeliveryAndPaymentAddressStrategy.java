package com.springernature.hybris.checkout.order.impl;

import com.springernature.hybris.checkout.order.SpringerlinkDeliveryAndPaymentAddressStrategy;
import de.hybris.platform.commerceservices.delivery.DeliveryService;
import de.hybris.platform.commerceservices.order.CommerceCartCalculationStrategy;
import de.hybris.platform.commerceservices.order.CommerceDeliveryModeValidationStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

/**
 * Created by tklostermann on 05.05.2017.
 */
public class DefaultSpringerlinkDeliveryAndPaymentAddressStrategy implements SpringerlinkDeliveryAndPaymentAddressStrategy {

    private ModelService modelService;
    private CommerceCartCalculationStrategy commerceCartCalculationStrategy;
    private CommerceDeliveryModeValidationStrategy commerceDeliveryModeValidationStrategy;
    private DeliveryService deliveryService;

    @Override
    public boolean storeDeliveryAndPaymentAddress(CommerceCheckoutParameter parameter) {
        final CartModel cartModel = parameter.getCart();
        final AddressModel addressModel = parameter.getAddress();
        final boolean flagAsDeliveryAddress = parameter.isIsDeliveryAddress();

        validateParameterNotNull(cartModel, "Cart model cannot be null");
        getModelService().refresh(cartModel);

        final UserModel user = cartModel.getUser();
        getModelService().refresh(user);

        cartModel.setDeliveryAddress(addressModel);
        cartModel.setPaymentAddress(addressModel);

        // Check that the address model belongs to the same user as the cart
        if (isValidDeliveryAddress(cartModel, addressModel))
        {
            getModelService().save(cartModel);

            if (addressModel != null && flagAsDeliveryAddress && !Boolean.TRUE.equals(addressModel.getShippingAddress()))
            {
                // Flag the address as a delivery address
                addressModel.setShippingAddress(Boolean.TRUE);
                addressModel.setBillingAddress(Boolean.TRUE);
                getModelService().save(addressModel);
            }
            getCommerceCartCalculationStrategy().calculateCart(cartModel);
            // verify if the current delivery mode is still valid for this address
            getCommerceDeliveryModeValidationStrategy().validateDeliveryMode(parameter);
            getModelService().refresh(cartModel);

            return true;
        }

        return false;
    }

    protected boolean isValidDeliveryAddress(final CartModel cartModel, final AddressModel addressModel)
    {
        if (addressModel != null)
        {
            final List<AddressModel> supportedAddresses = getDeliveryService().getSupportedDeliveryAddressesForOrder(cartModel,
                    false);
            return supportedAddresses != null && supportedAddresses.contains(addressModel);
        }
        else
        {
            return true;
        }
    }

    public ModelService getModelService() {
        return modelService;
    }

    @Required
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public CommerceCartCalculationStrategy getCommerceCartCalculationStrategy() {
        return commerceCartCalculationStrategy;
    }

    @Required
    public void setCommerceCartCalculationStrategy(CommerceCartCalculationStrategy commerceCartCalculationStrategy) {
        this.commerceCartCalculationStrategy = commerceCartCalculationStrategy;
    }

    public CommerceDeliveryModeValidationStrategy getCommerceDeliveryModeValidationStrategy() {
        return commerceDeliveryModeValidationStrategy;
    }

    @Required
    public void setCommerceDeliveryModeValidationStrategy(CommerceDeliveryModeValidationStrategy commerceDeliveryModeValidationStrategy) {
        this.commerceDeliveryModeValidationStrategy = commerceDeliveryModeValidationStrategy;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    @Required
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
}
