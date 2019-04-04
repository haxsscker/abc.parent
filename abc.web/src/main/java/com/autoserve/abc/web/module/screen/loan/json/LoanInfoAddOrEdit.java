/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.loan.json;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

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
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.loan.LoanCarService;
import com.autoserve.abc.service.biz.intf.loan.LoanHouseService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 融资申请添加/融资申请资料补全
 * 
 * @author segen189 2014年12月27日 下午9:16:56
 */
public class LoanInfoAddOrEdit {
    private static final Log log = LogFactory.getLog(LoanInfoAddOrEdit.class);

    @Resource
    private LoanService      loanService;

    @Resource
    private LoanCarService   loanCarService;

    @Resource
    private LoanHouseService loanHouseService;

    @Resource
    private ReviewService    reviewService;

    public JsonBaseVO execute(Context context, ParameterParser params) {
        if ("add".equals(params.getString("action"))) {
            return createLoanInfo(context, params);
        } else {
            return infoSupplement(context, params);
        }
    }

    /**
     * 新增贷款信息
     */
    private JsonBaseVO createLoanInfo(Context context, ParameterParser params) {
        try {
            BaseResult baseResult = new BaseResult();

            JSONObject formJson = JSON.parseObject(params.getString("main"));
            Loan loan = assembleLoan(formJson);
            //使用范围
            if (!StringUtil.isEmpty(params.getString("redUseScopes")))
                loan.setLoanRedUseScopes(params.getString("redUseScopes").replace(",", "|"));
            //项目状态
            loan.setLoanState(LoanState.WAIT_MAINTAIN_REVIEW);
            // 项目是否来自前台意向
            loan.setLoanFromIntent(false);

            if (!params.getString("carInfo").equals("{}")) {
                JSONArray list = JSON.parseArray(params.getString("carInfo"));
                //                if (list == null || list.isEmpty()) {
                //                    return directReturnError("自定义信息不能为空");
                //                }
                baseResult = this.loanService.createCustomLoan(loan, list,LoginUserUtil.getEmpId());
            }

            return ResultMapper.toBaseVO(baseResult);
        } catch (Exception e) {
            if (log.isInfoEnabled()) {
                log.info("解析出错", e);
            }
            return directReturnError("参数解析失败");
        }
    }

    /**
     * 资料补全贷款信息
     */
    private JsonBaseVO infoSupplement(Context context, ParameterParser params) {
        try {
            BaseResult baseResult = new BaseResult();

            JSONObject formJson = JSON.parseObject(params.getString("main"));
            Loan loan = assembleLoan(formJson);
            //使用范围
            if (!StringUtil.isEmpty(params.getString("redUseScopes")))
                loan.setLoanRedUseScopes(params.getString("redUseScopes").replace(",", "|"));
            loan.setLoanId(params.getInt("loanId"));
            JSONArray list = new JSONArray();
            if (!params.getString("carInfo").equals("{}")) {
                list = JSON.parseArray(params.getString("carInfo"));
                if (list == null || list.isEmpty()) {
                    return directReturnError("自定义信息不能为空");
                }
            }
            baseResult = this.loanService.infoSupplementForCustomLoan(loan, list);

            return ResultMapper.toBaseVO(baseResult);
        } catch (Exception e) {
            if (log.isInfoEnabled()) {
                log.info("解析出错", e);
            }
            JsonBaseVO errResult = new JsonBaseVO();
            errResult.setSuccess(false);
            errResult.setMessage(e.getMessage());
            return errResult;
        }
    }

    private Loan assembleLoan(JSONObject formJson) throws Exception {
        Loan loan = new Loan();

        loan.setLoanLogo(formJson.getString("loanLogo"));
        loan.setLoanNo(formJson.getString("pro_loan_no"));
        loan.setLoanPayDate(formJson.getInteger("pro_pay_date"));
        loan.setLoanUserId(Integer.valueOf(formJson.getString("act_loanee_id")));

        if (formJson.containsKey("act_user_id")) {
    		loan.setLoanGuarGov(Integer.valueOf(formJson.getString("act_user_id")));
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
        }else {
        	loan.setLoanInvestStarttime(new Date());
        }
        loan.setLoanInvestEndtime(DateUtil.parseDate(formJson.getString("pro_invest_endDate"),
                DateUtil.DEFAULT_DAY_STYLE));
        loan.setLoanClearType(LoanClearType.valueOf(formJson.getInteger("pro_collect_type")));
        loan.setLoanUse(formJson.getString("pro_loan_use"));
        loan.setLoanCategory(LoanCategory.valueOf(formJson.getInteger("loanCategory")));
        loan.setLoanFileUrl(formJson.getString("dataId"));
        loan.setLoanCreator(LoginUserUtil.getEmpId());
        loan.setLoanNote(formJson.getString("pro_loan_note"));
        loan.setBorrowerIntroduction(formJson.getString("borrowerIntroduction"));
        loan.setRiskIntroduction(formJson.getString("riskIntroduction"));
        loan.setRelevantIntroduction(formJson.getString("relevantIntroduction"));
        loan.setLoanModifier(LoginUserUtil.getEmpId());

        //yehui add start
        //二次分配
        //loan.setLoanSecondaryAllocation(formJson.getString("payee_status"));
        if ("0".equals(formJson.getString("payee_status"))) {
            loan.setLoanSecondaryAllocation("2");
        } else {
            loan.setLoanSecondaryAllocation(formJson.getString("loan_pay_organ"));
        }
        //yehui add end

        //是否二次分配
        loan.setLoanAutomaticBid(formJson.getString("pro_auto_invest"));
        loan.setLoanContractType(formJson.getString("loan_contract_type"));
        loan.setLoanOriginalNo(formJson.getString("loan_original_no"));
        loan.setAssId(formJson.getString("ass_id"));
        loan.setIsShow(formJson.getString("is_show"));
        loan.setInvestCode(formJson.getString("invest_code"));
        
        //loan.setLoanSecondaryAllocation(formJson.getString("loan_pay_organ"));
        loan.setLoanSecondaryUser(formJson.getInteger("act_payee_id"));
        loan.setInvestRedsendRatio(formJson.getDouble("investRedsendRatio"));
        loan.setInvestReduseRatio(formJson.getDouble("investReduseRatio"));
        loan.setLoanExpireDate(DateUtil.parseDate(formJson.getString("loanExpireDate"), DateUtil.DEFAULT_DAY_STYLE));
        //项目类型
        loan.setLoanType(LoanType.valueOf(formJson.getInteger("loan_type")));
        //借款服务费收取方式
        loan.setHandFeeKind(formJson.getString("handFeeKind"));
        if("2".equals(formJson.getString("handFeeKind"))){//分期收取
        	loan.setFsRate(formJson.getDouble("fsRate"));
        	/*loan.setFsChargeType(formJson.getInteger("fsChargeType"));
        	if(1==formJson.getInteger("fsChargeType")){//按笔
        		loan.setFsAccurateAmount(new BigDecimal(Double.valueOf(formJson.getString("fsAccurateAmount"))));
        	}else{//按比例
        		loan.setFsRate(formJson.getDouble("fsRate"));
        	}*/
        }
        return loan;
    }

    private JsonBaseVO directReturnError(String msg) {
        JsonBaseVO result = new JsonBaseVO();
        result.setMessage(msg);
        result.setSuccess(false);
        return result;
    }
}
