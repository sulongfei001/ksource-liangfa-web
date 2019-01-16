package com.ksource.liangfa.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ksource.liangfa.domain.NoticeOrg;
import com.ksource.liangfa.domain.NoticeOrgExample;

public interface NoticeOrgMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_ORG
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	int countByExample(NoticeOrgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_ORG
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	int deleteByExample(NoticeOrgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_ORG
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	int insert(NoticeOrg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_ORG
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	int insertSelective(NoticeOrg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_ORG
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	List<NoticeOrg> selectByExample(NoticeOrgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_ORG
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	int updateByExampleSelective(@Param("record") NoticeOrg record,
			@Param("example") NoticeOrgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_ORG
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	int updateByExample(@Param("record") NoticeOrg record,
			@Param("example") NoticeOrgExample example);

	List<NoticeOrg> selectByNoticeId(@Param("noticeId")Integer noticeId);
	
	/**
	 * 获取通知公告的参与组织数量
	 */
	int getNoticeOrgCount(@Param("noticeId")Integer noticeId);
}