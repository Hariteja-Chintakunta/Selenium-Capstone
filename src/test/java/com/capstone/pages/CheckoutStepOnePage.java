// ABOUTME: Page object for checkout step one (shipping info) on saucedemo.com.
// ABOUTME: Provides methods to fill the form, submit, cancel, and read validation errors.
package com.capstone.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepOnePage extends BasePage {

    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By zipCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    @Step("Fill checkout form: {firstName} {lastName} {zipCode}")
    public void fillForm(String firstName, String lastName, String zipCode) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(zipCodeField, zipCode);
    }

    @Step("Continue to checkout step two")
    public CheckoutStepTwoPage clickContinue() {
        click(continueButton);
        return new CheckoutStepTwoPage(driver);
    }

    public CheckoutStepOnePage clickContinueExpectingError() {
        click(continueButton);
        return this;
    }

    @Step("Cancel checkout")
    public CartPage clickCancel() {
        click(cancelButton);
        return new CartPage(driver);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }
}
