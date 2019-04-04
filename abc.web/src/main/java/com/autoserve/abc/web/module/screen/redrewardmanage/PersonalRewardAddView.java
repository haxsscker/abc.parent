package com.autoserve.abc.web.module.screen.redrewardmanage;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.LoanCategory;

/**
 * web 层 红包添加（从枚举中取出红包使用范围，放在context中）
 * 
 * @author fangrui 2015年1月5日 上午11:39:37
 */
public class PersonalRewardAddView {

    public void execute(Context context, ParameterParser params) {
        context.put("loanCategories", LoanCategory.values());
    }
}
