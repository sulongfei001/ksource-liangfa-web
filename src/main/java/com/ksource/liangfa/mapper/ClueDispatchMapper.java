package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.ClueDispatch;
import com.ksource.liangfa.domain.ClueDispatchExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ClueDispatchMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_DISPATCH
     *
     * @mbggenerated Mon May 15 18:57:03 CST 2017
     */
    int countByExample(ClueDispatchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_DISPATCH
     *
     * @mbggenerated Mon May 15 18:57:03 CST 2017
     */
    int deleteByExample(ClueDispatchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_DISPATCH
     *
     * @mbggenerated Mon May 15 18:57:03 CST 2017
     */
    int deleteByPrimaryKey(Integer dispatchId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_DISPATCH
     *
     * @mbggenerated Mon May 15 18:57:03 CST 2017
     */
    int insert(ClueDispatch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_DISPATCH
     *
     * @mbggenerated Mon May 15 18:57:03 CST 2017
     */
    int insertSelective(ClueDispatch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_DISPATCH
     *
     * @mbggenerated Mon May 15 18:57:03 CST 2017
     */
    List<ClueDispatch> selectByExample(ClueDispatchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_DISPATCH
     *
     * @mbggenerated Mon May 15 18:57:03 CST 2017
     */
    ClueDispatch selectByPrimaryKey(Integer dispatchId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_DISPATCH
     *
     * @mbggenerated Mon May 15 18:57:03 CST 2017
     */
    int updateByExampleSelective(@Param("record") ClueDispatch record, @Param("example") ClueDispatchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_DISPATCH
     *
     * @mbggenerated Mon May 15 18:57:03 CST 2017
     */
    int updateByExample(@Param("record") ClueDispatch record, @Param("example") ClueDispatchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_DISPATCH
     *
     * @mbggenerated Mon May 15 18:57:03 CST 2017
     */
    int updateByPrimaryKeySelective(ClueDispatch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_DISPATCH
     *
     * @mbggenerated Mon May 15 18:57:03 CST 2017
     */
    int updateByPrimaryKey(ClueDispatch record);

	void updateDispatchState(ClueDispatch dispatch);

	List<ClueDispatch> getClueReadList(Integer clueInfo);
	
	/**
	 * 查询线索查阅情况
	 * @param map
	 * @return
	 */
	List<ClueDispatch> getClueReadInfo(Map<String,Object> map);
}