package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.ArticleClassDO;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.enums.ColumnType;

public class ArticleClassConverter {

    public static ArticleClassDO toArticleClassDO(ArticleClass articleClass) {
        if (articleClass == null) {
            return null;
        }

        ArticleClassDO articleClassDO = new ArticleClassDO();
        articleClassDO.setAcId(articleClass.getAcId());
        articleClassDO.setAcName(articleClass.getAcName());
        articleClassDO.setAcMode(articleClass.getAcMode());
        articleClassDO.setAcPath(articleClass.getAcPath());
        articleClassDO.setAcTemplate(articleClass.getAcTemplate());
        articleClassDO.setAcContentTemplate(articleClass.getAcContentTemplate());
        articleClassDO.setAcDesc(articleClass.getAcDesc());
        articleClassDO.setAcKeyWord(articleClass.getAcKeyWord());
        if (articleClass.getAcType() != null) {
            articleClassDO.setAcType(articleClass.getAcType().state);
        }
        articleClassDO.setAcOrder(articleClass.getAcOrder());
        articleClassDO.setAcClass(articleClass.getAcClass());
        return articleClassDO;
    }

    public static ArticleClass toArticleClass(ArticleClassDO articleClassDO) {
        if (articleClassDO == null) {
            return null;
        }

        ArticleClass articleClass = new ArticleClass();
        articleClass.setAcId(articleClassDO.getAcId());
        articleClass.setAcName(articleClassDO.getAcName());
        articleClass.setAcMode(articleClassDO.getAcMode());
        articleClass.setAcPath(articleClassDO.getAcPath());
        articleClass.setAcTemplate(articleClassDO.getAcTemplate());
        articleClass.setAcContentTemplate(articleClassDO.getAcContentTemplate());
        articleClass.setAcDesc(articleClassDO.getAcDesc());
        articleClass.setAcKeyWord(articleClassDO.getAcKeyWord());
        articleClass.setAcType(ColumnType.valueOf(articleClassDO.getAcType()));
        articleClass.setAcOrder(articleClassDO.getAcOrder());
        articleClass.setAcClass(articleClassDO.getAcClass());
        return articleClass;
    }

}
