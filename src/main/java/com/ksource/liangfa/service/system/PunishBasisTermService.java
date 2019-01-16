package com.ksource.liangfa.service.system;

import java.util.List;
import com.ksource.liangfa.domain.PunishBasisTerm;

/**
 * 行政处罚依据项 接口
 * 
 * @author lijiajia
 * 
 */
public interface PunishBasisTermService {

	/**
	 * 根据行政处罚依据id获取行政处罚依据项
	 * @param punishBasisTerm
	 * @return
	 */
	public List<PunishBasisTerm> getTermByBasisId(PunishBasisTerm param);
	
	/**
	 * 根据basisId删除行政处罚依据项信息
	 * @param basisId
	 * @return
	 */
	public int deleteByBasisId(Integer basisId);
	
	public int deleteTerm(Integer termId);
}