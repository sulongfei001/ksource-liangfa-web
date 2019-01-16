package com.ksource.liangfa.workflow.stepView;

import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.mapper.*;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 处罚案件办理步骤视图<br>
 *
 * @author gengzi
 * @data 2012-3-14
 */
public class YisongProcStepView extends CaseProcStepView<CaseBasic> {

    public YisongProcStepView(CaseStep caseStep, User sessionUser) {
        super(caseStep, sessionUser);
    }

    protected ModelAndView dtformView() {
        MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
        AccuseInfoMapper accuseInfoMapper = SpringContext.getApplicationContext().getBean(AccuseInfoMapper.class);
        String caseId = this.procBusinessEntity.getBusinessKey();
        int actionType = caseStep.getActionType();
        //获得办理步骤的目标任务办理机构
        String targetOrgType = caseStep.getTargetOrgType();
        User transactUser = mapperService.selectByPrimaryKey(UserMapper.class, caseStep.getAssignPerson(), User.class);
        Organise transactUserOrg = mapperService.selectByPrimaryKey(OrganiseMapper.class, transactUser.getOrgId(), Organise.class);
        //1行政机关用户无权查看，其他非行政机关办理的案件步骤(最后步骤可以看到，提交给行政机关的可以看到)
        boolean isXingzheng= sessionUser.getOrganise()==null?false:sessionUser.getOrganise().getOrgType().equals(Const.ORG_TYPE_XINGZHENG);
        if ((isXingzheng
                && !transactUserOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG))
                && targetOrgType != null
                && !(targetOrgType != null
                && (
                targetOrgType.equals(Const.ORG_TYPE_XINGZHENG)
                        || targetOrgType.equals(Const.TASK_ASSGIN_EQUALS_INPUTER)
                        || targetOrgType.equals(Const.TASK_ASSGIN_IS_INPUTER)
        )
        )
                ) {
            ModelAndView view = new ModelAndView("workflow/caseProcView/noAccess");
            view.addObject("caseStep", caseStep);
            return view;
        } else {
            ModelAndView view = super.dtformView();
            return view;
        }
    }

    @Override
    protected ModelAndView getProcBusinessEntityView() {
        MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
        String caseId = this.procBusinessEntity.getBusinessKey();
        ModelAndView view = new ModelAndView();

        view.setViewName("workflow/caseProcView/yisongChufaCaseInfo");
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
        List<CaseParty> warnCasePartyList = casePartyMapper.queryHistoryBySameOrgAndIdsNO(caseId);
        //有预警信息的涉案企业列表
        List<CaseCompany> warnCaseCompanyList = caseCompanyMapper.queryHistoryBySameOrgAndRegNo(caseId);
        //扩展信息
        PenaltyReferCaseExtMapper penaltyReferCaseExtMapper = SpringContext.getApplicationContext().getBean(PenaltyReferCaseExtMapper.class);
        PenaltyReferCaseExt penaltyReferCaseExt = penaltyReferCaseExtMapper.selectByPrimaryKey(procBusinessEntity.getBusinessKey());
        view.addObject("penaltyReferCaseExt", penaltyReferCaseExt);
        //如果有附件，则查询附件
        if (penaltyReferCaseExt.getFileId() != null) {
            CaseAttachment atta = caseAttachmentMapper.selectByPrimaryKey(Long.valueOf(penaltyReferCaseExt.getFileId()));
            view.addObject("atta", atta);
        }
        view.addObject("caseInfo", procBusinessEntity);
        view.addObject("CasePartyList", CasePartyList);
        view.addObject("caseCompanyList", caseCompanyList);
        view.addObject("warnCasePartyList", warnCasePartyList);
        view.addObject("warnCaseCompanyList", warnCaseCompanyList);
        return view;
    }

    @Override
    protected void initProcBusinessEntity(String procBusinessKey) {
        MybatisAutoMapperService mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
        this.procBusinessEntity = mapperService.selectByPrimaryKey(CaseBasicMapper.class, procBusinessKey, CaseBasic.class);
        User user = mapperService.selectByPrimaryKey(UserMapper.class, procBusinessEntity.getInputer(), User.class);
        Organise org = mapperService.selectByPrimaryKey(OrganiseMapper.class, user.getOrgId(), Organise.class);
        procBusinessEntity.setInputerName(user.getUserName());
        procBusinessEntity.setOrgName(org.getOrgName());
    }
}
