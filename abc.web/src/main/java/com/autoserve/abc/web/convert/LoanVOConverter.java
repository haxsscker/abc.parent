/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.convert;

import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.projectmanage.LoanVO;

/**
 * @author segen189 2014年12月22日 下午8:34:03
 */
public class LoanVOConverter extends ResultConverter<Loan, LoanVO> {
    private static LoanVOConverter singleton = new LoanVOConverter();

    private LoanVOConverter() {
    }

    public static LoanVOConverter getInstance() {
        return singleton;
    }

    @Override
    public LoanVO convert(Loan loan) {
        if (loan == null) {
            return null;
        }

        LoanVO loanVO = new LoanVO();

        loanVO.setLoanId(loan.getLoanId());
        loanVO.setLoanLogo(loan.getLoanLogo());
        loanVO.setLoanIntentNo(loan.getLoanIntentId());
        loanVO.setLoanNo(loan.getLoanNo());
        loanVO.setLoanUserId(loan.getLoanUserId());
        loanVO.setLoanGov(loan.getLoanGov());
        loanVO.setLoanGuarGov(loan.getLoanGuarGov());
        loanVO.setLoanMoney(loan.getLoanMoney());
        loanVO.setLoanRate(loan.getLoanRate());
        loanVO.setLoanPeriod(loan.getLoanPeriod());
        loanVO.setLoanMinInvest(loan.getLoanMinInvest());
        loanVO.setLoanMaxInvest(loan.getLoanMaxInvest());
        loanVO.setLoanCurrentInvest(loan.getLoanCurrentInvest());
        loanVO.setLoanCurrentValidInvest(loan.getLoanCurrentValidInvest());
        loanVO.setLoanUse(loan.getLoanUse());
        loanVO.setLoanCategoryId(loan.getLoanCategoryId());
        loanVO.setLoanFileUrl(loan.getLoanFileUrl());
        loanVO.setLoanCreator(loan.getLoanCreator());
        loanVO.setLoanModifier(loan.getLoanModifier());
        loanVO.setLoanNote(loan.getLoanNote());
//        loanVO.setLoanSpeed(Arith.div(loan.getLoanCurrentValidInvest(), loan.getLoanMoney()).doubleValue() * 100);
        loanVO.setLoanSpeed(Arith.calcPercent(loan.getLoanCurrentValidInvest(), loan.getLoanMoney(), loan.getLoanState()).intValue());

        if (loan.getLoaneeType() != null) {
            loanVO.setLoaneeType(loan.getLoaneeType().getPrompt());
        }
        if (loan.getLoanPeriodUnit() != null) {
            loanVO.setLoanPeriodUnit(loan.getLoanPeriodUnit().getPrompt());
        }

        if (loan.getLoanPayType() != null) {
            loanVO.setLoanPayType(loan.getLoanPayType().getPrompt());
        }

        if (loan.getLoanType() != null) {
            loanVO.setLoanType(loan.getLoanType().getPrompt());
        }

        if (loan.getLoanClearType() != null) {
            loanVO.setLoanClearType(loan.getLoanClearType().getPrompt());
        }

        if (loan.getLoanState() != null) {
            loanVO.setLoanState(loan.getLoanState().getPrompt());
        }

        if (loan.getLoanCategory() != null) {
            loanVO.setLoanCategory(loan.getLoanCategory().getPrompt());
        }
        loanVO.setLoanExpireDate(DateUtil.formatDate(loan.getLoanExpireDate(), DateUtil.DATE_FORMAT));
        loanVO.setLoanCreatetime(DateUtil.formatDate(loan.getLoanCreatetime(), DateUtil.DATE_FORMAT));
        loanVO.setLoanModifiytime(DateUtil.formatDate(loan.getLoanModifiytime(), DateUtil.DATE_FORMAT));
        loanVO.setLoanInvestStarttime(DateUtil.formatDate(loan.getLoanInvestStarttime(), DateUtil.DATE_FORMAT));
        loanVO.setLoanInvestEndtime(DateUtil.formatDate(loan.getLoanInvestEndtime(), DateUtil.DATE_FORMAT));
        loanVO.setLoanInvestFulltime(DateUtil.formatDate(loan.getLoanInvestFulltime(), DateUtil.DATE_FORMAT));
        loanVO.setLoanFullTransferedtime(DateUtil.formatDate(loan.getLoanFullTransferedtime(), DateUtil.DATE_FORMAT));

        return loanVO;
    }

}
