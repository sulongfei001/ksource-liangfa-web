package com.ksource.liangfa.service.instruction;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CommunionReceive;
import com.ksource.liangfa.mapper.CommunionReceiveMapper;

/**
 * 横向交流接收表service
 * @author wuzy
 * @date 2016-7-26上午9:35:52
 */
@Service
@Transactional
public class CommunionReceiveServiceImpl implements CommunionReceiveService{
	@Autowired
	SystemDAO systemDao;
	
	@Autowired
	CommunionReceiveMapper communionReceiveMapper;
	
	public PaginationHelper<CommunionReceive> find(CommunionReceive communionReceive,
			String page, Map<String, Object> params) {
		return systemDao.find(communionReceive,page,null);
	}

    @Override
    public List<CommunionReceive> getCommunionReceiveList(CommunionReceive communionReceive) {
        return communionReceiveMapper.getCommunionReceiveList(communionReceive);
    }

    @Override
    public int updateByPrimaryKeySelective(CommunionReceive communionReceive) {
        return communionReceiveMapper.updateByPrimaryKeySelective(communionReceive);
    }

}
