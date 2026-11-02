package com.capstone.tests;

import com.capstone.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SetupCheckTest extends BaseTest {

    @Test
    public void pageTitleIsSwagLabs() {
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
    }
}