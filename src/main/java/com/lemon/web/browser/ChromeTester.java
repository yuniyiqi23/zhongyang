package com.lemon.web.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeTester {

	public static void main(String[] args) throws InterruptedException {
		//异常1：The path to the driver executable must be set by the webdriver.chrome.driver system property;
		//解决方法：1添加到path 2指定驱动文件的路径
		//1:打开https://docs.seleniumhq.org/download/
		//2:找到第三方chrome驱动下载地址:https://sites.google.com/a/chromium.org/chromedriver/downloads
		//学习版本：selenium2.53.1+chrome60.0+chromedriver2.32
		
		//一定要记住的：http://www.lemfix.com/topics/321
		
		System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://www.baidu.com");
		driver.findElement(By.id("kw")).sendKeys("柠檬班");
		driver.findElement(By.id("su")).click();
		Thread.sleep(5000);
		driver.quit();
	}
	//！！！ 驱动的镜像：https://npm.taobao.org/mirrors/chromedriver
	//!!!  资源网站：http://www.lemfix.com/topics/321
	/**
	 v2.31	v58-60
	v2.30	v58-60
	v2.29	v56-58
	v2.28	v55-57
	v2.27	v54-56
	v2.26	v53-55
	v2.25	v53-55
	v2.24	v52-54
	v2.23	v51-53
	v2.22	v49-52
	v2.21	v46-50
	v2.20	v43-48
	v2.19	v43-47
	v2.18	v43-46
	 */
	
	/*
	 * 有可能会遇到的错误：
	 * 使用3.x的selenium来完成自动化测试时，代码报了如下错误：
	Exception in thread "main" java.lang.UnsupportedClassVersionError: 
	
	错误总结：
	3.x的selenium需要1.8的jdk，可能jdk版本过低
	
	解决办法：
	降级selenium版本，或提高jdk的版本为1.8

	 */

}
