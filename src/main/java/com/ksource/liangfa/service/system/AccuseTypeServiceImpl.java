package com.ksource.liangfa.service.system;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.AccuseType;
import com.ksource.liangfa.domain.AccuseTypeExample;
import com.ksource.liangfa.mapper.AccuseTypeMapper;

/**
 * @author 王振亚
 * @2012-7-19 下午5:27:55
 */
@Service
public class AccuseTypeServiceImpl implements AccuseTypeService {

	@Autowired
	private AccuseTypeMapper accuseTypeMapper;

	// 日志
	static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<AccuseType> find(Map<String, Object> map) {
		try {
			return accuseTypeMapper.find(map);
		} catch (Exception e) {
			logger.error("罪名分类查询失败：" + e.getMessage());
			throw new BusinessException("罪名分类查询失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccuseType> accuseTypeSelector() {
		AccuseTypeExample example = new AccuseTypeExample();
		example.createCriteria().andAccuseLevelEqualTo(1);
		example.setOrderByClause("ACCUSE_ORDER");
		List<AccuseType> accuseTypes= accuseTypeMapper.selectByExample(example);
		for(AccuseType accuseType:accuseTypes){
			AccuseTypeExample example1 = new AccuseTypeExample();
			example1.createCriteria().andParentIdEqualTo(accuseType.getAccuseId());
			example1.setOrderByClause("ACCUSE_ORDER");
			accuseType.setChildren(accuseTypeMapper.selectByExample(example1));
		}
		return accuseTypes;
	}
}
