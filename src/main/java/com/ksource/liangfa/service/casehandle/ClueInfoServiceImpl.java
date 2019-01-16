package com.ksource.liangfa.service.casehandle;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.ClueAccept;
import com.ksource.liangfa.domain.ClueCaseAndReply;
import com.ksource.liangfa.domain.ClueDispatch;
import com.ksource.liangfa.domain.ClueDispatchExample;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.ClueInfoExample;
import com.ksource.liangfa.domain.HotlineInfo;
import com.ksource.liangfa.domain.HotlineInfoExample;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CasePartyMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.mapper.ClueAcceptMapper;
import com.ksource.liangfa.mapper.ClueCaseAndReplyMapper;
import com.ksource.liangfa.mapper.ClueDispatchMapper;
import com.ksource.liangfa.mapper.ClueInfoMapper;
import com.ksource.liangfa.mapper.ClueInfoReplyMapper;
import com.ksource.liangfa.mapper.HotlineInfoMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;

@Service
@Transactional
public class ClueInfoServiceImpl implements ClueInfoService {

	@Autowired
	private ClueInfoMapper clueInfoMapper;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private ClueDispatchMapper clueDispatchMapper;
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;
    @Autowired
    CaseXianyirenMapper caseXianyirenMapper;
    @Autowired
    CasePartyMapper casePartyMapper;
    @Autowired
    CaseCompanyMapper caseCompanyMapper;
    @Autowired
    ClueCaseAndReplyMapper clueCaseAndReplyMapper;
    @Autowired
    private InstantMessageService instantMessageService;
    @Autowired
    private ClueAcceptMapper clueAcceptMapper;
    @Autowired
    private ClueInfoReplyMapper clueInfoReplyMapper;
	@Autowired
	private CaseBasicMapper caseBasicMapper;
    
	@Autowired
	private HotlineInfoMapper hotlineInfoMapper;
	/**
	 * 线索录入
	 */
	@Override
	public ServiceResponse saveClueInfo(ClueInfo clueInfo,
			MultipartRequest multipartRequest, User user) {
		ServiceResponse res = new ServiceResponse(true,"线索录入成功");
		Date currentDate = new Date();
		try {
			String userId = user.getUserId();
			Integer orgId = user.getOrgId();
			//创建人ID
			clueInfo.setCreateUser(userId);
			//创建人所在部门ID
			clueInfo.setCreateOrg(orgId);
			clueInfo.setCreateTime(currentDate);
			//将线索状态改为1，待分派
			clueInfo.setClueState(Const.CLUE_STATE_DAIFENPAI);
			
			
			//是否接受
			clueInfo.setIsReceive(Const.TABLE_CLUE_ISRECEIVE);
			
			Integer id = clueInfo.getClueId();
			if(id==null){
				
				//设置创建人所在的区域
				clueInfo.setCreateDistrictcode(user.getOrganise().getDistrictCode());
				//添加线索
				clueInfo.setClueId(systemDAO.getSeqNextVal(Const.TABLE_CLUE_INFO));
				clueInfoMapper.insertSelective(clueInfo);
			}else{
				//更新
				clueInfoMapper.updateByPrimaryKeySelective(clueInfo);
			}
			
			if(clueInfo.getHotlineId() != null){
				HotlineInfo hotline = new HotlineInfo();
				hotline.setInfoId(clueInfo.getHotlineId());
				hotline.setHotlineState(1);//已转换为线索
				hotlineInfoMapper.updateByPrimaryKeySelective(hotline);
			}
			
			
			//添加附件
			uploadFile(clueInfo.getClueId(), multipartRequest.getFiles("attachMent_s"),Const.TABLE_CLUE_INFO);
			//添加APP待分派线索提醒
			instantMessageService.addClueDispacthMessage(clueInfo);
		} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
		return res;
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
					publishInfoFile.setFileId(systemDAO.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
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

	/**
	 * 获取线索分页列表
	 */
	@Override
	public PaginationHelper<ClueInfo> getClueInfoList(ClueInfo clueInfo,
			String page) {
		return systemDAO.find(clueInfo, null, page, 
				"com.ksource.liangfa.mapper.ClueInfoMapper.getClueInfoCount",
				"com.ksource.liangfa.mapper.ClueInfoMapper.getClueInfoList");
	}
	@Override
	public PaginationHelper<ClueInfo> getInputClueList(ClueInfo clueInfo,
			String page) {
		return systemDAO.find(clueInfo, null, page, 
				"com.ksource.liangfa.mapper.ClueInfoMapper.getInputClueCount",
				"com.ksource.liangfa.mapper.ClueInfoMapper.getInputClueList");
	}
	@Override
	public ServiceResponse clueFenpai(Integer id,String reOrg, User user) {
		ServiceResponse res = new ServiceResponse(true,"线索分派成功！");
		try {
			if(id!=null){
				String[] reOrgs = reOrg.split(",");
				if(reOrgs.length > 0){
					
					for (int i = 0; i < reOrgs.length; i++) {
						
					Integer clueId = Integer.valueOf(id);
					//在clue_info中将clue_state状态改为2，已分派
					Date currentDate = new Date();
					ClueInfo clueInfo = new ClueInfo();
					clueInfo.setClueId(clueId);
					clueInfo.setClueState(Const.CLUE_STATE_YIFENPAI);
					clueInfoMapper.updateByPrimaryKeySelective(clueInfo);
					//删除app待分派线索信息
					instantMessageService.deleteByBusinessKey(clueId.toString());
					
					ClueDispatch clueDispatch = new ClueDispatch();
					clueDispatch.setReceiveOrg(Integer.valueOf(reOrgs[i]));
					//在clue_dispatch中插入一条记录
					clueDispatch.setClueId(clueId);
					clueDispatch.setDispatchId(systemDAO.getSeqNextVal(Const.TABLE_CLUE_DISPATCH));
					clueDispatch.setDispatchOrg(user.getOrgId());
					clueDispatch.setDispatchUser(user.getUserId());
					clueDispatch.setDispatchTime(currentDate);
					clueDispatchMapper.insert(clueDispatch);
					
					//设置分派人
					ClueInfo clue = new ClueInfo();
					clue.setClueId(clueId);
					clue.setDistributeUserId(user.getUserId());
					clueInfoMapper.updateByPrimaryKeySelective(clue);
					
					instantMessageService.addClueHandleMessage(clueId,user.getUserId(),reOrgs[i]);
					}
					
				}
			}
	
		
		} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
		return res;
	}
	
	/**行政单位未办理线索列表*/
	@Override
	public PaginationHelper<ClueDispatch> notDealClueList(ClueDispatch clueDispatch,
			String page) {
		return systemDAO.find(clueDispatch, null, page, 
				"com.ksource.liangfa.mapper.ClueDispatchMapper.getNotDealClueCount", 
				"com.ksource.liangfa.mapper.ClueDispatchMapper.getNotDealClueList");
	}

	@Override
	public ClueInfo selectByPrimaryKey(Integer clueId) {
		ClueInfo clue = clueInfoMapper.selectByPrimaryKey(clueId);
		PublishInfoFileExample example = new PublishInfoFileExample();
		example.createCriteria().andInfoIdEqualTo(clueId).andFileTypeEqualTo(Const.TABLE_CLUE_INFO);
		List<PublishInfoFile> attFiles = publishInfoFileMapper.selectByExample(example);
		clue.setAttments(attFiles);
		return clue;
	}
	@Override
	public ServiceResponse batchDeleteClue(Integer[] clueIds){
		ServiceResponse response = new ServiceResponse(true, "批量删除线索成功!");
		ClueInfoExample example = new ClueInfoExample();
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		ClueDispatchExample clueDispatchExample = new ClueDispatchExample();
		try {
			ClueInfo clue = null;
			for(Integer clueId:clueIds){
				example.clear();
				publishInfoFileExample.clear();
				example.createCriteria().andClueIdEqualTo(clueId);
				clue = clueInfoMapper.selectByPrimaryKey(clueId);
				if(clue != null){
					publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_CLUE_INFO).andInfoIdEqualTo(clueId);
					List<PublishInfoFile> publishInfoFiles = publishInfoFileMapper.selectByExample(
							publishInfoFileExample);
					for(PublishInfoFile publishInfoFile : publishInfoFiles) {
						FileUtil.deleteFileInDisk(publishInfoFile.getFilePath());
						publishInfoFileMapper.deleteByPrimaryKey(publishInfoFile.getFileId());
					}
					clueInfoMapper.deleteByPrimaryKey(clueId);
					//删除分派表信息
					clueDispatchExample.clear();
					clueDispatchExample.createCriteria().andClueIdEqualTo(clueId);
					clueDispatchMapper.deleteByExample(clueDispatchExample);
					//删除回复表
					clueInfoReplyMapper.deleteByClueId(clueId);
					//更新案件信息表
					caseBasicMapper.updateClueId(clueId);

					
					
					//删除app消息
					instantMessageService.deleteByBusinessKey(clueId.toString());
				}
			}
		} catch (Exception e) {
			response.setResult(false);
			throw new BusinessException("批量删除线索失败");
		}
		return response;
	}


	@Override
	public List<ClueDispatch> clueReadList(Integer clueInfo) {
		List<ClueDispatch> readList=clueDispatchMapper.getClueReadList(clueInfo);
		return readList;
	}

	@Override
	public List<ClueCaseAndReply> clueCaseList(Integer clueInfo) {
		List<ClueCaseAndReply> dealList=clueCaseAndReplyMapper.getClueCaseList(clueInfo);
		return dealList;
	}

	@Override
	public PaginationHelper<ClueDispatch> haveDealClueList(
			ClueDispatch clueDispatch, String page) {
		return systemDAO.find(clueDispatch, null, page, 
				"com.ksource.liangfa.mapper.ClueDispatchMapper.getHaveDealClueCount", 
				"com.ksource.liangfa.mapper.ClueDispatchMapper.getHaveDealClueList");
	}

	@Override
	public List<ClueDispatch> getClueReadInfo(Map<String, Object> map) {
		return clueDispatchMapper.getClueReadInfo(map);
	}

	@Override
	public List<ClueAccept> getClueTransactInfo(Map<String, Object> map) {
		return clueAcceptMapper.getClueTransactInfo(map);
	}
}
