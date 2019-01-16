package com.ksource.liangfa.workflow.stepView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.domain.CaseAgreeNolian;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseAttachmentExample;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseFenpai;
import com.ksource.liangfa.domain.CaseNolianReason;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CasePartyExample;
import com.ksource.liangfa.domain.CaseRequireLian;
import com.ksource.liangfa.domain.CaseRequireNolianReason;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseStepExample;
import com.ksource.liangfa.domain.CaseTurnover;
import com.ksource.liangfa.domain.ChufaTypeForm;
import com.ksource.liangfa.domain.CrimeCaseForm;
import com.ksource.liangfa.domain.FormDef;
import com.ksource.liangfa.domain.FormInstance;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PenaltyCaseForm;
import com.ksource.liangfa.domain.PenaltyLianForm;
import com.ksource.liangfa.domain.DetentionInfo;
import com.ksource.liangfa.domain.DetentionInfoExample;
import com.ksource.liangfa.domain.SuggestYisongForm;
import com.ksource.liangfa.domain.TransferDetention;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.XingzhengCancelLianForm;
import com.ksource.liangfa.domain.XingzhengCancelLianFormExample;
import com.ksource.liangfa.domain.XingzhengJieanForm;
import com.ksource.liangfa.domain.XingzhengJieanFormExample;
import com.ksource.liangfa.domain.XingzhengNotLianForm;
import com.ksource.liangfa.domain.XingzhengNotLianFormExample;
import com.ksource.liangfa.domain.XingzhengNotPenalty;
import com.ksource.liangfa.domain.XingzhengNotPenaltyExample;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.mapper.CaseAgreeNolianMapper;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CaseFenpaiMapper;
import com.ksource.liangfa.mapper.CaseNolianReasonMapper;
import com.ksource.liangfa.mapper.CasePartyMapper;
import com.ksource.liangfa.mapper.CaseRequireLianMapper;
import com.ksource.liangfa.mapper.CaseRequireNolianReasonMapper;
import com.ksource.liangfa.mapper.CaseStepMapper;
import com.ksource.liangfa.mapper.CaseTurnoverMapper;
import com.ksource.liangfa.mapper.CrimeCaseFormMapper;
import com.ksource.liangfa.mapper.FormDefMapper;
import com.ksource.liangfa.mapper.FormInstanceMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PenaltyCaseFormMapper;
import com.ksource.liangfa.mapper.PenaltyLianFormMapper;
import com.ksource.liangfa.mapper.DetentionInfoMapper;
import com.ksource.liangfa.mapper.SuggestYisongFormMapper;
import com.ksource.liangfa.mapper.TransferDetentionMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.mapper.XingzhengCancelLianFormMapper;
import com.ksource.liangfa.mapper.XingzhengJieanFormMapper;
import com.ksource.liangfa.mapper.XingzhengNotLianFormMapper;
import com.ksource.liangfa.mapper.XingzhengNotPenaltyMapper;
import com.ksource.liangfa.service.ChufaTypeFormService;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.workflow.ActivitiUtil;
import com.ksource.liangfa.workflow.ProcBusinessEntity;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;

/**
 * 案件办理步骤视图<br>
 * 不同案件的不同处理方式由子类对此类的抽象方法的实现来完成。
 *
 * @author gengzi
 * @data 2012-3-14
 */
public abstract class CaseProcStepView<T extends ProcBusinessEntity> {

    T procBusinessEntity;
    CaseStep caseStep;
    User sessionUser;
    @Autowired
    XingzhengNotPenaltyMapper xingzhengNotPenaltyMapper;

    public CaseProcStepView(CaseStep caseStep, User sessionUser) {
        this.caseStep = caseStep;
        this.sessionUser = sessionUser;
        initProcBusinessEntity(caseStep.getCaseId());
    }

    /**
     * 得到spring mvc的模型视图
     *
     * @return
     */
    public ModelAndView getModelAndView() {
        int taskType = caseStep.getTaskType();
        if (taskType == Const.TASK_TYPE_ADD_CASE || taskType == Const.TASK_TYPE_UPDATE_CASE) {//此步骤为案件基本信息
            return getProcBusinessEntityView();
        }else if(taskType == Const.TASK_TYPE_XINGZHENGLIAN){
        	return getXingzhengLianView();
        }else if(taskType == Const.TASK_TYPE_XINGZHENGCHUFA){
        	return getXingzhengChufaView();
        }else if(taskType == Const.TASK_TYPE_YISONGGONGAN){
        	return getYisongGonganView();
        }else if(taskType == Const.TASK_TYPE_JIANYIYISONG){
        	return getJianyiYisongView();
        }else if(taskType == Const.TASK_TYPE_TUIHUI){
        	return getTuiHuiView();
        }else if(taskType == Const.TASK_TYPE_XINGZHENGJIEAN){
        	return getXingzhengJieanView();
        }else if(taskType == Const.TASK_TYPE_BUYULIAN){
        	return getXingzhengBuLiAnView();
        }else if(taskType == Const.TASK_TYPE_XINGZHENGCHEAN){
        	return getXingzhengCheAnView();
        }else if(taskType == Const.TASK_TYPE_FENPAI){
        	return getFenpaiView();
        }else if(taskType == Const.TASK_TYPE_YISONGGUANXIA){
        	return getYiSongGuanXiaView();
        }else if(taskType == Const.TASK_TYPE_NOTPENALTY){
        	return getCaseNotPenaltyView();
        }else if(taskType == Const.TASK_TYPE_TRANSFERDETENTION){
        	return getCaseTransferRdetentionView();
        }else if(taskType == Const.TASK_TYPE_DETENTIONINFO){
        	return getCaseDetentionInfoView();
        }else if(taskType == Const.TASK_TYPE_NOTDETENTIONINFO){
        	return getCaseNotDetentionInfoView();
        }else if(taskType == Const.TASK_TYPE_REQNOLIANREASON){
        	return getReqNoLianReasonView();
        }else if(taskType == Const.TASK_TYPE_NOLIANREASON){
        	return getNoLianReasonView();
        }else if(taskType == Const.TASK_TYPE_REQLIAN){
        	return getReqLianView();
        }else if(taskType == Const.TASK_TYPE_AGREENOLIAN){
        	return getAgreeNoLian();
        }else {
            return dtformView();
        }
    }
    
    
    //行政不予处罚表单详情
	private ModelAndView getCaseNotPenaltyView() {
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/xingzhengNotPenaltyView");
		//查询行政不予处罚表单信息
		XingzhengNotPenaltyMapper xingzhengNotPenaltyMapper = SpringContext.getApplicationContext().getBean(XingzhengNotPenaltyMapper.class);
		XingzhengNotPenaltyExample xingzhengNotPenaltyExample = new XingzhengNotPenaltyExample();
		xingzhengNotPenaltyExample.createCriteria().andCaseIdEqualTo(caseId);
		List<XingzhengNotPenalty> xingzhengNotLianFormList = xingzhengNotPenaltyMapper.selectByExample(xingzhengNotPenaltyExample);
		
		XingzhengNotPenalty xingzhengNotPenalty = null;
		if(xingzhengNotLianFormList.size()>0){
			xingzhengNotPenalty = xingzhengNotLianFormList.get(0);
		}
		
		//行政结案附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		
		//判断“重新办理”按钮是否展示
		CaseStepMapper caseStepMapper = SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
		CaseBasicMapper caseBasicMapper= SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
		CaseStepExample example=new CaseStepExample();
		example.createCriteria().andCaseIdEqualTo(caseId).andCaseStateEqualTo(Const.ChUFA_PROC_5);
		example.setOrderByClause("STEP_ID DESC");
		List<CaseStep> steps=caseStepMapper.selectByExample(example);
		CaseStep caseStep=new CaseStep();
		//获取案件回退步骤
		if(steps!=null && steps.size()>0){
			caseStep=steps.get(0);
		}
		CaseBasic caseBasic=caseBasicMapper.selectByPrimaryKey(caseId);
		//显示案件"重新办理"条件，1.案件回退步骤caseStep案件状态与caseBasic案件状态一致。
		//2.当前案件回退步骤任务节点是当前用户办理,并且有流程步骤缓存时。
        if (caseStep!=null && caseStep.getCaseState().equals(caseBasic.getCaseState())
        		&& sessionUser.getUserId().equals(caseStep.getAssignPerson())) {
            view.addObject("showBackAndUpdate", true);
        }
        
		view.addObject("caseInfo", procBusinessEntity);
		view.addObject("xingzhengNotPenalty",xingzhengNotPenalty);
		view.addObject("attaMap", attaMap);
		return view;
	}

	/**
     * 用于初始化流程业务实体（案件）
     *
     * @param procBusinessKey
     */
    protected abstract void initProcBusinessEntity(String procBusinessKey);

    /**
     * 业务实体(案件)的录入信息视图
     */
    protected abstract ModelAndView getProcBusinessEntityView();

    /**
     * 默认的视图（动态表单视图）
     *
     * @return
     */
    protected ModelAndView dtformView() {
        MybatisAutoMapperService mybatisAutoMapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
        UserMapper userMapper = SpringContext.getApplicationContext().getBean(UserMapper.class);
        HistoryService historyService = SpringContext.getApplicationContext().getBean(HistoryService.class);
        TaskService taskService = SpringContext.getApplicationContext().getBean(TaskService.class);
        Task task=null;
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(caseStep.getProcInstId()).list();
        if(tasks.size()!=0){
            task=tasks.get(0);
        }
        ModelAndView view = new ModelAndView("workflow/caseProcView/caseStepView");
        //办理步骤view
        //form定义
        FormDef formDef = mybatisAutoMapperService.selectByPrimaryKey(FormDefMapper.class, caseStep.getFormDefId(), FormDef.class);
        view.addObject("formDefJson", formDef.getJsonView());
        //form实例
        FormInstance formInstance = mybatisAutoMapperService.selectByPrimaryKey(FormInstanceMapper.class, caseStep.getTaskInstId(), FormInstance.class);
        view.addObject("fieldValueList", formInstance.getJsonValue());
        view.addObject("caseStep", caseStep);
        if (task != null&&
                !(tasks.size()>1)
                && (task.getTaskDefinitionKey().equals(caseStep.getTargetTaskDefId()))
                && sessionUser.getUserId().equals(caseStep.getAssignPerson())
                && ActivitiUtil.hasProcDefCache(caseStep.getProcDefId())
                ) {//如果上一个任务节点是当前用户办理,并且有流程定义缓存时就显示修改和回退
            view.addObject("showBackAndUpdate", true);
        }
        return view;
    }
    
    protected  ModelAndView getXingzhengLianView(){
		MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/xingzhenglianForm");
		//查询行政立案表单信息
		PenaltyLianFormMapper penaltyLianFormMapper = SpringContext.getApplicationContext().getBean(PenaltyLianFormMapper.class);
		PenaltyLianForm penaltyLianForm = penaltyLianFormMapper.selectByPrimaryKey(caseId);
		//案件当时人列表
		CasePartyExample casePartyExample = new CasePartyExample();
		casePartyExample.createCriteria().andCaseIdEqualTo(caseId);
		List<CaseParty> CasePartyList = mapperService.selectByExample(CasePartyMapper.class, casePartyExample, CaseParty.class);
		CaseService caseService = SpringContext.getApplicationContext().getBean(CaseService.class);
		//涉案企业列表
		List<CaseCompany> caseCompanyList = caseService.getCaseCompanyByCaseId(caseId);
		CasePartyMapper casePartyMapper = SpringContext.getApplicationContext().getBean(CasePartyMapper.class);
		CaseCompanyMapper caseCompanyMapper = SpringContext.getApplicationContext().getBean(CaseCompanyMapper.class);
		//有预警信息的当事人列表
		List<CaseParty> warnCasePartyList=casePartyMapper.queryHistoryBySameOrgAndIdsNO(caseId);
		//有预警信息的涉案企业列表
		List<CaseCompany> warnCaseCompanyList=caseCompanyMapper.queryHistoryBySameOrgAndRegNo(caseId);
		//行政立案附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		
		view.addObject("caseInfo", procBusinessEntity);
		view.addObject("CasePartyList",CasePartyList);
		view.addObject("caseCompanyList",caseCompanyList);
		view.addObject("warnCasePartyList",warnCasePartyList);
		view.addObject("warnCaseCompanyList",warnCaseCompanyList);
		view.addObject("penaltyLianForm",penaltyLianForm);
		view.addObject("attaMap", attaMap);
		
		//判断“重新办理”按钮是否展示
		CaseStepMapper caseStepMapper = SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
		CaseBasicMapper caseBasicMapper= SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
		CaseStepExample example=new CaseStepExample();
		example.createCriteria().andCaseIdEqualTo(caseId).andCaseStateEqualTo(Const.CHUFA_PROC_1);
		example.setOrderByClause("STEP_ID DESC");
		List<CaseStep> steps=caseStepMapper.selectByExample(example);
		CaseStep caseStep=null;
		//获取案件回退步骤
		if(steps!=null && steps.size()>0){
			caseStep=steps.get(0);
		}
		CaseBasic caseBasic=caseBasicMapper.selectByPrimaryKey(caseId);
		//显示案件"重新办理"条件，1.案件回退步骤caseStep案件状态与caseBasic案件状态一致。
		//2.当前案件回退步骤任务节点是当前用户办理,并且有流程步骤缓存时。
        if (caseStep!=null && caseStep.getCaseState().equals(caseBasic.getCaseState())
        		&& sessionUser.getUserId().equals(caseStep.getAssignPerson())) {
            view.addObject("showBackAndUpdate", true);
        }
		return view;
    }
    //转发到行政处罚页面
    protected  ModelAndView getXingzhengChufaView(){
    	MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/xingzhengchufaForm");
		//查询行政处罚表单信息
		/*CrimeCaseFormMapper crimeCaseFormMapper = SpringContext.getApplicationContext().getBean(CrimeCaseFormMapper.class);
		CrimeCaseForm crimeCaseForm = crimeCaseFormMapper.selectByPrimaryKey(caseId);*/
		PenaltyCaseFormMapper penaltyCaseFormMapper = SpringContext.getApplicationContext().getBean(PenaltyCaseFormMapper.class);
		PenaltyCaseForm penaltyCaseForm = penaltyCaseFormMapper.selectByPrimaryKey(caseId);
		//查询行政处罚类型信息
		ChufaTypeFormService chufaTypeFormService = SpringContext.getApplicationContext().getBean(ChufaTypeFormService.class);
		List<ChufaTypeForm> xingzhengChufaTypeList = chufaTypeFormService.selectTypeByCaseId(caseId);
		//案件当时人列表
		CasePartyExample casePartyExample = new CasePartyExample();
		casePartyExample.createCriteria().andCaseIdEqualTo(caseId);
		List<CaseParty> CasePartyList = mapperService.selectByExample(CasePartyMapper.class, casePartyExample, CaseParty.class);
		CaseService caseService = SpringContext.getApplicationContext().getBean(CaseService.class);
		//涉案企业列表
		List<CaseCompany> caseCompanyList = caseService.getCaseCompanyByCaseId(caseId);
		CasePartyMapper casePartyMapper = SpringContext.getApplicationContext().getBean(CasePartyMapper.class);
		CaseCompanyMapper caseCompanyMapper = SpringContext.getApplicationContext().getBean(CaseCompanyMapper.class);
		//有预警信息的当事人列表
		List<CaseParty> warnCasePartyList=casePartyMapper.queryHistoryBySameOrgAndIdsNO(caseId);
		//有预警信息的涉案企业列表
		List<CaseCompany> warnCaseCompanyList=caseCompanyMapper.queryHistoryBySameOrgAndRegNo(caseId);
		//行政处罚附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		
		view.addObject("caseInfo", procBusinessEntity);
		view.addObject("CasePartyList",CasePartyList);
		view.addObject("caseCompanyList",caseCompanyList);
		view.addObject("warnCasePartyList",warnCasePartyList);
		view.addObject("warnCaseCompanyList",warnCaseCompanyList);
		view.addObject("penaltyCaseForm",penaltyCaseForm);
		view.addObject("xingzhengChufaTypeList",xingzhengChufaTypeList);
		view.addObject("attaMap", attaMap);
		
		//判断“重新办理”按钮是否展示
		CaseStepMapper caseStepMapper = SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
		CaseBasicMapper caseBasicMapper= SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
		CaseStepExample example=new CaseStepExample();
		example.createCriteria().andCaseIdEqualTo(caseId).andCaseStateEqualTo(Const.CHUFA_PROC_2);
		example.setOrderByClause("STEP_ID DESC");
		List<CaseStep> steps=caseStepMapper.selectByExample(example);
		CaseStep caseStep=null;
		//获取案件回退步骤
		if(steps!=null && steps.size()>0){
			caseStep=steps.get(0);
		}
		CaseBasic caseBasic=caseBasicMapper.selectByPrimaryKey(caseId);
		//显示案件"重新办理"条件，1.案件回退步骤caseStep案件状态与caseBasic案件状态一致。
		//2.当前案件回退步骤任务节点是当前用户办理,并且有流程步骤缓存时。
        if (caseStep!=null && caseStep.getCaseState().equals(caseBasic.getCaseState())
        		&& sessionUser.getUserId().equals(caseStep.getAssignPerson())) {
            view.addObject("showBackAndUpdate", true);
        }
		return view;
    }
    //转发到移送公安页面
    protected  ModelAndView getYisongGonganView(){
    	MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/yisonggonganForm");
		//查询移送公安表单信息
		CrimeCaseFormMapper crimeCaseFormMapper = SpringContext.getApplicationContext().getBean(CrimeCaseFormMapper.class);
		CrimeCaseForm crimeCaseForm = crimeCaseFormMapper.selectByPrimaryKey(caseId);
		//查询受案单位名称
		OrganiseMapper organiseMapper = SpringContext.getApplicationContext().getBean(OrganiseMapper.class);
		Organise org = organiseMapper.selectByPrimaryKey(crimeCaseForm.getAcceptOrg());
		crimeCaseForm.setAcceptOrgName(org.getOrgName());
		
		//案件当时人列表
		CasePartyExample casePartyExample = new CasePartyExample();
		casePartyExample.createCriteria().andCaseIdEqualTo(caseId);
		List<CaseParty> CasePartyList = mapperService.selectByExample(CasePartyMapper.class, casePartyExample, CaseParty.class);
		CaseService caseService = SpringContext.getApplicationContext().getBean(CaseService.class);
		//涉案企业列表
		List<CaseCompany> caseCompanyList = caseService.getCaseCompanyByCaseId(caseId);
		CasePartyMapper casePartyMapper = SpringContext.getApplicationContext().getBean(CasePartyMapper.class);
		CaseCompanyMapper caseCompanyMapper = SpringContext.getApplicationContext().getBean(CaseCompanyMapper.class);
		//有预警信息的当事人列表
		List<CaseParty> warnCasePartyList=casePartyMapper.queryHistoryBySameOrgAndIdsNO(caseId);
		//有预警信息的涉案企业列表
		List<CaseCompany> warnCaseCompanyList=caseCompanyMapper.queryHistoryBySameOrgAndRegNo(caseId);
		//移送公安附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
	    //查询涉案罪名
        AccuseInfoMapper accuseInfoMapper = SpringContext.getApplicationContext().getBean(AccuseInfoMapper.class);
        List<AccuseInfo> accuseInfoList= accuseInfoMapper.selectCaseZm(caseId, Const.CASE_ZM_TYPE_yisonggongan);
		
		view.addObject("caseInfo", procBusinessEntity);
		view.addObject("CasePartyList",CasePartyList);
		view.addObject("caseCompanyList",caseCompanyList);
		view.addObject("warnCasePartyList",warnCasePartyList);
		view.addObject("warnCaseCompanyList",warnCaseCompanyList);
		view.addObject("crimeCaseForm",crimeCaseForm);
		view.addObject("attaMap", attaMap);
		view.addObject("accuseInfoList",accuseInfoList);
		//判断“重新办理”按钮是否展示
		TaskService taskService = SpringContext.getApplicationContext().getBean(TaskService.class);
		Task task=null;
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(caseStep.getProcInstId()).list();
        if(tasks.size()!=0){
            task=tasks.get(0);
        }
        view.addObject("caseStep", caseStep);
        if (task != null&&
                !(tasks.size()>1)
                && (task.getTaskDefinitionKey().equals(caseStep.getTargetTaskDefId()))
                && sessionUser.getUserId().equals(caseStep.getAssignPerson())
                && ActivitiUtil.hasProcDefCache(caseStep.getProcDefId())
                && caseStep.getCaseState().equals(procBusinessEntity.getState())
                ) {//如果上一个任务节点是当前用户办理,并且有流程定义缓存时就显示修改和回退
            view.addObject("showBackAndUpdate", true);
        }
		
		return view;
    }
    
    //转发到建议移送详情页
    protected  ModelAndView getJianyiYisongView(){
    	String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/suggestYisongForm");
		//查询建议移送表单信息
		SuggestYisongFormMapper suggestYisongFormMapper = 
				SpringContext.getApplicationContext().getBean(SuggestYisongFormMapper.class);
		SuggestYisongForm suggestYisongForm = suggestYisongFormMapper.selectByPrimaryKey(caseId);
		
		//查询附件表
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		
		view.addObject("caseInfo", procBusinessEntity);
		view.addObject("suggestYisongForm",suggestYisongForm);
		view.addObject("attaMap", attaMap);
		return view;
    }
    
    protected  ModelAndView getTuiHuiView(){
    	
    	return null;
    }
    
    //转发到已租出处罚，已结案页面
    protected  ModelAndView getXingzhengJieanView(){
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/xingzhengJieanForm");
		//查询行政结案表单信息
		XingzhengJieanFormMapper xingzhengJieanFormMapper = SpringContext.getApplicationContext().getBean(XingzhengJieanFormMapper.class);
		XingzhengJieanFormExample xingzhengExample = new XingzhengJieanFormExample();
		xingzhengExample.createCriteria().andCaseIdEqualTo(caseId);
		List<XingzhengJieanForm> xingzhengList = xingzhengJieanFormMapper.selectByExample(xingzhengExample);
		XingzhengJieanForm xingzheng1 = null;
		if(xingzhengList.size()>0){
			xingzheng1 = xingzhengList.get(0);
		}
		
		//行政结案附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		
		view.addObject("caseInfo", procBusinessEntity);
		view.addObject("xingzhengJieanForm",xingzheng1);
		view.addObject("attaMap", attaMap);
		return view;
    }
    
	private ModelAndView getXingzhengBuLiAnView() {
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/xingzhengNotLiAnForm");
		//查询行政结案表单信息
		XingzhengNotLianFormMapper xingzhengNotLianFormMapper = SpringContext.getApplicationContext().getBean(XingzhengNotLianFormMapper.class);
		XingzhengNotLianFormExample xingzhengNotLianFormExample = new XingzhengNotLianFormExample();
		xingzhengNotLianFormExample.createCriteria().andCaseIdEqualTo(caseId);
		List<XingzhengNotLianForm> xingzhengNotLianFormList = xingzhengNotLianFormMapper.selectByExample(xingzhengNotLianFormExample);
		XingzhengNotLianForm xingzheng1 = null;
		if(xingzhengNotLianFormList.size()>0){
			xingzheng1 = xingzhengNotLianFormList.get(0);
		}
		
		//行政结案附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		
		//判断“重新办理”按钮是否展示
		CaseStepMapper caseStepMapper = SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
		CaseBasicMapper caseBasicMapper= SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
		CaseStepExample example=new CaseStepExample();
		example.createCriteria().andCaseIdEqualTo(caseId).andCaseStateEqualTo(Const.CHUFA_PROC_3);
		example.setOrderByClause("STEP_ID DESC");
		List<CaseStep> steps=caseStepMapper.selectByExample(example);
		CaseStep caseStep=new CaseStep();
		//获取案件回退步骤
		if(steps!=null && steps.size()>0){
			caseStep=steps.get(0);
		}
		CaseBasic caseBasic=caseBasicMapper.selectByPrimaryKey(caseId);
		//显示案件"重新办理"条件，1.案件回退步骤caseStep案件状态与caseBasic案件状态一致。
		//2.当前案件回退步骤任务节点是当前用户办理,并且有流程步骤缓存时。
        if (caseStep!=null && caseStep.getCaseState().equals(caseBasic.getCaseState())
        		&& sessionUser.getUserId().equals(caseStep.getAssignPerson())) {
            view.addObject("showBackAndUpdate", true);
        }
        
		view.addObject("caseInfo", procBusinessEntity);
		view.addObject("xingzhengNotLiAnForm",xingzheng1);
		view.addObject("attaMap", attaMap);
		return view;
	}
	
	//行政撤案详情展示
	private ModelAndView getXingzhengCheAnView() {
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/xingzhengCancelLiAnForm");
		//查询行政撤案表单信息
		XingzhengCancelLianFormMapper xingzhengCancelLianFormMapper = SpringContext.getApplicationContext().getBean(XingzhengCancelLianFormMapper.class);
		XingzhengCancelLianFormExample xingzhengCancelLianFormExample = new XingzhengCancelLianFormExample();
		xingzhengCancelLianFormExample.createCriteria().andCaseIdEqualTo(caseId);
		List<XingzhengCancelLianForm> xingzhengCancelLianFormList = xingzhengCancelLianFormMapper.selectByExample(xingzhengCancelLianFormExample);
		XingzhengCancelLianForm xingzheng1 = null;
		if(xingzhengCancelLianFormList.size()>0){
			xingzheng1 = xingzhengCancelLianFormList.get(0);
		}
		
		//行政撤案附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		
		//判断“重新办理”按钮是否展示
		CaseStepMapper caseStepMapper = SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
		CaseBasicMapper caseBasicMapper= SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
		CaseStepExample example=new CaseStepExample();
		example.createCriteria().andCaseIdEqualTo(caseId).andCaseStateEqualTo(Const.CHUFA_PROC_4);
		example.setOrderByClause("STEP_ID DESC");
		List<CaseStep> steps=caseStepMapper.selectByExample(example);
		CaseStep caseStep=new CaseStep();
		//获取案件回退步骤
		if(steps!=null && steps.size()>0){
			caseStep=steps.get(0);
		}
		CaseBasic caseBasic=caseBasicMapper.selectByPrimaryKey(caseId);
		//显示案件"重新办理"条件，1.案件回退步骤caseStep案件状态与caseBasic案件状态一致。
		//2.当前案件回退步骤任务节点是当前用户办理,并且有流程步骤缓存时。
        if (caseStep!=null && caseStep.getCaseState().equals(caseBasic.getCaseState())
        		&& sessionUser.getUserId().equals(caseStep.getAssignPerson())) {
            view.addObject("showBackAndUpdate", true);
        }
		        
		view.addObject("caseInfo", procBusinessEntity);
		view.addObject("xingzhengCancelLiAnForm",xingzheng1);
		view.addObject("attaMap", attaMap);
		return view;
	}
    
	private ModelAndView getFenpaiView(){
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/fenpaiForm");
		//需要展示的字段 1.分派机构 2.接收机构 3.分派时间 4.案件编号 5.案件名称 6.案件录入机构
		/*CaseBasicMapper caseBasicMapper = SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
		CaseBasic caseInfo = caseBasicMapper.getFenpaiInfo(caseId);*/
		CaseFenpaiMapper caseFenpaiMapper = SpringContext.getApplicationContext().getBean(CaseFenpaiMapper.class);
		CaseFenpai caseInfo = caseFenpaiMapper.getFenpaiInfo(caseId);
		
		// 查询分派附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap = new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId)
				.andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper
				.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			// ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f" + caseAttachment.getId(), caseAttachment);
		}
		view.addObject("attaMap", attaMap);
		view.addObject("caseInfo", caseInfo);
	      //判断“重新办理”按钮是否展示
        CaseStepMapper caseStepMapper = SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
        CaseBasicMapper caseBasicMapper= SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
        CaseStepExample example=new CaseStepExample();
        example.createCriteria().andCaseIdEqualTo(caseId).andCaseStateEqualTo(Const.CHUFA_PROC_28);
        example.setOrderByClause("STEP_ID DESC");
        List<CaseStep> steps=caseStepMapper.selectByExample(example);
        CaseStep caseStep=new CaseStep();
        //获取案件回退步骤
        if(steps!=null && steps.size()>0){
            caseStep=steps.get(0);
        }
        CaseBasic caseBasic=caseBasicMapper.selectByPrimaryKey(caseId);
        //显示案件"重新办理"条件，1.案件回退步骤caseStep案件状态与caseBasic案件状态一致。
        //2.当前案件回退步骤任务节点是当前用户办理,并且有流程步骤缓存时。
        if (caseStep!=null && caseStep.getCaseState().equals(caseBasic.getCaseState())
                && sessionUser.getUserId().equals(caseStep.getAssignPerson())) {
            view.addObject("showBackAndUpdate", true);
        }
		return view;
	}
	
	private ModelAndView getYiSongGuanXiaView(){
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/yiSongGuanXiaForm");
		//需要展示的字段录入单位，录入时间，接收单位，移送时间
		/*CaseBasicMapper caseBasicMapper = SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
		CaseBasic yiSongGuanXiaInfo = caseBasicMapper.getYiSongGuanXiaInfo(caseId);*/
		CaseTurnoverMapper caseTurnoverMapper = SpringContext.getApplicationContext().getBean(CaseTurnoverMapper.class);
		CaseTurnover yiSongGuanXiaInfo = caseTurnoverMapper.getYiSongGuanXiaInfo(caseId);
		
		// 查询移送管辖附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext
				.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap = new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId)
				.andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper
				.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			// ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f" + caseAttachment.getId(), caseAttachment);
		}
		view.addObject("attaMap", attaMap);
		view.addObject("yiSongGuanXiaInfo", yiSongGuanXiaInfo);
		return view;
	}

	/**
	 * 此类的caseStep属性被初始化后，只能在类内部使用
	 * 外部无法获取，因此写一个get方法
	 * @return
	 */
	public CaseStep getCaseStep() {
		//添加办理人，办理时间
		CaseStepMapper caseStepMapper = SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
		CaseStep assignPersonInfo = caseStepMapper.getAssignPersonInfo(caseStep.getStepId());
		caseStep.setAssignPersonName(assignPersonInfo.getAssignPersonName());
		caseStep.setAssignPersonOrgName(assignPersonInfo.getAssignPersonOrgName());
		return caseStep;
	}
	
	
	//移送行政拘留表单详情
	private ModelAndView getCaseTransferRdetentionView() {
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/transferRdetentionView");
		//查询移送行政拘留表单信息
		TransferDetentionMapper transferRdetentionMapper=SpringContext.getApplicationContext().getBean(TransferDetentionMapper.class);
		List<TransferDetention> transferRdetentionList = transferRdetentionMapper.getByCaseId(caseId);
		
		TransferDetention transferRdetention = null;
		if(transferRdetentionList.size()>0){
			transferRdetention = transferRdetentionList.get(0);
		}
		
		//行政结案附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
        
		view.addObject("transferRdetention",transferRdetention);
		view.addObject("attaMap", attaMap);
		return view;
	}
	
	//公安办理行政拘留信息详情
	private ModelAndView getCaseDetentionInfoView() {
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/detentionInfoView");
		//查询公安办理行政拘留信息
		DetentionInfoMapper detentionInfoMapper=SpringContext.getApplicationContext().getBean(DetentionInfoMapper.class);
		
		DetentionInfoExample detentionInfoExample = new DetentionInfoExample();
		detentionInfoExample.createCriteria().andCaseIdEqualTo(caseId);
		List<DetentionInfo> detentionInfonList = detentionInfoMapper.selectByExample(detentionInfoExample);
		
		DetentionInfo detentionInfo = null;
		if(detentionInfonList.size()>0){
			detentionInfo = detentionInfonList.get(0);
		}
		
		//公安办理行政拘留信息附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
        
		view.addObject("detentionInfo",detentionInfo);
		view.addObject("attaMap", attaMap);
		return view;
	}
	//要求说明不立案理由详情
	private ModelAndView getReqNoLianReasonView() {
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/reqNoLianReason");
		CaseRequireNolianReasonMapper requireNolianReasonMapper = SpringContext.getApplicationContext().getBean(CaseRequireNolianReasonMapper.class);
		CaseRequireNolianReason require = requireNolianReasonMapper.selectByCaseId(caseId);
		CaseStep caseStep = getCaseStep();
		//附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		view.addObject("caseStep", caseStep);
		view.addObject("require", require);
		view.addObject("attaMap", attaMap);
		return view;
	}
	//公安说明不（予）立案理由
	private ModelAndView getNoLianReasonView() {
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/noLianReasonView");
		CaseNolianReasonMapper nolianReasonMapper = SpringContext.getApplicationContext().getBean(CaseNolianReasonMapper.class);
		CaseNolianReason reason = nolianReasonMapper.selectByCaseId(caseId);
		CaseStep caseStep = getCaseStep();
		//附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		view.addObject("caseStep", caseStep);
		view.addObject("reason", reason);
		view.addObject("attaMap", attaMap);
		return view;
	}
	//通知公安立案
	private ModelAndView getReqLianView() {
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/requireLianView");
		CaseRequireLianMapper caseRequireLianMapper = SpringContext.getApplicationContext().getBean(CaseRequireLianMapper.class);
		CaseRequireLian requireLian = caseRequireLianMapper.selectByCaseId(caseId);
		CaseStep caseStep = getCaseStep();
		//附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		view.addObject("caseStep", caseStep);
		view.addObject("requireLian", requireLian);
		view.addObject("attaMap", attaMap);
		return view;
	}
	//同意公安不立案理由
	private ModelAndView getAgreeNoLian() {
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/agreeNoLianView");
		CaseAgreeNolianMapper caseAgreeNoLianMapper = SpringContext.getApplicationContext().getBean(CaseAgreeNolianMapper.class);
		CaseAgreeNolian agreeNoLian = caseAgreeNoLianMapper.selectByCaseId(caseId);
		CaseStep caseStep = getCaseStep();
		//附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		view.addObject("caseStep", caseStep);
		view.addObject("agreeNoLian", agreeNoLian);
		view.addObject("attaMap", attaMap);
		return view;
	}
	
	//公安办理不予拘留信息详情
	private ModelAndView getCaseNotDetentionInfoView() {
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/notDetentionInfoView");
		//查询公安办理不予拘留信息
		DetentionInfoMapper detentionInfoMapper=SpringContext.getApplicationContext().getBean(DetentionInfoMapper.class);
		
		DetentionInfoExample detentionInfoExample = new DetentionInfoExample();
		detentionInfoExample.createCriteria().andCaseIdEqualTo(caseId);
		List<DetentionInfo> detentionInfonList = detentionInfoMapper.selectByExample(detentionInfoExample);
		
		DetentionInfo detentionInfo = null;
		if(detentionInfonList.size()>0){
			detentionInfo = detentionInfonList.get(0);
		}
		
		//公安办理不予拘留信息附件
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
        
		view.addObject("detentionInfo",detentionInfo);
		view.addObject("attaMap", attaMap);
		return view;
	}
}
