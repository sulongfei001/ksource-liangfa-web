package com.ksource.liangfa.web.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CompanyLib;
import com.ksource.liangfa.domain.CompanyLibExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CompanyLibMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.PeopleCompanyLibService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

/**
 * 企业库<br>
 * 
 * @author gengzi
 * @data 2012-3-30
 */
@Controller
@RequestMapping("/system/companyLib")
public class CompanyLibController {

	private static final String SEACHER_STRING = "redirect:/system/companyLib/search" ;
	/** 用于保存查询条件 */
	
	@Autowired
	MybatisAutoMapperService mapperService;
	@Autowired
	PeopleCompanyLibService libService;
	@Autowired
	UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@RequestMapping("/{rs}")
	@ResponseBody
	public String getCompany(@PathVariable String rs) {
		CompanyLib companyLib =new CompanyLib();
		companyLib = mapperService.selectByPrimaryKey(
				CompanyLibMapper.class, rs, CompanyLib.class);
		String companyLibJson=JSON.toJSONString(companyLib);
		return companyLibJson;
	}

	// 搜索企业库
	/**
	 * String division 用于区别是查询操作还是（添加、修改、删除）
	 */
	@RequestMapping(value = "search")
	public ModelAndView search(Boolean division,CompanyLib companyLibFilter, String page,String info, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/companylib/companylib");
		division = division==null? true : division ;
		if(division) { //查询操作
			saveSearchPeople(request, companyLibFilter);
			saveSearchPage(request, page);
		}else { // 不是查询操作
			companyLibFilter = this.getSearchpeople(request) ;
		}
		PaginationHelper<CompanyLib> companyList = libService.findCompany(companyLibFilter, page);
		view.addObject("companyList", companyList);
		view.addObject("companyLibFilter", companyLibFilter);
		if (StringUtils.isNotBlank(info)) {
			view.addObject("info", info);
		}
		view.addObject("page", page);
		return view;
	}
	
	@RequestMapping(value="addUI")
	public ModelAndView addUI() {
		ModelAndView view = new ModelAndView("system/companylib/companylib_add");
		return view;
	}
	// 新增企业库信息
	@RequestMapping(value = "add")
	public String add(CompanyLib companyLib, HttpServletRequest request)
			throws Exception {
		String path = SEACHER_STRING;
		String addView = request.getParameter("view");
		User user = SystemContext.getCurrentUser(request);
		String inputer = user.getUserId();
		companyLib.setInputer(inputer);
		Date now = new Date();
		companyLib.setInputTime(now);
		companyLib.setRegistractionNum(companyLib.getRegistractionNum().trim());
		libService.insertCompany(companyLib);
		//view.addObject("info", "新增企业库" + companyLib.getName()+ "成功!") ;
		if ("add".equals(addView)) { // 根据前台条件跳转
			path = "redirect:/system/companylib/companylib_add";
		}
		return ResponseMessage.addPromptTypeForPath(path, PromptType.add);
	}
	
	//企业库修改页面
	@RequestMapping(value="companylibUpdateUI/{regNo}")
	public ModelAndView companylibUpdateUI(@PathVariable String regNo,HttpServletRequest request){
		ModelAndView view = new ModelAndView("system/companylib/companylib_update");
		CompanyLib companyLib = mapperService.selectByPrimaryKey(CompanyLibMapper.class, regNo, CompanyLib.class);
		
		view.addObject("companyLib", companyLib);
		return view;
	}

	// 修改企业库信息
	@RequestMapping(value = "update")
	public String update(CompanyLib companyLib, HttpServletRequest request)
			throws Exception {
		companyLib.setRegistractionNum(companyLib.getRegistractionNum().trim());
		libService.updateCompanyByPrimaryKey(companyLib);
		return ResponseMessage.addPromptTypeForPath(SEACHER_STRING, PromptType.update);
	}

	// 删除企业库信息
	@RequestMapping(value = "delete/{regNo}")
	public String delete(@PathVariable String regNo,HttpServletRequest request) throws Exception {
		libService.deleteCompanyByPrimaryKey(regNo.trim());
		String page = getSearchPage(request);
        String path=SEACHER_STRING + "?division=false&info=1";
        if(page!=null){
             path+="&page=" + page;
        }
		return  path;
	}

	@RequestMapping(value = "checkRegNo")
	@ResponseBody
	public boolean checkRegNo(String registractionNum) {
		CompanyLibExample example = new CompanyLibExample();
		example.createCriteria().andRegistractionNumEqualTo(
				registractionNum.trim());
		int size = mapperService
				.countByExample(CompanyLibMapper.class, example);
		if (size > 0) {
			return false;
		} else {
			return true;
		}
	}

	// 显示企业库详细信息
	@RequestMapping(value = "showDetalis/{registractionNum}")
	public ModelAndView showDetalis(@PathVariable String registractionNum)
			throws Exception {

		ModelAndView view = new ModelAndView(
				"system/companylib/companylibdetalis");
		CompanyLib companyLib = mapperService.selectByPrimaryKey(
				CompanyLibMapper.class, registractionNum, CompanyLib.class);
		String userId = companyLib.getInputer();

		if (StringUtils.isNotBlank(userId)) {
			User user = userService.find(userId);
			if(user!=null){
				companyLib.setInputeName(user.getUserName());
			}
		}
		List<CaseCompany> caseCompanyList = libService
				.getCaseCompanyHistoryCase(registractionNum);
		if (caseCompanyList != null) {
			List<CaseBasic> caseList = new ArrayList<CaseBasic>();
			for (CaseCompany caseCompany : caseCompanyList) {
				CaseBasic caseBasic = mapperService.selectByPrimaryKey(CaseBasicMapper.class,
						caseCompany.getCaseId(), CaseBasic.class);
				caseList.add(caseBasic);
			}
			view.addObject("caselist", caseList);
		}
		view.addObject("company", companyLib);
		return view;
	}

	//  返回
	@RequestMapping(value = "back")
	public ModelAndView back(HttpServletRequest request) throws Exception {
		// 有查询条件,按查询条件返回
		CompanyLib companyLib =getSearchpeople(request);
		String page = getSearchPage(request);
		if(companyLib==null){
			companyLib = new CompanyLib();
		}
		if(StringUtils.isEmpty(page)){
				page = "1";
			}	
		return this.search(true,companyLib,page,"",request);
	}
	
	private void saveSearchPeople(HttpServletRequest request,
			CompanyLib companyLib) {
		request.getSession().setAttribute(
				this.getClass().getName() + "searchCompany", companyLib);
	}

	private CompanyLib getSearchpeople(HttpServletRequest request) {
		return (CompanyLib) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchCompany"));
	}

	private void saveSearchPage(HttpServletRequest request, String page) {
		request.getSession().setAttribute(
				this.getClass().getName() + "searchPage", page);
	}

	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPage"));
	}
}
