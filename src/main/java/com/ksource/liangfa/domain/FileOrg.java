package com.ksource.liangfa.domain;

public class FileOrg {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FILE_ORG.FILE_ID
     *
     * @mbggenerated Sat Oct 08 17:46:06 CST 2011
     */
    private Integer fileId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FILE_ORG.ORG_ID
     *
     * @mbggenerated Sat Oct 08 17:46:06 CST 2011
     */
    private Integer orgId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FILE_ORG.FILE_ID
     *
     * @return the value of FILE_ORG.FILE_ID
     *
     * @mbggenerated Sat Oct 08 17:46:06 CST 2011
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FILE_ORG.FILE_ID
     *
     * @param fileId the value for FILE_ORG.FILE_ID
     *
     * @mbggenerated Sat Oct 08 17:46:06 CST 2011
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FILE_ORG.ORG_ID
     *
     * @return the value of FILE_ORG.ORG_ID
     *
     * @mbggenerated Sat Oct 08 17:46:06 CST 2011
     */
    public Integer getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FILE_ORG.ORG_ID
     *
     * @param orgId the value for FILE_ORG.ORG_ID
     *
     * @mbggenerated Sat Oct 08 17:46:06 CST 2011
     */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
    
    
    
    /**
     * 某一个资源文件对应的所有机构的IDs
     */
    private String orgs;

	public String getOrgs() {
		return orgs;
	}

	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}
}