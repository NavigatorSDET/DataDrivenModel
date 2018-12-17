package com.w2a.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.IAlterSuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;
import com.w2a.utilities.MonitoringMail;
import com.w2a.utilities.TestConfig;
import com.w2a.utilities.TestUtil;

public class CustomListeners extends TestBase implements ITestListener, IAlterSuiteListener {
	
	String messageBody;
	

	public void onTestStart(ITestResult context) {
		

		test= rep.startTest(context.getName().toUpperCase());
		
		
	}
	
	public void onTestSuccess(ITestResult result) {
		
		test.log(LogStatus.PASS, result.getName().toUpperCase()+" PASS");
		
		rep.endTest(test);
		
		rep.flush();
		
	}

	public void onTestFailure(ITestResult result) {
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			}

		
		test.log(LogStatus.FAIL, result.getName().toUpperCase()+" Failed with exception : "+result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
		rep.endTest(test);
		rep.flush();
		
	}

	public void onTestSkipped(ITestResult result) { 

		test.log(LogStatus.SKIP, result.getName().toUpperCase()+" Skipped"+result.getName()+ "with run mode NO");
		rep.endTest(test);
		rep.flush();
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {


	
		
	}

	public void onFinish(ITestContext context) {
		MonitoringMail mail= new MonitoringMail();

		
		try {
			messageBody = "http://"+InetAddress.getLocalHost().getHostAddress()+  ":8080/job/DataDrivenLiveProject/Extent_20Reports/";
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(messageBody);
		
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject ,messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void alter(List<XmlSuite> suites) {
		// TODO Auto-generated method stub
		
	}

}
