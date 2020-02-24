package com.lemon.web.pojo;/**
 * Created by TW on 2019/11/4 18:55
 */

/**
 *@program: JavaAutoTest
 *@description: 页面元素定位
 *@author: liu yan
 *@create: 2019-11-04 18:55
 */
public class Locator {
//    name="手机号码输入框" type="id" value
    private String name;
    private String type;
    private String value;

    public Locator(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Locator{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
