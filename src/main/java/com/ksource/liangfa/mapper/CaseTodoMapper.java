package com.ksource.liangfa.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.CaseTodoExample;

public interface CaseTodoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    int countByExample(CaseTodoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    int deleteByExample(CaseTodoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    int deleteByPrimaryKey(Integer todoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    int insert(CaseTodo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    int insertSelective(CaseTodo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    List<CaseTodo> selectByExample(CaseTodoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    CaseTodo selectByPrimaryKey(Integer todoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    int updateByExampleSelective(@Param("record") CaseTodo record, @Param("example") CaseTodoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    int updateByExample(@Param("record") CaseTodo record, @Param("example") CaseTodoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    int updateByPrimaryKeySelective(CaseTodo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    int updateByPrimaryKey(CaseTodo record);
    
    /**
     * 待办案件列表
     * @param caseTodo
     * @return
     */
    List<CaseTodo> getCaseTodoList(Map<String,Object> paramMap);
    
    int getCaseTodoCount(Map<String,Object> paramMap);

	int getTodoCount(@Param("orgCode")Integer orgCode);

	CaseTodo getTodoCountForXingzheng(@Param("orgCode")Integer orgCode);

	void deleteOldCaseByCaseId(String oldCaseId);
	
	/**
	 * 查询公安机关行政拘留待办案件数量
	 * @param paramMap
	 * @return
	 */
	int getTransferDetentionTodoCount(Map<String,Object> paramMap);

	int getLianSupTodoCount(Map<String, Object> map);
    
}