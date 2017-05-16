package com.mea.hybris.facades.product.converters.populator;

import com.springernature.hybris.springernaturecore.model.MEAAuthorModel;
import de.hybris.platform.commercefacades.product.converters.populator.ProductBasicPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stn8048 on 5/12/17.
 */
public class MEAProductBasicPopulator<SOURCE extends ProductModel, TARGET extends ProductData> extends ProductBasicPopulator<SOURCE, TARGET> {

    @Override
    public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
    {
        super.populate(productModel, productData);

        if(productModel.getAuthor() != null && ! productModel.getAuthor().isEmpty()) {
            List<String> authors = new ArrayList<String>();
            for (MEAAuthorModel authorModel:productModel.getAuthor()) {
                authors.add(authorModel.getAuthorName());
            }
            productData.setAuthors(authors);
        }

        productData.setComponent(productModel.getComponent());
        productData.setInspectionCopyAllowed(productModel.getInspectionCopyAllowed());
        productData.setPublicationDate(productModel.getPublicationDate());
        productData.setBinding(productModel.getBinding().getCode());
        productData.setFormat(productModel.getFormat().getCode());
        productData.setDivision(productModel.getDivision());
        productData.setSubject(productModel.getSubject());
        productData.setSeries(productModel.getSeries());
        productData.setCurriculum(productModel.getCurriculum());
        productData.setYearLevel(productModel.getYearLevel());
        productData.setSubTitle(productModel.getSubTitle());

    }
}

