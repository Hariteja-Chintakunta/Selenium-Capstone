// ABOUTME: Tests for shopping cart functionality including add, remove, and item verification.
// ABOUTME: Covers single/multiple items, cart badge updates, continue shopping, and empty cart.
package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.CartPage;
import com.capstone.pages.LoginPage;
import com.capstone.pages.ProductsPage;
import com.capstone.utils.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Epic("SauceDemo")
@Feature("Shopping Cart")
public class ShoppingCartTest extends BaseTest {

    private ProductsPage loginAndGetProductsPage() {
        return new LoginPage(driver).login(TestData.VALID_USER, TestData.VALID_PASSWORD);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify a single added product appears in the shopping cart")
    @Test(groups = {"smoke", "regression"})
    public void addSingleItemShowsInCart() {
        ProductsPage products = loginAndGetProductsPage();
        String firstProduct = products.getProductNames().get(0);
        products.addToCart(firstProduct);

        CartPage cart = products.goToCart();
        List<String> cartItems = cart.getItemNames();
        Assert.assertTrue(cartItems.contains(firstProduct),
                "Cart should contain the added product: " + firstProduct);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify adding two products results in cart count of two")
    @Test(groups = {"regression"})
    public void addMultipleItemsShowsCorrectCount() {
        ProductsPage products = loginAndGetProductsPage();
        List<String> names = products.getProductNames();
        products.addToCart(names.get(0));
        products.addToCart(names.get(1));

        CartPage cart = products.goToCart();
        Assert.assertEquals(cart.getCartItemCount(), 2,
                "Cart should contain 2 items");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify removing the only item leaves the cart empty")
    @Test(groups = {"regression"})
    public void removeItemFromCart() {
        ProductsPage products = loginAndGetProductsPage();
        String firstProduct = products.getProductNames().get(0);
        products.addToCart(firstProduct);

        CartPage cart = products.goToCart();
        Assert.assertEquals(cart.getCartItemCount(), 1, "Cart should have 1 item");

        cart.removeItemByName(firstProduct);
        Assert.assertTrue(cart.isCartEmpty(), "Cart should be empty after removal");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify cart item name and price match the product listing page")
    @Test(groups = {"regression"})
    public void cartItemDetailsMatchProductPage() {
        ProductsPage products = loginAndGetProductsPage();
        String firstProduct = products.getProductNames().get(0);
        Double firstPrice = products.getProductPrices().get(0);
        products.addToCart(firstProduct);

        CartPage cart = products.goToCart();
        Assert.assertTrue(cart.getItemNames().contains(firstProduct),
                "Cart item name should match");
        String cartPrice = cart.getItemPrices().get(0).replace("$", "");
        Assert.assertEquals(Double.parseDouble(cartPrice), firstPrice,
                "Cart item price should match product listing price");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify Continue Shopping button navigates back to the products page")
    @Test(groups = {"regression"})
    public void continueShoppingReturnsToProducts() {
        ProductsPage products = loginAndGetProductsPage();
        products.addToCart(products.getProductNames().get(0));
        CartPage cart = products.goToCart();

        ProductsPage returned = cart.clickContinueShopping();
        Assert.assertEquals(returned.getTitle(), TestData.PRODUCTS_TITLE,
                "Should return to Products page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify navigating to cart without adding products shows zero items")
    @Test(groups = {"regression"})
    public void emptyCartShowsNoItems() {
        ProductsPage products = loginAndGetProductsPage();
        CartPage cart = products.goToCart();
        Assert.assertEquals(cart.getCartItemCount(), 0, "Cart should be empty");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify cart badge appears on add and disappears on remove")
    @Test(groups = {"regression"})
    public void cartBadgeUpdatesOnAddAndRemove() {
        ProductsPage products = loginAndGetProductsPage();
        String firstProduct = products.getProductNames().get(0);

        Assert.assertFalse(products.isCartBadgePresent(),
                "Cart badge should not be present initially");

        products.addToCart(firstProduct);
        Assert.assertEquals(products.getCartBadgeCount(), 1,
                "Cart badge should show 1");

        products.removeFromCart(firstProduct);
        Assert.assertFalse(products.isCartBadgePresent(),
                "Cart badge should disappear after removing");
    }
}
