package com.autoserve.abc.web.convert;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.review.LoanCheckVO;

/**
 * @author yuqing.zheng Created on 2014-12-09,16:42
 */
public class LoanCheckVOConverter {
    public static LoanCheckVO toVO(Loan loan, UserDO user, GovernmentDO govDO, Review review) {
        LoanCheckVO vo = new LoanCheckVO();
        if (loan == null) {
            return vo;
        }

        vo.setPro_loan_id(loan.getLoanId());
        vo.setPro_loan_no(ObjectUtils.toString(loan.getLoanNo()));
        vo.setPdo_product_name(loan.getLoanCategory().prompt);
        vo.setPro_loan_money(loan.getLoanMoney().doubleValue());
        vo.setPro_loan_rate(loan.getLoanRate().doubleValue());
        vo.setPro_invest_all(loan.getLoanCurrentInvest().doubleValue());
        vo.setPro_curr_invest(loan.getLoanCurrentValidInvest().doubleValue());
        if (loan.getLoanPeriod() != null)
            vo.setPro_loan_period(loan.getLoanPeriod() + loan.getLoanPeriodUnit().getPrompt());
        vo.setPro_add_date(new DateTime(loan.getLoanCreatetime()).toString(DateUtil.DEFAULT_DAY_STYLE));
        vo.setPro_full_date(new DateTime(loan.getLoanInvestFulltime()).toString(DateUtil.DEFAULT_DAY_STYLE));
        vo.setPro_invest_endDate(new DateTime(loan.getLoanInvestEndtime()).toString(DateUtil.DEFAULT_DAY_STYLE));
        vo.setPro_loan_use(StringUtils.isBlank(loan.getLoanUse()) ? "-" : loan.getLoanUse());
        if (loan.getLoanPayType() != null)
            vo.setPro_pay_type(loan.getLoanPayType().prompt);

        if (loan.getLoanState() == LoanState.WAIT_RELEASE) {
            vo.setPro_check_state(loan.getLoanState().prompt);
        } else {
            vo.setPro_check_state(review.getState().des);
        }

        vo.setCan_check(review.getState() == ReviewState.PENDING_REVIEW);
        vo.setCan_revoke(review.getState() == ReviewState.PASS_REVIEW && loan.getLoanState() == LoanState.WAIT_RELEASE);

        if (user != null) {
            vo.setPro_user_name(user.getUserName());
            vo.setPro_user_phone(user.getUserPhone());
        }

        if (govDO != null) {
            vo.setGov_name(govDO.getGovName());
        } else {
            vo.setGov_name("-");
        }

        return vo;
    }
}
