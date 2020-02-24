package com.zhongyang.pojo;/**
 * Created by TW on 2019/9/21 16:51
 */

/**
 *@program: JavaAutoTest
 *@description: 响应数据参数化
 *@author: liu yan
 *@create: 2019-09-21 16:51
 */
public class ExtractRespInfo {
//    [{"jsonPath":"$.data.id","paramName":"member_id"}]

    private String jsonPath;
    private String paramName;

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public String toString() {
        return "ExtractRespInfo{" +
                "jsonPath='" + jsonPath + '\'' +
                ", paramName='" + paramName + '\'' +
                '}';
    }
}
