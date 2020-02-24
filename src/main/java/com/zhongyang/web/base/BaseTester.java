package com.zhongyang.web.base;/**
 * Created by TW on 2019/10/28 16:59
 */

import com.zhongyang.web.pojo.Locator;
import com.zhongyang.web.utils.LocatorUtils;
import com.zhongyang.web.utils.PropertiesUtil;
import com.zhongyang.web.utils.WebAutoUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.Map;

/**
 * @program: JavaAutoTest
 * @description: 测试基类
 * @author: liu yan
 * @create: 2019-10-28 16:59
 */
public abstract class BaseTester {
    // TODO 完善日志

    protected static WebDriver driver = null;
    private static long WAITTIME = 5L;
    protected static int CLOSEWINDOWTIME = 2000;

    protected abstract String getCurrentPageName();

    public static WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public void beforeSuite() {
        driver = WebAutoUtils.getDriver("chrome", "2.x");
//        driver.manage().window().maximize();
    }

    @AfterSuite
    public void afterSuite() throws InterruptedException {
        Thread.sleep(CLOSEWINDOWTIME);
        driver.quit();
    }

    /**
     * @Description: 显示等待元素
     * @Param: [by]
     * @return: org.openqa.selenium.WebElement
     * @Author: Adam
     * @Date: 2019/10/29
     */
    protected static WebElement getElement(By by) {
        return getElement(by, WAITTIME);
    }

    protected WebElement getElement(String pageName, String locatorName) {
        Map<String, Locator> locators = LocatorUtils.getLocatorsByPageName(pageName);
        Locator locator = locators.get(locatorName);
        String type = locator.getType();// 定位的方式：id,name,cssSelector，xpath...
        String value = locator.getValue();// 定位的值:phone password
        By by = null;
        if ("id".equalsIgnoreCase(type)) {
            by = By.id(value);
        } else if ("name".equalsIgnoreCase(type)) {
            by = By.name(value);
        } else if ("tagName".equalsIgnoreCase(type)) {
            by = By.tagName(value);
        } else if ("linkText".equalsIgnoreCase(type)) {
            by = By.linkText(value);
        } else if ("partialLinkText".equalsIgnoreCase(type)) {
            by = By.partialLinkText(value);
        } else if ("cssSelector".equalsIgnoreCase(type)) {
            by = By.cssSelector(value);
        } else if ("className".equalsIgnoreCase(type)) {
            by = By.className(value);
        } else if ("xpath".equalsIgnoreCase(type)) {
            by = By.xpath(value);
        } else {
            throw new RuntimeException("没有这种定位方式！！！");
        }
        return getElement(by, WAITTIME);
    }

    /**
     * @Description: 显示等待元素
     * @Param: [by, time]
     * @return: org.openqa.selenium.WebElement
     * @Author: Adam
     * @Date: 2019/10/29
     */
    protected static WebElement getElement(final By by, long time) {
        WebDriverWait wait = new WebDriverWait(driver, time);
        return wait.until(new ExpectedCondition<WebElement>() {

            @Override
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(by);
            }
        });
    }

    /**
     * @Description: 智能等待获取元素文本
     * @Param: []
     * @return: java.lang.String
     * @Author: Adam
     * @Date: 2019/10/31
     */
    protected String excuteGetElementText(final String pageName, final String locatorName) {
        WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
        return wait.until(new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver webDriver) {
                WebElement element = getElement(pageName, locatorName);
                // 判断元素是否存在
                if (element != null && element.getText() != null && element
                        .getText().length() > 0) {
                    return element.getText();
                }
                return null;
            }
        });
    }

    /**
     * @Description: 获取当前页面元素文本
     * @Param: [locatorName]
     * @return: java.lang.String
     * @Author: Adam
     * @Date: 2019/11/7
     */
    protected String getElementText(String locatorName) {
        return getElementText(this.getCurrentPageName(), locatorName);
    }

    /** 
    * @Description: 获取页面元素的文本 
    * @Param: [pageName, locatorName] 
    * @return: java.lang.String 
    * @Author: Adam
    * @Date: 2019/11/7 
    */
    protected String getElementText(String pageName, String locatorName) {
        return excuteGetElementText(pageName, locatorName);
    }

    /**
     * @Description: 打开网页
     * @Param: [url]
     * @return: void
     * @Author: Adam
     * @Date: 2019/10/29
     */
    protected void toURL(String urlKey) {
        driver.get(PropertiesUtil.getPropertieValue(urlKey));
    }

    /**
     * @Description: 点击元素
     * @Param: []
     * @return: void
     * @Author: Adam
     * @Date: 2019/10/28
     */
    protected static void click(By by) {
        getElement(by).click();
    }

    protected void click(String pageName, String locatorName) {
        getElement(pageName, locatorName).click();
    }

    protected void click(String locatorName) {
        getElement(this.getCurrentPageName(), locatorName).click();
    }

    /**
     * @Description: 输入元素
     * @Param: []
     * @return: void
     * @Author: Adam
     * @Date: 2019/10/28
     */
    protected static void type(By by, String content) {
        getElement(by).sendKeys(content);
    }

    protected void type(String pageName, String locatorName, String content) {
        getElement(pageName, locatorName).sendKeys(content);
    }

    protected void type(String locatorName, String content) {
        getElement(this.getCurrentPageName(), locatorName).sendKeys(content);
    }

    /**
     * @Description: 获取元素文本
     * @Param: []
     * @return: void
     * @Author: Adam
     * @Date: 2019/10/28
     */
    protected static String getText(By by) {
        return getElement(by).getText();
    }

    /**
     * @Description: 智能等待，获取URL
     * @Param: []
     * @return: java.lang.String
     * @Author: Adam
     * @Date: 2019/10/31
     */
    protected boolean currentUrlContainers(final String partialContext) {
        WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
//        wait.until(ExpectedConditions.urlContains(partialContext));

        return wait.until(new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver webDriver) {
                String currentUrl = driver.getCurrentUrl();
                if (currentUrl != null && currentUrl.contains(partialContext)) {
                    return true;
                }
                return null;
            }
        });
    }

    /**
     * @Description: 当前页面提示信息断言
     * @Param: [elementName, expectedText]
     * @return: void
     * @Author: Adam
     * @Date: 2019/11/7
     */
    protected void assertElementText(String locatorName, String expectedText) {
        assertElementText(this.getCurrentPageName(), locatorName, expectedText);
    }

    /** 
    * @Description: 页面元素文本断言
    * @Param: [pageName, elementName, expectedText] 
    * @return: void 
    * @Author: Adam
    * @Date: 2019/11/7 
    */
    protected void assertElementText(String pageName, String locatorName,
                                     String expectedText) {
        String actual = getElementText(pageName, locatorName);
        Assert.assertEquals(actual, expectedText);
    }

    /** 
    * @Description: 断言当前URL包含某部分文本
    * @Param: [partialUrl] 
    * @return: void 
    * @Author: Adam
    * @Date: 2019/11/7 
    */
    protected void assertCurrentUrlContainers(String partialUrl) {
        Assert.assertTrue(currentUrlContainers(partialUrl));
    }

    // assertTextPresent：断言页面元素文本值为某文本
    protected void assertTextPresent(String pageName,String locatorName,String expectedText){
        //TODO
    }
    // assertPartialTextPresent：断言指定页面元素文本值包含某文本
    protected void assertPartialTextPresent(String pageName,String locatorName,String expectedText){
        String actualText = getElement(pageName, locatorName).getText();
        Assert.assertTrue(actualText.contains(expectedText));
    }
    //断言指定页面元素文本值包含某文本
    protected void assertPartialTextPresent(String locatorName,String expectedText){
        assertPartialTextPresent(this.getCurrentPageName(), locatorName, expectedText);
    }

    // assertElementEditable：断言某元素可编辑
    protected void assertElementEditable(String pageName,String locatorName){
        WebElement element = getElement(pageName, locatorName);
        Assert.assertTrue(element.isEnabled());
    }
    protected void assertElementEditable(String locatorName){
        assertElementEditable(this.getCurrentPageName(), locatorName);
    }
    // assertElementNotEditable：断言某元素不可编辑
    // assertURLContains：断言当前URL包含
    // assertTextNotPresent
    // assertTextNotPresentPrecesion
    // assertElementAttributeValueEquals
    // assertElementAttributeValueNotEquals
    // assertAlertTextContains
}
