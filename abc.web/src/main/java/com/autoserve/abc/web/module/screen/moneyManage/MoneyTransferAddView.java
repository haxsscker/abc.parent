package com.autoserve.abc.web.module.screen.moneyManage;

import java.math.BigDecimal;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.moneyManage.MoneyTransferVO;

/**
 * 类实现描述 资金划转页面传至
 * 
 * @author liuwei 2014年12月30日 下午5:28:45
 */

public class MoneyTransferAddView {

    @Resource
    private FeeSettingService feeSettingService;
    @Resource
    private LoanQueryService  loanQueryService;

    public JsonBaseVO execute(ParameterParser params, @Param("loanId") int loanId, Context context) {
        JsonBaseVO jsonBaseVO = new JsonBaseVO();
        PlainResult<Loan> result = loanQueryService.queryById(loanId);
        MoneyTransferVO vo = new MoneyTransferVO();
        BigDecimal insure = BigDecimal.ZERO;
        // 1. 计算服务费
        PlainResult<BigDecimal> operatingFeeResult = feeSettingService.calcFee(loanId, FeeType.PLA_SERVE_FEE);
        if(!operatingFeeResult.isSuccess()){
        	jsonBaseVO.setMessage(operatingFeeResult.getMessage());
            jsonBaseVO.setSuccess(operatingFeeResult.isSuccess());
            return jsonBaseVO;
        }
        BigDecimal operating = operatingFeeResult.getData();

        // 2. 计算担保费（如果没有担保机构则不收取担保费）
      //暂时不考虑担保费，所以这段暂时注掉
//        if (result.getData().getLoanGuarGov() != null) {
//            PlainResult<BigDecimal> insureFeeResult = feeSettingService.calcFee(loanId, FeeType.INSURANCE_FEE);           
//            if (!insureFeeResult.isSuccess()) {
//                jsonBaseVO.setMessage(insureFeeResult.getMessage());
//                jsonBaseVO.setSuccess(insureFeeResult.isSuccess());
//                return jsonBaseVO;
//            }
//            insure = insureFeeResult.getData();
//        }

        
        vo.setLen_pay_guar_fee(insure);
        vo.setLen_collect_guar_fee(insure);
        if("2".equals(result.getData().getHandFeeKind())){//分期收取
        	vo.setLen_pay_fee(BigDecimal.ZERO);
        	vo.setLen_collect_fee(BigDecimal.ZERO);
        	vo.setLen_lend_money(result.getData().getLoanCurrentValidInvest().subtract(BigDecimal.ZERO).subtract(insure));
            vo.setLen_pay_total(result.getData().getLoanCurrentValidInvest().subtract(BigDecimal.ZERO).subtract(insure));
        	context.put("servFee", operating);
        }else if("1".equals(result.getData().getHandFeeKind())){//线下收取
        	vo.setLen_pay_fee(BigDecimal.ZERO);
        	vo.setLen_collect_fee(BigDecimal.ZERO);
        	vo.setLen_lend_money(result.getData().getLoanCurrentValidInvest().subtract(BigDecimal.ZERO).subtract(insure));
            vo.setLen_pay_total(result.getData().getLoanCurrentValidInvest().subtract(BigDecimal.ZERO).subtract(insure));
        	context.put("servFee", BigDecimal.ZERO);
        }

        context.put("moneyTransferVO", vo);
        context.put("loanId", loanId);
        context.put("bidId", loanId);
        context.put("bidType", BidType.COMMON_LOAN.getType());
        return ResultMapper.toBaseVO(result);
    }

}
