package com.autoserve.abc.web.module.screen.review.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewOpType;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.review.ReviewOperationService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-03,15:58
 */
public class ReviewSendCheckData {
    private static final Logger logger = LoggerFactory.getLogger(ReviewSendCheckData.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReviewOperationService reviewOperationService;

    public JsonBaseVO execute(@Param("reviewType") int reviewTypeIdx,
                              @Param("applyId")    int applyId,
                              @Param("message")    String message,
                              @Param("govType")    int govType,
                              @Param("govId")      int govId) {
        if (applyId <= 0 ) {
            logger.warn("参数不正确 applyId={}", applyId);
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }

        if (govId <= 0 || govType < 0) {
            logger.error("机构不正确");
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }

        ReviewOp reviewOp = buildSendReviewOp(govType, govId, message);
        if (reviewOp == null) {
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }

        ReviewType reviewType = ReviewType.valueOf(reviewTypeIdx);
        BaseResult opRes = reviewOperationService.doReview(reviewType, applyId, reviewOp);
        if (!opRes.isSuccess()) {
            logger.warn("调用审核操作接口出错");
            return new JsonBaseVO(false, opRes.getMessage());
        }

        return JsonBaseVO.SUCCESS;
    }

    private ReviewOp buildSendReviewOp(int govType, int govId, String message) {
        ReviewOp reviewOp = new ReviewOp();
        reviewOp.setMessage(message);
        reviewOp.setOpType(ReviewOpType.SEND);

        // 设置当前登录人为操作人
        LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
        reviewOp.setEmployee(new Employee(emp.getEmpId()));

        BaseRoleType toGovType;
        switch (govType) {
            case 0: {
                toGovType = BaseRoleType.LOAN_GOVERNMENT;
                break;
            }
            case 1: {
                toGovType = BaseRoleType.INSURANCE_GOVERNMENT;
                break;
            }
            case 2: {
                toGovType = BaseRoleType.PLATFORM_SERVICE;
                break;
            }
            default: {
                logger.error("govType不正确，只能是0（小贷），1（担保），2（平台）");
                return null;
            }
        }
        reviewOp.setNextRole(toGovType);

        // 发送到平台时不需要设置EmployeeID
        if (toGovType != BaseRoleType.PLATFORM_SERVICE) {
            PlainResult<Employee> empRes = employeeService.findByGovId(govId);
            if (!empRes.isSuccess()) {
                logger.error("未找到机构的EmployeeID，govId={}", govId);
                return null;
            }
            reviewOp.setNextEmp(empRes.getData());
        }

        return reviewOp;
    }
}
