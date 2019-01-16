package com.ksource.liangfa.web.casehandle;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.liangfa.domain.CaseSupervision;
import com.ksource.liangfa.domain.CaseSupervisionExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseSupervisionMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.SystemContext;

/**
 *@author wangzhenya
 *@2012-11-28 上午11:36:42
 */
@Controller
@RequestMapping(value = "casehandle/caseSupervision")
public class CaseSupervisionController {

	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	
	/**
	 * 当前用户监督待办案件列表里的案件
	 * @param request
	 * @param caseId
	 * @param procKey
	 * @return
	 */
	@RequestMapping(value = "/supervision")
	@ResponseBody
	public boolean supervision(HttpServletRequest request,String caseId,String procKey){
		User user = SystemContext.getCurrentUser(request);
		CaseSupervision caseSupervision = new CaseSupervision();
		caseSupervision.setUserId(user.getUserId());
		caseSupervision.setCaseId(caseId);
		caseSupervision.setProcKey(procKey);
		int result = mybatisAutoMapperService.insert(CaseSupervisionMapper.class, caseSupervision);
		if(result>0){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 取消当前用户监督的案件
	 * @param request
	 * @param caseId
	 * @param procKey
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public boolean delete(HttpServletRequest request,String caseId,String procKey){
		User user = SystemContext.getCurrentUser(request);
		CaseSupervisionExample example = new CaseSupervisionExample();
		example.createCriteria().andCaseIdEqualTo(caseId).andUserIdEqualTo(user.getUserId()).andProcKeyEqualTo(procKey);
		int result = mybatisAutoMapperService.deleteByExample(CaseSupervisionMapper.class, example);
		if(result>0){
			return true;
		}else{
			return false;
		}
	}
}
