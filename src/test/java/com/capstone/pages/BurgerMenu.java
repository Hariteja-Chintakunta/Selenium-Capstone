// ABOUTME: Page component for the sidebar navigation menu on saucedemo.com.
// ABOUTME: Provides methods for menu open/close, navigation links, and app state reset.
package com.capstone.pages;

import com.capstone.utils.FailureHighlight;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BurgerMenu extends SwagBasePage {

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeButton;

    @FindBy(css = ".bm-menu-wrap")
    private WebElement menuWrap;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItemsLink;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetLink;

    private static final By closeButtonLocator = By.id("react-burger-cross-btn");

    public BurgerMenu(WebDriver driver) {
        super(driver);
    }

    @Step("Open sidebar menu")
    public void openMenu() {
        click(menuButton);
        waits.waitForClickable(closeButton);
    }

    @Step("Close sidebar menu")
    public void closeMenu() {
        click(closeButton);
        waits.waitForInvisible(closeButtonLocator);
    }

    public boolean isMenuOpen() {
        FailureHighlight.setTarget(menuWrap);
        String hidden = menuWrap.getAttribute("aria-hidden");
        return "false".equals(hidden);
    }

    @Step("Click All Items link")
    public InventoryPage clickAllItems() {
        click(allItemsLink);
        return new InventoryPage(driver);
    }

    @Step("Click About link")
    public void clickAbout() {
        click(aboutLink);
    }

    @Step("Click Logout link")
    public AuthPage clickLogout() {
        click(logoutLink);
        return new AuthPage(driver);
    }

    @Step("Click Reset App State")
    public void clickResetAppState() {
        click(resetLink);
    }
}
