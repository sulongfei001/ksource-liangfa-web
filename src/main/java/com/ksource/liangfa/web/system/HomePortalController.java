package com.ksource.liangfa.web.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.activiti.engine.impl.bpmn.data.SimpleDataInputAssociation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.text.SimpleDateFormat;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.Paging;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.dao.TongjiDAO;
import com.ksource.liangfa.domain.CaseFilter;
import com.ksource.liangfa.domain.CaseFilterExample;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.DistrictExample;
import com.ksource.liangfa.domain.LayInfo;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WarnCondition;
import com.ksource.liangfa.domain.stat.StatisData;
import com.ksource.liangfa.mapper.BusinessLogMapper;
import com.ksource.liangfa.mapper.CaseFilterMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.model.ChartBean;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.StatisDataService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.CaseTodoService;
import com.ksource.liangfa.service.casehandle.WarnConditionService;
import com.ksource.liangfa.service.echart.ChartService;
import com.ksource.liangfa.service.info.InfoService;
import com.ksource.liangfa.service.notice.NoticeService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.service.workflow.CaseYisongjiweiService;
import com.ksource.liangfa.service.workflow.WorkflowService;
import com.ksource.liangfa.util.ResponseUtils;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.liangfa.workflow.task.TaskVO;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/home/portal")
public class HomePortalController  {
	
	@Autowired
	TongjiDAO tongjiDAO;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private InfoService infoService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private StatisDataService statisDataService;
	@Autowired
	private IndustryInfoService industryInfoService;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private CaseTodoService caseTodoService;
	@Autowired
	private CaseService caseService;
	@Autowired
	OrganiseMapper organiseMapper;
	@Autowired
	private CaseYisongjiweiService caseYisongjiweiService;
	@Autowired
	private WarnConditionService warnConditionService;
	@Autowired
	private BusinessLogMapper businessLogMapper;
	@Autowired
	private ChartService chartService;
	@Autowired
	private OrgService orgService;

	
	
	//通知通告　信息
	@RequestMapping(value="/noiceList")
	@ResponseBody
	public String noice(HttpServletRequest request){ 
		User user = SystemContext.getCurrentUser(request);
        List<Notice> noticeList =new ArrayList<Notice>();
		List<Notice> noReadNoticeList = noticeService.getNoRead(user.getUserId());
		List<Notice> readNoticeList = noticeService.getAlread(user.getUserId());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    //将查询未读的通知设置为指定状态
	    if(noReadNoticeList.size()>0){
			for(Notice notice : noReadNoticeList){
				notice.setReadState("1");
				//日期格式化转换为字符串
				notice.setNoticeCreateTime(sdf.format(notice.getNoticeTime()));
				noticeList.add(notice);
			}
	    }
	    //将查询已读的通知设置为指定状态
	    if(readNoticeList.size()>0){
	    	for(Notice notice : readNoticeList){
	    		notice.setReadState("0");
	    		notice.setNoticeCreateTime(sdf.format(notice.getNoticeTime()));
	    		noticeList.add(notice);
	    	}
	    }
	    
		String noticeJson = "";
		if(noticeList.size() > 5){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("noticeList", noticeList.subList(0, 5));
			noticeJson = jsonObject.toJSONString();
		}else{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("noticeList", noticeList);
			noticeJson = jsonObject.toJSONString();
		}
		return noticeJson;
	}
	
	/**
	 * 市、县用户查询本地系统中的接入单位
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/jierudanweiData")
	@ResponseBody
	public String jierudanweiData(String orgType,String industryType,Integer districtJB,HttpServletRequest request){ 
    	User user = SystemContext.getCurrentUser(request);
        String districtCode = user.getOrganise().getDistrictCode();
		StatisData statisData = new StatisData();
		//如果是检察院或牵头单位
		if(orgType.equals(Const.ORG_TYPE_JIANCHAYUAN) || orgType.equals(Const.ORG_TYPE_LIANGFALEADER)){
			districtJB = districtJB.intValue() == Const.DISTRICT_JB_1 ? null:districtJB.intValue();
			statisData = statisDataService.statisAccesOrg(null,null, districtJB,districtCode);
		}else{
			statisData = statisDataService.statisAccesOrg(orgType,industryType, districtJB,districtCode);
		}
		return JSONObject.toJSONString(statisData);
	}
	
	/**
	 *  市、县用户查询本地系统中案件环节数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/caseData")
	@ResponseBody
	public String caseData(String queryScope,String orgType,String industryType,Integer districtJB,HttpServletRequest request){ 
    	User user = SystemContext.getCurrentUser(request);
        String districtCode = user.getOrganise().getDistrictCode(); 
		StatisData statisData = new StatisData();
		//如果是检察院或牵头单位
		if(orgType.equals(Const.ORG_TYPE_JIANCHAYUAN) || orgType.equals(Const.ORG_TYPE_LIANGFALEADER)){
			districtJB = districtJB.intValue() == Const.DISTRICT_JB_1 ? null:districtJB.intValue();
			//TODO 默认查累计的数据 
			statisData = statisDataService.statisCaseNum(null,null, districtJB,queryScope,districtCode,null);
		}else{
			//判断登录单位是否是森林公安，森林公安权限是只能查看森林公安办理的案件
			Integer policeType=0;
			Organise org=user.getOrganise();
			//policeType为1:普通公安,2:森林公安
			if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){//森林公安
	        	 policeType=org.getPoliceType();
	        }
			statisData = statisDataService.statisCaseNum(orgType,industryType, districtJB,queryScope,districtCode,policeType);
		}
		return JSONObject.toJSONString(statisData);
	}
	
	/**
	 * 待办案件统计
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/daibanData")
	@ResponseBody
	public String daibanData(String queryScope,HttpServletRequest request){ 
        Map<String,Object> paramMap = new HashMap<String, Object>();
		User user = SystemContext.getCurrentUser(request);
		String orgType=user.getOrganise().getOrgType();
		StatisData statisData = new StatisData();
		//如果是检察院或牵头单位
		if(orgType.equals(Const.ORG_TYPE_JIANCHAYUAN) || orgType.equals(Const.ORG_TYPE_LIANGFALEADER)){
			String candidateGroup = String.valueOf(user.getPostId());
	        paramMap.put("userID", user.getUserId());
	        paramMap.put("candidateGroup", candidateGroup);
	        paramMap.put("queryScope", queryScope);
	        String districtId = user.getOrganise().getDistrictCode();
	        paramMap.put("districtId", com.ksource.liangfa.util.StringUtils.rightTrim0(districtId));
			//查询待办疑似涉嫌犯罪案件数量
			int crimeToDoNum = systemDAO.getCount(paramMap, null, "com.ksource.liangfa.mapper.CaseBasicMapper.getSuspectedCaseCount");
			//查询待办立案监督线索筛查案件
			CaseFilterExample example=new CaseFilterExample();
			List<CaseFilter> list=mybatisAutoMapperService.selectByExample(CaseFilterMapper.class, example, CaseFilter.class);
			int count=list.size();
			CaseFilter caseFilter=new CaseFilter();
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
			paramMap.put("districtCode", com.ksource.liangfa.util.StringUtils.rightTrim0(districtId));
			int filterToDoNum = systemDAO.getCount(paramMap, null, "com.ksource.liangfa.mapper.CaseBasicMapper.queryfilingSupervisionCount");
			statisData.setCrimeToDoNum(crimeToDoNum);
			statisData.setFilterToDoNum(filterToDoNum);
		}
		int todoNum = caseTodoService.getTodoCount(user.getOrgId()); 
		statisData.setToDoNum(todoNum);
		
		//查询公安机关行政拘留待办案件数量
		Organise org=orgService.getDeptByOrgCode(user.getOrgId());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("assignOrg", org.getOrgCode());
		int transferDetentionTodoNum=caseTodoService.getTransferDetentionTodoCount(map);
		statisData.setTransferDetentionTodoNum(transferDetentionTodoNum);
		//立案监督案件数量
		map.clear();
    	if(!Const.TOP_ORG_ID.equals(user.getOrganise().getUpOrgCode())) {//是否为顶级机构
    		if(Const.ORG_TYPE_JIANCHAYUAN.equals(orgType)) {//检察机关
    			map.put("createOrg", user.getOrgId());
    		}
    		if(Const.ORG_TYPE_GOGNAN.equals(orgType)) {//公安机关
    			map.put("assignOrg", user.getOrgId());
    		}
    	}
    	map.put("orgType", org.getOrgType());
		int lianSupTodoNum = caseTodoService.getLianSupTodoCount(map);
		statisData.setLianSupTodoNum(lianSupTodoNum);
		return JSONObject.toJSONString(statisData);
	}
	
	/**
	 * 待办案件统计(监察局首页使用)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/daibanData_jianchaju")
	@ResponseBody
	public String daibanData_jianchaju(String queryScope,HttpServletRequest request){ 
		User user = SystemContext.getCurrentUser(request);
		Integer orgId = user.getOrgId();
		//查询移送纪委案件数量
		int caseYisongNum = caseYisongjiweiService.getCaseYisongCount(orgId);
		StatisData statisData = new StatisData();
		statisData.setCaseYisongNum(caseYisongNum);
		return JSONObject.toJSONString(statisData);
	}
	
	/**
	 * 预警案件统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/warningData")
	@ResponseBody
	public String warningData(HttpServletRequest request){ 
		//获取当前用户行政区划(市、县取控制权限使用)
		User user = SystemContext.getCurrentUser(request);
		Organise org=user.getOrganise();
		Map<String,Object> param=new HashMap<String,Object>();
		String districtId=org.getDistrictCode();
		districtId = com.ksource.liangfa.util.StringUtils.rightTrim0(districtId);
		param.put("districtCode", districtId);
		//控制行政单位权限
		String orgPath="";
		if(org.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
			orgPath=org.getOrgPath();
		}
		//控制公安机关权限
		Integer acceptOrg = null;
        if(org.getOrgType().equals(Const.ORG_TYPE_GOGNAN) && user.getDistrictJB() == Const.DISTRICT_JB_3){
            acceptOrg = user.getDeptId(); 
        }
		//查询案件滞留案件
		Map<String,Integer> waringData = new HashMap<String, Integer>() ;
		//查询办理超时案件数量,1.查询超时配置设置 
		List<WarnCondition> warnConditionList = warnConditionService.queryAll();
		int timeOutCount = 0;
		int amountWarnCount = 0;
		int delayWarnCount = 0;
		if(warnConditionList != null && warnConditionList.size() > 0){
		    for(WarnCondition con:warnConditionList){
		    	//分角色查询预警信息
				if(org.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
					//超时预警案件查询
			        if(con.getWarnType().equals(Const.WARN_CONDITION_TYPE_1)){
			            timeOutCount = caseService.getTimeOutCount(districtId,con.getConditionFormula(),orgPath);
			            waringData.put("TIMEOUT_COUNT", timeOutCount); 
			        }
				}else if(org.getOrgType().equals(Const.ORG_TYPE_GOGNAN)){
					//超时预警案件查询
			        if(con.getWarnType().equals(Const.WARN_CONDITION_TYPE_1)){
			            timeOutCount = caseService.getTimeOutCount(districtId,con.getConditionFormula(),orgPath);
			            waringData.put("TIMEOUT_COUNT", timeOutCount); 
			        }
			        //案件滞留超时
			        if(con.getWarnType().equals(Const.WARN_CONDITION_TYPE_3)){
			            delayWarnCount = caseService.getDelayWarnCount(districtId,con.getConditionFormula(),acceptOrg);
			            waringData.put("DELAY_WARN_COUNT", delayWarnCount);
			        }
				}else{
					//超时预警案件查询
			        if(con.getWarnType().equals(Const.WARN_CONDITION_TYPE_1)){
			            timeOutCount = caseService.getTimeOutCount(districtId,con.getConditionFormula(),orgPath);
			            waringData.put("TIMEOUT_COUNT", timeOutCount); 
			        }
			        //涉案金额预警案件查询
			        if(con.getWarnType().equals(Const.WARN_CONDITION_TYPE_2)){
			            amountWarnCount = caseService.getAmountWarnCount(districtId,con.getConditionFormula());
			            waringData.put("AMOUNT_WARN_COUNT", amountWarnCount); 
			        }
			        //案件滞留超时
			        if(con.getWarnType().equals(Const.WARN_CONDITION_TYPE_3)){
			            delayWarnCount = caseService.getDelayWarnCount(districtId,con.getConditionFormula(),acceptOrg);
			            waringData.put("DELAY_WARN_COUNT", delayWarnCount);
			        }
				}
		    }
		}
		
		String data = JSONObject.toJSONString(waringData);
		return data;
	}
	
	/**
	 * 待办案件统计
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/daibanData_xingzheng")
	@ResponseBody
	public String daibanData_xingzheng(HttpServletRequest request){ 
		User user = SystemContext.getCurrentUser(request);
		Integer orgCode = user.getOrgId();
		CaseTodo caseTodo = caseTodoService.getTodoCountForXingzheng(orgCode);
		String data = JSONObject.toJSONString(caseTodo);
		return data;
	}
	
	/**
	 * 获得用户当天的登录次数
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login_count")
	@ResponseBody
	public String getTodayLoginCount(HttpServletRequest request){ 
		Map<String ,Object> map=new HashMap<String ,Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String newDate=sdf.format(new Date());
		map.put("optTime", newDate);
		Integer count=businessLogMapper.getTodayLoginCount(map);
		String data="";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("loginCount", count);
		data = jsonObject.toJSONString();
		return data;
	}
	
	
	/**
	 *  统计本年度、季度、月份市县两级案件数量
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/caseData_time")
	@ResponseBody
	public String caseData_time(HttpServletRequest request){ 
    	User user = SystemContext.getCurrentUser(request);
        String districtCode = user.getOrganise().getDistrictCode(); 
        Map<String,Object> map=new HashMap<String,Object>();
        String shortDistrictCode = StringUtils.rightTrim0(districtCode);
        map.put("districtCode", shortDistrictCode);
		StatisData statisData = new StatisData();
		statisData = statisDataService.statisCaseTotalNum(map);
		return JSONObject.toJSONString(statisData);
	}
	
	/**
	 * 查询通知公告信息(仅供大屏展示页面使用)
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/noticeListsForIMax")
	@ResponseBody
	public String noiceListForIMax(HttpServletRequest request){ 
		User user = SystemContext.getCurrentUser(request);
		Map<String,Object> params=new HashMap<String,Object>();
        params.put("userId", user.getUserId());
        params.put("orgId", user.getOrganise().getOrgCode());
		List<Notice> myNoticeList = noticeService.getMyNoticeList(params);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    if(myNoticeList.size()>0){
			for(Notice notice : myNoticeList){
				//日期格式化转换为字符串
				notice.setNoticeCreateTime(sdf.format(notice.getNoticeTime()));
			}
	    }
	    
		String noticeJson = "";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("noticeList", myNoticeList);
		noticeJson = jsonObject.toJSONString();
		return noticeJson;
	}
	
	
	   /**
     *大屏展示区域排名案件统计
     * @param bean
     * @param index
     * @param request
     * @param response
     */
    @RequestMapping(value="regionSortData")
    @ResponseBody
    public String regionSortData(String startTime,String endTime,String index){
        Map<String,String> params = new HashMap<String, String>();
        if (StringUtils.isNotBlank(startTime)) {
          startTime = startTime.replace("-", "");
        }
        if (StringUtils.isNotBlank(endTime)) {
          endTime = endTime.replace("-", "");
        }
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("index", index);
        
        List<Map<String,Object>> regionSortData =  chartService.queryRegionSortData(params);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("regionSortData", JSONArray.fromObject(regionSortData));
        return jsonObject.toJSONString();
    }
}
