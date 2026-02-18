package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilities.DataUtilits;
import Utilities.LogsUtilits;
import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtilits.getPropertyValue;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})

public class TC02_LandingTest {
    private String username = DataUtilits.getJsonData("ValidLogin", "username");
    private String password = DataUtilits.getJsonData("ValidLogin", "password");

    @BeforeMethod
    public void setUp() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "Browser");
        setUpDriver(browser);
        LogsUtilits.info("Edge Browser is opened successfully");
        getDriver().get(getPropertyValue("environment", "Base_URL"));
        LogsUtilits.info("Navigated to the login page successfully");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void checkingNumberOfSelectedProducts() {
        new P01_LoginPage(getDriver())
                .enterUsername(username)
                .enterPassword(password)
                .clickLoginButton()
                .addAllProductsToCart();
        Assert.assertTrue(new P02_LandingPage(getDriver()).
                compareNumberOfProductsOnCartIconWithNumberOfSelectedProducts());

    }

    @Test
    public void addRandomProductsToCart() {
        new P01_LoginPage(getDriver())
                .enterUsername(username)
                .enterPassword(password)
                .clickLoginButton()
                .addRandomProductToCart(3, 6);
        Assert.assertTrue(new P02_LandingPage(getDriver()).
                compareNumberOfProductsOnCartIconWithNumberOfSelectedProducts());
    }

    @Test
    public void clickOnCartIcon() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(username)
                .enterPassword(password)
                .clickLoginButton()
                .addRandomProductToCart(3, 6)
                .clickOnCartIcon();

        Assert.assertTrue(new P02_LandingPage(getDriver()).
                verifyCartPageURL(getPropertyValue("environment", "CART_URL")));
    }

    @AfterMethod
    public void quit() {
        quitDriver();

    }
}
