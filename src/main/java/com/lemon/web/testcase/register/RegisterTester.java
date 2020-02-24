package com.lemon.web.testcase.register;/**
 * Created by TW on 2019/10/11 20:10
 */

import com.lemon.web.base.BaseTester;
import com.lemon.web.base.CaseDataProvider;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @program: java_web_auto
 * @description: web自动化测试
 * @author: liu yan
 * @create: 2019-10-11 20:10
 */
public class RegisterTester extends BaseTester {

    @Override
    protected String getCurrentPageName() {
        return "注册页面";
    }


    @Test(dataProvider = "dp", dataProviderClass = CaseDataProvider.class)
    public void register_failure_test_case(RegisterFailureData testData) throws
            InterruptedException {
        toURL("register_url");
        type("手机号码输入框", testData.getMobilephone());
        type("密码输入框", testData.getPassword());
        type("重复密码输入框", testData.getPwdConfirm());
        type("验证码", testData.getVerifyCode());
        click("注册按钮");
        // 断言
        assertElementText("提示信息元素", testData.getExpectedTips());
    }

}
