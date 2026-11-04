// ABOUTME: Page component for the sidebar navigation menu on saucedemo.com.
// ABOUTME: Provides methods for menu open/close, navigation links, and app state reset.
package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SidebarMenu extends BasePage {

    private final By menuButton = By.id("react-burger-menu-btn");
    private final By closeButton = By.id("react-burger-cross-btn");
    private final By menuWrap = By.cssSelector(".bm-menu-wrap");
    private final By allItemsLink = By.id("inventory_sidebar_link");
    private final By aboutLink = By.id("about_sidebar_link");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By resetLink = By.id("reset_sidebar_link");

    public SidebarMenu(WebDriver driver) {
        super(driver);
    }

    public void openMenu() {
        click(menuButton);
        waits.waitForClickable(closeButton);
    }

    public void closeMenu() {
        click(closeButton);
        waits.waitForInvisible(closeButton);
    }

    public boolean isMenuOpen() {
        String hidden = driver.findElement(menuWrap).getAttribute("aria-hidden");
        return "false".equals(hidden);
    }

    public ProductsPage clickAllItems() {
        click(allItemsLink);
        return new ProductsPage(driver);
    }

    public void clickAbout() {
        click(aboutLink);
    }

    public LoginPage clickLogout() {
        click(logoutLink);
        return new LoginPage(driver);
    }

    public void clickResetAppState() {
        click(resetLink);
    }
}
