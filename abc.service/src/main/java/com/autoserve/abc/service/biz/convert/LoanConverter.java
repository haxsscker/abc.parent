/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanClearType;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanType;
import com.autoserve.abc.service.biz.enums.LoaneeType;

/**
 * Loan与LoanDO互相转换
 * 
 * @author segen189 2014年11月21日 下午8:47:13
 */
public class LoanConverter {
    private static final Logger logger = LoggerFactory.getLogger(LoanConverter.class);

    public static LoanDO toLoanDO(Loan loan) {
        if (loan == null) {
            return null;
        }

        LoanDO loanDO = new LoanDO();

        loanDO.setLoanId(loan.getLoanId());
        loanDO.setLoanLogo(loan.getLoanLogo());
        loanDO.setLoanFromIntent(loan.getLoanFromIntent());
        loanDO.setLoanIntentId(loan.getLoanIntentId());
        loanDO.setLoanNo(loan.getLoanNo());
        if (loan.getLoaneeType() != null) {
            loanDO.setLoanEmpType(loan.getLoaneeType().getType());
        }
        loanDO.setLoanUserId(loan.getLoanUserId());
        loanDO.setLoanGov(loan.getLoanGov());
        loanDO.setLoanGuarGov(loan.getLoanGuarGov());
        loanDO.setLoanMoney(loan.getLoanMoney());
        loanDO.setLoanRate(loan.getLoanRate());
        loanDO.setLoanPeriod(loan.getLoanPeriod());
        if (loan.getLoanPeriodUnit() != null) {
            loanDO.setLoanPeriodType(loan.getLoanPeriodUnit().getUnit());
        }
        loanDO.setLoanMinInvest(loan.getLoanMinInvest());
        loanDO.setLoanMaxInvest(loan.getLoanMaxInvest());
        loanDO.setLoanCurrentInvest(loan.getLoanCurrentInvest());
        loanDO.setLoanCurrentValidInvest(loan.getLoanCurrentValidInvest());
        if (loan.getLoanPayType() != null) {
            loanDO.setLoanPayType(loan.getLoanPayType().getType());
        }
        if (loan.getLoanType() != null) {
            loanDO.setLoanType(loan.getLoanType().getType());
        }

        loanDO.setLoanInvestStarttime(loan.getLoanInvestStarttime());
        loanDO.setLoanInvestEndtime(loan.getLoanInvestEndtime());
        loanDO.setLoanInvestFulltime(loan.getLoanInvestFulltime());
        loanDO.setLoanFullTransferedtime(loan.getLoanFullTransferedtime());
        if (loan.getLoanClearType() != null) {
            loanDO.setLoanClearType(loan.getLoanClearType().getClearType());
        }
        loanDO.setLoanUse(loan.getLoanUse());
        if (loan.getLoanState() != null) {
            loanDO.setLoanState(loan.getLoanState().getState());
        }
        if (loan.getLoanCategory() != null) {
            loanDO.setLoanCategory(loan.getLoanCategory().getCategory());
        }
        loanDO.setLoanCategoryId(loan.getLoanCategoryId());
        loanDO.setLoanFileUrl(loan.getLoanFileUrl());
        loanDO.setLoanCreator(loan.getLoanCreator());
        loanDO.setLoanModifier(loan.getLoanModifier());
        loanDO.setLoanCreatetime(loan.getLoanCreatetime());
        loanDO.setLoanModifiytime(loan.getLoanModifiytime());
        loanDO.setLoanDeleted(LoanState.DELETED.equals(loan.getLoanState()) ? 1 : 0);
        loanDO.setLoanNote(loan.getLoanNote());
        loanDO.setInvestRedsendRatio(loan.getInvestRedsendRatio());
        loanDO.setInvestReduseRatio(loan.getInvestReduseRatio());
        loanDO.setLoanSecondaryAllocation(loan.getLoanSecondaryAllocation());
        /*
         * if (loan.getLoanSecondaryAllocation() != null) {
         * loanDO.setLoanSecondaryAllocation
         * (loan.getLoanSecondaryAllocation().getType()); }
         */

        loanDO.setLoanSecondaryUser(loan.getLoanSecondaryUser());
        loanDO.setLoanPayDate(loan.getLoanPayDate());
        loanDO.setLoanExpireDate(loan.getLoanExpireDate());
        loanDO.setBorrowerIntroduction(loan.getBorrowerIntroduction());
        loanDO.setRelevantIntroduction(loan.getRelevantIntroduction());
        loanDO.setRiskIntroduction(loan.getRiskIntroduction());
        loanDO.setLoanRedUseScopes(loan.getLoanRedUseScopes());
        loanDO.setLoanReleaseDate(loan.getLoanReleaseDate());
        loanDO.setLoanAutomaticBid(loan.getLoanAutomaticBid());
        loanDO.setLoanContractType(loan.getLoanContractType());
        loanDO.setLoanOriginalNo(loan.getLoanOriginalNo());
        loanDO.setAssId(loan.getAssId());
        loanDO.setPostLoanIntroduction(loan.getPostLoanIntroduction());
        loanDO.setIsShow(loan.getIsShow());
        loanDO.setInvestCode(loan.getInvestCode());
        loanDO.setContractIssend(loan.getContractIssend());
        loanDO.setHandFeeKind(loan.getHandFeeKind());
//        loanDO.setFsChargeType(loan.getFsChargeType());
        loanDO.setFsRate(loan.getFsRate());
//        loanDO.setFsAccurateAmount(loan.getFsAccurateAmount());
        return loanDO;
    }

    public static Loan toLoan(LoanDO loanDO) {
        if (loanDO == null) {
            return null;
        }
        Loan loan = new Loan();
        loan.setLoanId(loanDO.getLoanId());
        loan.setLoanLogo(loanDO.getLoanLogo());
        loan.setLoanFromIntent(loanDO.getLoanFromIntent());
        loan.setLoanIntentId(loanDO.getLoanIntentId());
        loan.setLoanNo(loanDO.getLoanNo());
        loan.setLoaneeType(LoaneeType.valueOf(loanDO.getLoanEmpType()));
        loan.setLoanUserId(loanDO.getLoanUserId());
        loan.setLoanGov(loanDO.getLoanGov());
        loan.setLoanGuarGov(loanDO.getLoanGuarGov());
        loan.setLoanMoney(loanDO.getLoanMoney());
        loan.setLoanRate(loanDO.getLoanRate());
        loan.setLoanPeriod(loanDO.getLoanPeriod());
        loan.setLoanPeriodUnit(LoanPeriodUnit.valueOf(loanDO.getLoanPeriodType()));
        loan.setLoanMinInvest(loanDO.getLoanMinInvest());
        loan.setLoanMaxInvest(loanDO.getLoanMaxInvest());
        loan.setLoanCurrentInvest(loanDO.getLoanCurrentInvest());
        loan.setLoanCurrentValidInvest(loanDO.getLoanCurrentValidInvest());
        loan.setLoanPayType(LoanPayType.valueOf(loanDO.getLoanPayType()));
        loan.setLoanType(LoanType.valueOf(loanDO.getLoanType()));
        loan.setLoanInvestStarttime(loanDO.getLoanInvestStarttime());
        loan.setLoanInvestEndtime(loanDO.getLoanInvestEndtime());
        loan.setLoanInvestFulltime(loanDO.getLoanInvestFulltime());
        loan.setLoanFullTransferedtime(loanDO.getLoanFullTransferedtime());
        loan.setLoanClearType(LoanClearType.valueOf(loanDO.getLoanClearType()));
        loan.setLoanUse(loanDO.getLoanUse());
        loan.setLoanState(LoanState.valueOf(loanDO.getLoanState()));
        loan.setLoanCategory(LoanCategory.valueOf(loanDO.getLoanCategory()));
        loan.setLoanCategoryId(loanDO.getLoanCategoryId());
        loan.setLoanFileUrl(loanDO.getLoanFileUrl());
        loan.setLoanCreator(loanDO.getLoanCreator());
        loan.setLoanModifier(loanDO.getLoanModifier());
        loan.setLoanCreatetime(loanDO.getLoanCreatetime());
        loan.setLoanModifiytime(loanDO.getLoanModifiytime());
        loan.setLoanNote(loanDO.getLoanNote());
        loan.setInvestRedsendRatio(loanDO.getInvestRedsendRatio());
        loan.setInvestReduseRatio(loanDO.getInvestReduseRatio());
        //loan.setLoanSecondaryAllocation(LoanSecondaryAllocation.valueOf(loanDO.getLoanSecondaryAllocation()));
        loan.setLoanSecondaryAllocation(loanDO.getLoanSecondaryAllocation());
        loan.setLoanSecondaryUser(loanDO.getLoanSecondaryUser());
        loan.setLoanPayDate(loanDO.getLoanPayDate());
        loan.setLoanExpireDate(loanDO.getLoanExpireDate());
        loan.setBorrowerIntroduction(loanDO.getBorrowerIntroduction());
        loan.setRelevantIntroduction(loanDO.getRelevantIntroduction());
        loan.setRiskIntroduction(loanDO.getRiskIntroduction());
        loan.setLoanRedUseScopes(loanDO.getLoanRedUseScopes());
        loan.setLoanReleaseDate(loanDO.getLoanReleaseDate());
        loan.setLoanAutomaticBid(loanDO.getLoanAutomaticBid());
        loan.setLoanContractType(loanDO.getLoanContractType());
        loan.setLoanOriginalNo(loanDO.getLoanOriginalNo());
        loan.setAssId(loanDO.getAssId());
        loan.setPostLoanIntroduction(loanDO.getPostLoanIntroduction());
        loan.setIsShow(loanDO.getIsShow());
        loan.setInvestCode(loanDO.getInvestCode());
        loan.setContractIssend(loanDO.getContractIssend());
        loan.setHandFeeKind(loanDO.getHandFeeKind());
//        loan.setFsChargeType(loanDO.getFsChargeType());
        loan.setFsRate(loanDO.getFsRate());
//        loan.setFsAccurateAmount(loanDO.getFsAccurateAmount());
        return loan;
    }

    public static List<Loan> toLoanList(List<LoanDO> loanDOList) {
        if (loanDOList == null) {
            return null;
        }

        List<Loan> loanList = new ArrayList<Loan>(loanDOList.size());
        for (LoanDO loanDO : loanDOList) {
            loanList.add(toLoan(loanDO));
        }

        return loanList;
    }

}
