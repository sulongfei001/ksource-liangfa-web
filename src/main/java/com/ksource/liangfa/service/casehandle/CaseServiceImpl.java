package com.ksource.liangfa.service.casehandle;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.ksource.common.bean.DatabaseDialectBean;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.jms.MessageProducer;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.CosineSimilarityUtil;
import com.ksource.common.util.DateUtil;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.exception.CaseDealException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.ActivityCase;
import com.ksource.liangfa.domain.ActivityCaseExample;
import com.ksource.liangfa.domain.CaseAccuseExample;
import com.ksource.liangfa.domain.CaseAccuseKey;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseAttachmentExample;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseCompanyExample;
import com.ksource.liangfa.domain.CaseConsultation;
import com.ksource.liangfa.domain.CaseConsultationContent;
import com.ksource.liangfa.domain.CaseConsultationContentExample;
import com.ksource.liangfa.domain.CaseConsultationExample;
import com.ksource.liangfa.domain.CaseFenpai;
import com.ksource.liangfa.domain.CaseFilter;
import com.ksource.liangfa.domain.CaseIllegalSituation;
import com.ksource.liangfa.domain.CaseIllegalSituationExample;
import com.ksource.liangfa.domain.CaseImport;
import com.ksource.liangfa.domain.CaseImportExample;
import com.ksource.liangfa.domain.CaseJieanNoticeExample;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CasePartyExample;
import com.ksource.liangfa.domain.CaseRecord;
import com.ksource.liangfa.domain.CaseReply;
import com.ksource.liangfa.domain.CaseReplyExample;
import com.ksource.liangfa.domain.CaseState;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.CaseTurnover;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.domain.CaseYisongNoticeExample;
import com.ksource.liangfa.domain.ClueDispatch;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.ClueInfoReply;
import com.ksource.liangfa.domain.CompanyLib;
import com.ksource.liangfa.domain.CrimeCaseExt;
import com.ksource.liangfa.domain.CrimeCaseForm;
import com.ksource.liangfa.domain.Dictionary;
import com.ksource.liangfa.domain.DictionaryExample;
import com.ksource.liangfa.domain.DqdjCaseCategory;
import com.ksource.liangfa.domain.FieldInstance;
import com.ksource.liangfa.domain.FieldInstanceExample;
import com.ksource.liangfa.domain.IllegalSituation;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.PenaltyCaseForm;
import com.ksource.liangfa.domain.PenaltyLianForm;
import com.ksource.liangfa.domain.PenaltyReferCaseExt;
import com.ksource.liangfa.domain.PeopleLib;
import com.ksource.liangfa.domain.ProcDeploy;
import com.ksource.liangfa.domain.SpecialActivity;
import com.ksource.liangfa.domain.SuggestYisongForm;
import com.ksource.liangfa.domain.TakeoffAdministrativeOrganExample;
import com.ksource.liangfa.domain.TimeoutWarn;
import com.ksource.liangfa.domain.TimeoutWarnExample;
import com.ksource.liangfa.domain.TimeoutWarnReminder;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.UserExample;
import com.ksource.liangfa.domain.XianyirenAccuseExample;
import com.ksource.liangfa.domain.XingzhengCancelLianForm;
import com.ksource.liangfa.domain.XingzhengJieanForm;
import com.ksource.liangfa.domain.XingzhengNotLianForm;
import com.ksource.liangfa.domain.XingzhengNotPenalty;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.mapper.ActivityCaseMapper;
import com.ksource.liangfa.mapper.CaseAccuseMapper;
import com.ksource.liangfa.mapper.CaseAccuseRuleRelationMapper;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CaseConsultationContentMapper;
import com.ksource.liangfa.mapper.CaseConsultationMapper;
import com.ksource.liangfa.mapper.CaseFenpaiMapper;
import com.ksource.liangfa.mapper.CaseIllegalSituationMapper;
import com.ksource.liangfa.mapper.CaseImportMapper;
import com.ksource.liangfa.mapper.CaseJieanNoticeMapper;
import com.ksource.liangfa.mapper.CasePartyMapper;
import com.ksource.liangfa.mapper.CaseProcessMapper;
import com.ksource.liangfa.mapper.CaseRecordMapper;
import com.ksource.liangfa.mapper.CaseReplyMapper;
import com.ksource.liangfa.mapper.CaseStateMapper;
import com.ksource.liangfa.mapper.CaseStepMapper;
import com.ksource.liangfa.mapper.CaseTodoMapper;
import com.ksource.liangfa.mapper.CaseTurnoverMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.mapper.CaseYisongNoticeMapper;
import com.ksource.liangfa.mapper.ClueAcceptMapper;
import com.ksource.liangfa.mapper.ClueDispatchMapper;
import com.ksource.liangfa.mapper.ClueInfoMapper;
import com.ksource.liangfa.mapper.ClueInfoReplyMapper;
import com.ksource.liangfa.mapper.CompanyLibMapper;
import com.ksource.liangfa.mapper.CrimeCaseExtMapper;
import com.ksource.liangfa.mapper.CrimeCaseFormMapper;
import com.ksource.liangfa.mapper.DictionaryMapper;
import com.ksource.liangfa.mapper.DqdjCaseCategoryMapper;
import com.ksource.liangfa.mapper.FieldInstanceMapper;
import com.ksource.liangfa.mapper.IllegalSituationAccuseMapper;
import com.ksource.liangfa.mapper.IllegalSituationMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PenaltyCaseFormMapper;
import com.ksource.liangfa.mapper.PenaltyLianFormMapper;
import com.ksource.liangfa.mapper.PenaltyReferCaseExtMapper;
import com.ksource.liangfa.mapper.PeopleLibMapper;
import com.ksource.liangfa.mapper.ProcDeployMapper;
import com.ksource.liangfa.mapper.SpecialActivityMapper;
import com.ksource.liangfa.mapper.SuggestYisongFormMapper;
import com.ksource.liangfa.mapper.TakeoffAdministrativeOrganMapper;
import com.ksource.liangfa.mapper.TimeoutWarnMapper;
import com.ksource.liangfa.mapper.TimeoutWarnReminderMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.mapper.XianyirenAccuseMapper;
import com.ksource.liangfa.mapper.XingzhengCancelLianFormMapper;
import com.ksource.liangfa.mapper.XingzhengJieanFormMapper;
import com.ksource.liangfa.mapper.XingzhengNotLianFormMapper;
import com.ksource.liangfa.mapper.XingzhengNotPenaltyMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.AccuseRuleService;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.workflow.ProcessFactory;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;
import com.ksource.syscontext.ThreadContext;

/**
 * 此类为 案件 业务 层
 *
 * @author zxl :)
 * @version 1.0 date 2011-5-13 time 上午09:35:32
 */
@Service
public class CaseServiceImpl implements CaseService {
    // 日志
    private static final Logger log = LogManager
            .getLogger(CaseServiceImpl.class);
    @Autowired
    SystemDAO systemDAO;
    @Autowired
    CasePartyMapper casePartyMapper;
    @Autowired
    PeopleLibMapper peopleLibMapper;
    @Autowired
    CompanyLibMapper companyLibMapper;
    @Autowired
    CaseStepMapper caseStepMapper;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    CaseXianyirenMapper caseXianyirenMapper;
    @Autowired
    CaseCompanyMapper caseCompanyMapper;
    @Resource(name = "databaseConfigBean")
    DatabaseDialectBean databaseDialectBean;
    @Autowired
    SpecialActivityMapper specialActivityMapper;
    @Autowired
    ActivityCaseMapper activityCaseMapper;
    @Autowired
    private CaseAttachmentMapper caseAttachmentMapper;
    @Autowired
    private TimeoutWarnReminderMapper timeoutWarnReminderMapper;
    @Autowired
    private MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    private TakeoffAdministrativeOrganMapper takeoffAdministrativeOrganMapper;
    @Autowired
    private CaseAccuseMapper caseAccuseMapper;
    @Autowired
    private XianyirenAccuseMapper xianyirenAccuseMapper;
    @Autowired
    private CaseConsultationContentMapper caseConsultationContentMapper;
    @Autowired
    private CaseConsultationMapper caseConsultationMapper;
    @Autowired
    private CaseReplyMapper caseReplyMapper;
    @Autowired
    private CaseJieanNoticeMapper caseJieanNoticeMapper;
    @Autowired
    private FieldInstanceMapper fieldInstanceMapper;
    @Autowired
    private CaseStateMapper caseStateMapper;
    @Autowired
    private CrimeCaseExtMapper crimeCaseExtMapper;
    @Autowired
    private CaseBasicMapper caseBasicMapper;
    @Autowired
    private CaseImportMapper caseImportMapper;
    @Autowired
    private CaseProcessMapper caseProcessMapper;
    @Autowired
    private OrganiseMapper organiseMapper;
    @Autowired
    private PenaltyReferCaseExtMapper penaltyReferCaseExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CaseRecordMapper caseRecordMapper;
    @Autowired
    private DqdjCaseCategoryMapper dqdjCaseCategoryMapper;
    @Autowired
    private AccuseInfoMapper accuseInfoMapper;
    @Autowired
    private CaseIllegalSituationMapper caseIllegalSituationMapper;
    @Autowired
    private IllegalSituationMapper illegalSituationMapper;
    @Autowired
    private IllegalSituationAccuseMapper illegalSituationAccuseMapper;
    @Autowired
    private CaseTodoMapper caseTodoMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private ProcDeployMapper procDeployMapper;
    @Autowired
    private PenaltyCaseFormMapper penaltyCaseFormMapper;
    @Autowired
    private CrimeCaseFormMapper crimeCaseFormMapper;
    @Autowired
    private SuggestYisongFormMapper suggestYisongFormMapper;
    @Autowired
    private PenaltyLianFormMapper penaltyLianFormMapper;
    @Autowired
    private CaseYisongNoticeMapper caseYisongNoticeMapper;
	@Autowired
	private MessageProducer messageProducer;
	@Autowired
	private ClueAcceptMapper clueAcceptMapper;
	@Autowired
	private ClueInfoMapper clueInfoMapper;
	@Autowired
	private ClueDispatchMapper clueDispatchMapper;
	@Autowired
	private XingzhengNotLianFormMapper xingzhengNotLianFormMapper;
	@Autowired
	private CaseTurnoverMapper caseTurnoverMapper;
	@Autowired
	private CaseFenpaiMapper caseFenpaiMapper;
	@Autowired
	private XingzhengNotPenaltyMapper xingzhengNotPenaltyMapper;
	@Autowired
	private XingzhengCancelLianFormMapper xingzhengCancelLianFormMapper;
	@Autowired
	private XingzhengJieanFormMapper xingzhengJieanFormMapper;
	@Autowired
	private SuggestYisongFormMapper suggestYisongMapper;
    @Autowired
    private CaseAccuseRuleRelationMapper  caseAccuseRuleRelationMapper;
    @Autowired
	private AccuseRuleService  accuseRuleService;
    @Autowired
	private InstantMessageService instantMessageService;
    @Autowired
    private ClueInfoReplyMapper clueInfoReplyMapper;
    
    @Override
    @Transactional(readOnly = true)
    public String getCaseSequenceId() {
        try {
            return String.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_BASIC));
        } catch (Exception e) {
            log.error("案件序列ID生成失败：" + e.getMessage());
            throw new BusinessException("案件序列ID生成失败");
        }
    }

    public int getCaseAttSequenceId() {
        return systemDAO.getSeqNextVal("CASE_ATTACHMENT");
    }

    //获取待办案件id
    public int getCaseTodoSequenceId() {
        return systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO);
    }
    @Override
    @Transactional
    @LogBusiness(operation = "案件受理", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_mapper_class = CaseBasicMapper.class, target_domain_position = 0)
    public ServiceResponse addPenaltyCase(CaseBasic caseBasic,MultipartHttpServletRequest attachmentFile,String oldCaseId,User user,HttpServletRequest request) {
        ServiceResponse res = new ServiceResponse(true, "案件添加成功");
        try {
        	//移送管辖受理，其他单位移送给本单位的案件
        	//移送管辖受理先删除当前待办，再添加新的待办
        	if(oldCaseId!=null){
        		caseTodoMapper.deleteOldCaseByCaseId(oldCaseId);
        	}
            // 获取案件ID
            String caseId = getCaseSequenceId();
            caseBasic.setCaseId(caseId);
            Date currentDate = new Date();
            caseBasic.setInputer(user.getUserId());
            caseBasic.setInputUnit(user.getOrgId());
            caseBasic.setInputTime(currentDate);
            caseBasic.setCaseState(Const.CHUFA_PROC_0);//案件处理状态，行政受理
            caseBasic.setLatestPocessTime(currentDate);
            caseBasic.setProcKey(Const.CASE_CHUFA_PROC_KEY);
            caseBasic.setInputType(Const.INPUT_TYPE_HAND); //手动录入
            caseBasic.setVersionNo(Const.SYSTEM_VERSION_1);
            caseBasic.setIsAssign(Const.IS_ASSIGN_NO);//是否交办案件
            caseBasic.setIsSuspectedCriminal(Const.SUSPECTED_CRIMINAL_NO);//疑似涉嫌犯罪
            //对案件编号进行去掉空格处理(与案件编号的验证，查询保持一致)
            caseBasic.setCaseNo(StringUtils.trim(caseBasic.getCaseNo()));
            caseBasic.setIsTurnover(Const.IS_TURNOVER_NO);//默认案件都不移送管辖
            caseBasicMapper.insert(caseBasic);
            
            //添加待办信息
            CaseTodo caseTodo = new CaseTodo();
            caseTodo.setTodoId(getCaseTodoSequenceId());
            caseTodo.setCreateUser(caseBasic.getInputer());
            caseTodo.setCreateTime(currentDate);
            caseTodo.setCreateOrg(caseBasic.getInputUnit());
            caseTodo.setAssignUser(caseBasic.getInputer());
            caseTodo.setAssignOrg(caseBasic.getInputUnit());
            caseTodo.setCaseId(caseId);
            caseTodoMapper.insert(caseTodo);
            
            //设置案件状态
            CaseState caseState = new CaseState();
            caseState.setCaseId(caseId);
            caseState.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NOTYET);//行政立案状态
            caseState.setChufaState(Const.CHUFA_STATE_NOTYET);
            caseState.setLianState(Const.LIAN_STATE_NOTYET);
            caseState.setDaibuState(Const.DAIBU_STATE_NOTYET);
            caseState.setQisuState(Const.QISU_STATE_NOTYET);
            caseState.setPanjueState(Const.PANJUE_STATE_NOTYET);
            caseState.setYisongState(Const.YISONG_STATE_NO);
            caseState.setJieanState(Const.JIEAN_STATE_NO);
            caseState.setExplainState(Const.EXPLAIN_STATE_NOTYET);
            caseState.setReqExplainState(Const.REQ_EXPLAIN_STATE_NOTYET);            
            caseStateMapper.insert(caseState);
            
            //设置案件步骤,在常量类中设置
            CaseStep caseStep = new CaseStep();
            caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
            caseStep.setStepName(getCaseState(Const.CHUFA_PROC_0));
            caseStep.setCaseId(caseId);
            caseStep.setCaseState(caseBasic.getCaseState());
            caseStep.setStartDate(caseBasic.getInputTime());
            caseStep.setEndDate(caseBasic.getInputTime());
            caseStep.setAssignPerson(caseBasic.getInputer());//这一步骤的办理人
            caseStep.setTargetOrgId(caseBasic.getInputUnit());//目标办理机构id，在这里也就是案件录入机构
            caseStep.setTaskActionName(getCaseState(Const.CHUFA_PROC_0));
            
            ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
            caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
            caseStep.setProcDefId(procDeploy.getProcDefId());
            caseStep.setTaskType(Const.TASK_TYPE_ADD_CASE);
            caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
            caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
            caseStepMapper.insert(caseStep);
            
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
                        CaseXianyiren caseXianyiren = getCaseXianYiFanByCaseParty(caseParty);
                        caseXianyirenMapper.insertSelective(caseXianyiren);
                        // 保存到个人库
                        PeopleLib peopleLib = PeopleLib.createFromCaseParty(caseParty);
                        peopleLib.setInputer(caseBasic.getInputer());
                        peopleLib.setInputTime(caseBasic.getInputTime());
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
                        caseCompanyMapper.insertSelective(caseCompany);
                        // 保存到企业库
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
            
	        //疑似涉嫌犯罪分析
	        String industryType = user.getOrganise().getIndustryType();
            //罪名规则匹配案件基本信息并且保存案件罪名规则关联信息
	        
	        caseBasicMapper.updateByPrimaryKeySelective(accuseRuleService.matchCaseRule(caseBasic,industryType,request));
//	        crimeAnalyze(caseId,industryType,caseBasic.getCaseDetailName());
        } catch (CaseDealException e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件添加失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件添加失败！");
        }
        return res;
    }


	/**
	 * 分析是否涉嫌犯罪，比较案件受理表单中违法事实和该行业的违法情形描述
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
    
    
    /**
     * 保存案件符件
     *
     * @param caseId
     * @param procKey
     * @param penaltyFile
     * @return
     * @author XT
     */
    private Long addCaseAtta(String caseId, String procKey,
                            MultipartFile penaltyFile) {
    	Long caseAttSequenceId = Long.valueOf(getCaseAttSequenceId());
        CaseAttachment atta = new CaseAttachment();
        atta.setId(Long.valueOf(caseAttSequenceId));
        atta.setProcKey(procKey);
        atta.setCaseId(caseId);
        UpLoadContext upLoad = new UpLoadContext(
                new UploadResource());
        String url = upLoad.uploadFile(penaltyFile, null);
        String fileName = penaltyFile.getOriginalFilename();
        atta.setAttachmentName(fileName);
        atta.setAttachmentPath(url);
        //保存案件符件
        caseAttachmentMapper.insert(atta);
        return caseAttSequenceId;
    }

    @Override
    @Transactional
    @LogBusiness(operation = "添加涉嫌犯罪案件", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_mapper_class = CaseBasicMapper.class, target_domain_position = 0)
    public ServiceResponse addCrimeCase(CaseBasic caseBasic,
                                        CaseState caseState, CrimeCaseExt crimeCaseExt, Map<String, Object> map, MultipartHttpServletRequest attachmentFile, Integer orgCode) {
        ServiceResponse res = new ServiceResponse(true, "案件添加成功");
        try {
            // 获取案件ID
            String caseId = getCaseSequenceId();
            caseBasic.setCaseId(caseId);
            //案件录入类型 ----手工录入
            caseBasic.setInputType(Const.INPUT_TYPE_HAND);
            caseState.setCaseId(caseId);
            crimeCaseExt.setCaseId(caseId);
            caseBasic.setCaseState(String.valueOf(Const.STATE_INVALID));
            String procKey = caseBasic.getProcKey();
            String inputer = caseBasic.getInputer();
            String casePartyJson = caseBasic.getCasePartyJson();
            String caseCompanyJson = caseBasic.getCaseCompanyJson();


            //保存案件符件和行政处罚案件扩展属性
            MultipartFile attaFile = null;
            String fileName = null;
            Long fildId;
            //主要物证和书证目录
            attaFile = attachmentFile.getFile("proofFile");
            fileName = attaFile.getOriginalFilename();
            if (StringUtils.isNotEmpty(fileName)) {
                fildId = addCaseAtta(caseId, procKey, attaFile);
                crimeCaseExt.setProofFileId(fildId);
            }

            //保存涉嫌犯罪案件扩展属性
            crimeCaseExtMapper.insert(crimeCaseExt);

            //保存案件状态
            caseStateMapper.insert(caseState);

            //保存案件基本信息
            caseBasicMapper.insert(caseBasic);


            //保存案件流程信息并启动流程
            ProcessFactory.createProcessUtil(caseBasic).saveBusinessEntityAndstartProcessInstanceByProcDefKey(
                    procKey, inputer, map);

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
                        caseXianyirenMapper
                                .insertSelective(getCaseXianYiFanByCaseParty(caseParty));
                        // 保存到个人库
                        PeopleLib peopleLib = PeopleLib
                                .createFromCaseParty(caseParty);
                        peopleLib.setInputer(caseBasic.getInputer());
                        peopleLib.setInputTime(caseBasic.getInputTime());
                        int count = peopleLibMapper
                                .updateByPrimaryKeySelective(peopleLib);
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
                        caseCompanyMapper.insertSelective(caseCompany);
                        // 保存到企业库
                        CompanyLib companyLib = CompanyLib
                                .createFromCaseCompany(caseCompany);
                        companyLib.setInputer(caseBasic.getInputer());
                        companyLib.setInputTime(caseBasic.getInputTime());
                        int count = companyLibMapper
                                .updateByPrimaryKeySelective(companyLib);
                        if (count == 0) {
                            companyLibMapper.insert(companyLib);
                        }
                    }
                }
            }

            //保存案件和专项活动关联关系
            if (orgCode != null) {
                saveActivityCaseRelation(caseId, caseBasic.getInputTime(), orgCode);
            }
            //保存报备关联信息(以后如果录入的速度很慢，可以考虑单独来一个线程来执行些任务)
            //查询与当前用户在同一行政区划下的检查院用户
            User currUser = ThreadContext.getCurUser();
            String userDis;
            if(currUser.getAccount().equals(Const.SYSTEM_ADMIN_ID)){
                userDis= SystemContext.getSystemInfo().getDistrict();
            }else{
                userDis=currUser.getOrganise().getDistrictCode();
            }
            OrganiseExample organiseExample = new OrganiseExample();
            organiseExample.createCriteria().andDistrictCodeEqualTo(userDis).andOrgTypeEqualTo(Const.ORG_TYPE_JIANCHAYUAN);
            List<Organise> orgList = organiseMapper.selectByExample(organiseExample);
            List<Integer> orgIdList = new ArrayList<Integer>();
            for (Organise temp : orgList) {
                orgIdList.add(temp.getOrgCode());
            }
            UserExample userExample = new UserExample();
            userExample.createCriteria().andOrgIdIn(orgIdList);
            List<User> userList = userMapper.selectByExample(userExample);
            CaseRecord caseRecord = new CaseRecord();
            caseRecord.setCaseId(caseId);
            caseRecord.setProcKey(procKey);
            caseRecord.setReadState(Const.STATE_INVALID);//未读
            for (User user : userList) {
                caseRecord.setUserId(user.getUserId());
                caseRecordMapper.insert(caseRecord);
            }
            
            //保存打侵打假关联关系
            if(caseBasic.getDqdjType() != null){
             	DqdjCaseCategory caseCategory = new DqdjCaseCategory();
             	caseCategory.setCaseId(caseId);
             	caseCategory.setCategoryId(caseBasic.getDqdjType());
             	dqdjCaseCategoryMapper.insert(caseCategory);
            }
        } catch (CaseDealException e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件添加失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件添加失败！");
        }
        return res;
    }

    @Override
    @Transactional
    @LogBusiness(operation = "修改涉嫌犯罪案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = CrimeCaseExtMapper.class)
    public ServiceResponse updateCrimeCase(CaseBasic caseBasic, CrimeCaseExt crimeCaseExt, MultipartHttpServletRequest attachmentFile) {
        ServiceResponse serviceResponse = new ServiceResponse(true, "");
        String caseId = caseBasic.getCaseId();
        String procKey = caseBasic.getProcKey();
        //先保存符件得到符件ID
        //保存案件符件和行政处罚案件扩展属性
        MultipartFile attaFile = null;
        String fileName = null;
        Long fildId;
        //主要物证和书证目录
        attaFile = attachmentFile.getFile("proofFile");
        if (attaFile != null) {
            caseAttachmentMapper.deleteByPrimaryKey(Long.valueOf(crimeCaseExt.getProofFileId()));
            fildId = addCaseAtta(caseId, procKey, attaFile);
            crimeCaseExt.setProofFileId(fildId);
        }


        caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
        crimeCaseExtMapper.updateByPrimaryKeySelective(crimeCaseExt);
        // 保存 案件嫌疑人信息并把嫌疑人信息保存为嫌疑人，如果没有嫌疑人，企业信息则什么也不做
        List<CaseParty> casePartyList = null;
        List<CaseCompany> caseCompanyList = null;
        String casePartyJson = caseBasic.getCasePartyJson();
        if (StringUtils.isNotBlank(casePartyJson)) {
            casePartyList = JSON.parseArray(casePartyJson, CaseParty.class);
        }
        String caseCompanyJson = caseBasic.getCaseCompanyJson();
        if (StringUtils.isNotBlank(caseCompanyJson)) {
            caseCompanyList = JSON.parseArray(caseCompanyJson, CaseCompany.class);
        }
        try {
            if (casePartyList != null && !casePartyList.isEmpty()) {
                CasePartyExample casePartyExample = new CasePartyExample();
                CaseXianyirenExample caseXianyirenExample = new CaseXianyirenExample();
                casePartyExample.createCriteria().andCaseIdEqualTo(
                        caseBasic.getCaseId());
                caseXianyirenExample.createCriteria().andCaseIdEqualTo(
                        caseBasic.getCaseId());
                casePartyMapper.deleteByExample(casePartyExample);
                caseXianyirenMapper.deleteByExample(caseXianyirenExample);


                for (CaseParty caseParty : casePartyList) {
                    if (caseParty == null)
                        continue;
                    caseParty.setCaseId(caseBasic.getCaseId());
                    caseParty.setPartyId(Long.valueOf((systemDAO
                            .getSeqNextVal(Const.TABLE_CASE_PARTY))));
                    casePartyMapper.insertSelective(caseParty);
                    caseXianyirenMapper
                            .insertSelective(getCaseXianYiFanByCaseParty(caseParty));
                    // 保存到个人库
                    PeopleLib peopleLib = PeopleLib
                            .createFromCaseParty(caseParty);
                    int count = peopleLibMapper
                            .updateByPrimaryKeySelective(peopleLib);
                    if (count == 0) {
                        peopleLibMapper.insert(peopleLib);
                    }
                }
            }
            if (caseCompanyList != null && !caseCompanyList.isEmpty()) {
                CaseCompanyExample caseCompanyExample = new CaseCompanyExample();
                caseCompanyExample.createCriteria().andCaseIdEqualTo(
                        caseBasic.getCaseId());
                caseCompanyMapper.deleteByExample(caseCompanyExample);

                for (CaseCompany caseCompany : caseCompanyList) {
                    if (caseCompany == null)
                        continue;
                    caseCompany.setCaseId(caseBasic.getCaseId());
                    caseCompany.setId(systemDAO.getSeqNextVal(Const.TABLE_CASE_COMPANY));
                    caseCompanyMapper.insertSelective(caseCompany);
                    // 保存到企业库
                    CompanyLib companyLib = CompanyLib
                            .createFromCaseCompany(caseCompany);
                    int count = companyLibMapper
                            .updateByPrimaryKeySelective(companyLib);
                    if (count == 0) {
                        companyLibMapper.insert(companyLib);
                    }
                }
            }

        } catch (Exception e) {
            log.error("修改案件失败：" + e.getMessage());
            throw new BusinessException("修改案件失败");
        }
        return serviceResponse;
    }

    /**
     * 案件信息 查询
     */
    @Override


    public PaginationHelper<CaseBasic> findByState(CaseBasic caseBasic, String page,
                                                   Map<String, Object> map) {
        try {
            return systemDAO.find(caseBasic, map, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getStateCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getStateList");
        } catch (Exception e) {
            log.error("查询案件失败：" + e.getMessage());
            throw new BusinessException("查询案件失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationHelper<CaseBasic> findByAccuse(CaseBasic caCondition, String page,
                                                    Map<String, Object> map) {
        try {
            return systemDAO.find(caCondition, map, page, "getAccuseCount",
                    "getAccuseList");
        } catch (Exception e) {
            log.error("查询案件失败：" + e.getMessage());
            throw new BusinessException("查询案件失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationHelper<CaseBasic> find(CaseBasic caseBasic, String page,
                                            Map<String, Object> param) {
        try {
            return systemDAO.find(caseBasic, page, param);
        } catch (Exception e) {
            log.error("查询案件失败：" + e.getMessage());
            throw new BusinessException("查询案件失败");
        }
    }

    @Override
    @Transactional
    @LogBusiness(operation = "修改违法案件", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = CaseBasicMapper.class)
    public ServiceResponse update(CaseBasic caseWithBLOBs, MultipartHttpServletRequest attachmentFile) {
        ServiceResponse serviceResponse = new ServiceResponse(true, "");
        caseBasicMapper.updateByPrimaryKeySelective(caseWithBLOBs);

        // 保存 案件嫌疑人信息并把嫌疑人信息保存为嫌疑人，如果没有嫌疑人，企业信息则什么也不做
        List<CaseParty> casePartyList = null;
        List<CaseCompany> caseCompanyList = null;
        if (StringUtils.isNotBlank(caseWithBLOBs.getCasePartyJson())) {
            casePartyList = JSON.parseArray(caseWithBLOBs.getCasePartyJson(),
                    CaseParty.class);
        }
        if (StringUtils.isNotBlank(caseWithBLOBs.getCaseCompanyJson())) {
            caseCompanyList = JSON.parseArray(
                    caseWithBLOBs.getCaseCompanyJson(), CaseCompany.class);
        }
        // 把嫌疑人和嫌疑人,企业都删除，再添加,，如果没有嫌疑人，企业信息则什么也不做
        try {
            if (casePartyList != null && !casePartyList.isEmpty()) {
                CasePartyExample casePartyExample = new CasePartyExample();
                CaseXianyirenExample caseXianyirenExample = new CaseXianyirenExample();
                casePartyExample.createCriteria().andCaseIdEqualTo(
                        caseWithBLOBs.getCaseId());
                caseXianyirenExample.createCriteria().andCaseIdEqualTo(
                        caseWithBLOBs.getCaseId());
                casePartyMapper.deleteByExample(casePartyExample);
                caseXianyirenMapper.deleteByExample(caseXianyirenExample);


                for (CaseParty caseParty : casePartyList) {
                    if (caseParty == null)
                        continue;
                    caseParty.setCaseId(caseWithBLOBs.getCaseId());
                    caseParty.setPartyId(Long.valueOf((systemDAO
                            .getSeqNextVal(Const.TABLE_CASE_PARTY))));
                    casePartyMapper.insertSelective(caseParty);
                    caseXianyirenMapper
                            .insertSelective(getCaseXianYiFanByCaseParty(caseParty));
                    // 保存到个人库
                    PeopleLib peopleLib = PeopleLib
                            .createFromCaseParty(caseParty);
                    int count = peopleLibMapper
                            .updateByPrimaryKeySelective(peopleLib);
                    if (count == 0) {
                        peopleLibMapper.insert(peopleLib);
                    }
                }
            }
            if (caseCompanyList != null && !caseCompanyList.isEmpty()) {
                CaseCompanyExample caseCompanyExample = new CaseCompanyExample();
                caseCompanyExample.createCriteria().andCaseIdEqualTo(
                        caseWithBLOBs.getCaseId());
                caseCompanyMapper.deleteByExample(caseCompanyExample);

                for (CaseCompany caseCompany : caseCompanyList) {
                    if (caseCompany == null)
                        continue;
                    caseCompany.setCaseId(caseWithBLOBs.getCaseId());
                    caseCompany.setId(systemDAO.getSeqNextVal(Const.TABLE_CASE_COMPANY));
                    caseCompanyMapper.insertSelective(caseCompany);
                    // 保存到企业库
                    CompanyLib companyLib = CompanyLib
                            .createFromCaseCompany(caseCompany);
                    int count = companyLibMapper
                            .updateByPrimaryKeySelective(companyLib);
                    if (count == 0) {
                        companyLibMapper.insert(companyLib);
                    }
                }
            }
            List<MultipartFile> files = null;
            if (attachmentFile != null
                    && (files = attachmentFile.getFiles("caseDetailFiles")) != null
                    && !files.isEmpty()) {
                CaseAttachment atta = new CaseAttachment();
                atta.setCaseId(caseWithBLOBs.getCaseId());
                atta.setProcKey(caseWithBLOBs.getProcKey());
                UpLoadContext upLoad = new UpLoadContext(new UploadResource());
                for (MultipartFile file : files) {
                    if (file.getSize() != 0) {
                        String url = upLoad.uploadFile(file, null);
                        String fileName = file.getOriginalFilename();
                        atta.setId(Long.valueOf(getCaseAttSequenceId()));
                        atta.setAttachmentName(fileName);
                        atta.setAttachmentPath(url);
                        caseAttachmentMapper.insert(atta);
                    }
                }
            }
        } catch (Exception e) {
            log.error("修改案件失败：" + e.getMessage());
            throw new BusinessException("修改案件失败");
        }

        return serviceResponse;

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

    @Override
    @Transactional(readOnly = true)
    public List<CaseParty> getCasePartyByCaseId(String caseId) {
        try {
            Object[] args = {caseId};
            return (List<CaseParty>) databaseDialectBean.callMapperByDialect(
                    caseBasicMapper, "getCasePartyByCaseId", args);
        } catch (Exception e) {
            log.error("查询案件嫌疑人信息失败：" + e.getMessage());
            throw new BusinessException("查询案件嫌疑人信息失败");
        }

    }

    @Override
    @Transactional
    public CaseBasic findByPk(String caseId) {
        try {
            return caseBasicMapper.findByPk(caseId);
        } catch (Exception e) {
            log.error("查询案件信息失败：" + e.getMessage());
            throw new BusinessException("查询案件信息失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int countBycaseNo(Map<String, Object> map) {
        try {
            return caseBasicMapper.countBycaseNo(map);
        } catch (Exception e) {
            log.error("查询案件信息失败：" + e.getMessage());
            throw new BusinessException("查询案件信息失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CaseBasic findByCaseNoandOrg(Map<String, Object> map) {
        try {
            return caseBasicMapper.findByCaseNoandOrg(map);
        } catch (Exception e) {
            log.error("查询案件信息失败：" + e.getMessage());
            throw new BusinessException("查询案件信息失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CaseBasic> findByLikeCaseNo(Map<String, Object> map) {
        try {
            // 用findByLikeCaseNo方法以map条件查询前10条案件信息。
            return systemDAO.find(CaseBasic.class, map, 0, 10, "findByLikeCaseNo");
        } catch (Exception e) {
            log.error("查询案件信息失败：" + e.getMessage());
            throw new BusinessException("查询案件信息失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationHelper<CaseXianyiren> find(CaseXianyiren caseXianyiren,
                                                String page, Map<String, Object> paramMap) {
        try {
            return systemDAO.find(caseXianyiren, paramMap, page,
                    "getStateCount", "getStateList");
        } catch (Exception e) {
            log.error("查询案件嫌疑人信息失败：" + e.getMessage());
            throw new BusinessException("查询案件嫌疑人信息失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CaseCompany> getCaseCompanyByCaseId(String caseId) {
        try {
            Object[] args = {caseId};
            return (List<CaseCompany>) databaseDialectBean.callMapperByDialect(
                    caseBasicMapper, "getCaseCompanyByCaseId", args);
        } catch (Exception e) {
            log.error("查询案件企业信息失败：" + e.getMessage());
            throw new BusinessException("查询案件企业信息失败");
        }
    }

    public PaginationHelper<CaseBasic> getDaibuList(CaseBasic caseBasic, String page,
                                                    Map<String, Object> paramMap) {
        try {
            return systemDAO.find(caseBasic, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuCaseList");
        } catch (Exception e) {
            log.error("查询案件失败：" + e.getMessage());
            throw new BusinessException("查询案件失败");
        }
    }

    @Override
    public PaginationHelper<CaseXianyiren> getDaibuPartyList(
            CaseXianyiren caseXianyiren, String page,
            Map<String, Object> paramMap) {
        try {
            return systemDAO.find(caseXianyiren, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseXianyirenMapper.getDaibuPartyCount",
                    "com.ksource.liangfa.mapper.CaseXianyirenMapper.getDaibuPartyList");
        } catch (Exception e) {
            log.error("查询案件失败：" + e.getMessage());
            throw new BusinessException("查询案件失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationHelper<CaseBasic> searchCaseAndWorkflow(CaseBasic case1,
                                                             String page, Map<String, Object> paramMap) {
        try {
            return systemDAO.find(case1, paramMap, page,
                    "searchCaseAndWorkflowCount", "searchCaseAndWorkflow");
        } catch (Exception e) {
            log.error("查询案件失败：" + e.getMessage());
            throw new BusinessException("查询案件失败");
        }
    }

    @Override
    @Transactional
    @LogBusiness(operation = "删除行政违法案件", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = CaseBasicMapper.class)
    public boolean deleteCaseAndWorkflow(String caseIdTemp,Map<String, Object> paramMap) {

        boolean isDeleted = true;
        String caseId = (String) paramMap.get("caseId");
        String procKey = (String) paramMap.get("procKey");
        CasePartyExample casePartyExample = new CasePartyExample();
        casePartyExample.createCriteria().andCaseIdEqualTo(caseId);
        CaseCompanyExample caseCompanyExample = new CaseCompanyExample();
        caseCompanyExample.createCriteria().andCaseIdEqualTo(caseId);
        CaseXianyirenExample caseXianyirenExample = new CaseXianyirenExample();
        caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId);
        CaseAttachmentExample caseAttaExample = new CaseAttachmentExample();
        List<CaseConsultation> caseConsultationList = null;
        List<CaseConsultationContent> caseConsultationContentList = new ArrayList<CaseConsultationContent>();
        List<CaseReply> caseReplyList = null;

        try {
            CaseBasic case1 = caseBasicMapper.selectByPK(caseId);
            caseAttaExample.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(case1.getProcKey());
            List<CaseAttachment> caseAttachmentList = caseAttachmentMapper.selectByExample(caseAttaExample);
            // 删除案件（与案件相关的流程表，状态表和扩展表）
            caseBasicMapper.deleteByPrimaryKey(caseId);
            caseProcessMapper.deleteByPrimaryKey(caseId);
            caseStateMapper.deleteByPrimaryKey(caseId);
            //删除附件
            caseAttachmentMapper.deleteByExample(caseAttaExample);
            // 删除案件相关当事人
            casePartyMapper.deleteByExample(casePartyExample);
            // 删除案件涉案企业
            caseCompanyMapper.deleteByExample(caseCompanyExample);
            // 删除涉案嫌疑人
            caseXianyirenMapper.deleteByExample(caseXianyirenExample);
            //删除 移送行政机关信息
            TakeoffAdministrativeOrganExample takeoffAdministrativeOrganExample = new TakeoffAdministrativeOrganExample();
            takeoffAdministrativeOrganExample.createCriteria().andTakeoffCaseidEqualTo(caseId);
            takeoffAdministrativeOrganMapper.deleteByExample(takeoffAdministrativeOrganExample);
            //删除 罪名信息 (移送公安处，嫌疑人罪名)
            CaseAccuseExample caseAccuseExample = new CaseAccuseExample();
            caseAccuseExample.createCriteria().andCaseIdEqualTo(caseId);
            caseAccuseMapper.deleteByExample(caseAccuseExample);
            XianyirenAccuseExample xianyirenAccuseExample = new XianyirenAccuseExample();
            xianyirenAccuseExample.createCriteria().andCaseIdEqualTo(caseId);
            xianyirenAccuseMapper.deleteByExample(xianyirenAccuseExample);
            //删除　案件咨询
            if(StringUtils.isNotBlank(case1.getCaseNo())){
                CaseConsultationExample caseConsultationExample = new CaseConsultationExample();
                caseConsultationExample.createCriteria().andCaseNoEqualTo(case1.getCaseNo());
                caseConsultationList = caseConsultationMapper.selectByExample(caseConsultationExample);
                CaseConsultationContentExample caseConsultationContentExample = new CaseConsultationContentExample();
                for (CaseConsultation caseConsultation : caseConsultationList) {
                    caseConsultationContentExample.createCriteria().andCaseConsultationIdEqualTo(caseConsultation.getId());
                    List<CaseConsultationContent> temp = caseConsultationContentMapper.selectByExample(caseConsultationContentExample);
                    if (temp != null) {
                        caseConsultationContentList.addAll(temp);
                        caseConsultationContentMapper.deleteByExample(caseConsultationContentExample);
                    }
                    caseConsultationContentExample.clear();

                }
                caseConsultationMapper.deleteByExample(caseConsultationExample);       
                //删除案件咨询及案件咨询内容附件
                for (CaseConsultation caseConsultation : caseConsultationList) {
                    FileUtil.deleteFileInDisk(caseConsultation.getAttachmentPath());
                }
                for (CaseConsultationContent caseConsultationContent : caseConsultationContentList) {
                    FileUtil.deleteFileInDisk(caseConsultationContent.getAttachmentPath());
                }
            }
            //删除 案件批复
            CaseReplyExample caseReplyExample = new CaseReplyExample();
            caseReplyExample.createCriteria().andCaseIdEqualTo(caseId);
            caseReplyList = caseReplyMapper.selectByExample(caseReplyExample);
            caseReplyMapper.deleteByExample(caseReplyExample);
            //删除　案件结案通知
            CaseJieanNoticeExample caseJieanNoticeExample = new CaseJieanNoticeExample();
            caseJieanNoticeExample.createCriteria().andCaseIdEqualTo(caseId);
            caseJieanNoticeMapper.deleteByExample(caseJieanNoticeExample);


            //删除案件附件(删除案件的动作都应该放在后面)
            for (CaseAttachment caseAttachment : caseAttachmentList) {
                FileUtil.deleteFileInDisk(caseAttachment.getAttachmentPath());
                //删除预览文件
                if (caseAttachment.getSwfPath() != null) {
                    FileUtil.deleteFileInDisk(caseAttachment.getSwfPath());
                }
            }
            //删除 案件批复附件
            for (CaseReply caseReply : caseReplyList) {
                FileUtil.deleteFileInDisk(caseReply.getAttachmentPath());
            }
            //删除案件和专项活动关联关系
            deleteActivityCaseRelation(caseId);
            //删除案件和打侵打假关联关系
            dqdjCaseCategoryMapper.deleteByCaseId(caseId);
            //删除行政立案信息
            penaltyLianFormMapper.deleteByPrimaryKey(caseId);
            //删除行政处罚信息
            penaltyCaseFormMapper.deleteByPrimaryKey(caseId);
            //删除移送公安信息
            crimeCaseFormMapper.deleteByPrimaryKey(caseId);
            //删除建议移送信息
            suggestYisongFormMapper.deleteByPrimaryKey(caseId);
            //删除待办信息
            caseTodoMapper.deleteOldCaseByCaseId(caseId);
            //删除移送公安通知检察院信息
            CaseYisongNoticeExample caseYisongNoticeExample = new CaseYisongNoticeExample();
            caseYisongNoticeExample.createCriteria().andCaseIdEqualTo(caseId);
            caseYisongNoticeMapper.deleteByExample(caseYisongNoticeExample);
            
            //删除流程相关信息
            if(StringUtils.isNotBlank(case1.getProcInstId())){
                caseBasicMapper.deleteCaseAndWorkflow(case1.getProcInstId());
                FieldInstanceExample fieldInstanceExample = new FieldInstanceExample();
                fieldInstanceExample.createCriteria().andProcInstIdEqualTo(case1.getProcInstId());
                List<FieldInstance> fieldInstanceList = fieldInstanceMapper.selectByExample(fieldInstanceExample);
                //删除表单附件
                for (FieldInstance fieldInstance : fieldInstanceList) {
                    FileUtil.deleteFileInDisk(fieldInstance.getFieldPath());
                    //删除预览文件
                    if (fieldInstance.getSwfPath() != null) {
                        FileUtil.deleteFileInDisk(fieldInstance.getSwfPath());
                    }
                }
            }
        } catch (Exception e) {
            log.error("查询案件失败：" + e.getMessage());
            isDeleted = false;
            throw new BusinessException("查询案件失败");
        }
        return isDeleted;
    }

    @Override
    @Transactional
    public void delAtta(Long id) {
        try {
            CaseAttachment caseAttachment = caseAttachmentMapper.selectByPrimaryKey(id);
            FileUtil.deleteFileInDisk(caseAttachment.getAttachmentPath());
            FileUtil.deleteFileInDisk(caseAttachment.getSwfPath());
            caseAttachmentMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            log.error("修改案件附件失败：" + e.getMessage());
            throw new BusinessException("修改案件附件失败");
        }

    }

    @Override
    public CaseBasic findCaseById(String id) {
        return caseBasicMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CaseBasic> getCases(Map<String, Object> map) {
        return caseBasicMapper.getCases(map);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CaseBasic> getTimeoutWarnCases(User user) {
        // 2、TIMEOUT_WARN_REMINDER表和district表联合，根据userId判断当前用户的等级和权限
        List<TimeoutWarnReminder> timeoutWarnReminderKeys = timeoutWarnReminderMapper.getTimeoutWarnReminderByuserId(user.getUserId());
        // 3、如果当前用户的等级等于1，是市级权限，可查看所有的超时案件
        Integer distinction = Const.TIMEOUTWARNREMINDERKEY_CITY; // 默认为1
        List<CaseBasic> cases = new ArrayList<CaseBasic>(); // 定义一个所有节点的所有案件的集合
        Map<String, Object> paramMap = new HashMap<String, Object>(); // 定义一个参数Map对象
        if (timeoutWarnReminderKeys.size() > 0) {
            for (TimeoutWarnReminder timeoutWarnReminderKey : timeoutWarnReminderKeys) {
                if (timeoutWarnReminderKey.getjB() == 2) {
                    // 3.1、 如果当前用户的等级不等于1，是县级/区级权限，可查看对应的超时案件，否则是市级，可查看所有的超时案件
                    distinction = Const.TIMEOUTWARNREMINDERKEY_COUNTY;
                }
//				根据任务定义ID和流程定义ID查询出超时设置时间
                TimeoutWarnExample timeoutWarnExample = new TimeoutWarnExample();
                timeoutWarnExample.createCriteria().andProcDefIdEqualTo(timeoutWarnReminderKey.getProcDefId()).andTaskDefIdEqualTo(timeoutWarnReminderKey.getTaskDefId());
                List<TimeoutWarn> timeoutWarns = mybatisAutoMapperService.selectByExample(TimeoutWarnMapper.class, timeoutWarnExample, TimeoutWarn.class);
                for (TimeoutWarn timeoutWarn : timeoutWarns) {
               	 //设置超时时间时就查询超时案件，没有或设置为00:00:00时不查询
                   String due_date = null;
                   if(timeoutWarns.size() > 0) {
                   	due_date = timeoutWarn.getDueDate();
                   } else {
                   	continue;
                   }

                   //paramMap.put("distinction", distinction);
                   paramMap.put("task_def_id", timeoutWarnReminderKey.getTaskDefId());
                   paramMap.put("from_task_def_id", timeoutWarn.getFromTaskDefId());
                   paramMap.put("proc_def_id", timeoutWarnReminderKey.getProcDefId());
                   paramMap.put("district_code", timeoutWarnReminderKey.getDistrictCode());
                   paramMap.put("due_date", due_date);
                   paramMap.put("candidateGroup", user.getPostId());
                   paramMap.put("userID", user.getUserId());
                   List<CaseBasic> cases2 = (List<CaseBasic>) this.getCases(paramMap);
                   Date currentDate = new Date();
                   for(CaseBasic case1:cases2) {
                   	Date taskDate  = case1.getCreateTime();
                   	Date timeoutDate = DateUtil.addDate(taskDate,due_date,Const.TIMEOUT_DATE_FORMAT);
                   	long result = DateUtil.dateDiffTimeOut(timeoutDate, currentDate);
                   	result = - result;	//返回的是负数，现转换成正数
                   	StringBuffer sb = new StringBuffer();
                   	if((result/24 > 0) && (result%24 != 0)) {
                   		sb.append(result/24 + "天" + result%24 + "小时");
                   	}else if((result/24 > 0) && (result%24 == 0)) {
                   		sb.append(result/24 + "天");
                   	}else {
                   		sb.append(result%24 + "小时");
                   	}
                   	case1.setWarnTime(sb.toString());
                   }
                   if (cases2.size() > 0) {
                       cases.addAll(cases2);
                   }
				}
              
            }
        }
        return cases;
    }

    @Override
    public String getDistrictCode(String caseId) {
        return caseBasicMapper.getDistrictCode(caseId);
    }


    @Override
    @Transactional(readOnly = true)
    @LogBusiness(operation="查询涉嫌犯罪案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=CaseBasicMapper.class,target_domain_position=0)
    public PaginationHelper<CaseBasic> findYisongQuery(CaseBasic caCondition,
                                                       String page, Map<String, Object> param) {
        try {
            return systemDAO.find(caCondition, param, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.yisongQueryCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.yisongQueryList");
        } catch (Exception e) {
            log.error("移送涉嫌犯罪案件查询失败：" + e.getMessage());
            throw new BusinessException("移送涉嫌犯罪案件查询失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    @LogBusiness(operation="查询移送行政违法案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=CaseBasicMapper.class,target_domain_position=0)
    public PaginationHelper<CaseBasic> findYisongChufaQuery(CaseBasic caCondition,
                                                            String page, Map<String, Object> param) {
        try {
            return systemDAO.find(caCondition, param, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getYisongChufaQueryCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getYisongChufaQueryList");
        } catch (Exception e) {
            log.error("移送行政违法案件查询失败：" + e.getMessage());
            throw new BusinessException("移送行政违法案件查询失败");
        }
    }

    @Override
    public List<CaseBasic> selectJieanNoticeCase(String userId) {
        try {
            return caseBasicMapper.selectJieanNoticeCase(userId);
        } catch (Exception e) {
            log.error("移送行政违法案件查询失败：" + e.getMessage());
            throw new BusinessException("移送行政违法案件查询失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CaseBasic> caseSupervisionNotice(String userId) {
        try {
            return caseBasicMapper.caseSupervisionNotice(userId);
        } catch (Exception e) {
            log.error("当前用户监督案件查询失败！" + e.getMessage());
            throw new BusinessException("当前用户监督案件查询失败！");
        }
    }

    @Override
    @Transactional
    @LogBusiness(operation = "添加违法案件", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_mapper_class = CaseBasicMapper.class, target_domain_position = 0)
    public ServiceResponse addByTem(CaseBasic caseBasic,Map<String, Object> map,String industryType,HttpServletRequest request ) {
        ServiceResponse res = new ServiceResponse(true, "案件添加成功");
        CaseImport caseImport = (CaseImport)map.get("caseImport");
        CaseState caseState = (CaseState)map.get("caseState");
        CaseTodo caseTodo = (CaseTodo)map.get("caseTodo");
        /**
         * 1.迁移数据 把case_import数据迁移到case_basic,penalty_case_add表中
         * 2.删除case_import数据或修改状态,
         * 3.如果有附件则修改附件表中的case_id字段
         */
        try {
            // 获取案件ID
            String caseId = getCaseSequenceId();
            caseBasic.setCaseId(caseId);
            //案件录入类型 ----Excel导入
            caseBasic.setCaseState(String.valueOf(Const.STATE_INVALID));
            Integer orgCode=ThreadContext.getCurUser().getOrgId();
            Integer isTurnover = 0;
            caseBasic.setIsTurnover(isTurnover);

            //保存案件基本信息
            caseBasicMapper.insert(caseBasic);
            
            //保存案件状态
            caseState.setCaseId(caseId);
            caseStateMapper.insert(caseState);

            //修改状态(caseBasicMapper)
            CaseImportExample example= new CaseImportExample();
            example.createCriteria().andImportIdEqualTo(caseImport.getImportId());
            CaseImport caseImport1=new CaseImport();
            caseImport.setUploadFlag(Const.STATE_VALID);    //已经上传
            caseImportMapper.updateByExampleSelective(caseImport,example);
            
            //caseTodo表插入数据
            caseTodo.setTodoId(systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO));
            caseTodo.setCaseId(caseId);
            caseTodoMapper.insert(caseTodo);

            //保存案件步骤表case_step
            CaseStep caseStep = new CaseStep();
            caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
            caseStep.setStepName(getCaseState(Const.CHUFA_PROC_0));
            caseStep.setCaseId(caseId);
            caseStep.setCaseState(caseBasic.getCaseState());
            caseStep.setStartDate(caseBasic.getInputTime());
            caseStep.setEndDate(caseBasic.getInputTime());
            caseStep.setAssignPerson(caseBasic.getInputer());//这一步骤的办理人
            caseStep.setTargetOrgId(caseBasic.getInputUnit());//目标办理机构id，在这里也就是案件录入机构
            caseStep.setTaskActionName(getCaseState(Const.CHUFA_PROC_0));
            
            ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
            caseStep.setProcDefKey(procDeploy.getProcDefKey());
            caseStep.setProcDefId(procDeploy.getProcDefId());
            caseStep.setTaskType(Const.TASK_TYPE_ADD_CASE);
            //taskActionName  procDefKey  procDefId
            //caseStep.setTaskInstId(str);
            //caseStep.setTaskDefId(str);
            //caseStep.setProcInstId(str);
            caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
            //caseStep.setTaskActionId(1);
            caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
            caseStepMapper.insert(caseStep);
            
            //保存当事人信息和涉案企业信息，由于用模板导入的当事人和涉案企业都只有名字，而且是用逗号隔开的，所以这里只能存名字
            String casePartysString=caseBasic.getCasePartyJson();
            casePartysString = StringUtils.replace(casePartysString, "：", ":");
            if(StringUtils.isNotBlank(casePartysString)&&casePartysString.contains(":")){
            	casePartysString=StringUtils.replace(casePartysString, "，", ",");
            	String[] peopleNameArray=StringUtils.split(casePartysString,",");
                for (String peopleName : peopleNameArray) {
                	String name="";
                	String IdNo="";
                	if(peopleName.contains(":")){
                		 name=peopleName.substring(0,peopleName.indexOf(":"));
                		 IdNo = peopleName.substring(peopleName.indexOf(":")+1);
                	}
                	CaseParty caseParty=new CaseParty();
                	caseParty.setPartyId(Long.valueOf((systemDAO
                            .getSeqNextVal(Const.TABLE_CASE_PARTY))));
                	caseParty.setCaseId(caseBasic.getCaseId());
            		caseParty.setName(name);
            		caseParty.setIdsNo(IdNo);
                	casePartyMapper.insertSelective(caseParty);
    			}
			
            }
            String caseCompanysString=caseBasic.getCaseCompanyJson();
            StringUtils.replace(caseCompanysString, "：", ":");
            if (StringUtils.isNotBlank(caseCompanysString)&&caseCompanysString.contains(":")) {
            	caseCompanysString=StringUtils.replace(caseCompanysString, "，", ",");
            	caseCompanysString=StringUtils.replace(caseCompanysString, "：", ":");
				String[] companyNameArray=StringUtils.split(caseCompanysString,",");
				for (String companyName : companyNameArray) {
						String name="";
						String registractionNum="";
						if(companyName.contains(":")){
							 name = companyName.substring(0,companyName.indexOf(":"));
							 registractionNum = companyName.substring(companyName.indexOf(":")+1);
						}
						CaseCompany caseCompany=new CaseCompany();
						caseCompany.setId(systemDAO.getSeqNextVal(Const.TABLE_CASE_COMPANY));
						caseCompany.setCaseId(caseBasic.getCaseId());
						caseCompany.setName(name);
						caseCompany.setRegistractionNum(registractionNum);
						caseCompanyMapper.insertSelective(caseCompany);
				}
			}
            //疑似犯罪分析，罪名规则匹配案件基本信息并且保存案件罪名规则关联信息
            caseBasicMapper.updateByPrimaryKeySelective(accuseRuleService.matchCaseRule(caseBasic,industryType,request));  
        } catch (CaseDealException e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件添加失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件添加失败！");
        }
        return res;
    }

    /**
     * 打侵打假案件详细信息查询
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationHelper<CaseBasic> queryCaseList(
            String page, Map<String, Object> param) {
        try {
            return systemDAO.find(param, page, "com.ksource.liangfa.mapper.CaseBasicMapper.selectdqdjCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.selectdqdjCaseList");
        } catch (Exception e) {
            log.error("打侵打假案件(按组织机构)查询失败：" + e.getMessage(), e);
            throw new BusinessException("打侵打假案件(按组织机构)查询失败：" + e.getMessage());
        }
    }

    /**
     * 保存案件和专项活动关联关系
     */
    @Override
    @Transactional
    public Integer saveActivityCaseRelation(String caseId, Date inputTime, Integer orgCode) {
        int count = 0;
        //查找是否存在此案件的专项活动关联关系
        ActivityCaseExample activityCaseExample = new ActivityCaseExample();
        activityCaseExample.createCriteria().andCaseIdEqualTo(caseId);
        List<ActivityCase> activityCases = activityCaseMapper.selectByExample(activityCaseExample);
        //根据案件信息帅选出对应的专项活动信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("memberCode", orgCode);
        paramMap.put("inputTime", inputTime);
        List<SpecialActivity> specialActivitys = specialActivityMapper.getSpecialActivityList(paramMap);
        //保存关联关系
        if (activityCases == null || activityCases.size() == 0) {
            if (specialActivitys.size() > 0) {
                ActivityCase activityCase = new ActivityCase();
                activityCase.setCaseId(caseId);
                for (SpecialActivity s : specialActivitys) {
                    activityCase.setActivityId(s.getId());
                    count += activityCaseMapper.insert(activityCase);
                }
            }
        }
        return count;
    }

    /**
     * 删除案件和专项活动关联关系
     */
    @Override
    @Transactional
    public Integer deleteActivityCaseRelation(String caseId) {
        int count = 0;
        ActivityCaseExample activityCaseExample = new ActivityCaseExample();
        activityCaseExample.createCriteria().andCaseIdEqualTo(caseId);
        count = activityCaseMapper.deleteByExample(activityCaseExample);
        return count;
    }

    @Override
    public PaginationHelper<CaseBasic> queryCaseListByDistrict(String page,
                                                               Map<String, Object> param) {
        try {
            return systemDAO.find(param, page, "com.ksource.liangfa.mapper.CaseBasicMapper.selectdqdjCaseByDistrictCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.selectdqdjCaseByDistrictList");
        } catch (Exception e) {
            log.error("打侵打假案件(按行政区划)查询失败：" + e.getMessage(), e);
            throw new BusinessException("打侵打假案件(按行政区划)查询失败：" + e.getMessage());
        }
    }

    @Override
    @LogBusiness(operation="查询行政处罚案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=CaseBasicMapper.class,target_domain_position=0)
    public PaginationHelper<CaseBasic> findPenaltyCaseList(CaseBasic caseBasic,
                                                           String page, Map<String, Object> param) {
        try {
            return systemDAO.find(caseBasic, param, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getPenaltyCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getPenaltyCaseList");
        } catch (Exception e) {
            log.error("行政处罚案件查询失败：" + e.getMessage());
            throw new BusinessException("行政处罚案件查询失败");
        }
    }

    @Override
    @Transactional
    @LogBusiness(operation = "添加移送处罚案件", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT,target_domain_mapper_class = PenaltyReferCaseExtMapper.class, target_domain_position = 0)
    public ServiceResponse addYisongChufaCase(CaseBasic caseBasic,
                                              CaseState caseState, PenaltyReferCaseExt penaltyReferCaseExt, Map<String, Object> map,
                                              MultipartHttpServletRequest attachmentFile) {
        ServiceResponse res = new ServiceResponse(true, "案件添加成功");
        try {
            // 获取案件ID
            String caseId = getCaseSequenceId();
            caseBasic.setCaseId(caseId);
            caseBasic.setCaseState(String.valueOf(Const.STATE_INVALID));
            caseState.setCaseId(caseId);
            penaltyReferCaseExt.setCaseId(caseId);
            String procKey = caseBasic.getProcKey();
            String inputer = caseBasic.getInputer();

            //保存案件符件
            MultipartFile penaltyReferFile = attachmentFile.getFile("penaltyReferFile");
            String fileName = penaltyReferFile.getOriginalFilename();
            if (!StringUtils.isBlank(fileName)) {
                Long fileId = addCaseAtta(caseId, procKey, penaltyReferFile);
                penaltyReferCaseExt.setFileId(fileId);
            }

            //保存案件基本信息
            caseBasicMapper.insert(caseBasic);
            //保存案件状态
            caseStateMapper.insert(caseState);
            //保存移送案件的扩展信息
            penaltyReferCaseExtMapper.insert(penaltyReferCaseExt);

            //保存案件流程信息并启动流程
            ProcessFactory.createProcessUtil(caseBasic).saveBusinessEntityAndstartProcessInstanceByProcDefKey(
                    procKey, inputer, map);
        } catch (CaseDealException e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件添加失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件添加失败！");
        }
        return res;
    }


    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> findCrimeCaseById(String caseId) {
        Map<String, Object> map = new HashMap<String, Object>();
        CaseBasic caseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
        String acceptUnitName = organiseMapper.getSpliceOrgName(caseBasic.getAcceptUnit());
        caseBasic.setAcceptUnitName(acceptUnitName);
        CrimeCaseExt crimeCaseExt = crimeCaseExtMapper.selectByPrimaryKey(caseId);
        CaseAttachmentExample example = new CaseAttachmentExample();
        example.createCriteria().andCaseIdEqualTo(caseId);
        List<CaseAttachment> attaList = caseAttachmentMapper.selectByExample(example);
        map.put("attaList", attaList);
        map.put("caseBasic", caseBasic);
        map.put("crimeCaseExt", crimeCaseExt);
        return map;
    }

    @Override
    public PaginationHelper<CaseBasic> findChufaOrCrimeCaseList(
            CaseBasic case1, String page, Map<String, Object> param) {
        try {
            return systemDAO.find(case1, param, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getChufaOrCrimeCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getChufaOrCrimeList");
        } catch (Exception e) {
            log.error("案件查询失败：" + e.getMessage());
            throw new BusinessException("案件查询失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> findPenaltyReferCaseById(String caseId) {
        Map<String, Object> caseMap = new HashMap<String, Object>();
        CaseBasic caseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
        caseMap.put("caseBasic", caseBasic);
        PenaltyReferCaseExt penaltyReferCaseExt = penaltyReferCaseExtMapper.selectByPrimaryKey(caseId);
        caseMap.put("penaltyReferCaseExt", penaltyReferCaseExt);
        if (penaltyReferCaseExt.getFileId() != null) {
            CaseAttachment caseAttachment = caseAttachmentMapper.selectByPrimaryKey(Long.valueOf(penaltyReferCaseExt.getFileId()));
            caseMap.put("caseAttachment", caseAttachment);
        }
        return caseMap;
    }

    @Override
    @Transactional
    @LogBusiness(operation="修改移送行政违法案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_UPDATE,target_domain_mapper_class=PenaltyReferCaseExtMapper.class,target_domain_position=0)
    public ServiceResponse updatepenaltyReferCase(CaseBasic caseBasic, PenaltyReferCaseExt penaltyReferCaseExt, MultipartHttpServletRequest caseAttachmentFile) {
        ServiceResponse serviceResponse = new ServiceResponse(true, "");
        try {
            //保存案件附件
            MultipartFile penaltyFile = caseAttachmentFile.getFile("penaltyReferFile");
            if(penaltyFile != null) {
            	CaseAttachment caseAttachment = null;
            	String fileName = penaltyFile.getOriginalFilename();
            	if (StringUtils.isBlank(fileName)) {
            		caseAttachmentMapper.deleteByPrimaryKey(Long.valueOf(penaltyReferCaseExt.getFileId()));
            		//修改了updateByPrimaryKeySelective语句，再次自动生成时需要注意
            		penaltyReferCaseExt.setFileId(null);
            	}
            	if (!StringUtils.isBlank(fileName)) {
            		caseAttachment = caseAttachmentMapper.selectByPrimaryKey(Long.valueOf(penaltyReferCaseExt.getFileId()));
            		if (caseAttachment != null) {
            			caseAttachmentMapper.deleteByPrimaryKey(Long.valueOf(penaltyReferCaseExt.getFileId()));
            		}
            		Long fileId = addCaseAtta(caseBasic.getCaseId(), caseBasic.getProcKey(),
            				penaltyFile);
            		penaltyReferCaseExt.setFileId(fileId);
            	}
            }
            penaltyReferCaseExtMapper.updateByPrimaryKeySelective(penaltyReferCaseExt);

        } catch (Exception e) {
            log.error("修改案件失败：" + e.getMessage());
            throw new BusinessException("修改案件失败");
        }
        return serviceResponse;
    }

    @Override
    public PaginationHelper<CaseBasic> caseRecordNotice(Map<String, Object> paramMap, String page) {
        try {
            return systemDAO.find(paramMap, page, "com.ksource.liangfa.mapper.CaseBasicMapper.getCaseRecordCount", "com.ksource.liangfa.mapper.CaseBasicMapper.getCaseRecordList");
        } catch (Exception e) {
            log.error("当前用户报备案件查询失败！" + e.getMessage());
            throw new BusinessException("当前用户报备案件查询失败！");
        }
    }

    /**
     * 根据案件类型查询案件详细信息(行政处罚案件统计报表和涉嫌犯罪案件统计报表用)
     */
    @Override
    public PaginationHelper<CaseBasic> findAllStateCaseList(CaseBasic case1,
                                                            String page, Map<String, Object> param) {
        try {
            return systemDAO.find(case1, param, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getAllStateCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getAllStateList");
        } catch (Exception e) {
            log.error("案件查询失败：" + e.getMessage());
            throw new BusinessException("案件查询失败");
        }
    }

    @Override
    public PaginationHelper<CaseBasic> findStateCaseListByOrg(CaseBasic case1,
                                                              String page, Map<String, Object> param) {
        try {
            return systemDAO.find(case1, param, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getStateCountByOrg",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getStateListByOrg");
        } catch (Exception e) {
            log.error("案件查询失败：" + e.getMessage());
            throw new BusinessException("案件查询失败");
        }
    }

    @Override
    public PaginationHelper<CaseBasic> findCrimeCaseListByOrg(
            CaseBasic case1, String page, Map<String, Object> param) {
        try {
            return systemDAO.find(case1, param, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getCrimeCountByOrg",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getCrimeListByOrg");
        } catch (Exception e) {
            log.error("案件查询失败：" + e.getMessage());
            throw new BusinessException("案件查询失败");
        }
    }

    @Override
    public PaginationHelper<CaseXianyiren> findXianyirenByOrg(
            CaseXianyiren caseXianyiren, String page,
            Map<String, Object> paramMap) {
        try {
            return systemDAO.find(caseXianyiren, paramMap, page,
                    "getStateCountByOrg", "getStateListByOrg");
        } catch (Exception e) {
            log.error("查询案件嫌疑人信息失败：" + e.getMessage());
            throw new BusinessException("查询案件嫌疑人信息失败");
        }
    }
    
    @Override
	public PaginationHelper<CaseBasic> findNoPenaltyTransferCaseList(
			CaseBasic caseBasic, String page, Map<String, Object> param) {
		try {
            return systemDAO.find(caseBasic, param, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getNoPenaltyTransferCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getNoPenaltyTransferCaseList");
        } catch (Exception e) {
            log.error("未处罚直接移送案件查询失败：" + e.getMessage());
            throw new BusinessException("未处罚直接移送案件查询失败");
        }
	}

    /**
     * 自定义查询
     */
	@Override
	public PaginationHelper<CaseBasic> generalQuery(CaseBasic caseBasic,
			String page, Map<String, Object> paramMap) {
		try {
            return systemDAO.find(caseBasic, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getGeneralCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getGeneralCaseList");
        } catch (Exception e) {
            log.error("自定义案件查询失败：" + e.getMessage());
            throw new BusinessException("自定义案件查询失败");
        }
	}
	
	@Override
	@LogBusiness(operation="查询疑似涉嫌犯罪案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=CaseBasicMapper.class,target_domain_position=0)
	public PaginationHelper<CaseBasic> findYisiCaseList(CaseBasic caseBasic,
            String page, Map<String, Object> param) {
		   try {
	            return systemDAO.find(caseBasic, param, page,
	                    "com.ksource.liangfa.mapper.CaseBasicMapper.getyisiCaseCount",
	                    "com.ksource.liangfa.mapper.CaseBasicMapper.getyisiCaseList");
	        } catch (Exception e) {
	            log.error("疑似涉嫌犯罪案件查询失败：" + e.getMessage());
	            throw new BusinessException("疑似涉嫌犯罪案件查询失败");
	        }
	}

	@Override
	@LogBusiness(operation="查询立案监督案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=CaseBasicMapper.class,target_domain_position=0)
	public PaginationHelper<CaseBasic> findlianCaseList(CaseBasic caseBasic, String page, Map<String, Object> param) {
		 try {
	            return systemDAO.find(caseBasic, param, page,
	                    "com.ksource.liangfa.mapper.CaseBasicMapper.getlianCaseCount",
	                    "com.ksource.liangfa.mapper.CaseBasicMapper.getlianCaseList");
	        } catch (Exception e) {
	            log.error("立案监督案件查询失败：" + e.getMessage());
	            throw new BusinessException("立案监督案件查询失败");
	        }
	}

	@Override
	@LogBusiness(operation="比对分析案件查询",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=CaseBasicMapper.class,target_domain_position=0)
	public PaginationHelper<CaseBasic> findYisiFaCaseList(CaseBasic caseBasic, String page, Map<String, Object> param) {
		 try {
	            return systemDAO.find(caseBasic, param, page,
	                    "com.ksource.liangfa.mapper.CaseBasicMapper.getyisiFaCaseCount",
	                    "com.ksource.liangfa.mapper.CaseBasicMapper.getyisiFaCaseList");
	        } catch (Exception e) {
	            log.error("比对分析案件查询失败：" + e.getMessage());
	            throw new BusinessException("比对分析案件查询失败");
	        }
	}
	
	@Override
	public PaginationHelper<CaseBasic> getDaibuAndQisuCaseList(CaseBasic case1,
			String page, Map<String, Object> paramMap) {
		try {
            return systemDAO.find(case1, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuAndQisuCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuAndQisuCaseList");
        } catch (Exception e) {
            log.error("提请逮捕或移送起诉案件查询失败：" + e.getMessage());
            throw new BusinessException("提请逮捕或移送起诉案件查询失败");
        }
	}

	@Override
	public PaginationHelper<CaseBasic> getDaibuAndQisuCaseListByOrg(
			CaseBasic case1, String page, Map<String, Object> paramMap) {
		try {
            return systemDAO.find(case1, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuAndQisuCaseCountByOrg",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuAndQisuCaseListByOrg");
        } catch (Exception e) {
            log.error("提请逮捕或移送起诉案件查询失败：" + e.getMessage());
            throw new BusinessException("提请逮捕或移送起诉案件查询失败");
        }
	}

	@Override
	public PaginationHelper<CaseBasic> getDaibuListByOrg(CaseBasic case1,
			String page, Map<String, Object> paramMap) {
		try {
            return systemDAO.find(case1, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuCaseCountByOrg",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuCaseListByOrg");
        } catch (Exception e) {
            log.error("提请逮捕或移送起诉案件查询失败：" + e.getMessage());
            throw new BusinessException("提请逮捕或移送起诉案件查询失败");
        }
	}

	@Override
	public PaginationHelper<CaseBasic> findCaseAccuseList(CaseBasic case1,
			String page, Map<String, Object> paramMap) {
		try {
            return systemDAO.find(case1, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.findCaseAccuseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.findCaseAccuseList");
        } catch (Exception e) {
            log.error("提请逮捕或移送起诉案件查询失败：" + e.getMessage());
            throw new BusinessException("提请逮捕或移送起诉案件查询失败");
        }
	}

	@Override
	public PaginationHelper<CaseBasic> getDaibuListByIndustry(
			CaseBasic caseBasic, String page, Map<String, Object> paramMap) {
		 try {
	            return systemDAO.find(caseBasic, paramMap, page,
	                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuCaseCountByIndustry",
	                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuCaseListByIndustry");
	        } catch (Exception e) {
	            log.error("查询案件失败：" + e.getMessage());
	            throw new BusinessException("查询案件失败");
	        }
	}

	@Override
	public PaginationHelper<CaseBasic> getDaibuAndQisuCaseListByIndustry(
			CaseBasic case1, String page, Map<String, Object> paramMap) {
		try {
            return systemDAO.find(case1, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuAndQisuCaseCountByIndustry",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getDaibuAndQisuCaseListByIndustry");
        } catch (Exception e) {
            log.error("提请逮捕或移送起诉案件查询失败：" + e.getMessage());
            throw new BusinessException("提请逮捕或移送起诉案件查询失败");
        }
	}

	@Override
	public PaginationHelper<CaseBasic> findByStateByIndustry(
			CaseBasic caseBasic, String page, Map<String, Object> paramMap) {
		try {
            return systemDAO.find(caseBasic, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getStateCountByIndustry",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getStateListByIndustry");
        } catch (Exception e) {
            log.error("查询案件失败：" + e.getMessage());
            throw new BusinessException("查询案件失败");
        }
	}

	@Override
	public Date queryMinTimeByCaseState(int caseState) {
		return caseStateMapper.queryMinTimeByCaseState(caseState);
	}

	@Override
	public int queryCountByState(int caseStateChayue) {
		return caseStateMapper.queryCountByState(caseStateChayue);
	}
	
	@Override
	public List<CaseBasic> querySuspectedCriminalCase(CaseBasic caseBasic) {
		return caseBasicMapper.querySuspectedCriminalCase(caseBasic);
	}

	@Override
	public PaginationHelper<CaseBasic> getjiandulianCaseList(
			CaseBasic caseBasic, String page, Map<String, Object> paramMap) {
		try {
            return systemDAO.find(caseBasic, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getjiandulianCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getjiandulianCaseList");
        } catch (Exception e) {
            log.error("查询监督立案案件失败：" + e.getMessage());
            throw new BusinessException("查询监督立案案件失败");
        }
	}

	@Override
	public List<CaseBasic> queryfilingSupervisionCase(Map<String, Object> paramMap,Map<String, Object> filterMap) {
		if(filterMap!=null && !filterMap.isEmpty()){
			paramMap.putAll(filterMap);
		}
		return caseBasicMapper.queryfilingSupervisionCase(paramMap);
	}
	
	@Override
	public CaseBasic getCaseByTaskId(Map<String, Object> paramMap) {
		return caseBasicMapper.getCaseByTaskId(paramMap);
	}

	@Override
	public PaginationHelper<CaseBasic> yisongPoliceNotAcceptQueryList(
			CaseBasic caseBasic, String page, Map<String, Object> paramMap) {
		try {
            return systemDAO.find(caseBasic, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.yisongPoliceNotAcceptQueryCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.yisongPoliceNotAcceptQueryList");
        } catch (Exception e) {
            log.error("查询公安未受理移送涉嫌犯罪案件失败：" + e.getMessage());
            throw new BusinessException("查询公安未受理移送涉嫌犯罪案件失败");
        }
	}


	@Override
	public Date queryMinCaseInputTime(CaseBasic caseBasic) {
		return caseBasicMapper.queryMinCaseInputTime(caseBasic);
	}

	@Override
	public int queryCaseCount(CaseBasic caseBasic) {
		return caseBasicMapper.queryCaseCount(caseBasic);
	}

	@Override
	public Integer getyisiFaCaseCount(Map<String, Object> paramMap,
			Map<String, Object> filterMap) {
		if(filterMap != null){
			paramMap.putAll(filterMap);
		}
		return caseBasicMapper.getyisiFaCaseCount(paramMap);
	}

	@Override
	public List<CaseBasic> getyisiFaCaseList(Map<String, Object> paramMap,
			Map<String, Object> filterMap) {
		if(filterMap != null){
			paramMap.putAll(filterMap);
		}
		return caseBasicMapper.getyisiFaCaseList(paramMap);
	}

	@Override
	public Integer queryfilingSupervisionCaseCount(Map<String, Object> paramMap,Map<String, Object> filterMap) {
		if(filterMap != null){
			paramMap.putAll(filterMap);
		}
		return caseBasicMapper.queryfilingSupervisionCaseCount(paramMap);
	}

	@Override
	public PaginationHelper<CaseBasic> filterDrill(CaseBasic caseBasic,
			Integer num, String page,Map<String, Object> paramMap) {
		try {
			paramMap.put("filterNum", num);
            return systemDAO.find(caseBasic, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.filterDrillCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.filterDrillList");
        } catch (Exception e) {
            log.error("查询公安未受理移送涉嫌犯罪案件失败：" + e.getMessage());
            throw new BusinessException("查询公安未受理移送涉嫌犯罪案件失败");
        }
	}

	@Override
	public List<Map<String, Object>> queryCaseInputInfoForIndustry(
			Map<String, Object> paramMap) {
		return caseBasicMapper.queryCaseInputInfoForIndustry(paramMap);
	}

	@Override
	public List<Map<String, Object>> queryCrimeCaseInfoForIndustry(
			Map<String, Object> paramMap) {
		return caseBasicMapper.queryCrimeCaseInfoForIndustry(paramMap);
	}

	@Override
	public Map<String, Object> queryAllCrimeCaseDealStatis(
			Map<String, Object> paramMap) {
		return caseBasicMapper.queryAllCrimeCaseDealStatis(paramMap);
	}

	@Override
	public PaginationHelper<CaseBasic> getYisongPoliceNotAcceptList(
			CaseBasic caseBasic, Map<String, Object> paramMap, String page) {
		try {
            return systemDAO.find(caseBasic, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getYisongPoliceNotAcceptCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getYisongPoliceNotAcceptList");
        } catch (Exception e) {
            log.error("滞留案件查询失败：" + e.getMessage());
            throw new BusinessException("滞留案件查询失败");
        }
	}

	@Override
	public List<Map<String, Object>> queryXzcfInputRatioPerRegion(Map paramMap) {
		return caseBasicMapper.queryXzcfInputRatioPerRegion(paramMap);
	}

	@Override
	public List<Map<String, Object>> queryXzcfInputRatioPerIndustry(
			Map<String, Object> paramMap) {
		return caseBasicMapper.queryXzcfInputRatioPerIndustry(paramMap);
	}

	@Override
	public List<Map<String, Object>> queryCrimeCaseDealStatis(
			Map<String, Object> paramMap) {
		return caseBasicMapper.queryCrimeCaseDealStatis(paramMap);
	}

	@Override
	public List<Map<String, Object>> queryCrimeCaseDealStatisByIndustry(
			Map<String, Object> paramMap) {
		return caseBasicMapper.queryCrimeCaseDealStatisByIndustry(paramMap);
	}

	@Override
	public List<Map<String, Object>> queryTop10Accuse(
			Map<String, Object> paramMap) {
		return caseBasicMapper.queryTop10Accuse(paramMap);
	}

	@Override
	public int queryCaseCountForZHFX(Map<String, Object> paramMap) {
		return caseBasicMapper.queryCaseCountForZHFX(paramMap);
	}

	@Override
	public int queryCrimeCaseCountForZHFX(Map<String, Object> paramMap) {
		return caseBasicMapper.queryCrimeCaseCountForZHFX(paramMap);
	}

	@Override
	public Map<String, Object> queryJGCLCaseCountForZHFX(
			Map<String, Object> paramMap) {
		return caseBasicMapper.queryJGCLCaseCountForZHFX(paramMap);
	}

	@Override
	public Integer queryDSJFXCaseCountForZHFX(Map<String, Object> paramMap,
			Map<String, Object> filterMap) {
		if(filterMap != null){
			paramMap.putAll(filterMap);
		}
		return caseBasicMapper.queryDSJFXCaseCountForZHFX(paramMap);
	}

	@Override
	public Integer queryYisongCaseCount(CaseBasic caseBasic) {
		return caseBasicMapper.queryYisongCaseCount(caseBasic);
	}

	@Override
	public Integer queryPoliceNotAcceptCaseCount(CaseBasic caseBasic) {
		return caseBasicMapper.queryPoliceNotAcceptCaseCount(caseBasic);
	}

	@Override
	public List<CaseBasic> queryPoliceNotAcceptCaseList(
			CaseBasic caseBasic) {
		return caseBasicMapper.queryPoliceNotAcceptCaseList(caseBasic);
	}

	@Override
	public int queryYisongCount(Map<String, Object> paramMap) {
		return caseBasicMapper.queryYisongCount(paramMap);
	}

	@Override
	public PaginationHelper<CaseBasic> queryfilingSupervisionCase(String page,
			Map<String, Object> paramMap,CaseFilter caseFilter) {
		PaginationHelper<CaseBasic> caseList = systemDAO.find(paramMap, page,
	            "com.ksource.liangfa.mapper.CaseBasicMapper.queryfilingSupervisionCount",
	            "com.ksource.liangfa.mapper.CaseBasicMapper.queryfilingSupervisionList");
		List<CaseBasic> caseBasicList = caseList.getList();
      	 boolean yesOrNo=false;//判断是否筛选条件，至少有1个为true
		if(!caseBasicList.isEmpty()){
	    	//查询出此案件是经过哪个筛选条件进行筛选出来的
			for(CaseBasic cb:caseBasicList){
		    	String filterResult="";
		    	String str="";
		    	boolean temp=false;
				 //录入时间
	           	 Date inputDate=cb.getInputTime();
	       		if(caseFilter.getMinCaseInputTime()!=null || caseFilter.getMaxCaseInputTime()!=null){
	       			Date minDate=caseFilter.getMinCaseInputTime();
	       			Date maxDate=caseFilter.getMaxCaseInputTime();
	       			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	       			if(minDate!=null && maxDate!=null){
	       				if((inputDate.after(minDate) || inputDate.equals(minDate)) && (inputDate.before(maxDate) || inputDate.equals(maxDate))){
		       				temp=true;
		       				yesOrNo=true;
		       				str="在"+sdf.format(minDate)+"到"+sdf.format(maxDate)+"范围内";
		       			}
	       			}
	       			if(minDate!=null && maxDate==null){
	       				if(inputDate.after(minDate) || inputDate.equals(minDate)){
		       				temp=true;
		       				yesOrNo=true;
		       				str="从"+sdf.format(minDate)+"开始";
		       			}
	       			}
	       			if(maxDate!=null && minDate==null){
	       				if(inputDate.before(maxDate) || inputDate.equals(maxDate)){
		       				temp=true;
		       				yesOrNo=true;
		       				str="截止到"+sdf.format(maxDate);
		       			}
	       			}
	       			if(temp==true){
	           	    	filterResult+="录入时间<b>"+str+"</b><br/>";
	           	    }
	       		}
	           	 //涉案金额
       			temp=false;
	           	 double money=cb.getAmountInvolved();
	           	 BigDecimal dataMoney = new BigDecimal(money);
	           	 if(caseFilter.getMinAmountInvolved()!=null || caseFilter.getMaxAmountInvolved()!=null){
	           	 	double minMoney = 0;
	           	 	double maxMoney = 0;
	           	 	if(caseFilter.getMinAmountInvolved()!=null){
	           	 		minMoney = caseFilter.getMinAmountInvolved();
	           	 	}
	           	 	if(caseFilter.getMaxAmountInvolved()!=null){
	           	 		maxMoney = caseFilter.getMaxAmountInvolved();
	           	 	}
	           	    BigDecimal dataMinMoney = new BigDecimal(minMoney);
	           	    BigDecimal dataMaxMoney = new BigDecimal(maxMoney);
	           	    if(dataMinMoney!=null && dataMaxMoney!=null && (dataMinMoney.compareTo(BigDecimal.ZERO))!=0 && (dataMaxMoney.compareTo(BigDecimal.ZERO))!=0){
	           	    	if((dataMoney.compareTo(dataMinMoney)>=0) && (dataMoney.compareTo(dataMaxMoney)<=0)){
		           	    	temp=true;
		           	    	yesOrNo=true;
		           	    	str="在"+dataMinMoney.toString()+"元~"+dataMaxMoney.toString()+"元之内";
		           	    }
	           	    }
	           	    if(dataMinMoney!=null && (dataMaxMoney==null || dataMaxMoney.compareTo(BigDecimal.ZERO)==0)){
	           	    	if(dataMoney.compareTo(dataMinMoney)>=0){
		           	    	temp=true;
		           	    	yesOrNo=true;
		           	    	str="高于或等于"+dataMinMoney.toString()+"元";
		           	    }
	           	    }
	           	    if(dataMaxMoney!=null && (dataMinMoney==null || dataMinMoney.compareTo(BigDecimal.ZERO)==0)){
	           	    	if(dataMoney.compareTo(dataMaxMoney)<=0){
		           	    	temp=true;
		           	    	yesOrNo=true;
		           	    	str="低于或等于"+dataMaxMoney.toString()+"元";
		           	    }
	           	    }
	           	    
	           	    if(temp==true){
	           	    	filterResult+="涉案金额<b>"+str+"</b><br/>";
	           	    }
	           	 }
	       		
	       		//情节是否严重
   				if(caseFilter.getIsSeriousCase()!=null && cb.getIsSeriousCase()!=null 
   						&& cb.getIsSeriousCase().intValue() == caseFilter.getIsSeriousCase().intValue()){
   					switch(caseFilter.getIsSeriousCase().intValue()){
       					case 1:
     						filterResult+="案件情节严重<br/>";
       						yesOrNo=true;
       						break; 
       					case 0:
       						filterResult+="案件情节不严重<br/>";
       						yesOrNo=true;
       						break; 
       					default:
       						break;
   					}
       			}
	       		//是否经过集体讨论
   				if(caseFilter.getIsDiscussCase()!=null && cb.getIsDiscussCase()!=null 
   						&& cb.getIsDiscussCase().intValue() == caseFilter.getIsDiscussCase().intValue()){
   					switch(caseFilter.getIsDiscussCase().intValue()){
       					case 1:
       						filterResult+="已经过集体讨论<br/>";
       						yesOrNo=true;
       						break; 
       					case 0:
       						filterResult+="未经过集体讨论<br/>";
       						yesOrNo=true;
       						break; 
       					default:
       						break;
   					}
       			}
	       		
	       		//涉案金额达是否达到刑事立案标准80%
   				if(caseFilter.getIsBeyondEighty() != null && cb.getIsBeyondEighty() != null 
   						&& cb.getIsBeyondEighty().intValue() == caseFilter.getIsBeyondEighty()){
   					switch(caseFilter.getIsBeyondEighty().intValue()){
       					case 1:
       						filterResult += "涉案金额达已达到刑事立案标准80%以上<br/>";
       						yesOrNo = true;
       						break; 
       					case 0:
       						filterResult += "涉案金额达未达到刑事立案标准80%以上<br/>";
       						yesOrNo = true;
       						break; 
       					default:
       						break;
   					}
       			}
	       		//行政处罚次数(大于)
	       		if(caseFilter.getChufaTimes() != null && cb.getChufaTimes() != null
	       				&& cb.getChufaTimes()>caseFilter.getChufaTimes()){
		       					filterResult+="行政处罚次数"+(caseFilter.getChufaTimes().intValue()+1)+"次以上<br/>";
		       					yesOrNo=true;
	       		}
	       		//是否低于行政处罚规定的下限金额
   				if(caseFilter.getIsLowerLimitMoney() != null && cb.getIsLowerLimitMoney() != null 
   						&& cb.getIsLowerLimitMoney().intValue() == caseFilter.getIsLowerLimitMoney().intValue()){
   					switch(caseFilter.getIsLowerLimitMoney().intValue()){
       					case 1:
       						filterResult+="低于行政处罚规定的下限金额<br/>";
       						yesOrNo = true;
       						break; 
       					case 0:
       						filterResult+="不低于行政处罚规定的下限金额<br/>";
       						yesOrNo = true;
       						break; 
       					default:
       						break;
   					}
       			}
	       		//是否进行鉴定
   				if(caseFilter.getIsIdentify() != null && cb.getIsIdentify() != null 
   						&& cb.getIsIdentify().intValue() == caseFilter.getIsIdentify().intValue()){
   					switch(caseFilter.getIsLowerLimitMoney().intValue()){
       					case 1:
       						filterResult+="已进行鉴定<br/>";
       						yesOrNo = true;
       						break; 
       					case 0:
       						filterResult+="未进行鉴定<br/>";
       						yesOrNo = true;
       						break; 
       					default:
       						break;
   					}
       			}       				
           	 
           	 String filterCondition="符合的筛选条件如下：<br/>";
           	 if(yesOrNo==true){
           		filterResult=filterCondition+filterResult;
           		cb.setFilterResult(filterResult);
           	 }
			}
		}
		return caseList;
	}

	@Override
	public PaginationHelper<CaseBasic> queryCompletedCaseList(
			CaseBasic caseBasic, String page) {
		PaginationHelper<CaseBasic> caseList = systemDAO.find(caseBasic, null, page, "com.ksource.liangfa.mapper.CaseBasicMapper.queryCompletedCaseCount", "com.ksource.liangfa.mapper.CaseBasicMapper.queryCompletedCaseList");
		return caseList;
	}

	
	
	@Override
	@LogBusiness(operation="查询受理案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=CaseBasicMapper.class,target_domain_position=0)
    public PaginationHelper<CaseBasic> findAcceptCaseList(CaseBasic caseBasic,
                                                           String page, Map<String, Object> param) {
        try {
            return systemDAO.find(caseBasic, param, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getAcceptCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getAcceptCaseList");
        } catch (Exception e) {
            log.error("受理案件查询失败：" + e.getMessage());
            throw new BusinessException("受理案件查询失败");
        }
    }

	@Override
	@LogBusiness(operation="查询行政立案案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=CaseBasicMapper.class,target_domain_position=0)
	public PaginationHelper<CaseBasic> findXingzhengLianCaseList(
			CaseBasic caseBasic, String page, Map<String, Object> paramMap) {
		try{
			return systemDAO.find(caseBasic,paramMap,page,
					"com.ksource.liangfa.mapper.CaseBasicMapper.getXingzhengLianCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getXingzhengLianCaseList");
		}catch (Exception e) {
            log.error("行政立案案件查询失败：" + e.getMessage());
            throw new BusinessException("行政立案案件查询失败");
        }
	}

	@Override
	@LogBusiness(operation="案件办理查询行政立案案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=CaseBasicMapper.class,target_domain_position=0)
	public PaginationHelper<CaseBasic> findXingzhengLianQueryCaseList(
			CaseBasic caseBasic, String page) {
		try{
			return systemDAO.find(caseBasic,null,page,
					"com.ksource.liangfa.mapper.CaseBasicMapper.getXingzhengLianQueryCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getXingzhengLianQueryCaseList");
		}catch (Exception e) {
            log.error("行政立案案件查询失败：" + e.getMessage());
            throw new BusinessException("行政立案案件查询失败");
        }
	}
	
	@Override
	@LogBusiness(operation="案件办理查询行政处罚案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=CaseBasicMapper.class,target_domain_position=0)
	public PaginationHelper<CaseBasic> findXingzhengChufaCaseList(
			CaseBasic caseBasic, String page, Map<String, Object> paramMap) {
		try{
			return systemDAO.find(caseBasic,paramMap,page,
					"com.ksource.liangfa.mapper.CaseBasicMapper.getXingzhengChufaQueryCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getXingzhengChufaQueryCaseList");
		}catch (Exception e) {
            log.error("行政处罚案件查询失败：" + e.getMessage());
            throw new BusinessException("行政处罚案件查询失败");
        }
	}
	
	@Override
	public Map<String, Integer> queryWarningData(Map<String,Object> param) {
		return caseBasicMapper.queryWarningData(param);
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

	@Override
	public PaginationHelper<CaseBasic> querySuspectedCaseList(String page,
			Map<String, Object> paramMap) {
		try{
			return systemDAO.find(paramMap,page,
					"com.ksource.liangfa.mapper.CaseBasicMapper.getSuspectedCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getSuspectedCaseList");
		}catch (Exception e) {
            log.error("疑似犯罪案件筛查失败：" + e.getMessage());
            throw new BusinessException("疑似犯罪案件筛查失败");
        } 
	}

	@Override
	public PaginationHelper<CaseBasic> queryAmonutWarnCase(CaseBasic caseBasic,
			String page, Map<String, Object> paramMap) {
		try{
			return systemDAO.find(caseBasic,paramMap,page,
					"com.ksource.liangfa.mapper.CaseBasicMapper.getAmonutWarnCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getAmonutWarnCaseList");
		}catch (Exception e) {
            log.error("金额预警案件查询失败：" + e.getMessage());
            throw new BusinessException("金额预警案件查询失败");
        }
	}

	@Override
    @LogBusiness(operation="查询所有案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=CaseBasicMapper.class,target_domain_position=0)
    public PaginationHelper<CaseBasic> getAllCaseList(CaseBasic caseBasic,
                                                           String page, Map<String, Object> param) {
        try {
            return systemDAO.find(caseBasic, param, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getAllCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getAllCaseList");
        } catch (Exception e) {
            log.error("所有案件查询失败：" + e.getMessage());
            throw new BusinessException("所有案件查询失败");
        }
    }

	@Override
	public PaginationHelper<CaseBasic> findJieanCase(CaseBasic caseBasic,
			String page, Map<String, Object> paramMap) {
		try {
            return systemDAO.find(caseBasic, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getJieanCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getJieanCaseList");
        } catch (Exception e) {
            log.error("结案案件查询失败：" + e.getMessage());
            throw new BusinessException("结案案件查询失败");
        }
	}

	@Override
	public Map<String, Object> findAccpetCaseById(String caseId) {
		Map<String, Object> caseMap = new HashMap<String, Object>();
        CaseBasic caseBasic = caseBasicMapper.selectByCaseId(caseId);
        
        //案件当事人和涉案企业信息
        //TODO:XT 这个地方应该查询案件
        List<CaseParty> casePartyList=casePartyMapper.selectCasePartyByCaseId(caseId);
        caseMap.put("casePartyList", casePartyList);
        
        CaseXianyiren caseXianyiren=new CaseXianyiren();
        caseXianyiren.setCaseId(caseId);
        List<CaseXianyiren> xianyirens = caseXianyirenMapper.findAll(caseXianyiren);
        caseMap.put("xianyirenList", xianyirens);
        List<CaseCompany> caseCompanies = caseCompanyMapper.selectCaseCompanyByCaseId(caseId);
        
        caseMap.put("caseCompanyList", caseCompanies);
        caseMap.put("caseBasic", caseBasic);
        return caseMap;
	}

	@Override
	@Transactional
    @LogBusiness(operation = "修改行政受理案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = CaseBasicMapper.class)
	public ServiceResponse updateAcceptCase(CaseBasic caseBasic,
			MultipartHttpServletRequest attachmentFile) {
		ServiceResponse res = new ServiceResponse(true, "案件修改成功");
        caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
        
        // 保存 案件嫌疑人信息并把嫌疑人信息保存为嫌疑人，如果没有嫌疑人，企业信息则什么也不做
        List<CaseParty> casePartyList = null;
        List<CaseCompany> caseCompanyList = null;
        if (StringUtils.isNotBlank(caseBasic.getCasePartyJson())) {
            casePartyList = JSON.parseArray(caseBasic.getCasePartyJson(),
                    CaseParty.class);
        }
        if (StringUtils.isNotBlank(caseBasic.getCaseCompanyJson())) {
            caseCompanyList = JSON.parseArray(
                    caseBasic.getCaseCompanyJson(), CaseCompany.class);
        }
        // 把嫌疑人和企业都删除，再添加，如果没有嫌疑人，企业信息则什么也不做
        //先删除再说，不论本次修改后，还有没有当事人和涉案单位
        casePartyMapper.deleteByCaseId(caseBasic.getCaseId());
        caseXianyirenMapper.deleteByCaseId(caseBasic.getCaseId());
        caseCompanyMapper.deleteByCaseId(caseBasic.getCaseId());
        
        try {
            if (casePartyList != null && !casePartyList.isEmpty()) {

                for (CaseParty caseParty : casePartyList) {
                    if (caseParty == null)
                        continue;
                    caseParty.setCaseId(caseBasic.getCaseId());
                    caseParty.setPartyId(Long.valueOf((systemDAO
                            .getSeqNextVal(Const.TABLE_CASE_PARTY))));
                    casePartyMapper.insertSelective(caseParty);
                    caseXianyirenMapper
                            .insertSelective(getCaseXianYiFanByCaseParty(caseParty));
                    // 保存到个人库
                    PeopleLib peopleLib = PeopleLib
                            .createFromCaseParty(caseParty);
                    int count = peopleLibMapper
                            .updateByPrimaryKeySelective(peopleLib);
                    if (count == 0) {
                        peopleLibMapper.insert(peopleLib);
                    }
                }
            }
            if (caseCompanyList != null && !caseCompanyList.isEmpty()) {
                

                for (CaseCompany caseCompany : caseCompanyList) {
                    if (caseCompany == null)
                        continue;
                    caseCompany.setCaseId(caseBasic.getCaseId());
                    caseCompany.setId(systemDAO.getSeqNextVal(Const.TABLE_CASE_COMPANY));
                    caseCompanyMapper.insertSelective(caseCompany);
                    // 保存到企业库
                    CompanyLib companyLib = CompanyLib
                            .createFromCaseCompany(caseCompany);
                    int count = companyLibMapper
                            .updateByPrimaryKeySelective(companyLib);
                    if (count == 0) {
                        companyLibMapper.insert(companyLib);
                    }
                }
            }
        } catch (Exception e) {
            log.error("修改案件失败：" + e.getMessage());
            throw new BusinessException("修改案件失败");
        }
        return res;
	}

	@Override
	public PaginationHelper<CaseBasic> findMyCaseTurnover(CaseBasic caseBasic, String page) {
		return systemDAO.find(caseBasic,null,page,
				"com.ksource.liangfa.mapper.CaseBasicMapper.getCaseTurnoverCount",
				"com.ksource.liangfa.mapper.CaseBasicMapper.getCaseTurnoverList");
	}
	
	@Override
	public PaginationHelper<CaseBasic> findMyCaseFenpai(CaseBasic caseBasic, String page) {
		return systemDAO.find(caseBasic,null,page,
				"com.ksource.liangfa.mapper.CaseBasicMapper.getCaseFenpaiCount",
				"com.ksource.liangfa.mapper.CaseBasicMapper.getCaseFenpaiList");
	}
	
	/**
	 * 线索办理页面案件录入
	 */
	@Override
    @Transactional
    @LogBusiness(operation = "案件受理", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_mapper_class = CaseBasicMapper.class, target_domain_position = 0)
	public ServiceResponse saveCaseBasicFromClue(CaseBasic caseBasic, User user,ClueInfoReply clueinfoReply,HttpServletRequest request) {
		ServiceResponse res = new ServiceResponse(true, "案件添加成功");
        try {
            // 获取案件ID
            String caseId = getCaseSequenceId();
            caseBasic.setCaseId(caseId);
            Date currentDate = new Date();
            caseBasic.setInputTime(currentDate);
            caseBasic.setCaseState(Const.CHUFA_PROC_0);//案件处理状态，行政受理
            caseBasic.setLatestPocessTime(currentDate);
            caseBasic.setProcKey(Const.CASE_CHUFA_PROC_KEY);
            caseBasic.setInputType(Const.INPUT_TYPE_HAND); //手动录入
            caseBasic.setVersionNo(Const.SYSTEM_VERSION_1);
            caseBasic.setIsAssign(Const.IS_ASSIGN_NO);//是否交办案件
            caseBasic.setIsSuspectedCriminal(Const.SUSPECTED_CRIMINAL_NO);//疑似涉嫌犯罪
            //对案件编号进行去掉空格处理(与案件编号的验证，查询保持一致)
            caseBasic.setCaseNo(StringUtils.trim(caseBasic.getCaseNo()));
            caseBasic.setIsTurnover(Const.IS_TURNOVER_NO);//默认案件都不移送管辖
            caseBasic.setClueId(clueinfoReply.getClueInfoId());
            caseBasicMapper.insert(caseBasic);
            
            //添加待办信息
            CaseTodo caseTodo = new CaseTodo();
            caseTodo.setTodoId(getCaseTodoSequenceId());
            caseTodo.setCreateUser(caseBasic.getInputer());
            caseTodo.setCreateTime(currentDate);
            caseTodo.setCreateOrg(Integer.valueOf(caseBasic.getInputUnit()));
            caseTodo.setAssignUser(caseBasic.getInputer());
            caseTodo.setAssignOrg(Integer.valueOf(caseBasic.getInputUnit()));
            caseTodo.setCaseId(caseId);
            caseTodoMapper.insert(caseTodo);
            
            //设置案件状态
            CaseState caseState = new CaseState();
            caseState.setCaseId(caseId);
            caseState.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NOTYET);//行政立案状态
            caseState.setChufaState(Const.CHUFA_STATE_NOTYET);
            caseState.setLianState(Const.LIAN_STATE_NOTYET);
            caseState.setDaibuState(Const.DAIBU_STATE_NOTYET);
            caseState.setQisuState(Const.QISU_STATE_NOTYET);
            caseState.setPanjueState(Const.PANJUE_STATE_NOTYET);
            caseState.setYisongState(Const.YISONG_STATE_NO);
            caseState.setJieanState(Const.JIEAN_STATE_NO);
            caseState.setExplainState(Const.EXPLAIN_STATE_NOTYET);
            caseState.setReqExplainState(Const.REQ_EXPLAIN_STATE_NOTYET);            
            caseStateMapper.insert(caseState);
            
            //设置案件步骤,在常量类中设置
            CaseStep caseStep = new CaseStep();
            caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
            caseStep.setStepName(getCaseState(Const.CHUFA_PROC_0));
            caseStep.setCaseId(caseId);
            caseStep.setCaseState(caseBasic.getCaseState());
            caseStep.setStartDate(caseBasic.getInputTime());
            caseStep.setEndDate(caseBasic.getInputTime());
            caseStep.setAssignPerson(caseBasic.getInputer());//这一步骤的办理人
            caseStep.setTargetOrgId(caseBasic.getInputUnit());//目标办理机构id，在这里也就是案件录入机构
            caseStep.setTaskActionName(getCaseState(Const.CHUFA_PROC_0));
            
            ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
            caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
            caseStep.setProcDefId(procDeploy.getProcDefId());
            caseStep.setTaskType(Const.TASK_TYPE_ADD_CASE);
            caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
            caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
            caseStepMapper.insert(caseStep);
            
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
                        peopleLib.setInputer(caseBasic.getInputer());
                        peopleLib.setInputTime(caseBasic.getInputTime());
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
                        caseCompanyMapper.insertSelective(caseCompany);
                        // 保存到企业库
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
            
	        //疑似涉嫌犯罪分析
	        String industryType = user.getOrganise().getIndustryType();
//	        crimeAnalyze(caseId,industryType,caseBasic.getCaseDetailName());
	        caseBasicMapper.updateByPrimaryKeySelective(accuseRuleService.matchCaseRule(caseBasic,industryType,request));
	        
	        //在违法线索受理记录表中插入数据
	        clueinfoReply.setReplyId(systemDAO.getSeqNextVal(Const.TABLE_CLUE_REPLY));
	        clueinfoReply.setCreateTime(currentDate);
	        clueinfoReply.setCreateUserId(Integer.parseInt(user.getUserId()));
	        clueinfoReply.setCaseId(caseId);
	        clueInfoReplyMapper.add(clueinfoReply);
	        
	      //更新clueInfo中的线索状态为3 已办理
	        ClueInfo clueInfo = new ClueInfo();
	        clueInfo.setClueState(Const.CLUE_STATE_YISHOULI);
	        clueInfo.setIsReceive(1);
	        clueInfo.setClueId(clueinfoReply.getClueInfoId());
	        clueInfoMapper.updateByPrimaryKeySelective(clueInfo);
	        ClueDispatch cluedispatch=new ClueDispatch();
	        //案件状态设置为已办理
	        cluedispatch.setDispatchCasestate(Const.CLUE_STATE_YIBANLI);
	        cluedispatch.setClueId(clueinfoReply.getClueInfoId());
	        cluedispatch.setReceiveOrg(user.getOrganise().getOrgCode());
	        clueDispatchMapper.updateDispatchState(cluedispatch);
	        //删除app待处理线索消息提醒
	        instantMessageService.deleteByBusinessKey(clueinfoReply.getClueInfoId().toString());	        
        } catch (CaseDealException e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件添加失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件添加失败！");
        }
        return res;
	}

	@Override
	public PaginationHelper<CaseBasic> findAllCase(CaseBasic caseBasic, Map<String, Object> paraMap, String page) {
		return systemDAO.find(caseBasic,paraMap,page,
				"com.ksource.liangfa.mapper.CaseBasicMapper.getCaseAllCount",
				"com.ksource.liangfa.mapper.CaseBasicMapper.getCaseAllList");
	}
	
    @Override
    public int getTimeOutCount(String districtCode, String conditionFormula,String orgPath) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("districtCode", districtCode);
        paramMap.put("conditionFormula", conditionFormula);
        paramMap.put("orgPath", orgPath);
        return caseBasicMapper.getTimeOutCount(paramMap);
    }

    @Override
    public PaginationHelper<CaseBasic> queryTimeOutWarnCase(
            CaseBasic caseBasic, String page, Map<String, Object> param) {
        return systemDAO.find(caseBasic,param,page,
                "com.ksource.liangfa.mapper.CaseBasicMapper.queryTimeOutWarnCaseCount",
                "com.ksource.liangfa.mapper.CaseBasicMapper.queryTimeOutWarnCaseList");
    }

	/**
	 * 根据案件状态获取案件
	 */
	@Override
	public PaginationHelper<CaseBasic> findCaseByState(CaseBasic caseBasic,
			Map<String, Object> paramMap,String page) {
		return systemDAO.find(caseBasic,paramMap,page,
				"com.ksource.liangfa.mapper.CaseBasicMapper.getCaseCount",
				"com.ksource.liangfa.mapper.CaseBasicMapper.getCaseList");
	}

    @Override
    public int getAmountWarnCount(String districtCode, String conditionFormula) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("districtCode", districtCode);
        paramMap.put("conditionFormula", conditionFormula);
        return caseBasicMapper.getAmountWarnCount(paramMap);
    }

	@Override
	public PaginationHelper<CaseBasic> findFenpaiCase(CaseBasic caseBasic,
			Map<String, Object> paramMap, String page) {
		return systemDAO.find(caseBasic,paramMap,page,
				"com.ksource.liangfa.mapper.CaseBasicMapper.getFenpaiCaseCount",
				"com.ksource.liangfa.mapper.CaseBasicMapper.getFenpaiCaseList");
	}

    @Override
    public int getDelayWarnCount(String districtCode, String conditionFormula,Integer acceptOrg) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("districtCode", districtCode);
        paramMap.put("conditionFormula", conditionFormula);
        paramMap.put("acceptOrg", acceptOrg);
        return caseBasicMapper.getDelayWarnCount(paramMap);
    }

	@Override
	public PaginationHelper<CaseBasic> queryAllCaseList(CaseBasic caseBasic,
			String page,Map<String,Object> map) {
		try {
            return systemDAO.find(caseBasic, map, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.queryAllCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.queryAllCaseList");
        } catch (Exception e) {
            log.error("案件查询失败：" + e.getMessage());
            throw new BusinessException("案件查询失败");
        }
	}

	@Override
	public List<CaseStep> getCaseStepListByCaseId(Map<String, Object> paramMap) {
		return caseStepMapper.getCaseStepListByCaseId(paramMap);
	}

	@Override
	public CaseBasic getAdministratorAcceptCase(Map<String, Object> paramMap) {
		return caseBasicMapper.getAdministratorAcceptCase(paramMap);
	}

	@Override
	public PenaltyLianForm getXingzhengLianCase(Map<String,Object> map){
		return penaltyLianFormMapper.getXingzhengLianCase(map);
	}
	
	@Override
	public PenaltyCaseForm getXingzhengChufaCase(Map<String,Object> map){
    	return penaltyCaseFormMapper.getXingzhengChufaCase(map);
    }

	@Override
	public XingzhengNotLianForm getXingzhengBuLianCase(Map<String, Object> map) {
		return xingzhengNotLianFormMapper.getXingzhengBuLianCase(map);
	}

	@Override
	public CaseTurnover getTurnOverCase(Map<String, Object> map) {
		return caseTurnoverMapper.getTurnOverCase(map);
	}

	@Override
	public CaseFenpai getFenpaiCase(Map<String, Object> map) {
		return caseFenpaiMapper.getFenpaiCase(map);
	}

	@Override
	public XingzhengNotPenalty getXingzhengBuChufaCase(Map<String, Object> map) {
		return xingzhengNotPenaltyMapper.getXingzhengBuChufaCase(map);
	}

	@Override
	public XingzhengCancelLianForm getXingzhengCheAnCase(Map<String, Object> map) {
		return xingzhengCancelLianFormMapper.getXingzhengCheAnCase(map);
	}
	
	@Override
	public XingzhengJieanForm getXingzhengJieAnCase(Map<String,Object> map){
		return xingzhengJieanFormMapper.getXingzhengJieAnCase(map);
	}

	@Override
	public CrimeCaseForm getYisongGongAnCase(Map<String, Object> map) {
		return crimeCaseFormMapper.getYisongGongAnCase(map);
	}
	
	@Override
	public List<CaseAccuseKey> getAccuseByCaseId(CaseAccuseKey caseAccuseKey){
		return caseAccuseMapper.getAccuseByCaseId(caseAccuseKey);
	}
	
	@Override
	public SuggestYisongForm getSuggestYisongCase(Map<String, Object> map){
		return suggestYisongMapper.getSuggestYisongCase(map);
	}

	/**
	 * 对旧的案件进行涉嫌犯罪分析
	 * 
	 * @author: LXL
	 * @createTime:2017年10月16日 下午3:57:43
	 */
	@Override
	@Transactional
	public void analyCaseBasicList(Map<String, Object> paramMap,User user,HttpServletRequest request) {
		try {
			String industryType=user.getOrganise().getIndustryType();
			List<CaseBasic> caseBasicList = caseBasicMapper.analyCaseBasicList(paramMap);
			for (CaseBasic caseBasic : caseBasicList) {
		        //保存案件和罪名规则关联关系
				System.out.println("分析前："+caseBasic.getIsSuspectedCriminal());
				caseBasicMapper.updateByPrimaryKeySelective(accuseRuleService.matchCaseRule(caseBasic,industryType,request));
		        System.out.println("分析后："+caseBasic.getIsSuspectedCriminal());
		        caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
			}
		} catch (Exception e) {
			log.error("疑似犯罪案件筛查失败：" + e.getMessage());
			throw new BusinessException("疑似犯罪案件筛查失败");
		}
	}

	@Override
	public PaginationHelper<CaseBasic> queryPanjueCase(CaseBasic caseBasic,
			String page, Map<String, Object> paramMap) {
		try {
            return systemDAO.find(caseBasic, paramMap, page,
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getPanjueCaseCount",
                    "com.ksource.liangfa.mapper.CaseBasicMapper.getPanjueCaseList");
        } catch (Exception e) {
            log.error("案件查询失败：" + e.getMessage());
            throw new BusinessException("案件查询失败");
        }
	}
}
