package com.autoserve.abc.web.module.screen.site.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.vo.site.ShowVO;

public class ShowColumnView {

    @Resource
    private ArticleClassService articleClassService;

    public List<ShowVO> execute(ParameterParser params) {
        ListResult<ArticleClass> list = this.articleClassService.queryList();
        List<ShowVO> listVO = new ArrayList<ShowVO>();
        for (ArticleClass articleClass : list.getData()) {
            ShowVO vo = new ShowVO();
            vo.setId(articleClass.getAcId());
            vo.setText(articleClass.getAcName());
            listVO.add(vo);
        }
        return listVO;
    }
}
