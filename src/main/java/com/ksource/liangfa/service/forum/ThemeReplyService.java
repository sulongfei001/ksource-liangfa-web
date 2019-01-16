package com.ksource.liangfa.service.forum;

import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.ThemeReply;
import com.ksource.liangfa.domain.User;

/**
 * 此类为  主题回复 服务接口类
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-12-30
 * time   下午4:09:11
 */
public interface ThemeReplyService {

	/**
	 * 添加主题回复.
	 * 维护:
	 * 论坛板块回复总数
	 * 主题回复总数,最新回复信息
	 * <pre>method test</pre>
	 * @param user 
	 * @param reply
	 * @param contentFile 
	 */
	void add(User user, ThemeReply reply, MultipartFile contentFile);

	/**
	 * 数据库分页(查询某一个主题的回复)
	 * @param themeId
	 * @param page
	 * @return
	 */
	PaginationHelper<ThemeReply> find(Integer themeId, String page);
	
	/**
	 * 通过回复主题ID删除该回复主题
	 * 需要同时修改该主题的回复总数，如果要删除的是最新回复主题，则需要修改最新回复人ID、最新回复人名称、最新回复时间
	 * @param inputer 回复主题ID
	 */
	ServiceResponse deleteThemeReply(Integer themeId,Integer replyId) ;
	
	/**
	 * 
	 */
	ServiceResponse updateByPrimaryKeySelective(ThemeReply reply) ;
}
