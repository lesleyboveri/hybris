/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.springernature.hybris.springernaturestorefront.controllers.misc;

import com.springernature.hybris.springernaturefacades.cart.PayPerViewCartFacade;
import de.hybris.platform.acceleratorfacades.product.data.ProductWrapperData;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.AbstractController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddToCartForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddToCartOrderForm;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.converters.populator.GroupCartModificationListPopulator;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.util.Config;
import com.springernature.hybris.springernaturestorefront.controllers.ControllerConstants;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller for Add to Cart functionality which is not specific to a certain page.
 */
@Controller
public class AddToCartController extends AbstractController
{
	private static final String QUANTITY_ATTR = "quantity";
	private static final String TYPE_MISMATCH_ERROR_CODE = "typeMismatch";
	private static final String ERROR_MSG_TYPE = "errorMsg";
	private static final String QUANTITY_INVALID_BINDING_MESSAGE_KEY = "basket.error.quantity.invalid.binding";
	private static final String SHOWN_PRODUCT_COUNT = "springernaturestorefront.storefront.minicart.shownProductCount";

	private static final Logger LOG = Logger.getLogger(AddToCartController.class);

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "payPerViewCartFacade")
	private PayPerViewCartFacade payPerViewCartFacade;

    @Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "groupCartModificationListPopulator")
	private GroupCartModificationListPopulator groupCartModificationListPopulator;

	@RequestMapping(value = "/cart/add", method = RequestMethod.POST, produces = "application/json")
	public String addToCart(@RequestParam("productCodePost") final String code, final Model model,
			@Valid final AddToCartForm form, final BindingResult bindingErrors)
	{
		return addToCart(null, code, model, form, bindingErrors, ControllerConstants.Views.Fragments.Cart.AddToCartPopup);
	}


    static final String PARAM_MAC = "mac";
	static final String PARAM_RURL = "returnurl";

	@RequestMapping(value = "/slcart/add", method = RequestMethod.POST)
	public String addToSpringerLinkCart(@RequestParam("productCodePost") final String code, final Model model,
							@Valid final AddToCartForm form, final BindingResult bindingErrors, final HttpServletRequest request)
	{
        if (StringUtils.isEmpty(request.getParameter(PARAM_MAC))) {
            model.addAttribute(ERROR_MSG_TYPE, "basket.information.mac.missing");
            return null;
        }

        if (!verifyMac(request, Config.getString("ppvMd5SecretKey", null))) {
            model.addAttribute(ERROR_MSG_TYPE, "basket.information.mac.failed");
            return null;
        }

        if (cartFacade.hasSessionCart()) {
            cartFacade.removeSessionCart();
        }

		return addToCart(request.getParameterMap(), code, model, form, bindingErrors, REDIRECT_PREFIX + "/cart/checkout");
	}


	private String addToCart (final Map<String, String[]> parameterMap, final String code, final Model model,
						   final AddToCartForm form, final BindingResult bindingErrors, final String view) {
		if (bindingErrors.hasErrors())
		{
			return getViewWithBindingErrorMessages(model, bindingErrors);
		}

		final long qty = form.getQty();

		if (qty <= 0)
		{
			model.addAttribute(ERROR_MSG_TYPE, "basket.error.quantity.invalid");
			model.addAttribute(QUANTITY_ATTR, Long.valueOf(0L));
		}
		else
		{
			try
			{
				final CartModificationData cartModification = parameterMap != null ? payPerViewCartFacade.addToCart(parameterMap, code) : cartFacade.addToCart(code, qty);
				model.addAttribute(QUANTITY_ATTR, Long.valueOf(cartModification.getQuantityAdded()));
				model.addAttribute("entry", cartModification.getEntry());
				model.addAttribute("cartCode", cartModification.getCartCode());
				model.addAttribute("isQuote", cartFacade.getSessionCart().getQuoteData() != null ? Boolean.TRUE : Boolean.FALSE);

				if (cartModification.getQuantityAdded() == 0L)
				{
					model.addAttribute(ERROR_MSG_TYPE, "basket.information.quantity.noItemsAdded." + cartModification.getStatusCode());
				}
				else if (cartModification.getQuantityAdded() < qty)
				{
					model.addAttribute(ERROR_MSG_TYPE,
							"basket.information.quantity.reducedNumberOfItemsAdded." + cartModification.getStatusCode());
				}
			}
			catch (final CommerceCartModificationException ex)
			{
				logDebugException(ex);
				model.addAttribute(ERROR_MSG_TYPE, "basket.error.occurred");
				model.addAttribute(QUANTITY_ATTR, Long.valueOf(0L));
			}
		}

		model.addAttribute("product", productFacade.getProductForCodeAndOptions(code, Arrays.asList(ProductOption.BASIC)));

		return view;
	}

    boolean verifyMac(final HttpServletRequest request, final String secret)
	{
		final Map<String,String[]> parameterMap= new TreeMap<>(request.getParameterMap());

        final String mac = parameterMap.get(PARAM_MAC)[0];
		parameterMap.remove(PARAM_MAC);
        parameterMap.remove(PARAM_RURL);

        final StringBuilder md5str = new StringBuilder();
        for (String[] value : parameterMap.values()) {
            md5str.append(value[0]);
        }
        md5str.append(secret);
        final String hash = Hex.encodeHexString(DigestUtils.getMd5Digest().digest(md5str.toString().getBytes()));
        return hash.equals(mac);
    }


    protected String getViewWithBindingErrorMessages(final Model model, final BindingResult bindingErrors)
	{
		for (final ObjectError error : bindingErrors.getAllErrors())
		{
			if (isTypeMismatchError(error))
			{
				model.addAttribute(ERROR_MSG_TYPE, QUANTITY_INVALID_BINDING_MESSAGE_KEY);
			}
			else
			{
				model.addAttribute(ERROR_MSG_TYPE, error.getDefaultMessage());
			}
		}
		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	protected boolean isTypeMismatchError(final ObjectError error)
	{
		return error.getCode().equals(TYPE_MISMATCH_ERROR_CODE);
	}

	@RequestMapping(value = "/cart/addGrid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public final String addGridToCart(@RequestBody final AddToCartOrderForm form, final Model model)
	{
		final Set<String> multidErrorMsgs = new HashSet<String>();
		final List<CartModificationData> modificationDataList = new ArrayList<CartModificationData>();

		for (final OrderEntryData cartEntry : form.getCartEntries())
		{
			if (!isValidProductEntry(cartEntry))
			{
				LOG.error("Error processing entry");
			}
			else if (!isValidQuantity(cartEntry))
			{
				multidErrorMsgs.add("basket.error.quantity.invalid");
			}
			else
			{
				final String errorMsg = addEntryToCart(modificationDataList, cartEntry, true);
				if (StringUtils.isNotEmpty(errorMsg))
				{
					multidErrorMsgs.add(errorMsg);
				}

			}
		}

		if (CollectionUtils.isNotEmpty(modificationDataList))
		{
			groupCartModificationListPopulator.populate(null, modificationDataList);

			model.addAttribute("modifications", modificationDataList);
		}

		if (CollectionUtils.isNotEmpty(multidErrorMsgs))
		{
			model.addAttribute("multidErrorMsgs", multidErrorMsgs);
		}

		model.addAttribute("numberShowing", Integer.valueOf(Config.getInt(SHOWN_PRODUCT_COUNT, 3)));


		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	@RequestMapping(value = "/cart/addQuickOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public final String addQuickOrderToCart(@RequestBody final AddToCartOrderForm form, final Model model)
	{
		final List<CartModificationData> modificationDataList = new ArrayList();
		final List<ProductWrapperData> productWrapperDataList = new ArrayList();
		final int maxQuickOrderEntries = Config.getInt("springernaturestorefront.quick.order.rows.max", 25);
		final int sizeOfCartEntries = CollectionUtils.size(form.getCartEntries());
		form.getCartEntries().stream().limit(Math.min(sizeOfCartEntries, maxQuickOrderEntries)).forEach(cartEntry -> {
			String errorMsg = StringUtils.EMPTY;
			final String sku = !isValidProductEntry(cartEntry) ? StringUtils.EMPTY : cartEntry.getProduct().getCode();
			if (StringUtils.isEmpty(sku))
			{
				errorMsg = "text.quickOrder.product.code.invalid";
			}
			else if (!isValidQuantity(cartEntry))
			{
				errorMsg = "text.quickOrder.product.quantity.invalid";
			}
			else
			{
				errorMsg = addEntryToCart(modificationDataList, cartEntry, false);
			}

			if (StringUtils.isNotEmpty(errorMsg))
			{
				productWrapperDataList.add(createProductWrapperData(sku, errorMsg));
			}
		});

		if (CollectionUtils.isNotEmpty(productWrapperDataList))
		{
			model.addAttribute("quickOrderErrorData", productWrapperDataList);
			model.addAttribute("quickOrderErrorMsg", "basket.quick.order.error");
		}

		if (CollectionUtils.isNotEmpty(modificationDataList))
		{
			model.addAttribute("modifications", modificationDataList);
		}

		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	protected ProductWrapperData createProductWrapperData(final String sku, final String errorMsg)
	{
		final ProductWrapperData productWrapperData = new ProductWrapperData();
		final ProductData productData = new ProductData();
		productData.setCode(sku);
		productWrapperData.setProductData(productData);
		productWrapperData.setErrorMsg(errorMsg);
		return productWrapperData;
	}

	protected void logDebugException(final Exception ex)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug(ex);
		}
	}

	protected String addEntryToCart(final List<CartModificationData> modificationDataList, final OrderEntryData cartEntry,
			final boolean isReducedQtyError)
	{
		String errorMsg = StringUtils.EMPTY;
		try
		{
			final long qty = cartEntry.getQuantity().longValue();
			final CartModificationData cartModificationData = cartFacade.addToCart(cartEntry.getProduct().getCode(), qty);
			if (cartModificationData.getQuantityAdded() == 0L)
			{
				errorMsg = "basket.information.quantity.noItemsAdded." + cartModificationData.getStatusCode();
			}
			else if (cartModificationData.getQuantityAdded() < qty && isReducedQtyError)
			{
				errorMsg = "basket.information.quantity.reducedNumberOfItemsAdded." + cartModificationData.getStatusCode();
			}

			modificationDataList.add(cartModificationData);

		}
		catch (final CommerceCartModificationException ex)
		{
			errorMsg = "basket.error.occurred";
			logDebugException(ex);
		}
		return errorMsg;
	}

	protected boolean isValidProductEntry(final OrderEntryData cartEntry)
	{
		return cartEntry.getProduct() != null && StringUtils.isNotBlank(cartEntry.getProduct().getCode());
	}

	protected boolean isValidQuantity(final OrderEntryData cartEntry)
	{
		return cartEntry.getQuantity() != null && cartEntry.getQuantity().longValue() >= 1L;
	}
}
