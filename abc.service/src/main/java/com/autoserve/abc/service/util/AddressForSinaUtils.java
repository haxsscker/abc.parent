package com.autoserve.abc.service.util;

import java.io.UnsupportedEncodingException;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.HttpUtil;

/**
 *  * 根据IP地址获取详细的地域信息  * @project:personGocheck  * @class:AddressUtils.java  *
 * @author:heguanhua E-mail:37809893@qq.com  * @date：Nov 14, 2012 6:38:25 PM  
 */
public class AddressForSinaUtils {

	public static String getAddresses(String content, String encodingString)
			throws UnsupportedEncodingException {
		//新浪接口
		String urlStr = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="+content;
		Map<String, String> params = new LinkedHashMap <String, String>();
		params.put("format", "json");
		params.put("ip", content);
		String returnStr="";
		//String country ="";
		String region = "";
		String city = "";
		try{
//			returnStr=HttpUtil.postRequest(urlStr, params, encodingString);
//			JSONObject obj=JSON.parseObject(returnStr);
//			//country = decodeUnicode(obj.getString("country"));
//		    region = decodeUnicode(obj.getString("province"));
//		    city = decodeUnicode(obj.getString("city"));
		}catch(Exception e){
			//return AddressUtils.getAddresses("ip="+content, "utf-8");
		}
		
	    if (region != "") {
			return region+" "+city;
		}else{
			return "";
			//return AddressUtils.getAddresses("ip="+content, "utf-8");
		}
	}

	
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
							value = (value << 4) + aChar - '0';
							break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
							default:
							throw new IllegalArgumentException(
							"Malformed  encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
				if (aChar == 't') {
				aChar = '\t';
				} else if (aChar == 'r') {
				aChar = '\r';
				} else if (aChar == 'n') {
				aChar = '\n';
				} else if (aChar == 'f') {
				aChar = '\f';
				}
				outBuffer.append(aChar);
				}
			} else {
			outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	public static void main(String[] args) {
		AddressForSinaUtils addressUtils = new AddressForSinaUtils();
		// 测试ip 219.136.134.157 中国=华南=广东省=广州市=越秀区=电信
		String ip = "219.136.134.157";
		String address = "";
		try {
		address = addressUtils.getAddresses(ip, "GBK");
		} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
		}
		System.out.println(address);
		// 输出结果为：广东省,广州市,越秀区
	}
}
