/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.template;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 生成页面的视图渲染
 *
 * @author segen189 2015年1月4日 下午5:10:26
 */
public class PageRenderView {
    private static final Log    log                 = LogFactory.getLog(PageRenderView.class);

    @Resource
    private ArticleClassService articleClassService;

    final String                TEMPLATE_SYMBOL_KEY = "TEMPLATE_SYMBOL_KEY";

    public void execute(Context context, ParameterParser params) {

        String pageSymbol = params.getString(TEMPLATE_SYMBOL_KEY);
        PlainResult<ArticleClass> pageTemplateResult = articleClassService.queryByTemplateSymbol(pageSymbol);

        if (pageTemplateResult.isSuccess()) {
            context.put("pageTemplate", pageTemplateResult.getData());
        }

    }

}
