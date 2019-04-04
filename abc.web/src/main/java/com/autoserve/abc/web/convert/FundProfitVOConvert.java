package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.service.biz.entity.FundProfit;
import com.autoserve.abc.web.vo.fund.FundProfitVO;

public class FundProfitVOConvert {

	/*
	 * 有限合伙人收益VO转entity
	 */
	public static FundProfit toFundProfit(FundProfitVO fundProfitVO) {
    	FundProfit fundProfit = new FundProfit();
    	fundProfit.setFpProfitId(fundProfitVO.getFpProfitId());
    	fundProfit.setFpFundId(fundProfitVO.getFpFundId());
    	fundProfit.setFpMinMoney(fundProfitVO.getFpMinMoney());
    	fundProfit.setFpMaxMoney(fundProfitVO.getFpMaxMoney());
    	fundProfit.setFpProfitYields(fundProfitVO.getFpProfitYields());
    	return fundProfit;
    }

    public static List<FundProfit> convertEntityList(List<FundProfitVO> list) {
        List<FundProfit> result = new ArrayList<FundProfit>();
        for (FundProfitVO favo : list) {
            result.add(toFundProfit(favo));
        }
        return result;
    }
}
