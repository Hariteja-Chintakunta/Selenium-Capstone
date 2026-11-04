// ABOUTME: Smoke test to verify the browser connects to saucedemo.com correctly.
// ABOUTME: Validates basic setup before running the full test suite.
package com.capstone.tests;

import com.capstone.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SetupCheckTest extends BaseTest {

    @Test(groups = {"smoke"})
    public void pageTitleIsSwagLabs() {
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
    }
}