/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.projectmanage.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.loan.manage.LoanManageService;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 发布/取消发布
 *
 * @author segen189 2014年12月24日 下午9:27:24
 */
public class ReleaseProject {
    @Resource
    private LoanManageService loanManageService;
    

    public JsonBaseVO execute(@Param("releaseOrNot") String releaseOrNot, @Param("loanId") int loanId,
                              ParameterParser params) {

        if ("release".equals(releaseOrNot)) {
            return ResultMapper.toBaseVO(loanManageService.releaseLoan(loanId, LoginUserUtil.getEmpId(), "发布项目"));
        } else if ("cancelRelease".equals(releaseOrNot)) {
            return ResultMapper.toBaseVO(loanManageService.cancelRelease(loanId, LoginUserUtil.getEmpId(), "取消发布"));
        } else {
            return new JsonBaseVO();
        }
    }

}
