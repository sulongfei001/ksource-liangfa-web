package com.ksource.liangfa.workflow;

import java.util.List;

import com.ksource.liangfa.domain.CaseStep;

/**
 *流程业务实体：<br>
 *@author gengzi
 *@data 2012-3-13
 */
public interface ProcBusinessEntity {

	/**
	 * 业务实体ID（如案件ID）
	 * @return
	 */
	String getBusinessKey();
	/**
	 * 业务实体名称（如案件名称）
	 * @return
	 */
	String getBusinessName();
	/**
	 * 业务实体编号（如案件编号）
	 * @return
	 */
	String getProcBusinessEntityNO();
	
	/**
	 * 流程发起者ID
	 * @return
	 */
	String getInitiator();
	/**
	 * 实体（案件）创建人名称
	 */
	String getPublishUserName();
	/**
	 * 实体（案件）创建人机构名称
	 */
	String getPublishOrgName();
	/**
	 * 流程类型
	 */
	String getProcKey();
	/**
	 * 流程定义ID
	 * @return procDefId
	 */
	String getProcDefId();
	/**
	 * 流程定义ID
	 * @param procDefId
	 */
	void setProcDefId(String procDefId);
	/**
	 * 流程实例ID
	 * @return procInstId
	 */
	String getProcInstId();
	/**
	 * 流程实例ID
	 * @param procInstId
	 */
	void setProcInstId(String procInstId);
	/**
	 * 处理状态
	 * @return
	 */
	String getState();
	
	/**
	 * 最后处理时间
	 * @return
	 */
	public List<CaseStep> getCaseStepList() ;
	public void setCaseStepList(List<CaseStep> caseStepList) ;
	
	void setFilterResult(String param);
	String getFilterResult();
	
	String getAccuseNameStr();
	
	void setAccuseNameStr(String accuseNameStr);
	
	String getDistrictCode();
	
	Integer getYisongState();
	
    Integer getInputUnit();
	
}
