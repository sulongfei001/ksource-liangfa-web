package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.SuperviseCase;
import com.ksource.liangfa.domain.SuperviseCaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SuperviseCaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    int countByExample(SuperviseCaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    int deleteByExample(SuperviseCaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    int deleteByPrimaryKey(Integer superviseId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    int insert(SuperviseCase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    int insertSelective(SuperviseCase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    List<SuperviseCase> selectByExampleWithBLOBs(SuperviseCaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    List<SuperviseCase> selectByExample(SuperviseCaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    SuperviseCase selectByPrimaryKey(Integer superviseId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    int updateByExampleSelective(@Param("record") SuperviseCase record, @Param("example") SuperviseCaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    int updateByExampleWithBLOBs(@Param("record") SuperviseCase record, @Param("example") SuperviseCaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    int updateByExample(@Param("record") SuperviseCase record, @Param("example") SuperviseCaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    int updateByPrimaryKeySelective(SuperviseCase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    int updateByPrimaryKeyWithBLOBs(SuperviseCase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SUPERVISE_CASE
     *
     * @mbggenerated Tue Apr 16 14:51:03 CST 2013
     */
    int updateByPrimaryKey(SuperviseCase record);
    
    SuperviseCase detail(Integer superviseId);
    
}