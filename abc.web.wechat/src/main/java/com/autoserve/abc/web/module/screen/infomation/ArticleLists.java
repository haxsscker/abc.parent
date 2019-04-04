package com.autoserve.abc.web.module.screen.infomation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ArticleInfoDO;
import com.autoserve.abc.service.biz.convert.ArticleInfoConverter;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.Pagebean;

public class ArticleLists {
	 @Autowired
	    private HttpSession session;
	    @Resource
	    private ArticleInfoService articleInfoService;
	    public void execute(Context context, ParameterParser params) {
	    	String fromKind = params.getString("fromKind");
	    	String KeyWord = params.getString("KeyWord");
	    	int pageSize = 10;
	    	int currentPage = 1;
	    	PageCondition pageCondition = new PageCondition(currentPage, pageSize);
	    	PageResult<ArticleInfoDO> pageResult = articleInfoService.queryListByKeyWord(KeyWord,pageCondition);
	    	List<ArticleInfo> articleList = new ArrayList<ArticleInfo>();
            for (ArticleInfoDO obj : pageResult.getData()) {
            	articleList.add(ArticleInfoConverter.toArticleInfo(obj));
            }
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
	    	Pagebean<ArticleInfo> pagebean = new Pagebean<ArticleInfo>(currentPage, pageSize, articles, pageResult.getTotalCount());
	    	context.put("pagebean", pagebean);
	    	context.put("KeyWord", KeyWord);
	    	context.put("fromKind", fromKind);
	    	String title = "";
	    	if("Law".equals(KeyWord)){
	    		title = "政策法规";
	    	}else if("Industryinformation".equals(KeyWord)){
	    		title = "风险教育";
	    	}
	    	context.put("title", title);
	    }
}
