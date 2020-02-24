package com.zhongyang.utils;/**
 * Created by TW on 2019/9/18 21:16
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhongyang.pojo.ApiCaseDetail;
import com.zhongyang.pojo.CellData;
import com.zhongyang.pojo.sqlChecker;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @program: JavaAutoTest
 * @description: 数据库验证
 * @author: liu yan
 * @create: 2019-09-18 21:16
 */
public class sqlCheckerUtils {

    private static Logger logger = Logger.getLogger(sqlCheckerUtils.class);

    /**
     * @Description: 前置sql验证
     * @Param: [apiCaseDetail]
     * @return: void
     * @Author: Adam
     * @Date: 2019/9/18
     */
    public static void checkBeforeSql(ApiCaseDetail apiCaseDetail) {
        doCheckSql(apiCaseDetail, 1);
    }

    /**
     * @Description: 后置sql验证
     * @Param: [apiCaseDetail]
     * @return: void
     * @Author: Adam
     * @Date: 2019/9/18
     */
    public static void checkAfterSql(ApiCaseDetail apiCaseDetail) {
        doCheckSql(apiCaseDetail, 2);
    }

    private static void doCheckSql(ApiCaseDetail apiCaseDetail, int type) {
        List<sqlChecker> sqlCheckerList = null;
        if (type == 1) {
            logger.info("前置SQL验证：" + apiCaseDetail.getCaseId());
            sqlCheckerList = apiCaseDetail.getBeforeCheckSql();
        } else if (type == 2) {
            logger.info("后置SQL验证：" + apiCaseDetail.getCaseId());
            sqlCheckerList = apiCaseDetail.getAfterCheckSql();
        }
        if (sqlCheckerList == null) {
            return;
        }
        for (com.zhongyang.pojo.sqlChecker sqlChecker : sqlCheckerList) {
            List<LinkedHashMap<String, Object>> actualResult = DbUtils.excuteQuery
                    (sqlChecker.getSql());
            String expected = sqlChecker.getExpected();
            logger.info("期望结果：" + expected);
            String actual = JSONObject.toJSONString(actualResult);
            logger.info("实际结果：" + actual);
            // 将结果数据保存到数据池
            CellData cellData = new CellData(sqlChecker.getRowNo(), 5,
                    actual);
            ApiUtils.setCellSqlData(cellData);
            // 将测试是否通过保存到数据池
            if (expected.equalsIgnoreCase(actual)) {
                ApiUtils.setCellSqlData(new CellData(sqlChecker.getRowNo(), 6,
                        "通过"));
                logger.info("通过！");
            } else {
                ApiUtils.setCellSqlData(new CellData(sqlChecker.getRowNo(), 6,
                        "不通过"));
                logger.info("不通过！");
            }
//            System.out.println("期望 : " + expected);
//            System.out.println("实际 : " + actual);
        }
    }
}
