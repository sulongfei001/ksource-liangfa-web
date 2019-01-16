package com.ksource.liangfa.model;

import java.math.BigDecimal;

public class ChartBean {
	private Integer totalNum; //案件总数
	private Integer daishenheNum;//待审核案件数量
	private Integer buchufaNum;//不处罚案件数量
	private Integer chufaNum;	//处罚案件数量
	private Integer daibuNum;	//逮捕案件数量
	private Integer directyisongNum;//直接移送案件数量
	private Integer jieanNum;	//已结案案件数量
	private Integer lianNum;		//立案数量
	private Integer gonganfenpaiNum;//公安分派数量
	private Integer panjueNum; //已判决数量
	private Integer qisuNum;     //已起诉数量
	private Integer suggestyisongNum; //建议移送案件数量
	private BigDecimal amountInvolved;	//涉案金额
	private String regionId; //行政区划ID
	private String regionName; //行政区划名称
	private Integer rLevel;	//行政区划级别 
	private Integer yearCode; //年度
	private String yearName;
	private Integer monthCode;//月度
	private String monthName;
	private String daibupartyNum; //逮捕当事人
	private String panjuepartyNum;//判决当事人
	private String xingzhengshouliNum;//判决当事人
	private String xingzhenglianNum;//判决当事人
	
	private BigDecimal lianlv;  //立案率
	private Integer yisongNum;//移送案件数量
	
	private String Xval;//x轴信息，通用性
	private String Yval;//y轴信息，通用性
	
	private String orgCode;//组织机构编码
	private String orgName;//组织机构名称
	private Integer bulianNum;//不立案数量
	
	private String caseRate;//比率
	private String yearRate;
	private String monthRate;
	private String lastYearVal;//同比上一年的数据
	private String lastMonthVal;//环比上一月的数据
	
	private String industryId;//行业id
	private String industryName;//行业名称
	
	private Integer currYearNum;//当年数量
	private Integer currQuarterNum;//当季度数量
	private Integer currMonthNum;//当月数据
	private Integer proviceTotalNum;//累计省直数量
	private Integer proviceCurrYearNum;//当年省直数量
	private Integer proviceCurrQuarterNum;//当季度省直数量
	private Integer proviceCurrMonthNum;//当月省直数据
	private Integer cityTotalNum;//累计地市数量
	private Integer cityCurrYearNum;//当年地市数量
	private Integer cityCurrQuarterNum;//当季度地市数量
	private Integer cityCurrMonthNum;//当月地市数据
	private Integer countyTotalNum;//累计县区数量
	private Integer countyCurrYearNum;//当年县区数量
	private Integer countyCurrQuarterNum;//当季度县区数量
	private Integer countyCurrMonthNum;//当月县区数据	
	
	private Integer jyysNum;//建议移送数量
	private Integer lajdNum;//立案监督数量
	
	private String yoy;
	private String proviceYoy;
	private String cityYoy;
	private String countyYoy;
	private String qoq;
	private String proviceQoq;
	private String cityQoq;
	private String countyQoq;
	
	public ChartBean() {
		super();
	}

	public ChartBean(Integer totalNum, Integer buchufaNum, Integer chufaNum,
			Integer daibuNum, Integer directyisongNum, Integer jieanNum,
			Integer lianNum,Integer gonganfenpaiNum, Integer panjueNum, Integer qisuNum,
			Integer suggestyisongNum, BigDecimal amountInvolved,
			String regionId, String regionName, Integer rLevel,
			Integer yearCode, String yearName, Integer monthCode,
			String monthName, String daibupartyNum, String panjuepartyNum,
			BigDecimal lianlv, Integer yisongNum, String xval, String yval,
			String orgCode, String orgName) {
		super();
		this.totalNum = totalNum;
		this.buchufaNum = buchufaNum;
		this.chufaNum = chufaNum;
		this.daibuNum = daibuNum;
		this.directyisongNum = directyisongNum;
		this.jieanNum = jieanNum;
		this.lianNum = lianNum;
		this.gonganfenpaiNum = gonganfenpaiNum;
		this.panjueNum = panjueNum;
		this.qisuNum = qisuNum;
		this.suggestyisongNum = suggestyisongNum;
		this.amountInvolved = amountInvolved;
		this.regionId = regionId;
		this.regionName = regionName;
		this.rLevel = rLevel;
		this.yearCode = yearCode;
		this.yearName = yearName;
		this.monthCode = monthCode;
		this.monthName = monthName;
		this.daibupartyNum = daibupartyNum;
		this.panjuepartyNum = panjuepartyNum;
		this.lianlv = lianlv;
		this.yisongNum = yisongNum;
		Xval = xval;
		Yval = yval;
		this.orgCode = orgCode;
		this.orgName = orgName;
	}


	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getBuchufaNum() {
		return buchufaNum;
	}

	public void setBuchufaNum(Integer buchufaNum) {
		this.buchufaNum = buchufaNum;
	}

	public Integer getChufaNum() {
		return chufaNum;
	}

	public void setChufaNum(Integer chufaNum) {
		this.chufaNum = chufaNum;
	}

	public Integer getDaibuNum() {
		return daibuNum;
	}

	public void setDaibuNum(Integer daibuNum) {
		this.daibuNum = daibuNum;
	}

	public Integer getDirectyisongNum() {
		return directyisongNum;
	}

	public void setDirectyisongNum(Integer directyisongNum) {
		this.directyisongNum = directyisongNum;
	}

	public Integer getJieanNum() {
		return jieanNum;
	}

	public void setJieanNum(Integer jieanNum) {
		this.jieanNum = jieanNum;
	}

	public Integer getLianNum() {
		return lianNum;
	}

	public void setLianNum(Integer lianNum) {
		this.lianNum = lianNum;
	}

	public Integer getGonganfenpaiNum() {
		return gonganfenpaiNum;
	}

	public void setGonganfenpaiNum(Integer gonganfenpaiNum) {
		this.gonganfenpaiNum = gonganfenpaiNum;
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

	public Integer getSuggestyisongNum() {
		return suggestyisongNum;
	}

	public void setSuggestyisongNum(Integer suggestyisongNum) {
		this.suggestyisongNum = suggestyisongNum;
	}

	public BigDecimal getAmountInvolved() {
		return amountInvolved;
	}

	public void setAmountInvolved(BigDecimal amountInvolved) {
		this.amountInvolved = amountInvolved;
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

	public Integer getYearCode() {
		return yearCode;
	}

	public void setYearCode(Integer yearCode) {
		this.yearCode = yearCode;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public Integer getMonthCode() {
		return monthCode;
	}

	public void setMonthCode(Integer monthCode) {
		this.monthCode = monthCode;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getDaibupartyNum() {
		return daibupartyNum;
	}

	public void setDaibupartyNum(String daibupartyNum) {
		this.daibupartyNum = daibupartyNum;
	}

	public String getPanjuepartyNum() {
		return panjuepartyNum;
	}

	public void setPanjuepartyNum(String panjuepartyNum) {
		this.panjuepartyNum = panjuepartyNum;
	}

	public Integer getYisongNum() {
		return yisongNum;
	}

	public void setYisongNum(Integer yisongNum) {
		this.yisongNum = yisongNum;
	}

	public BigDecimal getLianlv() {
		return lianlv;
	}

	public void setLianlv(BigDecimal lianlv) {
		this.lianlv = lianlv;
	}

	public Integer getrLevel() {
		return rLevel;
	}

	public void setrLevel(Integer rLevel) {
		this.rLevel = rLevel;
	}

	public String getXval() {
		return Xval;
	}

	public void setXval(String xval) {
		Xval = xval;
	}

	public String getYval() {
		return Yval;
	}

	public void setYval(String yval) {
		Yval = yval;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getDaishenheNum() {
		return daishenheNum;
	}

	public void setDaishenheNum(Integer daishenheNum) {
		this.daishenheNum = daishenheNum;
	}

	public Integer getBulianNum() {
		return bulianNum;
	}

	public void setBulianNum(Integer bulianNum) {
		this.bulianNum = bulianNum;
	}

	public String getCaseRate() {
		return caseRate;
	}

	public void setCaseRate(String caseRate) {
		this.caseRate = caseRate;
	}

	public String getYearRate() {
		return yearRate;
	}

	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}

	public String getMonthRate() {
		return monthRate;
	}

	public void setMonthRate(String monthRate) {
		this.monthRate = monthRate;
	}

	public String getLastYearVal() {
		return lastYearVal;
	}

	public void setLastYearVal(String lastYearVal) {
		this.lastYearVal = lastYearVal;
	}

	public String getLastMonthVal() {
		return lastMonthVal;
	}

	public void setLastMonthVal(String lastMonthVal) {
		this.lastMonthVal = lastMonthVal;
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

	public Integer getCurrYearNum() {
		return currYearNum;
	}

	public void setCurrYearNum(Integer currYearNum) {
		this.currYearNum = currYearNum;
	}

	public Integer getCurrQuarterNum() {
		return currQuarterNum;
	}

	public void setCurrQuarterNum(Integer currQuarterNum) {
		this.currQuarterNum = currQuarterNum;
	}

	public Integer getCurrMonthNum() {
		return currMonthNum;
	}

	public void setCurrMonthNum(Integer currMonthNum) {
		this.currMonthNum = currMonthNum;
	}

	public Integer getJyysNum() {
		return jyysNum;
	}

	public void setJyysNum(Integer jyysNum) {
		this.jyysNum = jyysNum;
	}

	public Integer getLajdNum() {
		return lajdNum;
	}

	public void setLajdNum(Integer lajdNum) {
		this.lajdNum = lajdNum;
	}
	public Integer getProviceTotalNum() {
		return proviceTotalNum;
	}

	public void setProviceTotalNum(Integer proviceTotalNum) {
		this.proviceTotalNum = proviceTotalNum;
	}

	public Integer getProviceCurrYearNum() {
		return proviceCurrYearNum;
	}

	public void setProviceCurrYearNum(Integer proviceCurrYearNum) {
		this.proviceCurrYearNum = proviceCurrYearNum;
	}

	public Integer getProviceCurrQuarterNum() {
		return proviceCurrQuarterNum;
	}

	public void setProviceCurrQuarterNum(Integer proviceCurrQuarterNum) {
		this.proviceCurrQuarterNum = proviceCurrQuarterNum;
	}

	public Integer getProviceCurrMonthNum() {
		return proviceCurrMonthNum;
	}

	public void setProviceCurrMonthNum(Integer proviceCurrMonthNum) {
		this.proviceCurrMonthNum = proviceCurrMonthNum;
	}

	public Integer getCityTotalNum() {
		return cityTotalNum;
	}

	public void setCityTotalNum(Integer cityTotalNum) {
		this.cityTotalNum = cityTotalNum;
	}

	public Integer getCityCurrYearNum() {
		return cityCurrYearNum;
	}

	public void setCityCurrYearNum(Integer cityCurrYearNum) {
		this.cityCurrYearNum = cityCurrYearNum;
	}

	public Integer getCityCurrQuarterNum() {
		return cityCurrQuarterNum;
	}

	public void setCityCurrQuarterNum(Integer cityCurrQuarterNum) {
		this.cityCurrQuarterNum = cityCurrQuarterNum;
	}

	public Integer getCityCurrMonthNum() {
		return cityCurrMonthNum;
	}

	public void setCityCurrMonthNum(Integer cityCurrMonthNum) {
		this.cityCurrMonthNum = cityCurrMonthNum;
	}

	public Integer getCountyTotalNum() {
		return countyTotalNum;
	}

	public void setCountyTotalNum(Integer countyTotalNum) {
		this.countyTotalNum = countyTotalNum;
	}

	public Integer getCountyCurrYearNum() {
		return countyCurrYearNum;
	}

	public void setCountyCurrYearNum(Integer countyCurrYearNum) {
		this.countyCurrYearNum = countyCurrYearNum;
	}

	public Integer getCountyCurrQuarterNum() {
		return countyCurrQuarterNum;
	}

	public void setCountyCurrQuarterNum(Integer countyCurrQuarterNum) {
		this.countyCurrQuarterNum = countyCurrQuarterNum;
	}

	public Integer getCountyCurrMonthNum() {
		return countyCurrMonthNum;
	}

	public void setCountyCurrMonthNum(Integer countyCurrMonthNum) {
		this.countyCurrMonthNum = countyCurrMonthNum;
	}

	public String getYoy() {
		return yoy;
	}

	public void setYoy(String yoy) {
		this.yoy = yoy;
	}

	public String getProviceYoy() {
		return proviceYoy;
	}

	public void setProviceYoy(String proviceYoy) {
		this.proviceYoy = proviceYoy;
	}

	public String getCityYoy() {
		return cityYoy;
	}

	public void setCityYoy(String cityYoy) {
		this.cityYoy = cityYoy;
	}

	public String getCountyYoy() {
		return countyYoy;
	}

	public void setCountyYoy(String countyYoy) {
		this.countyYoy = countyYoy;
	}

	public String getQoq() {
		return qoq;
	}

	public void setQoq(String qoq) {
		this.qoq = qoq;
	}

	public String getProviceQoq() {
		return proviceQoq;
	}

	public void setProviceQoq(String proviceQoq) {
		this.proviceQoq = proviceQoq;
	}

	public String getCityQoq() {
		return cityQoq;
	}

	public void setCityQoq(String cityQoq) {
		this.cityQoq = cityQoq;
	}

	public String getCountyQoq() {
		return countyQoq;
	}

	public void setCountyQoq(String countyQoq) {
		this.countyQoq = countyQoq;
	}

	public String getXingzhengshouliNum() {
		return xingzhengshouliNum;
	}

	public void setXingzhengshouliNum(String xingzhengshouliNum) {
		this.xingzhengshouliNum = xingzhengshouliNum;
	}

	public String getXingzhenglianNum() {
		return xingzhenglianNum;
	}

	public void setXingzhenglianNum(String xingzhenglianNum) {
		this.xingzhenglianNum = xingzhenglianNum;
	}
	
}
