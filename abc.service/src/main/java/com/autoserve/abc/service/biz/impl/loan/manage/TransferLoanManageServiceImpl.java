/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealRecord;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanTraceOperation;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.manage.TransferLoanManageService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 转让标项目管理服务
 *
 * @author segen189 2015年1月9日 下午5:13:21
 */
//@Service
public class TransferLoanManageServiceImpl implements TransferLoanManageService {
    private static final Logger  log = LoggerFactory.getLogger(TransferLoanManageServiceImpl.class);

    @Resource
    private InvestDao            investDao;

    @Resource
    private TransferLoanService  transferLoanService;

    @Resource
    private ReviewService        reviewService;

    @Resource
    private DealRecordService    dealRecordService;

    @Resource
    private Callback<DealNotify> loanCanceledCallback;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult forceTransferLoanToFull(int transferLoanId, int operatorId, String note) {
        // 转让标强制满标
        TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(null);
        traceRecord.setTransferLoanId(transferLoanId);
        traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.fullWaitReview);
        traceRecord.setOldTransferLoanState(TransferLoanState.TRANSFERING);
        traceRecord.setNewTransferLoanState(TransferLoanState.FULL_WAIT_REVIEW);
        traceRecord.setNote(note);

        BaseResult changeResult = transferLoanService.changeTransferLoanState(traceRecord);
        if (!changeResult.isSuccess()) {
            log.error("转让标强制满标失败！{}", changeResult.getMessage());
            throw new BusinessException("转让标强制满标失败");
        }

        // 发起审核流程
        Review review = new Review();
        review.setApplyId(transferLoanId);
        review.setType(ReviewType.TRANSFER_FULL_BID_REVIEW);
        review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
        BaseResult reviewRes = reviewService.initiateReview(review);
        if (!reviewRes.isSuccess()) {
            log.error("发起转让项目满标审核失败！TransferLoanId={}", transferLoanId);
            throw new BusinessException("发起转让项目满标审核失败");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult cancelTransferLoan(int transferLoanId, int operatorId, String note) {
        // 转让标流标
        // 流标 解冻金额
        // 获取所有投资的流水号集合
        InvestDO investParam = new InvestDO();
        investParam.setInBidId(transferLoanId);
        investParam.setInBidType(BidType.TRANSFER_LOAN.getType());
        investParam.setInInvestState(InvestState.PAID.getState());
        List<InvestDO> investDOList = investDao.findListByParam(investParam, null);
        if (CollectionUtils.isEmpty(investDOList)) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "待解冻的投资列表查询为空");
        }

        int loanId = investDOList.get(0).getInOriginId();

        List<String> seqNos = new ArrayList<String>();
        for (InvestDO investDO : investDOList) {
            seqNos.add(investDO.getInInnerSeqNo());
        }

        // 根据流水号集合查询交易记录
        ListResult<DealRecord> oldDealResult = dealRecordService.queryDealRecordsByInnerSeqNo(seqNos);
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
        deal.setBusinessId(loanId);

        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, loanCanceledCallback);
        if (!dealResult.isSuccess()) {
            log.warn(dealResult.getMessage());
            throw new BusinessException("交易创建失败");
        }

        // 项目跟踪记录
        TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(null);
        traceRecord.setTransferLoanId(transferLoanId);
        traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.bidCanceled);
        traceRecord.setOldTransferLoanState(null);
        traceRecord.setNewTransferLoanState(TransferLoanState.BID_CANCELED);
        traceRecord.setNote(note);

        BaseResult changeResult = transferLoanService.changeTransferLoanState(traceRecord);
        if (!changeResult.isSuccess()) {
            log.error("转让标状态更改失败！{}", changeResult.getMessage());
            throw new BusinessException("转让标状态更改失败");
        }

        // 执行新的交易
        BaseResult invokeResult = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!invokeResult.isSuccess()) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "发起转让标流标交易失败");
        }

        return BaseResult.SUCCESS;
    }

}
