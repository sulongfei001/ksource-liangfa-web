package com.ksource.liangfa.service.casehandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ksource.common.upload.UpLoadUtil;
import com.ksource.common.upload.bean.UploadBean;
import com.ksource.common.util.FileUtil;
import com.ksource.common.util.Zip4jUtil;
import com.ksource.common.util.ZipUtil;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseAccuseKey;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseModifiedExpLogWithBLOBs;
import com.ksource.liangfa.domain.CaseModifiedInfo;
import com.ksource.liangfa.domain.CaseModifiedInfoExample;
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
import com.ksource.liangfa.mapper.CaseAccuseMapper;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CaseModifiedExpLogMapper;
import com.ksource.liangfa.mapper.CaseModifiedInfoMapper;
import com.ksource.liangfa.mapper.CasePartyMapper;
import com.ksource.liangfa.mapper.CaseProcessMapper;
import com.ksource.liangfa.mapper.CaseStateMapper;
import com.ksource.liangfa.mapper.CaseStepMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.mapper.CrimeCaseExtMapper;
import com.ksource.liangfa.mapper.FieldInstanceMapper;
import com.ksource.liangfa.mapper.FormInstanceMapper;
import com.ksource.liangfa.mapper.PenaltyReferCaseExtMapper;
import com.ksource.liangfa.mapper.XianyirenAccuseMapper;
import com.ksource.syscontext.Const;

@Service
public class CaseExportServiceImpl implements CaseExportService {
    
    @Autowired
    CaseBasicMapper caseBasicMapper;
    @Autowired
    CaseStateMapper caseStateMapper;
    @Autowired
    CaseProcessMapper caseProcessMapper;
    @Autowired
    CrimeCaseExtMapper crimeCaseExtMapper;
    @Autowired
    PenaltyReferCaseExtMapper penaltyReferCaseExtMapper;
    @Autowired
    CaseAccuseMapper caseAccuseMapper;
    @Autowired
    CasePartyMapper casePartyMapper;
    @Autowired
    CaseCompanyMapper caseCompanyMapper;
    @Autowired
    CaseXianyirenMapper caseXianyirenMapper;
    @Autowired
    XianyirenAccuseMapper xianyirenAccuseMapper;
    @Autowired
    CaseStepMapper caseStepMapper;
    @Autowired
    CaseAttachmentMapper caseAttachmentMapper;
    @Autowired
    FormInstanceMapper formInstanceMapper;
    @Autowired
    FieldInstanceMapper fieldInstanceMapper;
    @Autowired
    CaseModifiedExpLogMapper caseModifiedExpLogMapper;
    @Autowired
    SystemDAO systemDAO;
    @Autowired
    CaseModifiedInfoMapper caseModifiedInfoMapper;
    
	@SuppressWarnings("rawtypes")
	@Override
	public void export(User user,String districtCode,HttpServletResponse response) throws Exception{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String ymd = sdf.format(new Date());
        String folderPath = FileUtil.createFolder();
        String zipFilePath =  File.separator+"案件信息导出"+ymd+Const.ZIP_FILE_SUFFIX;//压缩后生成的文件
        String zipFileRealPath = folderPath.substring(0,folderPath.lastIndexOf("\\"))+zipFilePath;//压缩后生成的文件路径
    	//查询案件基础表
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("districtCode", districtCode);
        paramMap.put("tableName", Const.TABLE_CASE_BASIC);
    	List<CaseBasic> caseList = caseBasicMapper.queryNoExportDataList(paramMap);
    	if(caseList.size() > 0){
        	File caseBasicFile = new File(folderPath+File.separator+Const.TABLE_CASE_BASIC+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(caseBasicFile,caseList);	
    	}
    	//案件状态表
    	paramMap.put("tableName", Const.TABLE_CASE_STATE);
    	List<CaseState> caseStates = caseStateMapper.queryNoExportDataList(paramMap);
    	if(caseStates.size() > 0){
        	File caseStateFile = new File(folderPath+File.separator+Const.TABLE_CASE_STATE+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(caseStateFile,caseStates);    		
    	}
    	//案件流程表
    	paramMap.put("tableName", Const.TABLE_CASE_PROCESS);
    	List<CaseProcess> caseProcesses = caseProcessMapper.queryNoExportDataList(paramMap);
    	if(caseProcesses.size() > 0){
        	File caseProcessFile = new File(folderPath+File.separator+Const.TABLE_CASE_PROCESS+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(caseProcessFile,caseProcesses);   		
    	}
    	//涉嫌犯罪
    	paramMap.put("tableName", Const.TABLE_CRIME_CASE_EXT);
    	List<CrimeCaseExt> crimeCaseExts = crimeCaseExtMapper.queryNoExportDataList(paramMap);
    	if(crimeCaseExts.size() > 0){
        	File crimeCaseExtFile = new File(folderPath+File.separator+Const.TABLE_CRIME_CASE_EXT+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(crimeCaseExtFile,crimeCaseExts);		
    	}
    	//移送行政违法
    	paramMap.put("tableName", Const.TABLE_PENALTY_REFER_CASE_EXT);
    	List<PenaltyReferCaseExt> referCaseExts = penaltyReferCaseExtMapper.queryNoExportDataList(paramMap);
    	if(referCaseExts.size() > 0){
        	File referCaseFile = new File(folderPath+File.separator+Const.TABLE_PENALTY_REFER_CASE_EXT+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(referCaseFile,referCaseExts);
    	}
    	//案件罪名
    	paramMap.put("tableName", Const.TABLE_CASE_ACCUSE);
    	List<CaseAccuseKey> caseAccuses = caseAccuseMapper.queryNoExportDataList(paramMap);
    	if(caseAccuses.size() > 0){
        	File caseAccuseFile = new File(folderPath+File.separator+Const.TABLE_CASE_ACCUSE+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(caseAccuseFile,caseAccuses);
    	}
    	//案件当事人
    	paramMap.put("tableName", Const.TABLE_CASE_PARTY);
    	List<CaseParty> caseParties = casePartyMapper.queryNoExportDataList(paramMap);
    	if(caseParties.size() > 0){
        	File casePartyFile = new File(folderPath+File.separator+Const.TABLE_CASE_PARTY+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(casePartyFile,caseParties);    
    	}
		//案件企业表
    	paramMap.put("tableName", Const.TABLE_CASE_COMPANY);
    	List<CaseCompany> caseCompanies = caseCompanyMapper.queryNoExportDataList(paramMap);
    	if(caseCompanies.size() > 0){
        	File caseCompanyFile = new File(folderPath+File.separator+Const.TABLE_CASE_COMPANY+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(caseCompanyFile,caseCompanies);       		
    	}
		//案件嫌疑人
    	paramMap.put("tableName", Const.TABLE_CASE_XIANYIREN);
    	List<CaseXianyiren> caseXianyirens = caseXianyirenMapper.queryNoExportDataList(paramMap);
    	if(caseXianyirens.size() > 0){
        	File caseXianyirenFile = new File(folderPath+File.separator+Const.TABLE_CASE_XIANYIREN+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(caseXianyirenFile,caseXianyirens);     		
    	}
		//嫌疑人罪名
    	paramMap.put("tableName", Const.TABLE_XIANYIREN_ACCUSE);
    	List<XianyirenAccuse> xianyirenAccuses = xianyirenAccuseMapper.queryNoExportDataList(paramMap);
    	if(xianyirenAccuses.size() > 0){
        	File xianyirenAccuseFile = new File(folderPath+File.separator+Const.TABLE_XIANYIREN_ACCUSE+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(xianyirenAccuseFile,xianyirenAccuses);      	
    	}
		//案情处理跟踪
    	paramMap.put("tableName", Const.TABLE_CASE_STEP);
    	List<CaseStep> caseSteps = caseStepMapper.queryNoExportDataList(paramMap);
    	if(caseSteps.size() > 0){
        	File caseStepFile = new File(folderPath+File.separator+Const.TABLE_CASE_STEP+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(caseStepFile,caseSteps);
    	}
		//任务表单实例
    	paramMap.put("tableName", Const.TABLE_FORM_INSTANCE);
    	List<FormInstance> formInstances = formInstanceMapper.queryNoExportDataList(paramMap);
    	if(formInstances.size() > 0){
        	File formInstanceFile = new File(folderPath+File.separator+Const.TABLE_FORM_INSTANCE+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(formInstanceFile,formInstances);   
    	}
		//表单字段实例
    	paramMap.put("tableName", Const.TABLE_FIELD_INSTANCE);
    	List<FieldInstance> fieldInstances  = fieldInstanceMapper.queryNoExportDataList(paramMap);
    	if(fieldInstances.size() > 0){
        	File fieldInstanceFile = new File(folderPath+File.separator+Const.TABLE_FIELD_INSTANCE+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(fieldInstanceFile,fieldInstances);
    	}
		//案件附件表
    	paramMap.put("tableName", Const.TABLE_CASE_ATTACHMENT);
    	List<CaseAttachment> caseAttachments = caseAttachmentMapper.queryNoExportDataList(paramMap);
    	if(caseAttachments.size() > 0){
        	File caseAttachmentFile = new File(folderPath+File.separator+Const.TABLE_CASE_ATTACHMENT+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(caseAttachmentFile,caseAttachments);  
    	}
    	//查询需要删除的数据
    	List<CaseModifiedInfo> deleteDataInfos = caseModifiedInfoMapper.queryDeleteModifiedInfo(paramMap); 
    	if(deleteDataInfos.size() > 0){
        	File deleteDataInfoFile = new File(folderPath+File.separator+"DELETE"+Const.TXT_FILE_SUFFIX);
        	FileUtil.writeFile(deleteDataInfoFile,deleteDataInfos); 
    	}
    	//导出记录数
    	int insertCount = 0;
    	int updateCount = 0;
    	int deleteCount = 0;
    	List<Map> insertMapList = new ArrayList<Map>();
    	List<Map> updateMapList = new ArrayList<Map>();
    	String deleteContent = "";
    	List<CaseModifiedInfo> caseModifiedInfos =  caseModifiedInfoMapper.queryNoExportModifiedInfo(Const.TABLE_CASE_BASIC);
    	for(CaseModifiedInfo cmi:caseModifiedInfos){
    		if(Const.OPERATE_TYPE_INSERT.equals(cmi.getOperateType())){
    			insertCount++;
    			Map<String,Object> content = new HashMap<String,Object>();
    			content.put("caseId", cmi.getKeyValue());
    			content.put("caseNo", cmi.getCaseNo());
    			insertMapList.add(content);
    		}else if(Const.OPERATE_TYPE_UPDATE.equals(cmi.getOperateType())){
    			updateCount++;
    			Map<String,Object> content = new HashMap<String,Object>();
    			content.put("caseId", cmi.getKeyValue());
    			content.put("caseNo", cmi.getCaseNo());
    			updateMapList.add(content);
    		}else if(Const.OPERATE_TYPE_DELETE.equals(cmi.getOperateType())){
    			deleteCount++;
    			deleteContent += cmi.getCaseNo()+",";
    		}
    	}
    	if(insertCount>0 || updateCount>0 || deleteCount>0){
        	Map<String,Object> map = new HashMap<String, Object>();
        	map.put("insertCount", insertCount);
        	map.put("updateCount", updateCount);
        	map.put("deleteCount", deleteCount);
        	FileUtil.writeFile(folderPath+File.separator+"EXP_LOG"+Const.TXT_FILE_SUFFIX,JSONObject.toJSONString(map));     		
    	}
    	
    	//压缩文件夹
		File file = new File(folderPath);
		File[] inputFiles = file.listFiles();
		if(inputFiles.length > 0){
	    	//压缩案件数据
			Zip4jUtil.zipByDefaultPwd(folderPath,zipFileRealPath,true);
			//压缩案件附件数据
			String attPathName = zipCaseAtt();
			String fileName = "案件导出"+ymd+".zip";
			String filePath = folderPath.substring(0,folderPath.lastIndexOf("\\")+1)+fileName;
			Map<String, String> map = new HashMap<String, String>();
			map.put("案件信息导出"+ymd+Const.ZIP_FILE_SUFFIX, zipFileRealPath);
			if(StringUtils.isNotBlank(attPathName)){
				map.put("案件附件导出"+ymd+".zip", attPathName);
			}
			ZipUtil.fileListToZip(map,filePath);//案件信息和附件信息合为一个压缩包
			outFile(response,filePath);
			FileUtil.deleteFile(zipFileRealPath);
			FileUtil.deleteFile(attPathName);
			
	    	//记录案件的导出日志
	    	if(StringUtils.isNotBlank(deleteContent)){
	    		deleteContent  = deleteContent.substring(0, deleteContent.length() - 1);
	    	}
	    	CaseModifiedExpLogWithBLOBs caseModifiedExpLog = new CaseModifiedExpLogWithBLOBs();
	    	caseModifiedExpLog.setLogId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_MODIFIED_EXP_LOG)));
	    	caseModifiedExpLog.setOperator(user.getUserId());
	    	caseModifiedExpLog.setExpTime(new Date());
	    	caseModifiedExpLog.setInsertCount(insertCount);
	    	caseModifiedExpLog.setUpdateCount(updateCount);
	    	caseModifiedExpLog.setDeleteCount(deleteCount);
	    	caseModifiedExpLog.setInsertContent(JSONArray.toJSONString(insertMapList));
	    	caseModifiedExpLog.setUpdateContent(JSONArray.toJSONString(updateMapList));
	    	caseModifiedExpLog.setDeleteContent(deleteContent);
	    	caseModifiedExpLog.setExpFile(fileName);
	    	caseModifiedExpLogMapper.insert(caseModifiedExpLog);
	    	//更新导出数据的导出状态
	    	CaseModifiedInfo caseModifiedInfo = new CaseModifiedInfo();
	    	caseModifiedInfo.setExpState(Const.EXP_STATE_YES);
	    	CaseModifiedInfoExample example = new CaseModifiedInfoExample();
	    	example.createCriteria().andExpStateEqualTo(Const.EXP_STATE_NO);
	    	caseModifiedInfoMapper.updateByExampleSelective(caseModifiedInfo, example);
			//更新导出日志
	    	caseModifiedInfo = new CaseModifiedInfo();
	    	caseModifiedInfo.setFileExpState(Const.EXP_STATE_YES);
	    	example.clear();
	    	example.createCriteria().andFileExpStateEqualTo(Const.EXP_STATE_NO);
	    	caseModifiedInfoMapper.updateByExampleSelective(caseModifiedInfo, example);
		}else{
			FileUtil.deleteFoler(folderPath);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write("<script>") ;
				out.write("alert('未找到需要导出的案件数据');") ;
				out.write("history.back();");
				out.write("</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				out.flush();
				out.close();
			}
    	}
	}

    @SuppressWarnings("unused")
	private void outFile(HttpServletResponse response, String zipFilePath) throws IOException {
    	File file = new File(zipFilePath);
    	InputStream input = new FileInputStream(file);
		if(input == null){
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write("<script>") ;
			out.write("alert('未找到需要导出的数据');") ;
			out.write("history.back();");
			out.write("</script>") ;
		}else{
			String fileName = URLEncoder.encode(file.getName(), "UTF-8");
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition","attachment; filename="+fileName);
			byte[] b = new byte[2048]; 
			int fileLength=0;
			int len = 0; 
			while((len=input.read(b)) >0){
				response.getOutputStream().write(b,0,len);
				fileLength+=len;
			}
			response.setContentLength(fileLength);
			input.close();
			response.flushBuffer();
		}
	}
    
    private String zipCaseAtt(){
		//查询未导出的附件信息
		List<String> attPaths = caseModifiedInfoMapper.querNoExportAttrPath();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String ymd = sdf.format(new Date());
        String attPathName = "";
        if(attPaths.size() > 0){
			Map<String, String> map=new HashMap<String, String>();
			UploadBean uploadBean = UpLoadUtil.getUpLoadBeanFromFile("upload_resource.properties");
			attPathName = uploadBean.getExpDir()+File.separator+"案件附件导出"+ymd+".zip";
			for(String att:attPaths){
				if(att.startsWith("/")){
					att = att.substring(1);
				}
				map.put(att,uploadBean.getUploadDir()+File.separator+att);
			}
			try {
				ZipUtil.fileListToZip(map, attPathName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return attPathName;
    }
    
	@Override
	public void exportAttr(String districtCode, HttpServletResponse response) {
		//查询未导出的附件信息
		List<String> attPaths = caseModifiedInfoMapper.querNoExportAttrPath();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String ymd = sdf.format(new Date());
		if(attPaths.size() > 0){
			Map<String, String> map=new HashMap<String, String>();
			UploadBean uploadBean = UpLoadUtil.getUpLoadBeanFromFile("upload_resource.properties");
			String attPathName = uploadBean.getExpDir()+File.separator+"案件附件导出"+ymd+Const.ZIP_FILE_SUFFIX;
			for(String att:attPaths){
				if(att.startsWith("/")){
					att = att.substring(1);
				}
				map.put(att,uploadBean.getUploadDir()+att);
			}
			try {
				ZipUtil.fileListToZip(map, attPathName);
				outFile(response,attPathName);
				//更新导出日志
		    	CaseModifiedInfo caseModifiedInfo = new CaseModifiedInfo();
		    	caseModifiedInfo.setFileExpState(Const.EXP_STATE_YES);
		    	CaseModifiedInfoExample example = new CaseModifiedInfoExample();
		    	example.createCriteria().andFileExpStateEqualTo(Const.EXP_STATE_NO);
		    	caseModifiedInfoMapper.updateByExampleSelective(caseModifiedInfo, example);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write("<script>") ;
				out.write("alert('未找到需要导出的案件附件');") ;
				out.write("history.back();");
				out.write("</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				out.flush();
				out.close();
			}
		}
	}
}
