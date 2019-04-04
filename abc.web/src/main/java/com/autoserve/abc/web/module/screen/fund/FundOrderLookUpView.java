/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.fund;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.FundOrderApplyUser;
import com.autoserve.abc.service.biz.intf.fund.FundOrderService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.FundOrderVOConvert;
import com.autoserve.abc.web.vo.fund.FundOrderApplyUserJVO;

public class FundOrderLookUpView {
    @Resource
    private FundOrderService fundOrderService;

    public void execute(Context context, ParameterParser params) {
        //params.getInt("cuiId");
        PlainResult<FundOrderApplyUser> plainResult = fundOrderService.queryByOrderId(params.getInt("cuiId"));
        if (plainResult.isSuccess()) {
            FundOrderApplyUserJVO vo = FundOrderVOConvert.toFundOrderApplyUserJVO(plainResult.getData());
            context.put("order", vo);
        }
    }
}
