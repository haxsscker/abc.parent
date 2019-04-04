package com.autoserve.abc.service.biz.intf.review;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.result.*;

import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-24,11:30
 */
public interface ReviewOpLogService {
    /**
     * 创建审核操作记录
     *
     * @return 新创建的记录主键
     */
    public PlainResult<Integer> creatReviewOpLog(ReviewOp reviewOp);

    public PlainResult<ReviewOp> queryById(int reviewOpLogId);

    /**
     * 根据审核ID查询该审核的最后一条审核操作记录
     */
    public PlainResult<ReviewOp> queryReviewLastOpLog(Integer reviewId);

    /**
     * 根据审核类型与申请ID查询其审核操作历史
     */
    public PageResult<ReviewOp> queryReviewOpHistory(ReviewType reviewType, Integer applyId, PageCondition pageCondition);

    /**
     * 查询多个审核的操作历史
     *
     * @return key是applyId，value是applyId对应的所有审核操作列表
     */
    public MapResult<Integer, List<ReviewOp>> queryMultiReviewOpHistory(ReviewType reviewType, List<Integer> applyIdList);
    /**
     * 根据申请ID查询其审核操作历史
     * @param applyId
     * @return
     */
	ListResult<ReviewOp> queryReviewOpHistory(Integer applyId);
}
