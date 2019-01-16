package com.ksource.liangfa.model;

/**
 * Created with IntelliJ IDEA.
 * User: zxl
 * Date: 13-2-1
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
public class CaseStepKey {
    protected String platformId;	//平台注册ID
    protected Long stepId;		//处理步骤ID

    public CaseStepKey() {
        super();
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }
}
