package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.Module;
import com.ksource.liangfa.domain.ModuleExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ModuleMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	int countByExample(ModuleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	int deleteByExample(ModuleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	int deleteByPrimaryKey(Integer moduleId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	int insert(Module record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	int insertSelective(Module record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	List<Module> selectByExample(ModuleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	Module selectByPrimaryKey(Integer moduleId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	int updateByExampleSelective(@Param("record") Module record,
			@Param("example") ModuleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	int updateByExample(@Param("record") Module record,
			@Param("example") ModuleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	int updateByPrimaryKeySelective(Module record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	int updateByPrimaryKey(Module record);

	List<Module> queryGrantMenusByParentId(Module menu);

	List<Module> queryGrantedMenus(Integer roleId);

	List<Module> getChildModule(Map<String, String> paramMap);
	
	List<Module> getModules(Map<String, Integer> paramMap);
}