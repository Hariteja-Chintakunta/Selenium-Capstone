// ABOUTME: Tests for the product listing page including count verification and sort functionality.
// ABOUTME: Covers sort by name (A-Z, Z-A), sort by price (low-high, high-low), and data validation.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Epic("SauceDemo")
@Feature("Product Listing")
public class InventorySortingTest extends SwagBaseTest {

    private InventoryPage loginAndGetProductsPage() {
        return new AuthPage(driver).login(TestData.VALID_USER, TestData.VALID_PASSWORD);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify product listing page displays exactly 6 products")
    @Test(groups = {"smoke", "regression"})
    public void productCountIsSix() {
        InventoryPage products = loginAndGetProductsPage();
        Assert.assertEquals(products.getProductCount(), TestData.EXPECTED_PRODUCT_COUNT,
                "Product count should be " + TestData.EXPECTED_PRODUCT_COUNT);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify products are sorted alphabetically A-Z by default")
    @Test(groups = {"regression"})
    public void productsAreSortedByNameAToZByDefault() {
        InventoryPage products = loginAndGetProductsPage();
        List<String> names = products.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        Collections.sort(sorted);
        Assert.assertEquals(names, sorted, "Products should be sorted A-Z by default");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify sort by Name Z-A produces reverse alphabetical order")
    @Test(groups = {"regression"})
    public void sortByNameZToA() {
        InventoryPage products = loginAndGetProductsPage();
        products.sortBy("Name (Z to A)");
        List<String> names = products.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(Collections.reverseOrder());
        Assert.assertEquals(names, sorted, "Products should be sorted Z-A");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify sort by Price low-to-high produces ascending prices")
    @Test(groups = {"regression"})
    public void sortByPriceLowToHigh() {
        InventoryPage products = loginAndGetProductsPage();
        products.sortBy("Price (low to high)");
        List<Double> prices = products.getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        Assert.assertEquals(prices, sorted, "Prices should be sorted low to high");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify sort by Price high-to-low produces descending prices")
    @Test(groups = {"regression"})
    public void sortByPriceHighToLow() {
        InventoryPage products = loginAndGetProductsPage();
        products.sortBy("Price (high to low)");
        List<Double> prices = products.getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Collections.reverseOrder());
        Assert.assertEquals(prices, sorted, "Prices should be sorted high to low");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify no product has an empty name string")
    @Test(groups = {"regression"})
    public void productNamesAreNotEmpty() {
        InventoryPage products = loginAndGetProductsPage();
        List<String> names = products.getProductNames();
        for (String name : names) {
            Assert.assertFalse(name.trim().isEmpty(), "Product name should not be empty");
        }
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify all product prices are greater than zero")
    @Test(groups = {"regression"})
    public void productPricesArePositive() {
        InventoryPage products = loginAndGetProductsPage();
        List<Double> prices = products.getProductPrices();
        for (Double price : prices) {
            Assert.assertTrue(price > 0, "Product price should be positive, got: " + price);
        }
    }
}
