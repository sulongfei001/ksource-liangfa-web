package com.ksource.liangfa.domain;

import java.util.Date;
import java.util.List;

/**
 * 线索回复实体
 * 
 * @author 符家鑫
 */
public class ClueInfoReply {

	// 回复id REPLY_ID
	private Integer replyId;

	// 回复人id CREATE_USER_ID
	private Integer createUserId;

	// 回复时间 CREATE_TIME
	private Date createTime;

	// 执行人
	private String executorName;

	// 执行时间
	private Date executorTime;

	// 线索Id CLUEINFO_ID
	private Integer clueInfoId;
	// 分派线索id
	private Integer dispatchId;

	// 回复内容 CONTENT
	private String content;
	
	//办理案件后的案件id
	private String caseId;

	// 附件列表
	private List<PublishInfoFile> attMents;

	public List<PublishInfoFile> getAttMents() {
		return attMents;
	}

	public void setAttMents(List<PublishInfoFile> attMents) {
		this.attMents = attMents;
	}

	public Integer getReplyId() {
		return replyId;
	}

	public String getExecutorName() {
		return executorName;
	}

	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getClueInfoId() {
		return clueInfoId;
	}

	public void setClueInfoId(Integer clueInfoId) {
		this.clueInfoId = clueInfoId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getExecutorTime() {
		return executorTime;
	}

	public void setExecutorTime(Date executorTime) {
		this.executorTime = executorTime;
	}

	public Integer getDispatchId() {
		return dispatchId;
	}

	public void setDispatchId(Integer dispatchId) {
		this.dispatchId = dispatchId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	private String orgName;
	private String userName;
	private String caseName;
	private String caseNo;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	
	
	
	
}
