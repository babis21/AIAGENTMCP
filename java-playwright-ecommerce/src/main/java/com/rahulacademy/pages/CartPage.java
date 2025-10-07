package com.rahulacademy.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Page Object Model for Cart Page
 * Handles all interactions with the shopping cart page
 */
public class CartPage {
    
    private final Page page;
    
    // Locators
    private final Locator cartTable;
    private final Locator productRows;
    private final Locator totalAmount;
    private final Locator continueShoppingButton;
    private final Locator checkoutButton;
    
    public CartPage(Page page) {
        this.page = page;
        
        // Initialize locators
        this.cartTable = page.getByRole(com.microsoft.playwright.options.AriaRole.TABLE);
        this.productRows = page.getByRole(com.microsoft.playwright.options.AriaRole.ROW);
        this.totalAmount = page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING, 
                new Page.GetByRoleOptions().setName("₹. 100000"));
        this.continueShoppingButton = page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON, 
                new Page.GetByRoleOptions().setName("Continue Shopping"));
        this.checkoutButton = page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON, 
                new Page.GetByRoleOptions().setName("Checkout"));
    }
    
    /**
     * Verify cart page is loaded
     */
    public CartPage verifyCartPageLoaded() {
        assertThat(cartTable).isVisible();
        return this;
    }
    
    /**
     * Get product row by name
     */
    public Locator getProductRow(String productName) {
        return productRows.filter(new Locator.FilterOptions().setHasText(productName));
    }
    
    /**
     * Verify product is in cart
     */
    public CartPage verifyProductInCart(String productName) {
        Locator productRow = getProductRow(productName);
        assertThat(productRow).isVisible();
        assertThat(productRow.getByText(productName)).isVisible();
        assertThat(productRow.getByText("In Stock")).isVisible();
        return this;
    }
    
    /**
     * Verify product quantity
     */
    public CartPage verifyProductQuantity(String productName, String expectedQuantity) {
        Locator productRow = getProductRow(productName);
        assertThat(productRow.getByRole(com.microsoft.playwright.options.AriaRole.SPINBUTTON))
                .hasValue(expectedQuantity);
        return this;
    }
    
    /**
     * Verify product price in cart
     */
    public CartPage verifyProductPrice(String productName, String expectedPrice) {
        Locator productRow = getProductRow(productName);
        assertThat(productRow.getByText(expectedPrice).first()).isVisible();
        return this;
    }
    
    /**
     * Verify total amount
     */
    public CartPage verifyTotalAmount(String expectedTotal) {
        assertThat(page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING, 
                new Page.GetByRoleOptions().setName(expectedTotal))).isVisible();
        return this;
    }
    
    /**
     * Remove product from cart
     */
    public CartPage removeProduct(String productName) {
        Locator productRow = getProductRow(productName);
        productRow.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON, 
                new Locator.GetByRoleOptions().setName("Remove")).click();
        return this;
    }
    
    /**
     * Continue shopping
     */
    public ShopPage continueShopping() {
        continueShoppingButton.click();
        return new ShopPage(page);
    }
    
    /**
     * Proceed to checkout
     */
    public CheckoutPage proceedToCheckout() {
        checkoutButton.click();
        return new CheckoutPage(page);
    }
    
    /**
     * Get count of products in cart
     */
    public int getProductCount() {
        // Count rows that contain product information (exclude header and total rows)
        return (int) productRows.filter(new Locator.FilterOptions().setHasText("₹.")).count() - 1; // Subtract total row
    }
}