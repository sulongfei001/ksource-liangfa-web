package com.ksource.liangfa.web.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.alibaba.fastjson.JSON;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.domain.IllegalSituation;
import com.ksource.liangfa.domain.IllegalSituationExample;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.IllegalSituationMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.IllegalSituationService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.liangfa.service.system.PeopleCompanyLibService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

/**
 * 违法情形
 * @author lijiajia
 * @data 2016-2-29
 */
@Controller
@RequestMapping("/system/illegalSituation")
public class IllegalSituationController {

	private static final String SEACHER_STRING = "redirect:/system/illegalSituation/search" ;
	
	@Autowired
	MybatisAutoMapperService mapperService;
	@Autowired
	IllegalSituationService illegalSituationService;
	@Autowired
	UserService userService;
	@Autowired
	PeopleCompanyLibService libService;
	@Autowired
	private IndustryInfoService industryInfoService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	
	// 查询违法情形
	@RequestMapping(value = "search")
	public ModelAndView search(IllegalSituation illegalSituation,String info, String page,HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/illegal_situation/illegalSituation");
		PaginationHelper<IllegalSituation> list = illegalSituationService.find(illegalSituation, page);
		for(IllegalSituation i :list.getList()){
			List<String> accuseNames=i.getAccuseNameList();
			//循环罪名集合getAccuseNameList，组成用，分割的字符串
			String accuseName="";
			for(String name:accuseNames){
				name=name+"；";
				accuseName+=name;
			}
			//去除把最后一个,
			accuseName=accuseName.substring(0, accuseName.length()-1);
			//把字符串赋值给list集合的accuseName属性
			i.setAccuseName(accuseName);
		}
		view.addObject("list", list);
		view.addObject("page", page).addObject("info", info);
		view.addObject("illegal_Situation", illegalSituation);
		return view;
	}
	
	//新增违法情形信息UI
	@RequestMapping(value="addUI")
	public ModelAndView addUI(HttpServletRequest request,String industryType){
		ModelAndView view = new ModelAndView("system/illegal_situation/illegalSituationAdd");
		List<IllegalSituation> list=new ArrayList<IllegalSituation>();
		view.addObject("illegalSituationlist", list);
		view.addObject("industryType", industryType);
		return view;
	}

	// 新增违法情形信息
	@RequestMapping(value = "add")
	public String add(IllegalSituation illegalSituation, HttpServletRequest request) throws Exception {
		String path = SEACHER_STRING;
		illegalSituation.setInputUser(SystemContext.getCurrentUser(request).getUserId());
		illegalSituation.setInputTime(new Date());
		illegalSituationService.insert(illegalSituation);
		path=path+"?industryType="+illegalSituation.getIndustryType();
		return  path+"&info="+PromptType.add;
	}
	
	//违法情形修改页面
	@RequestMapping(value="updateUI/{id}")
	public ModelAndView updateUI(@PathVariable String id,String industryType,String info){
		ModelAndView view = new ModelAndView("system/illegal_situation/illegalSituationUpdate");
		IllegalSituation illegalSituation = illegalSituationService.selectById(id);
		
		List<AccuseInfo> ai=illegalSituation.getAccuseInfos();
		String accuseInfoListJson=JSON.toJSONString(ai);
		illegalSituation.setAccuseInfo(accuseInfoListJson);
		
		view.addObject("illegalSituation", illegalSituation);
		view.addObject("industryType", industryType);
		view.addObject("info", info);
		return view;
	}
	
	
	// 修改违法情形信息
	@RequestMapping(value = "update")
	public String update(IllegalSituation illegalSituation, HttpServletRequest request)
			throws Exception {
		String path = SEACHER_STRING;
		illegalSituationService.updateByPrimaryKeySelective(illegalSituation);
		path=path+"?industryType="+illegalSituation.getIndustryType();
		return  path+"&info="+PromptType.update;
	}

	// 删除违法情形信息
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable String id,String industryType,HttpServletRequest request) throws Exception {
		illegalSituationService.del(id);
		String page = getSearchPage(request);
        String path=SEACHER_STRING + "?info="+PromptType.del+"&industryType="+industryType;
        if(page!=null){
             path+="&page="+page;
        }
		return path;
	}
	
	@RequestMapping(value="back")
	public ModelAndView back(HttpServletRequest request,String industryType) throws Exception{
		IllegalSituation illegalSituation=new IllegalSituation();
		String page = getSearchPage(request);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		illegalSituation.setIndustryType(industryType);
		return this.search(illegalSituation,"",page, request);
	}
	
	
	/**
     *获取行业信息
     * */
    @RequestMapping(value = "loadIndustryInfo")
    public void loadChildModule(HttpServletResponse response,HttpServletRequest request) {
        List<IndustryInfo> industrys = industryInfoService.selectAll();
        String trees = JsTreeUtils.industryJsonztree(industrys);
        response.setContentType("application/json");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(trees);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
	
	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPage"));
	}
}
