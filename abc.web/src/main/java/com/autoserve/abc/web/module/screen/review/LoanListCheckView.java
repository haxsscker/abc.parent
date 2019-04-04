
package com.autoserve.abc.web.module.screen.review;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;

public class LoanListCheckView {

    @Resource
    private AuthorityService authorityService;

    public void execute(Context context, ParameterParser params) {

        context.put("loanCatogories", LoanCategory.values());

    }

}
