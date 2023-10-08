package com.jn.test.bean;

public class CaseInfo {
    private String caseNo;
    private String caseTitle;
    private String apiMethod;
    private String apiHeader;
    private String apiUrl;
    private String inputInfo;
    private String expected;
    private String checked;

    public CaseInfo() {
    }

    public CaseInfo(String caseNo, String caseTitle, String apiMethod, String apiHeader, String apiUrl, String inputInfo, String expected, String checked) {
        this.caseNo = caseNo;
        this.caseTitle = caseTitle;
        this.apiMethod = apiMethod;
        this.apiHeader = apiHeader;
        this.apiUrl = apiUrl;
        this.inputInfo = inputInfo;
        this.expected = expected;
        this.checked = checked;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getApiHeader() {
        return apiHeader;
    }

    public void setApiHeader(String apiHeader) {
        this.apiHeader = apiHeader;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getInputInfo() {
        return inputInfo;
    }

    public void setInputInfo(String inputInfo) {
        this.inputInfo = inputInfo;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "CaseInfo{" +
                "caseNo='" + caseNo + '\'' +
                ", caseTitle='" + caseTitle + '\'' +
                ", apiMethod='" + apiMethod + '\'' +
                ", apiHeader='" + apiHeader + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", inputInfo='" + inputInfo + '\'' +
                ", expected='" + expected + '\'' +
                ", checked='" + checked + '\'' +
                '}';
    }
}
