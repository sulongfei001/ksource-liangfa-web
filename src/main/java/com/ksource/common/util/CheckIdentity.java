package com.ksource.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckIdentity {

	/**
	 * 判断身份证号码格式函数 公民身份号码是特征组合码，排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码
	 * 
	 * @param identitycard
	 *            中国居民身份证号码
	 * @return true 校验成功 false 校验失败
	 */
	public static boolean CheckIDCard(String identitycard) {
		// 身份证号码长度判断
		boolean returnvalue = true;
		if (identitycard.length() < 15 || identitycard.length() == 16
				|| identitycard.length() == 17 || identitycard.length() > 18) {
			returnvalue = false;
		} else {
			// 身份证号码最后一位可能是超过100岁老年人的X.X也可以代表是阿拉伯数字10的意思
			// 所以排除掉最后一位数字进行数字格式测试，最后一位数字有最后一位数字的算法
			String Ai = "";// 身份证号前17位 37078319810220091
			if (identitycard.length() == 18) {
				Ai = identitycard.substring(0, 17);
			} else {
				Ai = identitycard.substring(0, 6) + "19"
						+ identitycard.substring(6);
			}
			if (!isNumeric(Ai))// 判断是否是数字
				returnvalue = false;
			else {
				String strYear = "";
				String strMonth = "";
				String strDay = "";
//				String strBirthDay = "";
				strYear = Ai.substring(6, 10);
				strMonth = Ai.substring(10, 12);
				strDay = Ai.substring(12, 14);
				// 调用日期判断函数IsValidDate()
				if (!IsValidDate(strYear, strMonth, strDay)) {
					returnvalue = false;
				} else {
					/* 校验码的验证 */
					String[] arrVerifyCode = new String[] { "1", "0", "x", "9",
							"8", "7", "6", "5", "4", "3", "2" };
					int[] Wi = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9,
							10, 5, 8, 4, 2 };
					int k = 0;
					int TotalmulAiWi = 0;
					for (k = 0; k < 17; k++) {
						TotalmulAiWi = TotalmulAiWi
								+ Integer.valueOf(Ai.substring(k, k + 1))
										.intValue() * Wi[k];
					}
					int modValue = TotalmulAiWi % 11;
					String strVerifyCode = arrVerifyCode[modValue];
					Ai = Ai + strVerifyCode;

					if ((identitycard.length() == 18)) {
						if (!identitycard.toLowerCase().equals(Ai)) {
							returnvalue = false;
						}

					} else if (identitycard.length() == 15) {
						returnvalue = true;
					}
					/* 校验码的验证 */
				}
			}
		}
		return returnvalue;
	}

	public static boolean IsValidDate(String y, String m, String d) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            dateFormat.parse(y+m+d);
			return true;
		} catch (ParseException e) {
			return false;
		}
		/*
		int year = Integer.valueOf(y).intValue();
		int month = Integer.valueOf(m).intValue();
		int day = Integer.valueOf(d).intValue();
		if ((year < 0) || (9999 < year))
			return false;
		else if ((month < 0) || (12 < month))
			return false;
		else if ((day < 0) || (31 < day))
			return false;
		else
			return true;*/
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] avg) {
		CheckIdentity.CheckIDCard("11010119800101015X");
	}

	/**
	 * 从身份证中截取性别字段
	 * @param str  身份证号字符串
	 * @return
	 * @throws NumberFormatException
	 */
	public static String getGender(String str)throws NumberFormatException {
		String gender = null;
		if (CheckIDCard(str)) {
			// 获取身份证号码长度，15位，18位
			int len = str.length();
			int i = 1;
			try {
				if (len == 18) {
					// 18位获取倒数第二位数字
					i = Integer.parseInt(str.substring(16, 17));
				} else if (len == 15) {
					// 15位获取最后一位数字
					i = Integer.parseInt(str.substring(14, 15));
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			// 如果能被2整除，为女性，不能被2整除则为男性
			if (i % 2 == 0) {
				gender = "0";
			} else {
				gender = "1";
			}
		}
		return gender;
	}
	
	
	/**
	 * 从身份证获取生日
	 * @param identitycard 身份证号 
	 * @param splitStr 年月日分隔符,默认为"-"
	 * @return
	 */
	public static String getBirthday(String identitycard){
		return getBirthday(identitycard,"-");
	}
	public static String getBirthday(String identitycard,String splitStr){
		String birthStr = "";
		
		if(CheckIDCard(identitycard)){
			String Ai = "";
			if (identitycard.length() == 18) {
				Ai = identitycard.substring(0, 17);
			} else {
				Ai = identitycard.substring(0, 6) + "19"
						+ identitycard.substring(6);
			}
			//获取年月日
			String strYear = Ai.substring(6, 10);
			String strMonth = Ai.substring(10, 12);
			String strDay = Ai.substring(12, 14);
			
			birthStr = strYear + splitStr + strMonth +splitStr +strDay;
		}
		
		return birthStr;
	}
	
	/**
	 * 获得生日
	 * @param birthday
	 * @return
	 */
	public static Date genDate(String birthday){
		Date bdate = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			bdate = simpleDateFormat.parse(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return bdate;
	}

}
