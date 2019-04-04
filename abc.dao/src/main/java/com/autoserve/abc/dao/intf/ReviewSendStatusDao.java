package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.ReviewSendStatusDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-03,15:42
 */
public interface ReviewSendStatusDao extends BaseDao<ReviewSendStatusDO, Integer> {
    public ReviewSendStatusDO findByReviewId(@Param("reviewId") Integer reviewId);

    public List<ReviewSendStatusDO> findByReviewIdList(@Param("reviewIdList") List<Integer> reviewIdList);

    public int updateLoanSendStatus(@Param("value") int value, @Param("reviewId") Integer reviewId);

    public int updateGuarSendStatus(@Param("value") int value, @Param("reviewId") Integer reviewId);

    public int updatePlatformSendStatus(@Param("value") int value, @Param("reviewId") Integer reviewId);
}
