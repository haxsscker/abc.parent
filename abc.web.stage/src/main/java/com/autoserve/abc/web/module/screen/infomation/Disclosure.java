package com.autoserve.abc.web.module.screen.infomation;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 首页
 * 
 * @author DS 2015下午5:01:17
 */
public class Disclosure {
    @Resource
    private HttpSession         session;
    @Resource
    private LoanService         loanService;
    @Resource
    private UserService         userService;
    @Resource	
    LoanQueryService            loanQueryService;
    @Resource
    IncomePlanService           incomePlanService;

    public void execute(Context context, ParameterParser params) {
//        Long currTime = System.currentTimeMillis();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            context.put("user", user);
        }
        
        //实际撮合成交金额
        PlainResult<BigDecimal> sumLoanTotal = loanService.sumLoanTotal();
        context.put("sumLoanTotal", sumLoanTotal.getData());
        
        //实际撮合成交项目数
        PlainResult<Integer> sumLoanNum = loanService.sumLoanNum();
        context.put("sumLoanNum", sumLoanNum.getData());
        
        //实际融资人数
        PlainResult<Integer> loanUserTotal = userService.queryLoanUserTotal();
        context.put("loanUserTotal", loanUserTotal.getData());
        
        //实际投资人数
        PlainResult<Integer> investUserTotal = userService.queryInvestUserTotal();
        context.put("investUserTotal", investUserTotal.getData());
        
        //为投资待收的本金
        PlainResult<BigDecimal> profitToPay = incomePlanService.findIncomeToPay();
        context.put("profitToPay", profitToPay.getData());

    }
}
