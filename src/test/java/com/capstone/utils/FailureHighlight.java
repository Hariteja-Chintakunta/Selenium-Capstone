// ABOUTME: Tracks the last page element inspected so failed assertions can highlight it.
// ABOUTME: Supports both By locators and WebElement references for PageFactory compatibility.
package com.capstone.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class FailureHighlight {

    private static final ThreadLocal<By> targetLocator = new ThreadLocal<>();
    private static final ThreadLocal<WebElement> targetElement = new ThreadLocal<>();

    private FailureHighlight() {
    }

    public static void setTarget(By locator) {
        targetLocator.set(locator);
        targetElement.remove();
    }

    public static void setTarget(WebElement element) {
        targetElement.set(element);
        targetLocator.remove();
    }

    public static By getTarget() {
        return targetLocator.get();
    }

    public static WebElement getTargetElement() {
        return targetElement.get();
    }

    public static void clear() {
        targetLocator.remove();
        targetElement.remove();
    }
}
