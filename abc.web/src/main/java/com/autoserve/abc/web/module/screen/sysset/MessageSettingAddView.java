package com.autoserve.abc.web.module.screen.sysset;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.vo.sysset.MessageVO;

/**
 * 显示短信帐号设置账户密码
 * 
 * @author liuwei
 */
public class MessageSettingAddView {

    @Resource
    private SysConfigService SysConfigService;

    public void execute(Context context, ParameterParser params) {

        List<String> list = new ArrayList<String>();
        list.add("SMS_USER");
        list.add("SMS_PASSWORD");

        ListResult<SysConfig> result = this.SysConfigService.queryListByParam(list);

        MessageVO vo = new MessageVO();

        for (SysConfig SysConfig : result.getData()) {
            if (SysConfig.getConf() == SysConfigEntry.SMS_USER) {
                vo.setSys_user_name(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.SMS_PASSWORD) {

                vo.setSys_user_pwd(SysConfig.getConfValue());
            }
        }
        context.put("vo", vo);
    }

}
