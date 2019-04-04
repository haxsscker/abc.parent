/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.sysset.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.loan.NumberRuleService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPlainVO;

/**
 * 项目编号模板配置
 *
 * @author segen189 2015年1月1日 下午10:37:02
 */
public class NumberRuleUpdate {

    @Resource
    private NumberRuleService numberRuleService;

    public JsonPlainVO<String> execute(Context context, ParameterParser params) {
        BaseResult baseResult = numberRuleService.modifyNumberRule(params.getString("numberRule"));
        if (baseResult.isSuccess()) {
            int demoLoanId = 88;
            PlainResult<String> plainResult = numberRuleService.createNumberByNumberRule(demoLoanId);
            if (plainResult.isSuccess()) {
                return ResultMapper.toPlainVO(plainResult);
            }
        }

        JsonPlainVO<String> errResult = new JsonPlainVO<String>();
        errResult.setSuccess(false);
        errResult.setMessage("项目编号模版配置失败");
        return errResult;
    }
}
