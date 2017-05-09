package com.springernature.hybris.checkout.order;

import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;

/**
 * Created by tklostermann on 05.05.2017.
 */
public interface SpringerlinkDeliveryAndPaymentAddressStrategy {

    boolean storeDeliveryAndPaymentAddress(CommerceCheckoutParameter parameter);

}
