package com.ksource.liangfa.domain;

public class NoticeOrg {
	
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOTICE_ORG.NOTICE_ID
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	private Integer noticeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOTICE_ORG.ORG_ID
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	private Integer orgId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOTICE_ORG.NOTICE_ID
	 * @return  the value of NOTICE_ORG.NOTICE_ID
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	public Integer getNoticeId() {
		return noticeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOTICE_ORG.NOTICE_ID
	 * @param noticeId  the value for NOTICE_ORG.NOTICE_ID
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOTICE_ORG.ORG_ID
	 * @return  the value of NOTICE_ORG.ORG_ID
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOTICE_ORG.ORG_ID
	 * @param orgId  the value for NOTICE_ORG.ORG_ID
	 * @mbggenerated  Thu Jun 30 19:40:49 CST 2011
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	
	private String orgs;
	
	private String orgName;

	public String getOrgs() {
		return orgs;
	}

	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    
}