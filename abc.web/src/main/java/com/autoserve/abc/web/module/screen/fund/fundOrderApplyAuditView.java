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

/**
 * 类fundOrderApplyAuditView.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月25日 下午2:51:31
 */
public class fundOrderApplyAuditView {
    @Resource
    private FundOrderService fundOrderService;

    public void execute(Context context, ParameterParser params) {
        Integer cpiId = params.getInt("cpiId");
        if (!"0".equals(cpiId) && !"".equals(cpiId) && null != cpiId) {
            FundOrderApplyUser fundOrderApplyUser = new FundOrderApplyUser();
            fundOrderApplyUser.setFoOrderId(cpiId);
            PlainResult<FundOrderApplyUser> result = fundOrderService.queryByOrderId(cpiId);
            if (result.isSuccess()) {
                FundOrderApplyUserJVO vo = FundOrderVOConvert.toFundOrderApplyUserJVO(result.getData());
                context.put("order", vo);

            }

        }
    }
}
