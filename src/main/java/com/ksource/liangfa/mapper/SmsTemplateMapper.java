package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.SmsTemplate;
import com.ksource.liangfa.domain.SmsTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsTemplateMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SMS_TEMPLATE
	 * @mbggenerated  Tue Dec 13 15:22:34 CST 2011
	 */
	int countByExample(SmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SMS_TEMPLATE
	 * @mbggenerated  Tue Dec 13 15:22:34 CST 2011
	 */
	int deleteByExample(SmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SMS_TEMPLATE
	 * @mbggenerated  Tue Dec 13 15:22:34 CST 2011
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SMS_TEMPLATE
	 * @mbggenerated  Tue Dec 13 15:22:34 CST 2011
	 */
	int insert(SmsTemplate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SMS_TEMPLATE
	 * @mbggenerated  Tue Dec 13 15:22:34 CST 2011
	 */
	int insertSelective(SmsTemplate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SMS_TEMPLATE
	 * @mbggenerated  Tue Dec 13 15:22:34 CST 2011
	 */
	List<SmsTemplate> selectByExample(SmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SMS_TEMPLATE
	 * @mbggenerated  Tue Dec 13 15:22:34 CST 2011
	 */
	SmsTemplate selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SMS_TEMPLATE
	 * @mbggenerated  Tue Dec 13 15:22:34 CST 2011
	 */
	int updateByExampleSelective(@Param("record") SmsTemplate record,
			@Param("example") SmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SMS_TEMPLATE
	 * @mbggenerated  Tue Dec 13 15:22:34 CST 2011
	 */
	int updateByExample(@Param("record") SmsTemplate record,
			@Param("example") SmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SMS_TEMPLATE
	 * @mbggenerated  Tue Dec 13 15:22:34 CST 2011
	 */
	int updateByPrimaryKeySelective(SmsTemplate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SMS_TEMPLATE
	 * @mbggenerated  Tue Dec 13 15:22:34 CST 2011
	 */
	int updateByPrimaryKey(SmsTemplate record);
}