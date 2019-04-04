package com.autoserve.abc.web.module.screen.projectmanage.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.loan.manage.LoanManageService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-12,20:58
 */
public class ProjectReleaseReturn {
    private static final Logger logger = LoggerFactory.getLogger(ProjectReleaseReturn.class);

    @Autowired
    private LoanManageService loanManageService;

    public JsonBaseVO execute(@Param("loanId") Integer loanId) {
        if (loanId == null || loanId <= 0) {
            logger.warn("参数校验失败，loanId必须不为空并且大于0");
            return new JsonBaseVO(false, "退回失败");
        }

        Integer empId = LoginUserUtil.getEmpId();
        if (empId == null || empId <= 0) {
            logger.warn("获取当前登录人ID失败");
            return new JsonBaseVO(false, "退回失败");
        }

        BaseResult returnRes = loanManageService.sendBackToWaitProjectReview(loanId, empId, "");
        if (!returnRes.isSuccess()) {
            logger.warn("项目发布页面退回到项目初审失败！");
            return new JsonBaseVO(false, "退回失败");
        }

        return JsonBaseVO.SUCCESS;
    }
}
