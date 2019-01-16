package com.ksource.liangfa.service.specialactivity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.ActivityCase;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.SpecialActivity;

/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0 date 2012-7-17 time 下午4:39:24
 */
public interface SpecialActivityService {

	/**
	 * 通过主键查询专项活动信息
	 * 可查出：专项活动中牵头单位的名字(liangfaLeaderName)及专项活动的参与人员(memeberList)
	 * @param id
	 * @return
	 */
	SpecialActivity findByPk(Integer id);

	/**
	 * 通过条件查询专项活动
	 * @param acti  专项活动集合
	 * @return
	 */
	List<SpecialActivity> find(SpecialActivity acti,Map<String,Object> map);

	/**
	 * 添加专项活动信息及其参与单位集合信息
	 * @param acti
	 * @param orgIds
	 */
	void add(SpecialActivity acti, String orgIds);

	/**
	 * 更新专项活动信息同时也更新(先删除后添加)专项活动的参与人员
	 *
	 * @param acti
	 * @param orgIds
	 */
	void update(SpecialActivity acti, String orgIds);


	/**
	 * 删除专项活动信息及其参与机构信息
	 * @param check
	 */
	void del(int id);
	
	/**
	 *查询参与专项活动案件信息 
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> queryActivityCase(String page,Map<String, Object> paramMap);
	
	/**
	 * 调整专项活动案件归属
	 * @param activityCase
	 * @return
	 */
	 boolean saveChangeActivity(ActivityCase activityCase);

	 /**
	  * 查询专项活动的名称和ID
	  * @return
	  */
	 public List<SpecialActivity> getSpecialActivityName();
}
