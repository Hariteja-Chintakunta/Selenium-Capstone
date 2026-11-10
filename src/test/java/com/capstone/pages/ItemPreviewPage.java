// ABOUTME: Page object for the individual product detail page on saucedemo.com.
// ABOUTME: Provides methods to read product info, add/remove from cart, and navigate back.
package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ItemPreviewPage extends SwagBasePage {

    private final By productName = By.cssSelector("[data-test='inventory-item-name']");
    private final By productDescription = By.cssSelector("[data-test='inventory-item-desc']");
    private final By productPrice = By.cssSelector("[data-test='inventory-item-price']");
    private final By addToCartButton = By.cssSelector("button[data-test^='add-to-cart']");
    private final By removeButton = By.cssSelector("button[data-test^='remove']");
    private final By backButton = By.id("back-to-products");
    private final By cartBadge = By.className("shopping_cart_badge");

    public ItemPreviewPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return getText(productName);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    public String getProductDescription() {
        return getText(productDescription);
    }

    public void addToCart() {
        click(addToCartButton);
    }

    public void removeFromCart() {
        click(removeButton);
    }

    public boolean isCartBadgePresent() {
        return isDisplayed(cartBadge);
    }

    public InventoryPage goBackToProducts() {
        click(backButton);
        return new InventoryPage(driver);
    }
}
