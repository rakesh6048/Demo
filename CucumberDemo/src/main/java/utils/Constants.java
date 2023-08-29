package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Constants {
	
	public static Properties config = null;
	public static Properties bsconfig = null;
	public static Properties locatorprop = null;
	public static Properties testdataprop = null;
	public static WebDriver driver = null;
	public static String reportpath = System.getProperty("user.dir") + "//screenshot";
	public static String reportpathink = "TEMP";
	public static String baseUrl = null;
	
	public static void configureDriverPath() throws IOException {
		
		//WebDriverManager.firefoxdriver().setup();
	    //String firefoxDriverPath = System.getProperty("user.dir") + "//drivers//windows//geckodriver.exe";
	    //System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
		
		//WebDriverManager.chromedriver().setup();
	    //String chromeDriverPath = System.getProperty("user.dir") + "//drivers//windows//chromedriver.exe";
	    //System.setProperty("webdriver.chrome.driver", chromeDriverPath);
	    System.setProperty("webdriver.chrome.driver", "D:/Driver/chromedriver_win32/chromedriver.exe");
		
		//WebDriverManager.iedriver().setup();
	    //String ieDriverPath = System.getProperty("user.dir") + "//drivers//windows//IEDriverServer.exe";
	    //System.setProperty("webdriver.ie.driver", ieDriverPath);
	}
	
	public static void LoadConfigProperty() throws FileNotFoundException {
		config = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "//config.properties");
		try {
			config.load(ip);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void LoadBSConfigProperty() throws FileNotFoundException {
		bsconfig = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "//BSconfig.properties");
		try {
			bsconfig.load(ip);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void LoadLocatorProperty() throws FileNotFoundException {
		locatorprop = new Properties();
		FileInputStream locatorfile = new FileInputStream(System.getProperty("user.dir") + "//locator.properties");
		try {
			locatorprop.load(locatorfile);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void LoadTestDataProperty() throws FileNotFoundException {
		testdataprop = new Properties();
		FileInputStream testdataFile = new FileInputStream(System.getProperty("user.dir") + "//TestData.properties");
		try {
			testdataprop.load(testdataFile);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
