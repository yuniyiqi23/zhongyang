package com.zhongyang.base.pojo;/**
 * Created by TW on 2019/9/12 17:13
 */

/**
 *@program: JavaAutoTest
 *@description: Excel数据对象
 *@author: liu yan
 *@create: 2019-09-12 17:13
 */
public abstract class ExcelObject {
    private int rowNo;

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    @Override
    public String toString() {
        return "ExcelObject{" +
                "rowNo=" + rowNo +
                '}';
    }
}
