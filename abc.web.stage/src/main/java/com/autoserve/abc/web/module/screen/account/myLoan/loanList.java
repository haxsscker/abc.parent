package com.autoserve.abc.web.module.screen.account.myLoan;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.common.PageCondition.Order;
import com.autoserve.abc.dao.dataobject.CashBorrowerViewDO;
import com.autoserve.abc.dao.dataobject.stage.myborrow.BorrowStatistics;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.cash.CashBorrowerService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.Pagebean;

public class loanList {
    @Autowired
    private HttpSession         session;
    @Resource
    private LoanService         loanService;
    @Resource
    private CashBorrowerService cashBorrowerService;
    @Resource
    private PaymentPlanService  paymentPlanService;

    public void execute(Context context, ParameterParser params) {

        User user = (User) session.getAttribute("user");
        int currentPage = params.getInt("currentPage");
        if (currentPage == 0)
            currentPage = 1;
        int pageSize = 5;//默认
        //查询借款统计,待还汇总
        PlainResult<CashBorrowerViewDO> Cresult = cashBorrowerService.queryCashBorrower(user.getUserId());
        CashBorrowerViewDO cDO = Cresult.getData();
        context.put("cDO", cDO);
        //查询统计明细
        PageCondition pageCondition = new PageCondition(currentPage, pageSize, "waitPeriod", Order.DESC);
        PageResult<BorrowStatistics> Bresult = loanService.queryBorrowStatistics(user.getUserId(), pageCondition);
        List<BorrowStatistics> borrowStatisticsList = Bresult.getData();
        Pagebean<BorrowStatistics> pagebean = new Pagebean<BorrowStatistics>(currentPage, pageSize,
                borrowStatisticsList, Bresult.getTotalCount());

        //统计本月待付本金，利息
        PlainResult<BigDecimal> wResult = this.paymentPlanService.queryCurMonthWaitPayCapital(user.getUserId());
        BigDecimal curWaitCapital = wResult.getData();
        wResult = this.paymentPlanService.queryCurMonthWaitPayInterest(user.getUserId());
        BigDecimal curWaitInterest = wResult.getData();
        context.put("curWaitCapital", curWaitCapital);
        context.put("curWaitInterest", curWaitInterest);
        context.put("pagebean", pagebean);
    }
}
