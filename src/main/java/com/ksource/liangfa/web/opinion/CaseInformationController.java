package com.ksource.liangfa.web.opinion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseInformation;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.opinion.CaseInformationService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/opinion/caseInformation")
public class CaseInformationController {
	@Autowired
	SystemDAO systemDao;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	CaseInformationService caseInformationService;
	/** 用于保存查询条件 */
	private static final String CASE_INFORMATION_CONDITION=CaseInformationController.class.getName()+"conditionObjManage";
	/** 用于保存分页标识 */
	private static final String CASE_INFORMATION_PAGE_MARK=CaseInformationController.class.getName()+"pageManage";
	/** 进入案件信息 主页面 */
	private static final String CASE_INFORMATION_MAIN="/opinion/caseInformation/main";
	/** 转发 ：案件信息 主页面 */
	private static final String CASE_INFORMATION_RETURN="redirect:/opinion/caseInformation/main";
	/** 进入添加页面 */
	private static final String CASE_INFORMATION_ADD="/opinion/caseInformation/add";
	/** 进入修改页面 */
	private static final String CASE_INFORMATION_UPDATE="/opinion/caseInformation/update";
	/** 进入详情页面 */
	private static final String CASE_INFORMATION_DETAIL="/opinion/caseInformation/detail";
	
	/** 进入修改页面 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Long infoId,Map<String,Object> model){
		CaseInformation caseInformation=caseInformationService.getById(infoId);
		//上传
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_CASE_INFORMATION)
			.andInfoIdEqualTo(Integer.parseInt(infoId.toString()));
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		model.put("publishInfoFiles", publishInfoFiles);
		model.put("caseInformation", caseInformation);
		return CASE_INFORMATION_UPDATE;
	}
	/** 进入案件详细页面 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Long infoId,Map<String,Object> model){
		CaseInformation caseInformation=caseInformationService.getById(infoId);
		//上传
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_CASE_INFORMATION)
		.andInfoIdEqualTo(Integer.parseInt(infoId.toString()));
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		model.put("publishInfoFiles", publishInfoFiles);
		model.put("caseInformation", caseInformation);
		return CASE_INFORMATION_DETAIL;
	}
	/**
	 * 修改案件信息
	 * @param caseInformation
	 * @param attachmentFile
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(CaseInformation caseInformation,MultipartHttpServletRequest attachmentFile){
		caseInformationService.updateById(caseInformation, attachmentFile);
		return CASE_INFORMATION_RETURN;
	}
	/** 删除 */
	@RequestMapping("del")
	public String del(Long infoId){
		caseInformationService.del(infoId);
		return CASE_INFORMATION_RETURN;
	}
	/** 批量删除 */
	@RequestMapping("/batch_delete")
	public String batch_delete(String[] check){
		if(check!=null){
			caseInformationService.batchDelete(check);
		}
		return CASE_INFORMATION_RETURN;
	}
	
	/** 进入添加页面 */
	@RequestMapping(value="add" ,method=RequestMethod.GET)
	public String add(){
		return CASE_INFORMATION_ADD;
	}
	/** 保存执法人员信息 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(CaseInformation caseInformation,HttpServletRequest request,MultipartHttpServletRequest attachmentFile){
		caseInformation.setInfoId(Long.valueOf(systemDao
				.getSeqNextVal(Const.TABLE_CASE_INFORMATION)));
		User user=SystemContext.getCurrentUser(request);
		caseInformation.setInputer(Long.parseLong(user.getUserId()));
		caseInformation.setInputTime(new Date());
		caseInformationService.save(caseInformation, attachmentFile);
		return CASE_INFORMATION_RETURN;
	}
	
	/** 进入案件信息页面 */
	@RequestMapping("/main")
	public String main(HttpServletRequest request,CaseInformation caseInformation,String page,Map<String,Object> model,HttpSession session){
		/*
		 * 权限控制
		 * 1.普通用户只查自己添加的信息
		 * 2.orgtype=2或者 ‘系统管理员’查看所有添加信息
		 */
		String orgType=SystemContext.getCurrentUser(request).getOrganise().getOrgType();
		User user=SystemContext.getCurrentUser(request);
		if(!orgType.equals(Const.ORG_TYPE_JIANCHAYUAN) && !user.getAccount().equals(Const.SYSTEM_ADMIN_ID)){
			caseInformation.setInputer(Long.parseLong(user.getUserId()));
		}
		PaginationHelper<CaseInformation> caseInformationList=caseInformationService.queryPage(caseInformation, page,null);
		model.put("caseInformationList", caseInformationList);
		model.put("caseInformation", caseInformation);
		// 第一次进入管理页面或空查询时把查询条件删除。
		if((caseInformation.getTitle()==null)&&(page==null)){
			removeCondition(session, CASE_INFORMATION_CONDITION, CASE_INFORMATION_PAGE_MARK);
		}else{
			session.setAttribute(CASE_INFORMATION_CONDITION, caseInformation);
			session.setAttribute(CASE_INFORMATION_PAGE_MARK, page);
			model.put("page", page);
		}
		return CASE_INFORMATION_MAIN;
	}
	
	private void removeCondition(HttpSession session, String con,
			String pageMark) {
		if (session.getAttribute(con) != null) {
			session.removeAttribute(con);
		}

		if (session.getAttribute(pageMark) != null) {
			session.removeAttribute(pageMark);
		}
	}
	//初始化日期参数
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
