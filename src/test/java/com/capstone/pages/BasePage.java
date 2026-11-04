package com.capstone.pages;

import com.capstone.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {

    protected WebDriver driver;
    protected WaitUtils waits;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
    }

    protected void click(By locator) {
        waits.waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waits.waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waits.waitForVisible(locator).getText();
    }
}