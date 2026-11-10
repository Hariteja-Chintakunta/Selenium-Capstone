// ABOUTME: Page object for the shopping cart page on saucedemo.com.
// ABOUTME: Provides methods to inspect cart items, remove products, and proceed to checkout.
package com.capstone.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingBagPage extends SwagBasePage {

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNameElements;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPriceElements;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public ShoppingBagPage(WebDriver driver) {
        super(driver);
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public List<String> getItemNames() {
        return waits.waitForPresence(itemNameElements).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getItemPrices() {
        return waits.waitForPresence(itemPriceElements).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Remove {name} from cart")
    public void removeItemByName(String name) {
        String buttonId = "remove-" + name.toLowerCase().replace(" ", "-");
        click(By.id(buttonId));
    }

    @Step("Click continue shopping")
    public InventoryPage clickContinueShopping() {
        click(continueShoppingButton);
        return new InventoryPage(driver);
    }

    @Step("Proceed to checkout")
    public BuyerInfoPage clickCheckout() {
        click(checkoutButton);
        return new BuyerInfoPage(driver);
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }
}
