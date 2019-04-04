package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.FundProfitDO;
import com.autoserve.abc.service.biz.entity.FundProfit;

public class FundProfitConverter {

    public static FundProfitDO toFundProfitDO(FundProfit fundProfit) {

        FundProfitDO fundProfitDO = new FundProfitDO();
        if (fundProfit == null) {
            return fundProfitDO;
        }
        fundProfitDO.setFpProfitId(fundProfit.getFpProfitId());
        fundProfitDO.setFpFundId(fundProfit.getFpFundId());
        fundProfitDO.setFpMinMoney(fundProfit.getFpMinMoney());
        fundProfitDO.setFpMaxMoney(fundProfit.getFpMaxMoney());
        fundProfitDO.setFpProfitYields(fundProfit.getFpProfitYields());
        return fundProfitDO;
    }

    public static FundProfit toFundProfit(FundProfitDO fundProfitDO) {
        FundProfit fundProfit = new FundProfit();
        fundProfit.setFpProfitId(fundProfitDO.getFpProfitId());
        fundProfit.setFpFundId(fundProfitDO.getFpFundId());
        fundProfit.setFpMinMoney(fundProfitDO.getFpMinMoney());
        fundProfit.setFpMaxMoney(fundProfitDO.getFpMaxMoney());
        fundProfit.setFpProfitYields(fundProfitDO.getFpProfitYields());
        return fundProfit;
    }

    public static List<FundProfit> toFundProfitList(List<FundProfitDO> fundProfitDOList) {
        List<FundProfit> resultList = new ArrayList<FundProfit>();
        for (FundProfitDO fundProfitDO : fundProfitDOList) {
            FundProfit fundProfit = toFundProfit(fundProfitDO);
            resultList.add(fundProfit);
        }
        return resultList;
    }
}
