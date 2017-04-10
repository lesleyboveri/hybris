package com.springernature.hybris.springernaturecore.daos.product;


import com.springernature.hybris.springernaturecore.model.MEAProductModel;

import java.util.List;

/**
 * Created by tklostermann on 04.04.2017.
 */
public interface MEAProductDao {

    List<MEAProductModel> findProductsByCode(final String code);
}
