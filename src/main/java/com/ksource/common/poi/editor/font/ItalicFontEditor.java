package com.ksource.common.poi.editor.font;

import com.ksource.common.poi.editor.IFontEditor;
import com.ksource.common.poi.style.font.Font;

/**
 * 实现一些常用的字体<br/>
 * 该类用于设置斜体
 * @author lxp
 *
 */
public class ItalicFontEditor implements IFontEditor {

	public void updateFont(Font font) {
		font.italic(true);
	}

}
