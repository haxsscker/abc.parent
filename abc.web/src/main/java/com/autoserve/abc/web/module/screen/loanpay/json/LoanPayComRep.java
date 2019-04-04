package com.autoserve.abc.web.module.screen.loanpay.json;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.intf.loan.repay.RepayService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 强制还款
 *
 * @author liuwei 2014年12月29日 下午12:54:56
 */
public class LoanPayComRep {

    @Resource
    private RepayService repayService;

    public JsonBaseVO execute(@Param("planId") int planId, @Param("loanId") int loanId) {

        BaseResult result = repayService.repay(loanId, planId, PayType.FORCE_CLEAR, LoginUserUtil.getEmpId());
        return ResultMapper.toBaseVO(result);

    }
}
