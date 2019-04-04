/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.sysset;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.loan.NumberRuleService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 项目编号模板配置
 *
 * @author segen189 2015年1月1日 下午10:37:02
 */
public class NumberRuleView {

    @Resource
    private NumberRuleService numberRuleService;

    public void execute(Context context, ParameterParser params) {
        PlainResult<String> numberRuleResult = numberRuleService.queryNumberRule();
        PlainResult<String> demoRuleResult = numberRuleService.createNumberByNumberRule(88); // demo loan id is 88
        if (numberRuleResult.isSuccess() && demoRuleResult.isSuccess()) {
            context.put("numberRule", numberRuleResult.getData());
            context.put("demoNumberStr", demoRuleResult.getData());
        }
    }
}
