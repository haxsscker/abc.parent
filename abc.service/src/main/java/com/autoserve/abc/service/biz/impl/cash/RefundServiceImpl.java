/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RefundRecordDO;
import com.autoserve.abc.dao.dataobject.search.RefundRecordSearchDO;
import com.autoserve.abc.dao.intf.RefundRecordDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.callback.center.ReviewCallbackCenter;
import com.autoserve.abc.service.biz.convert.RefundConverter;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Refund;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.RefundState;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RefundService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 退费业务逻辑
 * 
 * @author J.YL 2014年11月18日 下午1:23:46
 */
@Service
public class RefundServiceImpl implements RefundService {

    private final Logger               logger              = LoggerFactory.getLogger(RefundServiceImpl.class);
    @Resource
    private RefundRecordDao            refundRecordDao;

    @Resource
    private ReviewService              review;

    @Resource
    private DealRecordService          dealRecord;
    @Resource
    private SysConfigService           sysConfigService;
    private final Callback<ReviewOp>   refundApplyCallback = new Callback<ReviewOp>() {

                                                               @Override
                                                               @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
                                                               public BaseResult doCallback(ReviewOp data) {
                                                                   switch (data.getOpType()) {
                                                                       case PASS:
                                                                           return doRefundApplySuccess(data);
                                                                       case REJECT:
                                                                           return doRefundApplyFailure(data);
                                                                       default:
                                                                           break;
                                                                   }
                                                                   return new BaseResult();
                                                               }

                                                           };
    private final Callback<DealNotify> refundCallback      = new Callback<DealNotify>() {

                                                               @Override
                                                               @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
                                                               public BaseResult doCallback(DealNotify data) {
                                                                   switch (data.getState()) {
                                                                       case FAILURE:
                                                                           return doRefundFailure(data);
                                                                       case SUCCESS:
                                                                           return doRefundSuccess(data);
                                                                       default:
                                                                           break;
                                                                   }
                                                                   return new BaseResult();
                                                               }

                                                           };

    /**
     * 发起退款活动
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult refund(RefundRecordDO refund) {
        InnerSeqNo seqNo = InnerSeqNo.getInstance();
        //更新退款日期 和seqNo
        refund.setRefundSeqNo(seqNo.getUniqueNo());
        refund.setRefundDate(new Date());
        refundRecordDao.updateRefundApplyState(refund);
        RefundRecordDO refundRecord = refundRecordDao.findById(refund.getRefundId());
        if (refundRecord == null) {
            throw new BusinessException(CommonResultCode.ERROR_DATA_NOT_EXISTS.getCode(), String.format(
                    "退款记录id %s 记录不存在", refund.getRefundId()));
        }
        Deal deal = new Deal();

        //构建deal
        deal.setInnerSeqNo(seqNo);
        deal.setOperator(refund.getRefundOperator());
        deal.setBusinessType(DealType.REFUND);
        deal.setBusinessId(refund.getRefundId());
        //构建detail
        DealDetail detail = new DealDetail();
        //平台账户
        detail.setDealDetailType(DealDetailType.REFUND_MONEY);
        String payAccountNo = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT).getData().getConfValue();
        detail.setMoneyAmount(refundRecord.getRefundAmount());
        detail.setPayAccountId(payAccountNo);
        detail.setReceiveAccountId(refundRecord.getRefundAccountNo());
        List<DealDetail> detailList = new ArrayList<DealDetail>(1);
        detailList.add(detail);
        deal.setDealDetail(detailList);

        dealRecord.createBusinessRecord(deal, refundCallback);
        dealRecord.invokePayment(seqNo.getUniqueNo());
        return new BaseResult();
    }

    @Override
    public PageResult<RefundRecordDO> queryListRefundRecord(RefundRecordSearchDO refund, PageCondition pageCondition) {
        List<RefundRecordDO> refundList = refundRecordDao.findList(refund, pageCondition);
        PageResult<RefundRecordDO> result = new PageResult<RefundRecordDO>(pageCondition);
        int totalCount = refundRecordDao.countBySearchDO(refund);
        result.setTotalCount(totalCount);
        result.setData(refundList);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createRefundApply(Refund refund) {
        BaseResult result = new BaseResult();
        RefundRecordDO refundRecordDO = RefundConverter.toRefundRecordDO(refund);
        refundRecordDO.setRefundApplyState(ReviewState.PENDING_REVIEW.getState());
        int flag = refundRecordDao.insert(refundRecordDO);
        if (flag <= 0) {
            throw new BusinessException("创建退款申请失败");
        }
        result.setSuccess(true);
        //发起审核流程
        Review refundReview = new Review();
        refundReview.setApplyId(refundRecordDO.getRefundId());
        refundReview.setType(ReviewType.REFUND_REVIEW);
        refundReview.setCurrRole(BaseRoleType.PLATFORM_FINANCIAL);
        BaseResult reviewRes = review.initiateReview(refundReview);
        if (!reviewRes.isSuccess()) {
            throw new BusinessException("创建退款申请失败");
        }
        return BaseResult.SUCCESS;
    }

    //审核通过
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private BaseResult doRefundApplySuccess(ReviewOp data) {
        RefundRecordDO refundRecordDO = new RefundRecordDO();
        refundRecordDO.setRefundId(data.getReview().getApplyId());
        refundRecordDO.setRefundApplyOpinion(data.getMessage());
        refundRecordDO.setRefundApplyState(ReviewState.PASS_REVIEW.getState());
        refundRecordDO.setRefundOperator(data.getEmployee().getEmpId());
        int flag = refundRecordDao.updateRefundApplyState(refundRecordDO);
        if (flag <= 0) {
            throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), "退款审核出错");
        }
        return refund(refundRecordDO);
    }

    //审核失败
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private BaseResult doRefundApplyFailure(ReviewOp data) {
        RefundRecordDO refundRecordDO = new RefundRecordDO();
        refundRecordDO.setRefundId(data.getReview().getApplyId());
        refundRecordDO.setRefundApplyOpinion(data.getMessage());
        refundRecordDO.setRefundState(RefundState.FAILURE.getState());
        refundRecordDO.setRefundApplyState(ReviewState.FAILED_PASS_REVIEW.getState());
        refundRecordDO.setRefundOperator(data.getEmployee().getEmpId());
        int flag = refundRecordDao.updateRefundApplyState(refundRecordDO);
        if (flag <= 0) {
            throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), "退款审核出错");
        }
        return new BaseResult();
    }

    //退款交易成功
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private BaseResult doRefundSuccess(DealNotify data) {
        RefundRecordDO refundRecordDo = refundRecordDao.findBySeqNo(data.getInnerSeqNo());
        refundRecordDo.setRefundSeqNo(data.getInnerSeqNo());
        refundRecordDo.setRefundState(DealState.SUCCESS.getState());
        if (0 != refundRecordDo.getRefundAmount().compareTo(data.getTotalFee())) {
            throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(), "金额错误");
        }
        int flag = refundRecordDao.updateStateBySeqNo(refundRecordDo);
        if (flag <= 0) {
            logger.warn("退款记录seqNo:{} 更新状态影响行数为0", data.getInnerSeqNo());
        }
        return new BaseResult();
    }

    //退款交易失败
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private BaseResult doRefundFailure(DealNotify data) {
        RefundRecordDO refundRecordDo = refundRecordDao.findBySeqNo(data.getInnerSeqNo());
        refundRecordDo.setRefundSeqNo(data.getInnerSeqNo());
        refundRecordDo.setRefundState(DealState.FAILURE.getState());
        if (0 != refundRecordDo.getRefundAmount().compareTo(data.getTotalFee())) {
            throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(), "金额错误");
        }
        int flag = refundRecordDao.updateStateBySeqNo(refundRecordDo);
        if (flag <= 0) {
            logger.warn("退款记录seqNo:{} 更新状态影响行数为0", data.getInnerSeqNo());
        }
        return new BaseResult();
    }

    @PostConstruct
    private void registCallback() {
        CashCallBackCenter.registCallBack(DealType.REFUND, refundCallback);
        ReviewCallbackCenter.registerCallback(ReviewType.REFUND_REVIEW, refundApplyCallback);
    }

}
