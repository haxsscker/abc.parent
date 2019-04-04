package com.autoserve.abc.web.module.screen.infomation;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.Pagebean;

public class HelpCenter {
	@Autowired
	private HttpSession session;
	@Resource
	private ArticleInfoService articleInfoService;
	@Resource
	private ArticleClassService articleClassService;

	public void execute(Context context, ParameterParser params) {
		int aticletype = params.getInt("aticletype");
		int currentPage = 1;
		int pageSize = 10;

		if (aticletype == 0)
			aticletype = 31;
		PlainResult<ArticleClass> articleresult = articleClassService
				.queryById(aticletype);

		ArticleClass articleClass = articleresult.getData();
		ArticleInfo articleInfo = new ArticleInfo();
		articleInfo.setAiClassId(aticletype);
		if (35 == aticletype) {
			currentPage = params.getInt("currentPage");
			if (currentPage == 0)
				currentPage = 1;
			pageSize = params.getInt("pageSize");
			if (pageSize == 0)
				pageSize = 10;

		}
		PageResult<ArticleInfo> result = articleInfoService
				.queryArticleInfoListByParam(articleInfo, new PageCondition(
						currentPage, pageSize));
		if (result.getData().size() > 0)
			context.put("article", result.getData().get(0));
		if (35 == aticletype) {
			Pagebean<ArticleInfo> pagebean = new Pagebean<ArticleInfo>(
					currentPage, pageSize, result.getData(),
					result.getTotalCount());
			context.put("pagebean", pagebean);
		}

		context.put("aticletype", aticletype);
		context.put("title", articleClass.getAcName());

	}
}
