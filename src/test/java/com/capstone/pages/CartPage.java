// ABOUTME: Page object for the shopping cart page on saucedemo.com.
// ABOUTME: Provides methods to inspect cart items, remove products, and proceed to checkout.
package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private final By cartItems = By.className("cart_item");
    private final By itemNames = By.className("inventory_item_name");
    private final By itemPrices = By.className("inventory_item_price");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartItemCount() {
        List<WebElement> items = driver.findElements(cartItems);
        return items.size();
    }

    public List<String> getItemNames() {
        return getElements(itemNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getItemPrices() {
        return getElements(itemPrices).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void removeItemByName(String name) {
        String buttonId = "remove-" + name.toLowerCase().replace(" ", "-");
        click(By.id(buttonId));
    }

    public ProductsPage clickContinueShopping() {
        click(continueShoppingButton);
        return new ProductsPage(driver);
    }

    public CheckoutStepOnePage clickCheckout() {
        click(checkoutButton);
        return new CheckoutStepOnePage(driver);
    }

    public boolean isCartEmpty() {
        return driver.findElements(cartItems).isEmpty();
    }
}
