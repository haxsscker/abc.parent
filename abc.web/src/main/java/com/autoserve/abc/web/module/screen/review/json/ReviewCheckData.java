package com.autoserve.abc.web.module.screen.review.json;

import java.net.URLDecoder;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

/**
 * @author yuqing.zheng Created on 2014-12-25,14:53
 */
public class ReviewCheckData {
    private static final Logger    logger = LoggerFactory.getLogger(ReviewCheckData.class);

    @Autowired
    private ReviewOperationService reviewOperationService;

    public JsonBaseVO execute(@Param("reviewType") int reviewTypeIdx, @Param("applyId") int applyId,
                              @Param("opType") int opTypeIdx, @Param("message") String message) {
            if (applyId <= 0 || opTypeIdx <= 0) {
                logger.warn("参数不正确 loanId={}, opType={}", applyId, opTypeIdx);
                return new JsonBaseVO(false, "退回操作出错，请重试！");
            }
            ReviewType reviewType = ReviewType.valueOf(reviewTypeIdx);
            ReviewOp reviewOp;

            try {
            	message = "";//解决中文乱码
            	reviewOp = buildReviewOp(opTypeIdx, message);
				BaseResult opRes = reviewOperationService.doReview(reviewType, applyId, reviewOp);
				if (!opRes.isSuccess()) {
					// 转让初审&&通过 
					if(ReviewType.LOAN_TRANSFER_REVIEW.equals(reviewType) && ReviewOpType.PASS.equals(reviewOp.getOpType())){
						return new JsonBaseVO(false, opRes.getMessage());
					}else{
						logger.warn("调用审核操作接口出错");
						return new JsonBaseVO(false, opRes.getMessage());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new JsonBaseVO(false, e.getMessage());
			}
        return JsonBaseVO.SUCCESS;
    }

    private ReviewOp buildReviewOp(int opTypeIdx, String message) {
        ReviewOp reviewOp = new ReviewOp();
        reviewOp.setDate(new Date());
        reviewOp.setOpType(ReviewOpType.valueOf(opTypeIdx));
        reviewOp.setMessage(StringUtils.defaultIfBlank(message, ""));

        LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
        reviewOp.setEmployee(new Employee(emp.getEmpId()));

        return reviewOp;
    }
}
