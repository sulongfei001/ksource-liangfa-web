package com.ksource.liangfa.domain.cms;

import java.util.List;

/**
 * cms 栏目
 * @author Administrator
 *
 */
public class CmsChannel {
	
	/** 栏目ID*/
	private Integer channelId;
	/** 名称*/
	private String name;
	/** 排序*/
	private Integer sort;
	/** 父节点*/
	private Integer parentId;
	/** 叶子节点*/
	private Integer isLeaf;
	/** 路径*/
	private String path;
	/** 是否显示*/
	private Integer isShow;
	/** 备注*/
	private String remark;
	/** 栏目来源*/
	private Integer channelFrom;
	
	private List<CmsContent> contents;
	
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<CmsContent> getContents() {
		return contents;
	}
	public void setContents(List<CmsContent> contents) {
		this.contents = contents;
	}
	public Integer getChannelFrom() {
		return channelFrom;
	}
	public void setChannelFrom(Integer channelFrom) {
		this.channelFrom = channelFrom;
	}
	
}
