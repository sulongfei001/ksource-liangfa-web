package com.ksource.liangfa.service.info;

import java.util.List;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.LayInfo;
import com.ksource.liangfa.domain.ZhifaInfo;

/**
 * 信息管理（包含类型和详细信息维护）
 * 
 * @author junxy
 * 
 */
public interface InfoService {

	/**
	 * 查询法律法规信息
	 * 
	 * @param info
	 *            查询条件
	 * @param page
	 *            页码
	 * @return
	 */
	public PaginationHelper<LayInfo> queryInfos(LayInfo info, String page);

	/**
	 * 查询执法信息
	 * 
	 * @param info
	 *            查询条件
	 * @param page
	 *            页码
	 * @return
	 */
	public PaginationHelper<ZhifaInfo> queryZhiFaInfos(ZhifaInfo info,
			String page);

	/**
	 * 查询法律法规信息
	 * 
	 * @param layInfo
	 *            查询条件
	 * @param pageSize
	 *            　截取符合查询条件的条数(比如:pageSize=8则查询8条,如果符合条件的条数不够8条，就全查询出来)
	 * @return
	 */
	public List<LayInfo> queryInfos(LayInfo layInfo, int pageSize);

	/**
	 * 查询法律法规信息
	 * 
	 * @param layInfo
	 *            查询条件
	 */
	public List<LayInfo> queryLayInfos(LayInfo layInfo);

	/**
	 * 删除法律法规及与法律法规信息相关的上传图片。
	 * 
	 * @param infoId
	 */
	public ServiceResponse delLay(String infoId);

	/**
	 * 删除执法信息及与法执法动态信息相关的上传图片。
	 * 
	 * @param infoId
	 */
	public ServiceResponse delZhifa(String infoId);

	/**
	 * 添加法律法规信息
	 * 
	 * @param info
	 * @return
	 */
	public ServiceResponse insertLay(LayInfo layInfo);

	/**
	 * 修改法律法规信息
	 * 
	 * @param info
	 * @return
	 */
	public ServiceResponse updateLayByPrimaryKeySelective(LayInfo layInfo);

	/**
	 * 添加执法动态信息
	 * 
	 * @param info
	 * @return
	 */
	public ServiceResponse insertZhifaInfo(ZhifaInfo info);

	/**
	 * 修改执法动态信息
	 * 
	 * @param info
	 * @return
	 */
	public ServiceResponse updateZhifaByPrimaryKeySelective(ZhifaInfo info);
	
	/**
	 * 批量删除法律法则
	 * @param check
	 * @return
	 */
	public ServiceResponse batchDeleteLay(String[] infoIds);

	/**
	 * 批量删除执法动态信息
	 * @param check
	 */
	public ServiceResponse batchDeleteZhifa(String[] check);

	public LayInfo selectByPrimaryKey(String infoId);
}