package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewSendStatus;
import com.autoserve.abc.service.biz.enums.ReviewSendState;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.review.GovCheckVO;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-11,14:59
 */
public class GovCheckVOConverter {
    public static GovCheckVO toVO(Review review, Loan loan, UserDO user, ReviewSendStatus sendStatus) {
        GovCheckVO vo = new GovCheckVO();

        if (review != null) {
            vo.setPro_check_id(review.getApplyId());
            vo.setPro_check_type(review.getType().type);
            vo.setPro_check_state(review.getState().des);
            vo.setCan_check(review.getState() == ReviewState.PENDING_REVIEW);
            if (review.getType() == ReviewType.INTENTION_REVIEW) {
                vo.setPro_intent_id(review.getApplyId());
            }
        }

        if (loan != null) {
        	vo.setPro_loan_id(loan.getLoanId());
            vo.setPro_loan_no(ObjectUtils.toString(loan.getLoanNo()));
            if(loan.getLoanCategory() != null)
            	vo.setPdo_product_name(loan.getLoanCategory().prompt);
            if(loan.getLoanMoney() != null)
            	vo.setPro_loan_money(loan.getLoanMoney().doubleValue());
            if(loan.getLoanRate() != null)
            	vo.setPro_loan_rate(loan.getLoanRate().doubleValue());
            if(loan.getLoanPeriodUnit()!=null)
            	vo.setPro_loan_period(loan.getLoanPeriod() + loan.getLoanPeriodUnit().getPrompt());
            if(loan.getLoanCreatetime()!=null)
            	vo.setPro_add_date(new DateTime(loan.getLoanCreatetime()).toString(DateUtil.DEFAULT_DAY_STYLE));
            if(loan.getLoanInvestFulltime()!=null)
            	vo.setPro_full_date(new DateTime(loan.getLoanInvestFulltime()).toString(DateUtil.DEFAULT_DAY_STYLE));
            if(loan.getLoanInvestEndtime()!=null)
            	vo.setPro_invest_endDate(new DateTime(loan.getLoanInvestEndtime()).toString(DateUtil.DEFAULT_DAY_STYLE));
            	vo.setPro_loan_use(StringUtils.isBlank(loan.getLoanUse()) ? "-" : loan.getLoanUse());
            if(loan.getLoanPayType()!=null)
            		vo.setPro_pay_type(loan.getLoanPayType().prompt);
        }

        if (user != null) {
            vo.setPro_user_name(user.getUserName());
        }
        
        if (sendStatus != null) {
            if (sendStatus.getSendPlatform()) {
                vo.setPro_send_status(ReviewSendState.SEND_TO_PLATFORM.prompt);
                vo.setPro_send_gov(ReviewSendState.SEND_TO_PLATFORM.type);
            } else if (sendStatus.getSendGuarGov()) {
                vo.setPro_send_status(ReviewSendState.SEND_TO_GUAR_GOV.prompt);
                vo.setPro_send_gov(ReviewSendState.SEND_TO_GUAR_GOV.type);
            } else if (sendStatus.getSendLoanGov()) {
                vo.setPro_send_status(ReviewSendState.SEND_TO_LOAN_GOV.prompt);
                vo.setPro_send_gov(ReviewSendState.SEND_TO_LOAN_GOV.type);
            } else {
                vo.setPro_send_status("-");
                vo.setPro_send_gov(-1);
            }
        }

        return vo;
    }
}
