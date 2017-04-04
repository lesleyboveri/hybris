package com.mea.hybris.macmillancore.daos.product.impl;

import com.mea.hybris.macmillancore.daos.product.MEAProductDao;
import com.mea.hybris.macmillancore.model.MEAProductModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;

import java.util.Collections;
import java.util.List;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

/**
 * Created by tklostermann on 04.04.2017.
 */
public class DefaultMEAProductDao extends DefaultGenericDao<MEAProductModel> implements MEAProductDao {

    public DefaultMEAProductDao(final String typecode)
    {
        super(typecode);
    }

    @Override
    public List<MEAProductModel> findProductsByCode(String code) {
        validateParameterNotNull(code, "Product code must not be null!");

        return find(Collections.singletonMap(MEAProductModel.CODE, (Object) code));
    }
}
