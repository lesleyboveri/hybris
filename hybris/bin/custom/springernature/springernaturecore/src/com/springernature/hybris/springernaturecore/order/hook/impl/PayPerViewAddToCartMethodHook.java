package com.springernature.hybris.springernaturecore.order.hook.impl;

import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.hook.CommerceAddToCartMethodHook;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;


public class PayPerViewAddToCartMethodHook implements CommerceAddToCartMethodHook
{
    private static final Logger LOG = LoggerFactory.getLogger(PayPerViewAddToCartMethodHook.class);

    private ModelService modelService;

    @Override
    public void beforeAddToCart(CommerceCartParameter parameters) throws CommerceCartModificationException {
        // nothing to do
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
                getModelService().save(entry);
            }
        }
    }

    public ModelService getModelService() {
        return modelService;
    }

    @Required
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

}

