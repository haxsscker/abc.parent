package com.autoserve.abc.web.module.screen.fund;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.FundApply;
import com.autoserve.abc.service.biz.entity.FundProfit;
import com.autoserve.abc.service.biz.intf.fund.FundApplyService;
import com.autoserve.abc.service.biz.intf.fund.FundProfitService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.FundApplyVOConvert;

public class FundApplyEditView {
	@Resource
	FundApplyService fundApplyService;
	
	@Resource
	FundProfitService fundProfitService;
	
	public void execute(Context context, ParameterParser params) {
		int faFundId = Integer.valueOf(params.get("faFundId").toString());
		// 1.根据有限合伙人id取合伙人信息
        PlainResult<FundApply> plainResult = fundApplyService.queryById(faFundId);
        if (plainResult.isSuccess()) {
        	FundApply fundApply = plainResult.getData();
            context.put("fundApply", FundApplyVOConvert.toFundApplyVO(fundApply));
        }
        // 2.根据有限合伙人id取收益表信息
        FundProfit fundProfit = new FundProfit();
        fundProfit.setFpFundId(faFundId);
        ListResult<FundProfit> fundProfitList = fundProfitService.queryList(fundProfit);
        
        if(fundProfitList.isSuccess()){
        	context.put("fundProfitList", fundProfitList.getData());
        }
    }
	
	
}
