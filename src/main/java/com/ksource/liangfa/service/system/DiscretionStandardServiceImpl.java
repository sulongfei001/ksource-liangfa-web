package com.ksource.liangfa.service.system;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.DiscretionStandard;
import com.ksource.liangfa.mapper.DiscretionStandardMapper;
import com.ksource.syscontext.Const;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiscretionStandardServiceImpl implements DiscretionStandardService {
	
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private DiscretionStandardMapper discretionStandardMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(DiscretionStandardServiceImpl.class);

	@Override
	public PaginationHelper<DiscretionStandard> find(
			DiscretionStandard discretionStandard, String page) {
		try {
			return systemDao.find(discretionStandard, page);
		} catch (Exception e) {
			log.error("查询失败：" + e.getMessage());
			throw new BusinessException("查询失败");
		}
	}

	@Override
	@Transactional
	public ServiceResponse insert(DiscretionStandard discretionStandard) {
		ServiceResponse response = new ServiceResponse(true, "添加行政处罚裁量标准成功!");
		try {
			discretionStandard.setStandardId(systemDao.getSeqNextVal(Const.TABLE_DISCRETION_STANDARD));
			if(discretionStandard.getTermId()==null){
				discretionStandard.setTermId(0);
			}
			discretionStandardMapper.insert(discretionStandard);
			return response;
		} catch (Exception e) {
			log.error("添加行政处罚裁量标准失败：" + e.getMessage());
			throw new BusinessException("添加行政处罚裁量标准失败");
		}
	}

	@Override
	@Transactional
	public ServiceResponse update(DiscretionStandard discretionStandard) {
		ServiceResponse response = new ServiceResponse(true, "修改行政处罚裁量标准成功!");
		try {
			discretionStandardMapper.updateByPrimaryKeySelective(discretionStandard);
			return response;
		} catch (Exception e) {
			log.error("修改行政处罚裁量标准失败：" + e.getMessage());
			throw new BusinessException("修改行政处罚裁量标准失败");
		}
	}

	@Override
	@Transactional
	public int del(Integer id) {
		try {
			return discretionStandardMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			log.error("删除行政处罚裁量标准失败：" + e.getMessage());
			throw new BusinessException("删除行政处罚裁量标准失败");
		}
	}

	@Override
	public DiscretionStandard selectByPrimaryKey(Integer id) {
		return discretionStandardMapper.selectByPrimaryKey(id);
	}
	
}
