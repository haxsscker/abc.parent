package com.autoserve.abc.web.module.screen.site.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.enums.ColumnType;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类AddColumn.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月5日 下午2:58:22
 */
public class EditColumn {
    @Resource
    private ArticleClassService articleClassService;

    public JsonBaseVO execute(ParameterParser params) {
        Integer columnid = params.getInt("columnid");
        String columnname = params.getString("columnname");
        String columnkeyword = params.getString("columnkeyword");
        String columndesc = params.getString("columndesc");
        String columnpath = params.getString("columnpath");
        String columntemplate = params.getString("columntemplate");
        String columncontenttemplate = params.getString("columncontenttemplate");
        Integer columntype = params.getInt("columntype");
        Integer columnorder = params.getInt("columnorder");

        ArticleClass articleClass = new ArticleClass();
        articleClass.setAcId(columnid);
        articleClass.setAcName(columnname);
        articleClass.setAcKeyWord(columnkeyword);
        articleClass.setAcDesc(columndesc);
        articleClass.setAcPath(columnpath);
        articleClass.setAcTemplate(columntemplate);
        articleClass.setAcContentTemplate(columncontenttemplate);
        articleClass.setAcType(ColumnType.valueOf(columntype));
        articleClass.setAcOrder(columnorder);
        BaseResult result = this.articleClassService.modifyArticleClass(articleClass);
        JsonBaseVO jsonBaseVO = new JsonBaseVO();
        jsonBaseVO.setSuccess(result.isSuccess());
        jsonBaseVO.setMessage(result.getMessage());
        return jsonBaseVO;
    }
}
