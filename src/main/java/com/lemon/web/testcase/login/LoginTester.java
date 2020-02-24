package com.lemon.web.testcase.login;/**
 * Created by TW on 2019/10/11 20:10
 */

import com.lemon.web.base.BaseTester;
import com.lemon.web.base.CaseDataProvider;
import com.lemon.web.utils.ZTestReport;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * @program: java_web_auto
 * @description: web自动化测试
 * @author: liu yan
 * @create: 2019-10-11 20:10
 */
@Listeners({ZTestReport.class})
public class LoginTester extends BaseTester {

    @Override
    protected String getCurrentPageName() {
        return "登录页面";
    }

    /**
     * @Description: 登录失败
     * @Param: [mobliePhone, password, expectedTips]
     * @return: void
     * @Author: Adam
     * @Date: 2019/10/31
     */
    @Test(dataProvider = "dp", dataProviderClass = CaseDataProvider.class,
            description = "登录失败测试用例")
    public void login_failure_test_case(LoginFailureData testData) throws
            InterruptedException {
        toURL("login_url");
        // 从XML获取元素定位信息
        type("手机号码输入框", testData.getPhone());
        type("密码输入框", testData.getPassword());
        click("登录按钮");
        // 断言
        assertElementText("提示信息元素", testData.getExpectedTips());
    }

    @Test(dataProvider = "dp", dataProviderClass =
            CaseDataProvider.class)
    public void login_success_test_case(LoginSuccessData testData) throws
            InterruptedException {
        toURL("login_url");
        type("手机号码输入框", testData.getPhone());
        type("密码输入框", testData.getPassword());
        click("登录按钮");
//        System.out.println(testData);
        // 断言
        assertCurrentUrlContainers(testData.getPartialUrl());
    }

}
