// ABOUTME: Tests for the product listing page including count verification and sort functionality.
// ABOUTME: Covers sort by name (A-Z, Z-A), sort by price (low-high, high-low), and data validation.
package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.LoginPage;
import com.capstone.pages.ProductsPage;
import com.capstone.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductListingTest extends BaseTest {

    private ProductsPage loginAndGetProductsPage() {
        return new LoginPage(driver).login(TestData.VALID_USER, TestData.VALID_PASSWORD);
    }

    @Test(groups = {"smoke", "regression"})
    public void productCountIsSix() {
        ProductsPage products = loginAndGetProductsPage();
        Assert.assertEquals(products.getProductCount(), TestData.EXPECTED_PRODUCT_COUNT,
                "Product count should be " + TestData.EXPECTED_PRODUCT_COUNT);
    }

    @Test(groups = {"regression"})
    public void productsAreSortedByNameAToZByDefault() {
        ProductsPage products = loginAndGetProductsPage();
        List<String> names = products.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        Collections.sort(sorted);
        Assert.assertEquals(names, sorted, "Products should be sorted A-Z by default");
    }

    @Test(groups = {"regression"})
    public void sortByNameZToA() {
        ProductsPage products = loginAndGetProductsPage();
        products.sortBy("Name (Z to A)");
        List<String> names = products.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(Collections.reverseOrder());
        Assert.assertEquals(names, sorted, "Products should be sorted Z-A");
    }

    @Test(groups = {"regression"})
    public void sortByPriceLowToHigh() {
        ProductsPage products = loginAndGetProductsPage();
        products.sortBy("Price (low to high)");
        List<Double> prices = products.getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        Assert.assertEquals(prices, sorted, "Prices should be sorted low to high");
    }

    @Test(groups = {"regression"})
    public void sortByPriceHighToLow() {
        ProductsPage products = loginAndGetProductsPage();
        products.sortBy("Price (high to low)");
        List<Double> prices = products.getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Collections.reverseOrder());
        Assert.assertEquals(prices, sorted, "Prices should be sorted high to low");
    }

    @Test(groups = {"regression"})
    public void productNamesAreNotEmpty() {
        ProductsPage products = loginAndGetProductsPage();
        List<String> names = products.getProductNames();
        for (String name : names) {
            Assert.assertFalse(name.trim().isEmpty(), "Product name should not be empty");
        }
    }

    @Test(groups = {"regression"})
    public void productPricesArePositive() {
        ProductsPage products = loginAndGetProductsPage();
        List<Double> prices = products.getProductPrices();
        for (Double price : prices) {
            Assert.assertTrue(price > 0, "Product price should be positive, got: " + price);
        }
    }
}
