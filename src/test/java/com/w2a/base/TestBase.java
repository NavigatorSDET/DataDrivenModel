package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;

public class TestBase {

	public static WebDriver driver;

	public static Properties config = new Properties();

	public static Properties OR = new Properties();

	public static FileInputStream fis;

	public static Logger log = Logger.getLogger("devpinoyLogger");

	public static ExcelReader excel = new ExcelReader(
			"C:\\Users\\y\\eclipse-workspace\\DataDrivenFramework2\\src\\test\\resources\\excel\\testdata.xlsx");

	public static WebDriverWait wait;

	public ExtentReports rep = ExtentManager.getInstance(); // lazy initialization

	public static ExtentTest test;
	
	public static String browser;

	@BeforeSuite

	public void setUp() {

		if (driver == null) {

			try {
				fis = new FileInputStream(
						"C:\\Users\\y\\eclipse-workspace\\DataDrivenFramework2\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				config.load(fis);
				log.debug("Config file is loading");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						"C:\\Users\\y\\eclipse-workspace\\DataDrivenFramework2\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				OR.load(fis);
				log.debug("OR file is loading");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if (System.getenv("browser")!=null && !System.getenv("browser").isEmpty()){
				
				browser=System.getenv("browser");
				
			}else {
				
				browser=config.getProperty("browser");
				
			}
				
			config.setProperty("browser", browser);
			
			
			if (config.getProperty("browser").equals("firefox")) {

				driver = new FirefoxDriver();

			} else if (config.getProperty("browser").equals("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						"C:\\Users\\y\\eclipse-workspace\\DataDrivenFramework2\\src\\test\\resources\\executables\\chromedriver.exe");

				driver = new ChromeDriver();

				log.debug("Chrome launched");

			}

			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to : " + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicitly.wait")),
					TimeUnit.SECONDS);

			wait = new WebDriverWait(driver, 5);

		}

	}

	public void click(String locator) {

		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();

		test.log(LogStatus.INFO, "Clicking on : " + locator);

	}

	public void type(String locator, String value) {

		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);

		test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);

	}

	static WebElement dropdown;

	public void select(String locator, String value) {

		driver.findElement(By.cssSelector(OR.getProperty(locator)));

		Select select = new Select(dropdown);

		select.selectByVisibleText(value);

		test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);

	}

	public boolean isElementPresent(By by) {
		try {
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void verifyEquals(String expected, String actual) throws IOException {

		try {

			Assert.assertEquals(actual, expected);

		} catch (Throwable t) {

			TestUtil.captureScreenshot();

			// ReportNG

			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");

			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");

			Reporter.log("<br>");

			Reporter.log("<br>");

			// Extent Reports

			test.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());

			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		}

	}



	@AfterSuite
	public void tearDown() {

		if (driver != null)

			driver.quit();

		log.debug("Test execution is completed");

	}

}
