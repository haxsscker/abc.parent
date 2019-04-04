package com.autoserve.abc.service.http;

import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.http.core.HttpGetUtil;
import com.autoserve.abc.service.http.core.HttpPostUtil;
import com.autoserve.abc.service.http.core.HttpResponseHandler;
import com.autoserve.abc.service.http.core.HttpUrlConnectionUtil;

/**
 * http 同步类请求调用
 *
 * @author shensheng.ss 2014年4月25日 下午12:00:41
 */
public class HttpCallServiceImpl implements HttpCallService {

    private static final ResponseHandler<PlainResult<String>> defaultResponseHandler = new HttpResponseHandler();

    private static final Logger                               log                    = LoggerFactory
                                                                                             .getLogger(HttpCallServiceImpl.class);

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
        return HttpPostUtil.execute(requestUri, null, defaultResponseHandler);
    }

    @Override
    public PlainResult<String> httpPost(String requestUri, Map<String, String> params) {
        return HttpPostUtil.execute(requestUri, params, defaultResponseHandler);
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

}
