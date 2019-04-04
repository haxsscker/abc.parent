package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.http;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class WebUtils {

	// 如果关注性能问题可以考虑使用HttpClientConnectionManager
	public static String doPost(String url ,Map<String, String> params)
			throws ClientProtocolException, IOException {
		String result = null;
		List<NameValuePair> nvps = HttpClientHandler.buildNameValuePair(params);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		EntityBuilder builder = EntityBuilder.create();
		try {
			HttpPost httpPost = new HttpPost(url);
			builder.setParameters(nvps);
			httpPost.setEntity(builder.build());
			CloseableHttpResponse response = httpclient.execute(httpPost);

			try {
				HttpEntity entity = response.getEntity();
				if (response.getStatusLine().getReasonPhrase().equals("OK")
						&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return result;
	}
	
	
//  public static String sendHttp(String url, Map<String, String> parameters) throws Exception  {
//		String outStr="";
//		try {
//			String charSet="UTF-8";
//			String timeOut ="200000";//自行配置
//			outStr = HttpClientHandler.doHttp(url,charSet,parameters, timeOut);
//			if(outStr==null){
//				throw new Exception("请求接口失败!");
//			}
//			System.out.println("outStr="+outStr);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception("请求接口失败!");
//		}
//		return outStr;
//	}
}
