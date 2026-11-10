// ABOUTME: Base class for all page objects providing common interaction methods.
// ABOUTME: Wraps Selenium actions with explicit waits and initializes PageFactory elements.
package com.capstone.pages;

import com.capstone.utils.FailureHighlight;
import com.capstone.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SwagBasePage {

    protected WebDriver driver;
    protected WaitUtils waits;

    public SwagBasePage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element) {
        waits.waitForClickable(element).click();
    }

    protected void click(By locator) {
        waits.waitForClickable(locator).click();
    }

    protected void type(WebElement element, String text) {
        WebElement visible = waits.waitForVisible(element);
        visible.clear();
        visible.sendKeys(text);
    }

    protected void type(By locator, String text) {
        WebElement element = waits.waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        FailureHighlight.setTarget(element);
        return waits.waitForVisible(element).getText();
    }

    protected String getText(By locator) {
        FailureHighlight.setTarget(locator);
        return waits.waitForVisible(locator).getText();
    }

    protected boolean isDisplayed(WebElement element) {
        FailureHighlight.setTarget(element);
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected boolean isDisplayed(By locator) {
        FailureHighlight.setTarget(locator);
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected List<WebElement> getElements(By locator) {
        FailureHighlight.setTarget(locator);
        waits.waitForVisible(locator);
        return driver.findElements(locator);
    }

    protected void selectByVisibleText(WebElement element, String text) {
        WebElement visible = waits.waitForVisible(element);
        new Select(visible).selectByVisibleText(text);
    }

    protected void selectByVisibleText(By locator, String text) {
        WebElement element = waits.waitForVisible(locator);
        new Select(element).selectByVisibleText(text);
    }
}
