package com.springernature.hybris.springernaturefacades.cart;

import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.*;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commerceservices.order.*;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


public class PayPerViewCartFacade implements CartFacade
{

    @Resource(name = "defaultCartFacade")
    private CartFacade delegate;

    private CartService cartService;
    private ProductService productService;
    private CommerceCartService commerceCartService;
    private Converter<CommerceCartModification, CartModificationData> cartModificationConverter;

    @Override
    public CartData getSessionCart() {
        return getDelegate().getSessionCart();
    }

    @Override
    public CartData getSessionCartWithEntryOrdering(boolean recentlyAddedFirst) {
        return getDelegate().getSessionCartWithEntryOrdering(recentlyAddedFirst);
    }

    @Override
    public boolean hasSessionCart() {
        return getDelegate().hasSessionCart();
    }

    @Override
    public boolean hasEntries() {
        return getDelegate().hasEntries();
    }

    @Override
    public CartData getMiniCart() {
        return getDelegate().getMiniCart();
    }


    public CartModificationData addToCart(final Map<String,String> parameterMap, final String code) throws CommerceCartModificationException {
        final ProductModel product = getProductService().getProductForCode(code);
        final CartModel cartModel = getCartService().getSessionCart();
        final CommerceCartParameter parameter = new CommerceCartParameter();
        parameter.setEnableHooks(true);
        parameter.setCart(cartModel);
        parameter.setQuantity(1L);
        parameter.setProduct(product);
        parameter.setUnit(product.getUnit());
        parameter.setCreateNewEntry(false);

        parameter.setParameters(parameterMap);

        final CommerceCartModification modification = getCommerceCartService().addToCart(parameter);

        return getCartModificationConverter().convert(modification);
    }

    @Override
    public CartModificationData addToCart(String code, long quantity) throws CommerceCartModificationException {
        return getDelegate().addToCart(code, quantity);
    }

    @Override
    public CartModificationData addToCart(String code, long quantity, String storeId) throws CommerceCartModificationException {
        return getDelegate().addToCart(code, quantity, storeId);
    }

    @Override
    public List<CartModificationData> validateCartData() throws CommerceCartModificationException {
        return getDelegate().validateCartData();
    }

    @Override
    public CartModificationData updateCartEntry(long entryNumber, long quantity) throws CommerceCartModificationException {
        return getDelegate().updateCartEntry(entryNumber, quantity);
    }

    @Override
    public CartModificationData updateCartEntry(long entryNumber, String storeId) throws CommerceCartModificationException {
        return getDelegate().updateCartEntry(entryNumber, storeId);
    }

    @Override
    public CartRestorationData restoreSavedCart(String guid) throws CommerceCartRestorationException {
        return getDelegate().restoreSavedCart(guid);
    }

    @Override
    public List<CountryData> getDeliveryCountries() {
        return getDelegate().getDeliveryCountries();
    }

    @Override
    public CartData estimateExternalTaxes(String deliveryZipCode, String countryIsoCode) {
        return getDelegate().estimateExternalTaxes(deliveryZipCode, countryIsoCode);
    }

    @Override
    public void removeStaleCarts() {
        getDelegate().removeStaleCarts();
    }

    @Override
    public CartRestorationData restoreAnonymousCartAndTakeOwnership(String guid) throws CommerceCartRestorationException {
        return getDelegate().restoreAnonymousCartAndTakeOwnership(guid);
    }

    @Override
    public void removeSessionCart() {
        getDelegate().removeSessionCart();
    }

    @Override
    public List<CartData> getCartsForCurrentUser() {
        return getDelegate().getCartsForCurrentUser();
    }

    @Override
    public String getMostRecentCartGuidForUser(Collection<String> excludedCartsGuid) {
        return getDelegate().getMostRecentCartGuidForUser(excludedCartsGuid);
    }

    @Override
    public String getSessionCartGuid() {
        return getDelegate().getSessionCartGuid();
    }

    @Override
    public CartRestorationData restoreAnonymousCartAndMerge(String fromAnonumousCartGuid, String toUserCartGuid) throws CommerceCartMergingException, CommerceCartRestorationException {
        return getDelegate().restoreAnonymousCartAndMerge(fromAnonumousCartGuid, toUserCartGuid);
    }

    @Override
    public CartRestorationData restoreCartAndMerge(String fromUserCartGuid, String toUserCartGuid) throws CommerceCartRestorationException, CommerceCartMergingException {
        return getDelegate().restoreCartAndMerge(fromUserCartGuid, toUserCartGuid);
    }

    @Override
    public CartModificationData updateCartEntry(OrderEntryData cartEntry) throws CommerceCartModificationException {
        return getDelegate().updateCartEntry(cartEntry);
    }

    @Override
    public void updateCartMetadata(CommerceCartMetadata metadata) {
        getDelegate().updateCartMetadata(metadata);
    }

    public CartService getCartService() {
        return cartService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public CommerceCartService getCommerceCartService() {
        return commerceCartService;
    }

    public void setCommerceCartService(CommerceCartService commerceCartService) {
        this.commerceCartService = commerceCartService;
    }

    public Converter<CommerceCartModification, CartModificationData> getCartModificationConverter() {
        return cartModificationConverter;
    }

    public void setCartModificationConverter(Converter<CommerceCartModification, CartModificationData> cartModificationConverter) {
        this.cartModificationConverter = cartModificationConverter;
    }

    private CartFacade getDelegate()
    {
        return delegate;
    }
}
