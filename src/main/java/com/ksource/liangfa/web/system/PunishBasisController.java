package com.ksource.liangfa.web.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.domain.PunishBasis;
import com.ksource.liangfa.domain.PunishBasisTerm;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.liangfa.service.system.PunishBasisService;
import com.ksource.liangfa.service.system.PunishBasisTermService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.PromptType;

/**
 * 行政处罚依据
 * @author lijiajia
 * @data 2017-3-28
 */
@Controller
@RequestMapping("/system/punishBasis")
public class PunishBasisController {

	private static final String SEACHER_STRING = "redirect:/system/punishBasis/search" ;
	
	@Autowired
	MybatisAutoMapperService mapperService;
	@Autowired
	PunishBasisService punishBasisService;
	@Autowired
	UserService userService;
	@Autowired
	PunishBasisTermService punishBasisTermService;
	@Autowired
	IndustryInfoService industryInfoService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	// 进入行业信息树界面
	@RequestMapping(value = "loadIndustryInfoTree")
	public ModelAndView loadIndustryInfoTree(HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/discretionStandard/industryInfoTree");
		return view;
	}
	
	//获取行业树信息
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
    
    
	// 查询行政处罚依据
	@RequestMapping(value = "search")
	public ModelAndView search(PunishBasis punishBasis,String info, String page,HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/discretionStandard/punishBasisList");
		PaginationHelper<PunishBasis> list = punishBasisService.find(punishBasis, page);
		view.addObject("list", list);
		view.addObject("page", page).addObject("info", info);
		view.addObject("punishBasis", punishBasis);
		return view;
	}
	
	//新增行政处罚依据UI
	@RequestMapping(value="addUI")
	public ModelAndView addUI(HttpServletRequest request,String industryType,String info){
		ModelAndView view = new ModelAndView("system/discretionStandard/punishBasisAdd");
		view.addObject("industryType", industryType);
		view.addObject("info", info);
		return view;
	}

	// 新增行政处罚依据
	@RequestMapping(value = "add")
	public String add(PunishBasis punishBasis, HttpServletRequest request) throws Exception {
		//String path = SEACHER_STRING;
		String path = "redirect:/system/punishBasis/addUI?industryType="+punishBasis.getIndustryType();
		List<String> termInfoAry = getTermInfoAry(request); 
		ServiceResponse res = punishBasisService.insert(punishBasis,termInfoAry);
		String info = "";
		if (res.getResult()) {
			info = "true";
        } else {
        	info = "false";
        }   
		path += "&info="+info;
		
		return path;
	}
	
	//获取 政处罚依据项 集合
	private List<String> getTermInfoAry(HttpServletRequest request) {
		Enumeration paramNames=request.getParameterNames();
		List<String> termInfos=new ArrayList<String>();
		List<String> sortList = new ArrayList<String>();
		String paramName;
		while(paramNames.hasMoreElements()){
			paramName=(String) paramNames.nextElement();
			if(paramName.startsWith("termInfo")){
				sortList.add(paramName);
			}
		}
		Collections.sort(sortList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int num1 = o1.charAt(o1.length()-1);
				int num2 = o2.charAt(o2.length()-1);
				return num1-num2;
			}
		});
		for(String l:sortList){
			termInfos.add(request.getParameter(l));
		}
		return termInfos;
	}
	
		
	//行政处罚依据修改页面
	@RequestMapping(value="updateUI")
	public ModelAndView updateUI(Integer basisId,String industryType){
		ModelAndView view = new ModelAndView("system/discretionStandard/punishBasisUpdate");
		PunishBasis punishBasis = punishBasisService.selectByPrimaryKey(basisId);
		//获取行政处罚依据项信息
		PunishBasisTerm param=new PunishBasisTerm();
		param.setBasisId(basisId);
		List<PunishBasisTerm> terms=punishBasisTermService.getTermByBasisId(param);
		
		view.addObject("punishBasis", punishBasis);
		view.addObject("industryType", industryType);
		view.addObject("terms", terms);
		return view;
	}
	
	
	// 修改行政处罚依据
	@RequestMapping(value = "update")
	public String update(PunishBasis punishBasis, HttpServletRequest request)
			throws Exception {
		String path = SEACHER_STRING;
		punishBasisService.update(punishBasis);
		path=path+"?industryType="+punishBasis.getIndustryType();
		return  ResponseMessage.addPromptTypeForPath(path, PromptType.update);
	}

	
	// 删除行政处罚依据
	@RequestMapping(value = "delete")
	public String delete(Integer basisId,String industryType,HttpServletRequest request) throws Exception {
		punishBasisService.del(basisId);
		String page = getSearchPage(request);
        String path=SEACHER_STRING + "?info=1&industryType="+industryType;
        if(page!=null){
             path+="&page="+page;
        }
		return path;
	}
	
	// 删除行政处罚依据项
	@RequestMapping(value = "deleteTerm")
	@ResponseBody
	public int deleteTerm(Integer termId,HttpServletRequest request) throws Exception {
		return punishBasisTermService.deleteTerm(termId);
	}
	
	//返回
	@RequestMapping(value="back")
	public ModelAndView back(HttpServletRequest request,String industryType) throws Exception{
		PunishBasis punishBasis=new PunishBasis();
		String page = getSearchPage(request);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		punishBasis.setIndustryType(industryType);
		return this.search(punishBasis,"",page, request);
	}
	
	
	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPage"));
	}
}
