package com.ksource.liangfa.domain;

import java.math.BigDecimal;

public class DqdjCaseCategory {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column DQDJ_CASE_CATEGORY.CATEGORY_ID
	 * @mbggenerated  Sun Jan 06 16:50:19 CST 2013
	 */
	private Integer categoryId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column DQDJ_CASE_CATEGORY.CASE_ID
	 * @mbggenerated  Sun Jan 06 16:50:19 CST 2013
	 */
	private String caseId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column DQDJ_CASE_CATEGORY.CATEGORY_ID
	 * @return  the value of DQDJ_CASE_CATEGORY.CATEGORY_ID
	 * @mbggenerated  Sun Jan 06 16:50:19 CST 2013
	 */
	public Integer getCategoryId() {
		return categoryId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column DQDJ_CASE_CATEGORY.CATEGORY_ID
	 * @param categoryId  the value for DQDJ_CASE_CATEGORY.CATEGORY_ID
	 * @mbggenerated  Sun Jan 06 16:50:19 CST 2013
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column DQDJ_CASE_CATEGORY.CASE_ID
	 * @return  the value of DQDJ_CASE_CATEGORY.CASE_ID
	 * @mbggenerated  Sun Jan 06 16:50:19 CST 2013
	 */
	public String getCaseId() {
		return caseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column DQDJ_CASE_CATEGORY.CASE_ID
	 * @param caseId  the value for DQDJ_CASE_CATEGORY.CASE_ID
	 * @mbggenerated  Sun Jan 06 16:50:19 CST 2013
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
}