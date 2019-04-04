package com.autoserve.abc.service.biz.intf.review;

import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.BuyLoanReviewSearchDO;
import com.autoserve.abc.dao.dataobject.search.IntentReviewSearchDO;
import com.autoserve.abc.dao.dataobject.search.LoanGuarGovSearchDO;
import com.autoserve.abc.dao.dataobject.search.LoanReviewSearchDO;
import com.autoserve.abc.dao.dataobject.search.TransferReviewSearchDO;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-11-17,17:56
 */
public interface ReviewService {
    /**
     * 发起一个审核流程
     */
    public PlainResult<Review> initiateReview(Review review);

    /**
     * 发起一个由某审核通过后引起的新的审核 例如：意向审核通过后会引发项目初审 注意：两个initateReview方法的主要区别是
     * 如果发起审核时不置顶原始审核，则新发起的审核其version为1；如果有原始审核，则新审核的version为原始审核的version+1
     */
    public PlainResult<Review> initiateReview(Review review, Review originReview);

    /**
     * 项目初审与项目满标审核搜索
     */
    public PageResult<Review> searchLoanReview(LoanReviewSearchDO searchDO, PageCondition pageCondition);

    /**
     * 收购审核与收购满标审核搜索
     *
     * @author J.YL 2014-12-27,上午11：35
     */
    public PageResult<Review> searchBuyLoanReview(BuyLoanReviewSearchDO searchDO, PageCondition pageCondition);

    /**
     * 转让审核与转让满标审核搜索
     */
    public PageResult<Review> searchTransferReview(TransferReviewSearchDO searchDO, PageCondition pageCondition);

    /**
     * 转让审核搜索
     */
    public PageResult<Review> searchIntentReview(IntentReviewSearchDO searchDO, PageCondition pageCondition);

    /**
     * 信贷与担保审核搜索
     */
    public PageResult<Review> searchLoanGuarReview(LoanGuarGovSearchDO searchDO, PageCondition pageCondition);

    /**
     * 根据ReviewType与ApplyId查询审核
     */
    public PlainResult<Review> queryByTypeApplyId(ReviewType reviewType, Integer applyId);

    /**
     * 根据ReviewType与多个ApplyId查询审核
     */
    public ListResult<Review> queryByTypeApplyIdList(ReviewType reviewType, List<Integer> applyIdList);

    /**
     * 根据ReviewType与ApplyId查询未结束审核 注意：该方法对数据库行加了排它锁
     */
    public PlainResult<Review> queryActiveByTypeApplyIdWithLock(ReviewType reviewType, Integer applyId);

    /**
     * 根据Review对象查询
     */
    public PageResult<Review> query(Review review, PageCondition pageCondition);

    /**
     * 根据多个ReviewType查询
     */
    public PageResult<Review> queryByMultiTypes(List<ReviewType> reviewTypes, BaseRoleType role, Integer employeeId,
                                                PageCondition pageCondition);

    /**
     * 更新审核的version，使其+1
     */
    public BaseResult updateVersion(ReviewType reviewType, Integer applyId);

    /**
     * 更新审核状态，将审核状态变为待审核
     */
    public BaseResult updateEndAndState(Integer applyId, ReviewType reviewType);

    public PlainResult<Review> queryById(int reviewId);

    public PlainResult<Review> createReview(Review review);

    public BaseResult updateReview(Review review);

    public BaseResult deleteReview(Integer reviewId);
    
    //add by xiatt 20160625 客户要求 还款后，未资金划转的债权不做流标处理,而是还原成初始状态
    public BaseResult deleteReviewByApplyId(Integer applyId,Integer reviewType);

	public int updateReviewState(int state, int state2, Integer loanId);

}
