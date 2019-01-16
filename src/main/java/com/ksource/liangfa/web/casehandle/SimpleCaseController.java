package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.ksource.liangfa.domain.SimpleCase;
import com.ksource.liangfa.domain.SimpleCaseExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.SimpleCaseMapper;
import com.ksource.liangfa.mapper.UkeyUserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.SimpleCaseService;
import com.ksource.syscontext.SystemContext;

/**
 * 类说明 简易案件控制器
 * 
 * @author Guojianyong
 * @date 2012-7-9
 */
@Controller
@RequestMapping("/casehandle/simplecase/")
public class SimpleCaseController {
	private static final String SimpleCaseSearch = "redirect:/casehandle/simplecase/search";
	@Autowired
	MybatisAutoMapperService mapperService;
	@Autowired
	private SimpleCaseService simpleCaseService;
	@Autowired
	UkeyUserMapper keyMapper;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM"), true));
	}

	@RequestMapping("/{caseId}")
	@ResponseBody
	public SimpleCase getSimpleCase(@PathVariable String caseId) {
		SimpleCase simpleCase = mapperService.selectByPrimaryKey(
				SimpleCaseMapper.class, caseId, SimpleCase.class);
		return simpleCase;
	}

	@RequestMapping("add")
	public String add(SimpleCase simpleCase, HttpServletRequest request) {
		User user = SystemContext.getCurrentUser(request);
		String inputer = user.getUserId();
		simpleCase.setInputer(inputer);
		simpleCase.setOrgId(user.getOrgId());
		Date now = new Date();
		simpleCase.setInputTime(now);
		simpleCaseService.insertSimpleCase(simpleCase);
		String page = getSearchPage(request);
		return SimpleCaseSearch + "?division=false&info=0" +(page==null?"":"&page="+page) ;
	} 

	@RequestMapping(value = "delete/{caseId}")
	public String delete(@PathVariable String caseId, HttpServletRequest request) {
		simpleCaseService.deleteByPrimaryKey(caseId);
		return SimpleCaseSearch+ "?division=false&info=1";
	}

	@RequestMapping("update")
	public String update(SimpleCase simpleCase, HttpServletRequest request) {
		User user = SystemContext.getCurrentUser(request);
		simpleCase.setOrgId(user.getOrgId());
		simpleCaseService.updateByPrimaryKey(simpleCase);
		return SimpleCaseSearch + "?division=false&info=2";
	}

	@RequestMapping("checkCaseTime")
	@ResponseBody
	public boolean checkCaseTime(Date caseTime, HttpServletRequest request) {
		User user = SystemContext.getCurrentUser(request);
		SimpleCaseExample sce = new SimpleCaseExample();
		sce.createCriteria().andOrgIdEqualTo(user.getOrgId())
				.andCaseTimeEqualTo(caseTime);
		int count = mapperService.countByExample(SimpleCaseMapper.class, sce);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	private void saveSearchSimpleCase(HttpServletRequest request,
			SimpleCase simpleCase) {
		request.getSession().setAttribute(
				this.getClass().getName() + "searchSimpleCase", simpleCase);
	}

	private SimpleCase getSearchSimpleCase(HttpServletRequest request) {
		return (SimpleCase) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchSimpleCase"));
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
