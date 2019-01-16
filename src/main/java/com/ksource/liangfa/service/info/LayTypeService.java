package com.ksource.liangfa.service.info;

import java.util.List;
import com.ksource.liangfa.domain.InfoType;

public interface LayTypeService {
	/**
	 * 查询法律法规类型下是否所拥有信息，如果没有就可以删除
	 * @param typeId
	 * @return
	 */
	public boolean delete(String typeId);
	
	/**
	 * 查询法律法规类型
	 * @param infoType 
	 * @return
	 */
	public List<InfoType> queryLayTypes(InfoType infoType);	
}