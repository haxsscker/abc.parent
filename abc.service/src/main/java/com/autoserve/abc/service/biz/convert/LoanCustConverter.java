package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.LoanCustDO;
import com.autoserve.abc.service.biz.entity.LoanCust;
import com.autoserve.abc.service.biz.enums.CompanyType;
import com.autoserve.abc.service.biz.enums.IndustryType;
import com.autoserve.abc.service.biz.enums.ScaleType;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-15,10:46
 */
public class LoanCustConverter {
    public static LoanCust toLoanCust(LoanCustDO loanCustDO) {
        LoanCust loanCust = new LoanCust();
        if (loanCustDO == null) {
            return loanCust;
        }

        loanCust.setId(loanCustDO.getLcuId());
        loanCust.setLoanId(loanCustDO.getLcuLoanId());
        loanCust.setCustName(loanCustDO.getLcuCustName());
        loanCust.setCustNo(loanCustDO.getLcuCustNo());
        loanCust.setTaxNo(loanCustDO.getLcuTaxNo());
        loanCust.setBizNo(loanCustDO.getLcuBizNo());
        loanCust.setTotalCapital(loanCustDO.getLcuTotalCapital());
        loanCust.setLegalPerson(loanCustDO.getLcuLegalPerson());
        loanCust.setCardType(loanCustDO.getLcuCardType());
        loanCust.setCardNo(loanCustDO.getLcuCardNo());
        loanCust.setContactPerson(loanCustDO.getLcuContactPerson());
        loanCust.setContactPhone(loanCustDO.getLcuContactPhone());
        loanCust.setMonthSalary(loanCustDO.getLcuMonthSalary());
        loanCust.setRegistMoney(loanCustDO.getLcuRegistMoney());
        loanCust.setRegistTime(loanCustDO.getLcuRegistTime());
        loanCust.setRegistNo(loanCustDO.getLcuRegistNo());
        loanCust.setRegistAddress(loanCustDO.getLcuRegistAddress());
        loanCust.setAreaId(loanCustDO.getLcuAreaId());
        loanCust.setAreaAddress(loanCustDO.getLcuAreaAddress());

        Integer custType = loanCustDO.getLcuCustType();
        loanCust.setCustType(CompanyType.valueOf(custType));

        Integer industry = loanCustDO.getLcuCustIndustry();
        loanCust.setCustIndustry(IndustryType.valueOf(industry));

        Integer scale = loanCustDO.getLcuCustScale();
        loanCust.setCustScale(ScaleType.valueOf(scale));

        return loanCust;
    }
}
