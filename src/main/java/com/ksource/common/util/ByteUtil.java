package com.ksource.common.util;

/**
 * @author zhaoxy Aug 7, 2009 4:35:02 PM
 */
public class ByteUtil {
	/**
	 * convert byte array to hex string(lower case)
	 * 
	 * @param data
	 * @return
	 */
	public static String convertToHexString(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}
}
