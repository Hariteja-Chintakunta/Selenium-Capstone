// ABOUTME: Page object for checkout step one (shipping info) on saucedemo.com.
// ABOUTME: Provides methods to fill the form, submit, cancel, and read validation errors.
package com.capstone.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BuyerInfoPage extends SwagBasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement zipCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public BuyerInfoPage(WebDriver driver) {
        super(driver);
    }

    @Step("Fill checkout form: {firstName} {lastName} {zipCode}")
    public void fillForm(String firstName, String lastName, String zipCode) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(zipCodeField, zipCode);
    }

    @Step("Continue to checkout step two")
    public OrderSummaryPage clickContinue() {
        click(continueButton);
        return new OrderSummaryPage(driver);
    }

    public BuyerInfoPage clickContinueExpectingError() {
        click(continueButton);
        return this;
    }

    @Step("Cancel checkout")
    public ShoppingBagPage clickCancel() {
        click(cancelButton);
        return new ShoppingBagPage(driver);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

}
