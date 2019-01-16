package com.ksource.liangfa.workflow;

import java.util.Date;
import java.util.List;

import com.ksource.liangfa.domain.CaseStep;

/**
 *流程业务实体的默认实现<br>
 *@author gengzi
 *@data 2012-3-17
 */
public class ProcBusinessEntityImpl implements ProcBusinessEntity{

	private String businessKey;
	private String businessName;
	private String procBusinessEntityNO;
	private String initiator;
	private String publishUserName;
	private String publishOrgName;
	private String procKey;
	private String procDefId;
	private String procInstId;
	private String state;
	
	private String procKeyName;
	private Date startTime;
	private Date endTime;
	private List<CaseStep> caseStepList	;
	private String filterResult;
	private String accuseNameStr;
	private String districtCode;
	private Integer yisongState;
	private Integer inputUnit;
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getProcBusinessEntityNO() {
		return procBusinessEntityNO;
	}
	public void setProcBusinessEntityNO(String procBusinessEntityNO) {
		this.procBusinessEntityNO = procBusinessEntityNO;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public String getPublishUserName() {
		return publishUserName;
	}
	public void setPublishUserName(String publishUserName) {
		this.publishUserName = publishUserName;
	}
	public String getPublishOrgName() {
		return publishOrgName;
	}
	public void setPublishOrgName(String publishOrgName) {
		this.publishOrgName = publishOrgName;
	}
	public String getProcKey() {
		return procKey;
	}
	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<CaseStep> getCaseStepList() {
		return caseStepList;
	}
	public void setCaseStepList(List<CaseStep> caseStepList) {
		this.caseStepList = caseStepList;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getProcKeyName() {
		return procKeyName;
	}
	public void setProcKeyName(String procKeyName) {
		this.procKeyName = procKeyName;
	}
	
	private String  caseId;
	
	public String getCaseId() {
		return caseId;
	}
	
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getFilterResult() {
		return filterResult;
	}
	public void setFilterResult(String filterResult) {
		this.filterResult = filterResult;
	}
	
	public String getAccuseNameStr() {
		return accuseNameStr;
	}
	public void setAccuseNameStr(String accuseNameStr) {
		this.accuseNameStr=accuseNameStr;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public Integer getYisongState() {
		return yisongState;
	}
	public void setYisongState(Integer yisongState) {
		this.yisongState = yisongState;
	}
    public Integer getInputUnit() {
        return inputUnit;
    }
    public void setInputUnit(Integer inputUnit) {
        this.inputUnit = inputUnit;
    }
	
}
