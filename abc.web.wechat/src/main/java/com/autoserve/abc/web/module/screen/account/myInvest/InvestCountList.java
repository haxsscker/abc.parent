/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.account.myInvest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 资产统计详细列表，包括：已收收益，待收收益，已收本金，待收本金
 * 
 * @author zhangkang 2015年6月3日 上午10:57:58
 */
public class InvestCountList {

    @Autowired
    private DeployConfigService deployConfigService;
    @Autowired
    private HttpSession         session;
    @Autowired
    private IncomePlanService   incomePlanService;
    @Resource
    private HttpServletRequest  request;

    public void execute(Context context, ParameterParser params, Navigator nav) {

        //登录URL
        User user = (User) session.getAttribute("user");
        if (user == null) {
            nav.forwardTo(deployConfigService.getLoginUrl(request));
            return;
        }

        Integer currentPage = params.getInt("currentPage");
        Integer pageSize = params.getInt("pageSize");
        if (currentPage == 0)
            currentPage = 1;//默认情况
        pageSize = 10;//默认情况
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);

        IncomePlan incomePlan = new IncomePlan();
        incomePlan.setBeneficiary(user.getUserId());
        String menu = params.getString("menu");
        if ("yssy".equals(menu) || "ysbj".equals(menu)) {
            //已收收益，已收本金
            incomePlan.setIncomePlanState(IncomePlanState.CLEARED);
        } else if ("dssy".equals(menu) || "dsbj".equals(menu)) {
            //待收收益，待收本金
            incomePlan.setIncomePlanState(IncomePlanState.GOING);
        }

        PageResult<IncomePlan> pageResult = incomePlanService.queryIncomePlanList(incomePlan, null, pageCondition);
        Pagebean<IncomePlan> pagebean = new Pagebean<IncomePlan>(currentPage, pageSize, pageResult.getData(),
                pageResult.getTotalCount());
        context.put("pagebean", pagebean);
        context.put("menu", menu);
    }
}
