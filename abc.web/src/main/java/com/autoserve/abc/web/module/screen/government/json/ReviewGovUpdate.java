package com.autoserve.abc.web.module.screen.government.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateHistoryDO;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.ReviewOpType;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.government.GovUpdateHistoryService;
import com.autoserve.abc.service.biz.intf.review.ReviewOperationService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
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
public class ReviewGovUpdate {
    private static final Logger logger = LoggerFactory.getLogger(ReviewGovUpdate.class);

    @Autowired
    private GovUpdateHistoryService historyService;

    @Autowired
    private ReviewOperationService reviewOperationService;

    public JsonBaseVO execute(@Param("govId") int govId,
                              @Param("opType") int opTypeIdx,
                              @Param("reviewType") int reviewTypeIdx,
                              @Param("message") String message) {
        if (govId <= 0 || opTypeIdx <= 0) {
            logger.warn("参数不正确 govId={}, opType={}", govId, opTypeIdx);
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }

        PlainResult<GovernmentUpdateHistoryDO> plainResult = historyService.findLastUpdateHistory(govId);
        if(!plainResult.isSuccess()){
            logger.warn("根据机构ID查询机构修改记录出错");
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }
        int govUpdateId = plainResult.getData().getGuhId();

        ReviewType reviewType = ReviewType.valueOf(reviewTypeIdx);
        ReviewOp reviewOp = buildReviewOp(opTypeIdx, message);

        BaseResult opRes = reviewOperationService.doReview(reviewType, govUpdateId, reviewOp);
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
