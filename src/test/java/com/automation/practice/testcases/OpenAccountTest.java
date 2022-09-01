package com.automation.practice.testcases;

import com.automation.practice.utilities.TestUtil;
import com.automation.practice.base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class OpenAccountTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void openAccountTest(Hashtable<String, String> data) throws InterruptedException {
        click("openAccountButton");
        System.out.println(data.get("customer"));
        select("customer", data.get("customer"));
        select("currency", data.get("currency"));
        click("process");
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        //Assert.assertTrue(alert.getText().contains(alertText));
        Thread.sleep(3000);
        alert.accept();
        Thread.sleep(3000);
    }
}
