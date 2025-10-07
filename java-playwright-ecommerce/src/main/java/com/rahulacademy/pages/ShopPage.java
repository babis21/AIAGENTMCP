package com.rahulacademy.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Page Object Model for Shop Page
 * Handles all interactions with the product shop page
 */
public class ShopPage {
    
    private final Page page;
    
    // Locators
    private final Locator shopTitle;
    private final Locator checkoutButton;
    private final Locator productCards;
    
    public ShopPage(Page page) {
        this.page = page;
        
        // Initialize locators
        this.shopTitle = page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING, 
                new Page.GetByRoleOptions().setName("Shop Name"));
        this.checkoutButton = page.getByText("Checkout (");
        this.productCards = page.locator("app-card");
    }
    
    /**
     * Verify shop page is loaded
     */
    public ShopPage verifyShopPageLoaded() {
        assertThat(page).hasURL(java.util.regex.Pattern.compile(".*\\/shop$"));
        assertThat(page).hasTitle("ProtoCommerce");
        assertThat(shopTitle).isVisible();
        return this;
    }
    
    /**
     * Get product card by name
     */
    public Locator getProductCard(String productName) {
        return productCards.filter(new Locator.FilterOptions().setHasText(productName));
    }
    
    /**
     * Verify product is available
     */
    public ShopPage verifyProductAvailable(String productName, String price) {
        Locator productCard = getProductCard(productName);
        assertThat(productCard).isVisible();
        assertThat(productCard.getByRole(com.microsoft.playwright.options.AriaRole.HEADING, 
                new Locator.GetByRoleOptions().setName(productName))).isVisible();
        assertThat(productCard.getByText(price)).isVisible();
        return this;
    }
    
    /**
     * Add product to cart
     */
    public ShopPage addProductToCart(String productName) {
        Locator productCard = getProductCard(productName);
        productCard.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON, 
                new Locator.GetByRoleOptions().setName(java.util.regex.Pattern.compile("Add.*"))).click();
        return this;
    }
    
    /**
     * Verify cart counter is updated
     */
    public ShopPage verifyCartCounter(int expectedCount) {
        assertThat(page.getByText("Checkout ( " + expectedCount + " )")).isVisible();
        return this;
    }
    
    /**
     * Go to checkout
     */
    public CartPage goToCheckout() {
        checkoutButton.click();
        return new CartPage(page);
    }
    
    /**
     * Get current cart count from the checkout button text
     */
    public int getCartCount() {
        String checkoutText = checkoutButton.textContent();
        // Extract number from "Checkout ( 1 )" format
        return Integer.parseInt(checkoutText.replaceAll("[^0-9]", ""));
    }
}