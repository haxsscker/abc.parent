/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.site;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 模板编辑视图
 *
 * @author segen189 2015年1月4日 下午5:45:11
 */
public class TemplateEditView {

    @Resource
    private ArticleClassService articleClassService;

    public void execute(Context context, ParameterParser params) {

        PlainResult<ArticleClass> plainResult = articleClassService.queryById(params.getInt("acId"));
        if (plainResult.isSuccess()) {
            context.put("templateClass", plainResult.getData());
        }

    }

}
