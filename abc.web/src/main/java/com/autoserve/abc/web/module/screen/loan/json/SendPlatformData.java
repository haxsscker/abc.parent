package com.autoserve.abc.web.module.screen.loan.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.review.ReviewOperationService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-10,17:51
 */
public class SendPlatformData {
    private static final Logger logger = LoggerFactory.getLogger(SendPlatformData.class);

    @Autowired
    private ReviewOperationService reviewOperationService;

    public JsonBaseVO execute(@Param("loanId") Integer loanId) {

        if (loanId == null || loanId <= 0) {
            logger.warn("参数校验失败，loanId不能为空，且必须大于0，当前值为{}", loanId);
            JsonBaseVO result = new JsonBaseVO();
            result.setSuccess(false);
            result.setMessage("发送失败");
            return result;
        }

        LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
        Integer empId = emp.getEmpId();

        BaseResult sendRes = reviewOperationService.sendFinancingReviewToPlatform(loanId, empId);
        return ResultMapper.toBaseVO(sendRes);
    }
}
