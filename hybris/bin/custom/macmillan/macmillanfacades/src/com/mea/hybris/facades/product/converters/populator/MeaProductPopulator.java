package com.mea.hybris.facades.product.converters.populator;

import com.mea.hybris.facades.data.MEAProductData;
import com.mea.hybris.macmillancore.model.MEAProductModel;
import de.hybris.platform.commercefacades.product.converters.populator.AbstractProductPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * Created by tklostermann on 04.04.2017.
 */
public class MeaProductPopulator<SOURCE extends MEAProductModel, TARGET extends MEAProductData> extends
        AbstractProductPopulator<SOURCE, TARGET> {

    @Override
    public void populate(SOURCE source, TARGET target) throws ConversionException {
        target.setPublicationDate(source.getPublicationDate());
    }
}
