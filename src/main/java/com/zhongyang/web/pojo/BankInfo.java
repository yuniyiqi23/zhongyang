package com.zhongyang.web.pojo;/**
 * Created by Adam Smith on 2020/4/6 10:48
 */

import com.zhongyang.base.pojo.ExcelObject;

/**
 *@program: AutoGetMoney
 *@description: 银行账号信息
 *@author: liu yan
 *@create: 2020-04-06 10:48
 */
public class BankInfo extends ExcelObject {

    private String account;
    private String password;
    private String userName;
    private String bankName;
    private String bankAccount;
    private String IDNum;// 身份证号码

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getIDNum() {
        return IDNum;
    }

    public void setIDNum(String IDNum) {
        this.IDNum = IDNum;
    }

    @Override
    public String toString() {
        return "BankInfo{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", IDNum='" + IDNum + '\'' +
                '}';
    }
}
