package com.autoserve.abc.service.biz.impl.credit;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CreditApplyDO;
import com.autoserve.abc.dao.dataobject.CreditJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.CreditSearchDO;
import com.autoserve.abc.dao.intf.CreditApplyDao;
import com.autoserve.abc.dao.intf.ScoreHistoryDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.callback.center.ReviewCallbackCenter;
import com.autoserve.abc.service.biz.convert.CreditApplyConverter;
import com.autoserve.abc.service.biz.entity.CreditApply;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewOpType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.credit.CreditService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * @author RJQ 2015/1/6 15:52.
 */
@Service
public class CreditServiceImpl implements CreditService {
    private static final Logger      logger         = LoggerFactory.getLogger(CreditServiceImpl.class);

    @Resource
    private CreditApplyDao           creditApplyDao;

    @Resource
    private UserService              userService;
    @Resource
    private ReviewService            reviewService;
    @Resource
    private ScoreHistoryDao          scoreHistoryDao;
    /**
     * 信用额度审核后发起回调方法
     */
    private final Callback<ReviewOp> reviewCallback = new Callback<ReviewOp>() {

                                                        @Override
                                                        public BaseResult doCallback(ReviewOp data) {
                                                            int creditId = data.getReview().getApplyId();
                                                            ReviewOpType reviewOpType = data.getOpType();
                                                            BaseResult result = null;

                                                            switch (reviewOpType) {
                                                                case PASS:
                                                                    result = modifyCreditAfterReview(creditId,
                                                                            ReviewState.PASS_REVIEW);
                                                                    return result;
                                                                case REJECT:
                                                                    result = modifyCreditAfterReview(creditId,
                                                                            ReviewState.FAILED_PASS_REVIEW);
                                                                    return result;
                                                            }
                                                            return result;
                                                        }
                                                    };

    @PostConstruct
    private void registerCallBack() {
        ReviewCallbackCenter.registerCallback(ReviewType.CREDIT_APPLY_REVIEW, reviewCallback);
    }

    @Override
    public PageResult<CreditJDO> queryList(CreditJDO creditJDO, PageCondition pageCondition) {
        PageResult<CreditJDO> result = new PageResult<CreditJDO>(pageCondition);
        int totalCount = creditApplyDao.countListByParam(creditJDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(creditApplyDao.findListByParam(creditJDO, pageCondition));
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<CreditJDO> searchList(CreditSearchDO searchDO, PageCondition pageCondition) {
        PageResult<CreditJDO> result = new PageResult<CreditJDO>(pageCondition);
        int totalCount = creditApplyDao.countForSearch(searchDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(creditApplyDao.search(searchDO, pageCondition));
        }

        return result;
    }

    @Override
    public BaseResult createCreditApply(CreditApply creditApply) {

        PlainResult<Integer> result = createCreditApply(CreditApplyConverter.convertToDO(creditApply));

        // 发起信用审核
        BaseResult initiateResult = initReview(result.getData());
        if (!initiateResult.isSuccess()) {
            throw new BusinessException("发起审核流程失败");
        }

        return new BaseResult();
    }

    @Override
    public PlainResult<Integer> createCreditApply(CreditApplyDO creditApplyDO) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        int val = creditApplyDao.insert(creditApplyDO);
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加失败！");
        }
        result.setData(creditApplyDO.getCreditApplyId());
        return result;
    }

    @Override
    public PlainResult<CreditJDO> findFullCreditInfoById(int creditId) {
        PlainResult<CreditJDO> result = new PlainResult<CreditJDO>();
        CreditJDO creditJDO = creditApplyDao.findFullCreditInfoById(creditId);
        if (creditJDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "为找到指定信息");
            return result;
        }
        result.setData(creditJDO);
        return result;
    }

    @Override
    public BaseResult modifyCreditApply(CreditApply creditApply) {
        BaseResult result = new BaseResult();
        int val = creditApplyDao.updateByPrimaryKeySelective(CreditApplyConverter.convertToDO(creditApply));
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改信用额度记录失败！");
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult modifyCreditAfterReview(int creditId, ReviewState reviewState) {
        CreditApplyDO creditApplyDO = creditApplyDao.findById(creditId);
        if (creditApplyDO == null) {
            throw new BusinessException("信用额度记录查找失败！");
        }
        Integer userId = creditApplyDO.getCreditUserId();
        BigDecimal creditAmount = creditApplyDO.getCreditReviewAmount();

        if (reviewState.equals(ReviewState.PASS_REVIEW)) {//通过审核，修改用户的信用额度和可用信用额度
            PlainResult<UserDO> userResult = userService.findByIdWithLock(userId);
            if (!userResult.isSuccess()) {
                throw new BusinessException("查找用户信息失败！");
            }
            UserDO user = userResult.getData();
            BigDecimal userLoanCredit = user.getUserLoanCredit();//信用额度
            BigDecimal userCreditSett = user.getUserCreditSett();//可用信用额度
            //要判断原先额度是否为空，注意：前台在用户注册的时候需要用户填写信用额度，并且需要将可用信用额度初始和信用额度一致
            if (userLoanCredit == null || userCreditSett == null) {
                user.setUserLoanCredit(creditAmount);
                user.setUserCreditSett(creditAmount);
            } else {
            	//根据客户要求，信用额度、可用信用额度都要增加（前台字段是增加授信额度）
            	user.setUserLoanCredit(creditAmount.add(user.getUserLoanCredit()));
                user.setUserCreditSett(creditAmount.add(user.getUserCreditSett()));
//                user.setUserLoanCredit(creditAmount);
//                if (creditAmount.compareTo(userLoanCredit) >= 0) {//新的信用额度大于之前的额度时，可用额度需加上差值
//                    BigDecimal newUserCreditSett = userCreditSett.add(creditAmount.subtract(userLoanCredit));//可用额度需加上差值
//                    user.setUserCreditSett(newUserCreditSett);
//                } else {//新的信用额度小于之前的额度时，可用额度需减小
//                    BigDecimal usedCredit = userLoanCredit.subtract(userCreditSett);//用户已使用的信用额度
//                    if (creditAmount.compareTo(usedCredit) >= 0) {//新的信用额度大于用户已使用的信用额度时，可用额度更新为两者差值
//                        user.setUserCreditSett(creditAmount.subtract(usedCredit));
//                    } else {//新的信用额度小于用户已使用的信用额度时，可用额度更新为0，且用户借钱完成时，需要判断最大信用额度并更新相应的可用额度
//                        user.setUserCreditSett(BigDecimal.ZERO);
//                    }
//                }
            }

            BaseResult modifyResult = userService.modifyUserSelective(user);
            if (!modifyResult.isSuccess()) {
                throw new BusinessException("更新用户信用额度失败！");
            }
        }
        return BaseResult.SUCCESS;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public BaseResult initReview(int creditId) {
        BaseResult result = new BaseResult();

        Review review = new Review();
        review.setApplyId(creditId);
        review.setType(ReviewType.CREDIT_APPLY_REVIEW);
        review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);

        BaseResult reviewRes = reviewService.initiateReview(review);
        if (!reviewRes.isSuccess()) {
            logger.error("发起信用审核失败！creditId={}", creditId);
            return result.setError(CommonResultCode.BIZ_ERROR, "发起信用审核失败");
        }

        return result;
    }
    
    @Override
    public PlainResult<Integer> queryCountScore(int userid) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        Integer countScore = scoreHistoryDao.findCountScoreByUserId(userid);
        result.setData(countScore == null ? 0 : countScore);
        return result;

    }
}
