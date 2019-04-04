/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.callback;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.BuyLoanDO;
import com.autoserve.abc.dao.intf.BuyLoanDao;
import com.autoserve.abc.dao.intf.BuyLoanSubscribeDao;
import com.autoserve.abc.dao.intf.TraceRecordDao;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.enums.BuyLoanSubscribeState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 收购时冻结收购人资金时的回调
 *
 * @author segen189 2014年12月3日 上午10:43:43
 */
@Component
public class FreezeBuyerCashCallback implements Callback<DealNotify>, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(FreezeBuyerCashCallback.class);

    @Resource
    private BuyLoanDao          buyLoanDao;

    @Resource
    private BuyLoanSubscribeDao buyLoanSubscribeDao;

    @Resource
    private TraceRecordDao  traceRecordDao;

    @Resource
    private ReviewService       reviewService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult doCallback(DealNotify data) {
        switch (data.getState()) {
            case SUCCESS:
                return doPaidSuccess(data);
            case FAILURE:
                return doPaidFailure(data);
            default:
                return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "交易状态的值不符合预期");
        }
    }

    private BaseResult doPaidSuccess(DealNotify data) {
        BuyLoanDO buyLoanDO = buyLoanDao.findByFreezeSeqNo(data.getInnerSeqNo());

        // 更新收购标状态
        int buyUpdateCount = buyLoanDao.updateState(buyLoanDO.getBlId(), BuyLoanState.DELETED.getState(),
                BuyLoanState.WAIT_REVIEW.getState());
        if (buyUpdateCount <= 0) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "更新收购标状态失败");
        }

        // 更新收购通知表状态
        int subsUpdateCount = buyLoanSubscribeDao.updateState(buyLoanDO.getBlId(), buyLoanDO.getBlUserId(),
                BuyLoanSubscribeState.DELETED.getState(), BuyLoanSubscribeState.WAITING.getState());
        if (subsUpdateCount <= 0) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "更新收购标通知表状态失败");
        }

        // 创建收购标成功后发起收购初审
        Review review = new Review();
        review.setType(ReviewType.LOAN_PURCHASE_REVIEW);
        review.setApplyId(buyLoanDO.getBlId());
        review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
        BaseResult reviewRes = reviewService.initiateReview(review);
        if (!reviewRes.isSuccess()) {
            log.error("发起收购审核失败！BuyLoanId={}", buyLoanDO.getBlId());
            throw new BusinessException("发起收购审核失败");
        }

        return BaseResult.SUCCESS;
    }

    private BaseResult doPaidFailure(DealNotify data) {
        // TODO 收购失败日志
        return BaseResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CashCallBackCenter.registCallBack(DealType.PURCHASE, this);
    }

}
