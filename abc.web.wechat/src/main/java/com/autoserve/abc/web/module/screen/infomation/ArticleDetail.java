package com.autoserve.abc.web.module.screen.infomation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class ArticleDetail {
    @Resource
    private ArticleInfoService articleInfoService;
    @Resource
 	private ArticleClassService articleClassService;
    public void execute(Context context, ParameterParser params) {
    	Integer articleid = params.getInt("articleid"); 
		Integer aiClassId = params.getInt("aiClassId");
		PlainResult<ArticleClass> articleResult = articleClassService.queryById(aiClassId);
		ArticleClass articleClass = articleResult.getData();
		context.put("KeyWord", articleClass.getAcKeyWord());
		context.put("modelName", articleClass.getAcName());
//    	PlainResult<ArticleInfo> pageResult = articleInfoService.queryById(articleid);
//    	context.put("articleInfo", pageResult.getData());
    	ArticleInfo articleInfo = new ArticleInfo();
	    articleInfo.setAiClassId(aiClassId);
	    PageResult<ArticleInfo> result = articleInfoService.queryArticleInfoListByParam(articleInfo, new PageCondition());
	    List<ArticleInfo> ArticleInfolist = result.getData();
	    List<ArticleInfo> articles = new ArrayList<ArticleInfo>();
    	for (ArticleInfo articleInfo2 : ArticleInfolist)
		{
			if(articleInfo2.getAiIsTop().getType()==1)
			{
				articles.add(articleInfo2);//先添加置顶的文章
			}
		}
    	for (ArticleInfo articleInfo2 : ArticleInfolist)
    	{
    		if(articleInfo2.getAiIsTop().getType()==0)
    		{
    			articles.add(articleInfo2);//再添加非置顶的文章
    		}
    	}
    	int aiId=0;
	    for(int i=0;i<articles.size();i++)
	    {
	    	aiId =  articles.get(i).getAiId();
	    	if(articleid==aiId)
	    	{
	    		articleInfo = articles.get(i);
	    		// 更新阅读次数字段
	    		Integer count = articleInfo.getAiArticleCount();
	    		articleInfo.setAiArticleCount(count == null?1:count+1);
	    		articleInfoService.modifyArticleInfo(articleInfo);
	    		if(articles.size()==1)
	    		{
	    			context.put("messageP", "没有了");
	    			context.put("messageN", "没有了");
	    		}
	    		else if(i==0)
	    		{
	    			context.put("messageP", "没有了");
	    			context.put("articleNext", articles.get(i+1));
	    		}
	    		else if(i==articles.size()-1)
	    		{
	    			context.put("messageN", "没有了");
	    			context.put("articlePrvious", articles.get(i-1));
	    		}
	    		else
	    		{
	    			context.put("articlePrvious", articles.get(i-1));
	    			context.put("articleNext", articles.get(i+1));
	    		}
	    		context.put("articleInfo", articleInfo);
	    	}
	    
	    }
    }
}
