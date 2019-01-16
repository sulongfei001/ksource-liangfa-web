package com.ksource.liangfa.web.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.domain.AccuseRule;
import com.ksource.liangfa.domain.CaseAccuseRuleRelation;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseAccuseRuleRelationMapper;
import com.ksource.liangfa.service.system.AccuseRuleService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.liangfa.util.AemanticAnalysisUtil;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/system/accuseRule")
public class AccuseRuleController {

	private static final String SEACHER_STRING = "redirect:/system/accuseRule/search";

	@Autowired
	AccuseRuleService accuseRuleService;

	@Autowired
	private IndustryInfoService industryInfoService;
	
    @Autowired
    private CaseAccuseRuleRelationMapper caseAccuseRuleRelationMapper;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/**
	 * 查询罪名规则列表页面
	 * 
	 * @param accuseRule
	 * @param info
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "search")
	public ModelAndView search(AccuseRule accuseRule, String info, String page, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/accuseRule/accuseRuleList");
		PaginationHelper<AccuseRule> list = accuseRuleService.find(accuseRule, page);
		for (AccuseRule i : list.getList()) {
			List<String> accuseNames = i.getAccuseNameList();
			// 循环罪名集合getAccuseNameList，组成用，分割的字符串
			String accuseName = "";
			for (String name : accuseNames) {
				name = name + "；";
				accuseName += name;
			}
			// 去除把最后一个,
			accuseName = accuseName.substring(0, accuseName.length() - 1);
			// 把字符串赋值给list集合的accuseName属性
			i.setAccuseName(accuseName);
			// 更新新增加的json字段，用于后期添加字段快速更新规则的json
			// accuseRuleService.updateJsonField(i);
		}
		view.addObject("list", list);
		view.addObject("page", page).addObject("info", info);
		view.addObject("accuseRule", accuseRule);
		return view;
	}

	/**
	 * 添加规则页面
	 * 
	 * @param request
	 * @param industryType
	 * @return
	 */
	@RequestMapping(value = "addUI")
	public ModelAndView addUI(HttpServletRequest request, String industryType) {
		ModelAndView view = new ModelAndView("system/accuseRule/accuseRuleAdd");
		view.addObject("industryType", industryType);
		return view;
	}

	/**
	 * 保存规则页面
	 * 
	 * @param accuseRule
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "add")
	public String add(AccuseRule accuseRule, HttpServletRequest request) throws Exception {
		String path = SEACHER_STRING;
		User user = SystemContext.getCurrentUser(request);
		accuseRule.setInputUser(user.getUserId());
		accuseRule.setInputTime(new Date());
		accuseRuleService.insert(accuseRule);
		path = path + "?industryType=" + accuseRule.getIndustryType();
		return ResponseMessage.addPromptTypeForPath(path, PromptType.add);
	}

	/**
	 * 规则修改界面
	 * 
	 * @param id
	 * @param industryType
	 * @return
	 */
	@RequestMapping(value = "updateUI/{id}")
	public ModelAndView updateUI(@PathVariable String id, String industryType) {
		ModelAndView view = new ModelAndView("system/accuseRule/accuseRuleEdit");
		AccuseRule accuseRule = accuseRuleService.selectById(id);
		List<AccuseInfo> ai = new ArrayList<AccuseInfo>();
		if (accuseRule != null) {
			ai = accuseRule.getAccuseInfos();
			String accuseInfoListJson = JSON.toJSONString(ai);
			accuseRule.setAccuseInfo(accuseInfoListJson);
		}
		view.addObject("accuseRule", accuseRule);
		return view;
	}

	/**
	 * 规则修改
	 * 
	 * @param accuseRule
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	public String update(AccuseRule accuseRule, HttpServletRequest request) throws Exception {
		String path = SEACHER_STRING;
		accuseRuleService.updateByPrimaryKeySelective(accuseRule);
		path = path + "?industryType=" + accuseRule.getIndustryType();
		return ResponseMessage.addPromptTypeForPath(path, PromptType.update);
	}

	/**
	 * 查看规则详情
	 * 
	 * @author: LXL
	 * @createTime:2017年9月27日 下午4:26:31
	 * @return:String
	 */
	@RequestMapping(value = "detail/{id}")
	public ModelAndView deatil(@PathVariable String id, String industryType) {
		ModelAndView view = new ModelAndView("system/accuseRule/accuseRuleDetail");
		AccuseRule accuseRule = accuseRuleService.selectById(id);

		List<AccuseInfo> ai = accuseRule.getAccuseInfos();
		String accuseInfoListJson = JSON.toJSONString(ai);
		accuseRule.setAccuseInfo(accuseInfoListJson);
		view.addObject("accuseRule", accuseRule);
		return view;
	}

	@RequestMapping(value = "checkRuleName")
	@ResponseBody
	public boolean checkRuleName(String ruleName, String ruleId, String industryType, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("ruleName", ruleName);
		paramMap.put("ruleId", ruleId);
		return accuseRuleService.checkRuleName(paramMap);
	}

	/**
	 * 删除规则
	 * 
	 * @param id
	 * @param industryType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable String id, String industryType, HttpServletRequest request) throws Exception {
		accuseRuleService.del(id);
		String page = getSearchPage(request);
		String path = SEACHER_STRING + "?info=1&industryType=" + industryType;
		if (page != null) {
			path += "&page=" + page;
		}
		return path;
	}

	/**
	 * 返回
	 * 
	 * @param request
	 * @param industryType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "back")
	public ModelAndView back(HttpServletRequest request, String industryType) throws Exception {
		AccuseRule accuseRule = new AccuseRule();
		String page = getSearchPage(request);
		if (StringUtils.isEmpty(page)) {
			page = "1";
		}
		accuseRule.setIndustryType(industryType);
		return this.search(accuseRule, "", page, request);
	}

	/**
	 * 进入行业树展示界面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loadIndustryInfoTree")
	public ModelAndView loadIndustryInfoTree(HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("system/accuseRule/industryInfoTree");
		return view;
	}

	/**
	 * 获取行业信息
	 * 
	 * */
	@RequestMapping(value = "loadIndustryInfo")
	public void loadChildModule(HttpServletResponse response, HttpServletRequest request) {
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

	/**
	 * 获取查询界面
	 * 
	 * @param request
	 * @return
	 */
	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass().getName() + "searchPage"));
	}
	
    
    /**
     * 分析疑似犯罪数据
     * @author: LXL
     * @createTime:2017年10月23日 下午4:48:22
     * @param caseFilter
     * @param districtName
     * @param request
     * @param model
     * @param page
     */
    @RequestMapping(value = "/analysis")
    public ModelAndView analysis(Long accuseRuleId,String industryType,HttpServletRequest request,HttpServletResponse response) throws Exception {
      ModelAndView mv=new ModelAndView("/system/accuseRule/analysisResult");
      if(accuseRuleId != null){
          CaseAccuseRuleRelation relation = new CaseAccuseRuleRelation();
          relation.setRuleId(accuseRuleId);
          caseAccuseRuleRelationMapper.delete(relation);
      }
      List<CaseBasic> caseList = accuseRuleService.analysis(accuseRuleId,industryType);
      mv.addObject("caseList", caseList);
      return mv;
    }
    
    /**
     * 在线为本相似度比较
     * @param content
     * @param keywords
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/analysisUtil")
    public ModelAndView AnalysisUtil(String content,String keywords, HttpServletRequest request,HttpServletResponse response) throws Exception {
      ModelAndView mv=new ModelAndView("/system/accuseRule/analysisUtil");
      Double scope = AemanticAnalysisUtil.aemanticAnalysis(content, keywords);
      mv.addObject("scope", scope);
      return mv;
    }
    	
	
}
