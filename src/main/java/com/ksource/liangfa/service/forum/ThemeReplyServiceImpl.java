package com.ksource.liangfa.service.forum;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.mapper.ForumCommunityMapper;
import com.ksource.liangfa.mapper.ForumThemeMapper;
import com.ksource.liangfa.mapper.ThemeReplyMapper;
import com.ksource.syscontext.Const;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 此类为 主题回复 服务实现类
 * 
 * @author zxl :)
 * @version 1.0 date 2011-12-30 time 下午4:13:49
 */
@Service
public class ThemeReplyServiceImpl implements ThemeReplyService {
	@Autowired
	ThemeReplyMapper themeReplyMapper;
	@Autowired
	ForumThemeMapper forumThemeMapper;
	@Autowired
	ForumCommunityMapper forumCommunityMapper;
	@Autowired
	SystemDAO systemDao;
	// 日志
	private static final Logger log = LogManager
			.getLogger(ThemeReplyServiceImpl.class);

	@Override
	@Transactional
	public void add(User user, ThemeReply reply, MultipartFile contentFile) {
		try {
			ForumTheme theme = forumThemeMapper.selectByPrimaryKey(reply
					.getThemeId());
			// 1.添加回复信息
			reply.setSectionId(theme.getSectionId());
			reply.setState(Const.STATE_VALID);
			reply.setReplyTime(new Date());
			reply.setInputer(user.getUserId());
			reply.setInputerName(user.getUserName());
			// 上传文件
			if (contentFile != null && !contentFile.isEmpty()) {
				UpLoadContext upLoad = new UpLoadContext(new UploadResource());
				String url = upLoad.uploadFile(contentFile, null);
				reply.setAttachmentPath(url);
				reply.setAttachmentName(contentFile.getOriginalFilename());
			}
			reply.setId(systemDao.getSeqNextVal(Const.TABLE_THEME_REPLY));
			themeReplyMapper.insert(reply);
			// 2.修改主题信息(维护最新主题回复及回复数)
			/*theme.setReplyCount(theme.getReplyCount() + 1);
			theme.setLatestReplyId(reply.getId());
			theme.setLatestReplyName(user.getUserName());
			theme.setLatestReplyTime(reply.getReplyTime());
			forumThemeMapper.updateByPrimaryKeySelective(theme);*/
			ForumTheme record=new ForumTheme();
			record.setId(theme.getId());
			record.setReplyCount(theme.getReplyCount() + 1);
			record.setLatestReplyId(reply.getId());
			record.setLatestReplyName(user.getUserName());
			record.setLatestReplyTime(reply.getReplyTime());
			forumThemeMapper.updateReadCount(record);
			
			// 3.修改论坛板块回复总数
			ForumCommunity comm = forumCommunityMapper.selectByPrimaryKey(theme
					.getSectionId());
			int count = comm.getReplyCount() == null ? 1
					: comm.getReplyCount() + 1;
			comm.setReplyCount(count);
			forumCommunityMapper.updateByPrimaryKeySelective(comm);
		} catch (Exception e) {
			log.error("添加主题回复失败：" + e.getMessage());
			throw new BusinessException("添加主题回复失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<ThemeReply> find(Integer themeId, String page) {
		ThemeReply reply = new ThemeReply();
		reply.setThemeId(themeId);
		return systemDao.find(reply, page);
	}

	@Override
	@Transactional
	public ServiceResponse deleteThemeReply(Integer themeId, Integer replyId) {
		try {
			// 1、删除回复主题
			themeReplyMapper.deleteByPrimaryKey(replyId);
			// 2、查询对应的主题
			ForumTheme forumTheme = forumThemeMapper.selectByPrimaryKey(themeId);
			// 3、对比要删除的回复主题是否是最新回复主题，如果相等，需要修改主题表中的最新回复人ID、最新回复人名称、最新回复时间
			if (forumTheme.getLatestReplyId().equals(replyId)) {
//				4、需要查找另外一个最新回复主题来代替要删除的最新回复主题
				ThemeReplyExample themeReplyExample = new ThemeReplyExample() ;
				themeReplyExample.setOrderByClause("reply_time") ;
				themeReplyExample.createCriteria().andThemeIdEqualTo(themeId);
				List<ThemeReply> list = themeReplyMapper.selectByExample(themeReplyExample) ;
				int size = list.size() ;
				if(size == 0) {
//					设置LatestReplyId的值为0，来判断该标题中是否存在最新回复主题，
//					不用考虑LatestReplyName，LatestReplyTime
					forumTheme.setLatestReplyId(0);
				}else {
					forumTheme.setLatestReplyId(list.get(size-1).getId());
					forumTheme.setLatestReplyName(list.get(size-1).getInputerName());
					forumTheme.setLatestReplyTime(list.get(size-1).getReplyTime());
				}
			}
			// 5、修改主题表中的回复总数
			forumTheme.setReplyCount(forumTheme.getReplyCount() - 1);
			forumThemeMapper.updateByPrimaryKeySelective(forumTheme);
			// 6、修改版块表中的回复总数
			ForumCommunity comm = forumCommunityMapper.selectByPrimaryKey(forumTheme.getSectionId());
			int count = comm.getReplyCount() - 1;
			comm.setReplyCount(count);
			forumCommunityMapper.updateByPrimaryKeySelective(comm);
			ServiceResponse response = new ServiceResponse(true, "删除论坛主题回复成功！");
			return response ;
		} catch (Exception e) {
			log.error("删除回复主题失败" + e.getMessage());
			throw new BusinessException("删除回复主题失败");
		}
	}

	@Override
	@Transactional
	public ServiceResponse updateByPrimaryKeySelective(ThemeReply reply) {
		try {
			ServiceResponse response = new ServiceResponse(true, "更新回复主题成功！");
			themeReplyMapper.updateByPrimaryKeySelective(reply) ;
			return response ;
		} catch (Exception e) {
			log.error("更新回复主题失败" + e.getMessage());
			throw new BusinessException("更新回复主题失败");
		}
	}
}
