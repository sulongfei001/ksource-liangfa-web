package com.ksource.liangfa.app;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.domain.CaseAccuseKey;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseAttachmentExample;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseFenpai;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseTurnover;
import com.ksource.liangfa.domain.CrimeCaseForm;
import com.ksource.liangfa.domain.Dictionary;
import com.ksource.liangfa.domain.FieldInstance;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PenaltyCaseForm;
import com.ksource.liangfa.domain.PenaltyLianForm;
import com.ksource.liangfa.domain.SuggestYisongForm;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.XingzhengCancelLianForm;
import com.ksource.liangfa.domain.XingzhengJieanForm;
import com.ksource.liangfa.domain.XingzhengNotLianForm;
import com.ksource.liangfa.domain.XingzhengNotPenalty;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.DictionaryMapper;
import com.ksource.liangfa.service.ChufaTypeFormService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.CaseStepService;
import com.ksource.liangfa.service.casehandle.CasepartyService;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.service.workflow.FieldInstanceService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 案件查询
 * @author lijiajia
 * @date 2017
 */

@Controller
@RequestMapping("/app/case")
public class AppCaseController {
	
	@Autowired
	private CaseService caseService;
	@Autowired
	private ChufaTypeFormService chufaTypeFormService;
	@Autowired
	private DictionaryMapper dictionaryMapper;
	@Autowired
	private AccuseInfoMapper accuseInfoMapper;
    @Autowired
    private FieldInstanceService fieldInstanceService;	
    @Autowired
    private CasepartyService casepartyService;
    @Autowired
    private CaseAttachmentMapper caseAttachmentMapper;
    @Autowired
    private CaseStepService caseStepService;
    @Autowired
	private InstantMessageService instantMessageService;
    
	/**
	 * 案件查询
	 * @param queryType
	 * @param condition
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query (String queryType,String condition,String page,HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
    	User currUser = SystemContext.getCurrentUser(request);
	    Organise organise=currUser.getOrganise();
        CaseBasic caseBasic=new CaseBasic();
        Map<String,Object> map=new HashMap<String,Object>();
    	try {
    		map.put("condition", condition);
            //给参数赋值
      		String shortDistrictCode=StringUtils.rightTrim0(organise.getDistrictCode());
      		map.put("districtCode", shortDistrictCode);
			//如果用户是行政机构
			if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !currUser.getOrganise().isLiangfaLeaderOrg()){
			   map.put("orgPath", organise.getOrgPath());
			}
            PaginationHelper<CaseBasic> caseList=new PaginationHelper<CaseBasic>();
    		//根据queryType案件类型判断案件状态
            //A行政受理,B行政立案,C行政处罚,D移送公安,E公安立案,F提请逮捕,G移送起诉,H建议移送,I监督立案,J提起公诉,K法院判决,L-公安分派
    		if(queryType.equals("A")){//行政受理
    			caseBasic.setCaseState(Const.CHUFA_PROC_0);
    		}else if(queryType.equals("B")){//行政立案
    			caseBasic.setCaseState(Const.CHUFA_PROC_1);
    		}else if(queryType.equals("C")){//行政处罚
    			caseBasic.setCaseState(Const.CHUFA_PROC_2);
    		}else if(queryType.equals("D")){//移送公安
    			caseBasic.setCaseState(Const.CHUFA_PROC_10);
    		}else if(queryType.equals("E")){//公安立案
    			caseBasic.setCaseState(Const.CHUFA_PROC_14);
    		}else if(queryType.equals("F")){//提请逮捕
    			caseBasic.setCaseState(Const.CHUFA_PROC_17);
    		}else if(queryType.equals("G")){//移送起诉
    			caseBasic.setCaseState(Const.CHUFA_PROC_19);
    		}else if(queryType.equals("H")){//建议移送
        		caseBasic.setCaseState(Const.CHUFA_PROC_10);
        		caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);
        	}else if(queryType.equals("I")){//监督立案
        		caseList=caseService.getjiandulianCaseList(caseBasic, page,map);
        		caseBasic=null;
        	}else if(queryType.equals("J")){//提起公诉
            	caseBasic.setCaseState(Const.ChUFA_PROC_21);
            }else if(queryType.equals("K")){//法院判决
            	caseBasic.setCaseState(Const.ChUFA_PROC_22);
            }else if(queryType.equals("L")){//公安分派
               caseBasic.setCaseState(Const.CHUFA_PROC_28);
            }
    		
    		if(caseBasic!=null){
    			caseList = caseService.queryAllCaseList(caseBasic,page,map);
    		}
    	    if(caseList.getList().size()>0){
    	    	caseList.setMsg("案件信息查询成功！");
    	    }else{
    	    	caseList.setMsg("案件信息为空！");
    	    }
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", caseList.getList());
            jsonObject.put("totalPageNum", caseList.getTotalPageNum());
    	    SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
    	    return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success",false);
			jsonObject.put("msg","案件信息查询失败！");
			return jsonObject.toJSONString();
		}
	}
	
	/**
	 * 查询案件步骤信息
	 * @param caseId
	 * @param request
	 * @return
	 */
	@RequestMapping("queryCaseStep")
	@ResponseBody
	public String queryCaseStep(String caseId,HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
    	Map<String,Object> map=new HashMap<String,Object>();
    	try {
			map.put("caseId", caseId);
			List<CaseStep> caseSteps=caseService.getCaseStepListByCaseId(map);
			if(caseSteps.size()>0){
				jsonObject.put("msg","案件步骤查询成功！");
			}else{
				jsonObject.put("msg","案件步骤查询为空！");
			}
			jsonObject.put("success",true);
			jsonObject.put("caseStepList",caseSteps);
    		return jsonObject.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success",false);
			jsonObject.put("msg","案件步骤查询失败！");
			return jsonObject.toJSONString();
		}
	}
	
	
	/**
	 * 案件详情
	 * @param caseId
	 * @param caseState
	 * @param stepId
	 * @param taskInstId
	 * @param procInstId
	 * @param request
	 * @return
	 */
	@RequestMapping("detail")
	public ModelAndView detail(String caseId,String caseState,Long stepId,String taskInstId,HttpServletRequest request){
    	Map<String,Object> map=new HashMap<String,Object>();
		map.put("caseId", caseId);
		map.put("stepId", stepId);
		ModelAndView mv=new ModelAndView("");
		//获取案件附件信息
		Map<String, CaseAttachment> attaMap=new HashMap<String, CaseAttachment>();
		CaseAttachmentExample caseAttaExa = new CaseAttachmentExample();
		caseAttaExa.createCriteria().andCaseIdEqualTo(caseId).andProcKeyEqualTo(Const.CASE_CHUFA_PROC_KEY);
		List<CaseAttachment> attas = caseAttachmentMapper.selectByExample(caseAttaExa);
		for (CaseAttachment caseAttachment : attas) {
			//ID为数值，不能做为map的KEY，因为前台EL表达式取不到KEY为数值的MAP值。
			attaMap.put("f"+caseAttachment.getId(), caseAttachment);
		}
		
		User currUser = SystemContext.getCurrentUser(request);
		//更新消息详情
		instantMessageService.upReadStatus(caseId, currUser);
		
		if(StringUtils.isNotBlank(caseState) && caseState.equals(Const.CHUFA_PROC_0)){//行政受理
			mv = new ModelAndView("app/caseDetail/xingzhengShouliDetail");
			CaseBasic caseBasic=new CaseBasic();
			caseBasic=caseService.getAdministratorAcceptCase(map);
			//案件当事人列表
			List<CaseParty> CasePartyList = casepartyService.selectCasePartyByCaseId(caseId);
			//涉案企业列表
			List<CaseCompany> caseCompanyList = caseService.getCaseCompanyByCaseId(caseId);
			mv.addObject("caseInfo", caseBasic);
			mv.addObject("CasePartyList", CasePartyList);
			mv.addObject("caseCompanyList", caseCompanyList);
			return mv;
		}else if(StringUtils.isNotBlank(caseState) && caseState.equals(Const.CHUFA_PROC_1)){//行政立案
			mv = new ModelAndView("app/caseDetail/xingzhengLianDetail");
			PenaltyLianForm penaltyLianForm=new PenaltyLianForm();
			penaltyLianForm=caseService.getXingzhengLianCase(map);
			//案件当事人列表
			List<CaseParty> CasePartyList = casepartyService.selectCasePartyByCaseId(caseId);
			//涉案企业列表
			List<CaseCompany> caseCompanyList = caseService.getCaseCompanyByCaseId(caseId);
			mv.addObject("penaltyLianForm", penaltyLianForm);
			mv.addObject("CasePartyList", CasePartyList);
			mv.addObject("caseCompanyList", caseCompanyList);
			mv.addObject("attaMap", attaMap);
			return mv;
		}else if(StringUtils.isNotBlank(caseState) && caseState.equals(Const.CHUFA_PROC_2)){//行政处罚
			mv = new ModelAndView("app/caseDetail/xingzhengChufaDetail");
			PenaltyCaseForm penaltyCaseForm=new PenaltyCaseForm();
			penaltyCaseForm=caseService.getXingzhengChufaCase(map);
			//为处罚类型赋值
			String chufaTypeName="";
			if(StringUtils.isNotBlank(penaltyCaseForm.getChufaTypeName())){
				String[] chufaTypes= penaltyCaseForm.getChufaTypeName().split(",");
				Map<String,Object> param=new HashMap<String,Object>();
				param.put("groupCode", "chufaType");
				for(String type:chufaTypes){
					param.put("dtCode", type);
					Dictionary dic=dictionaryMapper.getDicByDtCode(param);
					if(dic!=null) {
						chufaTypeName +=dic.getDtName()+";";
					}
				}
			}
			penaltyCaseForm.setChufaTypeName(chufaTypeName);
			//案件当事人列表
			List<CaseParty> CasePartyList = casepartyService.selectCasePartyByCaseId(caseId);
			//涉案企业列表
			List<CaseCompany> caseCompanyList = caseService.getCaseCompanyByCaseId(caseId);
			mv.addObject("penaltyCaseForm", penaltyCaseForm);
			mv.addObject("CasePartyList", CasePartyList);
			mv.addObject("caseCompanyList", caseCompanyList);
			mv.addObject("attaMap", attaMap);
			return mv;
		}else if(StringUtils.isNotBlank(caseState) && caseState.equals(Const.CHUFA_PROC_3)){//行政机关不予立案
			mv = new ModelAndView("app/caseDetail/xingzhengBuLianDetail");
			XingzhengNotLianForm xingzhengNotlianForm=new XingzhengNotLianForm();
			xingzhengNotlianForm=caseService.getXingzhengBuLianCase(map);
			mv.addObject("xingzhengNotlianForm",xingzhengNotlianForm);
			mv.addObject("attaMap", attaMap);
			return mv;
		}else if(StringUtils.isNotBlank(caseState) && caseState.equals(Const.CHUFA_PROC_27)){//行政机关移送管辖
			mv = new ModelAndView("app/caseDetail/yisongGuanxiaDetail");
			CaseTurnover caseTurnover=new CaseTurnover();
			caseTurnover=caseService.getTurnOverCase(map);
			mv.addObject("caseTurnover",caseTurnover);
			mv.addObject("attaMap", attaMap);
			return mv;
		}else if(StringUtils.isNotBlank(caseState) && caseState.equals(Const.CHUFA_PROC_28)){//分派
			mv = new ModelAndView("app/caseDetail/fenPaiDetail");
			CaseFenpai caseFenpai=new CaseFenpai();
			caseFenpai=caseService.getFenpaiCase(map);
			mv.addObject("caseFenpai",caseFenpai);
			mv.addObject("attaMap", attaMap);
			return mv;
		}else if(StringUtils.isNotBlank(caseState) && caseState.equals(Const.CHUFA_PROC_5)){//行政机关不予处罚
			mv = new ModelAndView("app/caseDetail/xingzhengBuChufaDetail");
			XingzhengNotPenalty xingzhengNotPenalty=new XingzhengNotPenalty();
			xingzhengNotPenalty=caseService.getXingzhengBuChufaCase(map);
			mv.addObject("xingzhengNotPenalty",xingzhengNotPenalty);
			mv.addObject("attaMap", attaMap);
			return mv;
		}else if(StringUtils.isNotBlank(caseState) && caseState.equals(Const.CHUFA_PROC_4)){//行政撤案
			mv = new ModelAndView("app/caseDetail/xingzhengCheAnDetail");
			XingzhengCancelLianForm xingzhengCancelLianForm=new XingzhengCancelLianForm();
			xingzhengCancelLianForm=caseService.getXingzhengCheAnCase(map);
			mv.addObject("xingzhengCancelLianForm",xingzhengCancelLianForm);
			mv.addObject("attaMap", attaMap);
			return mv;
		}else if(StringUtils.isNotBlank(caseState) && caseState.equals(Const.CHUFA_PROC_29)){//已作出行政处罚，结案
			mv = new ModelAndView("app/caseDetail/xingzhengJieAnDetail");
			XingzhengJieanForm xingzhengJieanForm=new XingzhengJieanForm();
			xingzhengJieanForm=caseService.getXingzhengJieAnCase(map);
			mv.addObject("xingzhengJieanForm",xingzhengJieanForm);
			mv.addObject("attaMap", attaMap);
			return mv;
		}else if(StringUtils.isNotBlank(caseState) && caseState.equals(Const.CHUFA_PROC_10)){//移送公安
			mv = new ModelAndView("app/caseDetail/yisongGongAnDetail");
			CrimeCaseForm crimeCaseForm=new CrimeCaseForm();
			crimeCaseForm=caseService.getYisongGongAnCase(map);
			//查询移送公安罪名
			CaseAccuseKey caseAccuse=new CaseAccuseKey();
			caseAccuse.setCaseId(caseId);
			caseAccuse.setZmType(Const.CASE_ZM_TYPE_yisonggongan);
			List<CaseAccuseKey> accuseList=caseService.getAccuseByCaseId(caseAccuse);
			String caseAccuseName="";
			if(accuseList.size()>0){
				for(CaseAccuseKey accuse:accuseList){
					AccuseInfo accuseInfo=accuseInfoMapper.selectByPrimaryKey(accuse.getAccuseId());
					caseAccuseName +=accuseInfo.getName()+"。";
				}
				crimeCaseForm.setCaseAccuse(caseAccuseName);
			}
			
			//案件当事人列表
			List<CaseParty> CasePartyList = casepartyService.selectCasePartyByCaseId(caseId);
			//涉案企业列表
			List<CaseCompany> caseCompanyList = caseService.getCaseCompanyByCaseId(caseId);
			
			mv.addObject("CasePartyList", CasePartyList);
			mv.addObject("caseCompanyList", caseCompanyList);
			mv.addObject("crimeCaseForm",crimeCaseForm);
			mv.addObject("attaMap", attaMap);
			return mv;
		}else if(StringUtils.isNotBlank(caseState) && caseState.equals(Const.CHUFA_PROC_9)){//建议移送公安
			mv = new ModelAndView("app/caseDetail/jianyiYisongGongAnDetail");
			SuggestYisongForm suggestYisongForm=new SuggestYisongForm();
			suggestYisongForm=caseService.getSuggestYisongCase(map);
			mv.addObject("suggestYisongForm",suggestYisongForm);
			mv.addObject("attaMap", attaMap);
			return mv;
		}else{
	       mv = new ModelAndView("app/caseDetail/dynamicFormDetail");
	       CaseStep caseStep = caseStepService.queryStepInfoAndDeal(stepId);
           List<FieldInstance> fieldInstanceList = fieldInstanceService.selectByTaskInstId(taskInstId);
           mv.addObject("fieldInstanceList",fieldInstanceList);
           mv.addObject("assignPerson",caseStep.getAssignPersonName());
           mv.addObject("assignTime",caseStep.getStartDate());
           return mv;
        }
	}
}
