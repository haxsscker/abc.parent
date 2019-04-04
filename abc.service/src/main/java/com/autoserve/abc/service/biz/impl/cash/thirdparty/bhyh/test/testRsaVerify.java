package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

import junit.framework.TestCase;

import com.security.bssp.ex.SecurityException;
import com.security.bssp.ext.HexStringByte;
import com.security.bssp.ext.SignatureExt;

public class testRsaVerify extends TestCase {
	public static void main(String[] args) {
		testRsaVerify t=new testRsaVerify();
		t.testMyRsaVerify();
	}
	final String encode = "GBK";
	String cerPath = "E:\\ssl\\xh99d\\test\\bank.cer";
	String crlPath = "E:\\ssl\\xh99d\\test\\bank.crl";
	String macString = "4038DB039B29AA2DB9D701F74B77D36E01072E3F08B029DC61E2B440253AA08B702850457A463B8C86396F87E1AAA25721787B900CDBCE526C951198BDF8778FC885C6A6EFC643E1ACC20DF50314F4ABD7D3130329621315DE2B2314B14037423DAF74D8EEDEE253B6852B52E8F32E51BA3749A3CF706649FB90AE1FCE0CE4224D8947EB7891592CD8C6492A721565B7B1140E9EA3886BCD8519B3C2C66D2013BB7B1508AB2CD7387E72E1317AD5D34B98CAAF037290BBD77B5FDD78A768FE8707F8EB0CF858EE4F9C830061CE5FA7C3A96727E081D6AC52C80305F96DC64D41B18CE13299BA91859851694C41A86CA8B03BA0F52F82A406AC7C33D5B9FD7222";
	// 需转码GBK编码
	String dataString = "008000551000100012.0BindCardWebRSA80005510001000100000002603373830001100000056622http://36.33.24.109:8562/bhyhNotify/bindCardReturnhttp://36.33.24.109:8562/bhyhNotify/bindCardNotify.json1590";

	public void testMyRsaVerify() {

		SignatureExt se = new SignatureExt();

		try {
			byte[] signDatab = HexStringByte.hexToByte(macString.getBytes());
			byte[] indatab = dataString.getBytes(encode);
			X509Certificate cert = getCertfromPath(cerPath);
			boolean res = se.verifySignatureWithCert(cert, signDatab, indatab);
			System.out.println(res);
			// X509CRL x509crl = getCRLFromPath(crlPath);
			// System.out.println(se.verifyCRL(cert, x509crl));

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

}
