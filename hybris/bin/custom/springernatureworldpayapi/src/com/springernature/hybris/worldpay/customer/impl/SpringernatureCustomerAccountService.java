package com.springernature.hybris.worldpay.customer.impl;

import com.sap.hybris.sapcustomerb2c.outbound.DefaultB2CSapCustomerAccountService;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.core.model.user.CustomerModel;
import org.springframework.beans.factory.annotation.Required;

/**
 * Created by tklostermann on 11.05.2017.
 */
public class SpringernatureCustomerAccountService extends DefaultB2CSapCustomerAccountService {

    private CustomerAccountService worldpayCustomerAccountService;

    @Override
    public void unlinkCCPaymentInfo(CustomerModel customerModel, CreditCardPaymentInfoModel creditCardPaymentInfo) {
        worldpayCustomerAccountService.unlinkCCPaymentInfo(customerModel, creditCardPaymentInfo);
    }

    public CustomerAccountService getWorldpayCustomerAccountService() {
        return worldpayCustomerAccountService;
    }

    @Required
    public void setWorldpayCustomerAccountService(CustomerAccountService worldpayCustomerAccountService) {
        this.worldpayCustomerAccountService = worldpayCustomerAccountService;
    }
}
