package com.autoserve.abc.service.http;

import com.autoserve.abc.service.biz.result.PlainResult;

import java.util.Map;

/**
 * abc http请求调用
 *
 * @author shensheng.ss 2014年4月25日 上午11:57:26
 */
public interface AbcHttpCallService {

    PlainResult<String> httpGet(String requestUri);

    PlainResult<String> httpGet(String requestUri, Map<String, String> params);

    PlainResult<String> httpPost(String requestUri);

    PlainResult<String> httpPost(String requestUri, Map<String, String> params);
    
    PlainResult<String> sendPost(String requestUri, Map<String, String> params);

    PlainResult<String> urlConnectionPost(String url, String params);

    PlainResult<String> urlConnectionGet(String url, Map<String, String> params);

}
