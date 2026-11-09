// ABOUTME: Tracks the last page element inspected so failed assertions can highlight it.
package com.capstone.utils;

import org.openqa.selenium.By;

public final class FailureHighlight {

    private static final ThreadLocal<By> target = new ThreadLocal<>();

    private FailureHighlight() {
    }

    public static void setTarget(By locator) {
        target.set(locator);
    }

    public static By getTarget() {
        return target.get();
    }

    public static void clear() {
        target.remove();
    }
}
