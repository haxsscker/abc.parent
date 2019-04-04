/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.webnotify;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.EasyPayUtils;

/**
 * 第三方支付接口回调接口
 * 
 * @author J.YL 2014年12月8日 下午8:52:56
 */
public class PayInterfaceNotify {
    private final static Logger logger = LoggerFactory.getLogger(PayInterfaceNotify.class);
    @Resource
    private DealRecordService   dealRecordService;
    @Resource
    private CashRecordService   cashRecordService;
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;

    public void execute(Context context, ParameterParser params) {
        Map paramterMap = resq.getParameterMap();

        Map notifyMap = EasyPayUtils.transformRequestMap(paramterMap);
        //        Map notifyMap = JSON
        //                .parseObject(
        //                        "{\"body\":\"RechargeBusiness\",\"buyer_email\":\"\",\"buyer_id\":\"\",\"discount\":\"0.00\",\"gmt_create\":\"2014-12-15 13:41:37\",\"gmt_logistics_modify\":\"2014-12-15 20:06:51\",\"gmt_payment\":\"2014-12-15 13:42:48\",\"is_success\":\"T\",\"is_total_fee_adjust\":\"0\",\"notify_id\":\"b3c52037389b4e698ff3fa64118634aa\",\"notify_time\":\"2014-12-15 13:42:48\",\"notify_type\":\"WAIT_TRIGGER\",\"out_trade_no\":\"e3cd9dad-c09c-49a2-8c3e-fbbc72dcd4bd\",\"payment_type\":\"1\",\"price\":\"0.01\",\"quantity\":\"1\",\"seller_actions\":\"SEND_GOODS\",\"seller_email\":\"59098759@qq.com\",\"seller_id\":\"100000000001264\",\"sign\":\"34eef23d562ce8396a15d8d5b81a21df\",\"sign_type\":\"MD5\",\"subject\":\"RechargeBusinessSubject\",\"total_fee\":\"0.01\",\"trade_no\":\"101412152094908\",\"trade_status\":\"TRADE_FINISHED\"}",
        //                        Map.class);
        logger.info("[PayInterfaceNotify][execute] parameters:{}", JSON.toJSON(notifyMap));
        CashRecordDO cashRecord = new CashRecordDO();
        cashRecord.setCrSeqNo(notifyMap.get("out_trade_no").toString());
        //保存回调
        cashRecord.setCrResponse(JSON.toJSONString(notifyMap));
        cashRecord.setCrResponseState(200);
        cashRecordService.modifyCashRecordState(cashRecord);
        BaseResult result = dealRecordService.payMentNotify(notifyMap);

        try {
            if (result.isSuccess()) {
                resp.getWriter().print("success");
            } else {
                resp.getWriter().print("fail");
            }
        } catch (IOException e) {
            logger.error("[PayInterfaceNotify] error: ", e);
        }
    }
}
