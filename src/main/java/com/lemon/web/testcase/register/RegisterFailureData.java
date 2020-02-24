package com.lemon.web.testcase.register;/**
 * Created by TW on 2019/10/31 18:18
 */


import com.zhongyang.pojo.ExcelObject;

/**
 *@program: JavaAutoTest
 *@description: 登录失败数据对象
 *@author: liu yan
 *@create: 2019-10-31 18:18
 */
public class RegisterFailureData extends ExcelObject {
//    Mobilephone	Password	Pwdconfirm	Verifycode ExpectedTips
    private String mobilephone;
    private String password;
    private String pwdConfirm;
    private String verifyCode;
    private String expectedTips;

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwdConfirm() {
        return pwdConfirm;
    }

    public void setPwdConfirm(String pwdConfirm) {
        this.pwdConfirm = pwdConfirm;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getExpectedTips() {
        return expectedTips;
    }

    public void setExpectedTips(String expectedTips) {
        this.expectedTips = expectedTips;
    }

    @Override
    public String toString() {
        return "RegisterFailureData{" +
                "mobilephone='" + mobilephone + '\'' +
                ", password='" + password + '\'' +
                ", pwdConfirm='" + pwdConfirm + '\'' +
                ", verifycode='" + verifyCode + '\'' +
                ", expectedTips='" + expectedTips + '\'' +
                "} " + super.toString();
    }
}
