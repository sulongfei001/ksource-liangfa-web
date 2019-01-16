package com.ksource.liangfa.service.casehandle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.FileUtil;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseAccuseKey;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseBasicExample;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseModifiedImpLogWithBLOBs;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.CaseProcess;
import com.ksource.liangfa.domain.CaseState;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CrimeCaseExt;
import com.ksource.liangfa.domain.FieldInstance;
import com.ksource.liangfa.domain.FormInstance;
import com.ksource.liangfa.domain.PenaltyReferCaseExt;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.XianyirenAccuse;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseModifiedImpLogMapper;
import com.ksource.liangfa.mapper.CaseModifiedImpMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.Const;

/**
 * User: lxh
 */
@Service
public class CaseModifiedImpServiceImpl implements CaseModifiedImpService {
    // 日志
    private static final Logger log = LogManager.getLogger(CaseModifiedImpServiceImpl.class);	
   
    @Autowired
    SystemDAO systemDAO;
    //案件导入
    @Autowired
    private CaseModifiedImpMapper caseModifiedImpMapper;
    
    //案件导入日志
    @Autowired
    private CaseModifiedImpLogMapper caseModifiedImpLogMapper;
     
    @Autowired
    private MybatisAutoMapperService mybatisAutoMapperService;
    
    @Override
    @Transactional
	public ServiceResponse uploadCase(Map<String, String> zipMap,User user,String url) {
    	ServiceResponse response = new ServiceResponse(true ,"导入成功");	
    	int insertCount = 0;
        int updateCount = 0;
        int delCount =0;
        
        List<Map> insertList = new ArrayList<Map>();
        List<Map> updateList = new ArrayList<Map>();
        StringBuffer delMsg = new StringBuffer();
        
        if(zipMap.size()==0){
        	response.setingError("文件有误，请上传正确文件！");
        	return response;
        }
        
    	for (String key : zipMap.keySet()) {
    		String json =zipMap.get(key) ;		
			if(key.equals(Const.TABLE_CASE_BASIC)){
				List<CaseBasic> caseList = (List<CaseBasic>) JSONArray.parseArray(json,CaseBasic.class);
		        Iterator<CaseBasic> it = caseList.iterator();		        
		        while(it.hasNext()){
		        	CaseBasic caseBasic =it.next();
		        	caseModifiedImpMapper.insertCaseBasicTemp(caseBasic);
		        	CaseBasicExample example = new CaseBasicExample();
		        	example.createCriteria().andCaseIdEqualTo(caseBasic.getCaseId());
		        	int count =mybatisAutoMapperService.countByExample(CaseBasicMapper.class, example);
		        	if(count >0){
		        		Map<String,String> updateMap = new HashMap<String, String>();
		        		updateMap.put("caseId", caseBasic.getCaseId());
		        		updateMap.put("caseNo", caseBasic.getCaseNo());		        		
		        		updateList.add(updateMap);		        		
		        		updateCount++;
		        	}else{
		        		Map<String,String> insertMap = new HashMap<String, String>();
		        		insertMap.put("caseId", caseBasic.getCaseId());
		        		insertMap.put("caseNo", caseBasic.getCaseNo());		        		
		        		insertList.add(insertMap);		        		       	
		        		insertCount++;
		        	}
		        }
			}else if(key.equals(Const.TABLE_CASE_STATE)){
				List<CaseState> caseStateList = (List<CaseState>) JSONArray.parseArray(json,CaseState.class);
		        Iterator<CaseState> it = caseStateList.iterator();   
		        while(it.hasNext()){
		        	CaseState caseState =it.next();
		        	caseModifiedImpMapper.insertCaseStateTemp(caseState);
		        }
			}else if(key.equals(Const.TABLE_CASE_PROCESS)){
				List<CaseProcess> caseProcessList = (List<CaseProcess>) JSONArray.parseArray(json,CaseProcess.class);
		        Iterator<CaseProcess> it = caseProcessList.iterator();   
		        while(it.hasNext()){
		        	CaseProcess caseProcess =it.next();
		        	caseModifiedImpMapper.insertCaseProcessTemp(caseProcess);
		        }
			}else if(key.equals(Const.TABLE_CRIME_CASE_EXT)){
				List<CrimeCaseExt> crimeCaseExtList = (List<CrimeCaseExt>) JSONArray.parseArray(json,CrimeCaseExt.class);
		        Iterator<CrimeCaseExt> it = crimeCaseExtList.iterator();   
		        while(it.hasNext()){
		        	CrimeCaseExt crimeCaseExt =it.next();
		        	caseModifiedImpMapper.insertCrimeCaseExtTemp(crimeCaseExt);
		        }
			}else if(key.equals(Const.TABLE_PENALTY_REFER_CASE_EXT)){
				List<PenaltyReferCaseExt> penaltyReferCaseExtList = (List<PenaltyReferCaseExt>) JSONArray.parseArray(json,PenaltyReferCaseExt.class);
		        Iterator<PenaltyReferCaseExt> it = penaltyReferCaseExtList.iterator();   
		        while(it.hasNext()){
		        	PenaltyReferCaseExt penaltyReferCaseExt =it.next();
		        	caseModifiedImpMapper.insertPenaltyReferCaseExtTemp(penaltyReferCaseExt);
		        }
			}else if(key.equals(Const.TABLE_CASE_ACCUSE)){
				List<CaseAccuseKey> caseAccuseList = (List<CaseAccuseKey>) JSONArray.parseArray(json,CaseAccuseKey.class);
		        Iterator<CaseAccuseKey> it = caseAccuseList.iterator();   
		        while(it.hasNext()){
		        	CaseAccuseKey caseAccuse =it.next();
		        	caseModifiedImpMapper.insertCaseAccuseTemp(caseAccuse);
		        }
			}else if(key.equals(Const.TABLE_CASE_PARTY)){
				List<CaseParty> casePartyList = (List<CaseParty>) JSONArray.parseArray(json,CaseParty.class);
		        Iterator<CaseParty> it = casePartyList.iterator();   
		        while(it.hasNext()){
		        	CaseParty caseParty =it.next();
		        	caseModifiedImpMapper.insertCasePartyTemp(caseParty);
		        }
			}else if(key.equals(Const.TABLE_CASE_COMPANY)){
				List<CaseCompany> caseCompanyList = (List<CaseCompany>) JSONArray.parseArray(json,CaseCompany.class);
		        Iterator<CaseCompany> it = caseCompanyList.iterator();   
		        while(it.hasNext()){
		        	CaseCompany caseCompany =it.next();
		        	caseModifiedImpMapper.insertCaseCompanyTemp(caseCompany);
		        }
			}else if(key.equals(Const.TABLE_CASE_XIANYIREN)){
				List<CaseXianyiren> caseXianYiRenList = (List<CaseXianyiren>) JSONArray.parseArray(json,CaseXianyiren.class);
		        Iterator<CaseXianyiren> it = caseXianYiRenList.iterator();   
		        while(it.hasNext()){
		        	CaseXianyiren caseXianYiRen =it.next();
		        	caseModifiedImpMapper.insertCaseXianYiRenTemp(caseXianYiRen);
		        }
			}else if(key.equals(Const.TABLE_XIANYIREN_ACCUSE)){
				List<XianyirenAccuse> xianyirenAccuseList = (List<XianyirenAccuse>) JSONArray.parseArray(json,XianyirenAccuse.class);
		        Iterator<XianyirenAccuse> it = xianyirenAccuseList.iterator();   
		        while(it.hasNext()){
		        	XianyirenAccuse xianyirenAccuse =it.next();
		        	caseModifiedImpMapper.insertXianYiRenAccuseTemp(xianyirenAccuse);
		        }
			}else if(key.equals(Const.TABLE_CASE_STEP)){
				List<CaseStep> caseStepList = (List<CaseStep>) JSONArray.parseArray(json,CaseStep.class);
		        Iterator<CaseStep> it = caseStepList.iterator();   
		        while(it.hasNext()){
		        	CaseStep caseStep =it.next();
		        	caseModifiedImpMapper.insertCaseStepTemp(caseStep);
		        }
			}else if(key.equals(Const.TABLE_CASE_ATTACHMENT)){
				List<CaseAttachment> caseAttachmentList = (List<CaseAttachment>) JSONArray.parseArray(json,CaseAttachment.class);
		        Iterator<CaseAttachment> it = caseAttachmentList.iterator();   
		        while(it.hasNext()){
		        	CaseAttachment caseAttachment =it.next();
		        	caseModifiedImpMapper.insertCaseAttachmentTemp(caseAttachment);
		        }
			}else if(key.equals(Const.TABLE_FIELD_INSTANCE)){
				List<FieldInstance> fieldInstanceList = (List<FieldInstance>) JSONArray.parseArray(json,FieldInstance.class);
		        Iterator<FieldInstance> it = fieldInstanceList.iterator();   
		        while(it.hasNext()){
		        	FieldInstance fieldInstance =it.next();
		        	caseModifiedImpMapper.insertFieldInstanceTemp(fieldInstance);
		        }
			}else if(key.equals(Const.TABLE_FORM_INSTANCE)){	
				List<FormInstance> formInstanceList = (List<FormInstance>) JSONArray.parseArray(json,FormInstance.class);
		        Iterator<FormInstance> it = formInstanceList.iterator();   
		        while(it.hasNext()){
		        	FormInstance formInstance =it.next();
		        	caseModifiedImpMapper.insertFormInstanceTemp(formInstance);
		        }
			}else if(key.equals(Const.DELETE)){
				List<Map> formInstanceList = (List<Map>) JSONArray.parseArray(json,Map.class);				
		        Iterator<Map> it = formInstanceList.iterator();   
		        while(it.hasNext()){
		        	Map<String,String> map =it.next();
		        	String tableName = map.get("tableName");
		        	String keyValueConcat = map.get("keyValueConcat");

		        		 if(tableName.equals(Const.TABLE_CASE_BASIC)){
		        			 List<String> caseNos = caseModifiedImpMapper.queryDeleteCaseNo(keyValueConcat);
		        			 if (caseNos.size()>0){
		        				 delMsg.append(caseNos.toString()); 
		        			 }	
		        			 delCount = caseModifiedImpMapper.deleteCaseBasic(keyValueConcat);   				        			 
		        		 }else if(tableName.equals(Const.TABLE_CASE_STATE)){
		        			 caseModifiedImpMapper.deleteCaseState(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_CASE_PROCESS)){
		        			 caseModifiedImpMapper.deleteCaseProcess(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_CRIME_CASE_EXT)){
		        			 caseModifiedImpMapper.deleteCrimeCaseExt(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_PENALTY_CASE_EXT)){
		        			 caseModifiedImpMapper.deletePenaltyCaseExt(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_PENALTY_REFER_CASE_EXT)){
		        			 caseModifiedImpMapper.deletePenaltyReferCaseExt(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_CASE_ACCUSE)){
		        			 caseModifiedImpMapper.deleteCaseAccuse(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_CASE_PARTY)){
		        			 caseModifiedImpMapper.deleteCaseParty(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_CASE_COMPANY)){
		        			 caseModifiedImpMapper.deleteCaseCompany(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_CASE_XIANYIREN)){
		        			 caseModifiedImpMapper.deleteCaseXianYiRen(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_XIANYIREN_ACCUSE)){
		        			 caseModifiedImpMapper.deleteXianYiRenAccuse(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_CASE_STEP)){
		        			 caseModifiedImpMapper.deleteCaseStep(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_CASE_ATTACHMENT)){
		        			 List<String> caseFileUrl = caseModifiedImpMapper.queryCaseFileUrl(keyValueConcat);	
		        			 try{
		        				 for(String rul : caseFileUrl){
			        				 FileUtil.deleteFileInDisk(rul);		 
			        			 }		
		        			 }catch(Exception e){
		        				 log.debug("删除文件失败！");
		        				 e.printStackTrace();
		        			 }		   			
		        			 caseModifiedImpMapper.deleteCaseAttachment(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_FIELD_INSTANCE)){
		        			 caseModifiedImpMapper.deleteFieldInstance(keyValueConcat);	
		        		 }else if(tableName.equals(Const.TABLE_FORM_INSTANCE)){
		        			 caseModifiedImpMapper.deleteFormInstance(keyValueConcat);	
		        		 }
		        	}			
			}else{
				response.setingError("文件有误，请上传正确文件！");
	        	return response;
			}			
		}
     	
    	//导入到业务库
		this.insertIntoBusiness();
		
		//添加日志
		String deleteContent = delMsg.toString();
	  	if(StringUtils.isNotBlank(deleteContent)){
    		deleteContent  = deleteContent.substring(0, deleteContent.length() - 1);
    	}
    	CaseModifiedImpLogWithBLOBs impLog = new CaseModifiedImpLogWithBLOBs();
    	impLog.setId(systemDAO.getSeqNextVal(Const.TABLE_CASE_MODIFIED_IMP_LOG));
		impLog.setInsertCount(insertCount);
		impLog.setUpdateCount(updateCount);
		impLog.setDeleteCount(delCount);
		impLog.setImpTime(new Date());
		impLog.setOperator(user.getUserId());
		impLog.setInsertContent(JSONArray.toJSONString(insertList));
		impLog.setUpdateContent(JSONArray.toJSONString(updateList));
		impLog.setDeleteContent(deleteContent);
		impLog.setImpFile(url);
		
		caseModifiedImpLogMapper.insert(impLog);
		
//		Map<String,Object> map = new HashMap<String, Object>();
//    	map.put("insertCount", insertCount);
//    	map.put("updateCount", updateCount);
//    	map.put("delCount", delCount);
//		
//		response.setingSucess(JSONObject.toJSONString(map));
//		
		return response;
	}
    
    public void insertIntoBusiness(){
    	
    	/**
    	 * 导入案件基本信息
    	 */
    	caseModifiedImpMapper.insertCaseBasic();
    	
    	/**
    	 * 导入案件状态
    	 */
    	caseModifiedImpMapper.insertCaseState();
    	
    	/**
    	 * 导入案件流程信息
    	 */
    	caseModifiedImpMapper.insertCaseProcess();
    	
    	/**
    	 * 导入涉嫌犯罪扩展属性
    	 */
    	caseModifiedImpMapper.insertCrimeCaseExt();
    	
    	
    	/**
    	 * 导入移送行政违法扩展属性
    	  */
    	caseModifiedImpMapper.insertPenaltyReferCaseExt();
    	
    	/**
    	 * 导入案件罪名
    	 */
    	caseModifiedImpMapper.insertCaseAccuse();
    	
    	/**
    	 * 导入案件当事人信息
    	 */
    	caseModifiedImpMapper.insertCaseParty();
    	
    	/**
    	 * 导入案件企业信息
    	 */
    	caseModifiedImpMapper.insertCaseCompany();
    	
    	/**
    	 * 导入案件嫌疑人
    	 */
    	caseModifiedImpMapper.insertCaseXianYiRen();
    	
    	/**
    	 * 导入案件嫌疑人罪名
    	 */
    	caseModifiedImpMapper.insertXianYiRenAccuse();
    	
    	/**
    	 * 导入案件处理步骤
    	 */
    	caseModifiedImpMapper.insertCaseStep();
    	
    	/**
    	 * 导入案件附件信息
    	 */
    	caseModifiedImpMapper.insertCaseAttachment();
    	
    	/**
    	 * 导入案件表单字段
    	 */
    	caseModifiedImpMapper.insertFieldInstance();
    	
    	/**
    	 * 导入案件任务表单
    	 */
    	caseModifiedImpMapper.insertFormInstance();
    }
}
