package com.zhongyang.base;

import com.zhongyang.base.pojo.CellData;
import com.zhongyang.base.utils.*;
import com.zhongyang.base.pojo.ApiCaseDetail;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestCase {

    private static Logger logger = Logger.getLogger(TestCase.class);

    @BeforeSuite
    public void initData() {
        logger.info("初始化数据（从数据查询最大的手机号码）");
        // 数据初始化（数据库查询最大的手机号）
        String getMaxPhoneSqlStr = "SELECT MAX(mobile_phone) as max_phone FROM member;";
        List<LinkedHashMap<String, Object>> maxPhoneData = DbUtils.excuteQuery
                (getMaxPhoneSqlStr);
        if (maxPhoneData.size() > 0) {
            // 获取最大的手机号
            LinkedHashMap<String, Object> maxPhoneMap = maxPhoneData.get(0);
            if(maxPhoneMap.get("max_phone") != null){
                String maxPhone = maxPhoneMap.get("max_phone").toString();
                // 获取到数据库最大的手机号 + 1
                Long mobilePhone = Long.parseLong(maxPhone) + 1;
                // 添加全局数据
                ParamUtils.addGlobalData("mobilePhone", mobilePhone);
                ParamUtils.addGlobalData("pwd", "12345678");
            }
        }
    }

    /**
     * 数据提供者
     *
     * @return object[][]
     */
    @DataProvider
    public static Object[][] apiCaseData() {
        Object[][] dataList = ApiUtils.getData();
        return dataList;
    }

    /**
     * 测试
     *
     * @param apiCaseDetail
     */
    @Test(dataProvider = "apiCaseData")
    public void testCase1(ApiCaseDetail apiCaseDetail) {
        // 前置数据库验证
        sqlCheckerUtils.checkBeforeSql(apiCaseDetail);
        // 实际的响应数据
        String actualResult = HttpUtil.excute(apiCaseDetail);
        // 将写回数据保存到数据池
        ApiUtils.setCellData(new CellData(apiCaseDetail.getRowNo(), 4,
                actualResult));
        // 提取数据
        ApiUtils.extractData(actualResult, apiCaseDetail);
        // 后置数据库验证
        sqlCheckerUtils.checkAfterSql(apiCaseDetail);
        // 断言
        AssertUtil.assertResKeyInfo(apiCaseDetail, actualResult);
    }

    @AfterSuite
    public void writeExcel() {
        // 将数据整合起来
        Map<String, Object> excelMapList = new HashMap<String, Object>();
        excelMapList.put("0", ApiUtils.getCellDataList());
        excelMapList.put("2", ApiUtils.getCellSqlDataList());
        // 批量把测试结果写回Excel
        ExcelUtil.batchWriteExcel(ApiUtils.filePath, ApiUtils.targetFilePath,
                excelMapList);
    }

    public static void main(String[] args) {

        System.out.println("Hello World!");
    }

}
