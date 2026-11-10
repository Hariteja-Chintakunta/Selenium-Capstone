// ABOUTME: Page object for the checkout complete (order confirmation) page on saucedemo.com.
// ABOUTME: Provides methods to verify the confirmation message and navigate back to products.
package com.capstone.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderConfirmationPage extends SwagBasePage {

    private final By completeHeader = By.className("complete-header");
    private final By backHomeButton = By.id("back-to-products");

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationHeader() {
        return getText(completeHeader);
    }

    @Step("Return to products from order confirmation")
    public InventoryPage clickBackHome() {
        click(backHomeButton);
        return new InventoryPage(driver);
    }
}
