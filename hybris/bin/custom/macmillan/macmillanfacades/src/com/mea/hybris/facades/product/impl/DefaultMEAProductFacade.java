package com.mea.hybris.facades.product.impl;

import com.mea.hybris.facades.product.MEAProductFacade;
import de.hybris.platform.catalog.enums.ProductReferenceTypeEnum;
import de.hybris.platform.commercefacades.order.data.ConfigurationInfoData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.ProductReferenceData;
import de.hybris.platform.commercefacades.product.data.ReviewData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by tklostermann on 04.04.2017.
 */
public class DefaultMEAProductFacade implements MEAProductFacade {

    private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;
    private Converter<ProductModel, ProductData> productConverter;
    private ProductService productService;

    @Resource(name = "defaultProductFacade")
    private ProductFacade delegate;

    @Override
    public ProductData getProductForOptions(final ProductModel productModel, final Collection<ProductOption> options)
    {
        final ProductData productData = this.getProductConverter().convert(productModel);

        if (options != null)
        {
            this.getProductConfiguredPopulator().populate(productModel, productData, options);
        }

        return productData;
    }

    @Override
    public ProductData getProductForCodeAndOptions(String code, Collection<ProductOption> options) throws UnknownIdentifierException, IllegalArgumentException {
        final ProductModel productModel = getProductService().getProductForCode(code);
        return getProductForOptions(productModel, options);
    }

    @Override
    public ReviewData postReview(String productCode, ReviewData reviewData) throws UnknownIdentifierException, IllegalArgumentException {
        return this.getDelegate().postReview(productCode,reviewData);
    }

    @Override
    public List<ReviewData> getReviews(String productCode) throws UnknownIdentifierException {
        return this.getDelegate().getReviews(productCode);
    }

    @Override
    public List<ReviewData> getReviews(String productCode, Integer numberOfReviews) throws UnknownIdentifierException, IllegalArgumentException {
        return this.getDelegate().getReviews(productCode, numberOfReviews);
    }

    @Override
    public List<ProductReferenceData> getProductReferencesForCode(String code, ProductReferenceTypeEnum referenceType, List<ProductOption> options, Integer limit) {
        return this.getDelegate().getProductReferencesForCode(code, referenceType, options, limit);
    }

    @Override
    public List<ProductReferenceData> getProductReferencesForCode(String code, List<ProductReferenceTypeEnum> referenceTypes, List<ProductOption> options, Integer limit) {
        return this.getDelegate().getProductReferencesForCode(code, referenceTypes, options, limit);
    }

    @Nonnull
    @Override
    public List<ConfigurationInfoData> getConfiguratorSettingsForCode(@Nonnull String code) {
        return this.getDelegate().getConfiguratorSettingsForCode(code);
    }

    private ProductFacade getDelegate()
    {
        return delegate;
    }

    protected ConfigurablePopulator<ProductModel, ProductData, ProductOption> getProductConfiguredPopulator()
    {
        return productConfiguredPopulator;
    }

    @Required
    public void setProductConfiguredPopulator(
            final ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator)
    {
        this.productConfiguredPopulator = productConfiguredPopulator;
    }

    protected Converter<ProductModel, ProductData> getProductConverter()
    {
        return productConverter;
    }

    @Required
    public void setProductConverter(final Converter<ProductModel, ProductData> productConverter)
    {
        this.productConverter = productConverter;
    }

    protected ProductService getProductService()
    {
        return productService;
    }

    @Required
    public void setProductService(final ProductService productService)
    {
        this.productService = productService;
    }

}
