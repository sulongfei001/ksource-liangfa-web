package com.ksource.liangfa.service.instruction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.WorkReport;
import com.ksource.liangfa.domain.WorkReportReceive;
import com.ksource.liangfa.domain.WorkReportReceiveExample;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.mapper.WorkReportMapper;
import com.ksource.liangfa.mapper.WorkReportReceiveMapper;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.syscontext.Const;

@Service
@Transactional
public class WorkReportServiceImpl implements WorkReportService{

	@Autowired
	WorkReportMapper workReportMapper;
	@Autowired
	SystemDAO systemDAO;
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;
	@Autowired
	private WorkReportReceiveMapper workReportReceiveMapper;
	@Autowired
    private InstantMessageService instantMessageService;
	
    public int getSequenceId() {
        return systemDAO.getSeqNextVal(Const.TABLE_WORK_REPORT);
    }
	
	@Override
	public void add(WorkReport workReport,MultipartHttpServletRequest attachmentFile) {
		Integer reportId = getSequenceId();
		workReport.setReportId(reportId);
		workReportMapper.insert(workReport);
		
		if(workReport.getReceiveOrg()!=null){
			String receiveOrgs=workReport.getReceiveOrg();
			String[] org = receiveOrgs.split(",");
			for (int i = 0; i < org.length; i++) {
				String orgId = org[i].trim().toString();
				//循环插入接收表
				WorkReportReceive reportReceive=new WorkReportReceive();
				reportReceive.setReceiveId(systemDAO.getSeqNextVal(Const.TABLE_WORK_REPORT_RECEIVE));
				reportReceive.setReportId(reportId);
				reportReceive.setTitle(workReport.getTitle());
				reportReceive.setSendOrg(workReport.getSendOrg());
				reportReceive.setSendTime(workReport.getSendTime());
				reportReceive.setReceiveOrg(orgId);
				reportReceive.setContent(workReport.getContent());
				reportReceive.setSendOrgName(workReport.getSendOrgName());
				reportReceive.setReadStatus(Const.READ_STATUS_NO);
				workReportReceiveMapper.insert(reportReceive);
				instantMessageService.addWorkReportMessage(reportReceive,workReport.getCreator(),orgId);
			}
		}
		
		List<MultipartFile> files = attachmentFile.getFiles("file");
		PublishInfoFile publishInfoFile = null;
		for (int i = 0; i < files.size(); i++) {
			MultipartFile mFile = files.get(i);
			if (mFile != null && !mFile.isEmpty()) {
				UpLoadContext upLoad = new UpLoadContext(
						new UploadResource());
				String url = upLoad.uploadFile(mFile, null);
				String fileName = mFile.getOriginalFilename();
				publishInfoFile = new PublishInfoFile();
				publishInfoFile.setFileId(systemDAO.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
				publishInfoFile.setInfoId(workReport.getReportId());
				publishInfoFile.setFileName(fileName);
				publishInfoFile.setFilePath(url);
				publishInfoFile.setFileType(Const.TABLE_WORK_REPORT);
				publishInfoFileMapper.insert(publishInfoFile);
			}
		}
	}

	@Override
	public PaginationHelper<WorkReport> find(WorkReport workReport, String page) {
		return systemDAO.find(workReport, page, null);
	}

	@Override
	public WorkReport selectByPrimaryKey(Integer reportId) {
		return workReportMapper.selectByPrimaryKey(reportId);
	}

	@Override
	public int update(WorkReport workReport,MultipartHttpServletRequest attachmentFile,String deletedFileId) {
		//先删除app消息提醒
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("reportId", workReport.getReportId());
		List<WorkReportReceive> receivelist=workReportReceiveMapper.getListByReportId(map);
		if(receivelist.size()>0){
			for(WorkReportReceive receive:receivelist){
				instantMessageService.deleteByBusinessKey(receive.getReceiveId().toString());
			}
		}
		//删除WorkReportReceive相关信息
		WorkReportReceiveExample receiveExample = new WorkReportReceiveExample();
		receiveExample.createCriteria().andReportIdEqualTo(workReport.getReportId());
		workReportReceiveMapper.deleteByExample(receiveExample);
		if(workReport.getReceiveOrg()!=null){
			String receiveOrgs=workReport.getReceiveOrg();
			String[] org = receiveOrgs.split(",");
			for (int i = 0; i < org.length; i++) {
				String orgId = org[i].trim().toString();
				WorkReportReceive reportReceive=new WorkReportReceive();
				reportReceive.setReceiveId(systemDAO.getSeqNextVal(Const.TABLE_WORK_REPORT_RECEIVE));
				reportReceive.setReportId(workReport.getReportId());
				reportReceive.setTitle(workReport.getTitle());
				reportReceive.setSendOrg(workReport.getSendOrg());
				reportReceive.setSendTime(workReport.getSendTime());
				reportReceive.setReceiveOrg(orgId);
				reportReceive.setContent(workReport.getContent());
				reportReceive.setSendOrgName(workReport.getSendOrgName());
				reportReceive.setReadStatus(Const.READ_STATUS_NO);
				workReportReceiveMapper.insert(reportReceive);
				//添加app消息提醒
				instantMessageService.addWorkReportMessage(reportReceive,workReport.getCreator(),orgId);
			}
		}		
		List<MultipartFile> files = attachmentFile.getFiles("file");
		PublishInfoFile publishInfoFile = null;
		if(StringUtils.isNotBlank(deletedFileId)){
			String[] fileIdAry = deletedFileId.split(",");
			for(String f:fileIdAry){
				PublishInfoFile pf = publishInfoFileMapper.selectByPrimaryKey(Integer.valueOf(f));
				FileUtil.deleteFileInDisk(pf.getFilePath());
				publishInfoFileMapper.deleteByPrimaryKey(Integer.valueOf(f));
			}
		}
		for (int i = 0; i < files.size(); i++) {
			MultipartFile mFile = files.get(i);
			if (mFile != null && !mFile.isEmpty()) {
				UpLoadContext upLoad = new UpLoadContext(
						new UploadResource());
				String url = upLoad.uploadFile(mFile, null);
				String fileName = mFile.getOriginalFilename();
				publishInfoFile = new PublishInfoFile();
				publishInfoFile.setFileId(systemDAO.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
				publishInfoFile.setInfoId(workReport.getReportId());
				publishInfoFile.setFileName(fileName);
				publishInfoFile.setFilePath(url);
				publishInfoFile.setFileType(Const.TABLE_WORK_REPORT);
				publishInfoFileMapper.insert(publishInfoFile);
			}
		}
		return workReportMapper.updateByPrimaryKeySelective(workReport);
		
	}

	@Override
	public int del(Integer reportId) {
		//先删除app消息提醒
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("reportId", reportId);
		List<WorkReportReceive> receivelist=workReportReceiveMapper.getListByReportId(map);
		if(receivelist.size()>0){
			for(WorkReportReceive receive:receivelist){
				instantMessageService.deleteByBusinessKey(receive.getReceiveId().toString());
			}
		}
		//删除WorkReportReceive相关信息
		WorkReportReceiveExample receiveExample = new WorkReportReceiveExample();
		receiveExample.createCriteria().andReportIdEqualTo(reportId);
		workReportReceiveMapper.deleteByExample(receiveExample);
		//删除附件信息
		PublishInfoFileExample fileExample = new PublishInfoFileExample();
		fileExample.createCriteria().andInfoIdEqualTo(reportId).andFileTypeEqualTo(Const.TABLE_WORK_REPORT);
		List<PublishInfoFile> files = publishInfoFileMapper.selectByExample(fileExample);
		for(PublishInfoFile f:files){
			FileUtil.deleteFileInDisk(f.getFilePath());
			publishInfoFileMapper.deleteByPrimaryKey(f.getFileId());
		}
		return workReportMapper.deleteByPrimaryKey(reportId);
	}

	@Override
	public WorkReport selectById(Integer reportId) {
		return workReportMapper.selectById(reportId);
	}

    @Override
    public WorkReport selectByReceiveId(Integer receiveId) {
        return workReportMapper.selectByReceiveId(receiveId);
    }
}

