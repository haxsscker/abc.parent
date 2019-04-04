/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.payment.json;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类Recharge.java的实现描述：充值
 * 
 * @see 粗糙的充值页面 见谅(^_^)
 * @author J.YL 2014年12月8日 下午5:24:56
 */
public class Recharge {

    @Resource
    private RechargeService recharge;

    public void execute(Context context, Navigator nav, ParameterParser params) {

        double easymoney = params.getDouble("easymoney");
        Map paramsMap = new HashMap();
        //TODO 更换成实际充值账户，现在写死为2
        PlainResult<DealReturn> result = recharge.recharge(2, UserType.PARTNER, BigDecimal.valueOf(easymoney),paramsMap);
        String url = result.getData().getCashRecords().get(0).getCrRequestUrl();
        String param = result.getData().getCashRecords().get(0).getCrRequestParameter();
        String redirectUrl = url + "?" + param;
        //System.out.println(redirectUrl);
        nav.redirectToLocation(redirectUrl);
    }
}
