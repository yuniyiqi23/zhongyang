package com.zhongyang;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
        String a = "2020-05-17";
        String str = "d15158015845@163.com 2020-05-17 11:20:43 1800.00 2020-05-17 21:56:11 已确认";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        System.out.println(str.lastIndexOf(a));// new Date()为获取当前系统时间
    }

}
