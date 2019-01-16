package com.ksource.common.util;



/**
 * @author zhaoxy Feb 6, 2009 10:18:00 AM
 */
public class PasswordUtil {
	public static String encrypt(String text) {
		return text;
		//return SHA1Encode.encode(text);
	}

	public static boolean verify(String text, String cipher) {
		try {
			String cipherString = SHA1Encode.encode(text);
			return cipherString.equals(cipher) ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
    public static String encrypt2(String text) {
		return SHA1Encode.encode(text);
	}
	
	public static void main(String[] args) {
		System.out.println(PasswordUtil.encrypt("qweasd"));
	}
}
