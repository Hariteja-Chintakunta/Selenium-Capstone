// ABOUTME: Tests for the sidebar navigation menu on saucedemo.com.
// ABOUTME: Covers menu open/close, All Items, About, Logout, Reset App State links.
package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.CartPage;
import com.capstone.pages.LoginPage;
import com.capstone.pages.ProductsPage;
import com.capstone.pages.SidebarMenu;
import com.capstone.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationTest extends BaseTest {

    private ProductsPage loginAndGetProductsPage() {
        return new LoginPage(driver).login(TestData.VALID_USER, TestData.VALID_PASSWORD);
    }

    @Test(groups = {"regression"})
    public void sidebarMenuOpensAndCloses() {
        loginAndGetProductsPage();
        SidebarMenu sidebar = new SidebarMenu(driver);

        sidebar.openMenu();
        Assert.assertTrue(sidebar.isMenuOpen(), "Menu should be open");

        sidebar.closeMenu();
        Assert.assertFalse(sidebar.isMenuOpen(), "Menu should be closed");
    }

    @Test(groups = {"regression"})
    public void allItemsLinkGoesToProducts() {
        ProductsPage products = loginAndGetProductsPage();
        CartPage cart = products.goToCart();

        SidebarMenu sidebar = new SidebarMenu(driver);
        sidebar.openMenu();
        ProductsPage returned = sidebar.clickAllItems();

        Assert.assertEquals(returned.getTitle(), TestData.PRODUCTS_TITLE,
                "Should navigate to Products page");
    }

    @Test(groups = {"regression"})
    public void aboutLinkNavigatesToSauceLabs() {
        loginAndGetProductsPage();
        SidebarMenu sidebar = new SidebarMenu(driver);
        sidebar.openMenu();
        sidebar.clickAbout();

        Assert.assertTrue(driver.getCurrentUrl().contains("saucelabs.com"),
                "Should navigate to Sauce Labs website");
    }

    @Test(groups = {"regression"})
    public void logoutLinkReturnsToLogin() {
        loginAndGetProductsPage();
        SidebarMenu sidebar = new SidebarMenu(driver);
        sidebar.openMenu();
        LoginPage loginPage = sidebar.clickLogout();

        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"),
                "Should return to login page");
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "Should not be on products page");
    }

    @Test(groups = {"regression"})
    public void resetAppStateClearsCart() {
        ProductsPage products = loginAndGetProductsPage();
        products.addToCart(products.getProductNames().get(0));
        Assert.assertEquals(products.getCartBadgeCount(), 1, "Cart badge should show 1");

        SidebarMenu sidebar = new SidebarMenu(driver);
        sidebar.openMenu();
        sidebar.clickResetAppState();
        sidebar.closeMenu();

        Assert.assertFalse(products.isCartBadgePresent(),
                "Cart badge should be gone after reset");
    }

    @Test(groups = {"regression"})
    public void menuIsNotVisibleByDefault() {
        loginAndGetProductsPage();
        SidebarMenu sidebar = new SidebarMenu(driver);
        Assert.assertFalse(sidebar.isMenuOpen(),
                "Menu should be closed by default");
    }
}
