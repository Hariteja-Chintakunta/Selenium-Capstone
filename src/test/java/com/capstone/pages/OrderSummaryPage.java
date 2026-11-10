// ABOUTME: Page object for checkout step two (order overview) on saucedemo.com.
// ABOUTME: Provides methods to read order summary, prices, tax, total, and complete the order.
package com.capstone.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class OrderSummaryPage extends SwagBasePage {

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNameElements;

    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotal;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(css = "[data-test='total-label']")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    public OrderSummaryPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getItemNames() {
        return waits.waitForPresence(itemNameElements).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public double getItemTotalAsDouble() {
        return parsePrice(getText(itemTotal));
    }

    public double getTaxAsDouble() {
        return parsePrice(getText(taxLabel));
    }

    public double getTotalAsDouble() {
        return parsePrice(getText(totalLabel));
    }

    @Step("Finish order")
    public OrderConfirmationPage clickFinish() {
        click(finishButton);
        return new OrderConfirmationPage(driver);
    }

    @Step("Cancel order")
    public InventoryPage clickCancel() {
        click(cancelButton);
        return new InventoryPage(driver);
    }

    private double parsePrice(String text) {
        String numericPart = text.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numericPart);
    }
}
