package com.zhongyang.web;

import com.zhongyang.web.base.BaseTester;
import com.zhongyang.web.pojo.LoginData;
import com.zhongyang.web.utils.ExcelUtil;
import com.zhongyang.web.utils.MailUtils;
import com.zhongyang.web.utils.PropertiesUtil;
import com.zhongyang.web.utils.WebAutoUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import javax.swing.plaf.TableHeaderUI;
import java.text.SimpleDateFormat;
import java.util.*;

public class AutoGetMoney extends BaseTester {

    private static Logger logger = Logger.getLogger(AutoGetMoney.class);
    private static Map<String, String> resultFailureMap = new HashMap<String, String>();
    private static int fixedTime = 2; // 时间间隔（min）
    private static int maxRent = 40000;
    private static boolean isGetRent = true;
    private static List<LoginData> collectedAccountList = new ArrayList<>();
    private static String collectedTime = "2020-05-17";

    @DataProvider
    public static Object[][] dp() {
        //得到数据列表
        List<Object> objs = ExcelUtil.readExcel("/login.xlsx", 0,
                LoginData.class);
        //组装到object类型的二维数组
        // 创建一个二维数组
        Object[][] datas = new Object[objs.size()][];
        for (int index = 0; index < objs.size(); index++) {
            Object[] itemArray = {objs.get(index)};// 每个LoginFailData对象保存到一个一维数组
            datas[index] = itemArray;// 把每个一维数组放到二维数组对应索引
        }
        return datas;
    }

    /**
     * @Description: 主程序入口
     * @Param: [args]
     * @return: void
     * @Author: Adam
     * @Date: 2020/3/18
     */
    public static void main(String[] args) {
        try {
            logger.info(">>>>>>>>>>>>>>>>>>>开始收租<<<<<<<<<<<<<<<<<<<<<<");
            // 获取浏览器驱动
            driver = WebAutoUtils.getDriver("chrome", "2.x");
            driver.manage().window().maximize();
            // 获取用户信息
            List<Object> userList = ExcelUtil.readExcel("/login.xlsx", 0,
                    LoginData.class);
            // 获取主账号列表
            Map<String, LoginData> mainAccountMap = getMainAccountMap(userList);
            // 初始化时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            collectedTime = df.format(new Date());
            // 定时循环收取租金（间隔15min）
            while (true) {
                // 初始化数据
                Set<String> resultSuccess = new HashSet<String>();
                Set<String> resultFail = new HashSet<String>();

                // 合并收租（副账号合并到主账号）
                // 防止漏收，在有可收租金的条件下循环收租
                int num = 0;
                List<LoginData> statisticsFailAccountList = new ArrayList<>();
                List<LoginData> failAccountList = new ArrayList<>();
                while (isGetRent) {
                    isGetRent = false;
                    logger.info("开始第 " + ++num + " 次合并收租");
                    mergeRent2MainAccount(userList, mainAccountMap,
                            statisticsFailAccountList,
                            failAccountList, resultSuccess, resultFail);
                    // 打印漏收的账号信息
                    logger.info("结束第 " + num + " 次合并收租，漏收账号数： " +
                            failAccountList.size());
                    if (failAccountList.size() > 0) {
                        statisticsFailAccountList = failAccountList;
                        String failuerAccountInfo = "";
                        for (LoginData user : failAccountList) {
                            failuerAccountInfo = "【" + user.getAccount() + "】、" +
                                    failuerAccountInfo;
                        }
                        logger.info(failuerAccountInfo);
                    }
                    failAccountList = new ArrayList<>();
                    collectedAccountList = new ArrayList<>();
                }
                // 提取总租金（进入主账号）
                getAllRent(mainAccountMap, resultSuccess, resultFail);

                // 输出提现结果，标记提现成功
                logger.info("应收总数：王新兰=77400,毛小美=14400,王金兰=9000,叶林建=21600,蔡云飞=36000");
                logger.info("本次收租总人数： " + userList.size());
                logger.info("本次收租成功人数： " + resultSuccess.size());
                // 发送邮件(收租开始才发送)
                if (isGetRent) {
                    sendEmail2Users(mainAccountMap, resultSuccess, statisticsFailAccountList);
                }
                logger.info("本次提现失败人数： " + resultFail.size() + " , 说明：具体原因请查看日志信息！");
                String failuerInfo = "";
                if (resultFail != null && resultFail.size() > 0) {
                    for (String str : resultFail) {
                        failuerInfo = "【" + str + "】、" + failuerInfo;
                    }
                    logger.info(failuerInfo);
                }
                // sleep 15min
                Thread.sleep(1000 * 60 * fixedTime);
                // 设置isGetRent = true，开启下一次收租
                isGetRent = true;
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private static void sendEmail2Users(Map<String, LoginData> mainAccountMap,
                                        Set<String> resultSuccess, List<LoginData> statisticsFailAccountList) throws
            MessagingException {
//        if (resultSuccess.size() > 0) {
        String emailStr = PropertiesUtil.getPropertieValue("email.users");
        List<String> adds = Arrays.asList(emailStr.split(","));
        // 组织邮件内容
        String emailContent = "<div>";
        // 总收租数据
        for (String key : mainAccountMap.keySet()) {
            LoginData mainAccount = mainAccountMap.get(key);
            emailContent += mainAccount.getUserName() + " 共收租： " + mainAccount.getMoney() + " ; ";
        }
        emailContent += "</div><br/>";
        // 漏收数据
        if (statisticsFailAccountList.size() > 0) {
            emailContent += "<hr/><div>漏收账号信息：";
            for (LoginData user : statisticsFailAccountList) {
                emailContent += user.getUserName() + "【" + user.getAccount() + "】、";
            }
        }
        emailContent += "</div><br/>";
        MailUtils.sendMail(adds, "中扬联众", emailContent);
//            logger.info("恭喜您，提现成功！");
//        }
    }

    private static void getAllRent(Map<String, LoginData> mainAccountMap, Set<String> resultSuccess, Set<String> resultFail) throws InterruptedException {
        for (String key : mainAccountMap.keySet()) {
            LoginData mainAccount = mainAccountMap.get(key);

            // 用户登录
            login(mainAccount);
            if ("https://agent.apolloyun.com/".equalsIgnoreCase(driver.getCurrentUrl())) {
                logger.info("----------------登录成功-------------------");
                // 获取总租金
                getAllRent(resultSuccess, resultFail, mainAccount);
                // 用户登出
                logout(mainAccount);
            } else {
                resultFail.add(mainAccount.getAccount());
                logger.info("----------------登录失败（请检查账号和密码）-------------------");
            }
        }
    }

    private static void mergeRent2MainAccount(List<Object> userList, Map<String, LoginData>
            mainAccountMap, List<LoginData> statisticsFailAccountList, List<LoginData>
                                                      failAccountList, Set<String> resultSuccess, Set<String> resultFail) throws InterruptedException {
        // 补收账号
        if (statisticsFailAccountList.size() > 0) {
            for (Object obj : statisticsFailAccountList) {
                excuteMergeRent(mainAccountMap, failAccountList, resultSuccess,
                        resultFail, (LoginData) obj);
            }
        } else {
            for (Object obj : userList) {
                excuteMergeRent(mainAccountMap, failAccountList, resultSuccess,
                        resultFail, (LoginData) obj);
            }
        }
    }

    private static void excuteMergeRent(Map<String, LoginData> mainAccountMap, List<LoginData> failAccountList, Set<String> resultSuccess, Set<String> resultFail, LoginData obj) throws InterruptedException {
        LoginData userInfo = obj;
        // 根据主账号列表挑选出副账号
        for (String key : mainAccountMap.keySet()) {
            // 判断组别
            if (userInfo.getUserName().equals(key)) {
                // 获取组别里的主账号
                LoginData mainAccount = mainAccountMap.get(key);
                // 判断非主账号
                if (!userInfo.getAccount().equals(mainAccount.getAccount())) {
                    // 用户登录
                    login(userInfo);
                    if ("https://agent.apolloyun.com/".equalsIgnoreCase(driver.getCurrentUrl())) {
                        logger.info("----------------登录成功-------------------");
                        // 收取租金
                        getRenting(failAccountList, resultSuccess,
                                resultFail, userInfo, mainAccount);
                        // 用户登出
                        logout(userInfo);
                    } else {
                        resultFail.add(userInfo.getAccount());
                        logger.info
                                ("----------------登录失败（请检查账号和密码是否正确" +
                                        "）-------------------");
                    }
                }
            }
        }
    }

    private static void getAllRent(Set<String> resultSuccess, Set<String> resultFail, LoginData mainAccount) throws InterruptedException {
        if ("五星".equalsIgnoreCase(getText(By.xpath("//font")))) {
            logger.info(mainAccount.getAccount() + "是【五星】用户");
            // 每周收租（获取每周租金）
            getRentFromWeek(mainAccount);

            // 判断用户是否进行最后的总租金收取操作
            List<String> nameList = Arrays.asList(PropertiesUtil.getPropertieValue("notget" +
                    ".allrent" +
                    ".users").split(","));
//            for(String name : nameList){
//                logger.info("name = " + name);
//            }
//            logger.info("mainAccount.getUserName() = " + mainAccount.getUserName());
            // 进入【收取】页面
            driver.get("https://agent.apolloyun.com/purse");
            // 获取当前可用租金，输入相应金额、密码
            String moneyStr = getText(By.className("transfer-number"));
            // 记录总租金
            mainAccount.setMoney(moneyStr);
            logger.info(mainAccount.getAccount() + " 收取总租金: " + moneyStr);
            if (!nameList.contains(mainAccount.getUserName())) {
                double money = Double.valueOf(moneyStr);
                if (money > 0) {
                    // 添加提现成功的账号
                    resultSuccess.add(mainAccount.getAccount());
                }
                // 根据总金额提取租金
                while (money > 0) {
                    // 判断总租金是否大于 maxRent
                    if (money > maxRent) {
                        type(By.name("point"), String.valueOf(maxRent));
                        type(By.name("T_WithdrawalsPsw"), mainAccount.getSecondPassword());
                        // 点击【确认】
                        click(By.xpath("//button[contains(text(),'确认')]"));
                    } else {
                        type(By.name("point"), moneyStr);
                        type(By.name("T_WithdrawalsPsw"), mainAccount.getSecondPassword());
                        // 点击【确认】
                        click(By.xpath("//button[contains(text(),'确认')]"));
                    }
                    Thread.sleep(600);
                    money = Double.valueOf(getText(By.className("transfer-number")));
                }
            }
        } else {
            resultFail.add(mainAccount.getAccount());
            logger.info(mainAccount.getAccount() + "不是【五星】用户");
        }
    }

    private static void getRenting(List<LoginData> failAccountList, Set<String>
            resultSuccess, Set<String> resultFail, LoginData userInfo, LoginData mainAccount) throws InterruptedException {
        // 判断是否【五星】用户
        if ("五星".equalsIgnoreCase(getText(By.xpath("//font")))) {
            logger.info(userInfo.getAccount() + "是【五星】用户");
            // 每周收租（获取每周租金）
            getRentFromWeek(userInfo);
            // 将账号租金转入主账号
            mergeRent(failAccountList, resultSuccess, resultFail, userInfo,
                    mainAccount);
            Thread.sleep(600);
        } else {
            resultFail.add(userInfo.getAccount());
            logger.info(userInfo.getAccount() + "不是【五星】用户");
        }
    }

    private static void mergeRent(List<LoginData> failAccountList, Set<String> resultSuccess, Set<String> resultFail, LoginData userInfo, LoginData mainAccount) {
        // 进入【收取(合)】页面
        driver.get("https://agent.apolloyun.com/purse/merge");
        // 获取当前可用租金，输入相应金额、密码
        String moneyStr = getText(By.className("transfer-number"));
        logger.info(userInfo.getAccount() + "(" + userInfo.getUserName() + ") 合并收租: " + moneyStr);
        if (!"0.00".equalsIgnoreCase(moneyStr)) {
            type(By.name("point"), moneyStr);
            type(By.name("T_WithdrawalsPsw"), userInfo.getSecondPassword());
            type(By.name("T_MergeID"), mainAccount.getAccount());
            type(By.name("T_MergeWithdrawalsPsw"), mainAccount.getSecondPassword());
            // 点击【确认】
            click(By.xpath("//button[contains(text(),'确认')]"));
            // 添加提现成功的账号
            resultSuccess.add(userInfo.getAccount());
        } else {
            // 收集漏收的账号信息
            if (!collectedAccountList.contains(userInfo)) {
                failAccountList.add(userInfo);
            }
        }
    }

    /**
     * @Description: 获取主账号列表
     * @Param: [userList]
     * @return: java.util.Map<java.lang.String, com.zhongyang.web.pojo.LoginData>
     * @Author: Adam
     * @Date: 2020/3/18
     */
    private static Map<String, LoginData> getMainAccountMap(List<Object> userList) {
        Map<String, LoginData> mainAccountMap = new LinkedHashMap<>();
        for (Object item : userList) {
            LoginData user = (LoginData) item;
            if (!mainAccountMap.containsKey(user.getUserName())) {
                mainAccountMap.put(user.getUserName(), user);
            }
        }
        return mainAccountMap;
    }

    /**
     * @Description: 用户登录
     * @Param: [userInfo]
     * @return: void
     * @Author: Adam
     * @Date: 2020/3/18
     */
    private static void login(LoginData userInfo) throws InterruptedException {
        logger.info("================== 账号：" + userInfo.getAccount() + " " +
                "开始提现==================");
        // 登录网址每周收租成功
        driver.get("https://agent.apolloyun.com/login");
        // 输入用户名和密码
        type(By.name("T_Email"), userInfo.getAccount());
        type(By.name("password"), userInfo.getPassword());
        // 点击【登录】
        click(By.xpath("//button[contains(text(),'登录')]"));
        Thread.sleep(300);
    }

    /**
     * @Description: 用户退出
     * @Param: [userInfo]
     * @return: void
     * @Author: Adam
     * @Date: 2020/3/18
     */
    private static void logout(LoginData userInfo) {
        logger.info("----------------开始【退出登录】-------------------");
        // 点击账号->点击【退出登录】
        click(By.xpath("//*[@id=\"pageWrapper\"]/div[1]/div[2]/div[4]/button"));
        click(By.xpath("//a[contains(text(),'退出登陆')]"));
        logger.info("================== 账号：" + userInfo.getAccount() + "结束提现==================");
    }

    public static void main1(String[] args) throws MessagingException {
        // 获取用户信息
//        List<Object> userList = ExcelUtil.readExcel("/login.xlsx", 0,
////                LoginData.class);
////        for(Object item: userList){
////            LoginData user = (LoginData)item;
////            System.out.println(user);
////        }
//        MailUtils.sendMail("378532514@qq.com,2027708942@qq.com", "中扬联众", "测试成功！");
    }

    /**
     * @Description: 每周收租
     * @Param: [userInfo]
     * @return: void
     * @Author: Adam
     * @Date: 2020/3/18
     */
    private static void getRentFromWeek(LoginData userInfo) throws InterruptedException {
        logger.info("----------------开始每周收租-------------------");
        // 进入【每周收租】
        driver.get("https://agent.apolloyun.com/earning");
        // 选择最后一页
        List<WebElement> itemList = getElementList(By.className("page-item"));
        int itemSize = itemList.size();
        logger.info("itemSize = " + itemSize);
        if (itemSize >= 2) {
            // 滚动最底部
//            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
            (itemList.get(itemSize - 2)).click();
        }

        // 判断是否已经被收取
        List<WebElement> trlist = getElementList(By.xpath("//*/table/tbody/tr"));
        if (trlist != null && trlist.size() > 0) {
            String trText = trlist.get(trlist.size() - 1).getText();
            if (trText != null || trText != "") {
                // 当前已经可以开始收租
                if (trText.indexOf(collectedTime) != -1) {
                    // 判断是否已经收租
                    if (trText.indexOf(collectedTime) != trText.lastIndexOf(collectedTime)) {
                        collectedAccountList.add(userInfo);
                    }
                }
            } else {
                logger.error("无法获取trText! " + userInfo.getAccount() + " " + userInfo.getUserName());
            }
        } else {
            collectedAccountList.add(userInfo);
        }
        // 判断是否有【确认】字样
        List<WebElement> itemBtList = getElementList(By.className("profit-button"));
        /*if(itemBtList.size() > 0){
            WebElement button = itemBtList.get(itemBtList.size() - 1);
            button.click();
            logger.info("lastBtText = " + button.getText());
        }*/
        // 进行遍历收租
        for (WebElement button : itemBtList) {
            if ("确认".equalsIgnoreCase(button.getText())) {
                button.click();
                logger.info(userInfo.getAccount() + "每周收租成功！");
                // 判断是否有租金可收
                isGetRent = true;
            }
        }
        Thread.sleep(500);
        logger.info("----------------结束每周收租-------------------");
    }

    @Test(dataProvider = "dp")
    public void testWebSite() throws InterruptedException {

    }

    @Override
    protected String getCurrentPageName() {
        return null;
    }

}
