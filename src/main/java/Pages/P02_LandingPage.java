package Pages;

import Utilities.LogsUtilits;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

import static Utilities.Utility.waitForElement;

public class P02_LandingPage {
    private static List<WebElement> allProducts;
    private static List<WebElement> selectedProducts;
    private final WebDriver driver;
    private final By addToCartButtonForAllProducts = By.xpath("//button[@class]");
    private final By numberOfProductsOnCartIcon = By.className("shopping_cart_badge");
    private final By numberOfSlectedProducts = By.xpath("//button[.='Remove']");
    private final By cartIcon = By.className("shopping_cart_container");
    private final By priceOfProductsLocator = By.xpath("//button[.=\"Remove\"] //preceding-sibling::div[@class=\"inventory_item_price\"]");
    float totalPrice = 0;

    public P02_LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    public P02_LandingPage addAllProductsToCart() {
        allProducts = driver.findElements(addToCartButtonForAllProducts);
        LogsUtilits.info("Number of products on landing page: " + allProducts.size());
        for (int i = 1; i <= allProducts.size(); i++) {
            By addToCartButtonForAllProducts =
                    By.xpath("(//button[@class])[" + i + "]");//dynamic locator
            Utility.clickingOnElement(driver, addToCartButtonForAllProducts);
        }
        return this;
    }

    public String getNumberOfProductsOnCartIcon() {
        try {
            LogsUtilits.info("Number of products on cart icon: "
                    + Utility.getText(driver, numberOfProductsOnCartIcon));
            Utility.getText(driver, numberOfProductsOnCartIcon);
        } catch (Exception e) {
            LogsUtilits.error(e.getMessage());
        }
        return "0";
    }

    public String getNumberOfSelectedProducts() {
        try {
            LogsUtilits.info("Number of selected products: " + selectedProducts.size());
            selectedProducts = driver.findElements(numberOfSlectedProducts);
            return String.valueOf(selectedProducts.size());
        } catch (Exception e) {
            LogsUtilits.error(e.getMessage());
        }
        return "0";
    }

    public P02_LandingPage addRandomProductToCart(int numberNeeded, int totalNumberOfProducts) {
        Set<Integer> rondomNumbers = Utility.generateUniqeRandomNumbers(numberNeeded, totalNumberOfProducts);
        for (int random : rondomNumbers) {
            By addToCartButtonForAllProducts =
                    By.xpath("(//button[@class])[" + random + "]");//dynamic locator
            Utility.clickingOnElement(driver, addToCartButtonForAllProducts);
        }
        return this;
    }

    public P03_CartPage clickOnCartIcon() {
        Utility.clickingOnElement(driver, cartIcon);
        return new P03_CartPage(driver);
    }

    public boolean verifyCartPageURL(String expectedURL) {
        try {
            waitForElement(driver, cartIcon).until(driver -> driver.getCurrentUrl().equals(expectedURL));
            LogsUtilits.info("Successfully navigated to the cart page. Current URL: " + driver.getCurrentUrl());
            return true;
        } catch (Exception e) {
            LogsUtilits.error("Failed to navigate to the cart page. Current URL: " + driver.getCurrentUrl());
            LogsUtilits.error(e.getMessage());
            return false;
        }
    }

    public boolean compareNumberOfProductsOnCartIconWithNumberOfSelectedProducts() {
        return getNumberOfProductsOnCartIcon().equals(getNumberOfSelectedProducts());
    }

    public String getTotalPriceOfSelectedProducts() {

        try {
            List<WebElement> priceOfSlectedProducts = driver.findElements(priceOfProductsLocator);
            for (int i = 1; i <= priceOfSlectedProducts.size(); i++) {
                By elements = By.xpath("(//button[.=\"Remove\"] //preceding-sibling::div[@class=\"inventory_item_price\"])[" + i + "]");
                String priceText = Utility.getText(driver, elements);
                totalPrice += Float.parseFloat(priceText.replace("$", ""));
            }
            LogsUtilits.info("Total price of selected products: " + totalPrice);
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtilits.error(e.getMessage());
            return "0";
        }
    }

}
