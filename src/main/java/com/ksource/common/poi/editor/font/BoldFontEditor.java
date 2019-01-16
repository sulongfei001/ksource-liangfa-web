package com.ksource.common.poi.editor.font;

import com.ksource.common.poi.editor.IFontEditor;
import com.ksource.common.poi.style.font.BoldWeight;
import com.ksource.common.poi.style.font.Font;

/**
 * 实现一些常用的字体<br/>
 * 该类用于把字体加粗
 * @author lxp
 *
 */
public class BoldFontEditor implements IFontEditor {

	public void updateFont(Font font) {
		font.boldweight(BoldWeight.BOLD);
	}

}
