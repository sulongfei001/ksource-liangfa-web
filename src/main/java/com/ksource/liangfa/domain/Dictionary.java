package com.ksource.liangfa.domain;

public class Dictionary extends DictionaryKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column DICTIONARY_.DT_NAME
	 * @mbggenerated  Thu Jul 07 15:48:12 CST 2011
	 */
	private String dtName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column DICTIONARY_.ACTIVE
	 * @mbggenerated  Thu Jul 07 15:48:12 CST 2011
	 */
	private Integer active;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column DICTIONARY_.SERIAL
	 * @mbggenerated  Thu Jul 07 15:48:12 CST 2011
	 */
	private Integer serial;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column DICTIONARY_.DT_NAME
	 * @return  the value of DICTIONARY_.DT_NAME
	 * @mbggenerated  Thu Jul 07 15:48:12 CST 2011
	 */
	public String getDtName() {
		return dtName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column DICTIONARY_.DT_NAME
	 * @param dtName  the value for DICTIONARY_.DT_NAME
	 * @mbggenerated  Thu Jul 07 15:48:12 CST 2011
	 */
	public void setDtName(String dtName) {
		this.dtName = dtName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column DICTIONARY_.ACTIVE
	 * @return  the value of DICTIONARY_.ACTIVE
	 * @mbggenerated  Thu Jul 07 15:48:12 CST 2011
	 */
	public Integer getActive() {
		return active;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column DICTIONARY_.ACTIVE
	 * @param active  the value for DICTIONARY_.ACTIVE
	 * @mbggenerated  Thu Jul 07 15:48:12 CST 2011
	 */
	public void setActive(Integer active) {
		this.active = active;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column DICTIONARY_.SERIAL
	 * @return  the value of DICTIONARY_.SERIAL
	 * @mbggenerated  Thu Jul 07 15:48:12 CST 2011
	 */
	public Integer getSerial() {
		return serial;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column DICTIONARY_.SERIAL
	 * @param serial  the value for DICTIONARY_.SERIAL
	 * @mbggenerated  Thu Jul 07 15:48:12 CST 2011
	 */
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
}