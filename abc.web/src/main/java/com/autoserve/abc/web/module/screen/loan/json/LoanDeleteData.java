package com.autoserve.abc.web.module.screen.loan.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.loan.manage.LoanManageService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 融资维护－删除项目
 * 
 * @author yuqing.zheng Created on 2015-01-14,12:10
 */
public class LoanDeleteData {
    private static final Logger logger = LoggerFactory.getLogger(LoanDeleteData.class);

    @Autowired
    private LoanManageService   loanManageService;

    public JsonBaseVO execute(@Param("loanId") Integer loanId) {
        if (loanId == null || loanId <= 0) {
            logger.warn("参数校验失败，loanId必须不为空切大于0");
            return new JsonBaseVO(false, "操作失败");
        }

        Integer empId = LoginUserUtil.getEmpId();
        if (empId == null || empId <= 0) {
            logger.warn("获取当前登录人ID失败");
            return new JsonBaseVO(false, "操作失败");
        }

        BaseResult loanRes = loanManageService.removeProject(loanId, empId, "");
        if (!loanRes.isSuccess()) {
            logger.warn("删除项目失败，loanId={}, 操作人Id={}", loanId, empId);
            return new JsonBaseVO(false, loanRes.getMessage());
        }

        return JsonBaseVO.SUCCESS;
    }
}
