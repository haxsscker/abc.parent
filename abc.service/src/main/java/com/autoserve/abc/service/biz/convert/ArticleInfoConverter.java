package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.ArticleInfoDO;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.enums.BooleanType;

/**
 * ArticleInfo和ArticleInfoDO互相转换
 * 
 * @author liuwei 2014年11月29日 下午1:59:33
 */
public class ArticleInfoConverter {

    public static ArticleInfo toArticleInfo(ArticleInfoDO articleInfoDO) {

        if (articleInfoDO == null) {
            return null;
        }
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setAiId(articleInfoDO.getAiId());
        articleInfo.setAiClassId(articleInfoDO.getAiClassId());
        articleInfo.setAiArticleTitle(articleInfoDO.getAiArticleTitle());
        articleInfo.setAiArticlecontent(articleInfoDO.getAiArticlecontent());
        if(articleInfoDO.getAiIsTop()!=null)
        	articleInfo.setAiIsTop(BooleanType.valueOf(articleInfoDO.getAiIsTop()));
        articleInfo.setAiArticleSource(articleInfoDO.getAiArticleSource());
        articleInfo.setAiAddEmp(articleInfoDO.getAiAddEmp());
        articleInfo.setAiAddDate(articleInfoDO.getAiAddDate());
        articleInfo.setAiArticleCount(articleInfoDO.getAiArticleCount());
        articleInfo.setAiArticleIntroduction(articleInfoDO.getAiArticleIntroduction());
        articleInfo.setAiArticleLogo(articleInfoDO.getAiArticleLogo());
        articleInfo.setAiArticleUrl(articleInfoDO.getAiArticleUrl());
        return articleInfo;
    }

    public static ArticleInfoDO toArticleInfoDO(ArticleInfo articleInfo) {

        if (articleInfo == null) {
            return null;
        }
        ArticleInfoDO articleInfoDO = new ArticleInfoDO();
        articleInfoDO.setAiId(articleInfo.getAiId());
        articleInfoDO.setAiClassId(articleInfo.getAiClassId());
        articleInfoDO.setAiArticleTitle(articleInfo.getAiArticleTitle());
        articleInfoDO.setAiArticlecontent(articleInfo.getAiArticlecontent());
        if(articleInfo.getAiIsTop()!=null)
          articleInfoDO.setAiIsTop(articleInfo.getAiIsTop().getType());
        articleInfoDO.setAiArticleSource(articleInfo.getAiArticleSource());
        articleInfoDO.setAiAddEmp(articleInfo.getAiAddEmp());
        articleInfoDO.setAiAddDate(articleInfo.getAiAddDate());
        articleInfoDO.setAiArticleCount(articleInfo.getAiArticleCount());
        articleInfoDO.setAiArticleIntroduction(articleInfo.getAiArticleIntroduction());
        articleInfoDO.setAiArticleLogo(articleInfo.getAiArticleLogo());
        articleInfoDO.setAiArticleUrl(articleInfo.getAiArticleUrl());
        return articleInfoDO;
    }

}
