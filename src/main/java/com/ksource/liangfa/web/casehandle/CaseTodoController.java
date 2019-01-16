package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseAgreeNolian;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseCompanyExample;
import com.ksource.liangfa.domain.CaseFenpai;
import com.ksource.liangfa.domain.CaseNolianReason;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CasePartyExample;
import com.ksource.liangfa.domain.CaseRequireLian;
import com.ksource.liangfa.domain.CaseRequireNolianReason;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.CaseTurnover;
import com.ksource.liangfa.domain.CrimeCaseForm;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.IllegalSituation;
import com.ksource.liangfa.domain.OrgAmount;
import com.ksource.liangfa.domain.Organise;
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
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CasePartyMapper;
import com.ksource.liangfa.mapper.CaseTodoMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.IllegalSituationMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.casehandle.CaseCompanyService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.CaseTodoService;
import com.ksource.liangfa.service.casehandle.CasepartyService;
import com.ksource.liangfa.service.casehandle.PenaltyCaseFormService;
import com.ksource.liangfa.service.system.OrgAmountService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.workflow.ActivitiUtil;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/casehandle/caseTodo")
public class CaseTodoController {

	private static final String CASETODO_LIST = "casehandle/caseTodo/caseTodoList";
	private static final String CASETODO_LIST_LIAN = "casehandle/caseTodo/caseTodoLianList";
	private static final String CASETODO_LIST_CHUFA = "casehandle/caseTodo/caseTodoChufaList";
	private static final String CASETODO_LIST_BUCHONGDIAOCHA = "casehandle/caseTodo/caseTodoBuChongDiaoChaList";
	private static final String CASETODO_LIST_TURNOVER="casehandle/caseTodo/caseTodoTurnoverList";
	private static final String CASETODO_LIST_SUGGESTYISONG="casehandle/caseTodo/caseTodoSuggestyisongList";
	private static final String CASETODO_LIST_CHUFADONE="casehandle/caseTodo/caseTodoChufadoneList";
	private static final String CASETODOLIAN_AUDIT_VIEW = "casehandle/caseTodo/caseTodoLianAuditView";
	private static final String CASETODO_SUGGEST_YISONG="casehandle/caseTodo/caseTodoSuggestYisong";
	private static final String CASETODO_XINGZHENG_JIEAN="casehandle/caseTodo/caseTodoXingzhengJiean";
	private static final String CASETODO_TURNOVER_VIEW="casehandle/caseTodo/caseTodoTurnoverView";
	/**行政不予立案页面*/
	private static final String CASETODOLIAN_NOT_LIAN_VIEW="casehandle/caseTodo/caseTodoLianNotLianView";
	@Autowired
	private CaseTodoMapper caseTodoMapper;
	@Autowired
	private CaseTodoService caseTodoService;
	@Autowired
	private CaseBasicMapper caseBasicMapper;
	@Autowired
	private CaseAttachmentMapper caseAttachmentMapper;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private PenaltyCaseFormService penaltyCaseFormService;
	@Autowired
	private CasePartyMapper casePartyMapper;
	@Autowired
	private CaseCompanyMapper caseCompanyMapper;
	@Autowired
	private OrganiseMapper organiseMapper;
	@Autowired
	private IllegalSituationMapper illegalSituationMapper;
	@Autowired
	private CasepartyService casepartyService;
	@Autowired
	private CaseCompanyService caseCompanyService;
	@Autowired
	private OrgAmountService orgAmountService;
	@Autowired
	private DistrictMapper districtMapper;
	@Autowired
	private CaseService caseService;
	@Autowired
	private OrgService orgService;
	
	/**
	 * 待办案件列表
	 * @param request
	 * @param modelMap
	 * @param caseTodo
	 * @param page
	 * @return
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,ModelMap modelMap,
			CaseTodo caseTodo,String page){
		String path = "";
		String caseStateFlag = caseTodo.getCaseStateFlag();
		User user = SystemContext.getCurrentUser(request);
		Integer assignOrg = user.getOrgId();
		caseTodo.setAssignOrg(assignOrg);
		Integer isTurnover = caseTodo.getIsTurnover();
		if(isTurnover==null){
			//默认查询非移送管辖案件
			caseTodo.setIsTurnover(Const.IS_TURNOVER_NO);
		}
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
    	//行政区划级别为2的才有分派选项
    	Integer orgId = user.getOrgId();
    	String districtCode = organiseMapper.selectByPrimaryKey(orgId).getDistrictCode();
    	District district = districtMapper.selectByPrimaryKey(districtCode);
    	Integer Jb = district.getJb();
    	modelMap.addAttribute("Jb", Jb);
    	if(caseStateFlag == null){
    		path = CASETODO_LIST;//待办案件页面
    	}else if(caseStateFlag.equals("0")){
    		if(caseTodo.getIsTurnover()==0){
    			path = CASETODO_LIST_LIAN;//行政立案
    		}else{
    			path = CASETODO_LIST_TURNOVER;//移送受理
    		}
    	}else if(caseStateFlag.equals("1")){
    		path = CASETODO_LIST_CHUFA;//行政处罚
    	}else if(caseStateFlag.equals("8")){
    		path = CASETODO_LIST_BUCHONGDIAOCHA;//补充调查
    	}else if(caseStateFlag.equals("9")){
    		path = CASETODO_LIST_SUGGESTYISONG;//建议移送菜单
    	}else if(caseStateFlag.equals("2")){
    		path = CASETODO_LIST_CHUFADONE;//已作出处罚
    	}
		return path;
	}
	
	//行政立案菜单
	@RequestMapping("caseTodoLianList")
	public String getCaseTodoLianlist(HttpServletRequest request,ModelMap modelMap,
			CaseTodo caseTodo,String page){
		User user = SystemContext.getCurrentUser(request);
		Integer assignOrg = user.getOrgId();
		caseTodo.setAssignOrg(assignOrg);
		//默认查询非移送管辖案件
		caseTodo.setIsTurnover(Const.IS_TURNOVER_NO);
		//只查询行政受理案件
		caseTodo.setCaseStateFlag(Const.CHUFA_PROC_0);
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
    	}
    	//行政区划级别为2的才有分派选项
    	String districtCode = user.getOrganise().getDistrictCode();
    	District district = districtMapper.selectByPrimaryKey(districtCode);
    	Integer Jb = district.getJb();
    	modelMap.addAttribute("Jb", Jb);
    	return "casehandle/caseTodo/caseTodoLianList";
	}
	
	
	/**
	 * 行政立案跳转
	 */
	@RequestMapping("auditView")
	public String getAuditView(HttpServletRequest request,CaseTodo caseTodo1,
			ModelMap modelMap,String info){
		//根据caseId查询待办案件详细信息
		String caseId = caseTodo1.getCaseId();
		CaseBasic caseBasic = caseBasicMapper.getAudit(caseId);
		modelMap.addAttribute("caseBasic",caseBasic);
		//根据caseId找到对应的taskId 
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("caseId", caseId);
		CaseTodo paramCaseTodo = caseTodoMapper.getCaseTodoList(paramMap).get(0);
		String taskId = paramCaseTodo.getTaskId();
		modelMap.addAttribute("taskId", taskId);
		modelMap.addAttribute("info", info);
		findPartyAndCompany(modelMap, caseId);
		return CASETODOLIAN_AUDIT_VIEW;
	}
	
	
	/**
	 * 行政立案提交
	 */
	@RequestMapping("saveLian")
	public ModelAndView saveLian(MultipartRequest multipartRequest,HttpServletRequest request,
			CaseBasic caseBasic2,PenaltyLianForm penaltyLianForm){
		//String path = REDIRECT_CASETODOLIAN_LIST;
		String path = "redirect:/casehandle/caseTodo/auditView?caseId="+penaltyLianForm.getCaseId();
		User user = SystemContext.getCurrentUser(request);
		//caseState表 caseBasic attachment附件表
		ServiceResponse res = caseTodoService.lianAdd(caseBasic2,penaltyLianForm,multipartRequest,user,request);
		String info = "";
		if (res.getResult()) {
			info = "true";
        } else {
        	info = "false";
        }   
		path += "&info="+info;
		ModelAndView mvAndView = new ModelAndView(path);
		return mvAndView;
	}
		
	//移送管辖跳转页面
    @RequestMapping("caseTurnOverView")
    public String caseTurnOverView(HttpServletRequest request,ModelMap modelMap,
    		CaseTodo caseTodo,String info,String mark){
    	CaseBasic caseBasic = caseService.findCaseById(caseTodo.getCaseId());
    	modelMap.put("caseBasic", caseBasic);
    	modelMap.put("info", info);
    	modelMap.put("mark", mark);
    	return "casehandle/caseTodo/caseTodoYisongGuanxiaView";
    }
    
    //分派页面跳转
    @RequestMapping("caseFenpaiView")
    public String caseFenpaiView(HttpServletRequest request,ModelMap modelMap,
    		CaseTodo caseTodo,String info,String taskId){
    	User user = SystemContext.getCurrentUser(request);
    	Organise organise = user.getOrganise();
    	String orgType = organise.getOrgType();
    	CaseBasic caseBasic = caseService.findCaseById(caseTodo.getCaseId());
    	modelMap.put("caseBasic", caseBasic);
    	modelMap.put("info", info);
    	modelMap.put("taskId", taskId);
    	modelMap.put("orgType", orgType);
    	return "casehandle/caseTodo/caseTodoFenpaiView";
    }
    
    //行政机关案件移送管辖turn over
    @RequestMapping("saveTurnOver")
    public ModelAndView saveTurnOver(MultipartRequest multipartRequest,HttpServletRequest request,
    		CaseBasic caseBasic,CaseTurnover caseTurnover,String mark){
    	String path = "redirect:/casehandle/caseTodo/caseTurnOverView?caseId="+caseTurnover.getCaseId();
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.saveTurnOver(multipartRequest,user,caseBasic,caseTurnover);
    	String info = "";
    	if (res.getResult()) {
    		info = "true";
    	} else {
    		info = "false";
    	}   
    	path += "&info="+info+"&mark="+mark;
    	ModelAndView mvAndView = new ModelAndView(path);
    	return mvAndView;
    }
    
    //案件分派表单提交
    @RequestMapping("saveFenpai")
    public ModelAndView saveFenpai(MultipartRequest multipartRequest,HttpServletRequest request,
    		CaseBasic caseBasic,CaseFenpai caseFenpai,String taskId){
    	String path = "redirect:/casehandle/caseTodo/caseFenpaiView?caseId="+caseFenpai.getCaseId();
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.saveFenpai(multipartRequest,user,caseBasic,caseFenpai,taskId);
    	String info = "";
    	if (res.getResult()) {
    		info = "true";
    	} else {
    		info = "false";
    	}   
    		path += "&info="+info;
    	ModelAndView mvAndView = new ModelAndView(path);
    	return mvAndView;
    }
    
    
    /**
     * 不予立案跳转
     * @param request
     * @param modelMap
     * @param paramCaseBasic
     * @param info 不予立案保存后重定向到此方法，在页面上根据这个值提示
     * @return
     */
    @RequestMapping("getNotLianView")
    public String getNotLianView(HttpServletRequest request,ModelMap modelMap,
    		CaseBasic paramCaseBasic,String info){
    	String caseId = paramCaseBasic.getCaseId();
    	CaseBasic caseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
    	modelMap.addAttribute("caseBasic", caseBasic);
    	modelMap.addAttribute("info", info);
    	return CASETODOLIAN_NOT_LIAN_VIEW;
    }
    
    /**
     * 行政不予立案提交
     * @param multipartRequest
     * @param request
     * @param caseBasic2
     * @param xingzhengNotLianForm
     * @return
     * 表单提交后重定向到不予立案方法
     */
    @RequestMapping("saveXingzhengNotLian")
	public ModelAndView saveXingzhengNotLian(MultipartRequest multipartRequest,HttpServletRequest request,
			CaseBasic caseBasic2,XingzhengNotLianForm xingzhengNotLianForm){
    	String path = "redirect:/casehandle/caseTodo/getNotLianView?caseId="+xingzhengNotLianForm.getCaseId();
		User user = SystemContext.getCurrentUser(request);
		//caseState表 caseBasic attachment附件表
		ServiceResponse res = caseTodoService.saveXingzhengNotLianForm(multipartRequest,user,caseBasic2,xingzhengNotLianForm);
		String info = "";
		if (res.getResult()) {
			info = "true";
        } else {
        	info = "false";
        }   
		path += "&info="+info;
		ModelAndView mvAndView = new ModelAndView(path);
		return mvAndView;
	}
    
    /**
     * 
     * @param caseTodo1
     * @param modelMap
     * @param markup 从哪个列表页面移送公安的标记
     * @param info 移送公安是否保存成功
     * @param skipPath 移送公安保存成功后跳转的路径
     * @return
     */
    //移送司法跳转页面（移送公安）
    @RequestMapping("getMovePoliceView")
    public String getMovePoliceView(CaseTodo caseTodo1,ModelMap modelMap,String markup,String info){
    	//根据caseId查询待办案件详细信息
		String caseId = caseTodo1.getCaseId();
		CaseBasic caseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
		modelMap.addAttribute("caseBasic",caseBasic);
		modelMap.addAttribute("markup",markup);
		modelMap.addAttribute("info",info);
		findPartyAndCompany(modelMap, caseId);
		if(info != null){
			String skipPath = "";
			if(markup.equals("lian")){
				skipPath = "/casehandle/caseTodo/caseTodoLianList";
	    	}else if(markup.equals("chufa")){
	    		skipPath = "/casehandle/caseTodo/caseTodoChufaList";
	    	}else if(markup.equals("suggestyisong")){
	    		skipPath = "/casehandle/caseTodo/suggestYisongList";
	    	}else if(markup.equals("chufadone")){
	    		skipPath = "/casehandle/caseTodo/chufaDoneList";
	    	}
			modelMap.addAttribute("skipPath",skipPath);
		}
    	return "casehandle/caseTodo/caseTodoMovePoliceView";
    }
    
    //移送司法表单提交
    /**
     * path变量传到getMovePoliceView（）方法，再传到页面
     * 再页面上保存成功后跳转的路径
     * @param multipartRequest
     * @param request
     * @param caseBasic
     * @param crimeCaseForm
     * @param markup
     * @return
     */
    @RequestMapping("saveCrimeCase")
    public ModelAndView saveCrimeCase(MultipartRequest multipartRequest,
    		HttpServletRequest request,CaseBasic caseBasic,CrimeCaseForm crimeCaseForm,String markup){
    	User user = SystemContext.getCurrentUser(request);
    	//流程变量集合
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ActivitiUtil.VAR_ORG_CODE, crimeCaseForm.getAcceptOrg());
    	ServiceResponse res = caseTodoService.addCrimeCase(multipartRequest,user,caseBasic,crimeCaseForm,map);
    	String info="";
    	if (res.getResult()) {
			info = "true";
        } else {
        	info = "false";
        }   
		String policePath = "redirect:/casehandle/caseTodo/getMovePoliceView?caseId="+crimeCaseForm.getCaseId();
		policePath+= "&info="+info+"&markup="+markup;
		ModelAndView mvAndView = new ModelAndView(policePath);
    	return mvAndView;
    }
    
    //TODO:行政处罚
    @RequestMapping("caseTodoChufaList")
	public String getCaseTodoChufaList(HttpServletRequest request,ModelMap modelMap,
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
    	return "casehandle/caseTodo/caseTodoChufaList";
	}
    /**
	 * 行政处罚跳转
	 * info:行政处罚表单保存后的提示信息
	 */
	@RequestMapping("getPunishView")
    public String getPunishView(HttpServletRequest request,ModelMap modelMap,
    		CaseTodo caseTodo,String info){
    	modelMap.put("caseTodo", caseTodo);
    	modelMap.put("info", info);
    	//将party和company信息加入到map中
    	findPartyAndCompany(modelMap, caseTodo.getCaseId());
    	return "casehandle/caseTodo/caseTodoChufaPunishView";
    }
    
	/**
	 * 行政处罚提交
	 */
	@RequestMapping("savePenalty")
    public ModelAndView savePenalty(MultipartRequest multipartRequest,HttpServletRequest request,
    		CaseBasic caseBasic2,PenaltyCaseForm penaltyCaseForm,TransferDetention transferDetention){
		//链接里的参数caseId先留着，防止重定向后使用caseId报异常
    	String path = "redirect:/casehandle/caseTodo/getPunishView?caseId="+penaltyCaseForm.getCaseId();
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res=new ServiceResponse();
    	if(caseBasic2.getChufaState()==2){
    		//行政处罚案件保存
    		res = caseTodoService.addPenaltyCase(caseBasic2,penaltyCaseForm,multipartRequest,user,request);
    	}else if(caseBasic2.getChufaState()==1){
    		//移送行政拘留信息保存
    		res = caseTodoService.saveTransferDetention(multipartRequest,user,transferDetention);
    	}
    	
    	String info = "";
		if (res.getResult()) {
			info = "true";
        } else {
        	info = "false";
        }   
		path += "&info="+info;
    	ModelAndView mvAndView = new ModelAndView(path);
    	return mvAndView;
    }
	
	//行政撤案跳转页面
    @RequestMapping("getCancelLianView")
    public String getCancelLianView(HttpServletRequest request,ModelMap modelMap,
    		CaseTodo caseTodo,String info){
    	modelMap.put("caseTodo", caseTodo);
    	modelMap.put("info", info);
    	return "casehandle/caseTodo/caseTodoChufaCancelLianView";
    }
    //行政撤案表单提交
    @RequestMapping("saveXingzhengCancelLianForm")
    public ModelAndView saveXingzhengCancelLianForm(MultipartRequest multipartRequest,HttpServletRequest request,
    		CaseBasic caseBasic2,XingzhengCancelLianForm xingzhengCancelLianForm,String markup){
    	String path = "redirect:/casehandle/caseTodo/getCancelLianView?caseId="+xingzhengCancelLianForm.getCaseId();
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.xingzhengCancelLianForm(multipartRequest,user,caseBasic2,xingzhengCancelLianForm);
    	String info = "";
		if (res.getResult()) {
			info = "true";
        } else {
        	info = "false";
        }   
		path += "&info="+info;
    	ModelAndView mvAndView = new ModelAndView(path);
    	return mvAndView;
    }
    
    
    //行政不予处罚跳转页面
	@RequestMapping("getCaseNotPenalty")
	public String getCaseNotPenalty(HttpServletRequest request,
			ModelMap modelMap, CaseTodo caseTodo, String info) {
		modelMap.put("caseTodo", caseTodo);
		modelMap.put("info", info);
		return "casehandle/caseTodo/caseTodoNotPenaltyView";
	}
    
	//保存行政不予处罚表单
    @RequestMapping("saveXingzhengNotPenalty")
    public ModelAndView saveXingzhengNotPenalty(MultipartRequest multipartRequest,HttpServletRequest request,
    		CaseBasic caseBasic,XingzhengNotPenalty xingzhengNotPenalty,String markup){
    	String path = "redirect:/casehandle/caseTodo/getCaseNotPenalty?caseId="+xingzhengNotPenalty.getCaseId();
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.xingzhengNotPenalty(multipartRequest,user,caseBasic,xingzhengNotPenalty);
    	String info = "";
    	if (res.getResult()) {
    		info = "true";
    	} else {
    		info = "false";
    	}   
    	path += "&info="+info;
    	ModelAndView mvAndView = new ModelAndView(path);
    	return mvAndView;
    }
    
    
    //TODO:补充调查          caseTodoBuChongDiaoChaList
    @RequestMapping("buChongDiaoChaList")
	public String getCaseTodoBuChongDiaoChaList(HttpServletRequest request,ModelMap modelMap,
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
    	return "casehandle/caseTodo/caseTodoBuChongDiaoChaList";
	}
    
    //TODO:建议移送
    @RequestMapping("suggestYisongList")
	public String getcaseTodoSuggestYisongList(HttpServletRequest request,ModelMap modelMap,
			CaseTodo caseTodo,String page){
		User user = SystemContext.getCurrentUser(request);
		Integer assignOrg = user.getOrgId();
		caseTodo.setAssignOrg(assignOrg);
		//默认查询非移送管辖案件
		caseTodo.setIsTurnover(Const.IS_TURNOVER_NO);
		caseTodo.setCaseStateFlag(Const.CHUFA_PROC_9);
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
    	return "casehandle/caseTodo/caseTodoSuggestYisongList";
	}
    //TODO:已作出处罚列表
    //已作出处罚
    @RequestMapping("chufaDoneList")
	public String getcaseTodoChufaDoneList(HttpServletRequest request,ModelMap modelMap,
			CaseTodo caseTodo,String page){
		User user = SystemContext.getCurrentUser(request);
		Integer assignOrg = user.getOrgId();
		caseTodo.setAssignOrg(assignOrg);
		//默认查询非移送管辖案件
		caseTodo.setIsTurnover(Const.IS_TURNOVER_NO);
		//只查询补充调查的案件
		caseTodo.setCaseStateFlag(Const.CHUFA_PROC_2);
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
    	return "casehandle/caseTodo/caseTodoChufaDoneList";
	}
    
    //TODO:移送管辖列表
    @RequestMapping("turnoverList")
	public String getcaseTodoTurnoverList(HttpServletRequest request,ModelMap modelMap,
			CaseTodo caseTodo,String page){
		User user = SystemContext.getCurrentUser(request);
		Integer assignOrg = user.getOrgId();
		caseTodo.setAssignOrg(assignOrg);
		//查询移送管辖案件
		caseTodo.setIsTurnover(Const.IS_TURNOVER_YES);
		//只有刚移送的案件才可以移送管辖
		caseTodo.setCaseStateFlag(Const.CHUFA_PROC_27);
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
    	return "casehandle/caseTodo/caseTodoTurnoverList";
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
		modelMap.put("casePartyList", casePartyList);
		//查询caseCompany信息
		CaseCompanyExample caseCompanyExample = new CaseCompanyExample();
		caseCompanyExample.createCriteria().andCaseIdEqualTo(caseId);
		List<CaseCompany> companyList = caseCompanyMapper.selectByExample(caseCompanyExample);
		modelMap.put("companyList", companyList);
	}
	
    /*//行政处罚跳转页面
    @RequestMapping("punishAdd")
    public String punishAdd(HttpServletRequest request,ModelMap modelMap,
    		CaseTodo caseTodo,String markup){
    	modelMap.put("caseTodo", caseTodo);
    	//将party和company信息加入到map中
    	findPartyAndCompany(modelMap, caseTodo.getCaseId());
    	modelMap.put("markup", markup);
    	return CASETODO_PUNISH_ADD;
    }*/
    
    //查询当前用户的违法情形信息
    private void getIllEaglSituationList(HttpServletRequest request,ModelMap model){
    	//查询当前用户的违法情形信息
        User user = SystemContext.getCurrentUser(request);
		Organise o= organiseMapper.selectByPrimaryKey(user.getOrgId());
		Map<String, Object> map1 = new HashMap<String, Object>();
		List<IllegalSituation> list=null;
		if(o.getIndustryType()!=null){
			map1.put("industryType", o.getIndustryType());
			list=illegalSituationMapper.selectByOrgCode(map1);
		}
		Map<Integer,List<IllegalSituation>> situationMap=new HashMap<Integer,List<IllegalSituation>>();
		int index=0;
		if(list!=null){
			for(int i=0;i<list.size();i++){
				List<IllegalSituation> items=new ArrayList<IllegalSituation>();
				if(i/2==index){
					items.add(list.get(i));
					if(situationMap.get(index)!=null){
						situationMap.get(index).add(list.get(i));
					}else{
						situationMap.put(index, items);
					}
				}else{
					index= i/2;
					items.add(list.get(i));
					situationMap.put(index, items);
				}
			}
		}
		
		if(list!=null && list.size()>0){
			model.addAttribute("situationMap", situationMap);
		}
    }
    /*//行政处罚表单提交
    @RequestMapping("penaltySave")
    public ModelAndView penaltySave(MultipartRequest multipartRequest,HttpServletRequest request,
    		CaseBasic caseBasic2,PenaltyCaseForm penaltyCaseForm,String markup){
    	String path = REDIRECT_CASETODO_LIST;
    	if(markup.equals("chufa")){
			path+="?caseStateFlag=1";
		}
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.addPenaltyCase(multipartRequest,user,caseBasic2,penaltyCaseForm);
    	ModelAndView mvAndView = new ModelAndView(path);
		mvAndView.addObject("tabid","行政处罚");
    	return mvAndView;
    }*/
    
    /*//案件审查步骤的行政撤案跳转页面
    @RequestMapping("cancelLian")
    public String cancelLianView(HttpServletRequest request,ModelMap modelMap,
    		CaseTodo caseTodo,String markup){
    	modelMap.put("caseTodo", caseTodo);
    	modelMap.put("markup", markup);
    	return CASETODO_CANCEL_LIAN;
    }
    //案件审查步骤的行政撤案表单提交
    @RequestMapping("xingzhengCancelLianForm")
    public ModelAndView xingzhengCancelLianForm(MultipartRequest multipartRequest,HttpServletRequest request,
    		CaseBasic caseBasic2,XingzhengCancelLianForm xingzhengCancelLianForm,String markup){
    	String path = REDIRECT_CASETODO_LIST;
    	if(markup.equals("chufa")){
			path+="?caseStateFlag=1";
		}
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.xingzhengCancelLianForm(multipartRequest,user,caseBasic2,xingzhengCancelLianForm);
    	ModelAndView mvAndView = new ModelAndView(path);
		mvAndView.addObject("tabid","行政处罚");
    	return mvAndView;
    }*/
    
	/**
	 * 异步获取受案公安机关树
	 * 
	 * @author: LXL
	 * @return:List<Organise>
	 * @createTime:2017年9月20日 上午11:59:51
	 */
    @RequestMapping(value="getAcceptOrgTree")
    @ResponseBody
	public List<Organise> getAcceptOrgTree(HttpServletRequest request, Integer orgCode, Integer districtCode) {
		User user = SystemContext.getCurrentUser(request);
        Organise org =  SystemContext.getCurrentUser(request).getOrganise();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		List<Organise> orgList = new ArrayList<Organise>();
		paraMap.put("orgType", "3");
        if (orgCode == null) {
          orgCode = user.getOrgId();
          paraMap.put("orgCode", orgCode);
          orgList = organiseMapper.getAcceptOrg(paraMap);
          //登陆用户的判断 ，如果为区级的林业部门，这里办理单位为市级的森林公安
          //判断是否设置公安办案机构
          if (org.getAcceptDistrictCode() != null) {
            paraMap.put("districtCode", org.getAcceptDistrictCode());
            // 使用了 现有生成要求公安说明不立案理由通知书 的方法 根据区划查找公安部门
            List<Organise> orgList1 = organiseMapper.findPoliceByDistrictId(paraMap);
            orgList.addAll(orgList1);
          }
        } else {
          paraMap.put("orgCode", orgCode);
          paraMap.put("districtCode", districtCode);
          orgList = organiseMapper.getAcceptOrgByOrgCodeAndDistrictId(paraMap);
        }
		return orgList;
	}
    
    /**
     * 检察院建议移送跳转
     * @param request
     * @param modelMap
     * @param caseTodo
     * @param backType
     * @param info 建议移送信息是否保存成功
     * @return
     */
    @RequestMapping("suggestYisong")
    public String suggestYisong(HttpServletRequest request,ModelMap modelMap,
    		CaseTodo caseTodo,String backType,String info){
    	modelMap.put("caseTodo", caseTodo);
    	CaseBasic case1 =caseBasicMapper.selectByPrimaryKey(caseTodo.getCaseId());
    	modelMap.put("inputerId", case1.getInputer());
    	modelMap.put("backType", backType);
    	modelMap.put("info",info);
    	return CASETODO_SUGGEST_YISONG;
    }
    
    //检察院建议移送表单提交
    @RequestMapping("saveSuggestYisongForm")
    public String saveSuggestYisongForm(MultipartRequest multipartRequest,HttpServletRequest request,
    		SuggestYisongForm suggestYisongForm,String backType){
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = 
    			caseTodoService.saveSuggestYisongForm(multipartRequest,suggestYisongForm,user);
    	String path = "redirect:/casehandle/caseTodo/suggestYisong?caseId="+suggestYisongForm.getCaseId();
    	/*if(backType.equals("daiban")){
    		path =  "redirect:/casehandle/caseTodo/list";
    	}else if (backType.equals("1")) {
    		path = "redirect:/query/acceptCaseQuery/search";
		}else if(backType.equals("2")){
			path = "redirect:/query/xingzhengLianQuery/query";
    	}else if(backType.equals("3")){
    		path = "redirect:/query/xingzhengChufaQuery/query";
    	}*/
    	String info="";
    	if (res.getResult()) {
    		info = "true";
    	} else {
    		info = "false";
    	}
    	path += "&info="+info+"&backType="+backType;
    	return path;
    }
    
    /**
     * 行政结案页面跳转
     * 已作出处罚菜单的结案
     * @param request
     * @param modelMap
     * @param caseTodo
     * @return
     */
    @RequestMapping("xingzhengJiean")
    public String xingzhengJiean(HttpServletRequest request,ModelMap modelMap,
    		CaseTodo caseTodo,String info){
    	String caseId = caseTodo.getCaseId();
    	CaseBasic caseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
    	modelMap.put("caseTodo", caseBasic);
    	modelMap.put("info", info);
    	return CASETODO_XINGZHENG_JIEAN;
    }
    
    //行政结案表单提交
    @RequestMapping("saveXingzhengJieanForm")
    public ModelAndView saveXingzhengJieanForm(MultipartRequest multipartRequest,HttpServletRequest request,
    	    XingzhengJieanForm xingzhengJieanForm){
    	String path = "redirect:/casehandle/caseTodo/xingzhengJiean?caseId="+xingzhengJieanForm.getCaseId();
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = 
    			caseTodoService.saveXingzhengJieanForm(multipartRequest,xingzhengJieanForm,user);
    	/*String info = res.getResult()+"";
    	path += "&info="+info;*/
    	String info  = "";
    	if (res.getResult()) {
            info += "true";
        } else {
        	info += "false";
        } 
    	path +="&info="+info;
    	ModelAndView mvAndView = new ModelAndView(path);
    	return mvAndView;
    }
    
    //移送管辖案件办理跳转
    @RequestMapping("turnoverView")
    public String turnoverView(HttpServletRequest request,ModelMap modelMap,
    		CaseBasic paramCaseBasic,String info){
    	String caseId = paramCaseBasic.getCaseId();
    	CaseBasic caseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
    	//查询案发区域名称
    	District district = districtMapper.selectByPrimaryKey(caseBasic.getAnfaAddress());
    	caseBasic.setAnfaAddressName(district.getDistrictName());
    	modelMap.addAttribute("caseBasic", caseBasic);
    	modelMap.addAttribute("info", info);
    	findPartyAndCompany(modelMap, caseId);
    	return CASETODO_TURNOVER_VIEW;
    }
    //要求说明不立案理由表单跳转
    @RequestMapping("requireNoLianReason")
    public String requireNoLianReason(HttpServletRequest request,ModelMap modelMap,String info) {
    	String caseId = request.getParameter("caseId");
    	modelMap.addAttribute("caseId", caseId);
    	modelMap.addAttribute("info", info);
    	return "casehandle/caseTodo/caseTodoRequireNoLianReason";
    }
    //保存要求说明不立案理由表单
    @RequestMapping("saveRequireNoLianReason")
    public String saveRequireNoLianReason(HttpServletRequest request,CaseRequireNolianReason require,MultipartRequest multipartRequest) {
    	User user = SystemContext.getCurrentUser(request);
    	String path = "redirect:/casehandle/caseTodo/requireNoLianReason";
    	ServiceResponse res = caseTodoService.saveRequireNoLianReason(require,user,multipartRequest);
    	String info  = "";
    	if (res.getResult()) {
            info += "true";
        } else {
        	info += "false";
        } 
    	path +="?info="+info;
    	return path;
    }
    //立案监督案件查询
    @RequestMapping("lianSupTodoList")
    public ModelAndView lianSupTodoList(HttpServletRequest request,CaseTodo caseTodo,String page) {
    	ModelAndView mv=new ModelAndView("/casehandle/caseTodo/lianSupTodoList");
    	User user = SystemContext.getCurrentUser(request);
    	Organise org = user.getOrganise();
    	if(!Const.TOP_ORG_ID.equals(org.getUpOrgCode())) {//是否为顶级机构
    		if(Const.ORG_TYPE_JIANCHAYUAN.equals(org.getOrgType())) {//检察机关
    			caseTodo.setCreateOrg(user.getOrgId());
    		}
    		if(Const.ORG_TYPE_GOGNAN.equals(org.getOrgType())) {//公安机关
    			caseTodo.setAssignOrg(user.getOrgId());
    		}
    	}
    	Map<String, Object> map = new HashMap<String, Object>();
    	mv.addObject("orgType", org.getOrgType());
    	map.put("orgType", org.getOrgType());
		PaginationHelper<CaseTodo> caseTodoList = caseTodoService.getLianSupTodoList(caseTodo, map, page);
		mv.addObject("caseTodoList", caseTodoList);
		mv.addObject("page",page);
    	return mv;
    }
    //公安说明不立案理由表单跳转
    @RequestMapping("noLianReason")
    public String noLianReason(HttpServletRequest request,ModelMap modelMap,String info) {
    	String caseId = request.getParameter("caseId");
    	modelMap.addAttribute("caseId", caseId);
    	modelMap.addAttribute("info", info);
    	return "casehandle/caseTodo/noLianReason";
    }
    //保存公安说明不立案理由表单
    @RequestMapping("saveNolianReason")
    public String saveNolianReason(HttpServletRequest request,CaseNolianReason reason,MultipartRequest multipartRequest) {
    	User user = SystemContext.getCurrentUser(request);
    	String path = "redirect:/casehandle/caseTodo/noLianReason";
    	ServiceResponse res = caseTodoService.saveNolianReason(reason,user,multipartRequest);
    	String info  = "";
    	if (res.getResult()) {
            info += "true";
        } else {
        	info += "false";
        } 
    	path +="?info="+info;
    	return path;
    }
    
    //通知公安立案表单跳转
    @RequestMapping("requireLian")
    public String requireLian(HttpServletRequest request,ModelMap modelMap,String info) {
    	String caseId = request.getParameter("caseId");
    	modelMap.addAttribute("caseId", caseId);
    	modelMap.addAttribute("info", info);
    	return "casehandle/caseTodo/requireLian";
    }
    //保存通知公安立案表单
    @RequestMapping("saveRequireLian")
    public String saveRequireLian(HttpServletRequest request,CaseRequireLian requireLian,MultipartRequest multipartRequest) {
    	String path = "redirect:/casehandle/caseTodo/requireLian";
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.saveRequireLian(requireLian,user,multipartRequest);
    	String info = res.getResult()?"?info=noAgreeSuccess":"?info=noAgreeFailure";
    	return path+info;
    }
    //保存同意公安不立案表单
    @RequestMapping("saveAgreeNoLian")
    public String saveAgreeNoLian(HttpServletRequest request,CaseAgreeNolian agreeNoLian,MultipartRequest multipartRequest) {
    	String path = "redirect:/casehandle/caseTodo/requireLian";
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.saveAgreeNoLian(agreeNoLian,user,multipartRequest);
    	String info=res.getResult()?"?info=agreeSuccess":"?info=agreeFailure";
    	return path+info;
    }
    
    /**
     * 
     * @param caseBasic
     * @param attachmentFile
     * @param request
     * @param oldCaseId就是移送管辖表单提交后传的caseId，老案件的
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveTransactTurnover")
    public String penaltyCaseAdd(CaseBasic caseBasic,MultipartHttpServletRequest attachmentFile,
            HttpServletRequest request,String oldCaseId) throws Exception {
    	String path = "redirect:/casehandle/caseTodo/turnoverView?caseId="+oldCaseId+"&info=";
        User user = SystemContext.getCurrentUser(request);
        Integer orgCode = user.getOrgId();
        String userId = user.getUserId();
        caseBasic.setInputer(userId);
        caseBasic.setInputUnit(orgCode);
        ServiceResponse res = caseService.addPenaltyCase(caseBasic,attachmentFile,oldCaseId,user,request);
        if (res.getResult()) {
            path += "true";
        } else {
            path += "false";
        }       
        return path;
    }
    
    
    /**
     * 行政拘留待办列表查询
     * @param request
     * @param modelMap
     * @param caseTodo
     * @param page
     * @return
     */
    @RequestMapping("transferDetentionTodoList")
	public ModelAndView transferDetentionTodoList(HttpServletRequest request,
			CaseTodo caseTodo,String page){
		User user = SystemContext.getCurrentUser(request);
		//查询当前组织的部门id
		Organise org=orgService.getDeptByOrgCode(user.getOrgId());
		caseTodo.setAssignOrg(org.getOrgCode());
		PaginationHelper<CaseTodo> caseTodoList = caseTodoService.getTransferDetentionTodoList(caseTodo, page);
		
		ModelAndView mv=new ModelAndView("/casehandle/caseTodo/transferDetentionTodoList");
		mv.addObject("caseTodoList", caseTodoList);
		mv.addObject("page",page);
		return mv;
	}
    
    //公安办理行政拘留信息页面跳转
    @RequestMapping("detentionInfoView")
    public ModelAndView detentionInfoView(HttpServletRequest request,
    		String caseId,Integer transferId,String info){
    	ModelAndView mv=new ModelAndView("/casehandle/caseTodo/caseTodoDetentionInfoView");
    	mv.addObject("caseId", caseId);
    	mv.addObject("transferId", transferId);
    	mv.addObject("info", info);
    	return mv;
    }
    
    
    //保存公安办理行政拘留信息
    @RequestMapping("saveDetentionInfo")
    public ModelAndView saveDetentionInfo(MultipartRequest multipartRequest,HttpServletRequest request,
    		DetentionInfo detentionInfo){
    	detentionInfo.setDetentionState(Const.DETENTION_STATE_YES);
    	String path = "redirect:/casehandle/caseTodo/detentionInfoView?caseId="+detentionInfo.getCaseId()+"&transferId="+detentionInfo.getTransferId();
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.saveDetentionInfo(multipartRequest,user,detentionInfo);
    	String info = "";
    	if (res.getResult()) {
    		info = "true";
    	} else {
    		info = "false";
    	}
    	path += "&info="+info;
    	ModelAndView mvAndView = new ModelAndView(path);
    	return mvAndView;
    }
    
    //公安办理不予拘留信息页面跳转
    @RequestMapping("notDetentionInfoView")
    public ModelAndView notDetentionInfoView(HttpServletRequest request,
    		String caseId,Integer transferId,String info){
    	ModelAndView mv=new ModelAndView("/casehandle/caseTodo/caseTodoNotDetentionInfoView");
    	mv.addObject("caseId", caseId);
    	mv.addObject("transferId", transferId);
    	mv.addObject("info", info);
    	return mv;
    }
    
    //保存公安不予拘留信息
    @RequestMapping("saveNotDetentionInfo")
    public ModelAndView saveNotDetentionInfo(MultipartRequest multipartRequest,HttpServletRequest request,
    		DetentionInfo detentionInfo){
    	detentionInfo.setDetentionState(Const.DETENTION_STATE_NO);
    	String path = "redirect:/casehandle/caseTodo/detentionInfoView?caseId="+detentionInfo.getCaseId()+"&transferId="+detentionInfo.getTransferId();
    	User user = SystemContext.getCurrentUser(request);
    	ServiceResponse res = caseTodoService.saveNotDetentionInfo(multipartRequest,user,detentionInfo);
    	String info = "";
    	if (res.getResult()) {
    		info = "true";
    	} else {
    		info = "false";
    	}
    	path += "&info="+info;
    	ModelAndView mvAndView = new ModelAndView(path);
    	return mvAndView;
    }
    
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
}
