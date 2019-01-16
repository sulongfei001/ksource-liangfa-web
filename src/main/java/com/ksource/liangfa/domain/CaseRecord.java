package com.ksource.liangfa.domain;

/**
 * Created with IntelliJ IDEA.
 * User: zxl
 * Date: 13-4-2
 * Time: 下午2:08
 * To change this template use File | Settings | File Templates.
 */
public class CaseRecord {
    private String userId;
    private String procKey;
    private String caseId;
    private int readState;

    public int getReadState() {
		return readState;
	}

	public void setReadState(int readState) {
		this.readState = readState;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProcKey() {
        return procKey;
    }

    public void setProcKey(String procKey) {
        this.procKey = procKey;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}