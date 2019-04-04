package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.CAP12CertTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.HexStringByte;
/**
 * 签名及验证签名类
 * @author sunlu
 *
 */
public class CryptHelper {
	private static Logger logger = LoggerFactory.getLogger(CryptHelper.class);
//	private static String merchantCertPath =""; //证书路径
//	private static String merchantCertPass =""; //证书密码
//	private static String bankCertPath =""; //银行证书名称
	private static String merchantCertPath =ConfigHelper.getMerchantCertPath(); //证书路径
	private static String merchantCertPass =ConfigHelper.getMerchantCertPass(); //证书密码
	private static String bankCertPath =ConfigHelper.getBankCertPath(); //银行证书名称
//	private static String bankCrlPath =ConfigHelper.getBankCrlPath(); //银行吊销列表证书名称
	private static String keyStr = ConfigHelper.getConfig("keyStr");//签名秘钥（手机端）
//	private static String keyStr = "cbhb&virtu%@)000";
	private static final String AESTYPE ="AES/ECB/PKCS5Padding";
	private static CAP12CertTool cAP12CertTool;
	/**
	 * 签名 （使用原生的Signature数字签名对象）
	 * @param map（LinkedHashMap类型，按接口文档返回报文顺序组装）
	 * @param encoding
	 * @return
	 */
	public static String sign(Map<String, String> map, String encoding) {
		String singedData = "";
		// 组装待RSA签名数据包
		StringBuffer waitSignData = new StringBuffer();
		for (Entry<String, String> vo : map.entrySet()) {
			waitSignData.append(vo.getValue());
			logger.info(vo.getKey() + "=====" + vo.getValue());
		}
		logger.info("签名原报文：" + waitSignData.toString());
		// 生成签名
		try {
			Signature e = Signature.getInstance("SHA1WITHRSA");
			e.initSign(getPrivateKey());
			e.update(waitSignData.toString().getBytes(encoding));
			byte[] res = e.sign();
			singedData = HexStringByte.byteToHex(res);
			logger.info("签名信息：" + singedData);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return singedData;
	}
	/**
	 * 验签（使用原生的Signature数字签名对象）
	 * @param map 原数据（LinkedHashMap类型，按接口文档返回报文顺序组装）
	 * @param signData 签名数据
	 * @param encoding 编码格式
	 * @return
	 */
	public static boolean verifySignature(Map<String, String> map, String signData,String encoding) {
		boolean res = false;
		String oridata="";
		StringBuffer waitVerifyData = new StringBuffer();
		for (Entry<String, String> vo : map.entrySet()) {
			waitVerifyData.append(vo.getValue());
			logger.info(vo.getKey() + "=====" + vo.getValue());
		}
		oridata=waitVerifyData.toString();
		logger.info("验签报文：" + oridata);
        try {
			byte[] e = HexStringByte.hexToByte(signData.getBytes());
			byte[] inDataBytes = oridata.getBytes(encoding);
			byte[] signaturepem = checkPEM(e);
			if (signaturepem != null) {
				e = org.bouncycastle.util.encoders.Base64.decode(signaturepem);
			}
			Signature signet = Signature.getInstance("SHA1WITHRSA");
			signet.initVerify(getPublicKey());
			signet.update(inDataBytes);
			res = signet.verify(e);
		} catch (Exception e) {
			logger.error("验签异常：" + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * 数据AES加密
	 * @param map
	 * @return
	 */
	public static String AES_Encrypt(Map<String, String> map){
		String encText = "";
		byte[] encrypt = null; 
        try{ 
        	String plainText = JSON.toJSONString(map);
        	logger.info("AES加密明文====="+plainText);
            Key key = generateKey(keyStr); 
 
            Cipher cipher = Cipher.getInstance(AESTYPE); 
 
            cipher.init(Cipher.ENCRYPT_MODE, key); 
 
            encrypt = cipher.doFinal(plainText.getBytes());     
 
            encText = URLEncoder.encode(new String(Base64.encodeBase64(encrypt)),"utf-8");
        }catch(Exception e){ 
        	logger.error("数据AES加密异常：" + e.getMessage());
            e.printStackTrace(); 
        }
        logger.info("数据AES加密密文====="+encText);
        return encText;
	}
	/**
	 * 数据解密
	 * @param encryptData
	 * @return
	 */
	public static String AES_Decrypt(String encryptData) {
		logger.info("===================银行返回密文===================");
        logger.info(encryptData);
		String res="";
        byte[] decrypt = null; 
        try{ 
        	encryptData = URLDecoder.decode(encryptData ,"utf-8" );
        	logger.debug("URLDecoder==============="+encryptData);
//        	logger.debug("keyStr==============="+keyStr);
            Key key = generateKey(keyStr); 
            Cipher cipher = Cipher.getInstance(AESTYPE); 
            cipher.init(Cipher.DECRYPT_MODE, key); 
            decrypt = cipher.doFinal(Base64.decodeBase64(encryptData.getBytes())); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
        res=new String(decrypt).trim();
        logger.info("===================银行返回报文解密===================");
        logger.info(res);
        return res; 
    }
	private static Key generateKey(String key)throws Exception{ 
        try{            
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES"); 
            return keySpec; 
        }catch(Exception e){ 
            e.printStackTrace(); 
            throw e; 
        } 
  }
	public static PrivateKey getPrivateKey(){
        return getCAP12CertToolInstanse().getPrivateKey();
	}
	
	public static PublicKey getPublicKey(){
		X509Certificate cert = null;
		try {
			cert = getCertfromPath(bankCertPath);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
        return cert.getPublicKey();
	}
	
	
	public static CAP12CertTool getCAP12CertToolInstanse(){
		if(null == cAP12CertTool){
			try {
				cAP12CertTool = new CAP12CertTool(merchantCertPath,merchantCertPass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cAP12CertTool;
	}
	/**
	 * 
	 * @param crt_path
	 *            证书全路径
	 * @return
	 * @throws SecurityException
	 */
	public static X509Certificate getCertfromPath(String crt_path) throws SecurityException {
		X509Certificate cert = null;
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(new File(crt_path));
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			cert = (X509Certificate) cf.generateCertificate(inStream);
		} catch (CertificateException e) {
			throw new SecurityException(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new SecurityException(e.getMessage());
		}
		return cert;
	}

	/**
	 * 
	 * @param crlPath
	 *            crl全路径
	 * @return
	 */
	private X509CRL getCRLFromPath(String crlPath) {
		X509CRL x509crl = null;
		FileInputStream bIn = null;
		try {
			bIn = new FileInputStream(new File(crlPath));
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			CRL crl = cf.generateCRL(bIn);
			x509crl = (X509CRL) crl;
		} catch (CertificateException e) {
		} catch (CRLException e) {
		} catch (FileNotFoundException e) {
		} finally {
			try {
				if (bIn != null)
					bIn.close();
			} catch (IOException localIOException3) {
			}
		}
		return x509crl;
	}
	
	public static byte[] checkPEM(byte[] arg) {
		String arg0 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789/+= \r\n-";

		for (int arg1 = 0; arg1 < arg.length; ++arg1) {
			if (arg0.indexOf(arg[arg1]) == -1) {
				return null;
			}
		}

		StringBuffer arg4 = new StringBuffer(arg.length);
		String arg2 = new String(arg);

		for (int arg3 = 0; arg3 < arg2.length(); ++arg3) {
			if (arg2.charAt(arg3) != 32 && arg2.charAt(arg3) != 13
					&& arg2.charAt(arg3) != 10) {
				arg4.append(arg2.charAt(arg3));
			}
		}

		return arg4.toString().getBytes();
	}
	public static void main(String[] args) {
//		String signDataString = "008000551000100012.0RealNameWebRSA800055100010001000000ezn65ke0wy100520111199101301759司马儒伯17567789889http://60.173.242.216:8090/bhyhNotify/openAccountReturn.actionhttp://60.173.242.216:8090/bhyhNotify/OpenAccountNotify.json别星达000";
//		Map<String, String> params = new LinkedHashMap <String, String>();
//		params.put("test", signDataString);
//		String s=sign(params, "GBK");
//		System.out.println("s===>"+s);
//		testRsaSign test=new testRsaSign();
//		test.testMyRsaSign();
//		testRsaVerify rsaVerify=new testRsaVerify();
//		rsaVerify.testMyRsaVerify();
		//123456签名数据
//		String signedData="09238A94619F4FECB6BC7F9B2316ED3EFAAD5A8F5B4E129933CEA0ECF7C074778D85B35936377DAA2BCA8AFEDC82716AC72157878AA3A6EEBD67BD8002FA86764BCBA53F29D0492DBFAC6026B8EF6A0B0FCEAAF9404D07F8B2CDFE7F7946DC46EA7E366F365C118EC9A3485DBEA0DAFADA55001E5ED44DDF6BE9BF60B3DD680D344BBDC5311F1125A5FEC4CD10460B45F1B2D511DA3F9E105FA52123EE4D4E1BB93F0689A4836158AD1D3E139B78C16780A0F980D296891030CC13CF6BCA2D8CAA32770CFF70F2CC1F4AAAA72F60853D9F84CBD845B1F32C35A720967E610FCC4EE333730D9EFE200D314BAA17838CF743262ADD6248B4E8186AD86DC4D3DA60";
//		boolean r=verifySignature(signDataString, signedData, "GBK");
//		System.out.println("验签结果===================="+r);
		
	}
}
