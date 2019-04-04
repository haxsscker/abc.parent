package com.autoserve.abc.web.module.screen.sysset.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.SysConfig;
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
public class MessageAddOrEditData {
    @Resource
    private SysConfigService SysConfigService;

    public JsonBaseVO execute(ParameterParser params) {
        BaseResult result = null;
        List<SysConfig> list = new ArrayList<SysConfig>();
        String sys_user_name = params.getString("sys_user_name");
        String sys_user_pwd = params.getString("sys_user_pwd");

        SysConfig sysConfigName = new SysConfig();
        sysConfigName.setConf(SysConfigEntry.SMS_USER);
        sysConfigName.setConfValue(sys_user_name);
        list.add(sysConfigName);
        SysConfig sysConfigPwd = new SysConfig();
        sysConfigPwd.setConf(SysConfigEntry.SMS_PASSWORD);
        sysConfigPwd.setConfValue(sys_user_pwd);
        list.add(sysConfigPwd);

        for (SysConfig sysCofig : list) {

            result = this.SysConfigService.modifySysConfig(sysCofig.getConf(), sysCofig.getConfValue());
        }

        return ResultMapper.toBaseVO(result);
    }
}
