package com.ksource.liangfa.service.forum;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.ForumTheme;

/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-12-28
 * time   下午3:05:08
 */
public interface ForumThemeService {

	/**
	 * 查询最近时间发表的主题.<br/>
	 * 如果pageSize==8,
	 * 查出按发表时间降序排序后的前8条记录.
	 * @param pageSize
	 * @return
	 */
	List<ForumTheme> findLatestTop(int pageSize);

	/**
	 * 查询回复最多的主题.<br/>
	 * 如果pageSize==8,
	 * 查出按回复数降序排序后的前8条记录.
	 * @param pageSize
	 * @return
	 */
	List<ForumTheme> findReplyTop(int pageSize);
	/**
	 * 数据库分页查询.
	 * @param theme
	 * @param page
	 * @return
	 */
	PaginationHelper<ForumTheme> find(ForumTheme theme, String page);

	/**
	 * 通过唯一标示查询论坛主题.
	 * 除基本字段外还能查询出
	 * inputerName 录入人名称
	 * orgName     录入人所在单位名称
	 * @param id
	 * @return
	 */
	ForumTheme findByPk(Integer id);
	
	
	void add(ForumTheme forumTheme, MultipartFile attachmentFile) ;
	
	/**
	 * 通过回复人ID查找相应的 回复的在论坛主题ID,然后在根据论坛主题ID查找对应的主题
	 * @param inputer 回复人ID
	 */
	List<ForumTheme> findToThemeID(String inputer) ;
	
	/**
	 * 通过主题ID删除主题和该主题下的回复 ,
	 * 同时修改版块表中的主题总数，回复主题总数，最新主题，最新主题名称，最新主题创建时间，最新主题创建人ID，最新主题创建人名称
	 * @param inputer 主题ID
	 */
	void deleteThemeAndReplyById(Integer forumCommId,Integer themeId) ;

	/**
	 * 更新主题信息
	 * @param forumTheme
	 * @return
	 */
	int updateReadCount(ForumTheme forumTheme);
}
