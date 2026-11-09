// ABOUTME: Captures browser screenshots as files or byte arrays.
// ABOUTME: Used by TestListener for failure screenshots in Extent and Allure reports.
package com.capstone.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScreenshotUtil {

    public static String capture(WebDriver driver, String testName, By highlightTarget) {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Path destination = Paths.get("screenshots", testName + "_" + time + ".png");
        try {
            Files.createDirectories(destination.getParent());
            highlight(driver, highlightTarget);
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(source.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Screenshot failed", e);
        }
        return destination.toAbsolutePath().toString();
    }

    public static byte[] captureAsBytes(WebDriver driver, By highlightTarget) {
        highlight(driver, highlightTarget);
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    private static void highlight(WebDriver driver, By locator) {
        if (locator == null) {
            return;
        }

        List<WebElement> elements = driver.findElements(locator);
        for (WebElement element : elements) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.setProperty('outline', '4px solid #ff0000', 'important');"
                            + "arguments[0].style.setProperty('outline-offset', '3px', 'important');"
                            + "arguments[0].style.setProperty('box-shadow', '0 0 0 4px rgba(255,0,0,.25)', 'important');",
                    element);
        }
    }
}
