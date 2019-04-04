package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util;

import java.io.File;

import com.autoserve.abc.service.util.SystemGetPropeties;

public class ConfigHelper {
	public static String getMerchantCertPath(){
		String merCert = SystemGetPropeties.getStrString("merchantCertFileName");//商户证书
		String classPath=SystemGetPropeties.getSysPath();
		return classPath+merCert;
//		return "E:\\ssl\\xh99d\\test\\800055100010001.p12";//测试
	}
	public static String getBankCertPath(){
		String bankCert = SystemGetPropeties.getStrString("bankCertFileName");//银行证书名称
		String classPath=SystemGetPropeties.getSysPath();
		return classPath+bankCert;
//		return "E:\\ssl\\xh99d\\test\\bank.cer";//测试
	}
	public static String getBankCrlPath(){
		String bankCrl = SystemGetPropeties.getStrString("bankCrlFileName");//银行吊销列表证书名称
		String classPath=SystemGetPropeties.getSysPath();
		return classPath+bankCrl;
//		return "E:\\ssl\\xh99d\\test\\bank.crl";//测试
	}
	public static String getMerchantCertPass(){
		return SystemGetPropeties.getStrString("merchantCertPass");
	}
	public static String getConfig(String key){
		return SystemGetPropeties.getStrString(key);
	}
	public static String getSftpIp(){
		return SystemGetPropeties.getSftpString("sftp.ip");
//		return "221.239.93.141";//测试
	}
	public static String getSftpPort(){
		return SystemGetPropeties.getSftpString("sftp.port");
//		return "22";//测试
	}
	public static String getSftpUserName(){
		return SystemGetPropeties.getSftpString("sftp.userName");
//		return "XinHuaJiuJiuDai";//测试
	}
	public static String getSftpPassword(){
		return SystemGetPropeties.getSftpString("sftp.password");
//		return "xinhuajiujiu";//测试
	}
	public static String getSftpLocalPath(){
		return SystemGetPropeties.getSftpString("sftp.localPath");
//		return "D:/ftpLocalFile/";//测试
	}
	public static String getSftpRemotePath(){
		return SystemGetPropeties.getSftpString("sftp.remotePath");
//		return "/pfs/800055100010001";//测试
	}
	public static String getSftpRemoteDownPath(){
		return SystemGetPropeties.getSftpString("sftp.remoteDownPath");
//		return "/pfs/800055100010001/CHK";//测试
	}
	public static void main(String[] args) {
		System.out.println("/home/ftpLocalFile/upload/".replace("\\","/").replace("%20"," "));
	}
}
