package com.lemon.web.utils;

import com.lemon.web.AutoGetMoney;
import com.lemon.web.testcase.login.LoginFailureData;
import com.zhongyang.utils.ParamUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    private static Logger logger = Logger.getLogger(ExcelUtil.class);


    public static List<Object> readExcel(String filePath, int sheetNo,
                                         Class objectClass) {
        List<Object> objectList = null;
        try {
            // 获取excel文件输入流
            InputStream isStream = ExcelUtil.class
                    .getResourceAsStream(filePath);
            // 创建工作簿对象
            Workbook workbook = WorkbookFactory.create(isStream);
            // 读取表单对象
            Sheet sheet = (Sheet) workbook.getSheetAt(sheetNo);
            // 读取第一行
            Row firstRow = sheet.getRow(0);
            int lastCellnum = firstRow.getLastCellNum();
            // System.out.println("lastCellnum = " + lastCellnum);
            // 用数组存储对象字段
            String[] fields = new String[lastCellnum];
            objectList = new ArrayList<Object>();
            // 读取第一列的数据
            for (int i = 0; i < lastCellnum; i++) {
                Cell cell = firstRow.getCell(i, Row.CREATE_NULL_AS_BLANK);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String valueString = cell.getStringCellValue();
                fields[i] = valueString;
                System.out.print(valueString + " ");
            }
//            System.out.println("--------------------------");
            // 确定行数
            int lastRowNum = sheet.getLastRowNum();
//			System.out.println("lastRowNum = " + lastRowNum);
            for (int i = 1; i <= lastRowNum; i++) {
                // 创建一个对象来保存数据
                Object object = objectClass.newInstance();
//                Class clazz = objectClass;
                // 设置行号（索引值）
                int rowNo = i;
                String setRowNoMethodName = "setRowNo";
                Method setRowNoMethod = objectClass.getMethod(setRowNoMethodName, int
                        .class);
                // 调用方法
                setRowNoMethod.invoke(object, rowNo);

                // 获取当前行
                Row row = sheet.getRow(i);
                // 读取数据
                for (int j = 0; j < lastCellnum; j++) {
                    Cell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
                    cell.setCellType(Cell.CELL_TYPE_STRING);// CELL_TYPE_STRING
                    // 设置字符串枚举类型
                    String cellValue = cell.getStringCellValue();

                    // 根据${}先进行参数替换Excel里面的数据
                    cellValue = ParamUtils.getReplaceStr(cellValue);

                    // 使用反射
                    String methodName = "set"
                            + (fields[j].charAt(0) + "").toUpperCase()
                            + fields[j].substring(1);
                    Method method = objectClass.getMethod(methodName, String.class);
                    method.invoke(object, cellValue);
                    // System.out.print(methodName + " ");
                }
//				System.out.println(object);
                objectList.add(object);
            }
            // }
            return objectList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] arg) throws Exception {
        List<Object> results = readExcel("/login.xlsx", 0,
                LoginFailureData.class);
        for (Object obj : results) {
            logger.info(obj);
        }
    }
}
