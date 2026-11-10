// ABOUTME: Tests for the checkout flow including form validation and price verification.
// ABOUTME: Covers complete checkout, empty/missing fields errors, order summary, and price math.
package com.capstone.tests;

import com.capstone.base.SwagBaseTest;
import com.capstone.pages.*;
import com.capstone.utils.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Epic("SauceDemo")
@Feature("Checkout")
public class PurchaseFlowTest extends SwagBaseTest {

    private ShoppingBagPage addItemAndGoToCart() {
        InventoryPage products = new AuthPage(driver)
                .login(TestData.VALID_USER, TestData.VALID_PASSWORD);
        String firstProduct = products.getProductNames().get(0);
        products.addToCart(firstProduct);
        return products.goToCart();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify the full checkout flow completes with an order confirmation message")
    @Test(groups = {"smoke", "regression"})
    public void completeCheckoutShowsConfirmation() {
        ShoppingBagPage cart = addItemAndGoToCart();
        BuyerInfoPage stepOne = cart.clickCheckout();
        stepOne.fillForm(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE);
        OrderSummaryPage stepTwo = stepOne.clickContinue();
        OrderConfirmationPage complete = stepTwo.clickFinish();

        Assert.assertEquals(complete.getConfirmationHeader(), TestData.ORDER_CONFIRMATION,
                "Order confirmation message should be displayed");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify submitting an empty checkout form shows first name required error")
    @Test(groups = {"regression"})
    public void emptyFormShowsFirstNameError() {
        ShoppingBagPage cart = addItemAndGoToCart();
        BuyerInfoPage stepOne = cart.clickCheckout();
        stepOne.clickContinueExpectingError();

        Assert.assertTrue(stepOne.getErrorMessage().contains(TestData.ERROR_FIRSTNAME_REQUIRED),
                "Should show first name required error");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify missing last name shows last name required error")
    @Test(groups = {"regression"})
    public void missingLastNameShowsError() {
        ShoppingBagPage cart = addItemAndGoToCart();
        BuyerInfoPage stepOne = cart.clickCheckout();
        stepOne.fillForm(TestData.FIRST_NAME, "", "");
        stepOne.clickContinueExpectingError();

        Assert.assertTrue(stepOne.getErrorMessage().contains(TestData.ERROR_LASTNAME_REQUIRED),
                "Should show last name required error");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify missing zip code shows postal code required error")
    @Test(groups = {"regression"})
    public void missingZipCodeShowsError() {
        ShoppingBagPage cart = addItemAndGoToCart();
        BuyerInfoPage stepOne = cart.clickCheckout();
        stepOne.fillForm(TestData.FIRST_NAME, TestData.LAST_NAME, "");
        stepOne.clickContinueExpectingError();

        Assert.assertTrue(stepOne.getErrorMessage().contains(TestData.ERROR_ZIPCODE_REQUIRED),
                "Should show zip code required error");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify the order summary page lists all items added to the cart")
    @Test(groups = {"regression"})
    public void orderSummaryShowsCorrectItems() {
        InventoryPage products = new AuthPage(driver)
                .login(TestData.VALID_USER, TestData.VALID_PASSWORD);
        List<String> names = products.getProductNames();
        products.addToCart(names.get(0));
        products.addToCart(names.get(1));

        ShoppingBagPage cart = products.goToCart();
        BuyerInfoPage stepOne = cart.clickCheckout();
        stepOne.fillForm(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE);
        OrderSummaryPage stepTwo = stepOne.clickContinue();

        List<String> summaryItems = stepTwo.getItemNames();
        Assert.assertTrue(summaryItems.contains(names.get(0)),
                "Order summary should contain first item");
        Assert.assertTrue(summaryItems.contains(names.get(1)),
                "Order summary should contain second item");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify total price equals item subtotal plus tax")
    @Test(groups = {"regression"})
    public void priceTotalCalculationIsCorrect() {
        ShoppingBagPage cart = addItemAndGoToCart();
        BuyerInfoPage stepOne = cart.clickCheckout();
        stepOne.fillForm(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE);
        OrderSummaryPage stepTwo = stepOne.clickContinue();

        double itemTotal = stepTwo.getItemTotalAsDouble();
        double tax = stepTwo.getTaxAsDouble();
        double total = stepTwo.getTotalAsDouble();

        Assert.assertEquals(total, itemTotal + tax, 0.01,
                "Total should equal item total + tax");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify cancel on checkout step one returns to the cart page")
    @Test(groups = {"regression"})
    public void cancelOnStepOneReturnsToCart() {
        ShoppingBagPage cart = addItemAndGoToCart();
        BuyerInfoPage stepOne = cart.clickCheckout();
        ShoppingBagPage returned = stepOne.clickCancel();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart"),
                "Should return to cart page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify cancel on checkout step two returns to the products page")
    @Test(groups = {"regression"})
    public void cancelOnStepTwoReturnsToProducts() {
        ShoppingBagPage cart = addItemAndGoToCart();
        BuyerInfoPage stepOne = cart.clickCheckout();
        stepOne.fillForm(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE);
        OrderSummaryPage stepTwo = stepOne.clickContinue();
        InventoryPage products = stepTwo.clickCancel();

        Assert.assertEquals(products.getTitle(), TestData.PRODUCTS_TITLE,
                "Should return to Products page");
    }
}
