package com.autoserve.abc.web.module.screen.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.LoanCategory;

public class loanRedeemListCheckView {
    private static final Logger logger = LoggerFactory.getLogger(loanRedeemListCheckView.class);

    public void execute(Context context) {
    	context.put("loanCatogories", LoanCategory.values());
    }
}
