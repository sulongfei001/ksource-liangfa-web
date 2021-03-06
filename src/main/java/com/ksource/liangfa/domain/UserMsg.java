package com.ksource.liangfa.domain;

import java.util.Date;

import com.ksource.common.log.businesslog.ModelCode;
import com.ksource.common.log.businesslog.ModelDesc;
import com.ksource.common.log.businesslog.ModelName;

public class UserMsg {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_MSG.ID
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_MSG.FROM_
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    private String from;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_MSG.TO_
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    private String to;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_MSG.MSG_TITLE
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    private String msgTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_MSG.MSG_BODY
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    private String msgBody;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_MSG.DATA_TIME
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    private Date dataTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_MSG.REPLY_MSG_ID
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    private Integer replyMsgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_MSG.READ_STATE
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    private Integer readState;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_MSG.ID
     *
     * @return the value of USER_MSG.ID
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_MSG.ID
     *
     * @param id the value for USER_MSG.ID
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_MSG.FROM_
     *
     * @return the value of USER_MSG.FROM_
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public String getFrom() {
        return from;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_MSG.FROM_
     *
     * @param from the value for USER_MSG.FROM_
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_MSG.TO_
     *
     * @return the value of USER_MSG.TO_
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public String getTo() {
        return to;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_MSG.TO_
     *
     * @param to the value for USER_MSG.TO_
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_MSG.MSG_TITLE
     *
     * @return the value of USER_MSG.MSG_TITLE
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public String getMsgTitle() {
        return msgTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_MSG.MSG_TITLE
     *
     * @param msgTitle the value for USER_MSG.MSG_TITLE
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_MSG.MSG_BODY
     *
     * @return the value of USER_MSG.MSG_BODY
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public String getMsgBody() {
        return msgBody;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_MSG.MSG_BODY
     *
     * @param msgBody the value for USER_MSG.MSG_BODY
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_MSG.DATA_TIME
     *
     * @return the value of USER_MSG.DATA_TIME
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public Date getDataTime() {
        return dataTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_MSG.DATA_TIME
     *
     * @param dataTime the value for USER_MSG.DATA_TIME
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_MSG.REPLY_MSG_ID
     *
     * @return the value of USER_MSG.REPLY_MSG_ID
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public Integer getReplyMsgId() {
        return replyMsgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_MSG.REPLY_MSG_ID
     *
     * @param replyMsgId the value for USER_MSG.REPLY_MSG_ID
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public void setReplyMsgId(Integer replyMsgId) {
        this.replyMsgId = replyMsgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_MSG.READ_STATE
     *
     * @return the value of USER_MSG.READ_STATE
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public Integer getReadState() {
        return readState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_MSG.READ_STATE
     *
     * @param readState the value for USER_MSG.READ_STATE
     *
     * @mbggenerated Sat Jan 07 13:57:06 CST 2012
     */
    public void setReadState(Integer readState) {
        this.readState = readState;
    }
    
    /****************一下为自己添加字段************************/
//   发信人的信息 
    private String fromName ;

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	 private String toName ;

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}
	
	@ModelCode
	public  Integer getModelCode(){
		return id;
	}
	@ModelName
	public  String getModelName(){
		return msgTitle;
	}
	@ModelDesc
	public  String toString(){
		return id+"："+msgTitle;
	}
}