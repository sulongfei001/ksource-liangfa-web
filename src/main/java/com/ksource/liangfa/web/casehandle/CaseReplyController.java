package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseReply;
import com.ksource.liangfa.domain.CaseReplyExample;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseReplyService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 此类为 案件批复 控制器
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2012-3-13
 * time   上午11:17:01
 */
@Controller
@RequestMapping("caseReply")
public class CaseReplyController {
	private static final String MAIN_VIEW = "casehandle/caseReply/caseReplyMain";
	 /** 用于保存查询条件 */
    private static final String SEARCH_CONDITION = CaseReplyController.class.getName()+"conditionObj";

    /**用于保存分页的标志*/
    private static final String PAGE_MARK = CaseReplyController.class.getName() +
        "page";
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	CaseService caseService;
	@Autowired
	CaseReplyService caseReplyService;
	@Autowired
	private OrgService orgService;
	
	@RequestMapping("main")
	public String main(CaseBasic case1,ModelMap map,String page,HttpSession session){
		//删除条件
		if (page==null) {
            session.removeAttribute(SEARCH_CONDITION);
            session.removeAttribute(PAGE_MARK);
        }else{
        	 session.setAttribute(SEARCH_CONDITION, case1);
             session.setAttribute(PAGE_MARK, page);
             map.addAttribute("page", page);
        }
		map.addAttribute("caseList", getCaseReplys(case1, page,null));
		return MAIN_VIEW;
	}
	@RequestMapping("lookup")
	public String lookup(CaseBasic case1,ModelMap map,String page,HttpSession session){
		case1.setIsReply(Const.CASE_REPLY_YES);
		User user = SystemContext.getCurrentUser(session);
		Map<String,Object> paramMap = new HashMap<String,Object>();
//		行政机关
		if(!Const.SYSTEM_ADMIN_ID.equals(user.getAccount())&&Const.ORG_TYPE_XINGZHENG.equals(user.getOrganise().getOrgType()) && !user.getOrganise().isLiangfaLeaderOrg()){
			paramMap.put("orgCode", user.getOrganise().getOrgCode());
		}
		map.addAttribute("caseList", getCaseReplys(case1, page,paramMap));
		map.addAttribute("page",page);
		return "casehandle/caseReply/caseReplyLookup";
	}
	private PaginationHelper<CaseBasic> getCaseReplys(CaseBasic case1, String page,Map<String, Object> paramMap) {
		case1.setChufaState(Const.CHUFA_STATE_YES);
		PaginationHelper<CaseBasic> caseList =caseReplyService.find(case1, page,paramMap);
		return caseList;
	}
	
	@RequestMapping("addUI")
	public String addUI(String caseId,ModelMap map){
		map.addAttribute("caseInfo", getCaseReply(caseId));
		return "casehandle/caseReply/caseReplyAdd";
	}

	private CaseBasic getCaseReply(String caseId) {
		CaseReplyExample caseReplayexample = new CaseReplyExample();
		caseReplayexample.createCriteria().andCaseIdEqualTo(caseId);
		CaseBasic caseInfo=mybatisAutoMapperService.selectByPrimaryKey(CaseBasicMapper.class, caseId, CaseBasic.class);
		List<CaseReply> caseReplyList =caseReplyService.find(caseId);
		caseInfo.setCaseReplyList(caseReplyList);
		return caseInfo;
	}
	@RequestMapping("add")
	public String add(CaseReply reply ,HttpServletRequest request,@RequestParam(value = "attachment", required = false)
    MultipartFile attachmentFile){
		if (attachmentFile != null && !attachmentFile.isEmpty()) {
			// 1.上传文件
			UpLoadContext upLoad = new UpLoadContext(
					new UploadResource());
			String url = upLoad.uploadFile(attachmentFile, null);
			// 2.添加批复
			String fileName = attachmentFile.getOriginalFilename();
			reply.setAttachmentPath(url);
			reply.setAttachmentName(fileName);
		}
		reply.setInputer(SystemContext.getCurrentUser(request).getUserId());
		reply.setInputTime(new Date());
		CaseBasic case1 = mybatisAutoMapperService.selectByPrimaryKey(CaseBasicMapper.class, reply.getCaseId(), CaseBasic.class);
		reply.setCaseNo(case1.getCaseNo());
		reply.setId(systemDAO.getSeqNextVal(Const.TABLE_CASE_CONSULTATION_CONTENT));
		caseReplyService.insert(reply);
		return "redirect:/caseReply/back?backType=main";
	}
	@RequestMapping("detail")
	public String detail(String caseId,String backType,ModelMap map){
		map.addAttribute("caseInfo", getCaseReply(caseId));
		map.addAttribute("backType", backType);
		return "casehandle/caseReply/caseReplyDetail";
	}
	
	// 用户模块 返回
    @RequestMapping(value = "back")
    public String back(HttpSession session,ModelMap map,String backType) {
        // 有查询条件,按查询条件返回
        CaseBasic case1;
        String page;

        if (session.getAttribute(SEARCH_CONDITION) != null) {
            case1 = (CaseBasic) session.getAttribute(SEARCH_CONDITION);
        } else {
            case1 = new CaseBasic();
        }
        if (session.getAttribute(PAGE_MARK) != null) {
            page = (String) session.getAttribute(PAGE_MARK);
        } else {
            page = null;
        }
        if("main".equals(backType)){
        	return this.main(case1,map,page,session);
        }else{
        	return this.lookup(case1, map, page,session);
        }
    }
    
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
