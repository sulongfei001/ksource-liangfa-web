package com.ksource.liangfa.service.casehandle;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseAccuseKey;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseFenpai;
import com.ksource.liangfa.domain.CaseFilter;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CaseState;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseTurnover;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.ClueAccept;
import com.ksource.liangfa.domain.ClueInfoReply;
import com.ksource.liangfa.domain.CrimeCaseExt;
import com.ksource.liangfa.domain.CrimeCaseForm;
import com.ksource.liangfa.domain.PenaltyCaseForm;
import com.ksource.liangfa.domain.PenaltyLianForm;
import com.ksource.liangfa.domain.PenaltyReferCaseExt;
import com.ksource.liangfa.domain.SuggestYisongForm;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.XingzhengCancelLianForm;
import com.ksource.liangfa.domain.XingzhengJieanForm;
import com.ksource.liangfa.domain.XingzhengNotLianForm;
import com.ksource.liangfa.domain.XingzhengNotPenalty;

/**
 * 此类为 案件 业务 处理类（服务层）
 * 
 * @author zxl :)
 * @version 1.0 date 2011-5-13 time 上午09:33:25
 */
public interface CaseService {

	/**
	 * 根据数据库的序列生成一个序列ID
	 * 
	 * @return 序列ID
	 */
	String getCaseSequenceId();

	/**
	 * 添加案件及案件当事人信息。
	 * 
	 * @param caseBasic
	 *            案件信息
	 * @param attachmentFile 
	 *            　案件当事人信息(json数据)
	 * @return
	 */
	ServiceResponse addPenaltyCase(CaseBasic caseBasic, MultipartHttpServletRequest attachmentFile,String oldCaseId,User user,HttpServletRequest request);

	/**
	 * 根据特定条件查询案件信息。 采用数据库分页查询。
	 * 
	 * @param caCondition
	 * @param page
	 * @return
	 */
	PaginationHelper<CaseBasic> find(CaseBasic caCondition, String page,
			Map<String, Object> param);

	/**
	 * 修改案件
	 * 
	 * @param caseWithBLOBs
	 * @param attachmentFile 
	 */
	ServiceResponse update(CaseBasic caseWithBLOBs, MultipartHttpServletRequest attachmentFile);

	/**
	 * 通过案件ID得到对应的案件当事人信息集合． 除了当事人数据库中字段外，新查出了三个字段． 1.birthdayName
	 * 用yyy-MM-dd格式　格式后 birthday 2.educationName 教育程度与education对应 3.sexName 性别
	 * 与sex对应
	 * 
	 * @param caseId
	 * @return
	 */
	List<CaseParty> getCasePartyByCaseId(String caseId);

	
	/**
	 * 通过唯一标示查询案件信息(不包括附件文件信息)
	 * 
	 * @param caseId
	 * @return
	 */
	CaseBasic findByPk(String caseId);

	/**
	 * 通过案件编号查询是否存在案件.<br/>
	 * 如果是行政区划只能查出同组织机构内的案件, 如果是其它组织机构,可以查出同行政区划内的案件.
	 * 
	 * @return
	 */
	int countBycaseNo(Map<String, Object> map);

	/**
	 * 通过案件编号查询案件信息(不包括附件文件信息).<br/>
	 * 如果是行政区划只能查出同组织机构内的案件, 如果是其它组织机构,可以查出同行政区划内的案件.
	 * 
	 * @param map
	 * @return
	 */
	CaseBasic findByCaseNoandOrg(Map<String, Object> map);

	/**
	 * 通过案件编号模糊匹配查询案件信息(只有案件编号和案件名称)<br/>
	 * 如果是行政区划只能查出同组织机构内的案件, 如果是其它组织机构,可以查出同行政区划内的案件.
	 * 
	 * @param map
	 * @return
	 */
	List<CaseBasic> findByLikeCaseNo(Map<String, Object> map);

	/**
	 * 案件信息查询
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */

	PaginationHelper<CaseBasic> findByState(CaseBasic caseBasic, String page,
			Map<String, Object> paramMap);
	
	
	/**
	 * 数据库分页查询案件信息(用于罪名下钻).
	 * 
	 * @param case1
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> findByAccuse(CaseBasic case1, String page,
			Map<String, Object> paramMap);

	/**
	 * 案件嫌疑人查询
	 * @param caseXianyiren
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseXianyiren> find(CaseXianyiren caseXianyiren,
			String page, Map<String, Object> paramMap);
	

	/**
	 * 通过案件ID得到对应的案件企业信息集合． 除了当事人数据库中字段外，新查出了一个字段． 1.registractionTimeName
	 * 用yyy-MM-dd格式　格式后 registractionTime
	 * 
	 * @param caseId
	 * @return
	 */
	List<CaseCompany> getCaseCompanyByCaseId(String caseId);


	
	/**
	 * 检查机关逮捕案件查询
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> getDaibuList(CaseBasic caseBasic, String page,
			Map<String, Object> paramMap);

	
	/**
	 * 逮捕嫌疑人查询
	 * @param caseXianyiren
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseXianyiren> getDaibuPartyList(
			CaseXianyiren caseXianyiren, String page,
			Map<String, Object> paramMap);

	/**
	 * 案件删除时查询案件列表.
	 * 
	 * @param case1
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> searchCaseAndWorkflow(CaseBasic case1, String page,
			Map<String, Object> paramMap);

	/**
	 * 与些案件相关的企业与嫌疑人信息，咨询，批复，结案通知，罪名信息，移送行政机关信息，办理表单数据及流程数据及其相关附件
	 * 
	 * @param paramMap
	 * @return
	 */
	boolean deleteCaseAndWorkflow(String caseId,Map<String, Object> paramMap);

	void delAtta(Long id);

	CaseBasic findCaseById(String id);
	
	/**
	 * 当前用户得到的超时案件
	 * @param map
	 * @return
	 */
	List<CaseBasic> getCases(Map<String, Object> map);
	
	public List<CaseBasic> getTimeoutWarnCases(User user) ;
	
	/**
	 * 
	 * @param caseId
	 * @return 该案件对应的行政区划代码
	 */
	String getDistrictCode(String caseId) ;
	
	/**
	 * 移送涉嫌犯罪案件查询
	 * @param caCondition
	 * @param page
	 * @param param
	 * @return
	 */
	PaginationHelper<CaseBasic> findYisongQuery(CaseBasic caCondition, String page,
			Map<String, Object> param);
	
	/**
	 * 移送行政违法案件查询
	 * @param caCondition
	 * @param page
	 * @param param
	 * @return
	 */
	PaginationHelper<CaseBasic> findYisongChufaQuery(CaseBasic caCondition, String page,
			Map<String, Object> param);

     /**
	 * 查询已经结案的由userId录入人录入的案件(并且是未读结案案件)
	 * @param userId  录入人
	 * @return
	 */
    List<CaseBasic> selectJieanNoticeCase(String userId);

 

    /**
     * 查询当前用户的监督案件
     * @param userId
     * @return
     */
    public List<CaseBasic> caseSupervisionNotice(String userId);
    
    
    /**
	 * 打侵打假案件详细信息(按组织机构)
     * @param page
	 * @param param
	 * @return
	 */
	public PaginationHelper<CaseBasic> queryCaseList(String page,Map<String, Object> param);
	
	/**
	 * 打侵打假案件详细信息(按行政区划)
     * @param page
	 * @param param
	 * @return
	 */
	public PaginationHelper<CaseBasic> queryCaseListByDistrict(String page,Map<String, Object> param);
	
	/**
	 * 保存案件和专项活动的关联关系
	 * @param caseId
	 * @param inputTime
	 * @param orgCode
	 * @return
	 */
	Integer saveActivityCaseRelation(String caseId, Date inputTime,Integer orgCode);
	
	/**
	 * 删除案件和专项活动的关联关系
	 * @param caseId
	 * @return
	 */
	Integer deleteActivityCaseRelation(String caseId);

	/**
	 * 添加涉嫌犯罪案件
	 * @param caseBasic
	 * @param caseState
	 * @param crimeCaseExt
	 * @param map
	 * @param attachmentFile
	 * @param orgCode
	 * @return
	 * @author XT
	 */
	ServiceResponse addCrimeCase(CaseBasic caseBasic, CaseState caseState,
			CrimeCaseExt crimeCaseExt, Map<String, Object> map,
			MultipartHttpServletRequest attachmentFile, Integer orgCode);
	
	/**
	 * 查询所有行政处罚案件
	 * @param case1
	 * @param page
	 * @param param
	 * @return
	 */
	PaginationHelper<CaseBasic> findPenaltyCaseList(CaseBasic case1, String page, Map<String, Object> param);
	
	
	/**
	 * 添加移送行政处罚案件
	 * @param caseBasic 
	 * @param caseState
	 * @param penaltyReferCaseExt 
	 * @param map
	 * @return
	 */
	ServiceResponse addYisongChufaCase(CaseBasic caseBasic,CaseState caseState, PenaltyReferCaseExt penaltyReferCaseExt, Map<String, Object> map,MultipartHttpServletRequest attachmentFile);


	/**
	 * 查找涉嫌犯罪案件
	 * @param caseId
	 * @return
	 * @author XT
	 */
	Map<String, Object> findCrimeCaseById(String caseId);

	/**
	 * 更新涉嫌犯罪案件信息
	 * @param caseBasic
	 * @param crimeCaseExt
	 * @param attachmentFile
	 * @return
	 * @author XT
	 */
	ServiceResponse updateCrimeCase(CaseBasic caseBasic,
			CrimeCaseExt crimeCaseExt,
			MultipartHttpServletRequest attachmentFile);

	
	/**
	 * 查询行政处罚或涉嫌犯罪案件
	 * @param case1
	 * @param page
	 * @param param
	 * @return
	 */
	PaginationHelper<CaseBasic> findChufaOrCrimeCaseList(CaseBasic case1, String page, Map<String, Object> param);
	
	/**
	 * 根据Id查找移送行政违法案件
	 * @param caseId
	 * @return
	 */
	Map<String, Object> findPenaltyReferCaseById(String caseId);

	/**
	 * 更新移送行政违法案件
	 * @param caseBasic
	 * @param penaltyReferCaseExt
	 * @param caseAttachmentFile
	 * @return
	 */
	ServiceResponse updatepenaltyReferCase(CaseBasic caseBasic,PenaltyReferCaseExt penaltyReferCaseExt,MultipartHttpServletRequest caseAttachmentFile);

    /**
     * 查询报备案件
     * @param paramMap
     * @return
     */
    PaginationHelper<CaseBasic> caseRecordNotice(Map<String,Object> paramMap,String page);
    
    
     /**
      * 根据案件类型查询案件详细信息(行政处罚案件统计报表和涉嫌犯罪案件统计报表用)
      * @param case1
      * @param page
      * @param param
      * @return
      */
    PaginationHelper<CaseBasic> findAllStateCaseList(CaseBasic case1, String page, Map<String, Object> param);
    
    /**
     * 根据指标，行政区划或组织机构查询案件信息(案件综合统计报表(按录入单位)使用)
     * @param case1
     * @param page
     * @param param
     * @return
     */
    PaginationHelper<CaseBasic> findStateCaseListByOrg(CaseBasic case1, String page, Map<String, Object> param);
    
    /**
     * 涉嫌犯罪案件指标查询(案件综合统计报表(按录入单位))
     * @param case1
     * @param page
     * @param param
     * @return
     */
    PaginationHelper<CaseBasic> findCrimeCaseListByOrg(CaseBasic case1, String page, Map<String, Object> param);
    
    /**
     * 根据行政区划或组织机构查询案件的嫌疑人信息 (案件综合统计报表(按录入单位))
     * @param caseXianyiren
     * @param page
     * @param paramMap
     * @return
     */
	PaginationHelper<CaseXianyiren> findXianyirenByOrg(CaseXianyiren caseXianyiren,
			String page, Map<String, Object> paramMap);
	
	/**
	 * 查询未处罚主动移送案件
	 * @param case1
	 * @param page
	 * @param param
	 * @return
	 */
	PaginationHelper<CaseBasic> findNoPenaltyTransferCaseList(CaseBasic caseBasic, String page, Map<String, Object> param);

	/**
     * 添加　此方法用于模板批量添加
     * @param caseBasic
     * @param map
     * @return
     */
    ServiceResponse addByTem(CaseBasic caseBasic,Map<String, Object> map,String industryType,HttpServletRequest request);

    /**
     * 疑似涉嫌犯罪案件查询
     * @param penaltyCaseExt
     * @param page
     * @param object
     * @return
     */
	PaginationHelper<CaseBasic> findYisiCaseList(CaseBasic case1, String page, Map<String, Object> param);

	/**
	 * 立案监督案件查询
	 * @param penaltyCaseExt
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> findlianCaseList(CaseBasic case1, String page,Map<String, Object> paramMap);

	/**
	 * 疑似分案查询
	 * @param case1
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> findYisiFaCaseList(CaseBasic case1, String page,Map<String, Object> paramMap);
	
	/**
	 * 提请逮捕和提请起诉案件查询(报表统计使用)
	 * @param case1
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> getDaibuAndQisuCaseList(CaseBasic case1, String page,Map<String, Object> paramMap);
	
	/**
	 * 提请逮捕和提请起诉案件查询(按组织报表统计使用)
	 * @param case1
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> getDaibuAndQisuCaseListByOrg(CaseBasic case1, String page,Map<String, Object> paramMap);
	
	/**
	 * 逮捕案件查询(按组织报表统计使用)
	 * @param case1
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> getDaibuListByOrg(CaseBasic case1, String page,Map<String, Object> paramMap);
	
	/**
	 * 案件罪名信息查询
	 * @param case1
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> findCaseAccuseList(CaseBasic case1, String page,Map<String, Object> paramMap);
	
	
	
	/**
	 * 检查机关逮捕案件查询 (按行业报表统计使用)
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> getDaibuListByIndustry(CaseBasic caseBasic, String page,
			Map<String, Object> paramMap);
	
	
	/**
	 * 提请逮捕和提请起诉案件查询(按行业报表统计使用)
	 * @param case1
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> getDaibuAndQisuCaseListByIndustry(CaseBasic case1, String page,Map<String, Object> paramMap);
	
	/**
	 * 案件信息查询(按行业报表统计使用)
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */

	PaginationHelper<CaseBasic> findByStateByIndustry(CaseBasic caseBasic, String page,
			Map<String, Object> paramMap);
	
	/**
	 * 查询某一状态下的案件的最小的录入时间
	 *
	 * @param caseState
	 * @return
	 */
	Date queryMinTimeByCaseState(int caseState);
	
	/**
	 * 查询待查阅案件总数
	 *
	 * @param caseStateChayue
	 * @return
	 */
	int queryCountByState(int caseStateChayue);

	List<CaseBasic> querySuspectedCriminalCase(CaseBasic caseBasic);
	
	/**
	 * 查询监督立案案件
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> getjiandulianCaseList(CaseBasic caseBasic, String page,Map<String, Object> paramMap);

	/**
	 * 查询满足6种筛选条件其中一条的待阅案件
	 * @param filterMap 
	 *
	 * @param caseBasic
	 * @return 
	 */
	List<CaseBasic> queryfilingSupervisionCase(Map<String, Object> paramMap, Map<String, Object> filterMap);
	
	CaseBasic getCaseByTaskId(Map<String, Object> paramMap);
	
	/**
	 * 查询公安未受理的移送涉嫌犯罪案件
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> yisongPoliceNotAcceptQueryList(CaseBasic caseBasic, String page,Map<String, Object> paramMap);
	
	Date queryMinCaseInputTime(CaseBasic caseBasic);

	int queryCaseCount(CaseBasic caseBasic);

	Integer getyisiFaCaseCount(Map<String, Object> paramMap,Map<String, Object> filterMap);

	List<CaseBasic> getyisiFaCaseList(Map<String, Object> paramMap,Map<String, Object> filterMap);

	Integer queryfilingSupervisionCaseCount(Map<String, Object> paramMap, Map<String, Object> filterMap);

	PaginationHelper<CaseBasic> filterDrill(CaseBasic caseBasic, Integer num,
			String page,Map<String, Object> paramMap);

	/**
	 * 某行业行政处罚录入统计按区划
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryCaseInputInfoForIndustry(
			Map<String, Object> paramMap);

	/**
	 * 获得某行业涉嫌犯罪案件处理情况按区划统计 
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryCrimeCaseInfoForIndustry(
			Map<String, Object> paramMap);

	/**
	 * 获取业务系统涉嫌犯罪案件处理情况总体情况
	 * @author chenzengjie
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryAllCrimeCaseDealStatis(Map<String, Object> paramMap);
	
	
	/**
	 * 行政处罚案件录入按区划进行统计
	 * author XT
	 * @param paramMap TODO
	 * @return
	 */
	List<Map<String, Object>> queryXzcfInputRatioPerRegion(Map paramMap);
	/**
	 * 行政处罚录入统计按行业
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryXzcfInputRatioPerIndustry(
			Map<String, Object> paramMap);
	/**
	 * 获得涉嫌犯罪案件处理情况按区划统计
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryCrimeCaseDealStatis(
			Map<String, Object> paramMap);
	/**
	 * 获得涉嫌犯罪案件处理情况按行业统计 
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryCrimeCaseDealStatisByIndustry(
			Map<String, Object> paramMap);
	/**
	 * 统计前十名罪名个数 
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryTop10Accuse(Map<String, Object> paramMap);
	
	/**
	 * 综合分析获取行政处罚案件总数
	 * @author chenzengjie
	 * @param paramMap
	 */
	int queryCaseCountForZHFX(Map<String, Object> paramMap);
	/**
	 * 综合分析获取行疑似涉嫌犯罪案件
	 * @author chenzengjie
	 * @param paramMap
	 * @return
	 */
	int queryCrimeCaseCountForZHFX(Map<String, Object> paramMap);
	/**
	 * 降格处理案件数量
	 * @author chenzengjie
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryJGCLCaseCountForZHFX(Map<String, Object> paramMap);
	
	/**
	 * 大数据分析筛查案件数量
	 * @author chenzengjie
	 * @param paramMap
	 * @param filterMap
	 * @return
	 */
	Integer queryDSJFXCaseCountForZHFX(Map<String, Object> paramMap,Map<String, Object> filterMap);

	/**
	 * 滞留案件查询(移送公安未受理)
	 * @param caseBasic
	 * @param paramMap
	 * @param page
	 * @return
	 */
	PaginationHelper<CaseBasic> getYisongPoliceNotAcceptList(CaseBasic caseBasic, Map<String,Object> paramMap,String page);
	
	/**
	 * 查询移送案件数量
	 * @param caseBasic
	 * @return
	 */
	Integer queryYisongCaseCount(CaseBasic caseBasic);
	
	/**
	 * 查询移送公安尚未处理案件数量
	 * @param caseBasic
	 * @return
	 */
	Integer queryPoliceNotAcceptCaseCount(CaseBasic caseBasic);
	
	/**
	 * 查询移送公安尚未处理案件信息
	 * @param caseBasic
	 * @return
	 */
	List<CaseBasic> queryPoliceNotAcceptCaseList(CaseBasic caseBasic);

	/**
	 * 查询移送案件数量
	 *
	 * @param paramMap
	 * @return
	 */
	int queryYisongCount(Map<String, Object> paramMap);

	/**
	 * 查询降格处理案件
	 *
	 * @param page
	 * @param paramMap
	 * @param caseFilter 
	 * @return
	 */
	PaginationHelper<CaseBasic> queryfilingSupervisionCase(String page,
			Map<String, Object> paramMap, CaseFilter caseFilter);

	/**
	 * 查询当前用户已办案件
	 *
	 * @param caseBasic
	 * @param page
	 * @return
	 */
	PaginationHelper<CaseBasic> queryCompletedCaseList(CaseBasic caseBasic,
			String page);

	/**
	 * 查询行政受理案件信息
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> findAcceptCaseList(CaseBasic caseBasic,
			String page, Map<String, Object> paramMap);

	/**
	 * 案件查询行政立案案件查询
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> findXingzhengLianCaseList(CaseBasic caseBasic,
			String page, Map<String, Object> paramMap);

	/**
	 * 查询预警数据数量
	 *
	 * @return
	 */
	Map<String, Integer> queryWarningData(Map<String,Object> param);

	/**
	 * 案件办理行政立案案件查询
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> findXingzhengLianQueryCaseList(
			CaseBasic caseBasic, String page);

	/**
	 * 案件办理行政处罚案件查询
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> findXingzhengChufaCaseList(CaseBasic caseBasic,
			String page, Map<String, Object> paramMap);

	/**
	 * 疑似犯罪案件线索筛查
	 *
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> querySuspectedCaseList(String page,
			Map<String, Object> paramMap);

	PaginationHelper<CaseBasic> queryAmonutWarnCase(CaseBasic caseBasic,String page,Map<String, Object> paramMap);

	/**
	 * 查询所有全部案件
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> getAllCaseList(CaseBasic caseBasic,
			String page, Map<String, Object> paramMap);

	/**
	 * 自定义条件查询
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> generalQuery(CaseBasic caseBasic, String page,
			Map<String, Object> paramMap);
	
	/**
	 * 查询结案案件
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> findJieanCase(CaseBasic caseBasic, String page,Map<String, Object> paramMap);

	/**
	 * 查询行受理案件（根据caseID）
	 * @param caseId
	 * @return
	 */
	Map<String, Object> findAccpetCaseById(String caseId);

	/**
	 * 修改受理案件
	 * @param caseBasic
	 * @param attachmentFile
	 * @return
	 */
	ServiceResponse updateAcceptCase(CaseBasic caseBasic,
			MultipartHttpServletRequest attachmentFile);
	
	/**
	 * 我的移送管辖
	 * @param caseBasic
	 * @param page
	 * @return
	 */
	PaginationHelper<CaseBasic> findMyCaseTurnover(CaseBasic caseBasic,String page);
	/**
	 * 我的分派
	 * @param caseBasic
	 * @param page
	 * @return
	 */
	PaginationHelper<CaseBasic> findMyCaseFenpai(CaseBasic caseBasic,String page);
	
	/**
	 * 线索办理页面的案件录入
	 * @param caseBasic
	 * @param user
	 * @return
	 */
	ServiceResponse saveCaseBasicFromClue(CaseBasic caseBasic,User user,ClueInfoReply clueinfoReply,HttpServletRequest request);

	PaginationHelper<CaseBasic> findAllCase(CaseBasic caseBasic, Map<String, Object> paraMap, String page);

	   //查询超时案件数量
    int getTimeOutCount(String districtId, String conditionFormula,String orgPath);

    /**
     * 查询超时预警案件信息
     *
     * @param caseBasic
     * @param page
     * @param param
     * @return
     */
    PaginationHelper<CaseBasic> queryTimeOutWarnCase(CaseBasic caseBasic,String page, Map<String, Object> param);
	

	/**
	 * 根据案件状态查询
	 * @param caseBasic
	 * @param map 
	 * @param page
	 * @return
	 */
	PaginationHelper<CaseBasic> findCaseByState(CaseBasic caseBasic, Map<String, Object> map, String page);

    int getAmountWarnCount(String districtId, String conditionFormula);

    /**
     * 查询公安分派案件
     * @param caseBasic
     * @param map
     * @param page
     * @return
     */
	PaginationHelper<CaseBasic> findFenpaiCase(CaseBasic caseBasic,
			Map<String, Object> map, String page);

    /**
     * 查询滞留案件数量
     *
     * @param districtId
     * @param conditionFormula
     * @return
     */
    int getDelayWarnCount(String districtId, String conditionFormula,Integer acceptOrg);

    /**
     * 
     * @param caseBasic
     * @param map
     * @param page
     * @return
     */
    PaginationHelper<CaseBasic> queryAllCaseList(CaseBasic caseBasic,String page,Map<String,Object> map);
    
    /**
     * 根据caseId查询案件步骤
     * @param paramMap
     * @return
     */
    List<CaseStep> getCaseStepListByCaseId(Map<String, Object> paramMap);
    
    /**
     * 查询行政受理步骤详情
     * @param paramMap
     * @return
     */
    CaseBasic getAdministratorAcceptCase(Map<String, Object> paramMap);
    
    /**
     * 查询行政立案步骤详情
     * @param map
     * @return
     */
    PenaltyLianForm getXingzhengLianCase(Map<String,Object> map);
    
    /**
     * 查询行政处罚步骤详情
     * @param map
     * @return
     */
    PenaltyCaseForm getXingzhengChufaCase(Map<String,Object> map);
    
    /**
     * 查询行政不立案步骤详情
     * @param map
     * @return
     */
    XingzhengNotLianForm getXingzhengBuLianCase(Map<String,Object> map);
    
    /**
	 * 查询移送管辖步骤详情
	 * @param map
	 * @return
	 */
	CaseTurnover getTurnOverCase(Map<String,Object> map);
	
	/**
	 * 查询分派步骤详情
	 * @param map
	 * @return
	 */
	CaseFenpai getFenpaiCase(Map<String,Object> map);
	
	/**
     * 查询行政不予处罚步骤详情
     * @param map
     * @return
     */
    XingzhengNotPenalty getXingzhengBuChufaCase(Map<String,Object> map);
    
    /**
	 * 查询行政撤案案件步骤详情
	 * @param map
	 * @return
	 */
	XingzhengCancelLianForm getXingzhengCheAnCase(Map<String,Object> map);
	
	/**
     * 查询行政结案案件步骤详情
     * @param map
     * @return
     */
    XingzhengJieanForm getXingzhengJieAnCase(Map<String,Object> map);
    
    /**
	 * 查询移送公安步骤信息
	 * @param map
	 * @return
	 */
	CrimeCaseForm getYisongGongAnCase(Map<String,Object> map);
	
	
	/**
	 * 根据caseId和zmType查询案件罪名信息
	 * @param paramMap
	 * @return
	 */
	List<CaseAccuseKey> getAccuseByCaseId(CaseAccuseKey caseAccuseKey);
	
	/**
	 * 查询建议移送公安步骤信息
	 * @param map
	 * @return
	 */
	SuggestYisongForm getSuggestYisongCase(Map<String,Object> map);

	void analyCaseBasicList(Map<String, Object> paramMap,User user,HttpServletRequest request);
	
	/**
	 * 查询法院判决案件
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> queryPanjueCase(CaseBasic caseBasic, String page,Map<String, Object> paramMap);
}
