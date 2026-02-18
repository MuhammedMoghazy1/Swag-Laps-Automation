package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driverThreadLocal= new ThreadLocal<>();
        public static void setUpDriver(String browser){
            switch (browser.toLowerCase())
            {
                case "chrome" :
                     driverThreadLocal.set(new ChromeDriver());
                    break;

                case "firefox":
                    driverThreadLocal.set(new FirefoxDriver());
                default :
                    EdgeOptions options = new EdgeOptions();
                    options.addArguments("--start-maximized");
                    driverThreadLocal.set(new EdgeDriver(options));
            }
        }
        public static WebDriver getDriver(){
            return driverThreadLocal.get();
        }
        public static void quitDriver(){
            getDriver().quit();
            driverThreadLocal.remove();
            }
}


