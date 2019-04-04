package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

import com.security.bssp.ex.SecurityException;
import com.security.bssp.ext.HexStringByte;
import com.security.bssp.ext.MobileData;
import com.security.bssp.ext.SystemOperation;

public class testRsaSign extends TestCase {
	String cert = "E:\\ssl\\xh99d\\test\\800055100010001.p12";

	public void testMyRsaSign() {

		// RSASignUtil rs = new RSASignUtil(cert, "123456");
		String signDataString = "008000551000100012.0AutoInvestAuthRSA800055100010001000000124974356800011000000566221http://36.33.24.109:8562/bhyhNotify/AuthorizeReturnhttp://36.33.24.109:8562/bhyhNotify/AuthorizeNotify.json";
		try {
			System.out.println(sign(cert, "123456", signDataString, "GBK"));
			// System.out.println(rs.sign(signDataString, "GBK"));
			// System.out.println(signFromCert(signDataString));
			// System.out.println(signByPublicKey("8000220000100172.0QueryMerchantAcctsRSA000000SUCCESSPD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8+CiAgPHZpaW4+CiAgICA8cnM+CiAgICAgIDxhY190eXA+ODAwPC9hY190eXA+CiAgICAgIDxhdmxfYmFsPjA8L2F2bF9iYWw+CiAgICAgIDxhY3RfYmFsPjA8L2FjdF9iYWw+CiAgICAgIDxmcnpfYmFsPjA8L2Zyel9iYWw+CiAgICA8L3JzPgogICAgPHJzPgogICAgICA8YWNfdHlwPjgxMDwvYWNfdHlwPgogICAgICA8YXZsX2JhbD4wPC9hdmxfYmFsPgogICAgICA8YWN0X2JhbD4wPC9hY3RfYmFsPgogICAgICA8ZnJ6X2JhbD4wPC9mcnpfYmFsPgogICAgPC9ycz4KICAgIDxycz4KICAgICAgPGFjX3R5cD44MjA8L2FjX3R5cD4KICAgICAgPGF2bF9iYWw+MDwvYXZsX2JhbD4KICAgICAgPGFjdF9iYWw+MDwvYWN0X2JhbD4KICAgICAgPGZyel9iYWw+MDwvZnJ6X2JhbD4KICAgIDwvcnM+CiAgICA8cnM+CiAgICAgIDxhY190eXA+ODMwPC9hY190eXA+CiAgICAgIDxhdmxfYmFsPjA8L2F2bF9iYWw+CiAgICAgIDxhY3RfYmFsPjA8L2FjdF9iYWw+CiAgICAgIDxmcnpfYmFsPjA8L2Zyel9iYWw+CiAgICA8L3JzPgogICAgPHJzPgogICAgICA8YWNfdHlwPjg0MDwvYWNfdHlwPgogICAgICA8YXZsX2JhbD4wPC9hdmxfYmFsPgogICAgICA8YWN0X2JhbD4wPC9hY3RfYmFsPgogICAgICA8ZnJ6X2JhbD4wPC9mcnpfYmFsPgogICAgPC9ycz4KICA8L3ZpaW4+Cg==","1"));
			// SignedPack c = CAP12CertTool( cert, "123456");
			// System.out.println(c.getPriKey()+"---");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param certFilePath
	 *            证书地址
	 * @param password
	 *            私钥秘密吗
	 * @param indata
	 *            待加签数据：需转码GBK
	 * @param encoding
	 *            编码：默认GBK
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String sign(String certFilePath, String password, String indata, String encoding) throws UnsupportedEncodingException {
		String singData = null;

		if (StringUtils.isBlank(encoding)) {
			encoding = "GBK";
		}
		try {
			CAP12CertTool c = new CAP12CertTool(certFilePath, password);
			X509Certificate cert = c.getCert();
			byte[] si = c.getSignData(indata.getBytes(encoding));
			byte[] cr = cert.getEncoded();
			// 公钥
			String hexCert = HexStringByte.byteToHex(cr);
			singData = HexStringByte.byteToHex(si);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return singData;
	}

	public String signFromCert(String indata) {
		SystemOperation so = new SystemOperation();
		MobileData md = null;
		try {
			md = so.getSignPack(indata, cert, "123456");
		} catch (SecurityException e) {
			return null;
		}
		String singData = md.getHexSignData();
		return singData;
	}

}

class CAP12CertTool {
	private static SignedPack signedPack;

	public CAP12CertTool(InputStream fileInputStream, String keyPass) throws SecurityException {
		signedPack = getP12(fileInputStream, keyPass);
	}

	public CAP12CertTool(String path, String keyPass) throws SecurityException, FileNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(new File(path));

		signedPack = getP12(fileInputStream, keyPass);
	}

	private SignedPack getP12(InputStream fileInputStream, String keyPass) throws SecurityException {
		SignedPack sp = new SignedPack();
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			char[] nPassword = (char[]) null;
			if ((keyPass == null) || (keyPass.trim().equals("")))
				nPassword = (char[]) null;
			else {
				nPassword = keyPass.toCharArray();
			}
			ks.load(fileInputStream, nPassword);
			Enumeration enum2 = ks.aliases();
			String keyAlias = null;
			if (enum2.hasMoreElements()) {
				keyAlias = (String) enum2.nextElement();
			}

			PrivateKey priKey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			System.out.println(priKey);
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubKey = cert.getPublicKey();
			sp.setCert((X509Certificate) cert);
			sp.setPubKey(pubKey);
			sp.setPriKey(priKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SecurityException(e.getMessage());
		} finally {
			if (fileInputStream != null)
				try {
					fileInputStream.close();
				} catch (IOException localIOException) {
				}
		}
		return sp;
	}

	public X509Certificate getCert() {
		return signedPack.getCert();
	}

	public PublicKey getPublicKey() {
		return signedPack.getPubKey();
	}

	public PrivateKey getPrivateKey() {
		return signedPack.getPriKey();
	}

	public byte[] getSignData(byte[] indata) throws SecurityException {
		byte[] res = (byte[]) null;
		try {
			Signature signet = Signature.getInstance("SHA1WITHRSA");
			signet.initSign(getPrivateKey());
			signet.update(indata);
			res = signet.sign();
		} catch (InvalidKeyException e) {
			throw new SecurityException(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e.getMessage());
		} catch (SignatureException e) {
			throw new SecurityException(e.getMessage());
		}
		return res;
	}
}

class SignedPack {
	private byte[] signData;
	private PublicKey pubKey;
	private X509Certificate cert;
	private PrivateKey priKey;

	public X509Certificate getCert() {
		return this.cert;
	}

	public void setCert(X509Certificate cert) {
		this.cert = cert;
	}

	public PublicKey getPubKey() {
		return this.pubKey;
	}

	public void setPubKey(PublicKey pubKey) {
		this.pubKey = pubKey;
	}

	public byte[] getSignData() {
		return this.signData;
	}

	public void setSignData(byte[] signData) {
		this.signData = signData;
	}

	public PrivateKey getPriKey() {
		return this.priKey;
	}

	public void setPriKey(PrivateKey priKey) {
		this.priKey = priKey;
	}
}