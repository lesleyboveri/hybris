package com.mea.hybris.facades.product.impl;

import com.mea.hybris.facades.data.MEAProductData;
import com.mea.hybris.facades.product.MEAProductFacade;
import com.mea.hybris.macmillancore.model.MEAProductModel;
import com.mea.hybris.macmillancore.service.product.MEAProductService;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.ReviewData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.springframework.beans.factory.annotation.Required;

import java.util.Collection;

/**
 * Created by tklostermann on 04.04.2017.
 */
public class DefaultMEAProductFacade implements MEAProductFacade {

    private MEAProductService meaProductService;

    private Converter<MEAProductModel, MEAProductData> productConverter;
    private ConfigurablePopulator<MEAProductModel, MEAProductData, ProductOption> productConfiguredPopulator;

    @Override
    public MEAProductData getProductForCodeAndOptions(String code, Collection<ProductOption> options) throws UnknownIdentifierException, IllegalArgumentException {
        MEAProductModel product = getMeaProductService().getProductForCode(code);
        return getProductForOptions(product, options);
    }

    @Override
    public MEAProductData getProductForOptions(final MEAProductModel productModel, final Collection<ProductOption> options)
    {
        final MEAProductData productData = getProductConverter().convert(productModel);

        if (options != null)
        {
            getProductConfiguredPopulator().populate(productModel, productData, options);
        }

        return productData;
    }

    public MEAProductService getMeaProductService() {
        return meaProductService;
    }

    @Required
    public void setMeaProductService(MEAProductService meaProductService) {
        this.meaProductService = meaProductService;
    }

    public Converter<MEAProductModel, MEAProductData> getProductConverter() {
        return productConverter;
    }

    @Required
    public void setProductConverter(Converter<MEAProductModel, MEAProductData> productConverter) {
        this.productConverter = productConverter;
    }

    public ConfigurablePopulator<MEAProductModel, MEAProductData, ProductOption> getProductConfiguredPopulator() {
        return productConfiguredPopulator;
    }

    @Required
    public void setProductConfiguredPopulator(ConfigurablePopulator<MEAProductModel, MEAProductData, ProductOption> productConfiguredPopulator) {
        this.productConfiguredPopulator = productConfiguredPopulator;
    }
}
