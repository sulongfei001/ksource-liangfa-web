package com.ksource.common.poi.style;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * 垂直对齐方式
 * @author luxiaopan
 *
 */
public enum VAlign {
	/**
	 * 
	 */
	TOP(HSSFCellStyle.VERTICAL_TOP), 
	
	/**
	 * 
	 */
	CENTER(HSSFCellStyle.VERTICAL_CENTER),
	
	/**
	 * 
	 */
	BOTTOM(HSSFCellStyle.VERTICAL_BOTTOM),
	
	/**
	 * 
	 */
	JUSTIFY(HSSFCellStyle.VERTICAL_JUSTIFY);
	
	private short alignment;

	private VAlign(short alignment){
		this.alignment = alignment;
	}

	public short getAlignment() {
		return alignment;
	}
}