/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.manage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.TraceRecordDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealRecord;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.FullTransferType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanTraceOperation;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
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
@Service
public class DmTransferLoanManageServiceImpl implements TransferLoanManageService {
    private static final Logger   log = LoggerFactory.getLogger(DmTransferLoanManageServiceImpl.class);

    @Resource
    private InvestDao             investDao;

    @Resource
    private TransferLoanService   transferLoanService;

    @Resource
    private ReviewService         reviewService;

    @Resource
    private DealRecordService     dealRecordService;

    @Resource
    private Callback<DealNotify>  loanCanceledCallback;
    @Resource
    private InvestOrderService    investOrderService;
    @Resource
    private TraceRecordDao        traceRecordDao;
    @Resource
    private FullTransferRecordDao fullTransferRecordDao;
    @Resource
    private InvestService investService;
    
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

    /**
     * 债权流标的两种情况
     * 1.有投资
     * 2.无投资
     */
    @Override
    public BaseResult cancelTransferLoan(int transferLoanId, int operatorId, String note) {
        //从abc_invest_order获取该原始标号在钱多多平台投资的标号
//        List<InvestOrderDO> listLoanSeq = investOrderService.queryInvestOrderByBidId(transferLoanId,
//                BidType.TRANSFER_LOAN.getType());
//        StringBuffer loanNo = new StringBuffer();
//        for (int i = 0; i < listLoanSeq.size(); i++) {
//        	InvestOrderDO investOrderDO =listLoanSeq.get(i);
//        	 String seq = investOrderDO.getIoOutSeqNo();
//             if (i == 0) {
//                 loanNo = loanNo.append(seq);
//             } else {
//                 loanNo = loanNo.append(",").append(seq);
//             }
//        }
//        String LoanNoList = loanNo.toString();
        PlainResult<TransferLoan> transferLoan = transferLoanService.queryById(transferLoanId);
//        // 转让标流标
//        // 流标 解冻金额
//        // 获取所有投资的流水号集合
//        InvestDO investParam = new InvestDO();
//        investParam.setInBidId(transferLoanId);
//        investParam.setInBidType(BidType.TRANSFER_LOAN.getType());
//        investParam.setInInvestState(InvestState.PAID.getState());
//        List<InvestDO> investDOList = investDao.findListByParam(investParam, null);
//        if (CollectionUtils.isEmpty(investDOList)) {
        
          	 //是否流标（根据该标的是否有人投资判断，如果没人投资则直接流标，有人投资则为强制满标）
        	 boolean isCancelBid = true;
        	 InvestDO ivtDo = new InvestDO();
        	 ivtDo.setInBidId(transferLoanId);
        	 ivtDo.setInBidType(BidType.TRANSFER_LOAN.getType());
        	 List<InvestDO> ivtDo2 = investDao.findListParam(ivtDo);
        	 if(ivtDo2!=null && ivtDo2.size()>0){
        		 isCancelBid=false;
        	 }
        	 //无投资，更新债权标的状态
        	 TransferLoan toModify = new TransferLoan();
             toModify.setId(transferLoanId);
             if(isCancelBid){
            	 toModify.setTransferLoanState(TransferLoanState.BID_CANCELED);
             }else{
            	 toModify.setTransferLoanState(TransferLoanState.MONEY_TRANSFERED);
             }
             
             toModify.setModifytime(new Date());
             // 项目跟踪状态记录
             TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();
             traceRecord.setCreator(0);
             traceRecord.setTransferLoanId(transferLoanId);
             if(isCancelBid){
            	 traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.bidCanceled);
            	 traceRecord.setNewTransferLoanState(TransferLoanState.BID_CANCELED);
            	 traceRecord.setNote("转让标项目流标成功");
             }else{
            	 traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.moneyTransferSucceed);
            	 traceRecord.setNewTransferLoanState(TransferLoanState.MONEY_TRANSFERED);
            	 traceRecord.setNote("转让标项目划转成功");
             }
            
             traceRecord.setOldTransferLoanState(transferLoan.getData().getTransferLoanState());
             BaseResult modResult = transferLoanService.modifyTransferLoanInfo(toModify, traceRecord);
             if (!modResult.isSuccess()) {
                 throw new BusinessException("转让标状态修改失败");
             }
//            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "待解冻的投资列表查询为空");
             return  BaseResult.SUCCESS;
//        }

//        int loanId = investDOList.get(0).getInOriginId();
//
//        List<String> seqNos = new ArrayList<String>();
//        for (InvestDO investDO : investDOList) {
//            seqNos.add(investDO.getInInnerSeqNo());
//        }
//
//        // 根据流水号集合查询交易记录
//        ListResult<DealRecord> oldDealResult = dealRecordService.queryDealRecordsByInnerSeqNo(seqNos);
//        if (!oldDealResult.isSuccess() || CollectionUtils.isEmpty(oldDealResult.getData())) {
//            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "待解冻的投资交易记录查询失败");
//        }
//
//        // 根据查询出来的交易记录，创建新的交易记录
//        Deal deal = new Deal();
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("LoanNoList", LoanNoList);
//        List<DealDetail> dealDetailList = new ArrayList<DealDetail>(oldDealResult.getCount());
//        BigDecimal fullMoney = BigDecimal.ZERO;
//        for (DealRecord dealRecord : oldDealResult.getData()) {
//            DealDetail dealDetail = new DealDetail();
//            dealDetail.setMoneyAmount(dealRecord.getMoneyAmount());
//            dealDetail.setPayAccountId(dealRecord.getPayAccount());
//            dealDetail.setReceiveAccountId(dealRecord.getReceiveAccount());
//            dealDetail.setDealDetailType(DealDetailType.ABORT_BID_MONEY);
//            dealDetail.setData(params);
//            fullMoney = fullMoney.add(dealRecord.getMoneyAmount());
//            dealDetailList.add(dealDetail);
//        }
//
//        deal.setInnerSeqNo(InnerSeqNo.getInstance());
//        deal.setBusinessType(DealType.ABORT_BID);
//        deal.setOperator(operatorId);
//        deal.setDealDetail(dealDetailList);
//        deal.setBusinessId(loanId);
//
//        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, loanCanceledCallback);
//        if (!dealResult.isSuccess()) {
//            log.warn(dealResult.getMessage());
//            throw new BusinessException("交易创建失败");
//        }

        // 5. 添加满标资金划转记录， 状态为 DealState.NOCALLBACK
//        FullTransferRecordDO fullTransRecord = new FullTransferRecordDO();
//        fullTransRecord.setFtrBidId(transferLoanId);
//        fullTransRecord.setFtrOriginId(transferLoan.getData().getOriginId());
//        fullTransRecord.setFtrOperator(operatorId);
//        fullTransRecord.setFtrUserId(transferLoan.getData().getUserId());
//        fullTransRecord.setFtrBidType(BidType.TRANSFER_LOAN.getType());
//        fullTransRecord.setFtrSeqNo(dealResult.getData().getDrInnerSeqNo());
//        fullTransRecord.setFtrTransferDate(new Date());
//        fullTransRecord.setFtrTransferType(FullTransferType.TRANSF_LOAN_FULL_TRANSFER.getType());

//        fullTransRecord.setFtrTransferMoney(fullMoney);
//        if (fullTransRecord.getFtrTransferMoney().compareTo(BigDecimal.ZERO) <= 0) {
//            throw new BusinessException("满标资金划转金额须是正数");
//        }
//        fullTransRecord.setFtrPayFee(BigDecimal.ZERO);
//        fullTransRecord.setFtrGuarFee(BigDecimal.ZERO);
//
//        fullTransRecord.setFtrRealPayFee(BigDecimal.ZERO);
//        fullTransRecord.setFtrRealGuarFee(BigDecimal.ZERO);
//        fullTransRecord.setFtrDealState(DealState.NOCALLBACK.getState());

//        // 满标资金划转记录id
//        int count1 = fullTransferRecordDao.insert(fullTransRecord);
//        if (count1 <= 0) {
//            BaseResult result = new BaseResult();
//            result.setMessage("转让标状态更改失败");
//            result.setSuccess(false);
//            log.error("转让标状态更改失败！{}", "");
//            // throw new BusinessException("转让标状态更改失败");
//            return result.setError(CommonResultCode.BIZ_ERROR, "项目跟踪状态记录创建失败");
//        }
//        // 项目跟踪记录
//        TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();
//        traceRecord.setCreator(operatorId);
//        traceRecord.setLoanId(transferLoan.getData().getOriginId());
//        traceRecord.setTransferLoanId(transferLoanId);
//        traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.bidCanceled);
//        if(transferLoan.getData().getTransferLoanState()==TransferLoanState.FULL_REVIEW_PASS){
//        	 traceRecord.setOldTransferLoanState(TransferLoanState.FULL_REVIEW_PASS);
//        }else{
//        	traceRecord.setOldTransferLoanState(TransferLoanState.TRANSFERING);
//        }      
//        traceRecord.setNewTransferLoanState(TransferLoanState.BID_CANCELED);
//        traceRecord.setNote(note);
//
//        // BaseResult changeResult = transferLoanService.changeTransferLoanState(traceRecord);
//        // 添加项目跟踪状态记录
//        int count = traceRecordDao.insert(TransferLoanTraceRecordConverter.toTraceRecordDO(traceRecord));
//        if (count <= 0) {
//            BaseResult result = new BaseResult();
//            result.setMessage("转让标状态更改失败");
//            result.setSuccess(false);
//            log.error("转让标状态更改失败！{}", "");
//            // throw new BusinessException("转让标状态更改失败");
//            return result.setError(CommonResultCode.BIZ_ERROR, "项目跟踪状态记录创建失败");
//        }
        /*
         * if (!changeResult.isSuccess()) { BaseResult result = new
         * BaseResult(); result.setMessage("转让标状态更改失败");
         * result.setSuccess(false); log.error("转让标状态更改失败！{}",
         * changeResult.getMessage()); // throw new
         * BusinessException("转让标状态更改失败"); return result; }
         */

//        // 执行新的交易
//        BaseResult invokeResult = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
//        if (!invokeResult.isSuccess()) {
//            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "发起转让标流标交易失败");
//        }

//        return BaseResult.SUCCESS;
    }

}
