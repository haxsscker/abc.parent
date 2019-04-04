package com.autoserve.abc.web.module.screen.article.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类DelArticleView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月18日 下午3:13:26
 */
public class DelArticleView {

    @Resource
    private ArticleInfoService articleInfoService;

    public JsonBaseVO execute(ParameterParser params) {
        Integer sys_article_id = params.getInt("article_id");

        BaseResult result = this.articleInfoService.removeArticleInfo(sys_article_id);
        JsonBaseVO jsonBaseVO = new JsonBaseVO();
        jsonBaseVO.setSuccess(result.isSuccess());
        jsonBaseVO.setMessage(result.getMessage());
        return jsonBaseVO;
    }

}
