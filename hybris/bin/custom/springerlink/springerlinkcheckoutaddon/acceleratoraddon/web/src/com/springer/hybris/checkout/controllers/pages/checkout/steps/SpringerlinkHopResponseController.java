package com.springer.hybris.checkout.controllers.pages.checkout.steps;

import com.worldpay.controllers.pages.checkout.steps.WorldpayChoosePaymentMethodCheckoutStepController;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tklostermann on 05.05.2017.
 */
@Controller
@RequestMapping(value = "/checkout/multi/sl-worldpay")
public class SpringerlinkHopResponseController extends WorldpayChoosePaymentMethodCheckoutStepController {

    protected static final String REDIRECT_URL_ADD_DELIVERY_AND_PAYMENT_ADDRESS = REDIRECT_PREFIX + "/checkout/multi/delivery-and-payment-address/add";

    @RequestMapping (value = "/hop-cancel", method = RequestMethod.GET)
    @RequireHardLogIn
    public String doCancelPayment() {
        String redirectUrl = REDIRECT_URL_CHOOSE_PAYMENT_METHOD;
        if (!getCheckoutFacade().hasValidCart()) {
            redirectUrl = REDIRECT_URL_CART;
        } else if (getCheckoutFacade().hasNoDeliveryAddress()) {
            redirectUrl = REDIRECT_URL_ADD_DELIVERY_AND_PAYMENT_ADDRESS;
        } else if (getCheckoutFacade().hasNoDeliveryMode()) {
            redirectUrl = REDIRECT_URL_CHOOSE_DELIVERY_METHOD;
        }
        return redirectUrl;
    }
}
