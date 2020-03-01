package com.zhongyang.base.utils;/**
 * Created by TW on 2019/9/21 14:52
 */

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: JavaAutoTest
 * @description: 参数提取工具类
 * @author: liu yan
 * @create: 2019-09-21 14:52
 */
public class ParamUtils {

    // 全局数据池
    public static Map<String, Object> globalParamMap = new HashMap<String,
            Object>();

    /** 
    * @Description:  根据key获取全局变量的值
    * @Param: [key] 
    * @return: java.lang.Object 
    * @Author: Adam
    * @Date: 2019/9/21 
    */
    public static Object getGlobalData(String key){
        return globalParamMap.get(key);
    }
    /** 
    * @Description:  设置全局变量
    * @Param: [key, value] 
    * @return: void 
    * @Author: Adam
    * @Date: 2019/9/21 
    */
    public static void addGlobalData(String key, Object value){
        globalParamMap.put(key, value);
    }

    /** 
    * @Description: 替换str方法
    * @Param: [reqStr] 
    * @return: java.lang.String 
    * @Author: Adam
    * @Date: 2019/9/21 
    */
    public static String getReplaceStr(String reqStr) {
//        paramMap.put("mobilePhone", "13188888888");
//        paramMap.put("pwd", "12345678");

        if (reqStr == null) {
            return null;
        } else {
            // 正则表达式
            String regex = "\\$\\{(.*?)\\}";
            // 根据正则表达式创建一个模式对象
            Pattern pattern = Pattern.compile(regex);
            // 对字符串进行匹配
            Matcher matcher = pattern.matcher(reqStr);
            // 循环查找匹配到的字符串
            while (matcher.find()) {
                String totalStr = matcher.group(0);
                String paramName = matcher.group(1);
//            System.out.println(totalStr);
//            System.out.println(paramName);
                Object paramValue = globalParamMap.get
                        (paramName);
                if(paramValue != null){
                    reqStr = reqStr.replace(totalStr, globalParamMap.get
                            (paramName).toString());
                }
            }
            return reqStr;
        }
    }

    public static void main(String[] args) {
        String reqStr = "{ \"mobile_phone\": \"${mobilePhone}\",\"pwd\": \"${pwd}\"}";
        String result = getReplaceStr(reqStr);
        System.out.println(result);
    }

}
