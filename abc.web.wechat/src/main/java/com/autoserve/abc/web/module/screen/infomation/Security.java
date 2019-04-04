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

public class Security
{
    @Autowired
    private HttpSession session;
    @Resource
    private ArticleInfoService articleInfoService;
    public void execute(Context context, ParameterParser params) {
    	ArticleInfo articleInfo = new ArticleInfo();
    	articleInfo.setAiClassId(26);//安全保障
    	PageResult<ArticleInfo> result = articleInfoService.queryArticleInfoListByParam(articleInfo, new PageCondition(1,1));
    	if(result.getData()!=null && result.getData().size()>0){
    		context.put("security", result.getData().get(0));//只获取第一个
    	}
    }
}
