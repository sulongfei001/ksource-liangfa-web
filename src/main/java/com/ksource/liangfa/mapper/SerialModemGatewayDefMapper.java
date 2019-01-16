package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.SerialModemGatewayDef;
import com.ksource.liangfa.domain.SerialModemGatewayDefExample;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SerialModemGatewayDefMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SERIAL_MODEM_GATEWAY_DEF
	 * @mbggenerated  Thu Dec 01 16:12:06 CST 2011
	 */
	int countByExample(SerialModemGatewayDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SERIAL_MODEM_GATEWAY_DEF
	 * @mbggenerated  Thu Dec 01 16:12:06 CST 2011
	 */
	int deleteByExample(SerialModemGatewayDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SERIAL_MODEM_GATEWAY_DEF
	 * @mbggenerated  Thu Dec 01 16:12:06 CST 2011
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SERIAL_MODEM_GATEWAY_DEF
	 * @mbggenerated  Thu Dec 01 16:12:06 CST 2011
	 */
	int insert(SerialModemGatewayDef record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SERIAL_MODEM_GATEWAY_DEF
	 * @mbggenerated  Thu Dec 01 16:12:06 CST 2011
	 */
	int insertSelective(SerialModemGatewayDef record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SERIAL_MODEM_GATEWAY_DEF
	 * @mbggenerated  Thu Dec 01 16:12:06 CST 2011
	 */
	List<SerialModemGatewayDef> selectByExample(
			SerialModemGatewayDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SERIAL_MODEM_GATEWAY_DEF
	 * @mbggenerated  Thu Dec 01 16:12:06 CST 2011
	 */
	SerialModemGatewayDef selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SERIAL_MODEM_GATEWAY_DEF
	 * @mbggenerated  Thu Dec 01 16:12:06 CST 2011
	 */
	int updateByExampleSelective(@Param("record") SerialModemGatewayDef record,
			@Param("example") SerialModemGatewayDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SERIAL_MODEM_GATEWAY_DEF
	 * @mbggenerated  Thu Dec 01 16:12:06 CST 2011
	 */
	int updateByExample(@Param("record") SerialModemGatewayDef record,
			@Param("example") SerialModemGatewayDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SERIAL_MODEM_GATEWAY_DEF
	 * @mbggenerated  Thu Dec 01 16:12:06 CST 2011
	 */
	int updateByPrimaryKeySelective(SerialModemGatewayDef record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SERIAL_MODEM_GATEWAY_DEF
	 * @mbggenerated  Thu Dec 01 16:12:06 CST 2011
	 */
	int updateByPrimaryKey(SerialModemGatewayDef record);
}