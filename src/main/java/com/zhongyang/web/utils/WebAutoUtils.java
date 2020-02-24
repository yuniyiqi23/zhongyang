package com.zhongyang.web.utils;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * web自动化工具类
 * @author happy
 *
 */
public class WebAutoUtils {

	/**
	 * 获得driver
	 * 
	 * @param browserName
	 *            浏览器名称
	 * @param seleniumVersion
	 *            selenium版本号
	 * @return
	 */
	public static WebDriver getDriver(String browserName, String seleniumVersion) {
		if ("IE".equalsIgnoreCase(browserName)) {
			return getIEDriver();
		} else if ("Chrome".equalsIgnoreCase(browserName)) {
			return getChromeDriver();
		} else if ("Firefox".equalsIgnoreCase(browserName)) {
			return getFirefoxDriver(seleniumVersion);
		} else if ("Edge".equalsIgnoreCase(browserName)) {
			return getEdgeDriver();
		} else if ("Opera".equalsIgnoreCase(browserName)) {
			return getOperaDriver();
		} else if ("Safiri".equalsIgnoreCase(browserName)) {
			return getSafiriDriver();
		} else {
			throw new RuntimeException("没有对应的浏览器");
		}
	}

	private static WebDriver getSafiriDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	private static WebDriver getOperaDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	private static WebDriver getEdgeDriver() {
		System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY,
				"src/main/resources/web_driver/MicrosoftWebDriver.exe");
		return new EdgeDriver();
	}

	private static WebDriver getFirefoxDriver(String seleniumVersion) {
		if (!"2.x".equalsIgnoreCase(seleniumVersion)) {
			System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY,
					"src/main/resources/web_driver/geckodriver.exe");
		}
		return new FirefoxDriver();
	}

	private static WebDriver getChromeDriver() {
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
				"src/main/resources/web_driver/chromedriver.exe");
		DesiredCapabilities sCaps = new DesiredCapabilities();
		sCaps.setJavascriptEnabled(true);
		return new ChromeDriver(sCaps);
	}

	private static WebDriver getIEDriver() {
		System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY,
				"src/main/resources/web_driver/IEDriverServer.exe");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.baidu.com");
		return new InternetExplorerDriver(capabilities);
	}

}
