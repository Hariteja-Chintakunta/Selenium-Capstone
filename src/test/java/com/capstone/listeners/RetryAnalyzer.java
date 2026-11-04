// ABOUTME: Implements IRetryAnalyzer for automatic retry of failed tests.
// ABOUTME: Retry count is configurable via retryCount in config.properties.
package com.capstone.listeners;

import com.capstone.utils.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private final int maxRetry = ConfigReader.getInt("retryCount");

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxRetry) {
            count++;
            return true;
        }
        return false;
    }
}
