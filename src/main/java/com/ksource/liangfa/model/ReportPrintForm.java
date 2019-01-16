package com.ksource.liangfa.model;

public class ReportPrintForm {

	//行政区划id
	private String regionId;
	
	//行政区划名称
	private String regionName;
	
	//开始时间
	private String startTime;
	
	//结束时间
	private String endTime;

	//录入人组织机构编码
	private String inputerOrgCode;
	//录入人组织机构名称
	private String inputerOrgName;
	//案件录入年份
	private String yearCode;
	
	private String platformId;
	//案件录入时间
	private String inputerTime;
	
	private String upPlatformId;
	
	//记录查询范围，是包含下级，还是只查本级。(1为包含下级，2为只查本级)，默认为1
	private String queryScope;
	
	private String districtId;
	//行业类型
	private String industryId;
	//行业名称
	private String industryName;
	//公安类型(1普通公安，2森林公安)
	private String policeType;
	
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getInputerOrgCode() {
		return inputerOrgCode;
	}

	public void setInputerOrgCode(String inputerOrgCode) {
		this.inputerOrgCode = inputerOrgCode;
	}

	public String getInputerOrgName() {
		return inputerOrgName;
	}

	public void setInputerOrgName(String inputerOrgName) {
		this.inputerOrgName = inputerOrgName;
	}

	public String getYearCode() {
		return yearCode;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getInputerTime() {
		return inputerTime;
	}

	public void setInputerTime(String inputerTime) {
		this.inputerTime = inputerTime;
	}

	public String getUpPlatformId() {
		return upPlatformId;
	}

	public void setUpPlatformId(String upPlatformId) {
		this.upPlatformId = upPlatformId;
	}

	public String getQueryScope() {
		return queryScope;
	}

	public void setQueryScope(String queryScope) {
		this.queryScope = queryScope;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getPoliceType() {
		return policeType;
	}

	public void setPoliceType(String policeType) {
		this.policeType = policeType;
	}

}
