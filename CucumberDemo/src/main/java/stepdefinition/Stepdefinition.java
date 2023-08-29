

package stepdefinition;

import static utils.Constants.config;
import static utils.Constants.driver;
import static utils.Constants.locatorprop;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import framework.BusinessFunctions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import junit.textui.TestRunner;
import utils.Library;
@SuppressWarnings("unused")
public class Stepdefinition extends TestRunner {
	
	static String sAddressBookName;
	static String phoneNumber;
	WebElement ele, ele1, elt;
	Boolean doesObjectExist;
	Actions action;
	String locatorType;
	String locatorValue;
	List<String> ExactMatchFromUI = new ArrayList<String>();
	List<String> ExactMatchFromDB = new ArrayList<String>();
	List<String> ApproveMatchFromUI = new ArrayList<String>();
	String InvoiceID;
	private static int NoOfUPCForOrgill_UI, NoOfUPCForAce_UI, NoOfUPCForAce_DB, NumberOfSuggInDB;
	String invoice=null;
	
	@Given("^I am on \"(.*?)\" login page$")
     public void launchApplication(String field) throws Throwable {
    	 Library.launchApplication(field);
     }
	@Given("^I am on \"(.*?)\" page$")
    public void onPage(String field) throws Throwable {
   	 Boolean status;
   	 if(field.equals("login") || field.equals("GitLogin")) {
   		 String baseUrl = null;
   		 if(config.getProperty("env").equals("test")) {
   			 Library.launchApplication("testurl");
   		 }else if(config.getProperty("env").equals("dev")) {
	   			 Library.launchApplication("devurl");
	   			status = true;
   		 }
   	   } else {
   		   String sExpHeaderValue = field;
   		   field = "pageheader";
   		   ele = Library.getWebElement(field, 20);
   		   Library.highLightElement(driver, ele);
   		   Assert.assertEquals(ele.getText().trim(), sExpHeaderValue.trim(), sExpHeaderValue + " page not displayed ");
   		   Library.removeHighLightElement(driver, ele);
   	   }
    }
	@And("^I type \"(.*?)\"$")
	public void type(String field) throws Throwable{
		try {
			Library.enterValue(field, Integer.valueOf(config.getProperty("wait_getelement")));
		}catch (Exception e) {
			Library.enterValue(field, 20);
		}
	}
	@And("^I enter \"(.*?)\" for \"(.*?)\" field$")
	public void i_enter_for_fields(String field1, String field2) throws Throwable{
		
	        BusinessFunctions.checkUILoader();
	        Thread.sleep(4000);
			Library.enterValue(field2, Library.getData(field1), 20);
	}
	@Then("^I should see \"(.*?)\" field$")
	public void verifyFieldsDisplayed(String field) throws Throwable{
		if(field.contains(",")) {
			String[] fieldArray = field.split(",");
			for(int i=0;i<fieldArray.length;i++) {
				field = fieldArray[i].trim();
				ele = Library.getWebElement(field, 30);
				Library.highLightElement(driver, ele);
				Thread.sleep(1000);
				Library.removeHighLightElement(driver, ele);
			}
		} else{
			ele = Library.getWebElement(field, 30);
			Library.highLightElement(driver, ele);
			Library.removeHighLightElement(driver, ele);
		}
	}
	@Then("^I should see \"(.*?)\" page$")
	public void verifyPageDisplayedByURL(String field) throws Throwable{
		String AppInUrl = driver.getCurrentUrl();
		String ExpURL = Library.getData(field);
		Assert.assertEquals(AppInUrl.trim(), ExpURL.trim(), field + " page not displayed");
	}
	@Then("^\"(.*?)\" page should be displayed$")
	public void verifyPageDisplayedByHeader(String field) throws Throwable{
		String sExpHeaderValue = field;
		field = "pageheader";
		ele = Library.getWebElement(field, Integer.valueOf(config.getProperty("wait_getelement")));
		Assert.assertEquals(ele.getText().trim(), sExpHeaderValue.trim(), sExpHeaderValue + " page not displayed");
	}
	@When("^I wait for \"(.*?)\" seconds$")
	public void waitStep(int seconds) throws Throwable{
	     BusinessFunctions.checkUILoader();
	     int waittime = seconds * 1000;
	     Thread.sleep(waittime);
	     BusinessFunctions.checkUILoader();
	}
	@When("^I click \"(.*?)\"$")
	public void clickOn(String field) throws Throwable{
		if(field.equals("login_button")) {
			Library.clickelement(field, Integer.valueOf(config.getProperty("wait_getelement")));
			String AppInUrl = null;
			AppInUrl = driver.getCurrentUrl();
			Integer docount = 0;
			do {
				AppInUrl = driver.getCurrentUrl();
				docount = docount + 1;
				Thread.sleep(600);
				if(docount == 5) {
					this.relogin();
					break;
				}
			}while(AppInUrl.trim().equals("https://github.com/login"));
		}else if(field.equals("Git_login_button")) {
			Library.clickelement(field, Integer.valueOf(config.getProperty("wait_getelement")));
			BusinessFunctions.checkUILoader();
		} else {
			BusinessFunctions.checkUILoader();
			Library.clickelement(field, Integer.valueOf(config.getProperty("wait_getelement")));
			BusinessFunctions.checkUILoader();
		}
	}
	private void relogin() {
		// TODO Auto-generated method stub
		
	}
	
	
}
