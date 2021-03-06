package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.NoticeRead;
import com.ksource.liangfa.domain.NoticeReadExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface NoticeReadMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	int countByExample(NoticeReadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	int deleteByExample(NoticeReadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	int insert(NoticeRead record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	int insertSelective(NoticeRead record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	List<NoticeRead> selectByExample(NoticeReadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	NoticeRead selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	int updateByExampleSelective(@Param("record") NoticeRead record,
			@Param("example") NoticeReadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	int updateByExample(@Param("record") NoticeRead record,
			@Param("example") NoticeReadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	int updateByPrimaryKeySelective(NoticeRead record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	int updateByPrimaryKey(NoticeRead record);
	
	/**
	 * 获取通知公告的已读信息
	 */
	int noticeReadCount(NoticeRead record);
	
	/**
	 * 获取通知公告未读数量
	 * @param map
	 * @return
	 */
	int getNoReadNoticeCount(Map<String,Object> map);
}