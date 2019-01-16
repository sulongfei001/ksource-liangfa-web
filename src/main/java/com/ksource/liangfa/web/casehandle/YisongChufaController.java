package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.dForm.DFormUtil;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseState;
import com.ksource.liangfa.domain.PenaltyReferCaseExt;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.system.PostService;
import com.ksource.liangfa.workflow.ActivitiUtil;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


/**
 * 此类为 移送处罚案件 控制器
 * PENALTY_REFER_CASE_EXT
 * @author zxl :)
 * @version 1.0   
 * date   2011-9-2
 * time   上午08:56:39
 */
@Controller
@RequestMapping("/casehandle/yisongCase")
public class YisongChufaController {
	private static final String UPDATE_VIWE = "casehandle/yisongchufa/yisongchufa_update";
	private static final String REDI_SEARCH_VIEW = "redirect:/casehandle/yisongCase/search";
	private static final String REDI_ADDUI_VIEW = "redirect:/casehandle/yisongCase/addUI?info=";
	private static final String ADD_VIEW = "casehandle/yisongchufa/yisongchufa_add";
	private static final String MAIN_VIEW = "casehandle/yisongchufa/yisongchufa_main";
	 /** 用于保存查询条件 */
    private static final String SEARCH_CONDITION = CaseConsultationController.class.getName()+"conditionObj";

    /**用于保存分页的标志*/
    private static final String PAGE_MARK = CaseConsultationController.class.getName() +"page";
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	CaseService caseService;
	@Autowired
	PostService postService;
	@Autowired
	TaskService taskService;

	@RequestMapping(value = "add")
	public String add(CaseBasic caseBasic, PenaltyReferCaseExt penaltyReferCaseExt,String casePartyJson,String assignTarget,MultipartHttpServletRequest attachmentFile,
			HttpServletRequest request) throws Exception {
		String path = REDI_ADDUI_VIEW;
		User user = SystemContext.getCurrentUser(request);
		caseBasic.setInputer(user.getUserId());
		caseBasic.setCaseState(String.valueOf(Const.YISONGCHUFA_STATE));
		Date currentDate = new Date();
		caseBasic.setInputTime(currentDate);
		caseBasic.setStartTime(currentDate);
		caseBasic.setLatestPocessTime(currentDate);
		caseBasic.setAmountInvolved(0.0000) ;
		caseBasic.setProcKey( Const.CASE_YISONGXINGZHENG_PROC_KEY);
		CaseState caseState = new CaseState();
		caseState.setChufaState(Const.CHUFA_STATE_NOTYET);
		caseState.setYisongState(Const.YISONG_STATE_NO);
		caseState.setLianState(Const.LIAN_STATE_NOTYET);
		caseState.setDaibuState(Const.DAIBU_STATE_NOTYET);
		caseState.setQisuState(Const.QISU_STATE_NOTYET);
		caseState.setPanjueState(Const.PANJUE_STATE_NOTYET);
		caseState.setJieanState(Const.JIEAN_STATE_NO);
		
		//流程变量集合
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(ActivitiUtil.VAR_ORG_CODE, assignTarget);
	
		ServiceResponse res = caseService.addYisongChufaCase(caseBasic, caseState,penaltyReferCaseExt, map, attachmentFile);
		
		if (res.getResult()) {
			path += "true";
		} else {
			path += "false";
		}
		if ("search".equals(request.getParameter("view"))) { // 根据前台条件跳转
			path = REDI_SEARCH_VIEW;
		}
		return path;
	}

	/** 进入案件界面 
	 * @throws Exception */
	@RequestMapping(value = "main")
	public String main(ModelMap model,HttpSession session,HttpServletRequest request){
		return this.search(model, new CaseBasic(), "1", request);
	}
	@RequestMapping(value = "addUI")
	public String addUI(ModelMap model, String info,
			HttpSession session) throws Exception {
		if (info != null) {
			String inf = null;
			if (info.equals("true")) {
				inf = "信息添加成功";
			} else if (info.equals("false")) {
				inf = "信息添加失败";
			}
			model.addAttribute("info", inf);
		}
		model.addAttribute("procKey", Const.CASE_YISONGXINGZHENG_PROC_KEY);
		//TODO:录入权限验证,根据录入节点的机构类型(待完善)来判定录入权限
		//判断是否需要选择提交机构(提交给自己)
		Map<String, String> map = DFormUtil.getProcDefIdAndInputerTargetTaskDef(Const.CASE_YISONGXINGZHENG_PROC_KEY);
		String procDefId =  map.get("procDefId");
		String targetTaskDefId = map.get("targetTaskDefId");
		boolean needAssignTarget = DFormUtil.needAssignTarget(procDefId, targetTaskDefId);
		model.addAttribute("needAssignTarget", needAssignTarget);
		model.addAttribute("targetTaskDefId", targetTaskDefId);
		model.addAttribute("procDefId", procDefId);
		return ADD_VIEW;
	}

	@RequestMapping(value = "search")
	public String search(ModelMap model, CaseBasic caseBasic, String page,
			HttpServletRequest request){
			User user = SystemContext.getCurrentUser(request);
			caseBasic.setInputer(user.getUserId());
			caseBasic.setProcKey(Const.CASE_YISONGXINGZHENG_PROC_KEY);
			PaginationHelper<CaseBasic> caseBasicList = caseService.find(caseBasic, page,null);
			model.addAttribute("caseBasicList", caseBasicList);
			
			// 保存查询条件,用于返回使用
			request.getSession().setAttribute(SEARCH_CONDITION, caseBasic);
			request.getSession().setAttribute(PAGE_MARK, page);
			model.addAttribute("page", page);
			return MAIN_VIEW;
	}
	// 案件模块 返回
	@RequestMapping(value = "back")
	public String back(ModelMap model, HttpServletRequest request) throws Exception {
		// 有查询条件,按查询条件返回
		CaseBasic caseBasic;
		String page ;
		HttpSession session = request.getSession();
		if (session.getAttribute(SEARCH_CONDITION) != null) {
			caseBasic = (CaseBasic) session.getAttribute(SEARCH_CONDITION);
		} else {
			caseBasic = new CaseBasic();
			caseBasic.setProcKey("noCaseInfo");//如果没有保存的查询条件就这样来。
		}
		if (session.getAttribute(PAGE_MARK) != null) {
			page = (String)session.getAttribute(PAGE_MARK);
		}else{
			page="1";
		}
		return this.search(model, caseBasic, page, request);
	}
	//进入案件　修改页面
	@RequestMapping(value="updateUI",method=RequestMethod.GET)
	public String updateUI(String caseId,ModelMap model){
		Map<String,Object> caseMap = new HashMap<String, Object>();
		caseMap = caseService.findPenaltyReferCaseById(caseId);
		model.addAttribute("caseBasic", caseMap.get("caseBasic"));
		model.addAttribute("penaltyReferCaseExt",caseMap.get("penaltyReferCaseExt"));
		if(caseMap.get("caseAttachment") != null){
			model.addAttribute("caseAttachment",caseMap.get("caseAttachment"));
		}
		return UPDATE_VIWE;
	}
	//修改案件　　
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String update(CaseBasic caseBasic,PenaltyReferCaseExt penaltyReferCaseExt, MultipartHttpServletRequest caseAttachmentFile,HttpServletRequest request) throws Exception {
		caseService.updatepenaltyReferCase(caseBasic,penaltyReferCaseExt,caseAttachmentFile);
		return REDI_SEARCH_VIEW;
		
	}
	
	/** 删除附件 */
	@ResponseBody
	@RequestMapping(value="delFile/{caseId}/{fileType}",method=RequestMethod.GET)
	public void del(@PathVariable String caseId,@PathVariable int fileType){
		CaseBasic caseBasic = caseService.findCaseById(caseId);
		caseBasic.setCaseId(caseId);
/*		if(fileType==Const.CASE_DETAIL_FILE){
			caseBasic.setCaseDetailFileName("");
			caseBasic.setCaseDetailFilePath(null);
		}
		FileUtil.deleteFile(caseBasic.getCaseDetailFilePath()); //删除本地磁盘附件
*/	    caseService.update(caseBasic,null);
	}
	@RequestMapping(value="checkCaseState/{caseId}")
	@ResponseBody
	public ServiceResponse checkCaseState(@PathVariable String caseId){
		ServiceResponse res = new ServiceResponse(true,"");
		CaseBasic caseBasic =mybatisAutoMapperService.selectByPrimaryKey(CaseBasicMapper.class, caseId, CaseBasic.class);
		if(!caseBasic.getCaseState().equals(String.valueOf(Const.CASE_STATE_SHENGHE))){
			res.setingError("本案件已通过审核,不能修改");
		}
		return res;
	}
   
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
