package com.ksource.liangfa.model;

import java.math.BigDecimal;
/**
 * 流程任务定义
 * @author czj
 *
 */
public class TaskDef {
	private String guid;
	private String taskDefId;  //任务定义ID
	private String platformId; //平台注册ID
	private String procDefId; //流程定义ID
	private String taskDefName;  //节点名称
	private BigDecimal height; //高度
	private BigDecimal width; //宽度
	private BigDecimal pointX; //x坐标
	private BigDecimal pointY; //y坐标
	private BigDecimal pointX2; //x坐标2
	private BigDecimal pointY2; //y坐标2

	public TaskDef() {
		super();
	}

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTaskDefId() {
		return taskDefId;
	}

	public void setTaskDefId(String taskDefId) {
		this.taskDefId = taskDefId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getTaskDefName() {
		return taskDefName;
	}

	public void setTaskDefName(String taskDefName) {
		this.taskDefName = taskDefName;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}



	public BigDecimal getPointX() {
		return pointX;
	}

	public void setPointX(BigDecimal pointX) {
		this.pointX = pointX;
	}

	public BigDecimal getPointY() {
		return pointY;
	}

	public void setPointY(BigDecimal pointY) {
		this.pointY = pointY;
	}

	public BigDecimal getPointX2() {
		return pointX2;
	}

	public void setPointX2(BigDecimal pointX2) {
		this.pointX2 = pointX2;
	}

	public BigDecimal getPointY2() {
		return pointY2;
	}

	public void setPointY2(BigDecimal pointY2) {
		this.pointY2 = pointY2;
	}

}
