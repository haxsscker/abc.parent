package com.autoserve.abc.web.module.screen.infomation;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.PageResult;

public class Technology {
	@Autowired
	private HttpSession session;
	@Resource
	private ArticleInfoService articleInfoService;
	
	public void execute(Context context, ParameterParser params) {
		ArticleInfo articleInfo = new ArticleInfo();
		articleInfo.setAiClassId(28);//先进技术
    	PageResult<ArticleInfo> technology = articleInfoService.queryArticleInfoListByParam(articleInfo, new PageCondition(1,1));
    	if(technology.isSuccess()&&technology.getData().size()>0){
    	context.put("technology", technology.getData().get(0));//只获取第一个
    	}
	}
}
