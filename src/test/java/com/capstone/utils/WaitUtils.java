// ABOUTME: Provides explicit wait methods wrapping WebDriverWait.
// ABOUTME: All dynamic element synchronization uses these methods instead of Thread.sleep.
package com.capstone.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(ConfigReader.getInt("explicitWait")));
    }

    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForUrlContains(String text) {
        return wait.until(ExpectedConditions.urlContains(text));
    }

    public boolean waitForInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public boolean waitForTextInElement(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
}