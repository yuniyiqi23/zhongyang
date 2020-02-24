package com.lemon.web.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IETester {

	// exception1：The path to the driver executable must be set by the
	// webdriver.ie.driver system property;
	// exception2:Unexpected error launching Internet Explorer. Browser zoom
	// level was set to 150%. It should be set to 100%
	// 1:把缩放级别设置到100% 2：忽略缩放级别的
	// exception3：Unexpected error launching Internet Explorer. Protected Mode
	// settings are not the same for all zones. Enable Protected Mode must be
	// set to the same value (enabled or disabled) for all zones.
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.ie.driver", "src/main/resources/driver/IEDriverServer.exe");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.baidu.com");

		InternetExplorerDriver driver = new InternetExplorerDriver(capabilities);
		driver.get("http://www.baidu.com");
		driver.findElement(By.id("kw")).sendKeys("欢迎大家来到柠檬班");

		Thread.sleep(5000);
		driver.quit();// 关闭浏览器
	}

}
