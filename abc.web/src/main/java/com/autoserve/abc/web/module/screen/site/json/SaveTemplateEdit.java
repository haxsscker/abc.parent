/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.site.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 保存模版编辑
 *
 * @author segen189 2015年1月4日 下午5:47:44
 */
public class SaveTemplateEdit {

    @Resource
    private ArticleClassService articleClassService;

    public JsonBaseVO execute(Context context, ParameterParser params) {
        int acId = params.getInt("acId");
        String acContentTemplate = params.getString("templatecontent");

        ArticleClass pojo = new ArticleClass();
        pojo.setAcId(acId);
        pojo.setAcContentTemplate(acContentTemplate);

        BaseResult baseResult = articleClassService.modifyArticleClass(pojo);
        return ResultMapper.toBaseVO(baseResult);
    }

}
