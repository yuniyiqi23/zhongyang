package com.zhongyang.base.utils;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.zhongyang.base.pojo.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口的工具类
 */
public class ApiUtils {

    // TODO 配置文件读取文件路径
    // 测试用例文件路径
    public static String filePath = "/test_case_01.xlsx";
    // 测试用例执行结果文件路径
    public static String targetFilePath = "d://SVN//TestResult.xlsx";
    // 创建一个数据池，保存回写Excel数据
    private static List<CellData> cellDataList = new ArrayList<CellData>();
    // 数据库执行结果回写到Excel
    private static List<CellData> cellSqlDataList = new ArrayList<CellData>();
    private static Logger logger = Logger.getLogger(ApiUtils.class);

    public static List<CellData> getCellSqlDataList() {
        return cellSqlDataList;
    }

    public static void setCellSqlData(CellData cellData) {
        ApiUtils.cellSqlDataList.add(cellData);
    }

    public static List<CellData> getCellDataList() {
        return cellDataList;
    }

    public static void setCellData(CellData cellData) {
        ApiUtils.cellDataList.add(cellData);
    }

    /** 
    * @Description: 从Excel中获取数据
    * @Param: [] 
    * @return: java.lang.Object[][] 
    * @Author: Adam
    * @Date: 2019/9/21 
    */
    public static Object[][] getData() {
        logger.info("从Excel中获取数据");
        // 从excel获取ApiCaseDetail数据
        List<Object> apiCaseDetails = ExcelUtil.readExcel(filePath,
                0, ApiCaseDetail.class);
        // 从excel获取ApiInfo数据
        List<Object> apiInfos = ExcelUtil.readExcel(filePath, 1,
                ApiInfo.class);
        // 从excel获取前置和后置sql列表
        List<Object> sqlCheckList = ExcelUtil.readExcel(filePath, 2,
                sqlChecker.class);
        // 将apiInfos组装成map，便于后面的封装
        Map<String, ApiInfo> apiInfoMap = new HashMap<String, ApiInfo>();
        for (Object obj : apiInfos) {
            apiInfoMap.put(((ApiInfo) obj).getApiId(), (ApiInfo) obj);
        }
        // 将sqlCheckList组装成map，便于后面的封装
        Map<String, List<sqlChecker>> mapSql = new HashMap<String,
                List<sqlChecker>>();
        for (Object sqlObject : sqlCheckList) {
            sqlChecker sqlChecker = (sqlChecker) sqlObject;
            // 测试用例的id拼接上type
            String key = sqlChecker.getCaseId() + "-" + sqlChecker.getType();
            List<sqlChecker> checkerList = mapSql.get(key);
            if(checkerList == null){
                checkerList = new ArrayList<sqlChecker>();
            }
            // 添加到对应的数组
            checkerList.add(sqlChecker);
            mapSql.put(key, checkerList);
        }

        //初始化二维数组长度
        Object[][] dataList = new Object[apiCaseDetails.size()][];
        for (int i = 0; i < apiCaseDetails.size(); i++) {
            //获取ApiCaseDetail对象
            ApiCaseDetail apiDetail = (ApiCaseDetail) apiCaseDetails.get(i);
            // 将ApiInfo数据封装到ApiCaseDetail中
            apiDetail.setApiInfo(apiInfoMap.get(apiDetail.getApiId()));
            // 将BeforeCheckSql封装到ApiCaseDetail中
            String beforeKey = apiDetail.getCaseId() + "-bf";
            apiDetail.setBeforeCheckSql(mapSql.get(beforeKey));
            // 将AfterCheckSql封装到ApiCaseDetail中
            String afterKey = apiDetail.getCaseId() + "-af";
            apiDetail.setAfterCheckSql(mapSql.get(afterKey));

            //将数组转化成数组格式
            Object[] data = {apiDetail};
            dataList[i] = data;
            // System.out.println(data);
        }
        return dataList;
    }

    /** 
    * @Description: 提取数据
    * @Param: [] 
    * @return: void 
    * @Author: Adam
    * @Date: 2019/9/21 
    */
    public static void extractData(String actualResult, ApiCaseDetail
            apiCaseDetail){
        String extractResp = apiCaseDetail.getExtractResp();
        if(extractResp != null){
            // string转容器
            List<ExtractRespInfo> extractList = JSONObject.parseArray(extractResp,
                    ExtractRespInfo.class);
            if(extractList == null){
                return;
            }
            // 解析实际接口调用结果
            Object doc = Configuration.defaultConfiguration().jsonProvider().parse(actualResult);
            for (ExtractRespInfo item: extractList){
                // 通过jsonpath获取key和value
                String jsonPath = item.getJsonPath();
                Object actual = JsonPath.read(doc, jsonPath);
                // 设置到全局数据池中
                ParamUtils.addGlobalData(item.getParamName(), actual);
            }
        }
    }

}
