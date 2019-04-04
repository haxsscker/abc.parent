package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.ecs.html.Map;

import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.HexStringByte;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.CryptHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.hisun.merchant.atc.MerchantConfig;

public class Test {
	public static void main(String[] args) {
//		MerchantConfig merchantConfig = MerchantConfig.getConfig();
//		merchantConfig.loadPropertiesFromSrc();
//		System.out.println(merchantConfig.getRequestUrl());
//		Properties properties=merchantConfig.getProperties();
//		String value = properties.getProperty("");
//		byte[] c = null; 
//        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//       String RespData="PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8%2BCiAgPHZpaW4%2BCiAgICA8UmVzcERhdGE%2BCiAgICAgIDxhdXRoX3R5cD4xMTwvYXV0aF90eXA%2BCiAgICAgIDxzdGFydF9kdD4yMDE4MDQwODwvc3RhcnRfZHQ%2BCiAgICAgIDxlbmRfZHQ%2BMjAxODA2MzA8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE%2BCiAgICA8UmVzcERhdGE%2BCiAgICAgIDxhdXRoX3R5cD41OTwvYXV0aF90eXA%2BCiAgICAgIDxzdGFydF9kdD4yMDE4MDQwODwvc3RhcnRfZHQ%2BCiAgICAgIDxlbmRfZHQ%2BMjAxODA2MzA8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE%2BCiAgICA8UmVzcERhdGE%2BCiAgICAgIDxhdXRoX3R5cD42MDwvYXV0aF90eXA%2BCiAgICAgIDxzdGFydF9kdD4yMDE4MDQwODwvc3RhcnRfZHQ%2BCiAgICAgIDxlbmRfZHQ%2BMjAxODA2MzA8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE%2BCiAgPC92aWluPgo%3D";
//        try {
//			c = decoder.decodeBuffer(RespData);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}  
//        try {
//			RespData = new String(c,"GBK");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        System.out.println(RespData);
//        System.out.println("-------------------------------------------------------------------------------------------");
//        byte[] c1 = null; 
//        sun.misc.BASE64Decoder decoder1 = new sun.misc.BASE64Decoder();
//       String RespData1="PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8%2BCiAgPHZpaW4%2BCiAgICA8UmVzcERhdGE%2BCiAgICAgIDxhdXRoX3R5cD4xMTwvYXV0aF90eXA%2BCiAgICAgIDxzdGFydF9kdD4yMDE4MDQwODwvc3RhcnRfZHQ%2BCiAgICAgIDxlbmRfZHQ%2BMjAxODA2MzA8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE%2BCiAgICA8UmVzcERhdGE%2BCiAgICAgIDxhdXRoX3R5cD41OTwvYXV0aF90eXA%2BCiAgICAgIDxzdGFydF9kdD4yMDE4MDQwODwvc3RhcnRfZHQ%2BCiAgICAgIDxlbmRfZHQ%2BMjAxODA2MzA8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE%2BCiAgICA8UmVzcERhdGE%2BCiAgICAgIDxhdXRoX3R5cD42MDwvYXV0aF90eXA%2BCiAgICAgIDxzdGFydF9kdD4yMDE4MDQwODwvc3RhcnRfZHQ%2BCiAgICAgIDxlbmRfZHQ%2BMjAxODA2MzA8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE%2BCiAgPC92aWluPgo%3D";
//       RespData1=RespData1.replaceAll("%", "+");
//       try {
//			c1 = decoder1.decodeBuffer(RespData1);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}  
//        RespData1 = new String(c1);
//        System.out.println(RespData1);
//		String RespData="韦玫丰";
//		String encodeStr="%CE%A4%C3%B5%B7%E1";
//		byte[] e = HexStringByte.hexToByte(encodeStr.getBytes());
//		try {
//			System.out.println(new String(e,"GBK"));
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		/*byte[] b = null;   
        try {  
               b = RespData.getBytes("gb2312");                  
           } catch (Exception e) {  
               e.printStackTrace();  
           }  
           if (b != null) {  
        	   RespData = new sun.misc.BASE64Encoder().encode(b).replaceAll("\\s*","");  
           } */
           /*byte[] c = null; 
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
			c = decoder.decodeBuffer(RespData);
		} catch (IOException e) {
			e.printStackTrace();
		}  
        RespData = new String(c);*/
//        System.out.println(URLEncoder.encode(RespData));
		String str="{\"Biz_Type\":\"200001\",\"Char_Set\":\"00\",\"Fee_Amt\":\"200\",\"IdentNo\":\"530103199409303883\",\"IdentType\":\"00\",\"Mac\":\"\",\"MerBillNo\":\"8000551000100010000000929986998\",\"MerPriv\":\"4867\",\"MobileNo\":\"17600000002\",\"OpenAcctId\":\"6225882436635678\",\"OpenBankId\":\"CMB\",\"PartnerId\":\"800055100010001\",\"PlaCustId\":\"0001100000056498\",\"RpCode\":\"000000\",\"RpDesc\":\"SUCCESS\",\"Sign_Type\":\"Des\",\"UsrName\":\"鐙勫阀鏋?,\"Version_No\":\"2.0\"}";
		str="{\"char_set\":\"00\", \"partner_id\":\"800055100010001\", \"version_no\":\"2.0\", \"biz_type\":\"AutoInvestAuth\", \"sign_type\":\"RSA\", \"MerBillNo\":\"8000551000100010000001921716790\", \"PlaCustId\":\"0001100000056622\", \"TxnTyp\":\"1\", \"PageReturnUrl\":\"http://36.33.24.109:8562/bhyhNotify/AuthorizeReturn\", \"BgRetUrl\":\"http://36.33.24.109:8562/bhyhNotify/AuthorizeNotify.json\", \"requestUrl\":\"http://221.239.93.141:9080/bhdep/hipos/payTransaction\"}";

		String vv="OcTNpDNlLYf8GVorX7YPpQL8JAXo61HqegPlFyvaJDSs4AWaHbt75yP1fgWgFzn9oLwnlSsTwiLw8T+G/LqJmPAmBv2ssRVBmq4pZ7QERhYKKZ8ITjDQTemVvEgF1vejXaxzCQxcLzLni2AvaGMsdumMi0meIpO7CYQeMwA6Kl2qOflBpDjk/pG8x7H0JFcfi5c4TQo65cCO4T+7zf29NxzYDDgOalpEk5/jVJsuKeTfBBTrI3OE3QCu79U1YguRUtqEzPTx7su1h0/Zwp0fGf1B69vTku64sHv+PqOxMw4woS/vfyPwC3d+yHfwBXHKVRs32L0MsFbmmEF3tmJ+AkWZ0CG8deCUhQBfhj5llDBjmSfAiWLVinbyAIpqUm1JvqCHwshsn8/OXr8Ma0m/qRhDyhfxmIJo/l6sM+0IsDdU9mcd6mG4XO6DCXWW9wx+2Is5F8spNX323IwqkdBBtWxs/8RyF1t7jQ/0WA5Vv6C38vaojPjsOSKDEbPHOdoNJgk1a/BhheVMmxeH6pWuzfP3/z2WEW4p99wdDGHYqkM=";
		String encryptData="OcTNpDNlLYf8GVorX7YPpQL8JAXo61HqegPlFyvaJDSs4AWaHbt75yP1fgWgFzn9oLwnlSsTwiLw8T%2BG%2FLqJmPAmBv2ssRVBmq4pZ7QERhYKKZ8ITjDQTemVvEgF1vejXaxzCQxcLzLni2AvaGMsdumMi0meIpO7CYQeMwA6Kl2qOflBpDjk%2FpG8x7H0JFcfi5c4TQo65cCO4T%2B7zf29NxzYDDgOalpEk5%2FjVJsuKeTfBBTrI3OE3QCu79U1YguRUtqEzPTx7su1h0%2FZwp0fGf1B69vTku64sHv%2BPqOxMw4woS%2FvfyPwC3d%2ByHfwBXHKVRs32L0MsFbmmEF3tmJ%2BAkWZ0CG8deCUhQBfhj5llDBjmSfAiWLVinbyAIpqUm1JvqCHwshsn8%2FOXr8Ma0m%2FqRhDyhfxmIJo%2Fl6sM%2B0IsDdU9mcd6mG4XO6DCXWW9wx%2B2Is5F8spNX323IwqkdBBtWxs%2F8RyF1t7jQ%2F0WA5Vv6C38vaojPjsOSKDEbPHOdoNJgk1a%2FBhheVMmxeH6pWuzfP3%2Fz2WEW4p99wdDGHYqkM%3D";
		try {
			Map jsonmap=JSONObject.parseObject(str,Map.class);
			System.out.println(jsonmap.toString());
			vv=URLEncoder.encode(vv,"utf-8");
			System.out.println(encryptData);
			System.out.println(vv);
//			encryptData=URLEncoder.encode(encryptData,"utf-8");
			String rspData=AES_Decrypt(encryptData);
			System.out.println(rspData);
//			System.out.println(FormatHelper.GBKDecodeStr(URLEncoder.encode("鐙勫阀鏋","utf-8")));
//			JSONObject rspMap = JSONObject.parseObject(rspData);
//			String UsrName = rspMap.getString("UsrName");
//			System.out.println(UsrName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 数据解密
	 * @param encryptData
	 * @return
	 */
	public static String AES_Decrypt(String encryptData) {
        byte[] decrypt = null; 
        try{ 
        	encryptData = URLDecoder.decode(encryptData ,"utf-8" );
            Key key = generateKey("cbhb&virtu%@)000"); 
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
            cipher.init(Cipher.DECRYPT_MODE, key); 
            decrypt = cipher.doFinal(Base64.decodeBase64(encryptData.getBytes())); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
        return new String(decrypt).trim(); 
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
}
