package com.zhongyang.web;/**
 * Created by Adam Smith on 2020/4/6 10:35
 */

import com.zhongyang.web.base.BaseTester;
import com.zhongyang.web.pojo.BankInfo;
import com.zhongyang.web.pojo.LoginData;
import com.zhongyang.web.utils.ExcelUtil;
import com.zhongyang.web.utils.WebAutoUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: AutoGetMoney
 * @description: 设置账号银行卡信息
 * @author: liu yan
 * @create: 2020-04-06 10:35
 */
public class SetBankInfo extends BaseTester {
    // 绑定失败提示信息
    // 登出前判断按钮是否可点击

    private static Logger logger = Logger.getLogger(SetBankInfo.class);

    public static void main(String[] args) {
        try {
            logger.info(">>>>>>>>>>>>>>>>>>>开始<<<<<<<<<<<<<<<<<<<<<<");
            // 获取浏览器驱动
            driver = WebAutoUtils.getDriver("chrome", "2.x");
            driver.manage().window().maximize();
            // 获取用户信息
            List<Object> userList = ExcelUtil.readExcel("/BankInfo.xlsx", 0,
                    BankInfo.class);
            // 循环操作填写银行信息
            long current = System.currentTimeMillis();    //当前时间毫秒数
            long zeroT = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();  //今天零点零分零秒的毫秒数
            long endT = zeroT + 24 * 60 * 60 * 1000 - 1;  //今天23点59分59秒的毫秒数
            String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endT);
            System.out.println(end);
            while (true) {
                current = System.currentTimeMillis();    //当前时间毫秒数
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(current));
                while (current > endT) {
                    logger.info(">>>>>>>>>>>>>>>>>>>开始补充银行信息<<<<<<<<<<<<<<<<<<<<<<");
                    for (Object item : userList) {
                        BankInfo user = (BankInfo) item;
                        logger.info(user);
                        // 登录
                        login(user);
                        // 填写银行信息
                        SetUserBankInfo(user);
                        // 登出
                        logout(user);
                    }
                }
                Thread.sleep(1000 * 30);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private static void SetUserBankInfo(BankInfo user) throws InterruptedException {
        driver.get("https://agent.apolloyun.com/user/bank");
//        logger.info("T_AccountNo = " + getAttribute(By.name("T_AccountNo"), "value"));
        // 判断是否已绑定银行卡信息
        if ("".equalsIgnoreCase(getAttribute(By.name("T_AccountNo"), "value"))) {
            // 输入用户名和密码
            type(By.name("T_Name"), user.getUserName());
            type(By.name("T_IdCard"), user.getIDNum());
            type(By.name("T_OpenBankName"), user.getBankName());
            type(By.name("T_AccountNo"), user.getBankAccount());
            // 点击【确认】
            click(By.className("submit"));
            Thread.sleep(500);
        }
    }

    /**
     * @Description: 用户登录
     * @Param: [userInfo]
     * @return: void
     * @Author: Adam
     * @Date: 2020/3/18
     */
    private static void login(BankInfo userInfo) throws InterruptedException {
        logger.info("================== 账号：" + userInfo.getAccount() + " " +
                "开始登录==================");
        // 登录网址每周收租成功
        driver.get("https://agent.apolloyun.com/login");
        // 输入用户名和密码
        type(By.name("T_Email"), userInfo.getAccount());
        type(By.name("password"), userInfo.getPassword());
        // 点击【登录】
        click(By.xpath("//button[contains(text(),'登录')]"));
        Thread.sleep(500);
    }

    /**
     * @Description: 用户退出
     * @Param: [userInfo]
     * @return: void
     * @Author: Adam
     * @Date: 2020/3/18
     */
    private static void logout(BankInfo userInfo) {
        logger.info("----------------开始【退出登录】-------------------");
        // 点击账号->点击【退出登录】
        click(By.xpath("//*[@id=\"pageWrapper\"]/div[1]/div[2]/div[4]/button"));
        click(By.xpath("//a[contains(text(),'退出登陆')]"));
        logger.info("================== 账号：" + userInfo.getAccount() + "结束==================");
    }

    @Override
    protected String getCurrentPageName() {
        return null;
    }
}
