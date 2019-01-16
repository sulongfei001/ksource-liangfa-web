package com.ksource.liangfa.workflow.stepView;

import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseWeijiWithBLOBs;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseWeijiMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.SpringContext;

/**
 *违纪案件办理步骤视图<br>
 *@author gengzi
 *@data 2012-3-14
 */
public class WeijiProcStepView extends CaseProcStepView<CaseWeijiWithBLOBs> {
	public WeijiProcStepView(CaseStep caseStep,User sessionUser) {
		super(caseStep, sessionUser);
	}

	@Override
	protected void initProcBusinessEntity(String procBusinessKey) {
		MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
		this.procBusinessEntity=mapperService.selectByPrimaryKey(CaseWeijiMapper.class, Integer.valueOf(procBusinessKey), CaseWeijiWithBLOBs.class);
		User user = mapperService.selectByPrimaryKey(UserMapper.class, procBusinessEntity.getInputer(), User.class);
		Organise org = mapperService.selectByPrimaryKey(OrganiseMapper.class, user.getOrgId(), Organise.class);
		procBusinessEntity.setInputerName(user.getUserName());
		procBusinessEntity.setOrgName(org.getOrgName());
	}

	@Override
	protected ModelAndView getProcBusinessEntityView() {
		ModelAndView view = new ModelAndView("workflow/caseProcView/weijiCaseInfo");
		return view;
	}

}
