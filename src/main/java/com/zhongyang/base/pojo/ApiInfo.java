package com.zhongyang.base.pojo;

public class ApiInfo extends ExcelObject {
    //	ApiId	ApiName	Url	Type	Headers
    private String apiId;
    private String apiName;
    private String url;
    private String type;
    private String headers;
    private String auth;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "ApiInfo{" +
                "apiId='" + apiId + '\'' +
                ", apiName='" + apiName + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", headers='" + headers + '\'' +
                ", auth='" + auth + '\'' +
                "} " + super.toString();
    }
}
