package com.lemon.web.pojo;

import com.zhongyang.pojo.ExcelObject;

public class LoginData extends ExcelObject {

    private String account;
    private String password;
    private String secondPassword;
    private String userName;
    private String money;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", secondPassword='" + secondPassword + '\'' +
                ", userName='" + userName + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
