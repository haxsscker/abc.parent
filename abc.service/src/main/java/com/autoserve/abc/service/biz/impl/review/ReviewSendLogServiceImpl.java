package com.autoserve.abc.service.biz.impl.review;

import com.autoserve.abc.dao.dataobject.ReviewSendLogDO;
import com.autoserve.abc.dao.intf.ReviewSendLogDao;
import com.autoserve.abc.service.biz.convert.ReviewSendLogConverter;
import com.autoserve.abc.service.biz.entity.ReviewSendLog;
import com.autoserve.abc.service.biz.intf.review.ReviewSendLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-03,22:57
 */
@Service
public class ReviewSendLogServiceImpl implements ReviewSendLogService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewSendLogServiceImpl.class);

    @Autowired
    private ReviewSendLogDao sendLogDao;

    @Override
    public PlainResult<ReviewSendLog> createReviewSendLog(ReviewSendLog sendLog) {
        PlainResult<ReviewSendLog> result = new PlainResult<ReviewSendLog>();

        if (sendLog == null) {
            logger.warn("参数校验失败, ReviewSendLog不能为空");
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        if (sendLog.getPrev() == null) {
            sendLog.setPrev(ReviewSendLog.NULL_SEND_LOG);
        }

        ReviewSendLogDO sendLogDO = ReviewSendLogConverter.toReviewSendLogDO(sendLog);
        int rows = sendLogDao.insert(sendLogDO);
        if (rows != 1) {
            logger.error("创建ReviewSendLog记录出错");
            throw new BusinessException("创建ReviewSendLog记录出错");
        }

        sendLog.setId(sendLogDO.getRslId());
        result.setData(sendLog);

        return result;
    }

    @Override
    public PlainResult<ReviewSendLog> queryById(Integer id) {
        PlainResult<ReviewSendLog> result = new PlainResult<ReviewSendLog>();

        if (id == null || id <= 0) {
            logger.warn("参数校验失败,ReviewSendLog的id出错");
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        ReviewSendLogDO sendLogDO = sendLogDao.findById(id);
        if (sendLogDO == null) {
            logger.error("未找到id为{}的审核发送记录", id);
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS);
            return result;
        }

        result.setData(ReviewSendLogConverter.toReviewSendLog(sendLogDO));
        return result;
    }

    @Override
    public ListResult<ReviewSendLog> queryByReviewId(Integer reviewId) {
        ListResult<ReviewSendLog> result = new ListResult<ReviewSendLog>();

        if (reviewId == null || reviewId <= 0) {
            logger.warn("参数校验失败，reviewId={}", reviewId == null ? null : reviewId);
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        List<ReviewSendLogDO> sendLogDOList = sendLogDao.findByReviewId(reviewId);
        if (CollectionUtils.isEmpty(sendLogDOList)) {
            logger.warn("未找到相关审核发送列表");
            result.setData(new ArrayList<ReviewSendLog>());
            return result;
        }

        result.setData(ReviewSendLogConverter.toReviewSendLogList(sendLogDOList));
        return result;
    }

    @Override
    public PlainResult<ReviewSendLog> queryReviewLastSendLog(Integer reviewId) {
        PlainResult<ReviewSendLog> result = new PlainResult<ReviewSendLog>();

        if (reviewId == null || reviewId <= 0) {
            logger.warn("参数校验失败，reviewId不能为空且必须大于0");
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        ReviewSendLogDO sendLogDO = sendLogDao.findReviewLastSendLog(reviewId);
        if (sendLogDO == null) {
            logger.warn("未找到指定审核的最后发送记录，reviewId={}", reviewId);
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS);
            return result;
        }

        result.setData(ReviewSendLogConverter.toReviewSendLog(sendLogDO));
        return result;
    }

    @Override
    public BaseResult updateReviewSendLog(ReviewSendLog sendLog) {
        if (sendLog == null) {
            logger.warn("参数校验失败, ReviewSendLog不能为空");
            return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        int rows = sendLogDao.update(ReviewSendLogConverter.toReviewSendLogDO(sendLog));
        if (rows != 1) {
            logger.error("更新ReviewSendLog记录出错");
            throw new BusinessException("更新ReviewSendLog记录出错");
        }

        return BaseResult.SUCCESS;
    }
}
