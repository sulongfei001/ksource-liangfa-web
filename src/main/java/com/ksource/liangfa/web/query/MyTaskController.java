package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseFilter;
import com.ksource.liangfa.domain.CaseFilterExample;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.OrgAmount;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseFilterMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseCompanyService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.CasepartyService;
import com.ksource.liangfa.service.system.AccuseInfoService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.OrgAmountService;
import com.ksource.liangfa.service.workflow.WorkflowService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.liangfa.workflow.task.TaskVO;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


/**
  * 此类为  待办案件查询 控制器
  * 
  * @author ljj)
  *
  */ 
@Controller
@RequestMapping("/query/myTask")
public class MyTaskController {
	
	@Autowired
	WorkflowService workflowService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	AccuseInfoService accuseInfoService;
	@Autowired
	CaseService caseService;
	@Autowired
	DistrictService districtService;
	@Autowired
	CasepartyService casepartyService;
	@Autowired
	CaseCompanyService caseCompanyService;
	@Autowired
	OrgAmountService orgAmountService;
	
	
	/**
     * 获取登录用户的待办任务，应该包含直接分配给该登录用户及用户所在组的任务  集合
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/todo")
    public String toDoTaskList(CaseFilter caseFilter,HttpServletRequest request, Map<String, Object> model, String page) {
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	User user = SystemContext.getCurrentUser(request);
    	paramMap.put("caseNo", caseFilter.getCaseNo());
    	paramMap.put("caseName", caseFilter.getCaseName());
    	paramMap.put("minCaseInputTime", caseFilter.getMinCaseInputTime());
    	paramMap.put("maxCaseInputTime", caseFilter.getMaxCaseInputTime());
    	paramMap.put("isDiscussCase", caseFilter.getIsDiscussCase());
    	paramMap.put("minAmountInvolved", caseFilter.getMinAmountInvolved());
    	paramMap.put("maxAmountInvolved", caseFilter.getMaxAmountInvolved());
    	paramMap.put("chufaTimes", caseFilter.getChufaTimes());
    	paramMap.put("isSeriousCase", caseFilter.getIsSeriousCase());
    	paramMap.put("isBeyondEighty", caseFilter.getIsBeyondEighty());
    	paramMap.put("orgId", caseFilter.getOrgId());
    	//此参数表示是首页待办 还是案件管理的待办案件查询，这两个查询条件不一致,首页待办查询条件用or过滤，待办案件查询条件用and过滤
		//1:首页待办    2：待办案件查询
    	paramMap.put("type", 2);
    	
        PaginationHelper<TaskVO> tasks = workflowService.queryToDoTasks(user, 0, page,paramMap);
        model.put("tasks", tasks);
        model.put("page", page);
        return "/querystats/todoTaskList";
    }
	
    /**
     * 当前用户的疑似犯罪待办任务查询
     * @param caseFilter
     * @param request
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/suspectedCrimeTodo")
    public String suspectedCrimeToDoTaskList(CaseFilter caseFilter,Integer queryScope,Integer districtQueryScope,String districtName,HttpServletRequest request, Map<String, Object> model, String page) {
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	User user = SystemContext.getCurrentUser(request);
    	paramMap.put("caseNo", caseFilter.getCaseNo());
    	paramMap.put("caseName", caseFilter.getCaseName());
    	paramMap.put("minCaseInputTime", caseFilter.getMinCaseInputTime());
    	paramMap.put("maxCaseInputTime", caseFilter.getMaxCaseInputTime());
    	paramMap.put("orgId", caseFilter.getOrgId());
    	paramMap.put("orgPath", caseFilter.getOrgPath());
        String districtId="";
        if(caseFilter.getDistrictId() != null){
            if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
                districtId=StringUtils.rightTrim0(caseFilter.getDistrictId().toString());
            }else{
                districtId = caseFilter.getDistrictId().toString();
            }
        }else{
            districtId=StringUtils.rightTrim0(user.getOrganise().getDistrictCode());
        }
    	paramMap.put("districtId", districtId);
        if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
            paramMap.put("orgId", null);
        }
    	String accuseIds = caseFilter.getAccuseId();
    	if(StringUtils.isNotBlank(accuseIds)){
        	String[] accuseIdAry = accuseIds.split(",");
        	paramMap.put("accuseIdAry", accuseIdAry);
        	List<AccuseInfo> accuseInfos = accuseInfoService.queryAccuseByIds(accuseIdAry);
        	String accuseInfoListJson=JSON.toJSONString(accuseInfos);
        	 model.put("accuseInfos", accuseInfoListJson);
    	}
    	//TODO LXL:对旧的案件进行疑似犯罪分析
//    	caseService.analyCaseBasicList(paramMap,user);
    	
        //PaginationHelper<TaskVO> tasks = workflowService.queryIllegalToDoTasks(user, 0, page,paramMap);
    	PaginationHelper<CaseBasic> casePagination = caseService.querySuspectedCaseList(page,paramMap);
    	List<CaseBasic> caseBasicList = casePagination.getList();
    	if(caseBasicList != null && !caseBasicList.isEmpty()){
    		for(CaseBasic cb:caseBasicList){
    			String accuseNameStr = accuseInfoService.getAccuseByCaseId(cb.getCaseId());
    			cb.setAccuseNameStr(accuseNameStr);
    		}
    	}
        model.put("casePagination", casePagination);
        model.put("page", page);
        District currDistrict = districtService.selectByPrimaryKey(user.getOrganise().getDistrictCode());
        model.put("districtLevel", currDistrict.getJb());
        model.put("currOrg", user.getDeptId());
        
      //当筛查数据为空时，按照"涉嫌犯罪案件线索筛查报表(无)"模版需求，查询出某行政区划下某段时间内的行政处罚案件数
        Map<String,Object> param = new HashMap<String, Object>();
        int chufaCount=0;
        District d=new District();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String districtId1="";
        Date minCaseInputTime=null;
        Date maxCaseInputTime=null;
        if(casePagination.getList().size()==0){
        	String docType="sxfz3";
        	String fileName="涉嫌犯罪案件线索筛查报告(无).doc";
        	
        	JSONArray jsonArray = new JSONArray();
    		JSONObject object = new JSONObject();
    		//行政区划
        	if(caseFilter.getDistrictId()!=null){
        		districtId1=caseFilter.getDistrictId().toString();
        	}else{
        		districtId1=user.getOrganise().getDistrictCode();
        	}
        	param.put("districtId", com.ksource.liangfa.util.StringUtils.rightTrim0(districtId1));
        	//获取行政区划名称
        	d=districtService.selectByPrimaryKey(districtId1);
    		object.put("bookMarkName", "districtName");
    		if(StringUtils.isNotBlank(districtName)){
    			object.put("bookMarkValue", districtName);
	       	}else{
	       		if(d!=null){
	       			object.put("bookMarkValue", d.getDistrictName());
	       		}
	       	}
    		
    		jsonArray.add(object);
    		//构建时间条件
        	minCaseInputTime=caseFilter.getMinCaseInputTime();
        	CaseBasic caseBasic=new CaseBasic();
        	caseBasic.setDistrictId(param.get("districtId").toString());
        	caseBasic.setChufaState(Const.CHUFA_STATE_YES);
        	if(minCaseInputTime==null){
        		//查询行政区划下的行政处罚案件的最小录入时间
        		minCaseInputTime=caseService.queryMinCaseInputTime(caseBasic);
        		if(minCaseInputTime==null){//当最小时间为空时，暂时设置当前时间，后期需修改
        			minCaseInputTime=new Date();
        		}
        	}
        	param.put("startTime", minCaseInputTime);
        	JSONObject startTimeObj = new JSONObject();
    		startTimeObj.put("bookMarkName", "startTime");
    		startTimeObj.put("bookMarkValue", dateFormat.format(minCaseInputTime));
    		jsonArray.add(startTimeObj);
        	
        	maxCaseInputTime=caseFilter.getMaxCaseInputTime();
        	if(maxCaseInputTime==null){
        		maxCaseInputTime=new Date();
        	}
        	param.put("endTime", maxCaseInputTime);
    		JSONObject endTimeObj = new JSONObject();
    		endTimeObj.put("bookMarkName", "endTime");
    		endTimeObj.put("bookMarkValue", dateFormat.format(maxCaseInputTime));
    		jsonArray.add(endTimeObj);
    		
    		//构建待查阅的总数
    		//查询符合以上条件的行政处罚案件数
        	caseBasic.setMinCaseInputTime(minCaseInputTime);
        	caseBasic.setMaxCaseInputTime(maxCaseInputTime);
        	chufaCount= caseService.queryCaseCount(caseBasic);
    		JSONObject totalNumObj = new JSONObject();
    		totalNumObj.put("bookMarkName", "chufaCount");
    		totalNumObj.put("bookMarkValue", chufaCount);	
    		jsonArray.add(totalNumObj);
    		//
    		District dis=districtService.selectByPrimaryKey(user.getOrganise().getDistrictCode());
    		JSONObject objectJ = new JSONObject();
    		objectJ.put("bookMarkName", "districtNameJ");
    		objectJ.put("bookMarkValue", dis.getDistrictName());
    		jsonArray.add(objectJ);
    		
    		JSONObject currentTimeObj = new JSONObject();
    		currentTimeObj.put("bookMarkName", "currentTime");
    		currentTimeObj.put("bookMarkValue", dateFormat.format(new Date()));
    		jsonArray.add(currentTimeObj);
    		
    		model.put("jsonArray", jsonArray);
    		model.put("docType", docType);
    		model.put("fileName", fileName);
        }
    	else{
        	//不为空时，则查询预警信息
        	for(CaseBasic cb:casePagination.getList()){
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
        		if(orgAmount != null && cb.getAmountInvolved()!=null && cb.getAmountInvolved() > orgAmount.getAmountInvolved() && orgAmount.getAmountInvolved() != 0.00){
        			Double beyondAmount = cb.getAmountInvolved() - orgAmount.getAmountInvolved();
        			cb.getWarnMap().put("beyondAmount", beyondAmount);
        			cb.getWarnMap().put("orgAmount", orgAmount.getAmountInvolved());
        		}
        	}
        }
        
        return "/querystats/suspectedCrimeTodoTaskList";
    }
    
    
    /**
     * 当前用户的立案监督线索待办任务查询
     * @param caseFilter
     * @param request
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/filingSupervisionTodo")
    public String filingSupervisionToDoTaskList(CaseFilter caseFilter,Integer queryScope,Integer districtQueryScope,HttpServletRequest request, Map<String, Object> model, String page) {
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	User user = SystemContext.getCurrentUser(request);
    	Organise currOrg = user.getOrganise();
		District currDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, currOrg.getDistrictCode(), District.class);
    	paramMap.put("caseNo", caseFilter.getCaseNo());
    	paramMap.put("caseName", caseFilter.getCaseName());
    	paramMap.put("orgId", caseFilter.getOrgId());
    	paramMap.put("orgPath", caseFilter.getOrgPath());
    	paramMap.put("minCaseInputTimeS", caseFilter.getMinCaseInputTime());
    	paramMap.put("maxCaseInputTimeS", caseFilter.getMaxCaseInputTime());
    	paramMap.put("isDiscussCaseS", caseFilter.getIsDiscussCase());
    	paramMap.put("minAmountInvolvedS", caseFilter.getMinAmountInvolved());
    	paramMap.put("maxAmountInvolvedS", caseFilter.getMaxAmountInvolved());
    	paramMap.put("chufaTimesS", caseFilter.getChufaTimes());
    	paramMap.put("isSeriousCaseS", caseFilter.getIsSeriousCase());
    	paramMap.put("isBeyondEightyS", caseFilter.getIsBeyondEighty());  
    	paramMap.put("isIdentifyS", caseFilter.getIsIdentify());
    	paramMap.put("isLowerLimitMoneyS", caseFilter.getIsLowerLimitMoney()); 
    	String districtId = "";//user.getOrganise().getDistrictCode();
        if(caseFilter.getDistrictId() != null){
            if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
                districtId=StringUtils.rightTrim0(caseFilter.getDistrictId().toString());
                
            }else{
                districtId = caseFilter.getDistrictId().toString();
            }
        }else{
            districtId=StringUtils.rightTrim0(user.getOrganise().getDistrictCode());
        }
        paramMap.put("districtCode", districtId);
        if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
            paramMap.put("orgId", null);
        }
    	//根据登录用户组织机构查询案件筛选表信息，并作为参数筛选待办案件
        CaseFilterExample example=new CaseFilterExample();
		List<CaseFilter> list = mybatisAutoMapperService.selectByExample(CaseFilterMapper.class, example, CaseFilter.class);
		int count=list.size();
		if(count>=1){
			caseFilter=list.get(0);
		}
		if(caseFilter!=null){
			if(caseFilter.getMinAmountInvolved()!=null){
				paramMap.put("minAmountInvolved", caseFilter.getMinAmountInvolved());
				paramMap.put("procKey", "chufaProc");
			}
			if(caseFilter.getMaxAmountInvolved()!=null){
				paramMap.put("maxAmountInvolved", caseFilter.getMaxAmountInvolved());
				paramMap.put("procKey", "chufaProc");
			}
			if(caseFilter.getMinCaseInputTime()!=null){
				paramMap.put("minCaseInputTime", caseFilter.getMinCaseInputTime());
				paramMap.put("procKey", "chufaProc");
			}
			if(caseFilter.getMaxCaseInputTime()!=null){
				paramMap.put("maxCaseInputTime", caseFilter.getMaxCaseInputTime());
				paramMap.put("procKey", "chufaProc");
			}
			if(caseFilter.getIsDiscussCase()!=null){
				paramMap.put("isDiscussCase", caseFilter.getIsDiscussCase());
				paramMap.put("procKey", "chufaProc");
			}
			if(caseFilter.getIsSeriousCase()!=null){
				paramMap.put("isSeriousCase", caseFilter.getIsSeriousCase());
				paramMap.put("procKey", "chufaProc");
			}
			if(caseFilter.getIsBeyondEighty()!=null){
				paramMap.put("isBeyondEighty", caseFilter.getIsBeyondEighty());
				paramMap.put("procKey", "chufaProc");
			}
			if(caseFilter.getChufaTimes()!=null){
				paramMap.put("chufaTimes", caseFilter.getChufaTimes());
				paramMap.put("procKey", "chufaProc");
			}
			if(caseFilter.getIsLowerLimitMoney()!=null){
				paramMap.put("isLowerLimitMoney", caseFilter.getIsLowerLimitMoney());
				paramMap.put("procKey", "chufaProc");
			}
			if(caseFilter.getIsIdentify()!=null){
				paramMap.put("isIdentify", caseFilter.getIsIdentify());
				paramMap.put("procKey", "chufaProc");
			}
		}
    	PaginationHelper<CaseBasic> caseList = caseService.queryfilingSupervisionCase(page,paramMap,caseFilter);
    	for(CaseBasic cb:caseList.getList()){
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
    		if(orgAmount != null && cb.getAmountInvolved()!=null && cb.getAmountInvolved() > orgAmount.getAmountInvolved() && orgAmount.getAmountInvolved() != 0.00){
    			Double beyondAmount = cb.getAmountInvolved() - orgAmount.getAmountInvolved();
    			cb.getWarnMap().put("beyondAmount", beyondAmount);
    			cb.getWarnMap().put("orgAmount", orgAmount.getAmountInvolved());
    		}
    	}
    	model.put("caseList", caseList);
        model.put("page", page);
        model.put("districtJB", currDistrict.getJb());
        model.put("currOrg", user.getDeptId());
        return "/querystats/filingSupervisionTodoTaskList";
    }
    
    
    /**
     * 诉讼信息案件录入：查询公安受理之后的待办
     * @param caseFilter
     * @param request
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/lawsuitTodo")
    public String lawsuitTodo(CaseFilter caseFilter,HttpServletRequest request, Map<String, Object> model, String page) {
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	User user = SystemContext.getCurrentUser(request);
    	paramMap.put("caseNo", caseFilter.getCaseNo());
    	paramMap.put("caseName", caseFilter.getCaseName());
    	paramMap.put("minCaseInputTime", caseFilter.getMinCaseInputTime());
    	paramMap.put("maxCaseInputTime", caseFilter.getMaxCaseInputTime());
    	paramMap.put("isDiscussCase", caseFilter.getIsDiscussCase());
    	paramMap.put("minAmountInvolved", caseFilter.getMinAmountInvolved());
    	paramMap.put("maxAmountInvolved", caseFilter.getMaxAmountInvolved());
    	paramMap.put("chufaTimes", caseFilter.getChufaTimes());
    	paramMap.put("isSeriousCase", caseFilter.getIsSeriousCase());
    	paramMap.put("isBeyondEighty", caseFilter.getIsBeyondEighty());
    	paramMap.put("orgId", caseFilter.getOrgId());
    	//此参数表示是首页待办 还是案件管理的待办案件查询，这两个查询条件不一致,首页待办查询条件用or过滤，待办案件查询条件用and过滤
		//1:首页待办    2：待办案件查询
    	paramMap.put("type", 2);
    	//此参数表示公安受理之后的待办(诉讼信息案件录入)
    	paramMap.put("afterPoliceAccept", 1);
    	
        PaginationHelper<TaskVO> tasks = workflowService.queryLawsuitTodoTasks(user,page,paramMap);
        model.put("tasks", tasks);
        model.put("page", page);
        return "/querystats/lawsuitTodoList";
    }
    
    //进行日期转换格式操作
    @InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
