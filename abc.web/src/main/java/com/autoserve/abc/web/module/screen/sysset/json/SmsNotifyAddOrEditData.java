package com.autoserve.abc.web.module.screen.sysset.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.SmsNotifyCfg;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 参数设置 短信设置 类MessageAddOrEditData.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月10日 下午5:11:11
 */
public class SmsNotifyAddOrEditData {
    @Resource
    private SysConfigService sysConfigService;

    public JsonBaseVO execute(ParameterParser params) {
        BaseResult result = new BaseResult();
//        List<SysConfig> list = new ArrayList<SysConfig>();
//        String sys_user_name = params.getString("sys_user_name");
//        String sys_user_pwd = params.getString("sys_user_pwd");
//        
//        SysConfig sysConfigName = new SysConfig();
//        sysConfigName.setConf(SysConfigEntry.SMS_USER);
//        sysConfigName.setConfValue(sys_user_name);
//        list.add(sysConfigName);
//        SysConfig sysConfigPwd = new SysConfig();
//        sysConfigPwd.setConf(SysConfigEntry.SMS_PASSWORD);
//        sysConfigPwd.setConfValue(sys_user_pwd);
//        list.add(sysConfigPwd);
//
//        for (SysConfig sysCofig : list) {
//            result = this.SysConfigService.modifySysConfig(sysCofig.getConf(), sysCofig.getConfValue());
//        }
        
        Integer investSwitch = params.getInt("investSwitch");
        String investTemplate = params.getString("investTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_INVEST_CFG, JSON.toJSONString(new SmsNotifyCfg(investSwitch, investTemplate)));
        
        Integer specialSwitch = params.getInt("specialSwitch");
        String specialTemplate = params.getString("specialTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_SPECIAL_TRANSFER_CFG, JSON.toJSONString(new SmsNotifyCfg(specialSwitch, specialTemplate)));
        
        Integer commonSwitch = params.getInt("commonSwitch");
        String commonTemplate = params.getString("commonTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_COMMON_TRANSFER_CFG, JSON.toJSONString(new SmsNotifyCfg(commonSwitch, commonTemplate)));
        
        Integer repaymentSwitch = params.getInt("repaymentSwitch");
        String repaymentTemplate = params.getString("repaymentTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_REPAYMENT_CFG, JSON.toJSONString(new SmsNotifyCfg(repaymentSwitch, repaymentTemplate)));
        
        //自动流标发给转让人的短信
        Integer abortBidTransferSwitch = params.getInt("abortBidTransferSwitch");
        String abortBidTransferTemplate = params.getString("abortBidTransferTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_ABORT_BID_TRANSFER_USER, JSON.toJSONString(new SmsNotifyCfg(abortBidTransferSwitch, abortBidTransferTemplate)));
        
        //自动流标发给投资人的短信
        Integer abortBidInvestSwitch = params.getInt("abortBidInvestSwitch");
        String abortBidInvestTemplate = params.getString("abortBidInvestTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_ABORT_BID_INVEST_USER, JSON.toJSONString(new SmsNotifyCfg(abortBidInvestSwitch, abortBidInvestTemplate)));
        
        //正常还款短信提醒
        Integer normalPaymentSwitch = params.getInt("normalPaymentSwitch");
        String normalPaymentTemplate = params.getString("normalPaymentTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_NORMAL_PAYMENT, JSON.toJSONString(new SmsNotifyCfg(normalPaymentSwitch, normalPaymentTemplate)));
        
        //逾期还款短信提醒
        Integer overduePaymentSwitch = params.getInt("overduePaymentSwitch");
        String overduePaymentTemplate = params.getString("overduePaymentTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_OVERDUE_PAYMENT, JSON.toJSONString(new SmsNotifyCfg(overduePaymentSwitch, overduePaymentTemplate)));
        
        //生日短信
        Integer smsBirthdaySwitch = params.getInt("smsBirthdaySwitch");
        String smsBirthdayTemplate = params.getString("smsBirthdayTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_BIRTHDAY, JSON.toJSONString(new SmsNotifyCfg(smsBirthdaySwitch, smsBirthdayTemplate)));
        
        return ResultMapper.toBaseVO(result);
    }
}
