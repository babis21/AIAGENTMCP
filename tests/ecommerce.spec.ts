import { test, expect } from '@playwright/test';

test.describe('E-commerce Shopping Flow', () => {
  test('should login, add iPhone X to cart, checkout and verify successful purchase', async ({ page }) => {
    // Navigate to the login page
    await page.goto('https://rahulshettyacademy.com/loginpagePractise/');
    
    // Verify login page loads correctly
    await expect(page).toHaveTitle(/LoginPage Practise/);
    await expect(page.getByText('(username is rahulshettyacademy and Password is learning)')).toBeVisible();

    // Login with provided credentials
    await page.getByRole('textbox', { name: 'Username:' }).fill('rahulshettyacademy');
    await page.getByRole('textbox', { name: 'Password:' }).fill('learning');
    
    // Check terms and conditions
    await page.getByRole('checkbox', { name: 'I Agree to the terms and conditions' }).check();
    
    // Click Sign In button
    await page.getByRole('button', { name: 'Sign In' }).click();
    
    // Wait for navigation to shop page and verify
    await expect(page).toHaveURL(/.*\/shop$/);
    await expect(page).toHaveTitle('ProtoCommerce');
    await expect(page.getByRole('heading', { name: 'Shop Name' })).toBeVisible();

    // Verify iPhone X product is available
    const iphoneCard = page.locator('app-card').filter({ hasText: 'iphone X' });
    await expect(iphoneCard).toBeVisible();
    await expect(iphoneCard.getByRole('heading', { name: 'iphone X' })).toBeVisible();
    await expect(iphoneCard.getByText('$24.99')).toBeVisible();

    // Add iPhone X to cart
    await iphoneCard.getByRole('button', { name: /Add/ }).click();

    // Verify cart counter is updated
    await expect(page.getByText('Checkout ( 1 )')).toBeVisible();

    // Go to checkout
    await page.getByText('Checkout ( 1 )').click();

    // Verify cart contents
    const cartTable = page.getByRole('table');
    await expect(cartTable).toBeVisible();
    
    // Verify iPhone X is in cart with correct details
    const productRow = page.getByRole('row').filter({ hasText: 'iphone X' });
    await expect(productRow).toBeVisible();
    await expect(productRow.getByText('iphone X')).toBeVisible();
    await expect(productRow.getByText('In Stock')).toBeVisible();
    await expect(productRow.getByText('₹. 100000').first()).toBeVisible();

    // Verify quantity is 1
    await expect(productRow.getByRole('spinbutton')).toHaveValue('1');

    // Verify total amount
    await expect(page.getByRole('heading', { name: '₹. 100000' })).toBeVisible();

    // Proceed to final checkout
    await page.getByRole('button', { name: 'Checkout' }).click();

    // Fill delivery location
    const deliveryInput = page.getByRole('textbox', { name: 'Please choose your delivery location' });
    await deliveryInput.fill('ind');
    
    // Wait for dropdown to appear and select India
    await page.waitForSelector('text=India');
    await page.getByText('India').first().click();
    await expect(deliveryInput).toHaveValue('India');

    // Agree to terms and conditions
    await page.getByText('I agree with the term & Conditions').click();
    
    // Verify checkbox is checked
    await expect(page.getByRole('checkbox', { name: /I agree with the term/ })).toBeChecked();

    // Complete purchase
    await page.getByRole('button', { name: 'Purchase' }).click();

    // Verify successful purchase
    await expect(page.getByText('Success!')).toBeVisible();
    await expect(page.getByText('Thank you! Your order will be delivered in next few weeks :-)')).toBeVisible();
  });

  test('should verify cart functionality - add multiple products', async ({ page }) => {
    // Navigate and login
    await page.goto('https://rahulshettyacademy.com/loginpagePractise/');
    
    await page.getByRole('textbox', { name: 'Username:' }).fill('rahulshettyacademy');
    await page.getByRole('textbox', { name: 'Password:' }).fill('learning');
    await page.getByRole('checkbox', { name: 'I Agree to the terms and conditions' }).check();
    await page.getByRole('button', { name: 'Sign In' }).click();
    
    // Wait for shop page
    await expect(page).toHaveURL(/.*\/shop$/);

    // Add iPhone X to cart
    await page.locator('app-card').filter({ hasText: 'iphone X' }).getByRole('button', { name: /Add/ }).click();
    await expect(page.getByText('Checkout ( 1 )')).toBeVisible();

    // Add Samsung Note 8 to cart
    await page.locator('app-card').filter({ hasText: 'Samsung Note 8' }).getByRole('button', { name: /Add/ }).click();
    await expect(page.getByText('Checkout ( 2 )')).toBeVisible();

    // Go to cart and verify both products
    await page.getByText('Checkout ( 2 )').click();

    // Verify both products are in cart
    await expect(page.getByRole('row').filter({ hasText: 'iphone X' })).toBeVisible();
    await expect(page.getByRole('row').filter({ hasText: 'Samsung Note 8' })).toBeVisible();
  });

  test('should handle invalid login credentials', async ({ page }) => {
    // Navigate to login page
    await page.goto('https://rahulshettyacademy.com/loginpagePractise/');
    
    // Try with wrong credentials
    await page.getByRole('textbox', { name: 'Username:' }).fill('wronguser');
    await page.getByRole('textbox', { name: 'Password:' }).fill('wrongpass');
    await page.getByRole('checkbox', { name: 'I Agree to the terms and conditions' }).check();
    await page.getByRole('button', { name: 'Sign In' }).click();

    // Should remain on login page with error (if any error handling exists)
    await expect(page).toHaveURL(/.*loginpagePractise/);
  });
});