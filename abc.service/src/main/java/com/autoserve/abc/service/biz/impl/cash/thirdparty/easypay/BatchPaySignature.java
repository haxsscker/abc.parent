package com.autoserve.abc.service.biz.impl.cash.thirdparty.easypay;

import com.autoserve.abc.service.biz.entity.SignatureData;
import com.autoserve.abc.service.biz.enums.EasyPayConfig;
import com.autoserve.abc.service.util.Md5Encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类BatchPaySignature.java的实现描述：
 * 
 * @author pp 2014-11-26 上午10:02:13
 */
@SuppressWarnings("restriction")
public class BatchPaySignature {

    /**
     * 生成签名 需要对map中的数据和encryptionStr进行md5摘要
     * 
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String buildBatchPaySign(SignatureData data) {
        Map<String, String> param = new HashMap<String, String>();
        if (data.getBatchBizid() != null)
            param.put("batchBizid", data.getBatchBizid());
        if (data.getBatchContent() != null)
            param.put("batchContent", data.getBatchContent());
        if (data.getBatchVersion() != null)
            param.put("batchVersion", data.getBatchVersion());
        @SuppressWarnings("rawtypes")
        List keys = new ArrayList(param.keySet());
        Collections.sort(keys);
        StringBuilder preStr = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = param.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                preStr.append(key).append('=').append(value);
            } else {
                preStr.append(key).append('=').append(value).append('&');
            }
        }
        preStr.append(EasyPayConfig.KEY);
        return Md5Encrypt.md5(preStr.toString());
    }

    /**
     * RSA加密
     * 
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     * @throws CertificateException
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encryption(String content) throws UnsupportedEncodingException, CertificateException,
            FileNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        // 待加解密的消息
        byte[] msg = content.getBytes("GBK");
        // 用证书的公钥加密
        CertificateFactory cff = CertificateFactory.getInstance("X.509");
        // 证书文件
        InputStream input = BatchPaySignature.class.getResourceAsStream(EasyPayConfig.CERTIFICATEPUBPATH);
        Certificate cf = cff.generateCertificate(input);
        // 得到证书文件携带的公钥
        PublicKey pk1 = cf.getPublicKey();
        // 定义算法：RSA
        Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        byte[] dataReturn = null;
        c1.init(Cipher.PUBLIC_KEY, pk1);
        for (int i = 0; i < msg.length; i += 100) {
            byte[] doFinal = c1.doFinal(ArrayUtils.subarray(msg, i, i + 100));
            dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
        }
        return new String(Base64.encodeBase64(dataReturn), "UTF-8");
    }

    /**
     * 解密
     * 
     * @param dataReturn
     * @return
     * @throws Exception
     */
    public static String decrypt(byte[] dataReturn) throws Exception {

        final String KEYSTORE_PASSWORD = "clientok";
        final String KEYSTORE_ALIAS = "clientok";

        KeyStore ks = KeyStore.getInstance("PKCS12");
        InputStream fis = BatchPaySignature.class.getResourceAsStream(EasyPayConfig.KEYSTORE_FILE);

        char[] nPassword;
        if (KEYSTORE_PASSWORD.trim().equals("")) {
            nPassword = null;
        } else {
            nPassword = KEYSTORE_PASSWORD.toCharArray();
        }
        ks.load(fis, nPassword);
        fis.close();
        PrivateKey privateKey = (PrivateKey) ks.getKey(KEYSTORE_ALIAS, nPassword);
        Cipher rc2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rc2.init(Cipher.DECRYPT_MODE, privateKey);
        // byte[] rmsg2 = rc2.doFinal(dataReturn); // 解密后的数据
        StringBuilder bf_r = new StringBuilder();
        byte[] bs = null;
        for (int i = 0; i < dataReturn.length; i += 128) {
            byte[] doFinal = rc2.doFinal(ArrayUtils.subarray(dataReturn, i, i + 128));
            bs = ArrayUtils.addAll(bs, doFinal);
            // bf_r.append(new String(doFinal,DsfConfig.input_charset));
        }
        bf_r.append(new String(bs, EasyPayConfig.CHART_SET));
        return bf_r.toString();
    }
}
