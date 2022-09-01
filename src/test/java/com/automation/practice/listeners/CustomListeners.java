package com.automation.practice.listeners;

import com.relevantcodes.extentreports.LogStatus;
import com.automation.practice.base.TestBase;
import com.automation.practice.utilities.TestUtil;
import org.testng.*;

import java.io.IOException;

public class CustomListeners extends TestBase implements ITestListener {

    @Override
    public void onTestStart(ITestResult iTestResult) {
        test = rep.startTest(iTestResult.getName().toUpperCase());
        if(!TestUtil.isTestRunnable(iTestResult.getName(),excel)){
            throw new SkipException("Skipping the test "+iTestResult.getName().toUpperCase()+" as Run Mode is not set");
        }
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        test.log(LogStatus.PASS, iTestResult.getName().toUpperCase()+" PASS");
        rep.endTest(test);
        rep.flush();

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.setProperty("org.uncommons.reportng.escape-output","false");
        Reporter.log("Login Success");
        Reporter.log("Capturing Screenshot");
        try {
            TestUtil.captureScreenshot();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        test.log(LogStatus.FAIL, iTestResult.getName().toUpperCase()+" Failed with Exception :0"+iTestResult.getThrowable());
        test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenShotName));

        Reporter.log("Click to see Screenshot");
        Reporter.log("<a target=\"_blank\" href="+TestUtil.screenShotName+">Screenshot</a>");
        Reporter.log("<br>");
        Reporter.log("<br>");
        Reporter.log("<a target=\"_blank\" href="+TestUtil.screenShotName+"><img src="+TestUtil.screenShotName+ "height=200 width=200></img></a>");

        rep.endTest(test);
        rep.flush();

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        test.log(LogStatus.SKIP, iTestResult.getName().toUpperCase() +" Skipped the test as the runmode is NO");
        rep.endTest(test);
        rep.flush();

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
