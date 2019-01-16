package com.ksource.liangfa.service.specialactivity;

import java.util.List;
import java.util.Map;

import com.ksource.liangfa.domain.DqdjCategory;
import com.ksource.liangfa.domain.DqdjChart;

/**
 *@author wangzhenya
 *@2013-1-6 下午4:52:14
 */
public interface DqdjCategoryService {

	/**
	 * 根据父类ID查询打侵打假案件类别
	 * @param parentId
	 * @return
	 */
	public List<DqdjCategory> getDqdjCategoryTree(Integer parentId);
	
	/**
	 * 打侵打假图表
	 * @return 
	 */
	public DqdjChart querydqdjCharts();
}
