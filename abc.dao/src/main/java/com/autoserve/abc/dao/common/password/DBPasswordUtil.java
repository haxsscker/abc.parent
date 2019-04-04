package com.autoserve.abc.dao.common.password;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.naming.NamingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类DBPasswordUtil.java的实现描述：DB 密码加解密Util
 */
public class DBPasswordUtil {
    private final static Logger log      = LoggerFactory.getLogger(DBPasswordUtil.class);

    private static final byte[] KEY_DATA = "autoserve_db_secret_key".getBytes();

    public static String encode(String text) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(KEY_DATA);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        // 现在，获取数据并加密
        byte data[] = text.getBytes();
        // 正式执行加密操作
        byte[] encryptedData = cipher.doFinal(data);
        return Hex.encodeHexString(encryptedData);
    }

    public static String decode(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException,
            DecoderException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(KEY_DATA);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        // 正式执行解密操作
        byte decryptedData[] = cipher.doFinal(Hex.decodeHex(secret.toCharArray()));
        return new String(decryptedData);
    }

    public static void main(String[] args) throws NamingException, InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException,
            DecoderException {
        String secretText = encode("phoenix");
        log.info("encode:phoenix->" + secretText);
        log.info("decode:" + secretText + "->" + decode(secretText));

        String secretText2 = encode("root");
        log.info("encode:root->" + secretText2);
        log.info("decode:" + secretText2 + "->" + decode(secretText2));
    }
}
