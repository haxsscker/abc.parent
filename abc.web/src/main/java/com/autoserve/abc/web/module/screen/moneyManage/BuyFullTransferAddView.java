package com.autoserve.abc.web.module.screen.moneyManage;

import java.math.BigDecimal;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.moneyManage.MoneyTransferVO;

/**
 * 类实现描述
 * 
 * @author liuwei 2015年1月8日 下午5:59:28
 */
public class BuyFullTransferAddView {

    @Resource
    private FeeSettingService   feeSettingService;

    @Resource
    private LoanQueryService    loanQueryService;

    @Resource
    private BuyLoanService      buyLoanService;

    public void execute(ParameterParser params, @Param("loanId") int loanId, @Param("buyLoanId") int buyLoanId,
                        Context context) {

        PlainResult<Loan> loanResult = loanQueryService.queryById(loanId);
        if (!loanResult.isSuccess()) {
            return;
        }

        PlainResult<BuyLoan> buyLoanResult = buyLoanService.queryById(buyLoanId);
        if (!buyLoanResult.isSuccess()) {
            return;
        }

        // 计算转让手续费
        PlainResult<FeeSetting> operatingFeeResult = feeSettingService.queryByFeeTypeLoanCategory(FeeType.PURCHASE_FEE,
                loanResult.getData().getLoanCategory(),loanResult.getData().getLoanMoney());
        BigDecimal operating = calculateFee(operatingFeeResult.getData(), buyLoanResult.getData().getBuyMoney());

        MoneyTransferVO vo = new MoneyTransferVO();

        vo.setLen_pay_fee(operating);
        vo.setLen_collect_fee(operating);
        vo.setLen_pay_total(buyLoanResult.getData().getBuyMoney().subtract(operating));
        vo.setLen_lend_money(vo.getLen_pay_total());

        context.put("moneyTransferVO", vo);
        context.put("loanId", loanId);
        context.put("bidType", BidType.BUY_LOAN.getType());
        context.put("bidId", buyLoanId);
        context.put("buyLoanId", buyLoanId);

    }

    private BigDecimal calculateFee(FeeSetting feeSetting, BigDecimal base) {
        if (feeSetting == null) {
            return null;
        }

        switch (feeSetting.getChargeType()) {
            case BY_DEAL: {
                return feeSetting.getAccurateAmount();
            }
            case BY_RATIO: {
                double fee = base.doubleValue() * feeSetting.getRate()/100;
//                if (fee < feeSetting.getMinAmount().doubleValue()) {
//                    return feeSetting.getMinAmount();
//                } else if (fee > feeSetting.getMaxAmount().doubleValue()) {
//                    return feeSetting.getMaxAmount();
//                } else {
                    return new BigDecimal(fee).setScale(2, BigDecimal.ROUND_HALF_UP);
                //}
            }
            default:
                return null;
        }

    }

}
