package com.ksource.liangfa.web.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseDuty;
import com.ksource.liangfa.domain.CaseWeiji;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.domain.ProcKeyExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 此类为 案件及相关流程删除 控制器
 * 
 * @author Guojianyong
 * @date 2012-4-16
 */
@Controller
@RequestMapping("system/workflowDelete")
public class WorkflowDeleteController {

	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	CaseService caseService;

	private static final String QUERY_VIEW = "system/caseAndworkflow/caseAndworkflow";

	// 删除前先查询出来案件
	@RequestMapping("search")
	public ModelAndView queryCase(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page, String info,
			HttpServletRequest request,HttpSession session) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		ModelAndView mav = new ModelAndView();
		// 为页面下拉列表，获取所有案件类型
		List<ProcKey> procKeyList = mybatisAutoMapperService.selectByExample(
				ProcKeyMapper.class, new ProcKeyExample(), ProcKey.class);
		mav.addObject("procKeyList", procKeyList);
		String procKey = request.getParameter("procKey");
		
		// 无案件类型条件时默认为：处罚案件
		if (StringUtils.isEmpty(procKey)) {
			procKey = Const.CASE_CHUFA_PROC_KEY;
		}
		
		//处理行政区划参数(去除行政区划后两位00,如果没有00，不去除)
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		String shortDistrictCode="";
		if(caseBasic!=null && StringUtils.isNotBlank(caseBasic.getDistrictCode())){
		    if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
		        shortDistrictCode=StringUtils.rightTrim0(caseBasic.getDistrictCode());
		    }else{
		        shortDistrictCode = caseBasic.getDistrictCode();
		    }
		}else{
			shortDistrictCode=StringUtils.rightTrim0(organise.getDistrictCode());
		}
		caseBasic.setDistrictCode(shortDistrictCode);
		
		if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
            caseBasic.setOrgId(null);
        }
		// 处罚案件及移送行政违法案件分页查询
		if (Const.CASE_CHUFA_PROC_KEY.equals(procKey)
				|| Const.CASE_YISONGXINGZHENG_PROC_KEY.equals(procKey)|| Const.CASE_CRIME_PROC_KEY.equals(procKey)) {
			paramMap.put("procKey", procKey);
			caseBasic.setProcKey(procKey);
			
			if (!StringUtils.isEmpty(caseBasic.getCaseNo())) {
				paramMap.put("caseNo", caseBasic.getCaseNo().trim());
			}
			if (!StringUtils.isEmpty(caseBasic.getCaseName())) {
				paramMap.put("caseName", caseBasic.getCaseName().trim());
			}
			PaginationHelper<CaseBasic> caseList = caseService.searchCaseAndWorkflow(caseBasic, page, paramMap);
			mav.addObject("caseList", caseList);
		}
		
		mav.addObject("info", info);
		mav.addObject("procKey", procKey);
		mav.addObject("page", page);
		mav.setViewName(QUERY_VIEW);
		return mav;
	}

	// 根据caseId和案件类型procKey删除案件
	@RequestMapping("delete/{caseId}")
	public ModelAndView deleteCase(@PathVariable String caseId, String page,
			HttpServletRequest request,HttpSession session) {
		String procKey = request.getParameter("procKey");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("caseId", caseId);
		boolean isDelete = this.deleteCase(caseId, procKey);
		String info = "";
		if (isDelete) {
			info = "删除案件及流程成功！";
		} else {
			info = "删除案件及流程失败！";
		}
		return this.queryCase(new CaseBasic(),null,null,page, info, request,session);
	}

	// 根据caseId和procKey批量删除案件
	@RequestMapping("deleteBatch")
	public ModelAndView deleteBatch(String page, HttpServletRequest request,
			String[] check,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String procKey = request.getParameter("procKey");
		int count = 0;
		for (String caseId : check) {
			boolean isDeleted = this.deleteCase(caseId, procKey);
			if (isDeleted) {
				count++;
			}
		}
		String info = "";
		if (check.length - count == 0) {
			info = "成功删除所选数据！";
		} else {
			info = "成功删除" + count + "条数据，有" + (check.length - count) + "条未删除";
		}
		return this.queryCase(new CaseBasic(),null,null,page, info, request,session);
	}

	// 删除案件及流程方法，参数为caseId,prockey
	public boolean deleteCase(String caseId, String procKey) {
		boolean isDelete = false;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("caseId", caseId);
		procKey = "chufaProc";
		paramMap.put("procKey", procKey);
		if (Const.CASE_CHUFA_PROC_KEY.equals(procKey)
				|| Const.CASE_YISONGXINGZHENG_PROC_KEY.equals(procKey)|| Const.CASE_CRIME_PROC_KEY.equals(procKey)) {
			isDelete = caseService.deleteCaseAndWorkflow(caseId,paramMap);
		} 
		return isDelete;
	}
}
