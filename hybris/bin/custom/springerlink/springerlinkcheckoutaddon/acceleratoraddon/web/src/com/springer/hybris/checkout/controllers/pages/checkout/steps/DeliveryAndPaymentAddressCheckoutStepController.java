package com.springer.hybris.checkout.controllers.pages.checkout.steps;

import com.springer.hybris.checkout.controllers.SpringerlinkcheckoutaddonControllerConstants;
import com.springernature.hybris.checkout.order.SpringerlinkCheckoutFacade;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.PreValidateCheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.PreValidateQuoteCheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.validation.ValidationResults;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.AbstractCheckoutStepController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;
import de.hybris.platform.acceleratorstorefrontcommons.util.AddressDataUtil;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.address.data.AddressVerificationResult;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commerceservices.address.AddressVerificationDecision;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.util.Config;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by tklostermann on 05.05.2017.
 */
@Controller
@RequestMapping(value = "/checkout/multi/delivery-and-payment-address")
public class DeliveryAndPaymentAddressCheckoutStepController extends AbstractCheckoutStepController {

    private static final String DELIVERY_AND_PAYMENT_ADDRESS = "delivery-and-payment-address";
    private static final String SHOW_SAVE_TO_ADDRESS_BOOK_ATTR = "showSaveToAddressBook";

    @Resource(name = "addressDataUtil")
    private AddressDataUtil addressDataUtil;

    @Resource(name = "springerlinkCheckoutFacade")
    private SpringerlinkCheckoutFacade springerlinkCheckoutFacade;

    @Override
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequireHardLogIn
    @PreValidateQuoteCheckoutStep
    @PreValidateCheckoutStep(checkoutStep = DELIVERY_AND_PAYMENT_ADDRESS)
    public String enterStep(Model model, RedirectAttributes redirectAttributes) throws CMSItemNotFoundException, CommerceCartModificationException {
        getCheckoutFacade().setDeliveryAddressIfAvailable();
        final CartData cartData = getCheckoutFacade().getCheckoutCart();

        populateCommonModelAttributes(model, cartData, new AddressForm());

        return SpringerlinkcheckoutaddonControllerConstants.Views.Pages.MultiStepCheckout.AddEditDeliveryAndPaymentAddressPage;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequireHardLogIn
    public String add(final AddressForm addressForm, final BindingResult bindingResult, final Model model,
                      final RedirectAttributes redirectModel) throws CMSItemNotFoundException
    {
        final CartData cartData = getCheckoutFacade().getCheckoutCart();

        getAddressValidator().validate(addressForm, bindingResult);
        populateCommonModelAttributes(model, cartData, addressForm);

        if (bindingResult.hasErrors())
        {
            GlobalMessages.addErrorMessage(model, "address.error.formentry.invalid");
            return SpringerlinkcheckoutaddonControllerConstants.Views.Pages.MultiStepCheckout.AddEditDeliveryAndPaymentAddressPage;
        }

        final AddressData newAddress = addressDataUtil.convertToAddressData(addressForm);

        processAddressVisibilityAndDefault(addressForm, newAddress);

        // Verify the address data.
        final AddressVerificationResult<AddressVerificationDecision> verificationResult = getAddressVerificationFacade()
                .verifyAddressData(newAddress);
        final boolean addressRequiresReview = getAddressVerificationResultHandler().handleResult(verificationResult, newAddress,
                model, redirectModel, bindingResult, getAddressVerificationFacade().isCustomerAllowedToIgnoreAddressSuggestions(),
                "checkout.multi.address.updated");

        if (addressRequiresReview)
        {
            return SpringerlinkcheckoutaddonControllerConstants.Views.Pages.MultiStepCheckout.AddEditDeliveryAndPaymentAddressPage;
        }

        getUserFacade().addAddress(newAddress);

        final AddressData previousSelectedAddress = getCheckoutFacade().getCheckoutCart().getDeliveryAddress();
        // Set the new address as the selected checkout delivery address
        getCheckoutFacade().setDeliveryAddress(newAddress);
        if (previousSelectedAddress != null && !previousSelectedAddress.isVisibleInAddressBook())
        { // temporary address should be removed
            getUserFacade().removeAddress(previousSelectedAddress);
        }

        // Set the new address as the selected checkout delivery address
        springerlinkCheckoutFacade.setDeliveryAndPaymentAddress(newAddress);

        return getCheckoutStep().nextStep();
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @RequireHardLogIn
    public String doSelectSuggestedAddress(final AddressForm addressForm, final RedirectAttributes redirectModel)
    {
        final Set<String> resolveCountryRegions = org.springframework.util.StringUtils
                .commaDelimitedListToSet(Config.getParameter("resolve.country.regions"));

        final AddressData selectedAddress = addressDataUtil.convertToAddressData(addressForm);
        final CountryData countryData = selectedAddress.getCountry();

        if (!resolveCountryRegions.contains(countryData.getIsocode()))
        {
            selectedAddress.setRegion(null);
        }

        if (addressForm.getSaveInAddressBook() != null)
        {
            selectedAddress.setVisibleInAddressBook(addressForm.getSaveInAddressBook().booleanValue());
        }

        if (Boolean.TRUE.equals(addressForm.getEditAddress()))
        {
            getUserFacade().editAddress(selectedAddress);
        }
        else
        {
            getUserFacade().addAddress(selectedAddress);
        }

        final AddressData previousSelectedAddress = getCheckoutFacade().getCheckoutCart().getDeliveryAddress();
        // Set the new address as the selected checkout delivery address
        springerlinkCheckoutFacade.setDeliveryAndPaymentAddress(selectedAddress);
        if (previousSelectedAddress != null && !previousSelectedAddress.isVisibleInAddressBook())
        { // temporary address should be removed
            getUserFacade().removeAddress(previousSelectedAddress);
        }

        GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "checkout.multi.address.added");

        return getCheckoutStep().nextStep();
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @RequireHardLogIn
    public String doSelectDeliveryAddress(@RequestParam("selectedAddressCode") final String selectedAddressCode,
                                          final RedirectAttributes redirectAttributes)
    {
        final ValidationResults validationResults = getCheckoutStep().validate(redirectAttributes);
        if (getCheckoutStep().checkIfValidationErrors(validationResults))
        {
            return getCheckoutStep().onValidation(validationResults);
        }
        if (StringUtils.isNotBlank(selectedAddressCode))
        {
            final AddressData selectedAddressData = getCheckoutFacade().getDeliveryAddressForCode(selectedAddressCode);
            final boolean hasSelectedAddressData = selectedAddressData != null;
            if (hasSelectedAddressData)
            {
                setDeliveryAddress(selectedAddressData);
            }
        }
        return getCheckoutStep().nextStep();
    }

    protected void setDeliveryAddress(final AddressData selectedAddressData)
    {
        final AddressData cartCheckoutDeliveryAddress = getCheckoutFacade().getCheckoutCart().getDeliveryAddress();
        springerlinkCheckoutFacade.setDeliveryAndPaymentAddress(selectedAddressData);
        if (cartCheckoutDeliveryAddress != null && !cartCheckoutDeliveryAddress.isVisibleInAddressBook())
        { // temporary address should be removed
            getUserFacade().removeAddress(cartCheckoutDeliveryAddress);
        }
    }

    protected void processAddressVisibilityAndDefault(final AddressForm addressForm, final AddressData newAddress)
    {
        if (addressForm.getSaveInAddressBook() != null)
        {
            newAddress.setVisibleInAddressBook(addressForm.getSaveInAddressBook().booleanValue());
            if (addressForm.getSaveInAddressBook().booleanValue() && getUserFacade().isAddressBookEmpty())
            {
                newAddress.setDefaultAddress(true);
            }
        }
        else if (getCheckoutCustomerStrategy().isAnonymousCheckout())
        {
            newAddress.setDefaultAddress(true);
            newAddress.setVisibleInAddressBook(true);
        }
    }

    @RequestMapping(value = "/back", method = RequestMethod.GET)
    @RequireHardLogIn
    @Override
    public String back(RedirectAttributes redirectAttributes) {
        return getCheckoutStep().previousStep();
    }

    @RequestMapping(value = "/next", method = RequestMethod.GET)
    @RequireHardLogIn
    @Override
    public String next(RedirectAttributes redirectAttributes) {
        return getCheckoutStep().nextStep();
    }

    protected String getBreadcrumbKey()
    {
        return "checkout.multi." + getCheckoutStep().getProgressBarId() + ".breadcrumb";
    }

    protected CheckoutStep getCheckoutStep()
    {
        return getCheckoutStep(DELIVERY_AND_PAYMENT_ADDRESS);
    }

    protected void populateCommonModelAttributes(final Model model, final CartData cartData, final AddressForm addressForm)
            throws CMSItemNotFoundException
    {
        model.addAttribute("cartData", cartData);
        model.addAttribute("addressForm", addressForm);
        model.addAttribute("deliveryAddresses", getDeliveryAddresses(cartData.getDeliveryAddress()));
        model.addAttribute("noAddress", Boolean.valueOf(getCheckoutFlowFacade().hasNoDeliveryAddress()));
        model.addAttribute("addressFormEnabled", Boolean.valueOf(getCheckoutFacade().isNewAddressEnabledForCart()));
        model.addAttribute("removeAddressEnabled", Boolean.valueOf(getCheckoutFacade().isRemoveAddressEnabledForCart()));
        model.addAttribute(SHOW_SAVE_TO_ADDRESS_BOOK_ATTR, Boolean.TRUE);
        model.addAttribute(WebConstants.BREADCRUMBS_KEY, getResourceBreadcrumbBuilder().getBreadcrumbs(getBreadcrumbKey()));
        model.addAttribute("metaRobots", "noindex,nofollow");
        if (StringUtils.isNotBlank(addressForm.getCountryIso()))
        {
            model.addAttribute("regions", getI18NFacade().getRegionsForCountryIso(addressForm.getCountryIso()));
            model.addAttribute("country", addressForm.getCountryIso());
        }
        prepareDataForPage(model);
        storeCmsPageInModel(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));
        setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));
        setCheckoutStepLinksForModel(model, getCheckoutStep());
    }
}
