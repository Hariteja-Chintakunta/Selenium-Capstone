// ABOUTME: Page object for the checkout complete (order confirmation) page on saucedemo.com.
// ABOUTME: Provides methods to verify the confirmation message and navigate back to products.
package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    private final By completeHeader = By.className("complete-header");
    private final By completeText = By.className("complete-text");
    private final By backHomeButton = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationHeader() {
        return getText(completeHeader);
    }

    public String getConfirmationText() {
        return getText(completeText);
    }

    public ProductsPage clickBackHome() {
        click(backHomeButton);
        return new ProductsPage(driver);
    }
}
