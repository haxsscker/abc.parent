package com.autoserve.abc.service.http.core;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * ResponseHandler
 *
 * @author shensheng.ss 2014年4月25日 下午3:49:31
 */
public class HttpResponseHandler implements ResponseHandler<PlainResult<String>> {
    private static final Logger  logger   = LoggerFactory.getLogger(HttpResponseHandler.class);

    @Override
    public PlainResult<String> handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
        PlainResult<String> result = new PlainResult<String>();

        final StatusLine statusLine = response.getStatusLine();
        logger.info("http响应的返回码为：{}",statusLine.getStatusCode());
        
        final HttpEntity entity = response.getEntity();
       
        
        if (statusLine.getStatusCode() < 200 || statusLine.getStatusCode() >= 300) {
            EntityUtils.consume(entity);
            return new PlainResult<String>().setErrorMessage(CommonResultCode.FAIL_HTTP_CALL,
                    statusLine.getReasonPhrase(), statusLine.getStatusCode());
        }
        
        String content=null;
        if(entity!=null){
        	content= EntityUtils.toString(entity);
        }
        result.setData(content);    
        logger.info("http响应的返回值：{}",content);
        return result;
    }

};
