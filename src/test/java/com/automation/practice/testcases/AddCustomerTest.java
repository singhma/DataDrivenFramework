package com.automation.practice.testcases;

import com.automation.practice.utilities.TestUtil;
import com.automation.practice.base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class AddCustomerTest extends TestBase {

    @Test(dataProviderClass= TestUtil.class, dataProvider = "dp")
    public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {
        if(!data.get("runmode").equalsIgnoreCase("y")){
            throw new SkipException("Skipping test case as runmode for data set up is set to n");
        }
        click("addCustBtn");
        type("firstname",data.get("firstName"));
        type("lastname",data.get("lastName"));
        type("postcode",data.get("postCode"));
        click("addBtn");
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        //Assert.assertTrue(alert.getText().contains(alertText));
        Thread.sleep(3000);
        alert.accept();
        Thread.sleep(3000);

    }

    @DataProvider
    public Object[][] getData(){
        String sheetName = "AddCustomerTest";
        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        Object[][] data = new Object[rows-1][cols];

        for (int rowNum = 2; rowNum <=rows ; rowNum++) {
            for (int colNum= 0; colNum <cols ; colNum++) {
                data[rowNum-2][colNum]=excel.getCellData(sheetName,colNum,rowNum);
            }

        }
        return data;
    }
}
