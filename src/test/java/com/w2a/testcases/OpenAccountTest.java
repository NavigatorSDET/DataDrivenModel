package com.w2a.testcases;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class OpenAccountTest extends TestBase {

	@Test(dataProvider = "getData")

	public void openAccountTest(String customer, String currency) throws InterruptedException, IOException {
		
		System.out.println((TestUtil.isTestRunnable( "OpenAccountTest" , excel)));
		
		
		if (!(TestUtil.isTestRunnable( "OpenAccountTest"   , excel))) {
			
			throw new SkipException ("Skipping the test"+"openAccountTest" +" with no error");
			
		}
		
		System.out.println((TestUtil.isTestRunnable( "OpenAccountTest" , excel)));
		
		driver.findElement(By.cssSelector(OR.getProperty("openaccount"))).click();

		Select select1 = new Select(driver.findElement(By.cssSelector(OR.getProperty("customer"))));

		select1.selectByVisibleText(customer);

		Select select2 = new Select(driver.findElement(By.cssSelector(OR.getProperty("currency"))));

		select2.selectByVisibleText(currency);

		driver.findElement(By.cssSelector(OR.getProperty("process"))).click();

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		alert.accept();

	}
	
	@DataProvider

	public Object[][] getData() {

		String sheetName = "OpenAccountTest";

		int rows = excel.getRowCount(sheetName);

		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][cols];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {

			for (int colNum = 0; colNum < cols; colNum++) {

				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}

		return data;

	}

}
