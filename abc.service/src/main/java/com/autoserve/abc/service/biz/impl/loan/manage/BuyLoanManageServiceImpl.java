/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.manage;

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

import com.autoserve.abc.dao.dataobject.BuyLoanDO;
import com.autoserve.abc.dao.intf.BuyLoanDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.entity.BuyLoanTraceRecord;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealRecord;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.enums.BuyLoanTraceOperation;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.manage.BuyLoanManageService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 收购标项目管理服务
 *
 * @author segen189 2015年1月9日 下午5:13:21
 */
@Service
public class BuyLoanManageServiceImpl implements BuyLoanManageService {
    private static final Logger  log = LoggerFactory.getLogger(BuyLoanManageServiceImpl.class);

    @Resource
    private InvestDao            investDao;

    @Resource
    private BuyLoanDao           buyLoanDao;

    @Resource
    private BuyLoanService       buyLoanService;

    @Resource
    private ReviewService        reviewService;

    @Resource
    private DealRecordService    dealRecordService;

    @Resource
    private Callback<DealNotify> loanCanceledCallback;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult forceBuyLoanToFull(int buyLoanId, int operatorId, String note) {
        // 收购标强制满标
        BuyLoanTraceRecord traceRecord = new BuyLoanTraceRecord();
        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(null);
        traceRecord.setBuyLoanId(buyLoanId);
        traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.moneyTransferFail);
        traceRecord.setOldBuyLoanState(BuyLoanState.BUYING);
        traceRecord.setNewBuyLoanState(BuyLoanState.FULL_WAIT_REVIEW);
        traceRecord.setNote(note);

        BaseResult changeResult = buyLoanService.changeBuyLoanState(traceRecord);
        if (!changeResult.isSuccess()) {
            log.error("收购标强制满标失败！{}", changeResult.getMessage());
            throw new BusinessException("收购标强制满标失败");
        }

        // 发起审核流程
        Review review = new Review();
        review.setApplyId(buyLoanId);
        review.setType(ReviewType.PURCHASE_FULL_BID_REVIEW);
        review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
        BaseResult reviewRes = reviewService.initiateReview(review);
        if (!reviewRes.isSuccess()) {
            log.error("发起收购项目满标审核失败！BuyLoanId={}", buyLoanId);
            throw new BusinessException("发起收购项目满标审核失败");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult cancelBuyLoan(int buyLoanId, int operatorId, String note) {
        // 收购标流标
        // 流标 解冻金额
        // 获取所有投资的流水号集合
        BuyLoanDO buyLoanDO = buyLoanDao.findById(buyLoanId); // can not be null

        // 根据流水号集合查询交易记录
        ListResult<DealRecord> oldDealResult = dealRecordService.queryDealRecordsByInnerSeqNo(Arrays.asList(buyLoanDO
                .getBlFreezeSeqNo()));
        if (!oldDealResult.isSuccess() || CollectionUtils.isEmpty(oldDealResult.getData())) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "待解冻的投资交易记录查询失败");
        }

        // 根据查询出来的交易记录，创建新的交易记录
        Deal deal = new Deal();

        List<DealDetail> dealDetailList = new ArrayList<DealDetail>(oldDealResult.getCount());
        for (DealRecord dealRecord : oldDealResult.getData()) {
            DealDetail dealDetail = new DealDetail();

            dealDetail.setMoneyAmount(dealRecord.getMoneyAmount());
            dealDetail.setPayAccountId(dealRecord.getPayAccount());
            dealDetail.setReceiveAccountId(dealRecord.getReceiveAccount());
            dealDetail.setDealDetailType(DealDetailType.ABORT_BID_MONEY);

            dealDetailList.add(dealDetail);
        }

        deal.setInnerSeqNo(InnerSeqNo.getInstance());
        deal.setBusinessType(DealType.ABORT_BID);
        deal.setOperator(operatorId);
        deal.setDealDetail(dealDetailList);
        deal.setBusinessId(buyLoanDO.getBlOriginId());

        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, loanCanceledCallback);
        if (!dealResult.isSuccess()) {
            log.warn(dealResult.getMessage());
            throw new BusinessException("交易创建失败");
        }

        // 项目跟踪记录
        BuyLoanTraceRecord traceRecord = new BuyLoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(null);
        traceRecord.setBuyLoanId(buyLoanId);
        traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.bidCanceled);
        traceRecord.setOldBuyLoanState(null);
        traceRecord.setNewBuyLoanState(BuyLoanState.BID_CANCELED);
        traceRecord.setNote(note);

        BaseResult changeResult = buyLoanService.changeBuyLoanState(traceRecord);
        if (!changeResult.isSuccess()) {
            log.error("收购标流标失败！{}", changeResult.getMessage());
            throw new BusinessException("收购标流标失败");
        }

        // 执行新的交易
        BaseResult invokeResult = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!invokeResult.isSuccess()) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "发起收购标流标交易失败");
        }

        return BaseResult.SUCCESS;
    }

}
