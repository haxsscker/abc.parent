package com.autoserve.abc.web.convert;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewSendStatus;
import com.autoserve.abc.service.biz.enums.ReviewSendState;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.review.IntentCheckVO;

/**
 * @author yuqing.zheng Created on 2014-12-23,17:34
 */
public class IntentCheckVOConverter {
    public static IntentCheckVO toIntentCheckVO(LoanIntentApply intentApply, Review review, ReviewSendStatus sendStatus) {
        IntentCheckVO intentCheckVO = new IntentCheckVO();
        if (intentApply == null) {
            return intentCheckVO;
        }

        intentCheckVO.setPro_intent_no(intentApply.getIntentNo());
        intentCheckVO.setPro_add_date(new DateTime(intentApply.getIntentTime()).toString(DateUtil.DEFAULT_DAY_STYLE));
        intentCheckVO.setPro_loan_use(StringUtils.isBlank(intentApply.getIntentUse()) ? "-" : intentApply
                .getIntentUse());
        intentCheckVO.setPro_pay_type(intentApply.getIntentPayType() == null ? null
                : intentApply.getIntentPayType().prompt);
        intentCheckVO.setPdo_product_name(intentApply.getIntentCategory() == null ? null : intentApply
                .getIntentCategory().prompt);
        intentCheckVO.setPro_user_name(intentApply.getUserName());
        intentCheckVO.setPro_loan_money(intentApply.getIntentMoney().doubleValue());
        intentCheckVO.setPro_loan_rate(intentApply.getIntentRate() == null ? 0 : intentApply.getIntentRate()
                .doubleValue());
        intentCheckVO.setPro_loan_period(intentApply.getIntentPeriod() + intentApply.getIntentPeriodUnit().getPrompt());
        intentCheckVO.setPro_intent_id(intentApply.getId());
        intentCheckVO.setPro_loan_id(intentApply.getLoanId());
        intentCheckVO.setPro_has_loan(false);
        intentCheckVO.setPro_user_phone(intentApply.getPhone());

        fillReviewAndSendInfo(intentCheckVO, review, sendStatus);

        return intentCheckVO;
    }

    public static IntentCheckVO toIntentCheckVO(LoanIntentApply intentApply, Loan loan, Review review,
                                                ReviewSendStatus sendStatus,Map<Integer, String> govNameMap) {
        IntentCheckVO intentCheckVO = new IntentCheckVO();
        if (loan == null) {
            return intentCheckVO;
        }
        intentCheckVO.setPro_loan_no(loan.getLoanNo());
        intentCheckVO.setPro_intent_no(intentApply.getIntentNo());
        if (loan.getLoanCreatetime() != null)
            intentCheckVO.setPro_add_date(new DateTime(loan.getLoanCreatetime()).toString(DateUtil.DEFAULT_DAY_STYLE));
        intentCheckVO.setPro_loan_use(StringUtils.isBlank(loan.getLoanUse()) ? "-" : loan.getLoanUse());
        if (loan.getLoanPayType() != null)
            intentCheckVO.setPro_pay_type(loan.getLoanPayType().prompt);
        if (loan.getLoanCategory() != null)
            intentCheckVO.setPdo_product_name(loan.getLoanCategory().prompt);
        if (loan.getLoanMoney() != null)
            intentCheckVO.setPro_loan_money(loan.getLoanMoney().doubleValue());
        if (loan.getLoanRate() != null)
            intentCheckVO.setPro_loan_rate(loan.getLoanRate().doubleValue());
        if (loan.getLoanPeriodUnit() != null)
            intentCheckVO.setPro_loan_period(loan.getLoanPeriod() + loan.getLoanPeriodUnit().getPrompt());

        intentCheckVO.setPro_user_name(intentApply.getUserName());
        intentCheckVO.setPro_intent_id(intentApply.getId());
        intentCheckVO.setPro_loan_id(intentApply.getLoanId());
        intentCheckVO.setPro_has_loan(true);
        intentCheckVO.setPro_user_phone(intentApply.getPhone());
        intentCheckVO.setPro_gov_id(loan.getLoanGuarGov());
        intentCheckVO.setPro_guar_gov(govNameMap.get(loan.getLoanGuarGov()));

        fillReviewAndSendInfo(intentCheckVO, review, sendStatus);

        return intentCheckVO;
    }

    public static IntentCheckVO toIntentCheckVO(LoanIntentApply intentApply, Review review, Loan loan,
                                                GovernmentDO guarGov) {
        IntentCheckVO intentCheckVO = new IntentCheckVO();
        if (intentApply == null) {
            return intentCheckVO;
        }

        intentCheckVO = toIntentCheckVO(intentApply, review, null);

        if (loan != null) {
            intentCheckVO.setPro_invest_endDate(new DateTime(loan.getLoanInvestEndtime())
                    .toString(DateUtil.DEFAULT_DAY_STYLE));
            intentCheckVO.setPro_has_loan(true);
        } else {
            intentCheckVO.setPro_has_loan(false);
        }

        if (guarGov != null) {
            intentCheckVO.setPro_guar_gov(guarGov.getGovName());
        }

        return intentCheckVO;
    }

    private static void fillReviewAndSendInfo(IntentCheckVO intentCheckVO, Review review, ReviewSendStatus sendStatus) {
        if (review.getCurrRole() != null) {
            intentCheckVO.setPro_check_role(review.getCurrRole().roleName);
            intentCheckVO.setPro_check_role_idx(review.getCurrRole().index);
        }

        intentCheckVO.setPro_loan_intentstate(review.getState().des);
        intentCheckVO.setPro_review_id(review.getReviewId());
        intentCheckVO.setCan_intent_check(review.getState() == ReviewState.PENDING_REVIEW);

        if (sendStatus != null && sendStatus.getSendPlatform()) {
            intentCheckVO.setPro_send_status(ReviewSendState.SEND_TO_PLATFORM.prompt);
            intentCheckVO.setPro_send_gov(ReviewSendState.SEND_TO_PLATFORM.type);
        } else if (sendStatus != null && sendStatus.getSendGuarGov()) {
            intentCheckVO.setPro_send_status(ReviewSendState.SEND_TO_GUAR_GOV.prompt);
            intentCheckVO.setPro_send_gov(ReviewSendState.SEND_TO_GUAR_GOV.type);
        } else if (sendStatus != null && sendStatus.getSendLoanGov()) {
            intentCheckVO.setPro_send_status(ReviewSendState.SEND_TO_LOAN_GOV.prompt);
            intentCheckVO.setPro_send_gov(ReviewSendState.SEND_TO_LOAN_GOV.type);
        } else {
            intentCheckVO.setPro_send_status("-");
            intentCheckVO.setPro_send_gov(-1);
        }
    }
}
