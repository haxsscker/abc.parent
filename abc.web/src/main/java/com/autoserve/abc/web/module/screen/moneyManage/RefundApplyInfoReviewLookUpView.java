/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.moneyManage;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;

/**
 * 退款审核页面，将退款信息展示到页面
 * 
 * @author J.YL 2015年1月17日 上午11:07:09
 */
public class RefundApplyInfoReviewLookUpView {

    public void execute(Context context, ParameterParser params) {
        Integer refundId = params.getInt("refundId");
        context.put("refundId", refundId);
    }
}
