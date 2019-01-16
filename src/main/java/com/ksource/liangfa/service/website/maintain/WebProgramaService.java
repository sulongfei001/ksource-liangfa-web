package com.ksource.liangfa.service.website.maintain;

import java.util.List;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.WebPrograma;

/**
 *@author wangzhenya
 *@2012-10-26 上午11:11:06
 */
public interface WebProgramaService {

	/**
	 * 查询栏目管理，并实现内存分页
	 * @param page
	 * @return
	 */
	public PaginationHelper<WebPrograma> find(WebPrograma webPrograma,String page);
	
	/**
	 * 查询所有栏目
	 * @return
	 */
	public List<WebPrograma> webProgramaTree();
	
	/**
	 * 查询栏目,根据栏目在首页的显示位置字段排序
	 * @return
	 */
	public List<WebPrograma> selectHomeLocation();
}
