package com.ksource.liangfa.web.specialactivity;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.domain.DqdjCaseCategory;
import com.ksource.liangfa.mapper.DqdjCaseCategoryMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.PromptType;

/**
 *@author wangzhenya
 *@2013-1-7 上午10:34:36
 */
@Controller
@RequestMapping(value = "activity/dqdjCaseCategory")
public class DqdjCaseCategoryController {

	private static final String DQDJ_ADD = "activity/dqdjCaseAdd";
	private static final String DQDJ_ADDUI = "redirect:/activity/dqdjCaseCategory/addUI";
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	
	/**
	 * 进入打侵打假添加页面
	 * @param strs
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/addUI")
	public String addUI(String[] strs,ModelMap map,ResponseMessage res) {
		
		map.addAttribute("strs", Arrays.toString(strs));
		map.addAttribute("info", ResponseMessage.parseMsg(res));
		return DQDJ_ADD;
	}
	
	/**
	 * 添加打侵打假案件
	 * @param dqdjCaseCategory
	 * @param strs
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(DqdjCaseCategory dqdjCaseCategory,String strs) {
		strs = strs.substring(1, strs.length()-1);
		String[] data = strs.split(",");
		for(String caseId : data) {
			dqdjCaseCategory.setCaseId(caseId.trim());
			mybatisAutoMapperService.insert(DqdjCaseCategoryMapper.class, dqdjCaseCategory);
		}
		
		return ResponseMessage.addPromptTypeForPath(DQDJ_ADDUI, PromptType.shift);
	}
}
