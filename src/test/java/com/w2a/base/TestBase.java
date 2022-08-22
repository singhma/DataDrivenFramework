package com.w2a.base;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    /*
    * Web Driver - Done
    * Properties - Done
    * Logs - log4j Jar, .log files, log4j.properties, Logger
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

    public static Logger log = Logger.getLogger(TestBase.class.getName());


    PropertyConfigurator.configure("src/test/resources/properties/log4j.properties");

    @BeforeSuite
    public void setUp() throws IOException {

        if(driver==null){
            fis = new FileInputStream("src/test/resources/properties/config.properties");
            config.load(fis);
            log.info("config file loaded");
            fis = new FileInputStream("src/test/resources/properties/OR.properties");
            OR.load(fis);
        }

        if(config.getProperty("browser").equals("chrome")){
            System.setProperty("webdriver.chrome.driver","src/test/resources/executables/chromedriver");
            driver = new ChromeDriver();
        }
        log.info("chrome launched");

        driver.get(config.getProperty("testSiteURL"));
        log.info("navigated to"+config.getProperty("testSiteURL"));


        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);


    }

    @AfterSuite
    public void tearDown(){
        if (driver !=null){
            driver.quit();
        }
        log.info("Test Execution completed");

    }
}
