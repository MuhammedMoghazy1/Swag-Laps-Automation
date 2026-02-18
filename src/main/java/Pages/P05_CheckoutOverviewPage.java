package Pages;

import Utilities.LogsUtilits;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_CheckoutOverviewPage {
    private final By finishButton = By.id("finish");
    private final By subTotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private WebDriver driver;

    public P05_CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public float getSubTotal() {
        return Float.parseFloat(Utility.getText(driver, subTotal).replace("Item total: $", ""));
    }

    public float getTax() {
        LogsUtilits.info("Displayed tax value: " + Utility.getText(driver, tax));
        return Float.parseFloat(Utility.getText(driver, tax).replace("Tax: $", ""));
    }

    public float getTotal() {
        LogsUtilits.info("Displayed total value: " + Utility.getText(driver, total));
        return Float.parseFloat(Utility.getText(driver, total).replace("Total: $", ""));
    }

    public String calculateTotal() {
        LogsUtilits.info("Calculated total value: " + (getSubTotal() + getTax()));
        return String.valueOf(getSubTotal() + getTax());
    }

    public boolean compareCalculatedTotalWithDisplayedTotal() {
        LogsUtilits.info("Comparing calculated total with displayed total");
        return calculateTotal().equals(String.valueOf(getTotal()));
    }

    public P06_CheckoutComplete ClickOnFinighButton() {
        Utility.clickingOnElement(driver, finishButton);
        return new P06_CheckoutComplete(driver);
    }
}
