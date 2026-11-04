package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.LoginPage;
import com.capstone.pages.ProductsPage;
import com.capstone.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "regression"})
    public void validLoginShowsProductsPage() {
        ProductsPage products = new LoginPage(driver)
                .login(ConfigReader.get("username"), ConfigReader.get("password"));

        Assert.assertEquals(products.getTitle(), "Products");
        Assert.assertEquals(products.getProductCount(), 6);
    }

    @Test(groups = {"regression"})
    public void invalidPasswordShowsError() {
        LoginPage login = new LoginPage(driver);
        login.login(ConfigReader.get("username"), "wrong_password");

        Assert.assertTrue(login.getErrorMessage()
                .contains("Username and password do not match"));
    }

    @Test(groups = {"regression"})
    public void emptyFieldsShowUsernameRequired() {
        LoginPage login = new LoginPage(driver);
        login.login("", "");

        Assert.assertTrue(login.getErrorMessage().contains("Username is required"));
    }

    @Test(groups = {"regression"})
    public void lockedUserShowsLockedMessage() {
        LoginPage login = new LoginPage(driver);
        login.login(ConfigReader.get("lockedUser"), ConfigReader.get("password"));

        Assert.assertTrue(login.getErrorMessage().contains("locked out"));
    }

    @Test(groups = {"regression"})
    public void backButtonAfterLogoutDoesNotShowProducts() {
        ProductsPage products = new LoginPage(driver)
                .login(ConfigReader.get("username"), ConfigReader.get("password"));
        products.logout();
        driver.navigate().back();

        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "Products page should not be reachable after logout");
    }
}