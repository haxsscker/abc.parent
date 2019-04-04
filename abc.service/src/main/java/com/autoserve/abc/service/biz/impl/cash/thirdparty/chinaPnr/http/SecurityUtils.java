package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.http;

import java.io.UnsupportedEncodingException;
import java.security.PublicKey;

import chinapnr.PrivateKey;
import chinapnr.SecureLink;

public class SecurityUtils {
	/**
	 * 私钥 ,富友分配给商户的
	 */
	public static PrivateKey privateKey;
	/**
	 * 公钥，富友的公钥
	 */
	public static PublicKey publicKey;
	/**
	 * 私钥文件路径 如：D:/rsa/prkey.key
	 */
	

	/**
	 * 汇付私钥签名
	 * @param value
	 * @return
	 */
	public String  getChkValue(String value){
		String secureResult="";
		SecureLink secureLink=new SecureLink();
		try {
			byte [] MsgDataBytes=value.getBytes("UTF-8");
			//第一种签名方式
			//int result=secureLink.SignMsg("530166", "D://workSpace8.6//huifu//src//MerPrK530166.key", value);
			//第二种签名方式,汇付接口建议用第二种
				String merId=SystemProperties.getChinaPrnString("MerId");
				String keyUrl=SystemProperties.getSysPath()+SystemProperties.getChinaPrnString("MerKeyFile");
				System.out.println(keyUrl+"=keyUrl");
			int result=secureLink.SignMsg(merId, keyUrl, MsgDataBytes);
			System.out.println("返回结果="+result);
			if(result==0){
			secureResult=secureLink.getChkValue();
			System.out.println("secureResult="+secureResult);
			System.out.println("secureResult.length()="+secureResult.length());
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return secureResult;	
	}
	/**
	 * 
	 * true 表示是汇付返回的数据，没有被篡改
	 * false 表示数据被篡改 
	 * @param msgData  返回的数据体
	 * @param chkValue 返回需要验证的数据签名
	 * @return
	 */
	public Boolean  checkSignMsg(String msgData,String chkValue){ 
		Boolean checkResult=false;
		 SecureLink secureLink=new SecureLink();
		 String keyUrl=SystemProperties.getSysPath()+SystemProperties.getChinaPrnString("PgKeyFile");
		 System.out.println(keyUrl+"=keyUrl");
		 int result=secureLink.VeriSignMsg(keyUrl, msgData, chkValue);
		 System.out.println("返回结果="+result);
		 if(result==0){
			 checkResult=true;
		 }
		return checkResult;
	}
}
