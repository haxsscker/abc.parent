package com.autoserve.abc.web.convert;

import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.web.vo.site.ArticleClassVO;

/**
 * 类ColumnConvert.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月5日 下午2:14:20
 */
public class ArticleClassVOConverter {

    public static ArticleClassVO toArticleClassVO(ArticleClass articleClass) {
        ArticleClassVO articleClassVO = new ArticleClassVO();
        articleClassVO.setColumncontenttemplate(articleClass.getAcContentTemplate());
        articleClassVO.setColumnClass(articleClass.getAcClass());
        articleClassVO.setColumndesc(articleClass.getAcDesc());
        articleClassVO.setColumnid(articleClass.getAcId());
        articleClassVO.setColumnkeyword(articleClass.getAcKeyWord());
        articleClassVO.setColumnmode(articleClass.getAcMode());
        articleClassVO.setColumnname(articleClass.getAcName());
        articleClassVO.setColumnorder(articleClass.getAcOrder());
        articleClassVO.setColumnpath(articleClass.getAcPath());
        articleClassVO.setColumntemplate(articleClass.getAcTemplate());
        articleClassVO.setColumntype(articleClass.getAcType().state);
        return articleClassVO;
    }
}
