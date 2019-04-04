package com.autoserve.abc.web.module.screen.credit.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.CreditApply;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.ReviewOpType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.credit.CreditService;
import com.autoserve.abc.service.biz.intf.review.ReviewOperationService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 审核信用额度
 *
 * @author RJQ 2015/1/6 20:50.
 */
public class ReviewCreditApply {

    @Autowired
    private CreditService creditService;

    @Autowired
    private ReviewOperationService reviewOperationService;

    private static final Logger logger = LoggerFactory.getLogger(ReviewCreditApply.class);

    public JsonBaseVO execute(@Param("creId") int creId,
                              @Param("userId") int userId,
                              @Param("opType") int opTypeIdx,
                              @Param("reviewType") int reviewTypeIdx,
                              @Param("checkMoney") BigDecimal checkMoney,
                              @Param("message") String message) {
        if (creId <= 0 || opTypeIdx <= 0 || userId <= 0) {
            logger.warn("参数不正确 creId={}, opType={}, userId={}", creId, opTypeIdx, userId);
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }

        ReviewType reviewType = ReviewType.valueOf(reviewTypeIdx);
        ReviewOp reviewOp = buildReviewOp(opTypeIdx, message);

        CreditApply creditApply = new CreditApply();
        creditApply.setCreditApplyId(creId);
        creditApply.setCreditReviewAmount(checkMoney);
        creditApply.setCreditReviewState(ReviewState.valueOf(opTypeIdx));
        creditApply.setCreditApplyNote(message);
        BaseResult modifyResult = creditService.modifyCreditApply(creditApply);
        if (!modifyResult.isSuccess()) {
            logger.warn("修改信用额度记录失败");
            return new JsonBaseVO(false, "修改信用额度记录失败");
        }

        BaseResult opRes = reviewOperationService.doReview(reviewType, creId, reviewOp);
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
