package com.autoserve.abc.web.convert;

import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.enums.BooleanType;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.article.ArticleInfoVO;

public class ArticleInfoVOConverter {

    public static ArticleInfo toArticleInfo(ArticleInfoVO articleInfoVO) {
        ArticleInfo ArticleInfo = new ArticleInfo();
        ArticleInfo.setAiAddDate(DateUtil.parseDate(articleInfoVO.getSys_add_date(), "yy_MM_dd"));
        ArticleInfo.setAiAddEmp(articleInfoVO.getSys_add_emp());
        ArticleInfo.setAiArticlecontent(articleInfoVO.getSys_article_content());
        ArticleInfo.setAiArticleCount(articleInfoVO.getSys_article_count());
        ArticleInfo.setAiArticleSource(articleInfoVO.getSys_article_source());
        ArticleInfo.setAiArticleTitle(articleInfoVO.getSys_article_title());
        ArticleInfo.setAiClassId(articleInfoVO.getSys_class_name());
        ArticleInfo.setAiId(articleInfoVO.getSys_article_id());
        ArticleInfo.setAiIsTop(BooleanType.valueOf(articleInfoVO.getSys_is_top()));
        return ArticleInfo;
    }

    public static ArticleInfoVO toArticleInfoVO(ArticleInfo articleInfo, String className) {
        ArticleInfoVO ArticleInfoVO = new ArticleInfoVO();
        ArticleInfoVO.setSys_add_date(DateUtil.formatDate(articleInfo.getAiAddDate()));
        ArticleInfoVO.setSys_add_emp(articleInfo.getAiAddEmp());
        ArticleInfoVO.setSys_article_content(articleInfo.getAiArticlecontent());
        ArticleInfoVO.setSys_article_count(articleInfo.getAiArticleCount());
        ArticleInfoVO.setSys_article_id(articleInfo.getAiId());
        ArticleInfoVO.setSys_article_source(articleInfo.getAiArticleSource());
        ArticleInfoVO.setSys_article_title(articleInfo.getAiArticleTitle());
        ArticleInfoVO.setSys_class_name(articleInfo.getAiClassId());
        ArticleInfoVO.setSys_is_top(articleInfo.getAiIsTop().getType());
        ArticleInfoVO.setClassName(className);
        return ArticleInfoVO;

    }
}
