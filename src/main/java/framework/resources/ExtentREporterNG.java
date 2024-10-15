package framework.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentREporterNG {
	public static ExtentReports getReportObject() {
		String path = System.getProperty("user.dir") + "\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path); //This is responsible for providing the reports. We have to provide the path for that. as it expects the path.
		reporter.config().setReportName("Web Automation Results"); // We can set the report name with this
		reporter.config().setDocumentTitle("Test Results");
		
		ExtentReports extent = new ExtentReports(); //This Extent class report helps to drive all reporting execution.
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Gran");
		return extent;
	}
}
