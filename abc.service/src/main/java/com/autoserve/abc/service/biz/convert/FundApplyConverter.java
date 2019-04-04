package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.FundApplyDO;
import com.autoserve.abc.service.biz.entity.FundApply;
import com.autoserve.abc.service.biz.enums.FundPayType;
import com.autoserve.abc.service.biz.enums.FundState;

public class FundApplyConverter {

    public static FundApplyDO toFundApplyDO(FundApply fundApply) {

        FundApplyDO fundApplyDO = new FundApplyDO();
        if (fundApply == null) {
            return fundApplyDO;
        }
        fundApplyDO.setFaFundId(fundApply.getFaFundId());
        fundApplyDO.setFaFundNo(fundApply.getFaFundNo());
        fundApplyDO.setFaFundName(fundApply.getFaFundName());
        fundApplyDO.setFaFundComp(fundApply.getFaFundComp());
        fundApplyDO.setFaFundMoney(fundApply.getFaFundMoney());
        fundApplyDO.setFaFundPeriod(fundApply.getFaFundPeriod());
        fundApplyDO.setFaFundRate(fundApply.getFaFundRate());
        fundApplyDO.setFaMinMoney(fundApply.getFaMinMoney());
        fundApplyDO.setFaFundIndustry(fundApply.getFaFundIndustry());
        if (fundApply.getFaPayType() != null) {
            fundApplyDO.setFaPayType(fundApply.getFaPayType().getType());
        }
        fundApplyDO.setFaFundType(fundApply.getFaFundType());
        fundApplyDO.setFaMartgageRate(fundApply.getFaMartgageRate());
        fundApplyDO.setFaMartgageIntrol(fundApply.getFaMartgageIntrol());
        fundApplyDO.setFaFundIntrol(fundApply.getFaFundIntrol());
        fundApplyDO.setFaFundUse(fundApply.getFaFundUse());
        fundApplyDO.setFaFundRisk(fundApply.getFaFundRisk());
        fundApplyDO.setFaPayResource(fundApply.getFaPayResource());
        fundApplyDO.setFaProjectIntrol(fundApply.getFaProjectIntrol());
        fundApplyDO.setFaCompIntrol(fundApply.getFaCompIntrol());
        if (fundApply.getFaFundState() != null) {
            fundApplyDO.setFaFundState(fundApply.getFaFundState().getState());
        }
        fundApplyDO.setFaAddDate(fundApply.getFaAddDate());
        return fundApplyDO;
    }

    public static FundApply toFundApply(FundApplyDO fundApplyDO) {
        FundApply fundApply = new FundApply();
        if(fundApplyDO == null){
            return fundApply;
        }
        fundApply.setFaFundId(fundApplyDO.getFaFundId());
        fundApply.setFaFundNo(fundApplyDO.getFaFundNo());
        fundApply.setFaFundName(fundApplyDO.getFaFundName());
        fundApply.setFaFundComp(fundApplyDO.getFaFundComp());
        fundApply.setFaFundMoney(fundApplyDO.getFaFundMoney());
        fundApply.setFaFundPeriod(fundApplyDO.getFaFundPeriod());
        fundApply.setFaFundRate(fundApplyDO.getFaFundRate());
        fundApply.setFaMinMoney(fundApplyDO.getFaMinMoney());
        fundApply.setFaFundIndustry(fundApplyDO.getFaFundIndustry());
        fundApply.setFaPayType(FundPayType.valueOf(fundApplyDO.getFaPayType()));
        fundApply.setFaFundType(fundApplyDO.getFaFundType());
        fundApply.setFaMartgageRate(fundApplyDO.getFaMartgageRate());
        fundApply.setFaMartgageIntrol(fundApplyDO.getFaMartgageIntrol());
        fundApply.setFaFundIntrol(fundApplyDO.getFaFundIntrol());
        fundApply.setFaFundUse(fundApplyDO.getFaFundUse());
        fundApply.setFaFundRisk(fundApplyDO.getFaFundRisk());
        fundApply.setFaPayResource(fundApplyDO.getFaPayResource());
        fundApply.setFaProjectIntrol(fundApplyDO.getFaProjectIntrol());
        fundApply.setFaCompIntrol(fundApplyDO.getFaCompIntrol());
        fundApply.setFaFundState(FundState.valueOf(fundApplyDO.getFaFundState()));
        fundApply.setFaAddDate(fundApplyDO.getFaAddDate());
        return fundApply;
    }

}
