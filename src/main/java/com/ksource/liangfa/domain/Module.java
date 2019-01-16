package com.ksource.liangfa.domain;

import java.util.List;

public class Module {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.MODULE_ID
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	private Integer moduleId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.MODULE_NAME
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	private String moduleName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.MODULE_URL
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	private String moduleUrl;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.IS_LEAF
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	private Integer isLeaf;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.PARENT_ID
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	private Integer parentId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.ORDER_NO
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	private Integer orderNo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.MODULE_NOTE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	private String moduleNote;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.MODULE_CATEGORY
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	private Integer moduleCategory;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.IS_MAINTAIN
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	private Integer isMaintain;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.MODULE_ID
	 * @return  the value of MODULE.MODULE_ID
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public Integer getModuleId() {
		return moduleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.MODULE_ID
	 * @param moduleId  the value for MODULE.MODULE_ID
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.MODULE_NAME
	 * @return  the value of MODULE.MODULE_NAME
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.MODULE_NAME
	 * @param moduleName  the value for MODULE.MODULE_NAME
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.MODULE_URL
	 * @return  the value of MODULE.MODULE_URL
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public String getModuleUrl() {
		return moduleUrl;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.MODULE_URL
	 * @param moduleUrl  the value for MODULE.MODULE_URL
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.IS_LEAF
	 * @return  the value of MODULE.IS_LEAF
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public Integer getIsLeaf() {
		return isLeaf;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.IS_LEAF
	 * @param isLeaf  the value for MODULE.IS_LEAF
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.PARENT_ID
	 * @return  the value of MODULE.PARENT_ID
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.PARENT_ID
	 * @param parentId  the value for MODULE.PARENT_ID
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.ORDER_NO
	 * @return  the value of MODULE.ORDER_NO
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public Integer getOrderNo() {
		return orderNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.ORDER_NO
	 * @param orderNo  the value for MODULE.ORDER_NO
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.MODULE_NOTE
	 * @return  the value of MODULE.MODULE_NOTE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public String getModuleNote() {
		return moduleNote;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.MODULE_NOTE
	 * @param moduleNote  the value for MODULE.MODULE_NOTE
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public void setModuleNote(String moduleNote) {
		this.moduleNote = moduleNote;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.MODULE_CATEGORY
	 * @return  the value of MODULE.MODULE_CATEGORY
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public Integer getModuleCategory() {
		return moduleCategory;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.MODULE_CATEGORY
	 * @param moduleCategory  the value for MODULE.MODULE_CATEGORY
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public void setModuleCategory(Integer moduleCategory) {
		this.moduleCategory = moduleCategory;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.IS_MAINTAIN
	 * @return  the value of MODULE.IS_MAINTAIN
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public Integer getIsMaintain() {
		return isMaintain;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.IS_MAINTAIN
	 * @param isMaintain  the value for MODULE.IS_MAINTAIN
	 * @mbggenerated  Tue Mar 13 16:36:23 CST 2012
	 */
	public void setIsMaintain(Integer isMaintain) {
		this.isMaintain = isMaintain;
	}

	List<Module> childModelList;

	public List<Module> getChildModelList() {
		return childModelList;
	}

	public void setChildModelList(List<Module> childModelList) {
		this.childModelList = childModelList;
	}
}