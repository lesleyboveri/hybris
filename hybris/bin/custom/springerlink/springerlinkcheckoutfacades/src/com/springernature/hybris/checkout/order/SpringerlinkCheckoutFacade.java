package com.springernature.hybris.checkout.order;

import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;

/**
 * Created by tklostermann on 05.05.2017.
 */
public interface SpringerlinkCheckoutFacade extends CheckoutFacade {

    boolean setDeliveryAndPaymentAddress(AddressData addressData);

}
