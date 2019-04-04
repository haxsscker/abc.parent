package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util;

import java.util.Map;

import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.HttpSendResult;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SimpleHttpsClient;

public class HttpUtil {

	public static String postRequest(String url, Map<String, String> params, String characterSet) {
		SimpleHttpsClient arg10 = new SimpleHttpsClient();
		HttpSendResult arg11 = arg10.postRequest(url, params, 3000, characterSet);
		String arg12 = arg11.getResponseBody();
		return arg12;
	}
}
