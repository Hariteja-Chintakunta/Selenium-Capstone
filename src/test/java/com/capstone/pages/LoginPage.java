// ABOUTME: Page object for the saucedemo.com login page.
// ABOUTME: Provides methods for valid login and login-expecting-error scenarios.
package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public ProductsPage login(String user, String pass) {
        type(usernameField, user);
        type(passwordField, pass);
        click(loginButton);
        return new ProductsPage(driver);
    }

    public LoginPage loginExpectingError(String user, String pass) {
        type(usernameField, user);
        type(passwordField, pass);
        click(loginButton);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
