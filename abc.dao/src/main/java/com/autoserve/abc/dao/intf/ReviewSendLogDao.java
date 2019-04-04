package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.ReviewSendLogDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-30,18:59
 */
public interface ReviewSendLogDao extends BaseDao<ReviewSendLogDO, Integer>{
    public List<ReviewSendLogDO> findByReviewId(@Param("reviewId") Integer reviewId);

    public ReviewSendLogDO findReviewLastSendLog(@Param("reviewId") Integer reviewId);
}
