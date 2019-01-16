package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.DistrictExternal;
import com.ksource.liangfa.domain.DistrictExternalExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DistrictExternalMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.DISTRICT_EXTERNAL
     *
     * @mbggenerated Fri Mar 25 09:51:43 CST 2016
     */
    int countByExample(DistrictExternalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.DISTRICT_EXTERNAL
     *
     * @mbggenerated Fri Mar 25 09:51:43 CST 2016
     */
    int deleteByExample(DistrictExternalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.DISTRICT_EXTERNAL
     *
     * @mbggenerated Fri Mar 25 09:51:43 CST 2016
     */
    int deleteByPrimaryKey(String districtCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.DISTRICT_EXTERNAL
     *
     * @mbggenerated Fri Mar 25 09:51:43 CST 2016
     */
    int insert(DistrictExternal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.DISTRICT_EXTERNAL
     *
     * @mbggenerated Fri Mar 25 09:51:43 CST 2016
     */
    int insertSelective(DistrictExternal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.DISTRICT_EXTERNAL
     *
     * @mbggenerated Fri Mar 25 09:51:43 CST 2016
     */
    List<DistrictExternal> selectByExample(DistrictExternalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.DISTRICT_EXTERNAL
     *
     * @mbggenerated Fri Mar 25 09:51:43 CST 2016
     */
    DistrictExternal selectByPrimaryKey(String districtCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.DISTRICT_EXTERNAL
     *
     * @mbggenerated Fri Mar 25 09:51:43 CST 2016
     */
    int updateByExampleSelective(@Param("record") DistrictExternal record, @Param("example") DistrictExternalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.DISTRICT_EXTERNAL
     *
     * @mbggenerated Fri Mar 25 09:51:43 CST 2016
     */
    int updateByExample(@Param("record") DistrictExternal record, @Param("example") DistrictExternalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.DISTRICT_EXTERNAL
     *
     * @mbggenerated Fri Mar 25 09:51:43 CST 2016
     */
    int updateByPrimaryKeySelective(DistrictExternal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.DISTRICT_EXTERNAL
     *
     * @mbggenerated Fri Mar 25 09:51:43 CST 2016
     */
    int updateByPrimaryKey(DistrictExternal record);

	List<DistrictExternal> districtTreeManage(Map<String, Object> map);
	
	List<DistrictExternal> districtTreeCommunion(Map<String, Object> map);

	List<DistrictExternal> queryDistrictForSync(@Param("jb") Integer jb);
}