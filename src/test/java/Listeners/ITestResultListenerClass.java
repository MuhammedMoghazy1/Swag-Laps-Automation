package Listeners;

import Utilities.LogsUtilits;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestResultListenerClass implements ITestListener {
    public void onTestStart(ITestResult result) {
        LogsUtilits.info("TestCase"+result.getName()+" started");
    }
    public void onTestSuccess(ITestResult result) {
        System.out.println("TestCase" + result.getName()+" passed");
    }
    public void onTestSkipped(ITestResult result) {
        System.out.println("TestCase" + result.getName()+" skipped");
    }
}
