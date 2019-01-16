package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.domain.TaskBindExample;
import com.ksource.liangfa.domain.TaskBindKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskBindMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	int countByExample(TaskBindExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	int deleteByExample(TaskBindExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	int deleteByPrimaryKey(TaskBindKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	int insert(TaskBind record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	int insertSelective(TaskBind record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	List<TaskBind> selectByExample(TaskBindExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	TaskBind selectByPrimaryKey(TaskBindKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	int updateByExampleSelective(@Param("record") TaskBind record,
			@Param("example") TaskBindExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	int updateByExample(@Param("record") TaskBind record,
			@Param("example") TaskBindExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	int updateByPrimaryKeySelective(TaskBind record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	int updateByPrimaryKey(TaskBind record);
	
	/**
	 * 查询任务表单
	 * @return
	 */
	List<TaskBind> getNewTaskBindList();
}