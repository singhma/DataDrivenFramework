package com.w2a.testcases;

import com.w2a.base.TestBase;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {
    @Test
    public void loginAsBankManager() throws InterruptedException {
        log.info("Inside login test");
        driver.findElement(By.cssSelector(OR.getProperty("bmlBtn_CSS"))).click();
        Thread.sleep(1000);
        log.info("login successfully executed");

    }
}
