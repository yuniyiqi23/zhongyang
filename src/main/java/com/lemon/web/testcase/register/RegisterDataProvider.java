package com.lemon.web.testcase.register;/**
 * Created by TW on 2019/10/28 16:51
 */

import com.lemon.web.utils.DataProviderUtils;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

/**
 *@program: JavaAutoTest
 *@description: 注册数据提供者
 *@author: liu yan
 *@create: 2019-10-28 16:51
 */
public class RegisterDataProvider {
    //反向测试用例的数据提供者
    @DataProvider
    public static Object[][] dp1(Method method) {
        // 从方法名中获取Excel路径
        method.getName();
        return DataProviderUtils.getData("/testcase/register/register" +
                ".xlsx", 0, RegisterFailureData.class);
    }

    //正向测试用例的数据提供者
    @DataProvider
    public static Object[][] dp2() {
        return new Object[][] {
                new Object[] { "", "", "", "", "用户名不能为空" },
                new Object[] { "lemon", "", "", "", "非法的手机号" },
                new Object[] { "13888888888", "", "", "", "密码不能为空" },
                new Object[] { "13888888888", "12345", "", "", "密码长度至少为6位" },
                new Object[] { "13888888888", "123456", "", "", "重复密码不能为空" },
                new Object[] { "13888888888", "123456", "12345", "", "密码不一致" },
                new Object[] { "13888888888", "123456", "123456", "", "验证码不能为空" },
        };
    }
}
