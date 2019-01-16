package com.ksource.liangfa.service.instruction;

import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CommunionReceive;

/**
 * 横向交流接收表service
 * @author wuzy
 * @date 2016-7-26上午9:35:52
 */
public interface CommunionReceiveService {
	public PaginationHelper<CommunionReceive> find(CommunionReceive communionReceive,
			String page, Map<String, Object> params);

    public List<CommunionReceive> getCommunionReceiveList(CommunionReceive communionReceive);

    public int updateByPrimaryKeySelective(CommunionReceive communionReceive);
}
