package com.lemon.web.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxTester {

	// selenium2.xxx版本，Firefox驱动程序是集成在FirefoxDriver（Firefox46.0 + selenium
	// 2.53.1）
	// selenium3.xxx本本，Firefox是需要驱动程序（放到Path路径里，或者代码手动设置）
	// System.setProperty("webdriver.gecko.driver",
	// "src/test/resources/geckodriver.exe")
	/**
	 * 异常1：如果在PATH中找不到Firefox的可执行程序对应的路径，会报如下错误
	 * 	Cannot find firefox binary in PATH. Make sure firefox is installed.
	 * 		fix1:添加环境变量
	 * 		fix2:指定可执行文件的路径
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		//0:设置系统属性，指定firefox的可执行文件的路径
//		System.setProperty("webdriver.firefox.bin", "‪D:\\Program Files\\Mozilla Firefox64\\firefox.exe");
		System.setProperty("webdriver.gecko.driver", "src/main/resources/driver/geckodriver.exe");
		// 1:自动启动浏览器-->selenium提供给我们启动Firefox浏览器的类、方法
		FirefoxDriver driver = new FirefoxDriver();
		// 2:自动输入url
		driver.get("http://www.baidu.com");
		// 3:自动找到百度输入框，输入要查找的内容
		WebElement input = driver.findElement(By.id("kw"));
		// System.out.println(input.getAttribute("name"));
		// System.out.println(input.getAttribute("maxlength"));
		input.sendKeys("柠檬班软件测试");
		// 4:自动找到"百度一下"按钮，点击
		driver.findElement(By.id("su")).click();
		// 5:自动关闭浏览器
		Thread.sleep(5000);
		driver.quit();
		
	}
	/**  se2.xx     firefox
	 *  2.25.0	v 18
		2.30.0	v 19
		2.31.0	v 20
		2.42.2	v 29
		2.44.0	v 33 (不支持31)
		2.52.0	v 45.0
		2.53.0	v 46.0
		2.53.1	v 47.0.1
		
		Selenium版本	geckodriver版本	firefox版本
		3.3			0.15			v 48+
		3.4			0.16			v 52
		3.4			0.17			v 52
		3.4			0.18			v 53
		
		找版本对应关系到changelog：https://github.com/mozilla/geckodriver/blob/release/CHANGES.md
		a)从selenium 3.0.0开始就要求firefox为48及以上版本
		b)selenium 3.x使用的java版本为jdk 1.8,selenium
		c)selenium 3.x使用geckodriver作为firefox浏览器的驱动的替代
	 */

}
