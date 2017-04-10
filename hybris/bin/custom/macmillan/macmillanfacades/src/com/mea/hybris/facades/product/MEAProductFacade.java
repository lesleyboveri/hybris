package com.mea.hybris.facades.product;

import com.mea.hybris.facades.data.MEAProductData;
import com.springernature.hybris.springernaturecore.model.MEAProductModel;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.Collection;

/**
 * Created by tklostermann on 04.04.2017.
 */
public interface MEAProductFacade {

    MEAProductData getProductForCodeAndOptions(String code, Collection<ProductOption> options) throws UnknownIdentifierException,
            IllegalArgumentException;

    MEAProductData getProductForOptions(final MEAProductModel productModel, final Collection<ProductOption> options);

}
