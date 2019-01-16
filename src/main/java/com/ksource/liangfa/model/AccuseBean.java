package com.ksource.liangfa.model;

public class AccuseBean {
	private String accuseNum;
	private String accuseID;
	private String accuseName;
	private String regionId;
	private String regionName;
	private String industryId;
	private String industryName;
	private String monthCode;
	private String monthName;
	private String yearCode;
	private String yearName;
	private Integer yisongNum;//移送案件数量
	private Integer lianNum;//立案数量
	private Integer panjueNum; //已判决数量
	private Integer qisuNum;     //已起诉数量
	private Integer daibuNum;	//逮捕案件数量
	/**
	 * 传入的钻取类型
	 */
	private String type;

	public String getAccuseNum() {
		return accuseNum;
	}

	public void setAccuseNum(String accuseNum) {
		this.accuseNum = accuseNum;
	}

	public String getAccuseID() {
		return accuseID;
	}

	public void setAccuseID(String accuseID) {
		this.accuseID = accuseID;
	}

	public String getAccuseName() {
		return accuseName;
	}

	public void setAccuseName(String accuseName) {
		this.accuseName = accuseName;
	}

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

	public String getMonthCode() {
		return monthCode;
	}

	public void setMonthCode(String monthCode) {
		this.monthCode = monthCode;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getYearCode() {
		return yearCode;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getYisongNum() {
		return yisongNum;
	}

	public void setYisongNum(Integer yisongNum) {
		this.yisongNum = yisongNum;
	}

	public Integer getLianNum() {
		return lianNum;
	}

	public void setLianNum(Integer lianNum) {
		this.lianNum = lianNum;
	}

	public Integer getPanjueNum() {
		return panjueNum;
	}

	public void setPanjueNum(Integer panjueNum) {
		this.panjueNum = panjueNum;
	}

	public Integer getQisuNum() {
		return qisuNum;
	}

	public void setQisuNum(Integer qisuNum) {
		this.qisuNum = qisuNum;
	}

	public Integer getDaibuNum() {
		return daibuNum;
	}

	public void setDaibuNum(Integer daibuNum) {
		this.daibuNum = daibuNum;
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

	

}
