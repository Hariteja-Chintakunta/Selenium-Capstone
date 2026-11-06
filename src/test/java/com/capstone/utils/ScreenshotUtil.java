// ABOUTME: Captures browser screenshots as files or byte arrays.
// ABOUTME: Used by TestListener for failure screenshots in Extent and Allure reports.
package com.capstone.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String capture(WebDriver driver, String testName) {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Path destination = Paths.get("screenshots", testName + "_" + time + ".png");
        try {
            Files.createDirectories(destination.getParent());
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(source.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Screenshot failed", e);
        }
        return destination.toAbsolutePath().toString();
    }

    public static byte[] captureAsBytes(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
