package framework;

import static utils.Constants.config;
import static utils.Constants.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.Assert;

import utils.Library;


public class BusinessFunctions {
	public static void checkUILoader() {
		int docount = 0;
		do {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			docount = docount + 1;
			System.out.println("PageLoadinggCheck - counter: " + docount);
			try {
				Thread.sleep(100);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(docount == 500) {
				Assert.fail("Application taking longer time than usual to load the data");

				break;
			}
		}while(driver.findElements(By.xpath("//div[@class='ui active loader']")).size() != 0);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}


}
