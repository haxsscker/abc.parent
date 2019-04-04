/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.selfprove.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.CashQuotaApplyDO;
import com.autoserve.abc.service.biz.intf.user.CashQuotaApplyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 免费提现额度申请
 * 
 * @author zhangkang 2015年6月17日 下午2:49:37
 */
public class CashQuotaApply {

    @Autowired
    private CashQuotaApplyService cashQuotaApplyService;

    private Logger                logger = LoggerFactory.getLogger(getClass());

    public JsonBaseVO execute(@Params CashQuotaApplyDO cashQuotaApply) {
        JsonBaseVO vo = new JsonBaseVO();
        LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
        cashQuotaApply.setCqaApplyUserId(emp.getEmpId());
        cashQuotaApply.setCqaApplyUsername(emp.getEmpName());
        BaseResult result = cashQuotaApplyService.submitApply(cashQuotaApply);
        logger.debug(cashQuotaApply.toString());
        if (!result.isSuccess()) {
            vo.setSuccess(false);
            vo.setMessage(result.getMessage());
        }
        return vo;
    }
}
