package com.ksource.liangfa.service.casehandle;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.alibaba.fastjson.JSON;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.jms.MessageProducer;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.CosineSimilarityUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseAccuseKey;
import com.ksource.liangfa.domain.CaseAgreeNolian;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseBasicExample;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseCompanyExample;
import com.ksource.liangfa.domain.CaseFenpai;
import com.ksource.liangfa.domain.CaseIllegalSituation;
import com.ksource.liangfa.domain.CaseIllegalSituationExample;
import com.ksource.liangfa.domain.CaseNolianReason;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CasePartyExample;
import com.ksource.liangfa.domain.CaseRequireLian;
import com.ksource.liangfa.domain.CaseRequireNolianReason;
import com.ksource.liangfa.domain.CaseState;
import com.ksource.liangfa.domain.CaseStateExample;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.CaseTodoExample;
import com.ksource.liangfa.domain.CaseTurnover;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.domain.CaseYisongNotice;
import com.ksource.liangfa.domain.ChufaTypeForm;
import com.ksource.liangfa.domain.CompanyLib;
import com.ksource.liangfa.domain.CrimeCaseForm;
import com.ksource.liangfa.domain.Dictionary;
import com.ksource.liangfa.domain.DictionaryExample;
import com.ksource.liangfa.domain.DqdjCaseCategory;
import com.ksource.liangfa.domain.IllegalSituation;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.PenaltyCaseForm;
import com.ksource.liangfa.domain.PenaltyLianForm;
import com.ksource.liangfa.domain.PeopleLib;
import com.ksource.liangfa.domain.ProcDeploy;
import com.ksource.liangfa.domain.DetentionInfo;
import com.ksource.liangfa.domain.SuggestYisongForm;
import com.ksource.liangfa.domain.TaskAction;
import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.domain.TaskBindKey;
import com.ksource.liangfa.domain.TransferDetention;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.XingzhengCancelLianForm;
import com.ksource.liangfa.domain.XingzhengJieanForm;
import com.ksource.liangfa.domain.XingzhengNotLianForm;
import com.ksource.liangfa.domain.XingzhengNotPenalty;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.mapper.CaseAccuseMapper;
import com.ksource.liangfa.mapper.CaseAgreeNolianMapper;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CaseFenpaiMapper;
import com.ksource.liangfa.mapper.CaseIllegalSituationMapper;
import com.ksource.liangfa.mapper.CaseNolianReasonMapper;
import com.ksource.liangfa.mapper.CasePartyMapper;
import com.ksource.liangfa.mapper.CaseRequireLianMapper;
import com.ksource.liangfa.mapper.CaseRequireNolianReasonMapper;
import com.ksource.liangfa.mapper.CaseStateMapper;
import com.ksource.liangfa.mapper.CaseStepMapper;
import com.ksource.liangfa.mapper.CaseSuspectedAccuseMapper;
import com.ksource.liangfa.mapper.CaseTodoMapper;
import com.ksource.liangfa.mapper.CaseTurnoverMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.mapper.CaseYisongNoticeMapper;
import com.ksource.liangfa.mapper.ChufaTypeFormMapper;
import com.ksource.liangfa.mapper.CompanyLibMapper;
import com.ksource.liangfa.mapper.CrimeCaseFormMapper;
import com.ksource.liangfa.mapper.DictionaryMapper;
import com.ksource.liangfa.mapper.DqdjCaseCategoryMapper;
import com.ksource.liangfa.mapper.IllegalSituationMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PenaltyCaseFormMapper;
import com.ksource.liangfa.mapper.PenaltyLianFormMapper;
import com.ksource.liangfa.mapper.PeopleLibMapper;
import com.ksource.liangfa.mapper.ProcDeployMapper;
import com.ksource.liangfa.mapper.DetentionInfoMapper;
import com.ksource.liangfa.mapper.SuggestYisongFormMapper;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.mapper.TaskBindMapper;
import com.ksource.liangfa.mapper.TransferDetentionMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.mapper.XingzhengCancelLianFormMapper;
import com.ksource.liangfa.mapper.XingzhengJieanFormMapper;
import com.ksource.liangfa.mapper.XingzhengNotLianFormMapper;
import com.ksource.liangfa.mapper.XingzhengNotPenaltyMapper;
import com.ksource.liangfa.service.system.AccuseRuleService;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.liangfa.service.workflow.WorkflowService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.liangfa.workflow.ActivitiUtil;
import com.ksource.liangfa.workflow.ProcessFactory;
import com.ksource.liangfa.workflow.proc.CaseProcessUtil;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Service
@SuppressWarnings("all")
public class CaseTodoServiceImpl implements CaseTodoService {
    
    private static final Logger logger = LoggerFactory.getLogger(CaseTodoServiceImpl.class);
    
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private CaseTodoMapper caseTodoMapper;
	@Autowired
	private CaseAttachmentMapper caseAttachmentMapper;
	@Autowired
	private PenaltyLianFormMapper penaltyLianFormMapper;
	@Autowired
	private CaseBasicMapper caseBasicMapper;
	@Autowired
	private CaseStateMapper caseStateMapper;
	@Autowired
	private CaseStepMapper caseStepMapper;
	@Autowired
	private PenaltyCaseFormMapper penaltyCaseFormMapper;
	@Autowired
	private DictionaryMapper dictionaryMapper;
	@Autowired
	private CrimeCaseFormMapper crimeCaseFormMapper;
	@Autowired
	private OrganiseMapper organiseMapper;
	@Autowired
	private CaseYisongNoticeMapper caseYisongNoticeMapper;
    @Autowired
    private ProcDeployMapper procDeployMapper;
    @Autowired
    private CaseIllegalSituationMapper caseIllegalSituationMapper;
    @Autowired
    private CaseAccuseMapper caseAccuseMapper;
    @Autowired
    private SuggestYisongFormMapper suggestYisongFormMapper;
    @Autowired
    private DqdjCaseCategoryMapper dqdjCaseCategoryMapper;
    @Autowired
    private XingzhengJieanFormMapper xingzhengJieanFormMapper;
    @Autowired
    private AccuseInfoMapper accuseInfoMapper;
    @Autowired
    private CaseSuspectedAccuseMapper caseSuspectedAccuseMapper;
    @Autowired
    private CasePartyMapper casePartyMapper;
    @Autowired
    private CaseXianyirenMapper caseXianyirenMapper;
    @Autowired
    private PeopleLibMapper peopleLibMapper;
    @Autowired
    private CaseCompanyMapper caseCompanyMapper;
    @Autowired
    private CompanyLibMapper companyLibMapper;
    @Autowired
    private UserMapper userMapper;
	@Autowired
	private MessageProducer messageProducer;
	@Autowired
	private IllegalSituationMapper illegalSituationMapper;
	@Autowired
	private CaseTurnoverMapper caseTurnoverMapper;
	@Autowired
	private TaskActionMapper taskActionMapper;
	@Autowired
	private TaskBindMapper taskBindMapper;
	@Autowired
    private UserService userService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private XingzhengNotLianFormMapper xingzhengNotLianFormMapper;
	@Autowired
	private XingzhengCancelLianFormMapper xingzhengCancelLianFormMapper;
	@Autowired
	private ChufaTypeFormMapper chufaTypeFormMapper;
	@Autowired
	private CaseFenpaiMapper caseFenpaiMapper;
	@Autowired
	private XingzhengNotPenaltyMapper xingzhengNotPenaltyMapper;
	@Autowired
	private InstantMessageService instantMessageService;
    @Autowired
	private AccuseRuleService  accuseRuleService;
    @Autowired
	private TransferDetentionMapper transferDetentionMapper;
    @Autowired
    private DetentionInfoMapper detentionInfoMapper;
    @Autowired
    private CaseRequireNolianReasonMapper requireNoLianReasonMapper;
    @Autowired
    private CaseNolianReasonMapper nolianReasonMapper;
    @Autowired
    private CaseRequireLianMapper caseRequireLianMapper;
    @Autowired
    private CaseAgreeNolianMapper caseAgreeNoLianMapper;
    
	@Override
	@Transactional
	public PaginationHelper<CaseTodo> find(CaseTodo caseTodo, String page) {
		return systemDAO.find(caseTodo,null,page,
				"com.ksource.liangfa.mapper.CaseTodoMapper.getCaseTodoCount",
				"com.ksource.liangfa.mapper.CaseTodoMapper.getCaseTodoList");
	}

	@Override
	public int getTodoCount(Integer orgCode) {
		return caseTodoMapper.getTodoCount(orgCode);
	}
	
	/**
	 * 行政机关立案
	 */
	@Override
	@Transactional
	public ServiceResponse lianAdd(CaseBasic caseBasic2,PenaltyLianForm penaltyLianForm,MultipartRequest multipartRequest,
			User user,HttpServletRequest request) {
		ServiceResponse res = new ServiceResponse(true, "案件办理成功");
		String caseId = penaltyLianForm.getCaseId();
		Date currentDate = new Date();
		Integer createOrgId = user.getOrgId();
		String createUserId = user.getUserId();
		String procKey = Const.CASE_CHUFA_PROC_KEY;
		
		/*//根据caseId找到对应的taskId actionId,userId,assignTarget
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("caseId", caseId);
		CaseTodo paramCaseTodo = caseTodoMapper.getCaseTodoList(paramMap).get(0);
		String taskId = paramCaseTodo.getTaskId();
		Integer actionId = Integer.parseInt(paramCaseTodo.getActionId());
		//这个是已经完成的上一步动作takAction
		TaskAction doneTaskAction = taskActionMapper.selectByPrimaryKey(actionId);
		TaskActionExample actionExample = new TaskActionExample();
		actionExample.createCriteria().andTaskDefIdEqualTo(doneTaskAction.getTargetTaskDefId())
			.andProcDefIdEqualTo(doneTaskAction.getProcDefId())
			.andProcVarValueEqualTo(Const.CHUCHAHESHI_XINGZHENGLIAN);//与流程设置时的action保持一致，说明将要执行哪个动作
		//这一步将要执行的动作
		TaskAction targetTaskAction = taskActionMapper.selectByExample(actionExample).get(0);
		Integer targetActionId = targetTaskAction.getActionId();
		String assignTarget="";
		String inputerId = caseBasicMapper.selectByPrimaryKey(caseId).getInputer();//案件录入人
		Integer taskType = Const.TASK_TYPE_XINGZHENGLIAN;
		xingzhenglianTaskDeal(taskId, targetActionId, assignTarget, inputerId, user,taskType);*/
		try {
		//保存案件附件（立案决定书）
		MultipartFile lianFile = multipartRequest.getFile("lianFile1");
		Long lianFileId  = null;
		if(lianFile !=null && !lianFile.isEmpty()){
			lianFileId = addCaseAtta(caseId,procKey,lianFile);
			penaltyLianForm.setLianFile(lianFileId.toString());//立案决定书id
		}
		//保存强制执行材料附件
		MultipartFile measuresFile = multipartRequest.getFile("measuresFile1");
		Long measuresFileId  = null;
		if(measuresFile !=null && !measuresFile.isEmpty()){
			measuresFileId = addCaseAtta(caseId,procKey,measuresFile);
			penaltyLianForm.setMeasuresFile(measuresFileId.toString());//强制执行材料id
		}
		penaltyLianFormMapper.insert(penaltyLianForm);
		
		//TODO:将case_basic中case_state更新为1，待行政处罚
		CaseBasic caseBasic = new CaseBasic();
		caseBasic.setCaseId(caseId);
		caseBasic.setCaseState(Const.CHUFA_PROC_1);
		caseBasic.setAmountInvolved(penaltyLianForm.getAmountInvolved());
		caseBasic.setLatestPocessTime(currentDate);
		String industryType = user.getOrganise().getIndustryType();
		accuseRuleService.matchCaseRule(caseBasic,industryType,request);
		caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
		
		//在案件状态表中更新案件的行政立案状态为3，已立案
		CaseState cState = new CaseState();
		cState.setCaseId(caseId);
		cState.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
		caseStateMapper.updateByPrimaryKeySelective(cState);
		
        //case_step表添加新的记录
        CaseStep caseStep = new CaseStep();
        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
        //下一步应该是待处罚
        String stepName = getCaseState(Const.CHUFA_PROC_1);
        caseStep.setStepName(stepName);
        caseStep.setCaseId(caseId);
        caseStep.setCaseState(Const.CHUFA_PROC_1);
        caseStep.setStartDate(currentDate);
        caseStep.setEndDate(currentDate);//暂时这样写，需要修改
        caseStep.setAssignPerson(createUserId);//下一步谁办理
        caseStep.setTargetOrgId(createOrgId);//目标办理机构id，在这里也就是案件录入机构
        caseStep.setTaskType(Const.TASK_TYPE_XINGZHENGLIAN);
        caseStep.setTaskActionName(stepName);
        
        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
        caseStep.setProcDefId(procDeploy.getProcDefId());
        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
        caseStepMapper.insert(caseStep);
        //疑似涉嫌犯罪分析
        PenaltyLianForm lianForm = penaltyLianFormMapper.selectByPrimaryKey(caseId);
//        crimeAnalyze(caseId,industryType,lianForm.getCaseDetail());
        
        delCasePartyAndCaseCompany(caseId);
        insertCasePartyAndCaseCompany(caseBasic2, caseId);
		} catch (Exception e) {
			res.setingError("案件办理失败！");
			logger.error("行政立案办理失败:", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}		
		return res;
	}
	
	/**
	 * 行政机关不予立案
	 */
	@Override
    @Transactional
    public ServiceResponse saveXingzhengNotLianForm(
    		MultipartRequest multipartRequest, User user, CaseBasic caseBasic2,
    		XingzhengNotLianForm xingzhengNotLianForm) {
		ServiceResponse res = new ServiceResponse(true, "案件办理成功");
		String caseId = xingzhengNotLianForm.getCaseId();
		String procKey = Const.CASE_CHUFA_PROC_KEY;
		Date currentDate = new Date();
		String createUserId = user.getUserId();
		Integer createOrgId = user.getOrgId();
		try {
		//保存不予立案附件
		MultipartFile atachFile = multipartRequest.getFile("attach");
		Long attachId  = null;
		if(atachFile !=null && !atachFile.isEmpty()){
			attachId = addCaseAtta(caseId,procKey,atachFile);
			xingzhengNotLianForm.setAttachId(attachId.toString());//不予立案附件id
		}
		//保存不予立案表单
		xingzhengNotLianFormMapper.insert(xingzhengNotLianForm);
		//在caseBasic中更新案件状态
		CaseBasic paramCasebasic = new CaseBasic();
		paramCasebasic.setCaseId(caseId);
		paramCasebasic.setCaseState(Const.CHUFA_PROC_3);//行政机关不予立案
		paramCasebasic.setLatestPocessTime(currentDate);
		caseBasicMapper.updateByPrimaryKeySelective(paramCasebasic);
		//在caseTodo中将此案件待办删
		caseTodoMapper.deleteOldCaseByCaseId(caseId);
		//在caseState中更新行政立案状态
		CaseState caseState = new CaseState();
		caseState.setCaseId(caseId);
		caseState.setXingzhenglianState(Const.LIAN_STATE_NO);//行政立案状态，不予立案
		caseStateMapper.updateByPrimaryKeySelective(caseState);
		//在case_step中增加记录
		CaseStep caseStep = new CaseStep();
        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
        String stepName = getCaseState(Const.CHUFA_PROC_3);
        caseStep.setStepName(stepName);
        caseStep.setCaseId(caseId);
        caseStep.setCaseState(Const.CHUFA_PROC_3);
        caseStep.setStartDate(currentDate);
        caseStep.setEndDate(currentDate);//暂时这样写，需要修改
        caseStep.setAssignPerson(createUserId);//下一步谁办理
        caseStep.setTargetOrgId(createOrgId);//目标办理机构id，在这里也就是案件录入机构
        caseStep.setTaskType(Const.TASK_TYPE_BUYULIAN);//taskType不予立案
        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
        caseStep.setProcDefId(procDeploy.getProcDefId());
        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
        caseStep.setTaskActionName(stepName);
        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
        caseStepMapper.insert(caseStep);
		} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
		return res;
    }
	
	/**
	 * 行政立案，行政处罚，移送公安每次保存，先将caseparty和
	 * caseCompany删掉，再重新插入
	 * peopleLib和companyLib只做更新，不做删除
	 */
	private void delCasePartyAndCaseCompany(String caseId){
		//删除该案件关联的当事人
		CasePartyExample casePartyExample = new CasePartyExample();
		casePartyExample.createCriteria().andCaseIdEqualTo(caseId);
		casePartyMapper.deleteByExample(casePartyExample);
		//删除该案件关联的嫌疑人
		CaseXianyirenExample caseXianyirenExample = new CaseXianyirenExample();
		caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId);
		caseXianyirenMapper.deleteByExample(caseXianyirenExample);
		//删除该案件关联的被处罚单位
		CaseCompanyExample caseCompanyExample = new CaseCompanyExample();
		caseCompanyExample.createCriteria().andCaseIdEqualTo(caseId);
		caseCompanyMapper.deleteByExample(caseCompanyExample);
	}
	/**
	 * 添加案件当事人和被处罚单位
	 * @param caseBasic
	 * @param caseId
	 */
	private void insertCasePartyAndCaseCompany(CaseBasic caseBasic,String caseId ){
		String casePartyJson = caseBasic.getCasePartyJson();
        String caseCompanyJson = caseBasic.getCaseCompanyJson();
        if (StringUtils.isNotBlank(casePartyJson)) {
            // 保存 案件当事人信息并把当事人信息保存为嫌疑人
            List<CaseParty> casePartyList = JSON.parseArray(casePartyJson,
                    CaseParty.class);
            if (casePartyList != null && !casePartyList.isEmpty()) {
                for (CaseParty caseParty : casePartyList) {
                	if (caseParty==null) {//由于前台JS数组的原因，导致JSON字符串中含有null，这里的list成员中也有可能为null
						continue;
					}
                    caseParty.setCaseId(caseId);
                    caseParty.setPartyId(Long.valueOf((systemDAO
                            .getSeqNextVal(Const.TABLE_CASE_PARTY))));
                    casePartyMapper.insertSelective(caseParty);
                    caseXianyirenMapper.insertSelective(getCaseXianYiFanByCaseParty(caseParty));
                    // 保存到个人库
                    PeopleLib peopleLib = PeopleLib.createFromCaseParty(caseParty);
                    int count = peopleLibMapper.updateByPrimaryKeySelective(peopleLib);
                    if (count == 0) {
                        peopleLibMapper.insert(peopleLib);
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(caseCompanyJson)) {
            // 保存 案件企业信息
            List<CaseCompany> caseCompanyList = JSON.parseArray(
                    caseCompanyJson, CaseCompany.class);
            if (caseCompanyList != null && !caseCompanyList.isEmpty()) {
                for (CaseCompany caseCompany : caseCompanyList) {
                	if (caseCompany==null) {//由于前台JS数组的原因，导致JSON字符串中含有null，这里的list成员中也有可能为null
						continue;
					}
                    caseCompany.setCaseId(caseId);
                    caseCompany.setId(systemDAO.getSeqNextVal(Const.TABLE_CASE_COMPANY));
                    caseCompany.setRegistractionNum(StringUtils.trim(caseCompany.getRegistractionNum()));
                    caseCompanyMapper.insertSelective(caseCompany);
                    //保存到企业库
                    CompanyLib companyLib = CompanyLib.createFromCaseCompany(caseCompany);
                    companyLib.setInputer(caseBasic.getInputer());
                    companyLib.setInputTime(caseBasic.getInputTime());
                    int count = companyLibMapper.updateByPrimaryKeySelective(companyLib);
                    if (count == 0) {
                        companyLibMapper.insert(companyLib);
                    }
                }
            }
        }
	}
	private CaseXianyiren getCaseXianYiFanByCaseParty(CaseParty caseParty) {
        CaseXianyiren caseXianyiren = new CaseXianyiren();
        caseXianyiren.setXianyirenId(Long.valueOf(systemDAO
                .getSeqNextVal(Const.TABLE_CASE_XIANYIREN)));
        caseXianyiren.setDaibuState(Const.XIANYIREN_DAIBU_STATE_NOTYET);
        caseXianyiren
                .setTiqigongsuState(Const.XIANYIREN_TIQIGONGSU_STATE_NOTYET);
        caseXianyiren
                .setTiqingqisuState(Const.XIANYIREN_TIQINGQISU_STATE_NOTYET);
        caseXianyiren.setFayuanpanjueState(Const.XIANYIREN_PANJUE_STATE_NOTYET);
        caseXianyiren.setName(caseParty.getName());
        caseXianyiren.setAddress(caseParty.getAddress());
        caseXianyiren.setBirthplace(caseParty.getBirthplace());
        caseXianyiren.setCaseId(caseParty.getCaseId());
        caseXianyiren.setIdsNo(caseParty.getIdsNo());
        caseXianyiren.setNation(caseParty.getNation());
        caseXianyiren.setSex(caseParty.getSex());
        caseXianyiren.setTel(caseParty.getTel());
        caseXianyiren.setEducation(caseParty.getEducation());
        caseXianyiren.setProfession(caseParty.getProfession());
        caseXianyiren.setResidence(caseParty.getResidence());
        caseXianyiren.setSpecialIdentity(caseParty.getSpecialIdentity());
        return caseXianyiren;
    }
	/**
	 * 添加行政处罚案件
	 */
	@Override
	@Transactional
	public ServiceResponse addPenaltyCase(CaseBasic caseBasic2,PenaltyCaseForm penaltyCaseForm,MultipartRequest multipartRequest,
			User user,HttpServletRequest request) {
		ServiceResponse res = new ServiceResponse(true, "案件办理成功");
		String caseId = penaltyCaseForm.getCaseId();
		Date currentDate = new Date();
		Integer createOrgId = user.getOrgId();
		String createUserId = user.getUserId();
		String industryType = user.getOrganise().getIndustryType();
		String procKey = Const.CASE_CHUFA_PROC_KEY;
		try {
		/*//与流程结合，根据caseId找到对应的taskId actionId,userId,assignTarget
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("caseId", caseId);
		CaseTodo paramCaseTodo = caseTodoMapper.getCaseTodoList(paramMap).get(0);
		String taskId = paramCaseTodo.getTaskId();
		Integer actionId = Integer.parseInt(paramCaseTodo.getActionId());
		//这个是已经完成的上一步动作takAction
		TaskAction doneTaskAction = taskActionMapper.selectByPrimaryKey(actionId);
		TaskActionExample actionExample = new TaskActionExample();
		actionExample.createCriteria().andTaskDefIdEqualTo(doneTaskAction.getTargetTaskDefId())
			.andProcDefIdEqualTo(doneTaskAction.getProcDefId())
			.andProcVarValueEqualTo(Const.ANJIANSHENCHA_XINGZHENGCHUFA);//与流程设置时的action保持一致，说明将要执行哪个动作
		//这一步将要执行的动作
		TaskAction targetTaskAction = taskActionMapper.selectByExample(actionExample).get(0);
		Integer targetActionId = targetTaskAction.getActionId();
		String assignTarget="";
		String inputerId = caseBasicMapper.selectByPrimaryKey(caseId).getInputer();//案件录入人
		Integer taskType = Const.TASK_TYPE_XINGZHENGCHUFA;
		xingzhenglianTaskDeal(taskId, targetActionId, assignTarget, inputerId, user,taskType);*/
		
		//将获取的行政处罚类型插入到行政处罚类型表中
		if(penaltyCaseForm.getChufaTypeName() != null){
			String chufaTypes = penaltyCaseForm.getChufaTypeName();
			String[] xingzhengChufaType = chufaTypes.split(",");
			for (int i = 0; i < xingzhengChufaType.length; i++) {
				String chufaType = xingzhengChufaType[i].trim().toString();
				ChufaTypeForm chufaTypeForm = new ChufaTypeForm();
				chufaTypeForm.setChufaTypeId(systemDAO.getSeqNextVal(Const.TABLE_CHUFA_TYPE));
				chufaTypeForm.setChufaType(chufaType);
				chufaTypeForm.setCaseId(caseId);
				chufaTypeFormMapper.insert(chufaTypeForm);
			}
		}
		
		//保存打侵打假关联关系
        if(penaltyCaseForm.getDqdjType() != null){
         	DqdjCaseCategory caseCategory = new DqdjCaseCategory();
         	caseCategory.setCaseId(caseId);
         	caseCategory.setCategoryId(penaltyCaseForm.getDqdjType());
         	dqdjCaseCategoryMapper.insert(caseCategory);
        }
        
		//保存附件
		MultipartFile penaltyFile = multipartRequest.getFile("penaltyFile");
		Long penaltyFileId  = null;
		if(penaltyFile !=null && !penaltyFile.isEmpty()){
			penaltyFileId = addCaseAtta(caseId,procKey,penaltyFile);
			penaltyCaseForm.setPenaltyFileId(penaltyFileId.intValue());//立案决定书id
		}
		//在行政处罚表中保存记录
		penaltyCaseFormMapper.insert(penaltyCaseForm);
		
		//在case_basic中更新案件状态行政处罚
		CaseBasic caseBasic = new CaseBasic();
		caseBasic.setCaseId(caseId);
		caseBasic.setCaseState(Const.CHUFA_PROC_2);
		caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
		//case_state 处罚状态2已处罚
		CaseState caseState = new CaseState();
		caseState.setCaseId(caseId);
		caseState.setChufaState(Const.CHUFA_STATE_YES);
		caseStateMapper.updateByPrimaryKeySelective(caseState);
		
		//在case_step中增加一条记录
        CaseStep caseStep = new CaseStep();
        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
        //TODO:从字典表中查出再拼接
        String stepName = getCaseState(Const.CHUFA_PROC_2);
        caseStep.setStepName(stepName);
        caseStep.setCaseId(caseId);
        caseStep.setCaseState(Const.CHUFA_PROC_2);//已行政处罚
        caseStep.setStartDate(currentDate);
        caseStep.setEndDate(currentDate);//暂时这样写，需要修改
        caseStep.setAssignPerson(createUserId);//下一步谁办理
        caseStep.setTargetOrgId(createOrgId);//目标办理机构id，在这里也就是案件录入机构
        caseStep.setTaskType(Const.TASK_TYPE_XINGZHENGCHUFA);
        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
        caseStep.setProcDefId(procDeploy.getProcDefId());
        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
        caseStep.setTaskActionName(stepName);
        caseStepMapper.insert(caseStep);
        //疑似涉嫌犯罪分析
        PenaltyLianForm lianForm = penaltyLianFormMapper.selectByPrimaryKey(caseId);
//        crimeAnalyze(caseId,industryType,lianForm.getCaseDetail());
        accuseRuleService.matchCaseRule(caseBasic2,industryType,request);
        
        delCasePartyAndCaseCompany(caseId);
        insertCasePartyAndCaseCompany(caseBasic2, caseId);
		} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * 行政撤案
	 */
	@Override
	@Transactional
	public ServiceResponse xingzhengCancelLianForm(
			MultipartRequest multipartRequest, User user, CaseBasic caseBasic2,
			XingzhengCancelLianForm xingzhengCancelLianForm) {
		ServiceResponse res = new ServiceResponse(true, "案件办理成功");
		String caseId = xingzhengCancelLianForm.getCaseId();
		String procKey = Const.CASE_CHUFA_PROC_KEY;
		Date currentDate = new Date();
		String createUserId = user.getUserId();
		Integer createOrgId = user.getOrgId(); 
		try {
			
		
		/*//与流程结合，根据caseId找到对应的taskId actionId,userId,assignTarget
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("caseId", caseId);
		CaseTodo paramCaseTodo = caseTodoMapper.getCaseTodoList(paramMap).get(0);
		String taskId = paramCaseTodo.getTaskId();
		Integer actionId = Integer.parseInt(paramCaseTodo.getActionId());
		//这个是已经完成的上一步动作takAction
		TaskAction doneTaskAction = taskActionMapper.selectByPrimaryKey(actionId);
		TaskActionExample actionExample = new TaskActionExample();
		actionExample.createCriteria().andTaskDefIdEqualTo(doneTaskAction.getTargetTaskDefId())
			.andProcDefIdEqualTo(doneTaskAction.getProcDefId())
			.andProcVarValueEqualTo(Const.ANJIANSHENCHA_XINGZHENGCHEAN);//与流程设置时的action保持一致，说明将要执行哪个动作
		//这一步将要执行的动作
		TaskAction targetTaskAction = taskActionMapper.selectByExample(actionExample).get(0);
		Integer targetActionId = targetTaskAction.getActionId();
		String assignTarget="";
		String inputerId = caseBasicMapper.selectByPrimaryKey(caseId).getInputer();//案件录入人
		Integer taskType = Const.TASK_TYPE_XINGZHENGCHEAN;
		xingzhenglianTaskDeal(taskId, targetActionId, assignTarget, inputerId, user,taskType);*/
		
		//保存附件
		MultipartFile attachFile = multipartRequest.getFile("attach");
		Long attachFileId  = null;
		if(attachFile !=null && !attachFile.isEmpty()){
			attachFileId = addCaseAtta(caseId,procKey,attachFile);
			xingzhengCancelLianForm.setAttachId(attachFileId.toString());//行政撤案附件id
		}
		//在行政撤案表中保存记录
		xingzhengCancelLianFormMapper.insert(xingzhengCancelLianForm);
		//在case_basic中更新案件状态
		CaseBasic paramCaseBasic = new CaseBasic();
		paramCaseBasic.setCaseId(caseId);
		paramCaseBasic.setCaseState(Const.CHUFA_PROC_4);//行政撤案
		paramCaseBasic.setLatestPocessTime(currentDate);
		caseBasicMapper.updateByPrimaryKeySelective(paramCaseBasic);
		//在case_todo中将此案件待办删掉
		CaseTodoExample caseTodoExample = new CaseTodoExample();
		caseTodoExample.createCriteria().andCaseIdEqualTo(caseId);
		caseTodoMapper.deleteByExample(caseTodoExample);
		//在case_step中增加记录
		CaseStep caseStep = new CaseStep();
        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
        //TODO:从字典表中查出再拼接
        String stepName = getCaseState(Const.CHUFA_PROC_4);//行政撤案
        caseStep.setStepName(stepName);
        caseStep.setCaseId(caseId);
        caseStep.setCaseState(Const.CHUFA_PROC_4);//行政撤案
        caseStep.setStartDate(currentDate);
        caseStep.setEndDate(currentDate);//暂时这样写，需要修改
        caseStep.setAssignPerson(createUserId);//下一步谁办理
        caseStep.setTargetOrgId(createOrgId);//目标办理机构id，在这里也就是案件录入机构
        caseStep.setTaskType(Const.TASK_TYPE_XINGZHENGCHEAN);//taskType类型行政撤案
        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
        caseStep.setProcDefId(procDeploy.getProcDefId());
        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
        caseStepMapper.insert(caseStep);
		} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * 分析是否涉嫌犯罪，比较行政处罚表单中案件详情和该行业得违法情形描述
	 *
	 * @param caseId
	 * @param penaltyContent
	 */
	private void crimeAnalyze(String caseId,String industryType,String penaltyContent) {
		//获取案件录入机构所属行业的常用罪名
		//查询当前单位得违法情形
		CaseIllegalSituation caseIllegalSituation=new CaseIllegalSituation();
    	caseIllegalSituation.setCaseId(caseId);
		List<IllegalSituation> situationList = illegalSituationMapper.selectByIndustryType(industryType);
		PenaltyLianForm lianForm = penaltyLianFormMapper.selectByPrimaryKey(caseId);
		if(!situationList.isEmpty()){
			for(IllegalSituation is:situationList){
				boolean similarity = CosineSimilarityUtil.CompareText(penaltyContent, is.getName());
				if(similarity){
					CaseBasic caseBasic = new CaseBasic();
					caseBasic.setCaseId(caseId);
					caseBasic.setIsSuspectedCriminal(Const.SUSPECTED_CRIMINAL_YES);
					caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
				}
				caseIllegalSituation.setSituationId(is.getId());
				//判断违法情形是否已经存在数据库中，如果已存在，不需要重复添加
				CaseIllegalSituationExample cis=new CaseIllegalSituationExample();
				cis.createCriteria().andCaseIdEqualTo(caseId).andSituationIdEqualTo(is.getId());
				List<CaseIllegalSituation> lists=caseIllegalSituationMapper.selectByExample(cis);
				if(lists==null || lists.size()==0){
					caseIllegalSituationMapper.insert(caseIllegalSituation);
				}
			}
		}
/*		List<AccuseInfo> accuseInfoList = accuseInfoMapper.selectByByCaseInputUnit(caseId);
		if(!accuseInfoList.isEmpty()){
			for(AccuseInfo info:accuseInfoList){
				boolean similarity = CosineSimilarityUtil.CompareText(penaltyContent, info.getLaw());
				if(similarity){
					CaseBasic caseBasic = new CaseBasic();
					caseBasic.setCaseId(caseId);
					caseBasic.setIsSuspectedCriminal(Const.SUSPECTED_CRIMINAL_YES);
					caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
				}
				CaseSuspectedAccuse caseSuspectedAccuse = new CaseSuspectedAccuse();
				caseSuspectedAccuse.setId(systemDAO.getSeqNextVal(Const.TABLE_CASE_SUSPECT_ACCUSE)+"");
				caseSuspectedAccuse.setCaseId(caseId);
				caseSuspectedAccuse.setAccuseId(info.getId());
				caseSuspectedAccuseMapper.insertSelective(caseSuspectedAccuse);
			}
		}*/
	}

	//保存案件和违法情形关系
	private void saveCaseIllegalSituation(PenaltyCaseForm penaltyCaseForm,String caseId){
		//保存案件和违法情形关联关系
        if(StringUtils.isNotBlank(penaltyCaseForm.getIllegalSituationId())){
        	String str=penaltyCaseForm.getIllegalSituationId();
        	//去掉最后一个,
        	str=str.substring(0, str.length()-1);
        	CaseIllegalSituation caseIllegalSituation=new CaseIllegalSituation();
        	caseIllegalSituation.setCaseId(caseId);
        	//当一个案件有多个违法情形时，循环保存，否则直接保存
        	if(str.contains(",")){
        		String [] s=str.split(",");
				for(int i=0;i<s.length;i++){
					String temp=s[i];
					caseIllegalSituation.setSituationId(temp);
					caseIllegalSituationMapper.insert(caseIllegalSituation);
				}
        	}else{
        		caseIllegalSituation.setSituationId(str);
				caseIllegalSituationMapper.insert(caseIllegalSituation);
        	}
        }
	}
	/**
	 * 保存案件附件
	 * 移送公安表中增加记录
	 * 移送公安 case_basic中case_state为10已移送，等待公安受理
	 * case_state中yisong_state改为2 直接移送
	 * case_todo中记录删掉
	 * case_step中step_name=""
	 */
	@Override
	@Transactional
	public ServiceResponse addCrimeCase(MultipartRequest multipartRequest,
			User user,CaseBasic caseBasic, CrimeCaseForm crimeCaseForm,Map<String, Object> map) {
		ServiceResponse res = new ServiceResponse(true, "案件办理成功");
		String caseId = crimeCaseForm.getCaseId();
		Date currentDate = new Date();
		Integer createOrgId = user.getOrgId();
		String createUserId = user.getUserId();
		String procKey = Const.CASE_CHUFA_PROC_KEY;
		/*
		//与流程结合，根据caseId找到对应的taskId actionId,userId,assignTarget
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("caseId", caseId);
		CaseTodo paramCaseTodo = caseTodoMapper.getCaseTodoList(paramMap).get(0);
		String taskId = paramCaseTodo.getTaskId();
		Integer actionId = Integer.parseInt(paramCaseTodo.getActionId());
		//这个是已经完成的上一步动作takAction
		TaskAction doneTaskAction = taskActionMapper.selectByPrimaryKey(actionId);
		TaskActionExample actionExample = new TaskActionExample();
		Criteria criteria = actionExample.createCriteria()
				.andTaskDefIdEqualTo(doneTaskAction.getTargetTaskDefId())
				.andProcDefIdEqualTo(doneTaskAction.getProcDefId());
		if(markup.equals("lian")){
			//初查核实步骤移送司法,与流程设置时的action保持一致，说明将要执行哪个动作
			criteria.andProcVarValueEqualTo(Const.CHUCHAHESHI_YISONGSIFA);
		}else if(markup.equals("chufa")){
			//案件审查步骤移送司法
			criteria.andProcVarValueEqualTo(Const.ANJIANSHENCHA_YISONGSIFA);
		}else if(markup.equals("chufadone")){
			//已作出处罚步骤移送司法
			criteria.andProcVarValueEqualTo(Const.ZUOCHUCHUFA_YISONGSIFA);
		}
		//这一步将要执行的动作
		TaskAction targetTaskAction = taskActionMapper.selectByExample(actionExample).get(0);
		Integer targetActionId = targetTaskAction.getActionId();
		//这一步设置公安局受案单位
		String assignTarget=crimeCaseForm.getAcceptOrg().toString();
		String inputerId = caseBasicMapper.selectByPrimaryKey(caseId).getInputer();//案件录入人
		Integer taskType = Const.TASK_TYPE_YISONGGONGAN;
		xingzhenglianTaskDeal(taskId, targetActionId, assignTarget, inputerId, user,taskType);*/
		
		delCasePartyAndCaseCompany(caseId);
        insertCasePartyAndCaseCompany(caseBasic, caseId);
        
		//保存案件附件,同时获取附件id
		crimeCaseForm = crimeAttaSave(multipartRequest, crimeCaseForm, caseId, procKey);
		
		//在移送公安表中增加记录
		crimeCaseFormMapper.insert(crimeCaseForm);
		
		//case_basic中将案件状态改为10已移送，等待公安受理
		Integer caseType = getCaseTypeOfCaseBasic(caseId);
		CaseBasicExample cBasicExample = new CaseBasicExample();
		CaseBasic cBasic = new CaseBasic();
		cBasic.setCaseType(caseType);
		cBasic.setCaseState(Const.CHUFA_PROC_10);
		cBasicExample.createCriteria().andCaseIdEqualTo(caseId);
		cBasic.setLatestPocessTime(currentDate);
		caseBasicMapper.updateByExampleSelective(cBasic, cBasicExample);
		
		//建议移送未移送（4）的案件移送后状态改为建议移送（3）
		//未移送（1）的案件移送后状态为直接移送（2）
		Integer oldYisongState = caseStateMapper.selectByPrimaryKey(caseId).getYisongState();
		CaseState caseState = new CaseState();
		if(oldYisongState == Const.YISONG_STATE_NO){
			caseState.setYisongState(Const.YISONG_STATE_ZHIJIE);
		}else if(oldYisongState == Const.YISONG_STATE_JIANYI_NOT){
			caseState.setYisongState(Const.YISONG_STATE_JIANYI);
		}
		CaseStateExample caseStateExample = new CaseStateExample();
		caseStateExample.createCriteria().andCaseIdEqualTo(caseId);
		caseStateMapper.updateByExampleSelective(caseState, caseStateExample);
		
		//在case_todo中将该case_id的待办记录删掉
		CaseTodoExample caseTodoExample = new CaseTodoExample();
		caseTodoExample.createCriteria().andCaseIdEqualTo(caseId);
		caseTodoMapper.deleteByExample(caseTodoExample);
		
		//添加罪名
		addCaseAccuse(caseId,crimeCaseForm.getCaseAccuse(),Const.CASE_ZM_TYPE_yisonggongan);
		
		//TODO:启动工作流
		CaseBasic caseBasic2 = caseBasicMapper.selectByPrimaryKey(caseId);
		String inputer = user.getUserId();//获取案件录入人
		map.put(ActivitiUtil.VAR_ORG_CODE_GA, map.get(ActivitiUtil.VAR_ORG_CODE));//待办机构id
		map.put(Const.VAR_TASK_TYPE, Const.TASK_TYPE_YISONGGONGAN);
        CaseProcessUtil createProcessUtil = ProcessFactory.createProcessUtil(caseBasic2);
        
        createProcessUtil.saveBusinessEntityAndstartProcessInstanceByProcDefKey(procKey, inputer, map,Const.CASE_TYPE_YISONG);//此时map中存的是受案单位        
        
        //通知检察院
        notifyOrganise(caseId, Const.ORG_TYPE_JIANCHAYUAN);
		return res;
	}
	
	/**
     * 保存案件符件
     *
     * @param caseId
     * @param procKey
     * @param file
     * @return
     * @author XT
     */
    private Long addCaseAtta(String caseId, String procKey,
                            MultipartFile file) {
    	Long caseAttSequenceId = Long.valueOf(getSequenceId(Const.TABLE_CASE_ATTA));
        CaseAttachment atta = new CaseAttachment();
        atta.setId(Long.valueOf(caseAttSequenceId));
        atta.setProcKey(procKey);
        atta.setCaseId(caseId);
        UpLoadContext upLoad = new UpLoadContext(
                new UploadResource());
        String url = upLoad.uploadFile(file, null);
        String fileName = file.getOriginalFilename();
        atta.setAttachmentName(fileName);
        atta.setAttachmentPath(url);
        //保存案件符件
        caseAttachmentMapper.insert(atta);
        HashMap<String,String> msgMap = new HashMap<String, String>();
        msgMap.put("type", "caseAttachment");
        msgMap.put("id", caseAttSequenceId.toString());
        msgMap.put("filePath", url);
        messageProducer.sendMessage(msgMap);
        return caseAttSequenceId;
    }
    
    /**
     * 添加案件罪名
     * @param caseId
     * @param caseAccuseArr
     * @param caseAccuseType
     */
    private void addCaseAccuse(String caseId, String caseAccuseArrString,
            int caseAccuseType) {
    	String[] caseAccuseArr=caseAccuseArrString.split(",");
		CaseAccuseKey caseAccuse = new CaseAccuseKey();
		caseAccuse.setCaseId(caseId);
		caseAccuse.setZmType(caseAccuseType);
		for (int i = 0; i < caseAccuseArr.length; i++) {
		String accuseId = caseAccuseArr[i];
		caseAccuse.setAccuseId(Integer.valueOf(accuseId));
		caseAccuseMapper.deleteByPrimaryKey(caseAccuse);
		caseAccuseMapper.insert(caseAccuse);
		}
	}
    //生成id
    public int getSequenceId(String tableName) {
        return systemDAO.getSeqNextVal(tableName);
    }
    
    //查询字典表步骤名称
    private String getCaseState(String dtCode){
    	String caseState = "";
    	DictionaryExample dicExample = new DictionaryExample();
    	dicExample.createCriteria().andDtCodeEqualTo(dtCode)
    	.andGroupCodeEqualTo("chufaProcState");
    	List<Dictionary> dicList = dictionaryMapper.selectByExample(dicExample);
    	if(dicList.size()>0){
    		caseState = dicList.get(0).getDtName();
    	}
    	return caseState;
    }
    
    //移送公安附件保存
    private CrimeCaseForm crimeAttaSave(MultipartRequest multipartRequest,
    		CrimeCaseForm crimeCaseForm,String caseId,String procKey){
    	MultipartFile yisongFile = multipartRequest.getFile("yisongFile1");
		Long yisongFileId  = null;
		if(yisongFile !=null && !yisongFile.isEmpty()){
			yisongFileId = addCaseAtta(caseId,procKey,yisongFile);
			crimeCaseForm.setYisongFile(yisongFileId.intValue());//涉嫌犯罪案件移送书
		}
		MultipartFile surveyFile = multipartRequest.getFile("surveyFile1");
		Long surveyFileId  = null;
		if(surveyFile !=null && !surveyFile.isEmpty()){
			surveyFileId = addCaseAtta(caseId,procKey,surveyFile);
			crimeCaseForm.setSurveyFile(surveyFileId.intValue());//涉嫌犯罪案件调查报告
		}
		MultipartFile goodsListFile = multipartRequest.getFile("goodsListFile1");
		Long goodsListFileId  = null;
		if(goodsListFile !=null && !goodsListFile.isEmpty()){
			goodsListFileId = addCaseAtta(caseId,procKey,goodsListFile);
			crimeCaseForm.setGoodsListFile(goodsListFileId.intValue());//涉案物品清单
		}
		MultipartFile identifyFile = multipartRequest.getFile("identifyFile1");
		Long identifyFileId  = null;
		if(identifyFile !=null && !identifyFile.isEmpty()){
			identifyFileId = addCaseAtta(caseId,procKey,identifyFile);
			crimeCaseForm.setIdentifyFile(identifyFileId.intValue());//检查报告或鉴定意见
		}
		MultipartFile otherFile = multipartRequest.getFile("otherFile1");
		Long otherFileId  = null;
		if(otherFile !=null && !otherFile.isEmpty()){
			otherFileId = addCaseAtta(caseId,procKey,otherFile);
			crimeCaseForm.setOtherFile(otherFileId.intValue());//其他涉嫌犯罪材料
		}
    	return crimeCaseForm ;
    }
    
    //移送公安时，根据CASE_STATE中案件处罚状态，确定CASE_BASIC的case_type
    private Integer getCaseTypeOfCaseBasic(String caseId){
    	Integer caseType = null;
    	CaseStateExample caseStateExample = new CaseStateExample();
    	caseStateExample.createCriteria().andCaseIdEqualTo(caseId);
    	List<CaseState> caseStateList = caseStateMapper.selectByExample(caseStateExample);
    	if(caseStateList.size()>0){
    		Integer chufaState = caseStateList.get(0).getChufaState();
    		if(chufaState==Const.CHUFA_STATE_NOTYET){
    			//3未做处罚直接移送公安
    			caseType=Const.CASE_TYPE_YISONG;
    		}else if(chufaState==Const.CHUFA_STATE_YES){
    			//2已作出处罚主动移送
    			caseType=Const.CASE_TYPE_CHUFA_YISONG;
    		}
    	}
		return caseType;
    }
    
    //移送公安时，通知检察院
    private void notifyOrganise(String caseId,String orgType){
    	//获取当前登录机构对应检察院的orgCode
    	Integer orgCode = null;
    	String districtCode = SystemContext.getSystemInfo().getDistrict();
    	Organise org = new Organise();
    	OrganiseExample oExample = new OrganiseExample();
    	oExample.createCriteria().andDistrictCodeEqualTo(districtCode)
    		.andIsDeptEqualTo(Const.IS_DEPT)
    		.andOrgTypeEqualTo(orgType);
    	List<Organise> orgList = organiseMapper.selectByExample(oExample);
    	if(orgList.size()>0){
    		orgCode = orgList.get(0).getOrgCode();
    	}
    	//在case_yisong_notify中插入记录
    	CaseYisongNotice notice = new CaseYisongNotice();
    	Integer noticeId = getSequenceId(Const.TABLE_CASE_YISONG_NOTICE);
    	notice.setId(noticeId);
    	notice.setNotifyId(orgCode.toString());
    	notice.setCaseId(caseId);
    	notice.setInputTime(new Date());
    	caseYisongNoticeMapper.insert(notice);
    }
    /**
     * 检察院建议移送表单提交
	 * 附件表保存
	 * case_basic中case_state改为9 建议移送公安
	 * case
	 * suggest_yisong_form添加一条记录
	 * case_step添加提条记录 case-state改为9
	 */
    @Override
    @Transactional
    public ServiceResponse saveSuggestYisongForm(MultipartRequest multipartRequest,
			SuggestYisongForm suggestYisongForm,User user){
    	ServiceResponse res = new ServiceResponse(true, "案件办理成功");
		String caseId = suggestYisongForm.getCaseId();
		Date currentDate = new Date();
		Integer createOrgId = user.getOrgId();
		String createUserId = user.getUserId();
		String procKey = Const.CASE_CHUFA_PROC_KEY;
		
		try {
		//保存附件
		MultipartFile suggestFile = multipartRequest.getFile("suggestFile");
		Long suggestFileId  = null;
		if(suggestFile !=null && !suggestFile.isEmpty()){
			suggestFileId = addCaseAtta(caseId,procKey,suggestFile);
			suggestYisongForm.setSuggestFileId(suggestFileId.toString());//检查报告或鉴定意见
		}
		
		//suggest_yisong_form添加一条记录
		suggestYisongFormMapper.insert(suggestYisongForm);
		
		//case_basic中case_state改为9 建议移送公安
		CaseBasic caseBasic = new CaseBasic();
		caseBasic.setCaseId(caseId);
		caseBasic.setCaseState(Const.CHUFA_PROC_9);
		caseBasic.setLatestPocessTime(currentDate);
		caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
		
		//case_state中yisong_state改为4 建议移送未移送
		CaseState caseState = new CaseState();
		caseState.setCaseId(caseId);
		caseState.setYisongState(Const.YISONG_STATE_JIANYI_NOT);
		caseStateMapper.updateByPrimaryKeySelective(caseState);
		
		//case_step添加新记录 case_state为9
		CaseStep caseStep = new CaseStep();
        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
        //TODO:从字典表中查出再拼接
        String stepName = getCaseState(Const.CHUFA_PROC_9);
        caseStep.setStepName(stepName);
        caseStep.setCaseId(caseId);
        caseStep.setCaseState(Const.CHUFA_PROC_9);//建议移送公安
        caseStep.setStartDate(currentDate);
        caseStep.setEndDate(currentDate);//暂时这样写，需要修改
        caseStep.setAssignPerson(createUserId);//
        caseStep.setTargetOrgId(createOrgId);//目标办理机构id，在这里也就是案件录入机构
        caseStep.setTaskType(Const.TASK_TYPE_JIANYIYISONG);
        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
        caseStep.setProcDefId(procDeploy.getProcDefId());
        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
        caseStepMapper.insert(caseStep);
        
        caseTodoMapper.deleteOldCaseByCaseId(caseId);
        
        CaseBasic caseBasic2 = caseBasicMapper.selectByPrimaryKey(caseId);
        CaseTodo caseTodo = new CaseTodo();
        caseTodo.setTodoId(systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO));
        caseTodo.setCaseId(caseId);
        caseTodo.setCreateUser(createUserId);
        caseTodo.setCreateTime(currentDate);
        caseTodo.setCreateOrg(createOrgId);
        caseTodo.setAssignOrg(Integer.valueOf(caseBasic2.getInputUnit()));
        caseTodoMapper.insert(caseTodo);
        //添加APP提醒消息
        instantMessageService.addCaseMessage(caseId,caseBasic2.getCaseNo(),createUserId,caseBasic2.getInputer(),null);
		} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
    	return res;
    }
    
    //行政结案表单提交
    @Override
    @Transactional
    public ServiceResponse saveXingzhengJieanForm(
    		MultipartRequest multipartRequest,
    		XingzhengJieanForm xingzhengJieanForm, User user) {
    	ServiceResponse res = new ServiceResponse(true, "案件办理成功");
		String caseId = xingzhengJieanForm.getCaseId();
		Date currentDate = new Date();
		Integer createOrgId = user.getOrgId();
		String createUserId = user.getUserId();
		String procKey = Const.CASE_CHUFA_PROC_KEY;
    	/**
    	 * 上传附件
    	 * 将case_todo中记录删除一条
    	 * 在xingzheng_jiean_form添加一条记录
    	 * 更新case_basic中案件的状态为3行政结案
    	 */
		try {
		//上传附件
		MultipartFile jieanAttaFile = multipartRequest.getFile("jieanAttaFile");
		Long jieanAttaFileId  = null;
		if(jieanAttaFile !=null && !jieanAttaFile.isEmpty()){
			jieanAttaFileId = addCaseAtta(caseId,procKey,jieanAttaFile);
			xingzhengJieanForm.setJieanAttachId(jieanAttaFileId.intValue());//检查报告或鉴定意见
		}
    	//case_todo中将这条记录删掉
		caseTodoMapper.deleteOldCaseByCaseId(caseId);
		
		//在xingzheng_jiean_form添加一条记录
		xingzhengJieanFormMapper.insert(xingzhengJieanForm);
		
		//更新case_basic中案件的状态为29，已作出行政处罚，已结案
		CaseBasic caseBasic = new CaseBasic();
		caseBasic.setCaseId(caseId);
		caseBasic.setCaseState(Const.CHUFA_PROC_29);
		caseBasic.setLatestPocessTime(currentDate);
		caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
		
		//更新case_state中结案状态
		CaseState caseState = new CaseState();
		caseState.setCaseId(caseId);
		caseState.setJieanState(Const.JIEAN_STATE_YES);
		caseStateMapper.updateByPrimaryKeySelective(caseState);
		
		//case_step添加提条记录
		CaseStep caseStep = new CaseStep();
        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
        //TODO:从字典表中查出再拼接
        String stepName = getCaseState(Const.CHUFA_PROC_29);
        caseStep.setStepName(stepName);
        caseStep.setCaseId(caseId);
        caseStep.setCaseState(Const.CHUFA_PROC_29);//建议移送公安
        caseStep.setStartDate(currentDate);
        caseStep.setEndDate(currentDate);//暂时这样写，需要修改
        caseStep.setAssignPerson(caseBasicMapper.selectByPrimaryKey(caseId).getInputer());
        caseStep.setTargetOrgId(createOrgId);//目标办理机构id，在这里也就是案件录入机构
        caseStep.setTaskType(Const.TASK_TYPE_XINGZHENGJIEAN);
        
        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
        //caseStep.setTaskInstId(str);
        //caseStep.setTaskDefId(str);
        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
        caseStep.setProcDefId(procDeploy.getProcDefId());
        //caseStep.setProcInstId(str);
        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
        //caseStep.setTaskActionId(1);
        caseStep.setTaskActionName(stepName);
        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
        caseStepMapper.insert(caseStep);
		} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
    	return res;
    }
    
    /**
     * 行政案件交办
     */
    @Override
    @Transactional
    public void taskFenpai(HttpServletRequest request,String orgCode,String caseId){
    	//行政处理阶段，修改案件的状态
    	Date currentDate = new Date();
    	User currentUser = SystemContext.getCurrentUser(request);
    	
    	//更新case_basic状态，是否交办，交办人,案件状态为已分派，等待受理（28
    	CaseBasic caseBasic = new CaseBasic();
    	//分派后下单位的org_code写入assignOrg字段
    	//caseBasic.setAssignOrg(orgCode);
    	caseBasic.setIsAssign(Const.IS_ASSIGN_YES);
    	caseBasic.setCaseId(caseId);
    	caseBasic.setCaseState(Const.CHUFA_PROC_28);
    	caseBasic.setLatestPocessTime(currentDate);
    	caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
    	
    	//在案件分派表中插入记录
        CaseFenpai caseFenpai = new CaseFenpai();
        caseFenpai.setCaseId(caseId);
        caseFenpai.setFenpaiOrg(currentUser.getOrgId());
        caseFenpai.setJieshouOrg(Integer.parseInt(orgCode));
        caseFenpai.setFenpaiTime(currentDate);
        caseFenpaiMapper.insert(caseFenpai);
        
    	//更新case_todo表
    	caseTodoMapper.deleteOldCaseByCaseId(caseId);
    	
    	CaseTodo caseTodo = new CaseTodo();
    	caseTodo.setTodoId(systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO));
    	caseTodo.setCaseId(caseId);
    	//创建人是当前登录用户
    	caseTodo.setCreateUser(currentUser.getUserId());
        caseTodo.setCreateTime(currentDate);
        caseTodo.setCreateOrg(currentUser.getOrgId());
        //caseTodo.setAssignUser(userId);//办理人id
        caseTodo.setAssignOrg(Integer.parseInt(orgCode));
        caseTodoMapper.insert(caseTodo);
        
        CaseBasic newCaseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
        //添加案件步骤
        CaseStep caseStep = new CaseStep();
        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
        caseStep.setStepName(getCaseState(Const.CHUFA_PROC_28));
        caseStep.setCaseId(caseId);
        caseStep.setCaseState(newCaseBasic.getCaseState());
        caseStep.setStartDate(currentDate);
        caseStep.setEndDate(currentDate);
        caseStep.setAssignPerson(currentUser.getUserId());//这一步骤的办理人
        //目标办理机构id，这里是分派后的下级单位orgCode
        caseStep.setTargetOrgId(Integer.parseInt(orgCode));
        caseStep.setTaskActionName(getCaseState(Const.CHUFA_PROC_28));
        
        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
        caseStep.setProcDefId(procDeploy.getProcDefId());
        caseStep.setTaskType(Const.TASK_TYPE_FENPAI);
        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
        caseStepMapper.insert(caseStep);
    };
    
    /**
     * 移送管辖
     * 将原待办记录删掉，添加一条新的
     * 在case_turnover中添加新纪录
     * user:当前登录用户
     * orgCode：接收单位orgCode
     */
    @Override
    public ServiceResponse saveTurnOver(
			MultipartRequest multipartRequest, User user, CaseBasic caseBasic,
			CaseTurnover caseTurnover) {
    	ServiceResponse res = new ServiceResponse(true,"案件办理成功");
    	String procKey = Const.CASE_CHUFA_PROC_KEY;
    	String caseId = caseTurnover.getCaseId();
    	String createUser = user.getUserId();
    	Date currentDate = new Date();
    	Integer createOrg = user.getOrgId();
    	
    	try {
			// 保存移送管辖附件
			MultipartFile atachFile = multipartRequest.getFile("attach");
			Long fileId = null;
			if (atachFile != null && !atachFile.isEmpty()) {
				fileId = addCaseAtta(caseId, procKey, atachFile);
				caseTurnover.setFileId(fileId.toString());// 移送管辖附件id
			}
    	
    	//移送管辖，删除原有的待办，重新添加
    	caseTodoMapper.deleteOldCaseByCaseId(caseId);
    	
    	CaseTodo caseTodo = new CaseTodo();
    	caseTodo.setTodoId(systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO));
    	caseTodo.setCaseId(caseId);
    	caseTodo.setCreateUser(createUser);
    	caseTodo.setCreateTime(currentDate);
    	caseTodo.setCreateOrg(createOrg);
    	caseTodo.setAssignOrg(caseTurnover.getJieshouOrg());
    	caseTodoMapper.insert(caseTodo);
    	
    	caseTurnover.setCaseId(caseId);
    	caseTurnover.setYisongOrg(createOrg);//案件原属单位
    	caseTurnover.setYisongTime(currentDate);//移送时间
    	caseTurnoverMapper.insert(caseTurnover);
    	
    	//将案件的状态更新为27 已移送管辖，待受理
    	CaseBasic basic = new CaseBasic();
    	basic.setCaseId(caseId);
    	basic.setIsTurnover(Const.IS_TURNOVER_YES);
    	basic.setCaseState(Const.CHUFA_PROC_27);
    	basic.setLatestPocessTime(currentDate);
    	caseBasicMapper.updateByPrimaryKeySelective(basic);
    	
    	//在case_step表中增加移送管辖步骤
    	CaseStep caseStep = new CaseStep();
        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
        String stepName = getCaseState(Const.CHUFA_PROC_27);
        caseStep.setStepName(stepName);
        caseStep.setCaseId(caseId);
        caseStep.setCaseState(Const.CHUFA_PROC_27);
        caseStep.setStartDate(currentDate);
        caseStep.setEndDate(currentDate);//暂时这样写，需要修改
        caseStep.setAssignPerson(user.getUserId());//这一步骤的办理人,即登录人
        caseStep.setTargetOrgId(caseTurnover.getJieshouOrg());//目标办理机构id
        caseStep.setTaskType(Const.TASK_TYPE_YISONGGUANXIA);
        caseStep.setTaskActionName(stepName);
        
        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
        caseStep.setProcDefId(procDeploy.getProcDefId());
        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
        caseStepMapper.insert(caseStep);
    	} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
		return res;
    }
    
    /**
     * 行政立案
     * @param taskId       任务id
     * @param actionId     任务提交动作id
     * @param assignTarget 任务提交目标（下一步任务分配目标）
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private ServiceResponse xingzhenglianTaskDeal(String taskId,
                           Integer actionId,String assignTarget,String inputerId,
                           User user,Integer taskType) {
    	//将不同方法的taskType放入流程变量中，在保存caseStep的时候赋值上去，为了详情展示需要
    	taskService.setVariable(taskId, Const.VAR_TASK_TYPE, taskType);
        Organise organise = user.getOrganise();
        //查询下一步办理单位,只查询同一个区划下的单位
        if(StringUtils.isBlank(assignTarget)){
            TaskBindKey targetTaskBindKey = new TaskBindKey();
            TaskAction taskAction = taskActionMapper.selectByPrimaryKey(actionId);
            targetTaskBindKey.setProcDefId(taskAction.getProcDefId());
            if(StringUtils.isNotBlank(taskAction.getTargetTaskDefId())){//下一步任务节点为空：流程结束、并行分支
            	targetTaskBindKey.setTaskDefId(taskAction.getTargetTaskDefId());
                TaskBind targetTaskBind = taskBindMapper.selectByPrimaryKey(targetTaskBindKey);
                String targetOrgType = targetTaskBind.getAssignTarget();
                //特殊节点的处理(检察移送到行政)
                if(targetOrgType.equals(Const.ORG_TYPE_XINGZHENG) 
                		|| targetOrgType.equals(Const.TASK_ASSGIN_EQUALS_INPUTER) 
                		|| targetOrgType.equals(Const.TASK_ASSGIN_IS_INPUTER)){
                	User inputUser = userService.find(inputerId);
                	//获取的是部门的id，如两法办的id
                	assignTarget = inputUser.getDeptId().toString();
                	//改为获取组织机构的id
                	/*assignTarget = inputUser.getOrgId().toString();*/
                //TODO 公安-->公安
                }else if(Const.ORG_TYPE_GOGNAN.equals(organise.getOrgType()) && targetOrgType.equals(organise.getOrgType())){
                	assignTarget = user.getDeptId().toString();
                //如果一个区划下存在多个公安，设置办理人为上一次公安办理案件时的公安部门
                }else if(Const.ORG_TYPE_GOGNAN.equals(targetOrgType)){
                	Object obj = taskService.getVariable(taskId, ActivitiUtil.VAR_ORG_CODE_GA);
                	if(obj != null){
                		assignTarget = obj.toString();
                	}
                }else{
                	//TODO 查询同级别区划下已设置的部门
                	List<Organise> targetOrgList= organiseMapper.findDistrictHasTaskAssignSettingOrg(taskAction.getProcDefId(), taskAction.getTargetTaskDefId(),organise.getDistrictCode());
                	if(targetOrgList.size() > 0){
                		assignTarget = targetOrgList.get(0).getOrgCode().toString();	
                	}
                }
            }
        }else{
        	taskService.setVariable(taskId, ActivitiUtil.VAR_ORG_CODE_GA, assignTarget);
        }
        ServiceResponse response = workflowService.xingzhenglianTaskDeal(user.getUserId(), taskId, actionId, assignTarget);
        if (!response.getResult()) {
            throw new BusinessException(response.getMsg());
        }
        return response;
    }
	@Override
	public CaseTodo getTodoCountForXingzheng(Integer orgCode) {
		return caseTodoMapper.getTodoCountForXingzheng(orgCode);
	}

	/**
	 * 行政不予处罚表单提交
	 */
	@Override
	public ServiceResponse xingzhengNotPenalty(
			MultipartRequest multipartRequest, User user, CaseBasic caseBasic,
			XingzhengNotPenalty xingzhengNotPenalty) {
		ServiceResponse res = new ServiceResponse(true, "案件办理成功");
		String procKey = Const.CASE_CHUFA_PROC_KEY;
		String caseId = xingzhengNotPenalty.getCaseId();
		Date currentDate = new Date();
		String createUserId = user.getUserId();
		Integer createOrgId = user.getOrgId();
		xingzhengNotPenalty.setAcceptOrg(createOrgId);
		try {
			// 保存不予处罚附件
			MultipartFile atachFile = multipartRequest.getFile("attach");
			Long fileId = null;
			if (atachFile != null && !atachFile.isEmpty()) {
				fileId = addCaseAtta(caseId, procKey, atachFile);
				xingzhengNotPenalty.setFileId(fileId.toString());// 不予处罚附件id
			}
			// 保存不予处罚表单
			xingzhengNotPenaltyMapper.insert(xingzhengNotPenalty);

			// 在caseBasic中更新案件状态
			CaseBasic paramCasebasic = new CaseBasic();
			paramCasebasic.setCaseId(caseId);
			paramCasebasic.setCaseState(Const.ChUFA_PROC_5);// 行政机关不予处罚
			paramCasebasic.setLatestPocessTime(currentDate);
			caseBasicMapper.updateByPrimaryKeySelective(paramCasebasic);

			// 在caseTodo中将此案件待办删
			caseTodoMapper.deleteOldCaseByCaseId(caseId);

			// 设置案件状态
			CaseState caseState = new CaseState();
			caseState.setCaseId(caseId);
			caseState.setChufaState(Const.CHUFA_STATE_NO);
			caseState.setJieanState(Const.JIEAN_STATE_YES);
			caseStateMapper.updateByPrimaryKeySelective(caseState);

			// 设置案件步骤,在常量类中设置
			CaseStep caseStep = new CaseStep();
			caseStep.setStepId(Long.valueOf(systemDAO
					.getSeqNextVal(Const.TABLE_CASE_STEP)));
			caseStep.setStepName(getCaseState(Const.ChUFA_PROC_5));
			caseStep.setCaseId(caseId);
			caseStep.setCaseState(Const.ChUFA_PROC_5);
			caseStep.setStartDate(currentDate);
			caseStep.setEndDate(currentDate);
			caseStep.setAssignPerson(createUserId);// 这一步骤的办理人
			caseStep.setTargetOrgId(createOrgId);// 目标办理机构id，在这里也就是案件录入机构
			caseStep.setTaskActionName(getCaseState(Const.ChUFA_PROC_5));
			caseStep.setTaskType(Const.TASK_TYPE_NOTPENALTY);

			ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
			caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
			caseStep.setProcDefId(procDeploy.getProcDefId());
			caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
			caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
			caseStepMapper.insert(caseStep);

		} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public ServiceResponse turnOver(User user, String caseId, Integer orgCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse saveFenpai(MultipartRequest multipartRequest,
			User user, CaseBasic caseBasic, CaseFenpai caseFenpai, String taskId) {
		ServiceResponse response = new ServiceResponse(true, "");
		String procKey = Const.CASE_CHUFA_PROC_KEY;
		//接收单位
		Integer orgCode = caseFenpai.getJieshouOrg();
		String caseId = caseFenpai.getCaseId();
		Integer createOrgId = user.getOrgId();
		String createUserId = user.getUserId();
        Date currentDate = new Date();
        if(!"".equals(taskId) && taskId != null){
            List<User> userList = userMapper.selectByOrgCode(orgCode);
            User fenpaiUser = userList.get(0);
            if(userList.size()>0){
            	taskService.setAssignee(taskId, fenpaiUser.getUserId());
            	OrganiseExample organiseExample = new OrganiseExample();
            	organiseExample.createCriteria().andUpOrgCodeEqualTo(orgCode);
            	Integer orgCode2 = organiseMapper.selectByExample(organiseExample).get(0).getOrgCode();
            	//公安分派后，该变量存的就是下级的公安机构id(两法办)
            	taskService.setVariable(taskId, ActivitiUtil.VAR_ORG_CODE_GA, orgCode2);
            	//将caseTodo中删除本级机构待办，添加分派机构待办
            	CaseTodoExample oldCaseTodoExample = new CaseTodoExample();
            	oldCaseTodoExample.createCriteria().andCaseIdEqualTo(caseId);
            	CaseTodo oldCaseTodo = caseTodoMapper.selectByExample(oldCaseTodoExample).get(0);
            	caseTodoMapper.deleteOldCaseByCaseId(caseId);
            	
            	//添加分派机构待办信息
                CaseTodo newCaseTodo = new CaseTodo();
                newCaseTodo.setTodoId(systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO));
                //当前登录用户为创建人
                newCaseTodo.setCreateUser(createUserId);
                newCaseTodo.setCreateTime(new Date());
                //当前登录用户机构为创建机构
                newCaseTodo.setCreateOrg(createOrgId);
                newCaseTodo.setAssignUser(null);
                newCaseTodo.setAssignOrg(orgCode);
                newCaseTodo.setCaseId(caseId);
                newCaseTodo.setProcInstId(oldCaseTodo.getProcInstId());
                newCaseTodo.setProcDefId(oldCaseTodo.getProcDefId());
                newCaseTodo.setTaskActionId(oldCaseTodo.getTaskActionId());
                newCaseTodo.setTaskActionName(oldCaseTodo.getTaskActionName());
                caseTodoMapper.insert(newCaseTodo);
                
                MultipartFile fenpaiFile = multipartRequest.getFile("attach");
        		Long fenpaiFileId  = null;
        		if(fenpaiFile !=null && !fenpaiFile.isEmpty()){
        			fenpaiFileId = addCaseAtta(caseId,procKey,fenpaiFile);
        			caseFenpai.setFileId(fenpaiFileId.toString());//立案决定书id
        		}
                //在案件分派表中插入记录
                caseFenpai.setCaseId(caseId);
                caseFenpai.setFenpaiOrg(createOrgId);
                caseFenpai.setJieshouOrg(orgCode);
                caseFenpai.setFenpaiTime(currentDate);
                caseFenpaiMapper.insert(caseFenpai);
                
                //更新casebaisc案件状态为已分派，等待受理（28）
                caseBasic.setIsAssign(Const.IS_ASSIGN_YES);
                caseBasic.setAssignOrg(caseFenpai.getJieshouOrg().toString());
                caseBasic.setCaseId(caseId);
                caseBasic.setCaseState(Const.CHUFA_PROC_28);
                //公安局分派的案件inputer不变
            	//caseBasic.setAssignOrg(orgCode);
                caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
                
                CaseBasic newCaseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
                //设置案件步骤,在常量类中设置
                CaseStep caseStep = new CaseStep();
                caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
                caseStep.setStepName(getCaseState(Const.CHUFA_PROC_28));
                caseStep.setCaseId(caseId);
                caseStep.setCaseState(newCaseBasic.getCaseState());
                caseStep.setStartDate(currentDate);
                caseStep.setEndDate(currentDate);
                caseStep.setAssignPerson(createUserId);//这一步骤的办理人
                //目标办理机构id，这里是分派后的下级单位orgCode
                caseStep.setTargetOrgId(orgCode);
                caseStep.setTaskActionName(getCaseState(Const.CHUFA_PROC_28));
                
                ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
                caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
                caseStep.setProcDefId(procDeploy.getProcDefId());
                caseStep.setTaskType(Const.TASK_TYPE_FENPAI);
                caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
                caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
                caseStepMapper.insert(caseStep);

            }
        }else {
        	try {
        	//更新case_basic状态，是否交办，交办人,案件状态为已分派，等待受理（28
        	//分派后下单位的org_code写入assignOrg字段
        	//caseBasic.setAssignOrg(orgCode);
        	caseBasic.setIsAssign(Const.IS_ASSIGN_YES);
        	caseBasic.setAssignOrg(caseFenpai.getJieshouOrg().toString());
        	caseBasic.setCaseId(caseId);
        	caseBasic.setCaseState(Const.CHUFA_PROC_28);
        	caseBasic.setLatestPocessTime(currentDate);
        	caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
        	
        	MultipartFile fenpaiFile = multipartRequest.getFile("attach");
    		Long fenpaiFileId  = null;
    		if(fenpaiFile !=null && !fenpaiFile.isEmpty()){
    			fenpaiFileId = addCaseAtta(caseId,procKey,fenpaiFile);
    			caseFenpai.setFileId(fenpaiFileId.toString());//立案决定书id
    		}
        	//在案件分派表中插入记录
            caseFenpai.setCaseId(caseId);
            caseFenpai.setFenpaiOrg(createOrgId);
            caseFenpai.setJieshouOrg(orgCode);
            caseFenpai.setFenpaiTime(currentDate);
            caseFenpaiMapper.insert(caseFenpai);
            
        	//更新case_todo表
        	caseTodoMapper.deleteOldCaseByCaseId(caseId);
        	
        	CaseTodo caseTodo = new CaseTodo();
        	caseTodo.setTodoId(systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO));
        	caseTodo.setCaseId(caseId);
        	//创建人是当前登录用户
        	caseTodo.setCreateUser(createUserId);
            caseTodo.setCreateTime(currentDate);
            caseTodo.setCreateOrg(createOrgId);
            //caseTodo.setAssignUser(userId);//办理人id
            caseTodo.setAssignOrg(orgCode);
            caseTodoMapper.insert(caseTodo);
            
            CaseBasic newCaseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
            //添加案件步骤
            CaseStep caseStep = new CaseStep();
            caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
            caseStep.setStepName(getCaseState(Const.CHUFA_PROC_28));
            caseStep.setCaseId(caseId);
            caseStep.setCaseState(newCaseBasic.getCaseState());
            caseStep.setStartDate(currentDate);
            caseStep.setEndDate(currentDate);
            caseStep.setAssignPerson(createUserId);//这一步骤的办理人
            //目标办理机构id，这里是分派后的下级单位orgCode
            caseStep.setTargetOrgId(orgCode);
            caseStep.setTaskActionName(getCaseState(Const.CHUFA_PROC_28));
            
            ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
            caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
            caseStep.setProcDefId(procDeploy.getProcDefId());
            caseStep.setTaskType(Const.TASK_TYPE_FENPAI);
            caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
            caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
            caseStepMapper.insert(caseStep);
        	} catch (Exception e) {
    			e.printStackTrace();
    		}
		}
        	return response;
       	}
	
	
	/**
     * 移送行政拘留
     * 将原待办记录删掉，添加一条新的
     * 在TRANSFER_RDETENTION中添加新纪录
     * user:当前登录用户
     */
    @Override
    @Transactional
    public ServiceResponse saveTransferDetention(
			MultipartRequest multipartRequest, User user,TransferDetention transferDetention) {
    	ServiceResponse res = new ServiceResponse(true,"案件办理成功");
    	String procKey = Const.CASE_CHUFA_PROC_KEY;
    	String caseId = transferDetention.getCaseId();
    	String createUser = user.getUserId();
    	Date currentDate = new Date();
    	Integer createOrg = user.getOrgId();
    	try {
			// 保存附件
			MultipartFile yisongFile = multipartRequest.getFile("yisongFile1");
			Long yisongFileId = null;
			if (yisongFile != null && !yisongFile.isEmpty()) {
				yisongFileId = addCaseAtta(caseId, procKey, yisongFile);
				transferDetention.setYisongFile(yisongFileId.intValue());// 移送材料清单附件id
			}
			
			MultipartFile otherFile = multipartRequest.getFile("otherFile1");
			Long otherFileId  = null;
			if(otherFile !=null && !otherFile.isEmpty()){
				otherFileId = addCaseAtta(caseId,procKey,otherFile);
				transferDetention.setOtherFile(otherFileId.intValue());//其他材料附件id
			}
    	
	    	//移送行政拘留，删除原有的待办，重新添加
	    	caseTodoMapper.deleteOldCaseByCaseId(caseId);
	    	
	    	CaseTodo caseTodo = new CaseTodo();
	    	caseTodo.setTodoId(systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO));
	    	caseTodo.setCaseId(caseId);
	    	caseTodo.setCreateUser(createUser);
	    	caseTodo.setCreateTime(currentDate);
	    	caseTodo.setCreateOrg(createOrg);
	    	caseTodo.setAssignOrg(transferDetention.getAcceptOrg());
	    	caseTodoMapper.insert(caseTodo);
	    	
	    	transferDetention.setTransferId(systemDAO.getSeqNextVal(Const.TABLE_TRANSFER_DETENTION));
	    	transferDetention.setCaseId(caseId);
	    	transferDetention.setCreateUser(createUser);
	    	transferDetention.setCreateTime(currentDate);
	    	transferDetentionMapper.insert(transferDetention);
	    	
	    	//将案件的状态更新为30 已移送行政拘留，待受理
	    	CaseBasic basic = new CaseBasic();
	    	basic.setCaseId(caseId);
	    	basic.setCaseState(Const.CHUFA_PROC_30);
	    	basic.setLatestPocessTime(currentDate);
	    	caseBasicMapper.updateByPrimaryKeySelective(basic);
	    	
	    	//在case_step表中增加移送行政拘留步骤
	    	CaseStep caseStep = new CaseStep();
	        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
	        String stepName = getCaseState(Const.CHUFA_PROC_30);
	        caseStep.setStepName(stepName);
	        caseStep.setCaseId(caseId);
	        caseStep.setCaseState(Const.CHUFA_PROC_30);
	        caseStep.setStartDate(currentDate);
	        caseStep.setEndDate(currentDate);//暂时这样写，需要修改
	        caseStep.setAssignPerson(user.getUserId());//这一步骤的办理人,即登录人
	        caseStep.setTargetOrgId(transferDetention.getAcceptOrg());//目标办理机构id
	        caseStep.setTaskType(Const.TASK_TYPE_TRANSFERDETENTION);
	        caseStep.setTaskActionName(stepName);
	        
	        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
	        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
	        caseStep.setProcDefId(procDeploy.getProcDefId());
	        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
	        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
	        caseStepMapper.insert(caseStep);
    	} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int getTransferDetentionTodoCount(Map<String, Object> paramMap) {
		return caseTodoMapper.getTransferDetentionTodoCount(paramMap);
	}

	@Override
	public PaginationHelper<CaseTodo> getTransferDetentionTodoList(
			CaseTodo caseTodo, String page) {
		try {
			return systemDAO.find(caseTodo,null,page,
					"com.ksource.liangfa.mapper.CaseTodoMapper.getTransferDetentionTodoCount",
					"com.ksource.liangfa.mapper.CaseTodoMapper.getTransferDetentionTodoList");
        } catch (Exception e) {
        	logger.error("查询案件失败：" + e.getMessage());
            throw new BusinessException("查询案件失败");
        }
	}
    
    
	/**
     * 行政拘留
     * 将原待办记录删掉，添加一条新的
     * 在TRANSFER_RDETENTION中添加新纪录
     * user:当前登录用户
     */
    @Override
    @Transactional
    public ServiceResponse saveDetentionInfo(
			MultipartRequest multipartRequest, User user,DetentionInfo detentionInfo) {
    	ServiceResponse res = new ServiceResponse(true,"案件办理成功");
    	String procKey = Const.CASE_CHUFA_PROC_KEY;
    	String caseId = detentionInfo.getCaseId();
    	String createUser = user.getUserId();
    	Date currentDate = new Date();
    	Integer createOrg = user.getOrgId();
    	try {
			// 保存附件
			MultipartFile detentionFile = multipartRequest.getFile("detentionFile1");
			Long detentionFileId = null;
			if (detentionFile != null && !detentionFile.isEmpty()) {
				detentionFileId = addCaseAtta(caseId, procKey, detentionFile);
				detentionInfo.setDetentionFile(detentionFileId.intValue());//拘留决定书附件id
			}
	    	//删除原有的待办
	    	caseTodoMapper.deleteOldCaseByCaseId(caseId);
	    	
	    	detentionInfo.setDetentionId(systemDAO.getSeqNextVal(Const.TABLE_DETENTION_INFO));
	    	detentionInfoMapper.insert(detentionInfo);
	    	
	    	//将案件的状态更新为31 公安办理行政拘留信息，已结案
	    	CaseBasic basic = new CaseBasic();
	    	basic.setCaseId(caseId);
	    	basic.setCaseState(Const.CHUFA_PROC_31);
	    	basic.setLatestPocessTime(currentDate);
	    	caseBasicMapper.updateByPrimaryKeySelective(basic);
	    	
	    	//将案件状态表的行政拘留状态改为行政拘留，结案状态改为已结案
	    	CaseState caseState=new CaseState();
	    	caseState.setCaseId(caseId);
	    	caseState.setJieanState(Const.JIEAN_STATE_YES);
	    	caseState.setDetentionState(Const.DETENTION_STATE_YES);
	    	caseStateMapper.updateByPrimaryKeySelective(caseState);
	    	
	    	//在case_step表中增加公安办理行政拘留信息，已结案步骤
	    	CaseStep caseStep = new CaseStep();
	        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
	        String stepName = getCaseState(Const.CHUFA_PROC_31);
	        caseStep.setStepName(stepName);
	        caseStep.setCaseId(caseId);
	        caseStep.setCaseState(Const.CHUFA_PROC_31);
	        caseStep.setStartDate(currentDate);
	        caseStep.setEndDate(currentDate);//暂时这样写，需要修改
	        caseStep.setAssignPerson(createUser);//这一步骤的办理人,即登录人
	        caseStep.setTargetOrgId(createOrg);//目标办理机构id，在这里也就是案件录入机构
	        caseStep.setTaskType(Const.TASK_TYPE_DETENTIONINFO);
	        caseStep.setTaskActionName(stepName);
	        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
	        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
	        caseStep.setProcDefId(procDeploy.getProcDefId());
	        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
	        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
	        caseStepMapper.insert(caseStep);
    	} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
		return res;
	}

    /**
     * 不予拘留
     * 将原待办记录删掉，添加一条新的
     * 在TRANSFER_RDETENTION中添加新纪录
     * user:当前登录用户
     */
    @Override
    @Transactional
	public ServiceResponse saveNotDetentionInfo(
			MultipartRequest multipartRequest, User user,
			DetentionInfo detentionInfo) {
    	ServiceResponse res = new ServiceResponse(true,"案件办理成功");
    	String procKey = Const.CASE_CHUFA_PROC_KEY;
    	String caseId = detentionInfo.getCaseId();
    	String createUser = user.getUserId();
    	Date currentDate = new Date();
    	Integer createOrg = user.getOrgId();
    	try {
			// 保存附件
			MultipartFile detentionFile = multipartRequest.getFile("detentionFile1");
			Long detentionFileId = null;
			if (detentionFile != null && !detentionFile.isEmpty()) {
				detentionFileId = addCaseAtta(caseId, procKey, detentionFile);
				detentionInfo.setDetentionFile(detentionFileId.intValue());//拘留决定书附件id
			}
	    	//删除原有的待办
	    	caseTodoMapper.deleteOldCaseByCaseId(caseId);
	    	
	    	detentionInfo.setDetentionId(systemDAO.getSeqNextVal(Const.TABLE_DETENTION_INFO));
	    	detentionInfoMapper.insert(detentionInfo);
	    	
	    	//将案件的状态更新为32 不予拘留，已结案
	    	CaseBasic basic = new CaseBasic();
	    	basic.setCaseId(caseId);
	    	basic.setCaseState(Const.CHUFA_PROC_32);
	    	basic.setLatestPocessTime(currentDate);
	    	caseBasicMapper.updateByPrimaryKeySelective(basic);
	    	
	    	//将案件状态表的行政拘留状态改为不予拘留，结案状态改为已结案
	    	CaseState caseState=new CaseState();
	    	caseState.setCaseId(caseId);
	    	caseState.setJieanState(Const.JIEAN_STATE_YES);
	    	caseState.setDetentionState(Const.DETENTION_STATE_NO);
	    	caseStateMapper.updateByPrimaryKeySelective(caseState);
	    	
	    	//在case_step表中增加公安办理不予拘留，已结案步骤
	    	CaseStep caseStep = new CaseStep();
	        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
	        String stepName = getCaseState(Const.CHUFA_PROC_32);
	        caseStep.setStepName(stepName);
	        caseStep.setCaseId(caseId);
	        caseStep.setCaseState(Const.CHUFA_PROC_32);
	        caseStep.setStartDate(currentDate);
	        caseStep.setEndDate(currentDate);//暂时这样写，需要修改
	        caseStep.setAssignPerson(createUser);//这一步骤的办理人,即登录人
	        caseStep.setTargetOrgId(createOrg);//目标办理机构id，在这里也就是案件录入机构
	        caseStep.setTaskType(Const.TASK_TYPE_NOTDETENTIONINFO);
	        caseStep.setTaskActionName(stepName);
	        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
	        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
	        caseStep.setProcDefId(procDeploy.getProcDefId());
	        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
	        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
	        caseStepMapper.insert(caseStep);
    	} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public ServiceResponse saveRequireNoLianReason(CaseRequireNolianReason require, User user,MultipartRequest multipartRequest) {
		ServiceResponse res = new ServiceResponse(true,"保存要求说明不立案理由成功！");
		try {
			String caseId = require.getCaseId();
			Date currentDate = new Date();
			
			
			
			//保存案件附件
			MultipartFile attach = multipartRequest.getFile("attach");
			Long attachFile  = null;
			if(attach !=null && !attach.isEmpty()){
				attachFile = addCaseAtta(caseId,Const.CASE_CHUFA_PROC_KEY,attach);
				require.setAttachFile(attachFile.toString());
			}
			
			require.setId(String.valueOf(systemDAO.getSeqNextVal("CASE_REQUIRE_NOLIAN_REASON")));
			CrimeCaseForm crimeCase = crimeCaseFormMapper.selectByPrimaryKey(caseId);
			//require.setReceiveOrg(crimeCase.getAcceptOrg().toString());
			require.setCreateTime(currentDate);
			require.setCreateUser(user.getUserId());
			require.setCreateUserName(user.getUserName());
			require.setCreateOrg(user.getOrgId().toString());
			requireNoLianReasonMapper.insert(require);
			
			//更新casebaisc案件状态
			CaseBasic caseBasic = new CaseBasic();
            caseBasic.setCaseId(caseId);
            caseBasic.setCaseState(Const.CHUFA_PROC_13);
            caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
            // 设置案件状态
 			CaseState caseState = new CaseState();
 			caseState.setCaseId(caseId);
 			caseState.setReqExplainState(Const.REQ_EXPLAIN_STATE_YES);
 			caseState.setExplainState(Const.EXPLAIN_STATE_NOTYET);
 			caseState.setLianSupState(Const.LIAN_SUP_STATE_1);
 			caseStateMapper.updateByPrimaryKeySelective(caseState);
 			
 			//在case_step中增加一条记录
 	        CaseStep caseStep = new CaseStep();
 	        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
 	        //TODO:从字典表中查出再拼接
 	        String stepName = getCaseState(Const.CHUFA_PROC_13);
 	        caseStep.setStepName(stepName);
 	        caseStep.setCaseId(caseId);
 	        caseStep.setCaseState(Const.CHUFA_PROC_13);
 	        caseStep.setStartDate(currentDate);
 	        caseStep.setEndDate(currentDate);
 	        caseStep.setAssignPerson(user.getUserId());//
 	        caseStep.setTargetOrgId(user.getOrgId());//目标办理机构id
 	        caseStep.setTaskType(Const.TASK_TYPE_REQNOLIANREASON);
 	        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
 	        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
 	        caseStep.setProcDefId(procDeploy.getProcDefId());
 	        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
 	        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
 	        caseStep.setTaskActionName(stepName);
 	        caseStepMapper.insert(caseStep);
 	        
		} catch (Exception e) {
			res.setResult(false);
			res.setingError("保存要求说明不立案理由失败！");
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public PaginationHelper<CaseTodo> getLianSupTodoList(CaseTodo caseTodo, Map<String, Object> map, String page) {
		try {
			return systemDAO.find(caseTodo,map,page,
					"com.ksource.liangfa.mapper.CaseTodoMapper.getLianSupTodoCount",
					"com.ksource.liangfa.mapper.CaseTodoMapper.getLianSupTodoList");
        } catch (Exception e) {
        	logger.error("查询案件失败：" + e.getMessage());
            throw new BusinessException("查询案件失败");
        }
	}

	@Override
	public int getLianSupTodoCount(Map<String, Object> map) {
		return caseTodoMapper.getLianSupTodoCount(map);
	}

	@Override
	public ServiceResponse saveNolianReason(CaseNolianReason reason, User user,MultipartRequest multipartRequest) {
		ServiceResponse res = new ServiceResponse(true,"保存说明不立案理由成功！");
		try {
			String caseId = reason.getCaseId();
			Date currentDate = new Date();
			
			//保存案件附件
			MultipartFile attach = multipartRequest.getFile("attach");
			Long attachFile  = null;
			if(attach !=null && !attach.isEmpty()){
				attachFile = addCaseAtta(caseId,Const.CASE_CHUFA_PROC_KEY,attach);
				reason.setAttachFile(attachFile.toString());
			}
			
			reason.setId(String.valueOf(systemDAO.getSeqNextVal("CASE_NOLIAN_REASON")));
			reason.setCreateTime(currentDate);
			reason.setCreateUser(user.getUserId());
			reason.setCreateUserName(user.getUserName());
			reason.setCreateOrg(user.getOrgId().toString());
			nolianReasonMapper.insert(reason);
			
			//更新casebaisc案件状态
			CaseBasic caseBasic = new CaseBasic();
            caseBasic.setCaseId(caseId);
            caseBasic.setCaseState(Const.CHUFA_PROC_15);
            caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
            // 设置案件状态
 			CaseState caseState = new CaseState();
 			caseState.setCaseId(caseId);
 			caseState.setReqExplainState(Const.REQ_EXPLAIN_STATE_YES);
 			caseState.setExplainState(Const.EXPLAIN_STATE_YES);
 			//caseState.setLianSupState(Const.LIAN_SUP_STATE_1);
 			caseStateMapper.updateByPrimaryKeySelective(caseState);
 			
 			//在case_step中增加一条记录
 	        CaseStep caseStep = new CaseStep();
 	        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
 	        //TODO:从字典表中查出再拼接
 	        String stepName = getCaseState(Const.CHUFA_PROC_15);
 	        caseStep.setStepName(stepName);
 	        caseStep.setCaseId(caseId);
 	        caseStep.setCaseState(Const.CHUFA_PROC_15);
 	        caseStep.setStartDate(currentDate);
 	        caseStep.setEndDate(currentDate);
 	        caseStep.setAssignPerson(user.getUserId());
 	        //caseStep.setTargetOrgId(user.getOrgId());//目标办理机构id
 	        caseStep.setTaskType(Const.TASK_TYPE_NOLIANREASON);
 	        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
 	        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
 	        caseStep.setProcDefId(procDeploy.getProcDefId());
 	        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
 	        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
 	        caseStep.setTaskActionName(stepName);
 	        caseStepMapper.insert(caseStep);
 	        
		} catch (Exception e) {
			res.setResult(false);
			res.setingError("保存说明不立案理由失败！");
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public ServiceResponse saveRequireLian(CaseRequireLian requireLian, User user, MultipartRequest multipartRequest) {
		ServiceResponse res = new ServiceResponse(true,"保存通知立案成功！");
		try {
			String caseId = requireLian.getCaseId();
			Date currentDate = new Date();
			
			//保存案件附件
			MultipartFile attach = multipartRequest.getFile("attach");
			Long attachFile  = null;
			if(attach !=null && !attach.isEmpty()){
				attachFile = addCaseAtta(caseId,Const.CASE_CHUFA_PROC_KEY,attach);
				requireLian.setAttachFile(attachFile.toString());
			}
			
			requireLian.setId(String.valueOf(systemDAO.getSeqNextVal("CASE_REQUIRE_LIAN")));
			requireLian.setCreateTime(currentDate);
			requireLian.setCreateUser(user.getUserId());
			requireLian.setCreateUserName(user.getUserName());
			requireLian.setCreateOrg(user.getOrgId().toString());
			caseRequireLianMapper.insert(requireLian);
			
			//更新casebaisc案件状态
			CaseBasic caseBasic = new CaseBasic();
            caseBasic.setCaseId(caseId);
            caseBasic.setCaseState(Const.CHUFA_PROC_16);
            caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
            
            CaseState caseState = new CaseState();
            caseState.setCaseId(caseId);
            caseState.setLianState(Const.LIAN_STATE_NOTICE);
            caseState.setLianSupState(Const.LIAN_SUP_STATE_2);
            caseStateMapper.updateByPrimaryKeySelective(caseState);
 			
 			//在case_step中增加一条记录
 	        CaseStep caseStep = new CaseStep();
 	        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
 	        //TODO:从字典表中查出再拼接
 	        String stepName = getCaseState(Const.CHUFA_PROC_16);
 	        caseStep.setStepName(stepName);
 	        caseStep.setCaseId(caseId);
 	        caseStep.setCaseState(Const.CHUFA_PROC_16);
 	        caseStep.setStartDate(currentDate);
 	        caseStep.setEndDate(currentDate);
 	        caseStep.setAssignPerson(user.getUserId());
 	        caseStep.setTargetOrgId(user.getOrgId());//目标办理机构id
 	        caseStep.setTaskType(Const.TASK_TYPE_REQLIAN);
 	        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
 	        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
 	        caseStep.setProcDefId(procDeploy.getProcDefId());
 	        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
 	        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
 	        caseStep.setTaskActionName(stepName);
 	        caseStepMapper.insert(caseStep);
		} catch (Exception e) {
			res.setResult(false);
			res.setingError("保存通知立案失败！");
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public ServiceResponse saveAgreeNoLian(CaseAgreeNolian agreeNoLian, User user, MultipartRequest multipartRequest) {
		ServiceResponse res = new ServiceResponse(true,"保存同意理由成功！");
		try {
			String caseId = agreeNoLian.getCaseId();
			Date currentDate = new Date();
			
			//保存案件附件
			MultipartFile attach = multipartRequest.getFile("attach");
			Long attachFile  = null;
			if(attach !=null && !attach.isEmpty()){
				attachFile = addCaseAtta(caseId,Const.CASE_CHUFA_PROC_KEY,attach);
				agreeNoLian.setAttachFile(attachFile.toString());
			}
			
			agreeNoLian.setId(String.valueOf(systemDAO.getSeqNextVal("CASE_AGREE_NOLIAN")));
			agreeNoLian.setCreateTime(currentDate);
			agreeNoLian.setCreateUser(user.getUserId());
			agreeNoLian.setCreateUserName(user.getUserName());
			agreeNoLian.setCreateOrg(user.getOrgId().toString());
			caseAgreeNoLianMapper.insert(agreeNoLian);
			
			//更新casebaisc案件状态
			CaseBasic caseBasic = new CaseBasic();
            caseBasic.setCaseId(caseId);
            caseBasic.setCaseState(Const.CHUFA_PROC_12);
            caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
            
            CaseState caseState = new CaseState();
            caseState.setCaseId(caseId);
            caseState.setLianState(Const.LIAN_STATE_NO);
            caseState.setJieanState(Const.JIEAN_STATE_YES);
            caseState.setLianSupState(Const.LIAN_SUP_STATE_3);//去除立案监督状态
            caseStateMapper.updateByPrimaryKeySelective(caseState);
 			
 			//在case_step中增加一条记录
 	        CaseStep caseStep = new CaseStep();
 	        caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
 	        //TODO:从字典表中查出再拼接
 	        String stepName = getCaseState(Const.CHUFA_PROC_12);
 	        caseStep.setStepName(stepName);
 	        caseStep.setCaseId(caseId);
 	        caseStep.setCaseState(Const.CHUFA_PROC_12);
 	        caseStep.setStartDate(currentDate);
 	        caseStep.setEndDate(currentDate);
 	        caseStep.setAssignPerson(user.getUserId());
 	        caseStep.setTargetOrgId(user.getOrgId());//目标办理机构id
 	        caseStep.setTaskType(Const.TASK_TYPE_AGREENOLIAN);
 	        ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
 	        caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
 	        caseStep.setProcDefId(procDeploy.getProcDefId());
 	        caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
 	        caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
 	        caseStep.setTaskActionName(stepName);
 	        caseStepMapper.insert(caseStep);
		} catch (Exception e) {
			res.setResult(false);
			res.setingError("保存同意理由失败！");
			e.printStackTrace();
		}
		return res;
	}
		
}
