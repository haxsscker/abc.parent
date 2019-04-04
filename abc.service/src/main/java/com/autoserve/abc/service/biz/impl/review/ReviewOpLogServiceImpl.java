package com.autoserve.abc.service.biz.impl.review;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ReviewDO;
import com.autoserve.abc.dao.dataobject.ReviewOpLogDO;
import com.autoserve.abc.dao.intf.ReviewDao;
import com.autoserve.abc.dao.intf.ReviewOpLogDao;
import com.autoserve.abc.service.biz.convert.ReviewOpConverter;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.review.ReviewOpLogService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.MapResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-24,11:31
 */
@Service
public class ReviewOpLogServiceImpl implements ReviewOpLogService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewOpLogServiceImpl.class);

    @Autowired
    private ReviewOpLogDao reviewOpLogDao;

    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private ReviewDao reviewDao;

    @Override
    public PlainResult<Integer> creatReviewOpLog(ReviewOp reviewOp) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        if (reviewOp == null) {
            logger.warn("参数校验失败，ReviewOp对象不能为空");
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
            return result;
        }

        ReviewOpLogDO reviewOpLogDO = ReviewOpConverter.toReviewOpLogDO(reviewOp);
        reviewOpLogDao.insert(reviewOpLogDO);
        Integer reviewOpLogId = reviewOpLogDO.getRolId();
        reviewOp.setOpLogId(reviewOpLogId);

        result.setData(reviewOpLogId);
        return result;
    }

    @Override
    public PlainResult<ReviewOp> queryById(int reviewOpLogId) {
        PlainResult<ReviewOp> result = new PlainResult<ReviewOp>();

        ReviewOpLogDO reviewOpLogDO = reviewOpLogDao.findById(reviewOpLogId);
        if (reviewOpLogDO == null) {
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "审核操作记录");
            result.setData(new ReviewOp());
            return result;
        }

        ReviewOp op = ReviewOpConverter.toReviewOp(reviewOpLogDO);
        result.setData(op);

        return result;
    }

    @Override
    public PlainResult<ReviewOp> queryReviewLastOpLog(Integer reviewId) {
        PlainResult<ReviewOp> result = new PlainResult<ReviewOp>();

        if (reviewId == null || reviewId <= 0) {
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
            result.setData(new ReviewOp());
            return result;
        }

        ReviewOpLogDO reviewOpLogDO = reviewOpLogDao.findReviewLastOpLog(reviewId);
        if (reviewOpLogDO == null) {
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "审核操作记录");
            result.setData(new ReviewOp());
            return result;
        }

        ReviewOp op = ReviewOpConverter.toReviewOp(reviewOpLogDO);
        result.setData(op);
        return result;
    }
    
    @Override
    public ListResult<ReviewOp> queryReviewOpHistory(Integer applyId){
    	ListResult<ReviewOp> result = new ListResult<ReviewOp>();
    	ReviewDO reviewDO = new ReviewDO();
    	reviewDO.setReviewApplyId(applyId);
    	List<ReviewDO> reviewList = reviewDao.find(reviewDO, null);
		List<Integer> reviewIdList = Lists.transform(reviewList, new Function<ReviewDO, Integer>() {
		     @Override
		     public Integer apply(ReviewDO review) {
		         return review.getReviewId();
		     }
		});
		List<ReviewOpLogDO> opLogDOs = reviewOpLogDao.findMultiReviewOpLogs(reviewIdList);
		result.setData(ReviewOpConverter.toReviewOpList(opLogDOs));
    	return result;
    }

    @Override
    public PageResult<ReviewOp> queryReviewOpHistory(ReviewType reviewType, Integer applyId, PageCondition pageCondition) {
        PageResult<ReviewOp> result = new PageResult<ReviewOp>(pageCondition.getPage(), pageCondition.getPageSize());

        try {
            paramCheck(reviewType, applyId);
        } catch (IllegalArgumentException e) {
            logger.error("参数校验出错");
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
            return result;
        }

        PlainResult<Review> reviewRes = reviewService.queryByTypeApplyId(reviewType, applyId);
        if (!reviewRes.isSuccess()) {
            logger.warn("根据ReviewType与applyId未找到审核，ReviewType={}，applyId={}", reviewType.prompt, applyId);
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "审核信息");
            return result;
        }

        Review review = reviewRes.getData();
        int count = reviewOpLogDao.countReviewOpLogs(review.getReviewId());
        if (count <= 0) {
            logger.warn("未找到审核操作历史，ReviewId={}", review.getReviewId());
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "审核操作历史");
            return result;
        }
        result.setTotalCount(count);

        List<ReviewOpLogDO> opLogDOs = reviewOpLogDao.findReviewOpLogs(review.getReviewId(), pageCondition);
        result.setData(ReviewOpConverter.toReviewOpList(opLogDOs));

        return result;
    }

    @Override
    public MapResult<Integer, List<ReviewOp>> queryMultiReviewOpHistory(ReviewType reviewType, List<Integer> applyIdList) {
        MapResult<Integer, List<ReviewOp>> result = new MapResult<Integer, List<ReviewOp>>();

        if (reviewType == null) {
            logger.error("reviewType不能为空");
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
            return result;
        }

        ListResult<Review> reviewRes = reviewService.queryByTypeApplyIdList(reviewType, applyIdList);
        if (!reviewRes.isSuccess()) {
            logger.warn("为找到相关审核信息");
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "相关审核信息");
            return result;
        }
        List<Review> reviewList = reviewRes.getData();

        List<Integer> reviewIdList = Lists.transform(reviewList, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getReviewId();
            }
        });
        List<ReviewOpLogDO> opLogDOs = reviewOpLogDao.findMultiReviewOpLogs(reviewIdList);
        if (CollectionUtils.isEmpty(opLogDOs)) {
            logger.warn("为找到相关审核操作信息");
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "审核操作信息");
            return result;
        }

        // opLogDOs里面包括了所有applyId相关的审核的所有操作历史
        // 需要根据applyId来把同一applyId的操作记录组成一个List

        // 首先构造key为reviewId的map，即根据reviewId将ReviewOpLogDO分组
        Map<Integer, List<ReviewOpLogDO>> logMapByReview = Maps.newHashMap();
        for (ReviewOpLogDO opLogDO : opLogDOs) {
            Integer reviewId = opLogDO.getRolReviewId();
            if (logMapByReview.containsKey(reviewId)) {
                logMapByReview.get(reviewId).add(opLogDO);
            } else {
                List<ReviewOpLogDO> list = Lists.newArrayList();
                list.add(opLogDO);
                logMapByReview.put(reviewId, list);
            }
        }

        // 因为当ReviewType确定时，一个applyId能唯一确定一个review
        // 故只需将上面的logMapByReview的key换成对应的applyId即可
        Map<Integer, List<ReviewOp>> opLogMapByApplyId = Maps.newHashMap();
        Map<Integer, Review> reviewMap = Maps.uniqueIndex(reviewList, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getReviewId();
            }
        });
        for (Map.Entry<Integer, List<ReviewOpLogDO>> entry : logMapByReview.entrySet()) {
            Integer reviewId = entry.getKey();
            Integer applyId = reviewMap.get(reviewId).getApplyId();
            List<ReviewOpLogDO> opLogDOList = entry.getValue();
            opLogMapByApplyId.put(applyId, ReviewOpConverter.toReviewOpList(opLogDOList));
        }

        result.setData(opLogMapByApplyId);
        return result;
    }

    private void paramCheck(ReviewType reviewType, Integer applyId) {
        if (reviewType == null) {
            logger.error("ReviewType不能为空");
            throw new IllegalArgumentException();
        }

        if (applyId == null || applyId <= 0) {
            logger.error("applyId不正确");
            throw new IllegalArgumentException();
        }
    }
}
