// ABOUTME: Page object for the products listing page on saucedemo.com.
// ABOUTME: Provides methods for product browsing, sorting, cart operations, and navigation.
package com.capstone.pages;

import io.qameta.allure.Step;
import com.capstone.utils.FailureHighlight;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage extends BasePage {

    private final By title = By.cssSelector("[data-test='title']");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By sortDropdown = By.className("product_sort_container");
    private final By cartLink = By.className("shopping_cart_link");
    private final By cartBadge = By.className("shopping_cart_badge");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getText(title);
    }

    public int getProductCount() {
        FailureHighlight.setTarget(productNames);
        waits.waitForVisible(productNames);
        return driver.findElements(productNames).size();
    }

    public List<String> getProductNames() {
        FailureHighlight.setTarget(productNames);
        waits.waitForVisible(productNames);
        return driver.findElements(productNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        FailureHighlight.setTarget(productPrices);
        waits.waitForVisible(productPrices);
        return driver.findElements(productPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    @Step("Sort products by {optionText}")
    public void sortBy(String optionText) {
        selectByVisibleText(sortDropdown, optionText);
    }

    @Step("Open product detail: {name}")
    public ProductDetailPage clickProduct(String name) {
        List<WebElement> names = getElements(productNames);
        for (WebElement el : names) {
            if (el.getText().equals(name)) {
                el.click();
                return new ProductDetailPage(driver);
            }
        }
        throw new RuntimeException("Product not found: " + name);
    }

    @Step("Add to cart: {productName}")
    public void addToCart(String productName) {
        String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        click(By.id(buttonId));
    }

    @Step("Remove from cart: {productName}")
    public void removeFromCart(String productName) {
        String buttonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        click(By.id(buttonId));
    }

    public int getCartBadgeCount() {
        if (!isCartBadgePresent()) {
            return 0;
        }
        return Integer.parseInt(getText(cartBadge));
    }

    public boolean isCartBadgePresent() {
        return isDisplayed(cartBadge);
    }

    @Step("Navigate to cart")
    public CartPage goToCart() {
        click(cartLink);
        return new CartPage(driver);
    }

    @Step("Logout via sidebar menu")
    public LoginPage logout() {
        SidebarMenu sidebar = new SidebarMenu(driver);
        sidebar.openMenu();
        return sidebar.clickLogout();
    }
}