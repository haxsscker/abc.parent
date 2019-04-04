package com.autoserve.abc.service.http.core;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * HttpGet
 *
 * @author shensheng.ss 2014年4月25日 下午4:06:49
 */
public class HttpGetUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpGetUtil.class);

    /**
     * 执行 HttpGet 请求
     *
     * @param requestUri
     * @param responseHandler
     * @return PlainResult<String>
     */
    public static PlainResult<String> execute(final String requestUri,
                                              final ResponseHandler<PlainResult<String>> responseHandler) {
        if (StringUtils.isBlank(requestUri)) {
            return new PlainResult<String>().setErrorMessage(CommonResultCode.ILLEGAL_PARAM, "requestUri");
        }

        CloseableHttpClient httpclient = HttpClientFactory.getCloseableHttpClient();
        try {
            HttpGet httpget = new HttpGet(requestUri);

            return httpclient.execute(httpget, responseHandler);
        } catch (Exception e) {
            String msg = e.getCause() == null ? e.toString() : e.getCause().getMessage();
            return new PlainResult<String>().setErrorMessage(CommonResultCode.EXCEPITON_HTTP_CALL, msg);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error("error message", e);
            }
        }

    }

    /**
     * 执行 HttpGet 请求
     *
     * @param requestUri
     * @param params
     * @param responseHandler
     * @return PlainResult<String>
     */
    public static PlainResult<String> execute(final String requestUri, Map<String, String> params,
                                              final ResponseHandler<PlainResult<String>> responseHandler) {
        if (StringUtils.isBlank(requestUri)) {
            return new PlainResult<String>().setErrorMessage(CommonResultCode.ILLEGAL_PARAM, "requestUri");
        }

        return execute(buildRequestUri(requestUri, params), responseHandler);
    }

    /***
     * 执行 HttpGet 请求, 不处理响应的cookie
     *
     * @param requestUri
     * @return PlainResult<HttpEntity>
     */
    public static PlainResult<HttpEntity> execute(final String requestUri) {
        if (StringUtils.isBlank(requestUri)) {
            return null;
        }

        PlainResult<HttpEntity> reuslt = new PlainResult<HttpEntity>();

        CloseableHttpClient httpclient = HttpClientFactory.getCloseableHttpClient();
        try {
            HttpGet httpget = new HttpGet(requestUri);

            CloseableHttpResponse response = httpclient.execute(httpget);

            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
            reuslt.setData(entity);

            response.close();
        } catch (Exception e) {
            String msg = e.getCause() == null ? e.toString() : e.getCause().getMessage();
            reuslt.setErrorMessage(CommonResultCode.EXCEPITON_HTTP_CALL, msg);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error("error message", e);
            }
        }

        return reuslt;
    }

    /**
     * @param requestUri
     * @param params
     * @return
     */
    public static PlainResult<HttpEntity> execute(String requestUri, Map<String, String> params) {
        if (StringUtils.isBlank(requestUri)) {
            return null;
        }

        return execute(buildRequestUri(requestUri, params));
    }

    private static String buildRequestUri(final String requestUri, final Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return requestUri;
        }

        StringBuilder sb = new StringBuilder(requestUri);
        sb.append('?');

        for (Entry<String, String> param : params.entrySet()) {
            sb.append(param.getKey()).append('=').append(param.getValue()).append('&');
        }

        return sb.substring(0, sb.length() - 1);
    }

}
