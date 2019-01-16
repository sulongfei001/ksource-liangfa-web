package com.ksource.liangfa.service.system;

import java.util.List;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.PunishBasis;
import com.ksource.liangfa.domain.PunishBasisTerm;

/**
 * 行政处罚依据 接口
 * 
 * @author lijiajia
 * 
 */
public interface PunishBasisService {

	
	/**
	 * 查询行政处罚依据 分页信息
	 * @param illegalSituation
	 * @param page
	 * @return
	 */
	public PaginationHelper<PunishBasis> find(PunishBasis punishBasis, String page);
	
	/**
	 * 添加行政处罚依据 
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse insert(PunishBasis punishBasis,List<String> termInfoAry);
	
	/**
	 * 修改行政处罚依据 
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse update(PunishBasis punishBasis);
	
	/**
	 * 删除行政处罚依据 
	 */
	public int del(Integer basisId);
	
	/**
	 * 根据Id获取行政处罚依据信息
	 * @param id
	 * @return
	 */
	public PunishBasis selectByPrimaryKey(Integer id);
	
	
}