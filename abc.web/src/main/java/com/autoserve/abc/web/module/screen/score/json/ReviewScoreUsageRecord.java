package com.autoserve.abc.web.module.screen.score.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.ReviewOpType;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.review.ReviewOperationService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author RJQ 2014/12/19 10:57.
 */
public class ReviewScoreUsageRecord {
    private static final Logger logger = LoggerFactory.getLogger(ReviewScoreUsageRecord.class);

    @Autowired
    private ReviewOperationService reviewOperationService;

    public JsonBaseVO execute(@Param("surId") int surId,
                              @Param("opType") int opTypeIdx,
                              @Param("reviewType") int reviewTypeIdx,
                              @Param("message") String message) {
        if (surId <= 0 || opTypeIdx <= 0) {
            logger.warn("参数不正确 surId={}, opType={}", surId, opTypeIdx);
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }

        ReviewType reviewType = ReviewType.valueOf(reviewTypeIdx);
        ReviewOp reviewOp = buildReviewOp(opTypeIdx, message);

        BaseResult opRes = reviewOperationService.doReview(reviewType, surId, reviewOp);
        if (!opRes.isSuccess()) {
            logger.warn("调用审核操作接口出错");
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }

        return JsonBaseVO.SUCCESS;
    }

    private ReviewOp buildReviewOp(int opTypeIdx, String message) {
        ReviewOp reviewOp = new ReviewOp();

        reviewOp.setOpType(ReviewOpType.valueOf(opTypeIdx));
        reviewOp.setMessage(StringUtils.defaultIfBlank(message, ""));

        LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
        reviewOp.setEmployee(new Employee(emp.getEmpId()));

        return reviewOp;
    }
}
