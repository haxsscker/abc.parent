/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.projectmanage;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;

/**
 * 项目管理-转让跟踪
 *
 * @author segen189 2014年12月22日 下午12:05:58
 */
public class ProjectScheduleListView {

    @Resource
    private AuthorityService authorityService;

    public void execute(Context context, ParameterParser params) {

        context.put("loanCatogories", LoanCategory.values());
        context.put("transferLoanStates", TransferLoanState.values());

    }

}
