package com.rahulacademy.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Page Object Model for Login Page
 * Handles all interactions with the login page
 */
public class LoginPage {
    
    private final Page page;
    
    // Locators
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator adminRadioButton;
    private final Locator userRadioButton;
    private final Locator roleDropdown;
    private final Locator termsCheckbox;
    private final Locator signInButton;
    private final Locator credentialsText;
    
    // Constants
    private static final String USERNAME = "rahulshettyacademy";
    private static final String PASSWORD = "learning";
    
    public LoginPage(Page page) {
        this.page = page;
        
        // Initialize locators
        this.usernameField = page.getByRole(com.microsoft.playwright.options.AriaRole.TEXTBOX, 
                new Page.GetByRoleOptions().setName("Username:"));
        this.passwordField = page.getByRole(com.microsoft.playwright.options.AriaRole.TEXTBOX, 
                new Page.GetByRoleOptions().setName("Password:"));
        this.adminRadioButton = page.getByRole(com.microsoft.playwright.options.AriaRole.RADIO, 
                new Page.GetByRoleOptions().setName("Admin"));
        this.userRadioButton = page.getByRole(com.microsoft.playwright.options.AriaRole.RADIO, 
                new Page.GetByRoleOptions().setName("User"));
        this.roleDropdown = page.getByRole(com.microsoft.playwright.options.AriaRole.COMBOBOX);
        this.termsCheckbox = page.getByRole(com.microsoft.playwright.options.AriaRole.CHECKBOX, 
                new Page.GetByRoleOptions().setName("I Agree to the terms and conditions"));
        this.signInButton = page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON, 
                new Page.GetByRoleOptions().setName("Sign In"));
        this.credentialsText = page.getByText("(username is rahulshettyacademy and Password is learning)");
    }
    
    /**
     * Navigate to the login page
     */
    public LoginPage navigateToLoginPage() {
        page.navigate("https://rahulshettyacademy.com/loginpagePractise/");
        return this;
    }
    
    /**
     * Verify login page is loaded
     */
    public LoginPage verifyLoginPageLoaded() {
        assertThat(page).hasTitle(java.util.regex.Pattern.compile("LoginPage Practise"));
        assertThat(credentialsText).isVisible();
        return this;
    }
    
    /**
     * Enter username
     */
    public LoginPage enterUsername(String username) {
        usernameField.fill(username);
        return this;
    }
    
    /**
     * Enter password
     */
    public LoginPage enterPassword(String password) {
        passwordField.fill(password);
        return this;
    }
    
    /**
     * Select user role (Admin or User)
     */
    public LoginPage selectUserRole(String role) {
        if ("Admin".equalsIgnoreCase(role)) {
            adminRadioButton.check();
        } else if ("User".equalsIgnoreCase(role)) {
            userRadioButton.check();
        }
        return this;
    }
    
    /**
     * Select role from dropdown
     */
    public LoginPage selectRoleDropdown(String role) {
        roleDropdown.selectOption(role);
        return this;
    }
    
    /**
     * Accept terms and conditions
     */
    public LoginPage acceptTermsAndConditions() {
        termsCheckbox.check();
        return this;
    }
    
    /**
     * Click Sign In button
     */
    public void clickSignIn() {
        signInButton.click();
    }
    
    /**
     * Perform complete login with default credentials
     */
    public void loginWithDefaultCredentials() {
        enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .acceptTermsAndConditions()
                .clickSignIn();
    }
    
    /**
     * Perform login with custom credentials
     */
    public void loginWithCredentials(String username, String password) {
        enterUsername(username)
                .enterPassword(password)
                .acceptTermsAndConditions()
                .clickSignIn();
    }
    
    /**
     * Verify invalid login (stays on login page)
     */
    public LoginPage verifyInvalidLogin() {
        assertThat(page).hasURL(java.util.regex.Pattern.compile(".*loginpagePractise"));
        return this;
    }
}