package com.autoserve.abc.web.module.screen.infomation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.common.PageCondition.Order;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.Pagebean;

public class CanagementTeam
{
    @Autowired
    private HttpSession session;
    @Resource
    private ArticleInfoService articleInfoService;
    public void execute(Context context, ParameterParser params) {
    	int currentPage = params.getInt("currentPage");
        int pageSize = 10;
        if(currentPage==0)currentPage=1;
    	PageCondition pageCondition = new PageCondition(currentPage,pageSize,"aiAddDate",Order.DESC);
    	ArticleInfo articleInfo = new ArticleInfo();
    	articleInfo.setAiClassId(21);//运营团队
    	PageResult<ArticleInfo> result = articleInfoService.queryArticleInfoListByParam(articleInfo, pageCondition);
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
    	Pagebean<ArticleInfo> pagebean = new Pagebean<ArticleInfo>(currentPage, pageSize, articles, result.getTotalCount());
    	context.put("pagebean", pagebean);
    }
}
