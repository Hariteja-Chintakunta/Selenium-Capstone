// ABOUTME: Page object for the saucedemo.com login page.
// ABOUTME: Provides methods for valid login and login-expecting-error scenarios.
package com.capstone.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthPage extends SwagBasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public AuthPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage login(String user, String pass) {
        type(usernameField, user);
        type(passwordField, pass);
        click(loginButton);
        return new InventoryPage(driver);
    }

    public AuthPage loginExpectingError(String user, String pass) {
        type(usernameField, user);
        type(passwordField, pass);
        click(loginButton);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
