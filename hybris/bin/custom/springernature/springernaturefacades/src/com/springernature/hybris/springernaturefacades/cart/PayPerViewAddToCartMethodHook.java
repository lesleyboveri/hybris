package com.springernature.hybris.springernaturefacades.cart;

import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.hook.CommerceAddToCartMethodHook;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PayPerViewAddToCartMethodHook implements CommerceAddToCartMethodHook
{
    private static final Logger LOG = LoggerFactory.getLogger(PayPerViewAddToCartMethodHook.class);

    @Override
    public void beforeAddToCart(CommerceCartParameter parameters) throws CommerceCartModificationException {
        //
    }

    @Override
    public void afterAddToCart(CommerceCartParameter parameters, CommerceCartModification result) throws CommerceCartModificationException {
        if (result.getQuantityAdded() > 0)
        {
            ServicesUtil.validateParameterNotNullStandardMessage("parameters", parameters);
            ServicesUtil.validateParameterNotNullStandardMessage("result", result);

            final AbstractOrderEntryModel entry = result.getEntry();
            if (entry == null)
            {
                LOG.warn("No entry created");
            }
            else
            {
                entry.setParameters(parameters.getParameters());
            }
        }
    }
}
