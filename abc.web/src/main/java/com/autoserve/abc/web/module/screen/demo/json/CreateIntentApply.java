/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.demo.json;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 前台意向申请
 *
 * @author segen189 2015年1月24日 下午3:16:41
 */
public class CreateIntentApply {
    private static final Logger     log = LoggerFactory.getLogger(CreateIntentApply.class);

    @Resource
    private InvestService           investService;

    @Resource
    private LoanIntentApplyService loanIntentApplyService;

    public JsonBaseVO execute(Context context, ParameterParser params) {
        try {
            LoanIntentApply pojo = new LoanIntentApply();

            String mainJsonStr = params.getString("applyForm");
            JSONObject mainJson = JSON.parseObject(mainJsonStr);

            pojo.setUserId(mainJson.getInteger("userId"));
            pojo.setPhone(mainJson.getString("mobilePhone"));
            pojo.setIntentMoney(BigDecimal.valueOf(mainJson.getDouble("loanMoney")));
            pojo.setIntentPeriod(mainJson.getInteger("loanPeriod"));
            pojo.setIntentPeriodUnit(LoanPeriodUnit.valueOf(mainJson.getInteger("loanPeriodUnit")));
            pojo.setIntentUse(mainJson.getString("intentUse"));
            pojo.setNote(mainJson.getString("intentNote"));
            pojo.setIntentCategory(LoanCategory.valueOf(mainJson.getInteger("loanCategory")));

            BaseResult createResult = loanIntentApplyService.createLoanIntentApply(pojo);
            return ResultMapper.toBaseVO(createResult);
        } catch (Exception e) {
            log.warn("参数填写错误", e);
            JsonBaseVO result = new JsonBaseVO();
            result.setMessage("参数填写错误");
            result.setSuccess(false);
            return result;
        }

    }
}
