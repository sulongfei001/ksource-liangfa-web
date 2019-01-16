/**
 * 
 */
package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.AdmdivLicenseApprove;
import com.ksource.liangfa.domain.AdmdivLicenseInfo;
import com.ksource.liangfa.domain.AdmdivLicenseReply;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.AdmdivLicenseService;
import com.ksource.syscontext.SystemContext;

/**
 * @author XT
 * 2013-1-7
 */
@Controller
@RequestMapping(value="admdiv_license")
public class AdmdivLicenseController {
	
	@Autowired
	AdmdivLicenseService admdivLicenseService;
	@Autowired
	SystemDAO systemDAO;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	
	@RequestMapping(value="add")
	public String add(HttpServletRequest request,AdmdivLicenseApprove approve,AdmdivLicenseInfo info,Map<String, Object> model,MultipartHttpServletRequest attachmentFiles){
		User currentUser = SystemContext.getCurrentUser(request);
		approve.setInputer(currentUser.getUserId());
		approve.setInputTime(new Date());
		info.setInputer(currentUser.getUserId());
		info.setInputTime(new Date());
		admdivLicenseService.add(approve, info, attachmentFiles);
		model.put("info", "已成功添加！");
		return "casehandle/admdivLicense/add";
	}
	
	@RequestMapping(value="manage")
	public String manage(Map<String, Object> model,AdmdivLicenseInfo info,String page,HttpServletRequest request){
		HttpSession session=request.getSession();
		session.setAttribute("licenseQueryParam", info);
		session.setAttribute("licenseQueryPage", page);
		PaginationHelper<AdmdivLicenseInfo> res = systemDAO.find(info, page);
		model.put("licenseList", res);
		model.put("page", page);
		return "casehandle/admdivLicense/manage";
	}
	
	
	@RequestMapping(value="reply")
	public String reply(AdmdivLicenseReply reply,HttpServletRequest request,Map<String, Object> model){
		HttpSession session=request.getSession();
		User currentUser = SystemContext.getCurrentUser(request);
		reply.setReplyUser(currentUser.getUserId());
		reply.setReplyTime(new Date());
		admdivLicenseService.reply(reply);
		return manage(model, (AdmdivLicenseInfo)session.getAttribute("licenseQueryParam"), (String)session.getAttribute("licenseQueryPage"), request);
	}
	
	@RequestMapping(value="query_reply")
	@ResponseBody
	public AdmdivLicenseReply queryReply(AdmdivLicenseReply reply,HttpServletRequest request){
		return admdivLicenseService.queryReply(reply);
	}
	
	
	@RequestMapping(value="detail")
	public String detail(Integer licenseId,Map<String, Object> model){
		AdmdivLicenseInfo license=admdivLicenseService.detail(licenseId);
		model.put("license", license);
		return "casehandle/admdivLicense/detail";
	}
	
	@RequestMapping(value = "back")
	public String back(ModelMap model, HttpServletRequest request) throws Exception {
		// 有查询条件,按查询条件返回
		HttpSession session=request.getSession();
		AdmdivLicenseInfo info= (AdmdivLicenseInfo)session.getAttribute("licenseQueryParam");
		String page=(String)session.getAttribute("licenseQueryPage");
		return this.manage(model, info, page,request);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder,WebRequest webRequest){
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
