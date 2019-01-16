package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.OrgAmount;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CasePartyMapper;
import com.ksource.liangfa.mapper.CaseTodoMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.service.casehandle.CaseCompanyService;
import com.ksource.liangfa.service.casehandle.CaseTodoService;
import com.ksource.liangfa.service.casehandle.CasepartyService;
import com.ksource.liangfa.service.system.OrgAmountService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;
/**
 * 行政立案菜单控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/casehandle/caseTodoBuChongDiaoCha")
public class CaseTodoBuChongDiaoChaController {

	@Autowired
	private CaseBasicMapper caseBasicMapper;
	@Autowired
	private CaseTodoMapper caseTodoMapper;
	@Autowired
	private CasePartyMapper casePartyMapper;
	@Autowired
	private CaseTodoService caseTodoService;
	@Autowired
	private CasepartyService casepartyService;
	@Autowired
	private CaseCompanyService caseCompanyService;
	@Autowired
	private OrgAmountService orgAmountService;
	@Autowired
	private CaseCompanyMapper caseCompanyMapper;
	@Autowired
	private DistrictMapper districtMapper;
	/**
	 * list
	 */
	@RequestMapping("list")
	public String getCaseTodoLianlist(HttpServletRequest request,ModelMap modelMap,
			CaseTodo caseTodo,String page){
		User user = SystemContext.getCurrentUser(request);
		Integer assignOrg = user.getOrgId();
		caseTodo.setAssignOrg(assignOrg);
		//默认查询非移送管辖案件
		caseTodo.setIsTurnover(Const.IS_TURNOVER_NO);
		//只查询补充调查的案件
		caseTodo.setCaseStateFlag(Const.CHUFA_PROC_8);
		PaginationHelper<CaseTodo> caseTodoList = caseTodoService.find(caseTodo, page);
		modelMap.addAttribute("caseTodoList", caseTodoList);
		modelMap.addAttribute("page",page);
    	//不为空时，则查询预警信息
    	for(CaseTodo cb:caseTodoList.getList()){
    		//涉案当事人历史案件预警
    		List<CaseParty> wranCasepartyList = casepartyService.queryHistoryBySameOrgAndIdsNO(cb.getCaseId());
    		if(wranCasepartyList != null && !wranCasepartyList.isEmpty()){
    			cb.getWarnMap().put("warnCaseParty", wranCasepartyList);
    		}
    		//涉案单位历史案件预警
    		List<CaseCompany> wranCaseCompanyList = caseCompanyService.queryHistoryBySameOrgAndRegNo(cb.getCaseId());
    		if(wranCaseCompanyList != null && !wranCaseCompanyList.isEmpty()){
    			cb.getWarnMap().put("warnCaseCompany", wranCaseCompanyList);
    		}
    		//涉案金额预警
    		OrgAmount orgAmount = orgAmountService.queryAmountByCaseInputer(cb.getCaseId());
    		if(orgAmount != null && cb.getAmountInvolved()!=null &&  cb.getAmountInvolved()!=null && cb.getAmountInvolved() > orgAmount.getAmountInvolved() && orgAmount.getAmountInvolved() != 0.00){
    			Double beyondAmount = cb.getAmountInvolved() - orgAmount.getAmountInvolved();
    			cb.getWarnMap().put("beyondAmount", beyondAmount);
    			cb.getWarnMap().put("orgAmount", orgAmount.getAmountInvolved());
    		}
    	}
    	return "casehandle/caseTodoBuChongDiaoCha/caseTodoBuChongDiaoChaList";
	}
    
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
