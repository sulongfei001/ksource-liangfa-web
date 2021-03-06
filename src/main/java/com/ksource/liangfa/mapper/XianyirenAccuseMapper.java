package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.XianyirenAccuseExample;
import com.ksource.liangfa.domain.XianyirenAccuseKey;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ksource.liangfa.domain.XianyirenAccuse;

public interface XianyirenAccuseMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.XIANYIREN_ACCUSE
	 * @mbggenerated  Thu Sep 25 11:48:59 CST 2014
	 */
	int countByExample(XianyirenAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.XIANYIREN_ACCUSE
	 * @mbggenerated  Thu Sep 25 11:48:59 CST 2014
	 */
	int deleteByExample(XianyirenAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.XIANYIREN_ACCUSE
	 * @mbggenerated  Thu Sep 25 11:48:59 CST 2014
	 */
	int deleteByPrimaryKey(XianyirenAccuseKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.XIANYIREN_ACCUSE
	 * @mbggenerated  Thu Sep 25 11:48:59 CST 2014
	 */
	int insert(XianyirenAccuse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.XIANYIREN_ACCUSE
	 * @mbggenerated  Thu Sep 25 11:48:59 CST 2014
	 */
	int insertSelective(XianyirenAccuse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.XIANYIREN_ACCUSE
	 * @mbggenerated  Thu Sep 25 11:48:59 CST 2014
	 */
	List<XianyirenAccuse> selectByExample(XianyirenAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.XIANYIREN_ACCUSE
	 * @mbggenerated  Thu Sep 25 11:48:59 CST 2014
	 */
	XianyirenAccuse selectByPrimaryKey(XianyirenAccuseKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.XIANYIREN_ACCUSE
	 * @mbggenerated  Thu Sep 25 11:48:59 CST 2014
	 */
	int updateByExampleSelective(@Param("record") XianyirenAccuse record,
			@Param("example") XianyirenAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.XIANYIREN_ACCUSE
	 * @mbggenerated  Thu Sep 25 11:48:59 CST 2014
	 */
	int updateByExample(@Param("record") XianyirenAccuse record,
			@Param("example") XianyirenAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.XIANYIREN_ACCUSE
	 * @mbggenerated  Thu Sep 25 11:48:59 CST 2014
	 */
	int updateByPrimaryKeySelective(XianyirenAccuse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.XIANYIREN_ACCUSE
	 * @mbggenerated  Thu Sep 25 11:48:59 CST 2014
	 */
	int updateByPrimaryKey(XianyirenAccuse record);

	List<XianyirenAccuse> queryNoExportDataList(Map<String, Object> paramMap);

	/**
	 * 根据caseid删除，嫌疑人和罪名对应的关系
	 * 
	 * @return:void
	 * @createTime:2017年11月7日 下午7:25:01
	 */
	void deleteAccuseByCaseId(Map<String, Object> paraMap);
}