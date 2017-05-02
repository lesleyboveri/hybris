package com.springernature.hybris.springernaturestorefront.controllers.misc;


import de.hybris.bootstrap.annotations.UnitTest;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

@UnitTest
public class AddToCartControllerTest {

    //private final AddToCartController controller = Mockito.spy(new AddToCartController());
    private AddToCartController controller;

    /*@Mock
    private AddToCartForm form;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private Model model;
    */
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        controller = new AddToCartController();

    }

    @Test
    public void testVerifyMac() throws Exception
    {
        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("productCodePost", "PPVJ");
        request.addParameter("type", "article");
        request.addParameter("doi", "10.1007/s12633-015-9306-7");
        request.addParameter("isxn", "1876-9918");
        request.addParameter("contenttitle", "The Polysiloxanes");
        request.addParameter("copyrightyear", "2015");
        request.addParameter("year", "2015");
        request.addParameter("authors", "Michael J. Owen");
        request.addParameter("title", "Silicon");
        request.addParameter(AddToCartController.PARAM_MAC, "4568cc1638e778e8c5b86cf10585a6e3");
        request.addParameter(AddToCartController.PARAM_RURL, "http://link.springer.com/article/10.1007/s12633-015-9306-7");

        Assert.assertTrue(controller.verifyMac(request, "GUtC&K3T&{Q]d_cR"));
    }


}
