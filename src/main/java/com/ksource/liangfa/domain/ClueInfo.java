package com.ksource.liangfa.domain;

import java.util.Date;
import java.util.List;

public class ClueInfo {
	
	
    public ClueInfo() {
		super();
	}

	public ClueInfo(Integer clueId) {
		super();
		this.clueId = clueId;
	}

	private Integer clueId;
    

    private String clueNo;
    
    private String orgPath;
    
    //线索来源
    private Integer clueResource;
    
    private String clueResourceName;
    
    //分发人
    private String  distributeUserId;
    
    //案发地点
    private String address;

    private Integer createOrg;

    private String createUser;

    private Date createTime;
    
    //对方读取状态
    private Integer isReceive;//0未读  1已读

    //创建人所属区域
    private String createDistrictcode;
    
    //案发时间
    private Date occurrenceTime;
    //查处时间
    private Date investigationTime;

    //线索状态
    private Integer clueState;
    
    //涉嫌金额
    private Long allegedAmount;

    //线索内容
    private String content;
    
    //经办人意见
    private String createUserOpinion;
    
    //========================================
    /**违法行为发生地*/
    private String addressName;
    /**线索创建人*/
    private String createUserName;
    /**线索创建单位*/
    private String createOrgName;
    /**模糊查询开始时间*/
    private Date startTime;
    /**模糊查询结束时间*/
    private Date endTime;
    /**登录用户的行政区划级别*/
    private Integer jb;
    /**创建机构所在行政区划id*/
    private String districtCode;
    /**检察院查询未分派时用到*/
    private Integer clueStateFlag;
    
    /** 案件当事人前台字符串 */
	private String casePartyJson;
	/** 案件当事单位前台字符串 */
	private String caseCompanyJson;
	/** 案件当事人前台集合 */
	private List<CaseParty> casePartyList;
	/** 案件当事单位前台集合 */
	private List<CaseCompany> caseCompanyList;
	
	public String getDistributeUserId() {
		return distributeUserId;
	}

	public void setDistributeUserId(String distributeUserId) {
		this.distributeUserId = distributeUserId;
	}

	public String getOrgPath() {
		return orgPath;
	}

	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}

	public String getCreateDistrictcode() {
		return createDistrictcode;
	}

	public void setCreateDistrictcode(String createDistrictcode) {
		this.createDistrictcode = createDistrictcode;
	}

	public Integer getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(Integer isReceive) {
		this.isReceive = isReceive;
	}

	//附加材料
	private List<PublishInfoFile> attments;
	

	public List<PublishInfoFile> getAttments() {
		return attments;
	}

	public void setAttments(List<PublishInfoFile> attments) {
		this.attments = attments;
	}

	public List<CaseParty> getCasePartyList() {
		return casePartyList;
	}

	public void setCasePartyList(List<CaseParty> casePartyList) {
		this.casePartyList = casePartyList;
	}

	public List<CaseCompany> getCaseCompanyList() {
		return caseCompanyList;
	}

	public void setCaseCompanyList(List<CaseCompany> caseCompanyList) {
		this.caseCompanyList = caseCompanyList;
	}

	public String getCreateUserOpinion() {
		return createUserOpinion;
	}

	public void setCreateUserOpinion(String createUserOpinion) {
		this.createUserOpinion = createUserOpinion;
	}

	public Long getAllegedAmount() {
		return allegedAmount;
	}

	public void setAllegedAmount(Long allegedAmount) {
		this.allegedAmount = allegedAmount;
	}

	public Integer getClueId() {
        return clueId;
    }

    public void setClueId(Integer clueId) {
        this.clueId = clueId;
    }

    public String getClueNo() {
        return clueNo;
    }

    public void setClueNo(String clueNo) {
        this.clueNo = clueNo;
    }

    public Integer getClueResource() {
        return clueResource;
    }

    public void setClueResource(Integer clueResource) {
        this.clueResource = clueResource;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCreateOrg() {
        return createOrg;
    }

    public void setCreateOrg(Integer createOrg) {
        this.createOrg = createOrg;
    }

    public String getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getClueState() {
        return clueState;
    }

    public void setClueState(Integer clueState) {
        this.clueState = clueState;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
  
	
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getJb() {
		return jb;
	}

	public void setJb(Integer jb) {
		this.jb = jb;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public Integer getClueStateFlag() {
		return clueStateFlag;
	}

	public void setClueStateFlag(Integer clueStateFlag) {
		this.clueStateFlag = clueStateFlag;
	}

	public Date getOccurrenceTime() {
		return occurrenceTime;
	}

	public void setOccurrenceTime(Date occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}

	public Date getInvestigationTime() {
		return investigationTime;
	}

	public void setInvestigationTime(Date investigationTime) {
		this.investigationTime = investigationTime;
	}

	public String getCasePartyJson() {
		return casePartyJson;
	}

	public void setCasePartyJson(String casePartyJson) {
		this.casePartyJson = casePartyJson;
	}

	public String getCaseCompanyJson() {
		return caseCompanyJson;
	}

	public void setCaseCompanyJson(String caseCompanyJson) {
		this.caseCompanyJson = caseCompanyJson;
	}

	public String getClueResourceName() {
		return clueResourceName;
	}

	public void setClueResourceName(String clueResourceName) {
		this.clueResourceName = clueResourceName;
	}

	private Integer hotlineId;

	public Integer getHotlineId() {
		return hotlineId;
	}

	public void setHotlineId(Integer hotlineId) {
		this.hotlineId = hotlineId;
	}


	

    
    
}