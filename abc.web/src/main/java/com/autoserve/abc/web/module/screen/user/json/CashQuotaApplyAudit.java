/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.user.json;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.intf.user.CashQuotaApplyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 免费提现额度审核
 * 
 * @author zhangkang 2015年6月18日 上午11:47:06
 */
public class CashQuotaApplyAudit {
    @Autowired
    private CashQuotaApplyService cashQuotaApplyService;

    public JsonBaseVO execute(ParameterParser params) {
        JsonBaseVO vo = new JsonBaseVO();
        Integer applyId = params.getInt("cqaId");
        boolean isPass = params.getBoolean("isPass");
        String auditOpinion = params.getString("cqaAuditOpinion");
        LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
        Employee caiwu = new Employee();
        caiwu.setEmpId(emp.getEmpId());
        BaseResult result = this.cashQuotaApplyService.audit(applyId, caiwu, isPass, auditOpinion);
        if (!result.isSuccess()) {
            vo.setSuccess(false);
            vo.setMessage(result.getMessage());
        }
        return vo;
    }
}
