package Utilities;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Utility {
    private static final String SCREENSHOTS_PATH = "test-outputs/Screenshots/";

    //clickingOnElement
    public static void clickingOnElement(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).click();
    }

    // send data
    public static void sendData(WebDriver driver, By locator, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }

    //get text
    public static String getText(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    //general wait
    public static WebDriverWait waitForElement(WebDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    //sCROLL TO ELEMENT
    public static void scrollToElement(WebDriver driver, By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", findWebElement(driver, locator));
    }

    //find element
    public static WebElement findWebElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

    public static String getTimestStamp() {
        return new SimpleDateFormat("yyyy-MM-dd-H-m-ssa").format(new Date());
    }

    public static void takeScreenShot(WebDriver driver, String screenshotName) {
        try {
            // Capture screenshot using TakesScreenshot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + getTimestStamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            // Attach the screenshot to Allure
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenshotFile.getPath())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void slectFromDropdown(WebDriver driver, By locator, String optione) {
        new Select(findWebElement(driver, locator)).selectByVisibleText(optione);
    }

    public static int generateRondomNumber(int upperBound) {
        return new Random().nextInt(upperBound) + 1;
    }

    //use set
    public static Set<Integer> generateUniqeRandomNumbers(int numberNeeded, int totalNumberOfProducts) {
        Set<Integer> uniqueNumbers = new HashSet<>();
        while (uniqueNumbers.size() < numberNeeded) {
            uniqueNumbers.add(generateRondomNumber(totalNumberOfProducts));
        }
        return uniqueNumbers;
    }

    public static boolean verifyURL(WebDriver driver, String expectedURL) {
        try {
            generalWaite(driver).until(ExpectedConditions.urlToBe(expectedURL));
            LogsUtilits.info("Successfully navigated to the cart page. Current URL: " + driver.getCurrentUrl());
            return true;
        } catch (Exception e) {
            LogsUtilits.error("Failed to navigate to the cart page. Current URL: " + driver.getCurrentUrl());
            LogsUtilits.error(e.getMessage());
            return false;
        }
    }

    public static WebDriverWait generalWaite(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public static File getLatestFile(String folderPath) {

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        assert files != null;

        if (files.length == 0)
            return null;

        Arrays.sort(files,
                Comparator.comparingLong(File::lastModified).reversed());

        return files[0];
    }


}
