/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.projectmanage;

import java.util.Arrays;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;

/**
 * 项目管理-项目发布
 *
 * @author segen189 2014年12月22日 下午1:12:27
 */
public class ProjectReleaseListView {

    @Resource
    private AuthorityService authorityService;

    public void execute(Context context, ParameterParser params) {

        context.put("loanCatogories", LoanCategory.values());
        context.put("loanStates", Arrays.asList(LoanState.BID_INVITING, LoanState.WAIT_RELEASE));

    }

}
