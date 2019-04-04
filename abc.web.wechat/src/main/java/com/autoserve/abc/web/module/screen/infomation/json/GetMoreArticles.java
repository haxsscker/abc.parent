package com.autoserve.abc.web.module.screen.infomation.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ArticleInfoDO;
import com.autoserve.abc.service.biz.convert.ArticleInfoConverter;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.PageResult;

public class GetMoreArticles {

	@Resource
    private ArticleInfoService articleInfoService;

	public List<ArticleInfo> execute(Context context, ParameterParser params) {
		List<ArticleInfo> articles = new ArrayList<ArticleInfo>();
		int pageSize = params.getInt("pageSize");
		String KeyWord = params.getString("KeyWord");
		int currentPage = params.getInt("currentPage") + 1;
		PageCondition pageCondition = new PageCondition(currentPage, pageSize);
		PageResult<ArticleInfoDO> pageResult = articleInfoService.queryListByKeyWord(KeyWord,pageCondition);
		if(pageResult.isSuccess()){
			List<ArticleInfo> articleList = new ArrayList<ArticleInfo>();
			for (ArticleInfoDO obj : pageResult.getData()) {
				articleList.add(ArticleInfoConverter.toArticleInfo(obj));
			}
			
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
		}
		return articles;
	}

}
