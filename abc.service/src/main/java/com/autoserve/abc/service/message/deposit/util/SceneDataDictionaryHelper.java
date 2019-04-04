package com.autoserve.abc.service.message.deposit.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.message.deposit.bean.DisplayLinkParam;
import com.autoserve.abc.service.message.deposit.constant.SceneConfig;

/***
 * @Description: 场景式存证_数据字典创建辅助类
 * @Team: 公有云技术支持小组
 * @Author: 天云小生
 * @Date: 2018年01月16日
 */
public class SceneDataDictionaryHelper {
	private static final Logger logger = LoggerFactory.getLogger(SceneDataDictionaryHelper.class);
	/***
	 * 定义所属行业类型
	 */
	public static String createIndustryType(String apiUrl) {
		logger.info("请求API接口地址:" + apiUrl);
		// 数据字典模板ID
		String templetId = null;
		// 行业名称
		String industyName = SceneConfig.INDUSTRY_TYPE;
		// 行业名称列表(根据实际情况进行增减或修改,此处仅以"房屋租赁行业"行业为例)
		ArrayList<String> industries = new ArrayList<String>();
		industries.add(industyName);// 如:金融行业-P2P信贷,医药卫生

		// 请求参数-JSON字符串
		JSONObject param_json = new JSONObject();
		param_json.put("name", JSONArray.fromObject(industries));
		logger.info("请求参数:" + param_json);

		// 请求签名值
		String signature = DigestHelper.getSignature(param_json.toString(), SceneConfig.PROJECT_SECRET,
				SceneConfig.ALGORITHM, SceneConfig.ENCODING);
				// logger.info("请求签名值 = " + signature);

		// HTTP请求内容类型
		String ContentType = "application/json";

		// 设置HTTP请求头信息
		LinkedHashMap<String, String> headers = HttpHelper.getPOSTHeaders(SceneConfig.PROJECT_ID, signature,
				SceneConfig.ALGORITHM, ContentType, SceneConfig.ENCODING);

		// 向指定URL发送POST方法的请求
		JSONObject RequestResult = HttpHelper.sendPOST(apiUrl, param_json.toString(), headers, SceneConfig.ENCODING);
		logger.info("[定义所属行业类型]接口返回json数据:" + RequestResult);
		if (0 == RequestResult.getInt("errCode")) {
			String result = RequestResult.getString("result");
			templetId = JSONHelper.getTempletId(result, industyName);
		} else {
			throw new RuntimeException(MessageFormat.format("发生错误,错误信息 = {0}", RequestResult));
		}
		return templetId;
	}

	/***
	 * 定义业务凭证（名称）(如：房屋租赁合同签署)
	 * 
	 * @param businessTempletId
	 *            所属行业类型ID
	 */
	public static String createSceneType(String apiUrl, String businessTempletId) {
		logger.info("请求API接口地址:" + apiUrl);
		// 数据字典模板ID
		String templetId = null;
		// 业务凭证（名称）
		String sceneBusinessName = SceneConfig.BUSINESS_VOUCHER;
		// 业务凭证（名称）列表(根据实际情况进行增减或修改,此处仅以"房屋租赁合同签署"为例)
		ArrayList<String> scenes = new ArrayList<String>();
		scenes.add(sceneBusinessName);

		// 请求参数-JSON字符串
		JSONObject param_json = new JSONObject();
		// businessTempletId对应的是[定义所属行业类型]时获取的[所属行业类型ID]
		param_json.put("businessTempletId", businessTempletId);
		param_json.put("name", JSONArray.fromObject(scenes));
		logger.info("请求参数:" + param_json);

		// 请求签名值
		String signature = DigestHelper.getSignature(param_json.toString(), SceneConfig.PROJECT_SECRET,
				SceneConfig.ALGORITHM, SceneConfig.ENCODING);
				// logger.info("请求签署值 = " + signature);

		// HTTP请求内容类型
		String ContentType = "application/json";

		// 设置HTTP请求头信息
		LinkedHashMap<String, String> headers = HttpHelper.getPOSTHeaders(SceneConfig.PROJECT_ID, signature,
				SceneConfig.ALGORITHM, ContentType, SceneConfig.ENCODING);
		// 向指定URL发送POST方法的请求
		JSONObject RequestResult = HttpHelper.sendPOST(apiUrl, param_json.toString(), headers, SceneConfig.ENCODING);
		logger.info("[定义业务凭证（名称）]接口返回json数据:" + RequestResult);
		if (0 == RequestResult.getInt("errCode")) {
			String result = RequestResult.getString("result");
			templetId = JSONHelper.getTempletId(result, sceneBusinessName);
		} else {
			throw new RuntimeException(MessageFormat.format("发生错误,错误信息 = {0}", RequestResult));
		}
		return templetId;
	}

	/***
	 * 定义业务凭证中某一证据点名称(如：合同签署人信息)
	 * 
	 * @param sceneTempletId
	 *            业务凭证（名称）ID
	 */
	public static String createSegmentType(String apiUrl, String sceneTempletId) {
		logger.info("请求API接口地址:" + apiUrl);
		// 数据字典模板ID
		String templetId = null;
		//
		String segmentName = SceneConfig.BUSINESS_VOUCHER_EVIDPOINT;
		// 存证场景环节类型列表(根据实际情况进行增减或修改,此处仅以"合同签署人信息"为例)
		ArrayList<String> segments = new ArrayList<String>();
		segments.add(segmentName);

		// 请求参数-JSON字符串
		JSONObject param_json = new JSONObject();
		// sceneTempletId对应的是[定义业务凭证（名称）]时获取的[业务凭证（名称）ID]
		param_json.put("sceneTempletId", sceneTempletId);
		param_json.put("name", JSONArray.fromObject(segments));
		logger.info("请求参数:" + param_json);

		// 请求签名值
		String signature = DigestHelper.getSignature(param_json.toString(), SceneConfig.PROJECT_SECRET,
				SceneConfig.ALGORITHM, SceneConfig.ENCODING);
				// logger.info("请求签署值 = " + signature);

		// HTTP请求内容类型
		String ContentType = "application/json";

		// 设置HTTP请求头信息
		LinkedHashMap<String, String> headers = HttpHelper.getPOSTHeaders(SceneConfig.PROJECT_ID, signature,
				SceneConfig.ALGORITHM, ContentType, SceneConfig.ENCODING);
		// 向指定URL发送POST方法的请求
		JSONObject RequestResult = HttpHelper.sendPOST(apiUrl, param_json.toString(), headers, SceneConfig.ENCODING);
		logger.info("[定义业务凭证中某一证据点名称]接口返回json数据:" + RequestResult);
		if (0 == RequestResult.getInt("errCode")) {
			String result = RequestResult.getString("result");
			templetId = JSONHelper.getTempletId(result, segmentName);
		} else {
			throw new RuntimeException(MessageFormat.format("发生错误,错误信息 = {0}", RequestResult));
		}
		return templetId;
	}

	/***
	 * 定义业务凭证中某一证据点的字段属性(如：姓名-name) 设置显示名称与参数名称的对应关系
	 * 
	 * @param segmentTempletId
	 *            业务凭证中某一证据点名称ID
	 */
	public static void createSegmentPropType(String apiUrl, String segmentTempletId,Map <String,String> param) {
		logger.info("请求API接口地址:" + apiUrl);
		// 业务凭证中某一证据点的字段属性列表
		List<DisplayLinkParam> displayLinkParamList = new ArrayList <DisplayLinkParam>();
		DisplayLinkParam displayLinkParam = null;
		if(SceneConfig.LOAN_CONTRACT.equals(param.get(SceneConfig.CONTRACT_TYPE))){
			displayLinkParam = new DisplayLinkParam();
			displayLinkParam.setDisplayName(SceneConfig.CREDITOR_DISPLAY_NAME);
			displayLinkParam.setParamName(SceneConfig.PARTYA_PARAM_NAME);
			displayLinkParamList.add(displayLinkParam);
			displayLinkParam = new DisplayLinkParam();
			displayLinkParam.setDisplayName(SceneConfig.DEBTOR_DISPLAY_NAME);
			displayLinkParam.setParamName(SceneConfig.PARTYB_PARAM_NAME);
			displayLinkParamList.add(displayLinkParam);
		}else if(SceneConfig.TRANS_CONTRACT.equals(param.get(SceneConfig.CONTRACT_TYPE))){
			displayLinkParam = new DisplayLinkParam();
			displayLinkParam.setDisplayName(SceneConfig.ASSIGNEE_DISPLAY_NAME);
			displayLinkParam.setParamName(SceneConfig.PARTYA_PARAM_NAME);
			displayLinkParamList.add(displayLinkParam);
			displayLinkParam = new DisplayLinkParam();
			displayLinkParam.setDisplayName(SceneConfig.TRANSFEROR_DISPLAY_NAME);
			displayLinkParam.setParamName(SceneConfig.PARTYB_PARAM_NAME);
			displayLinkParamList.add(displayLinkParam);
		}
		displayLinkParam = new DisplayLinkParam();
		displayLinkParam.setDisplayName(SceneConfig.PLATEFORM_DISPLAY_NAME);
		displayLinkParam.setParamName(SceneConfig.PARTYC_PARAM_NAME);
		displayLinkParamList.add(displayLinkParam);
		
		ArrayList<String> segmentProp = new ArrayList<String>();
		for(DisplayLinkParam d : displayLinkParamList){
			segmentProp.add(JSONObject.fromObject(d).toString());
		}

		// 请求参数-JSON字符串
		JSONObject param_json = new JSONObject();
		// segmentTempletId对应的是[定义业务凭证中某一证据点名称]时获取的[定义业务凭证中某一证据点名称ID]
		param_json.put("segmentTempletId", segmentTempletId);
		// 业务凭证中某一证据点字段属性列表
		param_json.put("properties", JSONArray.fromObject(segmentProp));
		logger.info("请求参数:" + param_json);

		// 请求签名值
		String signature = DigestHelper.getSignature(param_json.toString(), SceneConfig.PROJECT_SECRET,
				SceneConfig.ALGORITHM, SceneConfig.ENCODING);
				// logger.info("请求签署值 = " + signature);

		// HTTP请求内容类型
		String ContentType = "application/json";

		// 设置HTTP请求头信息
		LinkedHashMap<String, String> headers = HttpHelper.getPOSTHeaders(SceneConfig.PROJECT_ID, signature,
				SceneConfig.ALGORITHM, ContentType, SceneConfig.ENCODING);
		// 向指定URL发送POST方法的请求
		JSONObject result = HttpHelper.sendPOST(apiUrl, param_json.toString(), headers, SceneConfig.ENCODING);

		logger.info("[定义业务凭证中某一证据点的字段属性]接口返回json数据:" + result);
	}
}
