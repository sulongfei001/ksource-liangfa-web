package com.ksource.liangfa.domain;

public class ProcDeployWithBLOBs extends ProcDeploy {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PROC_DEPLOY.BPMN_FILE
	 * @mbggenerated  Sat Dec 31 11:15:30 CST 2011
	 */
	private byte[] bpmnFile;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PROC_DEPLOY.PICT_FILE
	 * @mbggenerated  Sat Dec 31 11:15:30 CST 2011
	 */
	private byte[] pictFile;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PROC_DEPLOY.BPMN_FILE
	 * @return  the value of PROC_DEPLOY.BPMN_FILE
	 * @mbggenerated  Sat Dec 31 11:15:30 CST 2011
	 */
	public byte[] getBpmnFile() {
		return bpmnFile;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PROC_DEPLOY.BPMN_FILE
	 * @param bpmnFile  the value for PROC_DEPLOY.BPMN_FILE
	 * @mbggenerated  Sat Dec 31 11:15:30 CST 2011
	 */
	public void setBpmnFile(byte[] bpmnFile) {
		this.bpmnFile = bpmnFile;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PROC_DEPLOY.PICT_FILE
	 * @return  the value of PROC_DEPLOY.PICT_FILE
	 * @mbggenerated  Sat Dec 31 11:15:30 CST 2011
	 */
	public byte[] getPictFile() {
		return pictFile;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PROC_DEPLOY.PICT_FILE
	 * @param pictFile  the value for PROC_DEPLOY.PICT_FILE
	 * @mbggenerated  Sat Dec 31 11:15:30 CST 2011
	 */
	public void setPictFile(byte[] pictFile) {
		this.pictFile = pictFile;
	}
}