// ABOUTME: Page object for the products listing page on saucedemo.com.
// ABOUTME: Provides methods for product browsing, sorting, cart operations, and navigation.
package com.capstone.pages;

import com.capstone.utils.FailureHighlight;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends SwagBasePage {

    @FindBy(css = "[data-test='title']")
    private WebElement title;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNameElements;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPriceElements;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getText(title);
    }

    public int getProductCount() {
        return waits.waitForPresence(productNameElements).size();
    }

    public List<String> getProductNames() {
        return waits.waitForPresence(productNameElements).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return waits.waitForPresence(productPriceElements).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    @Step("Sort products by {optionText}")
    public void sortBy(String optionText) {
        selectByVisibleText(sortDropdown, optionText);
    }

    @Step("Open product detail: {name}")
    public ItemPreviewPage clickProduct(String name) {
        List<WebElement> names = waits.waitForPresence(productNameElements);
        for (WebElement el : names) {
            if (el.getText().equals(name)) {
                el.click();
                return new ItemPreviewPage(driver);
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
    public ShoppingBagPage goToCart() {
        click(cartLink);
        return new ShoppingBagPage(driver);
    }

    @Step("Logout via sidebar menu")
    public AuthPage logout() {
        BurgerMenu sidebar = new BurgerMenu(driver);
        sidebar.openMenu();
        return sidebar.clickLogout();
    }
}
