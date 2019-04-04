/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.repay;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.PayRecordDao;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.SysConfig;
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
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilder;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilderFactory;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.ActivityRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
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
//@Service
public class DmRepayServiceImpl implements RepayService {
    private static final Log      log = LogFactory.getLog(DmRepayServiceImpl.class);

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
     * 5. 执行交易<br>
     */
    // check 还款和转让同时
    // check 还款和收购同时
    // check 还款和资金划转同时， FullTransferServiceImpl 有还款行为则自动流标
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult repay(int loanId, int repayPlanId, PayType payType, int operatorId) {
        BaseResult result = new BaseResult();

        // 1. 前置条件判断
        // 1.2 加行级锁
        PaymentPlanDO paymentLock = paymentPlanDao.findWithLock(repayPlanId);
        if (paymentLock == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "还款时加行级锁失败");
        }

        // 1.1 查询还款计划
        //edit by 夏同同 20160507 解决平台代还的计划，借款人未还清时，平台无法继续代还的问题
        PlainResult<PaymentPlan> repayPlanResult = null;
        if (!PayType.PLA_CLEAR.equals(payType)){
            //非平台待还时，查询下一条未还清的还款计划
            repayPlanResult = paymentPlanService.queryNextPaymentPlan(loanId);
        }else{
            //平台代还时，查询下一条未还清且不是平台待还的还款计划
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

        if (PayType.PLA_CLEAR.equals(repayPlan.getPayType()) && PayType.PLA_CLEAR.equals(payType)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台已经对本期还款进行了代还不能再次代还");
        }

        Deal deal;

        /*
         * 对平台代还的还款计划进行还款
         */
        if (repayPlan.getReplaceState()) {
            // 1.4 查询账户信息
            // 借款人
            UserIdentity loanee = new UserIdentity();
            loanee.setUserId(repayPlan.getLoanee());
            loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
            loanee.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
            PlainResult<Account> loaneeAccountResult = accountInfoService.queryByUserId(loanee);
            if (!loaneeAccountResult.isSuccess() || loaneeAccountResult.getData() == null) {
                log.warn(loaneeAccountResult.getMessage());
                throw new BusinessException("用户账户查询失败");
            }
            String loaneeAccountNo = loaneeAccountResult.getData().getAccountNo();

            // 平台账户
            PlainResult<SysConfig> platformAccountResult = sysConfigService
                    .querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
            if (!platformAccountResult.isSuccess() || platformAccountResult.getData() == null) {
                return result.setError(CommonResultCode.BIZ_ERROR, "平台账户查询失败");
            }

            String platformAccountNo = platformAccountResult.getData().getConfValue();

            /**
			 * @content:2. 计算逾期罚金和违约罚金
			 * @author:夏同同
			 * @date:2016年4月10日 上午10:01:23
			 */
            Map<String, BigDecimal> pulishMoneys = computePulishMoney(repayPlan);
            BigDecimal pulishMoney = pulishMoneys.get("pulishMoney");
            BigDecimal pulishBreachMoney = pulishMoneys.get("pulishBreachMoney");
            if (pulishMoney.compareTo(BigDecimal.ZERO) > 0) {
           	   // 更新应还罚金、应还总额
               BaseResult ppModifyResult = paymentPlanService.modifyPaymentPlan(repayPlan.getId(), pulishMoney,pulishBreachMoney,
                       PayState.UNCLEAR);
               if (!ppModifyResult.isSuccess()) {
                   return result.setError(CommonResultCode.BIZ_ERROR, "还款计划增加罚息失败");
               }
               //同步还款计划
               repayPlan.setPayFine(repayPlan.getPayFine().add(pulishMoney));
               repayPlan.setPayBreachFine(repayPlan.getPayBreachFine().add(pulishBreachMoney));
               repayPlan.setPayTotalMoney(repayPlan.getPayTotalMoney().add(pulishMoney).add(pulishBreachMoney));
               
               //分配罚金 （ 夏同同：是将逾期罚金分配给投资人，与违约罚金无关）
               //1.查询原始标
               //2.查询投资列表
               //3.查询本期收益计划列表
               //4.调用分配查询接口              
               ListResult<Loan> loans=loanService.queryByIds(Arrays.asList(repayPlan.getLoanId()), null);
               if(!loans.isSuccess()){
               	throw new BusinessException(loans.getMessage());
               }                
               ListResult<Invest> invests=investService.findListByParamEarning(repayPlan.getLoanId());
               
               IncomePlan incomPlan=new IncomePlan();
               incomPlan.setPaymentPlanId(repayPlan.getId());
               incomPlan.setIncomePlanState(IncomePlanState.GOING);
               ListResult<IncomePlan> incomePlanResult=incomePlanService.queryIncomePlanList(incomPlan);
               
               PlanBuilder planBuilder = PlanBuilderFactory.createPlanBuilder(LoanPeriodUnit.DAY);
               List<IncomePlan> incomePlans=planBuilder.distributionPenalty(loans.getData().get(0), invests.getData(),incomePlanResult.getData(),pulishMoney);
               
               //批量更新收益计划
               BaseResult baseResult=incomePlanService.updateIncomePlanByIncome(incomePlans);
               if(!baseResult.isSuccess()){
               	throw new BusinessException(baseResult.getMessage());
               }
           }

            // 3. 创建交易记录
            deal = new Deal();
            List<DealDetail> dealDetailList = new ArrayList<DealDetail>();

            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("ReplaceState", repayPlan.getReplaceState());
            paramsMap.put("BatchNo",loanId);     //网贷平台标号
            // 3.1 收取本金、利息、超期罚金 给投资人；服务费，违约罚金给平台
            DealDetail captitalDetail = new DealDetail();
            captitalDetail.setDealDetailType(DealDetailType.PAYBACK_CAPITAL);
            captitalDetail.setMoneyAmount(repayPlan.getPayCapital());
            captitalDetail.setPayAccountId(loaneeAccountNo);
            captitalDetail.setReceiveAccountId(platformAccountNo);
            captitalDetail.setData(paramsMap);
            dealDetailList.add(captitalDetail);

            DealDetail interestDetail = new DealDetail();
            interestDetail.setDealDetailType(DealDetailType.PAYBACK_INTEREST);
            interestDetail.setMoneyAmount(repayPlan.getPayInterest());
            interestDetail.setPayAccountId(loaneeAccountNo);
            interestDetail.setReceiveAccountId(platformAccountNo);
            interestDetail.setData(paramsMap);
            dealDetailList.add(interestDetail);

            DealDetail fineDetail = new DealDetail();
            fineDetail.setDealDetailType(DealDetailType.PAYBACK_OVERDUE_FINE);
            fineDetail.setMoneyAmount(pulishMoney);
            fineDetail.setPayAccountId(loaneeAccountNo);
            fineDetail.setReceiveAccountId(platformAccountNo);
            fineDetail.setData(paramsMap);
            dealDetailList.add(fineDetail);
            
            /*
             *  违约罚金 夏同同/2016-05-10
             *  注意：下面的如果大于三项或者每项的收款账户不一致时，会出现错误。
             *  现在只有两项，且违约罚金和服务费的收款户都是平台
             */
            if(pulishBreachMoney.compareTo(BigDecimal.ZERO) > 0){
	            DealDetail fineBreachDetail = new DealDetail();
	            fineBreachDetail.setDealDetailType(DealDetailType.PAYBACK_BREACH_FINE);
	            fineBreachDetail.setMoneyAmount(pulishBreachMoney);
	            fineBreachDetail.setPayAccountId(loaneeAccountNo);
	            fineBreachDetail.setReceiveAccountId(platformAccountNo);
	            fineBreachDetail.setData(paramsMap);
	            dealDetailList.add(fineBreachDetail);
            }

            // 3.2 收取服务费给平台
            if(repayPlan.getPayServiceFee()!=null && repayPlan.getPayServiceFee().compareTo(BigDecimal.ZERO)>0){
            	  DealDetail serveDetail = new DealDetail();
                  serveDetail.setDealDetailType(DealDetailType.PLA_SERVE_FEE);
                  serveDetail.setPayAccountId(loaneeAccountNo);
                  serveDetail.setReceiveAccountId(platformAccountNo);
                  serveDetail.setMoneyAmount(repayPlan.getPayServiceFee());
                  serveDetail.setData(paramsMap);
                  dealDetailList.add(serveDetail);
            }
            
            deal.setInnerSeqNo(InnerSeqNo.getInstance());
            deal.setBusinessType(DealType.PAYBACK);
            deal.setOperator(operatorId);
            deal.setBusinessId(repayPlan.getLoanId());
            deal.setDealDetail(dealDetailList);

            PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, repayedCallback);
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

        }
        /*
         * 对平台未代还的正常还款计划进行还款
         */

        else {
            // 1.3 查询收益计划
            IncomePlan pojo = new IncomePlan();
            pojo.setPaymentPlanId(repayPlan.getId());
            pojo.setIncomePlanState(IncomePlanState.GOING);
            ListResult<IncomePlan> incomePlanResult = incomePlanService.queryIncomePlanList(pojo); // TODO CHECK STATE
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
            // -----------------平台代还--------------------
            final String payAccount;
            final boolean takeServeFee;
            // add by 夏同同  违约罚金标记
            final boolean takeBreachFee;
            final BigDecimal pulishMoney;
            //增加违约罚金  夏同同/2016-4-10
            final BigDecimal pulishBreachMoney;

            if (PayType.PLA_CLEAR.equals(payType)) {
                payAccount = platformAccount;
                takeServeFee = false;
                takeBreachFee = false;
            } else {
            	takeBreachFee = true;
                payAccount = loanee.getUserId() + "|" + loanee.getUserType().getType();
                takeServeFee = (repayPlan.getPayServiceFee() != null)
                        && (repayPlan.getPayServiceFee().compareTo(BigDecimal.ZERO) > 0);
            }

            /**
			 * @content:2. 计算逾期罚金和违约罚金
			 * @author:夏同同
			 * @date:2016年4月10日 上午10:01:23
			 */
            Map<String, BigDecimal> pulishMoneys = computePulishMoney(repayPlan);
            pulishMoney = pulishMoneys.get("pulishMoney");
            pulishBreachMoney = pulishMoneys.get("pulishBreachMoney");
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
                ListResult<Loan> loans=loanService.queryByIds(Arrays.asList(repayPlan.getLoanId()), null);
                if(!loans.isSuccess()){
                	throw new BusinessException(loans.getMessage());
                }                
                ListResult<Invest> invests=investService.findListByParamEarning(repayPlan.getLoanId());
                
                IncomePlan incomPlan=new IncomePlan();
                incomPlan.setPaymentPlanId(repayPlan.getId());
                incomPlan.setIncomePlanState(IncomePlanState.GOING);
                ListResult<IncomePlan> incomePlanResultx=incomePlanService.queryIncomePlanList(incomPlan);
                
                PlanBuilder planBuilder = PlanBuilderFactory.createPlanBuilder(LoanPeriodUnit.DAY);
                List<IncomePlan> incomePlans=planBuilder.distributionPenalty(loans.getData().get(0), invests.getData(),incomePlanResultx.getData(),pulishMoney);
                
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
            if (takeServeFee) {
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

            deal.setInnerSeqNo(InnerSeqNo.getInstance());
            deal.setBusinessType(DealType.PAYBACK);
            deal.setOperator(operatorId);
            deal.setDealDetail(dealDetailList);
            deal.setBusinessId(repayPlan.getLoanId());

            PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, repayedCallback);
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
        // 5. 执行交易
        BaseResult invokeResult = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!invokeResult.isSuccess()) {
            log.warn(invokeResult.getMessage());
            throw new BusinessException(invokeResult.getMessage());
        }

        return result;
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
        if (nowTime.isAfter(planPayTime) && !nowTime.toString("MM/dd/yyyy").equals(planPayTime.toString("MM/dd/yyyy"))) {
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
        // TODO Auto-generated method stub
        return null;
    }

}
