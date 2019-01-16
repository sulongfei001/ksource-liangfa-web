package com.ksource.liangfa.domain;

import java.math.BigDecimal;

public class AccuseInfo {

	private Integer id;
	
	private String name;
	
	private String clause;
	
	private Integer infoOrder;
	
	private Integer accuseId1;
	
	private Integer accuseId2;
	
	private String law;

	private String illegalName;
	/**行业类型*/
	private String industryType;
	
	

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getClause() {
		return clause;
	}

	
	public void setClause(String clause) {
		this.clause = clause;
	}

	
	public Integer getInfoOrder() {
		return infoOrder;
	}

	
	public void setInfoOrder(Integer infoOrder) {
		this.infoOrder = infoOrder;
	}

	
	public Integer getAccuseId1() {
		return accuseId1;
	}

	
	public void setAccuseId1(Integer accuseId1) {
		this.accuseId1 = accuseId1;
	}

	
	public Integer getAccuseId2() {
		return accuseId2;
	}

	
	public void setAccuseId2(Integer accuseId2) {
		this.accuseId2 = accuseId2;
	}

	
	public String getLaw() {
		return law;
	}

	
	public void setLaw(String law) {
		this.law = law;
	}

	public String getIllegalName() {
		return illegalName;
	}

	public void setIllegalName(String illegalName) {
		this.illegalName = illegalName;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	
}