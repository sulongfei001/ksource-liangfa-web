package com.ksource.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.ksource.exception.BusinessException;

public class EncryptUtil {
	private static final String key = "@#$%^6a7";
	

	public static String decryptByDES(String message) throws Exception {
		byte[] bytesrc = hexStringToBytes(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec("@#$%^6a7".getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec("@#$%^6a7".getBytes("UTF-8"));

		cipher.init(2, secretKey, iv);

		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte, "UTF-8");
	}
	
	public static String encryptByAES(String srcStr) {
		try {
			SecretKey secretKey = getKey();
			Cipher cipher=Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encodeBytes = cipher.doFinal(srcStr.getBytes("UTF-8"));
			String encodeStr=bytesToHexString(encodeBytes);
			return encodeStr;
		} catch (Exception e) {
			e.printStackTrace();
			return srcStr;
		}
	}

	private static SecretKey getKey() throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
		keyGenerator.init(128,new SecureRandom(key.getBytes("UTF-8")));
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey;
	} 
	
	public static String decryptByAES(String encodeStr){
	    String decodeStr = "";
	    try {
			SecretKey secretKey = getKey();
			Cipher cipher=Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			 byte[] decodeBytes = cipher.doFinal(hexStringToBytes(encodeStr));
			decodeStr=new String(decodeBytes,"UTF-8");
		} catch (Exception e) {
			decodeStr = encodeStr;
		}
		return decodeStr;
	}

	
	
	public static String encryptByDES(String message) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec("@#$%^6a7".getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec("@#$%^6a7".getBytes("UTF-8"));
		cipher.init(1, secretKey, iv);

		String str = bytesToHexString(cipher.doFinal(message.getBytes("UTF-8")));
		return str;
	}
	

	private static byte[] hexStringToBytes(String temp) {
		byte[] digest = new byte[temp.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = temp.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = ((byte) byteValue);
		}

		return digest;
	}

	private static String bytesToHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xFF & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}
	/*
	public static void main(String[] args) throws Exception {
		String teString="tsadfsadfest";
		String enString=encryptByAES(teString);
		System.out.println(enString);
		String srcStr= decryptByAES(enString);
		System.out.println(srcStr);
		
		StringBuffer content=new StringBuffer();
		 FileReader fileReader=new FileReader("C:/Users/JMY-01/Desktop/20140925/CASE_ACCUSE.txt");
		 BufferedReader bufferedReader=new BufferedReader(fileReader);
		 String lineContent=bufferedReader.readLine();
		 while (lineContent!=null) {
			content.append(lineContent);
			System.out.println(lineContent);
			String enStr=encryptByAES(lineContent);
			System.out.println(enStr);
			System.out.println(decryptByAES(enStr));
			lineContent=bufferedReader.readLine();
		}
		// System.out.println(content);
		
	}
*/
}
