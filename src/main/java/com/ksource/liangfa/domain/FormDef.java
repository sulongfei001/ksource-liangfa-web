package com.ksource.liangfa.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FormDef {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column FORM_DEF.FORM_DEF_ID
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	private Integer formDefId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column FORM_DEF.FORM_DEF_NAME
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	private String formDefName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column FORM_DEF.JSON_VIEW
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	@JsonIgnore
	private String jsonView;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column FORM_DEF.FORM_DEF_ID
	 * @return  the value of FORM_DEF.FORM_DEF_ID
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public Integer getFormDefId() {
		return formDefId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column FORM_DEF.FORM_DEF_ID
	 * @param formDefId  the value for FORM_DEF.FORM_DEF_ID
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public void setFormDefId(Integer formDefId) {
		this.formDefId = formDefId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column FORM_DEF.FORM_DEF_NAME
	 * @return  the value of FORM_DEF.FORM_DEF_NAME
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public String getFormDefName() {
		return formDefName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column FORM_DEF.FORM_DEF_NAME
	 * @param formDefName  the value for FORM_DEF.FORM_DEF_NAME
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public void setFormDefName(String formDefName) {
		this.formDefName = formDefName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column FORM_DEF.JSON_VIEW
	 * @return  the value of FORM_DEF.JSON_VIEW
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public String getJsonView() {
		return jsonView;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column FORM_DEF.JSON_VIEW
	 * @param jsonView  the value for FORM_DEF.JSON_VIEW
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public void setJsonView(String jsonView) {
		this.jsonView = jsonView;
	}
	
	/**
	 * 表单字段集合
	 */
	private List<FormField> formFieldList;

	public List<FormField> getFormFieldList() {
		return formFieldList;
	}

	public void setFormFieldList(List<FormField> formFieldList) {
		this.formFieldList = formFieldList;
	}
}