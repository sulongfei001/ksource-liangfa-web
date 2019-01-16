package com.ksource.liangfa.domain.cms;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * cms文章
 * @author Administrator
 *
 */
public class CmsContent {
	
	/** 文章ID*/
	private Integer contentId;
	/** 栏目ID*/
	private Integer channelId;
	/** 栏目名称*/
	private String channelName;
	/** 栏目来源*/
	private Integer channelFrom;
	/** 创建人*/
	private String createUserId;
	/** 创建名称*/
	private String crateUserName;
	/** 创建人单位*/
	private Integer userOrgCode;
	/** 创建人行政区划*/
	private Integer userDistriceCode;
	/** 创建单位名称*/
	private String orgName;
	/** 创建单位*/
	private Integer orgCode;
	/** 标题*/
	private String title;
	/** 内容*/
	private String text;
	/** 创建时间*/
	private Date createTime;
	/** 查询条件*/
	private Date createStartTime;
	private Date createEndTime;
	/** 图片路径*/
	private String imagePath;
	/** 加粗*/
	private Integer isBold;
	/** 标题颜色*/
	private String titleColor;
	/** 状态*/
	private Integer status;
	/** 置顶*/
	private Integer top;
	/** 如果文章所以外部，外部ID*/
	private Integer outId;

	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Integer getIsBold() {
		return isBold;
	}
	public void setIsBold(Integer isBold) {
		this.isBold = isBold;
	}
	public String getTitleColor() {
		return titleColor;
	}
	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getCrateUserName() {
		return crateUserName;
	}
	public void setCrateUserName(String crateUserName) {
		this.crateUserName = crateUserName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Date getCreateStartTime() {
		return createStartTime;
	}
	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}
	public Date getCreateEndTime() {
		return createEndTime;
	}
	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}
	public Integer getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(Integer orgCode) {
		this.orgCode = orgCode;
	}
	public Integer getUserOrgCode() {
		return userOrgCode;
	}
	public void setUserOrgCode(Integer userOrgCode) {
		this.userOrgCode = userOrgCode;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getUserDistriceCode() {
		return userDistriceCode;
	}
	public void setUserDistriceCode(Integer userDistriceCode) {
		this.userDistriceCode = userDistriceCode;
	}
	
	public String toParam() throws UnsupportedEncodingException {
		StringBuffer param = new StringBuffer();
		DateFormat DATE_FORMAT = new SimpleDateFormat(
				"yyyy-MM-dd");
		
		if(channelId != null){
			param.append("&channelId="+ channelId);
		}
		if(channelName != null){
			param.append("&channelName=" + java.net.URLEncoder.encode(channelName,"utf-8"));
		}
		if(title != null){
			param.append("&title="+ java.net.URLEncoder.encode(title,"utf-8"));
		}
		if(createStartTime != null){
			param.append("&createStartTime="+ DATE_FORMAT.format(createStartTime));
		}
		if(createEndTime != null){
			param.append("&createEndTime="+ DATE_FORMAT.format(createEndTime));
		}
		if(status != null){
			param.append("&status="+ status);
		}
		
		return param.toString();
	}
	public Integer getOutId() {
		return outId;
	}
	public void setOutId(Integer outId) {
		this.outId = outId;
	}
	public Integer getChannelFrom() {
		return channelFrom;
	}
	public void setChannelFrom(Integer channelFrom) {
		this.channelFrom = channelFrom;
	}
	
}
