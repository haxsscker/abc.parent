package com.autoserve.abc.web.module.screen.review.json;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.loan.manage.LoanManageService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * @author yuqing.zheng Created on 2015-01-12,14:07
 */
public class LoanReviewRevoke {
    private static final Logger logger = LoggerFactory.getLogger(LoanReviewRevoke.class);

    @Autowired
    private LoanManageService   loanManageService;

    @Resource
    private ReviewService       reviewService;

    public JsonBaseVO execute(@Param("loanId") Integer loanId) {
        if (loanId == null || loanId <= 0) {
            logger.warn("参数校验失败，loanId不能为空且必须大于0");
            return new JsonBaseVO(false, "撤回失败");
        }

        Integer empId = LoginUserUtil.getEmpId();
        if (empId == null || empId <= 0) {
            logger.error("获取当前用户ID失败！");
            return new JsonBaseVO(false, "撤回失败");
        }

        BaseResult baseResult = loanManageService.revokeToWaitProjectReview(loanId, empId, "");
        if (!baseResult.isSuccess()) {
            logger.warn("撤回失败，loanId={}", loanId);
            return new JsonBaseVO(false, "撤回失败");
        }
        return JsonBaseVO.SUCCESS;
    }
}
