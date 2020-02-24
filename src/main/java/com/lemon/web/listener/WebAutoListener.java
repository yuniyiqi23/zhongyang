package com.lemon.web.listener;/**
 * Created by TW on 2019/11/12 18:18
 */

import com.lemon.web.base.BaseTester;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 *@program: JavaAutoTest
 *@description: 监听类
 *@author: liu yan
 *@create: 2019-11-12 18:18
 */
public class WebAutoListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr){
        //测试方法名
        String methodName = tr.getMethod().getMethodName();
        try {
            //调用一个保存截屏，并且为allure报告添加截图信息的方法
            takeScreenShot(methodName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Attachment(value = "Failure in method {0}", type = "image/png")
    private byte[] takeScreenShot(String methodName) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) BaseTester.getDriver();
        return takesScreenshot.getScreenshotAs(OutputType.BYTES);
    }

}
