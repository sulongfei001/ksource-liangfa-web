package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

public class AccuseRule {

	protected Long id;
	/**
	 * 罪名id
	 */
	protected String accuseId;
	protected String accuseName;
	/**
	 * 行业
	 */

	protected String industryType;

	protected String industryName;
	/**
	 * 规则名
	 */
	protected String name;
	/**
	 * 规则String
	 */
	protected String ruleInfo;
	/**
	 * 录入人
	 */
	protected String inputUser;
	/**
	 * 录入时间
	 */
	protected Date inputTime;

	public List<String> accuseNameList;
	public List<AccuseInfo> accuseInfos;
	public String AccuseInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccuseId() {
		return accuseId;
	}

	public void setAccuseId(String accuseId) {
		this.accuseId = accuseId;
	}

	public List<String> getAccuseNameList() {
		return accuseNameList;
	}

	public void setAccuseNameList(List<String> accuseNameList) {
		this.accuseNameList = accuseNameList;
	}

	public List<AccuseInfo> getAccuseInfos() {
		return accuseInfos;
	}

	public void setAccuseInfos(List<AccuseInfo> accuseInfos) {
		this.accuseInfos = accuseInfos;
	}

	public String getAccuseInfo() {
		return AccuseInfo;
	}

	public void setAccuseInfo(String accuseInfo) {
		AccuseInfo = accuseInfo;
	}

	public String getRuleInfo() {
		return ruleInfo;
	}

	public String getAccuseName() {
		return accuseName;
	}

	public void setAccuseName(String accuseName) {
		this.accuseName = accuseName;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getInputUser() {
		return inputUser;
	}

	public void setInputUser(String inputUser) {
		this.inputUser = inputUser;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public void setRuleInfo(String ruleInfo) {
		this.ruleInfo = ruleInfo;
	}

	public String getRuleInfo(String ruleInfo) {
		return ruleInfo;
	}

	@SuppressWarnings("unchecked")
	public List<AccuseRuleInfo> getRuleInfos() {
		List<AccuseRuleInfo> list = new ArrayList<AccuseRuleInfo>();
		if (StringUtils.isNotEmpty(this.ruleInfo)) {
			JSONArray jsonArray = JSONArray.fromObject(this.ruleInfo);
			list = (List<AccuseRuleInfo>) JSONArray.toCollection(jsonArray, AccuseRuleInfo.class);
		}
		return list;
	}

}
