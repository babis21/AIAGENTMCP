package com.rahulacademy.base;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import java.nio.file.Paths;

/**
 * Base test class that provides Playwright browser setup and common test configuration
 */
public class BaseTest {
    
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;
    
    // Configuration properties
    private static final String BROWSER_NAME = System.getProperty("browser.name", "chromium");
    private static final boolean HEADLESS = Boolean.parseBoolean(System.getProperty("browser.headless", "true"));
    private static final String BASE_URL = "https://rahulshettyacademy.com/loginpagePractise/";
    
    @BeforeClass
    public void setUpClass() {
        // Initialize Playwright
        playwright = Playwright.create();
        
        // Launch browser based on configuration
        browser = launchBrowser();
    }
    
    @BeforeMethod
    public void setUp() {
        // Create new browser context for each test
        BrowserContext.NewContextOptions contextOptions = new BrowserContext.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setRecordVideoDir(Paths.get("test-results/videos"));
        
        context = browser.newContext(contextOptions);
        
        // Enable tracing
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        
        // Create new page
        page = context.newPage();
        
        // Set default timeout
        page.setDefaultTimeout(30000);
    }
    
    @AfterMethod
    public void tearDown() {
        if (context != null) {
            // Stop tracing and save
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("test-results/trace.zip")));
            
            // Close context
            context.close();
        }
    }
    
    @AfterClass
    public void tearDownClass() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
    
    /**
     * Launch browser based on system property or default to Chromium
     */
    private Browser launchBrowser() {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(HEADLESS)
                .setSlowMo(500); // Slow down for better visibility in headed mode
        
        switch (BROWSER_NAME.toLowerCase()) {
            case "chrome":
            case "chromium":
                return playwright.chromium().launch(launchOptions);
            case "firefox":
                return playwright.firefox().launch(launchOptions);
            case "webkit":
            case "safari":
                return playwright.webkit().launch(launchOptions);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + BROWSER_NAME);
        }
    }
    
    /**
     * Navigate to the base URL
     */
    protected void navigateToBaseUrl() {
        page.navigate(BASE_URL);
    }
    
    /**
     * Get the current page instance
     */
    protected Page getPage() {
        return page;
    }
    
    /**
     * Take screenshot for reporting
     */
    protected byte[] takeScreenshot() {
        return page.screenshot();
    }
    
    /**
     * Wait for a specific amount of time (use sparingly)
     */
    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}