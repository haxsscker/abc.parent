package com.autoserve.abc.service.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.http.core.AbcHttpPostUtil;
import com.autoserve.abc.service.http.core.HttpGetUtil;
import com.autoserve.abc.service.http.core.HttpResponseHandler;
import com.autoserve.abc.service.http.core.HttpUrlConnectionUtil;

/**
 * abc http 同步类请求调用
 * 
 * @author shensheng.ss 2014年4月25日 下午12:00:41
 */
public class AbcHttpCallServiceImpl implements AbcHttpCallService {

    private static final ResponseHandler<PlainResult<String>> defaultResponseHandler = new HttpResponseHandler();

    private static final Logger                               log                    = LoggerFactory
                                                                                             .getLogger(AbcHttpCallServiceImpl.class);

    @Override
    public PlainResult<String> httpGet(String requestUri) {
        return HttpGetUtil.execute(requestUri, defaultResponseHandler);
    }

    @Override
    public PlainResult<String> httpGet(String requestUri, Map<String, String> params) {
        return HttpGetUtil.execute(requestUri, params, defaultResponseHandler);
    }

    @Override
    public PlainResult<String> httpPost(String requestUri) {
        return AbcHttpPostUtil.execute(requestUri, null, defaultResponseHandler);
    }

    @Override
    public PlainResult<String> httpPost(String requestUri, Map<String, String> params) {
        return AbcHttpPostUtil.execute(requestUri, params, defaultResponseHandler);
    }

    @Override
    public PlainResult<String> urlConnectionPost(String url, String params) {
        PlainResult<String> result = new PlainResult<String>();
        try {
            String rt = HttpUrlConnectionUtil.doPost(url, params, 5000);
            result.setData(rt);
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    @Override
    public PlainResult<String> urlConnectionGet(String url, Map<String, String> params) {
        PlainResult<String> result = new PlainResult<String>();
        try {
            String rt = HttpUrlConnectionUtil.doGet(url, params, 5000);
            result.setData(rt);
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    @Override
    public PlainResult<String> sendPost(String requestUri, Map<String, String> params) {
        PlainResult<String> result = new PlainResult<String>();
        UrlEncodedFormEntity entity = null;
        try {
            HttpClient defaultClient = new DefaultHttpClient();
            HttpEntity entity1 = entity = new UrlEncodedFormEntity(putParams(params), "UTF-8");
            HttpPost post = new HttpPost(requestUri);
            HttpResponse response = null;
            post.setEntity(entity1);
            response = defaultClient.execute(post);

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

            String str = br.readLine();
            result.setData(str);
        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public List<NameValuePair> putParams(Map<String, String> paramMap) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        return params;
    }

}
