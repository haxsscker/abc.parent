package com.autoserve.abc.web.module.screen.redrewardmanage;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.LoanCategory;

/**
 * 项目红包添加
 * 
 * @author lipeng 2015年1月5日 上午11:53:17
 */
public class ProjectRewardAddView {

    public void execute(Context context, ParameterParser params) {
        context.put("loanCategories", LoanCategory.values());
    }
}
