package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.ClueInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClueInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    int countByExample(ClueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    int deleteByExample(ClueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    int deleteByPrimaryKey(Integer clueId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    int insert(ClueInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    int insertSelective(ClueInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    List<ClueInfo> selectByExampleWithBLOBs(ClueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    List<ClueInfo> selectByExample(ClueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    ClueInfo selectByPrimaryKey(Integer clueId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    int updateByExampleSelective(@Param("record") ClueInfo record, @Param("example") ClueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") ClueInfo record, @Param("example") ClueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    int updateByExample(@Param("record") ClueInfo record, @Param("example") ClueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    int updateByPrimaryKeySelective(ClueInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(ClueInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_INFO
     *
     * @mbggenerated Mon May 15 10:09:25 CST 2017
     */
    int updateByPrimaryKey(ClueInfo record);
}