package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P04_CheckoutPage {
    private final WebDriver driver;
    private final By firstNameFieldLocator = By.id("first-name");
    private final By lastNameFieldLocator = By.id("last-name");
    private final By postalCodeFieldLocator = By.id("postal-code");
    private final By continueButtonLocator = By.id("continue");

    public P04_CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public P04_CheckoutPage fillCheckoutInformation(WebDriver driver, String firstName, String lastName, String postalCode) {
        try {
            Utility.sendData(driver, firstNameFieldLocator, firstName);
            Utility.sendData(driver, lastNameFieldLocator, lastName);
            Utility.sendData(driver, postalCodeFieldLocator, postalCode);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return this;
    }

    public P05_CheckoutOverviewPage clickOnContinueButton() {
        try {
            Utility.clickingOnElement(driver, continueButtonLocator);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new P05_CheckoutOverviewPage(driver);
    }
}
