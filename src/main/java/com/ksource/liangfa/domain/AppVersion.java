package com.ksource.liangfa.domain;

import java.util.Date;

public class AppVersion {
    
    private Integer versionId;

    
    private Double versionNo;

    
    private String upgradeUrl;

    
    private String content;

    
    private String createUser;
    
    
    private String createUserName;

    
    private Date createTime;

    
    public Integer getVersionId() {
        return versionId;
    }

    
    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    
    public Double getVersionNo() {
        return versionNo;
    }

    
    public void setVersionNo(Double versionNo) {
        this.versionNo = versionNo;
    }

    
    public String getUpgradeUrl() {
        return upgradeUrl;
    }

    
    public void setUpgradeUrl(String upgradeUrl) {
        this.upgradeUrl = upgradeUrl;
    }

    
    public String getContent() {
        return content;
    }

    
    public void setContent(String content) {
        this.content = content;
    }

    
    public String getCreateUser() {
        return createUser;
    }

    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    
    public Date getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getCreateUserName() {
        return createUserName;
    }


    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    
}