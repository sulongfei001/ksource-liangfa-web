package com.ksource.liangfa.service.casehandle;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseAgreeNolian;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseFenpai;
import com.ksource.liangfa.domain.CaseNolianReason;
import com.ksource.liangfa.domain.CaseRequireLian;
import com.ksource.liangfa.domain.CaseRequireNolianReason;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.CaseTurnover;
import com.ksource.liangfa.domain.CrimeCaseForm;
import com.ksource.liangfa.domain.PenaltyCaseForm;
import com.ksource.liangfa.domain.PenaltyLianForm;
import com.ksource.liangfa.domain.DetentionInfo;
import com.ksource.liangfa.domain.SuggestYisongForm;
import com.ksource.liangfa.domain.TransferDetention;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.XingzhengCancelLianForm;
import com.ksource.liangfa.domain.XingzhengJieanForm;
import com.ksource.liangfa.domain.XingzhengNotLianForm;
import com.ksource.liangfa.domain.XingzhengNotPenalty;

@Service
public interface CaseTodoService {

	
	PaginationHelper<CaseTodo> find(CaseTodo caseTodo,String page);

	int getTodoCount(Integer orgCode);
	
	
	//立案表单提交后，插入数据
	ServiceResponse lianAdd(CaseBasic caseBasic,PenaltyLianForm penaltyLianForm,MultipartRequest multipartRequest,User user,HttpServletRequest request);
		
	//行政处罚案件表单提交
	ServiceResponse addPenaltyCase(CaseBasic caseBasic,PenaltyCaseForm penaltyCaseForm,MultipartRequest multipartRequest,
			User user,HttpServletRequest request);
	//移送公安表单提交
	ServiceResponse addCrimeCase(MultipartRequest multipartRequest,
			User user,CaseBasic caseBasic,CrimeCaseForm crimeCaseForm,Map<String, Object> map);
	//检察院建议移送表单提交
	ServiceResponse saveSuggestYisongForm(MultipartRequest multipartRequest,
			SuggestYisongForm suggestYisongForm,User user);
	//行政结案表单提交
	ServiceResponse saveXingzhengJieanForm(MultipartRequest multipartRequest,
			XingzhengJieanForm xingzhengJieanForm,User user);
	//行政案件交办
	void taskFenpai(HttpServletRequest request,String userId,String caseId);
	
	//移送管辖
	ServiceResponse turnOver(User user,String caseId,Integer orgCode);
	//查询行政机关的待办案件
	CaseTodo getTodoCountForXingzheng(Integer orgCode);
	//行政不予立案
	ServiceResponse saveXingzhengNotLianForm(MultipartRequest multipartRequest,
			User user,CaseBasic caseBasic2,XingzhengNotLianForm xingzhengNotLianForm);
	//行政撤案
	ServiceResponse xingzhengCancelLianForm(MultipartRequest multipartRequest,
			User user,CaseBasic caseBasic2,XingzhengCancelLianForm xingzhengCancelLianForm);
	//行政不予处罚
	ServiceResponse xingzhengNotPenalty(MultipartRequest multipartRequest,
			User user, CaseBasic caseBasic,
			XingzhengNotPenalty xingzhengNotPenalty);
	//移送管辖
	ServiceResponse saveTurnOver(MultipartRequest multipartRequest, User user,
			CaseBasic caseBasic, CaseTurnover caseTurnover);

	ServiceResponse saveFenpai(MultipartRequest multipartRequest,
			User user, CaseBasic caseBasic, CaseFenpai caseFenpai, String taskId);
	
	/**
	 * 移送行政拘留信息保存
	 * @param multipartRequest
	 * @param user
	 * @param transferRdetention
	 * @return
	 */
	ServiceResponse saveTransferDetention(MultipartRequest multipartRequest, 
			User user,TransferDetention transferRdetention);

	/**
	 * 查询公安机关行政拘留待办案件数量
	 * @param paramMap
	 * @return
	 */
	int getTransferDetentionTodoCount(Map<String,Object> paramMap);
	
	/**
	 * 行政拘留待办案件查询
	 * @param caseTodo
	 * @param page
	 * @return
	 */
	PaginationHelper<CaseTodo> getTransferDetentionTodoList(CaseTodo caseTodo,String page);
	
	/**
	 * 公安办理行政拘留信息保存
	 * @param multipartRequest
	 * @param user
	 * @param rdetentionInfo
	 * @return
	 */
	ServiceResponse saveDetentionInfo(MultipartRequest multipartRequest, User user,DetentionInfo detentionInfo);
	
	/**
	 * 公安不予拘留信息保存
	 * @param multipartRequest
	 * @param user
	 * @param rdetentionInfo
	 * @return
	 */
	ServiceResponse saveNotDetentionInfo(MultipartRequest multipartRequest, User user,DetentionInfo detentionInfo);
	
	ServiceResponse saveRequireNoLianReason(CaseRequireNolianReason require, User user, MultipartRequest multipartRequest);

	PaginationHelper<CaseTodo> getLianSupTodoList(CaseTodo caseTodo, Map<String, Object> map, String page);

	int getLianSupTodoCount(Map<String, Object> map);

	ServiceResponse saveNolianReason(CaseNolianReason reason, User user, MultipartRequest multipartRequest);

	ServiceResponse saveRequireLian(CaseRequireLian lian, User user, MultipartRequest multipartRequest);

	ServiceResponse saveAgreeNoLian(CaseAgreeNolian agreeNoLian, User user, MultipartRequest multipartRequest);
}
