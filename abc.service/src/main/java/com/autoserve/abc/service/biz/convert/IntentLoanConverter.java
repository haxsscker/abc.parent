package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;

/**
 * 意向(LoanIntentApply)和项目（Loan）的很多字段意义相同，这个Converter可以将两者进行转换
 *
 * 注意：转换时只会对两者都具有的字段进行转换，LoanIntentApply和Loan独自拥有的字段在转换时会被忽略
 *
 * @author yuqing.zheng
 *         Created on 2015-01-06,14:02
 */
public class IntentLoanConverter {

    public static Loan toLoan(LoanIntentApply intent) {
        Loan loan = new Loan();

        loan.setLoanId(intent.getLoanId());
        loan.setLoanCategory(intent.getIntentCategory());
        loan.setLoanMoney(intent.getIntentMoney());
        loan.setLoanRate(intent.getIntentRate());
        loan.setLoanPeriod(intent.getIntentPeriod());
        loan.setLoanPeriodUnit(intent.getIntentPeriodUnit());
        loan.setLoanUse(intent.getIntentUse());
        loan.setLoanPayType(intent.getIntentPayType());
        loan.setLoaneeType(intent.getIntenteeType());
        loan.setLoanUserId(intent.getUserId());
        loan.setLoanNote(intent.getNote());
        loan.setLoanCreatetime(intent.getIntentTime());

        return loan;
    }

    public static LoanIntentApply toIntent(Loan loan) {
        LoanIntentApply intent = new LoanIntentApply();

        intent.setId(loan.getLoanId());
        intent.setIntentNo(loan.getLoanNo());
        intent.setLoanId(loan.getLoanId());
        intent.setIntentCategory(loan.getLoanCategory());
        intent.setIntentMoney(loan.getLoanMoney());
        intent.setIntentRate(loan.getLoanRate());
        intent.setIntentPeriod(loan.getLoanPeriod());
        intent.setIntentPeriodUnit(loan.getLoanPeriodUnit());
        intent.setIntentUse(loan.getLoanUse());
        intent.setIntentPayType(loan.getLoanPayType());
        intent.setIntenteeType(loan.getLoaneeType());
        intent.setUserId(loan.getLoanUserId());
        intent.setNote(loan.getLoanNote());
        intent.setIntentTime(loan.getLoanCreatetime());

        return intent;
    }
}
