package com.autoserve.abc.web.convert;

import org.apache.commons.lang.ObjectUtils;
import org.joda.time.DateTime;

import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.review.TransferCheckVO;

/**
 * @author yuqing.zheng Created on 2014-12-09,18:18
 */
public class TransferCheckVOConverter {
    public static TransferCheckVO toVO(Review review, TransferLoan transfer, Loan loan, UserDO user, UserDO cst) {
        TransferCheckVO vo = new TransferCheckVO();
        if (transfer == null) {
            return vo;
        }

        if (review != null) {
        	if (transfer.getTransferLoanState() == TransferLoanState.BID_CANCELED || transfer.getTransferLoanState() == TransferLoanState.DELETED)
        	{
        		vo.setPro_check_state(ReviewState.SYSTEM_DEALED.des);
        	}
        	else
        	{
        		vo.setPro_check_state(review.getState().des);
        	}
            vo.setCan_check(review.getState() == ReviewState.PENDING_REVIEW
                    && transfer.getTransferLoanState() == TransferLoanState.WAIT_REVIEW);
            vo.setCan_full_check(review.getState() == ReviewState.PENDING_REVIEW
                    && transfer.getTransferLoanState() == TransferLoanState.FULL_WAIT_REVIEW);
        }

        if (transfer != null) {
            vo.setPro_transfer_id(transfer.getId());
            vo.setPro_transfer_money(transfer.getTransferMoney().doubleValue());
            vo.setPayCount(transfer.getTransferPeriod());
            vo.setPro_transfer_date(new DateTime(transfer.getCreatetime()).toString(DateUtil.DEFAULT_DAY_STYLE));
            vo.setPro_full_date(new DateTime(transfer.getFulltime()).toString(DateUtil.DEFAULT_DAY_STYLE));
            vo.setPro_transfer_state(transfer.getTransferLoanState().prompt);
            vo.setPro_transferloan_no(ObjectUtils.toString(transfer.getTransferLoanNo()));
            if (transfer.getTransferFee() != null) {
                vo.setPro_transfer_fee(transfer.getTransferFee().doubleValue());
            } else {
                vo.setPro_transfer_fee(0.0);
            }

            vo.setPro_trans_invest(transfer.getCurrentInvest().doubleValue());
            vo.setPro_valid_trans_invest(transfer.getCurrentValidInvest().doubleValue());
        }

        if (loan != null) {
            vo.setPro_loan_id(loan.getLoanId());
            vo.setPdo_product_name(ObjectUtils.toString(loan.getLoanCategory().prompt));
            vo.setPro_loan_money(loan.getLoanMoney().doubleValue());
            vo.setPro_loan_rate(loan.getLoanRate().doubleValue());
            vo.setPro_loan_period(ObjectUtils.toString(loan.getLoanPeriod() + loan.getLoanPeriodUnit().getPrompt()));
            vo.setPro_loan_no(ObjectUtils.toString(loan.getLoanNo()));
        }

        if (user != null) {
            vo.setLoan_user_name(user.getUserName());
            vo.setLoan_user_phone(user.getUserPhone());
        }

        if (cst != null) {
            vo.setCst_real_name(cst.getUserName());
        }

        return vo;
    }
}
