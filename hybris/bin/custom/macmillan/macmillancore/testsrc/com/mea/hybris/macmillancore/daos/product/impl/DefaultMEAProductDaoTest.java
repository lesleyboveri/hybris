package com.mea.hybris.macmillancore.daos.product.impl;

import com.mea.hybris.macmillancore.daos.product.MEAProductDao;
import de.hybris.bootstrap.annotations.IntegrationTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by tklostermann on 04.04.2017.
 */
@IntegrationTest
public class DefaultMEAProductDaoTest {

    @Resource
    private MEAProductDao meaProductDao;

    @Test
    public void testGetSingleProduct()
    {
        //meaProductDao.findProductsByCode()
    }

}
