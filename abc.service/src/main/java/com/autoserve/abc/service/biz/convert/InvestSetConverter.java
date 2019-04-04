package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.InvestSetDO;
import com.autoserve.abc.service.biz.entity.InvestSet;
import com.autoserve.abc.service.biz.enums.InvestSetOpenState;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPayType;

/**
 * 类InvestSetConverter.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年3月10日 下午5:16:31
 */
public class InvestSetConverter {

    public static InvestSet toInvestSet(InvestSetDO investSetDO) {
        if (investSetDO == null) {
            return null;
        }
        InvestSet investSet = new InvestSet();
        investSet.setId(investSetDO.getIsId());
        investSet.setInvestMoney(investSetDO.getIsInvestMoney());
        if (investSetDO.getIsIsOpen() != null)
            investSet.setIsOpen(InvestSetOpenState.valueOf(investSetDO.getIsIsOpen()));
        investSet.setMaxLoanPeriod(investSetDO.getIsMaxLoanPeriod());
        investSet.setMinLoanPeriod(investSetDO.getIsMinLoanPeriod());
        if (investSetDO.getIsLoanType() != null)
            investSet.setLoanType(LoanPayType.valueOf(investSetDO.getIsLoanType()));
        investSet.setMaxMoney(investSetDO.getIsMaxMoney());
        investSet.setMaxRate(investSetDO.getIsMaxRate());
        investSet.setMinMoney(investSetDO.getIsMinMoney());
        investSet.setMinRate(investSetDO.getIsMinRate());
        investSet.setSettMoney(investSetDO.getIsSettMoney());
        investSet.setUserId(investSetDO.getIsUserId());
        if (investSetDO.getIsLoanCategory() != null)
            investSet.setLoanCategory(LoanCategory.valueOf(investSetDO.getIsLoanCategory()));
        investSet.setCreateTime(investSetDO.getCreateTime());
        investSet.setModifyTime(investSetDO.getModifyTime());
        return investSet;

    }

    public static InvestSetDO toInvestSetDO(InvestSet investSet) {
        if (investSet == null) {
            return null;
        }
        InvestSetDO investSetDO = new InvestSetDO();
        investSetDO.setIsId(investSet.getId());
        investSetDO.setIsInvestMoney(investSet.getInvestMoney());
        if (investSet.getIsOpen() != null)
            investSetDO.setIsIsOpen(investSet.getIsOpen().state);
        investSetDO.setIsMaxLoanPeriod(investSet.getMaxLoanPeriod());
        investSetDO.setIsMinLoanPeriod(investSet.getMinLoanPeriod());
        if (investSet.getLoanType() != null)
            investSetDO.setIsLoanType(investSet.getLoanType().type);
        investSetDO.setIsMaxMoney(investSet.getMaxMoney());
        investSetDO.setIsMaxRate(investSet.getMaxRate());
        investSetDO.setIsMinMoney(investSet.getMinMoney());
        investSetDO.setIsMinRate(investSet.getMinRate());
        investSetDO.setIsSettMoney(investSet.getSettMoney());
        investSetDO.setIsUserId(investSet.getUserId());
        if (investSet.getLoanCategory() != null)
            investSetDO.setIsLoanCategory(investSet.getLoanCategory().category);
        investSetDO.setCreateTime(investSet.getCreateTime());
        investSetDO.setModifyTime(investSet.getModifyTime());
        return investSetDO;

    }
}
