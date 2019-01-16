package com.ksource.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class SHA1Encode {

	static String encode(String data) {
		StringBuilder sbInfo = new StringBuilder("20090119");
		sbInfo.append(data).append("jiajia");
		String text = sbInfo.toString();
		MessageDigest md;

		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(text.getBytes("iso-8859-1"), 0, text.length());
			byte[] sha1hash = md.digest();
			return ByteUtil.convertToHexString(sha1hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String encode(byte[] data) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(data, 0, data.length);
			byte[] sha1hash = md.digest();
			return ByteUtil.convertToHexString(sha1hash);
		} catch (Exception e) {
			return null;
		}
	}
}
