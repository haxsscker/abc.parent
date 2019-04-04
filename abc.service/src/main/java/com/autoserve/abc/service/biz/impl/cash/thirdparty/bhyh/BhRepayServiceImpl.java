/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.PayRecordDao;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.convert.AccountConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealRecord;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilder;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilderFactory;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.invest.ActivityRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.manage.TransferLoanManageService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.loan.repay.RepayService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 还款服务实现
 * 
 * @author segen189 2014年11月29日 下午2:32:40
 */
@Service
public class BhRepayServiceImpl implements RepayService {
    private static final Log      log = LogFactory.getLog(BhRepayServiceImpl.class);

    @Resource
    private PaymentPlanDao        paymentPlanDao;

    @Resource
    private PaymentPlanService    paymentPlanService;

    @Resource
    private IncomePlanService     incomePlanService;

    @Resource
    private InvestDao             investDao;

    @Resource
    private PayRecordDao          payRecordDao;

    @Resource
    private InvestOrderService    investOrderService;

    @Resource
    private ActivityRecordService activityRecordService;

    @Resource
    private DealRecordService     dealRecordService;

    @Resource
    private LoanService           loanService;

    @Resource
    private TransferLoanService   transferLoanService;

    @Resource
    private BuyLoanService        buyLoanService;

    @Resource
    private AccountInfoService    accountInfoService;

    @Resource
    private UserService           userService;

    @Resource
    private SysConfigService      sysConfigService;

    @Resource
    private Callback<DealNotify>  repayedCallback;
    @Resource
    private InvestService   investService;
	@Resource
	private TransferLoanManageService transferLoanManageService;
	@Resource
	private DoubleDryService doubleDryService;
	@Resource
    private EmployeeService employeeService;
    /**
     * 1. 前置条件判断<br>
     * 1.1 查询还款计划<br>
     * 1.2 加行级锁<br>
     * 1.3 查询收益计划<br>
     * 1.4 查询账户信息<br>
     * 2. 判断是否逾期<br>
     * 2.1 如果有则查询罚息利率并计算罚息<br>
     * 2.2 如果有罚息，则更新还款计划表中的应还罚息、应还总额字段<br>
     * 2.3 如果有罚息，更新收益计划表中的应还罚息、应还总额字段<br>
     * 3. 创建交易记录<br>
     * 3.1 收取本金、利息、罚息 给投资人<br>
     * 3.2 收取服务费给平台<br>
     * 4. 更新还款计划表、收益计划表的内部交易流水号、状态<br>
     * 5. 查询是否有债券转让，如有，将债券标的状态改成流标<br>
     * 6. 执行交易<br>
     */
    // check 还款和转让同时
    // check 还款和收购同时
    // check 还款和资金划转同时， FullTransferServiceImpl 有还款行为则自动流标
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult repay(int loanId, int repayPlanId, PayType payType, int operatorId) {
        BaseResult result = new BaseResult();
        Loan loan = loanService.findLoanById(loanId).getData();
        if (loan == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "标的信息有误");
        }
        
        // 1. 前置条件判断
        // 1.2 加行级锁
        PaymentPlanDO paymentLock = paymentPlanDao.findWithLock(repayPlanId);
        if (paymentLock == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "还款时加行级锁失败");
        }

        // 1.1 查询还款计划
        //edit by 夏同同 20160507 解决担保代还的计划，借款人未还清时，担保无法继续代还的问题
        PlainResult<PaymentPlan> repayPlanResult = null;
        if (!PayType.GUAR_CLEAR.equals(payType)){
            //非担保待还时，查询下一条未还清的还款计划（正常还款、强制还款等）
            repayPlanResult = paymentPlanService.queryNextPaymentPlan(loanId);
        }else{
            //担保代还时，查询下一条未还清且不是担保待还的还款计划(担保代还过的，就不用再次担保代还了)
        	repayPlanResult = paymentPlanService.queryNextPaymentNoReplacePlan(loanId);
        }
        
        if (!repayPlanResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "还款计划表查询失败");
        }

        final PaymentPlan repayPlan = repayPlanResult.getData();
        if (repayPlan == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "没有需要还款的还款计划");
        } else if (repayPlanId != repayPlan.getId()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "请按期数顺序进行还款");
        }

        if (PayType.GUAR_CLEAR.equals(repayPlan.getPayType()) && PayType.GUAR_CLEAR.equals(payType)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "担保机构已经对本期还款进行了代还不能再次代还");
        }
        Deal deal;
        PlainResult<DealReturn> dealResult = null;

        /*
         * 对担保机构代还的还款计划进行还款
         * 
         * 渤海银行接口，暂不考虑平台代还，以下if块(199-346行)代码不会走到，本次也不修改，
         * 待确认删除“平台代还”功能后，再删除此处代码 xiatt 20180409
         */
        if (PayType.GUAR_CLEAR.equals(payType)) {
            // 1.3 查询收益计划
            IncomePlan pojo = new IncomePlan();
            pojo.setPaymentPlanId(repayPlan.getId());
            pojo.setIncomePlanState(IncomePlanState.GOING);
            ListResult<IncomePlan> incomePlanResult = incomePlanService.queryIncomePlanList(pojo);
            if (!incomePlanResult.isSuccess()) {
                return result.setError(CommonResultCode.BIZ_ERROR, "收益计划表查询失败");
            }

            List<IncomePlan> incomePlanList = incomePlanResult.getData();

            // 1.4 查询账户信息 payTotalMoney
            // 投资人
            final Map<Integer, UserType> incomeUserTypeMap = new HashMap<Integer, UserType>();
            List<UserIdentity> userList = new ArrayList<UserIdentity>();
            for (IncomePlan incomePlan : incomePlanList) {
                UserIdentity investor = new UserIdentity();
                investor.setUserId(incomePlan.getBeneficiary());
                investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
                investor.setAccountCategory(AccountCategory.INVESTACCOUNT.type);
                userList.add(investor);

                incomeUserTypeMap.put(incomePlan.getId(), investor.getUserType());
            }

            // 借款人
//            UserIdentity loanee = new UserIdentity();
//            loanee.setUserId(repayPlan.getLoanee());
//            loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
//            userList.add(loanee);

            ListResult<Account> accountResult = accountInfoService.queryByUserIds(userList);
            if (!accountResult.isSuccess()) {
                log.warn(accountResult.getMessage());
                throw new BusinessException("用户账户查询失败");
            }
            //担保机构id
            Integer loanGuarGov = loan.getLoanGuarGov();
            PlainResult<Employee> employee= employeeService.findByGovId(loanGuarGov);
            Integer empId = employee.getData().getEmpId();
            // 担保机构账户
            AccountInfoDO accountDo =  accountInfoService.queryByAccountMark(empId,UserType.PARTNER.type);
            Account guarAccount = AccountConverter.toUserAccount(accountDo);
            //担保账户余额
            Double[] accountBacance = this.doubleDryService.queryBalance(guarAccount.getAccountNo(), "1");
            double loanAvlBal = accountBacance[1];

            // 用户ID和账号的映射关系
            List<Account> userAccountList = accountResult.getData();
            userAccountList.add(guarAccount);
            final Map<String, String> userAccountMap = new HashMap<String, String>();
            for (Account account : userAccountList) {
                userAccountMap.put(account.getAccountUserId() + "|" + account.getAccountUserType().getType(),
                        account.getAccountNo());
            }

            // 平台账户
            PlainResult<SysConfig> platformAccountResult = sysConfigService
                    .querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
            if (!platformAccountResult.isSuccess()) {
                return result.setError(CommonResultCode.BIZ_ERROR, "平台账户查询失败");
            }

            String platformAccount = platformAccountResult.getData().getConfValue();

            // 目前还款是全额还款
            // -----------------正常还款--------------------
            // -----------------强制还款--------------------
            // -----------------平台代还--------------------
            final String payAccount;
            // add by 夏同同  违约罚金标记
            final boolean takeBreachFee;
            final BigDecimal pulishMoney;
            //增加违约罚金  夏同同/2016-4-10
            final BigDecimal pulishBreachMoney;
            payAccount = guarAccount.getAccountNo();
            takeBreachFee = true;
            
//          else {
//            	takeBreachFee = true;
//                payAccount = empId + "|" + UserType.PARTNER.getType();
//                takeServeFee = (repayPlan.getPayServiceFee() != null)
//                        && (repayPlan.getPayServiceFee().compareTo(BigDecimal.ZERO) > 0);
//            }

            /**
			 * @content:2. 计算逾期罚金和违约罚金
			 * @author:夏同同
			 * @date:2016年4月10日 上午10:01:23
			 */
            Map<String, BigDecimal> pulishMoneys = computePulishMoney(repayPlan);
            pulishMoney = pulishMoneys.get("pulishMoney");
            pulishBreachMoney = pulishMoneys.get("pulishBreachMoney");
            BigDecimal totalRepayAmount=repayPlan.getPayTotalMoney().add(pulishMoney).add(pulishBreachMoney);
            if(loanAvlBal<totalRepayAmount.doubleValue()){
            	return result.setError(CommonResultCode.EXCEPITON_HTTP_CALL, "担保机构融资户余额不足！");
            }
            PlainResult<EmployeeDO> empResult=employeeService.findById(guarAccount.getAccountUserId());
            //还款授权
			Date authorizeRepayStartDate=empResult.getData().getAuthorizeRepayStartDate();
			Date authorizeRepayEndDate=empResult.getData().getAuthorizeRepayEndDate();
			BigDecimal authorizeRepayAmount=null != empResult.getData().getAuthorizeRepayAmount()?empResult.getData().getAuthorizeRepayAmount():BigDecimal.ZERO;
			//还款授权判断
            if(!"60".equals(empResult.getData().getAuthorizeRepayType())){
            	return result.setError(CommonResultCode.EXCEPITON_HTTP_CALL, "担保账户未开启还款授权，请先去授权！");
			}else if(!"有效".equals(AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate))){
				return result.setError(CommonResultCode.EXCEPITON_HTTP_CALL, "担保账户还款授权"+AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate)+",请去修改！");
			}else if(totalRepayAmount.doubleValue()>authorizeRepayAmount.doubleValue()){
				return result.setError(CommonResultCode.EXCEPITON_HTTP_CALL, "还款金额超过担保账户还款授权金额,请去修改！");
			}
            if (pulishMoney.compareTo(BigDecimal.ZERO) > 0) {
                // 更新应还罚金、应还总额
                BaseResult ppModifyResult = paymentPlanService.
                		modifyPaymentPlan(repayPlan.getId(), pulishMoney,pulishBreachMoney,
                        PayState.UNCLEAR);
                if (!ppModifyResult.isSuccess()) {
                    return result.setError(CommonResultCode.BIZ_ERROR, "还款计划增加罚息失败");
                }
                
                //分配罚金 （ 夏同同：是将逾期罚金分配给投资人，与违约罚金无关）
                //1.查询原始标
                //2.查询投资列表
                //3.查询本期收益计划列表
                //4.调用分配查询接口              
                ListResult<Invest> invests=investService.findListByParamEarning(repayPlan.getLoanId());
                
                IncomePlan incomPlan=new IncomePlan();
                incomPlan.setPaymentPlanId(repayPlan.getId());
                incomPlan.setIncomePlanState(IncomePlanState.GOING);
                ListResult<IncomePlan> incomePlanResultx=incomePlanService.queryIncomePlanList(incomPlan);
                
                PlanBuilder planBuilder = PlanBuilderFactory.createPlanBuilder(LoanPeriodUnit.DAY);
                List<IncomePlan> incomePlans=planBuilder.distributionPenalty(loan, invests.getData(),incomePlanResultx.getData(),pulishMoney);
                
                //批量更新收益计划
                BaseResult baseResult=incomePlanService.updateIncomePlanByIncome(incomePlans);
                if(!baseResult.isSuccess()){
                	throw new BusinessException(baseResult.getMessage());
                }
                //重新查询收益计划列表
                incomePlanList=incomePlanService.queryIncomePlanList(incomPlan).getData();
            }

            // 3. 创建交易记录
            deal = new Deal();
            List<DealDetail> dealDetailList = new ArrayList<DealDetail>(userAccountList.size());
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("ReplaceState", false);
            paramsMap.put("BatchNo", loanId);    //网贷平台标号
            // 3.1 收取本金、利息、罚息 给投资人;服务费、违约罚金给平台
            for (IncomePlan incomePlan : incomePlanList) {
            	//说明：当投资人所获取的收益+本金+超期罚金为0时，过滤掉，不调用转账接口，防止转账不成功，报金额错误。
            	if(!(incomePlan.getPayCapital().compareTo(BigDecimal.ZERO)==0 && incomePlan.getPayInterest().compareTo(BigDecimal.ZERO)==0
            			&& incomePlan.getPayFine().compareTo(BigDecimal.ZERO)==0)){
	                // 收益人账户Id
	                String receiveAccountId = incomePlan.getBeneficiary() + "|"
	                        + incomeUserTypeMap.get(incomePlan.getId()).getType();
	
	                DealDetail captitalDetail = new DealDetail();
	                captitalDetail.setDealDetailType(DealDetailType.PAYBACK_CAPITAL);
	                captitalDetail.setMoneyAmount(incomePlan.getPayCapital());
	                captitalDetail.setPayAccountId(payAccount);
	                captitalDetail.setReceiveAccountId(receiveAccountId);
	                captitalDetail.setData(paramsMap);
	                dealDetailList.add(captitalDetail);

	                DealDetail interestDetail = new DealDetail();
	                interestDetail.setDealDetailType(DealDetailType.PAYBACK_INTEREST);
	                interestDetail.setMoneyAmount(incomePlan.getPayInterest());
	                interestDetail.setPayAccountId(payAccount);
	                interestDetail.setReceiveAccountId(receiveAccountId);
	                interestDetail.setData(paramsMap);
	                dealDetailList.add(interestDetail);

	                DealDetail fineDetail = new DealDetail();
	                fineDetail.setDealDetailType(DealDetailType.PAYBACK_OVERDUE_FINE);
	                fineDetail.setMoneyAmount(incomePlan.getPayFine());
	                fineDetail.setPayAccountId(payAccount);
	                fineDetail.setReceiveAccountId(receiveAccountId);
	                fineDetail.setData(paramsMap);
	                dealDetailList.add(fineDetail);
            	}
            }
            
            /*
             *  违约罚金 夏同同/2016-05-10
             *  注意：下面的如果大于三项或者每项的收款账户不一致时，会出现错误
             *  现在只有两项，且违约罚金和服务费的收款户都是平台
             */
            if(takeBreachFee && pulishBreachMoney.compareTo(BigDecimal.ZERO) > 0){
	            DealDetail fineBreachDetail = new DealDetail();
	            fineBreachDetail.setDealDetailType(DealDetailType.PAYBACK_BREACH_FINE);
	            fineBreachDetail.setMoneyAmount(pulishBreachMoney);
	            fineBreachDetail.setPayAccountId(payAccount);
	            fineBreachDetail.setReceiveAccountId(platformAccount);
	            fineBreachDetail.setData(paramsMap);
	            dealDetailList.add(fineBreachDetail);
            }

            // 3.2 收取服务费给平台
            if ("2".equals(loan.getHandFeeKind())) {
            	if(repayPlan.getPayServiceFee()!=null && repayPlan.getPayServiceFee().compareTo(BigDecimal.ZERO)>0){
            		DealDetail serveDetail = new DealDetail();
                    serveDetail.setDealDetailType(DealDetailType.PLA_SERVE_FEE);
                    serveDetail.setPayAccountId(payAccount);
                    serveDetail.setReceiveAccountId(platformAccount);
                    serveDetail.setMoneyAmount(repayPlan.getPayServiceFee());
                    serveDetail.setData(paramsMap);
                    dealDetailList.add(serveDetail);
            	}
            }

            deal.setInnerSeqNo(InnerSeqNo.getMerchInstance());
            deal.setBusinessType(DealType.PAYBACK);
            deal.setOperator(operatorId);
            deal.setDealDetail(dealDetailList);
            deal.setBusinessId(repayPlan.getLoanId());

            dealResult = dealRecordService.createBusinessRecord(deal, repayedCallback);
            if (!dealResult.isSuccess()) {
                log.warn(dealResult.getMessage());
                throw new BusinessException("还款交易创建失败");
            }

            // 4. 更新还款计划表、收益计划表的内部交易流水号、状态
            BaseResult repayModResult = paymentPlanService.modifyPaymentPlan(repayPlan.getId(), PayState.UNCLEAR,
                    PayState.PAYING, payType, deal.getInnerSeqNo().getUniqueNo());
            if (!repayModResult.isSuccess()) {
                log.warn(repayModResult.getMessage());
                throw new BusinessException("还款计划表状态更改失败");
            }

            BaseResult incomeModResult = incomePlanService.modifyIncomePlan(repayPlan.getId(), PayState.UNCLEAR,
                    PayState.PAYING, deal.getInnerSeqNo().getUniqueNo());
            if (!incomeModResult.isSuccess()) {
                log.warn(incomeModResult.getMessage());
                throw new BusinessException("收益计划表状态更改失败");
            }
        
        	
        } else {
        	UserDO userDO = userService.findById(operatorId).getData();
            if(null == userDO){
           	 return result.setError(CommonResultCode.BIZ_ERROR, "用户信息查询失败");
            }
            userDO.setAccountCategory(AccountCategory.LOANACCOUNT.type);
            AccountInfoDO loanAccount = accountInfoService.getAccountByCategory(userDO);
            if(null == loanAccount || StringUtils.isEmpty(loanAccount.getAccountNo())){
            	 return result.setError(CommonResultCode.BIZ_ERROR, CommonResultCode.NO_LOAN_ACCOUNT.message);
            }
            //融资户余额
            Double[] accountBacance = doubleDryService.queryBalanceDetail(loanAccount.getAccountNo());
            double loanAvlBal = accountBacance[3];
        	
        	/*
             * 对担保机构未代还的正常还款计划进行还款
             */
            // 1.3 查询收益计划
            IncomePlan pojo = new IncomePlan();
            pojo.setPaymentPlanId(repayPlan.getId());
            pojo.setIncomePlanState(IncomePlanState.GOING);
            ListResult<IncomePlan> incomePlanResult = incomePlanService.queryIncomePlanList(pojo);
            if (!incomePlanResult.isSuccess()) {
                return result.setError(CommonResultCode.BIZ_ERROR, "收益计划表查询失败");
            }

            List<IncomePlan> incomePlanList = incomePlanResult.getData();

            // 1.4 查询账户信息 payTotalMoney
            // 投资人
            final Map<Integer, UserType> incomeUserTypeMap = new HashMap<Integer, UserType>();
            List<UserIdentity> userList = new ArrayList<UserIdentity>();
            for (IncomePlan incomePlan : incomePlanList) {
                UserIdentity investor = new UserIdentity();
                investor.setUserId(incomePlan.getBeneficiary());
                investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
                investor.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
                userList.add(investor);

                incomeUserTypeMap.put(incomePlan.getId(), investor.getUserType());
            }

            // 借款人
            UserIdentity loanee = new UserIdentity();
            loanee.setUserId(repayPlan.getLoanee());
            loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
            loanee.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
            userList.add(loanee);

            ListResult<Account> accountResult = accountInfoService.queryByUserIds(userList);
            if (!accountResult.isSuccess()) {
                log.warn(accountResult.getMessage());
                throw new BusinessException("用户账户查询失败");
            }

            // 用户ID和账号的映射关系
            List<Account> userAccountList = accountResult.getData();
            final Map<String, String> userAccountMap = new HashMap<String, String>();
            for (Account account : userAccountList) {
                userAccountMap.put(account.getAccountUserId() + "|" + account.getAccountUserType().getType(),
                        account.getAccountNo());
            }

            // 平台账户
            PlainResult<SysConfig> platformAccountResult = sysConfigService
                    .querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
            if (!platformAccountResult.isSuccess()) {
                return result.setError(CommonResultCode.BIZ_ERROR, "平台账户查询失败");
            }

            String platformAccount = platformAccountResult.getData().getConfValue();

            // 目前还款是全额还款
            // -----------------正常还款--------------------
            // -----------------强制还款--------------------
            // -----------------担保代还--------------------
            final String payAccount = loanee.getUserId() + "|" + loanee.getUserType().getType();;
            // add by 夏同同  违约罚金标记
            final boolean takeBreachFee = true;
            final BigDecimal pulishMoney;
            //增加违约罚金  夏同同/2016-4-10
            final BigDecimal pulishBreachMoney;
            /**
			 * @content:2. 计算逾期罚金和违约罚金
			 * @author:夏同同
			 * @date:2016年4月10日 上午10:01:23
			 */
            Map<String, BigDecimal> pulishMoneys = computePulishMoney(repayPlan);
            pulishMoney = pulishMoneys.get("pulishMoney");//逾期罚金，其实就是罚息,还给投资人
            pulishBreachMoney = pulishMoneys.get("pulishBreachMoney");//违约罚金，就是罚金，还给平台的
            BigDecimal totalRepayAmount=repayPlan.getPayTotalMoney().add(pulishMoney).add(pulishBreachMoney);
            if(loanAvlBal<totalRepayAmount.doubleValue()){
            	return result.setError(CommonResultCode.EXCEPITON_HTTP_CALL, "借款人融资户余额不足！");
            }
            PlainResult<UserDO> userDoResult=userService.findById(repayPlan.getLoanee());
            //还款授权
			Date authorizeRepayStartDate=userDoResult.getData().getAuthorizeRepayStartDate();
			Date authorizeRepayEndDate=userDoResult.getData().getAuthorizeRepayEndDate();
			BigDecimal authorizeRepayAmount=null != userDoResult.getData().getAuthorizeRepayAmount()?userDoResult.getData().getAuthorizeRepayAmount():BigDecimal.ZERO;
			//还款授权判断
            if(!"60".equals(userDoResult.getData().getAuthorizeRepayType())){
            	return result.setError(CommonResultCode.EXCEPITON_HTTP_CALL, "借款人还未开启还款授权，请先去授权！");
			}else if(!"有效".equals(AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate))){
				return result.setError(CommonResultCode.EXCEPITON_HTTP_CALL, "借款人还款授权"+AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate)+",请去修改！");
			}else if(totalRepayAmount.doubleValue()>authorizeRepayAmount.doubleValue()){
				return result.setError(CommonResultCode.EXCEPITON_HTTP_CALL, "还款金额超过借款人的还款授权金额,请去修改！");
			}
            if (pulishMoney.compareTo(BigDecimal.ZERO) > 0) {
                // 更新应还罚金、应还总额
                BaseResult ppModifyResult = paymentPlanService.
                		modifyPaymentPlan(repayPlan.getId(), pulishMoney,pulishBreachMoney,
                        PayState.UNCLEAR);
                if (!ppModifyResult.isSuccess()) {
                    return result.setError(CommonResultCode.BIZ_ERROR, "还款计划增加罚息失败");
                }
                
                //分配罚金 （ 夏同同：是将逾期罚金分配给投资人，与违约罚金无关）
                //1.查询原始标
                //2.查询投资列表
                //3.查询本期收益计划列表
                //4.调用分配查询接口              
                ListResult<Invest> invests=investService.findListByParamEarning(repayPlan.getLoanId());
                
                IncomePlan incomPlan=new IncomePlan();
                incomPlan.setPaymentPlanId(repayPlan.getId());
                incomPlan.setIncomePlanState(IncomePlanState.GOING);
                ListResult<IncomePlan> incomePlanResultx=incomePlanService.queryIncomePlanList(incomPlan);
                
                PlanBuilder planBuilder = PlanBuilderFactory.createPlanBuilder(LoanPeriodUnit.DAY);
                List<IncomePlan> incomePlans=planBuilder.distributionPenalty(loan, invests.getData(),incomePlanResultx.getData(),pulishMoney);
                
                //批量更新收益计划
                BaseResult baseResult=incomePlanService.updateIncomePlanByIncome(incomePlans);
                if(!baseResult.isSuccess()){
                	throw new BusinessException(baseResult.getMessage());
                }
                //重新查询收益计划列表
                incomePlanList=incomePlanService.queryIncomePlanList(incomPlan).getData();
            }

            // 3. 创建交易记录
            deal = new Deal();
            List<DealDetail> dealDetailList = new ArrayList<DealDetail>(userAccountList.size());
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("ReplaceState", false);
            paramsMap.put("BatchNo", loanId);    //网贷平台标号
            // 3.1 收取本金、利息、罚息 给投资人;服务费、违约罚金给平台
            for (IncomePlan incomePlan : incomePlanList) {
            	//说明：当投资人所获取的收益+本金+超期罚金为0时，过滤掉，不调用转账接口，防止转账不成功，报金额错误。
            	if(!(incomePlan.getPayCapital().compareTo(BigDecimal.ZERO)==0 && incomePlan.getPayInterest().compareTo(BigDecimal.ZERO)==0
            			&& incomePlan.getPayFine().compareTo(BigDecimal.ZERO)==0)){
	                // 收益人账户Id
	                String receiveAccountId = incomePlan.getBeneficiary() + "|"
	                        + incomeUserTypeMap.get(incomePlan.getId()).getType();
	
	                DealDetail captitalDetail = new DealDetail();
	                captitalDetail.setDealDetailType(DealDetailType.PAYBACK_CAPITAL);
	                captitalDetail.setMoneyAmount(incomePlan.getPayCapital());
	                captitalDetail.setPayAccountId(payAccount);
	                captitalDetail.setReceiveAccountId(receiveAccountId);
	                captitalDetail.setData(paramsMap);
	                dealDetailList.add(captitalDetail);

	                DealDetail interestDetail = new DealDetail();
	                interestDetail.setDealDetailType(DealDetailType.PAYBACK_INTEREST);
	                interestDetail.setMoneyAmount(incomePlan.getPayInterest());
	                interestDetail.setPayAccountId(payAccount);
	                interestDetail.setReceiveAccountId(receiveAccountId);
	                interestDetail.setData(paramsMap);
	                dealDetailList.add(interestDetail);

	                DealDetail fineDetail = new DealDetail();
	                fineDetail.setDealDetailType(DealDetailType.PAYBACK_OVERDUE_FINE);
	                fineDetail.setMoneyAmount(incomePlan.getPayFine());
	                fineDetail.setPayAccountId(payAccount);
	                fineDetail.setReceiveAccountId(receiveAccountId);
	                fineDetail.setData(paramsMap);
	                dealDetailList.add(fineDetail);
            	}
            }
            
            /*
             *  违约罚金 夏同同/2016-05-10
             *  注意：下面的如果大于三项或者每项的收款账户不一致时，会出现错误
             *  现在只有两项，且违约罚金和服务费的收款户都是平台
             */
            if(takeBreachFee && pulishBreachMoney.compareTo(BigDecimal.ZERO) > 0){
	            DealDetail fineBreachDetail = new DealDetail();
	            fineBreachDetail.setDealDetailType(DealDetailType.PAYBACK_BREACH_FINE);
	            fineBreachDetail.setMoneyAmount(pulishBreachMoney);
	            fineBreachDetail.setPayAccountId(payAccount);
	            fineBreachDetail.setReceiveAccountId(platformAccount);
	            fineBreachDetail.setData(paramsMap);
	            dealDetailList.add(fineBreachDetail);
            }

            // 3.2 收取服务费给平台
            if ("2".equals(loan.getHandFeeKind())) {
            	if(repayPlan.getPayServiceFee()!=null && repayPlan.getPayServiceFee().compareTo(BigDecimal.ZERO)>0){
            		DealDetail serveDetail = new DealDetail();
                    serveDetail.setDealDetailType(DealDetailType.PLA_SERVE_FEE);
                    serveDetail.setPayAccountId(payAccount);
                    serveDetail.setReceiveAccountId(platformAccount);
                    serveDetail.setMoneyAmount(repayPlan.getPayServiceFee());
                    serveDetail.setData(paramsMap);
                    dealDetailList.add(serveDetail);
            	}
            }
            
            deal.setInnerSeqNo(InnerSeqNo.getMerchInstance());
            deal.setBusinessType(DealType.PAYBACK);
            deal.setOperator(operatorId);
            deal.setDealDetail(dealDetailList);
            deal.setBusinessId(repayPlan.getLoanId());

            dealResult = dealRecordService.createBusinessRecord(deal, repayedCallback);
            if (!dealResult.isSuccess()) {
                log.warn(dealResult.getMessage());
                throw new BusinessException("还款交易创建失败");
            }

            // 4. 更新还款计划表、收益计划表的内部交易流水号、状态
            BaseResult repayModResult = paymentPlanService.modifyPaymentPlan(repayPlan.getId(), PayState.UNCLEAR,
                    PayState.PAYING, payType, deal.getInnerSeqNo().getUniqueNo());
            if (!repayModResult.isSuccess()) {
                log.warn(repayModResult.getMessage());
                throw new BusinessException("还款计划表状态更改失败");
            }

            BaseResult incomeModResult = incomePlanService.modifyIncomePlan(repayPlan.getId(), PayState.UNCLEAR,
                    PayState.PAYING, deal.getInnerSeqNo().getUniqueNo());
            if (!incomeModResult.isSuccess()) {
                log.warn(incomeModResult.getMessage());
                throw new BusinessException("收益计划表状态更改失败");
            }
        }
        
        // 5. 查询是否有债券转让，如有，将债券标的状态改成已划转
        // tl_origin_id
        // tl_state( 将[0：待审核 1：初审已通过 2：初审未通过 3：转让招标中] 改成 7：已流标) 
        TransferLoan tf = new TransferLoan();
        tf.setOriginId(loanId);
        List<TransferLoanState> tlStates = new ArrayList<TransferLoanState>();
        tlStates.add(TransferLoanState.WAIT_REVIEW);
        tlStates.add(TransferLoanState.FIRST_REVIEW_PASS);
        tlStates.add(TransferLoanState.FIRST_REVIEW_FAIL);
        tlStates.add(TransferLoanState.TRANSFERING);
        tf.setTransferLoanStates(tlStates);
        
        ListResult<TransferLoan>  transLoan = transferLoanService.queryListByParam(tf);
//        TransferLoanTraceRecord traceRecord = null;
        if(transLoan.getData() != null && transLoan.getData().size()>0){
        	for(TransferLoan trLoan : transLoan.getData()){
//        		traceRecord = new TransferLoanTraceRecord();
//        		traceRecord.setCreator(operatorId);
//                traceRecord.setLoanId(trLoan.getOriginId());
//                traceRecord.setTransferLoanId(trLoan.getId());
//                traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.moneyTransferSucceed);
//                traceRecord.setOldTransferLoanState(trLoan.getTransferLoanState());
//                traceRecord.setNewTransferLoanState(TransferLoanState.MONEY_TRANSFERED);
//                traceRecord.setNote("转让标强制满标并划转");
                BaseResult changeResult = transferLoanManageService.cancelTransferLoan(trLoan.getId(),operatorId,"");
                if (!changeResult.isSuccess()) {
                    log.error("转让标流标失败！TransferLoanId:"+trLoan.getId()+","+changeResult.getMessage());
                    throw new BusinessException("转让标强制满标失败！"+changeResult.getMessage());
                }
        	}
        }
        //查询原来的还款计划最后一期
        int maxRepayPeriod = paymentPlanDao.findMaxPeriodByLoanId(loanId);
        // 6. 执行交易
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("BorrowId", String.valueOf(loanId));
        paramsMap.put("MerBillNo", deal.getInnerSeqNo().getUniqueNo());
        if (PayType.GUAR_CLEAR.equals(payType)) {
        	paramsMap.put("RepayTyp", "2");
        }else{
        	paramsMap.put("RepayTyp", "1");
        }
        paramsMap.put("repay_inst_tot", String.valueOf(maxRepayPeriod));//分期还款总期数 
        if (PayType.AHEAD_CLEAR.equals(payType)) {//分期还款当前期数,注意当前期数与总期数相同时视为还款结束。也就是若是提前还款这里要传总的期数
        	paramsMap.put("repay_inst_cur", String.valueOf(maxRepayPeriod));
        }else{
        	paramsMap.put("repay_inst_cur", String.valueOf(repayPlan.getLoanPeriod()));
        }
        
        Map<String, Object> ftpFileContentMap = getRePayFileData(loan,dealResult.getData().getDealRecords());
        paramsMap.put("fileData", FormatHelper.formatFtpFileContent(ftpFileContentMap));
        Map<String, String> resultMap = this.doRepay(paramsMap);
        if(!"000000".equals(resultMap.get("RespCode"))){
        	String errMsg = StringUtils.isNotEmpty(resultMap.get("RespDesc"))?resultMap.get("RespDesc"):"银行接口调用异常";
        	log.error(errMsg);
        	if("RED_TIMEOUT".equals(resultMap.get("RespCode"))){
        		result.setErrorMessage(CommonResultCode.FAIL_BUSY,resultMap.get("RespDesc"));
            }else{
            	throw new BusinessException(errMsg);
            }
        }else{
        	result.setMessage("还款申请成功，请等待处理结果！");
        }
        return result;
    }
    
	/**
	 * @content:渤海银行接口，构造还款申请文件数据fileData
	 * @author:夏同同
	 * @date:2016年4月10日 上午11:01:23
	 */
    private Map<String,Object> getRePayFileData(Loan loan,List<DealRecord> dealRecord){
    	if(loan == null || dealRecord == null || dealRecord.size() <= 0){
    		return null;
    	}
    	//获取借款人平台账号，因为担保代还时dealRecord里的付款账户是担保账户，不是借款人账户，但是接口要求是借款人账户，所以这里通过Loan来获取
    	UserDO userDO = userService.findById(loan.getLoanUserId()).getData();

        userDO.setAccountCategory(AccountCategory.LOANACCOUNT.type);
        AccountInfoDO loanAccount = accountInfoService.getAccountByCategory(userDO);

    	/** ftp文件数据*/
    	Map<String, Object> ftpFileContentMap = new HashMap<String, Object>();
    	/** 汇总数据*/
    	Map<String, String> summaryMap=new LinkedHashMap<String, String>();
    	/** 明细数据*/
    	List<Map<String, String>> detailListMap= new ArrayList<Map<String,String>>();
    	Map<String, Map<String,String>> detailMap = new HashMap<String, Map<String,String>>();
    	BigDecimal FeeAmt = BigDecimal.ZERO;
    	BigDecimal TransAmt = BigDecimal.ZERO;
    	for(int i = 0;i<dealRecord.size();i++){
    		DealRecord dr = dealRecord.get(i);
    		if(DealDetailType.PAYBACK_CAPITAL.getType() == dr.getDetailType().intValue()
    				|| DealDetailType.PAYBACK_INTEREST.getType() == dr.getDetailType().intValue()
    				|| DealDetailType.PAYBACK_OVERDUE_FINE.getType() == dr.getDetailType().intValue()){
    			TransAmt = TransAmt.add(dr.getMoneyAmount());
    			//获取收款账户
    			String receiveAccount = dr.getReceiveAccount();
    			if(!detailMap.containsKey(receiveAccount)){
    				Map<String, String> linkedMap = new LinkedHashMap<String, String>();
    				linkedMap.put("ID", SeqnoHelper.get8UUID());
        			linkedMap.put("PlaCustId", receiveAccount);
        			linkedMap.put("TransAmt", FormatHelper.changeY2F(0.00));
        			linkedMap.put("Interest", FormatHelper.changeY2F(0.00));
        			linkedMap.put("Inves_fee", FormatHelper.changeY2F(0.00));
    				detailMap.put(receiveAccount, linkedMap);
    			}
    			Map<String, String> linkedMap = detailMap.get(receiveAccount);
    			if(DealDetailType.PAYBACK_CAPITAL.getType() == dr.getDetailType().intValue()){
    				//累加到TransAmt字段
    				String money =  FormatHelper.changeY2F(dr.getMoneyAmount());
    				String dtTransAmt = linkedMap.get("TransAmt");
    				linkedMap.put("TransAmt",(new BigDecimal(dtTransAmt).add(new BigDecimal(money)))+"");
    			}else{
    				//累加到Interest字段
    				String money =  FormatHelper.changeY2F(dr.getMoneyAmount());
    				String dtInterest = linkedMap.get("Interest");
    				linkedMap.put("Interest",(new BigDecimal(dtInterest).add(new BigDecimal(money)))+"");
    			}
	    	}else{
	    		FeeAmt = FeeAmt.add(dr.getMoneyAmount());
	    	}
    	}
    	for(String key : detailMap.keySet()){
    		detailListMap.add(detailMap.get(key));
    	}
    	TransAmt = TransAmt.add(FeeAmt);
    	summaryMap.put("char_set", ConfigHelper.getConfig("charset"));
        summaryMap.put("partner_id", ConfigHelper.getConfig("merchantId"));
        summaryMap.put("MerBillNo", dealRecord.get(0).getInnerSeqNo());
        
        summaryMap.put("TransAmt", FormatHelper.changeY2F(TransAmt));
        summaryMap.put("FeeAmt", FormatHelper.changeY2F(FeeAmt));
        summaryMap.put("BorrowId", String.valueOf(loan.getLoanId()));
        
        summaryMap.put("BorrowerAmt", FormatHelper.changeY2F(loan.getLoanMoney()));
        summaryMap.put("BorrCustId", loanAccount.getAccountNo());
        summaryMap.put("MerPriv", dealRecord.get(0).getInnerSeqNo());
    	summaryMap.put("TotalNum", String.valueOf(detailListMap.size()));
    	
    	ftpFileContentMap.put("summaryMap", summaryMap);
        ftpFileContentMap.put("detailListMap", detailListMap);
    	return ftpFileContentMap;
    }
    
    /**
     * 渤海银行接口，还款(调用接口)
     * 1、ftp上传放款文件明细
     * 2、放款申请接口
     */
    private Map<String, String> doRepay(Map<String, String> map) {
    	Map<String, String> resultMap = new HashMap<String, String>();
    	SftpTool ftp = new SftpTool();
    	// 本地路径
        String localPath = ConfigHelper.getSftpLocalPath();
        // 目标路径
        String remotePath = ConfigHelper.getSftpRemotePath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");
        //还款临时修改
//        String remotePath = ConfigHelper.getSftpRemotePath()+"20190219";
        //上传文件名
        String merBillNo = map.get("MerBillNo");
        String fileName = FileNameUtil.getFileName("FileRepayment", "txt",merBillNo);
        //上送成功标志文件名
        String uploadSuccessFileName = FileNameUtil.getFileNameBySuffix(fileName, "OK");
        //数据写入文件
        String fileData = map.get("fileData");
        boolean isWriteSuccess = FileUtils.writeByBufferedWriter(fileData, localPath+fileName);
        if(isWriteSuccess){
        	//ftp上传文件
        	ftp.connect();
        	boolean isUploadSuccess = ftp.uploadFile(remotePath, fileName, localPath, fileName);
        	isUploadSuccess = ftp.uploadFile(remotePath, uploadSuccessFileName, localPath, uploadSuccessFileName);
        	ftp.disconnect();
        	if(isUploadSuccess){
        		//发送请求,获取数据
        		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        		paramsMap.put("biz_type", "FileRepayment");
        		paramsMap.put("MerBillNo", merBillNo);
        		paramsMap.put("BorrowId", map.get("BorrowId"));
        		paramsMap.put("RepayTyp", map.get("RepayTyp"));
        		paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("repayNotifyUrl"));
        		paramsMap.put("repay_inst_tot", map.get("repay_inst_tot"));//分期还款总期数 
        	    paramsMap.put("repay_inst_cur", map.get("repay_inst_cur"));//分期还款当前期数,注意当前期数与总期数相同时视为还款结束。也就是若是提前还款这里要传总的期数
        	    paramsMap.put("FileName", fileName);
        		paramsMap.put("MerPriv", "");
        		resultMap = ExchangeDataUtils.submitData(paramsMap);
        	}else{
        		resultMap.put("RespCode", "fail");
        		resultMap.put("RespDesc", "还款文件上传失败，请重新上传!");
        	}
        }else{
        	resultMap.put("RespCode", "fail");
    		resultMap.put("RespDesc", "还款文件写入失败!");
        }
        return resultMap;
    }
    
    
	/**
	 * @content:计算逾期罚金合违约罚金
	 * @author:夏同同
	 * @date:2016年4月10日 上午11:01:23
	 */
    private  Map<String, BigDecimal>  computePulishMoney(PaymentPlan repayPlan) {
		Map<String, BigDecimal> pulishMoneys = new HashMap<String, BigDecimal>();
		BigDecimal pulishMoney; // 逾期罚金
		BigDecimal pulishBreachMoney; // 违约罚金

        //edit by 夏同同 20160421 计算天数时不应该考虑时分秒
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        // 判断是否逾期
        DateTime nowTime = new DateTime(sf.format(new Date()));
        DateTime planPayTime = new DateTime(sf.format(repayPlan.getPaytime()));

        // 如果逾期则查询罚息利率并计算罚息
        if (nowTime.isAfter(planPayTime) 
        		&& !nowTime.toString("MM/dd/yyyy").equals(planPayTime.toString("MM/dd/yyyy"))) {
        	// 逾期罚息利率
            PlainResult<SysConfig> punishRateResult = sysConfigService
                    .querySysConfig(SysConfigEntry.PUNISH_INTEREST_RATE);
            if (!punishRateResult.isSuccess()) {
                throw new BusinessException("逾期罚息利率查询失败");
            }
			// 违约罚息利率
			PlainResult<SysConfig> punishBreachRateResult = sysConfigService
					.querySysConfig(SysConfigEntry.PUNISH_BREACH_INTEREST_RATE);
			if(!punishBreachRateResult.isSuccess()){
				throw new BusinessException("违约罚息利率查询失败");
			}
            
            
			// 天逾期罚息利率
            BigDecimal rate = new BigDecimal(punishRateResult.getData().getConfValue()).divide(
                    new BigDecimal(100 * 360), 10,BigDecimal.ROUND_HALF_UP);
            double punishRate = rate.doubleValue();
            
            // 天违约罚息利率
			BigDecimal breachRate = new BigDecimal(punishBreachRateResult.getData().getConfValue())
					.divide(new BigDecimal(100 * 360), 10, BigDecimal.ROUND_HALF_UP);
			double punishBreachRate = breachRate.doubleValue();
            //天数
            int expiryDays = Days.daysBetween(planPayTime, nowTime).getDays();
            //本标本期未还款的本金
            PlainResult<BigDecimal> remainingPrincipalResult=paymentPlanService.
            		queryRemainPrincipalByLoanidAndPeriod(repayPlan.getLoanId(), repayPlan.getLoanPeriod());
            BigDecimal remainingPrincipal=remainingPrincipalResult.getData();
			// 本标本期未还款的利息
			PlainResult<BigDecimal> remainingInterestResult = paymentPlanService
					.queryRemainInterestByLoanidAndPeriod(repayPlan.getLoanId(), repayPlan.getLoanPeriod());
			BigDecimal remainingInterest = remainingInterestResult.getData();
			/**
			 * 修改罚息计算公式,夏同同,2016年4月10日 上午11:01:23 罚息 = 剩余本金 * 罚息利率 * 逾期天数 +
			 * 剩余罚金(作废) 罚息 = (本期剩余本金+本期剩余利息） * 罚息利率 * 逾期天数 + 剩余罚金 剩余本金 = 应还本金 -
			 * 实还本金(作废) 本期剩余本金 = 本期应还本金 - 本期实还本金 本期剩余利息 = 本期应还利息 - 本期实还利息
			 * 罚息利率=罚息月利率/100/30 逾期天数 = 当前日期 - （实还日期（如果借款人还过部分款） 或
			 * 应还日期（如果借款人没有还过款））
			 */
			//计算逾期罚金
			pulishMoney = (remainingPrincipal.add(remainingInterest)).multiply(new BigDecimal(punishRate * expiryDays))
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			//计算违约罚金
			pulishBreachMoney = (remainingPrincipal.add(remainingInterest)).multiply(new BigDecimal(punishBreachRate * expiryDays))
					.setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            pulishMoney = BigDecimal.ZERO;
			pulishBreachMoney = BigDecimal.ZERO;
        }

		pulishMoneys.put("pulishMoney", pulishMoney);
		pulishMoneys.put("pulishBreachMoney", pulishBreachMoney);
		return pulishMoneys;
    }

    @Override
    public PlainResult<BigDecimal> queryPulishMoney(int repayPlanId) {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();

        PaymentPlanDO repayPlanDO = paymentPlanDao.findById(repayPlanId);
        if (repayPlanDO == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "该期还款计划表不存在");
        }

        BigDecimal pulishMoney;
        //edit by 夏同同 20160421 计算天数时不应该考虑时分秒
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        // 判断是否逾期
        DateTime nowTime = new DateTime(sf.format(new Date()));
        DateTime planPayTime = new DateTime(sf.format(repayPlanDO.getPpPaytime()));
        

        // 如果逾期则查询罚息利率并计算罚息
        if (nowTime.isAfter(planPayTime) && !nowTime.toString("MM/dd/yyyy").equals(planPayTime.toString("MM/dd/yyyy"))) {
            PlainResult<SysConfig> punishRateResult = sysConfigService
                    .querySysConfig(SysConfigEntry.PUNISH_INTEREST_RATE);
            if (!punishRateResult.isSuccess()) {
                throw new BusinessException("罚息利率查询失败");
            }
            MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN);

            BigDecimal rate = new BigDecimal(punishRateResult.getData().getConfValue()).divide(
                    new BigDecimal(100 * 360), mc);

            double punishRate = rate.doubleValue();
            int expiryDays = Days.daysBetween(planPayTime, nowTime).getDays();

            /**
             * 罚息 = 剩余本金 * 罚息利率 * 逾期天数 + 剩余罚金<br>
             * 剩余本金 = 应还本金 - 实还本金<br>
             * 罚息利率=罚息月利率/100/30<br>
             * 逾期天数 = 当前日期 - （实还日期（如果借款人还过部分款） 或 应还日期（如果借款人没有还过款））<br>
             */
            // 当应还本金为0时，是否罚息为0
            pulishMoney = repayPlanDO.getPpPayInterest().multiply(new BigDecimal(punishRate * expiryDays));
            pulishMoney=pulishMoney.abs(mc);
        } else {
            pulishMoney = BigDecimal.ZERO;
        }
        result.setData(pulishMoney);
        return result;
    }

    private UserType queryUserTypeByUserId(int userId) {
        PlainResult<User> userResult = userService.findEntityById(userId);
        if (!userResult.isSuccess() || userResult.getData() == null) {
            throw new BusinessException("用户类型查询失败");
        }

        return userResult.getData().getUserType();
    }

    @Override
    public BaseResult repay(boolean flag, IncomePlan incomePlan, PlainResult<PaymentPlan> repayPlanResult, int loanId,
                            int repayPlanId, PayType payType, int operatorId) {
        return null;
    }

}
