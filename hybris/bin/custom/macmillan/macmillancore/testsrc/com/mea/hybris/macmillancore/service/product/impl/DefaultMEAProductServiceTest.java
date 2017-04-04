package com.mea.hybris.macmillancore.service.product.impl;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mea.hybris.macmillancore.daos.product.MEAProductDao;
import com.mea.hybris.macmillancore.model.MEAProductModel;
import com.mea.hybris.macmillancore.service.product.MEAProductService;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

/**
 * Created by tklostermann on 04.04.2017.
 */
@UnitTest
public class DefaultMEAProductServiceTest {

    public static final String MEA_PRODUCT_1 = "meaProduct1";
    public static final String EXCEPTION = "Exception";
    // Service under Test
    private DefaultMEAProductService meaProductService;

    @Mock
    private MEAProductDao meaProductDao;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);

        meaProductService = new DefaultMEAProductService();
        meaProductService.setMeaProductDao(meaProductDao);
    }

    @Test
    public void testGetProduct()
    {
        MEAProductModel meaProduct1 = new MEAProductModel();
        meaProduct1.setCode(MEA_PRODUCT_1);

        when(meaProductDao.findProductsByCode(MEA_PRODUCT_1)).thenReturn(Arrays.asList(meaProduct1));

        MEAProductModel result = meaProductService.getProductForCode(MEA_PRODUCT_1);

        assertEquals(MEA_PRODUCT_1, result.getCode());

        verify(meaProductDao).findProductsByCode(MEA_PRODUCT_1);
    }

    @Test(expected = UnknownIdentifierException.class)
    public void testNoProductFound()
    {
        when(meaProductDao.findProductsByCode(MEA_PRODUCT_1)).thenThrow(new UnknownIdentifierException(EXCEPTION));

        meaProductService.getProductForCode(MEA_PRODUCT_1);
    }
}
