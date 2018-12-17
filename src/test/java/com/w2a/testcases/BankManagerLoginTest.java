package com.w2a.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class BankManagerLoginTest extends TestBase {

	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException{
		
		
		System.out.println((TestUtil.isTestRunnable( "BankManagerLoginTest" , excel)));
		
		if (!(TestUtil.isTestRunnable( "BankManagerLoginTest"   , excel))) {
			
			throw new SkipException ("Skipping the test"+"BankManagerLoginTest" +" with no error");
			
		}
		
		Thread.sleep(3000);
		log.debug("Inside Login Test");
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();

		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))),"Login not successful");
		
		log.debug("Login successfully executed");
		

		
		
		
	
	
	}
	
}
