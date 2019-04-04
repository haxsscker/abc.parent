package com.autoserve.abc.web.module.screen.feeset;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.LoanCategory;

/**
 * 类FeeManageListView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月8日 下午3:07:39
 */
public class FeeManageListView {

    public void execute(Context context, ParameterParser params) {
        Integer feeType = params.getInt("feeType");
        context.put("feeType", feeType);
        context.put("loanCategories", LoanCategory.values());
    }

}
