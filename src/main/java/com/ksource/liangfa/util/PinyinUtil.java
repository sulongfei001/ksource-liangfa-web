package com.ksource.liangfa.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PinyinUtil {
	public static String getPinyin(String chinese) {
		return getPinyinZh_CN(convertStringByChinese(chinese));
	}

	public static String getPinyinToUpperCase(String chinese) {
		return getPinyinZh_CN(convertStringByChinese(chinese)).toUpperCase();
	}

	public static String getPinyinToLowerCase(String chinese) {
		return getPinyinZh_CN(convertStringByChinese(chinese)).toLowerCase();
	}

	public static String getPinyinFirstToUpperCase(String chinese) {
		return getPinyin(chinese);
	}

	private static HanyuPinyinOutputFormat getDefaultFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
		return format;
	}

	private static Set<String> convertStringByChinese(String chinese) {
		char[] chars = chinese.toCharArray();
		if ((chinese != null) && (!chinese.trim().equalsIgnoreCase(""))) {
			char[] srcChar = chinese.toCharArray();
			String[][] temp = new String[chinese.length()][];
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];

				if ((String.valueOf(c).matches("[\\u4E00-\\u9FA5]+"))
						|| (String.valueOf(c).matches("[\\u3007]")))
					try {
						temp[i] = PinyinHelper.toHanyuPinyinStringArray(
								chars[i], getDefaultFormat());
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				else {
					// temp[i] = String.valueOf(c);
				}
			}
			String[] pingyinArray = exchange(temp);
			Set pinyin = new HashSet();
			for (int i = 0; i < pingyinArray.length; i++) {
				pinyin.add(pingyinArray[i]);
			}
			return pinyin;
		}
		return null;
	}

	private static String[] exchange(String[][] strJaggedArray) {
		String[][] temp = doExchange(strJaggedArray);
		return temp[0];
	}

	private static String[][] doExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[index] = (capitalize(strJaggedArray[0][i]) + capitalize(strJaggedArray[1][j]));
					index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[(i - 1)] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return doExchange(newArray);
		}
		return strJaggedArray;
	}

	private static String capitalize(String s) {
		char[] ch = s.toCharArray();
		if ((ch != null) && (ch.length > 0) && (ch[0] >= 'a') && (ch[0] <= 'z')) {
			ch[0] = ((char) (ch[0] - ' '));
		}
		return new String(ch);
	}

	private static String getPinyinZh_CN(Set<String> stringSet) {
		StringBuilder str = new StringBuilder();
		int i = 0;
		for (String s : stringSet) {
			if (i == stringSet.size() - 1)
				str.append(s);
			else {
				str.append(s + ",");
			}
			i++;
		}
		return str.toString();
	}

	public static String getPinYinHeadChar(String chinese) {
		StringBuffer pinyin = new StringBuffer();
		if ((chinese != null) && (!chinese.trim().equalsIgnoreCase(""))) {
			for (int j = 0; j < chinese.length(); j++) {
				char word = chinese.charAt(j);
				String[] pinyinArray = PinyinHelper
						.toHanyuPinyinStringArray(word);
				if (pinyinArray != null)
					pinyin.append(pinyinArray[0].charAt(0));
				else {
					pinyin.append(word);
				}
			}
		}
		return pinyin.toString();
	}

	public static String strFilter(String str) throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static String getPinYinHeadCharFilter(String chinese) {
		return strFilter(getPinYinHeadChar(chinese));
	}

	public static void main(String[] args) {
		//Logger logger = LoggerFactory.getLogger(PinyinUtil.class);
		String str = "";
		/*System.out.println();
		System.out.println("小写输出：" + getPinyinToLowerCase(str));
		System.out.println("大写输出：" + getPinyinToUpperCase(str));
		System.out.println("首字母大写输出：" + getPinyinFirstToUpperCase(str));*/
		System.out.println("返回中文的首字母输出：" + getPinYinHeadChar(str));
		//System.out.println("返回中文的首字母并过滤特殊字符输出：" + getPinYinHeadCharFilter(str));
	}
}
