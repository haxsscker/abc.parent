package com.autoserve.abc.web.module.screen.site.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.ArticleClassVOConverter;
import com.autoserve.abc.web.vo.site.ArticleClassItemVO;
import com.autoserve.abc.web.vo.site.ArticleClassVO;

/**
 * 类ActionColumnView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月4日 下午5:29:00
 */
public class ActionColumnView {

    @Resource
    private ArticleClassService articleClassService;

    public ArticleClassItemVO execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        PageResult<ArticleClass> list = this.articleClassService.queryListByParam(pageCondition);

        ArticleClassItemVO vo = new ArticleClassItemVO();
        List<ArticleClassVO> listVO = new ArrayList<ArticleClassVO>();
        
        //查询出父级菜单名称
        ListResult<ArticleClass> parentList = this.articleClassService.queryList();
        
        for (ArticleClass articleClass : list.getData()) {
        	
        	ArticleClassVO articleClassVO = ArticleClassVOConverter.toArticleClassVO(articleClass);
            
            for (ArticleClass parent : parentList.getData()) {
            	if(null != parent.getAcId() && null != articleClassVO.getColumnClass() && parent.getAcId().toString().equals(articleClassVO.getColumnClass().toString())){
            		articleClassVO.setColumnClassName(parent.getAcName());
            	}
            }
        	
            listVO.add(articleClassVO);
            
        }
        vo.setRows(listVO);
        vo.setTotal(list.getTotalCount());
        return vo;
    }
}
