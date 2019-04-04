package com.autoserve.abc.web.module.screen.infomation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.ListResult;

public class Team
{
    @Resource
    private ArticleInfoService articleInfoService;
    public void execute(Context context, ParameterParser params) {
    	//运营团队
    	ArticleInfo articleInfo = new ArticleInfo();
    	articleInfo.setAiClassId(21);//运营团队
    	ListResult<ArticleInfo> result = articleInfoService.queryArticleInfoListByParam(articleInfo);
    	List<ArticleInfo> articleList = result.getData();
    	List<ArticleInfo> articles = new ArrayList<ArticleInfo>();
    	for (ArticleInfo articleInfo2 : articleList)
		{
			if(articleInfo2.getAiIsTop().getType()==1)
			{
				articles.add(articleInfo2);//先添加置顶的文章
			}
		}
    	for (ArticleInfo articleInfo2 : articleList)
    	{
    		if(articleInfo2.getAiIsTop().getType()==0)
    		{
    			articles.add(articleInfo2);//再添加非置顶的文章
    		}
    	}
    	
    	for(int i=0;i<articles.size();i++){
    		articles.get(i).setAiArticlecontent(articles.get(i).getAiArticlecontent().replaceAll("\\&[a-zA-Z]{0,9};", "").replaceAll("<[^>]*>", "\n\t"));	
    	}
    	context.put("result", result);
    	
    }
}
