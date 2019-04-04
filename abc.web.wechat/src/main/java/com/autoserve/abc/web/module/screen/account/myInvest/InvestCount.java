package com.autoserve.abc.web.module.screen.account.myInvest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.dao.dataobject.stage.myinvest.InvestStatistics;
import com.autoserve.abc.dao.dataobject.stage.statistics.RecentDeal;
import com.autoserve.abc.dao.dataobject.stage.statistics.StatisticsPaymentPlan;
import com.autoserve.abc.dao.dataobject.stage.statistics.TenderOverview;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;

/**
 * @author DS 2015年1月28日上午9:47:03
 */
public class InvestCount {
    private final static Logger logger = LoggerFactory.getLogger(InvestCount.class); //加入日志
    @Autowired
    private AccountInfoService  accountInfoService;
    @Resource
    private DoubleDryService    doubleDryService;
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
    private HuifuPayService     huiFuPayServcice;

    @Resource
    private DealRecordService   dealrecordservice;
    @Resource
    private InvestService       investservice;
    @Resource
    private IncomePlanService   incomeplanservice;

    public void execute(Context context, ParameterParser params, Navigator nav) {
        logger.info("into InvestCount execute");

        User user = (User) session.getAttribute("user");
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

        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(user.getUserId());
        if (user.getUserType() == null || user.getUserType().getType() == 1) {
            user.setUserType(UserType.PERSONAL);
        } else {
            user.setUserType(UserType.ENTERPRISE);
        }
        userIdentity.setUserType(user.getUserType());
        PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);

        String accountNo = account.getData().getAccountNo();

        ListResult<TenderOverview> tenderoverview = investservice.findMyTenderOverview(user.getUserId());

        ListResult<StatisticsPaymentPlan> paymentplan = incomeplanservice.findMyPaymentPlan(user.getUserId());
        //最近交易
        ListResult<RecentDeal> recentdeal = dealrecordservice.findMyRecentDeal(accountNo,accountNo);

        context.put("tenderoverview", tenderoverview);
        context.put("paymentplan", paymentplan);
        context.put("recentdeal", recentdeal);
        context.put("accountNo", accountNo);
        //网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
        String accountMark = account.getData().getAccountNo(); //多多号id
        Double[] accountBacance = { 0.00, 0.00, 0.00 };
        if (accountMark != null && !"".equals(accountMark)) {
            //调用汇付查询接口
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("Version", SystemGetPropeties.getStrString("Version"));
            resultMap.put("CmdId", "QueryBalanceBg");
            resultMap.put("MerCustId", SystemGetPropeties.getStrString("MerCustId"));
            resultMap.put("UsrCustId", accountMark);
            Map<String, String> resultMapBalance = new HashMap<String, String>();
            resultMapBalance = huiFuPayServcice.queryBalanceBg(SystemGetPropeties.getStrString("url"), resultMap);
            accountBacance[0] = Double.valueOf(resultMapBalance.get("AcctBal"));
            accountBacance[1] = Double.valueOf(resultMapBalance.get("AvlBal"));
            accountBacance[2] = Double.valueOf(resultMapBalance.get("FrzBal"));
        }
        context.put("accountBacance", accountBacance);
        logger.info("可用余额：" + accountBacance[0]);
        logger.info("可用余额：" + accountBacance[1]);
        logger.info("可用余额：" + accountBacance[2]);

        logger.info("end InvestCount execute");
    }
}
