package com.zhongyang.base.utils;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.zhongyang.base.pojo.ApiCaseDetail;
import com.zhongyang.base.pojo.ExpectedRespKeyInfo;
import org.testng.Assert;


import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * @Description: 断言工具类
 * @Author: Adam Smith
 * @Date: 2019/9/7
 */
public class AssertUtil {

    /**
     * @Description: 断言ResKeyInfo
     * @Param: [apiCaseDetail]
     * @return: void
     * @Author: Adam
     * @Date: 2019/9/7
     */
    public static void assertResKeyInfo(ApiCaseDetail apiCaseDetail, String
            actualResult) {
        Dictionary a = new Hashtable();
        // 获取ResKeyInfo
        String expectedStr = apiCaseDetail.getExpectedRespKeyInfo();
        // expectedStr转List集合
        List<ExpectedRespKeyInfo> expectedList = JSONObject.parseArray
                (expectedStr, ExpectedRespKeyInfo.class);
        // 代码保护
        if (expectedList == null) return;
        // 解析实际接口调用结果
        Object doc = Configuration.defaultConfiguration().jsonProvider().parse(actualResult);
        for (ExpectedRespKeyInfo item : expectedList) {
            String jsonPath = item.getJsonPath();
            Object expected = item.getExpected();
            Object actual = JsonPath.read(doc, jsonPath);
            // 比对结果
            Assert.assertEquals(expected, actual);
        }

    }
}
