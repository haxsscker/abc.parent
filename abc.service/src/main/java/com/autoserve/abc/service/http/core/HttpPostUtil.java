package com.autoserve.abc.service.http.core;

import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HttpPost
 *
 * @author shensheng.ss 2014年4月25日 下午4:36:20
 */
public class HttpPostUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpPostUtil.class);

    /**
     * @param requestUri
     * @param params
     * @param responseHandler
     * @return PlainResult<String>
     */
    public static PlainResult<String> execute(final String requestUri, final Map<String, String> params,
                                              final ResponseHandler<PlainResult<String>> responseHandler) {
        if (StringUtils.isBlank(requestUri)) {
            return null;
        }

        CloseableHttpClient httpclient = HttpClientFactory.getCloseableHttpClient();

        try {
            HttpUriRequest request = buildUriPostRequest(requestUri, params);
            return httpclient.execute(request, responseHandler);
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

    /***
     * 执行 HttpPost 请求, 不处理响应的cookie
     *
     * @param requestUri
     * @param params
     * @return PlainResult<HttpEntity>
     */
    public static PlainResult<HttpEntity> execute(final String requestUri, final Map<String, String> params) {
        if (StringUtils.isBlank(requestUri)) {
            return null;
        }

        PlainResult<HttpEntity> reuslt = new PlainResult<HttpEntity>();

        CloseableHttpClient httpclient = HttpClientFactory.getCloseableHttpClient();
        try {
            HttpUriRequest request = buildUriPostRequest(requestUri, params);

            CloseableHttpResponse response = httpclient.execute(request);
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

    private static HttpUriRequest buildUriPostRequest(final String requestUri, final Map<String, String> params)
            throws URISyntaxException {
        RequestBuilder builder = RequestBuilder.post().setUri(new URI(requestUri));

        if (params != null) {
            for (Entry<String, String> param : params.entrySet()) {
                builder.addParameter(param.getKey(), param.getValue());
            }
        }

        return builder.build();
    }
}
