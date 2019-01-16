package com.ksource.liangfa.web.office;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.OfficeIdentity;
import com.ksource.liangfa.service.office.OfficeIdentityService;

@Controller
@RequestMapping({ "/office/identity/" })
public class OfficeIdentityController{

	@Resource
	private OfficeIdentityService officeIdentityService;

	@RequestMapping(value="list")
	public ModelAndView list(OfficeIdentity identity,String page,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/office/identity/identityList");
		PaginationHelper<OfficeIdentity> paginationHelper = officeIdentityService.find(identity,page,null);
		mv.addObject("paginationHelper", paginationHelper);
		return mv;
	}
	
	
	@RequestMapping(value = "addUI")
	public ModelAndView addUI(HttpServletRequest request){
		ModelAndView view = new ModelAndView("office/identity/identityAddUI");
		return view;
	}	

	@RequestMapping(value = "add")
	public ModelAndView add(OfficeIdentity identity,HttpServletRequest request) throws Exception {
		officeIdentityService.add(identity);
		ModelAndView mv = new ModelAndView("redirect:/office/identity/list");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="checkAliasExisted")
	public boolean checkAliasExisted(String alias,Integer identityId){
		Integer count = officeIdentityService.checkAliasExisted(alias,identityId);
		return count <= 0;
	}
	
	@RequestMapping(value="delete")
	public ModelAndView delete(Integer identityId){
		officeIdentityService.delete(identityId);
		ModelAndView mv = new ModelAndView("redirect:/office/identity/list");
		return mv;
	}
	
	@RequestMapping(value = "updateUI")
	public ModelAndView updateUI(Integer identityId,HttpServletRequest request){
		OfficeIdentity identity = officeIdentityService.selectById(identityId);
		ModelAndView view = new ModelAndView("office/identity/identityUpdateUI");
		view.addObject("identity", identity);
		return view;
	}
	
	@RequestMapping(value = "update")
	public ModelAndView update(OfficeIdentity identity,HttpServletRequest request) throws Exception {
		officeIdentityService.update(identity);
		ModelAndView mv = new ModelAndView("redirect:/office/identity/list");
		return mv;
	}
}
