/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.reportAnalysis;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;

/**
 * 资金划转详情
 * 
 * @author J.YL 2015年1月5日 下午2:19:19
 */
public class TransferFundsStatisticsLookUpView {

    public void execute(Context context, ParameterParser params) {

        String tdate = params.getString("tdate");
        context.put("tdate", tdate);
    }
}
