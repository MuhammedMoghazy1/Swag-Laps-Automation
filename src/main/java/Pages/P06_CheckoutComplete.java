package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.Utility.findWebElement;

public class P06_CheckoutComplete {
    private final WebDriver driver;
    private final By BackHomeButtonLocator = By.id("back-to-products");
    private final By thanksMessageLocator = By.tagName("h2");

    public P06_CheckoutComplete(WebDriver driver) {
        this.driver = driver;
    }

    public P01_LoginPage clickOnBackHomeButton() {
        try {
            driver.findElement(BackHomeButtonLocator).click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new P01_LoginPage(driver);
    }

    public boolean isThanksMessageDisplayed() {
        try {
            return findWebElement(driver, thanksMessageLocator).isDisplayed();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
