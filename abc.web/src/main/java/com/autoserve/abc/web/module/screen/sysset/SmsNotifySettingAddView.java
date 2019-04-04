package com.autoserve.abc.web.module.screen.sysset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.SmsNotifyCfg;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.ListResult;

/**
 * 显示短信帐号设置账户密码
 * 
 * @author liuwei
 */
public class SmsNotifySettingAddView {

    @Resource
    private SysConfigService SysConfigService;

    public void execute(Context context, ParameterParser params) {

        List<String> list = new ArrayList<String>();
        list.add("SMS_NOTIFY_INVEST_CFG");
        list.add("SMS_NOTIFY_SPECIAL_TRANSFER_CFG");
        list.add("SMS_NOTIFY_COMMON_TRANSFER_CFG");
        list.add("SMS_NOTIFY_REPAYMENT_CFG");
        list.add("SMS_NOTIFY_ABORT_BID_TRANSFER_USER");
        list.add("SMS_NOTIFY_ABORT_BID_INVEST_USER");
        list.add("SMS_NOTIFY_NORMAL_PAYMENT");
        list.add("SMS_NOTIFY_OVERDUE_PAYMENT");
        list.add("SMS_NOTIFY_BIRTHDAY");
        
        Map<String, Object> notifyCfg = new HashMap<String, Object>();
        ListResult<SysConfig> result = this.SysConfigService.queryListByParam(list);
        
        for(SysConfig sysConfig:result.getData()) {
        	if(sysConfig.getConf() == SysConfigEntry.SMS_NOTIFY_INVEST_CFG) {
        		notifyCfg.put("invest", JSON.parseObject(sysConfig.getConfValue(), SmsNotifyCfg.class));
        		
        	} else if(sysConfig.getConf() == SysConfigEntry.SMS_NOTIFY_SPECIAL_TRANSFER_CFG) {
        		notifyCfg.put("special", JSON.parseObject(sysConfig.getConfValue(), SmsNotifyCfg.class));
        		
        	} else if(sysConfig.getConf() == SysConfigEntry.SMS_NOTIFY_COMMON_TRANSFER_CFG) {
        		notifyCfg.put("common", JSON.parseObject(sysConfig.getConfValue(), SmsNotifyCfg.class));
        	} else if(sysConfig.getConf() == SysConfigEntry.SMS_NOTIFY_REPAYMENT_CFG) {
        		notifyCfg.put("repayment", JSON.parseObject(sysConfig.getConfValue(), SmsNotifyCfg.class));
        	} else if(sysConfig.getConf() == SysConfigEntry.SMS_NOTIFY_ABORT_BID_TRANSFER_USER) {
        		notifyCfg.put("abortBidTransfer", JSON.parseObject(sysConfig.getConfValue(), SmsNotifyCfg.class));
        	} else if(sysConfig.getConf() == SysConfigEntry.SMS_NOTIFY_ABORT_BID_INVEST_USER) {
        		notifyCfg.put("abortBidInvest", JSON.parseObject(sysConfig.getConfValue(), SmsNotifyCfg.class));
        	} else if(sysConfig.getConf() == SysConfigEntry.SMS_NOTIFY_NORMAL_PAYMENT) {
        		notifyCfg.put("normalPayment", JSON.parseObject(sysConfig.getConfValue(), SmsNotifyCfg.class));
        	} else if(sysConfig.getConf() == SysConfigEntry.SMS_NOTIFY_OVERDUE_PAYMENT) {
        		notifyCfg.put("overduePayment", JSON.parseObject(sysConfig.getConfValue(), SmsNotifyCfg.class));
        	} else if(sysConfig.getConf() == SysConfigEntry.SMS_NOTIFY_BIRTHDAY) {
        		notifyCfg.put("smsBirthday", JSON.parseObject(sysConfig.getConfValue(), SmsNotifyCfg.class));
        	}
        	
        }
        
        context.put("notifyCfg", notifyCfg);
    }

}
