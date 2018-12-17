package com.w2a.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;


// new changes

public class AddCustomerTest extends TestBase {

	@Test(dataProvider = "getData")

	public void addCustomerTest(Hashtable <String, String> data) throws InterruptedException, IOException {

		if (!data.get("runMode").equals("Y")) {
			
			throw new SkipException("Run data set is NO");
			
		}
			
		if (!(TestUtil.isTestRunnable( "AddCustomerTest"   , excel))) {
			
			throw new SkipException ("Skipping the test"+"AddCustomerTest" +" with no error");
			//4654354
			
			//,mjnb;fodjgn;dojf'nb
		}		

		driver.findElement(By.cssSelector(OR.getProperty("addCustBtn"))).click();

		driver.findElement(By.cssSelector(OR.getProperty("firstname"))).sendKeys(data.get("firstName"));

		driver.findElement(By.cssSelector(OR.getProperty("lastname"))).sendKeys(data.get("lastName"));

		driver.findElement(By.cssSelector(OR.getProperty("postcode"))).sendKeys(data.get("postCode"));

		driver.findElement(By.cssSelector(OR.getProperty("addbtn"))).click();

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		Assert.assertTrue(alert.getText().contains(data.get("alertText")));

		alert.accept();
		
		// ugvugvuy

	}

	@DataProvider

	public Object[][] getData() {

		String sheetName = "AddCustomerTest";

		int rows = excel.getRowCount(sheetName);
		
		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];
		
		Hashtable<String,String> table = null;

		for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2

			table = new Hashtable<String,String>();
			
			for (int colNum = 0; colNum < cols; colNum++) {

				// data[0][0]
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}

		}

		return data;

	}

}
