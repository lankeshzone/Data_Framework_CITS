package Utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ReportUtility {
	
	
	 public ExtentHtmlReporter htmlReporter;
	 public ExtentReports extent;
	 public ExtentTest logger;
	 
	 
	 public void startReport() 
	 {
		 htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/CITS_Report.html");
		        	
		 // Create an object of Extent Reports
		 extent = new ExtentReports();  
		 extent.attachReporter(htmlReporter);
		 extent.setSystemInfo("Host Name", "Century IT Services");
		        	extent.setSystemInfo("Environment", "Testing");
		 extent.setSystemInfo("User Name", "Beena Lankesh");
		 htmlReporter.config().setDocumentTitle("Automation Demo"); 
		                // Name of the report
		 htmlReporter.config().setReportName("Automation Demo - Registration Module"); 
		                // Dark Theme
		 htmlReporter.config().setTheme(Theme.STANDARD); 
	 }

	 
	 public void endReport() 
	 {
		 extent.flush();
	 }
}
