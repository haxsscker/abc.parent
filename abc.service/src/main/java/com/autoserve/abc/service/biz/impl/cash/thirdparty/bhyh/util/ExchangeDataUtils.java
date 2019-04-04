package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeDataUtils {
	private static Logger logger = LoggerFactory.getLogger(ExchangeDataUtils.class);
	private static String requestUrl = ConfigHelper.getConfig("requestUrl");
	private static String signType = ConfigHelper.getConfig("signType");
	private static String encoding = ConfigHelper.getConfig("charset");
	private static String partnerId = ConfigHelper.getConfig("merchantId");
	private static String versionNo = ConfigHelper.getConfig("version");
	private static String mobileRequestUrl = ConfigHelper.getConfig("mobileRequestUrl");
	/**
	 * 获取提交银行报文
	 * @param map
	 * @return
	 */
	public static Map<String, String> getSubmitData(Map<String, String> map) {
		Map<String, String> resultMap = new LinkedHashMap <String, String>();
		String bizType=map.remove("biz_type");//消息类型
		resultMap.put("char_set", encoding);
		resultMap.put("partner_id", partnerId);
		resultMap.put("version_no", versionNo);
		resultMap.put("biz_type", bizType);
		resultMap.put("sign_type", signType);
		resultMap.put("MerBillNo", partnerId+SeqnoHelper.getId(16));
		resultMap.putAll(map);
		resultMap.put("mac", CryptHelper.sign(resultMap, "GBK"));
		resultMap.put("requestUrl", requestUrl);
		logger.info("======================提交银行报文==========================");
		logger.info(resultMap.toString());
		return resultMap;
	}
	/**
	 * 手机端获取提交银行报文
	 * @param map
	 * @return
	 */
	public static Map<String, String> getMobileSubmitData(Map<String, String> map) {
		Map<String, String> resultMap = new LinkedHashMap <String, String>();
//		StringBuffer submitURL = new StringBuffer();
//		submitURL.append(mobileRequestUrl).append("?");
		Map<String, String> paramMap = new LinkedHashMap <String, String>();
		String transid=map.remove("Transid");//消息类型
//		submitURL.append("Transid").append("=").append(transid);
//		submitURL.append("&").append("NetLoanInfo").append("=");
		paramMap.put("PartnerId", partnerId);
		paramMap.put("MerBillNo", partnerId+SeqnoHelper.getId(16));
		paramMap.putAll(map);
		logger.info("======================提交银行报文==========================");
		logger.info(paramMap.toString());
		//数据加密
//		submitURL.append(CryptHelper.AES_Encrypt(paramMap));
//		resultMap.put("requestUrl", submitURL.toString());
		resultMap.put("requestUrl", mobileRequestUrl);
		resultMap.put("Transid", transid);
		resultMap.put("NetLoanInfo", CryptHelper.AES_Encrypt(paramMap));
		resultMap.put("MerBillNo", paramMap.get("MerBillNo"));
		return resultMap;
	}
	/**
	 * 提交报文并处理银行返回信息
	 * @param map
	 * @return
	 * RespCode=000000 表示成功
	 * RespDesc 描述信息
	 * respData 返回数据
	 */
	public static Map<String, String> submitData(Map<String, String> map) {
		Map<String, String> params = new LinkedHashMap <String, String>();
		String bizType=map.remove("biz_type");//消息类型
		String xmlDataElement=map.remove("xml_data_element");//要解析返回的RespData里xml标签名称，解决各个接口不一致的问题
		params.put("char_set", encoding);
		params.put("partner_id", partnerId);
		params.put("version_no", versionNo);
		params.put("biz_type", bizType);
		params.put("sign_type", signType);
//		params.put("MerBillNo", partnerId+SeqnoHelper.getId(16));
		params.putAll(map);
		params.put("mac", CryptHelper.sign(params, "GBK"));
		logger.info("======================提交银行报文==========================");
		logger.info(params.toString());
		String result=HttpUtil.postRequest(requestUrl, params, "GBK");
		if("RED_TIMEOUT".equals(result)){//读取超时，请稍后查询
			Map<String, String> resMap = new HashMap<String, String>();
			resMap.put("RespCode", "RED_TIMEOUT");
    		resMap.put("RespDesc", "读取超时，请稍后查询!");
    		return resMap;
		}
		logger.info("======================银行返回报文==========================");
		logger.info(result);
		Map<String,String> resultMap=XmlHelper.strToMap(result);
		logger.info("======================银行返回报文解析map==========================");
		logger.info(resultMap.toString());
		if(resultMap.containsKey("RespData")){
			String xml=XmlHelper.getRepXml(resultMap.get("RespData"));
			logger.info("======================银行返回RespData报文base64转码后==========================");
			logger.info(xml);
			String respData = XmlHelper.xmlToJsonStr(xml,xmlDataElement);
			resultMap.put("respData", respData);
		}
		//验签
		String mac = resultMap.remove("mac");
		boolean res = CryptHelper.verifySignature(resultMap, mac, "GBK");
		logger.info("验签结果======"+res);
		return resultMap;
	}
}
