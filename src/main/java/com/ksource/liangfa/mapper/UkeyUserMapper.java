package com.ksource.liangfa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ksource.liangfa.domain.UkeyUser;
import com.ksource.liangfa.domain.UkeyUserExample;
import com.ksource.syscontext.Const;

public interface UkeyUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    int countByExample(UkeyUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    int deleteByExample(UkeyUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    int deleteByPrimaryKey(String serialNumber);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    int insert(UkeyUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    int insertSelective(UkeyUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    List<UkeyUser> selectByExample(UkeyUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    UkeyUser selectByPrimaryKey(String serialNumber);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    int updateByExampleSelective(@Param("record") UkeyUser record, @Param("example") UkeyUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    int updateByExample(@Param("record") UkeyUser record, @Param("example") UkeyUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    int updateByPrimaryKeySelective(UkeyUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    int updateByPrimaryKey(UkeyUser record);
}