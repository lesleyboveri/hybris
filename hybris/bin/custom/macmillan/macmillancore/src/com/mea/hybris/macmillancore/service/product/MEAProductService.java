package com.mea.hybris.macmillancore.service.product;

import com.mea.hybris.macmillancore.jalo.MEAProduct;
import com.mea.hybris.macmillancore.model.MEAProductModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * Created by tklostermann on 04.04.2017.
 */
public interface MEAProductService {

    MEAProductModel getProductForCode(String code);

}
