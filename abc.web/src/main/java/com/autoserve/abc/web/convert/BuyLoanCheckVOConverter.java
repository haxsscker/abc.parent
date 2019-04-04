package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.review.BuyLoanCheckVO;

/**
 * @author yuqing.zheng Created on 2014-12-26,14:16
 */
public class BuyLoanCheckVOConverter {
    /**
     * @param buyLoan 收购标，必选
     * @param loan 原始标，可选
     * @param user 原始表借款人，可选
     * @param buyUser 收购人，可选
     */
    public static BuyLoanCheckVO toBuyLoanCheckVO(BuyLoan buyLoan, Loan loan, UserDO user, UserDO buyUser) {
        BuyLoanCheckVO buyLoanCheckVO = new BuyLoanCheckVO();

        buyLoanCheckVO.setPro_buy_loan_id(buyLoan.getId());
        buyLoanCheckVO.setPro_buy_loan_fee(buyLoan.getFee());
        buyLoanCheckVO.setPro_buy_loan_date(DateUtil.formatDate(buyLoan.getCreatetime()));
        buyLoanCheckVO.setPro_full_date(DateUtil.formatDate(buyLoan.getFullTime()));
        buyLoanCheckVO.setPro_buy_money(buyLoan.getBuyTotal());
        buyLoanCheckVO.setPro_buy_loan_state(buyLoan.getBuyLoanState().getPrompt());
        buyLoanCheckVO.setPro_buy_total(buyLoan.getBuyTotal());
        buyLoanCheckVO.setCan_full_check(buyLoan.getBuyLoanState() == BuyLoanState.FULL_WAIT_REVIEW);
        buyLoanCheckVO.setCan_check(buyLoan.getBuyLoanState() == BuyLoanState.WAIT_REVIEW);

        if (loan != null) {
            buyLoanCheckVO.setPdo_product_name(loan.getLoanCategory().prompt);
            buyLoanCheckVO.setPro_loan_money(loan.getLoanMoney().doubleValue());
            buyLoanCheckVO.setPro_loan_rate(loan.getLoanRate().doubleValue());
            buyLoanCheckVO.setPro_loan_period(loan.getLoanPeriod() + loan.getLoanPayType().prompt);
            buyLoanCheckVO.setPro_loan_no(loan.getLoanNo());
            buyLoanCheckVO.setPro_loan_id(loan.getLoanId());
        }

        if (user != null) {
            buyLoanCheckVO.setLoan_user_name(user.getUserName());
        }

        if (buyUser != null) {
            buyLoanCheckVO.setCst_real_name(buyUser.getUserName());
        }

        return buyLoanCheckVO;
    }
}
