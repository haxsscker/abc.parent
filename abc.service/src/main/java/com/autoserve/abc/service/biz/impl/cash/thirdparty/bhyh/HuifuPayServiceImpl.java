package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.BidDetail;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.BorrowerDetail;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.FeeDetail;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.http.SystemProperties;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.http.WebUtils;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.http.AbcHttpCallService;
import com.autoserve.abc.service.util.RsaHelper;


@Service
public class HuifuPayServiceImpl implements HuifuPayService {

	private final static Logger log = LoggerFactory.getLogger(HuifuPayServiceImpl.class);
	
	@Resource
	AbcHttpCallService abcHttpCallService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public PlainResult<Map> openAccount(Map params) {
		
		//开户接口需要用的数据
		
		String url = "";
		String Version = ""; //版本号   固定为10 若版本升级向前兼容
		String CmdId = ""; //消息类型   此处为UserRegister
		String MerCustId  = ""; //商户客户号   由汇付生成 ，商户唯一性标识
		String BgRetUrl = ""; //商户后台应答地址  后台异步通知
		String RetUrl  = ""; //页面返回Url       选填
		String UsrId = ""; //用户号  		                 选填
		String UsrName = ""; //真实名称                        选填
		String IdType = ""; //证件类型                          选填
		String IdNo = ""; //证件号码				选填							
		String UsrMp = ""; //手机号				选填
		String UsrEmail = ""; //用户Email 		选填
		String MerPriv = ""; //商户私有域			选填
		String CharSet = "utf-8"; //编码集				选填
//		String ChkValue = ""; //签名
		
		PlainResult<Map> returnData = new PlainResult<Map>();
//		SystemGetPropeties sgp = new SystemGetPropeties();
		Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry = it.next();
			String key = entry.getKey();
			
			if("url".equals(key)){
				url=entry.getValue();
			}
			if("Version".equals(key)){
				Version = entry.getValue();
			}
			if("CmdId".equals(key)){
				CmdId = entry.getValue();
			}
			if("MerCustId".equals(key)){
				MerCustId = entry.getValue();
			}
			if("BgRetUrl".equals(key)){
				BgRetUrl = entry.getValue();
			}
			if("RetUrl".equals(key)){
				RetUrl = entry.getValue();
			}
			if("UsrId".equals(key)){
				UsrId = entry.getValue();
			}
			if("UsrName".equals(key)){
				UsrName = entry.getValue();
			}
			if("IdType".equals(key)){
				IdType = entry.getValue();
			}
			if("IdNo".equals(key)){
				IdNo = entry.getValue();
			}
			if("UsrMp".equals(key)){
				UsrMp = entry.getValue();
			}
			if("UsrEmail".equals(key)){
				UsrEmail = entry.getValue();
			}
			if("MerPriv".equals(key)){
				MerPriv = entry.getValue();
			}
			if("CharSet".equals(key)){
				CharSet = entry.getValue();
			}
//			if("ChkValue".equals(key)){
//				ChkValue = entry.getValue();
//			}
			
			Map<String, String> paramsMap = new HashMap<String, String>();
			
			paramsMap.put("url", url);
			paramsMap.put("Version", Version);
			paramsMap.put("CmdId", CmdId);
			paramsMap.put("MerCustId", MerCustId);
			paramsMap.put("BgRetUrl", BgRetUrl);
			paramsMap.put("RetUrl", RetUrl);
			paramsMap.put("UsrId", UsrId);
			paramsMap.put("UsrName", UsrName);
			paramsMap.put("IdType", IdType);
			paramsMap.put("IdNo", IdNo);
			paramsMap.put("UsrMp", UsrMp);
			paramsMap.put("UsrEmail", UsrEmail);
			paramsMap.put("MerPriv", MerPriv);
			paramsMap.put("CharSet", CharSet);
			
			StringBuffer sf = new StringBuffer();
			sf.append(Version).append(CmdId).append(MerCustId).append(BgRetUrl).append(RetUrl).append(UsrId).append(UsrName).append(IdType).append(IdNo).append(UsrMp).append(UsrEmail).append(MerPriv);
//			String chkValueResult = Version + CmdId + MerCustId + BgRetUrl + RetUrl + UsrId + UsrName + IdType + IdNo
//									+ UsrMp + UsrEmail + MerPriv;
			String chkValueResult = sf.toString();
			RsaHelper rsa = RsaHelper.getInstance();
			String ChkValue = rsa.getChkValue(chkValueResult);
			paramsMap.put("ChkValue", ChkValue);
			
			returnData.setData(paramsMap);
			
		}
		
		return returnData;
	}
	
	@Override
	public HuiFuData openAccountResult(ParameterParser params){
		
		HuiFuData data = new HuiFuData();
//		Map<String, String> paramsMap = new HashMap<String, String>();
		
		String cmdId = params.getString("cmdId").trim();
    	String merCustId = params.getString("merCustId").trim();
    	String usrId = params.getString("UsrId").trim();
    	String usrCustId = params.getString("UsrCustID");
    	if(usrCustId==null || "".equals(usrCustId)){
    		 usrCustId = "";
    	}else{
    		 usrCustId = usrCustId.trim();
    		}
    	String idNo = params.getString("IdNo");

		if(null==idNo || "".equals(idNo)){
			idNo="";
		}else{
			idNo=idNo.trim();
		}
		String usrName = params.getString("UsrName");
		
		if(null==usrName || "".equals(usrName)){
			usrName="";
		}else{
			usrName=usrName.trim();
		}
		String bgRetUrl = params.getString("BgRetUrl").replace("%3A", ":").replace("%2F", "/").trim();
//		String bgRetUrl = request.getParameter("BgRetUrl").replace("%3A", ":").replace("%2F", "/").trim();  //后台应答地址
		// String usrName = request.getParameter("UsrName").trim();
				String retUrl = params.getString("RetUrl").trim();
//		String retUrl = request.getParameter("RetUrl").trim();  //页面返回URL
		if (!"".equals(retUrl)) {
			retUrl = retUrl.replace("%3A", ":").replace("%2F", "/");
		}
		String merPriv = params.getString("MerPriv").trim();
		String usrMp = "";
		if(params.getString("UsrMp")==null || "".equals(params.getString("UsrMp"))){
			usrMp = "";
		}else{
			usrMp = params.getString("UsrMp").trim();
		}
		String usrEmail = params.getString("UsrEmail");
		if(usrEmail==null || "".equals(usrEmail)){
			usrEmail = "";
		}else{
			usrEmail = usrEmail.trim();
		}
		String chkValue = params.getString("ChkValue").trim();
		String respCode = params.getString("RespCode").trim();
		String trxId = params.getString("TrxId");
		if(trxId==null || "".equals(trxId)){
			trxId = "";
		}else{
			trxId = trxId.trim();
		}
		String recvOrdId = "RECV_ORD_ID_" + trxId;
		String respDesc = params.getString("RespDesc").trim();
		System.out.println(usrCustId + "=usrCustId");

		StringBuffer sf = new StringBuffer();
		sf.append(cmdId).append(respCode).append(merCustId).append(usrId)
				.append(usrCustId).append(bgRetUrl).append(trxId)
				.append(retUrl).append(merPriv);
		String retData = sf.toString();
		
		RsaHelper rsa = RsaHelper.getInstance();
		
		System.err.println(retData + "=retData");
		
//		BaseResult result = new BaseResult();
		
		if (rsa.checkSignMsg(retData, chkValue)) {
			System.out.println("respCode=" + respCode);
			System.out.println("汇付开户的数据，未篡改！");
			
			data.setCmdId(cmdId);
			data.setMerCustId(merCustId);
			data.setUsrId(usrId);
			data.setIdNo(idNo);
			data.setUsrName(usrName);
			data.setUsrCustId(usrCustId);
			data.setTrxId(trxId);
			data.setRecvOrdId(recvOrdId);
			data.setRespCode(respCode);
			data.setRespDesc(respDesc);
			data.setUsrMp(usrMp);
			data.setUsrEmail(usrEmail);
			data.setMerPriv(merPriv);
			
		} else {
			System.out.println("数据被篡改！");
//			throw new TarringException("数据被篡改");
			
		}
		return data;
	}

	@Override
	public PlainResult<Map<String, String>> repayment(String  loanJsonList ) {
        
        PlainResult<Map<String,String>> resultMap=new PlainResult<Map<String,String>>();
        HuiFuData data = new HuiFuData();
        JSONObject jsonObject=(JSONObject)JSON.parse(loanJsonList);
        data.setUrl(jsonObject.get("url").toString());
        data.setVersion( jsonObject.get("version").toString());
        data.setCmdId( jsonObject.get("cmdId").toString());
        data.setMerCustId( jsonObject.get("merCustId").toString());
        data.setOrdId( jsonObject.get("ordId").toString());
        data.setOrdDate( jsonObject.get("ordDate").toString());
        data.setOutCustId( jsonObject.get("outCustId").toString());
        data.setSubOrdId( jsonObject.get("subOrdId").toString());
        data.setSubOrdDate( jsonObject.get("subOrdDate").toString());
        data.setOutAcctId( jsonObject.get("outAcctId").toString());
        data.setFee( jsonObject.get("fee").toString());
        data.setInCustId( jsonObject.get("inCustId").toString());
        data.setInAcctId( jsonObject.get("inAcctId").toString());
        data.setTransAmt( jsonObject.get("transAmt").toString());
        data.setDivDetails( jsonObject.get("divDetails").toString());
        data.setFeeObjFlag( jsonObject.get("feeObjFlag").toString());
        data.setBgRetUrl( jsonObject.get("bgRetUrl").toString());
        data.setMerPriv( jsonObject.get("merPriv").toString());
        data.setReqExt( jsonObject.get("reqExt").toString());
        data.setIdNo(jsonObject.get("idNo").toString());
        String outAcctId = data.getOutAcctId();
		if(outAcctId==null || "".equals(outAcctId)){
			outAcctId = "";
		}else{
			outAcctId =outAcctId.trim();
		}
		String inAcctId = data.getInAcctId();
		if(inAcctId==null || "".equals(inAcctId)){
			inAcctId = "";
		}else{
			inAcctId =inAcctId.trim();
		}
		String divDetails = data.getDivDetails();
		if(divDetails==null || "".equals(divDetails)){
			divDetails = "";
		}else{
			divDetails =divDetails.trim();
		}
		String merPriv = data.getMerPriv();
		if(merPriv==null || "".equals(merPriv)){
			merPriv = "";
		}else{
			merPriv =merPriv.trim();
		}
		
		
		Map<String,String> retResultMap=new HashMap<String, String>();
		StringBuffer sf=new StringBuffer();
		sf.append(data.getVersion()).append(data.getCmdId()).append(data.getMerCustId()).append(data.getOrdId()).append(data.getOrdDate()).append(data.getOutCustId())
		.append(data.getSubOrdId()).append(data.getSubOrdDate()).append(data.getOutAcctId()).append(data.getTransAmt()).append(data.getFee()).append(data.getInCustId())
		.append(data.getInAcctId()).append(data.getDivDetails())
		.append(data.getFeeObjFlag()).append(data.getBgRetUrl()).append(data.getMerPriv()).append(data.getReqExt());
		RsaHelper rsa = RsaHelper.getInstance();
		String chkValueResult=sf.toString();
		String chkValue=rsa.getChkValue(chkValueResult);
		Map<String,String> map=new HashMap<String, String>();
		map.put("Version",data.getVersion());
		map.put("CmdId", data.getCmdId());
		map.put("MerCustId", data.getMerCustId());
		map.put("OrdId", data.getOrdId());
		map.put("OrdDate", data.getOrdDate());
		map.put("OutCustId", data.getOutCustId());
		map.put("SubOrdId", data.getSubOrdId());
		map.put("SubOrdDate", data.getSubOrdDate());
		map.put("OutAcctId", data.getOutAcctId());
		map.put("TransAmt", data.getTransAmt());
		map.put("Fee", data.getFee());
		map.put("InCustId", data.getInCustId());
		map.put("InAcctId", data.getInAcctId());
		map.put("DivDetails", data.getDivDetails());
		map.put("FeeObjFlag", data.getFeeObjFlag());
		map.put("BgRetUrl", data.getBgRetUrl());
		map.put("MerPriv", data.getMerPriv());
		map.put("ReqExt", data.getReqExt());
		map.put("ChkValue", chkValue);
		try {
			String  queryBlanceResult=WebUtils.doPost(data.getUrl(),  map);
			if(null!=queryBlanceResult){
				JSONObject json= JSON.parseObject(queryBlanceResult);
				// 验证签名
				String retData=json.getString("CmdId")+json.getString("RespCode")+json.getString("MerCustId")+json.getString("OrdId")+json.getString("OrdDate")
				+json.getString("OutCustId")+json.getString("SubOrdId")+json.getString("SubOrdDate")+json.getString("OutAcctId")+json.getString("TransAmt")+json.getString("Fee")+json.getString("InCustId")
				+json.getString("InAcctId")+json.getString("FeeObjFlag")+json.getString("BgRetUrl").replace("%3A", ":").replace("%2F", "/").trim()+json.getString("MerPriv")+json.getString("RespExt");
				System.out.println("retData="+retData);
				String chkRetValue=json.getString("ChkValue").trim();
				System.out.println("chkRetValue="+chkRetValue);
				//用作于callBack数据处理
				retResultMap.put("ResultCode", json.getString("RespCode"));// 汇付接口返回结果，000代表成功，其余代表失败
				retResultMap.put("Message", json.getString("RespDesc"));// 返回错误信息
				retResultMap.put("InnerSeqNo", data.getIdNo());
				retResultMap.put("Money", json.getString("TransAmt"));
				retResultMap.put("IncomePlanId", json.getString("MerPriv"));
				
				if (rsa.checkSignMsg(retData, chkRetValue)) {
					System.out.println("汇付发送来的数据，未篡改！");
					//用作于bgRetUrl数据处理
					retResultMap.put("RespCode", json.getString("RespCode"));// 汇付接口返回结果，000代表成功，其余代表失败
					retResultMap.put("RespDesc", json.getString("RespDesc"));// 返回错误信息
					String	recvOrdId = "RECV_ORD_ID_" + json.getString("OrdId");
					System.out.println("recvOrdId="+recvOrdId);
					if (json.getString("RespCode").equals("000")) {
						retResultMap.put("CmdId", json.getString("CmdId"));
						retResultMap.put("RespCode", json.getString("RespCode"));
						retResultMap.put("RespDesc", json.getString("RespDesc"));
						retResultMap.put("MerCustId", json.getString("MerCustId"));
						retResultMap.put("OrdId", json.getString("OrdId"));
						retResultMap.put("OrdDate", json.getString("OrdDate"));
						retResultMap.put("OutCustId", json.getString("OutCustId"));
						retResultMap.put("SubOrdId", json.getString("SubOrdId"));
						retResultMap.put("SubOrdDate", json.getString("SubOrdDate"));
						retResultMap.put("OutAcctId", json.getString("OutAcctId"));
						retResultMap.put("TransAmt", json.getString("TransAmt"));
						retResultMap.put("Fee", json.getString("Fee"));
						retResultMap.put("InCustId", json.getString("InCustId"));
						retResultMap.put("InAcctId", json.getString("InAcctId"));	
						retResultMap.put("FeeObjFlag", json.getString("FeeObjFlag"));
						retResultMap.put("BgRetUrl", json.getString("BgRetUrl"));
						retResultMap.put("MerPriv", json.getString("MerPriv"));
						retResultMap.put("RespExt", json.getString("RespExt"));
						retResultMap.put("ChkValue", json.getString("ChkValue"));
						retResultMap.put("RecvOrdId", recvOrdId);			
						resultMap.setSuccess(true);
					}else{
						resultMap.setSuccess(false);
					}
				} else {
					resultMap.setSuccess(false);
				}
			}
			} catch (Exception e) {
				resultMap.setSuccess(false);
				e.printStackTrace();
				throw new BusinessException("还款交易接口异常");
			}
		resultMap.setData(retResultMap);
		return resultMap;
	}

	@Override
	public Map<String, String> repaymentBgRetUrl(HttpServletRequest request) {
		Map<String, String> map=new HashMap<String, String>();
		String cmdId = request.getParameter("CmdId").trim();
		String respCode = request.getParameter("RespCode").trim();
		String respDesc = request.getParameter("RespDesc").trim();
		String merCustId = request.getParameter("MerCustId").trim();
		String ordId=request.getParameter("OrdId").trim();
		String ordDate=request.getParameter("OrdDate").trim();
		String outCustId=request.getParameter("OutCustId").trim();
		String subOrdId=request.getParameter("SubOrdId").trim();
		String subOrdDate=request.getParameter("SubOrdDate").trim();
		String outAcctId=request.getParameter("OutAcctId");
		String transAmt=request.getParameter("TransAmt").trim();
		String fee=request.getParameter("Fee").trim();
		String inCustId=request.getParameter("InCustId").trim();
		String inAcctId=request.getParameter("InAcctId");
		String feeObjFlag=request.getParameter("FeeObjFlag");
		String bgRetUrl=request.getParameter("BgRetUrl").replace("%3A", ":").replace("%2F", "/").trim();
		String merPriv=request.getParameter("MerPriv");
		if(null==merPriv){
			merPriv="";
		}else{
			merPriv=merPriv.trim();
		}
		String respExt=request.getParameter("RespExt");
		if(null==respExt){
			respExt="";
		}else{
			respExt=respExt.trim();
		}
		String chkValue=request.getParameter("ChkValue");
		String recvOrdId = "RECV_ORD_ID_" + ordId;
		// 验证签名
		String retData= cmdId + respCode+ merCustId + ordId + ordDate + outCustId + subOrdId+
			subOrdDate+ outAcctId + transAmt+ fee + inCustId+ inAcctId+ feeObjFlag + bgRetUrl+
			merPriv + respExt;
		RsaHelper rsa = RsaHelper.getInstance();
		System.err.println(retData + "=retData");
		if (rsa.checkSignMsg(retData, chkValue)) {
			System.out.println("汇付发送来的数据，未篡改！");
			map.put("CmdId", cmdId);
			map.put("RespCode", respCode);
			map.put("RespDesc", respDesc);
			map.put("MerCustId", merCustId);
			map.put("OrdId", ordId);
			map.put("OrdDate", ordDate);
			map.put("OutCustId", outCustId);
			map.put("SubOrdId", subOrdId);
			map.put("SubOrdDate", subOrdDate);
			map.put("OutAcctId", outAcctId);
			map.put("Fee", fee);
			map.put("Tee", transAmt);
			map.put("InCustId", inCustId);
			map.put("InAcctId", inAcctId);
			map.put("FeeObjFlag", feeObjFlag);
			map.put("BgRetUrl", bgRetUrl);
			map.put("MerPriv", merPriv);
			map.put("RespExt", respExt);
			map.put("ChkValue", chkValue);
			map.put("RecvOrdId", recvOrdId);	
		} else {
			map.put("RecvOrdId", recvOrdId);
			map.put("RespCode", respCode);
			map.put("MerPriv", merPriv);
			System.out.println("数据被篡改！");
		}
		return map;
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public PlainResult<Map> recharge(HuiFuData data) {
		
		StringBuffer sf = new StringBuffer();
		sf.append(data.getVersion()).append(data.getCmdId()).append(
				data.getMerCustId()).append(data.getUsrCustId()).append(
				data.getOrdId()).append(data.getOrdDate()).append(
				data.getGateBusiId()).append(data.getOpenBankId()).append(
				data.getDcFlag()).append(data.getTransAmt()).append(data.getRetUrl()).append(
				data.getBgRetUrl()).append(data.getMerPriv());
		String chkValueResult = sf.toString();
		//签名验证
		RsaHelper rsa = RsaHelper.getInstance();
		String chkValue = rsa.getChkValue(chkValueResult);
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("url", data.getUrl());
		paramsMap.put("version", data.getVersion());
		paramsMap.put("cmdId", data.getCmdId());
		paramsMap.put("merCustId", data.getMerCustId());
		paramsMap.put("usrCustId", data.getUsrCustId());
		paramsMap.put("ordId", data.getOrdId());
		paramsMap.put("ordDate", data.getOrdDate());
		paramsMap.put("gateBusiId", data.getGateBusiId());
		paramsMap.put("openBankId", data.getOpenBankId());
		paramsMap.put("dcFlag", data.getDcFlag());
		paramsMap.put("transAmt", data.getTransAmt());
		paramsMap.put("bgRetUrl", data.getBgRetUrl());
		paramsMap.put("retUrl", data.getRetUrl());
		paramsMap.put("merPriv", data.getMerPriv());
		paramsMap.put("chkValue", chkValue);
		PlainResult<Map> resultMap = new PlainResult<Map>();
		resultMap.setData(paramsMap);
		return resultMap;
	}
	
	@Override
	public HuiFuData rechargeResult(HttpServletRequest request) {
		
		HuiFuData data = new HuiFuData();
		String cmdId = request.getParameter("CmdId").trim();
		String respCode = request.getParameter("RespCode").trim();
		String respDesc	= request.getParameter("RespDesc").trim();
		String merCustId = request.getParameter("MerCustId").trim();
		String usrCustId = request.getParameter("UsrCustId").trim();
		String ordId = request.getParameter("OrdId").trim();
		String ordDate = request.getParameter("OrdDate").trim();
		String transAmt = request.getParameter("TransAmt");
		if (null != transAmt) {
			transAmt = transAmt.trim();
		} else {
			transAmt = "";
		}
		String trxId = request.getParameter("TrxId");
		if (null == trxId) {
			trxId = "";
		} else {
			trxId = trxId.trim();
		}
		String retUrl = request.getParameter("RetUrl").trim();
		if (null != retUrl) {
			retUrl = retUrl.replace("%3A", ":").replace("%2F", "/").trim();
		} else {
			retUrl = "";
		}
		String bgRetUrl = request.getParameter("BgRetUrl").trim();
		if(bgRetUrl != null){
			bgRetUrl = bgRetUrl.replace("%3A", ":").replace("%2F", "/").trim();
		}else{
			bgRetUrl = "";
		}
		String merPriv = request.getParameter("MerPriv");
		if (null == merPriv) {
			merPriv = "";
		} else {
			merPriv = merPriv.trim();
		}
		String gateBusiId = request.getParameter("GateBusiId");
		if (null == gateBusiId) {
			gateBusiId = "";
		} else {
			gateBusiId = gateBusiId.trim();
		}
		String gateBankId = request.getParameter("GateBankId");
		if (null == gateBankId) {
			gateBankId = "";
		} else {
			gateBankId = gateBankId.trim();
		}
//		String openBankId = request.getParameter("OpenBankId");
//		if (null != openBankId) {
//			openBankId = openBankId.trim();
//		} else {
//			openBankId = "";
//		}
		String chkValue = request.getParameter("ChkValue");
		if(chkValue == null){
			chkValue="";
		}else{
			chkValue=chkValue.trim();
		}
		String feeAmt = request.getParameter("FeeAmt");
		if (null == feeAmt) {
			feeAmt = "";
		} else {
			feeAmt = feeAmt.trim();
		}
		String feeCustId = request.getParameter("FeeCustId");
		if (null == feeCustId) {
			feeCustId = "";
		} else {
			feeCustId = feeCustId.trim();
		}
		String feeAcctId = request.getParameter("FeeAcctId");
		if (null == feeAcctId) {
			feeAcctId = "";
		} else {
			feeAcctId = feeAcctId.trim();
		}
		String openAcctId = request.getParameter("OpenAcctId");
		if (null != openAcctId) {
			openAcctId = openAcctId.trim();
		} else {
			openAcctId = "";
		}
		String recvOrdId = "RECV_ORD_ID_" + trxId;
		// 验证签名
		// String retData = cmdId + respCode + merCustId + usrCustId + ordId
		// + ordDate + transAmt + trxId + retUrl + bgRetUrl + merPriv;
		StringBuffer sf = new StringBuffer();
		sf.append(cmdId).append(respCode).append(merCustId).append(usrCustId)
				.append(ordId).append(ordDate).append(transAmt).append(trxId)
				.append(retUrl).append(bgRetUrl).append(merPriv);
		String retData = sf.toString();
		System.out.println(cmdId+"++++ "+respCode+"++++"+merCustId+" ++++"+usrCustId+"++++ "+ordId+" ++++"+ordDate+"++++ "+transAmt+" ++++"+trxId+"++++ "+retUrl+"++++ "+bgRetUrl+"++++ "+merPriv);
		System.out.println("ChkValue:"+chkValue);
		System.out.println(retData + "=retData");
		RsaHelper rsa = RsaHelper.getInstance();
		if (rsa.checkSignMsg(retData, chkValue)) {
			System.out.println("汇付发送来的数据，未篡改！");
			data.setCmdId(cmdId);
			data.setRespCode(respCode);
			data.setRespDesc(respDesc);
			data.setMerCustId(merCustId);
			data.setGateBusiId(gateBusiId);
			data.setUsrCustId(usrCustId);
			data.setOrdId(ordId);
			data.setOrdDate(ordDate);
			data.setTransAmt(transAmt);
			data.setRetUrl(retUrl);
			data.setBgRetUrl(bgRetUrl);
			data.setMerPriv(merPriv);
			data.setGateBankId(gateBankId);
			data.setFeeAcctId(feeAcctId);
			data.setFeeCustId(feeCustId);
			data.setFeeAcctId(feeAcctId);
			data.setOpenAcctId(openAcctId);
			data.setFeeAmt(feeAmt);
			data.setRecvOrdId(recvOrdId);
			data.setTrxId(trxId);
		} else {
			System.out.println("数据被篡改");
		}
		return data;
	};

	/*投资接口接收返回参数*/
	@Override
	public HuiFuData investResult(HttpServletRequest request) {
		
		 HuiFuData data = new HuiFuData();
		 String  cmdId=request.getParameter("CmdId").trim();
		 String respCode=request.getParameter("RespCode").trim();
		 String respDesc=request.getParameter("RespDesc").trim();
		 String merCustId=request.getParameter("MerCustId").trim();
		 String ordId=request.getParameter("OrdId").trim();
		 String ordDate=request.getParameter("OrdDate").trim();
		 String transAmt=request.getParameter("TransAmt").trim();
		 String usrCustId=request.getParameter("UsrCustId");
		 if(null==usrCustId){
			 usrCustId=""; 
		 }else{
			 usrCustId=usrCustId.trim();
		 }
		 String trxId=request.getParameter("TrxId");
		 if(null==trxId){
			 trxId=""; 
		 }else{
			 trxId=trxId.trim();
		 }
		 String isFreeze=request.getParameter("IsFreeze");
		 if(null==isFreeze){
			 isFreeze="";
		 }else{
			 isFreeze=isFreeze.trim(); 
		 }
		 String freezeOrdId=request.getParameter("FreezeOrdId");
		 if(null==freezeOrdId){
			 freezeOrdId="";
		 }else{
			 freezeOrdId=freezeOrdId.trim(); 
		 }
		 String freezeTrxId=request.getParameter("FreezeTrxId");
		 if(null==freezeTrxId){
			 freezeTrxId="";
		 }else{
			 freezeTrxId=freezeTrxId.trim();
		 }
		 String retUrl=request.getParameter("RetUrl");
		 if(null==retUrl || "".equals(retUrl)){
			 retUrl=""; 
		 }else{
			 retUrl=retUrl.replace("%3A", ":").replace("%2F", "/").trim(); 
		 }
		 String bgRetUrl=request.getParameter("BgRetUrl").replace("%3A", ":").replace("%2F", "/").trim();				
		 String merPriv=request.getParameter("MerPriv");
		 if(null==merPriv || "".equals(merPriv)){
			 merPriv=""; 
		 }else{
			 merPriv=merPriv.replace("%2C", ",").trim();
		 }
		 String respExt=request.getParameter("RespExt");
		 System.out.println(respExt+"=respExt");
		 if(null==respExt || "".equals(respExt)){
			 respExt=""; 
		 }else{
			 try {
				 respExt = URLDecoder.decode(respExt, "UTF-8").trim();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			 respExt=respExt.replace("%3A", ":").replace("%2F", "/").trim();
		 }
		String chkValue = request.getParameter("ChkValue").trim();

		// String
		// retData=cmdId+respCode+merCustId+ordId+ordDate+transAmt+usrCustId+trxId+isFreeze+freezeOrdId+freezeTrxId+retUrl+bgRetUrl+merPriv+resqExt;
		StringBuffer sf=new StringBuffer();
		sf.append(cmdId).append(respCode).append(merCustId).append(ordId).append(ordDate).append(transAmt).append(usrCustId).append(trxId)
		.append(isFreeze).append(freezeOrdId).append(freezeTrxId).append(retUrl).append(bgRetUrl).append(merPriv).append(respExt);
	    RsaHelper rsa = RsaHelper.getInstance();
		System.out.println(sf.toString() + "=sf.toString()");
		if (rsa.checkSignMsg(sf.toString(), chkValue)) {
			System.out.println("汇付发送来的数据，未篡改！");
			data.setCmdId(cmdId);
			data.setRespCode(respCode);
			data.setRespDesc(respDesc);
			data.setMerCustId(merCustId);
			data.setOrdId(ordId);
			data.setOrdDate(ordDate);
			data.setTransAmt(transAmt);
			data.setUsrCustId(usrCustId);
			data.setTrxId(trxId);
			data.setIsFreeze(isFreeze);
			data.setFreezeOrdId(freezeOrdId);
			data.setFreezeTrxId(freezeTrxId);
			data.setRetUrl(retUrl);
			data.setBgRetUrl(bgRetUrl);
			data.setMerPriv(merPriv);
			data.setRespExt(respExt);
			data.setChkValue(chkValue);
		} else {
			System.out.println("数据被篡改！");
		}
		return data;
	}

	@Override
	public PlainResult<Map<String, String>> invest(String loanJsonList) {
		HuiFuData data = new HuiFuData();
        JSONObject jsonObject=(JSONObject)JSON.parse(loanJsonList);
        data.setUrl(jsonObject.get("url").toString());
        data.setVersion( jsonObject.get("version").toString());
        data.setCmdId( jsonObject.get("cmdId").toString());
        data.setMerCustId(jsonObject.get("merCustId").toString());
        data.setOrdId( jsonObject.get("ordId").toString());
        data.setOrdDate( jsonObject.get("ordDate").toString());
        data.setTransAmt( jsonObject.get("transAmt").toString());
        data.setBorrowerRate( jsonObject.get("borrowerRate").toString());
        data.setUsrCustId( jsonObject.get("usrCustId").toString());
        data.setBorrowerAmt( jsonObject.get("borrowerAmt").toString());
        data.setMaxTenderRate( jsonObject.get("maxTenderRate").toString());
        data.setBorrowerCustId( jsonObject.get("borrowerCustId").toString());
        data.setProId( jsonObject.get("proId").toString());
        data.setIsFreeze(jsonObject.get("isFreeze").toString());
        data.setFreezeOrdId( jsonObject.get("freezeOrdId").toString());
        data.setRetUrl( jsonObject.get("retUrl").toString());
        data.setBgRetUrl( jsonObject.get("bgRetUrl").toString());
        data.setMerPriv( jsonObject.get("merPriv").toString());
        data.setReqExt( jsonObject.get("reqExt").toString());
        data.setBorrowerDetails( jsonObject.get("borrowerDetails").toString());
        data.setChkValue( jsonObject.get("chkValue").toString());
     
        data.setInCustId(jsonObject.get("inCustId").toString());

		PlainResult<Map<String,String>> resultMap=new PlainResult<Map<String,String>>();
		Map<String,String> map=new HashMap<String, String>();
    	map.put("Url", data.getUrl());
		map.put("Version", data.getVersion());
		map.put("CmdId", data.getCmdId());
		map.put("MerCustId", data.getMerCustId());
		map.put("OrdId", data.getOrdId());
		map.put("OrdDate", data.getOrdDate());
		map.put("TransAmt", data.getTransAmt());
		map.put("UsrCustId", data.getUsrCustId());
		map.put("MaxTenderRate", data.getMaxTenderRate());
		map.put("BorrowerDetails", data.getBorrowerDetails());
		map.put("IsFreeze", data.getIsFreeze());
		map.put("FreezeOrdId", data.getFreezeOrdId());
		map.put("RetUrl", data.getRetUrl());
		map.put("BgRetUrl", data.getBgRetUrl());
		map.put("MerPriv", data.getMerPriv());
		map.put("ReqExt", data.getReqExt());
		map.put("ChkValue", data.getChkValue());
		map.put("bidType", data.getInCustId());
		StringBuffer sf = new StringBuffer();
		sf.append(data.getVersion()).append(data.getCmdId()).append(
				data.getMerCustId()).append(data.getOrdId()).append(
				data.getOrdDate()).append(data.getTransAmt()).append(
				data.getUsrCustId()).append(data.getMaxTenderRate()).append(
				data.getBorrowerDetails()).append(data.getIsFreeze()).append(
				data.getFreezeOrdId()).append(data.getRetUrl()).append(
				data.getBgRetUrl()).append(data.getMerPriv()).append(data.getReqExt());
		String url = jsonObject.get("url").toString();
			resultMap.setData(map);
			return resultMap;
	}

	@Override
	public HuiFuData delBankCard(String url ,Map<String,String> map) {
		// TODO Auto-generated method stub
		HuiFuData data = new HuiFuData();
		StringBuffer sf = new StringBuffer();
		sf.append(map.get("Version")).append(map.get("CmdId")).append(
				map.get("MerCustId")).append(map.get("UsrCustId")).append(
				map.get("CardId"));
		String chkValueResult = sf.toString();
		RsaHelper rsa = RsaHelper.getInstance();
		String chkValue = rsa.getChkValue(chkValueResult);
		map.put("ChkValue", chkValue);
		String  queryBlanceResult="";
		try {
		  queryBlanceResult=WebUtils.doPost(url,  map);
		  if(null!=queryBlanceResult){
				JSONObject json= JSON.parseObject(queryBlanceResult);
				String retData=json.getString("CmdId")+json.getString("RespCode")+json.getString("MerCustId")+json.getString("UsrCustId")
				+json.getString("CardId");
				String chkRetValue=json.getString("ChkValue").trim();
				if (rsa.checkSignMsg(retData, chkRetValue)) {
					data.setCmdId(json.getString("CmdId"));
					data.setRespCode(json.getString("RespCode"));
					data.setRespDesc(json.getString("RespDesc"));
					data.setMerCustId(json.getString("MerCustId"));
					data.setUsrCustId(json.getString("UsrCustId"));
					data.setCardId(json.getString("CardId"));
				} else {
					System.out.println("数据被篡改！");
				}			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	@Override
	public String bankCard(HuiFuData data) {
		// TODO Auto-generated method stub
		StringBuffer sf = new StringBuffer();
		sf.append(data.getVersion()).append(data.getCmdId()).append(
				data.getMerCustId()).append(data.getUsrCustId()).append(
				data.getBgRetUrl()).append(data.getMerPriv());
		String chkValueResult = sf.toString();
		RsaHelper rsa = RsaHelper.getInstance();
		String chkValue = rsa.getChkValue(chkValueResult);
		System.out.println(chkValueResult.length());
		return chkValue;
	}
	
	@Override
	public HuiFuData bankCardResult(HttpServletRequest request ) {
		HuiFuData data = new HuiFuData();
		String cmdId = request.getParameter("CmdId").trim();
		String respCode = request.getParameter("RespCode").trim();
		String respDesc = request.getParameter("RespDesc").trim();
		String merCustId = request.getParameter("MerCustId").trim();
		String openAcctId = request.getParameter("OpenAcctId").trim();
		String openBankId = request.getParameter("OpenBankId").trim();
		String usrCustId = request.getParameter("UsrCustId").trim();
		String bgRetUrl = request.getParameter("BgRetUrl").replace("%3A", ":")
				.replace("%2F", "/").trim();
		//String merPriv = request.getParameter("MerPriv").trim();
		String chkValue = request.getParameter("ChkValue").trim();
		String trxId = request.getParameter("TrxId").trim();
		
		String recvOrdId = "RECV_ORD_ID_" + trxId;
		System.out.println("开户返回...");
		StringBuffer sf = new StringBuffer();
		sf.append(cmdId).append(respCode).append(merCustId).append(openAcctId)
				.append(openBankId).append(usrCustId).append(trxId).append(
				//		bgRetUrl).append(merPriv);
						bgRetUrl);
		String retData = sf.toString();
		RsaHelper rsa = RsaHelper.getInstance();
		System.err.println(retData + "=retData");
		if (rsa.checkSignMsg(retData, chkValue)) {
			System.out.println("汇付发送来的绑卡数据，未篡改！");
			data.setCmdId(cmdId);
			data.setMerCustId(merCustId);
			data.setUsrCustId(usrCustId);
			data.setTrxId(trxId);
			data.setRecvOrdId(recvOrdId);
			data.setRespCode(respCode);
			data.setRespDesc(respDesc);
			data.setOpenAcctId(openAcctId);
			data.setOpenBankId(openBankId);
		} else {
			System.out.println("数据被篡改！");
		}
		return data;
	}

	
	/**
	 * retMap需要传入的数据有：Version，CmdId，MerCustId，UsrCustId
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public Map<String, String> queryBalanceBg(String url,Map<String, String> map){
		Map<String,String> retretMap=new HashMap<String, String>();
		StringBuffer sf=new StringBuffer();
		sf.append(map.get("Version")).append(map.get("CmdId")).append(map.get("MerCustId")).append(map.get("UsrCustId"));
		RsaHelper rsa = RsaHelper.getInstance();
		//String chkValue=secUtil.getChkValue(chkValueResult);
		String chkValue=rsa.getChkValue(sf.toString());
		map.put("ChkValue", chkValue);
		String  queryBlanceResult="";
		try {
		  queryBlanceResult=WebUtils.doPost(url,map);
		  if(null!=queryBlanceResult){
				JSONObject json= JSON.parseObject(queryBlanceResult);
				String retData=json.getString("CmdId")+json.getString("RespCode")+json.getString("MerCustId")+json.getString("UsrCustId")
				+json.getString("AvlBal")+json.getString("AcctBal")+json.getString("FrzBal");
				//String retData=retMap.get("CmdId")+retMap.get("RespCode")+retMap.get("MerCustId")+retMap.get("UsrCustId")+retMap.get("AvlBal")+retMap.get("AcctBal")+retMap.get("FrzBal");
				System.out.println("retData="+retData.replaceAll(",", ""));
				String chkRetValue=json.getString("ChkValue").trim();
				System.out.println("chkRetValue="+chkRetValue);
				if (rsa.checkSignMsg(retData, chkRetValue)) {
					System.out.println("汇付发送来的数据，未篡改！");
					retretMap.put("RespCode", json.getString("RespCode"));// 汇付接口返回结果，000代表成功，其余代表失败
					retretMap.put("RespDesc", json.getString("RespDesc"));// 返回错误信息
					if (json.getString("RespCode").equals("000")) {
						retretMap.put("AcctBal", json.getString("AcctBal").replaceAll(
								",", ""));// 账面总余额
						retretMap.put("AvlBal", json.getString("AvlBal").replaceAll(",",
								""));// 可用金额
						retretMap.put("FrzBal", json.getString("FrzBal").replaceAll(",",
								""));// 冻结金额
					}
				} else {
					System.out.println("数据被篡改！");
				}			
			}
			System.out.println("map="+map);
			System.out.println("queryBlanceResult="+queryBlanceResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retretMap;
	}

	@Override
	public PlainResult<Map> freeze(HuiFuData data) {
		StringBuffer sf = new StringBuffer();
		sf.append(data.getVersion()).append(data.getCmdId()).append(
				data.getMerCustId()).append(data.getUsrCustId()).append(
				data.getSubAcctType()).append(data.getSubAcctId()).append(
				data.getOrdId()).append(data.getOrdDate()).append(
				data.getTransAmt()).append(data.getRetUrl()).append(
				data.getBgRetUrl()).append(data.getMerPriv());
		String chkValueResult = sf.toString();
		RsaHelper rsa = RsaHelper.getInstance();
		String chkValue = rsa.getChkValue(chkValueResult);
		System.out.println(chkValue.length());
		PlainResult<Map> paramsMap = new PlainResult<Map>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("url", data.getUrl());
		params.put("version", data.getVersion());
		params.put("cmdId", data.getCmdId());
		params.put("merCustId", data.getMerCustId());
		params.put("usrCustId", data.getUsrCustId());	
		params.put("subAcctType", data.getSubAcctType());
		params.put("subAcctId", data.getSubAcctId());
		params.put("ordId", data.getOrdId());
		params.put("ordDate", data.getOrdDate());
		params.put("transAmt", data.getTransAmt());
		params.put("retUrl", data.getRetUrl());
		params.put("merPriv", data.getMerPriv());
		params.put("chkValue", chkValue);
		paramsMap.setData(params);
		return paramsMap;
	}

	@Override
	public Map<String,String> unFreeze(HuiFuData data) {
		StringBuffer sf = new StringBuffer();
		sf.append(data.getVersion()).append(data.getCmdId()).append(
				data.getMerCustId()).append(data.getOrdId()).append(
				data.getOrdDate()).append(data.getTrxId()).append(
				data.getRetUrl()).append(data.getBgRetUrl()).append(
				data.getMerPriv());
		String chkValueResult = sf.toString();
		System.out.println("sf.toString()" + sf.toString());
		RsaHelper rsa = RsaHelper.getInstance();
		String chkValue = rsa.getChkValue(chkValueResult);
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> retMap = new HashMap<String, String>();
		map.put("Version", data.getVersion());
		map.put("CmdId", data.getCmdId());
		map.put("MerCustId", data.getMerCustId());
		map.put("OrdId", data.getOrdId());
		map.put("OrdDate", data.getOrdDate());
		map.put("TrxId", data.getTrxId());
		map.put("RetUrl", data.getRetUrl());
		map.put("BgRetUrl", data.getBgRetUrl());
		map.put("Merpriv", data.getMerPriv());
		map.put("ChkValue", chkValue);
		try {
			String queryBlanceResult = WebUtils.doPost(SystemProperties
					.getChinaPrnString("url"), map);
			if (null != queryBlanceResult) {
				JSONObject json = JSON.parseObject(queryBlanceResult);
				// String retData = cmdId + respCode + merCustId +
				// ordId+ordDate+trxId+retUrl + bgRetUrl + merPriv;
				StringBuffer sb = new StringBuffer();
				sb.append(json.getString("CmdId")).append(
						json.getString("RespCode")).append(
						json.getString("MerCustId")).append(
						json.getString("OrdId")).append(
						json.getString("OrdDate")).append(
						json.getString("TrxId")).append(
						json.getString("RetUrl")).append(
						json.getString("BgRetUrl")).append(
						json.getString("MerPriv"));
				String retData = sb.toString();
				System.out.println("retData=" + sb.toString());
				String chkRetValue = json.getString("ChkValue").trim();
				System.out.println("chkRetValue=" + chkRetValue);
				if (rsa.checkSignMsg(retData, chkRetValue)) {
					System.out.println("汇付发送来的数据，未篡改！");
					retMap.put("RespCode", json.getString("RespCode"));// 汇付接口返回结果，000代表成功，其余代表失败
					retMap.put("RespDesc", json.getString("RespDesc"));// 返回错误信息
					if (json.getString("RespCode").equals("000")) {
						retMap.put("CmdId", json.getString("CmdId"));
						retMap.put("RespCode", json.getString("RespCode"));
						retMap.put("RespDesc", json.getString("RespDesc"));
						retMap.put("MerCustId", json.getString("MerCustId"));
						retMap.put("OrdId", json.getString("OrdId"));
						retMap.put("OrdDate", json.getString("OrdDate"));
						retMap.put("RetUrl", json.getString("RetUrl"));
						retMap.put("BgRetUrl", json.getString("BgRetUrl"));
						retMap.put("MerPriv", json.getString("MerPriv"));
						retMap.put("ChkValue", json.getString("ChkValue"));
						retMap.put("TrxId", json.getString("TrxId"));
						String recvOrdId = "RECV_ORD_ID_"
								+ json.getString("OrdId");
						System.out.println("recvOrdId=" + recvOrdId);
						retMap.put("RecvOrdId", recvOrdId);
					}
				} else {
					System.out.println("数据被篡改！");
				}

			}
			System.out.println("retMap=" + retMap);
			System.out.println("queryBlanceResult=" + queryBlanceResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}

	@Override
	public HuiFuData unfreezehResult(HttpServletRequest request) {
		HuiFuData data = new HuiFuData();
		String cmdId = request.getParameter("CmdId").trim();
		String respCode = request.getParameter("RespCode").trim();
		String respDesc = request.getParameter("RespDesc").trim();
		String merCustId = request.getParameter("MerCustId").trim();
		String ordId = request.getParameter("OrdId").trim();
		String ordDate = request.getParameter("OrdDate").trim();
		String retUrl = request.getParameter("RetUrl");
		if (null == retUrl) {
			retUrl = "";
		} else {
			retUrl = retUrl.replace("%3A", ":").replace("%2F", "/").trim();
		}
		String bgRetUrl = request.getParameter("BgRetUrl").replace("%3A", ":")
				.replace("%2F", "/").trim();
		String merPriv = request.getParameter("MerPriv");
		if (null == merPriv) {
			merPriv = "";
		} else {
			merPriv = merPriv.trim();
		}
		String chkValue = request.getParameter("ChkValue").trim();
		String trxId = request.getParameter("TrxId").trim();
		String recvOrdId = "RECV_ORD_ID_" + ordId;
		// 验证签名
		StringBuffer sf = new StringBuffer();
		sf.append(cmdId).append(respCode).append(merCustId).append(ordId)
				.append(ordDate).append(retUrl).append(trxId).append(bgRetUrl)
				.append(merPriv);
		String retData = sf.toString();
		RsaHelper rsa = RsaHelper.getInstance();
		System.err.println(retData + "=retData");
		if (rsa.checkSignMsg(retData, chkValue)) {
			System.out.println("汇付发送来的数据，未篡改！");
			data.setCmdId(cmdId);
			data.setRespCode(respCode);
			data.setRespDesc(respDesc);
			data.setMerCustId(merCustId);
			data.setOrdId(ordId);
			data.setOrdDate(ordDate);
			data.setRetUrl(retUrl);
			data.setBgRetUrl(bgRetUrl);
			data.setMerPriv(merPriv);
			data.setChkValue(chkValue);
			data.setTrxId(trxId);
			data.setRecvOrdId(recvOrdId);
		} else {
			System.out.println("数据被篡改！");
		}
		return data;
	}
	
	@Override
	public PlainResult<Map> cash(HuiFuData data) {
		// TODO Auto-generated method stub
		StringBuffer sf = new StringBuffer();
		sf.append(data.getVersion()).append(data.getCmdId()).append(
				data.getMerCustId()).append(data.getOrdId()).append(
				data.getUsrCustId()).append(data.getTransAmt()).append(
				data.getServFee()).append(data.getServFeeAcctId()).append(
				data.getOpenAcctId()).append(data.getRetUrl()).append(
				data.getBgRetUrl()).append(data.getRemark()).append(
				data.getMerPriv()).append(data.getReqExt());
		String chkValueResult = sf.toString();
		System.out.println(chkValueResult);
		RsaHelper rsa = RsaHelper.getInstance();
		String chkValue = rsa.getChkValue(chkValueResult);
		System.out.println(chkValue.length());
		PlainResult<Map> plr=new PlainResult<Map>();
		Map<String,String> map=new HashMap<String, String>();
		map.put("url", data.getUrl());
		map.put("version", data.getVersion());
		map.put("cmdId", data.getCmdId());
		map.put("merCustId", data.getMerCustId());
		map.put("ordId", data.getOrdId());
		map.put("usrCustId", data.getUsrCustId());
		map.put("transAmt", data.getTransAmt());
		map.put("servFee", data.getServFee());//平台收取的手续费，该数据保留2为小数点
		map.put("servFeeAcctId", data.getServFeeAcctId());
		map.put("retUrl", data.getRetUrl());
		map.put("bgRetUrl", data.getBgRetUrl());
		map.put("remark", data.getRemark());
		map.put("reqExt", data.getReqExt());
		map.put("charSet", data.getCharSet());
		map.put("merPriv", data.getMerPriv());
		map.put("chkValue", chkValue);
		plr.setData(map);
		return plr;
	}

	@Override
	public HuiFuData cashResult(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HuiFuData data = new HuiFuData();
		String cmdId = request.getParameter("CmdId").trim();
		String respCode = request.getParameter("RespCode").trim();
		String respDesc = request.getParameter("RespDesc").trim();
		String merCustId = request.getParameter("MerCustId").trim();
		String ordId = request.getParameter("OrdId").trim();
		String usrCustId = request.getParameter("UsrCustId").trim();
		String transAmt = request.getParameter("TransAmt").trim();
		String openAcctId = request.getParameter("OpenAcctId");//null
		if (openAcctId == null) {
			openAcctId = "";
		} else {
			openAcctId = openAcctId.trim();
		}
		String openBankId = request.getParameter("OpenBankId");//null
		if (openBankId == null) {
			openBankId = "";
		} else {
			openBankId = openBankId.trim();
		}
		String servFee = request.getParameter("ServFee");
		if (servFee == null) {
			servFee = "";
		} else {
			servFee = servFee.trim();
		}
		String servFeeAcctId = request.getParameter("ServFeeAcctId");
		if(servFeeAcctId==null){
			servFeeAcctId="";
		}else{
			servFeeAcctId=servFeeAcctId.trim();
	    }
		String feeAmt = request.getParameter("FeeAmt");//null
		if (feeAmt == null) {
			feeAmt = "";
		} else {
			feeAmt = feeAmt.trim();
		}
		String feeCustId = request.getParameter("FeeCustId");
		if (feeCustId == null) {
			feeCustId = "";
		} else {
			feeCustId = feeCustId.trim();
		}
		String feeAcctId = request.getParameter("FeeAcctId");
		if (feeAcctId == null) {
			feeAcctId = "";
		} else {
			feeAcctId = feeAcctId.trim();
		}
		
		String retUrl = request.getParameter("RetUrl");
		if (retUrl == null) {
			retUrl = "";
		} else {
			retUrl = retUrl.replace("%3A", ":").replace("%2F", "/").trim().trim();
		}
		String bgRetUrl = request.getParameter("BgRetUrl").replace("%3A", ":")
				.replace("%2F", "/").trim();
		String merPriv = request.getParameter("MerPriv");
		if (merPriv == null) {
			merPriv = "";
		} else {
			merPriv = merPriv.trim();
		}
		String respExt = request.getParameter("RespExt");
		if(respExt==null){
			respExt = "";
		} else {
			respExt = respExt.trim();
		}
		String recvOrdId = "RECV_ORD_ID_" + ordId;
		String chkValue = request.getParameter("ChkValue").trim();
		// 核对签名是否正确
		// String retData = cmdId + respCode + merCustId + ordId + usrCustId
		// + transAmt + openAcctId + openBankId + feeAmt + feeCustId
		// + feeAcctId + servFee + servFeeAcctId + retUrl + bgRetUrl
		// + merPriv + respExt;
	//	String retData = cmdId + respCode + merCustId + ordId+usrCustId
	//	+transAmt + openAcctId+openBankId+feeAmt+feeCustId+feeAcctId
	//	+servFee+servFeeAcctId+retUrl+bgRetUrl+merPriv+respExt;
		StringBuffer sf = new StringBuffer();
		sf.append(cmdId).append(respCode).append(merCustId).append(ordId)
				.append(usrCustId).append(transAmt).append(openAcctId).append(openBankId).append(
						feeAmt).append(feeCustId).append(feeAcctId).append(
						servFee).append(servFeeAcctId).append(retUrl).append(
						bgRetUrl).append(merPriv).append(respExt);
		String retData = sf.toString();
		System.err.println(retData + "=retData");
		RsaHelper rsa = RsaHelper.getInstance();
		System.out.println(respCode + "=respCode");
		if (rsa.checkSignMsg(retData, chkValue)) {
			System.out.println("汇付发送来的数据，未篡改！");
			data.setCmdId(cmdId);
			data.setRespCode(respCode);
			data.setRespDesc(respDesc);
			data.setMerCustId(merCustId);
			data.setOrdId(ordId);
			data.setUsrCustId(usrCustId);
			data.setTransAmt(transAmt);
			data.setOpenAcctId(openAcctId);
			data.setOpenBankId(openBankId);
			data.setServFee(servFee);
			data.setServFeeAcctId(servFeeAcctId);
			data.setFeeAmt(feeAmt);
			data.setFeeCustId(feeCustId);
			data.setFeeAcctId(feeAcctId);
			data.setRetUrl(retUrl);
			data.setBgRetUrl(bgRetUrl);
			data.setMerPriv(merPriv);
			data.setRespExt(respExt);
			data.setRecvOrdId(recvOrdId);
			data.setChkValue(chkValue);
		} else {
			System.out.println("数据被篡改！");
		}
		return data;
	}
	@Override
	public PlainResult<Map<String, String>> crdit(String loanJsonList) {
		PlainResult<Map<String,String>> resultMap=new PlainResult<Map<String,String>>();
		HuiFuData data = new HuiFuData();
        JSONObject jsonList= JSON.parseObject(loanJsonList);
		data.setUrl(jsonList.getString("url"));
		data.setVersion(jsonList.getString("version"));
		data.setCmdId(jsonList.getString("cmdId"));
		data.setMerCustId(jsonList.getString("merCustId"));
		data.setOrdId(jsonList.getString("ordId"));
		data.setOrdDate(jsonList.getString("ordDate"));
		data.setOutCustId(jsonList.getString("outCustId"));
		data.setTransAmt(jsonList.getString("transAmt"));
		data.setFee(jsonList.getString("fee"));
		data.setSubOrdId(jsonList.getString("subOrdId"));
		data.setSubOrdDate(jsonList.getString("subOrdDate"));
		data.setInCustId(jsonList.getString("inCustId"));
		data.setDivDetails(jsonList.getString("divDetails"));
		data.setFeeObjFlag(jsonList.getString("feeObjFlag"));
		data.setIsDefault(jsonList.getString("isDefault"));
		data.setIsUnFreeze(jsonList.getString("isUnFreeze"));
		data.setUnFreezeOrdId(jsonList.getString("unFreezeOrdId"));
		data.setFreezeTrxId(jsonList.getString("freezeTrxId"));
		data.setBgRetUrl(jsonList.getString("bgRetUrl"));
		data.setMerPriv(jsonList.getString("merPriv"));
		data.setReqExt(jsonList.getString("reqExt"));
		if(data.getDivDetails() == null ){
        	data.setDivDetails("");
        }
        if(data.getFeeObjFlag() == null ){
        	data.setFeeObjFlag("");
        }
        if(data.getUnFreezeOrdId() == null ){
        	data.setUnFreezeOrdId("");
        }
        if(data.getMerPriv() == null ){
        	data.setMerPriv("");
        }
        if(data.getReqExt() == null ){
        	data.setReqExt("");
        }
        if(data.getFreezeTrxId() == null ){
        	data.setFreezeTrxId("");
        }
		Map<String, String> retMap=new HashMap<String, String>();
		String chkValueResult=data.getVersion()+data.getCmdId()+data.getMerCustId()+data.getOrdId()+data.getOrdDate()+data.getOutCustId()
		+data.getTransAmt()+data.getFee()+data.getSubOrdId()+data.getSubOrdDate()+data.getInCustId()+data.getDivDetails()+data.getFeeObjFlag()
		+data.getIsDefault()+data.getIsUnFreeze()+data.getUnFreezeOrdId()+data.getFreezeTrxId()+data.getBgRetUrl()
		+data.getMerPriv()+data.getReqExt();
		System.out.println("huhuamin:"+chkValueResult);
		RsaHelper rsa = RsaHelper.getInstance();
		String 	chkValue=rsa.getChkValue(chkValueResult);
		Map<String,String> map=new HashMap<String, String>();
		map.put("Version",data.getVersion());
		map.put("CmdId", data.getCmdId());
		map.put("MerCustId", data.getMerCustId());
		map.put("OrdId", data.getOrdId());//系统生成订单号
		map.put("OrdDate", data.getOrdDate());//系统生成的订单日期
		map.put("OutCustId", data.getOutCustId());
		map.put("TransAmt", data.getTransAmt());
		map.put("Fee", data.getFee());//填写0.00
		map.put("SubOrdId", data.getSubOrdId());
		map.put("SubOrdDate", data.getSubOrdDate());
		map.put("InCustId", data.getInCustId());
		map.put("DivDetails", data.getDivDetails());//填写空字符
		map.put("FeeObjFlag", data.getFeeObjFlag());
		map.put("IsDefault", data.getIsDefault());
		// isUnFreeze 放款时同时接解冻，由IsUnFreeze判定，IsUnFreeze=N 不解冻  IsUnFreeze=Y 解冻  unFreezeOrdId,freezeTrxId必须
		map.put("IsUnFreeze", data.getIsUnFreeze());
		map.put("UnFreezeOrdId", data.getUnFreezeOrdId());
		map.put("FreezeTrxId", data.getFreezeTrxId());//必须 订单生成冻结金额时，产生的
		map.put("BgRetUrl", data.getBgRetUrl());
		map.put("MerPriv", data.getMerPriv());
		map.put("ReqExt", data.getReqExt());
		map.put("ChkValue", chkValue);
		try {
		String  queryBlanceResult=WebUtils.doPost(data.getUrl(),  map);
		if(null!=queryBlanceResult){
			JSONObject json= JSON.parseObject(queryBlanceResult);
			// 验证签名
			String retData=json.getString("CmdId")+json.getString("RespCode")+json.getString("MerCustId")+json.getString("OrdId")+json.getString("OrdDate")
			+json.getString("OutCustId")+json.getString("OutAcctId")+json.getString("TransAmt")+json.getString("Fee")+json.getString("InCustId")
			+json.getString("InAcctId")+json.getString("SubOrdId")+json.getString("SubOrdDate")+json.getString("FeeObjFlag")+json.getString("IsDefault")+json.getString("IsUnFreeze")
			+json.getString("UnFreezeOrdId")+json.getString("FreezeTrxId")+json.getString("BgRetUrl").replace("%3A", ":").replace("%2F", "/").trim()+json.getString("MerPriv")+json.getString("RespExt");
			//String retData=retMap.get("CmdId")+retMap.get("RespCode")+retMap.get("MerCustId")+retMap.get("UsrCustId")+retMap.get("AvlBal")+retMap.get("AcctBal")+retMap.get("FrzBal");
			String chkRetValue=json.getString("ChkValue").trim();
			retMap.put("RespCode", json.getString("RespCode"));// 汇付接口返回结果，000代表成功，其余代表失败
			retMap.put("RespDesc", json.getString("RespDesc"));// 返回错误信息
			System.out.println("放款接口返回码："+json.getString("RespCode")+",返回信息："+json.getString("RespDesc"));
			retMap.put("MerPriv", json.getString("MerPriv"));
			retMap.put("TransAmt", json.getString("TransAmt"));
			retMap.put("FullTransSeqNo", jsonList.getString("remark"));
			if (rsa.checkSignMsg(retData, chkRetValue)) {
				String recvOrdId = "RECV_ORD_ID_" + json.getString("OrdId");
				if (json.getString("RespCode").equals("000")) {
					retMap.put("CmdId", json.getString("CmdId"));
					retMap.put("RespCode", json.getString("RespCode"));
					retMap.put("RespDesc", json.getString("RespDesc"));
					retMap.put("MerCustId", json.getString("MerCustId"));
					retMap.put("OrdDate", json.getString("OrdDate"));
					retMap.put("OrdId", json.getString("OrdId"));
					retMap.put("OutCustId", json.getString("OutCustId"));
					retMap.put("OutAcctId", json.getString("OutAcctId"));
					retMap.put("TransAmt", json.getString("TransAmt"));
					retMap.put("Fee", json.getString("Fee"));		
					retMap.put("InCustId", json.getString("InCustId"));
					retMap.put("InAcctId", json.getString("InAcctId"));
					retMap.put("SubOrdId", json.getString("SubOrdId"));
					retMap.put("SubOrdDate", json.getString("SubOrdDate"));
					retMap.put("FeeObjFlag", json.getString("FeeObjFlag"));
					retMap.put("IsDefault", json.getString("IsDefault"));
					retMap.put("IsUnFreeze", json.getString("IsUnFreeze"));
					retMap.put("UnFreezeOrdId", json.getString("UnFreezeOrdId"));
					retMap.put("FreezeTrxId", json.getString("FreezeTrxId"));
					retMap.put("BgRetUrl", json.getString("BgRetUrl"));
					retMap.put("MerPriv", json.getString("MerPriv"));
					retMap.put("RespExt", json.getString("RespExt"));
					retMap.put("ChkValue", json.getString("ChkValue"));
					retMap.put("recvOrdId", recvOrdId);
					retMap.put("FreezeTrxId", json.getString("FreezeTrxId"));
				}else{
					resultMap.setSuccess(false);
				}
			} else {
				resultMap.setSuccess(false);
				System.out.println("creditAction数据被篡改！");
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.setData(retMap);
		return resultMap;
	}

	@Override
	public Map<String, String> crditBgRetUrl(HttpServletRequest request) {
		Map<String, String> map=new HashMap<String, String>();
		String cmdId = request.getParameter("CmdId");
		String respCode = request.getParameter("RespCode");
		String respDesc = request.getParameter("RespDesc");
		String merCustId = request.getParameter("MerCustId");
		String ordId=request.getParameter("OrdId");
		String ordDate=request.getParameter("OrdDate");
		String outCustId=request.getParameter("OutCustId");
		String outAcctId=request.getParameter("OutAcctId");
		String transAmt=request.getParameter("TransAmt");
		String fee=request.getParameter("Fee");
		String inCustId=request.getParameter("InCustId");
		String inAcctId=request.getParameter("InAcctId");
		String subOrdId=request.getParameter("SubOrdId");
		String subOrdDate=request.getParameter("SubOrdDate");
		String feeObjFlag=request.getParameter("FeeObjFlag");
		String isDefault=request.getParameter("IsDefault");
		String isUnFreeze=request.getParameter("IsUnFreeze");
		String unFreezeOrdId=request.getParameter("UnFreezeOrdId");
		String freezeTrxId=request.getParameter("FreezeTrxId");
		String bgRetUrl=request.getParameter("BgRetUrl").replace("%3A", ":").replace("%2F", "/").trim();
		String merPriv=request.getParameter("MerPriv");
		String respExt=request.getParameter("RespExt");
		String chkValue = request.getParameter("ChkValue");
		String trxId = request.getParameter("TrxId");
		if(null==feeObjFlag){
			feeObjFlag="";
		}else{
			feeObjFlag=feeObjFlag.trim();
		}
		if(null==respExt){
			respExt="";
		}else{
			respExt=respExt.trim();
		}
		if(null==trxId){
			trxId="";
		}else{
			trxId=trxId.trim();
		}
		String	recvOrdId = "RECV_ORD_ID_" + ordId;
		// 验证签名
		String retData = cmdId + respCode + merCustId + ordId + ordDate
				+ outCustId+outAcctId + transAmt + fee + inCustId+inAcctId+subOrdId+subOrdDate+feeObjFlag
				+isDefault+isUnFreeze+unFreezeOrdId+freezeTrxId+bgRetUrl+merPriv+respExt;
		RsaHelper rsa = RsaHelper.getInstance();
		System.err.println(retData + "=retData");
		map.put("RecvOrdId", recvOrdId);
		if (rsa.checkSignMsg(retData, chkValue)) {
			System.out.println("汇付发送来的数据，未篡改！");
			map.put("CmdId", cmdId);
			map.put("RespCode", respCode);
			map.put("RespDesc", respDesc);
			map.put("MerCustId", merCustId);
			map.put("OrdId", ordId);
			map.put("OrdDate", ordDate);
			map.put("OutCustId", outCustId);
			map.put("OutAcctId", outAcctId);
			map.put("Fee", fee);
			map.put("InCustId", inCustId);
			map.put("InAcctId", inAcctId);
			map.put("SubOrdId", subOrdId);
			map.put("SubOrdDate", subOrdDate);
			map.put("FeeObjFlag", feeObjFlag);
			map.put("IsDefault", isDefault);
			map.put("IsUnFreeze", isUnFreeze);
			map.put("UnFreezeOrdId", unFreezeOrdId);
			map.put("FreezeTrxId", freezeTrxId);
			map.put("BgRetUrl", bgRetUrl);
			map.put("MerPriv", merPriv);
			map.put("RespExt", respExt);
			map.put("ChkValue", chkValue);
			map.put("RecvOrdId", recvOrdId);			
		} else {
			System.out.println("数据被篡改！");
		}
		return map;
	
	}
	
	@Override
	public HuiFuData CreditAssign(String jsonList){
		HuiFuData data = new HuiFuData();
		JSONObject requestList = JSON.parseObject(jsonList);
		data.setUrl(requestList.getString("url"));
		data.setVersion(requestList.getString("version"));
        data.setCmdId(requestList.getString("cmdId"));
        data.setMerCustId(requestList.getString("merCustId"));
        data.setSellCustId(requestList.getString("sellCustId"));
        data.setCreditAmt(requestList.getString("creditAmt"));
        data.setCreditDealAmt(requestList.getString("creditDealAmt"));
        data.setBidDetails(requestList.getString("bidDetails"));
        data.setFee(requestList.getString("fee"));
        data.setDivDetails(requestList.getString("divDetails"));
        data.setBuyCustId(requestList.getString("buyCustId"));
        data.setOrdId(requestList.getString("ordId"));
        data.setOrdDate(requestList.getString("ordDate"));
        data.setRetUrl(requestList.getString("retUrl"));
        data.setBgRetUrl(requestList.getString("bgRetUrl"));
        data.setRespExt("");
        data.setMerPriv(requestList.getString("merPriv"));
        if(data.getBidDetails() == null){
        	data.setBidDetails("");
        }
        if(data.getDivDetails() == null){
        	data.setDivDetails("");
        }
        if(data.getReqExt() == null || "".equals(data.getReqExt())){
        	data.setRespExt("");
        }
        if(data.getMerPriv() == null || "".equals(data.getMerPriv())){
        	data.setMerPriv("");
        }
        
        StringBuffer sf=new StringBuffer();
		sf.append(data.getVersion()).append(data.getCmdId()).append(data.getMerCustId()).append(data.getSellCustId()).append(data.getCreditAmt())
		.append(data.getCreditDealAmt()).append(data.getBidDetails()).append(data.getFee()).append(data.getDivDetails()).append(data.getBuyCustId()).append(data.getOrdId())
		.append(data.getOrdDate()).append(data.getRetUrl()).append(data.getBgRetUrl()).append(data.getMerPriv()).append("");
		String chkValueResult=sf.toString();
		System.out.println(chkValueResult);
		RsaHelper rsa = RsaHelper.getInstance();
		String chkValue=rsa.getChkValue(chkValueResult);
		data.setChkValue(chkValue);
		System.out.println(chkValue.length());
		
		return data;
	}
	

	@Override
	public PlainResult<Map> CreditAssign(HuiFuData data) {
		// TODO Auto-generated method stub
		
		/*借款人明细*/
		BorrowerDetail borrowerDetail=new BorrowerDetail();
		borrowerDetail.setBorrowerCustId(data.getBorrowerCustId());
		borrowerDetail.setBorrowerCreditAmt(data.getCreditAmt());
		borrowerDetail.setPrinAmt(data.getPrinAmt());
		borrowerDetail.setProId(data.getProId());
		List<BorrowerDetail> listborrd = new ArrayList<BorrowerDetail>();
		listborrd.add(borrowerDetail);
		/*债券转让明细*/
		BidDetail bidDetail = new BidDetail();
		bidDetail.setBidOrdId(data.getBidOrdId());
		bidDetail.setBidOrdDate(data.getBidOrdDate());
		bidDetail.setBidCreditAmt(data.getCreditAmt());
		bidDetail.setBorrowerDetails(listborrd);
		List<BidDetail> lstBd = new ArrayList<BidDetail>();
		lstBd.add(bidDetail);
		Map<String, List<BidDetail>> bidDetailMap = new HashMap<String, List<BidDetail>>();
		bidDetailMap.put("BidDetails", lstBd);
		List<FeeDetail> list=new ArrayList<FeeDetail>();
		/*分账账户明细，只有平台*/
		FeeDetail feeDetail=new FeeDetail();
		feeDetail.setDivAcctId(SystemProperties.getChinaPrnString("ServFeeAcctId"));
//		feeDetail.setDivCustId(data.getMerCustId());
		feeDetail.setDivAmt(data.getFee());
		list.add(feeDetail);
		String divDetails=  JSON.toJSONString(list).replaceAll("divAcctId", "DivAcctId").replaceAll("divAmt", "DivAmt");
		String bidDetails=JSON.toJSONString(bidDetailMap).replaceAll("bid", "Bid").replaceAll("borrower", "Borrower").replaceAll("prinAmt", "PrinAmt");
		System.out.println(divDetails+"=divDetails");
		System.out.println(bidDetails+"=bidDetails");
		//String chkValueResult=version+cmdId+merCustId+sellCustId+creditAmt+creditDealAmt+bidDetails+fee+divDetails+buyCustId+ordId+ordDate+retUrl+bgRetUrl+merPriv.trim()+reqExt;
	    StringBuffer sf=new StringBuffer();
		sf.append(data.getVersion()).append(data.getCmdId()).append(data.getMerCustId()).append(data.getSellCustId()).append(data.getCreditAmt())
		.append(data.getCreditDealAmt()).append(bidDetails).append(data.getFee()).append(divDetails).append(data.getBuyCustId()).append(data.getOrdId())
		.append(data.getOrdDate()).append(data.getRetUrl()).append(data.getBgRetUrl()).append(data.getMerPriv().trim()).append(data.getReqExt());
		String chkValueResult=sf.toString();
		RsaHelper rsa = RsaHelper.getInstance();
		String chkValue=rsa.getChkValue(chkValueResult);
		System.out.println(chkValue.length());
		/**
		 * data中还需要传入prinAmt：已还本金 (借款人还款金额中所占本金的部分)
		 * 				  BidOrdId：原投标ID
		 * 				  BidOrdDate:原投标如期（YYYYMMDD）
		 */		
		
		PlainResult<Map> plr=new PlainResult<Map>();
		Map<String,String> map=new HashMap<String, String>();
		map.put("url", data.getUrl());
		map.put("version", data.getVersion());
		map.put("cmdId", data.getCmdId());
		map.put("merCustId", data.getMerCustId());
		map.put("sellCustId", data.getSellCustId());
		map.put("creditAmt", data.getCreditAmt());
		map.put("creditDealAmt", data.getCreditDealAmt());
		map.put("bidDetails", bidDetails);
		map.put("fee", data.getFee());
		map.put("divDetails", divDetails);
		map.put("buyCustId", data.getBuyCustId());
		map.put("ordId", data.getOrdId());
		map.put("ordDate", data.getOrdDate());
		map.put("retUrl", data.getRetUrl());
		map.put("bgRetUrl", data.getBgRetUrl());
		map.put("merPriv", data.getMerPriv());
		map.put("reqExt", data.getReqExt());
		map.put("chkValue", chkValue);
		plr.setData(map);
		return plr;
	}
	
	@Override
	public HuiFuData CreditAssignResult(HttpServletRequest request){
		
		HuiFuData data = new HuiFuData();
		
		String cmdId = request.getParameter("CmdId").trim();
		String respCode = request.getParameter("RespCode").trim();
		String respDesc = request.getParameter("RespDesc").trim();
		String merCustId = request.getParameter("MerCustId").trim();
		String sellCustId = request.getParameter("SellCustId").trim();
		String creditAmt = request.getParameter("CreditAmt").trim();
		String creditDealAmt = request.getParameter("CreditDealAmt").trim();
		String fee = request.getParameter("Fee");
		String buyCustId = request.getParameter("BuyCustId");
		String ordId = request.getParameter("OrdId").trim();
		String  recvOrdId = "RECV_ORD_ID_" + ordId;
		String ordDate = request.getParameter("OrdDate");
		String retUrl = request.getParameter("RetUrl");
		if(null==retUrl || "".equals(retUrl)){
			retUrl="";
		}else{
			retUrl=retUrl.replace("%3A", ":").replace("%2F", "/").trim();
		}
		String bgRetUrl = request.getParameter("BgRetUrl");
		if(bgRetUrl == null || "".equals(bgRetUrl)){
			bgRetUrl = "";
		}else{
			bgRetUrl = bgRetUrl.replace("%3A", ":").replace("%2F", "/").trim();
		}
		String merPriv = request.getParameter("MerPriv");
		if(merPriv == null || "".equals(merPriv)){
			merPriv = "";
		}else{
			merPriv = merPriv.replace("%2C", ",").trim().trim();
		}
		String respExt = request.getParameter("RespExt"); 
		if(respExt == null || "".equals(respExt)){
			respExt = "";
		}else{
			respExt = respExt.trim();
		}
		String chkValue = request.getParameter("ChkValue").trim();
		System.out.println(chkValue);
		
		StringBuffer sf=new StringBuffer();
		sf.append(cmdId).append(respCode).append(merCustId).append(sellCustId).append(creditAmt).append(creditDealAmt).append(fee)
		.append(buyCustId).append(ordId).append(ordDate).append(retUrl).append(bgRetUrl).append(merPriv).append(respExt);
		String retData = sf.toString();
		
		RsaHelper rsa = RsaHelper.getInstance();
		if(rsa.checkSignMsg(retData, chkValue)){
			System.out.println("汇付发送来的数据，未篡改！");
			data.setCmdId(cmdId);
			data.setRespCode(respCode);
			data.setRespDesc(respDesc);
			data.setMerCustId(merCustId);
			data.setSellCustId(sellCustId);
			data.setCreditAmt(creditAmt);
			data.setCreditDealAmt(creditDealAmt);
			data.setFee(fee);
			data.setBuyCustId(buyCustId);
			data.setOrdId(ordId);
			data.setOrdDate(ordDate);
			data.setRecvOrdId(recvOrdId);
			data.setRetUrl(retUrl);
			data.setBgRetUrl(bgRetUrl);
			data.setMerPriv(merPriv);
			data.setRespExt(respExt);;
			data.setChkValue(chkValue);
		}else{
			System.out.println("数据被篡改");
		}
		
		return data;
	}
	@Override
	public Map<String, String> cashAudit(HuiFuData data) {
		Map<String, String> retMap=new HashMap<String, String>();
		String chkValueResult = data.getVersion()+data.getCmdId()+data.getMerCustId()+data.getOrdId()+data.getUsrCustId()+data.getTransAmt()
				+data.getAuditFlag()+data.getRetUrl()+data.getBgRetUrl()+data.getMerPriv();
		RsaHelper rsa = RsaHelper.getInstance();
		String 	chkValue=rsa.getChkValue(chkValueResult);
		Map<String,String> map=new HashMap<String, String>();
		map.put("Version",data.getVersion());
		map.put("CmdId", data.getCmdId());
		map.put("MerCustId", data.getMerCustId());
		map.put("OrdId", data.getOrdId());//系统生成订单号
		map.put("UsrCustId", data.getUsrCustId());
		map.put("TransAmt", data.getTransAmt());
		map.put("AuditFlag", data.getAuditFlag());//填写0.00
		map.put("RetUrl", data.getRetUrl());
		map.put("BgRetUrl", data.getBgRetUrl());
		map.put("MerPriv", data.getMerPriv());
		map.put("ChkValue", chkValue);
		try {
		String  queryBlanceResult=WebUtils.doPost(data.getUrl(),  map);
		if(null!=queryBlanceResult){
			JSONObject json= JSON.parseObject(queryBlanceResult);
			String retData=json.getString("CmdId")+json.getString("RespCode")+json.getString("MerCustId")+json.getString("OrdId")
			+json.getString("UsrCustId")+json.getString("TransAmt")+json.getString("OpenAcctId")+json.getString("OpenBankId")
			+json.getString("AuditFlag")+json.getString("RetUrl")
			+json.getString("BgRetUrl").replace("%3A", ":").replace("%2F", "/").trim()+json.getString("MerPriv")+json.getString("RespExt");
			//String retData=retMap.get("CmdId")+retMap.get("RespCode")+retMap.get("MerCustId")+retMap.get("UsrCustId")+retMap.get("AvlBal")+retMap.get("AcctBal")+retMap.get("FrzBal");
			System.out.println("retData="+retData);
			String chkRetValue=json.getString("ChkValue").trim();
			System.out.println("chkRetValue="+chkRetValue);
			retMap.put("RespCode", json.getString("RespCode"));// 汇付接口返回结果，000代表成功，其余代表失败
			retMap.put("RespDesc", json.getString("RespDesc"));// 返回错误信息
			if (rsa.checkSignMsg(retData, chkRetValue)) {
				System.out.println("汇付发送来的数据，未篡改！");
				String recvOrdId = "RECV_ORD_ID_" + json.getString("OrdId");
				System.out.println("recvOrdId="+recvOrdId);
				if (json.getString("RespCode").equals("000")) {
					retMap.put("CmdId", json.getString("CmdId"));
					retMap.put("RespCode", json.getString("RespCode"));
					retMap.put("RespDesc", json.getString("RespDesc"));
					retMap.put("MerCustId", json.getString("MerCustId"));
					retMap.put("OrdDate", json.getString("OrdDate"));
					retMap.put("OrdId", json.getString("OrdId"));
					retMap.put("OutCustId", json.getString("OutCustId"));
					retMap.put("OutAcctId", json.getString("OutAcctId"));
					retMap.put("TransAmt", json.getString("TransAmt"));
					retMap.put("Fee", json.getString("Fee"));		
					retMap.put("InCustId", json.getString("InCustId"));
					retMap.put("InAcctId", json.getString("InAcctId"));
					retMap.put("SubOrdId", json.getString("SubOrdId"));
					retMap.put("SubOrdDate", json.getString("SubOrdDate"));
					retMap.put("FeeObjFlag", json.getString("FeeObjFlag"));
					retMap.put("IsDefault", json.getString("IsDefault"));
					retMap.put("IsUnFreeze", json.getString("IsUnFreeze"));
					retMap.put("UnFreezeOrdId", json.getString("UnFreezeOrdId"));
					retMap.put("FreezeTrxId", json.getString("FreezeTrxId"));
					retMap.put("BgRetUrl", json.getString("BgRetUrl"));
					retMap.put("MerPriv", json.getString("MerPriv"));
					retMap.put("RespExt", json.getString("RespExt"));
					retMap.put("ChkValue", json.getString("ChkValue"));
					retMap.put("recvOrdId", recvOrdId);
					retMap.put("FreezeTrxId", json.getString("FreezeTrxId"));
				}
			} else {
				System.out.println("creditAction数据被篡改！");
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retMap;
	}


	@Override
	public HuiFuData cashAuditResult(HttpServletRequest request) {
		
		HuiFuData data = new HuiFuData();
		String cmdId = request.getParameter("CmdId").trim();
		String respCode = request.getParameter("RespCode").trim();
		String respDesc = request.getParameter("RespDesc").trim();
		String merCustId = request.getParameter("MerCustId").trim();
		String ordId = request.getParameter("OrdId").trim();
		String usrCustId = request.getParameter("UsrCustId").trim();
		String auditFlag = request.getParameter("AuditFlag").trim();
		String openAcctId = request.getParameter("OpenAcctId");//可选
		String openBankId = request.getParameter("OpenBankId");//可选
		String transAmt = request.getParameter("TransAmt").trim();
		String feeAmt = request.getParameter("FeeAmt");
		String feeCustId = request.getParameter("FeeCustId");
		String feeAcctId = request.getParameter("FeeAcctId");
		String retUrl = request.getParameter("RetUrl");//可选
		String bgRetUrl = request.getParameter("BgRetUrl").replace("%3A", ":")
				.replace("%2F", "/").trim();
		String merPriv = request.getParameter("MerPriv");//可选
		String chkValue = request.getParameter("ChkValue").trim();
		String recvOrdId = "RECV_ORD_ID_" + ordId;
		if(null==openAcctId){
			openAcctId="";
		}else{
			openAcctId=openAcctId.trim();
		}
		if(null==openBankId){
			openBankId="";
		}else{
			openBankId=openBankId.trim();
		}
		if(null==retUrl){
			retUrl="";
		}else{
			retUrl=retUrl.trim();
		}
		if(null==merPriv){
			merPriv="";
		}else{
			merPriv=merPriv.trim();
		}
		
		System.out.println("提现复审返回...");
		StringBuffer sf = new StringBuffer();
		sf.append(cmdId).append(respCode).append(merCustId).append(ordId)
		.append(usrCustId).append(transAmt).append(openAcctId).append(openBankId)
		.append(auditFlag).append(retUrl).append(bgRetUrl).append(merPriv);
		String retData = sf.toString();
		RsaHelper rsa = RsaHelper.getInstance();
		System.err.println(retData + "=retData");
		data.setRespCode(respCode);
		data.setRespDesc(respDesc);
		data.setRecvOrdId(recvOrdId);
		if (rsa.checkSignMsg(retData, chkValue)) {
			System.out.println("汇付发送来的绑卡数据，未篡改！");
			data.setCmdId(cmdId);
			data.setMerCustId(merCustId);
			data.setTransAmt(transAmt);
			data.setAuditFlag(auditFlag);
			data.setUsrCustId(usrCustId);
			data.setTrxId(ordId);
			data.setFeeAmt(feeAmt);
			data.setFeeCustId(feeCustId);
			data.setFeeAcctId(feeAcctId);
			data.setRetUrl(bgRetUrl);
			data.setMerPriv(merPriv);
			data.setOpenAcctId(openAcctId);
			data.setOpenBankId(openBankId);
		} else {
			System.out.println("数据被篡改！");
		}
		return data;
	}
	
	/**
	 * 投标撤销时，ordid和FreezeTrxId和orddate是投标时的ordid和FreezeTrxId和OrdDate
	 * 取消投资时，汇付系统自动解冻。
	 */
	@Override
	public Map<String,String> withdrawInvest(HuiFuData data) {
		String chkValueResult = data.getVersion() + data.getCmdId() +  data.getMerCustId() + data.getOrdId().trim()
				+ data.getOrdDate().trim() + data.getTransAmt().trim() + data.getUsrCustId().trim()
				+ data.getIsUnFreeze() + data.getUnFreezeOrdId() + data.getFreezeTrxId()
				+ data.getRetUrl() + data.getBgRetUrl().trim() + data.getMerPriv().trim()
				+ data.getReqExt();
		RsaHelper rsa = RsaHelper.getInstance();
		String chkValue = rsa.getChkValue(chkValueResult);// 签名
		System.out.println("chkValue=" + chkValue);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", data.getVersion());
		map.put("CmdId", data.getCmdId());
		map.put("MerCustId", data.getMerCustId());
		map.put("OrdId", data.getOrdId());
		map.put("OrdDate", data.getOrdDate());
		map.put("TransAmt", data.getTransAmt());
		map.put("UsrCustId", data.getUsrCustId());
		map.put("IsUnFreeze", data.getIsUnFreeze());
		map.put("UnFreezeOrdId", data.getUnFreezeOrdId());
		map.put("FreezeTrxId", data.getFreezeTrxId());
		map.put("RetUrl", data.getRetUrl());
		map.put("BgRetUrl", data.getBgRetUrl());
		map.put("MerPriv", data.getMerPriv());
		map.put("ReqExt", data.getReqExt());
		map.put("ChkValue", chkValue);
		Map<String, String> retMap = new HashMap<String, String>();
		try {
			String queryBlanceResult = WebUtils.doPost(data.getUrl(), map);
			if (null != queryBlanceResult) {
				JSONObject json = JSON.parseObject(queryBlanceResult);
				String retData = json.getString("CmdId")
						+ json.getString("RespCode")
						+ json.getString("MerCustId")
						+ json.getString("OrdId")
						+ json.getString("OrdDate")
						+ json.getString("TransAmt")
						+ json.getString("UsrCustId")
						+ json.getString("IsUnFreeze")
						+ json.getString("UnFreezeOrdId")
						+ json.getString("FreezeTrxId")
						+ json.getString("RetUrl").replace("%3A", ":")
								.replace("%2F", "/").trim()
						+ json.getString("BgRetUrl").replace("%3A", ":")
								.replace("%2F", "/").trim()
						+ json.getString("MerPriv") + json.getString("RespExt");
				System.out.println("retData=" + retData);
				String chkRetValue = json.getString("ChkValue").trim();
				System.out.println("chkRetValue=" + chkRetValue);
				if (rsa.checkSignMsg(retData, chkRetValue)) {
					System.out.println("汇付发送来的数据，未篡改！");
					String recvOrdId = "RECV_ORD_ID_" + json.getString("OrdId");
					System.out.println("recvOrdId=" + recvOrdId);
					if (json.getString("RespCode").equals("000")) {
						retMap.put("RespCode", json.getString("RespCode"));
						retMap.put("RespDesc", json.getString("RespDesc"));
					}
				} else {
					System.out.println("creditAction数据被篡改！");
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retMap;
	}

	@Override
	public HuiFuData withdrawInvestResult(
			HttpServletRequest request) {
		HuiFuData data = new HuiFuData();
		String cmdId = request.getParameter("CmdId").trim();
		String respCode = request.getParameter("RespCode").trim();
		String respDesc = request.getParameter("RespDesc").trim();
		String merCustId = request.getParameter("MerCustId").trim();
		String ordId = request.getParameter("OrdId").trim();
		String recvOrdId = "RECV_ORD_ID_" + ordId;
		String ordDate = request.getParameter("OrdDate").trim();
		String transAmt = request.getParameter("TransAmt").trim();
		String usrCustId = request.getParameter("UsrCustId").trim();
		String isUnFreeze = request.getParameter("IsUnFreeze").trim();
		String unFreezeOrdId = request.getParameter("UnFreezeOrdId").trim();
		String freezeTrxId = request.getParameter("FreezeTrxId");
		if (null == freezeTrxId) {
			freezeTrxId = "";
		} else {
			freezeTrxId = freezeTrxId.trim();
		}
		String retUrl = request.getParameter("RetUrl");
		if (null == retUrl) {
			retUrl = "";
		} else {
			retUrl = retUrl.replace("%3A", ":").replace("%2F", "/").trim();
		}
		String bgRetUrl = request.getParameter("BgRetUrl").replace("%3A", ":")
				.replace("%2F", "/").trim();
		String merPriv = request.getParameter("MerPriv").trim();
		String respExt = request.getParameter("RespExt").trim();
		String chkValue = request.getParameter("ChkValue").trim();
		StringBuffer sf = new StringBuffer();
		sf.append(cmdId).append(respCode).append(merCustId).append(ordId)
				.append(ordDate).append(transAmt).append(usrCustId)
				.append(isUnFreeze).append(unFreezeOrdId).append(freezeTrxId)
				.append(retUrl).append(bgRetUrl).append(merPriv)
				.append(respExt);
		RsaHelper rsa = RsaHelper.getInstance();
		System.out.println(sf.toString() + "=sf.toString()");
		if (rsa.checkSignMsg(sf.toString(), chkValue)) {
			System.out.println("汇付发送来的数据，未篡改！");
			data.setCmdId(cmdId);
			data.setRespCode(respCode);
			data.setRespDesc(respDesc);
			data.setMerCustId(merCustId);
			data.setOrdId(ordId);
			data.setRecvOrdId(recvOrdId);
			data.setOrdDate(ordDate);
			data.setTransAmt(transAmt);
			data.setUsrCustId(usrCustId);
			data.setIsUnFreeze(isUnFreeze);
			data.setUnFreezeOrdId(unFreezeOrdId);
			data.setFreezeTrxId(freezeTrxId);
			data.setRetUrl(retUrl);
			data.setBgRetUrl(bgRetUrl);
			data.setMerPriv(merPriv);
			data.setRespExt(respExt);
			data.setChkValue(chkValue);
		} else {
			System.out.println("数据被篡改！");
		}
		return data;
	}
	
	@Override
	public HuiFuData investMessageLog(HuiFuData data){
		StringBuffer sf = new StringBuffer();
		sf.append(data.getVersion())
		.append(data.getCmdId())
		.append(data.getMerCustId())
		.append(data.getProId())
		.append("6000060000891307")
		.append("1000")
		.append("0.11")
		.append("03")
		.append("20150508113020")
		.append("20150605114021")
		.append("1027.50")
		.append("20150508")
		.append("")
		.append("")
		.append("1200")
		.append("http://36.33.24.109:8989/webNotify/investMessageLogResultBgUrl.json")
		.append("")
		.append("");
		String chkValueResult = sf.toString();
		RsaHelper rsa = RsaHelper.getInstance();
		String chkValue = rsa.getChkValue(chkValueResult);
		Map<String, String> map = new HashMap<String, String>();
		String url = "http://mertest.chinapnr.com/muser/publicRequests";
		map.put("Version",data.getVersion());
		map.put("CmdId", data.getCmdId());
		map.put("MerCustId", data.getMerCustId());
		map.put("ProId", data.getProId());
		map.put("BorrCustId", "6000060000891307");
		map.put("BorrTotAmt", "1000.00");
		map.put("YearRate","0.11");
		map.put("RetType","03");
		map.put("BidStartDate","20150508113020");
		map.put("BidEndDate","20150605114021");
		map.put("RetAmt", "1027.50");
		map.put("RetDate", "20150508");
		map.put("GuarCompId", "");
		map.put("GuarAmt", "");
		map.put("ProArea", "1200");
		map.put("BgRetUrl", "http://36.33.24.109:8989/webNotify/investMessageLogResultBgUrl.json");
		map.put("MerPriv", "");
		map.put("ReqExt", "");
		map.put("ChkValue", chkValue);
		String  investMessageLogResult="";
		try {
			investMessageLogResult=WebUtils.doPost(url,  map);
			System.out.println(investMessageLogResult);
		  if(null!=investMessageLogResult){
				JSONObject json= JSON.parseObject(investMessageLogResult);
				String retData=json.getString("CmdId")
						+json.getString("RespCode")
						+json.getString("RespDesc")
						+json.getString("MerCustId")
						+json.getString("ProId")
						+json.getString("BorrCustId")
						+json.getString("BorrTotAmt")
						+json.getString("GuarCompId")
						+json.getString("GuarAmt")
						+json.getString("ProArea")
						+json.getString("BgRetUrl").replace("%3A", ":")
						.replace("%2F", "/").trim()
						+json.getString("MerPriv").replace("%3A", ":")
						.replace("%2F", "/").trim()
						+json.getString("RespExt").replace("%3A", ":")
						.replace("%2F", "/").trim();
				System.out.println("retData="+retData.replaceAll(",", ""));
				String chkRetValue=json.getString("ChkValue").trim();
				System.out.println("chkRetValue="+chkRetValue);
				if (rsa.checkSignMsg(retData, chkRetValue)) {
					System.out.println("数据没有被串改");
				} else {
					System.out.println("数据被篡改！");
				}			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	@Override
	public Map<String, String> transferHuiFu(HuiFuData  data){
		Map<String, String> retMap=new HashMap<String, String>();
		RsaHelper rsa = RsaHelper.getInstance();
		String url = "http://mertest.chinapnr.com/muser/publicRequests";
		StringBuffer sf = new StringBuffer();
		sf.append(data.getVersion()).append(data.getCmdId()).append(data.getOrdId()).append(
				data.getOutCustId()).append(data.getOutAcctId()).append(data.getTransAmt()).append(
				data.getInCustId()).append(data.getInAcctId()).append(data.getRetUrl()).append(
				data.getBgRetUrl()).append(data.getMerPriv());
		String chkValueResult = sf.toString();
		String chkValue = rsa.getChkValue(chkValueResult);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Version",data.getVersion());
		map.put("CmdId", data.getCmdId());
		map.put("OrdId", data.getOrdId());//系统生成订单号
		map.put("OutCustId", data.getOutCustId());
		map.put("OutAcctId", data.getOutAcctId());
		map.put("TransAmt", data.getTransAmt());
		map.put("InCustId", data.getInCustId());
		map.put("InAcctId", data.getInAcctId());
		map.put("BgRetUrl", data.getBgRetUrl());
		map.put("MerPriv", data.getMerPriv());
		map.put("ChkValue", chkValue);
		try {
			String queryBlanceResult = WebUtils.doPost(url, map);
			System.out.println(queryBlanceResult);
			if (null != queryBlanceResult) {
				JSONObject json = JSON.parseObject(queryBlanceResult);
				// String retData = cmdId + respCode + merCustId +
				// ordId+ordDate+trxId+retUrl + bgRetUrl + merPriv;
				StringBuffer sb = new StringBuffer();
				sb.append(json.getString("CmdId")).append(
						json.getString("RespCode")).append(
						json.getString("OrdId")).append(
						json.getString("OutCustId")).append(
						json.getString("OutAcctId")).append(
						json.getString("TransAmt")).append(
						json.getString("InCustId")).append(
						json.getString("InAcctId")).append(
						json.getString("RetUrl")).append(
						json.getString("BgRetUrl")).append(
						json.getString("MerPriv"));
				// String
				// retData=map.get("CmdId")+map.get("RespCode")+map.get("MerCustId")+map.get("UsrCustId")+map.get("AvlBal")+map.get("AcctBal")+map.get("FrzBal");
				String retData = sb.toString();
				System.out.println("retData=" + sb.toString());
				String chkRetValue = json.getString("ChkValue").trim();
				System.out.println("chkRetValue=" + chkRetValue);
				if (rsa.checkSignMsg(retData, chkRetValue)) {
					System.out.println("汇付发送来的数据，未篡改！");
					retMap.put("RespCode", json.getString("RespCode"));// 汇付接口返回结果，000代表成功，其余代表失败
					retMap.put("RespDesc", json.getString("RespDesc"));// 返回错误信息
					if (json.getString("RespCode").equals("000")) {
						retMap.put("CmdId", json.getString("CmdId"));
						retMap.put("RespCode", json.getString("RespCode"));
						retMap.put("RespDesc", json.getString("RespDesc"));
						retMap.put("OrdId", json.getString("OrdId"));
						retMap.put("OutCustId", json.getString("OutCustId"));
						retMap.put("OutAcctId", json.getString("OutAcctId"));
						retMap.put("TransAmt", json.getString("TransAmt"));
						retMap.put("InCustId", json.getString("InCustId"));
						retMap.put("InAcctId", json.getString("InAcctId"));
						retMap.put("RetUrl", json.getString("RetUrl"));
						retMap.put("BgRetUrl", json.getString("BgRetUrl"));
						retMap.put("MerPriv", json.getString("MerPriv"));
						retMap.put("ChkValue", json.getString("ChkValue"));
						String recvOrdId = "RECV_ORD_ID_"
								+ json.getString("OrdId");
						System.out.println("recvOrdId=" + recvOrdId);
						retMap.put("RecvOrdId", recvOrdId);
					}
				} else {
					System.out.println("数据被篡改！");
				}

			}
			System.out.println("retMap=" + retMap);
			System.out.println("queryBlanceResult=" + queryBlanceResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
}
