package com.ksource.liangfa.web.specialactivity;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.ActivityCase;
import com.ksource.liangfa.domain.ActivityMember;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.domain.ProcKeyExample;
import com.ksource.liangfa.domain.SpecialActivity;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.liangfa.mapper.SpecialActivityMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.specialactivity.SpecialActivityService;
import com.ksource.syscontext.SystemContext;

/**
 * 此类为 专项活动查询控制器(用于查看参与专项活动的单位的案件)
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2012-7-19
 * time   上午9:34:19
 */
@Controller
@RequestMapping("activity/query")
public class ActivityQueryController {
	private static final String MAIN_VIEW = "activity/activityQuery";
	private static final String ACTIVITY_CASE_QUERY = "activity/activityCaseQuery";
	@Autowired
	SpecialActivityService specialActivityService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	
		/**
		 * @param acti
		 * @return
		 */
		@RequestMapping(value = "search")
		public ModelAndView search(SpecialActivity acti,HttpSession session) {
			ModelAndView view = new ModelAndView(MAIN_VIEW);
		    Map<String,Object> map = new HashMap<String,Object>();
            User currUser =SystemContext.getCurrentUser(session);
		    int orgId = currUser.getOrgId();
		    map.put("orgId",orgId);
            map.put("userId",currUser.getUserId());
			List<SpecialActivity> actiList = specialActivityService.find(acti,map);
			view.addObject("actiList", actiList);
			view.addObject("acti", acti);
			//view.addObject("isQuery",isQuery);
			return view;
		}
		/**查询案件*/
		@RequestMapping("queryCase/{id}")
		public String queryCase(@PathVariable Integer id,String page,ModelMap map,Date startTime,Date endTime,String orgIds,String orgNames){
			Map<String,Object> paramMap = new HashMap<String, Object>();
			
			SpecialActivity acti = mybatisAutoMapperService.selectByPrimaryKey(SpecialActivityMapper.class, id, SpecialActivity.class);
			if(startTime==null){
				startTime=acti.getStartTime();
			}
			if(endTime==null){
				endTime= acti.getEndTime();
			}
			paramMap.put("startTime",startTime);
			paramMap.put("endTime",endTime);
			//只查询处罚流程
			//paramMap.put("procKey",Const.CASE_CHUFA_PROC_KEY);
			paramMap.put("id", id);
			if(orgIds!=null&&orgIds.endsWith(",")){
				orgIds=orgIds.substring(0,orgIds.length()-1);
			}
			paramMap.put("orgIds",orgIds);
			//PaginationHelper<Case> caseList =specialActivityService.queryCase(page,paramMap);
			PaginationHelper<CaseBasic> caseList =specialActivityService.queryActivityCase(page,paramMap);
			List<ProcKey> procKeyList = mybatisAutoMapperService.selectByExample(
					ProcKeyMapper.class, new ProcKeyExample(), ProcKey.class);
			map.addAttribute("procKeyList", procKeyList);
			map.addAttribute("caseList",caseList);
			map.addAttribute("acti",acti);
			map.addAttribute("page",page);
			return ACTIVITY_CASE_QUERY;
		}
		@RequestMapping("loadActivityOrg/{id}")
		public void loadActivityOrg(@PathVariable Integer id,HttpServletResponse response){
			SpecialActivity acti = specialActivityService.findByPk(id);
			List<Integer> orgCodeList = new ArrayList<Integer>();
			for(ActivityMember mem:acti.getMemberList()){
				orgCodeList.add(mem.getMemberCode());
			}
			OrganiseExample example = new OrganiseExample();
			example.createCriteria().andOrgCodeIn(orgCodeList);
			List<Organise> orgList =mybatisAutoMapperService.selectByExample(OrganiseMapper.class, example, Organise.class);
			
			String trees = JsTreeUtils.loadLiangfaLeader(orgList);
			response.setContentType("application/json");

			PrintWriter out = null;

			try {
				out = response.getWriter();
				out.print(trees);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
		
		/**
		 * 查询专项活动
		 * @param response
		 */
		@RequestMapping("loadActivity/{id}")
		public void loadActivity(@PathVariable Integer id,HttpServletRequest request,HttpServletResponse response){
			User user = SystemContext.getCurrentUser(request);
			//Map<String,Object> params = new HashMap<String, Object>();
			//params.put("userId", user.getUserId());
			//	params.put("orgId", user.getOrgId());
			List<SpecialActivity> actiList = specialActivityService.find(new SpecialActivity(),new HashMap<String, Object>());
			//List<SpecialActivity> actiList = specialActivityService.find(new SpecialActivity(),params);
			String trees = JsTreeUtils.loadActivity(actiList,id);
			response.setContentType("application/json");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(trees);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
		
		/**
		 * 保存调整专项活动案件
		 * @param activityCase
		 * @param response
		 * @return
		 */
		@RequestMapping(value="saveChangeActivity")
		@ResponseBody
		public boolean saveChangeActivity(ActivityCase activityCase,HttpServletResponse response){
			SpecialActivity activity = mybatisAutoMapperService.selectByPrimaryKey(SpecialActivityMapper.class, activityCase.getActivityId(), SpecialActivity.class);
			CaseBasic c = mybatisAutoMapperService.selectByPrimaryKey(CaseBasicMapper.class, activityCase.getCaseId(), CaseBasic.class);
			boolean flag = false;
			if(activity!=null&&c!=null){
				if(c.getInputTime().getTime()>activity.getStartTime().getTime()&&c.getInputTime().getTime()<(activity.getEndTime().getTime()+24*60*60*1000)){
					 flag = specialActivityService.saveChangeActivity(activityCase);
				}
			}
			return flag;
		}
		
		@InitBinder
		public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
			webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
					new SimpleDateFormat("yyyy-MM-dd"), true));
		}
		

}