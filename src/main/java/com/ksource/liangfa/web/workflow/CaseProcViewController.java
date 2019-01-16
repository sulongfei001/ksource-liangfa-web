package com.ksource.liangfa.web.workflow;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.ClueInfoService;
import com.ksource.liangfa.service.system.AccuseInfoService;
import com.ksource.liangfa.service.workflow.WorkflowService;
import com.ksource.liangfa.workflow.ProcessFactory;
import com.ksource.liangfa.workflow.stepView.CaseProcStepView;
import com.ksource.liangfa.workflow.view.CaseProcView;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 案件详情查看
 * @author rengeng
 *
 */
@Controller
@RequestMapping("/workflow/caseProcView")
public class CaseProcViewController {

	@Autowired
	CaseService caseService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	TaskService taskService;
	@Autowired
	AccuseInfoService accuseInfoService;
	@Autowired
	ClueInfoService clueInfoService;
	/**
	 * 查看案件详情（案件流程信息）
	 * @param caseId	案件id
	 * @return
	 */
	@RequestMapping("{caseId}")
	@LogBusiness(operation="查看案件详情",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type = LogConst.LOG_DB_OPT_TYPE_SELECT, target_domain_mapper_class = Object.class,target_domain_position=0)
	public ModelAndView caseProcView(@PathVariable String caseId,String viewStepId,String procKey,HttpSession session){
		if(StringUtils.isBlank(procKey)){
			procKey=Const.CASE_CHUFA_PROC_KEY;
		}
		CaseProcView caseProcView=ProcessFactory.createCaseProcView(procKey,caseId,viewStepId);
		//获取当前登录用户的组织机构类型
		String currentOrgType=SystemContext.getCurrentUser(session).getOrganise().getOrgType();
		ModelAndView mv=caseProcView.getView();
		mv.addObject("currentOrgType",currentOrgType);
		//查询案件关联线索
		CaseBasic caseInfo=caseService.findCaseById(caseId);
		if(caseInfo!=null&&caseInfo.getClueId()!=null){
			ClueInfo clueInfo=clueInfoService.selectByPrimaryKey(caseInfo.getClueId());
			mv.addObject("clueInfo",clueInfo);
		}
		
		return mv;
	}
	
	@RequestMapping("caseStepView/{stepId}")
	public ModelAndView caseStepView(@PathVariable Long stepId,String divId,HttpSession session){
		CaseStep caseStep = workflowService.queryStepInfoAndDeal(stepId);
		CaseProcStepView caseProcStepView = ProcessFactory.createCaseProcStepView(caseStep);
		ModelAndView model = caseProcStepView.getModelAndView();
	    model.addObject("divId", divId);
	    model.addObject("stepId", stepId);
	    //caseStep对象包含了本步骤的所有信息
	    model.addObject("caseStep", caseProcStepView.getCaseStep());
	    //获取当前登录用户的组织机构类型
	    String currentOrgType=SystemContext.getCurrentUser(session).getOrganise().getOrgType();
	    model.addObject("currentOrgType", currentOrgType);
		return model;
	}
	
	/**
	 * 根据罪名id获取办案助手信息
	 */
	/*@RequestMapping("caseHealperView/{accuseId}")
	public ModelAndView caseHealperView(@PathVariable int accuseId,String divId){
		AccuseInfo info=new AccuseInfo();
		info=accuseInfoService.findById(accuseId);
		ModelAndView view = new ModelAndView("workflow/caseProcView/caseHelperView");
		view.addObject("law", info.getLaw());
		view.addObject("accuseName", info.getName());
		view.addObject("divId", divId);
		view.addObject("accuseId", accuseId);
		return view;
	}*/
	
	/**
	 * 查看案件详情（案件流程信息）
	 * @param caseId	案件id
	 * @return
	 */
	@RequestMapping("docCaseProcView")
	@LogBusiness(operation="查看案件详情",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type = LogConst.LOG_DB_OPT_TYPE_SELECT, target_domain_mapper_class = Object.class,target_domain_position=0)
	public ModelAndView docCaseProcView(String caseId,String viewStepId,String procKey,
			String drillType,Integer num,String indexList,String districtId,Date startTime,
    		Date endTime,String requestURL,CaseBasic caseBasic,HttpSession session){
		if(StringUtils.isBlank(procKey)){
			procKey=Const.CASE_CHUFA_PROC_KEY;
		}
		CaseProcView caseProcView=ProcessFactory.createCaseProcView(procKey,caseId,viewStepId);
		//获取当前登录用户的组织机构类型
		String currentOrgType=SystemContext.getCurrentUser(session).getOrganise().getOrgType();
		//格式化时间参数
      	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String startTimeS="";
        String endTimeS="";
        if(startTime!=null){
        	startTimeS=simpleDateFormat.format(startTime);
        }
        if(endTime!=null){
        	endTimeS=simpleDateFormat.format(endTime);
        }
		return caseProcView.getDocView().addObject("currentOrgType", currentOrgType)
				.addObject("drillType", drillType).addObject("num", num)
				.addObject("districtId", districtId).addObject("startTime", startTimeS)
        		.addObject("endTime", endTimeS).addObject("requestURL", requestURL)
        		.addObject("indexList", indexList).addObject("caseBasic", caseBasic);
	}
	
	 @InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
			new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
