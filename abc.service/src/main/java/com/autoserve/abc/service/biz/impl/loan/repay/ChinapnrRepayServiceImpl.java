/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.repay;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.ActivityRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
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
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 还款服务实现
 * 
 * @author segen189 2014年11月29日 下午2:32:40
 */
//@Service
public class ChinapnrRepayServiceImpl implements RepayService {
    private static final Log      log = LogFactory.getLog(ChinapnrRepayServiceImpl.class);

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
    private InvestQueryService    investQueryService;

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

//        // 1. 前置条件判断
//        // 1.2 加行级锁
//        PaymentPlanDO paymentLock = paymentPlanDao.findWithLock(repayPlanId);
//        if (paymentLock == null) {
//            return result.setError(CommonResultCode.BIZ_ERROR, "还款时加行级锁失败");
//        }
//
//        // 1.1 查询还款计划
//        PlainResult<PaymentPlan> repayPlanResult = paymentPlanService.queryNextPaymentPlan(loanId);
//        if (!repayPlanResult.isSuccess()) {
//            return result.setError(CommonResultCode.BIZ_ERROR, "还款计划表查询失败");
//        }
//
//        final PaymentPlan repayPlan = repayPlanResult.getData();
//        if (repayPlan == null) {
//            return result.setError(CommonResultCode.BIZ_ERROR, "没有需要还款的还款计划");
//        } else if (repayPlanId != repayPlan.getId()) {
//            return result.setError(CommonResultCode.BIZ_ERROR, "请按期数顺序进行还款");
//        }
//
//        if (PayType.PLA_CLEAR.equals(repayPlan.getPayType()) && PayType.PLA_CLEAR.equals(payType)) {
//            return result.setError(CommonResultCode.BIZ_ERROR, "平台已经对本期还款进行了代还不能再次代还");
//        }
//
//        Deal deal;
//
//        /*
//         * 对平台代还的还款计划进行还款
//         */
//
//        if (repayPlan.getReplaceState()) {
//            // 1.4 查询账户信息
//            // 借款人
//            UserIdentity loanee = new UserIdentity();
//            loanee.setUserId(repayPlan.getLoanee());
//            loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
//
//            PlainResult<Account> loaneeAccountResult = accountInfoService.queryByUserId(loanee);
//            if (!loaneeAccountResult.isSuccess() || loaneeAccountResult.getData() == null) {
//                log.warn(loaneeAccountResult.getMessage());
//                throw new BusinessException("用户账户查询失败");
//            }
//            String loaneeAccountNo = loaneeAccountResult.getData().getAccountNo();
//
//            // 平台账户
//            PlainResult<SysConfig> platformAccountResult = sysConfigService
//                    .querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
//            if (!platformAccountResult.isSuccess() || platformAccountResult.getData() == null) {
//                return result.setError(CommonResultCode.BIZ_ERROR, "平台账户查询失败");
//            }
//
//            String platformAccountNo = platformAccountResult.getData().getConfValue();
//
//            // 2. 查询罚息利率并计算罚息
//            BigDecimal pulishMoney = computePulishMoney(repayPlan);
//
//            // 2.1 如果有罚息，则更新还款计划表中的应还罚息、应还总额字段
//            // 2.2 如果有罚息，更新收益计划表中的应还罚息、应还总额字段
//            if (pulishMoney.compareTo(BigDecimal.ZERO) > 0) {
//                // 应还本金、应还利息、应还罚金、应还平台服务费、应还平台担保费、应还总额、剩余罚金、期数、是否还清、应还日期
//                BaseResult ppModifyResult = paymentPlanService.modifyPaymentPlan(repayPlan.getId(), pulishMoney,
//                        PayState.PAYING);
//                if (!ppModifyResult.isSuccess()) {
//                    return result.setError(CommonResultCode.BIZ_ERROR, "还款计划增加罚息失败");
//                }
//
//                // 批量更新，是否一个连接待检查
//                // 新的收益计划表中，每个人应得罚息＝投资人本期剩余罚息+借款人本期应还罚息总额*(投资人本期应回收本金/借款人本期应还本金)
//                //                          ＝投资人本期剩余罚息+投资人本期应回收本金*(借款人本期应还罚息总额/借款人本期应还本金)
//                // 罚息因子=(借款人本期应还罚息总额/借款人本期应还本金)
//                //解释说明(项目中只有两种还款方式：按月还息到期还本、到期还本付息，所以不是最后一期的话，借款人本期应还本金为0，那么罚金分配不正确)
//                //初步更改为：每个人应得罚息＝投资人本期剩余罚息+借款人本期应还罚息总额*(投资人本期应回收总额/借款人本期应还总额)
//                BaseResult ipModifyResult = incomePlanService.modifyIncomePlanByAllocPunishMoney(repayPlan.getId(),
//                        pulishMoney.doubleValue(), null);
//                if (!ipModifyResult.isSuccess()) {
//                    return result.setError(CommonResultCode.BIZ_ERROR, "收益计划增加罚息失败");
//                }
//            }
//
//            // 3. 创建交易记录
//            deal = new Deal();
//            List<DealDetail> dealDetailList = new ArrayList<DealDetail>();
//
//            Map<String, Boolean> paramsMap = new HashMap<String, Boolean>();
//            paramsMap.put("ReplaceState", repayPlan.getReplaceState());
//            // 3.1 收取本金、利息、罚息 给投资人
//            // 还款本金
//            DealDetail captitalDetail = new DealDetail();
//            captitalDetail.setDealDetailType(DealDetailType.PAYBACK_CAPITAL);
//            captitalDetail.setMoneyAmount(repayPlan.getPayCapital());
//            captitalDetail.setPayAccountId(loaneeAccountNo);
//            captitalDetail.setReceiveAccountId(platformAccountNo);
//            captitalDetail.setData(paramsMap);
//            dealDetailList.add(captitalDetail);
//
//            // 还款利息
//            DealDetail interestDetail = new DealDetail();
//            interestDetail.setDealDetailType(DealDetailType.PAYBACK_INTEREST);
//            interestDetail.setMoneyAmount(repayPlan.getPayInterest());
//            interestDetail.setPayAccountId(loaneeAccountNo);
//            interestDetail.setReceiveAccountId(platformAccountNo);
//            interestDetail.setData(paramsMap);
//            dealDetailList.add(interestDetail);
//
//            // 超期罚金
//            if (pulishMoney.compareTo(BigDecimal.ZERO) > 0) {
//                DealDetail fineDetail = new DealDetail();
//                fineDetail.setDealDetailType(DealDetailType.PAYBACK_OVERDUE_FINE);
//                fineDetail.setMoneyAmount(pulishMoney);
//                fineDetail.setPayAccountId(loaneeAccountNo);
//                fineDetail.setReceiveAccountId(platformAccountNo);
//                fineDetail.setData(paramsMap);
//                dealDetailList.add(fineDetail);
//            }
//
//            // 3.2 收取服务费给平台
//            DealDetail serveDetail = new DealDetail();
//
//            serveDetail.setDealDetailType(DealDetailType.PLA_SERVE_FEE);
//            serveDetail.setPayAccountId(loaneeAccountNo);
//            serveDetail.setReceiveAccountId(platformAccountNo);
//            serveDetail.setMoneyAmount(repayPlan.getCollectServiceFee());
//            serveDetail.setData(paramsMap);
//            dealDetailList.add(serveDetail);
//
//            deal.setInnerSeqNo(InnerSeqNo.getInstance());
//            deal.setBusinessType(DealType.PAYBACK);
//            deal.setOperator(operatorId);
//            deal.setBusinessId(repayPlan.getLoanId());
//            deal.setDealDetail(dealDetailList);
//
//            PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, repayedCallback);
//            if (!dealResult.isSuccess()) {
//                log.warn(dealResult.getMessage());
//                throw new BusinessException("还款交易创建失败");
//            }
//
//            // 4. 更新还款计划表、收益计划表的内部交易流水号、状态
//            BaseResult repayModResult = paymentPlanService.modifyPaymentPlan(repayPlan.getId(), PayState.UNCLEAR,
//                    PayState.PAYING, payType, deal.getInnerSeqNo().getUniqueNo());
//            if (!repayModResult.isSuccess()) {
//                log.warn(repayModResult.getMessage());
//                throw new BusinessException("还款计划表状态更改失败");
//            }
//
//        }
//        /*
//         * 对平台未代还的正常还款计划进行还款
//         */
//
//        else {
//            // 1.3 查询收益计划
//            IncomePlan pojo = new IncomePlan();
//            pojo.setPaymentPlanId(repayPlan.getId());
//            pojo.setIncomePlanState(IncomePlanState.GOING);
//            ListResult<IncomePlan> incomePlanResult = incomePlanService.queryIncomePlanList(pojo); // TODO CHECK STATE
//            if (!incomePlanResult.isSuccess()) {
//                return result.setError(CommonResultCode.BIZ_ERROR, "收益计划表查询失败");
//            }
//
//            final List<IncomePlan> incomePlanList = incomePlanResult.getData();
//
//            // 1.4 查询账户信息 payTotalMoney
//            // 投资人
//            final Map<Integer, UserType> incomeUserTypeMap = new HashMap<Integer, UserType>();
//            List<UserIdentity> userList = new ArrayList<UserIdentity>();
//            for (IncomePlan incomePlan : incomePlanList) {
//                UserIdentity investor = new UserIdentity();
//                investor.setUserId(incomePlan.getBeneficiary());
//                investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
//
//                userList.add(investor);
//
//                incomeUserTypeMap.put(incomePlan.getId(), investor.getUserType());
//            }
//
//            // 借款人
//            UserIdentity loanee = new UserIdentity();
//            loanee.setUserId(repayPlan.getLoanee());
//            loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
//            userList.add(loanee);
//
//            ListResult<Account> accountResult = accountInfoService.queryByUserIds(userList);
//            if (!accountResult.isSuccess()) {
//                log.warn(accountResult.getMessage());
//                throw new BusinessException("用户账户查询失败");
//            }
//
//            // 用户ID和账号的映射关系
//            List<Account> userAccountList = accountResult.getData();
//            final Map<String, String> userAccountMap = new HashMap<String, String>();
//            for (Account account : userAccountList) {
//                userAccountMap.put(account.getAccountUserId() + "|" + account.getAccountUserType().getType(),
//                        account.getAccountNo());
//            }
//
//            // 平台账户
//            PlainResult<SysConfig> platformAccountResult = sysConfigService
//                    .querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
//            if (!platformAccountResult.isSuccess()) {
//                return result.setError(CommonResultCode.BIZ_ERROR, "平台账户查询失败");
//            }
//
//            String platformAccount = platformAccountResult.getData().getConfValue();
//
//            // 目前还款是全额还款
//            // -----------------正常还款--------------------
//            // -----------------强制还款--------------------
//            // -----------------平台代还--------------------
//            final String payAccount;
//            final boolean takeServeFee;
//            final BigDecimal pulishMoney;
//
//            if (PayType.PLA_CLEAR.equals(payType)) {
//                payAccount = platformAccount;
//                takeServeFee = false;
//            } else {
//                payAccount = loanee.getUserId() + "|" + loanee.getUserType().getType();
//                takeServeFee = (repayPlan.getCollectServiceFee() != null)
//                        && (repayPlan.getCollectServiceFee().compareTo(BigDecimal.ZERO) > 0);
//            }
//
//            // 2. 判断是否逾期
//            DateTime nowTime = DateTime.now();
//            DateTime planPayTime = new DateTime(repayPlan.getPaytime());
//
//            // 2.1 如果逾期则查询罚息利率并计算罚息
//            if (nowTime.isAfter(planPayTime)) {
//                PlainResult<SysConfig> punishRateResult = sysConfigService
//                        .querySysConfig(SysConfigEntry.PUNISH_INTEREST_RATE);
//                if (!punishRateResult.isSuccess()) {
//                    return result.setError(CommonResultCode.BIZ_ERROR, "罚息利率查询失败");
//                }
//
//                double punishRate = Double.valueOf(punishRateResult.getData().getConfValue()) / 100;
//                int expiryDays = Days.daysBetween(planPayTime, nowTime).getDays();
//
//                /**
//                 * 每月罚息 = 每月利息 * 罚息利率 * 逾期天数 <br>
//                 * 剩余本金 = 应还本金 - 实还本金<br>
//                 * 罚息利率=罚息月利率/100/30<br>
//                 * 逾期天数 = 当前日期 - （实还日期（如果借款人还过部分款） 或 应还日期（如果借款人没有还过款））<br>
//                 */
//                // 当应还本金为0时，是否罚息为0
//                pulishMoney = repayPlan.getPayInterest().multiply(new BigDecimal(punishRate * expiryDays));
//            } else {
//                pulishMoney = BigDecimal.ZERO;
//            }
//
//            // 2.2 如果有罚息，则更新还款计划表中的应还罚息、应还总额字段
//            // 2.3 如果有罚息，更新收益计划表中的应还罚息、应还总额字段
//            if (pulishMoney.compareTo(BigDecimal.ZERO) > 0) {
//                // 应还本金、应还利息、应还罚金、应还平台服务费、应还平台担保费、应还总额、剩余罚金、期数、是否还清、应还日期
//                BaseResult ppModifyResult = paymentPlanService.modifyPaymentPlan(repayPlan.getId(), pulishMoney,
//                        PayState.PAYING);
//                if (!ppModifyResult.isSuccess()) {
//                    return result.setError(CommonResultCode.BIZ_ERROR, "还款计划增加罚息失败");
//                }
//
//                // 批量更新，是否一个连接待检查
//                // 新的收益计划表中，每个人应得罚息＝投资人本期剩余罚息+借款人本期应还罚息总额*(投资人本期应回收本金/借款人本期应还本金)
//                //                          ＝投资人本期剩余罚息+投资人本期应回收本金*(借款人本期应还罚息总额/借款人本期应还本金)
//                // 罚息因子=(借款人本期应还罚息总额/借款人本期应还本金)
//                BaseResult ipModifyResult = incomePlanService.modifyIncomePlanByAllocPunishMoney(repayPlan.getId(),
//                        pulishMoney.doubleValue(), null);
//                if (!ipModifyResult.isSuccess()) {
//                    return result.setError(CommonResultCode.BIZ_ERROR, "收益计划增加罚息失败");
//                }
//            }
//
//            // 3. 创建交易记录
//            deal = new Deal();
//            List<DealDetail> dealDetailList = new ArrayList<DealDetail>(userAccountList.size());
//            BigDecimal payBackMoney = BigDecimal.ZERO;
//            BigDecimal oldCost = BigDecimal.ZERO;
//            //计算总还款金额
//            for (IncomePlan incomePlan : incomePlanList) {
//                payBackMoney = payBackMoney.add(incomePlan.getPayCapital());
//            }
//            int i = 0;
//            // 3.1 收取本金、利息、罚息 给投资人
//            for (IncomePlan incomePlan : incomePlanList) {
//                // 收益人账户Id
//                String receiveAccountId = incomePlan.getBeneficiary() + "|"
//                        + incomeUserTypeMap.get(incomePlan.getId()).getType();
//
//                // 获取投资订单 获取对应的SubOrdId、SubOrdDate
//                String investInnerSeqNo = investQueryService.queryById(incomePlan.getInvestId()).getData()
//                        .getInnerSeqNo();
//                PlainResult<InvestOrder> InvestOrderList = investOrderService
//                        .queryInvestOrderByInnerSeqNo(investInnerSeqNo);
//                String outSeqNo = InvestOrderList.getData().getIoOutSeqNo();
//
//                Map<String, String> map = new HashMap<String, String>();
//                //投资订单的SubOrdId，SubOrdDate
//                map.put("orderList", outSeqNo);
//                map.put("takeServeFee", String.valueOf(takeServeFee));
//                // 还款本金 
//                DealDetail captitalDetail = new DealDetail();
//                captitalDetail.setDealDetailType(DealDetailType.PAYBACK_CAPITAL);
//                captitalDetail.setMoneyAmount(incomePlan.getPayCapital());
//                captitalDetail.setPayAccountId(payAccount);
//                captitalDetail.setReceiveAccountId(receiveAccountId);
//                captitalDetail.setData(map);
//                dealDetailList.add(captitalDetail);
//
//                // 还款利息
//                DealDetail interestDetail = new DealDetail();
//                interestDetail.setDealDetailType(DealDetailType.PAYBACK_INTEREST);
//                interestDetail.setMoneyAmount(incomePlan.getPayInterest());
//                interestDetail.setPayAccountId(payAccount);
//                interestDetail.setReceiveAccountId(receiveAccountId);
//                interestDetail.setData(map);
//                dealDetailList.add(interestDetail);
//
//                // 超期罚金
//                DealDetail fineDetail = new DealDetail();
//                fineDetail.setDealDetailType(DealDetailType.PAYBACK_OVERDUE_FINE);
//                fineDetail.setMoneyAmount(incomePlan.getPayFine());
//                fineDetail.setPayAccountId(payAccount);
//                fineDetail.setReceiveAccountId(receiveAccountId);
//                fineDetail.setData(map);
//                dealDetailList.add(fineDetail);
//
//                // 3.2 收取服务费给平台
//                if (takeServeFee) {
//                    DealDetail serveDetail = new DealDetail();
//                    serveDetail.setDealDetailType(DealDetailType.PLA_SERVE_FEE);
//                    serveDetail.setPayAccountId(payAccount);
//                    serveDetail.setReceiveAccountId(platformAccount);
//                    String cost = calculateCost(payBackMoney.toString(), incomePlan.getPayCapital().toString(),
//                            repayPlan.getCollectServiceFee().toString(), incomePlanList.size(), i, oldCost.toString());
//                    serveDetail.setMoneyAmount(new BigDecimal(cost));
//                    oldCost = oldCost.add(new BigDecimal(cost));
//                    i++;
//                    serveDetail.setData(map);
//                    dealDetailList.add(serveDetail);
//                }
//
//            }
//
//            deal.setInnerSeqNo(InnerSeqNo.getInstance());
//            deal.setBusinessType(DealType.PAYBACK);
//            deal.setOperator(operatorId);
//            deal.setDealDetail(dealDetailList);
//            deal.setBusinessId(repayPlan.getLoanId());
//
//            PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, repayedCallback);
//            if (!dealResult.isSuccess()) {
//                log.warn(dealResult.getMessage());
//                throw new BusinessException("还款交易创建失败");
//            }
//
//            // 4. 更新还款计划表、收益计划表的内部交易流水号、状态
//            BaseResult repayModResult = paymentPlanService.modifyPaymentPlan(repayPlan.getId(), PayState.UNCLEAR,
//                    PayState.PAYING, payType, deal.getInnerSeqNo().getUniqueNo());
//            if (!repayModResult.isSuccess()) {
//                log.warn(repayModResult.getMessage());
//                throw new BusinessException("还款计划表状态更改失败");
//            }
//
//            BaseResult incomeModResult = incomePlanService.modifyIncomePlan(repayPlan.getId(), PayState.UNCLEAR,
//                    PayState.PAYING, deal.getInnerSeqNo().getUniqueNo());
//            if (!incomeModResult.isSuccess()) {
//                log.warn(incomeModResult.getMessage());
//                throw new BusinessException("收益计划表状态更改失败");
//            }
//        }
//        // 5. 执行交易
//        BaseResult invokeResult = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
//        if (!invokeResult.isSuccess()) {
//            log.warn(invokeResult.getMessage());
//            throw new BusinessException(invokeResult.getMessage());
//        }

        return result;
    }

    public static String calculateCost(String allMoney, String investMoney, String money, Integer listSize,
                                       Integer oldSize, String oldCost) {
        double Money = Double.parseDouble(allMoney);
        double investCost = Double.parseDouble(investMoney);
        double tempresult = investCost / Money;
        double resultCost = Double.parseDouble(money);
        double oldCostMoney = Double.parseDouble(oldCost);
        double resultMoney = resultCost * tempresult;
        if (listSize - oldSize == 1) {
            resultMoney = resultCost - oldCostMoney;
        }
        return String.valueOf(resultMoney);
    }

    /**
     * 查询罚息利率并计算罚息
     */
    private BigDecimal computePulishMoney(PaymentPlan repayPlan) {
        BigDecimal pulishMoney;

        //edit by 夏同同 20160421 计算天数时不应该考虑时分秒
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        // 判断是否逾期
        DateTime nowTime = new DateTime(sf.format(new Date()));
        DateTime planPayTime = new DateTime(sf.format(repayPlan.getPaytime()));

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

            /**
             * 当前为 罚息 = 当月利息* 罚息利率 /100/360* 逾期天数 + 剩余罚金<br>
             */
            // 当应还本金为0时，是否罚息为0
            pulishMoney = repayPlan.getPayInterest().multiply(new BigDecimal(punishRate * expiryDays));
            pulishMoney.abs(mc);
        } else {
            pulishMoney = BigDecimal.ZERO;
        }

        return pulishMoney;
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
            pulishMoney.abs(mc);
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
