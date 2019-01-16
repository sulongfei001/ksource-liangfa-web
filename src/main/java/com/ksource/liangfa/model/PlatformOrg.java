/**
 *
 */
package com.ksource.liangfa.model;

/**
 * @author XT
 *         2013-1-31
 */
public class PlatformOrg {

    private String platformId;    //平台注册ID
    private Integer orgCode;    //组织机构代码
    private String guid;
    private String regionId;    //区划Id
    private String orgName;    //组织机构名称
    private String orgType;    //组织机构类型,行业统一编码标准
    private Integer upOrgCode;    //上级组织机构代码
    private String telephone;    //电话号码
    private String address;    //地址
    private String note;        //备注
    private String leader;        //负责人
    private Integer isDept;        //是否是部门
    private String sampleName;    //机构简称
    private String districtCode;
    private Boolean isParent;
    private String path;
    private String upPlatformId;
    private String industryId;     //行业id
    private String industryName;   //行业名称
    

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public Integer getDept() {
        return isDept;
    }

    public void setDept(Integer dept) {
        isDept = dept;
    }

    public Integer getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(Integer orgCode) {
        this.orgCode = orgCode;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Integer getUpOrgCode() {
        return upOrgCode;
    }

    public void setUpOrgCode(Integer upOrgCode) {
        this.upOrgCode = upOrgCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public Integer getIsDept() {
        return isDept;
    }

    public void setIsDept(Integer isDept) {
        this.isDept = isDept;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUpPlatformId() {
		return upPlatformId;
	}

	public void setUpPlatformId(String upPlatformId) {
		this.upPlatformId = upPlatformId;
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
