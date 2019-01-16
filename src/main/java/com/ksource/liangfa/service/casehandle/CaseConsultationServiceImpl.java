package com.ksource.liangfa.service.casehandle;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseConsultation;
import com.ksource.liangfa.domain.CaseConsultationContent;
import com.ksource.liangfa.domain.CaseConsultationContentExample;
import com.ksource.liangfa.domain.Participants;
import com.ksource.liangfa.domain.ParticipantsExample;
import com.ksource.liangfa.domain.ParticipantsKey;
import com.ksource.liangfa.mapper.CaseConsultationContentMapper;
import com.ksource.liangfa.mapper.CaseConsultationMapper;
import com.ksource.liangfa.mapper.ParticipantsMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.syscontext.Const;

/**
 * 
 * @author zxl :)
 * @version 1.0 date 2011-8-29 time 上午11:40:33
 */
@Service
public class CaseConsultationServiceImpl implements CaseConsultationService {

	@Autowired
	private CaseConsultationMapper caseConsultationMapper;
	@Autowired
	private CaseConsultationContentMapper caseConsultationContentMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	ParticipantsMapper participantsMapper;
	@Autowired
	SystemDAO systemDAO;
	@Autowired
	InstantMessageService instantMessageService;
	// 日志
	private static final Logger log = LogManager
			.getLogger(CaseConsultationServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<CaseConsultation> getToDoList(String userId, int readState) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("readState", readState);
			return caseConsultationMapper.getToDoList(map);
		} catch (Exception e) {
			log.error("查询未读信息失败：" + e.getMessage());
			throw new BusinessException("查询未读信息失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<CaseConsultationContent> selectContext(
			Integer caseConsultationId) {
		try {
			return caseConsultationContentMapper
					.selectContext(caseConsultationId);
		} catch (Exception e) {
			log.error("查询未读信息失败：" + e.getMessage());
			throw new BusinessException("查询未读信息失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public CaseConsultation selectByPK(Integer caseConsultationId) {
		try {
			return caseConsultationMapper.selectByPK(caseConsultationId);
		} catch (Exception e) {
			log.error("查询未读信息失败：" + e.getMessage());
			throw new BusinessException("查询未读信息失败");
		}
	}

	@Override
	@Transactional
	public CaseConsultationContent addContext(CaseConsultationContent content,
			MultipartFile attachmentFile) {
		try {
			if (attachmentFile != null && !attachmentFile.isEmpty()) {
				// 1.上传文件
				UpLoadContext upLoad = new UpLoadContext(
						new UploadResource());
				String url = upLoad.uploadFile(attachmentFile, null);
				// 2.添加添加资源内容
				String fileName = attachmentFile.getOriginalFilename();
				content.setAttachmentPath(url);
				content.setAttachmentName(fileName);
			}
			content.setParticipateTime(new Date());
			content.setId(systemDAO.getSeqNextVal(Const.TABLE_CASE_CONSULTATION_CONTENT));
			caseConsultationContentMapper.insert(content);
			
			// 新加回复时，应该把这个案件咨询对应的其它参与人的状态改为末读
			ParticipantsExample participantsExample = new ParticipantsExample();
			Participants participants=new Participants();
			participantsExample
					.createCriteria()
					.andCaseConsultationIdEqualTo(
							content.getCaseConsultationId());
			participants.setState(Const.CASE_CONSULTATION_NOREAD);
			participantsMapper.updateByExampleSelective(participants, participantsExample);
			
			
			//新加回复时，应该把这个案件咨询回复人的状态改为已读
			participantsExample.clear();
			participants.setState(Const.CASE_CONSULTATION_READED);
			participantsExample
					.createCriteria()
					.andCaseConsultationIdEqualTo(
							content.getCaseConsultationId())
					.andUserIdEqualTo(content.getUserId());
			participantsMapper.updateByExampleSelective(participants, participantsExample);
			return content;
		} catch (Exception e) {
			log.error("添加案件咨询内容失败：" + e.getMessage());
			throw new BusinessException("添加案件咨询内容失败");
		}

	}

	// 创建咨询组
	private void chooseParticipant(ParticipantsKey participantsKey,
			String userId) {
		int caseConsultationId = participantsKey.getCaseConsultationId();
		String userIds = participantsKey.getUserId();
		del(caseConsultationId);
		Participants participants = new Participants();
		if (!"".equals(userIds)) {
			String[] userIds1 = userIds.split(",");
			for (int i = 0; i < userIds1.length; i++) {
				participants.setId(systemDAO.getSeqNextVal(Const.PARTICIPANTS_SEQ));
				participants.setCaseConsultationId(caseConsultationId);
				participants.setUserId(userIds1[i]);
				participants.setState(Const.CASE_CONSULTATION_NOREAD);
				participants.setReadTime(new Date());
				participantsMapper.insertSelective(participants);
			}
		}
		participants.setId(systemDAO.getSeqNextVal(Const.PARTICIPANTS_SEQ));
		participants.setCaseConsultationId(caseConsultationId);
		participants.setUserId(userId);
		participants.setState(Const.CASE_CONSULTATION_READED);
		participants.setReadTime(new Date());
		participantsMapper.insertSelective(participants);
	}

	@Transactional
	@Override
	@LogBusiness(operation = "添加案件咨询",business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK,  db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_mapper_class = CaseConsultationMapper.class, target_domain_position = 0)
	public ServiceResponse add(CaseConsultation caseConsultation,
			ParticipantsKey participantsKey, MultipartFile attachmentFile) {
		ServiceResponse res = new ServiceResponse(true, "案件咨询成功");
		try {
			if (attachmentFile != null && !attachmentFile.isEmpty()) {
				// 1.上传文件
				UpLoadContext upLoad = new UpLoadContext(
						new UploadResource());
				String url = upLoad.uploadFile(attachmentFile, null);
				// 2.添加添加资源内容
				String fileName = attachmentFile.getOriginalFilename();
				caseConsultation.setAttachmentPath(url);
				caseConsultation.setAttachmentName(fileName);
			}
			
			int seqNextVal = systemDAO.getSeqNextVal(Const.TABLE_CASE_CONSULTATION);
			caseConsultation.setId(seqNextVal);
			caseConsultationMapper.insertSelective(caseConsultation);
			participantsKey.setCaseConsultationId(caseConsultation.getId());
			chooseParticipant(participantsKey, caseConsultation.getInputer());
			//添加APP提示信息
			instantMessageService.addCaseConsultationMessage(caseConsultation,participantsKey.getUserId());
		} catch (Exception e) {
			log.error("咨询添加失败" + e.getMessage());
			throw new BusinessException("咨询添加失败");
		}
		return res;
	}

	@Transactional
	@Override
	@LogBusiness(operation = "删除案件咨询", db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_mapper_class = ParticipantsMapper.class, target_domain_class = CaseConsultation.class, target_domain_code_position = 0)
	public void del(int caseConsultationId) {
		ParticipantsExample participantsExample = new ParticipantsExample();
		try {
			participantsExample.createCriteria().andCaseConsultationIdEqualTo(
					caseConsultationId);
			participantsMapper.deleteByExample(participantsExample);
		} catch (Exception e) {
			log.error("删除咨询组失败" + e.getMessage());
			throw new BusinessException("删除咨询组失败");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public PaginationHelper<CaseConsultation> search(
			CaseConsultation caseConsultation, String page) {
		try {
			caseConsultation.setSetTop(Const.TOP_NO);
			PaginationHelper<CaseConsultation> caseConPaList=systemDAO.find(caseConsultation, page);
			return caseConPaList;
		} catch (Exception e) {
			log.error("查询咨询失败" + e.getMessage());
			throw new BusinessException("查询咨询失败");
		}
	}

	@Override
	@Transactional
	public void updateParticipants(Participants par) {
		try {
			participantsMapper.updateByPrimaryKeySelective(par);
		} catch (Exception e) {
			log.error("修改咨询参与人失败" + e.getMessage());
			throw new BusinessException("修改咨询参与人失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "修改案件咨询信息", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = CaseConsultationMapper.class)
	public void updateConsultation(CaseConsultation con) {
		try {
			caseConsultationMapper.updateByPrimaryKeySelective(con);
		} catch (Exception e) {
			log.error("修改咨询失败" + e.getMessage());
			throw new BusinessException("修改咨询失败");
		}
	}

	@Override
	public void updateContent(CaseConsultationContent content) {
		try {
			caseConsultationContentMapper.updateByPrimaryKeySelective(content);
		} catch (Exception e) {
			log.error("修改咨询内容失败" + e.getMessage());
			throw new BusinessException("修改咨询内容失败");
		}
		
	}
	
	@Override
	public void deleteCaseConsultation(Integer id) {
		try {
			//删除咨询的回复和附件
			CaseConsultationContentExample example = new CaseConsultationContentExample();
	    	example.createCriteria().andCaseConsultationIdEqualTo(id);
	    	List<CaseConsultationContent> caseConsultationContents = caseConsultationContentMapper.selectByExample(example);
	    	if(!caseConsultationContents.isEmpty()) {
	    		for(CaseConsultationContent caseConsultationContent : caseConsultationContents) {
	    			FileUtil.deleteFileInDisk(caseConsultationContent.getAttachmentPath());
	    			caseConsultationContentMapper.deleteByPrimaryKey(caseConsultationContent.getId());
	    		}
	    	}
	    	//删除指定的咨询人
	    	ParticipantsExample participantsExample = new ParticipantsExample();
	    	participantsExample.createCriteria().andCaseConsultationIdEqualTo(id);
			participantsMapper.deleteByExample(participantsExample);
			//删除咨询内容
	    	CaseConsultation caseConsultation = caseConsultationMapper.selectByPrimaryKey(id);
	    	if(caseConsultation != null) {
	    		FileUtil.deleteFileInDisk(caseConsultation.getAttachmentPath());
	    		caseConsultationMapper.deleteByPrimaryKey(id);
	    	}
	    	
	    	//删除app信息
	    	instantMessageService.deleteByBusinessKey(id.toString());
		} catch (Exception e) {
			log.error("删除案件咨询失败" + e.getMessage());
			throw new BusinessException("删除案件咨询失败");
		}
		
	}

	@Override
	public PaginationHelper<CaseConsultation> receiveList(
			CaseConsultation caseConsultation, String page) {
		try {
            return systemDAO.find(caseConsultation, null, page,
                    "com.ksource.liangfa.mapper.CaseConsultationMapper.receiveCount",
                    "com.ksource.liangfa.mapper.CaseConsultationMapper.receiveList");
        } catch (Exception e) {
            log.error("案件咨询查询失败：" + e.getMessage());
            throw new BusinessException("案件咨询查询失败");
        }
	}

	@Override
	public PaginationHelper<CaseConsultationContent> findReplyList(
			Map<String, Object> map, String page) {
		try {
            return systemDAO.find(map, page,
                    "com.ksource.liangfa.mapper.CaseConsultationContentMapper.selectReplyCount",
                    "com.ksource.liangfa.mapper.CaseConsultationContentMapper.selectReplyList");
        } catch (Exception e) {
            log.error("案件咨询回复查询失败：" + e.getMessage());
            throw new BusinessException("案件咨询回复查询失败");
        }
	}

}