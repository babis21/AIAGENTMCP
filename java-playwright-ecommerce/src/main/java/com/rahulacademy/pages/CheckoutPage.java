package com.rahulacademy.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Page Object Model for Checkout Page
 * Handles all interactions with the final checkout and purchase page
 */
public class CheckoutPage {
    
    private final Page page;
    
    // Locators
    private final Locator deliveryLocationInput;
    private final Locator termsCheckbox;
    private final Locator termsLabel;
    private final Locator purchaseButton;
    private final Locator successMessage;
    private final Locator orderConfirmationText;
    
    public CheckoutPage(Page page) {
        this.page = page;
        
        // Initialize locators
        this.deliveryLocationInput = page.getByRole(com.microsoft.playwright.options.AriaRole.TEXTBOX, 
                new Page.GetByRoleOptions().setName("Please choose your delivery location"));
        this.termsCheckbox = page.getByRole(com.microsoft.playwright.options.AriaRole.CHECKBOX, 
                new Page.GetByRoleOptions().setName(java.util.regex.Pattern.compile("I agree with the term.*")));
        this.termsLabel = page.getByText("I agree with the term & Conditions");
        this.purchaseButton = page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON, 
                new Page.GetByRoleOptions().setName("Purchase"));
        this.successMessage = page.getByText("Success!");
        this.orderConfirmationText = page.getByText("Thank you! Your order will be delivered in next few weeks :-)");
    }
    
    /**
     * Fill delivery location
     */
    public CheckoutPage fillDeliveryLocation(String location) {
        deliveryLocationInput.fill(location);
        return this;
    }
    
    /**
     * Select country from dropdown
     */
    public CheckoutPage selectCountryFromDropdown(String countryName) {
        // Wait for dropdown to appear
        page.waitForSelector("text=" + countryName);
        page.getByText(countryName).first().click();
        return this;
    }
    
    /**
     * Verify delivery location is set
     */
    public CheckoutPage verifyDeliveryLocation(String expectedLocation) {
        assertThat(deliveryLocationInput).hasValue(expectedLocation);
        return this;
    }
    
    /**
     * Accept terms and conditions
     */
    public CheckoutPage acceptTermsAndConditions() {
        termsLabel.click();
        return this;
    }
    
    /**
     * Verify terms checkbox is checked
     */
    public CheckoutPage verifyTermsAccepted() {
        assertThat(termsCheckbox).isChecked();
        return this;
    }
    
    /**
     * Click purchase button
     */
    public CheckoutPage completePurchase() {
        purchaseButton.click();
        return this;
    }
    
    /**
     * Verify successful purchase
     */
    public CheckoutPage verifySuccessfulPurchase() {
        assertThat(successMessage).isVisible();
        assertThat(orderConfirmationText).isVisible();
        return this;
    }
    
    /**
     * Complete entire checkout process
     */
    public CheckoutPage completeCheckoutProcess(String country) {
        return fillDeliveryLocation("ind")
                .selectCountryFromDropdown(country)
                .verifyDeliveryLocation(country)
                .acceptTermsAndConditions()
                .verifyTermsAccepted()
                .completePurchase()
                .verifySuccessfulPurchase();
    }
    
    /**
     * Get success message text
     */
    public String getSuccessMessage() {
        return successMessage.textContent() + " " + orderConfirmationText.textContent();
    }
}