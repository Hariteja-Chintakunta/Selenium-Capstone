// ABOUTME: TestNG listener for automatic screenshot capture and report generation.
// ABOUTME: Integrates both Extent Reports and Allure for failure logging with screenshots.
package com.capstone.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.capstone.base.BaseTest;
import com.capstone.utils.ScreenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
            spark.config().setDocumentTitle("SauceDemo Test Report");
            spark.config().setReportName("Automation Test Results");
            spark.config().setTheme(Theme.STANDARD);
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Application", "SauceDemo");
            extent.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(
                result.getTestClass().getRealClass().getSimpleName() + " - " + result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.log(Status.FAIL, "Test failed: " + result.getMethod().getMethodName());
        test.log(Status.FAIL, "Cause: " + result.getThrowable().getMessage());

        WebDriver driver = getDriverFromResult(result);
        if (driver != null) {
            String screenshotPath = ScreenshotUtil.capture(driver, result.getMethod().getMethodName());
            test.addScreenCaptureFromPath(screenshotPath);

            byte[] screenshotBytes = ScreenshotUtil.captureAsBytes(driver);
            Allure.addAttachment("Failure Screenshot",
                    new ByteArrayInputStream(screenshotBytes));

            String currentUrl = driver.getCurrentUrl();
            test.log(Status.INFO, "Failed at URL: " + currentUrl);
            Allure.addAttachment("Failed URL", "text/plain", currentUrl);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            extentTest.get().log(Status.SKIP, "Reason: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    private WebDriver getDriverFromResult(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest) {
            return ((BaseTest) testInstance).getDriver();
        }
        return null;
    }
}
