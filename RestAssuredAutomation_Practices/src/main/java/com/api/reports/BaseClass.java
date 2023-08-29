package com.api.reports;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.LogStatus;
import java.lang.reflect.Method;

public abstract class BaseClass {
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		ExtentTestManager.startTest(method.getName());
	}
	
	@AfterMethod
	protected void afterMethod(ITestResult result) {
		
		if(result.getStatus() == ITestResult.FAILURE ) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
		}else if(result.getStatus()== ITestResult.SKIP) {
			ExtentTestManager.getTest().log(LogStatus.SKIP, " Test skipped " + result.getThrowable());
		}else {
			ExtentTestManager.getTest().log(LogStatus.PASS, "" );
		}
		
		ExtentManager.getReporter().endTest(ExtentTestManager.getTest());
		ExtentManager.getReporter().flush();
}
	
	protected String getStackTrace(Throwable t) {
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		
		return sw.toString();
	}


}
