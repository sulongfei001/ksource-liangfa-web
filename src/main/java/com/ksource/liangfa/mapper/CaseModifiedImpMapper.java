package com.ksource.liangfa.mapper;

import java.util.List;

import com.ksource.liangfa.domain.CaseAccuseKey;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CaseProcess;
import com.ksource.liangfa.domain.CaseState;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CrimeCaseExt;
import com.ksource.liangfa.domain.FieldInstance;
import com.ksource.liangfa.domain.FormInstance;
import com.ksource.liangfa.domain.PenaltyReferCaseExt;
import com.ksource.liangfa.domain.XianyirenAccuse;


public interface CaseModifiedImpMapper {
	/**
	 * 导入案件保存到临时表
	 * @param record
	 * @return
	 */
	int insertCaseBasicTemp(CaseBasic record);
	
	/**
	 * 导入案件状态保存到临时表
	 * @param record
	 * @return
	 */
	int insertCaseStateTemp(CaseState record);
	
	/**
	 * 导入案件流程信息保存到临时表
	 * @param record
	 * @return
	 */
	int insertCaseProcessTemp(CaseProcess CaseProcess);
	
	/**
	 * 导入涉嫌犯罪扩展属性保存到临时表
	 * @param record
	 * @return
	 */
	int insertCrimeCaseExtTemp(CrimeCaseExt record);
	
	/**
	 * 导入移送行政违法扩展属性保存到临时表
	 * @param record
	 * @return
	 */
	int insertPenaltyReferCaseExtTemp(PenaltyReferCaseExt record);
	
	/**
	 * 导入案件罪名保存到临时表
	 * @param record
	 * @return
	 */
	int insertCaseAccuseTemp(CaseAccuseKey record);
	
	/**
	 * 导入案件当事人信息保存到临时表
	 * @param record
	 * @return
	 */
	int insertCasePartyTemp(CaseParty record);
	
	/**
	 * 导入案件企业信息保存到临时表
	 * @param record
	 * @return
	 */
	int insertCaseCompanyTemp(CaseCompany record);
	
	/**
	 * 导入案件嫌疑人保存到临时表
	 * @param record
	 * @return
	 */
	int insertCaseXianYiRenTemp(CaseXianyiren record);
	
	/**
	 * 导入案件嫌疑人罪名保存到临时表
	 * @param record
	 * @return
	 */
	int insertXianYiRenAccuseTemp(XianyirenAccuse record);
	
	/**
	 * 导入案件处理步骤保存到临时表
	 * @param record
	 * @return
	 */
	int insertCaseStepTemp(CaseStep record);
	
	/**
	 * 导入案件附件信息保存到临时表
	 * @param record
	 * @return
	 */
	int insertCaseAttachmentTemp(CaseAttachment record);
	
	/**
	 * 导入案件表单字段保存到临时表
	 * @param record
	 * @return
	 */
	int insertFieldInstanceTemp(FieldInstance record);
	
	/**
	 * 导入案件任务表单保存到临时表
	 * @param record
	 * @return
	 */
	int insertFormInstanceTemp(FormInstance record);
	
	/**
	 * 导入案件
	 * @param record
	 * @return
	 */
	int insertCaseBasic();
	
	
	/**
	 * 导入案件状态
	 * @param record
	 * @return
	 */
	int insertCaseState();
	
	/**
	 * 导入案件流程信息
	 * @param record
	 * @return
	 */
	int insertCaseProcess();
	
	/**
	 * 导入涉嫌犯罪扩展属性
	 * @param record
	 * @return
	 */
	int insertCrimeCaseExt();
	
	/**
	 * 导入行政处罚扩展属性
	 * @param record
	 * @return
	 */
	//int insertPenaltyCaseExt();
	
	/**
	 * 导入移送行政违法扩展属性
	 * @param record
	 * @return
	 */
	int insertPenaltyReferCaseExt();
	
	/**
	 * 导入案件罪名
	 * @param record
	 * @return
	 */
	int insertCaseAccuse();
	
	/**
	 * 导入案件当事人信息
	 * @param record
	 * @return
	 */
	int insertCaseParty();
	
	/**
	 * 导入案件企业信息
	 * @param record
	 * @return
	 */
	int insertCaseCompany();
	
	/**
	 * 导入案件嫌疑人
	 * @param record
	 * @return
	 */
	int insertCaseXianYiRen();
	
	/**
	 * 导入案件嫌疑人罪名
	 * @param record
	 * @return
	 */
	int insertXianYiRenAccuse();
	
	/**
	 * 导入案件处理步骤
	 * @param record
	 * @return
	 */
	int insertCaseStep();
	
	/**
	 * 导入案件附件信息
	 * @param record
	 * @return
	 */
	int insertCaseAttachment();
	
	/**
	 * 导入案件表单字段
	 * @param record
	 * @return
	 */
	int insertFieldInstance();
	
	/**
	 * 导入案件任务表单
	 * @param record
	 * @return
	 */
	int insertFormInstance();
	
	
	/**
	 * 删除案件
	 * @param record
	 * @return
	 */
	int deleteCaseBasic(String ids);
	
	
	/**
	 * 删除案件状态
	 * @param record
	 * @return
	 */
	int deleteCaseState(String ids);
	
	/**
	 * 删除案件流程信息
	 * @param record
	 * @return
	 */
	int deleteCaseProcess(String ids);
	
	/**
	 * 删除涉嫌犯罪扩展属性
	 * @param record
	 * @return
	 */
	int deleteCrimeCaseExt(String ids);
	
	/**
	 * 删除行政处罚扩展属性
	 * @param record
	 * @return
	 */
	int deletePenaltyCaseExt(String ids);
	
	/**
	 * 删除移送行政违法扩展属性
	 * @param record
	 * @return
	 */
	int deletePenaltyReferCaseExt(String ids);
	
	/**
	 * 删除案件罪名
	 * @param record
	 * @return
	 */
	int deleteCaseAccuse(String ids);
	
	/**
	 * 删除案件当事人信息
	 * @param record
	 * @return
	 */
	int deleteCaseParty(String ids);
	
	/**
	 * 删除案件企业信息
	 * @param record
	 * @return
	 */
	int deleteCaseCompany(String ids);
	
	/**
	 * 删除案件嫌疑人
	 * @param record
	 * @return
	 */
	int deleteCaseXianYiRen(String ids);
	
	/**
	 * 删除案件嫌疑人罪名
	 * @param record
	 * @return
	 */
	int deleteXianYiRenAccuse(String ids);
	
	/**
	 * 删除案件处理步骤
	 * @param record
	 * @return
	 */
	int deleteCaseStep(String ids);
	
	/**
	 * 删除案件附件信息
	 * @param record
	 * @return
	 */
	int deleteCaseAttachment(String ids);
	
	/**
	 * 删除案件表单字段
	 * @param record
	 * @return
	 */
	int deleteFieldInstance(String ids);
	
	/**
	 * 删除案件任务表单
	 * @param record
	 * @return
	 */
	int deleteFormInstance(String ids);
	
	/**
	 * 查找删除的案件编号
	 * @param ids
	 * @return
	 */
	List<String> queryDeleteCaseNo(String ids);
	
	/**
	 * 查找删除的案件附件的URL
	 * @param ids
	 * @return
	 */
	List<String> queryCaseFileUrl(String ids);
	
}