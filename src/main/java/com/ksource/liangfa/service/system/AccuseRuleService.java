package com.ksource.liangfa.service.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.AccuseRule;
import com.ksource.liangfa.domain.CaseBasic;

public interface AccuseRuleService {

	
	/**
	 * 查询罪名规则分页信息
	 * @param illegalSituation
	 * @param page
	 * @return
	 */
	public PaginationHelper<AccuseRule> find(AccuseRule accuseRule, String page);
	
	/**
	 * 添加罪名规则
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse insert(AccuseRule accuseRule);

	/**
	 * 根据id查询规则
	 * @param id
	 * @return
	 */
	public AccuseRule selectById(String id);
	
	/**
	 * 根据id删除信息
	 * @param id
	 * @return 
	 */
	public void del(String id);

	/**
	 * 根据主键修改信息
	 * @param accuseRule
	 */
	public ServiceResponse updateByPrimaryKeySelective(AccuseRule accuseRule);

	/**
	 * 校验规则名是否已经存在
	 * @author: LXL
	 * @createTime:2017年9月27日 下午6:08:03
	 * @return:boolean
	 */
	public boolean checkRuleName(Map<String,String> paramMap);
	
	/**
	 * 对外提供案件规则匹配方法
	 * @param caseBasic
	 * @param industryType
	 * @param request 
	 * @return
	 */
	public CaseBasic  matchCaseRule(CaseBasic caseBasic,String industryType, HttpServletRequest request);

    /**
     * 分析该规则下的案件的疑似犯罪情况
     * */
    public List<CaseBasic> analysis(Long accuseRuleId, String industryType);
	
	
}
