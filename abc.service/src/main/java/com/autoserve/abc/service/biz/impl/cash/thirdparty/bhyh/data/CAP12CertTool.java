package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SignedPack;

public class CAP12CertTool {
	private static SignedPack signedPack;

	public CAP12CertTool(InputStream fileInputStream, String keyPass)
			throws SecurityException {
		signedPack = this.getP12(fileInputStream, keyPass);
	}

	public CAP12CertTool(String path, String keyPass) throws SecurityException,
			FileNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(new File(path));
		signedPack = this.getP12(fileInputStream, keyPass);
	}

	private SignedPack getP12(InputStream fileInputStream, String keyPass)
			throws SecurityException {
		SignedPack sp = new SignedPack();

		try {
			KeyStore e = KeyStore.getInstance("PKCS12");
			char[] nPassword = (char[]) null;
			if (keyPass != null && !keyPass.trim().equals("")) {
				nPassword = keyPass.toCharArray();
			} else {
				nPassword = (char[]) null;
			}

			e.load(fileInputStream, nPassword);
			Enumeration enum2 = e.aliases();
			String keyAlias = null;
			if (enum2.hasMoreElements()) {
				keyAlias = (String) enum2.nextElement();
			}

			PrivateKey priKey = (PrivateKey) e.getKey(keyAlias, nPassword);
			System.out.println(priKey);
			Certificate cert = e.getCertificate(keyAlias);
			PublicKey pubKey = cert.getPublicKey();
			sp.setCert((X509Certificate) cert);
			sp.setPubKey(pubKey);
			sp.setPriKey(priKey);
		} catch (Exception arg17) {
			arg17.printStackTrace();
			throw new SecurityException(arg17.getMessage());
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException arg16) {
					;
				}
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
			Signature e = Signature.getInstance("SHA1WITHRSA");
			e.initSign(this.getPrivateKey());
			e.update(indata);
			res = e.sign();
			return res;
		} catch (InvalidKeyException arg3) {
			throw new SecurityException(arg3.getMessage());
		} catch (NoSuchAlgorithmException arg4) {
			throw new SecurityException(arg4.getMessage());
		} catch (SignatureException arg5) {
			throw new SecurityException(arg5.getMessage());
		}
	}
}
