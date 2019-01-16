package com.ksource.liangfa.domain.cms;

/**
 * cms 附件
 * @author Administrator
 *
 */
public class CmsAttachment {
	
	/** 附件ID*/
	private Integer attachmentId;
	/** 对应文章ID*/
	private Integer contentId;
	/** 附件名称*/
	private String name;
	/** 附件路径*/
	private String path;
	
	public Integer getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
