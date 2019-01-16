package com.ksource.liangfa.workflow.view;

import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.CaseDutyWithBLOBs;
import com.ksource.liangfa.mapper.CaseDutyMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.SpringContext;

/**
 *职务犯罪案件详情视图<br>
 *@author gengzi
 *@data 2012-3-14
 */
public class CaseProcViewCaseDuty extends CaseProcView<CaseDutyWithBLOBs> {

	public CaseProcViewCaseDuty(String procBusinessKey, String viewStepId) {
		super(procBusinessKey, viewStepId);
	}

	@Override
	protected void initProcBusinessEntity(String procBusinessKey) {
		MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
		this.procBusinessEntity=mapperService.selectByPrimaryKey(CaseDutyMapper.class, Integer.valueOf(procBusinessKey), CaseDutyWithBLOBs.class);
	}

	@Override
	public ModelAndView getView() {
		ModelAndView view =new ModelAndView("workflow/caseProcView/caseProcView");
		view.addObject("caseProcView", this);
		return view;
	}

	@Override
	public ModelAndView getDocView() {
		// TODO Auto-generated method stub
		return null;
	}

}
