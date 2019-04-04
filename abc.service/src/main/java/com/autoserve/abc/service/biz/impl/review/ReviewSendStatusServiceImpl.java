package com.autoserve.abc.service.biz.impl.review;

import com.autoserve.abc.dao.dataobject.ReviewSendStatusDO;
import com.autoserve.abc.dao.intf.ReviewSendStatusDao;
import com.autoserve.abc.service.biz.convert.ReviewSendStatusConverter;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewSendStatus;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.GovType;
import com.autoserve.abc.service.biz.intf.review.ReviewSendStatusService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
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
 *         Created on 2015-01-03,16:24
 */
@Service
public class ReviewSendStatusServiceImpl implements ReviewSendStatusService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewSendStatusServiceImpl.class);

    @Autowired
    private ReviewSendStatusDao reviewSendStatusDao;

    @Autowired
    private ReviewService reviewService;

    @Override
    public BaseResult sendToLoanGov(Integer reviewId) {
        if (reviewId <= 0) {
            logger.error("参数校验失败，reviewId必须大于0");
            return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        try {
			createOrUpdateSendStatus(reviewId, GovType.SMALL_LOAN);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new BaseResult(CommonResultCode.BIZ_ERROR,e.getMessage());
		}

        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult sendToGuarGov(Integer reviewId) {
        if (reviewId <= 0) {
            logger.error("参数校验失败，reviewId必须大于0");
            return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        try {
			createOrUpdateSendStatus(reviewId, GovType.GUARANTEE_ORG);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new BaseResult(CommonResultCode.BIZ_ERROR,e.getMessage());
		}

        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult sendToPlatform(Integer reviewId) {
        if (reviewId <= 0) {
            logger.error("参数校验失败，reviewId必须大于0");
            return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        try {
			createOrUpdateSendStatus(reviewId, GovType.PLATFORM);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new BaseResult(CommonResultCode.BIZ_ERROR,e.getMessage());
		}

        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult removeReviewSendStatus(BaseRoleType toRemove, Integer reviewId) {
        int rows = 0;
        switch (toRemove) {
            case PLATFORM_SERVICE: {
                rows = reviewSendStatusDao.updatePlatformSendStatus(0, reviewId);
                break;
            }
            case LOAN_GOVERNMENT: {
                rows = reviewSendStatusDao.updateLoanSendStatus(0, reviewId);
                break;
            }
            case INSURANCE_GOVERNMENT: {
                rows = reviewSendStatusDao.updateGuarSendStatus(0, reviewId);
                break;
            }
        }

        if (rows != 1) {
            logger.error("更新审核发送状态信息失败，reviewId={}", reviewId);
            return new BaseResult(CommonResultCode.BIZ_ERROR);
        }

        return BaseResult.SUCCESS;
    }

    @Override
    public PlainResult<ReviewSendStatus> queryById(Integer id) {
        PlainResult<ReviewSendStatus> result = new PlainResult<ReviewSendStatus>();

        if (id <= 0) {
            logger.error("参数校验失败，id必须大于0");
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT, "参数错误");
            return result;
        }

        ReviewSendStatusDO reviewSendStatusDO = reviewSendStatusDao.findById(id);
        if (reviewSendStatusDO == null) {
            logger.warn("未找到相关数据，reviewSendStatudId={}", id);
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS);
            return result;
        }

        result.setData(ReviewSendStatusConverter.toReviewSendStatus(reviewSendStatusDO));
        return result;
    }

    @Override
    public PlainResult<ReviewSendStatus> queryByReviewId(Integer reviewId) {
        PlainResult<ReviewSendStatus> result = new PlainResult<ReviewSendStatus>();

        if (reviewId <= 0) {
            logger.error("参数校验失败，reviewID必须大于0");
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT, "参数错误");
            return result;
        }

        ReviewSendStatusDO reviewSendStatusDO = reviewSendStatusDao.findByReviewId(reviewId);
        if (reviewSendStatusDO == null) {
            logger.warn("未找到相关数据，reviewId={}", reviewId);
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS);
            return result;
        }

        result.setData(ReviewSendStatusConverter.toReviewSendStatus(reviewSendStatusDO));
        return result;
    }

    @Override
    public ListResult<ReviewSendStatus> queryByReviewIdList(List<Integer> reviewIdList) {
        ListResult<ReviewSendStatus> result = new ListResult<ReviewSendStatus>();

        if (CollectionUtils.isEmpty(reviewIdList)) {
            logger.warn("参数校验失败，reviewIdList为空");
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
            return result;
        }

        List<ReviewSendStatusDO> sendStatusDOList = reviewSendStatusDao.findByReviewIdList(reviewIdList);
        if (CollectionUtils.isEmpty(sendStatusDOList)) {
            logger.info("未找到相关审核发送状态");
            result.setData(new ArrayList<ReviewSendStatus>());
            return result;
        }

        result.setData(ReviewSendStatusConverter.toReviewSendStatusList(sendStatusDOList));
        return result;
    }

    @Override
    public BaseResult updateReviewSendStatus(ReviewSendStatus sendStatus) {
        if (sendStatus == null) {
            logger.warn("参数校验失败，ReviewSendStatus为空");
            return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        int rows = reviewSendStatusDao.update(ReviewSendStatusConverter.toReviewSendStatusDO(sendStatus));
        if (rows != 1) {
            logger.error("更新审核发送状态记录表失败");
            return new BaseResult(CommonResultCode.BIZ_ERROR);
        }

        return BaseResult.SUCCESS;
    }

    private void createOrUpdateSendStatus(Integer reviewId, GovType toGov) {
        PlainResult<ReviewSendStatus> statusRes = queryByReviewId(reviewId);
        if (!statusRes.isSuccess()) {
            logger.info("未找到审核发送状态记录，新建一个");

            ReviewSendStatus sendStatus = new ReviewSendStatus();
            sendStatus.setReview(new Review(reviewId));

            switch (toGov) {
                case SMALL_LOAN: {
                    sendStatus.setSendLoanGov(true);
                    break;
                }
                case GUARANTEE_ORG: {
                    sendStatus.setSendGuarGov(true);
                    break;
                }
                case PLATFORM: {
                    sendStatus.setSendPlatform(true);
                    break;
                }
            }

            int rows = reviewSendStatusDao.insert(ReviewSendStatusConverter.toReviewSendStatusDO(sendStatus));
            if (rows != 1) {
                logger.error("创建审核发送状态失败");
                throw new BusinessException("创建审核发送状态失败");
            }
        } else {
            ReviewSendStatus sendStatus = statusRes.getData();
            switch (toGov) {
                case SMALL_LOAN: {
                    if (sendStatus.getSendLoanGov()) {
                        logger.error("已发送到小贷，不能再次发送");
                        throw new BusinessException("已发送到小贷，不能再次发送");
                    }
                    sendStatus.setSendLoanGov(true);
                    break;
                }
                case GUARANTEE_ORG: {
                    if (sendStatus.getSendGuarGov()) {
                        logger.error("已发送到担保，不能再次发送");
                        throw new BusinessException("已发送到担保，不能再次发送");
                    }
                    sendStatus.setSendGuarGov(true);
                    break;
                }
                case PLATFORM: {
                    if (sendStatus.getSendPlatform()) {
                        logger.error("已发送到平台，不能再次发送");
                        throw new BusinessException("已发送到平台，不能再次发送");
                    }
                    sendStatus.setSendPlatform(true);
                    break;
                }
            }

            int rows = reviewSendStatusDao.update(ReviewSendStatusConverter.toReviewSendStatusDO(sendStatus));
            if (rows != 1) {
                logger.error("更新审核发送状态失败");
                throw new BusinessException("更新审核发送状态失败");
            }
        }
    }
}
