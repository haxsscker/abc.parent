/*
 * Copyright 2014 Aliyun.com All right reserved. This software is the
 * confidential and proprietary information of Aliyun.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Aliyun.com .
 */
package com.autoserve.abc.web.pipeline.valve;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValveDefinitionParser;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.pipeline.valve.AbstractInputOutputValve;
import com.alibaba.citrus.turbine.pipeline.valve.RenderResultAsJsonValve;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.alibaba.citrus.springext.util.SpringExtUtil.attributesToProperties;
import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;
import static com.alibaba.citrus.util.StringUtil.trimToNull;

/**
 * RenderJsonResultValve
 *
 * @author segen189
 * @since 2014-11-24 上午12:50:32
 */
public class RenderJsonResultValve extends AbstractInputOutputValve {
    private static final String DEFAULT_CONTENT_TYPE            = "application/json";
    private static final String DEFAULT_JAVASCRIPT_VARIABLE     = null;
    private static final String DEFAULT_JAVASCRIPT_CONTENT_TYPE = "application/javascript";

    @Autowired
    private HttpServletRequest  request;

    @Autowired
    private HttpServletResponse response;

    private String              contentType;
    private String              javascriptVariable;
    private String              javascriptContentType;

    public String getContentType() {
        return contentType == null ? DEFAULT_CONTENT_TYPE : contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = trimToNull(contentType);
    }

    public String getJavascriptVariable() {
        return javascriptVariable == null ? DEFAULT_JAVASCRIPT_VARIABLE : javascriptVariable;
    }

    public void setJavascriptVariable(String javascriptVariable) {
        this.javascriptVariable = trimToNull(javascriptVariable);
    }

    public String getJavascriptContentType() {
        return javascriptContentType == null ? DEFAULT_JAVASCRIPT_CONTENT_TYPE : javascriptContentType;
    }

    public void setJavascriptContentType(String javascriptContentType) {
        this.javascriptContentType = trimToNull(javascriptContentType);
    }

    @Override
    public void invoke(PipelineContext pipelineContext) throws Exception {
        TurbineRunData rundata = getTurbineRunData(request);

        if (!rundata.isRedirected()) {
            Object resultObject = consumeInputValue(pipelineContext);

            if (resultObject == null) {
                return;
            }

            String javascriptVariable = getJavascriptVariable();
            boolean outputAsJson = javascriptVariable == null;

            if (outputAsJson) {
                // output as json
                response.setContentType(getContentType());
            } else {
                // output as javascript
                response.setContentType(getJavascriptContentType());
            }

            PrintWriter out = response.getWriter();
            String jsonResult = JSON.toJSONString(resultObject, SerializerFeature.WriteTabAsSpecial);

            if (outputAsJson) {
                out.print(jsonResult);
            } else {
                out.print("var ");
                out.print(javascriptVariable);
                out.print(" = ");
                out.print(jsonResult);
                out.print(";");
            }
        }

        pipelineContext.invokeNext();
    }

    public static class DefinitionParser extends AbstractValveDefinitionParser<RenderResultAsJsonValve> {
        @Override
        protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
            attributesToProperties(element, builder, "input", "contentType", "javascriptVariable",
                    "javascriptContentType");
        }
    }
}
