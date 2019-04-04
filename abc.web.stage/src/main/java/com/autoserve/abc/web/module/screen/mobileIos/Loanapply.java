package com.autoserve.abc.web.module.screen.mobileIos;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 融资申请
 * 
 * @author tabor
 */
public class Loanapply {

    @Resource
    private UserService            userService;

    @Resource
    private LoanIntentApplyService loanIntentApplyService;

    public JsonMobileVO execute(Context context, ParameterParser params) throws IOException {
        JsonMobileVO result = new JsonMobileVO();

        try {
            String param = params.getString("param");
            JSONObject jsonObject = JSON.parseObject(param);

            if (!MobileHelper.check(userService, jsonObject.getInteger("userId"), result).isSuccess()) {
                return result;
            }

            LoanIntentApply loanIntentApply = new LoanIntentApply();
            loanIntentApply.setPhone(jsonObject.getString("phone"));
            loanIntentApply.setUserId(jsonObject.getInteger("userId"));
            loanIntentApply.setIntentMoney(BigDecimal.valueOf(jsonObject.getDouble("intentMoney")));
            loanIntentApply.setIntentPeriod(jsonObject.getInteger("intentPeriod"));
            loanIntentApply.setArea(jsonObject.getInteger("area"));
            Integer unit = jsonObject.getInteger("intentPeriodUnit");
            if (unit != 0) {
                loanIntentApply.setIntentPeriodUnit(LoanPeriodUnit.valueOf(unit));
            }
            String type = jsonObject.getString("type");
            //			{"信用贷","抵押贷","担保贷","综合贷"};
            // 信用贷
            if (type != null && "1".equals(type)) {
                // 标类型
                loanIntentApply.setIntentCategory(LoanCategory.LOAN_PERSON);
                // 融资利率
                loanIntentApply.setIntentRate(new BigDecimal(11));
            }
            // 抵押贷
            if (type != null && "2".equals(type)) {
                // 标类型
                loanIntentApply.setIntentCategory(LoanCategory.LOAN_CAR);
                // 融资利率
                loanIntentApply.setIntentRate(new BigDecimal(11));
            }
            // 担保贷
            if (type != null && "3".equals(type)) {
                // 标类型
                loanIntentApply.setIntentCategory(LoanCategory.LOAN_HOUSE);
                // 融资利率
                loanIntentApply.setIntentRate(new BigDecimal(11));
            }
            // 综合贷
            if (type != null && "4".equals(type)) {
                // 标类型
                loanIntentApply.setIntentCategory(LoanCategory.LOAN_CUST);
                // 融资利率
                loanIntentApply.setIntentRate(new BigDecimal(11));
            }
            // 申请时间
            loanIntentApply.setIntentTime(new Date());
            // 状态
            loanIntentApply.setIntentState(LoanState.WAIT_INTENT_REVIEW);
            BaseResult baseResult = loanIntentApplyService.createLoanIntentApply(loanIntentApply);
            if (baseResult.isSuccess()) {
                result.setResultCode("200");
                result.setResultMessage("申请成功");
            } else {
                result.setResultCode("201");
                result.setResultMessage(baseResult.getMessage());
            }
        } catch (Exception e) {
            result.setResultCode("201");
            result.setResultMessage("请求异常");
        }

        return result;
    }

}
