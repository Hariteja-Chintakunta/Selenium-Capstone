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
├── testng.xml                       # Test suite configuration (groups filter smoke/regression)
├── src/test/
│   ├── java/com/capstone/
│   │   ├── base/
│   │   │   └── SwagBaseTest.java        # Browser setup/teardown
│   │   ├── pages/
│   │   │   ├── SwagBasePage.java        # Common page interactions with PageFactory
│   │   │   ├── AuthPage.java
│   │   │   ├── InventoryPage.java
│   │   │   ├── ItemPreviewPage.java
│   │   │   ├── ShoppingBagPage.java
│   │   │   ├── BuyerInfoPage.java
│   │   │   ├── OrderSummaryPage.java
│   │   │   ├── OrderConfirmationPage.java
│   │   │   └── BurgerMenu.java
│   │   ├── tests/
│   │   │   ├── SiteLaunchTest.java          # 1 test
│   │   │   ├── AuthTest.java               # 7 tests
│   │   │   ├── InventorySortingTest.java    # 7 tests
│   │   │   ├── ItemPreviewTest.java         # 6 tests
│   │   │   ├── CartManagementTest.java      # 7 tests
│   │   │   ├── PurchaseFlowTest.java        # 8 tests
│   │   │   ├── BurgerMenuTest.java          # 6 tests
│   │   │   └── FullPurchaseJourneyTest.java # 4 tests
│   │   ├── listeners/
│   │   │   ├── TestListener.java    # Screenshot + Extent + Allure
│   │   │   ├── RetryAnalyzer.java   # Rerun failed tests
│   │   │   └── RetryTransformer.java
│   │   └── utils/
│   │       ├── ConfigReader.java
│   │       ├── WaitUtils.java
│   │       ├── ScreenshotUtil.java
│   │       ├── FailureHighlight.java
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

Run regression tests only:
```bash
mvn clean test -Dgroups=regression
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
After test execution, open `reports/ExtentReport.html` in a browser. Failed test screenshots are embedded directly in the report as base64 images.

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
| Site Launch | 1 | Page title verification |
| Authentication | 7 | Valid login, invalid password, empty fields, empty password, locked user, login redirect, back after logout |
| Product Sorting | 7 | Product count, default sort, Z-A sort, price sort (both), name/price validation |
| Item Preview | 6 | Name/price/description match, add/remove cart, back navigation |
| Cart Management | 7 | Add single/multiple, remove, item details, continue shopping, empty cart, badge |
| Purchase Flow | 8 | Complete flow, form validation (3 fields), order summary, price calculation, cancel flows |
| Burger Menu | 6 | Menu open/close, All Items, About, Logout, Reset App State, default state |
| Full Purchase Journey | 4 | Single/multi item purchase, add-remove-checkout, return to shop |
| **Total** | **46** | |

## Configuration

Edit `src/test/resources/config.properties`:

| Property | Default | Description |
|----------|---------|-------------|
| browser | edge | Browser: chrome, firefox, edge |
| headless | false | Run in headless mode |
| baseUrl | https://www.saucedemo.com | Application URL |
| explicitWait | 10 | Explicit wait timeout in seconds |
| retryCount | 1 | Number of retries for failed tests |

## Key Features

- **Page Object Model with PageFactory** - Locators defined via `@FindBy` annotations, elements initialized by Selenium PageFactory
- **Explicit Waits** - No Thread.sleep(), all synchronization via WebDriverWait
- **Auto Screenshot on Failure** - Captured with test name and timestamp, embedded in reports as base64
- **Failed Element Highlighting** - Red outline applied to the last interacted element before failure
- **Dual Reporting** - Both Extent Reports (HTML with embedded screenshots) and Allure Reports
- **Retry Mechanism** - Failed tests automatically retried (configurable)
- **Parallel Execution** - Test modules run in parallel threads
- **Config-Driven** - Browser, URL, credentials, timeouts from config.properties
- **Headless Support** - Chrome, Firefox, and Edge headless modes
