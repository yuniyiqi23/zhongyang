package com.zhongyang.web.utils;/**
 * Created by TW on 2019/10/31 19:04
 */


import com.zhongyang.base.pojo.ExcelObject;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

/**
 * @program: JavaAutoTest
 * @description: 数据提供工具类
 * @author: liu yan
 * @create: 2019-10-31 19:04
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

    public static void main(String[] args) throws InterruptedException {
        long current = System.currentTimeMillis();    //当前时间毫秒数
        long zeroT = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();  //今天零点零分零秒的毫秒数
        long endT = zeroT + 24 * 60 * 60 * 1000 - 1;  //今天23点59分59秒的毫秒数
        String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endT);
        System.out.println(end);
        while (true){
            current = System.currentTimeMillis();    //当前时间毫秒数
            System.out.println(current);
            while (current > endT) {
                System.out.println("开始执行！");
            }
            Thread.sleep(1000 * 3);
        }
    }
}
