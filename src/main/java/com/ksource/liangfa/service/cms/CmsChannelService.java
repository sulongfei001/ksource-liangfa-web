package com.ksource.liangfa.service.cms;

import java.util.List;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.cms.CmsChannel;

public interface CmsChannelService {
	/** 添加栏目*/
	ServiceResponse insert(CmsChannel channel);
	/** 根据父节点得到下级节点*/
	List<CmsChannel> selectByParentId(int parentId);
	/** 删除栏目
	 * @return */
	Boolean del(Integer channelId, Integer parentId);
	/** 判断是否还有子节点*/
	Boolean isNoChildren(Integer id);
	/** 通过栏目来源打到栏目ID*/
	Integer getChannelId(Integer channelFrom);
	/** 判断栏目来源是否关联栏目*/
	Boolean fromIsExist(Integer channelFrom,Integer channelId);
}
