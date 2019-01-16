package com.ksource.liangfa.service.info;

import java.util.List;
import com.ksource.liangfa.domain.ZhifaInfoType;

public interface ZhifaTypeService {
	/**
	 * 查询执法动态类型下是否所拥有信息，如果没有就可以删除
	 * @param typeId
	 * @return
	 */
	public boolean delete(Integer typeId);
	
	/**
	 * 查询执法动态类型
	 * @return
	 */
	public List<ZhifaInfoType> queryZhifaInfoTypes(ZhifaInfoType zhifaInfoType);
	
}