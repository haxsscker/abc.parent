package com.autoserve.abc.web.module.screen.article.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.enums.BooleanType;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类EditArticleView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月18日 下午3:13:30
 */
public class EditArticleView {

    @Resource
    private ArticleInfoService articleInfoService;

    public JsonBaseVO execute(ParameterParser params) {
        Integer sys_article_id = params.getInt("sys_article_id");
        String sys_article_title = params.getString("sys_article_title");
        Integer sys_add_emp = params.getInt("sys_add_emp");
        Integer sys_class_id = params.getInt("sys_class_id");
        String sys_add_date = params.getString("sys_add_date");
        int sys_is_top = params.getInt("sys_is_top");
        String sys_article_source = params.getString("sys_article_source");
        String sys_article_content = params.getString("sys_article_content");
        Integer sys_article_count = params.getInt("sys_article_count");
        String sysArticleIntroduction = params.getString("sysArticleIntroduction");
        String article_info_logo = params.getString("article_info_logo");
        String aiArticleUrl = params.getString("sys_article_url");
        
        ArticleInfo articleInfo = new ArticleInfo();

        articleInfo.setAiId(sys_article_id);
        articleInfo.setAiArticleTitle(sys_article_title);
        articleInfo.setAiAddEmp(sys_add_emp);
        articleInfo.setAiClassId(sys_class_id);
        articleInfo.setAiAddDate(DateUtil.parseDate(sys_add_date, "yy-MM-dd"));
        articleInfo.setAiIsTop(BooleanType.valueOf(sys_is_top));
        articleInfo.setAiArticleSource(sys_article_source);
        articleInfo.setAiArticlecontent(sys_article_content);
        articleInfo.setAiArticleCount(sys_article_count);
        articleInfo.setAiArticleIntroduction(sysArticleIntroduction);
        articleInfo.setAiArticleLogo(article_info_logo);
        articleInfo.setAiArticleUrl(aiArticleUrl);
        BaseResult result = this.articleInfoService.modifyArticleInfo(articleInfo);
        JsonBaseVO jsonBaseVO = new JsonBaseVO();
        jsonBaseVO.setSuccess(result.isSuccess());
        jsonBaseVO.setMessage(result.getMessage());
        return jsonBaseVO;
    }

}
