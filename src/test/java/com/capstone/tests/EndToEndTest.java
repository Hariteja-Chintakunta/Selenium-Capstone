// ABOUTME: End-to-end tests covering full purchase flows on saucedemo.com.
// ABOUTME: Validates complete user journeys from login through order confirmation.
package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.*;
import com.capstone.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EndToEndTest extends BaseTest {

    @Test(groups = {"smoke", "regression"})
    public void fullPurchaseFlowSingleItem() {
        ProductsPage products = new LoginPage(driver)
                .login(TestData.VALID_USER, TestData.VALID_PASSWORD);
        String firstProduct = products.getProductNames().get(0);
        products.addToCart(firstProduct);

        CartPage cart = products.goToCart();
        Assert.assertEquals(cart.getCartItemCount(), 1, "Cart should have 1 item");

        CheckoutStepOnePage stepOne = cart.clickCheckout();
        stepOne.fillForm(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE);
        CheckoutStepTwoPage stepTwo = stepOne.clickContinue();
        CheckoutCompletePage complete = stepTwo.clickFinish();

        Assert.assertEquals(complete.getConfirmationHeader(), TestData.ORDER_CONFIRMATION,
                "Should show order confirmation");
    }

    @Test(groups = {"regression"})
    public void fullPurchaseFlowMultipleItems() {
        ProductsPage products = new LoginPage(driver)
                .login(TestData.VALID_USER, TestData.VALID_PASSWORD);
        List<String> names = products.getProductNames();
        products.addToCart(names.get(0));
        products.addToCart(names.get(1));
        products.addToCart(names.get(2));

        CartPage cart = products.goToCart();
        Assert.assertEquals(cart.getCartItemCount(), 3, "Cart should have 3 items");

        CheckoutStepOnePage stepOne = cart.clickCheckout();
        stepOne.fillForm(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE);
        CheckoutStepTwoPage stepTwo = stepOne.clickContinue();

        List<String> summaryItems = stepTwo.getItemNames();
        Assert.assertEquals(summaryItems.size(), 3, "Summary should show 3 items");

        CheckoutCompletePage complete = stepTwo.clickFinish();
        Assert.assertEquals(complete.getConfirmationHeader(), TestData.ORDER_CONFIRMATION,
                "Should show order confirmation");
    }

    @Test(groups = {"regression"})
    public void addRemoveThenCheckout() {
        ProductsPage products = new LoginPage(driver)
                .login(TestData.VALID_USER, TestData.VALID_PASSWORD);
        List<String> names = products.getProductNames();
        products.addToCart(names.get(0));
        products.addToCart(names.get(1));
        products.removeFromCart(names.get(0));

        Assert.assertEquals(products.getCartBadgeCount(), 1, "Badge should show 1 after remove");

        CartPage cart = products.goToCart();
        Assert.assertEquals(cart.getCartItemCount(), 1, "Cart should have 1 item");
        Assert.assertTrue(cart.getItemNames().contains(names.get(1)),
                "Cart should contain the remaining item");

        CheckoutStepOnePage stepOne = cart.clickCheckout();
        stepOne.fillForm(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE);
        CheckoutStepTwoPage stepTwo = stepOne.clickContinue();
        CheckoutCompletePage complete = stepTwo.clickFinish();

        Assert.assertEquals(complete.getConfirmationHeader(), TestData.ORDER_CONFIRMATION,
                "Should show order confirmation");
    }

    @Test(groups = {"regression"})
    public void checkoutAndReturnToShop() {
        ProductsPage products = new LoginPage(driver)
                .login(TestData.VALID_USER, TestData.VALID_PASSWORD);
        products.addToCart(products.getProductNames().get(0));

        CartPage cart = products.goToCart();
        CheckoutStepOnePage stepOne = cart.clickCheckout();
        stepOne.fillForm(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE);
        CheckoutStepTwoPage stepTwo = stepOne.clickContinue();
        CheckoutCompletePage complete = stepTwo.clickFinish();
        ProductsPage returned = complete.clickBackHome();

        Assert.assertEquals(returned.getTitle(), TestData.PRODUCTS_TITLE,
                "Should return to Products page after completing order");
    }
}
