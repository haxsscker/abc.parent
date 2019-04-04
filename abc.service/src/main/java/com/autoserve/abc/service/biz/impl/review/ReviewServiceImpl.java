package com.autoserve.abc.service.biz.impl.review;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ReviewDO;
import com.autoserve.abc.dao.dataobject.search.BuyLoanReviewSearchDO;
import com.autoserve.abc.dao.dataobject.search.IntentReviewSearchDO;
import com.autoserve.abc.dao.dataobject.search.LoanGuarGovSearchDO;
import com.autoserve.abc.dao.dataobject.search.LoanReviewSearchDO;
import com.autoserve.abc.dao.dataobject.search.TransferReviewSearchDO;
import com.autoserve.abc.dao.intf.ReviewDao;
import com.autoserve.abc.service.biz.convert.ReviewConverter;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewSendLog;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * @author yuqing.zheng Created on 2014-11-17,18:00
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    private static Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Autowired
    private ReviewDao     reviewDao;

    @Override
    public PlainResult<Review> initiateReview(Review review) {
        return initiateReview(review, null);
    }

    @Override
    public PlainResult<Review> initiateReview(Review review, Review originReview) {
        PlainResult<Review> result = new PlainResult<Review>();

        try {
            paramCheck(review);
        } catch (IllegalArgumentException e) {
            logger.warn("参数校验失败");
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
            return result;
        }

        review.setOriginReview(originReview);
        if (originReview != null) {
            // 新审核的Version为原始审核的版本加1
            review.setVersion(originReview.getVersion() + 1);
        } else {
            // 没有原始审核的新审核版本从1开始
            review.setVersion(1);
        }

        PlainResult<Review> createRes = createReview(review);
        if (!createRes.isSuccess()) {
            logger.error("发起审核失败！");
            throw new BusinessException("发起审核失败！");
        }

        result.setData(createRes.getData());
        return result;
    }

    @Override
    public PlainResult<Review> queryByTypeApplyId(ReviewType reviewType, Integer applyId) {
        PlainResult<Review> result = new PlainResult<Review>();

        if (reviewType == null || applyId == null) {
            logger.error("reviewType与applyId均不能为null");
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
            return result;
        }

        ReviewDO reviewDO = new ReviewDO();
        reviewDO.setReviewType(reviewType.type);
        reviewDO.setReviewApplyId(applyId);

        List<ReviewDO> reviewDOs = reviewDao.find(reviewDO, null);
        if (CollectionUtils.isEmpty(reviewDOs)) {
            logger.error("未找到审核,reviewType={}, applyId={}", reviewType.type, applyId);
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "相关审核信息");
            return result;
        }

        result.setData(ReviewConverter.toReview(reviewDOs.get(0)));
        return result;
    }

    @Override
    public ListResult<Review> queryByTypeApplyIdList(ReviewType reviewType, List<Integer> applyIdList) {
        ListResult<Review> result = new ListResult<Review>();

        try {
            paramCheck(reviewType);
        } catch (IllegalArgumentException e) {
            logger.error("reviewType不能为空");
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
            return result;
        }

        List<ReviewDO> reviewDOs = reviewDao.findByTypeApplyIdList(reviewType.type, applyIdList);
        if (CollectionUtils.isEmpty(reviewDOs)) {
            logger.warn("未找到审核信息");
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "相关审核信息");
            return result;
        }

        result.setData(ReviewConverter.toReviewList(reviewDOs));
        return result;
    }

    @Override
    public PageResult<Review> query(Review review, PageCondition pageCondition) {
        ReviewDO reviewDO = ReviewConverter.toReviewDO(review);

        return findWithPageCondition(reviewDO, pageCondition);
    }

    @Override
    public PageResult<Review> queryByMultiTypes(List<ReviewType> reviewTypes, BaseRoleType role, Integer employeeId,
                                                PageCondition pageCondition) {
        PageResult<Review> result = new PageResult<Review>(pageCondition.getPage(), pageCondition.getPageSize());

        if (CollectionUtils.isEmpty(reviewTypes)) {
            logger.warn("参数校验失败，reviewTypes不能为空");
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        List<Integer> typeIdxList = Lists.transform(reviewTypes, new Function<ReviewType, Integer>() {
            @Override
            public Integer apply(ReviewType type) {
                return type.type;
            }
        });

        int count = reviewDao.countByMultiTypesAndCurrRole(typeIdxList, role.index, employeeId);
        if (count <= 0) {
            logger.warn("未根据指定审核类型列表查询到相应审核");
            return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS);
        }
        result.setTotalCount(count);

        List<ReviewDO> reviewDOList = reviewDao.findByMultiTypesAndCurrRole(typeIdxList, role.index, employeeId,
                pageCondition);

        result.setData(ReviewConverter.toReviewList(reviewDOList));
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public PlainResult<Review> queryActiveByTypeApplyIdWithLock(ReviewType reviewType, Integer applyId) {
        PlainResult<Review> result = new PlainResult<Review>();

        if (reviewType == null || applyId == null) {
            logger.error("reviewType与applyId均不能为null");
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
            return result;
        }

        ReviewDO reviewDO = reviewDao.findActiveByTypeApplyIdWithLock(reviewType.type, applyId);
        if (reviewDO == null) {
            logger.error("未找到审核,reviewType={}, applyId={}", reviewType.type, applyId);
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "相关审核信息");
            return result;
        }

        result.setData(ReviewConverter.toReview(reviewDO));
        return result;
    }

    @Override
    public PageResult<Review> searchLoanReview(LoanReviewSearchDO searchDO, PageCondition pageCondition) {
        PageResult<Review> result = new PageResult<Review>(pageCondition.getPage(), pageCondition.getPageSize());

        int total = reviewDao.countForLoanReviewSearch(searchDO);
        if (total <= 0) {
            logger.warn("未搜索到审核数据");
            result.setTotalCount(0);
            result.setData(new ArrayList<Review>());
            return result;
        }

        result.setTotalCount(total);
        List<ReviewDO> reviewDOs = reviewDao.searchLoanReview(searchDO, pageCondition);
        result.setData(ReviewConverter.toReviewList(reviewDOs));

        return result;
    }

    @Override
    public PageResult<Review> searchTransferReview(TransferReviewSearchDO searchDO, PageCondition pageCondition) {
        PageResult<Review> result = new PageResult<Review>(pageCondition.getPage(), pageCondition.getPageSize());

        int total = reviewDao.countForTransferReviewSearch(searchDO);
        if (total <= 0) {
            logger.warn("未搜索到审核数据");
            result.setTotalCount(0);
            result.setData(new ArrayList<Review>());
            return result;
        }

        result.setTotalCount(total);
        List<ReviewDO> reviewDOs = reviewDao.searchTransferReview(searchDO, pageCondition);
        result.setData(ReviewConverter.toReviewList(reviewDOs));

        return result;
    }

    @Override
    public PageResult<Review> searchBuyLoanReview(BuyLoanReviewSearchDO searchDO, PageCondition pageCondition) {
        PageResult<Review> result = new PageResult<Review>(pageCondition.getPage(), pageCondition.getPageSize());

        int total = reviewDao.countForBuyLoanReviewSearch(searchDO);
        if (total <= 0) {
            logger.warn("未搜索到审核数据");
            result.setTotalCount(0);
            result.setData(new ArrayList<Review>());
            return result;
        }

        result.setTotalCount(total);
        List<ReviewDO> reviewDOs = reviewDao.searchBuyLoanReview(searchDO, pageCondition);
        result.setData(ReviewConverter.toReviewList(reviewDOs));

        return result;
    }

    @Override
    public PageResult<Review> searchIntentReview(IntentReviewSearchDO searchDO, PageCondition pageCondition) {
        PageResult<Review> result = new PageResult<Review>(pageCondition.getPage(), pageCondition.getPageSize());

        int total = reviewDao.countForIntentReviewSearch(searchDO);
        if (total <= 0) {
            logger.warn("未搜索到审核数据");
            result.setTotalCount(0);
            result.setData(new ArrayList<Review>());
            return result;
        }

        result.setTotalCount(total);
        List<ReviewDO> reviewDOs = reviewDao.searchIntentReview(searchDO, pageCondition);
        result.setData(ReviewConverter.toReviewList(reviewDOs));

        return result;
    }

    @Override
    public PageResult<Review> searchLoanGuarReview(LoanGuarGovSearchDO searchDO, PageCondition pageCondition) {
        PageResult<Review> result = new PageResult<Review>(pageCondition.getPage(), pageCondition.getPageSize());

        int total = reviewDao.countForLoanGuarReviewSearch(searchDO);
        if (total <= 0) {
            logger.warn("未搜索到审核数据");
            result.setTotalCount(0);
            result.setData(new ArrayList<Review>());
            return result;
        }

        result.setTotalCount(total);
        List<ReviewDO> reviewDOs = reviewDao.searchLoanGuarReview(searchDO, pageCondition);
        result.setData(ReviewConverter.toReviewList(reviewDOs));

        return result;
    }

    @Override
    public BaseResult updateVersion(ReviewType reviewType, Integer applyId) {
        checkNotNull(reviewType, "参数校验失败，reviewType不能为null");
        checkArgument(applyId != null && applyId > 0, "参数校验失败，applyId必须大于0");

        int rows = reviewDao.updateVersion(reviewType.type, applyId);
        if (rows != 1) {
            logger.warn("更新审核version出错");
            throw new BusinessException("更新审核version出错");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    public PlainResult<Review> createReview(Review review) {
        PlainResult<Review> result = new PlainResult<Review>();

        review.setState(ReviewState.PENDING_REVIEW);
        review.setSuspend(false);
        review.setEnd(false);

        // 创建审核时，如果没有设置version的值，则自动将其设为1
        if (review.getVersion() == null || review.getVersion() == 0) {
            review.setVersion(1);
        }

        // 创建审核时，如果没有甚至lastSendLog，自动设置为-1
        if (review.getLastSendLog() == null) {
            review.setLastSendLog(ReviewSendLog.NULL_SEND_LOG);
        }

        ReviewDO reviewDO = ReviewConverter.toReviewDO(review);
        int rows = reviewDao.insert(reviewDO);
        if (rows != 1) {
            logger.error("创建审核失败");
            throw new BusinessException("创建审核失败");
        }

        review.setReviewId(reviewDO.getReviewId());
        result.setData(review);

        return result;
    }

    @Override
    public BaseResult updateReview(Review review) {
        ReviewDO reviewDO = ReviewConverter.toReviewDO(review);
        reviewDao.update(reviewDO);

        return BaseResult.SUCCESS;
    }

    @Override
    public PlainResult<Review> queryById(int reviewId) {
        PlainResult<Review> result = new PlainResult<Review>();

        ReviewDO reviewDO = reviewDao.findById(reviewId);
        if (reviewDO == null) {
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "审核");
            result.setData(new Review());
            return result;
        }

        result.setData(ReviewConverter.toReview(reviewDO));
        return result;
    }

    @Override
    public BaseResult deleteReview(Integer reviewId) {
        if (reviewId == null) {
            logger.warn("参数校验失败， reviewId不能为空");
            return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        int rows = reviewDao.delete(reviewId);
        if (rows != 1) {
            logger.error("删除出错，reviewId={}", reviewId);
            throw new BusinessException("删除审核出错");
        }

        return BaseResult.SUCCESS;
    }
    
    /*
     * 客户要求 还款后，未资金划转的债权不做流标处理,而是还原成初始状态
     * add by xiatt 20160625 
     */
    @Override
    public BaseResult deleteReviewByApplyId(Integer applyId,Integer reviewType) {
        if (applyId == null) {
            logger.warn("参数校验失败， reviewId不能为空");
            return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        int rows = reviewDao.deleteByApplyId(applyId,reviewType);
        if (rows != 1) {
            logger.error("删除出错，applyId={}", applyId);
            throw new BusinessException("删除审核出错");
        }

        return BaseResult.SUCCESS;
    }

    private PageResult<Review> findWithPageCondition(ReviewDO reviewDO, PageCondition pageCondition) {
        PageResult<Review> result = new PageResult<Review>(pageCondition.getPage(), pageCondition.getPageSize());

        // 先查询所有符合条件的记录总数，用于设置分页的total
        int count = reviewDao.count(reviewDO);
        if (count <= 0) {
            logger.warn("找不到相应审核信息记录");
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "审核信息");
            result.setData(new ArrayList<Review>());
            return result;
        }
        result.setTotalCount(count);

        List<ReviewDO> reviewDOs = reviewDao.find(reviewDO, pageCondition);
        List<Review> reviews = ReviewConverter.toReviewList(reviewDOs);
        result.setData(reviews);

        return result;
    }

    private void paramCheck(Review review) {
        if (review == null) {
            logger.error("Review不能为空");
            throw new IllegalArgumentException();
        }

        if (review.getApplyId() <= 0) {
            logger.error("缺少审核申请信息, Review.applyId");
            throw new IllegalArgumentException();
        }

        if (review.getType() == null) {
            logger.error("审核类型不能为空, Review.type");
            throw new IllegalArgumentException();
        }

        if (review.getCurrRole() != BaseRoleType.PLATFORM_FINANCIAL
                && review.getCurrRole() != BaseRoleType.PLATFORM_SERVICE) {
            if (review.getCurrEmp() == null || review.getCurrEmp().getEmpId() <= 0) {
                logger.error("缺少初始审核人信息, Review.firstEmp");
                throw new IllegalArgumentException();
            }
        }
    }

    private void paramCheck(ReviewType reviewType) {
        if (reviewType == null) {
            logger.error("reviewType不能为空");
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult updateEndAndState(Integer applyId, ReviewType reviewType) {
        paramCheck(reviewType);
        ReviewDO reviewDO = reviewDao.findActiveByTypeApplyIdWithLock(reviewType.type, applyId);
        if (reviewDO == null) {
            logger.error("reviewType:{}, applyId:{} 的审核信息不存在！", reviewType.prompt, applyId);
            throw new BusinessException(CommonResultCode.ERROR_DATA_NOT_EXISTS.getCode(), "未找到相应审核信息");
        }
        reviewDO.setReviewEnd(false);
        reviewDO.setReviewState(ReviewState.PENDING_REVIEW.getState());
        reviewDao.update(reviewDO);
        return BaseResult.SUCCESS;
    }

	@Override
	public int updateReviewState(int newState, int oldState, Integer applyId) {
		return reviewDao.updateReviewState(newState,oldState,applyId);
	}
}
