package com.lemon.web.testcase.login;/**
 * Created by TW on 2019/10/28 16:51
 */

import com.lemon.web.utils.DataProviderUtils;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

/**
 * @program: JavaAutoTest
 * @description: 登录数据提供者
 * @author: liu yan
 * @create: 2019-10-28 16:51
 */
public class LoginDataProvider {

    //反向测试用例的数据提供者
    @DataProvider
    public static Object[][] failData(Method method) {
        // 从方法名中获取Excel路径
        method.getName();
        return DataProviderUtils.getData("/testcase/login/login" +
                ".xlsx", 0, LoginFailureData.class);
    }

    /**
     * @Description: 正向测试用例
     * @Param: []
     * @return: java.lang.Object[][]
     * @Author: Adam
     * @Date: 2019/10/31
     */
    @DataProvider
    public static Object[][] successData(Method method) {
        return DataProviderUtils.getData("/testcase/login/login" +
                ".xlsx", 0, LoginSuccessData.class);
    }

}
