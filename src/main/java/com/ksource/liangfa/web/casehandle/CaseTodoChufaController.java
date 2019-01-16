package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseCompanyExample;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CasePartyExample;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.CrimeCaseForm;
import com.ksource.liangfa.domain.OrgAmount;
import com.ksource.liangfa.domain.PenaltyCaseForm;
import com.ksource.liangfa.domain.PenaltyLianForm;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.XingzhengCancelLianForm;
import com.ksource.liangfa.domain.XingzhengNotLianForm;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CasePartyMapper;
import com.ksource.liangfa.mapper.CaseTodoMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.service.casehandle.CaseCompanyService;
import com.ksource.liangfa.service.casehandle.CaseTodoService;
import com.ksource.liangfa.service.casehandle.CasepartyService;
import com.ksource.liangfa.service.system.OrgAmountService;
import com.ksource.liangfa.workflow.ActivitiUtil;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;
/**
 * 行政处罚菜单控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/casehandle/caseTodoChufa")
public class CaseTodoChufaController {

	private static final String REDIRECT_CASETODOCHUFA_LIST = "redirect:/casehandle/caseTodoChufa/list";
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
		//只查询行政立案案件
		caseTodo.setCaseStateFlag(Const.CHUFA_PROC_1);
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
    	return "casehandle/caseTodoChufa/caseTodoChufaList";
	}
	
	/**
	 * 行政处罚转发
	 */
	@RequestMapping("getPunishView")
    public String getPunishView(HttpServletRequest request,ModelMap modelMap,
    		CaseTodo caseTodo){
    	modelMap.put("caseTodo", caseTodo);
    	//将party和company信息加入到map中
    	findPartyAndCompany(modelMap, caseTodo.getCaseId());
    	return "casehandle/caseTodoChufa/caseTodoChufaPunishView";
    }
	
	/**
	 * 行政处罚提交
	 */
	@RequestMapping("savePenalty")
    public ModelAndView savePenalty(MultipartRequest multipartRequest,HttpServletRequest request,
    		CaseBasic caseBasic2,PenaltyCaseForm penaltyCaseForm){
    	String path = REDIRECT_CASETODOCHUFA_LIST;
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.addPenaltyCase(caseBasic2,penaltyCaseForm,multipartRequest,user,request);
    	ModelAndView mvAndView = new ModelAndView(path);
    	return mvAndView;
    }
	
	/**
	 * 将查询到的caseParty和caseCompany放入modelMap中
	 * @param modelMap
	 * @param caseId
	 */
	private void findPartyAndCompany(ModelMap modelMap,String caseId){
		//查询caseParty信息
		CasePartyExample casePartyExample = new CasePartyExample();
		casePartyExample.createCriteria().andCaseIdEqualTo(caseId);
		List<CaseParty> casePartyList = casePartyMapper.selectByExample(casePartyExample);
		//casePartyList至少含有一个空的caseparty，则前台页面至少有一个空的嫌疑人表单
		if(casePartyList.size()==0){
			casePartyList.add(new CaseParty());
		}
		modelMap.put("casePartyList", casePartyList);
		//查询caseCompany信息
		CaseCompanyExample caseCompanyExample = new CaseCompanyExample();
		caseCompanyExample.createCriteria().andCaseIdEqualTo(caseId);
		List<CaseCompany> companyList = caseCompanyMapper.selectByExample(caseCompanyExample);
		if(companyList.size()==0){
			companyList.add(new CaseCompany());
		}
		modelMap.put("companyList", companyList);
	}
	
    
    //行政机关案件移送管辖turn over
    @RequestMapping("saveTurnOver")
    @ResponseBody
    public ServiceResponse saveTurnOver(HttpServletRequest request,String caseId,Integer orgCode){
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.turnOver(user,caseId,orgCode);
    	return res;
    }
    
    
    //移送司法转发页面（移送公安）
    @RequestMapping("getMovePoliceView")
    public String getMovePoliceView(CaseTodo caseTodo1,ModelMap modelMap){
    	//根据caseId查询待办案件详细信息
		String caseId = caseTodo1.getCaseId();
		CaseBasic caseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
		modelMap.addAttribute("caseBasic",caseBasic);
		findPartyAndCompany(modelMap, caseId);
    	return "casehandle/caseTodoChufa/caseTodoChufaMovePoliceView";
    }
    
    //移送司法表单提交
    @RequestMapping("saveCrimeCase")
    public ModelAndView saveCrimeCase(MultipartRequest multipartRequest,
    		HttpServletRequest request,CaseBasic caseBasic,CrimeCaseForm crimeCaseForm,String markup){
    	String path = REDIRECT_CASETODOCHUFA_LIST;
    	User user = SystemContext.getCurrentUser(request);
    	//流程变量集合
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ActivitiUtil.VAR_ORG_CODE, crimeCaseForm.getAcceptOrg());
    	ServiceResponse res = caseTodoService.addCrimeCase(multipartRequest,user,caseBasic,crimeCaseForm,map);
    	ModelAndView mvAndView = new ModelAndView(path);
    	return mvAndView;
    }
    
    //行政撤案转发页面
    @RequestMapping("getCancelLianView")
    public String getCancelLianView(HttpServletRequest request,ModelMap modelMap,
    		CaseTodo caseTodo){
    	modelMap.put("caseTodo", caseTodo);
    	return "casehandle/caseTodoChufa/caseTodoChufaCancelLianView";
    }
    //行政撤案表单提交
    @RequestMapping("saveXingzhengCancelLianForm")
    public ModelAndView saveXingzhengCancelLianForm(MultipartRequest multipartRequest,HttpServletRequest request,
    		CaseBasic caseBasic2,XingzhengCancelLianForm xingzhengCancelLianForm,String markup){
    	String path = REDIRECT_CASETODOCHUFA_LIST;
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.xingzhengCancelLianForm(multipartRequest,user,caseBasic2,xingzhengCancelLianForm);
    	ModelAndView mvAndView = new ModelAndView(path);
    	return mvAndView;
    }
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
