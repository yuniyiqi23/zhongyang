package com.zhongyang.pojo;/**
 * Created by TW on 2019/9/12 16:27
 */

/**
 *@program: JavaAutoTest
 *@description: 表格属性
 *@author: liu yan
 *@create: 2019-09-12 16:27
 */
public class CellData {
    private int rowNo; // 行号。从0开始
    private int column; // 列号。从0开始
    private String content;

    public CellData(int rowNo, int column, String content) {
        this.rowNo = rowNo;
        this.column = column;
        this.content = content;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CellData{" +
                "rowNo=" + rowNo +
                ", column=" + column +
                ", content='" + content + '\'' +
                '}';
    }
}
