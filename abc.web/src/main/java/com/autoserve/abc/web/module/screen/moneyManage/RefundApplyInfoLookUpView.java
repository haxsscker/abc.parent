/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.moneyManage;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.refund.RefundRecordService;

/**
 * 退款审核信息查看
 * 
 * @author J.YL 2014年12月25日 上午10:34:01
 */
public class RefundApplyInfoLookUpView {
    @Resource
    private RefundRecordService refundRecordService;
    @Resource
    private AccountInfoService  accountInfoService;

    public void execute(Context context, ParameterParser params) {

        Integer refundId = params.getInt("refundId");
        context.put("refundId", refundId);
    }
}
