package com.ksource.liangfa.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class InstantMessage {

    private Integer msgId;

    /**消息类型*/
    private Integer msgType;
    private Integer[] msgTypes;

    private String title;

    private String content;

    private Date createTime;

    private String createUser;
    
    private String createUserName;

    private Long createOrg;
    
    private String receiveUser;

    private String businessKey;

    /**读取状态 1 已读 2 未读*/
    private Integer readStatus;
    
    /**当前类型的消息未读的数量*/
    private Integer noReadSize;
    
    /**消息类型 1：待办案件*/
    public static final Integer MSG_TYPE_CASE = 1;    
    /**消息类型 2：通知公告*/
    public static final Integer MSG_TYPE_NOTICE = 2;
    /**消息类型 3：案件咨询*/
    public static final Integer MSG_TYPE_CONSULATION = 3;    
    /**消息类型 4：工作指令*/
    public static final Integer MSG_TYPE_INSTRUCTION = 4;
    /**消息类型 5：工作汇报*/
    public static final Integer MSG_TYPE_WORG_REPORT = 5;    
    /**消息类型 6：横向交流*/
    public static final Integer MSG_TYPE_COMMUNION = 6;
    /**互动连线*/
    public static final Integer MSG_TYPE_COMMUNION_M = 10;
    public static final Integer[] MSG_TYPE_COMMUNION_MANAGE = {4,5,6};
    /**消息类型 7：线索分派*/
    public static final Integer MSG_TYPE_CLUE_DISPATCH = 7;   
    /**消息类型 8：线索处理*/
    public static final Integer MSG_TYPE_CLUE_HANDLE = 8;
    /**线索处理*/
    public static final Integer[] MSG_TYPE_CLUE_MNAGE = {7,8};
    public static final Integer MSG_TYPE_CLUE_M= 11;
    /**消息类型 9：疑似犯罪案件筛查*/
    public static final Integer MSG_TYPE_CRIME_CASE = 9;
    
    
    
    public InstantMessage() {
		super();
	}
    

	public InstantMessage(Integer msgType) {
		super();
		this.msgType = msgType;
	}


	public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    
    public void setMsgType(Integer msgType) {
    	this.msgType = null;
    	if(msgType == MSG_TYPE_COMMUNION_M){
    		this.msgTypes = MSG_TYPE_COMMUNION_MANAGE;
    	}else if(msgType == MSG_TYPE_CLUE_M){
    		this.msgTypes = MSG_TYPE_CLUE_MNAGE;
    	}else{
    		this.msgType = msgType;
    	}
    }
    public void setMsgType(Integer msgType,boolean b) {
    		this.msgType = msgType;
    		this.msgTypes = null;
    }

    
    public String getTitle() {
        return title;
    }

    
    public void setTitle(String title) {
        this.title = title;
    }

    
    public String getContent() {
        return content;
    }

    
    public void setContent(String content) {
        this.content = content;
    }

    
    public Date getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    public String getCreateUser() {
        return createUser;
    }

    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    
    public Long getCreateOrg() {
        return createOrg;
    }

    
    public void setCreateOrg(Long createOrg) {
        this.createOrg = createOrg;
    }

    
    public String getReceiveUser() {
        return receiveUser;
    }

    
    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    
    public String getBusinessKey() {
        return businessKey;
    }

    
    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    
    public Integer getReadStatus() {
        return readStatus;
    }

    
    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

	public Integer getNoReadSize() {
		return noReadSize;
	}

	public void setNoReadSize(Integer noReadSize) {
		if(noReadSize == null) {
			this.noReadSize = 0;
		}else {
			this.noReadSize = noReadSize;
		}
	}


	public Integer[] getMsgTypes() {
		return msgTypes;
	}


	public void setMsgTypes(Integer[] msgTypes) {
		this.msgTypes = msgTypes;
	}
	
    
    
}