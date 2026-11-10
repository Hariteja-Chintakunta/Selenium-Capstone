// ABOUTME: Smoke test to verify the browser connects to saucedemo.com correctly.
// ABOUTME: Validates basic setup before running the full test suite.
package com.capstone.tests;

import com.capstone.base.SwagBaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("SauceDemo")
@Feature("Setup")
public class SiteLaunchTest extends SwagBaseTest {

    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify the browser loads saucedemo.com and the page title is Swag Labs")
    @Test(groups = {"smoke"})
    public void pageTitleIsSwagLabs() {
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
    }
}
