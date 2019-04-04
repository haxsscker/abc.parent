package com.autoserve.abc.web.module.screen.site.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class DelColumn {

    @Resource
    private ArticleClassService articleClassService;

    public JsonBaseVO execute(ParameterParser params) {
        Integer id = params.getInt("id");
        BaseResult result = this.articleClassService.removeArticleClass(id);
        JsonBaseVO vo = new JsonBaseVO();
        vo.setSuccess(result.isSuccess());
        vo.setMessage(result.getMessage());
        return vo;
    }
}
