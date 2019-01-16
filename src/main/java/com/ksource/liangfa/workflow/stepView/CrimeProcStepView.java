package com.ksource.liangfa.workflow.stepView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseAttachmentExample;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CasePartyExample;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CasePartyMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.mapper.CrimeCaseExtMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;

/**
 *处罚案件办理步骤视图<br>
 *@author gengzi
 *@data 2012-3-14
 */
public class CrimeProcStepView extends CaseProcStepView<CaseBasic> {

	public CrimeProcStepView(CaseStep caseStep, User sessionUser) {
		super( caseStep,sessionUser);
	}

	protected ModelAndView dtformView(){
		MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
		AccuseInfoMapper accuseInfoMapper = SpringContext.getApplicationContext().getBean(AccuseInfoMapper.class);
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
				caseXianyirenExample = new CaseXianyirenExample();
				caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId)
				.andTiqigongsuStateEqualTo(Const.XIANYIREN_TIQIGONGSU_STATE_YES);
				xianyirenList = mapperService.selectByExample(CaseXianyirenMapper.class, caseXianyirenExample, CaseXianyiren.class);
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

		view.setViewName("workflow/caseProcView/crimeCaseInfo");
		CasePartyExample casePartyExample = new CasePartyExample();
		casePartyExample.createCriteria().andCaseIdEqualTo(caseId);
		//案件当时人列表
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
		//查询附件
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CRIME_PROC_KEY);
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

        CrimeCaseExtMapper crimeCaseExtMapper = SpringContext.getApplicationContext().getBean(CrimeCaseExtMapper.class);
        view.addObject("crimeCaseExt",crimeCaseExtMapper.selectByPrimaryKey(procBusinessEntity.getBusinessKey()));
		return view;
	}

    @Override
	protected void initProcBusinessEntity(String procBusinessKey) {
		MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
		this.procBusinessEntity=mapperService.selectByPrimaryKey(CaseBasicMapper.class, procBusinessKey, CaseBasic.class);
		User user = mapperService.selectByPrimaryKey(UserMapper.class, procBusinessEntity.getInputer(), User.class);
		Organise acceptOrg=mapperService.selectByPrimaryKey(OrganiseMapper.class,Integer.parseInt(procBusinessEntity.getAcceptUnit()), Organise.class);
		Organise org = mapperService.selectByPrimaryKey(OrganiseMapper.class, user.getOrgId(), Organise.class);
		procBusinessEntity.setAcceptUnitName(acceptOrg.getOrgName());
		procBusinessEntity.setInputerName(user.getUserName());
		procBusinessEntity.setOrgName(org.getOrgName());
	}
}
