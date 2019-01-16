package com.ksource.liangfa.service.forum;

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
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.ForumCommunity;
import com.ksource.liangfa.domain.ForumTheme;
import com.ksource.liangfa.domain.ForumThemeExample;
import com.ksource.liangfa.domain.ThemeReplyExample;
import com.ksource.liangfa.mapper.ForumCommunityMapper;
import com.ksource.liangfa.mapper.ForumThemeMapper;
import com.ksource.liangfa.mapper.ThemeReplyMapper;
import com.ksource.syscontext.Const;

/**
 * 此类为 
 * 
 * @author zxl :)
 * @version 1.0 date 2011-12-28 time 下午3:05:57
 */
@Service
public class ForumThemeServiceImpl implements ForumThemeService {
	@Autowired
	ForumThemeMapper forumThemeMapper;
	@Autowired
	ThemeReplyMapper themeReplyMapper ;
	@Autowired
	SystemDAO systemDao;
	@Autowired
	ForumCommunityMapper forumCommunityMapper;
	// 日志
		private static final Logger log = LogManager
				.getLogger(ForumThemeServiceImpl.class);

	@Override
	@Transactional(readOnly=true)
	public List<ForumTheme> findLatestTop(int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start",0);
		map.put("end", pageSize);
		map.put("orderType","latest");
		try{
		return systemDao.getList(map,new ForumTheme(),null);
		}catch (Exception e) {
			log.error("查询论坛主题失败:"+e.getMessage());
			throw new BusinessException("查询论坛主题失败");
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<ForumTheme> findReplyTop(int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", 0);
		map.put("end", pageSize);
		map.put("orderType","reply");
		try{
		return systemDao.getList(map,new ForumTheme(),null);
		}catch (Exception e) {
			log.error("查询论坛主题失败:"+e.getMessage());
			throw new BusinessException("查询论坛主题失败");
		}
	}

	@Override
	@Transactional(readOnly=true)
	public PaginationHelper<ForumTheme> find(ForumTheme theme, String page) {
		try{
		return systemDao.find(theme, page);
		}catch (Exception e) {
			log.error("查询论坛主题失败:"+e.getMessage());
			throw new BusinessException("查询论坛主题失败");
		}
	}

	@Override
	@Transactional(readOnly=true)
	public ForumTheme findByPk(Integer id) {
		try{
		return forumThemeMapper.findByPk(id);
		}catch (Exception e) {
			log.error("查询论坛主题失败:"+e.getMessage());
			throw new BusinessException("查询论坛主题失败");
		}
	}
	
	@Override
	@Transactional
	@LogBusiness(operation="添加论坛主题",business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_INSERT,target_domain_mapper_class = ForumThemeMapper.class,target_domain_position=0)
	public void add(ForumTheme forumTheme, MultipartFile attachmentFile) {
		try {
			if (attachmentFile != null && !attachmentFile.isEmpty()) {
				// 1.上传文件
				UpLoadContext upLoad = new UpLoadContext(
						new UploadResource());
				String url = upLoad.uploadFile(attachmentFile, null);
				// 2.添加添加资源内容
				String fileName = attachmentFile.getOriginalFilename();
				forumTheme.setAttachmentPath(url);
				forumTheme.setAttachmentName(fileName);
			}
			forumTheme.setId(systemDao.getSeqNextVal(Const.TABLE_FORUM_THEME));
			forumThemeMapper.insertSelective(forumTheme);
			//维护论坛板块内最新主题及主题总数
			ForumCommunity forumCommunity = new ForumCommunity() ;
			forumCommunity.setLatestTheme(forumTheme.getId()) ;
			forumCommunity.setLatestThemeInputer(forumTheme.getInputer()) ;
			forumCommunity.setLatestThemeInputerName(forumTheme.getInputerName()) ;
			forumCommunity.setLatestThemeName(forumTheme.getName()) ;
			forumCommunity.setLatestThemeTime(forumTheme.getCreateTime()) ;
			forumCommunity.setId(forumTheme.getSectionId()) ;
			
			ForumThemeExample forumThemeExample = new ForumThemeExample() ;
			forumThemeExample.createCriteria().andSectionIdEqualTo(forumTheme.getSectionId()) ;
			forumCommunity.setThemeCount(forumThemeMapper.countByExample(forumThemeExample)) ;
			
			forumCommunityMapper.updateByPrimaryKeySelective(forumCommunity) ;
		} catch (Exception e) {
			log.error("主题添加失败" + e.getMessage());
			throw new BusinessException("主题添加失败");
		}
	}

	@Override
	@Transactional
	public List<ForumTheme> findToThemeID(String inputer) {
		try {
			return forumThemeMapper.findMyReplyTheme(inputer);
		} catch (Exception e) {
			log.error("主题查找失败" + e.getMessage());
			throw new BusinessException("主题查找失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation="删除论坛主题",business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_DELETE,target_domain_code_position = 1, target_domain_mapper_class = ForumThemeMapper.class)
	public void deleteThemeAndReplyById(Integer forumCommId,Integer themeId) {
		try {
//			1、查找该主题下的回复主题的数量
			ThemeReplyExample themeReplyExample = new ThemeReplyExample() ;
			themeReplyExample.createCriteria().andThemeIdEqualTo(themeId) ;
			int count = themeReplyMapper.countByExample(themeReplyExample) ;
			
//			6、删除主题以及主题下的回复主题
			 forumThemeMapper.deleteByPrimaryKey(themeId) ;
			 ThemeReplyExample themeReplyExample1 = new ThemeReplyExample() ;
			 themeReplyExample1.createCriteria().andThemeIdEqualTo(themeId) ;
			 themeReplyMapper.deleteByExample(themeReplyExample1) ;
			
//			2、查找版块信息
			ForumCommunity forumCommunity = forumCommunityMapper.selectByPrimaryKey(forumCommId) ;
//			3、对比要删除的主题是否是最新主题，如果相等，查找出另外一个主题信息代替最新主题
			if(forumCommunity.getLatestTheme().equals(themeId)) {
//				4、查找另外一个主题
				ForumThemeExample forumThemeExample = new ForumThemeExample() ;
				forumThemeExample.setOrderByClause("CREATE_TIME") ;
				forumThemeExample.createCriteria().andSectionIdEqualTo(forumCommId) ;
				List<ForumTheme> list = forumThemeMapper.selectByExample(forumThemeExample) ;
				int size = list.size() ;
				if(size == 0) {
//					设置LatestTheme的值为0，来判断该版块中是否存在最新主题，
//					不用考虑LatestThemeInputer，LatestThemeInputerName，LatestThemeName，LatestThemeTime
					forumCommunity.setLatestTheme(0) ;
				}else {
					forumCommunity.setLatestTheme(list.get(size-1).getId()) ;
					forumCommunity.setLatestThemeInputer(list.get(size-1).getInputer()) ;
					forumCommunity.setLatestThemeInputerName(list.get(size-1).getInputerName()) ;
					forumCommunity.setLatestThemeName(list.get(size-1).getName()) ;
					forumCommunity.setLatestThemeTime(list.get(size-1).getCreateTime()) ;
				}
			}
			
//			5、更新主题数和回复数
			forumCommunity.setThemeCount(forumCommunity.getThemeCount() - 1) ;
			forumCommunity.setReplyCount(forumCommunity.getReplyCount() - count) ;
			forumCommunityMapper.updateByPrimaryKeySelective(forumCommunity) ;
			
		} catch (Exception e) {
			log.error("删除主题失败" + e.getMessage());
			throw new BusinessException("删除主题失败");
		}
	}

	@Override
	public int updateReadCount(ForumTheme forumTheme) {
		return forumThemeMapper.updateReadCount(forumTheme);
	}
}