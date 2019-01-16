package com.ksource.liangfa.service.notice;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.ksource.common.util.RegExpUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.InstantMessage;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.NoticeOrg;
import com.ksource.liangfa.domain.NoticeOrgExample;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.mapper.NoticeMapper;
import com.ksource.liangfa.mapper.NoticeOrgMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.service.system.UserServiceImpl;
import com.ksource.syscontext.Const;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private NoticeOrgMapper noticeOrgMapper;
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;
	@Autowired
	private InstantMessageService instantMessageService;

	// 日志
	private static final Logger log = LogManager
			.getLogger(UserServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<Notice> find(Notice notice, String page,Map<String,Object> map) {
		try {
			return systemDao.find(notice, page,map);
		} catch (Exception e) {
			log.error("查询公告失败：" + e.getMessage());
			throw new BusinessException("查询公告失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Notice find(Integer noticeId) {
		try {
			return noticeMapper.getDetailNotice(noticeId);
		} catch (Exception e) {
			log.error("查询公告失败：" + e.getMessage());
			throw new BusinessException("查询公告失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "删除通知公告", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = NoticeMapper.class)
	public int del(Integer noticeId) {
		NoticeOrgExample example = new NoticeOrgExample();
		example.createCriteria().andNoticeIdEqualTo(noticeId);
		try {
			Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
			// 1.删除图片文件
			FileUtil.deleteFile(RegExpUtil.getImgSrcFromHtml(notice
					.getNoticeContent()));
			// 2.删除公告对应的公告-组织机构表
			noticeOrgMapper.deleteByExample(example);
			// 3.删除附件信息
			PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
			publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_NOTICE).andInfoIdEqualTo(noticeId);
			List<PublishInfoFile> publishInfoFiles = publishInfoFileMapper.selectByExample(
					publishInfoFileExample);
			for(PublishInfoFile publishInfoFile : publishInfoFiles) {
				FileUtil.deleteFileInDisk(publishInfoFile.getFilePath());
				publishInfoFileMapper.deleteByPrimaryKey(publishInfoFile.getFileId());

			}
	        
            instantMessageService.deleteByBusinessKey(noticeId.toString());
			return noticeMapper.deleteByPrimaryKey(noticeId);
		} catch (Exception e) {
			log.error("删除公告失败：" + e.getMessage());
			throw new BusinessException("删除公告失败");
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<Notice> find(Notice notice, int pageSize,Map<String,Object>map) {
		try {
			// 1.根据pageSize计算出start,end.并把条件封装成map
			map.put("start", 0);
			map.put("end", pageSize);
			// 2.调用systemdao getList
			return systemDao.getList(map, notice, null);

		} catch (Exception e) {
			log.error("查询公告失败：" + e.getMessage());
			throw new BusinessException("查询公告失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "添加通知公告", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = NoticeMapper.class)
	public ServiceResponse add(Notice notice,String orgIds,
			MultipartHttpServletRequest attachmentFile) {
		List<MultipartFile> files = attachmentFile.getFiles("file");
		PublishInfoFile publishInfoFile = null;
		ServiceResponse response = new ServiceResponse(true, "添加通知公告成功!");
		try {
			notice.setNoticeId(systemDao.getSeqNextVal(Const.TABLE_NOTICE));
			notice.setIsPublished(Const.NOTICE_IS_PUBLISHED);//默认通知公告为生效！
			noticeMapper.insert(notice);
			for (int i = 0; i < files.size(); i++) {
				MultipartFile mFile = files.get(i);
				if (mFile != null && !mFile.isEmpty()) {
					UpLoadContext upLoad = new UpLoadContext(
							new UploadResource());
					String url = upLoad.uploadFile(mFile, null);
					String fileName = mFile.getOriginalFilename();
					publishInfoFile = new PublishInfoFile();
					publishInfoFile.setFileId(systemDao.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
					publishInfoFile.setInfoId(notice.getNoticeId());
					publishInfoFile.setFileName(fileName);
					publishInfoFile.setFilePath(url);
					publishInfoFile.setFileType(Const.TABLE_NOTICE);
					publishInfoFileMapper.insert(publishInfoFile);
				}
			}
			if(StringUtils.isNotBlank(orgIds)){
				String[] org = orgIds.split(",");
				NoticeOrg noticeOrg = new NoticeOrg();
				noticeOrg.setNoticeId(notice.getNoticeId());
				for (int i = 0; i < org.length; i++) {
					Integer orgId = Integer.parseInt(org[i]);
					noticeOrg.setOrgId(orgId);
					noticeOrgMapper.insert(noticeOrg);
				}
			}
			//添加APP提示消息
			instantMessageService.addNoticeMessage(notice,orgIds);
			return response;
		} catch (Exception e) {
			log.error("通知公告添加失败" + e.getMessage());
			throw new BusinessException("通知公告添加失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "修改通知公告", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = NoticeMapper.class)
	public ServiceResponse updateByPrimaryKeySelective(Notice notice,String orgIds,
			MultipartHttpServletRequest attachmentFile) {
		ServiceResponse response = new ServiceResponse(true, "修改通知公告成功!");
		List<MultipartFile> files = attachmentFile.getFiles("file");
		PublishInfoFile publishInfoFile = null;
		try {
			noticeMapper.updateByPrimaryKeySelective(notice);
			for (int i = 0; i < files.size(); i++) {
				MultipartFile mFile = files.get(i);
				if (mFile != null && !mFile.isEmpty()) {
					UpLoadContext upLoad = new UpLoadContext(
							new UploadResource());
					String url = upLoad.uploadFile(mFile, null);
					String fileName = mFile.getOriginalFilename();
					publishInfoFile = new PublishInfoFile();
					publishInfoFile.setFileId(systemDao.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
					publishInfoFile.setInfoId(notice.getNoticeId());
					publishInfoFile.setFileName(fileName);
					publishInfoFile.setFilePath(url);
					publishInfoFile.setFileType(Const.TABLE_NOTICE);
					publishInfoFileMapper.insert(publishInfoFile);
				}
			}
			NoticeOrgExample example = new NoticeOrgExample();
			example.createCriteria().andNoticeIdEqualTo(notice.getNoticeId());
			noticeOrgMapper.deleteByExample(example);
			if(StringUtils.isNotBlank(orgIds)){
				String[] org = orgIds.split(",");
				NoticeOrg noticeOrg = new NoticeOrg();
				noticeOrg.setNoticeId(notice.getNoticeId());
				for (int i = 0; i < org.length; i++) {
					Integer orgId = Integer.parseInt(org[i]);
					noticeOrg.setOrgId(orgId);
					noticeOrgMapper.insert(noticeOrg);
				}
			}
			return response;
		} catch (Exception e) {
			log.error("修改公告失败" + e.getMessage());
			throw new BusinessException("修改公告失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "批量删除通知公告", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_BATCH_DELETE, target_domain_code_position = 0, target_domain_mapper_class = NoticeMapper.class)
	public ServiceResponse batchDeleteNotice(Integer[] noticeIds) {
		ServiceResponse response = new ServiceResponse(true, "批量删除通知公告成功!");
		NoticeOrgExample example = new NoticeOrgExample();
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		try {
			Notice notice = null;
			for(Integer noticeId:noticeIds){
				example.clear();
				publishInfoFileExample.clear();
				example.createCriteria().andNoticeIdEqualTo(noticeId);
				notice = noticeMapper.selectByPrimaryKey(noticeId);
				if(notice != null){
					// 1.删除图片文件
					FileUtil.deleteFile(RegExpUtil.getImgSrcFromHtml(notice
							.getNoticeContent()));
					// 2.删除公告对应的公告-组织机构表
					noticeOrgMapper.deleteByExample(example);
					// 3.删除附件信息
					publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_NOTICE).andInfoIdEqualTo(noticeId);
					List<PublishInfoFile> publishInfoFiles = publishInfoFileMapper.selectByExample(
							publishInfoFileExample);
					for(PublishInfoFile publishInfoFile : publishInfoFiles) {
						FileUtil.deleteFileInDisk(publishInfoFile.getFilePath());
						publishInfoFileMapper.deleteByPrimaryKey(publishInfoFile.getFileId());
					}
					// 4.删除公告信息
					noticeMapper.deleteByPrimaryKey(noticeId);
					// 5.删除APP中的信息
					instantMessageService.deleteByBusinessKey(noticeId.toString());
				}
			}
			return response;
		} catch (Exception e) {
			log.error("批量删除公告失败：" + e.getMessage());
			throw new BusinessException("批量删除公告失败");
		}

	}

	@Override
	public PaginationHelper<Notice> getNoticeList(Notice notice, String page,
			Map<String, Object> map) {
		try {
            return systemDao.find(notice, map, page,
                    "com.ksource.liangfa.mapper.NoticeMapper.getNoticeCount",
                    "com.ksource.liangfa.mapper.NoticeMapper.getNoticeList");
        } catch (Exception e) {
            log.error("查询通知公告失败：" + e.getMessage());
            throw new BusinessException("查询通知公告失败");
        }
	}

	@Override
	public List<Notice> getNoticeList(Map<String, Object> params) {
		return noticeMapper.getNoticeList(params);
	}

	@Override
	public List<Notice> getNoRead(String userId) {
		return noticeMapper.getNoRead(userId);
	}

	@Override
	public List<Notice> getAlread(String userId) {
		return noticeMapper.getAlread(userId);
	}

	@Override
	public void updateByPrimaryKeySelective(Notice notice) {
		noticeMapper.updateByPrimaryKeySelective(notice);
		
	}

	@Override
	public Notice selectByPrimaryKey(Integer noticeId) {
		return noticeMapper.selectByPrimaryKey(noticeId);
	}

	@Override
	public List<Notice> getMyNoticeList(Map<String, Object> params) {
		return noticeMapper.getMyNoticeList(params);
	}

	@Override
	public PaginationHelper<Notice> findMyNoticeList(Notice notice,
			Map<String, Object> params,String page) {
		try {
            return systemDao.find(notice, params, page,
                    "com.ksource.liangfa.mapper.NoticeMapper.getMyNoticeCount",
                    "com.ksource.liangfa.mapper.NoticeMapper.getMyNoticeList");
        } catch (Exception e) {
            log.error("查询通知公告失败：" + e.getMessage());
            throw new BusinessException("查询通知公告失败");
        }
	}

	@Override
	public List<PublishInfoFile> getFilesByInfoId(
			PublishInfoFile publishInfoFile) {
		return publishInfoFileMapper.getFileByInfoId(publishInfoFile);
	}

}