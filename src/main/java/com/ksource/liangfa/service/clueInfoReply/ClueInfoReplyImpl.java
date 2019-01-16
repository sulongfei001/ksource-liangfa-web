package com.ksource.liangfa.service.clueInfoReply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.ClueCaseAndReply;
import com.ksource.liangfa.domain.ClueDispatch;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.ClueInfoReply;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.mapper.ClueDispatchMapper;
import com.ksource.liangfa.mapper.ClueInfoMapper;
import com.ksource.liangfa.mapper.ClueInfoReplyMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.syscontext.Const;

@Service
public class ClueInfoReplyImpl implements ClueInfoReplyService{
	@Autowired
	private ClueInfoReplyMapper clueInfoReplyMapper;
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;
	@Autowired
	private ClueInfoMapper clueInfoMapper;
	@Autowired
	private ClueDispatchMapper clueDispatchMapper;
	@Autowired
	private InstantMessageService instantMessageService;

	@Override
	public boolean add(ClueInfoReply clueReply,MultipartHttpServletRequest multipartRequest,Integer receiveOrgCode) {
		//保存附件
		uploadFile(clueReply.getReplyId(), multipartRequest.getFiles("attachMent_s"), Const.TABLE_CLUE_REPLY);
		
		ClueInfo clueInfo = new ClueInfo(clueReply.getClueInfoId());
		//设置线索状态
		clueInfo.setClueState(3);
		//设置 读取状态
		clueInfo.setIsReceive(1);
		clueInfoMapper.updateByPrimaryKeySelective(clueInfo);
		
		
		ClueDispatch dispatch=new ClueDispatch();
		dispatch.setDispatchCasestate(Const.CLUE_STATE_YIHUIFU);
		dispatch.setClueId(clueReply.getClueInfoId());
		dispatch.setReceiveOrg(receiveOrgCode);
		clueDispatchMapper.updateDispatchState(dispatch);
		//删除app待处理线索消息提醒
		instantMessageService.deleteByBusinessKey(clueReply.getClueInfoId().toString());
		return clueInfoReplyMapper.add(clueReply);
	}
	
	//文件上传工具类
	private void uploadFile(Integer id, List<MultipartFile> attachMent,String tableName) {
		PublishInfoFile publishInfoFile = null;
		for (int i = 0; i < attachMent.size(); i++) {
			try {
				MultipartFile mFile = attachMent.get(i);
				if (mFile != null && !mFile.isEmpty()) {
					UpLoadContext upLoad = new UpLoadContext(
							new UploadResource());
					String url = upLoad.uploadFile(mFile,null);
					String fileName = mFile.getOriginalFilename();
					publishInfoFile = new PublishInfoFile();
					//设置文件存储路径
					publishInfoFile.setFilePath(url);
					publishInfoFile.setFileId(systemDao.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
					publishInfoFile.setInfoId(id);
					publishInfoFile.setFileName(fileName);
					publishInfoFile.setFileType(tableName);
					publishInfoFileMapper.insert(publishInfoFile);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<ClueInfoReply> getListByClueInfoId(ClueInfo clueInfo) {
		List<ClueInfoReply> replys = clueInfoReplyMapper.getListByClueInfoId(clueInfo);
		for (ClueInfoReply reply : replys) {
			PublishInfoFileExample example = new PublishInfoFileExample();
			example.createCriteria().andInfoIdEqualTo(reply.getReplyId()).andFileTypeEqualTo(Const.TABLE_CLUE_REPLY);
			List<PublishInfoFile> attFiles = publishInfoFileMapper.selectByExample(example);
			
			reply.setAttMents(attFiles);
		}
		return replys;
	}

	@Override
	public ClueInfoReply getReplyById(Integer replyId) {
		ClueInfoReply reply = clueInfoReplyMapper.getReplyById(replyId);
		PublishInfoFileExample example = new PublishInfoFileExample();
		example.createCriteria().andInfoIdEqualTo(reply.getReplyId()).andFileTypeEqualTo(Const.TABLE_CLUE_REPLY);
		List<PublishInfoFile> attFiles = publishInfoFileMapper.selectByExample(example);
		reply.setAttMents(attFiles);
		
		return reply;
	}

	@Override
	public List<ClueInfoReply> getClueCaseList(Integer clueInfoId) {
		List<ClueInfoReply> dealList=clueInfoReplyMapper.getClueCaseList(clueInfoId);
		return dealList;
	}
	
	
}
