// ABOUTME: Tests for the login page covering valid login and negative scenarios.
// ABOUTME: Validates error messages for invalid credentials, empty fields, and locked-out users.
package com.capstone.tests;

import com.capstone.base.SwagBaseTest;
import com.capstone.pages.AuthPage;
import com.capstone.pages.InventoryPage;
import com.capstone.utils.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("SauceDemo")
@Feature("Login")
public class AuthTest extends SwagBaseTest {

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify valid credentials land on the Products page with 6 items")
    @Test(groups = {"smoke", "regression"})
    public void validLoginShowsProductsPage() {
        InventoryPage products = new AuthPage(driver)
                .login(TestData.VALID_USER, TestData.VALID_PASSWORD);

        Assert.assertEquals(products.getTitle(), TestData.PRODUCTS_TITLE);
        Assert.assertEquals(products.getProductCount(), TestData.EXPECTED_PRODUCT_COUNT);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify invalid password shows credentials mismatch error")
    @Test(groups = {"regression"})
    public void invalidPasswordShowsError() {
        AuthPage login = new AuthPage(driver)
                .loginExpectingError(TestData.VALID_USER, TestData.INVALID_PASSWORD);

        Assert.assertTrue(login.getErrorMessage()
                .contains(TestData.ERROR_CREDENTIALS_MISMATCH));
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify empty username and password shows username required error")
    @Test(groups = {"regression"})
    public void emptyFieldsShowUsernameRequired() {
        AuthPage login = new AuthPage(driver)
                .loginExpectingError("", "");

        Assert.assertTrue(login.getErrorMessage().contains(TestData.ERROR_USERNAME_REQUIRED));
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify empty password with valid username shows password required error")
    @Test(groups = {"regression"})
    public void emptyPasswordShowsPasswordRequired() {
        AuthPage login = new AuthPage(driver)
                .loginExpectingError(TestData.VALID_USER, "");

        Assert.assertTrue(login.getErrorMessage().contains(TestData.ERROR_PASSWORD_REQUIRED));
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify locked-out user sees a locked-out error message")
    @Test(groups = {"regression"})
    public void lockedUserShowsLockedMessage() {
        AuthPage login = new AuthPage(driver)
                .loginExpectingError(TestData.LOCKED_USER, TestData.VALID_PASSWORD);

        Assert.assertTrue(login.getErrorMessage().contains(TestData.ERROR_LOCKED_OUT));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify login redirects to the Products page")
    @Test(groups = {"smoke", "regression"})
    public void validLoginRedirectsToProductsPage() {
        InventoryPage products = new AuthPage(driver)
                .login(TestData.VALID_USER, TestData.VALID_PASSWORD);

        Assert.assertEquals(products.getTitle(), TestData.PRODUCTS_TITLE,
                "Expected page title to be '" + TestData.PRODUCTS_TITLE
                        + "' but found '" + products.getTitle() + "'");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify browser back button after logout does not return to products page")
    @Test(groups = {"regression"})
    public void backButtonAfterLogoutDoesNotShowProducts() {
        InventoryPage products = new AuthPage(driver)
                .login(TestData.VALID_USER, TestData.VALID_PASSWORD);
        products.logout();
        driver.navigate().back();

        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "Products page should not be reachable after logout");
    }
}
