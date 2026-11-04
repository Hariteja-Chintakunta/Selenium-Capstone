// ABOUTME: Page object for checkout step two (order overview) on saucedemo.com.
// ABOUTME: Provides methods to read order summary, prices, tax, total, and complete the order.
package com.capstone.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutStepTwoPage extends BasePage {

    private final By itemNames = By.className("inventory_item_name");
    private final By itemTotal = By.className("summary_subtotal_label");
    private final By taxLabel = By.className("summary_tax_label");
    private final By totalLabel = By.cssSelector("[data-test='total-label']");
    private final By finishButton = By.id("finish");
    private final By cancelButton = By.id("cancel");

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getItemNames() {
        return getElements(itemNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getItemTotalText() {
        return getText(itemTotal);
    }

    public String getTaxText() {
        return getText(taxLabel);
    }

    public String getTotalText() {
        return getText(totalLabel);
    }

    public double getItemTotalAsDouble() {
        return parsePrice(getItemTotalText());
    }

    public double getTaxAsDouble() {
        return parsePrice(getTaxText());
    }

    public double getTotalAsDouble() {
        return parsePrice(getTotalText());
    }

    @Step("Finish order")
    public CheckoutCompletePage clickFinish() {
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }

    @Step("Cancel order")
    public ProductsPage clickCancel() {
        click(cancelButton);
        return new ProductsPage(driver);
    }

    private double parsePrice(String text) {
        String numericPart = text.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numericPart);
    }
}
