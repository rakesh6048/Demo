package utils;
import com.google.common.io.Files;

import cucumber.api.Scenario;

import static utils.Constants.config;
import static utils.Constants.driver;
import static utils.Constants.locatorprop;
import static utils.Constants.testdataprop;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.browserstack.local.Local;

import org.testng.Assert;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;


public class Library {
	public static String failureScreenshotName = null;	
	static WebElement ele;
	Boolean doesobjectExist;
	static String newrptfoldername;
	//static Local bslocal;
	/**
	 * Highlight element under test
	 */
	public static void highLightElement(final WebDriver driver, final WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; color: blue; border: 2px solid red;');", element);
	}
	/**
	 * Remove Highlight element under test
	 */
	public static void removeHighLightElement(final WebDriver driver, final WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
	}
	/**
	 * @param IOExcetion, InteruptedException, AWTExcetion
	 */
	public static void getscreenshot() throws IOException, InterruptedException, AWTException {
		//Thraed.sleep(100);
		if(config.getProperty("capturescreenshot").equals("Y") || config.getProperty("capturescreenshot").equals("Y")) {
			final String failureImageFileName = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss-SS").format(new GregorianCalendar().getTime()) + ".png";
			final File failureImageFile = new File(System.getProperty("user.dir") + "//screenshots//" + failureImageFileName);
			failureScreenshotName = System.getProperty("user.dir") + "\\screenshot\\" + failureImageFileName;
			//E:\workspace3\NewCucumber//screenshot//10-13-2022_22-54-52-276.png
			//failureScreenshotName = failureScreenshotName.replaceAll("\\", "//");

			if(config.getProperty("browserType").equals("chrome") || config.getProperty("browserType").equals("firefox")) {
				final File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

				failureImageFile.getParentFile().mkdir();
				failureImageFile.createNewFile();
				Files.copy(imageFile, failureImageFile);
			} else if(config.getProperty("browserType").equals("ie")) {
				Calendar now = Calendar.getInstance();
				Robot robot =new Robot();
				BufferedImage screenshot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
				ImageIO.write(screenshot, "JPG", failureImageFile);
			}
		}
	}
	public static String getscreenshotstarts() throws IOException, InterruptedException {
		final File screenshotsource = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		final String imageFileName = new SimpleDateFormat("MM-dd-yyyy_HM-mm-ss-SS").format(new GregorianCalendar().getTime()) + ".png";
		String reportstatsfilename = "ReportStats-" + imageFileName;
		final File imageFile = new File(System.getProperty("user.dir") + "//screenshots//" + reportstatsfilename);
		imageFile.getParentFile().mkdir();
		imageFile.createNewFile();
		Files.copy(screenshotsource, imageFile);
		return reportstatsfilename;

	}
	/**
	 * @Currentdate
	 */
	public static String currentDateTime() {
		final DateFormat dateFormate =new SimpleDateFormat("yyyy/MM/dd HH: mm: ss");
		final Calendar cal = Calendar.getInstance();
		final String call = dateFormate.format(cal.getTime());
		return call;
	}
	/**
	 * @Wait for page Load
	 */
	public static void waitForPageLoaded() {

		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		}catch(Throwable error) {
			Assert.fail("Timeout waiting for Page Load to complete. ");
		}
	}
	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	public static void maximizeWindow() {
		driver.manage().window().maximize();
	}

	public static void implicitWait(final int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public static void explicitWait(final WebElement element) {
		WebDriverWait wait =new WebDriverWait(driver, 3000);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void pageLoad(final int time) {
		driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
	}
	public static void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	/**
	 * @Launch URL
	 */
	public static void launchApplication() throws Exception{
		String baseUrl = null;
		if(config.getProperty("env").equals("test")) {
			baseUrl = config.getProperty("testurl");
		}else if(config.getProperty("env").equals("dev")) {
			baseUrl = config.getProperty("devurl");
		}
		driver.get(baseUrl);
		Library.waitForPageLoaded();
		Library.getscreenshot();
	}

	public static void launchApplication(String urlkey) throws Exception {
		String baseUrl =config.getProperty(urlkey);
		//driver.close();
		driver.get(baseUrl);

		Library.waitForPageLoaded();
		Library.getscreenshot();
	}

	public static void openBroser() throws Exception {

		if(config.getProperty("browserType").equals("chrome")) {
			driver = new ChromeDriver();
			/*ChromeOptions options = new ChromeOptions();
			options.addArguments("--haedless");
			options.addArguments("--disable-gpu");
			options.addArguments("--incognito");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.setExperimentalOption("--useAutomationExtension", false);

			driver = new ChromeDriver(options);*/
			//Thread.sleep(20000);
			/*for(int i=0; i<6;i++) {
				driver.findElement(By.tagName("html")).sendKeys(Keys.CONTROL, Keys.SUBTRACT);
			}*/
		} else if(config.getProperty("broserType").equals("firefox")) {
			driver = new FirefoxDriver();
		}else if(config.getProperty("browserType").equals("ie")) {
			InternetExplorerOptions options = new InternetExplorerOptions();
			driver = new InternetExplorerDriver(options);
		}
		Library.maximizeWindow();
	}

	public static void closeDriver() {
		try {
			if(config.getProperty("browserstackexecution").equals("Y")) {

			}
			driver.quit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Locator
	 */
	public static By getLocator(String ElementName) throws Exception {
		String locator = locatorprop.getProperty(ElementName);
		String locatorType = locator.split("~")[0];
		String locaturValue = locator.split("~")[1];
		if(locatorType.toLowerCase().equals("id")) 
			return By.id(locaturValue);
		else if(locatorType.toLowerCase().equals("name")) 
			return By.name(locaturValue);
		else if((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return By.className(locaturValue);
		else if((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			return By.tagName(locaturValue);
		else if((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
			return By.linkText(locaturValue);
		else if((locatorType.toLowerCase().equals("partiallinktext")) || (locatorType.toLowerCase().equals("link")))
			return By.partialLinkText(locaturValue);
		else if((locatorType.toLowerCase().equals("cssseector")) || (locatorType.toLowerCase().equals("css")))
			return By.cssSelector(locaturValue);
		else if(locatorType.toLowerCase().equals("xpath")) 
			return By.xpath(locaturValue);
		else
			throw new Exception("Locator type :" + locatorType + " not defined!!");
	}

	public static String getData(String ElementName) throws Exception{
		//Read value using the logical name as key
		String data = testdataprop.getProperty(ElementName);
		return data;
	}

	public static String getLocatorData(String ElementName) throws Exception{
		//Read value using the logical name as key
		String locatorData = locatorprop.getProperty(ElementName);
		return locatorData;
	}
	public static String getConfigData(String ElementName) throws Exception{
		//Read value using the logical name as key
		String configData = config.getProperty(ElementName);
		return configData;
	}

	public static void generateCucumberReport() throws IOException{
		File reportOutputDirectory = new File("target");
		ArrayList<String> jsonFiles = new ArrayList<String>();
		jsonFiles.add("target/cucumber.json");
		/* Retrive Data from config.properties file */
		config = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir") +"//config.properties");
		config.load(ip);
		String baseUrl = null;
		if(config.getProperty("env").equals("test")) {
			baseUrl = config.getProperty("testurl");
		}else if(config.getProperty("env").equals("dev")) {
			baseUrl = config.getProperty("devurl");
		}
		String projectName = config.getProperty("project");
		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		configuration.addClassifications("platform", System.getProperty("os.name"));
		configuration.addClassifications("Application", config.getProperty("application"));
		configuration.addClassifications("BrowserType", config.getProperty("browserType"));
		configuration.addClassifications("Environment", config.getProperty("env"));
		configuration.addClassifications("URL", baseUrl);
		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		reportBuilder.generateReports();
	}
	public static void formatReport() {
		File file = new File(System.getProperty("user.dir") + "\\target\\cucumber-html-reports");
		File[] files = file.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.toLowerCase().endsWith(".html")) {
					return true;
				} else {
					return false;
				}
			}
		});
		String filename = "";
		for(File f : files) {
			if(filename.length() > 0) {
				filename = filename + "#" + f.getName();
			} else {
				filename = f.getName();
			}
		}
		String[] myArray;
		myArray = filename.split("#");
		for(int i = 0; i < myArray.length;i++) {
			String filePath = System.getProperty("user.dir") + "\\target\\cucumber-html-reports\\" + myArray[i];
			String oldString = "<small>generate Cucumber HTML report via:&nbsp;</small>\r\n" 
					+ "<a class=\"navbar-link\" href=\"https://github.com/jenkinsci/cucumber-reports-plugin\">Jenkins Plugin</a>" 
					+ "<a class=\"navbar-link\" href=\"https://github.com/damianszczepanik/cucumber-reporting\">Standalone</a>" 
					+ "<a class=\"navbar-link\" href=\"https://github.com/damianszczepanik/cucumber-sandwich\">Sandwich</a>" 
					+ "<a class=\"navbar-link\" href=\"https://github.com/damianszczepanik/maven-cucumber-reporting\">Maven</a>";
			String newString = "";
			File fileToBeModified = new File(filePath);
			String oldContent = "";
			BufferedReader reader = null;
			FileWriter writer = null;
			try {
				reader = new BufferedReader(new FileReader(fileToBeModified));
				// Reading all the lines of inputtext file into oldContent
				String line = reader.readLine();
				while(line != null) {
					oldContent = oldContent + line + System.lineSeparator();
					line = reader.readLine();
				}
				//Replacing oldString with newString in the oldContent
				String newContent = oldContent.replaceAll(oldString, newString);
				newContent = newContent.replace("|", "");
				newContent = newContent.replace("<p class=\"navbar-text navbar-right\">", 
						"<p class=\"navbar-text navbar-right\">|End of report|");
				newContent = newContent.replace("Cucumber", "TestNg-Cucumber");
				newContent = newContent.replace(">TestNg-Cucumber Reprt</p>", 
						"><b><font color=\"#80ff80\">AUTOMATION DASHBOARD</font></b></p>");
				newContent = newContent.replace(">Features</a></li>", "><b>Features</b></li>");
				newContent = newContent.replace(">Failures</a></li>", "><b>Failures</b></li>");
				newContent = newContent.replace(">Tags</a></li>", "><b>Tags</b></li>");
				newContent = newContent.replace(">Steps</a></li>", "><b>Steps</b></li>");
				newContent = newContent.replace("<title>TestNg-CucumberReports - Features Overview</title>", 
						"<title>Automation Dashboard - TestNg-Cucumber Report</title>");
				newContent = newContent.replace("<nav class=\"navbar navbar-default navbar-fixed-top\" id=\"navigation\">", 
						"<nav class=\"navbar navbar-default navbar-fixed-top\" id=\"navigation\" style=\"background-color:#000000\">");
				newContent = newContent.replace("<div class=\"collapse navbar-collapse\">", 
						"<div class=\"collapse navbar-collapse\" style\"background-color:black\">");
				newContent = newContent.replace("<body>", "<body style=\"background-color:#ffffff; background-color>#000000\">");
				newContent = newContent.replace("<th>Date</th>", "<th>Execution Date & Time</th>");
				newContent = newContent.replace("<h2>Features Statistics</h2>", "<h2><b>Test Execution Statistics :</b></h2>");
				newContent = newContent.replace("<tr class=\"info\">", "<tr class=\"info\" style=\"color:#000000\">");
				newContent = newContent.replace("<<table class=\"step-arguments\">", "<table class=\"step-arguments\" style=\"color:#ffffff\">");
				newContent = newContent.replace("div class=\"container-fluid\" id=\"report\" style=\"color:#000000\">", 
						"<div class=\"container-fluid\" id=\"report\" style=\"color:#000000\">");
				newContent = newContent.replace("<div class=\"brief failed\">", 
						"<div class=\"brief failed\" style=\"color:#000000\">");
				newContent = newContent.replace("<div class=\"brief skipped\">", 
						"<div class=\"brief skipped\" style=\"color:#000000\">");
				newContent = newContent.replace("<div class=\"brief undefined\">", 
						"<div class=\"brief undefined\" style=\"color:#000000\">");
				newContent = newContent.replace("<div id=\"featureChartCarousel\"class=\"carousel slide\" data-ride=\"carousel\">", 
						"<div id=\"featureChartCarousel\" class=\"carousel slide\" data-ride=\"carousel\" style=\"background-color:#262626\">");
				newContent = newContent.replace("chart\" height=\"120\"", "chart\" height=\"110\"");
				newContent = newContent.replace("The following graphsshow passing and failing statistics for features", "The following graphs show pass and fail statistics at featue\\scenario\\teststeplevel");
				//Rewriting the input text file with newContent
				writer =new FileWriter(fileToBeModified);
				writer.write(newContent);
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try {
					reader.close();
					writer.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static WebElement getWebElement(String field, int numberofseconds) throws Exception{
		WebDriverWait wait =new WebDriverWait(driver,  numberofseconds);
		try {
			WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(field)));
			return ele;
		}catch (Exception e) {
			Library.getscreenshot();
			throw new Exception("FIELD ' " + field+ "' NOT FOUND, see screenshot: " + failureScreenshotName);  
		}  
	}

	public static boolean doesObjectExist(WebElement ele) {
		Library.highLightElement(driver, ele);
		Library.removeHighLightElement(driver, ele);
		return ele.isDisplayed();
	}

	public static boolean click(WebElement ele) {
		ele.click();
		return true;
	}

	public static boolean moveToElement(WebElement ele) {
		Actions action = new Actions(driver);
		action.moveToElement(ele).perform();
		return true;
	}

	public static void doubleClickWebelement(WebElement ele) throws InterruptedException {
		Actions action = new Actions(driver);
		action.doubleClick().perform();
		Thread.sleep(2000);
	}

	public static void navigateToEveryLinkInPage() throws InterruptedException {
		List<WebElement> linksize = driver.findElements(By.tagName("a"));
		int linksCount = linksize.size();
		String[] links =new String[linksCount];
		for(int i=0;i<linksCount;i++) {
			links[i] = linksize.get(i).getAttribute("href");
		}
		for(int i=0;i<linksCount;i++) {
			driver.navigate().to(links[i]);
		}
	}

	public static void clickelement(String field, int numberofseconds) throws Throwable{
		try {
			ele = Library.getWebElement(field, 20);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.documentElement))");
			Library.highLightElement(driver, ele);
			Library.getscreenshot();
			ele.click();
			Library.getscreenshot();
		}catch(StaleElementReferenceException e) {
			Library.getscreenshot();
		}
	}

	public static void clickelement1(String field, int numberofseconds) throws Throwable{
		try {
			ele = Library.getWebElement(field, 20);
			/*JavascriptExecutor jse = (JavascriptExecutor)driver;
		 jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.documentElement))");
		 Library.highLightElement(driver, ele);
		 Library.getscreenshot();*/
			ele.click();
			Library.getscreenshot();
		}catch(StaleElementReferenceException e) {
			Library.getscreenshot();
		}
	}

	public static void printDialog(String field) {
		JFrame frame =new JFrame("JOptionPane showMessageDialog example");
		JOptionPane.showMessageDialog(frame, field);
	}

	/**
	 * @Webdriver Reference
	 * @Waiting Time
	 */
	public void waitToLoad(WebDriver driver, int waitTime) {
		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}

	public void takePartialScreenShot(WebElement element) throws IOException{
		String screenShot = System.getProperty("user.dir") + "\\screenshot.png";
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Point p = element.getLocation();
		int width = element.getSize().getWidth();
		int height = element.getSize().getHeight();
		BufferedImage img = ImageIO.read(screen);
		BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, height);
		ImageIO.write(dest, "png", screen);
		FileUtils.copyFile(screen, new File(screenShot));	
	}

	public static void copyFiles(File sourceFolder, File detinationFolder) throws IOException {
		String files[] = sourceFolder.list();

		for(String file : files) {
			if(file.endsWith("html") || file.endsWith("png")) {
				File srcFile = new File(sourceFolder, file);
				File destFile = new File(detinationFolder, file);
				Files.move(srcFile, destFile);	
			}
		}
	}
	/**
	 * @Element from locator properties
	 * @max wait time for object identification
	 */
	public static String getText(String field, int numberofseconds) throws Throwable {
		String elementText = null;
		try {
			ele = Library.getWebElement(field, numberofseconds);
			if(ele != null) {
				elementText = ele.getText();
			}
		}catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return elementText;
	}
	/**
	 * verify value from text field
	 * 
	 * @param field           field from locator properties
	 * @param expectedValue   String type expected value
	 * @param numberofseconds max wait time for object identification
	 */

	public static boolean verifyValueTextBox(String field, String expectedvalue, int numberofseconds) throws Throwable{
		String sTempStr = null;
		boolean result = false;
		try {
			ele = Library.getWebElement(field, numberofseconds);
			expectedvalue = expectedvalue.trim().toLowerCase();
			sTempStr = ele.getAttribute("value").trim().toLowerCase();
			if((sTempStr.contains(expectedvalue))) {
				result = true;
			} else {
				result = false;
			}
		}catch (Exception e) {
			throw new RuntimeException("Exception incommonSetTextTextBox:" + e.getMessage());
		}
		return result;
	}

	/**
	 * Select Item from combo box
	 * 
	 * @param field           field from locator properties
	 * @param expectedValue   visible text item to select
	 * @param numberofseconds max wait time for object identification
	 */
	public static boolean selectItemInCombobox(String field, String itemtext, int numberofseconds) throws Throwable{
		ele = Library.getWebElement(field, numberofseconds);
		Library.highLightElement(driver, ele);
		itemtext = Library.getData(field);
		boolean result = false;
		try {
			Select datatype = new Select(ele);
			datatype.selectByVisibleText(itemtext);
			result = verifyItemInCombobox(ele, itemtext, numberofseconds);
			Library.removeHighLightElement(driver, ele);
		}catch (Exception e) {
			throw new RuntimeException("Exception in commonSlectionItemInCombobox:" + e.getMessage());
		}
		return result;
	}
	
	public static void selectItemDropbox(String itemtext, String field) throws Throwable {
		ele =Library.getWebElement(field, 20);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);

		Assert.assertTrue(ele.isEnabled(), "Field '" + field +"' is disbled");
		Library.highLightElement(driver, ele);
		try {
			Select datatype = new Select(ele);
			datatype.selectByVisibleText(itemtext);
		} catch (Exception e) {
			throw new RuntimeException("Exception in commonSelectItemInCombobox:" + e.getMessage());
		}
	}
    
	/**
	 * @param comboBox           webElement reference
	 * @param sExpectedItemText  String type text which is verifying in the combo box
	 */
     
	public static boolean verifyItemInCombobox(WebElement ele, String expecteditemtext, int numberofseconds) {
		String actualText;
		boolean Result = false;
		try {
			Select selectValue = new Select(ele);
			actualText = selectValue.getFirstSelectedOption().getText();
			if(actualText.equalsIgnoreCase(expecteditemtext)) {
				Result = true;
			} else {
				Result = false;
			}
			} catch (Exception e) {
				throw new RuntimeException("Exception in commonSelectitemInCombobox:" + e.getMessage());
			}
			return Result;
		}
	
    public boolean checkUncheckBox(String field, String checkstatus, int numberofseconds) {
    	boolean result = false;
    	
    	try {
    		ele = Library.getWebElement(field, numberofseconds);
    		if(ele.isSelected()) {
    			result = true;
    		} else if(ele.isSelected() == false) {
    			ele.click();
    			result = true;
    		} else if(ele.isSelected() == false) {
    			result = true;
    		} else if (ele.isSelected()) {
    			ele.click();
    			result = true;
    		}
    	} catch(Exception e) {
    		throw new RuntimeException("Exception in commonSelectItemInCombobox:" + e.getMessage());
    	}
    	return result;
    }
    
    /**
	 * @param controlInfo    WebElement reference
	 * @param sExpectedText  String type expected text
	 * @param boolean True/false as result of verification level
	 */
    public boolean verifyLabelText(String field, String expectedtext, int numberofseconds) {
    	   boolean Result;
    	   String Temp = null;
    	   try {
    		   ele = Library.getWebElement(field, numberofseconds);
    		   Temp = ele.getText().trim().toLowerCase();
    		   if(Temp.contains(expectedtext.toLowerCase())) {
    			    Result = true;
    		   }else {
    			   Result = false;
    		   } 
    	   }catch(Exception e) {
    		  throw new RuntimeException("Exception in commonVerifyLabelText:" + e.getMessage()); 
    		   }
    	   return Result;
    	   }
    
    /**
	 * @param checkbox    WebElement reference
	 * @param checkstatus  String type check/uncheck status
	 */
    public boolean verifyCheckUncheckCheckBox(String field, String checkstatus, int numberofseconds) {
    	boolean result = false;
    	try {
    		ele = Library.getWebElement(field, numberofseconds);
    		if(ele.isSelected()) {
    			result = true;
    		} else if(ele.isSelected() == false) {
    			result = true;
    		} else if(ele.isSelected()== false) {
    			result = false;
    		} else if(ele.isSelected()) {
    			result = false;
    		}
    	} catch(Exception e) {
    		throw new RuntimeException("Exception in verifyCheckUncheckCheckBox:" + e.getMessage());
    	}
    	return result;
    }
    /**
	 * @param - scrolldown
	 */
    public void scrollDown(WebDriver driver, Integer numberoftimes, Integer delay) {
    	try {
    		for(int i=0;i<=numberoftimes;i++) {
    			driver.findElement(By.tagName("body")).sendKeys(Keys.ARROW_DOWN);
    		}
    		Thread.sleep(delay);
    	}catch (Exception e) {
    		throw new RuntimeException("Exception in scrollDown:"+ e.getMessage());
    	}
    }
    /**
	 * @param - scrollUp
	 */
    public void scrollUp(WebDriver driver, Integer numberoftimes, Integer delay) {
    	try {
    		for(int i=0;i<=numberoftimes;i++) {
    			driver.findElement(By.tagName("body")).sendKeys(Keys.ARROW_UP);
    		}
    		Thread.sleep(delay);
    	}catch (Exception e) {
    		throw new RuntimeException("Exception in scrollDown:"+ e.getMessage());
    	}
    }
    /**
	 * @param - This function wil check the data is number or not and return true or false
	 */
    public boolean isNumber(String sinput) {
    	boolean result = false;
    	try {
    		if(sinput.contains(",")) {
    		 //remove
    	    sinput = sinput.replace(",", "");
    	    Double.parseDouble(sinput);
    	    result = true;
    		} else if(sinput.contains("%")) {
    			//remove %
    		   sinput = sinput.replace("%", "");
    		   Double.parseDouble(sinput);
    		   result = true;
    		} else {
    			Double.parseDouble(sinput);
    			result = true;
    		}
    	} catch (Exception e) {
    		result = false;
    	}
    	return result;
     }
    /**
	 * This function will take comma seprated value and return the array
	 */
    public String[] getValue(String sValue) {
    	String sdata = sValue;
    	String[] a = sdata.split(",");
    	return a;
    	
    }
    
    public List getAllElementInCombobox(String field, int numberofseconds) {
    	List comboboxElements = null;
    	try {
    		ele = Library.getWebElement(field, numberofseconds);
    		Select datatype = new Select(ele);
    		comboboxElements = (List) datatype.getOptions();
    	}catch(Exception e) {
    	    throw new RuntimeException("Exception in getAllElementInCombobox:" + e.getMessage());
    }
    	return comboboxElements;
   
    }
    
    public static String buildDynamicLocator(String field, String findstring, String replacestring) {
    	String field1 = field;
    	String locator = locatorprop.getProperty(field1);
    	String locatorType = locator.split("~")[0];
    	String locatorValue = locator.split("~")[1];
    	locatorValue = locatorValue.replace("VAL", String.valueOf(replacestring));
    	return locatorValue;
    }
    
    public static String generateReportImage() {
    	String userdirectory = System.getProperty("user.dir");
    	String cucumberreportdirectory = userdirectory + "\\target\\cucumber-html-reports";
    	String strfile = "file:///" + cucumberreportdirectory + "\\" + newrptfoldername + "\\" + "overview-features.html";
    	
    	String field = strfile;
    	String reportstatsfilename = null;
    	try {
    		String chromeDriverPath = System.getProperty("user.dir") + "//drivers//windows//chromedriver.exe";
    		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
    		
    		ChromeOptions options = new ChromeOptions();
    		options.addArguments("--disable-gpu");
    		options.addArguments("--incognito");
    		options.addArguments("--no-sandbox");
    		options.addArguments("--disable-dev-shm-usage");
    		options.setExperimentalOption("useAutomationExtension", false);
    		
    		WebDriver driver_rpt = new ChromeDriver(options);
    		
    		driver_rpt.get(field);
    		driver_rpt.manage().window().maximize();
    		
    		JavascriptExecutor js = (JavascriptExecutor)driver_rpt;
    		js.executeScript("document.body.style.zoom='40%'");
    		System.out.print("Reached");
    		Thread.sleep(2000);
    		JavascriptExecutor js1 = (JavascriptExecutor)driver_rpt;
    		js1.executeScript("document.body.style.zoom='100%'");
    		js.executeAsyncScript("window.scrollTo(0, document.body.scrollHeight)");
    		final File screenshotsource = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    		final String imageFileName = new SimpleDateFormat("MM-dd-yyyy+HH-mm-ss-SS").format(new GregorianCalendar().getTime()) + ".png";
    		reportstatsfilename = System.getProperty("user.dir") + "//screenshots//" + newrptfoldername + "//ReportStats-" + imageFileName;
    		final File imageFile = new File(reportstatsfilename);
    		imageFile.getParentFile().mkdir();
    		imageFile.createNewFile();
    		Files.copy(screenshotsource, imageFile);
    		driver_rpt.close();
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return reportstatsfilename;
    }
    
    public static String getfeatureFileNameScenarioId(Scenario scenario) {
    	return scenario.getName();
    }
    
    public static void createTestRunIDFolder()throws IOException{
    	String userdirectory = System.getProperty("user.dir");
    	String cucumberreportdirectory = userdirectory + "//target//cucumber-html-reports";
    	String screenshotsdirectory = userdirectory + "//screenshots";
    	File file = new File(cucumberreportdirectory);
    	String[] directories = file.list(new FilenameFilter() {
    		public boolean accept(File current, String name) {
    			return new File(current,name).isDirectory();
    		}
		});
    	int lastrunid =0;
    	for(int i=0; i<=directories.length - 1; i++) {
    		if(directories[i].contains("Run")) {
    		  int runID = Integer.parseInt(directories[i].split("_")[1]);
    		  
    		  if(lastrunid < runID) {
    			  lastrunid = runID;
    		  }
    		}
    	}
    	int newrunid = lastrunid + 1;
    	newrptfoldername = "Run_" + newrunid + "_" + new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss-SS").format(new GregorianCalendar().getTime());
    	file = new File(cucumberreportdirectory + "\\" + newrptfoldername);
    	file.mkdir();
    	file = new File(screenshotsdirectory + "\\" + newrptfoldername);
    	file.mkdir();
    }
    
    public static void moveReportsAndScreenshots()throws IOException{
    	String userdirectory = System.getProperty("user.dir");
    	String cucumberreportdirectory = userdirectory + "\\target\\cucumber-html-reports";
    	String screenshotsdirectory = userdirectory + "\\screenshots";
    	
    	File sourceFolder = new File(cucumberreportdirectory + "\\css");
    	File destinationFolder = new File(cucumberreportdirectory + "\\" + newrptfoldername + "\\css");
    	copyFolder(sourceFolder, destinationFolder);
    	
    	sourceFolder = new File(cucumberreportdirectory + "\\fonts");
    	destinationFolder = new File(cucumberreportdirectory + "\\" + newrptfoldername + "\\fonts");
    	copyFolder(sourceFolder, destinationFolder);
    	
    	sourceFolder = new File(cucumberreportdirectory + "\\images");
    	destinationFolder = new File(cucumberreportdirectory + "\\" + newrptfoldername + "\\images");
    	copyFolder(sourceFolder, destinationFolder);
    	
    	sourceFolder = new File(cucumberreportdirectory + "\\js");
    	destinationFolder = new File(cucumberreportdirectory + "\\" + newrptfoldername + "\\js");
    	copyFolder(sourceFolder, destinationFolder);
    	 //move html reports
    	sourceFolder = new File(cucumberreportdirectory);
    	destinationFolder = new File(cucumberreportdirectory + "\\" + newrptfoldername);
    	copyFolder(sourceFolder, destinationFolder);
    	//movescreenshots
    	sourceFolder = new File(screenshotsdirectory);
    	destinationFolder = new File(screenshotsdirectory + "\\" + newrptfoldername);
    	copyFolder(sourceFolder, destinationFolder);
    }
    
    public static void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
    	
    	if(sourceFolder.isDirectory()) {
    		if(!destinationFolder.exists()) {
    			destinationFolder.mkdir();
    		}
    		String files[] = sourceFolder.list();
    		for(String file : files) {
    			File srcFile = new File(sourceFolder, file);
    			File destFile = new File(destinationFolder, file);
    			copyFolder(srcFile, destFile);
    		}
    		
    	} else {
    		Files.copy(sourceFolder, destinationFolder);
    	}
    }
    
    public static void navigateBack() {
    	driver.navigate().back();
    }
    
    public static void verifyText(String field, String exepectedtext, int numberofseconds) {
    	String Temp = null;
    	try {
    		ele = Library.getWebElement(field, numberofseconds);
    		Temp = ele.getAttribute("value");
    		if(Temp.contains(exepectedtext.toLowerCase())) {
    			Assert.assertTrue(true);
    		} else {
    			Assert.assertTrue(false);
    		}
    	}catch (Exception e) {
    	  throw new RuntimeException("Exception in commonVerifyLabelText:" + e.getMessage());
    	  
    		}
    	}
    public static void enterValue(String field, int numberofseconds) throws Throwable{
    	try {
    		ele = Library.getWebElement(field, numberofseconds);
    		ele.click();
    		ele.clear();
    		Assert.assertTrue(ele.isEnabled(), "Field '" + field + "' is disabled");
    		Library.highLightElement(driver, ele);
    		ele.sendKeys(Library.getData(field));
    		Library.getscreenshot();
    		Library.removeHighLightElement(driver, ele);
    	}catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    public static void enterValue(String field, String value, int numberofseconds) throws Throwable {
    	ele = Library.getWebElement(field, numberofseconds);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
    	
    	Assert.assertTrue(ele.isEnabled(), "Field '" + field + "' is disabled");
    	Library.highLightElement(driver, ele);
    	
    	ele.clear();
    	
    	ele.sendKeys(Keys.chord(Keys.CONTROL, "a"));
    	ele.sendKeys(Keys.BACK_SPACE);
    	System.out.println(value);
    	ele.sendKeys(Keys.TAB);
    }
    
    public static void enterValuewithoutclear(String field, String value, int numberofseconds) throws Throwable{
    	ele =Library.getWebElement(field, numberofseconds);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
    	
    	Assert.assertTrue(ele.isEnabled(), "Field '" + field + "' is disabled");
    	Library.highLightElement(driver, ele);
    	ele.sendKeys(value);
    	ele.sendKeys(Keys.TAB);
    }
 
}



