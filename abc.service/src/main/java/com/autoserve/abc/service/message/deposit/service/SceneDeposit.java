package com.autoserve.abc.service.message.deposit.service;

import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.message.deposit.constant.SceneConfig;
import com.autoserve.abc.service.message.deposit.util.DemoMessage;
import com.autoserve.abc.service.message.deposit.util.SceneDataDictionaryHelper;
import com.autoserve.abc.service.message.deposit.util.SceneHelper;



/***
 * @Description: 场景式存证
 * @Version: Ver_1.1
 * @Team: 公有云技术支持小组
 * @Author: 天云小生
 * @Date: 2018年04月15日
 */

public class SceneDeposit {
	private static final Logger logger = LoggerFactory.getLogger(SceneDeposit.class);
	// 所属行业类型ID
	private String BusinessTempletId = null;
	// 业务凭证（名称）ID
	private String SceneTempletId = null;
	// 业务凭证中某一证据点名称ID
	private String SegmentTempletId = null;
	// 是否使用e签宝电子签名,如果使用请主动传递相关的全部签署记录ID(SignServiceId)
	private boolean isHaveSignServiceId  = true;
	/**
	 * 场景式存证初始化
	 * 根据实际情况创建场景式存证数据字典(全局范围) 【提示:】场景式存证数据字典只需要创建一次,存在于数据字典中的ID可永久使用
	 */
	private void init(){
		logger.info("---------------------------------场景式存证初始化 start----------------------------------------");
		logger.info("API_HOST---------"+SceneConfig.API_HOST);
		logger.info("VIEWPAGE_HOST---------"+SceneConfig.VIEWPAGE_HOST);
		// 重要提示
//		DemoMessage.showImportantMessage();
		// 介绍两种常见的存证场景
//		DemoMessage.showHowToUseHelp();
		try {
			// 定义所属行业类型,获取所属行业类型ID
			BusinessTempletId = SceneDataDictionaryHelper.createIndustryType(SceneConfig.BUS_ADD_API);
			// 定义业务凭证（名称）, 获取业务凭证（名称）ID
			SceneTempletId = SceneDataDictionaryHelper.createSceneType(SceneConfig.SCENE_ADD_API, BusinessTempletId);
			// 定义业务凭证中某一证据点名称,获取业务凭证中某一证据点名称ID
			SegmentTempletId = SceneDataDictionaryHelper.createSegmentType(SceneConfig.SEG_ADD_API, SceneTempletId);
		} catch (Exception e) {
			logger.error("---------------------------------场景式存证初始化异常---------------------------------");
			e.printStackTrace();
		}
		logger.info("---------------------------------场景式存证初始化 end----------------------------------------");
	}
	/**
	 * 场景式存证,返回场景式存证编号（证据链编号）
	 */
	public String sceneDeposit(Map <String,String> param) {
		logger.info("-------------------------存证请求参数-------------------------");
		logger.info(param.toString());
		// 场景式存证编号（证据链编号）
		String C_Evid = null;
		// 原文存证（基础版）证据点ID（编号）
		String H_Standard_Evid = null;
		// 原文存证（高级版）证据点ID（编号）
		String H_Advanced_Evid = null;
		// 摘要存证证据点ID（编号）
		String H_Digest_Evid = null;
		// 用于上传待存证文档的Url
		String UploadUrl = null;
		// 存证证明页面查看完整Url
		String ViewInfoUrl = null;
		
		if(null == SegmentTempletId || "".equals(SegmentTempletId)){
			init();
		}
		try {
			// 定义业务凭证中某一证据点的字段属性
			SceneDataDictionaryHelper.createSegmentPropType(SceneConfig.SEGPROP_ADD_API, SegmentTempletId, param);

			// 待存证的文档路径
			String filePath = param.get(SceneConfig.CONTRACT_PATH);
			logger.info("-------------------------待存证的文档路径-------------------------");
			logger.info(filePath);
			// (如不知道该如何选择原文基础版、原文高级版或摘要版时请询问e签宝对接人员确认贵司所购买的存证类型)
			// 11:原文基础版 | 12:原文高级版 | 13:摘要版
			switch (SceneConfig.SCENE_TYPE) {
			case 11:
				logger.info("- - - - - - - [原文基础版存证] - - - - - - -");
				// 原文基础版存证成功后将原文同时推送到e签宝服务端和司法鉴定中心,不会推送到公证处
				// (请询问e签宝对接人员确认贵司所购买的存证类型)
				logger.info("- - - - - - - [第一步:创建证据链,获取场景式存证编号（证据链编号）] - - - - - - -");
				// 场景式存证编号(证据链编号)(请妥善保管场景式存证编号,以便日后查询存证证明)
				C_Evid = SceneHelper.createChainOfEvidence(SceneConfig.VOUCHER_API, SceneTempletId, param);
				logger.info("- - - - - - - [第二步:创建原文存证（基础版）证据点,获取存证环节编号（证据点编号）和待存证文档上传Url] - - - - - - -");
				// 原文存证（基础版）证据点ID（编号）
				JSONObject standard_Result = SceneHelper.createSegmentOriginal_Standard(SceneConfig.ORIGINAL_STANDARD_API, filePath,
						SegmentTempletId);
				// 原文存证（基础版）证据点ID（编号）
				H_Standard_Evid = standard_Result.getString("evid");
				logger.info("原文存证（基础版）证据点ID（编号）:" + H_Standard_Evid);
				// 待保全文档上传Url
				UploadUrl = standard_Result.getString("url");
				logger.info("待保全文档上传Url:" + UploadUrl);
				logger.info("- - - - - - - [第三步:进行待存证文档的上传] - - - - - - -");
				// 待存证文档上传
				SceneHelper.uploadOriginalDocumen(H_Standard_Evid, UploadUrl, filePath);
				logger.info("- - - - - - - [第四步:追加证据点,将证据点追加到已存在的证据链内形成证据链] - - - - - - -");
				// 是否使用e签宝电子签名,如果使用请主动传递相关的全部签署记录ID(SignServiceId)
				isHaveSignServiceId = true;
				// 向已存在的证据链中关联或追加证据(如追加补充协议的签署存证信息)
				SceneHelper.appendEvidence(SceneConfig.VOUCHER_APPEND_API, C_Evid, H_Standard_Evid,isHaveSignServiceId, param);
				logger.info("- - - - - - - [第五步:场景式存证编号(证据链编号)关联到指定的用户,以便指定用户日后可以顺利出证] - - - - - - -");
				SceneHelper.relateSceneEvIdWithUser(SceneConfig.RELATE_API, C_Evid, param);
				/*logger.info("- - - - - - - [第六步:通过贵司的系统跳转到存证证明查看页面,以便指定用户进行存证查看] - - - - - - -");
				// 存证证明页面查看完整Url
				ViewInfoUrl = SceneHelper.getViewCertificateInfoUrl(SceneConfig.VIEWPAGE_URL, C_Evid, param);
				logger.info("存证证明页面查看完整Url = " + ViewInfoUrl);*/
				break;
			case 12:
				logger.info("- - - - - - - [原文高级版存证] - - - - - - -");
				// 原文高级版存证成功后将原文同时推送到e签宝服务端、司法鉴定中心和公证处
				// (请询问e签宝对接人员确认贵司所购买的存证类型)
				logger.info("- - - - - - - [第一步:创建证据链,获取场景式存证编号（证据链编号）] - - - - - - -");
				// 场景式存证编号(证据链编号)(请妥善保管场景式存证编号,以便日后查询存证证明)
				C_Evid = SceneHelper.createChainOfEvidence(SceneConfig.VOUCHER_API, SceneTempletId, param);
				logger.info("- - - - - - - [第二步:创建原文存证（高级版）证据点,获取存证环节编号（证据点编号）和待存证文档上传Url] - - - - - - -");
				// 原文存证（高级版）证据点ID（编号）
				JSONObject advanced_Result = SceneHelper.createSegmentOriginal_Advanced(SceneConfig.ORIGINAL_ADVANCED_API, filePath,
						SegmentTempletId, param);
				// 原文存证（高级版）证据点ID（编号）
				H_Advanced_Evid = advanced_Result.getString("evid");
				logger.info("原文存证（高级版）证据点ID（编号）:" + H_Advanced_Evid);
				// 待保全文档上传Url
				UploadUrl = advanced_Result.getString("url");
				logger.info("待保全文档上传Url:" + UploadUrl);
				logger.info("- - - - - - - [第三步:进行待存证文档的上传] - - - - - - -");
				// 待存证文档上传
				SceneHelper.uploadOriginalDocumen(H_Advanced_Evid, UploadUrl, filePath);
				logger.info("- - - - - - - [第四步:追加证据点,将证据点追加到已存在的证据链内形成证据链] - - - - - - -");
				// 是否使用e签宝电子签名,如果使用请主动传递相关的全部签署记录ID(SignServiceId)
				isHaveSignServiceId  = true;
				// 向已存在的证据链中关联或追加证据(如追加补充协议的签署存证信息)
				SceneHelper.appendEvidence(SceneConfig.VOUCHER_APPEND_API, C_Evid, H_Advanced_Evid,isHaveSignServiceId, param);
				logger.info("- - - - - - - [第五步:场景式存证编号(证据链编号)关联到指定的用户,以便指定用户日后可以顺利出证] - - - - - - -");
				SceneHelper.relateSceneEvIdWithUser(SceneConfig.RELATE_API, C_Evid, param);
				/*logger.info("- - - - - - - [第六步:通过贵司的系统跳转到存证证明查看页面,以便指定用户进行存证查看] - - - - - - -");
				// 存证证明页面查看完整Url
				ViewInfoUrl = SceneHelper.getViewCertificateInfoUrl(SceneConfig.VIEWPAGE_URL, C_Evid, param);
				logger.info("存证证明页面查看完整Url = " + ViewInfoUrl);*/
				break;
			case 13:
				logger.info("- - - - - - - [摘要版存证] - - - - - - -");
				// 摘要版存证不会将原文进行推送,仅是将原文的摘要(SHA256)推送到e签宝服务端和司法鉴定中心,文件摘要(SHA256)不支持存放到公证处
				// (请询问e签宝对接人员确认贵司所购买的存证类型)
				logger.info("- - - - - - - [第一步:创建证据链,获取场景式存证编号（证据链编号）] - - - - - - -");
				// 场景式存证编号(证据链编号)(请妥善保管场景式存证编号,以便日后查询存证证明)
				C_Evid = SceneHelper.createChainOfEvidence(SceneConfig.VOUCHER_API, SceneTempletId, param);
				logger.info("- - - - - - - [第二步:创建摘要版存证环节,不想将待存证文档上传给第三方系统时可以选择该类存证环节] - - - - - - -");
				// 摘要版证据点ID（编号）
				JSONObject digest_Result = SceneHelper.createSegmentOriginal_Digest(SceneConfig.ORIGINAL_DIGEST_API, filePath,
						SegmentTempletId, param);
				// 摘要版证据点ID（编号）
				H_Digest_Evid = digest_Result.getString("evid");
				logger.info("摘要版证据点ID（编号）:" + H_Digest_Evid);
				logger.info("- - - - - - - [第三步:追加证据点,将证据点追加到已存在的证据链内形成证据链] - - - - - - -");
				// 是否使用e签宝电子签名,如果使用请主动传递相关的全部签署记录ID(SignServiceId)
				isHaveSignServiceId  = true;
				// 向已存在的证据链中关联或追加证据(如追加补充协议的签署存证信息)
				SceneHelper.appendEvidence(SceneConfig.VOUCHER_APPEND_API, C_Evid, H_Digest_Evid,isHaveSignServiceId, param);
				logger.info("- - - - - - - [第四步:场景式存证编号(证据链编号)关联到指定的用户,以便指定用户日后可以顺利出证] - - - - - - -");
				SceneHelper.relateSceneEvIdWithUser(SceneConfig.RELATE_API, C_Evid, param);
				/*logger.info("- - - - - - - [第五步:通过贵司的系统跳转到存证证明查看页面,以便指定用户进行存证查看] - - - - - - -");
				// 存证证明页面查看完整Url
				ViewInfoUrl = SceneHelper.getViewCertificateInfoUrl(SceneConfig.VIEWPAGE_URL, C_Evid, param);
				logger.info("存证证明页面查看完整Url = " + ViewInfoUrl);*/
				break;
			default:
				logger.info("示例演示时发生异常:请从11-13之间选择一种示例~");
				break;
			}
		} catch (Exception e) {
			logger.error("---------------------------------场景式存证异常---------------------------------");
			e.printStackTrace();
			return C_Evid;
		}
		return C_Evid;
	}
	/**
	 * 通过场景式存证编号(证据链编号)拼接存证证明查看页面完整Url
	 * @param param
	 * @return
	 */
	public String getSceneViewInfoUrl(Map <String,String> param) throws Exception{
		// 存证证明页面查看完整Url
		String ViewInfoUrl = "";
		//场景式存证编号(证据链编号)
		String C_Evid = param.get(SceneConfig.DEPOSIT_ID);
		if(!StringUtil.isEmpty(C_Evid)){
			ViewInfoUrl = SceneHelper.getViewCertificateInfoUrl(SceneConfig.VIEWPAGE_URL,param);
		}else{
			logger.error("存证编号为空");
			throw new RuntimeException("存证编号为空");
		}
		return ViewInfoUrl;
	}
}