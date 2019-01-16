package com.ksource.liangfa.domain.stat;


/**
 * 数量统计信息类
 *
 * @date 2016-4-25
 */
public class StatisData {
	/**行政区划级别*/
	private int regionLevel;
	/**组织机构类型*/
	private int orgType;
	/**组织机构数量*/
	private Long orgNum;
	
	/**接入行政机关总数*/
	private Long xingZhengNum;
	/**省级接入行政机关总数*/
	private Long proviceXingZhengNum;
	/**市级接入行政机关总数*/
	private Long cityXingZhengNum;
	/**县级接入行政机关总数*/
	private Long countyXingZhengNum;
	/**接入公安机关总数*/
	private Long policeNum;
	/**省级接入公安机关总数*/
	private Long provicePoliceNum;
	/**市级接入公安机关总数*/
	private Long cityPoliceNum;
	/**县级接入公安机关总数*/
	private Long countyPoliceNum;
	/**接入检察机关总数*/
	private Long jianChaNum;
	/**省级接入检察机关总数*/
	private Long proviceJianChaNum;
	/**市级接入检察机关总数*/
	private Long cityJianChaNum;
	/**县级接入检察机关总数*/
	private Long countyJianChaNum;	

	/**接入法院总数*/
	private Long judgeNum;
	/**省级接入法院总数*/
	private Long proviceJudgeNum;
	/**市级接入法院总数*/
	private Long cityJudgeNum;
	/**县级接入法院总数*/
	private Long countyJudgeNum;	
	
	/**接入法制办总数*/
	private Long fazhibanNum;
	/**省级接入法制办总数*/
	private Long proviceFazhibanNum;
	/**市级接入法制办总数*/
	private Long cityFazhibanNum;
	/**县级接入法制办总数*/
	private Long countyFazhibanNum;	
	
	
	/**接入监察局总数*/
	private Long jianchajuNum;
	/**省级接入监察局总数*/
	private Long proviceJianchajuNum;
	/**市级接入监察局总数*/
	private Long cityJianchajuNum;
	/**县级接入监察局总数*/
	private Long countyJianchajuNum;
	
	
	/**案件总数*/
	private Long totalNum;
	/**涉案金额总数*/
	private Double amountInvolved;
	/**行政处罚案件总数*/
	private Long penaltyNum;
	/**涉嫌犯罪案件总数*/
	private Long crimeNum;
	/**建议移送案件总数*/
	private Long suggestYiSongNum;
	/**直接移送案件总数*/
	private Long directYiSongNum;
	/**公安立案案件总数*/
	private String lianNum;
	/**检察院监督立案案件总数*/
	private String jianduLianNum;
	/**批准逮捕案件总数*/
	private Long daibuNum;
	/**提起公诉案件总数*/
	private Long gongsuNum;
	/**判决总数*/
	private Long panjueNum;	
	/**逮捕嫌疑人总数*/
	private Long daibuPersonNum;
	/**提起公诉嫌疑人总数*/
	private Long gongsuPersonNum;
	/**待办案件数量*/
	private Integer toDoNum;
	/**疑似涉嫌犯罪待办案件数量*/
	private Integer crimeToDoNum;
	/**立案监督线索筛查待办案件数量*/
	private Integer filterToDoNum;
	/**判决嫌疑人总数*/
	private Long panjuePersonNum;
	/**提请逮捕案件数*/
	private Long tiqingdaibuNum;
	/**移送起诉案件数*/
	private Long tiqingqisuNum;
	/**提请逮捕嫌疑人数*/
	private Long tiqingdaibuPersonNum;
	/**移送起诉嫌疑人数*/
	private Long tiqingqisuPersonNum; 
	/**检察院移送纪委案件数量*/
	private Integer caseYisongNum;
	
	
	private Long proviceChufaNum;
	private Long cityChufaNum;
	private Long countyChufaNum;
	private String proviceRate;
	private String cityRate;
	private String countyRate;
	/**行政立案*/
	private Long xingzhenglianNum;
	
	/**市长热线*/
	/**违法建筑问题数量*/
	private Integer weifaJianzhuNum;
	/**违法建筑百分比*/
	private String weifajianzhuPre;
	
	/**交通两难问题数量*/
	private Integer jiaotongLiangnanNum;
	private String jiaotongLiangnanPre;
	/**拖欠工资问题数量*/
	private Integer tuoqianGongziNum;
	private String tuoqianGongziPre;
	/**社区管理问题数量*/
	private Integer shequGuanliNum;
	private String shequGuanliPre;
	/**市容市貌问题数量*/
	private Integer shirongShimaoNum;
	private String shirongShimaoPre;
	/**安全隐患问题数量*/
	private Integer anquanYinhuanNum;
	private String anquanYinhuanPre;
	/**环境保护问题数量*/
	private Integer huanjingBaohuNum;
	private String huanjingBaohuPre;
	/**市长热线案件总数*/
	private Integer hotlineTotalNum;
	
	/**年度、季度、月份案件总数 */
	/**市级案件总数*/
	private Long cityTotalNum;
	/**县区级案件总数*/
	private Long countryTotalNum;
	/**本年度案件总数*/
	private Long yearTotalNum;
	/**本年度市级案件总数*/
	private Long yearCityTotalNum;
	/**本年度县区级案件总数*/
	private Long yearCountryTotalNum;
	/**本季度案件总数*/
	private Long quarterTotalNum;
	/**本季度市级案件总数*/
	private Long quarterCityTotalNum;
	/**本季度县区级案件总数*/
	private Long quarterCountryTotalNum;
	/**本月份案件总数*/
	private Long monthTotalNum;
	/**本月份市级案件总数*/
	private Long monthCityTotalNum;
	/**本月份县区级案件总数*/
	private Long monthCountryTotalNum;
	
	/**公安机关行政拘留待办案件数量*/
	private Integer transferDetentionTodoNum;
	/**公安机关立案监督待办案件数量*/
	private Integer lianSupTodoNum;
    public int getRegionLevel() {
        return regionLevel;
    }
    public void setRegionLevel(int regionLevel) {
        this.regionLevel = regionLevel;
    }
    public int getOrgType() {
        return orgType;
    }
    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }
    public Long getOrgNum() {
        return orgNum;
    }
    public void setOrgNum(Long orgNum) {
        this.orgNum = orgNum;
    }
    public Long getXingZhengNum() {
        return xingZhengNum;
    }
    public void setXingZhengNum(Long xingZhengNum) {
        this.xingZhengNum = xingZhengNum;
    }
    public Long getProviceXingZhengNum() {
        return proviceXingZhengNum;
    }
    public void setProviceXingZhengNum(Long proviceXingZhengNum) {
        this.proviceXingZhengNum = proviceXingZhengNum;
    }
    public Long getCityXingZhengNum() {
        return cityXingZhengNum;
    }
    public void setCityXingZhengNum(Long cityXingZhengNum) {
        this.cityXingZhengNum = cityXingZhengNum;
    }
    public Long getCountyXingZhengNum() {
        return countyXingZhengNum;
    }
    public void setCountyXingZhengNum(Long countyXingZhengNum) {
        this.countyXingZhengNum = countyXingZhengNum;
    }
    public Long getPoliceNum() {
        return policeNum;
    }
    public void setPoliceNum(Long policeNum) {
        this.policeNum = policeNum;
    }
    public Long getProvicePoliceNum() {
        return provicePoliceNum;
    }
    public void setProvicePoliceNum(Long provicePoliceNum) {
        this.provicePoliceNum = provicePoliceNum;
    }
    public Long getCityPoliceNum() {
        return cityPoliceNum;
    }
    public void setCityPoliceNum(Long cityPoliceNum) {
        this.cityPoliceNum = cityPoliceNum;
    }
    public Long getCountyPoliceNum() {
        return countyPoliceNum;
    }
    public void setCountyPoliceNum(Long countyPoliceNum) {
        this.countyPoliceNum = countyPoliceNum;
    }
    public Long getJianChaNum() {
        return jianChaNum;
    }
    public void setJianChaNum(Long jianChaNum) {
        this.jianChaNum = jianChaNum;
    }
    public Long getProviceJianChaNum() {
        return proviceJianChaNum;
    }
    public void setProviceJianChaNum(Long proviceJianChaNum) {
        this.proviceJianChaNum = proviceJianChaNum;
    }
    public Long getCityJianChaNum() {
        return cityJianChaNum;
    }
    public void setCityJianChaNum(Long cityJianChaNum) {
        this.cityJianChaNum = cityJianChaNum;
    }
    public Long getCountyJianChaNum() {
        return countyJianChaNum;
    }
    public void setCountyJianChaNum(Long countyJianChaNum) {
        this.countyJianChaNum = countyJianChaNum;
    }
    public Long getJudgeNum() {
        return judgeNum;
    }
    public void setJudgeNum(Long judgeNum) {
        this.judgeNum = judgeNum;
    }
    public Long getProviceJudgeNum() {
        return proviceJudgeNum;
    }
    public void setProviceJudgeNum(Long proviceJudgeNum) {
        this.proviceJudgeNum = proviceJudgeNum;
    }
    public Long getCityJudgeNum() {
        return cityJudgeNum;
    }
    public void setCityJudgeNum(Long cityJudgeNum) {
        this.cityJudgeNum = cityJudgeNum;
    }
    public Long getCountyJudgeNum() {
        return countyJudgeNum;
    }
    public void setCountyJudgeNum(Long countyJudgeNum) {
        this.countyJudgeNum = countyJudgeNum;
    }
    public Long getFazhibanNum() {
        return fazhibanNum;
    }
    public void setFazhibanNum(Long fazhibanNum) {
        this.fazhibanNum = fazhibanNum;
    }
    public Long getProviceFazhibanNum() {
        return proviceFazhibanNum;
    }
    public void setProviceFazhibanNum(Long proviceFazhibanNum) {
        this.proviceFazhibanNum = proviceFazhibanNum;
    }
    public Long getCityFazhibanNum() {
        return cityFazhibanNum;
    }
    public void setCityFazhibanNum(Long cityFazhibanNum) {
        this.cityFazhibanNum = cityFazhibanNum;
    }
    public Long getCountyFazhibanNum() {
        return countyFazhibanNum;
    }
    public void setCountyFazhibanNum(Long countyFazhibanNum) {
        this.countyFazhibanNum = countyFazhibanNum;
    }
    public Long getJianchajuNum() {
        return jianchajuNum;
    }
    public void setJianchajuNum(Long jianchajuNum) {
        this.jianchajuNum = jianchajuNum;
    }
    public Long getProviceJianchajuNum() {
        return proviceJianchajuNum;
    }
    public void setProviceJianchajuNum(Long proviceJianchajuNum) {
        this.proviceJianchajuNum = proviceJianchajuNum;
    }
    public Long getCityJianchajuNum() {
        return cityJianchajuNum;
    }
    public void setCityJianchajuNum(Long cityJianchajuNum) {
        this.cityJianchajuNum = cityJianchajuNum;
    }
    public Long getCountyJianchajuNum() {
        return countyJianchajuNum;
    }
    public void setCountyJianchajuNum(Long countyJianchajuNum) {
        this.countyJianchajuNum = countyJianchajuNum;
    }
    public Long getTotalNum() {
        return totalNum;
    }
    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }
    public Double getAmountInvolved() {
        return amountInvolved;
    }
    public void setAmountInvolved(Double amountInvolved) {
        this.amountInvolved = amountInvolved;
    }
    public Long getPenaltyNum() {
        return penaltyNum;
    }
    public void setPenaltyNum(Long penaltyNum) {
        this.penaltyNum = penaltyNum;
    }
    public Long getCrimeNum() {
        return crimeNum;
    }
    public void setCrimeNum(Long crimeNum) {
        this.crimeNum = crimeNum;
    }
    public Long getSuggestYiSongNum() {
        return suggestYiSongNum;
    }
    public void setSuggestYiSongNum(Long suggestYiSongNum) {
        this.suggestYiSongNum = suggestYiSongNum;
    }
    public Long getDirectYiSongNum() {
        return directYiSongNum;
    }
    public void setDirectYiSongNum(Long directYiSongNum) {
        this.directYiSongNum = directYiSongNum;
    }
    public String getLianNum() {
        return lianNum;
    }
    public void setLianNum(String lianNum) {
        this.lianNum = lianNum;
    }
    public String getJianduLianNum() {
        return jianduLianNum;
    }
    public void setJianduLianNum(String jianduLianNum) {
        this.jianduLianNum = jianduLianNum;
    }
    public Long getDaibuNum() {
        return daibuNum;
    }
    public void setDaibuNum(Long daibuNum) {
        this.daibuNum = daibuNum;
    }
    public Long getGongsuNum() {
        return gongsuNum;
    }
    public void setGongsuNum(Long gongsuNum) {
        this.gongsuNum = gongsuNum;
    }
    public Long getPanjueNum() {
        return panjueNum;
    }
    public void setPanjueNum(Long panjueNum) {
        this.panjueNum = panjueNum;
    }
    public Long getDaibuPersonNum() {
        return daibuPersonNum;
    }
    public void setDaibuPersonNum(Long daibuPersonNum) {
        this.daibuPersonNum = daibuPersonNum;
    }
    public Long getGongsuPersonNum() {
        return gongsuPersonNum;
    }
    public void setGongsuPersonNum(Long gongsuPersonNum) {
        this.gongsuPersonNum = gongsuPersonNum;
    }
    public Integer getToDoNum() {
        return toDoNum;
    }
    public void setToDoNum(Integer toDoNum) {
        this.toDoNum = toDoNum;
    }
    public Integer getCrimeToDoNum() {
        return crimeToDoNum;
    }
    public void setCrimeToDoNum(Integer crimeToDoNum) {
        this.crimeToDoNum = crimeToDoNum;
    }
    public Integer getFilterToDoNum() {
        return filterToDoNum;
    }
    public void setFilterToDoNum(Integer filterToDoNum) {
        this.filterToDoNum = filterToDoNum;
    }
    public Long getPanjuePersonNum() {
        return panjuePersonNum;
    }
    public void setPanjuePersonNum(Long panjuePersonNum) {
        this.panjuePersonNum = panjuePersonNum;
    }
    public Long getTiqingdaibuNum() {
        return tiqingdaibuNum;
    }
    public void setTiqingdaibuNum(Long tiqingdaibuNum) {
        this.tiqingdaibuNum = tiqingdaibuNum;
    }
    public Long getTiqingqisuNum() {
        return tiqingqisuNum;
    }
    public void setTiqingqisuNum(Long tiqingqisuNum) {
        this.tiqingqisuNum = tiqingqisuNum;
    }
    public Long getTiqingdaibuPersonNum() {
        return tiqingdaibuPersonNum;
    }
    public void setTiqingdaibuPersonNum(Long tiqingdaibuPersonNum) {
        this.tiqingdaibuPersonNum = tiqingdaibuPersonNum;
    }
    public Long getTiqingqisuPersonNum() {
        return tiqingqisuPersonNum;
    }
    public void setTiqingqisuPersonNum(Long tiqingqisuPersonNum) {
        this.tiqingqisuPersonNum = tiqingqisuPersonNum;
    }
    public Integer getCaseYisongNum() {
        return caseYisongNum;
    }
    public void setCaseYisongNum(Integer caseYisongNum) {
        this.caseYisongNum = caseYisongNum;
    }
    public Long getProviceChufaNum() {
        return proviceChufaNum;
    }
    public void setProviceChufaNum(Long proviceChufaNum) {
        this.proviceChufaNum = proviceChufaNum;
    }
    public Long getCityChufaNum() {
        return cityChufaNum;
    }
    public void setCityChufaNum(Long cityChufaNum) {
        this.cityChufaNum = cityChufaNum;
    }
    public Long getCountyChufaNum() {
        return countyChufaNum;
    }
    public void setCountyChufaNum(Long countyChufaNum) {
        this.countyChufaNum = countyChufaNum;
    }
    public String getProviceRate() {
        return proviceRate;
    }
    public void setProviceRate(String proviceRate) {
        this.proviceRate = proviceRate;
    }
    public String getCityRate() {
        return cityRate;
    }
    public void setCityRate(String cityRate) {
        this.cityRate = cityRate;
    }
    public String getCountyRate() {
        return countyRate;
    }
    public void setCountyRate(String countyRate) {
        this.countyRate = countyRate;
    }
    public Long getXingzhenglianNum() {
        return xingzhenglianNum;
    }
    public void setXingzhenglianNum(Long xingzhenglianNum) {
        this.xingzhenglianNum = xingzhenglianNum;
    }
    public Integer getWeifaJianzhuNum() {
        return weifaJianzhuNum;
    }
    public void setWeifaJianzhuNum(Integer weifaJianzhuNum) {
        this.weifaJianzhuNum = weifaJianzhuNum;
    }
    public String getWeifajianzhuPre() {
        return weifajianzhuPre;
    }
    public void setWeifajianzhuPre(String weifajianzhuPre) {
        this.weifajianzhuPre = weifajianzhuPre;
    }
    public Integer getJiaotongLiangnanNum() {
        return jiaotongLiangnanNum;
    }
    public void setJiaotongLiangnanNum(Integer jiaotongLiangnanNum) {
        this.jiaotongLiangnanNum = jiaotongLiangnanNum;
    }
    public String getJiaotongLiangnanPre() {
        return jiaotongLiangnanPre;
    }
    public void setJiaotongLiangnanPre(String jiaotongLiangnanPre) {
        this.jiaotongLiangnanPre = jiaotongLiangnanPre;
    }
    public Integer getTuoqianGongziNum() {
        return tuoqianGongziNum;
    }
    public void setTuoqianGongziNum(Integer tuoqianGongziNum) {
        this.tuoqianGongziNum = tuoqianGongziNum;
    }
    public String getTuoqianGongziPre() {
        return tuoqianGongziPre;
    }
    public void setTuoqianGongziPre(String tuoqianGongziPre) {
        this.tuoqianGongziPre = tuoqianGongziPre;
    }
    public Integer getShequGuanliNum() {
        return shequGuanliNum;
    }
    public void setShequGuanliNum(Integer shequGuanliNum) {
        this.shequGuanliNum = shequGuanliNum;
    }
    public String getShequGuanliPre() {
        return shequGuanliPre;
    }
    public void setShequGuanliPre(String shequGuanliPre) {
        this.shequGuanliPre = shequGuanliPre;
    }
    public Integer getShirongShimaoNum() {
        return shirongShimaoNum;
    }
    public void setShirongShimaoNum(Integer shirongShimaoNum) {
        this.shirongShimaoNum = shirongShimaoNum;
    }
    public String getShirongShimaoPre() {
        return shirongShimaoPre;
    }
    public void setShirongShimaoPre(String shirongShimaoPre) {
        this.shirongShimaoPre = shirongShimaoPre;
    }
    public Integer getAnquanYinhuanNum() {
        return anquanYinhuanNum;
    }
    public void setAnquanYinhuanNum(Integer anquanYinhuanNum) {
        this.anquanYinhuanNum = anquanYinhuanNum;
    }
    public String getAnquanYinhuanPre() {
        return anquanYinhuanPre;
    }
    public void setAnquanYinhuanPre(String anquanYinhuanPre) {
        this.anquanYinhuanPre = anquanYinhuanPre;
    }
    public Integer getHuanjingBaohuNum() {
        return huanjingBaohuNum;
    }
    public void setHuanjingBaohuNum(Integer huanjingBaohuNum) {
        this.huanjingBaohuNum = huanjingBaohuNum;
    }
    public String getHuanjingBaohuPre() {
        return huanjingBaohuPre;
    }
    public void setHuanjingBaohuPre(String huanjingBaohuPre) {
        this.huanjingBaohuPre = huanjingBaohuPre;
    }
    public Integer getHotlineTotalNum() {
        return hotlineTotalNum;
    }
    public void setHotlineTotalNum(Integer hotlineTotalNum) {
        this.hotlineTotalNum = hotlineTotalNum;
    }
    public Long getCityTotalNum() {
        return cityTotalNum;
    }
    public void setCityTotalNum(Long cityTotalNum) {
        this.cityTotalNum = cityTotalNum;
    }
    public Long getCountryTotalNum() {
        return countryTotalNum;
    }
    public void setCountryTotalNum(Long countryTotalNum) {
        this.countryTotalNum = countryTotalNum;
    }
    public Long getYearTotalNum() {
        return yearTotalNum;
    }
    public void setYearTotalNum(Long yearTotalNum) {
        this.yearTotalNum = yearTotalNum;
    }
    public Long getYearCityTotalNum() {
        return yearCityTotalNum;
    }
    public void setYearCityTotalNum(Long yearCityTotalNum) {
        this.yearCityTotalNum = yearCityTotalNum;
    }
    public Long getYearCountryTotalNum() {
        return yearCountryTotalNum;
    }
    public void setYearCountryTotalNum(Long yearCountryTotalNum) {
        this.yearCountryTotalNum = yearCountryTotalNum;
    }
    public Long getQuarterTotalNum() {
        return quarterTotalNum;
    }
    public void setQuarterTotalNum(Long quarterTotalNum) {
        this.quarterTotalNum = quarterTotalNum;
    }
    public Long getQuarterCityTotalNum() {
        return quarterCityTotalNum;
    }
    public void setQuarterCityTotalNum(Long quarterCityTotalNum) {
        this.quarterCityTotalNum = quarterCityTotalNum;
    }
    public Long getQuarterCountryTotalNum() {
        return quarterCountryTotalNum;
    }
    public void setQuarterCountryTotalNum(Long quarterCountryTotalNum) {
        this.quarterCountryTotalNum = quarterCountryTotalNum;
    }
    public Long getMonthTotalNum() {
        return monthTotalNum;
    }
    public void setMonthTotalNum(Long monthTotalNum) {
        this.monthTotalNum = monthTotalNum;
    }
    public Long getMonthCityTotalNum() {
        return monthCityTotalNum;
    }
    public void setMonthCityTotalNum(Long monthCityTotalNum) {
        this.monthCityTotalNum = monthCityTotalNum;
    }
    public Long getMonthCountryTotalNum() {
        return monthCountryTotalNum;
    }
    public void setMonthCountryTotalNum(Long monthCountryTotalNum) {
        this.monthCountryTotalNum = monthCountryTotalNum;
    }
	
	public Integer getTransferDetentionTodoNum() {
		return transferDetentionTodoNum;
	}
	public void setTransferDetentionTodoNum(Integer transferDetentionTodoNum) {
		this.transferDetentionTodoNum = transferDetentionTodoNum;
	}
	public Integer getLianSupTodoNum() {
		return lianSupTodoNum;
	}
	public void setLianSupTodoNum(Integer lianSupTodoNum) {
		this.lianSupTodoNum = lianSupTodoNum;
	}
	
	
}
