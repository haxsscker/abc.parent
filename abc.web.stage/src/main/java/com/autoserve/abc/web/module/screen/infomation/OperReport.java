package com.autoserve.abc.web.module.screen.infomation;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.MonthReportDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.intf.banel.BanelService;
import com.autoserve.abc.service.biz.intf.banel.MonthRptService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.util.DateUtil;

/**
 * 首页
 * 
 * @author DS 2015下午5:01:17
 */
public class OperReport {
    @Resource
    private HttpSession         session;
    @Resource
    private ArticleInfoService  articleInfoService;
    @Resource
    private LoanService         loanService;
    @Resource
    private InvestQueryService  investQueryService;
    @Resource
    private UserService         userService;
    @Resource
    private TransferLoanService transferLoanService;
    @Resource	
    LoanQueryService            loanQueryService;
    @Resource
    IncomePlanService           incomePlanService;
    @Resource
    private BanelService        banelService;
    @Resource
    private MonthRptService     monthRptService;
    
    

    public void execute(Context context, ParameterParser params) {
    	String infoType = params.getString("infoType");
    	String year = params.getString("year");
    	Calendar date = Calendar.getInstance();
    	if(StringUtils.isBlank(year)){
    		year = String.valueOf(date.get(Calendar.YEAR));
    	}
    	
    	context.put("infoType", infoType);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            context.put("user", user);
        }

        //平台累计成交额
        PlainResult<BigDecimal> turnover = loanService.queryTotal();
        context.put("turnover", turnover.getData());
        
        //为投资人赚取的收益
        PlainResult<BigDecimal> profit = incomePlanService.findIncome();
        context.put("profit", profit.getData());
        
        //当年投资人赚取的收益
        PlainResult<BigDecimal> profitNowYear = incomePlanService.findIncomeNowYear();
        context.put("profitNowYear", profitNowYear.getData());
        
//        //投资人数
//        PlainResult<Integer> number = userService.queryTotal();
//        context.put("number", number.getData());
        
        //平台当年成交额
        PlainResult<BigDecimal> turnoverNowYear = loanService.findTotalNowYear();
        context.put("turnoverNowYear", turnoverNowYear.getData());
        
        //上个月成交额
        PlainResult<BigDecimal> turnoverLastMonth = loanService.findTotalLastMonth();
        context.put("turnoverLastMonth", turnoverLastMonth.getData());
        
        //上个月投资人赚取的收益
        PlainResult<BigDecimal> profitLastMonth = incomePlanService.findIncomeLastMonth();
        context.put("profitLastMonth", profitLastMonth.getData());
        
        //上个月交易笔数
        Integer investLastMonth = investQueryService.findInvestLastMonth();
        context.put("investLastMonth", investLastMonth);
        
        //为投资待收的本金
        PlainResult<BigDecimal> profitToPay = incomePlanService.findIncomeToPay();
        context.put("profitToPay", profitToPay.getData());
        
        //为待收项目数
        PlainResult<Integer> loanToPay = loanService.sumLoanToPay();
        context.put("loanToPay", loanToPay.getData());
        
        //提前还款概况
      	context.put("aheadPaySummary", loanQueryService.queryAheadPay());
      	
      	//月度报告
        ListResult<MonthReportDO> result = monthRptService.queryListByYear(year);
        context.put("rows", result.getData());
        for(MonthReportDO mrd : result.getData()){
        	mrd.setCreateTime(DateUtil.formatDate(mrd.getCreateTime(),DateUtil.DATE_FORMAT));
        	mrd.setModifyTime(DateUtil.formatDate(mrd.getModifyTime(),DateUtil.DATE_FORMAT));
        }
    	String adminUrl = SystemGetPropeties.getBossString("adminUrl");
    	context.put("adminUrl", adminUrl);
    	context.put("year", year);

    }
}
