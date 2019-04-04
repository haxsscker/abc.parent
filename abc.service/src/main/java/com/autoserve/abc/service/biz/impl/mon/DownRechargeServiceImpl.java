/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.mon;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DownRechargeDO;
import com.autoserve.abc.dao.dataobject.DownRechargeJDO;
import com.autoserve.abc.dao.dataobject.search.DownRechargeSearchDO;
import com.autoserve.abc.dao.intf.DownRechargeDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.callback.center.ReviewCallbackCenter;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.mon.DownRechargeService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 线下充值
 * 
 * @author J.YL 2015年1月8日 下午2:33:48
 */
@Service
public class DownRechargeServiceImpl implements DownRechargeService {

    @Resource
    private DownRechargeDao          downRechargeDao;
    @Resource
    private ReviewService            review;
    private final Callback<ReviewOp> downRechargeApplyCallback = new Callback<ReviewOp>() {

                                                                   @Override
                                                                   public BaseResult doCallback(ReviewOp data) {
                                                                       switch (data.getOpType()) {
                                                                           case PASS:
                                                                               return doDownRechargeApplySuccess(data);
                                                                           case REJECT:
                                                                               return doDownRechargeApplyFailure(data);
                                                                           default:
                                                                               break;
                                                                       }
                                                                       return new BaseResult();
                                                                   }

                                                               };

    @Override
    public PageResult<DownRechargeJDO> queryDownRecharge(DownRechargeSearchDO downRechargeDO,
                                                         PageCondition pageCondition) {
        PageResult<DownRechargeJDO> result = new PageResult<DownRechargeJDO>(pageCondition);
        List<DownRechargeJDO> downRechargeList = downRechargeDao.queryByDownRechargeSelective(downRechargeDO,
                pageCondition);
        int count = downRechargeDao.countByDownRechargeSelective(downRechargeDO);
        result.setTotalCount(count);
        result.setData(downRechargeList);
        return result;
    }

    private BaseResult doDownRechargeApplyFailure(ReviewOp data) {
        DownRechargeDO downRechargeDO = new DownRechargeDO();
        downRechargeDO.setDrId(data.getReview().getApplyId());
        downRechargeDO.setDrCheckIdear(data.getMessage());
        downRechargeDO.setDrCheckDate(data.getDate());
        downRechargeDO.setDrCheckState(ReviewState.FAILED_PASS_REVIEW.getState());
        downRechargeDO.setDrCheckOperator(data.getEmployee().getEmpId());
        int flag = downRechargeDao.updateByPrimaryKeySelective(downRechargeDO);
        if (flag <= 0) {
            throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), "线下充值审核出错");
        }
        return BaseResult.SUCCESS;
    }

    private BaseResult doDownRechargeApplySuccess(ReviewOp data) {
        DownRechargeDO downRechargeDO = new DownRechargeDO();
        downRechargeDO.setDrId(data.getReview().getApplyId());
        downRechargeDO.setDrCheckIdear(data.getMessage());
        downRechargeDO.setDrCheckDate(data.getDate());
        downRechargeDO.setDrCheckState(ReviewState.PASS_REVIEW.getState());
        downRechargeDO.setDrCheckOperator(data.getEmployee().getEmpId());
        int flag = downRechargeDao.updateByPrimaryKeySelective(downRechargeDO);
        if (flag <= 0) {
            throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), "线下充值审核出错");
        }
        return BaseResult.SUCCESS;
    }

    @Override
    public PlainResult<DownRechargeJDO> queryById(Integer downRechargeId) {
        PlainResult<DownRechargeJDO> result = new PlainResult<DownRechargeJDO>();
        DownRechargeJDO findResult = downRechargeDao.findByPrimaryKey(downRechargeId);
        result.setData(findResult);
        return result;
    }

    @Override
    public BaseResult createDownRechargeApply(DownRechargeDO downRechargeDO) {
        int flag = downRechargeDao.insert(downRechargeDO);
        if (flag <= 0) {
            throw new BusinessException(CommonResultCode.ERROR_DB.code, "创建线下充值审核失败");
        }
        //发起审核流程
        Review refundReview = new Review();
        refundReview.setApplyId(downRechargeDO.getDrId());
        refundReview.setType(ReviewType.OFFLINE_RECHARGE_REVIEW);
        refundReview.setCurrRole(BaseRoleType.PLATFORM_FINANCIAL);
        return review.initiateReview(refundReview);
    }

    @PostConstruct
    private void registCallback() {
        ReviewCallbackCenter.registerCallback(ReviewType.OFFLINE_RECHARGE_REVIEW, downRechargeApplyCallback);
    }

}
