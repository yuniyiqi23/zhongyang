package com.zhongyang.pojo;

public class ExpectedRespKeyInfo {
//    "[{""jsonPath"":""$.code"",""expected"":2},
//    {""jsonPath"":""$.msg"",""expected"":""账号已存在""}]"

    private String jsonPath;
    private Object expected;

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public Object getExpected() {
        return expected;
    }

    public void setExpected(Object expected) {
        this.expected = expected;
    }

    @Override
    public String toString() {
        return "ExpectedRespKeyInfo{" +
                "jsonPath='" + jsonPath + '\'' +
                ", expected=" + expected +
                '}';
    }
}
