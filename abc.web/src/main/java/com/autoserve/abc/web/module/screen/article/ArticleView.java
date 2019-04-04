package com.autoserve.abc.web.module.screen.article;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.util.GetButtonUtils;

/**
 * 类ArticleView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月18日 下午3:13:33
 */
public class ArticleView {
    
	@Resource
    private AuthorityService authorityService;

	@Resource
    private ArticleClassService articleClassService;
    
    public void execute(Context context, ParameterParser params) {
    	
    	ListResult<ArticleClass> list = articleClassService.queryList();
        context.put("columns", list.getData());
        GetButtonUtils.getButtons(authorityService, context, params);
        
    }

}
