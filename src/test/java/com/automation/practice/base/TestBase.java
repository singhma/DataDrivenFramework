package com.automation.practice.base;
import com.automation.practice.utilities.ExcelReader;
import com.automation.practice.utilities.ExtentManager;
import com.automation.practice.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    /*
    * Web Driver - Done
    * Properties - Done
    * Logs - log4j Jar, .log files, log4j2.properties, Logger
    * ExtentReports
    * DB
    * Excel
    * Mail
    * ReportNG, ExtentReport
    * Jenkins
     */

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;

    public static Logger log = LogManager.getLogger(TestBase.class);

    public static ExcelReader excel = new ExcelReader("/Users/singhm/Desktop/taleem/DataDrivenFramework/src/test/resources/excel/testData.xlsx");

    public static WebDriverWait wait;

    public ExtentReports rep = ExtentManager.getInstance();
    public static ExtentTest test;



    @BeforeSuite
    public void setUp() throws IOException {
        ChromeOptions o= new ChromeOptions();
        o.addArguments("−−incognito");
        DesiredCapabilities c = DesiredCapabilities.chrome();
        c.setCapability(ChromeOptions.CAPABILITY, o);
        if(driver==null){
            fis = new FileInputStream("src/test/resources/properties/config.properties");
            config.load(fis);
            log.info("config file loaded");
            fis = new FileInputStream("src/test/resources/properties/OR.properties");
            OR.load(fis);
        }

        if(config.getProperty("browser").equals("chrome")){
            System.setProperty("webdriver.chrome.driver","src/test/resources/executables/chromedriver");
            driver = new ChromeDriver(o);
        }
        log.info("chrome launched");

        driver.get(config.getProperty("testSiteURL"));
        log.info("navigated to"+config.getProperty("testSiteURL"));


        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,5);


    }

    @AfterSuite
    public void tearDown(){
        if (driver !=null){
            driver.quit();
        }
        log.info("Test Execution completed");

    }


    public boolean isElementPresent(By by){
        try{
            driver.findElement(by);
            return true;
        }

        catch(NoSuchElementException e){
            return false ;
        }
    }


    public void click(String locator){
        // You can use if else to check for css, xpath to make it more generic
        driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        test.log(LogStatus.INFO, "clicking on :"+ locator );
    }

    public void type(String locator, String value){
        driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        test.log(LogStatus.INFO, "Typing in :"+ locator +" entered value as"+ value );

    }

    static WebElement dropdown;
    public void select(String locator, String value){
        dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
        test.log(LogStatus.INFO, "Selecting from Dropdown :"+ locator +" and value as"+ value );

    }

    public static void verifyEquals(String expected, String actual) throws IOException {
        try{
            Assert.assertEquals(actual,expected);
        }
        catch(Throwable t){
            //Report NG
            TestUtil.captureScreenshot();
            Reporter.log("<br>"+"Verification failure : "+ t.getMessage()+"<br");
            Reporter.log("<a target=\"_blank\" href="+TestUtil.screenShotName+"><img src="+TestUtil.screenShotName+ "height=200 width=200></img></a>");
            Reporter.log("<br>");
            // Extent Report
            test.log(LogStatus.FAIL, "Verification failed with Exception :"+ t.getMessage());
            test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenShotName));


        }
    }
}
