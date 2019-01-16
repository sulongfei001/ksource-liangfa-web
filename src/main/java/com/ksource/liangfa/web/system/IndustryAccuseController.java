package com.ksource.liangfa.web.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.IndustryAccuse;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.IndustryAccuseService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.syscontext.PromptType;

/**
 * 行业常用罪名信息
 * @author lijiajia
 * @data 2016-3-14
 */
@Controller
@RequestMapping("/system/industryAccuse")
public class IndustryAccuseController {

	private static final String SEACHER_STRING = "redirect:/system/industryAccuse/search" ;
	
	@Autowired
	MybatisAutoMapperService mapperService;
	@Autowired
	private IndustryAccuseService industryAccuseService;
	@Autowired
	private IndustryInfoService industryInfoService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	// 进入行业信息树界面
	@RequestMapping(value = "main")
	public ModelAndView loadIndustryInfoTree(HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/industryAccuse/industryInfoTree");
		return view;
	}
	
	/**
     *获取行业信息
     * */
    @RequestMapping(value = "loadIndustryTree")
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
	
	// 查询行业罪名信息
	@RequestMapping(value = "search")
	public ModelAndView search(IndustryAccuse industryAccuse,String info, String page,HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/industryAccuse/industryAccuseList");
		PaginationHelper<IndustryAccuse> list = industryAccuseService.find(industryAccuse, page);
		view.addObject("list", list);
		view.addObject("page", page).addObject("info", info);
		view.addObject("industryAccuse", industryAccuse);
		return view;
	}
	
	//新增行业罪名信息UI
	@RequestMapping(value="addUI")
	public ModelAndView addUI(ModelMap map,HttpServletRequest request,String industryType,ResponseMessage res){
		ModelAndView view = new ModelAndView("system/industryAccuse/industryAccuseAdd");
		map.put("info", ResponseMessage.parseMsg(res)) ;
		view.addObject("industryType", industryType);
		return view;
	}

	// 新增行业罪名信息
	@RequestMapping(value = "add")
	public String add(IndustryAccuse industryAccuse, HttpServletRequest request) throws Exception {
		String path = SEACHER_STRING;
		industryAccuseService.insert(industryAccuse);
		path=path+"?industryType="+industryAccuse.getIndustryType();
		return  ResponseMessage.addPromptTypeForPath(path, PromptType.add);
	}
	
	// 删除行业罪名信息
	@RequestMapping(value = "delete")
	public String delete(String industryType,String accuseId,HttpServletRequest request) throws Exception {
		industryAccuseService.del(industryType,accuseId);
		String page = getSearchPage(request);
        String path=SEACHER_STRING + "?info=1&industryType="+industryType;
        if(page!=null){
             path+="&page="+page;
        }
		return path;
	}
	
	@RequestMapping(value="back")
	public ModelAndView back(HttpServletRequest request,String industryType) throws Exception{
		IndustryAccuse industryAccuse=new IndustryAccuse();
		String page = getSearchPage(request);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		industryAccuse.setIndustryType(industryType);
		return this.search(industryAccuse,"",page, request);
	}
	
	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPage"));
	}
}
