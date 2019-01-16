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
import com.ksource.liangfa.domain.IntegratedInformation;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.opinion.IntegratedInformationService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/opinion/integratedInformation")
public class IntegratedInformationController {
	@Autowired
	SystemDAO systemDao;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	IntegratedInformationService integratedInformationService;
	/** 用于保存查询条件 */
	private static final String INTEGRATED_INFORMATION_CONDITION=IntegratedInformationController.class.getName()+"conditionObjManage";
	/** 用于保存分页标识 */
	private static final String INTEGRATED_INFORMATION_PAGE_MARK=IntegratedInformationController.class.getName()+"pageManage";
	/** 进入案件信息 主页面 */
	private static final String INTEGRATED_INFORMATION_MAIN="/opinion/integratedInformation/main";
	/** 转发 ：案件信息 主页面 */
	private static final String INTEGRATED_INFORMATION_RETURN="redirect:/opinion/integratedInformation/main";
	/** 进入添加页面 */
	private static final String INTEGRATED_INFORMATION_ADD="/opinion/integratedInformation/add";
	/** 进入修改页面 */
	private static final String INTEGRATED_INFORMATION_UPDATE="/opinion/integratedInformation/update";
	/** 进入详情页面 */
	private static final String INTEGRATED_INFORMATION_DETAIL="/opinion/integratedInformation/detail";
	
	/** 进入修改页面 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Long infoId,Map<String,Object> model){
		IntegratedInformation integratedInformation=integratedInformationService.getById(infoId);
		//上传
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_INTEGRATED_INFORMATION)
			.andInfoIdEqualTo(Integer.parseInt(infoId.toString()));
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		model.put("publishInfoFiles", publishInfoFiles);
		model.put("integratedInformation", integratedInformation);
		return INTEGRATED_INFORMATION_UPDATE;
	}
	/** 进入案件详细页面 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Long infoId,Map<String,Object> model){
		IntegratedInformation integratedInformation=integratedInformationService.getById(infoId);
		//上传
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_INTEGRATED_INFORMATION)
		.andInfoIdEqualTo(Integer.parseInt(infoId.toString()));
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		model.put("publishInfoFiles", publishInfoFiles);
		model.put("integratedInformation", integratedInformation);
		return INTEGRATED_INFORMATION_DETAIL;
	}
	/**
	 * 修改案件信息
	 * @param caseInformation
	 * @param attachmentFile
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(IntegratedInformation integratedInformation,MultipartHttpServletRequest attachmentFile){
		integratedInformationService.updateById(integratedInformation, attachmentFile);
		return INTEGRATED_INFORMATION_RETURN;
	}
	/** 删除 */
	@RequestMapping("del")
	public String del(Long infoId){
		integratedInformationService.del(infoId);
		return INTEGRATED_INFORMATION_RETURN;
	}
	/** 批量删除 */
	@RequestMapping("/batch_delete")
	public String batch_delete(String[] check){
		if(check!=null){
			integratedInformationService.batchDelete(check);
		}
		return INTEGRATED_INFORMATION_RETURN;
	}
	
	/** 进入添加页面 */
	@RequestMapping(value="add" ,method=RequestMethod.GET)
	public String add(){
		return INTEGRATED_INFORMATION_ADD;
	}
	/** 保存执法人员信息 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(IntegratedInformation integratedInformation,HttpServletRequest request,MultipartHttpServletRequest attachmentFile){
		integratedInformation.setInfoId(Long.valueOf(systemDao
				.getSeqNextVal(Const.TABLE_INTEGRATED_INFORMATION)));
		User user=SystemContext.getCurrentUser(request);
		integratedInformation.setInputer(Long.parseLong(user.getUserId()));
		integratedInformation.setInputTime(new Date());
		integratedInformationService.save(integratedInformation, attachmentFile);
		return INTEGRATED_INFORMATION_RETURN;
	}
	
	/** 进入案件信息页面 */
	@RequestMapping("/main")
	public String main(HttpServletRequest request,IntegratedInformation integratedInformation,String page,Map<String,Object> model,HttpSession session){
		/*
		 * 权限控制
		 * 1.普通用户只查自己添加的信息
		 * 2.orgtype=2或者 ‘系统管理员’查看所有添加信息
		 */
		String orgType=SystemContext.getCurrentUser(request).getOrganise().getOrgType();
		User user=SystemContext.getCurrentUser(request);
		if(!orgType.equals(Const.ORG_TYPE_JIANCHAYUAN) && !user.getAccount().equals(Const.SYSTEM_ADMIN_ID)){
			integratedInformation.setInputer(Long.parseLong(user.getUserId()));
		}
		PaginationHelper<IntegratedInformation> integratedInformationList=integratedInformationService.queryPage(integratedInformation, page,null);
		model.put("integratedInformationList", integratedInformationList);
		model.put("integratedInformation", integratedInformation);
		// 第一次进入管理页面或空查询时把查询条件删除。
		if((integratedInformation.getTitle()==null)&&(page==null)){
			removeCondition(session, INTEGRATED_INFORMATION_CONDITION, INTEGRATED_INFORMATION_PAGE_MARK);
		}else{
			session.setAttribute(INTEGRATED_INFORMATION_CONDITION, integratedInformation);
			session.setAttribute(INTEGRATED_INFORMATION_PAGE_MARK, page);
			model.put("page", page);
		}
		return INTEGRATED_INFORMATION_MAIN;
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
