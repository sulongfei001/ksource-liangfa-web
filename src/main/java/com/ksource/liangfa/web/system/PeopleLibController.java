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
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.domain.PeopleLib;
import com.ksource.liangfa.domain.PeopleLibExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.PeopleLibMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.PeopleCompanyLibService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

/**
 * 个人库<br>
 * 
 * @author gengzi
 * @data 2012-3-30
 */
@Controller
@RequestMapping("/system/peopleLib")
public class PeopleLibController {

	private static final String SEACHER_STRING = "redirect:/system/peopleLib/search" ;
	
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

	// 获取个人库信息
	@RequestMapping("/{IdsNO}")
	@ResponseBody
	public String getPeople(@PathVariable String IdsNO) {
		PeopleLib peopleLib = mapperService.selectByPrimaryKey(
				PeopleLibMapper.class, IdsNO, PeopleLib.class);
		String peopleLibJson=JSON.toJSONString(peopleLib);
		return peopleLibJson;
	}

	// 搜索个人库
	/**
	 * String division 用于区别是查询操作还是（添加、修改、删除、上一页、下一页）
	 */
	@RequestMapping(value = "search")
	public ModelAndView search(Boolean division,PeopleLib peopleLibFilter, String page,String info, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/peoplelib/peoplelib");
		division = division==null? true : division ;
		if(division) { //查询操作
			saveSearchPeople(request, peopleLibFilter);
			saveSearchPage(request, page);
		}else { // 不是查询操作
			peopleLibFilter = this.getSearchpeople(request) ;
		}
		PaginationHelper<PeopleLib> peopleList = libService.findPeople(peopleLibFilter, page);
		view.addObject("peopleList", peopleList);
		view.addObject("peopleLibFilter", peopleLibFilter);
		if (StringUtils.isNotBlank(info)) {
			view.addObject("info", info);
		}
		view.addObject("page", page);
		return view;
	}
	
	//新增个人库信息UI
	@RequestMapping(value="addUI")
	public ModelAndView addUI(){
		ModelAndView view = new ModelAndView("system/peoplelib/people_add");
		return view;
	}

	// 新增个人库信息
	@RequestMapping(value = "add")
	public String add(PeopleLib peopleLib, HttpServletRequest request) throws Exception {
		String path = SEACHER_STRING;
		String addView = request.getParameter("view");
		User user = SystemContext.getCurrentUser(request);
		String inputer = user.getUserId();
		peopleLib.setInputer(inputer);
		Date now = new Date();
		peopleLib.setInputTime(now);
		peopleLib.setIdsNo(peopleLib.getIdsNo().trim());
		libService.insertPeopleLib(peopleLib);
		if("add".equals(addView)){
			path = "redirect:/system/peoplelib/people_add";
		}
		return  ResponseMessage.addPromptTypeForPath(path, PromptType.add);
	}
	
	//个人库修改页面
	@RequestMapping(value="peoplelibUpdateUI/{idsNo}")
	public ModelAndView updateUI(@PathVariable String idsNo){
		ModelAndView view = new ModelAndView("system/peoplelib/people_update");
		PeopleLib peopleLib = mapperService.selectByPrimaryKey(PeopleLibMapper.class, idsNo, PeopleLib.class);
		view.addObject("peopleLib", peopleLib);
		return view;
	}
	// 修改个人库信息
	@RequestMapping(value = "update")
	public String update(PeopleLib peopleLib, HttpServletRequest request)
			throws Exception {
		String path = SEACHER_STRING;
		String addView = request.getParameter("view");
		libService.updateByPrimaryKey(peopleLib);
		if("update".equals(addView)){
			path = "redirect:/system/peoplelib/people_update";
		}
		return  ResponseMessage.addPromptTypeForPath(path, PromptType.update);
	}

	// 删除个人库信息
	@RequestMapping(value = "delete/{idsNo}")
	public String delete(@PathVariable String idsNo,
			HttpServletRequest request) throws Exception {
		libService.deleteByPrimaryKey(idsNo);
		String page = getSearchPage(request);
        String path=SEACHER_STRING + "?division=false&info=1";
        if(page!=null){
             path+="&page="+page;
        }
		return path;
	}
	
	@RequestMapping(value="back")
	public ModelAndView back(HttpServletRequest request) throws Exception{
		PeopleLib peopleLib = getSearchpeople(request);
		if(peopleLib == null){
			peopleLib = new PeopleLib();
		}
		String page = getSearchPage(request);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		return this.search(true, peopleLib, page, "", request);
	}
	
	
	@RequestMapping(value = "checkIdsNo")
	@ResponseBody
	public boolean checkIdsNo(String idsNo) {
		PeopleLibExample example = new PeopleLibExample();
		example.createCriteria().andIdsNoEqualTo(idsNo.trim());
		int size = mapperService.countByExample(PeopleLibMapper.class, example);
		if (size > 0) {
			return false;
		} else {
			return true;
		}
	}

	// 显示个人库详细信息
	@RequestMapping(value = "showDetalis/{idsNo}")
	public ModelAndView showDetalis(@PathVariable String idsNo)
			throws Exception {

		ModelAndView view = new ModelAndView(
				"system/peoplelib/peoplelibdetalis");
		PeopleLib peopleLib = mapperService.selectByPrimaryKey(
				PeopleLibMapper.class, idsNo, PeopleLib.class);
		String userId = peopleLib.getInputer();
		if (StringUtils.isNotBlank(userId)) {
			User user = userService.find(userId);
			if(user!=null){
				peopleLib.setInputeName(user.getUserName());
			}
		}

		CaseXianyirenExample caseXianyirenExample = new CaseXianyirenExample();
		caseXianyirenExample.createCriteria().andIdsNoEqualTo(idsNo);
		List<CaseXianyiren> caseXianyiren = libService
				.getXianyirenCaseId(caseXianyirenExample);
		if (caseXianyiren != null) {
			List<CaseBasic> caseList = new ArrayList<CaseBasic>();
			for (CaseXianyiren caseXianyi : caseXianyiren) {
				CaseBasic caseBasic = mapperService.selectByPrimaryKey(CaseBasicMapper.class,
						caseXianyi.getCaseId(), CaseBasic.class);
				caseList.add(caseBasic);
			}
			view.addObject("caselist", caseList);
		}
		view.addObject("people", peopleLib);

		return view;
	}
	
	private void saveSearchPeople(HttpServletRequest request,
			PeopleLib peopleLib) {
		request.getSession().setAttribute(
				this.getClass().getName() + "searchPeople", peopleLib);
	}

	private PeopleLib getSearchpeople(HttpServletRequest request) {
		return (PeopleLib) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPeople"));
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
