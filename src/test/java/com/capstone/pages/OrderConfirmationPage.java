// ABOUTME: Page object for the checkout complete (order confirmation) page on saucedemo.com.
// ABOUTME: Provides methods to verify the confirmation message and navigate back to products.
package com.capstone.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderConfirmationPage extends SwagBasePage {

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

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
