package com.autoserve.abc.web.module.screen.webnotify.hfnotify;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.util.EasyPayUtils;

/*
 * @author gongiuhui
 * 回付同步发回的地址
 * */
public class CashBgRetUrl {
    @Resource
    private HttpServletRequest  resq;
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HuifuPayService     payService;

    public void execute(Context context, ParameterParser params, Navigator nav) {
        System.out.println(resq.getParameter("CmdId").trim() + "sss");
        HuiFuData data = payService.cashResult(resq);
        if (!data.getRespCode().equals("000")) {
            System.out.println("提现失败,失败原因：" + data.getRespDesc());
            context.put("resultDesc", data.getRespDesc());
            nav.forwardTo("/error");
        } else {
            System.out.println("提现成功，成功提现了：" + data.getTransAmt() + "元");
            Map paramterMap = resq.getParameterMap();
            Map notifyMap = EasyPayUtils.transformRequestMap(paramterMap);
            String ResultCode = (String) notifyMap.get("RespCode");
            String OrderNo = (String) notifyMap.get("RespDesc");

            String trxId = data.getTrxId();
            String recvOrdId = data.getRecvOrdId();
            String ordId = data.getOrdId();

            context.put("trxId", trxId);
            context.put("recvOrdId", recvOrdId);
            context.put("ordId", ordId);

            nav.forwardTo("/receive");
        }

    }
}
