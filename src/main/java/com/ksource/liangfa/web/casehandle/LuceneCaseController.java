package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseForLucene;
import com.ksource.liangfa.service.casehandle.CaseIndexService;

@Controller
@RequestMapping("/lucene/search/")
public class LuceneCaseController {
	
	@Autowired
	private CaseIndexService caseIndexService;
	
	private static final String MANAGE_MARK = "caseManage";
	
	@RequestMapping(value="caseSearch")
	public String caseSearch(CaseBasic caseBasic,String indexPosition,String keywords,String page,Map<String, Object> model, String back, HttpSession session){
		return "/casehandle/lucene/caseSearch";
	}
	
	/**
	 * @param caseBasic
	 * @param indexPosition 索引位置
	 * @param keywords
	 * @param page
	 * @param model
	 * @param back
	 * @param session
	 * @return
	 */
	@RequestMapping(value="caseList")
	public String caseList(CaseForLucene caseBasic,String indexPosition,String keywords,String searchFieldAry,String page,Map<String, Object> model, String back, HttpSession session){
		if(StringUtils.isBlank(indexPosition) && StringUtils.isBlank(keywords)){
			PaginationHelper<CaseForLucene> paginationHelper = new PaginationHelper<CaseForLucene>();
			model.put("paginationHelper", paginationHelper);
			model.put("searchFieldAry", searchFieldAry);
			return "/casehandle/lucene/caseList";
		}
		if(StringUtils.isBlank(indexPosition)){
			indexPosition = "all";
		}
		boolean isAndCondition = false;
		if(StringUtils.isNotBlank(searchFieldAry)){
			isAndCondition = true;
		}
		PaginationHelper<CaseForLucene> paginationHelper = caseIndexService.queryIndex(caseBasic, page,
				MANAGE_MARK, session,indexPosition,keywords,isAndCondition);
		model.put("paginationHelper", paginationHelper);
		model.put("indexPosition", indexPosition);
		model.put("searchFieldAry", searchFieldAry);
		return "/casehandle/lucene/caseList";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyyMMdd"), true));
	}
}
