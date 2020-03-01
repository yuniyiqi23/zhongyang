package com.zhongyang.base.pojo;

import java.util.List;

/**
 * @Description: ApiCaseDetail
 * @Author: Adam Smith
 * @Date: 2019/9/7
 */
public class ApiCaseDetail extends ExcelObject {
    //	CaseId	ApiId	RequestData
    private String caseId;
    private String apiId;
    private String requestData;
    private ApiInfo apiInfo;
    private String expectedRespKeyInfo;
    private String ActualResp;
    private List<sqlChecker> beforeCheckSql;
    private List<sqlChecker> afterCheckSql;
    private String ExtractResp;

    public String getExtractResp() {
        return ExtractResp;
    }

    public void setExtractResp(String extractResp) {
        ExtractResp = extractResp;
    }

    public List<sqlChecker> getBeforeCheckSql() {
        return beforeCheckSql;
    }

    public void setBeforeCheckSql(List<sqlChecker> beforeCheckSql) {
        this.beforeCheckSql = beforeCheckSql;
    }

    public List<sqlChecker> getAfterCheckSql() {
        return afterCheckSql;
    }

    public void setAfterCheckSql(List<sqlChecker> afterCheckSql) {
        this.afterCheckSql = afterCheckSql;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    public String getExpectedRespKeyInfo() {
        return expectedRespKeyInfo;
    }

    public void setExpectedRespKeyInfo(String expectedRespKeyInfo) {
        this.expectedRespKeyInfo = expectedRespKeyInfo;
    }

    public String getActualResp() {
        return ActualResp;
    }

    public void setActualResp(String actualResp) {
        ActualResp = actualResp;
    }

    @Override
    public String toString() {
        return "ApiCaseDetail{" +
                "caseId='" + caseId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", requestData='" + requestData + '\'' +
                ", apiInfo=" + apiInfo +
                ", expectedRespKeyInfo='" + expectedRespKeyInfo + '\'' +
                ", ActualResp='" + ActualResp + '\'' +
                ", beforeCheckSql=" + beforeCheckSql +
                ", afterCheckSql=" + afterCheckSql +
                ", ExtractResp='" + ExtractResp + '\'' +
                "} " + super.toString();
    }
}
