/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash.thirdparty.easypay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BuyLoanDO;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.dataobject.FullTransferRecordJDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.search.FullTransferRecordSearchDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.intf.BuyLoanDao;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.convert.BuyLoanConverter;
import com.autoserve.abc.service.biz.convert.LoanConverter;
import com.autoserve.abc.service.biz.convert.TransferLoanConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BuyLoanTraceRecord;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.enums.BuyLoanTraceOperation;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.FullTransferType;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanTraceOperation;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilder;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilderFactory;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferService;
import com.autoserve.abc.service.biz.intf.loan.manage.BuyLoanManageService;
import com.autoserve.abc.service.biz.intf.loan.manage.TransferLoanManageService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 满标资金划转服务实现
 * 
 * @author segen189 2014年11月29日 下午2:34:19
 */
@Service
public class FullTransferServiceImpl implements FullTransferService {
    private static final Logger       log = LoggerFactory.getLogger(FullTransferServiceImpl.class);

    @Resource
    private LoanDao                   loanDao;

    @Resource
    private TransferLoanDao           transferLoanDao;

    @Resource
    private BuyLoanDao                buyLoanDao;

    @Resource
    private BuyLoanManageService      buyLoanManageService;

    @Resource
    private TransferLoanManageService transferLoanManageService;

    @Resource
    private PaymentPlanDao            paymentPlanDao;

    @Resource
    private FullTransferRecordDao     fullTransferRecordDao;

    @Resource
    private InvestQueryService        investQueryService;

    @Resource
    private TransferLoanService       transferLoanService;

    @Resource
    private BuyLoanService            buyLoanService;

    @Resource
    private IncomePlanService         incomePlanService;

    @Resource
    private PaymentPlanService        paymentPlanService;

    @Resource
    private FeeSettingService         feeSettingService;

    @Resource
    private DealRecordService         dealRecordService;

    @Resource
    private AccountInfoService        accountInfoService;

    @Resource
    private UserService               userService;

    @Resource
    private EmployeeService           employeeService;

    @Resource
    private SysConfigService          sysConfigService;

    @Resource
    private Callback<DealNotify>      moneyTransferedCallback;

    @Resource
    private GovernmentService         governmentService;

    @Resource
    private DealRecordDao             dealRecordDao;

    @Resource
    private InvestOrderService        investOrderService;

    /**
     * 普通标满标资金划转<br>
     * 1. 前置条件判断<br>
     * 2. 计算手续费<br>
     * 3. 计算担保费<br>
     * 4. 计算服务费<br>
     * 5. 更新标状态<br>
     * 6. 添加交易记录<br>
     * 6.1 交易账户查询<br>
     * 6.2 放款记录<br>
     * 6.3 收取手续费记录<br>
     * 6.4 收取担保费记录<br>
     * 7. 添加满标资金划转记录， 状态为 DealState.NOCALLBACK<br>
     * 8. 生成还款计划, 状态为 LoanPayState.INACTIVED<br>
     * 9. 生成收益计划, 状态为 IncomePlanState.INACTIVED<br>
     * 10. 执行资金划转交易
     */
    // check 执行资金交易的要保证异步，拆分service
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult commonBidMoneyTransfer(int loanId, double actrualOperateFee, BigDecimal actualGuarFee, int periods,
                                             FullTransferType transferType, int operatorId) {
        BaseResult result = new BaseResult();
        // 1. 前置条件
        if (actrualOperateFee < 0) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "实收手续费不能为负数");
        }

        LoanDO loanDO = loanDao.findByLoanIdWithLock(loanId);

        if (loanDO == null) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "借款不存在");
        }

        LoanCategory loanCategory = LoanCategory.valueOf(loanDO.getLoanCategory());

        int totalMonths;
        if (loanDO.getLoanPeriodType().equals(LoanPeriodUnit.YEAR.getUnit())) {
            totalMonths = loanDO.getLoanPeriod() * 12;
        } else {
            totalMonths = loanDO.getLoanPeriod();
        }

        if (totalMonths % periods != 0) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "还款总期数必须要能被借款总月数整除");
        }

        if (!loanDO.getLoanState().equals(LoanState.FULL_REVIEW_PASS.getState())) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "当前标的状态不能进行满标资金划转");
        }

        if (!loanDO.getLoanCurrentInvest().equals(loanDO.getLoanCurrentValidInvest())) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "当前标的状态不能进行满标资金划转");
        }

        InvestSearchDO searchDO = new InvestSearchDO();
        searchDO.setBidId(loanId);
        searchDO.setBidType(BidType.COMMON_LOAN.getType());
        searchDO.setInvestStates(Arrays.asList(InvestState.PAID.getState()));

        ListResult<Invest> investResult = investQueryService.queryInvestList(searchDO);
        List<Invest> investList = investResult.getData();
        if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资记录查询失败");
        }

        PlainResult<SysConfig> platformAccountResult = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
        if (!platformAccountResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台账户查询失败");
        }

        // 2. 计算手续费    平台手续费(固定算法)
        PlainResult<FeeSetting> operatingFeeResult = feeSettingService.queryByFeeTypeLoanCategory(FeeType.PLA_FEE,
                loanCategory, loanDO.getLoanMoney());
        if (!operatingFeeResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台手续费查询失败");
        }

        final BigDecimal collectOperatingFee = new BigDecimal(actrualOperateFee);
        // 当前有效投标总额
        final BigDecimal expectOperatingFee = calculateFee(operatingFeeResult.getData(),
                loanDO.getLoanCurrentValidInvest());
        if (collectOperatingFee.compareTo(expectOperatingFee) > 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "实收手续费不能大于应收手续费");
        }

        // 3. 计算担保费
        PlainResult<FeeSetting> insureFeeResult = feeSettingService.queryByFeeTypeLoanCategory(FeeType.INSURANCE_FEE,
                loanCategory, loanDO.getLoanMoney());
        if (!insureFeeResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "担保费查询失败");
        }

        final BigDecimal insureFee = calculateFee(insureFeeResult.getData(), loanDO.getLoanCurrentValidInvest());

        // 4. 计算服务费
        PlainResult<FeeSetting> serveFeeResult = feeSettingService.queryByFeeTypeLoanCategory(FeeType.PLA_SERVE_FEE,
                loanCategory, loanDO.getLoanMoney());
        if (!serveFeeResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台服务费查询失败");
        }

        final BigDecimal serveFee = calculateFee(serveFeeResult.getData(), loanDO.getLoanCurrentValidInvest());

        // 5. 更新标状态
        LoanDO toModify = new LoanDO();
        toModify.setLoanId(loanDO.getLoanId());
        toModify.setLoanState(LoanState.MONEY_TRANSFERING.getState());
        loanDao.update(toModify);

        // 6. 添加交易记录
        // 6.1 交易账户查询
        // 6.2 放款记录
        // 6.3 收取手续费记录
        // 6.4 收取担保费记录
        Deal deal = new Deal();
        List<DealDetail> dealDetailList = new ArrayList<DealDetail>();

        // 6.1 查询借款人账户、投资人账户、担保公司账户、平台账户
        List<UserIdentity> userList = new ArrayList<UserIdentity>();

        // 借款人
        UserIdentity loanee = new UserIdentity();
        loanee.setUserId(loanDO.getLoanUserId());
        loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
        userList.add(loanee);

        // 投资人
        final Map<Integer, UserType> investUserTypeMap = new HashMap<Integer, UserType>();
        for (Invest invest : investList) {
            UserIdentity investor = new UserIdentity();
            investor.setUserId(invest.getUserId());
            investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
            userList.add(investor);

            investUserTypeMap.put(invest.getId(), investor.getUserType());
        }

        // 担保公司
        Employee guarEmployee = null;
        if (loanDO.getLoanGuarGov() != null) {
            PlainResult<Employee> employeeResult = employeeService.findByGovId(loanDO.getLoanGuarGov());
            if (!employeeResult.isSuccess() || employeeResult.getData() == null) {
                throw new BusinessException("担保机构查询失败");
            }

            guarEmployee = employeeResult.getData();

            UserIdentity guarGov = new UserIdentity();
            guarGov.setUserId(guarEmployee.getEmpId());
            guarGov.setUserType(UserType.PARTNER);
            userList.add(guarGov);
        }

        ListResult<Account> accountResult = accountInfoService.queryByUserIds(userList);
        List<Account> userAccountList = accountResult.getData();
        if (!accountResult.isSuccess()) {
            throw new BusinessException("收益计划创建失败");
        }

        // 借款人账户、投资人账户、担保公司账户
        final Map<String, String> userIdAndAccountMap = new HashMap<String, String>();
        for (Account acc : userAccountList) {
            userIdAndAccountMap.put(acc.getAccountUserId() + "|" + acc.getAccountUserType().getType(),
                    acc.getAccountNo());
        }

        //从abc_invest_order获取该原始标号在钱多多平台投资的标号
        List<InvestOrderDO> listLoanSeq = investOrderService.queryInvestOrderByTransLoanId(loanId);
        StringBuffer loanNo = new StringBuffer();
        for (int i = 0; i < listLoanSeq.size(); i++) {
            Map map = (Map) listLoanSeq.get(i);
            String seq = map.get("io_out_seq_no").toString();

            if (i == 0) {
                loanNo = loanNo.append(map.get("io_out_seq_no").toString());
            } else {
                loanNo = loanNo.append(",").append(map.get("io_out_seq_no").toString());
            }

        }

        String LoanNoList = loanNo.toString();
        // 平台账户
        final String platformAccount = platformAccountResult.getData().getConfValue();
        Map params = new HashMap();
        // 6.2 放款记录
        for (Invest invest : investList) {
            DealDetail investDetail = new DealDetail();
            String payAccountId = userIdAndAccountMap.get(invest.getUserId() + "|"
                    + investUserTypeMap.get(invest.getId()).getType());
            String receiveAccountId = userIdAndAccountMap
                    .get(loanee.getUserId() + "|" + loanee.getUserType().getType());
            investDetail.setDealDetailType(DealDetailType.APPROPRIATE_MONEY);
            investDetail.setPayAccountId(payAccountId);
            investDetail.setReceiveAccountId(receiveAccountId);
            investDetail.setMoneyAmount(invest.getValidInvestMoney());
            params.put("LoanNoList", LoanNoList);
            investDetail.setData(params);
            dealDetailList.add(investDetail);
        }

        // 6.3 收取手续费记录
        if (collectOperatingFee != null) {
            DealDetail operateDetail = new DealDetail();
            String payAccountId = userIdAndAccountMap.get(loanee.getUserId() + "|" + loanee.getUserType().getType());
            operateDetail.setDealDetailType(DealDetailType.PLA_FEE);
            operateDetail.setPayAccountId(payAccountId);
            operateDetail.setReceiveAccountId(platformAccount);
            operateDetail.setMoneyAmount(collectOperatingFee);
            params.put("userId", loanee.getUserId());
            params.put("type", loanee.getUserType().getType());
            operateDetail.setData(params);
            dealDetailList.add(operateDetail);
        }

        // 6.4 收取担保费记录
        if (guarEmployee != null && insureFee != null) {
            //从担保机构中获取钱多多平台账号
            PlainResult<EmployeeDO> employeeResult = employeeService.findById(guarEmployee.getEmpId());
            PlainResult<GovernmentDO> government = governmentService.findById(employeeResult.getData().getEmpOrgId());
            GovernmentDO governmentDO = government.getData();
            String governmentMark = governmentDO.getGovOutSeqNo();
            DealDetail insureDetail = new DealDetail();
            insureDetail.setDealDetailType(DealDetailType.INSURANCE_FEE);
            insureDetail.setPayAccountId(userIdAndAccountMap.get(loanee.getUserId() + "|"
                    + loanee.getUserType().getType()));
            insureDetail.setReceiveAccountId(userIdAndAccountMap.get(guarEmployee.getEmpId() + "|"
                    + UserType.PARTNER.getType()));

            if (StringUtils.isBlank(insureDetail.getReceiveAccountId())) {
                throw new BusinessException("担保公司未开户");
            }
            params.put("userId", loanee.getUserId());
            params.put("type", loanee.getUserType().getType());
            params.put("inUserId", governmentMark);
            insureDetail.setData(params);
            insureDetail.setMoneyAmount(insureFee);
            dealDetailList.add(insureDetail);
        }

        deal.setBusinessId(loanId);
        deal.setBusinessType(DealType.TRANSFER);
        deal.setInnerSeqNo(InnerSeqNo.getInstance());
        deal.setOperator(operatorId);
        deal.setDealDetail(dealDetailList);

        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, moneyTransferedCallback);
        if (!dealResult.isSuccess()) {
            throw new BusinessException("满标资金划转交易记录创建失败");
        }

        // 7. 添加满标资金划转记录
        FullTransferRecordDO fullTransRecord = new FullTransferRecordDO();

        fullTransRecord.setFtrBidId(loanId);
        fullTransRecord.setFtrOriginId(loanId);
        fullTransRecord.setFtrOperator(operatorId);
        fullTransRecord.setFtrUserId(loanDO.getLoanUserId());
        fullTransRecord.setFtrBidType(BidType.COMMON_LOAN.getType());
        fullTransRecord.setFtrSeqNo(dealResult.getData().getDrInnerSeqNo());
        fullTransRecord.setFtrTransferDate(new Date());
        fullTransRecord.setFtrTransferType(transferType.getType());

        fullTransRecord.setFtrTransferMoney(loanDO.getLoanCurrentValidInvest().subtract(collectOperatingFee)
                .subtract(insureFee));
        if (fullTransRecord.getFtrTransferMoney().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("满标资金划转金额须是正数");
        }

        fullTransRecord.setFtrPayFee(collectOperatingFee);
        fullTransRecord.setFtrGuarFee(insureFee);

        fullTransRecord.setFtrRealPayFee(BigDecimal.ZERO);
        fullTransRecord.setFtrRealGuarFee(BigDecimal.ZERO);
        fullTransRecord.setFtrDealState(DealState.NOCALLBACK.getState());

        // 满标资金划转记录id
        fullTransferRecordDao.insert(fullTransRecord);
        final int fullTransferRecordId = fullTransRecord.getFtrId();

        // 8. 生成还款计划
        PlanBuilder planBuilder = PlanBuilderFactory.createPlanBuilder(LoanPeriodUnit.MONTH);

        List<PaymentPlan> paymentPlanList = planBuilder.buildPaymentPlanList(LoanConverter.toLoan(loanDO),
                serveFee.doubleValue(), fullTransferRecordId, periods);
        PlainResult<Integer> createPaymentResult = paymentPlanService.batchCreatePaymentPlanList(paymentPlanList);
        if (!createPaymentResult.isSuccess()) {
            throw new BusinessException("还款计划创建失败");
        }

        ListResult<PaymentPlan> queryPaymentResult = paymentPlanService.queryPaymentPlanList(fullTransferRecordId);
        if (!queryPaymentResult.isSuccess()) {
            throw new BusinessException("还款计划创建失败");
        }

        // 9. 生成收益计划
        List<IncomePlan> incomePlanList = planBuilder.buildIncomePlanList(LoanConverter.toLoan(loanDO), investList,
                queryPaymentResult.getData(), fullTransferRecordId);
        PlainResult<Integer> createIncomeResult = incomePlanService.batchCreateIncomePlanList(incomePlanList);
        if (!createIncomeResult.isSuccess()) {
            throw new BusinessException("收益计划创建失败");
        }

        // 10. 执行资金划转交易
        BaseResult dealInvoke = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!dealInvoke.isSuccess()) {
            log.warn("满标资金划转交易执行失败! {}", dealInvoke.getMessage());
            throw new BusinessException("满标资金划转交易执行失败");
        }

        return result;
    }

    /**
     * 转让标满标资金划转<br>
     * 1. 前置条件判断<br>
     * 1.1 加行级锁住还款计划<br>
     * 1.2 满标前有还款行为，则直接流标<br>
     * 2. 计算转让费<br>
     * 3. 更新标状态<br>
     * 4. 添加交易记录<br>
     * 4.1 交易账户查询<br>
     * 4.2 放款记录<br>
     * 4.3 收取转让费记录<br>
     * 5. 添加满标资金划转记录， 状态为 DealState.NOCALLBACK<br>
     * 6. 生成收益计划, 状态为 IncomePlanState.INACTIVED<br>
     * 7. 执行资金划转交易
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult transferBidMoneyTransfer(int transLoanId, double actrualOperateFee,
                                               FullTransferType transferType, int operatorId) {
        BaseResult result = new BaseResult();

        // 1. 前置条件判断
        if (actrualOperateFee < 0) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "实收手续费不能为负数");
        }

        TransferLoanDO transferLoanDO = transferLoanDao.findById(transLoanId);
        LoanDO loanDO = loanDao.findById(transferLoanDO.getTlOriginId());

        if (!transferLoanDO.getTlState().equals(TransferLoanState.FULL_REVIEW_PASS.getState())) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "当前标的状态不能进行满标资金划转");
        }

        // 1.1 加行级锁住还款计划
        PaymentPlanDO paymentLock = paymentPlanDao.findWithLock(transferLoanDO.getTlNextPaymentId());
        if (paymentLock == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "还款时加行级锁失败");
        }

        // 1.2 满标前有还款行为，则直接流标
        if (!paymentLock.getPpPayState().equals(PayState.UNCLEAR.getState())) {
            //            TransferLoanDO toModify = new TransferLoanDO();
            //            toModify.setTlId(transferLoanDO.getTlId());
            //            toModify.setTlState(TransferLoanState.BID_CANCELED.getState());
            //            transferLoanDao.update(toModify);
            BaseResult cancelTransResult = transferLoanManageService.cancelTransferLoan(transLoanId, 0,
                    "转让标发起后到满标资金划转期间借款人有还款行为");
            if (!cancelTransResult.isSuccess()) {
                result.setError(CommonResultCode.BIZ_ERROR, "转让标发起后到满标资金划转期间借款人有还款行为，自动流标失败");
            }

            return result.setError(CommonResultCode.BIZ_ERROR, "转让标发起后到满标资金划转期间借款人有还款行为");
        }

        // 目前的债权人转让是将债权人的所有未收益的收益计划查出来，进行转让
        // 如果要支持债权人部分转让，则此处需要查出的是转让人要部分转让的未收益的收益计划
        IncomePlan queryParam = new IncomePlan();
        queryParam.setInvestId(transferLoanDO.getTlInvestId());
        queryParam.setIncomePlanState(IncomePlanState.GOING);

        final ListResult<IncomePlan> transferIncomeResult = incomePlanService.queryIncomePlanList(queryParam);
        if (!transferIncomeResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "债权人的收益计划查询失败");
        } else if (CollectionUtils.isEmpty(transferIncomeResult.getData())) {
            // 流标
            //            TransferLoanDO toModify = new TransferLoanDO();
            //            toModify.setTlId(transferLoanDO.getTlId());
            //            toModify.setTlState(TransferLoanState.BID_CANCELED.getState());
            //            transferLoanDao.update(toModify);
            BaseResult cancelTransResult = transferLoanManageService.cancelTransferLoan(transLoanId, 0,
                    "债权人的收益计划查询结果为空");
            if (!cancelTransResult.isSuccess()) {
                result.setError(CommonResultCode.BIZ_ERROR, "债权人的收益计划查询结果为空");
            }

            return result.setError(CommonResultCode.BIZ_ERROR, "债权人的收益计划查询结果为空");
        }

        PlainResult<SysConfig> platformAccountResult = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
        if (!platformAccountResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台账户查询失败");
        }

        InvestSearchDO investSearchDO = new InvestSearchDO();
        //原始标号transLoanId
        investSearchDO.setBidId(transLoanId);
        investSearchDO.setBidType(BidType.TRANSFER_LOAN.getType());
        investSearchDO.setInvestStates(Arrays.asList(InvestState.PAID.getState()));
        ListResult<Invest> investResult = investQueryService.queryInvestList(investSearchDO);
        List<Invest> investList = investResult.getData();
        if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "转让标投资记录查询失败");
        }

        // 2. 计算转让手续费
        PlainResult<FeeSetting> transferFeeResult = feeSettingService.queryByFeeTypeLoanCategory(FeeType.TRANSFER_FEE,
                LoanCategory.valueOf(loanDO.getLoanCategory()), loanDO.getLoanMoney());
        if (!transferFeeResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台转让手续费查询失败");
        }
        //实收转让费
        BigDecimal collectTransferFee = new BigDecimal(actrualOperateFee);
        BigDecimal expectTransferFee = calculateFee(transferFeeResult.getData(), transferLoanDO.getTlTransferMoney());
        if (collectTransferFee.compareTo(expectTransferFee) > 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "实收转让手续费不能大于应收转让手续费");
        }

        // 3. 更新标状态
        TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(null);
        traceRecord.setTransferLoanId(transferLoanDO.getTlId());
        traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.moneyTransfering);
        traceRecord.setOldTransferLoanState(TransferLoanState.FULL_REVIEW_PASS);
        traceRecord.setNewTransferLoanState(TransferLoanState.MONEY_TRANSFERING);
        traceRecord.setNote("转让标资金划转");

        BaseResult changeResult = transferLoanService.changeTransferLoanState(traceRecord);
        if (!changeResult.isSuccess()) {
            log.error("转让标资金划转失败！{}", changeResult.getMessage());
            throw new BusinessException("转让标资金划转失败");
        }

        // 4. 添加交易记录
        // 4.1 交易账户查询
        Deal deal = new Deal();
        List<DealDetail> dealDetailList = new ArrayList<DealDetail>();

        // 查询转让人账户、投资人账户、平台账户
        List<UserIdentity> userList = new ArrayList<UserIdentity>();

        // 转让人
        UserIdentity transfer = new UserIdentity();
        transfer.setUserId(transferLoanDO.getTlUserId());
        transfer.setUserType(queryUserTypeByUserId(transfer.getUserId()));
        userList.add(transfer);

        // 投资人
        final Map<Integer, UserType> investUserTypeMap = new HashMap<Integer, UserType>();
        for (Invest invest : investList) {
            UserIdentity investor = new UserIdentity();
            investor.setUserId(invest.getUserId());
            investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
            userList.add(investor);

            investUserTypeMap.put(invest.getId(), investor.getUserType());
        }

        ListResult<Account> accountResult = accountInfoService.queryByUserIds(userList);
        List<Account> userAccountList = accountResult.getData();
        if (!accountResult.isSuccess()) {
            throw new BusinessException("收益计划创建失败");
        }

        // 转让人账户、投资人账户
        final Map<String, String> userIdAndAccountMap = new HashMap<String, String>();
        for (Account acc : userAccountList) {
            userIdAndAccountMap.put(acc.getAccountUserId() + "|" + acc.getAccountUserType().getType(),
                    acc.getAccountNo());
        }
        // 平台账户
        final String platformAccount = platformAccountResult.getData().getConfValue();

        // 4.2 放款记录
        for (Invest invest : investList) {
            DealDetail investDetail = new DealDetail();

            String payAccountId = userIdAndAccountMap.get(invest.getUserId() + "|"
                    + investUserTypeMap.get(invest.getId()).getType());
            String receiveAccountId = userIdAndAccountMap.get(transfer.getUserId() + "|"
                    + transfer.getUserType().getType());

            investDetail.setDealDetailType(DealDetailType.DEBT_TRANSFER_MONEY);
            investDetail.setPayAccountId(payAccountId);
            investDetail.setReceiveAccountId(receiveAccountId);
            investDetail.setMoneyAmount(invest.getValidInvestMoney());
            dealDetailList.add(investDetail);
        }

        // 4.3 收取转让费记录
        if (collectTransferFee != null) {
            DealDetail operateDetail = new DealDetail();
            operateDetail.setDealDetailType(DealDetailType.DEBT_TRANSFER_FEE);
            String payAccountId = userIdAndAccountMap
                    .get(transfer.getUserId() + "|" + transfer.getUserType().getType());
            operateDetail.setPayAccountId(payAccountId);
            operateDetail.setReceiveAccountId(platformAccount);
            operateDetail.setMoneyAmount(collectTransferFee);
            dealDetailList.add(operateDetail);
        }

        deal.setBusinessId(loanDO.getLoanId());
        deal.setBusinessType(DealType.TRANSFER);
        deal.setInnerSeqNo(InnerSeqNo.getInstance());
        deal.setOperator(operatorId);
        deal.setDealDetail(dealDetailList);

        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, moneyTransferedCallback);
        if (!dealResult.isSuccess()) {
            throw new BusinessException("满标资金划转交易记录创建失败");
        }

        // 5. 添加满标资金划转记录， 状态为 DealState.NOCALLBACK
        FullTransferRecordDO fullTransRecord = new FullTransferRecordDO();

        fullTransRecord.setFtrBidId(transLoanId);
        fullTransRecord.setFtrOriginId(loanDO.getLoanId());
        fullTransRecord.setFtrOperator(operatorId);
        fullTransRecord.setFtrUserId(transferLoanDO.getTlUserId());
        fullTransRecord.setFtrBidType(BidType.TRANSFER_LOAN.getType());
        fullTransRecord.setFtrSeqNo(dealResult.getData().getDrInnerSeqNo());
        fullTransRecord.setFtrTransferDate(new Date());
        fullTransRecord.setFtrTransferType(transferType.getType());

        fullTransRecord.setFtrTransferMoney(transferLoanDO.getTlCurrentValidInvest().subtract(collectTransferFee));
        if (fullTransRecord.getFtrTransferMoney().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("满标资金划转金额须是正数");
        }

        fullTransRecord.setFtrPayFee(collectTransferFee);
        fullTransRecord.setFtrGuarFee(BigDecimal.ZERO);

        fullTransRecord.setFtrRealPayFee(BigDecimal.ZERO);
        fullTransRecord.setFtrRealGuarFee(BigDecimal.ZERO);
        fullTransRecord.setFtrDealState(DealState.NOCALLBACK.getState());

        // 满标资金划转记录id
        fullTransferRecordDao.insert(fullTransRecord);
        final int fullTransferRecordId = fullTransRecord.getFtrId();
        //转让标调用审核接口
        Map params = new HashMap();
        DealDetail operateDetail = new DealDetail();
        //获取loanjsonList
        StringBuffer loanjsonList = new StringBuffer();
        //用于获取批量乾多多流水号集合
        List<String> listSeqNo = new ArrayList<String>();
        if (listSeqNo.size() > 0) {
            for (int i = 0; i < listSeqNo.size(); i++) {
                loanjsonList.append(listSeqNo.get(i)).append(",");
            }
        }
        params.put("loanjsonList", loanjsonList);
        operateDetail.setData(params);
        dealDetailList.add(operateDetail);
        // 6. 生成收益计划, 状态为 IncomePlanState.INACTIVED
        PlanBuilder planBuilder = PlanBuilderFactory.createPlanBuilder(LoanPeriodUnit.MONTH);

        List<IncomePlan> incomePlanList = planBuilder.buildTransferIncomePlanList(
                TransferLoanConverter.toTransferLoan(transferLoanDO), transferIncomeResult.getData(), investList,
                fullTransferRecordId, null);

        PlainResult<Integer> createIncomeResult = incomePlanService.batchCreateIncomePlanList(incomePlanList);
        if (!createIncomeResult.isSuccess()) {
            throw new BusinessException("收益计划创建失败");
        }

        // 目前的债权人转让是将债权人的所有未收益的收益计划查出来，进行转让
        // 如果要支持债权人部分转让，则应只改变用来转让的那几期的状态
        //        PlainResult<Integer> batchModifyIncomeStateResult = incomePlanService
        //                .batchModifyStateByUserIdAndFullTransRecordId(transferLoanDO.getTlUserId(),
        //                        paymentLock.getPpFullTransRecordId(), IncomePlanState.GOING, IncomePlanState.TRANSFERED);
        //        if (!batchModifyIncomeStateResult.isSuccess()) {
        //            throw new BusinessException("收益计划修改失败");
        //        }

        // 7. 执行资金划转交易
        BaseResult dealInvoke = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!dealInvoke.isSuccess()) {
            log.warn("满标资金划转交易执行失败! {}", dealInvoke.getMessage());
            throw new BusinessException("满标资金划转交易执行失败");
        }

        return result;
    }

    /**
     * 收购标满标资金划转<br>
     * 1. 前置条件判断<br>
     * 1.1 加行级锁住还款计划<br>
     * 1.2 满标前有还款行为，则直接流标<br>
     * 2. 计算收购费<br>
     * 3. 更新标状态<br>
     * 4. 添加交易记录<br>
     * 4.1 交易账户查询<br>
     * 4.2 放款记录<br>
     * 4.3 收取转让费记录<br>
     * 5. 添加满标资金划转记录， 状态为 DealState.NOCALLBACK<br>
     * 6. 生成收益计划, 状态为 IncomePlanState.INACTIVED<br>
     * 7. 执行资金划转交易
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult buyBidMoneyTransfer(int buyLoanId, double actrualOperateFee, FullTransferType transferType,
                                          int operatorId) {
        BaseResult result = new BaseResult();

        // 1. 前置条件判断
        if (actrualOperateFee < 0) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "实收手续费不能为负数");
        }

        BuyLoanDO buyLoanDO = buyLoanDao.findById(buyLoanId);
        LoanDO loanDO = loanDao.findById(buyLoanDO.getBlOriginId());

        if (!buyLoanDO.getBlState().equals(BuyLoanState.FULL_REVIEW_PASS.getState())) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "当前标的状态不能进行满标资金划转");
        }

        PlainResult<PaymentPlan> newPaymentResult = paymentPlanService.queryNextPaymentPlan(loanDO.getLoanId());
        if (!newPaymentResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "查询借款标要进行的下一期还款计划失败");
        }

        // 1.1 加行级锁住还款计划
        PaymentPlanDO paymentLock = paymentPlanDao.findWithLock(buyLoanDO.getBlNextPaymentId());
        if (paymentLock == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "还款时加行级锁失败");
        }

        // 1.2 满标前有还款行为，则直接流标
        if (!paymentLock.getPpPayState().equals(PayState.UNCLEAR.getState())) {
            //            BuyLoanDO toModify = new BuyLoanDO();
            //            toModify.setBlId(buyLoanDO.getBlId());
            //            toModify.setBlState(BuyLoanState.BID_CANCELED.getState());
            //            buyLoanDao.update(toModify);
            BaseResult cancelBuyResult = buyLoanManageService.cancelBuyLoan(buyLoanId, 0, "收购标发起后到满标资金划转期间借款人有还款行为");
            if (!cancelBuyResult.isSuccess()) {
                result.setError(CommonResultCode.BIZ_ERROR, "收购标发起后到满标资金划转期间借款人有还款行为，自动流标失败");
            }

            return result.setError(CommonResultCode.BIZ_ERROR, "收购标发起后到满标资金划转期间借款人有还款行为");
        }

        PlainResult<SysConfig> platformAccountResult = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
        if (!platformAccountResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台账户查询失败");
        }

        InvestSearchDO investSearchDO = new InvestSearchDO();
        investSearchDO.setBidId(buyLoanId);
        investSearchDO.setBidType(BidType.BUY_LOAN.getType());
        investSearchDO.setInvestStates(Arrays.asList(InvestState.PAID.getState()));
        ListResult<Invest> investResult = investQueryService.queryInvestList(investSearchDO);
        List<Invest> investList = investResult.getData();
        if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "收购标投资记录查询失败");
        }

        // 2. 计算收购手续费
        PlainResult<FeeSetting> buyFeeResult = feeSettingService.queryByFeeTypeLoanCategory(FeeType.PURCHASE_FEE,
                LoanCategory.valueOf(loanDO.getLoanCategory()), loanDO.getLoanMoney());
        if (!buyFeeResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台收购手续费查询失败");
        }

        BigDecimal collectBuyFee = new BigDecimal(actrualOperateFee);
        BigDecimal expectBuyFee = calculateFee(buyFeeResult.getData(), buyLoanDO.getBlBuyMoney());
        if (collectBuyFee.compareTo(expectBuyFee) > 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "实收收购手续费不能大于应收收购手续费");
        }

        // 3. 更新标状态

        BuyLoanTraceRecord traceRecord = new BuyLoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(null);
        traceRecord.setBuyLoanId(buyLoanDO.getBlId());
        traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.moneyTransfering);
        traceRecord.setOldBuyLoanState(BuyLoanState.FULL_REVIEW_PASS);
        traceRecord.setNewBuyLoanState(BuyLoanState.MONEY_TRANSFERING);
        traceRecord.setNote("收购标资金划转");

        BaseResult changeResult = buyLoanService.changeBuyLoanState(traceRecord);
        if (!changeResult.isSuccess()) {
            log.error("收购标资金划转失败！{}", changeResult.getMessage());
            throw new BusinessException("收购标的状态更改失败");
        }

        // 4. 添加交易记录
        // 4.1 交易账户查询
        Deal deal = new Deal();
        List<DealDetail> dealDetailList = new ArrayList<DealDetail>();

        // 查询收购人账户、投资人账户、平台账户
        List<UserIdentity> userList = new ArrayList<UserIdentity>();

        // 收购人
        UserIdentity buyer = new UserIdentity();
        buyer.setUserId(buyLoanDO.getBlUserId());
        buyer.setUserType(queryUserTypeByUserId(buyer.getUserId()));
        userList.add(buyer);

        // 投资人
        final Map<Integer, UserType> investUserTypeMap = new HashMap<Integer, UserType>();
        for (Invest invest : investList) {
            UserIdentity investor = new UserIdentity();
            investor.setUserId(invest.getUserId());
            investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
            userList.add(investor);

            investUserTypeMap.put(invest.getId(), investor.getUserType());
        }

        ListResult<Account> accountResult = accountInfoService.queryByUserIds(userList);
        List<Account> userAccountList = accountResult.getData();
        if (!accountResult.isSuccess()) {
            throw new BusinessException("收益计划创建失败");
        }

        // 收购人账户、投资人账户
        final Map<String, String> userIdAndAccountMap = new HashMap<String, String>();
        for (Account acc : userAccountList) {
            userIdAndAccountMap.put(acc.getAccountUserId() + "|" + acc.getAccountUserType().getType(),
                    acc.getAccountNo());
        }

        // 平台账户
        final String platformAccount = platformAccountResult.getData().getConfValue();

        // 4.2 放款记录
        for (Invest invest : investList) {
            DealDetail investDetail = new DealDetail();
            investDetail.setDealDetailType(DealDetailType.PURCHASE_MONEY);

            String payAccountId = userIdAndAccountMap.get(buyer.getUserId() + "|" + buyer.getUserType().getType());
            String receiveAccountId = userIdAndAccountMap.get(invest.getUserId() + "|"
                    + investUserTypeMap.get(invest.getId()).getType());

            investDetail.setPayAccountId(payAccountId);
            investDetail.setReceiveAccountId(receiveAccountId);
            investDetail.setMoneyAmount(invest.getValidInvestMoney());
            dealDetailList.add(investDetail);
        }

        // 4.3 收取收购费记录
        if (collectBuyFee != null) {
            DealDetail operateDetail = new DealDetail();
            operateDetail.setDealDetailType(DealDetailType.PURCHASE_FEE);

            String payAccountId = userIdAndAccountMap.get(buyer.getUserId() + "|" + buyer.getUserType().getType());

            operateDetail.setPayAccountId(payAccountId);
            operateDetail.setReceiveAccountId(platformAccount);
            operateDetail.setMoneyAmount(collectBuyFee);
            dealDetailList.add(operateDetail);
        }

        deal.setBusinessId(loanDO.getLoanId());
        deal.setBusinessType(DealType.TRANSFER);
        deal.setInnerSeqNo(InnerSeqNo.getInstance());
        deal.setOperator(operatorId);
        deal.setDealDetail(dealDetailList);

        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, moneyTransferedCallback);
        if (!dealResult.isSuccess()) {
            throw new BusinessException("满标资金划转交易记录创建失败");
        }

        // 5. 添加满标资金划转记录， 状态为 DealState.NOCALLBACK
        FullTransferRecordDO fullTransRecord = new FullTransferRecordDO();

        fullTransRecord.setFtrBidId(buyLoanId);
        fullTransRecord.setFtrOriginId(loanDO.getLoanId());
        fullTransRecord.setFtrOperator(operatorId);
        fullTransRecord.setFtrUserId(buyLoanDO.getBlUserId());
        fullTransRecord.setFtrBidType(BidType.BUY_LOAN.getType());
        fullTransRecord.setFtrSeqNo(dealResult.getData().getDrInnerSeqNo());
        fullTransRecord.setFtrTransferDate(new Date());
        fullTransRecord.setFtrTransferType(transferType.getType());

        fullTransRecord.setFtrTransferMoney(buyLoanDO.getBlCurrentValidInvest().subtract(collectBuyFee));
        if (fullTransRecord.getFtrTransferMoney().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("满标资金划转金额须是正数");
        }

        fullTransRecord.setFtrPayFee(collectBuyFee);
        fullTransRecord.setFtrGuarFee(BigDecimal.ZERO);

        fullTransRecord.setFtrRealPayFee(BigDecimal.ZERO);
        fullTransRecord.setFtrRealGuarFee(BigDecimal.ZERO);
        fullTransRecord.setFtrDealState(DealState.NOCALLBACK.getState());

        // 满标资金划转记录id
        fullTransferRecordDao.insert(fullTransRecord);
        final int fullTransferRecordId = fullTransRecord.getFtrId();

        // 6. 生成收益计划, 状态为 IncomePlanState.INACTIVED
        PlanBuilder planBuilder = PlanBuilderFactory.createPlanBuilder(LoanPeriodUnit.MONTH);

        // 目前的投资人认购是将投资人的所有未收益的收益计划查出来，进行认购
        // 如果要支持投资人部分认购，则此处需要查出的是投资人要用来部分认购的未收益的收益计划
        final List<Integer> beneficiaryIdList = new ArrayList<Integer>(investList.size());
        for (Invest invest : investList) {
            beneficiaryIdList.add(invest.getUserId());
        }

        Map params = new HashMap();
        DealDetail operateDetail = new DealDetail();
        //获取loanjsonList
        StringBuffer loanjsonList = new StringBuffer();
        //用于获取批量乾多多流水号集合
        List<String> listSeqNo = new ArrayList<String>();
        if (listSeqNo.size() > 0) {
            for (int i = 0; i < listSeqNo.size(); i++) {
                loanjsonList.append(listSeqNo.get(i)).append(",");
            }
        }
        params.put("loanjsonList", loanjsonList);
        operateDetail.setData(params);
        dealDetailList.add(operateDetail);

        ListResult<IncomePlan> buyIncomeResult = incomePlanService.queryIncomePlanList(IncomePlanState.GOING,
                loanDO.getLoanId(), beneficiaryIdList);
        if (!buyIncomeResult.isSuccess() || CollectionUtils.isEmpty(buyIncomeResult.getData())) {
            throw new BusinessException("认购人的收益计划查询失败");
        }

        List<IncomePlan> incomePlanList = planBuilder.buildBuyIncomePlanList(BuyLoanConverter.toBuyLoan(buyLoanDO),
                buyIncomeResult.getData(), investList, fullTransferRecordId);

        PlainResult<Integer> createIncomeResult = incomePlanService.batchCreateIncomePlanList(incomePlanList);
        if (!createIncomeResult.isSuccess()) {
            throw new BusinessException("收益计划创建失败");
        }

        //        PlainResult<Integer> batchModifyIncomeStateResult = incomePlanService.batchUpdateStateByLoanId(
        //                loanDO.getLoanId(), beneficiaryIdList, IncomePlanState.GOING, IncomePlanState.BUYED);
        //        if (!batchModifyIncomeStateResult.isSuccess()) {
        //            throw new BusinessException("认购人的收益计划修改失败");
        //        }

        // 7. 执行资金划转交易
        BaseResult dealInvoke = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!dealInvoke.isSuccess()) {
            log.warn("满标资金划转交易执行失败! {}", dealInvoke.getMessage());
            throw new BusinessException("满标资金划转交易执行失败");
        }

        return result;
    }

    private BigDecimal calculateFee(FeeSetting feeSetting, BigDecimal base) {
        if (feeSetting == null) {
            return null;
        }

        switch (feeSetting.getChargeType()) {
            case BY_DEAL: {
                return feeSetting.getAccurateAmount();
            }
            case BY_RATIO: {
                double fee = base.doubleValue() * feeSetting.getRate() / 100;
                //                if (fee < feeSetting.getMinAmount().doubleValue()) {
                //                    return feeSetting.getMinAmount();
                //                } else if (fee > feeSetting.getMaxAmount().doubleValue()) {
                //                    return feeSetting.getMaxAmount();
                //                } else {
                return new BigDecimal(fee).setScale(2, BigDecimal.ROUND_HALF_UP);
                //                }
            }
            default:
                return null;
        }
    }

    @Override
    public PageResult<FullTransferRecordJDO> queryMoneyTransferList(FullTransferRecordSearchDO fullTransferRecordSearchDO,
                                                                    PageCondition pageCondition) {
        int count = fullTransferRecordDao.countMoneyTransferList(fullTransferRecordSearchDO, pageCondition);
        PageResult<FullTransferRecordJDO> result = new PageResult<FullTransferRecordJDO>(pageCondition);
        if (count > 0) {
            List<FullTransferRecordJDO> list = this.fullTransferRecordDao.getMoneyTransferList(
                    fullTransferRecordSearchDO, pageCondition);
            result.setData(list);
        }
        result.setTotalCount(count);
        return result;
    }

    @Override
    public PageResult<FullTransferRecordJDO> queryAttFulScaTsfListView(FullTransferRecordSearchDO fullTransferRecordSearchDO,
                                                                       PageCondition pageCondition) {
        int count = fullTransferRecordDao.countAttFulScaTsfListView(fullTransferRecordSearchDO, pageCondition);
        PageResult<FullTransferRecordJDO> result = new PageResult<FullTransferRecordJDO>(pageCondition);
        if (count > 0) {
            List<FullTransferRecordJDO> list = this.fullTransferRecordDao.getAttFulScaTsfListView(
                    fullTransferRecordSearchDO, pageCondition);

            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PageResult<FullTransferRecordJDO> queryBuyFullTransferListView(FullTransferRecordSearchDO fullTransferRecordSearchDO,
                                                                          PageCondition pageCondition) {
        int count = fullTransferRecordDao.countBuyFullTransferListView(fullTransferRecordSearchDO, pageCondition);
        PageResult<FullTransferRecordJDO> result = new PageResult<FullTransferRecordJDO>(pageCondition);
        if (count > 0) {
            List<FullTransferRecordJDO> list = this.fullTransferRecordDao.getBuyFullTransferListView(
                    fullTransferRecordSearchDO, pageCondition);
            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    // TODO 优化
    private UserType queryUserTypeByUserId(int userId) {
        PlainResult<User> userResult = userService.findEntityById(userId);
        if (!userResult.isSuccess() || userResult.getData() == null) {
            throw new BusinessException("用户类型查询失败");
        }

        return userResult.getData().getUserType();
    }

}
