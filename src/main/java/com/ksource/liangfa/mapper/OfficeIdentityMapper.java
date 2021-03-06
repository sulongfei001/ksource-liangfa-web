package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.OfficeIdentity;
import com.ksource.liangfa.domain.OfficeIdentityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OfficeIdentityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_IDENTITY
     *
     * @mbggenerated Mon Sep 05 10:12:42 CST 2016
     */
    int countByExample(OfficeIdentityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_IDENTITY
     *
     * @mbggenerated Mon Sep 05 10:12:42 CST 2016
     */
    int deleteByExample(OfficeIdentityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_IDENTITY
     *
     * @mbggenerated Mon Sep 05 10:12:42 CST 2016
     */
    int deleteByPrimaryKey(Integer identityId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_IDENTITY
     *
     * @mbggenerated Mon Sep 05 10:12:42 CST 2016
     */
    int insert(OfficeIdentity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_IDENTITY
     *
     * @mbggenerated Mon Sep 05 10:12:42 CST 2016
     */
    int insertSelective(OfficeIdentity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_IDENTITY
     *
     * @mbggenerated Mon Sep 05 10:12:42 CST 2016
     */
    List<OfficeIdentity> selectByExample(OfficeIdentityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_IDENTITY
     *
     * @mbggenerated Mon Sep 05 10:12:42 CST 2016
     */
    OfficeIdentity selectByPrimaryKey(Integer identityId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_IDENTITY
     *
     * @mbggenerated Mon Sep 05 10:12:42 CST 2016
     */
    int updateByExampleSelective(@Param("record") OfficeIdentity record, @Param("example") OfficeIdentityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_IDENTITY
     *
     * @mbggenerated Mon Sep 05 10:12:42 CST 2016
     */
    int updateByExample(@Param("record") OfficeIdentity record, @Param("example") OfficeIdentityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_IDENTITY
     *
     * @mbggenerated Mon Sep 05 10:12:42 CST 2016
     */
    int updateByPrimaryKeySelective(OfficeIdentity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_IDENTITY
     *
     * @mbggenerated Mon Sep 05 10:12:42 CST 2016
     */
    int updateByPrimaryKey(OfficeIdentity record);

	Integer countByAlias(@Param("alias")String alias, @Param("identityId")Integer identityId);

	OfficeIdentity selectByAlias(@Param("alias")String alias);

	int updateVersion(OfficeIdentity identity);
}