package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
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

public class TC01_LoginTest {
    private String username = DataUtilits.getJsonData("ValidLogin", "username");
    private String password = DataUtilits.getJsonData("ValidLogin", "password");

    @BeforeMethod
    public void setUp() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "Browser");
        setUpDriver(browser);
        LogsUtilits.info("Edge Browser is opened successfully");
        getDriver().get(DataUtilits.getPropertyValue("environment", "Base_URL"));
        LogsUtilits.info("Navigated to the login page successfully");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void loginTest() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(username)
                .enterPassword(password)
                .clickLoginButton();
        Assert.assertTrue
                (new P01_LoginPage(getDriver()).assertLoginTc(
                        DataUtilits.getPropertyValue("environment", "homeURL")));
    }


    @AfterMethod
    public void quit() {
        quitDriver();

    }
}


