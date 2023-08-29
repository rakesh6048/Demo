package main;

import static utils.Constants.config;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import utils.Constants;
import utils.Library;


@CucumberOptions(strict = true, monochrome = true, features = "src/test/java/features", glue = "stepdefinition", 
format = { "pretty", "json:target/cucumber.json" }, tags = { "@Login_to_GitHub" })
public class TestRunner extends AbstractTestNGCucumberTests {
	@BeforeSuite(alwaysRun =true)
	public void setUp() throws Exception {
		//Library.generateCucumberReport();
		//Library.formatReport();

		Constants.LoadConfigProperty();
		Constants.LoadLocatorProperty();
		Constants.LoadTestDataProperty();
		//Constants.LoadBSConfigProperty();
		Library.createTestRunIDFolder();

		Constants.configureDriverPath();
		Library.openBroser();

		Library.implicitWait(20);
		Library.deleteAllCookies();

		/*if(config.getProperty("browserstackexecution").equals("Y") || config.getProperty("browserstackexecution").contentEquals("y")) {
			//Library.
		} else {
			Constants.configureDriverPath();
			Library.openBroser();
		}*/

	}

	@BeforeTest
	public void BeforeTest() throws Exception {

	}
	@BeforeClass(alwaysRun = true)
	public void BeforeClass()throws Exception{

	}
	@BeforeMethod(alwaysRun = true)
	public void BeforeMethod() throws Exception {

	}
	@Test
	public void first() {

	}
	@AfterMethod()
	public void tearDownr(ITestResult result) throws IOException, InterruptedException, AWTException{
		//Library.getscreenshot();
	}
	@AfterClass(alwaysRun = true)
	public void takeScreenshot() throws IOException {

	}
	@AfterTest()
	public void AfterTest() throws IOException {

	}
	@AfterSuite(alwaysRun = true)
	public void generateHTMLReports() throws IOException {
		Library.generateCucumberReport();
		Library.formatReport();
		//Library.sendEmail("Execution Complete", "Execution complete");
		Library.moveReportsAndScreenshots();
		//Library.sendEmail_image("Automated Execution Report", "");
		Library.closeDriver();
	}
}
