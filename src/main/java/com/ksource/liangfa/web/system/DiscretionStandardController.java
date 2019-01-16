package com.ksource.liangfa.web.system;

import java.text.SimpleDateFormat;
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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.domain.DiscretionStandard;
import com.ksource.liangfa.domain.PunishBasisTerm;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.DiscretionStandardService;
import com.ksource.liangfa.service.system.PunishBasisTermService;
import com.ksource.syscontext.PromptType;

/**
 * 行政处罚裁量标准
 * @author lijiajia
 * @data 2017-3-28
 */
@Controller
@RequestMapping("/system/discretionStandard")
public class DiscretionStandardController {

	private static final String SEACHER_STRING = "redirect:/system/discretionStandard/search" ;
	
	@Autowired
	MybatisAutoMapperService mapperService;
	@Autowired
	DiscretionStandardService discretionStandardService;
	@Autowired
	PunishBasisTermService punishBasisTermService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
    
	// 查询行政处罚裁量标准
	@RequestMapping(value = "search")
	public ModelAndView search(DiscretionStandard discretionStandard,String info, String page,HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/discretionStandard/discretionStandardList");
		PaginationHelper<DiscretionStandard> list = discretionStandardService.find(discretionStandard, page);
		//根据处罚依据查询处罚依据项
		PunishBasisTerm param=new PunishBasisTerm();
		param.setBasisId(discretionStandard.getBasisId());
		List<PunishBasisTerm> termList=punishBasisTermService.getTermByBasisId(param);
		view.addObject("list", list);
		view.addObject("page", page).addObject("info", info);
		view.addObject("discretionStandard", discretionStandard);
		view.addObject("termList", termList);
		return view;
	}
	
	//新增行政处罚裁量标准UI
	@RequestMapping(value="addUI")
	public ModelAndView addUI(HttpServletRequest request,DiscretionStandard discretionStandard){
		ModelAndView view = new ModelAndView("system/discretionStandard/discretionStandardAdd");
		//根据处罚依据查询处罚依据项
		PunishBasisTerm param=new PunishBasisTerm();
		param.setBasisId(discretionStandard.getBasisId());
		List<PunishBasisTerm> termList=punishBasisTermService.getTermByBasisId(param);
		view.addObject("discretionStandard", discretionStandard);
		view.addObject("termList", termList);
		return view;
	}

	// 新增行政处罚裁量标准
	@RequestMapping(value = "add")
	public String add(DiscretionStandard discretionStandard, HttpServletRequest request) throws Exception {
		String path = SEACHER_STRING;
		discretionStandardService.insert(discretionStandard);
		path=path+"?industryType="+discretionStandard.getIndustryType()
				+"&basisId="+discretionStandard.getBasisId();
		return  ResponseMessage.addPromptTypeForPath(path, PromptType.add);
	}
	
	
	//行政处罚裁量标准修改页面
	@RequestMapping(value="updateUI/{id}")
	public ModelAndView updateUI(@PathVariable Integer id,String industryType){
		ModelAndView view = new ModelAndView("system/discretionStandard/discretionStandardUpdate");
		DiscretionStandard discretionStandard = discretionStandardService.selectByPrimaryKey(id);
		//根据处罚依据查询处罚依据项
		PunishBasisTerm param=new PunishBasisTerm();
		param.setBasisId(discretionStandard.getBasisId());
		List<PunishBasisTerm> termList=punishBasisTermService.getTermByBasisId(param);
		
		view.addObject("discretionStandard", discretionStandard);
		view.addObject("industryType", industryType);
		view.addObject("termList", termList);
		return view;
	}
	
	
	// 修改行政处罚裁量标准
	@RequestMapping(value = "update")
	public String update(DiscretionStandard discretionStandard, HttpServletRequest request)
			throws Exception {
		String path = SEACHER_STRING;
		discretionStandardService.update(discretionStandard);
		path=path+"?industryType="+discretionStandard.getIndustryType()
				+"&basisId="+discretionStandard.getBasisId();
		return  ResponseMessage.addPromptTypeForPath(path, PromptType.update);
	}

	
	// 删除行政处罚裁量标准
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable Integer id,String industryType,Integer basisId,HttpServletRequest request) throws Exception {
		discretionStandardService.del(id);
		String page = getSearchPage(request);
        String path=SEACHER_STRING + "?info=1&industryType="+industryType+"&basisId="+basisId;
        if(page!=null){
             path+="&page="+page;
        }
		return path;
	}
	
	//返回
	@RequestMapping(value="back")
	public ModelAndView back(HttpServletRequest request,String industryType,Integer basisId) throws Exception{
		DiscretionStandard discretionStandard=new DiscretionStandard();
		String page = getSearchPage(request);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		discretionStandard.setIndustryType(industryType);
		discretionStandard.setBasisId(basisId);
		return this.search(discretionStandard,"",page, request);
	}
	
	
	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPage"));
	}
}
