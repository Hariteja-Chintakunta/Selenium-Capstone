// ABOUTME: Base class for all page objects providing common interaction methods.
// ABOUTME: Wraps Selenium actions with explicit waits to prevent flaky tests.
package com.capstone.pages;

import com.capstone.utils.FailureHighlight;
import com.capstone.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SwagBasePage {

    protected WebDriver driver;
    protected WaitUtils waits;

    public SwagBasePage(WebDriver driver) {
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
        FailureHighlight.setTarget(locator);
        return waits.waitForVisible(locator).getText();
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

    protected void selectByVisibleText(By locator, String text) {
        WebElement element = waits.waitForVisible(locator);
        new Select(element).selectByVisibleText(text);
    }
}
