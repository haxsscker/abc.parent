package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;
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
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.CashBorrowerViewDO;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.dao.dataobject.LevelDO;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.stage.statistics.RecentDeal;
import com.autoserve.abc.dao.dataobject.stage.statistics.StatisticsPaymentPlan;
import com.autoserve.abc.dao.dataobject.stage.statistics.TenderOverview;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashBorrowerService;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.score.LevelService;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.SafeUtil;

public class AccountOverview {
    private final static Logger   logger = LoggerFactory.getLogger(AccountOverview.class); //加入日志

    @Autowired
    private HttpSession           session;
    @Resource
    private UserService           userService;
    @Resource
    private LoanQueryService      loanService;
    @Autowired
    private AccountInfoService    accountInfoService;
    @Resource
    private DoubleDryService      doubleDryService;
    @Resource
    private SysMessageInfoService messageInfoService;
    @Resource
    private HttpServletRequest    request;
    @Autowired
    private DeployConfigService   deployConfigService;
    @Resource
    private DealRecordService     dealrecordservice;
    @Resource
    private InvestService         investservice;
    @Resource
    private IncomePlanService     incomeplanservice;
    @Resource
    LoanQueryService              loanQueryService;
    @Resource
    private BankInfoService       bankInfoService;
    @Autowired
    private CashInvesterService   cashInvesterService;
    @Resource
    private CashBorrowerService   cashBorrowerService;
    @Resource
    private LevelService          levelService;

    public void execute(Context context, ParameterParser params, Navigator nav) {
        logger.info("into AccountOverview excute");

        User user = (User) session.getAttribute("user");

        if (user == null) {
            nav.redirectToLocation(deployConfigService.getLoginUrl(request));
            return;
        }
        Integer completed = 0;
        String userPhone = "";
        if (user != null) {
            PlainResult<UserDO> userDO = this.userService.findById(user.getUserId());
            ListResult<BankInfoDO> banks = bankInfoService.queryListBankInfo(user.getUserId());
            userDO.getData().setUserBankcardIsbinded(banks.getData().size() > 0 ? 1 : 0);
            context.put("user", userDO.getData());
            if (userDO.getData().getUserPhone() != null && "" != userDO.getData().getUserPhone()) {
            	userPhone = userDO.getData().getUserPhone();
            	Map<String, String> userInfMap =this.doubleDryService.queryUserInf(userPhone);
                context.put("userPhone", SafeUtil.hideMobile(userDO.getData().getUserPhone()));
                completed += 15;
            }
            if (userDO.getData().getUserDocNo() != null && "" != userDO.getData().getUserDocNo()) {
                String userDocNo = SafeUtil.hideIDNumber(userDO.getData().getUserDocNo());
                context.put("userDocNo", userDocNo);
                completed += 15;
            }
            if (userDO.getData().getUserRealName() != null && "" != userDO.getData().getUserRealName()) {
                String userRealName = SafeUtil.hideName(userDO.getData().getUserRealName());
                context.put("userRealName", userRealName);
                completed += 10;
            }

            if (userDO.getData().getUserName() != null && "" != userDO.getData().getUserName()) {
                completed += 10;
            }
            if (userDO.getData().getUserEmail() != null && "" != userDO.getData().getUserEmail()) {
                completed += 15;
            }
            if (userDO.getData().getUserPwd() != null && "" != userDO.getData().getUserPwd()) {
                completed += 15;
            }
            if (userDO.getData().getUserDocType() != null && "" != userDO.getData().getUserDocType()) {
                completed += 10;
            }
            if (userDO.getData().getUserHeadImg() != null && "" != userDO.getData().getUserHeadImg()) {
                completed += 10;
            }
            context.put("completed", completed);

            //add by wangning 2015-07-06
//            if (userDO.getData().getUserScore() != null && userDO.getData().getUserScore() >= 0) {
//                BigDecimal dcmail_score = new BigDecimal(userDO.getData().getUserScore());
//                PlainResult<LevelDO> levelDo = levelService.findLevelByScore(dcmail_score);
//                if (null != levelDo.getData()) {
//                    context.put("levelIcon", levelDo.getData().getLevIcon());
//                }
//            }
        }

        //待收金额、待还金额
        /*
         * PlainResult<Map<String, BigDecimal>> planinResult =
         * investservice.findTotalIncomeAndPayMoneyByUserId(user .getUserId());
         * context.put("incomeAndPayMap", planinResult.getData());
         */

        //优选推荐
        ListResult<Loan> loanList = this.loanQueryService.queryOptimization(5);
        context.put("loanList", loanList.getData());
        Map<Integer, BigDecimal> loanListMap = new HashMap<Integer, BigDecimal>();
        for (Loan temp : loanList.getData()) {
            BigDecimal percent = temp.getLoanCurrentValidInvest()
                    .divide(temp.getLoanMoney(), 50, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            loanListMap.put(temp.getLoanId(), percent);
        }
        context.put("loanListPercent", loanListMap);
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(user.getUserId());
        if (user.getUserType() == null || user.getUserType().getType() == 1) {
            user.setUserType(UserType.PERSONAL);
        } else {
            user.setUserType(UserType.ENTERPRISE);
        }
        userIdentity.setUserType(user.getUserType());
        //投资账户
    	userIdentity.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
    	PlainResult<Account> account1 = accountInfoService.queryByUserId(userIdentity);
    	//String accountNo1 = account1.getData().getAccountNo();
    	//融资账户
    	userIdentity.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
    	PlainResult<Account> account2 = accountInfoService.queryByUserId(userIdentity);
       

        String accountNo1 = account1.getData().getAccountNo();
        String accountNo2 = account2.getData().getAccountNo();
        ListResult<TenderOverview> tenderoverview = investservice.findMyTenderOverview(user.getUserId());

        ListResult<StatisticsPaymentPlan> paymentplan = incomeplanservice.findMyPaymentPlan(user.getUserId());
        //最近交易
        ListResult<RecentDeal> recentdeal = dealrecordservice.findMyRecentDeal(accountNo1,accountNo2);

        context.put("tenderoverview", tenderoverview);
        context.put("paymentplan", paymentplan);
        context.put("recentdeal", recentdeal);
        context.put("accountNo1", accountNo1);
        context.put("account1", account1.getData());
        context.put("accountNo2", accountNo2);
        context.put("account2", account2.getData());
        
    }
}
