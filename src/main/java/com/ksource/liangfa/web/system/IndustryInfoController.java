package com.ksource.liangfa.web.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.IllegalSituationService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.liangfa.service.system.PeopleCompanyLibService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

/**
 * 行业信息
 * @author lijiajia
 * @data 2016-3-14
 */
@Controller
@RequestMapping("/system/industryInfo")
public class IndustryInfoController {

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

	// 进入行业信息树界面
	@RequestMapping(value = "loadIndustryInfoTree")
	public ModelAndView loadIndustryInfoTree(HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/illegal_situation/industryInfoTree");
		return view;
	}
	
	
	//新增行业信息UI
	@RequestMapping(value="addUI")
	public ModelAndView addUI(ModelMap map,HttpServletRequest request,String industryType,String info ){
		ModelAndView view = new ModelAndView("system/illegal_situation/industryInfoAdd");
		map.put("info", info) ;
		return view;
	}

	// 新增行业信息
	@RequestMapping(value = "add")
	public String add(IndustryInfo industryInfo, HttpServletRequest request) throws Exception {
		String path = "redirect:/system/industryInfo/addUI";
		industryInfoService.insert(industryInfo);
		return  path+"?info="+PromptType.add;
	}
	
	//行业信息修改页面
	@RequestMapping(value="updateUI")
	public ModelAndView updateUI(ModelMap map,String industryType,ResponseMessage res){
		ModelAndView view = new ModelAndView("system/illegal_situation/industryInfoUpdate");
		IndustryInfo industryInfo = industryInfoService.selectById(industryType);
		view.addObject("industryInfo", industryInfo);
		map.put("info", ResponseMessage.parseMsg(res)) ;
		return view;
	}
	
	
	// 修改行业信息信息
	@RequestMapping(value = "update")
	public String update(IndustryInfo industryInfo, HttpServletRequest request)
			throws Exception {
		String path = "redirect:/system/industryInfo/updateUI";
		industryInfoService.updateByPrimaryKeySelective(industryInfo);
		path=path+"?industryType="+industryInfo.getIndustryType();
		return  ResponseMessage.addPromptTypeForPath(path, PromptType.update);
	}

	// 删除行业信息
	@RequestMapping(value = "delete")
	@ResponseBody
	public int delete(String industryType,HttpServletRequest request) throws Exception {
		int result=0;
		result=industryInfoService.del(industryType);
		return result;
	}
	
	
	//行业类型信息列表页面
	@RequestMapping(value = "search")
	public ModelAndView industryInfoMain(HttpServletRequest request,String page,IndustryInfo industryInfo,ResponseMessage res) throws Exception {
		ModelAndView view = new ModelAndView("system/industryInfo/industryInfoList");
		PaginationHelper<IndustryInfo> industrys = industryInfoService.find(industryInfo,page);
		view.addObject("industryInfoList", industrys);
		view.addObject("page", page);
		view.addObject("info", res.getResponse_msg());
		view.addObject("industryInfo", industryInfo);
		return view;
	}
	
	//新增行业类型信息UI
	@RequestMapping(value="addView")
	public ModelAndView addView(ModelMap map,HttpServletRequest request,String industryType,ResponseMessage res){
		ModelAndView view = new ModelAndView("system/industryInfo/industryInfoAdd");
		map.put("info", ResponseMessage.parseMsg(res)) ;
		return view;
	}
	
	//保存行业类型信息
	@RequestMapping(value = "save")
	public String save(IndustryInfo industryInfo, HttpServletRequest request) throws Exception {
		String path = "redirect:/system/industryInfo/search";
		industryInfo.setCreateTime(new Date());
		industryInfo.setCreateUser(SystemContext.getCurrentUser(request).getUserId());
		industryInfoService.insert(industryInfo);
		return  ResponseMessage.addPromptTypeForPath(path, PromptType.add);
	}
	
	//校验行业类型是否已存在
	@RequestMapping(value = "checkIndustryType", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkIndustryType(String industryType) {
		IndustryInfo industryInfo = industryInfoService.selectById(industryType);
		if (industryInfo != null) {
			return false;
		}
		return true;
	}
	
	
	//行业类型信息修改页面
	@RequestMapping(value="updateView")
	public ModelAndView updateView(ModelMap map,String industryType,ResponseMessage res){
		ModelAndView view = new ModelAndView("system/industryInfo/industryInfoUpdate");
		IndustryInfo industryInfo = industryInfoService.selectById(industryType);
		view.addObject("industryInfo", industryInfo);
		map.put("info", ResponseMessage.parseMsg(res)) ;
		return view;
	}
		
		
	// 修改行业类型信息
	@RequestMapping(value = "updateInfo")
	public String updateInfo(IndustryInfo industryInfo, HttpServletRequest request)
			throws Exception {
		String path = "redirect:/system/industryInfo/search";
		industryInfoService.updateByPrimaryKeySelective(industryInfo);
		return  ResponseMessage.addPromptTypeForPath(path, PromptType.update);
	}
	
	//删除行业类型信息
	@RequestMapping(value = "del")
	public String delUser(String industryType) {
		int result=industryInfoService.del(industryType);
		return ResponseMessage.addPromptTypeForPath("redirect:/system/industryInfo/search",PromptType.del);
	}
	
}
