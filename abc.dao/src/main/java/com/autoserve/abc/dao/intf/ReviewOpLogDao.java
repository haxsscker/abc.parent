package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ReviewOpLogDO;

public interface ReviewOpLogDao extends BaseDao<ReviewOpLogDO, Integer> {
    /**
     * 根据审核ID查询审核最后一条操作记录
     */
    public ReviewOpLogDO findReviewLastOpLog(Integer reviewId);

    public int countReviewOpLogs(@Param("reviewId") Integer reviewId);

    /**
     * 根据审核ID查询其所有审核操作记录
     */
    public List<ReviewOpLogDO> findReviewOpLogs(@Param("reviewId") Integer reviewId,
                                                @Param("pageCondition") PageCondition pageCondition);

    /**
     * 根据多个审核ID查询其所有审核操作记录
     */
    public List<ReviewOpLogDO> findMultiReviewOpLogs(@Param("reviewIdList") List<Integer> reviewIdList);
}
