package com.autoserve.abc.web.module.control.common;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;

public class InfomationLeft
{
	@Resource
	private HttpSession session;
	@Resource
	private ArticleClassService articleClassService;
    public void execute(Context context, ParameterParser params) {
    	
    	/*List<ArticleClass> articleList = articleClassService.queryListByParam(null);
    	context.put("articleList", articleList);*/
    	
    }
}
