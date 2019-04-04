package com.autoserve.abc.web.module.screen.account.myInvest;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.dao.dataobject.stage.myinvest.InvestStatistics;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;

/**
 * @author DS 2015年1月28日上午9:47:03
 */
public class InvestCount {
    @Autowired
    private DeployConfigService deployConfigService;
    @Autowired
    private HttpSession         session;
    @Autowired
    private InvestQueryService  investQueryService;
    @Autowired
    private CashInvesterService cashInvesterService;
    @Resource
    private HttpServletRequest  request;
    @Resource
    private IncomePlanService   incomePlanService;

    public void execute(Context context, ParameterParser params, Navigator nav) {
        //登录URL
        User user = (User) session.getAttribute("user");
        if (user == null) {
            nav.forwardTo(deployConfigService.getLoginUrl(request));
            return;
        }
        //投资统计、待收汇总
        PlainResult<CashInvesterViewDO> plainResult = cashInvesterService.queryCashInvester(user.getUserId());
        //回款中的标
        Integer currentPage = params.getInt("currentPage");
        Integer pageSize = params.getInt("pageSize");
        if (currentPage == 0)
            currentPage = 1;//默认情况
        if (pageSize == 0)
            pageSize = 10;//默认情况
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        PageResult<InvestStatistics> pageResult = investQueryService.queryInvestStatistics(user.getUserId(),
                pageCondition);
        //分页处理
        Pagebean<InvestStatistics> pagebean = new Pagebean<InvestStatistics>(currentPage, pageSize,
                pageResult.getData(), pageResult.getTotalCount());

        context.put("pagebean", pagebean);
        context.put("cashInvesterViewDO", plainResult.getData());

        //加上本月待收本金，利息
        PlainResult<BigDecimal> r1 = this.incomePlanService.queryCurMonthWaitIncomeCapital(user.getUserId());
        context.put("curIncomeCapital", r1.getData());
        r1 = this.incomePlanService.queryCurMonthWaitIncomeInterest(user.getUserId());
        context.put("curIncomeInterest", r1.getData());
    }
}
