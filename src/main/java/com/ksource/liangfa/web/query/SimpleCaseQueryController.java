package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.SimpleCase;
import com.ksource.liangfa.service.casehandle.SimpleCaseService;

/**
 *@author wangzhenya
 *@2012-10-16 下午3:29:10
 */
@Controller
@RequestMapping(value = "query/simpleCaseQuery")
public class SimpleCaseQueryController {
	
	private static final String MAIN_VIEW="querystats/simpleCaseQuery";
	
	@Autowired
	private SimpleCaseService simpleCaseService;
	/**
	 * 跳转到简易案件的查询页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "main")
	public ModelAndView main(HttpServletRequest request){
		ModelAndView view = new ModelAndView(MAIN_VIEW);
		return view;
	}
	
	/**
	 * 根据组织机构和时间查询简易案件
	 * @param simpleCase
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "search")
	public ModelAndView search(SimpleCase simpleCase,String page){
		ModelAndView view = new ModelAndView(MAIN_VIEW);
		PaginationHelper<SimpleCase> simpleCaseList = simpleCaseService.findSimpleCase(simpleCase, page);
		view.addObject("simpleCaseList", simpleCaseList);
		view.addObject("simpleCase", simpleCase);
		view.addObject("page", page);
		return view;
	}

	//进行日期转换格式操作
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM"), true));
	}
}
