package com.mea.hybris.macmillancore.service.product.impl;

import com.mea.hybris.macmillancore.daos.product.MEAProductDao;
import com.mea.hybris.macmillancore.model.MEAProductModel;
import com.mea.hybris.macmillancore.service.product.MEAProductService;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateIfSingleResult;
import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;
import static java.lang.String.format;

/**
 * Created by tklostermann on 04.04.2017.
 */
public class DefaultMEAProductService implements MEAProductService {

    private MEAProductDao meaProductDao;

    @Override
    public MEAProductModel getProductForCode(String code) {
        validateParameterNotNull(code, "Parameter code must not be null");
        final List<MEAProductModel> products = meaProductDao.findProductsByCode(code);

        validateIfSingleResult(products, format("Product with code '%s' not found!", code),
                format("Product code '%s' is not unique, %d products found!", code, Integer.valueOf(products.size())));

        return products.get(0);
    }

    public MEAProductDao getMeaProductDao() {
        return meaProductDao;
    }

    @Required
    public void setMeaProductDao(MEAProductDao meaProductDao) {
        this.meaProductDao = meaProductDao;
    }
}
