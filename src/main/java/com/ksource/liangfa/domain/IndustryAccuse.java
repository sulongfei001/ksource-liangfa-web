package com.ksource.liangfa.domain;

public class IndustryAccuse {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE.INDUSTRY_TYPE
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	private String industryType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE.ACCUSE_ID
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	private String accuseId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE.INDUSTRY_TYPE
	 * @return  the value of LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE.INDUSTRY_TYPE
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	public String getIndustryType() {
		return industryType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE.INDUSTRY_TYPE
	 * @param industryType  the value for LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE.INDUSTRY_TYPE
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE.ACCUSE_ID
	 * @return  the value of LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE.ACCUSE_ID
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	public String getAccuseId() {
		return accuseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE.ACCUSE_ID
	 * @param accuseId  the value for LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE.ACCUSE_ID
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	public void setAccuseId(String accuseId) {
		this.accuseId = accuseId;
	}

	private String industryName;
    private String accuseName;
    private String clause;
    private String law;

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getAccuseName() {
		return accuseName;
	}

	public void setAccuseName(String accuseName) {
		this.accuseName = accuseName;
	}

	public String getClause() {
		return clause;
	}

	public void setClause(String clause) {
		this.clause = clause;
	}

	public String getLaw() {
		return law;
	}

	public void setLaw(String law) {
		this.law = law;
	}
    
}