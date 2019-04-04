package com.autoserve.abc.service.message.identity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.impl.cash.Common;
import com.autoserve.abc.service.http.AbcHttpCallService;
import com.autoserve.abc.service.http.AbcHttpCallServiceImpl;
import com.autoserve.abc.service.util.RsaHelper;
import com.autoserve.abc.service.util.SystemGetPropeties;

public class DmIdentityServiceImpl implements SendIdentityService {

	@Resource
    private final AbcHttpCallService abcHttpCallService = new AbcHttpCallServiceImpl();
	
	@Override
	public Map<String, String> sendIdentity(String realName, String identityNo) {
		
		String privatekey = SystemGetPropeties.getStrString("privatekey");
		String PlatformMoneymoremore = SystemGetPropeties.getStrString("PlatformMoneymoremore");//平台乾多多标识
		
		String NotifyURL = SystemGetPropeties.getStrString("notifyurlprefix")
                + SystemGetPropeties.getStrString("identityNotifyURL");
		
//		String NotifyURL = "http://test.xh99d.com";
		String submitUrl = SystemGetPropeties.getStrString("submiturlprefix") + SystemGetPropeties.getStrString("identitySubmitUrl");
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("PlatformMoneymoremore", PlatformMoneymoremore);
		paramsMap.put("NotifyURL", NotifyURL);
		
		List<IdentityJsonList> list = new ArrayList<IdentityJsonList>();
		IdentityJsonList ido = new IdentityJsonList();
		ido.setRealName(realName);
		ido.setIdentificationNo(identityNo);
		list.add(ido);
		String json = Common.JSONEncode(list);
		
		paramsMap.put("IdentityJsonList", Common.UrlEncoder(json, "utf-8"));
		
		//签名
		RsaHelper rsa = RsaHelper.getInstance();
		String dataStr = PlatformMoneymoremore + json + NotifyURL;
        String SignInfo = rsa.signData(dataStr, privatekey);
        paramsMap.put("SignInfo", SignInfo);
        
        //发送并解析
        String resultStr = abcHttpCallService.sendPost(submitUrl, paramsMap).getData();
        Map<String, String> returnDataMap = (Map<String, String>) JSON.parse(resultStr.toString());
        System.out.println("DmIdentityServiceImpl:returnDataMap="+returnDataMap.toString());
		
		return returnDataMap;
	}
}
