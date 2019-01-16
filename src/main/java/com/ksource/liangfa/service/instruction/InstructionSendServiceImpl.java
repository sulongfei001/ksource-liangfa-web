package com.ksource.liangfa.service.instruction;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.InstructionReceive;
import com.ksource.liangfa.domain.InstructionReceiveExample;
import com.ksource.liangfa.domain.InstructionReplyExample;
import com.ksource.liangfa.domain.InstructionSend;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.mapper.InstructionReceiveMapper;
import com.ksource.liangfa.mapper.InstructionReplyMapper;
import com.ksource.liangfa.mapper.InstructionSendMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.syscontext.Const;

@Service
@Transactional
public class InstructionSendServiceImpl implements InstructionSendService {
	
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private InstructionSendMapper instructionSendMapper;
	@Autowired
	private InstructionReceiveMapper instructionReceiveMapper;
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;
	@Autowired
	private InstructionReplyMapper instructionReplyMapper;
	@Autowired
	private InstantMessageService instantMessageService;
	// 日志
	private static final Logger log = LogManager .getLogger(InstructionSendServiceImpl.class);

	@Override
	public PaginationHelper<InstructionSend> find( InstructionSend instructionSend, String page) {
		try {
			return systemDao.find(instructionSend, page);
		} catch (Exception e) {
			log.error("查询失败：" + e.getMessage());
			throw new BusinessException("查询失败");
		}
	}

	@Override
	@Transactional
	public ServiceResponse insert(InstructionSend instructionSend,MultipartHttpServletRequest attachmentFile,Integer fileId) {
		ServiceResponse response = new ServiceResponse(true, "工作指令下发成功!");
		List<MultipartFile> files = attachmentFile.getFiles("file");
		PublishInfoFile publishInfoFile = null;
		try {
			instructionSend.setInstructionId(systemDao.getSeqNextVal(Const.TABLE_INSTRUCTION_SEND));
			instructionSendMapper.insert(instructionSend);
			//插入下发表后要同时插入接收表
			if(instructionSend.getReceiveOrg()!=null){
				String receiveOrgs=instructionSend.getReceiveOrg();
				String[] org = receiveOrgs.split(",");
				for (int i = 0; i < org.length; i++) {
					String orgId = org[i].trim().toString();
					//循环插入接收表
					InstructionReceive instructionReceive=new InstructionReceive();
					instructionReceive.setReceiveId(systemDao.getSeqNextVal(Const.TABLE_INSTRUCTION_RECEIVE));
					instructionReceive.setInstructionId(instructionSend.getInstructionId());
					instructionReceive.setTitle(instructionSend.getTitle());
					instructionReceive.setSendOrg(instructionSend.getSendOrg());
					instructionReceive.setSendTime(instructionSend.getSendTime());
					instructionReceive.setReceiveOrg(orgId);
					instructionReceive.setCompleteTime(new Date());
					instructionReceive.setContent(instructionSend.getContent());
					instructionReceive.setStatus(1);
					instructionReceiveMapper.insert(instructionReceive);
					instantMessageService.addInstructionMessage(instructionReceive,instructionSend.getCreator(),orgId);
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
					publishInfoFile.setFileId(systemDao.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
					publishInfoFile.setInfoId(instructionSend.getInstructionId());
					publishInfoFile.setFileName(fileName);
					publishInfoFile.setFilePath(url);
					publishInfoFile.setFileType(Const.TABLE_INSTRUCTION_SEND);
					publishInfoFileMapper.insert(publishInfoFile);
				}
			}
			
			if(fileId!=null && fileId>0){
				publishInfoFile = new PublishInfoFile();
				publishInfoFile=publishInfoFileMapper.selectByPrimaryKey(fileId);
				publishInfoFile.setFileId(systemDao.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
				publishInfoFile.setInfoId(instructionSend.getInstructionId());
				publishInfoFile.setFileType(Const.TABLE_INSTRUCTION_SEND);
				publishInfoFileMapper.insert(publishInfoFile);
			}
			return response;
		} catch (Exception e) {
			log.error("工作指令下发失败：" + e.getMessage());
			throw new BusinessException("工作指令下发失败");
		}
	}

	@Override
	public ServiceResponse updateByPrimaryKeySelective(InstructionSend instructionSend) {
		ServiceResponse response = new ServiceResponse(true, "修改工作指令下发成功!");
		try {
			instructionSendMapper.updateByPrimaryKeySelective(instructionSend);
			return response;
		} catch (Exception e) {
			log.error("修改工作指令下发失败：" + e.getMessage());
			throw new BusinessException("修改工作指令下发失败");
		}
	}
	
	
	@Override
	@Transactional
	public int del(Integer id) {
		try {
			//删除app信息
			InstructionReceive receive=new InstructionReceive();
			receive.setInstructionId(id);
			List<InstructionReceive> receiveList=instructionReceiveMapper.queryAll(receive);
			if(receiveList.size()>0){
				for(InstructionReceive instructionReceive:receiveList){
					instantMessageService.deleteByBusinessKey(instructionReceive.getReceiveId().toString());
				}
			}
			//先删除接收指令表关联信息
			InstructionReceiveExample example=new InstructionReceiveExample();
			example.createCriteria().andInstructionIdEqualTo(id);
			instructionReceiveMapper.deleteByExample(example);
			//删除附件信息
			PublishInfoFileExample fileExample = new PublishInfoFileExample();
			fileExample.createCriteria().andInfoIdEqualTo(id).andFileTypeEqualTo(Const.TABLE_INSTRUCTION_SEND);
			List<PublishInfoFile> files = publishInfoFileMapper.selectByExample(fileExample);
			for(PublishInfoFile f:files){
				FileUtil.deleteFileInDisk(f.getFilePath());
				publishInfoFileMapper.deleteByPrimaryKey(f.getFileId());
			}
			//删除回复记录表
			InstructionReplyExample instructionReplyExample = new InstructionReplyExample();
			instructionReplyExample.createCriteria().andInstructionIdEqualTo(id);
			instructionReplyMapper.deleteByExample(instructionReplyExample);
			
			//删除发送表
			return instructionSendMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			log.error("删除工作指令下发失败：" + e.getMessage());
			throw new BusinessException("删除工作指令下发失败");
		}
	}

	@Override
	public ServiceResponse update(InstructionSend instructionSend,
			MultipartHttpServletRequest attachmentFile,String deletedFileId) {
		ServiceResponse response = new ServiceResponse(true, "工作指令修改成功!");
		List<MultipartFile> files = attachmentFile.getFiles("file");
		PublishInfoFile publishInfoFile = null;
		try {
			//更新接收表信息 1、先删除 2、再添加
			//先删除app消息提醒
			InstructionReceive receive=new InstructionReceive();
			receive.setInstructionId(instructionSend.getInstructionId());
			List<InstructionReceive> receiveList=instructionReceiveMapper.queryAll(receive);
			if(receiveList.size()>0){
				for(InstructionReceive instructionReceive:receiveList){
					instantMessageService.deleteByBusinessKey(instructionReceive.getReceiveId().toString());
				}
			}
			//再删除InstructionReceive信息
			InstructionReceiveExample example=new InstructionReceiveExample();
			example.createCriteria().andInstructionIdEqualTo(instructionSend.getInstructionId());
			instructionReceiveMapper.deleteByExample(example);
			
			//插入下发表后要同时插入接收表
			if(instructionSend.getReceiveOrg()!=null){
				String receiveOrgs=instructionSend.getReceiveOrg();
				String[] org = receiveOrgs.split(",");
				for (int i = 0; i < org.length; i++) {
					String orgId = org[i].trim().toString();
					//循环插入接收表
					InstructionReceive instructionReceive=new InstructionReceive();
					instructionReceive.setReceiveId(systemDao.getSeqNextVal(Const.TABLE_INSTRUCTION_RECEIVE));
					instructionReceive.setInstructionId(instructionSend.getInstructionId());
					instructionReceive.setTitle(instructionSend.getTitle());
					instructionReceive.setSendOrg(instructionSend.getSendOrg());
					instructionReceive.setSendTime(instructionSend.getSendTime());
					instructionReceive.setReceiveOrg(orgId);
					instructionReceive.setCompleteTime(new Date());
					instructionReceive.setContent(instructionSend.getContent());
					instructionReceive.setStatus(1);
					instructionReceiveMapper.insert(instructionReceive);
					//添加app消息提醒
					instantMessageService.addInstructionMessage(instructionReceive,instructionSend.getCreator(),orgId);
				}
			}
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
					publishInfoFile.setFileId(systemDao.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
					publishInfoFile.setInfoId(instructionSend.getInstructionId());
					publishInfoFile.setFileName(fileName);
					publishInfoFile.setFilePath(url);
					publishInfoFile.setFileType(Const.TABLE_INSTRUCTION_SEND);
					publishInfoFileMapper.insert(publishInfoFile);
				}
			}
			instructionSendMapper.updateByPrimaryKeySelective(instructionSend);
			return response;
		} catch (Exception e) {
			log.error("工作指令修改失败：" + e.getMessage());
			throw new BusinessException("工作指令修改失败");
		}		
	}

	@Override
	public InstructionSend selectByPrimaryKey(Integer instructionId) {
		return instructionSendMapper.selectByPrimaryKey(instructionId);
	}
}
