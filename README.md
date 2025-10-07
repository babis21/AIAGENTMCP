# Playwright Test Automation Project

A comprehensive Playwright end-to-end testing project with TypeScript configuration for modern web application testing.

## 🚀 Features

- **Cross-browser testing** - Chromium, Firefox, and WebKit support
- **TypeScript support** - Full type safety and IntelliSense
- **Parallel test execution** - Fast test runs with built-in parallelization
- **Rich reporting** - HTML reports with screenshots and videos
- **Page Object Model** - Organized and maintainable test structure
- **API testing** - Built-in support for REST API testing
- **CI/CD ready** - Pre-configured for continuous integration

## 📁 Project Structure

```
AIAgentMCP/
├── tests/                      # Test files
│   ├── example.spec.ts        # Basic Playwright examples
│   ├── todo.spec.ts           # Page Object Model example
│   └── api.spec.ts            # API testing examples
├── playwright.config.ts        # Playwright configuration
├── package.json               # Dependencies and scripts
├── tsconfig.json              # TypeScript configuration
└── README.md                  # This file
```

## 🛠️ Getting Started

### Prerequisites

- Node.js (version 14 or higher)
- npm or yarn package manager

### Installation

1. **Install dependencies:**
   ```bash
   npm install
   ```

2. **Install Playwright browsers:**
   ```bash
   npx playwright install
   ```

## 🧪 Running Tests

### Basic Commands

```bash
# Run all tests
npm test

# Run tests in headed mode (visible browser)
npm run test:headed

# Debug tests with Playwright Inspector
npm run test:debug

# Show test report
npm run test:report

# Generate test code with Codegen
npm run test:codegen
```

### Advanced Options

```bash
# Run specific test file
npx playwright test tests/example.spec.ts

# Run tests in specific browser
npx playwright test --project=chromium

# Run tests with specific grep pattern
npx playwright test --grep "should allow adding"

# Run tests in parallel
npx playwright test --workers=4
```

## 📝 Writing Tests

### Basic Test Structure

```typescript
import { test, expect } from '@playwright/test';

test('basic test example', async ({ page }) => {
  await page.goto('https://example.com');
  await expect(page).toHaveTitle(/Example/);
});
```

### Page Object Model Example

```typescript
class LoginPage {
  constructor(private page: Page) {}

  async navigate() {
    await this.page.goto('/login');
  }

  async login(username: string, password: string) {
    await this.page.fill('[data-testid="username"]', username);
    await this.page.fill('[data-testid="password"]', password);
    await this.page.click('[data-testid="submit"]');
  }
}
```

### API Testing Example

```typescript
test('API test example', async ({ request }) => {
  const response = await request.get('/api/users');
  expect(response.status()).toBe(200);
  
  const users = await response.json();
  expect(users).toHaveProperty('length');
});
```

## 🔧 Configuration

The `playwright.config.ts` file contains all configuration options:

- **Test directory**: `./tests`
- **Browsers**: Chromium, Firefox, WebKit
- **Reporters**: HTML (default), JSON, JUnit
- **Screenshots**: On failure
- **Videos**: On failure
- **Traces**: On first retry

### Customizing Configuration

```typescript
export default defineConfig({
  use: {
    baseURL: 'http://localhost:3000',
    viewport: { width: 1280, height: 720 },
    ignoreHTTPSErrors: true,
    screenshot: 'only-on-failure',
  },
});
```

## 📊 Test Reports

Playwright generates comprehensive HTML reports automatically. After running tests:

```bash
npm run test:report
```

Reports include:
- Test results with pass/fail status
- Screenshots of failures
- Video recordings
- Network activity
- Console logs

## 🔍 Debugging Tests

### Using Playwright Inspector

```bash
npm run test:debug
```

### Using Console Debugging

```typescript
test('debug example', async ({ page }) => {
  await page.goto('/');
  
  // Pause execution for debugging
  await page.pause();
  
  // Add console logs
  console.log(await page.title());
});
```

## 🚀 CI/CD Integration

### GitHub Actions Example

```yaml
name: Playwright Tests
on:
  push:
    branches: [ main, master ]
  pull_request:
    branches: [ main, master ]
jobs:
  test:
    timeout-minutes: 60
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - uses: actions/setup-node@v3
      with:
        node-version: 18
    - name: Install dependencies
      run: npm ci
    - name: Install Playwright Browsers
      run: npx playwright install --with-deps
    - name: Run Playwright tests
      run: npx playwright test
    - uses: actions/upload-artifact@v3
      if: always()
      with:
        name: playwright-report
        path: playwright-report/
```

## 📚 Best Practices

1. **Use data-testid attributes** for reliable element selection
2. **Implement Page Object Model** for maintainable tests
3. **Use explicit waits** instead of arbitrary timeouts
4. **Group related tests** in describe blocks
5. **Clean up test data** in afterEach hooks
6. **Use meaningful test names** that describe behavior
7. **Keep tests independent** and able to run in any order

## 🛠️ Troubleshooting

### Common Issues

**Browser installation failed:**
```bash
npx playwright install --with-deps
```

**Tests timing out:**
- Increase timeout in playwright.config.ts
- Use proper wait conditions
- Check network connectivity

**Element not found:**
- Verify selectors with Playwright Inspector
- Use more specific locators
- Wait for elements to be visible

## 📖 Additional Resources

- [Playwright Documentation](https://playwright.dev/docs/intro)
- [Playwright API Reference](https://playwright.dev/docs/api/class-playwright)
- [Best Practices Guide](https://playwright.dev/docs/best-practices)
- [Debugging Guide](https://playwright.dev/docs/debug)

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Happy Testing! 🎭**