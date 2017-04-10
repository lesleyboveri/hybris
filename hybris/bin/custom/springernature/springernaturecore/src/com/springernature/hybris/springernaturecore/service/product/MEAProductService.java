package com.springernature.hybris.springernaturecore.service.product;


import com.springernature.hybris.springernaturecore.model.MEAProductModel;

/**
 * Created by tklostermann on 04.04.2017.
 */
public interface MEAProductService {

    MEAProductModel getProductForCode(String code);

}
