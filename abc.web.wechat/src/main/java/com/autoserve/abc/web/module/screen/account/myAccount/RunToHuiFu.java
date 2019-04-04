package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.intf.cash.PayMentService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class RunToHuiFu {
    @Resource
    private PayMentService payMent;

    public void execute(Context context, ParameterParser params) {

        PlainResult<Map<String, String>> invokeResult = new PlainResult<Map<String, String>>();
        List<DealRecordDO> dealRecords = new ArrayList<DealRecordDO>();
        invokeResult = payMent.tranfulAll(params.getString("seq"), dealRecords);
        context.put("jo", invokeResult.getData());
    }

}
