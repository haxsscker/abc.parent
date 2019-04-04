/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BuyLoanDO;
import com.autoserve.abc.dao.dataobject.BuyLoanSubscribeDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.intf.BuyLoanDao;
import com.autoserve.abc.dao.intf.BuyLoanSubscribeDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TraceRecordDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.convert.BuyLoanConverter;
import com.autoserve.abc.service.biz.convert.BuyLoanTraceRecordConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.BuyLoanTraceRecord;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.enums.BuyLoanSubscribeState;
import com.autoserve.abc.service.biz.enums.BuyLoanTraceOperation;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 收购标服务的实现
 *
 * @author segen189 2014年11月23日 下午3:42:33
 */
@Service
public class BuyLoanServiceImpl implements BuyLoanService {
    private static final Logger  log = LoggerFactory.getLogger(BuyLoanServiceImpl.class);

    @Resource
    private BuyLoanDao           buyLoanDao;

    @Resource
    private BuyLoanSubscribeDao  buyLoanSubscribeDao;

    @Resource
    private TraceRecordDao       traceRecordDao;

    @Resource
    private InvestDao            investDao;

    @Resource
    private LoanDao              loanDao;

    @Resource
    private PaymentPlanService   paymentPlanService;

    @Resource
    private IncomePlanService    incomePlanService;

    @Resource
    private InvestQueryService   investQueryService;

    @Resource
    private UserService          userService;

    @Resource
    private DealRecordService    dealRecordService;

    @Resource
    private AccountInfoService   accountInfoService;

    @Resource
    private Callback<DealNotify> freezeBuyerCashCallback;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Integer> createBuyLoan(BuyLoan pojo) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (pojo == null) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM, "BuyLoan");
        }

        PlainResult<PaymentPlan> nextPaymentResult = paymentPlanService.queryNextPaymentPlan(pojo.getOriginId());
        final PaymentPlan nextPaymentPlan = nextPaymentResult.getData();
        if (!nextPaymentResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "借款标要进行的下一期还款计划查询失败");
        } else if (nextPaymentPlan == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "无债权可以收购");
        }

        // 收购人可以是原始借款人吗
        LoanDO loanDO = loanDao.findById(pojo.getOriginId());
        if (loanDO == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "原始的普通标不存在");
        } else if (loanDO.getLoanUserId().equals(pojo.getUserId())) {
            return result.setError(CommonResultCode.BIZ_ERROR, "原始的普通标借款人不能发起本次收购");
        }

        // 收购金额为剩下全部未收益计划的总额
        PlainResult<BigDecimal> buyTotalResult = paymentPlanService.sumCapitalByLoaneeIdAndLoanId(
                nextPaymentPlan.getLoanee(), nextPaymentPlan.getLoanId(), PayState.UNCLEAR);

        if (!buyTotalResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "借款标当前未结清的债权总额查询失败");
        }

        if (buyTotalResult.getData().compareTo(BigDecimal.ZERO) <= 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "借款标当前没有待收益的债权");
        }

        // 无锁检查有没有重复发起收购
        BuyLoanDO buyParam = new BuyLoanDO();
        buyParam.setBlUserId(pojo.getUserId());
        buyParam.setBlOriginId(pojo.getOriginId());
        BuyLoanDO buyLoanDO = buyLoanDao.findByParam(buyParam);
        if (buyLoanDO != null && !buyLoanDO.getBlState().equals(BuyLoanState.DELETED.getState())) {
            return result.setError(CommonResultCode.BIZ_ERROR, "收购人已经发起过收购，不可重复收购");
        }

        // TODO 确认收购的时候不能有转让满标划转，防止加入了失效数据
        ListResult<Integer> beneficiaryResult = incomePlanService.queryBeneficiaryList(loanDO.getLoanId(),
                IncomePlanState.GOING);
        if (!beneficiaryResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "查询借款标当前的收益人集合失败");
        } else if (CollectionUtils.isEmpty(beneficiaryResult.getData())) {
            return result.setError(CommonResultCode.BIZ_ERROR, "借款标当前的收益人集合为空");
        }

        InnerSeqNo innerSeqNo = InnerSeqNo.getInstance();

        BuyLoanDO toInsertBlDO = BuyLoanConverter.toBuyLoanDO(pojo);
        toInsertBlDO.setBlState(BuyLoanState.DELETED.getState());
        toInsertBlDO.setBlNextPaymentId(nextPaymentPlan.getId());
        toInsertBlDO.setBlBuyTotal(buyTotalResult.getData());
        toInsertBlDO.setBlBuyMoney(pojo.getBuyMoney());
        // toInsert.setBlBuyPeriod(blBuyPeriod);
        toInsertBlDO.setBlCurrentInvest(BigDecimal.ZERO);
        toInsertBlDO.setBlCurrentValidInvest(BigDecimal.ZERO);
        toInsertBlDO.setBlFreezeSeqNo(innerSeqNo.getUniqueNo());

        buyLoanDao.insert(toInsertBlDO);

        // 收购通知表插入数据
        List<BuyLoanSubscribeDO> toInsertList = new ArrayList<BuyLoanSubscribeDO>();
        for (Integer beneficiaryId : beneficiaryResult.getData()) {
            BuyLoanSubscribeDO subscribe = new BuyLoanSubscribeDO();

            subscribe.setBlsBuyId(toInsertBlDO.getBlId());
            subscribe.setBlsLoanId(pojo.getOriginId());
            subscribe.setBlsUserId(beneficiaryId);
            subscribe.setBlsState(BuyLoanSubscribeState.DELETED.getState());

            toInsertList.add(subscribe);
        }

        buyLoanSubscribeDao.batchInsert(toInsertList);

        // 添加收购项目跟踪记录
        BuyLoanTraceRecord traceRecord = new BuyLoanTraceRecord();

        traceRecord.setCreator(pojo.getUserId());
        traceRecord.setLoanId(pojo.getOriginId());
        traceRecord.setBuyLoanId(toInsertBlDO.getBlId());
        traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.launch);
        traceRecord.setOldBuyLoanState(null);
        traceRecord.setNewBuyLoanState(BuyLoanState.WAIT_REVIEW);
        traceRecord.setNote("发起收购项目");

        traceRecordDao.insert(BuyLoanTraceRecordConverter.toTraceRecordDO(traceRecord));

        // 添加资金支付交易记录，冻结收购人的资金
        // 获取收购人账户
        UserIdentity buyer = new UserIdentity();
        buyer.setUserId(pojo.getUserId());
        buyer.setUserType(queryUserTypeByUserId(pojo.getUserId()));

        PlainResult<Account> accountResult = accountInfoService.queryByUserId(buyer);
        if (!accountResult.isSuccess()) {
            log.warn(accountResult.getMessage());
            throw new BusinessException("用户账户查询失败");
        }
        String buyAccountNo = accountResult.getData().getAccountNo();

        Deal deal = new Deal();

        DealDetail dealDetail = new DealDetail();
        dealDetail.setMoneyAmount(pojo.getBuyMoney());
        dealDetail.setPayAccountId(buyAccountNo);
        dealDetail.setDealDetailType(DealDetailType.PURCHASE_MONEY);

        deal.setInnerSeqNo(innerSeqNo);
        deal.setBusinessType(DealType.PURCHASE);
        deal.setOperator(pojo.getUserId());
        deal.setDealDetail(Arrays.asList(dealDetail));
        deal.setBusinessId(pojo.getOriginId());

        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, freezeBuyerCashCallback);
        if (!dealResult.isSuccess()) {
            log.warn(dealResult.getMessage());
            throw new BusinessException("收购冻结交易创建失败");
        }

        // 执行交易，冻结收购人的资金
        BaseResult invokeResult = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!invokeResult.isSuccess()) {
            log.warn(invokeResult.getMessage());
            throw new BusinessException("发起收购冻结交易失败");
        }

        result.setData(toInsertBlDO.getBlId());
        return result;
    }

    // TODO 收购是全额收购，如果要支持部分收购则，收购满标资金划转时还应要解冻多余钱数
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public BaseResult modifyBuyLoanInfo(BuyLoan pojo, BuyLoanTraceRecord traceRecord) {
        if (pojo == null) {
            return new BaseResult().setError(CommonResultCode.ILEEGAL_ARGUMENT);
        } else if (pojo.getBuyLoanState() != null && traceRecord == null) {
            return new BaseResult().setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        // 添加项目跟踪状态记录
        if (traceRecord != null) {
            int inCount = traceRecordDao.insert(BuyLoanTraceRecordConverter.toTraceRecordDO(traceRecord));
            if (inCount <= 0) {
                return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "项目跟踪状态记录创建失败");
            }
        }
        // 编辑项目信息
        int upCount = buyLoanDao.update(BuyLoanConverter.toBuyLoanDO(pojo));
        if (upCount <= 0) {
            throw new BusinessException("收购标项目信息修改失败");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult changeBuyLoanState(BuyLoanTraceRecord traceRecord) {
        BaseResult result = new BaseResult();

        // 添加项目跟踪状态记录
        int count = traceRecordDao.insert(BuyLoanTraceRecordConverter.toTraceRecordDO(traceRecord));
        if (count <= 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "项目跟踪状态记录创建失败");
        }

        // 更改项目状态
        count = buyLoanDao.updateState(traceRecord.getLoanId(), traceRecord.getOldBuyLoanState().getState(),
                traceRecord.getNewBuyLoanState().getState());
        if (count <= 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "项目状态改变失败");
        }

        return result;
    }

    @Override
    public PlainResult<BuyLoan> queryById(int id) {
        PlainResult<BuyLoan> result = new PlainResult<BuyLoan>();

        BuyLoan buyLoan = BuyLoanConverter.toBuyLoan(buyLoanDao.findById(id));
        if (buyLoan == null) {
            return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "BuyLoan");
        }

        result.setData(buyLoan);
        return result;
    }

    @Override
    public ListResult<BuyLoan> queryByIds(List<Integer> ids) {
        ListResult<BuyLoan> result = new ListResult<BuyLoan>();

        if (CollectionUtils.isEmpty(ids)) {
            return result.setErrorMessage(CommonResultCode.ILEEGAL_ARGUMENT, "loanIds");
        }

        List<BuyLoanDO> buyLoanDOList = buyLoanDao.findByList(ids);

        if (CollectionUtils.isEmpty(buyLoanDOList)) {
            return result.setErrorMessage(CommonResultCode.ERROR_DATA_NOT_EXISTS, "loanIds");
        }

        result.setData(BuyLoanConverter.toBuyLoanList(buyLoanDOList));
        return result;
    }

    @Override
    public PlainResult<BuyLoan> queryByParam(BuyLoan pojo) {
        PlainResult<BuyLoan> result = new PlainResult<BuyLoan>();
        result.setData(BuyLoanConverter.toBuyLoan(buyLoanDao.findByParam(BuyLoanConverter.toBuyLoanDO(pojo))));
        return result;
    }

    @Override
    public PageResult<BuyLoan> queryListByParam(BuyLoan pojo, PageCondition pageCondition) {
        PageResult<BuyLoan> result = new PageResult<BuyLoan>(pageCondition.getPage(), pageCondition.getPageSize());

        int totalCount = buyLoanDao.countListByParam(BuyLoanConverter.toBuyLoanDO(pojo));
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(BuyLoanConverter.toBuyLoanList(buyLoanDao.findListByParam(
                    BuyLoanConverter.toBuyLoanDO(pojo), pageCondition)));
        }

        return result;
    }

    private UserType queryUserTypeByUserId(int userId) {
        PlainResult<User> userResult = userService.findEntityById(userId);
        if (!userResult.isSuccess() || userResult.getData() == null) {
            throw new BusinessException("用户类型查询失败");
        }

        return userResult.getData().getUserType();
    }

}
