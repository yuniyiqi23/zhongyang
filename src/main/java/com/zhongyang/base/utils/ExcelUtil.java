package com.zhongyang.base.utils;

import com.zhongyang.base.pojo.ApiInfo;
import com.zhongyang.base.pojo.CellData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExcelUtil {


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
            System.out.println();
            System.out.println("--------------------------");
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

    /**
     * @Description: 批量回写
     * @Param: [sourceExcelPath, targetExcelPath, sheetNo, cellDataList]
     * @return: void
     * @Author: Adam
     * @Date: 2019/9/12
     */
    public static void batchWriteExcel(String sourceExcelPath, String
            targetExcelPath, Map<String, Object> excelMapList) {
        // 获取输入流
        InputStream inputStream = ExcelUtil.class.getResourceAsStream
                (sourceExcelPath);
        Workbook workbook = null;
        OutputStream outputStream = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            if (excelMapList == null) {
                return;
            }
            // 获取keySet
            Set<String> keySet = excelMapList.keySet();
            for (String key : keySet) {
                // 按照sheetNo获取sheet数据
                Sheet sheet = workbook.getSheetAt(Integer.valueOf(key));
                for (CellData cellData : (List<CellData>)excelMapList.get(key)) {
                    Row firstRow = sheet.getRow(cellData.getRowNo());
                    Cell cell = firstRow.getCell(cellData.getColumn(), Row
                            .CREATE_NULL_AS_BLANK);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(cellData.getContent());
                }
            }

            // 创建输出流
            outputStream = new FileOutputStream
                    (targetExcelPath);
            workbook.write(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            close(inputStream, outputStream);
        }

    }

    private static void close(InputStream inputStream, OutputStream outputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] arg) throws Exception {
        List<Object> apiCaseDetails = readExcel("/test_case_02.xlsx", 1,
                ApiInfo.class);
        for (Object obj : apiCaseDetails) {
            System.out.println(obj);
        }
    }
}
