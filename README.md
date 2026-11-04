# SauceDemo Test Automation Framework

Selenium WebDriver automation framework for [SauceDemo](https://www.saucedemo.com) built with Java, Maven, TestNG, Extent Reports, and Allure Reports.

## Prerequisites

- JDK 11 or later (with JAVA_HOME configured)
- Maven 3.6+
- Chrome, Firefox, or Edge browser installed

## Project Structure

```
saucedemo-automation/
├── pom.xml                          # Maven dependencies and plugins
├── testng.xml                       # Test suite configuration
├── src/test/
│   ├── java/com/capstone/
│   │   ├── base/
│   │   │   └── BaseTest.java        # Browser setup/teardown
│   │   ├── pages/
│   │   │   ├── BasePage.java        # Common page interactions
│   │   │   ├── LoginPage.java
│   │   │   ├── ProductsPage.java
│   │   │   ├── ProductDetailPage.java
│   │   │   ├── CartPage.java
│   │   │   ├── CheckoutStepOnePage.java
│   │   │   ├── CheckoutStepTwoPage.java
│   │   │   ├── CheckoutCompletePage.java
│   │   │   └── SidebarMenu.java
│   │   ├── tests/
│   │   │   ├── LoginTest.java       # 6 tests
│   │   │   ├── ProductListingTest.java  # 7 tests
│   │   │   ├── ProductDetailTest.java   # 6 tests
│   │   │   ├── ShoppingCartTest.java    # 7 tests
│   │   │   ├── CheckoutTest.java        # 8 tests
│   │   │   ├── NavigationTest.java      # 6 tests
│   │   │   ├── EndToEndTest.java        # 4 tests
│   │   │   └── SetupCheckTest.java      # 1 test
│   │   ├── listeners/
│   │   │   ├── TestListener.java    # Screenshot + Extent + Allure
│   │   │   ├── RetryAnalyzer.java   # Rerun failed tests
│   │   │   └── RetryTransformer.java
│   │   └── utils/
│   │       ├── ConfigReader.java
│   │       ├── WaitUtils.java
│   │       ├── ScreenshotUtil.java
│   │       └── TestData.java
│   └── resources/
│       ├── config.properties        # Test configuration
│       └── allure.properties
├── reports/                         # Extent Reports (generated)
├── allure-results/                  # Allure results (generated)
└── screenshots/                     # Failure screenshots (generated)
```

## How to Run

Run all tests:
```bash
mvn clean test
```

Run smoke tests only:
```bash
mvn clean test -Dgroups=smoke
```

Run in headless mode:
```bash
mvn clean test -Dheadless=true
```

Run with a different browser:
```bash
mvn clean test -Dbrowser=firefox
```

## Reports

### Extent Reports
After test execution, open `reports/ExtentReport.html` in a browser.

### Allure Reports
Generate and open Allure report:
```bash
mvn allure:serve
```

Or generate report without opening:
```bash
mvn allure:report
```
Report will be at `target/site/allure-maven-plugin/index.html`.

## Test Modules

| Module | Tests | Coverage |
|--------|-------|----------|
| Login | 6 | Valid login, invalid password, empty fields, empty password, locked user, back after logout |
| Product Listing | 7 | Product count, default sort, Z-A sort, price sort (both), name/price validation |
| Product Detail | 6 | Name/price/description match, add/remove cart, back navigation |
| Shopping Cart | 7 | Add single/multiple, remove, item details, continue shopping, empty cart, badge |
| Checkout | 8 | Complete flow, form validation (3 fields), order summary, price calculation, cancel flows |
| Navigation | 6 | Menu open/close, All Items, About, Logout, Reset App State, default state |
| End-to-End | 4 | Single/multi item purchase, add-remove-checkout, return to shop |
| Setup | 1 | Page title verification |
| **Total** | **45** | |

## Configuration

Edit `src/test/resources/config.properties`:

| Property | Default | Description |
|----------|---------|-------------|
| browser | chrome | Browser: chrome, firefox, edge |
| headless | false | Run in headless mode |
| baseUrl | https://www.saucedemo.com | Application URL |
| explicitWait | 10 | Explicit wait timeout in seconds |
| retryCount | 1 | Number of retries for failed tests |

## Key Features

- **Page Object Model** - All locators in page classes, tests call page methods only
- **Explicit Waits** - No Thread.sleep(), all synchronization via WebDriverWait
- **Auto Screenshot on Failure** - Captured with test name and timestamp
- **Dual Reporting** - Both Extent Reports and Allure Reports
- **Retry Mechanism** - Failed tests automatically retried (configurable)
- **Parallel Execution** - Test modules run in parallel threads
- **Config-Driven** - Browser, URL, credentials, timeouts from config.properties
- **Headless Support** - Chrome, Firefox, and Edge headless modes
