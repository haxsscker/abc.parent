package com.autoserve.abc.web.module.screen.mobile;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ArticleInfoDO;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.PageResult;

public class AboutUs {

    private final static String mobileColumnKey = "About_Us_Mobile"; //手机端关于我们栏目关键字，不能被修改
    private final static String CompanyIntroductionKey = "CompanyIntroduction"; //平台介绍
    private final static String ContactUsKey = "ContactUs"; //联系我们
    
    @Resource
    private ArticleInfoService  articleInfoService;

    public void execute(Context context, Navigator nav, ParameterParser params) {

        String fromKind = params.getString("fromKind");
        PageResult<ArticleInfoDO> pageResult = articleInfoService.queryListByKeyWord(CompanyIntroductionKey,
                new PageCondition());
        PageResult<ArticleInfoDO> pageResult1 = articleInfoService.queryListByKeyWord(ContactUsKey,
                new PageCondition());
        context.put("companyInfo", pageResult.getData().get(0));
        context.put("contactUsInfo", pageResult1.getData().get(0));
        context.put("fromKind", fromKind);
    }
}
