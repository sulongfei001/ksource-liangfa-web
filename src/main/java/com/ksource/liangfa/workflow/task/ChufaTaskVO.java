package com.ksource.liangfa.workflow.task;

import java.util.List;
import java.util.Map;

import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import org.activiti.engine.task.Task;

import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CasePartyMapper;
import com.ksource.liangfa.mapper.OrgAmountMapper;
import com.ksource.syscontext.SpringContext;

/**
 *处罚案件任务：<br>
 *@author gengzi
 *@data 2012-3-16
 */
public class ChufaTaskVO extends TaskVO {
	private List<CaseParty> wranCasepartyList;
	private List<CaseCompany> wranCaseCompanyList;
	
	public ChufaTaskVO(Task taskInfo, ProcKey procKey, String procBusinessKey,String userId,Map<String,Object> paraMap) {
		super(taskInfo, procKey, procBusinessKey,userId,paraMap);
		//增加处罚案件预警（个人库或企业库关联）
		if(procBusinessEntity!=null){
		
		CasePartyMapper casePartyMapper = SpringContext.getApplicationContext().getBean(CasePartyMapper.class);
		CaseCompanyMapper caseCompanyMapper = SpringContext.getApplicationContext().getBean(CaseCompanyMapper.class);
		OrgAmountMapper orgAmountMapper = SpringContext.getApplicationContext().getBean(OrgAmountMapper.class);
		wranCasepartyList = casePartyMapper.queryHistoryBySameOrgAndIdsNO(this.procBusinessEntity.getBusinessKey());
		if(wranCasepartyList!=null && wranCasepartyList.size()>0){
			this.wranList.add(warnType_CASE_PARTY_WARN);
		}
		wranCaseCompanyList = caseCompanyMapper.queryHistoryBySameOrgAndRegNo(this.procBusinessEntity.getBusinessKey());
		if(wranCaseCompanyList!=null && wranCaseCompanyList.size()>0){
			this.wranList.add(warnType_CASE_COMPANY_WARN);
		}
		if((wranCasepartyList==null || wranCasepartyList.size()==0) && (wranCaseCompanyList==null || wranCaseCompanyList.size()==0)){
			this.wranList.add(warnType_CASE_NORMAL);
		}
		CaseBasic tempCase = null;
		if(procBusinessEntity instanceof CaseBasic){
			tempCase= (CaseBasic)procBusinessEntity;
		}
		OrgAmount orgAmount = orgAmountMapper.queryAmountByCaseInputer(this.procBusinessEntity.getBusinessKey());
		if(orgAmount==null){
			this.wranList.add(warnType_AMOUNT_NORMAL);
		}else if(tempCase.getAmountInvolved()>orgAmount.getAmountInvolved() && orgAmount.getAmountInvolved() != 0.00){
			this.amountInvolved = tempCase.getAmountInvolved() - orgAmount.getAmountInvolved();
			this.orgAmount = orgAmount.getAmountInvolved();
			this.wranList.add(warnType_AMOUNT_WARN);
		}else{
			this.wranList.add(warnType_AMOUNT_NORMAL);
		}
		
		}
	}
	@Override
	protected void initProcBusinessEntity(Map<String, Object> map) {
		CaseBasicMapper caseMapper = SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
		this.procBusinessEntity=caseMapper.queryProcBusinessEntity(map);
	}
	
	
	public List<CaseParty> getWranCasepartyList() {
		return wranCasepartyList;
	}
	public void setWranCasepartyList(List<CaseParty> wranCasepartyList) {
		this.wranCasepartyList = wranCasepartyList;
	}
	public List<CaseCompany> getWranCaseCompanyList() {
		return wranCaseCompanyList;
	}
	public void setWranCaseCompanyList(List<CaseCompany> wranCaseCompanyList) {
		this.wranCaseCompanyList = wranCaseCompanyList;
	}
	
}
