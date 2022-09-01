package com.automation.practice.utilities;

import com.automation.practice.base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

public class TestUtil extends TestBase {
    public static String screenShotPath;
    public static String screenShotName;

    public static void captureScreenshot() throws IOException {
        Date date = new Date();
        screenShotName = date.toString().replace(":", "_").replace(" ", "_") + ".jpg";
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(System.getProperty("user.dir") + "/target/surefire-reports/html/" + screenShotName));
    }


    @DataProvider(name = "dp")
    public Object[][] getData(Method m) {
        String sheetName = m.getName();
        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        //Object[][] data = new Object[rows - 1][cols];
        Object[][] data = new Object[rows - 1][1];

        Hashtable<String, String> table = null;

        for (int rowNum = 2; rowNum <= rows; rowNum++) {
            table = new Hashtable<String, String>();
            for (int colNum = 0; colNum < cols; colNum++) {
                table.put(excel.getCellData(sheetName,colNum,1),excel.getCellData(sheetName,colNum,rowNum));
                data[rowNum - 2][0] = table;
            }

        }
        return data;
    }


    public static boolean isTestRunnable(String testName, ExcelReader excel) {
        String sheetName = "test_suite";
        int rows = excel.getRowCount(sheetName);
        for (int i = 2; i <= rows; i++) {
            String testCase = excel.getCellData(sheetName, "TCID", i);
            if (testCase.equalsIgnoreCase(testName)) {
                String runMode = excel.getCellData(sheetName, "Runmode", i);

                if (runMode.equalsIgnoreCase("y")) {
                    return true;
                } else return false;
            }

        }
        return  false;
    }
}
