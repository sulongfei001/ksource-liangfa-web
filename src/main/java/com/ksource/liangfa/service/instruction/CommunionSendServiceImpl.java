package com.ksource.liangfa.service.instruction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CommunionReceive;
import com.ksource.liangfa.domain.CommunionReceiveExample;
import com.ksource.liangfa.domain.CommunionSend;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.WorkReportReceive;
import com.ksource.liangfa.mapper.CommunionReceiveMapper;
import com.ksource.liangfa.mapper.CommunionReplyMapper;
import com.ksource.liangfa.mapper.CommunionSendMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.syscontext.Const;

@Service
@Transactional
public class CommunionSendServiceImpl implements CommunionSendService{
	@Autowired
	CommunionSendMapper communionSendMapper;
	@Autowired
	SystemDAO systemDao;
	@Autowired
	PublishInfoFileMapper publishInfoFileMapper;
	@Autowired
	CommunionReceiveMapper communionReceiveMapper;
	@Autowired
	CommunionReplyMapper communionReplyMapper;
	@Autowired
	OrganiseMapper organiseMapper;
	@Autowired
	InstantMessageService instantMessageService;
	// 日志
	private static final Logger log = LogManager.getLogger(CommunionSendServiceImpl.class);
	
	@Override
	public List<CommunionSend> queryCommunionSend(CommunionSend communionSend) {
		return null;
	}

	@Override
	public PaginationHelper<CommunionSend> find(CommunionSend communionSend,
			String page, Map<String, Object> params) {
		return systemDao.find(communionSend,page,null);
	}

	@Transactional
	@LogBusiness(operation = "添加 横向交流 信息", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = CommunionSendMapper.class)
	public ServiceResponse save(CommunionSend communionSend,
			MultipartHttpServletRequest attachmentFile) {
		try {
			communionSendMapper.insertSelective(communionSend);
			
			//如果接收单位不为空，批量插入接收单位
			if(communionSend.getReceiveOrg()!=null){
				CommunionReceive communionReceive;
				String receiveOrgs=communionSend.getReceiveOrg();
				String[] org = receiveOrgs.split(",");
				for(int i=0;i<org.length;i++){
					String orgId = org[i].trim().toString();
					communionReceive=new CommunionReceive();
					communionReceive.setReceiveId(systemDao.getSeqNextVal(Const.TABLE_COMMUNION_RECEIVE));
					communionReceive.setReceiveOrg(orgId);
					communionReceive.setSendOrg(communionSend.getSendOrg());
					communionReceive.setCommunionId(communionSend.getCommunionId());
					communionReceive.setContent(communionSend.getContent());
					communionReceive.setSendTime(communionSend.getSendTime());
					communionReceive.setTitle(communionSend.getTitle());
					communionReceive.setReadStatus(Const.READ_STATUS_NO);
					communionReceiveMapper.insertSelective(communionReceive);
		               //APP 添加提醒消息
	                instantMessageService.addCommuionMessage(communionReceive,communionSend.getCreator(),orgId);
				}
			}
			
			//添加附件
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
					publishInfoFile.setFileId(systemDao.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
					publishInfoFile.setInfoId(communionSend.getCommunionId());
					publishInfoFile.setFileName(fileName);
					publishInfoFile.setFilePath(url);
					publishInfoFile.setFileType(Const.TABLE_COMMUNION_SEND);
					publishInfoFileMapper.insert(publishInfoFile);
				}
			}
			
			return new ServiceResponse(true, "添加横向交流信息成功!");
		} catch (Exception e) {
			log.error("添加横向交流信息失败：" + e.getMessage());
			throw new BusinessException("添加横向交流信息失败");
		}
	}

	@Override
	public CommunionSend getById(Integer communionId) {
		CommunionSend communionSend=communionSendMapper.getById(communionId);
/*		if(communionSend.getReceiveOrg()!=null){
			//通过接收id获取接收组织机构name
			String[] receiveOrg=communionSend.getReceiveOrg().split(",");
			List<String> receiveOrgCodeList=new ArrayList<String>();
			for(int i=0;i<receiveOrg.length;i++){
				receiveOrgCodeList.add(receiveOrg[i]);
			}
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("orgCodeList", receiveOrgCodeList);
			List<Organise> organiseList=organiseMapper.getByOrgCodes(param);
			String orgName="";
			for(Organise organise:organiseList){
				orgName+=organise.getOrgName()+",";
			}
			communionSend.setReceiveOrgName(orgName.substring(0, orgName.length()-1));
		}*/
		return communionSend;
	}

	@Override
	public int del(Integer communionId) {
		//先删除app消息提醒
		CommunionReceive communionReceive=new CommunionReceive();
		communionReceive.setCommunionId(communionId);
		List<CommunionReceive> receivelist=communionReceiveMapper.getCommunionReceiveList(communionReceive);
		if(receivelist.size()>0){
			for(CommunionReceive receive:receivelist){
				instantMessageService.deleteByBusinessKey(receive.getReceiveId().toString());
			}
		}
		//删除CommunionReceive信息
		CommunionReceiveExample example=new CommunionReceiveExample();
		example.createCriteria().andCommunionIdEqualTo(communionId);
		communionReceiveMapper.deleteByExample(example);
		//删除附件信息
		PublishInfoFileExample fileExample = new PublishInfoFileExample();
		fileExample.createCriteria().andInfoIdEqualTo(communionId).andFileTypeEqualTo(Const.TABLE_COMMUNION_SEND);
		List<PublishInfoFile> files = publishInfoFileMapper.selectByExample(fileExample);
		for(PublishInfoFile f:files){
			FileUtil.deleteFileInDisk(f.getFilePath());
			publishInfoFileMapper.deleteByPrimaryKey(f.getFileId());
		}
		//删除回复信息
		communionReplyMapper.deleteByCommunionId(communionId);
		return communionSendMapper.deleteByPrimaryKey(communionId);
	}

	@Override
	public int update(CommunionSend communionSend,
			MultipartHttpServletRequest attachmentFile, String deletedFileId) {
		//先删除app消息提醒
		CommunionReceive param=new CommunionReceive();
		param.setCommunionId(communionSend.getCommunionId());
		List<CommunionReceive> receivelist=communionReceiveMapper.getCommunionReceiveList(param);
		if(receivelist.size()>0){
			for(CommunionReceive receive:receivelist){
				instantMessageService.deleteByBusinessKey(receive.getReceiveId().toString());
			}
		}
		//删除CommunionReceive信息
		CommunionReceiveExample receiveExample=new CommunionReceiveExample();
		receiveExample.createCriteria().andCommunionIdEqualTo(communionSend.getCommunionId());
		communionReceiveMapper.deleteByExample(receiveExample);
		//如果接收单位不为空，批量插入接收单位
		if(communionSend.getReceiveOrg()!=null){
			CommunionReceive communionReceive;
			String receiveOrgs=communionSend.getReceiveOrg();
			String[] org = receiveOrgs.split(",");
			for(int i=0;i<org.length;i++){
				String orgId = org[i].trim().toString();
				communionReceive=new CommunionReceive();
				communionReceive.setReceiveId(systemDao.getSeqNextVal(Const.TABLE_COMMUNION_RECEIVE));
				communionReceive.setReceiveOrg(orgId);
				communionReceive.setSendOrg(communionSend.getSendOrg());
				communionReceive.setCommunionId(communionSend.getCommunionId());
				communionReceive.setContent(communionSend.getContent());
				communionReceive.setSendTime(communionSend.getSendTime());
				communionReceive.setTitle(communionSend.getTitle());
				communionReceive.setReadStatus(Const.READ_STATUS_NO);
				communionReceiveMapper.insertSelective(communionReceive);
				//APP 添加提醒消息
                instantMessageService.addCommuionMessage(communionReceive,communionSend.getCreator(),orgId);
			}
		}
		//添加附件
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
				publishInfoFile.setFileId(systemDao.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
				publishInfoFile.setInfoId(communionSend.getCommunionId());
				publishInfoFile.setFileName(fileName);
				publishInfoFile.setFilePath(url);
				publishInfoFile.setFileType(Const.TABLE_COMMUNION_SEND);
				publishInfoFileMapper.insert(publishInfoFile);
			}
		}
		
		return communionSendMapper.updateByPrimaryKeySelective(communionSend);
	}

    @Override
    public CommunionSend getByReceiveId(Integer receiveId) {
        return communionSendMapper.getByReceiveId(receiveId);
    }

}
