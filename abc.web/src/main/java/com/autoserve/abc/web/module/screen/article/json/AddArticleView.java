package com.autoserve.abc.web.module.screen.article.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.enums.BooleanType;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类AddArticleView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月18日 下午3:13:19
 */
public class AddArticleView {

    @Resource
    private ArticleInfoService articleInfoService;

    public JsonBaseVO execute(ParameterParser params) {
        String sys_article_title = params.getString("sys_article_title");
        Integer sys_add_emp = params.getInt("sys_add_emp");
        Integer sys_class_id = params.getInt("sys_class_id");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date sys_add_date = params.getDate("sys_add_date", sf);
        if(sys_add_date==null){
        	sys_add_date = new Date();
        }
        Integer sys_is_top = params.getInt("sys_is_top");
        String sys_article_source = params.getString("sys_article_source");
        String sys_article_content = params.getString("sys_article_content");
        Integer sys_article_count = params.getInt("sys_article_count");
        String  sysArticleIntroduction = params.getString("sysArticleIntroduction");
        String  articleInfoLogo = params.getString("article_info_logo");
        String aiArticleUrl = params.getString("sys_article_url");
        
        ArticleInfo articleInfo = new ArticleInfo();

        articleInfo.setAiArticleTitle(sys_article_title);
        articleInfo.setAiAddEmp(sys_add_emp);
        articleInfo.setAiClassId(sys_class_id);
        articleInfo.setAiAddDate(sys_add_date);
        articleInfo.setAiIsTop(BooleanType.valueOf(sys_is_top));
        articleInfo.setAiArticleSource(sys_article_source);
        articleInfo.setAiArticlecontent(sys_article_content);
        articleInfo.setAiArticleCount(sys_article_count);
        articleInfo.setAiArticleIntroduction(sysArticleIntroduction);
        articleInfo.setAiArticleLogo(articleInfoLogo);
        articleInfo.setAiArticleUrl(aiArticleUrl);
        BaseResult result = this.articleInfoService.createArticleInfo(articleInfo);
        JsonBaseVO jsonBaseVO = new JsonBaseVO();
        jsonBaseVO.setSuccess(result.isSuccess());
        jsonBaseVO.setMessage(result.getMessage());
        return jsonBaseVO;
    }
}
