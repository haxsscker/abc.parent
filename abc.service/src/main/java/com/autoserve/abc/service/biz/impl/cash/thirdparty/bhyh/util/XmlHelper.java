
package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <b>类名：</b>XmlHelper<br>
 * 
 */
public class XmlHelper {
	protected static Logger logger = LoggerFactory.getLogger(XmlHelper.class);
	/**
	 * xml转为json字符串
	 * @param xmlStr
	 * @param xmlDataElement xml数据标签名称，解决各个接口不一致的问题
	 * @return
	 */
	public static String xmlToJsonStr(String xmlStr,String xmlDataElement){
		String respData="";
		if(null == xmlStr || "".equals(xmlStr)) 
			return respData;
		JSONObject xmlJSONObj = XML.toJSONObject(xmlStr);
		JSONObject root=(JSONObject) xmlJSONObj.get("viin");
		if(xmlStr.contains(xmlDataElement)){
			respData = root.get(xmlDataElement).toString();
		}
		logger.info("==================解析xml报文数据====================");
		logger.info(respData);
		return respData;
	}
	 
	/**
	 * 字符串转map
	 * @param RespStr（字符串中含有&，列如：partner_id=800055100010001&version_no=2.0&biz_type=QueryBalance）
	 * @return
	 */
	public static Map<String,String> strToMap(String RespStr){
		String[] resArr = StringUtils.split(RespStr, "&");
		Map <String,String> repMap = new LinkedHashMap <String,String>();
		for (int httpsClient = 0; httpsClient < resArr.length; ++httpsClient) {
			String res = resArr[httpsClient];
			int repMsg = StringUtils.indexOf(res, '=');
			String nm = StringUtils.substring(res, 0, repMsg);
			String val = StringUtils.substring(res, repMsg + 1);
			repMap.put(nm, val);
		}
		return repMap;
	}
	/**
	 * base64解码
	 * @param RespData
	 * @return
	 */
	public static String getRepXml(String RespData){
		if(null == RespData || "".equals(RespData)) 
			return "";
		byte[] c = null; 
        try {
        	sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			c = decoder.decodeBuffer(RespData);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return new String(c);
	}
	public static void main(String[] args) {
//		String xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?>"
//		+"  <viin>"
//		+" <RespData>"
//		+"  <cap_typ>1</cap_typ>"
//	    +"  <AvlBal>0</AvlBal>"
//	     +" <AcctBal>0</AcctBal>"
//		+" <FrzBal>0</FrzBal>"
//	   +" </RespData>"
//	   +" <RespData>"
//	   +"  <cap_typ>2</cap_typ>"
//	    +"  <AvlBal>0</AvlBal>"
//	     +" <AcctBal>0</AcctBal>"
//		+" <FrzBal>0</FrzBal>"
//	   +" </RespData>"
//	 +" </viin>";
//		System.out.println(xmlToJsonStr(xml,"RespData"));
//		String rep="partner_id=800055100010001&version_no=2.0&biz_type=QueryBalance&sign_type=RSA&RespCode=000000&RespDesc=SUCCESS&mac=6C3F2010162E9EAB0E7C88CBFA165E6C77DC9E41DDE204EC8425A00F3E2C99C21B480887611EDA02C439AFC2BF065226E0F3EAF72A0BC03DB425616005AF131FD6B7CB54A63A5DE71E478AD49012E4946184547794B9D88B40EE085074202008E2758D32349B5C6D422A8AA85CAB87279969F68941B5FCC35F93FB9384207E2C&RespData=PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8+CiAgPHZpaW4+CiAgICA8UmVzcERhdGE+CiAgICAgIDxjYXBfdHlwPjE8L2NhcF90eXA+CiAgICAgIDxBdmxCYWw+MDwvQXZsQmFsPgogICAgICA8QWNjdEJhbD4wPC9BY2N0QmFsPgogICAgICA8RnJ6QmFsPjA8L0ZyekJhbD4KICAgIDwvUmVzcERhdGE+CiAgICA8UmVzcERhdGE+CiAgICAgIDxjYXBfdHlwPjI8L2NhcF90eXA+CiAgICAgIDxBdmxCYWw+MDwvQXZsQmFsPgogICAgICA8QWNjdEJhbD4wPC9BY2N0QmFsPgogICAgICA8RnJ6QmFsPjA8L0ZyekJhbD4KICAgIDwvUmVzcERhdGE+CiAgPC92aWluPgo=";
//		Map<String,String> map=strToMap(rep);
//		System.out.println(map.toString());
//		String res="PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8%2BCiAgPHZpaW4%2BCiAgICA8UmVzcERhdGE%2BCiAgICAgIDxhdXRoX3R5cD4xMTwvYXV0aF90eXA%2BCiAgICAgIDxzdGFydF9kdD4yMDE4MDQwODwvc3RhcnRfZHQ%2BCiAgICAgIDxlbmRfZHQ%2BMjAxODA0MzA8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE%2BCiAgICA8UmVzcERhdGE%2BCiAgICAgIDxhdXRoX3R5cD41OTwvYXV0aF90eXA%2BCiAgICAgIDxzdGFydF9kdD4yMDE4MDQwODwvc3RhcnRfZHQ%2BCiAgICAgIDxlbmRfZHQ%2BMjAxODA0MzA8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE%2BCiAgICA8UmVzcERhdGE%2BCiAgICAgIDxhdXRoX3R5cD42MDwvYXV0aF90eXA%2BCiAgICAgIDxzdGFydF9kdD4yMDE4MDQwODwvc3RhcnRfZHQ%2BCiAgICAgIDxlbmRfZHQ%2BMjAxODA0MzA8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE%2BCiAgPC92aWluPgo%3D";
//		String res="PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8+CiAgPHZpaW4+CiAgICA8UmVzcERhdGE+CiAgICAgIDxhdXRoX3R5cD4xMTwvYXV0aF90eXA+CiAgICAgIDxzdGFydF9kdD4yMDE4MDQwOTwvc3RhcnRfZHQ+CiAgICAgIDxlbmRfZHQ+MjAxODA4MzE8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE+CiAgICA8UmVzcERhdGE+CiAgICAgIDxhdXRoX3R5cD41OTwvYXV0aF90eXA+CiAgICAgIDxzdGFydF9kdD4yMDE4MDQwOTwvc3RhcnRfZHQ+CiAgICAgIDxlbmRfZHQ+MjAxODA4MzE8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE+CiAgICA8UmVzcERhdGE+CiAgICAgIDxhdXRoX3R5cD42MDwvYXV0aF90eXA+CiAgICAgIDxzdGFydF9kdD4yMDE4MDQwOTwvc3RhcnRfZHQ+CiAgICAgIDxlbmRfZHQ+MjAxODA4MzE8L2VuZF9kdD4KICAgIDwvUmVzcERhdGE+CiAgPC92aWluPgo=";
//		res=FormatHelper.GBK2Chinese(res);
//		res=res.replace("%2B", "+");
//		res=res.replace("%3D", "=");
//		try {
//			res=URLDecoder.decode(res, "gbk");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(res);
//		try {
//			res=new String(res.getBytes(), "GBK");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(res);
		String res="PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8+CiAgPHZpaW4+CiAgICA8UmVzcERhdGE+CiAgICAgIDxjYXBfdHlwPjE8L2NhcF90eXA+CiAgICAgIDxBdmxCYWw+MDwvQXZsQmFsPgogICAgICA8QWNjdEJhbD4yMDAwMDwvQWNjdEJhbD4KICAgICAgPEZyekJhbD4yMDAwMDwvRnJ6QmFsPgogICAgPC9SZXNwRGF0YT4KICAgIDxSZXNwRGF0YT4KICAgICAgPGNhcF90eXA+MjwvY2FwX3R5cD4KICAgICAgPEF2bEJhbD4yMzYwMjAwPC9BdmxCYWw+CiAgICAgIDxBY2N0QmFsPjIzNzAyMDA8L0FjY3RCYWw+CiAgICAgIDxGcnpCYWw+MTAwMDA8L0ZyekJhbD4KICAgIDwvUmVzcERhdGE+CiAgPC92aWluPgo=";
		System.out.println(res);
		System.out.println(FormatHelper.GBKDecodeStr(res));
		String xml1=getRepXml(res);
		System.out.println(xml1);
	}
}
