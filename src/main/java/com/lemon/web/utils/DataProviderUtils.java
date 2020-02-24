package com.lemon.web.utils;/**
 * Created by TW on 2019/10/31 19:04
 */


import com.zhongyang.pojo.ExcelObject;

import java.util.List;

/**
 *@program: JavaAutoTest
 *@description: 数据提供工具类
 *@author: liu yan
 *@create: 2019-10-31 19:04
 */
public class DataProviderUtils {

    /**
    * @Description: 获取数据
    * @Param: [s, i, loginFailDataClass]
    * @return: void
    * @Author: Adam
    * @Date: 2019/10/31
    */
    public static Object[][] getData(String excelPath, int sheetNo,
                               Class<? extends ExcelObject> clazz) {
        // 读取Excel中的数据
        List<Object> results = ExcelUtil.readExcel(excelPath, sheetNo, clazz);
        // 转成二维数组
        Object[][] datas = new Object[results.size()][];
        for (int i = 0; i < results.size(); i++) {
            datas[i] = new Object[]{results.get(i)};
        }
        return datas;
    }
}
