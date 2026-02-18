package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.*;
import Utilities.DataUtilits;
import Utilities.LogsUtilits;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtilits.getPropertyValue;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC06_finshingOrderTest {
    private final String baseURL =
            getPropertyValue("environment", "Base_URL");

    private final String checkoutOverviewURL =
            getPropertyValue("environment", "overviewURL");

    private final String username =
            DataUtilits.getJsonData("ValidLogin", "username");

    private final String password =
            DataUtilits.getJsonData("ValidLogin", "password");

    private final String firstName =
            DataUtilits.getJsonData("Information", "fname")
                    + "-" + Utility.getTimestStamp();

    private final String lastName =
            DataUtilits.getJsonData("Information", "lname")
                    + "-" + Utility.getTimestStamp();

    private final String ZIPCode =
            new Faker().number().digits(5);

    public TC06_finshingOrderTest() throws IOException {
    }

    @BeforeMethod
    public void setUp() throws IOException {

        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "Browser");
        setUpDriver(browser);
        LogsUtilits.info("Browser opened successfully");

        getDriver().get(baseURL);
        LogsUtilits.info("Navigated to Login Page successfully");

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void finshOrderTC() {

        new P01_LoginPage(getDriver())
                .enterUsername(username)
                .enterPassword(password)
                .clickLoginButton();
        new P02_LandingPage(getDriver())
                .addAllProductsToCart()
                .clickOnCartIcon();
        new P03_CartPage(getDriver())
                .clickOnCheckoutButton();
        new P04_CheckoutPage(getDriver())
                .fillCheckoutInformation(getDriver(), firstName, lastName, ZIPCode)
                .clickOnContinueButton();
        P05_CheckoutOverviewPage overviewPage =
                new P05_CheckoutOverviewPage(getDriver());
        overviewPage.compareCalculatedTotalWithDisplayedTotal();
        overviewPage.ClickOnFinighButton();
        Assert.assertTrue(
                new P06_CheckoutComplete(getDriver()).isThanksMessageDisplayed(),
                "Thank You message is not displayed after finishing order");
    }


    @AfterMethod
    public void tearDown() {
        quitDriver();
        LogsUtilits.info("Browser closed successfully");
    }
}
