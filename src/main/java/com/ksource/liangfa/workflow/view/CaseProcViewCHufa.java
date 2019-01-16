package com.ksource.liangfa.workflow.view;

import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;

/**
 *处罚案件详情视图<br>
 *@author gengzi
 *@data 2012-3-14
 */
public class CaseProcViewCHufa extends CaseProcView<CaseBasic> {

	public CaseProcViewCHufa(String procBusinessKey, String viewStepId) {
		super(procBusinessKey, viewStepId);
	}

	@Override
	protected void initProcBusinessEntity(String procBusinessKey) {
		CaseBasicMapper caseBasicMapper = SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
		this.procBusinessEntity=caseBasicMapper.selectByPK(procBusinessKey);
	}

	@Override
	public ModelAndView getView() {
		String viewName = "workflow/caseProcView/caseProcView";
		ModelAndView view =new ModelAndView(viewName);
		view.addObject("caseProcView", this);
		return view;
	}

	@Override
	public ModelAndView getDocView() {
		ModelAndView view =new ModelAndView("workflow/caseProcView/docCaseProcView");
		view.addObject("caseProcView", this);
		return view;
	}
}
