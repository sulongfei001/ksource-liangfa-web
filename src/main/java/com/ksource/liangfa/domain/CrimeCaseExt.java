package com.ksource.liangfa.domain;

import java.util.List;


public class CrimeCaseExt extends CaseBasic{

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.CASE_ID
	 * @mbggenerated  Fri Sep 26 08:54:12 CST 2014
	 */
	private String caseId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.YISONG_FILE_NO
	 * @mbggenerated  Fri Sep 26 08:54:12 CST 2014
	 */
	private String yisongFileNo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.PROOF_FILE_ID
	 * @mbggenerated  Fri Sep 26 08:54:12 CST 2014
	 */
	private Long proofFileId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.CASE_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.CASE_ID
	 * @mbggenerated  Fri Sep 26 08:54:12 CST 2014
	 */
	public String getCaseId() {
		return caseId;
	}
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.CASE_ID
	 * @param caseId  the value for LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.CASE_ID
	 * @mbggenerated  Fri Sep 26 08:54:12 CST 2014
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.YISONG_FILE_NO
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.YISONG_FILE_NO
	 * @mbggenerated  Fri Sep 26 08:54:12 CST 2014
	 */
	public String getYisongFileNo() {
		return yisongFileNo;
	}
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.YISONG_FILE_NO
	 * @param yisongFileNo  the value for LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.YISONG_FILE_NO
	 * @mbggenerated  Fri Sep 26 08:54:12 CST 2014
	 */
	public void setYisongFileNo(String yisongFileNo) {
		this.yisongFileNo = yisongFileNo;
	}
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.PROOF_FILE_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.PROOF_FILE_ID
	 * @mbggenerated  Fri Sep 26 08:54:12 CST 2014
	 */
	public Long getProofFileId() {
		return proofFileId;
	}
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.PROOF_FILE_ID
	 * @param proofFileId  the value for LIANGFA_HUBEI_V2_XIAN.CRIME_CASE_EXT.PROOF_FILE_ID
	 * @mbggenerated  Fri Sep 26 08:54:12 CST 2014
	 */
	public void setProofFileId(Long proofFileId) {
		this.proofFileId = proofFileId;
	}

	/*=====================USER DEFINED=================================================*/
	private String caseAccuse;//案件罪名
	private List<AccuseInfo> accuseInfoList;

	public String getCaseAccuse() {
		return caseAccuse;
	}
	public void setCaseAccuse(String caseAccuse) {
		this.caseAccuse = caseAccuse;
	}

	public List<AccuseInfo> getAccuseInfoList() {
		return accuseInfoList;
	}

	public void setAccuseInfoList(List<AccuseInfo> accuseInfoList) {
		this.accuseInfoList = accuseInfoList;
	}
	
	
	
}