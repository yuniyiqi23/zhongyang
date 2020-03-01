package com.zhongyang.web.testcase.login;/**
 * Created by TW on 2019/10/31 18:18
 */


import com.zhongyang.base.pojo.ExcelObject;

/**
 *@program: JavaAutoTest
 *@description: 登录失败数据对象
 *@author: liu yan
 *@create: 2019-10-31 18:18
 */
public class LoginFailureData extends ExcelObject {
//    Phone	Password	ExpectedTips
    private String phone;
    private String password;
    private String expectedTips;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExpectedTips() {
        return expectedTips;
    }

    public void setExpectedTips(String expectedTips) {
        this.expectedTips = expectedTips;
    }

    @Override
    public String toString() {
        return "LoginFailData{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", expectedTips='" + expectedTips + '\'' +
                "} " + super.toString();
    }
}
