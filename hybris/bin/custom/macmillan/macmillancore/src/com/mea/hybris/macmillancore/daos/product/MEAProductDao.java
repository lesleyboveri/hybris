package com.mea.hybris.macmillancore.daos.product;

import com.mea.hybris.macmillancore.model.MEAProductModel;

import java.util.List;

/**
 * Created by tklostermann on 04.04.2017.
 */
public interface MEAProductDao {

    public List<MEAProductModel> findProductsByCode(final String code);
}
