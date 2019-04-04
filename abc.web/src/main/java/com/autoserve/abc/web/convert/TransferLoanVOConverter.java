/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanClearType;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoaneeType;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.projectmanage.TransferLoanVO;

/**
 * @author segen189 2014年12月22日 下午8:34:03
 */
public class TransferLoanVOConverter extends ResultConverter<TransferLoanJDO, TransferLoanVO> {
    private static TransferLoanVOConverter singleton = new TransferLoanVOConverter();

    private TransferLoanVOConverter() {
    }

    public static TransferLoanVOConverter getInstance() {
        return singleton;
    }

    @Override
    public TransferLoanVO convert(TransferLoanJDO transferLoan) {
        if (transferLoan == null) {
            return null;
        }

        TransferLoanVO transferLoanVO = new TransferLoanVO();

        transferLoanVO.setId(transferLoan.getTlId());
        transferLoanVO.setOriginId(transferLoan.getTlOriginId());
        transferLoanVO.setInvestId(transferLoan.getTlInvestId());
        transferLoanVO.setUserId(transferLoan.getTlUserId());
        transferLoanVO.setTransferTotal(transferLoan.getTlTransferTotal());
        transferLoanVO.setTransferMoney(transferLoan.getTlTransferMoney());
        transferLoanVO.setTransferRate(transferLoan.getTlTransferRate());
        transferLoanVO.setTransferFee(transferLoan.getTlTransferFee());
        transferLoanVO.setTransferDiscountRate(transferLoan.getTlTransferDiscountRate());
        transferLoanVO.setTransferDiscountFee(transferLoan.getTlTransferDiscountFee());
        transferLoanVO.setTransferPeriod(transferLoan.getTlTransferPeriod());
        transferLoanVO.setCurrentInvest(transferLoan.getTlCurrentInvest());
        transferLoanVO.setCurrentValidInvest(transferLoan.getTlCurrentValidInvest());
        transferLoanVO.setNextPaymentId(transferLoan.getTlNextPaymentId());

        if (transferLoan.getTlState() != null) {
            TransferLoanState transferLoanState = TransferLoanState.valueOf(transferLoan.getTlState());
            transferLoanVO.setTransferLoanStateStr(transferLoanState == null ? null : transferLoanState.getPrompt());
        }

        transferLoanVO.setInvestStarttimeStr(DateUtil.formatDate(transferLoan.getTlInvestStarttime(),
                DateUtil.DATE_FORMAT));
        transferLoanVO
                .setInvestEndtimeStr(DateUtil.formatDate(transferLoan.getTlInvestEndtime(), DateUtil.DATE_FORMAT));
        transferLoanVO.setCreatetimeStr(DateUtil.formatDate(transferLoan.getTlCreatetime(), DateUtil.DATE_FORMAT));
        transferLoanVO.setFulltimeStr(DateUtil.formatDate(transferLoan.getTlFulltime(), DateUtil.DATE_FORMAT));
        transferLoanVO.setFullTransferedtimeStr(DateUtil.formatDate(transferLoan.getTlFullTransferedtime(),
                DateUtil.DATE_FORMAT));
        transferLoanVO.setLoanInvestStarttime(DateUtil.formatDate(transferLoan.getLoanInvestStarttime(),
                DateUtil.DATE_FORMAT));
        transferLoanVO.setLoanInvestEndtime(DateUtil.formatDate(transferLoan.getLoanInvestEndtime(),
                DateUtil.DATE_FORMAT));

        transferLoanVO.setLoanNo(transferLoan.getLoanNo());
        transferLoanVO.setLoanUserId(transferLoan.getLoanUserId());
        transferLoanVO.setLoanGov(transferLoan.getLoanGov());
        transferLoanVO.setLoanGov(transferLoan.getLoanGov());
        transferLoanVO.setLoanMoney(transferLoan.getLoanMoney());
        transferLoanVO.setLoanRate(transferLoan.getLoanRate());
        transferLoanVO.setLoanPeriod(transferLoan.getLoanPeriod());
        transferLoanVO.setLoanCategoryId(transferLoan.getLoanCategoryId());
        transferLoanVO.setLoanFileUrl(transferLoan.getLoanFileUrl());

        if (transferLoan.getLoanEmpType() != null) {
            LoaneeType loaneeType = LoaneeType.valueOf(transferLoan.getLoanEmpType());
            transferLoanVO.setLoanEmpType(loaneeType == null ? null : loaneeType.getPrompt());
        }

        if (transferLoan.getLoanPeriodType() != null) {
            LoanPeriodUnit loanPeriodUnit = LoanPeriodUnit.valueOf(transferLoan.getLoanPeriodType());
            transferLoanVO.setLoanPeriodUnit(loanPeriodUnit == null ? null : loanPeriodUnit.getPrompt());
        }

        if (transferLoan.getLoanPayType() != null) {
            LoanPayType loanPayType = LoanPayType.valueOf(transferLoan.getLoanPayType());
            transferLoanVO.setLoanPayType(loanPayType == null ? null : loanPayType.getPrompt());
        }

        if (transferLoan.getLoanClearType() != null) {
            LoanClearType loanClearType = LoanClearType.valueOf(transferLoan.getLoanClearType());
            transferLoanVO.setLoanClearType(loanClearType == null ? null : loanClearType.getPrompt());
        }

        if (transferLoan.getLoanCategory() != null) {
            LoanCategory loanCategory = LoanCategory.valueOf(transferLoan.getLoanCategory());
            transferLoanVO.setLoanCategory(loanCategory == null ? null : loanCategory.getPrompt());
        }

        return transferLoanVO;
    }
}
