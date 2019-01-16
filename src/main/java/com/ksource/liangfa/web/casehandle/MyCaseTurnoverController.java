package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.OrgAmount;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.casehandle.CaseCompanyService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.CasepartyService;
import com.ksource.liangfa.service.system.OrgAmountService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("casehandle/myCaseTurnover")
public class MyCaseTurnoverController {

	private static final String CASE_TURNOVER_LIST="casehandle/myCaseTurnover/myCaseTurnoverList";
	
	@Autowired
	private CaseService caseService;
	@Autowired
	private CasepartyService casepartyService;
	@Autowired
	private CaseCompanyService caseCompanyService;
	@Autowired
	private OrgAmountService orgAmountService;
	
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,
			CaseBasic caseBasic,String page){
		User user = SystemContext.getCurrentUser(request);
		Integer orgId = user.getOrgId();
		ModelAndView mv = new ModelAndView();
		mv.setViewName(CASE_TURNOVER_LIST);
		caseBasic.setIsTurnover(Const.IS_TURNOVER_YES);
		caseBasic.setInputUnit(orgId);
		PaginationHelper<CaseBasic> myCaseTurnoverList = caseService.findMyCaseTurnover(caseBasic, page);
		//不为空时，则查询预警信息
    	for(CaseBasic ct:myCaseTurnoverList.getList()){
    		//涉案当事人历史案件预警
    		List<CaseParty> wranCasepartyList = casepartyService.queryHistoryBySameOrgAndIdsNO(ct.getCaseId());
    		if(wranCasepartyList != null && !wranCasepartyList.isEmpty()){
    			ct.getWarnMap().put("warnCaseParty", wranCasepartyList);
    		}
    		//涉案单位历史案件预警
    		List<CaseCompany> wranCaseCompanyList = caseCompanyService.queryHistoryBySameOrgAndRegNo(ct.getCaseId());
    		if(wranCaseCompanyList != null && !wranCaseCompanyList.isEmpty()){
    			ct.getWarnMap().put("warnCaseCompany", wranCaseCompanyList);
    		}
    		//涉案金额预警
    		OrgAmount orgAmount = orgAmountService.queryAmountByCaseInputer(ct.getCaseId());
    		if(orgAmount != null && ct.getAmountInvolved()!=null &&  ct.getAmountInvolved()!=null && ct.getAmountInvolved() > orgAmount.getAmountInvolved() && orgAmount.getAmountInvolved() != 0.00){
    			Double beyondAmount = ct.getAmountInvolved() - orgAmount.getAmountInvolved();
    			ct.getWarnMap().put("beyondAmount", beyondAmount);
    			ct.getWarnMap().put("orgAmount", orgAmount.getAmountInvolved());
    		}
    	}
    	mv.addObject("myCaseTurnoverList", myCaseTurnoverList);
    	mv.addObject("page", page);
		return mv;
	}
	
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
