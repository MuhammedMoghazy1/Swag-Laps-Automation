package Pages;

import Utilities.LogsUtilits;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {
    private final WebDriver driver;
    private final By priceOfProductsLocator = By.xpath("//button[.=\"Remove\"] //preceding-sibling::div[@class=\"inventory_item_price\"]");
    private final By checkoutButtonLocator = By.id("checkout");
    float totalPrice = 0;


    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPriceOfSelectedProducts() {

        try {

            List<WebElement> priceOfSlectedProducts = driver.findElements(priceOfProductsLocator);
            for (int i = 1; i <= priceOfSlectedProducts.size(); i++) {
                By priceOfProductsLocator = By.xpath("(//button[.=\"Remove\"] //preceding-sibling::div[@class=\"inventory_item_price\"])[" + i + "]");
                String priceText = Utility.getText(driver, priceOfProductsLocator);
                totalPrice += Float.parseFloat(priceText.replace("$", ""));
            }
            LogsUtilits.info("Total price of selected products: " + totalPrice);
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtilits.error(e.getMessage());
            return "0";
        }
    }

    public boolean comparePrice(String totalPrice) {
        LogsUtilits.info("Comparing total price of selected products with total price on cart page");
        return getTotalPriceOfSelectedProducts().equals(totalPrice);
    }

    public P04_CheckoutPage clickOnCheckoutButton() {
        try {
            Utility.clickingOnElement(driver, By.id("checkout"));
            LogsUtilits.info("Clicked on checkout button");
        } catch (Exception e) {
            LogsUtilits.error(e.getMessage());
        }
        return new P04_CheckoutPage(driver);
    }
}
