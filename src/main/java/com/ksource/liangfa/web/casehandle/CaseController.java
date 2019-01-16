package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.dForm.DFormUtil;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseAttachmentExample;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseBasicExample;
import com.ksource.liangfa.domain.CaseBasicExample.Criteria;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CaseState;
import com.ksource.liangfa.domain.CrimeCaseExt;
import com.ksource.liangfa.domain.CrimeCaseFormExample;
import com.ksource.liangfa.domain.DqdjCategory;
import com.ksource.liangfa.domain.IllegalSituation;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PenaltyCaseFormExample;
import com.ksource.liangfa.domain.PenaltyLianFormExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseJieanNoticeMapper;
import com.ksource.liangfa.mapper.CaseRecordMapper;
import com.ksource.liangfa.mapper.CrimeCaseFormMapper;
import com.ksource.liangfa.mapper.DqdjCategoryMapper;
import com.ksource.liangfa.mapper.IllegalSituationMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PenaltyCaseFormMapper;
import com.ksource.liangfa.mapper.PenaltyLianFormMapper;
import com.ksource.liangfa.mapper.ProcDeployMapper;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.mapper.TaskBindMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.PenaltyCaseFormService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.service.system.PostService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 此类为 处罚案件 业务 控制器
 *
 * @author zxl :)
 * @version 1.0 date 2011-5-12 time 下午05:10:17
 */
@Controller
@RequestMapping("/casehandle/case")
public class CaseController {

    /*此控制器针对处罚案件*/
    public static final String penaltyProcKey = Const.CASE_CHUFA_PROC_KEY;
    public static final String crimeProcKey = Const.CASE_CRIME_PROC_KEY;
    private static final String REDI_SEARCH_VIEW = "redirect:/casehandle/case/search";
    private static final String REDI_CRIME_SEARCH_VIEW = "redirect:/casehandle/case/crime_case_search";
    private static final String PENALTY_ADDUI_VIEW = "redirect:/casehandle/case/addUI?info=";
    private static final String CRIME_ADDUI_VIEW = "redirect:/casehandle/case/crimeAddUI?info=";
    private static final String PENALTY_MAIN_VIEW = "casehandle/penalty_case_main";
    @Autowired
    MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    CaseService caseService;
    @Autowired
    PostService postService;
    @Autowired
    OrgService orgService;
    @Autowired
    TaskService taskService;
    @Autowired
    ProcKeyMapper procKeyMapper;
    @Autowired
    ProcDeployMapper procDeployMapper;
    @Autowired
    TaskActionMapper taskActionMapper;
    @Autowired
    TaskBindMapper taskBindMapper;
    @Autowired
    CaseBasicMapper caseBasicMapper;
    @Autowired
    CaseAttachmentMapper caseAttachmentMapper;
    @Autowired
    CaseRecordMapper caseRecordMapper;
    @Autowired
    IllegalSituationMapper illegalSituationMapper;
    @Autowired
    OrganiseMapper organiseMapper;
    @Autowired
	DqdjCategoryMapper dqdjCategoryMapper;
    @Autowired
    PenaltyCaseFormMapper penaltyCaseFormMapper;
    @Autowired
    PenaltyCaseFormService penaltyCaseFormService;
    
    @RequestMapping(value = "penalty_case_add")
    public String penaltyCaseAdd(
            CaseBasic caseBasic,
            MultipartHttpServletRequest attachmentFile,
            HttpServletRequest request,String view) throws Exception {
    	String path = PENALTY_ADDUI_VIEW;
        User user = SystemContext.getCurrentUser(request);
        ServiceResponse res = caseService.addPenaltyCase(caseBasic,attachmentFile,view,user,request);
        if (res.getResult()) {
            path += "true";
        } else {
            path += "false";
        }       
        return path+"&view="+view;
    }

  //找到当前登录机构对应的两法办机构id
  	/*private Integer liangfabanOrgId(User user){
  		Integer upOrgCode = user.getOrgId();
  		//获取当前登录机构对应的两法办的机构id
  		OrganiseExample organiseExample = new OrganiseExample();
  		organiseExample.createCriteria().andUpOrgCodeEqualTo(upOrgCode).andIsDeptEqualTo(1);
  		List<Organise> orgList = organiseMapper.selectByExample(organiseExample);
  		Integer liangfabanOrgId = null;
  		if(orgList.size()>0){
  			liangfabanOrgId = orgList.get(0).getOrgCode();
  		}
  		return liangfabanOrgId;
  	}*/
    @RequestMapping(value = "crime_case_add")
    public String crimeCaseAdd(
            CaseBasic caseBasic, CrimeCaseExt crimeCaseExt, CaseState caseState,
            MultipartHttpServletRequest attachmentFile,
            HttpServletRequest request) throws Exception {
        String path = CRIME_ADDUI_VIEW;
        User user = SystemContext.getCurrentUser(request);
        Integer orgCode = user.getOrgId();
        caseBasic.setInputer(user.getUserId());
        Date currentDate = new Date();
        caseBasic.setInputTime(currentDate);
        caseBasic.setLatestPocessTime(currentDate);
        caseState.setChufaState(Const.CHUFA_STATE_NOTYET);
        caseState.setYisongState(Const.YISONG_STATE_NO);
        caseState.setLianState(Const.LIAN_STATE_NOTYET);
        caseState.setDaibuState(Const.DAIBU_STATE_NOTYET);
        caseState.setQisuState(Const.QISU_STATE_NOTYET);
        caseState.setPanjueState(Const.PANJUE_STATE_NOTYET);
        caseState.setJieanState(Const.JIEAN_STATE_NO);
        caseState.setExplainState(Const.EXPLAIN_STATE_NOTYET);
        caseState.setReqExplainState(Const.REQ_EXPLAIN_STATE_NOTYET);
        caseBasic.setProcKey(crimeProcKey);
        //对案件编号进行去掉空格处理(与案件编号的验证，查询保持一致)
        caseBasic.setCaseNo(StringUtils.trim(caseBasic.getCaseNo()));
        //流程变量集合
        Map<String, Object> map = new HashMap<String, Object>();
//      map.put(ActivitiUtil.VAR_ORG_CODE, caseBasic.getAcceptUnit());
        //未作出处罚主动移送公安页面，为“是否为侵权假冒类型”赋值
//      caseBasic.setIsDqdj(caseBasic.getIsDqdjYisong());
        ServiceResponse res = caseService.addCrimeCase(caseBasic, caseState, crimeCaseExt, map, attachmentFile, orgCode);

        if (res.getResult()) {
            path += "true";
        } else {
            path += "false";
        }
		/*if ("search".equals(request.getParameter("view"))) { // 根据前台条件跳转
			path = REDI_SEARCH_VIEW ;
		}else{
			path=ResponseMessage.addParam(path,"action", action);
		}*/
        //path=ResponseMessage.addParam(path,"caseInputTiming", caseWithBLOBs.getCaseInputTiming().toString());
        return path;
    }

    /**
     * 进入行政处罚案件管理界面
     *
     * @throws Exception
     */
    @RequestMapping(value = "penalty_case_main")
    public String main(ModelMap model, HttpServletRequest request) throws Exception {
        return this.search(model, new CaseBasic(), "1", request);
    }

    @RequestMapping(value = "addUI")
    public String addUI(ModelMap model, String info, HttpSession session,HttpServletRequest request,String view) throws Exception {
        model.addAttribute("info", info);
        
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
		
        String path="";
        if(view != null && !view.equals("null")){
        	//移送受理列表页面
        	path="redirect:/casehandle/caseTodo/list?caseState=0&isTurnover=1";
        }else{
        	path = "casehandle/penalty_case_add_v2";
        }
        return path;
    }

    @RequestMapping(value = "crimeAddUI")
    public String crimeAddUI(ModelMap model, String info, HttpSession session) throws Exception {
        if (info != null) {
            String inf = null;
            if (info.equals("true")) {
                inf = "信息添加成功";
            } else if (info.equals("false")) {
                inf = "信息添加失败";
            }
            model.addAttribute("info", inf);
        }
        //通过action决定path
		/*String path="";
		if(caseInputTiming==Const.CASE_INPUT_TIMING_CHUFA){
			path="casehandle/chufaCaseAdd";
		}else if(caseInputTiming==Const.CASE_INPUT_TIMING_LIAN){
			path="casehandle/lianCaseAdd";
		}else if(caseInputTiming==Const.CASE_INPUT_TIMING_YISONG){
			path="casehandle/yisongCaseAdd";
		}
		model.addAttribute("caseInputTiming",caseInputTiming);*/
        //TODO:录入权限验证,根据录入节点的机构类型(待完善)来判定录入权限
        //判断是否需要选择提交机构(提交给自己)
        Map<String, String> map = DFormUtil.getProcDefIdAndInputerTargetTaskDef(crimeProcKey);
        String procDefId = map.get("procDefId");
        String targetTaskDefId = map.get("targetTaskDefId");
        boolean needAssignTarget = DFormUtil.needAssignTarget(procDefId, targetTaskDefId);
        model.addAttribute("needAssignTarget", needAssignTarget);
        model.addAttribute("targetTaskDefId", targetTaskDefId);
        model.addAttribute("procDefId", procDefId);
        return "casehandle/crime_case_add";
    }

    //新增案件时，获取提交机构列表
    @RequestMapping(value = "getOrgTreeDataForAddCase")
    @ResponseBody
    public List<Organise> getOrgTreeDataForAddCase(String procDefId, String targetTaskDefId, HttpSession session) {
        User sessionUser = SystemContext.getCurrentUser(session);
        List<Organise> orgs =DFormUtil.getAssignTargetOrganiseList(procDefId, targetTaskDefId, sessionUser, sessionUser);
        Collections.sort(orgs,new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                return ((Organise)o2).getIsDept()-((Organise)o1).getIsDept();
            }
        });
        return  orgs;
    }

    /**
     * 涉嫌犯罪案件查询
     *
     * @return
     * @author XT
     */
    @RequestMapping(value = "crime_case_search")
    public String crimeCaseSearch(ModelMap model, CaseBasic caseBasic, String page, HttpServletRequest request) {
        User user = SystemContext.getCurrentUser(request);
        caseBasic.setInputer(user.getUserId());
        caseBasic.setProcKey(crimeProcKey);
        PaginationHelper<CaseBasic> caseList = caseService.find(caseBasic, page, null);
        model.addAttribute("caseList", caseList);
        // 保存查询条件,用于返回使用
        request.getSession().setAttribute(crimeProcKey, caseBasic);
        request.getSession().setAttribute(crimeProcKey + "page", page);
        request.getSession().setAttribute("procKey", crimeProcKey);
        model.addAttribute("page", page);
        return "/casehandle/crime_case_main";
    }

    @RequestMapping(value = "search")
    public String search(ModelMap model, CaseBasic caseBasic, String page,
                         HttpServletRequest request) throws Exception {
        User user = SystemContext.getCurrentUser(request);
        caseBasic.setInputer(user.getUserId());
        caseBasic.setProcKey(penaltyProcKey);
        PaginationHelper<CaseBasic> caseList = caseService.find(caseBasic, page, null);
        //PaginationHelper<CaseBasic> caseList = caseService.findPenaltyCaseList(caseBasic, page, null);
        model.addAttribute("caseList", caseList);

        // 保存查询条件,用于返回使用
        request.getSession().setAttribute(penaltyProcKey, caseBasic);
        request.getSession().setAttribute(penaltyProcKey + "page", page);
        request.getSession().setAttribute("procKey", penaltyProcKey);
        model.addAttribute("page", page);
        return PENALTY_MAIN_VIEW;
    }

    @RequestMapping(value = "addToLocal")
    public String addToLocal(@RequestParam("file") MultipartFile file,
                             HttpSession session) throws Exception {
        //caseService.addToLocal(file, session);
        return "casehandle/caseAddToLocal";
    }

    // 案件模块 返回
    @RequestMapping(value = "crime_case_back")
    public String crimeCaseBack(ModelMap model, HttpServletRequest request) throws Exception {
        // 有查询条件,按查询条件返回
        CaseBasic case1;
        String page;
        HttpSession session = request.getSession();
        if (session.getAttribute(crimeProcKey) != null) {
            case1 = (CaseBasic) session.getAttribute(crimeProcKey);
        } else {
            case1 = new CaseBasic();
            case1.setProcKey("noCaseInfo");//如果没有保存的查询条件就这样来。
        }
        if (session.getAttribute(crimeProcKey + "page") != null) {
            page = (String) session.getAttribute(crimeProcKey + "page");
        } else {
            page = "1";
        }
        return this.crimeCaseSearch(model, case1, page, request);
    }

    // 案件模块 返回
    @RequestMapping(value = "penalty_case_back")
    public String penaltyCaseBack(ModelMap model, HttpServletRequest request) throws Exception {
        // 有查询条件,按查询条件返回
        CaseBasic case1;
        String page;
        HttpSession session = request.getSession();
        if (session.getAttribute(penaltyProcKey) != null) {
            case1 = (CaseBasic) session.getAttribute(penaltyProcKey);
        } else {
            case1 = new CaseBasic();
            case1.setProcKey("noCaseInfo");//如果没有保存的查询条件就这样来。
        }
        if (session.getAttribute(penaltyProcKey + "page") != null) {
            page = (String) session.getAttribute(penaltyProcKey + "page");
        } else {
            page = "1";
        }
        return this.search(model, case1, page, request);
    }

    //进入案件　修改页面
    @RequestMapping(value = "v_update_crime_case", method = RequestMethod.GET)
    public String vUpdateCrimeCase(String caseId, ModelMap model) {
        //案件信息
        Map<String, Object> caseMap = new HashMap<String, Object>();
        caseMap = caseService.findCrimeCaseById(caseId);
        CaseAttachmentExample attaExample = new CaseAttachmentExample();
        attaExample.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(crimeProcKey);
        model.addAttribute("caseBasic", caseMap.get("caseBasic"));
        model.addAttribute("crimeCaseExt", caseMap.get("crimeCaseExt"));
        List<CaseAttachment> attaList = (List<CaseAttachment>) caseMap.get("attaList");
        Map<String, Object> attaMap = new HashMap<String, Object>();
        //EL表达式取MAP的值时，不能用数字来当KEY
        for (CaseAttachment caseAttachment : attaList) {
            attaMap.put("f" + caseAttachment.getId().toString(), caseAttachment);
        }
        model.addAttribute("attaMap", attaMap);
        Map<String, String> map = DFormUtil.getProcDefIdAndInputerTargetTaskDef(crimeProcKey);
        String procDefId = map.get("procDefId");
        String targetTaskDefId = map.get("targetTaskDefId");
        boolean needAssignTarget = DFormUtil.needAssignTarget(procDefId, targetTaskDefId);
        model.addAttribute("needAssignTarget", needAssignTarget);
        model.addAttribute("targetTaskDefId", targetTaskDefId);
        model.addAttribute("procDefId", procDefId);
        //查询打侵打假类型
      	DqdjCategory dqdjCategory = dqdjCategoryMapper.getByCaseId(caseId);
      	String dqdjTypeName = "";
      	Integer dqdjType;
      	if(dqdjCategory != null){
      		dqdjType = dqdjCategory.getCategoryId();
      		dqdjTypeName = dqdjCategory.getName();
      		model.addAttribute("dqdjType", dqdjType);
      		model.addAttribute("dqdjTypeName", dqdjTypeName);
      	}
        //通过案件录入时机决定path
		/*int caseInputTiming = caseInfo.getCaseInputTiming();
		String path="";
		if(caseInputTiming==Const.CASE_INPUT_TIMING_CHUFA){
			path="casehandle/chufaCaseUpdate";
		}else if(caseInputTiming==Const.CASE_INPUT_TIMING_LIAN){
			path="casehandle/lianCaseUpdate";
		}else if(caseInputTiming==Const.CASE_INPUT_TIMING_YISONG){
			path="casehandle/yisongCaseUpdate";
		}*/
        return "casehandle/crime_case_update";
    }

    //修改案件　　
    @RequestMapping(value = "o_update_crime_case", method = RequestMethod.POST)
    public String oUpdateCrimeCase(CaseBasic caseBasic, CrimeCaseExt crimeCaseExt, MultipartHttpServletRequest attachmentFile, HttpServletRequest request) throws Exception {
        caseService.updateCrimeCase(caseBasic, crimeCaseExt, attachmentFile);
        return REDI_CRIME_SEARCH_VIEW;
    }

    //涉嫌犯罪案件
    @RequestMapping(value = "detail_crime_case")
    public String detailCrimeCase(String caseId, ModelMap model) {
        //案件信息
        Map<String, Object> caseMap = new HashMap<String, Object>();
        caseMap = caseService.findCrimeCaseById(caseId);
        CaseAttachmentExample attaExample = new CaseAttachmentExample();
        attaExample.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(crimeProcKey);
        model.addAttribute("caseBasic", caseMap.get("caseBasic"));
        model.addAttribute("crimeCaseExt", caseMap.get("crimeCaseExt"));
        List<CaseAttachment> attaList = (List<CaseAttachment>) caseMap.get("attaList");
        Map<String, Object> attaMap = new HashMap<String, Object>();
        //EL表达式取MAP的值时，不能用数字来当KEY
        for (CaseAttachment caseAttachment : attaList) {
            attaMap.put("f" + caseAttachment.getId().toString(), caseAttachment);
        }
        model.addAttribute("attaMap", attaMap);
        return "casehandle/crime_case_detail";
    }

    @ResponseBody
    @RequestMapping(value = "delFile/{caseId}/{fileType}", method = RequestMethod.GET)
    public void delCaseFile(@PathVariable String caseId, @PathVariable int fileType) {
        CaseBasic caseBasic = new CaseBasic();
        caseBasic.setCaseId(caseId);
        if (fileType == Const.CASE_DETAIL_FILE) {
            caseBasic.setCaseDetailName("");
            //case1.setCaseDetailFileName("");
        }
        caseService.update(caseBasic, null);
    }

    @ResponseBody
    @RequestMapping(value = "delFile/{id}", method = RequestMethod.GET)
    public void delFile(@PathVariable Long id) {
        caseService.delAtta(id);
    }

    @RequestMapping(value = "checkCaseState/{caseId}")
    @ResponseBody
    public ServiceResponse checkCaseState(@PathVariable String caseId) {
        ServiceResponse res = new ServiceResponse(true, "");
        CaseBasic case1 = mybatisAutoMapperService.selectByPrimaryKey(CaseBasicMapper.class, caseId, CaseBasic.class);
        if (!case1.getCaseState().equals(String.valueOf(Const.CASE_STATE_SHENGHE))) {
            res.setingError("本案件已通过审核,不能修改");
        }
        return res;
    }

    @RequestMapping(value = "getCasePartyByCaseId/{caseId}")
    @ResponseBody
    public List<CaseParty> getCasePartyByCaseId(@PathVariable String caseId) {
        return caseService.getCasePartyByCaseId(caseId);
    }

    @RequestMapping(value = "getCaseCompanyByCaseId/{caseId}")
    @ResponseBody
    public List<CaseCompany> getCaseCompanyByCaseId(@PathVariable String caseId) {
        return caseService.getCaseCompanyByCaseId(caseId);
    }

    /**
     * 案件编号唯一性校验
     * @param caseNo
     * @param caseId
     * @return
     */
    @RequestMapping(value = "checkCaseNo")
    @ResponseBody
    public boolean checkCaseNo(String caseNo, String caseId) {
        CaseBasicExample example = new CaseBasicExample();
        Criteria criteria = example.createCriteria().andCaseNoEqualTo(caseNo.trim());
        if (caseId != null) {
            criteria.andCaseIdEqualTo(caseId);
        }
        int size = mybatisAutoMapperService.countByExample(CaseBasicMapper.class,
                example);

        if (size > 0) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 行政处罚决定书文号唯一编码验证
     * @param penaltyFileNo
     * @param caseId
     * @return
     */
    @RequestMapping(value = "checkPenaltyFileNo")
    @ResponseBody
    public boolean checkPenaltyFileNo(String penaltyFileNo, String caseId) {
    	PenaltyCaseFormExample example = new PenaltyCaseFormExample();
        com.ksource.liangfa.domain.PenaltyCaseFormExample.Criteria criteria = example.createCriteria().andPenaltyFileNoEqualTo(penaltyFileNo.trim());
        if (caseId != null) {
            criteria.andCaseIdEqualTo(caseId);
        }
        int size = mybatisAutoMapperService.countByExample(PenaltyCaseFormMapper.class,
                example);

        if (size > 0) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 校验立案编号唯一性
     * @param LianNo
     * @param caseId
     * @return
     */
    @RequestMapping(value="checkLianNo")
    @ResponseBody
    public boolean checkLianNo(String lianNo, String caseId) {
    	PenaltyLianFormExample example = new PenaltyLianFormExample();
    	com.ksource.liangfa.domain.PenaltyLianFormExample.Criteria criteria = example.createCriteria().andLianNoEqualTo(lianNo.trim());
    	if (caseId != null) {
    		criteria.andCaseIdEqualTo(caseId);
    	}
    	int size = mybatisAutoMapperService.countByExample(PenaltyLianFormMapper.class,
    			example);
    	
    	if (size > 0) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    
    
    /**
     * 移送文书号唯一编码验证
     * @param yisongFileNo
     * @param caseId
     * @return
     */
    @RequestMapping(value = "checkYisongFileNo")
    @ResponseBody
    public boolean checkYisongFileNo(String yisongNo, String caseId) {
    	CrimeCaseFormExample example = new CrimeCaseFormExample();
    	com.ksource.liangfa.domain.CrimeCaseFormExample.Criteria criteria = example.createCriteria().andYisongNoEqualTo(yisongNo.trim());
    	if (caseId != null) {
    		criteria.andCaseIdEqualTo(caseId);
    	}
    	int size = mybatisAutoMapperService.countByExample(CrimeCaseFormMapper.class,
    			example);
    	
    	if (size > 0) {
    		return false;
    	} else {
    		return true;
    	}
    }

    @ResponseBody
    @RequestMapping("checkCaseNoByorg")
    public boolean checkCaseNoByorg(String caseNo, HttpSession session) {
        boolean exist = true;
        int count = caseService.countBycaseNo(initParamMap(caseNo, session));
        if (count == 0) {
            exist = false;
        }
        return exist;

    }

    @ResponseBody
    @RequestMapping("findByCaseNoandOrg")
    public CaseBasic findByCaseNoandOrg(String caseNo, HttpSession session) {
        return caseService.findByCaseNoandOrg(initParamMap(caseNo, session));
    }

    @ResponseBody
    @RequestMapping("findByLikeCaseNo")
    public List<CaseBasic> findByLikeCaseNo(String caseNo, HttpSession session) {
        return caseService.findByLikeCaseNo(initParamMap(caseNo, session));
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    private Map<String, Object> initParamMap(String caseNo, HttpSession session) {
        Organise userOrg = SystemContext.getCurrentUser(session).getOrganise();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("caseNo", caseNo);
        if (userOrg!=null&&Const.ORG_TYPE_XINGZHENG.equals(userOrg.getOrgType())) {
            map.put("orgCode", userOrg.getOrgCode());
        } else {
            String userDis;
            if(userOrg==null){
                userDis=SystemContext.getSystemInfo().getDistrict();
            } else{
                userDis=userOrg.getDistrictCode();
            }
            map.put("districtCode", com.ksource.liangfa.util.StringUtils.rightTrim0(userDis));
        }
        return map;
    }

    //查询某案件的某当事人的历史案件
    @RequestMapping(value = "queryHistoryCaseBySameOrgAndCaseParty", method = RequestMethod.GET)
    public String queryHistoryCaseBySameOrgAndCaseParty(String caseId, String idsNo, ModelMap model) {
        List<CaseBasic> caseList = caseBasicMapper.queryHistoryCaseBySameOrgAndCaseParty(caseId, idsNo);
        model.addAttribute("caseList", caseList);
        return "casehandle/queryWarnHistoryCase";
    }

    //查询某案件的某当事人的历史案件
    @RequestMapping(value = "queryHistoryCaseBySameOrgAndCaseCompany", method = RequestMethod.GET)
    public String queryHistoryCaseBySameOrgAndCaseCompany(String caseId, String regNo, ModelMap model) {
        List<CaseBasic> caseList = caseBasicMapper.queryHistoryCaseBySameOrgAndCaseCompany(caseId, regNo);
        model.addAttribute("caseList", caseList);
        return "casehandle/queryWarnHistoryCase";
    }

    /**
     * 查询超时的案件
     *
     * @return
     */
    @RequestMapping("/timeOutWarningCases")
    public ModelAndView timeOutWarningCases(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/casehandle/timeOutWarning/timeOutWarningCases");
        User user = SystemContext.getCurrentUser(session);
        modelAndView.addObject("caseList", caseService.getTimeoutWarnCases(user));
        return modelAndView;
    }

    @RequestMapping("/caseJieanNotice")
    public ModelAndView caseJieanNotice(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/casehandle/caseJieanNotice");
        User user = SystemContext.getCurrentUser(session);
        modelAndView.addObject("caseList", caseService.selectJieanNoticeCase(user.getUserId()));
        return modelAndView;
    }

    @RequestMapping("/caseRecordNotice")
    public ModelAndView caseRecordNotice(HttpSession session, String page) {
        ModelAndView modelAndView = new ModelAndView("/casehandle/caseRecordNotice");
        User user = SystemContext.getCurrentUser(session);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", user.getUserId());
        modelAndView.addObject("caseList", caseService.caseRecordNotice(paramMap, page));
        return modelAndView;
    }

    @RequestMapping("/delCaseRecordNotice")
    @ResponseBody
    public boolean delCaseRecordNotice(HttpSession session, Integer caseId, String procKey) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", SystemContext.getCurrentUser(session).getUserId());
        paramMap.put("caseId", caseId);
        paramMap.put("procKey", procKey);
        caseRecordMapper.updateReadState(paramMap);  //状态更新为已读
        return true;
    }

    @RequestMapping("/delCaseJieanNotice")
    @ResponseBody
    public boolean delCaseJieanNotice(Integer id) {
        mybatisAutoMapperService.deleteByPrimaryKey(CaseJieanNoticeMapper.class, id);
        return true;
    }

    /**
     * 查询当前用户监督的案件
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/caseSupervisionNotice")
    public ModelAndView caseSupervisionNotice(HttpSession session) {
        ModelAndView view = new ModelAndView("/casehandle/caseSupervisionNotice");
        User user = SystemContext.getCurrentUser(session);
        view.addObject("caseList", caseService.caseSupervisionNotice(user.getUserId()));
        return view;
    }
    
  //进入行政处罚案件　修改页面
    @RequestMapping(value = "accept_updateUI")
    public String accept_updateUI(String caseId,ModelMap model, HttpServletRequest request,
    		String info) {
        //案件信息
        CaseBasicExample example = new CaseBasicExample();
        example.createCriteria().andCaseIdEqualTo(caseId);
        Map<String, Object> caseMap = new HashMap<String, Object>();
        caseMap = caseService.findAccpetCaseById(caseId);
        model.addAttribute("caseBasic", caseMap.get("caseBasic"));
        model.addAttribute("xianyirenList", caseMap.get("xianyirenList"));
        model.addAttribute("casePartyList",caseMap.get("casePartyList"));
        model.addAttribute("caseCompanyList", caseMap.get("caseCompanyList"));
        model.addAttribute("info", info);
        String view = "casehandle/accept_case_update";
        return view;
    }
    
  //修改案件　　
    @RequestMapping(value = "accpet_case_update", method = RequestMethod.POST)
    public String update(CaseBasic caseBasic,
            MultipartHttpServletRequest attachmentFile) throws Exception {
    	String path = "redirect:/casehandle/case/accept_updateUI?caseId="+caseBasic.getCaseId();
    	ServiceResponse res = caseService.updateAcceptCase(caseBasic, attachmentFile);
        String info = "";
		if (res.getResult()) {
			info = "true";
        } else {
        	info = "false";
        }   
		path += "&info="+info;
		return path;
    }
}
