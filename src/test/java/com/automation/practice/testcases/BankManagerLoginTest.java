package com.automation.practice.testcases;


import com.automation.practice.base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class BankManagerLoginTest extends TestBase {
    @Test
    public void bankManagerLoginTest() throws InterruptedException, IOException {
        //verifyEquals("abc","xyz");
        Thread.sleep(5000);
        log.info("Inside login test");
        click("bmlBtn_CSS");
        //driver.findElement(By.cssSelector(OR.getProperty("bmlBtn_CSS"))).click();
        Thread.sleep(1000);
        log.info(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))));
        Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))), "Login Not successfull");
        //Assert.fail("login not successful");

    }
}
