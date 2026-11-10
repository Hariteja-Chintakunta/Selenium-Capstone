// ABOUTME: Page object for the individual product detail page on saucedemo.com.
// ABOUTME: Provides methods to read product info, add/remove from cart, and navigate back.
package com.capstone.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ItemPreviewPage extends SwagBasePage {

    @FindBy(css = "[data-test='inventory-item-name']")
    private WebElement productName;

    @FindBy(css = "[data-test='inventory-item-desc']")
    private WebElement productDescription;

    @FindBy(css = "[data-test='inventory-item-price']")
    private WebElement productPrice;

    @FindBy(css = "button[data-test^='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(css = "button[data-test^='remove']")
    private WebElement removeButton;

    @FindBy(id = "back-to-products")
    private WebElement backButton;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

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
