package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.NoticeFile;
import com.ksource.liangfa.domain.NoticeFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoticeFileMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_FILE
	 * @mbggenerated  Mon Feb 06 15:50:51 CST 2012
	 */
	int countByExample(NoticeFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_FILE
	 * @mbggenerated  Mon Feb 06 15:50:51 CST 2012
	 */
	int deleteByExample(NoticeFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_FILE
	 * @mbggenerated  Mon Feb 06 15:50:51 CST 2012
	 */
	int deleteByPrimaryKey(Integer fileId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_FILE
	 * @mbggenerated  Mon Feb 06 15:50:51 CST 2012
	 */
	int insert(NoticeFile record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_FILE
	 * @mbggenerated  Mon Feb 06 15:50:51 CST 2012
	 */
	int insertSelective(NoticeFile record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_FILE
	 * @mbggenerated  Mon Feb 06 15:50:51 CST 2012
	 */
	List<NoticeFile> selectByExample(NoticeFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_FILE
	 * @mbggenerated  Mon Feb 06 15:50:51 CST 2012
	 */
	NoticeFile selectByPrimaryKey(Integer fileId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_FILE
	 * @mbggenerated  Mon Feb 06 15:50:51 CST 2012
	 */
	int updateByExampleSelective(@Param("record") NoticeFile record,
			@Param("example") NoticeFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_FILE
	 * @mbggenerated  Mon Feb 06 15:50:51 CST 2012
	 */
	int updateByExample(@Param("record") NoticeFile record,
			@Param("example") NoticeFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_FILE
	 * @mbggenerated  Mon Feb 06 15:50:51 CST 2012
	 */
	int updateByPrimaryKeySelective(NoticeFile record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_FILE
	 * @mbggenerated  Mon Feb 06 15:50:51 CST 2012
	 */
	int updateByPrimaryKey(NoticeFile record);
}