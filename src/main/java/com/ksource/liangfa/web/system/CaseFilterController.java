
package com.ksource.liangfa.web.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.domain.CaseFilter;
import com.ksource.liangfa.domain.CaseFilterExample;
import com.ksource.liangfa.mapper.CaseFilterMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.CaseFilterService;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;


/**
 * 
 * @author lijiajia
 *
 */
@Controller
@RequestMapping("system/caseFilter")
public class CaseFilterController {

	@Autowired
	CaseFilterService caseFilterService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	
	// 进入 新增案件筛选条件界面
		@RequestMapping(value = "addUI")
		public ModelAndView addUI(ResponseMessage res, HttpServletRequest request,HttpSession session) {
			CaseFilter caseFilter=new CaseFilter();
			ModelAndView view = new ModelAndView("system/caseFilter/caseFilterAdd");
			Integer orgCode=SystemContext.getCurrentUser(request).getOrgId();
			
			CaseFilterExample example=new CaseFilterExample();
			//example.createCriteria().andOrgCodeEqualTo(orgCode);
			List<CaseFilter> list=mybatisAutoMapperService.selectByExample(CaseFilterMapper.class, example, CaseFilter.class);
			int count=list.size();
			if(count>=1){
				caseFilter=list.get(0);
			}
			if(caseFilter!=null){
				view.addObject("caseFilter", caseFilter);
			}
			view.addObject("info",res.getResponse_msg());
			return view;
		}
		
		
		// 新增 案件筛选条件
		@RequestMapping(value = "add")
		public String add(HttpServletRequest request, CaseFilter caseFilter,ModelMap model) {
			String path="redirect:/system/caseFilter/addUI";
			Integer orgCode=SystemContext.getCurrentUser(request).getOrgId();
			caseFilter.setOrgCode(orgCode);
			if(caseFilter.getFilterId()==null){//新增
				caseFilterService.insert(caseFilter);
				model.addAttribute("caseFilter", caseFilter);
				return ResponseMessage.addPromptTypeForPath(path,PromptType.add);
			}else{//修改
				caseFilterService.update(caseFilter);
				model.addAttribute("caseFilter", caseFilter);
				return ResponseMessage.addPromptTypeForPath(path,PromptType.update);
			}
		}
		
	    @InitBinder
	    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
	        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
	                new SimpleDateFormat("yyyy-MM-dd"), true));
	    }
}
