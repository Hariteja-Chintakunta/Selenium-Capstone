// ABOUTME: Tests for the product detail page including navigation and content verification.
// ABOUTME: Validates product info matches listing, cart operations from detail, and back navigation.
package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.LoginPage;
import com.capstone.pages.ProductDetailPage;
import com.capstone.pages.ProductsPage;
import com.capstone.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ProductDetailTest extends BaseTest {

    private ProductsPage loginAndGetProductsPage() {
        return new LoginPage(driver).login(TestData.VALID_USER, TestData.VALID_PASSWORD);
    }

    @Test(groups = {"smoke", "regression"})
    public void productDetailShowsCorrectName() {
        ProductsPage products = loginAndGetProductsPage();
        List<String> names = products.getProductNames();
        String expectedName = names.get(0);

        ProductDetailPage detail = products.clickProduct(expectedName);
        Assert.assertEquals(detail.getProductName(), expectedName,
                "Detail page product name should match listing");
    }

    @Test(groups = {"regression"})
    public void productDetailShowsCorrectPrice() {
        ProductsPage products = loginAndGetProductsPage();
        List<String> names = products.getProductNames();
        List<Double> prices = products.getProductPrices();
        String expectedName = names.get(0);
        String expectedPrice = "$" + String.format("%.2f", prices.get(0));

        ProductDetailPage detail = products.clickProduct(expectedName);
        Assert.assertEquals(detail.getProductPrice(), expectedPrice,
                "Detail page price should match listing");
    }

    @Test(groups = {"regression"})
    public void productDetailShowsDescription() {
        ProductsPage products = loginAndGetProductsPage();
        String firstProduct = products.getProductNames().get(0);

        ProductDetailPage detail = products.clickProduct(firstProduct);
        Assert.assertFalse(detail.getProductDescription().trim().isEmpty(),
                "Product description should not be empty");
    }

    @Test(groups = {"regression"})
    public void addToCartFromDetailPage() {
        ProductsPage products = loginAndGetProductsPage();
        String firstProduct = products.getProductNames().get(0);

        ProductDetailPage detail = products.clickProduct(firstProduct);
        detail.addToCart();
        Assert.assertTrue(detail.isCartBadgePresent(),
                "Cart badge should appear after adding item");
    }

    @Test(groups = {"regression"})
    public void removeFromCartOnDetailPage() {
        ProductsPage products = loginAndGetProductsPage();
        String firstProduct = products.getProductNames().get(0);

        ProductDetailPage detail = products.clickProduct(firstProduct);
        detail.addToCart();
        Assert.assertTrue(detail.isCartBadgePresent(), "Cart badge should appear");

        detail.removeFromCart();
        Assert.assertFalse(detail.isCartBadgePresent(),
                "Cart badge should disappear after removing item");
    }

    @Test(groups = {"regression"})
    public void backButtonReturnsToProducts() {
        ProductsPage products = loginAndGetProductsPage();
        String firstProduct = products.getProductNames().get(0);

        ProductDetailPage detail = products.clickProduct(firstProduct);
        ProductsPage returned = detail.goBackToProducts();
        Assert.assertEquals(returned.getTitle(), TestData.PRODUCTS_TITLE,
                "Should return to Products page");
    }
}
