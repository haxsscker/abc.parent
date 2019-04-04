/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.loan.json;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanClearType;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanType;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 意向申请资料补全
 * 
 * @author segen189 2014年12月27日 下午9:16:56
 */
public class IntentLoanInfoSupplement {
    private static final Log       log = LogFactory.getLog(IntentLoanInfoSupplement.class);

    @Resource
    private LoanIntentApplyService loanIntentAppplyService;

    public JsonBaseVO execute(Context context, ParameterParser params) {
        try {
            BaseResult baseResult = new BaseResult();

            int loanIntentId = params.getInt("loanIntentId");
            JSONObject formJson = JSON.parseObject(params.getString("main"));
            Loan loan = assembleLoan(formJson);
            //使用范围
            if (!StringUtils.isEmpty(params.getString("redUseScopes"))) {
                loan.setLoanRedUseScopes(params.getString("redUseScopes").replace(",", "|"));
            }
            JSONArray list = new JSONArray();
            if (!params.getString("carInfo").equals("{}")) {
                list = JSON.parseArray(params.getString("carInfo"));
                //                if (list == null || list.isEmpty()) {
                //                    return directReturnError("自定义信息不能为空");
                //                }
            }
            baseResult = this.loanIntentAppplyService.infoSupplementForCustomLoan(loanIntentId, loan, list);

            return ResultMapper.toBaseVO(baseResult);
        } catch (Exception e) {
            if (log.isInfoEnabled()) {
                log.info("解析出错", e);
            }
            return directReturnError("资料补全失败");
        }

    }

    private Loan assembleLoan(JSONObject formJson) throws Exception {
        Loan loan = new Loan();

        if (StringUtils.isNotBlank(formJson.getString("loanId"))) {
            loan.setLoanId(Integer.valueOf(formJson.getString("loanId")));
        }
        if (StringUtils.isNotBlank(formJson.getString("loanExpireDate"))) {
            loan.setLoanExpireDate(DateUtil.parseDate(formJson.getString("loanExpireDate"), DateUtil.DEFAULT_DAY_STYLE));
        }
        if (StringUtils.isNotBlank(formJson.getString("pro_loan_no"))) {
            loan.setLoanNo(formJson.getString("pro_loan_no"));
        }
        if (StringUtils.isNotBlank(formJson.getString("pro_pay_date"))) {
            loan.setLoanPayDate(formJson.getInteger("pro_pay_date"));
        }
        loan.setLoanLogo(formJson.getString("loanLogo"));
        if (StringUtils.isNotBlank(formJson.getString("pro_intent_id"))) {
            loan.setLoanIntentId(Integer.valueOf(formJson.getString("pro_intent_id")));
        }
        loan.setLoanUserId(Integer.valueOf(formJson.getString("act_loanee_id")));

        if (formJson.containsKey("act_user_id")) {
            loan.setLoanGuarGov(Integer.valueOf(formJson.getString("act_user_id")));
        }
        loan.setLoanSecondaryAllocation(formJson.getString("loan_pay_organ"));
        if ("0".equals(formJson.getString("loan_pay_organ")) || "1".equals(formJson.getString("loan_pay_organ"))) {
            loan.setLoanSecondaryUser(formJson.getInteger("act_payee_id"));
        }
        loan.setLoanMoney(new BigDecimal(Double.valueOf(formJson.getString("pro_loan_money"))));
        loan.setLoanRate(new BigDecimal(Double.valueOf(formJson.getString("pro_loan_rate"))));
        loan.setLoanPeriod(Integer.valueOf(formJson.getString("pro_loan_period")));
        loan.setLoanPeriodUnit(LoanPeriodUnit.valueOf(formJson.getInteger("loanPeriodUnit")));
        loan.setLoanMinInvest(new BigDecimal(formJson.getString("pro_min_invest_money")));
        if (formJson.containsKey("pro_max_invest_money")) {
            loan.setLoanMaxInvest(new BigDecimal(formJson.getString("pro_max_invest_money")));
        }

        loan.setLoanPayType(LoanPayType.valueOf(formJson.getInteger("pro_pay_type")));
        if (formJson.containsKey("loanInvestStarttime")) {
            loan.setLoanInvestStarttime(DateUtil.parseDate(formJson.getString("loanInvestStarttime"),
                    DateUtil.DEFAULT_DATE_STYLE));
        }else{
        	loan.setLoanInvestStarttime(new Date());
        }
        loan.setLoanInvestEndtime(DateUtil.parseDate(formJson.getString("pro_invest_endDate"),
                DateUtil.DEFAULT_DAY_STYLE));
        loan.setInvestRedsendRatio(formJson.getDouble("investRedsendRatio"));
        loan.setInvestReduseRatio(formJson.getDouble("investReduseRatio"));
        loan.setLoanClearType(LoanClearType.valueOf(formJson.getInteger("pro_collect_type")));
        loan.setLoanUse(formJson.getString("pro_loan_use"));
        loan.setLoanCategory(LoanCategory.valueOf(formJson.getInteger("loanCategory")));
        loan.setLoanFileUrl(formJson.getString("dataId"));
        loan.setLoanCreator(LoginUserUtil.getEmpId());
        loan.setLoanNote(formJson.getString("pro_loan_note"));
        loan.setBorrowerIntroduction(formJson.getString("borrowerIntroduction"));
        loan.setRiskIntroduction(formJson.getString("riskIntroduction"));
        loan.setRelevantIntroduction(formJson.getString("relevantIntroduction"));
        //项目类型
        loan.setLoanType(LoanType.valueOf(formJson.getInteger("loan_type")));
        loan.setLoanAutomaticBid(formJson.getString("pro_auto_invest"));
        loan.setLoanContractType(formJson.getString("loan_contract_type"));
        loan.setLoanOriginalNo(formJson.getString("loan_original_no"));
        loan.setAssId(formJson.getString("ass_id"));
        loan.setIsShow(formJson.getString("is_show"));
        loan.setInvestCode(formJson.getString("invest_code"));
        loan.setHandFeeKind(formJson.getString("handFeeKind"));
        return loan;
    }

    private JsonBaseVO directReturnError(String msg) {
        JsonBaseVO result = new JsonBaseVO();
        result.setMessage(msg);
        result.setSuccess(false);
        return result;
    }
}
