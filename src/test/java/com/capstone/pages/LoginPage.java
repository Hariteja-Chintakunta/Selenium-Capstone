// ABOUTME: Page object for the saucedemo.com login page.
// ABOUTME: Provides methods for valid login and login-expecting-error scenarios.
package com.capstone.pages;

import io.qameta.allure.Step;
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

    @Step("Login with user {user}")
    public ProductsPage login(String user, String pass) {
        type(usernameField, user);
        type(passwordField, pass);
        click(loginButton);
        return new ProductsPage(driver);
    }

    @Step("Login expecting error with user {user}")
    public LoginPage loginExpectingError(String user, String pass) {
        type(usernameField, user);
        type(passwordField, pass);
        click(loginButton);
        return this;
    }

    @Step("Get login error message")
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }
}