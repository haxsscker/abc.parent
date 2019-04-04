package com.autoserve.abc.web.convert;

import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.charge.ChargeRecordVO;

/**
 * @author liuwei 2014年12月18日 上午9:45:16
 */
public class ChargeRecordVOConverter {

    public static ChargeRecordVO toChargeRecordVO(Loan loan, ChargeRecordVO vo) {
        vo.setProject_number(loan.getLoanNo());
        if (loan.getLoanCategory() != null) {
            vo.setProject_type(loan.getLoanCategory().prompt);
        }
        vo.setBorrowing_amount(loan.getLoanMoney());
        vo.setAnnual_rate(loan.getLoanRate());
        LoanPeriodUnit type = loan.getLoanPeriodUnit();
        switch (type) {
            case DAY:
                vo.setLoan_period(loan.getLoanPeriod().toString() + " 天");
                break;
            case MONTH:
                vo.setLoan_period(loan.getLoanPeriod().toString() + " 月");
                break;
            case YEAR:
                vo.setLoan_period(loan.getLoanPeriod().toString() + " 年");
                break;
            default:
                vo.setLoan_period("-");
                break;

        }

        if (loan.getLoanInvestStarttime() != null)
            vo.setStar_date(DateUtil.formatDay(loan.getLoanInvestStarttime()));
        if (loan.getLoanInvestEndtime() != null)
            vo.setEnd_date(DateUtil.formatDay(loan.getLoanInvestEndtime()));
        return vo;
    }
}
