package com.autoserve.abc.service.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
/**
 * 加密解密类
 * @author DS
 *
 * 2015年4月28日下午2:46:40
 */
public class Encryption {
	private static final String PASSWORD_CRYPT_KEY = "p2pEncryption";
	
	private final static String DES = "DES";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成方法存根
		String str="<?xml version='1.0' encoding='UTF-8'?><data><message><status>0</status><value>处理成功</value></message><policeCheckInfos><policeCheckInfo name='吕小东' id='340421199010102098'><message><status>0</status><value>信息认证成功</value></message><name desc='姓名'>吕小东</name><identitycard desc='身份证号'>340421199010102098</identitycard><compStatus desc='比对状态'>0</compStatus><compResult desc='比对结果'>一致</compResult><policeadd desc='原始发证地'>安徽省凤台县</policeadd><checkPhoto desc='照片'>/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a HBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy MjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAB2AGADASIA AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3 ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3 uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3eKcy g/IAR1BNRHdNcEsvEfGFPWi7IjBmiI3gYP0p9rIjwqysCW60xkbj7O/mxhgpPzJjr70+a4DIFiY7 m+6QDUk8scUDPIwCAc84rgr7xBNPO8ETeXChOzbwce5600ri2OxF7aW6mFpY1PcFuapPrMdm+1Sj xvyrbv0zz+tcM98gQqzZJ5JzyT2zVae93wldw9s9vWnyiud6det5pEkc4XdhR1AxyTU8+s2bOGil JcdwpIA9+1cBFc7UC5Bx09s08XTFcbioHYHj64p2Qcx6RFq0M8AeNXLt0TbzU9vFIpMjqvmN1ye1 eb2Oq3EVyMSkLGflB5/PNd1pOuw6hGEZlE4GSOzD1FS1Ya1LluZIneHYNq8qc9adLdFT5ao3mHtj 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 FBHYUUV0nKKcKxJUHPrTdpZw5ZsZxgGiimB//9k=</checkPhoto><birthday2 desc='出生日期2'>19901010</birthday2><sex2 desc='性别2'>男</sex2></policeCheckInfo></policeCheckInfos></data><operatResult>处理成功</operatResult>";
//		String str="12";
		String a = Encryption.encrypt(str,PASSWORD_CRYPT_KEY);
		String b = Encryption.decrypt(a,PASSWORD_CRYPT_KEY);
		System.out.println(a);
		System.out.println(b);
	}

	/**
	 * 加密
	 * @param src 数据源
	 * @param key 密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */

	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {

		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(src);

	}

	/**
	 * 解密
	 * @param src 数据源
	 * @param key 密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */

	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {

		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		// 现在，获取数据并解密
		// 正式执行解密操作
		return cipher.doFinal(src);

	}

	/**
	 * 密码解密(动态秘钥)
	 * @param data   需要解密的内容
	 * @param passwordCryptKey   秘钥
	 * @return
	 * @throws Exception
	 */

	public final static String decrypt(String data,String passwordCryptKey) {
		try {
			return new String(decrypt(hex2byte(data.getBytes()),
					passwordCryptKey.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 密码加密(动态秘钥)
	 * @param password   需要加密的内容
	 * @param passwordCryptKey  秘钥
	 * @return
	 * @throws Exception
	 */

	public final static String encrypt(String password,String passwordCryptKey) {
		try {
			return byte2hex(encrypt(password.getBytes(), passwordCryptKey
					.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 密码解密(静态秘钥)
	 * @param data   需要解密的内容
	 * @return
	 * @throws Exception
	 */

	public final static String decrypt(String data) {
		try {
			return new String(decrypt(hex2byte(data.getBytes()),
					PASSWORD_CRYPT_KEY.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 密码加密(静态秘钥)
	 * @param password   需要加密的内容
	 * @return
	 * @throws Exception
	 */

	public final static String encrypt(String password) {
		try {
			return byte2hex(encrypt(password.getBytes(), PASSWORD_CRYPT_KEY
					.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 二行制转字符串
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {

		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");

		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
}