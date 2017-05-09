package com.springernature.hybris.springernaturecore.order.converters.populator;

import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class ParametersPopulator implements Populator<AbstractOrderEntryModel,OrderEntryData> {
    @Override
    public void populate(AbstractOrderEntryModel entry, OrderEntryData data) throws ConversionException {
        data.setParameters(entry.getParameters());
    }
}
