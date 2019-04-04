package com.autoserve.abc.service.util;

import java.security.NoSuchAlgorithmException;

public class CryptUtils {
	/**
	 * 计算md5
	 * 
	 * @param source
	 * @return
	 */
	public static String md5(String source) {
		if(source==null){
			return null;
		}
		return md5(source.getBytes());
	}

	/**
	 * 计算md5
	 * 
	 * @param source
	 * @return
	 */
	public static String md5(byte[] source) {
		java.security.MessageDigest md;
		try {
			md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		byte bytes[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，

		StringBuilder sb = new StringBuilder(bytes.length * 2);

		for (byte b : bytes) {
			sb.append(Character.forDigit((b >>> 4) & 15, 16)).append(Character.forDigit(b & 15, 16));
		}
		return sb.toString();
	}
}
