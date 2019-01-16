package com.ksource.liangfa.service.specialactivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.DqdjCategory;
import com.ksource.liangfa.domain.DqdjChart;
import com.ksource.liangfa.mapper.DqdjCategoryMapper;

/**
 *@author wangzhenya
 *@2013-1-6 下午4:53:51
 */
@Service
public class DqdjCategoryServiceImpl implements DqdjCategoryService {

	@Autowired
	private DqdjCategoryMapper dqdjCategoryMapper;
	
	private static final Logger LOGGER = LogManager.getLogger(DqdjCategoryServiceImpl.class);
	
	@Override
	@Transactional(readOnly = true)
	public List<DqdjCategory> getDqdjCategoryTree(Integer parentId) {
		try {
			return dqdjCategoryMapper.getDqdjCategoryTree(parentId);
		} catch (Exception e) {
			LOGGER.error("查询打侵打假案件类别失败！" + e.getMessage());
			throw new BusinessException("查询打侵打假案件类别失败！");
		}
	}

	@Override
	public DqdjChart querydqdjCharts() {
			return  dqdjCategoryMapper.querydqdjCharts();
	}

}
