package com.ksource.liangfa.service.system;

import java.util.List;
import java.util.Map;

import com.ksource.liangfa.domain.AccuseType;

/**
 *@author 王振亚
 *@2012-7-19 下午5:27:06
 */
public interface AccuseTypeService {
	public List<AccuseType> find(Map<String, Object> map);

	public List<AccuseType> accuseTypeSelector();
	
}
