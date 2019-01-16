package com.ksource.liangfa.web.system;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.ksource.liangfa.domain.WebserviceConfig;
import com.ksource.liangfa.mapper.WebserviceConfigMapper;
import com.ksource.liangfa.service.system.WebserviceConfigService;
import com.ksource.syscontext.PromptType;

/**
 * webService接口地址配置
 * @author lijiajia
 * @data 2016-2-29
 */
@Controller
@RequestMapping("/system/webserviceConfig")
public class WebserviceConfigController {

	private static final String SEACHER_STRING = "redirect:/system/webserviceConfig/search" ;
	
	@Autowired
	WebserviceConfigService webserviceConfigService;
	@Autowired
	WebserviceConfigMapper webserviceConfigMapper;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	// 查询webService接口地址配置信息
	@RequestMapping(value = "search")
	public ModelAndView search(WebserviceConfig webserviceConfig,String info, String page,HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/webservice_config/webserviceConfig");
		PaginationHelper<WebserviceConfig> list = webserviceConfigService.find(webserviceConfig, page);
		view.addObject("list", list);
		view.addObject("page", page).addObject("info", info);
		view.addObject("webserviceConfig", webserviceConfig);
		return view;
	}
	
	//新增webService接口地址配置信息UI
	@RequestMapping(value="addUI")
	public ModelAndView addUI(HttpServletRequest request){
		ModelAndView view = new ModelAndView("system/webservice_config/webserviceConfigAdd");
		
		return view;
	}

	// 新增webService接口地址配置信息
	@RequestMapping(value = "add")
	public String add(WebserviceConfig webserviceConfig, HttpServletRequest request) throws Exception {
		String path = SEACHER_STRING;
		webserviceConfigService.insert(webserviceConfig);
		return  ResponseMessage.addPromptTypeForPath(path, PromptType.add);
	}
	
	//webService接口地址配置信息修改页面
	@RequestMapping(value="updateUI/{id}")
	public ModelAndView updateUI(@PathVariable Integer id){
		ModelAndView view = new ModelAndView("system/webservice_config/webserviceConfigUpdate");
		WebserviceConfig webserviceConfig = webserviceConfigMapper.selectByPrimaryKey(id);
		view.addObject("webserviceConfig", webserviceConfig);
		return view;
	}
	
	// 修改webService接口地址配置信息
	@RequestMapping(value = "update")
	public String update(WebserviceConfig webserviceConfig, HttpServletRequest request)
			throws Exception {
		String path = SEACHER_STRING;
		webserviceConfigService.updateByPrimaryKeySelective(webserviceConfig);
		return  ResponseMessage.addPromptTypeForPath(path, PromptType.update);
	}

	// 删除webService接口地址配置信息
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable Integer id,HttpServletRequest request) throws Exception {
		webserviceConfigService.del(id);
		String page = getSearchPage(request);
        String path=SEACHER_STRING + "?info=1";
        if(page!=null){
             path+="&page="+page;
        }
		return path;
	}
	
	//返回
	@RequestMapping(value="back")
	public ModelAndView back(HttpServletRequest request) throws Exception{
		WebserviceConfig webserviceConfig=new WebserviceConfig();
		String page = getSearchPage(request);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		return this.search(webserviceConfig,"",page, request);
	}
	
	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPage"));
	}
}
