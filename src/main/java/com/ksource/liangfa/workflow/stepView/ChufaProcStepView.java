package com.ksource.liangfa.workflow.stepView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.domain.AccuseRule;
import com.ksource.liangfa.domain.CaseAccuseRuleRelation;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseAttachmentExample;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CasePartyExample;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.domain.CrimeCaseExt;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.DqdjCategory;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.TaskAction;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.mapper.AccuseRuleMapper;
import com.ksource.liangfa.mapper.CaseAccuseRuleRelationMapper;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CasePartyMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.mapper.CrimeCaseExtMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.DqdjCategoryMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.liangfa.workflow.ActivitiUtil;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;

/**
 *处罚案件办理步骤视图<br>
 *@author gengzi
 *@data 2012-3-14
 */
public class ChufaProcStepView extends CaseProcStepView<CaseBasic> {

	public ChufaProcStepView(CaseStep caseStep,User sessionUser) {
		super( caseStep,sessionUser);
	}

	protected ModelAndView dtformView(){
		MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
		//已处罚主动移送特殊处理
		//1.流程节点上绑定自定义的流程变量--变量名：variable  值:1
		//根据流程变量判断显示内容
		Integer actionId = caseStep.getTaskActionId();
		 TaskAction taskAction = mapperService.selectByPrimaryKey(TaskActionMapper.class, actionId, TaskAction.class);
		 if(taskAction!= null){
			 String variable = taskAction.getProcVarName();
			 String valule = taskAction.getProcVarValue();
			 //若果符合已处罚主动移送绑定的变量信息，显示其对应页面
			 if(ActivitiUtil.PROC_VAR_NAME.equals(variable) && ActivitiUtil.PROC_PENALTY_TRANSFER_VALUE.equals(Integer.parseInt(valule))){
					return getPenaltyTransferView();
			 }
		 }
		AccuseInfoMapper accuseInfoMapper = SpringContext.getApplicationContext().getBean(AccuseInfoMapper.class);
		CaseXianyirenMapper caseXianyirenMapper = SpringContext.getApplicationContext().getBean(CaseXianyirenMapper.class);
		String caseId = this.procBusinessEntity.getBusinessKey();
		int actionType=caseStep.getActionType();
		//获得办理步骤的目标任务办理机构
		String targetOrgType  =caseStep.getTargetOrgType();
		User transactUser = mapperService.selectByPrimaryKey(UserMapper.class, caseStep.getAssignPerson(), User.class);
		Organise transactUserOrg = mapperService.selectByPrimaryKey(OrganiseMapper.class, transactUser.getOrgId(), Organise.class);
		//1行政机关用户无权查看，其他非行政机关办理的案件步骤(最后步骤可以看到，提交给行政机关的可以看到)
        boolean isXingzheng = sessionUser.getOrganise()==null?false:sessionUser.getOrganise().getOrgType().equals(Const.ORG_TYPE_XINGZHENG);
    		if((  isXingzheng
				&& !transactUserOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) )
				&& targetOrgType!=null
				&& !(targetOrgType!=null
					&&(
						targetOrgType.equals(Const.ORG_TYPE_XINGZHENG)
						|| targetOrgType.equals(Const.TASK_ASSGIN_EQUALS_INPUTER) 
						|| targetOrgType.equals(Const.TASK_ASSGIN_IS_INPUTER)
					   )
				  )
				){
			ModelAndView view = new ModelAndView("workflow/caseProcView/noAccess");
			view.addObject("caseStep", caseStep);
			return view;
		}else{
			ModelAndView view = super.dtformView();
			//特殊任务视图处理
			List<CaseXianyiren> xianyirenList = null;
			CaseXianyirenExample caseXianyirenExample = null;
			switch (actionType) {
			case Const.TASK_ACTION_TYPE_YISONG_GONAN:
				view.setViewName("workflow/caseProcView/yisonggongan");
				List<AccuseInfo> accuseInfoList= accuseInfoMapper.selectCaseZm(caseId, Const.CASE_ZM_TYPE_yisonggongan);
				view.addObject("accuseInfoList",accuseInfoList);
				break;
			case Const.TASK_ACTION_TYPE_TIQINGDAIBU:
				view.setViewName("workflow/caseProcView/tiqingdaibu");
				caseXianyirenExample = new CaseXianyirenExample();
				caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId).
				andDaibuStateNotEqualTo(Const.XIANYIREN_DAIBU_STATE_NOTYET);
				xianyirenList = mapperService.selectByExample(CaseXianyirenMapper.class, caseXianyirenExample, CaseXianyiren.class);
				for(CaseXianyiren caseXianyiren:xianyirenList){
					caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqingdaibuZm));//查询罪名
				}
				break;
			case Const.TASK_ACTION_TYPE_SHENCHADAIBU:
				view.setViewName("workflow/caseProcView/shenchadaibu");
				caseXianyirenExample = new CaseXianyirenExample();
				caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId).
				andDaibuStateNotEqualTo(Const.XIANYIREN_DAIBU_STATE_NOTYET);
				xianyirenList = mapperService.selectByExample(CaseXianyirenMapper.class, caseXianyirenExample, CaseXianyiren.class);
				for(CaseXianyiren caseXianyiren:xianyirenList){
					caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqingdaibuZm));//查询罪名
				}
				break;
			case Const.TASK_ACTION_TYPE_TIQINGQISU:
				view.setViewName("workflow/caseProcView/tiqingqisu");
				caseXianyirenExample = new CaseXianyirenExample();
				caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId).
				andTiqingqisuStateEqualTo(Const.XIANYIREN_TIQINGQISU_STATE_YES);
				xianyirenList = mapperService.selectByExample(CaseXianyirenMapper.class, caseXianyirenExample, CaseXianyiren.class);
				for(CaseXianyiren caseXianyiren:xianyirenList){
					caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqingqisuZm));//查询罪名
				}
				break;
			case Const.TASK_ACTION_TYPE_TIQIGONGSU:
				view.setViewName("workflow/caseProcView/tiqigongsu");
				caseXianyirenExample = new CaseXianyirenExample();
				caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId)
				.andTiqigongsuStateNotEqualTo(Const.XIANYIREN_TIQIGONGSU_STATE_NOTYET);
				xianyirenList = mapperService.selectByExample(CaseXianyirenMapper.class, caseXianyirenExample, CaseXianyiren.class);
				for(CaseXianyiren caseXianyiren:xianyirenList){
					caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqigongsuZm));//查询罪名
					caseXianyiren.setAccuseInfoList2(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqingqisuZm));//查询罪名
				}
				break;
			case Const.TASK_ACTION_TYPE_BUQISU://展示结果与提起公诉相同（只展示不起诉记录）
				view.setViewName("workflow/caseProcView/tiqigongsu");
				caseXianyirenExample = new CaseXianyirenExample();
				caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId)
				.andTiqigongsuStateEqualTo(Const.XIANYIREN_TIQIGONGSU_STATE_NO);
				xianyirenList = mapperService.selectByExample(CaseXianyirenMapper.class, caseXianyirenExample, CaseXianyiren.class);
				for(CaseXianyiren caseXianyiren:xianyirenList){
					caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqigongsuZm));//查询罪名
					caseXianyiren.setAccuseInfoList2(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqingqisuZm));//查询罪名
				}
				break;
			case Const.TASK_ACTION_TYPE_FAYUANPANJUE:
				view.setViewName("workflow/caseProcView/fayuanpanjue");
				CaseXianyiren xianyiren = new CaseXianyiren();
				xianyiren.setCaseId(caseId);
				xianyiren.setTiqigongsuState(Const.XIANYIREN_TIQIGONGSU_STATE_YES);
				xianyirenList = caseXianyirenMapper.findAll(xianyiren);
				for(CaseXianyiren caseXianyiren:xianyirenList){
					caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_panjue1Zm));//查询罪名
					caseXianyiren.setAccuseInfoList2(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_panjue2Zm));//查询罪名
					caseXianyiren.setAccuseInfoList3(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqigongsuZm));//嫌疑人起诉罪名查询
				}
				break;
			default:
				break;
			}
			view.addObject("xianyirenList",xianyirenList);
			return view;
		}
	
	}
	
	@Override
	protected ModelAndView getProcBusinessEntityView() {
		MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		view.setViewName("workflow/caseProcView/chufaCaseInfo");
		//案件当时人列表
		CasePartyExample casePartyExample = new CasePartyExample();
		casePartyExample.createCriteria().andCaseIdEqualTo(caseId);
		List<CaseParty> CasePartyList = mapperService.selectByExample(CasePartyMapper.class, casePartyExample, CaseParty.class);
		CaseService caseService = SpringContext.getApplicationContext().getBean(CaseService.class);
		//涉案企业列表
		List<CaseCompany> caseCompanyList = caseService.getCaseCompanyByCaseId(caseId);
		CasePartyMapper casePartyMapper = SpringContext.getApplicationContext().getBean(CasePartyMapper.class);
		CaseCompanyMapper caseCompanyMapper = SpringContext.getApplicationContext().getBean(CaseCompanyMapper.class);
		CaseAttachmentMapper caseAttachmentMapper = SpringContext.getApplicationContext().getBean(CaseAttachmentMapper.class);
		//有预警信息的当事人列表
		List<CaseParty> warnCasePartyList=casePartyMapper.queryHistoryBySameOrgAndIdsNO(caseId);
		//有预警信息的涉案企业列表
		List<CaseCompany> warnCaseCompanyList=caseCompanyMapper.queryHistoryBySameOrgAndRegNo(caseId);
		//查询行政处罚附件
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		view.addObject("attaMap", attaMap);
		view.addObject("caseInfo", procBusinessEntity);
		view.addObject("CasePartyList",CasePartyList);
		view.addObject("caseCompanyList",caseCompanyList);
		view.addObject("warnCasePartyList",warnCasePartyList);
		view.addObject("warnCaseCompanyList",warnCaseCompanyList);
		return view;
	}
	
	private ModelAndView getPenaltyTransferView() {
		String caseId = this.procBusinessEntity.getBusinessKey();
		ModelAndView view = new ModelAndView();
		//查询行政处罚扩展信息
		//PenaltyCaseExtMapper penaltyCaseExtMapper = SpringContext.getApplicationContext().getBean(PenaltyCaseExtMapper.class);
		//PenaltyCaseExt penaltyCaseExt =  penaltyCaseExtMapper.selectByPrimaryKey(procBusinessEntity.getBusinessKey());
		//查询涉嫌犯罪扩展信息
		CrimeCaseExtMapper crimeCaseExtMapper = SpringContext.getApplicationContext().getBean(CrimeCaseExtMapper.class);
		CrimeCaseExt crimeCaseExt =  crimeCaseExtMapper.selectByPrimaryKey(procBusinessEntity.getBusinessKey());
		//查询行政处罚附件
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
		
	    //view.addObject("penaltyCaseExt",penaltyCaseExt);
        view.addObject("crimeCaseExt", crimeCaseExt);
		view.addObject("attaMap", attaMap);
		view.addObject("caseInfo", procBusinessEntity);
		view.addObject("accuseInfoList",accuseInfoList);
		
        view.setViewName("workflow/caseProcView/penaltyTransferCaseInfo");
        return view;
	}

    @Override
	protected void initProcBusinessEntity(String procBusinessKey) {
		MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
		CaseAccuseRuleRelationMapper caseAccuseRuleRelationMapper = SpringContext.getApplicationContext().getBean(CaseAccuseRuleRelationMapper.class);
		AccuseRuleMapper accuseRuleMapper = SpringContext.getApplicationContext().getBean(AccuseRuleMapper.class);
		this.procBusinessEntity=mapperService.selectByPrimaryKey(CaseBasicMapper.class, procBusinessKey, CaseBasic.class);
		User user = mapperService.selectByPrimaryKey(UserMapper.class, procBusinessEntity.getInputer(), User.class);
		Organise org = mapperService.selectByPrimaryKey(OrganiseMapper.class, user.getOrgId(), Organise.class);
		procBusinessEntity.setInputerName(user.getUserName());
		procBusinessEntity.setOrgName(org.getOrgName());
		//查询案发区域名称
		DistrictMapper districtMapper = SpringContext.getApplicationContext().getBean(DistrictMapper.class);
		District district = districtMapper.selectByPrimaryKey(procBusinessEntity.getAnfaAddress());
		procBusinessEntity.setAnfaAddressName(district.getDistrictName());
		
		// 获取案件罪名规则
		List<CaseAccuseRuleRelation> caseAccuseRuleRelations = caseAccuseRuleRelationMapper.selectByCaseId(procBusinessKey);// procBusinessEntity.getBusinessKey()
		StringBuilder accuseRuleNames = new StringBuilder();
		if (caseAccuseRuleRelations.size() > 0) {
			for (CaseAccuseRuleRelation caseAccuseRuleRelation : caseAccuseRuleRelations) {
				// 获取的罪名规则id不为空时
				AccuseRule accuseRule = accuseRuleMapper.selectById(Long.valueOf(caseAccuseRuleRelation.getRuleId()));
				if (accuseRule != null && StringUtils.isNotBlank(accuseRule.getName())) {
					accuseRuleNames.append(accuseRule.getName() + "。<br/>");
				}
			}
		}
		
		procBusinessEntity.setAccuseRuleNames(accuseRuleNames.toString());
		
		// 当量刑标准不为空时，根据caseId查询量刑标准对应的疑似罪名信息
		if (StringUtils.isNotBlank(accuseRuleNames.toString())) {
			AccuseInfoMapper accuseInfoMapper = SpringContext.getApplicationContext().getBean(AccuseInfoMapper.class);
			StringBuilder accuseNameStr = new StringBuilder();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("caseId", procBusinessEntity.getBusinessKey());
			List<AccuseInfo> ailist = accuseInfoMapper.getAccuseByCaseId(map);
			if (ailist != null && ailist.size() > 0) {
				for (AccuseInfo ai : ailist) {
					accuseNameStr.append(ai.getName() + "<br/>");
				}
				procBusinessEntity.setAccuseNameStr(accuseNameStr.toString());
			}
		}
		//查询打侵打假类型
		DqdjCategoryMapper dqdjCategoryMapper=SpringContext.getApplicationContext().getBean(DqdjCategoryMapper.class);
		DqdjCategory dqdjCategory = dqdjCategoryMapper.getByCaseId(procBusinessKey);
		if(dqdjCategory!=null && dqdjCategory.getCategoryId()>0){
			procBusinessEntity.setDqdjType(dqdjCategory.getCategoryId());
			procBusinessEntity.setDqdjTypeName(dqdjCategory.getName());
		}
	}

}
