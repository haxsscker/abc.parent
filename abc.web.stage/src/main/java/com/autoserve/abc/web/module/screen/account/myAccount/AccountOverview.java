package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.stage.statistics.RecentDeal;
import com.autoserve.abc.dao.dataobject.stage.statistics.StatisticsPaymentPlan;
import com.autoserve.abc.dao.dataobject.stage.statistics.TenderOverview;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.SafeUtil;

public class AccountOverview {
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
	LoanQueryService  loanQueryService;
	@Resource
	private BankInfoService bankInfoService;
    @Resource
    private TransferLoanService transferLoanService;
    @Resource
    private SysConfigService        sysConfigService;

    public void execute(Context context, ParameterParser params, Navigator nav) {

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
			userDO.getData().setUserBankcardIsbinded(banks.getData().size()>0 ? 1:0);
            context.put("user", userDO.getData());
            if (userDO.getData().getUserPhone() != null && !"".equals(userDO.getData().getUserPhone())) {
            	userPhone = userDO.getData().getUserPhone();
                context.put("userPhone", SafeUtil.hideMobile(userDO.getData().getUserPhone()));
                completed += 15;
            }
            if (userDO.getData().getUserDocNo() != null && !"".equals(userDO.getData().getUserDocNo())) {
                String userDocNo = SafeUtil.hideIDNumber(userDO.getData().getUserDocNo());
                context.put("userDocNo", userDocNo);
                completed += 15;
            }
            if (userDO.getData().getUserRealName() != null && !"".equals(userDO.getData().getUserRealName())) {
                String userRealName = SafeUtil.hideName(userDO.getData().getUserRealName());
                context.put("userRealName", userRealName);
                completed += 10;
            }

            if (userDO.getData().getUserName() != null && !"".equals(userDO.getData().getUserName())) {
                completed += 10;
            }
            if (userDO.getData().getUserEmail() != null && !"".equals(userDO.getData().getUserEmail())) {
                completed += 15;
            }
            if (userDO.getData().getUserPwd() != null && !"".equals(userDO.getData().getUserPwd())) {
                completed += 15;
            }
            if (userDO.getData().getUserDocType() != null && !"".equals(userDO.getData().getUserDocType())) {
                completed += 10;
            }
            if (userDO.getData().getUserHeadImg() != null && !"".equals(userDO.getData().getUserHeadImg())) {
                completed += 10;
            }
            context.put("completed", completed);
            Map<String,String> conditionsMap = investservice.checkInvestConditions(user.getUserId());
            context.put("conditionsMap", conditionsMap);
        }
        
        //待收金额、待还金额
        PlainResult<Map<String, BigDecimal>> planinResult = investservice.findTotalIncomeAndPayMoneyByUserId(user.getUserId());
        context.put("incomeAndPayMap", planinResult.getData());
        //距离开始时间间隔
        Map<Integer, Long> duarStartTimeMap = new HashMap<Integer, Long>();
        //距离结束时间间隔
        Map<Integer, Long> duarEndTimeMap = new HashMap<Integer, Long>();
        
        //优选推荐  edit by xiatt 20161019 优选推荐没找到，则找优选债券
        Long currTime = System.currentTimeMillis();
        List<Loan> loanList = this.loanQueryService.queryOptimizationFy(1).getData();
    	Loan loan = loanList.size() > 0 ? loanList.get(0) : null;
    	
    	List<TransferLoanJDO> transferYxList = transferLoanService.queryTrOptimization(1).getData();
        TransferLoanJDO  tranLoan = transferYxList.size() > 0 ? transferYxList.get(0) : null;
        
        //last loan type
//    	int lastLoanType = this.loanQueryService.queryLastLoanType(user.getUserId());
        
        if (null != tranLoan)
        {
        	context.put("transferLoanYx", tranLoan);
            BigDecimal percent = tranLoan.getTlCurrentValidInvest()
                    .divide(tranLoan.getTlTransferMoney(), 50, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(100));
            context.put("transferLoanYxPercent", percent);
        }
        
        Map<Integer,BigDecimal> loanListMap = new HashMap<Integer,BigDecimal>();
    	context.put("loanList", loanList);
    	BigDecimal percent = Arith.calcPercent(loan.getLoanCurrentValidInvest(), loan.getLoanMoney());
		loanListMap.put(loan.getLoanId(), percent);	
		duarStartTimeMap.put(loan.getLoanId(), loan.getLoanInvestStarttime().getTime() - currTime);
        duarEndTimeMap.put(loan.getLoanId(), currTime - loan.getLoanInvestEndtime().getTime());
        
        context.put("loanListPercent", loanListMap);
        context.put("duarStartTimeMap", duarStartTimeMap);
        context.put("duarEndTimeMap", duarEndTimeMap);
        
        String accountNo1 = "";//投资账户
    	String accountNo2 = "";//融资账户
    	UserIdentity userIdentity =new UserIdentity();
    	userIdentity.setUserId(user.getUserId());
    	userIdentity.setUserType(user.getUserType());
    	if(UserType.PERSONAL.type == user.getUserType().getType()){
    		userIdentity.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
    		PlainResult<Account> account1 = accountInfoService.queryByUserId(userIdentity);
    		if(account1.isSuccess()){
    			accountNo1 = account1.getData().getAccountNo();
    		}else if(!account1.isSuccess() && !StringUtil.isEmpty(userPhone)){//根据手机号查询个人用户信息
            	Map<String, String> userInfMap =this.doubleDryService.queryUserInf(userPhone);
            	accountNo1=null==userInfMap.get("INVESTACCOUNT")?"":userInfMap.get("INVESTACCOUNT");
            	accountNo2=null==userInfMap.get("LOANACCOUNT")?"":userInfMap.get("LOANACCOUNT");
            }
    	}
    	if(StringUtil.isEmpty(accountNo2)){
    		userIdentity.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
    		PlainResult<Account> account2 = accountInfoService.queryByUserId(userIdentity);
    		if(account2.isSuccess()){
    			accountNo2 = account2.getData().getAccountNo();
    			if(StringUtil.isEmpty(accountNo2) && UserType.ENTERPRISE.type==user.getUserType().getType()){//根据对公账号查询企业开户结果
    				String accountUserAccount =account2.getData().getAccountUserAccount();
    				String mark=account2.getData().getAccountMark();
    				Map<String, String> chargeAccountMap =this.doubleDryService.queryChargeAccountResult(accountUserAccount,mark);
    				accountNo2=chargeAccountMap.get("accountNo");
    			}
    		}
    	}
    	context.put("accountNo1", accountNo1);
	    context.put("accountNo2", accountNo2);
	    //投资户与融资户账号一样
   		String accountNo = StringUtil.isEmpty(accountNo1)?accountNo2:accountNo1;
        
        ListResult<TenderOverview> tenderoverview = investservice.findMyTenderOverview(user.getUserId());
        
        ListResult<StatisticsPaymentPlan> paymentplan = incomeplanservice.findMyPaymentPlan(user.getUserId());
        //最近交易
        ListResult<RecentDeal> recentdeal = dealrecordservice.findMyRecentDeal(accountNo,accountNo);

        context.put("tenderoverview", tenderoverview);
        context.put("paymentplan", paymentplan);
        context.put("recentdeal", recentdeal);
        context.put("accountNo", accountNo);
        //网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
//        String accountMark = account1.getData().getAccountMark(); //多多号id
//        Double[] accountBacance = { 0.00, 0.00, 0.00 };
        Double[] accountBacanceDetail = { 0.00, 0.00, 0.00, 0.00, 0.00, 0.00};
        if (accountNo != null && !"".equals(accountNo)) {
//            accountBacance = this.doubleDryService.queryBalance(accountNo, "1");
            accountBacanceDetail = this.doubleDryService.queryBalanceDetail(accountNo);//投资户与融资户余额查询
        }
        context.put("accountBacanceDetail", accountBacanceDetail); 
//        context.put("accountBacance", accountBacance);       
        // 平台账户
        PlainResult<SysConfig> platformAccountResult = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
        if (!platformAccountResult.isSuccess()) {
            throw new BusinessException("平台账户查询失败");
        }
        context.put("platformAccount", platformAccountResult.getData().getConfValue());       
        //站内息
        PageResult<SysMessageInfoDO> pageResult = messageInfoService.queryByUserId(user.getUserId(),
                new PageCondition());
        context.put("MessageCount", pageResult.getTotalCount());
    }
}
