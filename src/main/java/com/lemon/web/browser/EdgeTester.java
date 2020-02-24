package com.lemon.web.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeTester {

	//The path to the driver executable must be set by the webdriver.edge.driver system property;
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.edge.driver", "src/main/resources/driver/MicrosoftWebDriver.exe");
		EdgeDriver driver = new EdgeDriver();
		driver.get("http://www.baidu.com");
		driver.findElement(By.id("kw")).sendKeys("欢迎大家来到柠檬班");
		
		Thread.sleep(5000);
		driver.quit();//关闭浏览器
	}

}
