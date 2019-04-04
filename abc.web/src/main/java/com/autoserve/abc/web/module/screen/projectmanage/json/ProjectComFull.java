package com.autoserve.abc.web.module.screen.projectmanage.json;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.manage.LoanManageService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 强制满标
 *
 * @author liuwei 2014年12月29日 下午2:10:13
 */
public class ProjectComFull {
    private static final Logger logger = LoggerFactory.getLogger(ProjectComFull.class);

    @Autowired
    private LoanManageService   loanManageService;
    @Autowired
    private InvestService       investService;

    public JsonBaseVO execute(@Param("loanId") Integer loanId) {
        if (loanId == null || loanId <= 0) {
            logger.warn("参数校验失败，loanId必须不为空切大于0");
            return new JsonBaseVO(false, "标号必须不为空切大于0");
        }

        Integer empId = LoginUserUtil.getEmpId();
        if (empId == null || empId <= 0) {
            logger.warn("获取当前登录人ID失败");
            return new JsonBaseVO(false, "获取当前登录人ID失败");
        }
        /*ListResult<Invest> investListResult = investService.findListByParam(loanId);
        List<Invest> investList = investListResult.getData();
        if (investList.isEmpty()) {
            logger.warn("当前标还没有人投资");
            return new JsonBaseVO(false, "当前标还没有人投资");
        }*/
        BaseResult loanRes = loanManageService.forceLoanToFull(loanId, empId, "");
        if (!loanRes.isSuccess()) {
            logger.warn("强制满标失败，loanId={}, 操作人Id={}", loanId, empId);
            return new JsonBaseVO(false, "操作失败");
        }

        return JsonBaseVO.SUCCESS;
    }
}
