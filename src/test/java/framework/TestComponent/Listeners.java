package framework.TestComponent;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;

import framework.resources.ExtentREporterNG;

public class Listeners extends BaseTest implements ITestListener{  //
	ExtentTest test;
	ExtentReports extent = ExtentREporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); // Thread Safe: Event if we run the test cases parallelly, each object creation has it's own Thread.As it won't interrupt the other overriding variable
	@Override  
	public void onTestStart(ITestResult result) {  
	// TODO Auto-generated method stub 
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);// Here, ThreadLocal assigns the unique thread Id() for this 'test' object
	}  
	@Override  
	public void onTestSuccess(ITestResult result) {  
	// TODO Auto-generated method stub 
		extentTest.get().log(Status.PASS, "Test Passed");
	}  
	@Override  
	public void onTestFailure(ITestResult result) {  
	// TODO Auto-generated method stub 
		extentTest.get().fail(result.getThrowable());
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}
	@Override  
	public void onFinish(ITestContext conteext) {  
	// TODO Auto-generated method stub 
		extent.flush();
	}  
}
