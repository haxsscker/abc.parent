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
import com.autoserve.abc.web.vo.sysset.EmailVO;

public class EmailSettingAddView {
	 @Resource
	    private SysConfigService SysConfigService;
		public void execute(Context context, ParameterParser params) {
			
			List<String> list = new ArrayList<String>();
			list.add("MAIL_SMTP_SERVER");
			list.add("MAIL_PORT");
			list.add("MAIL_ADDRESS");
			list.add("MAIL_PASSWORD");
			list.add("MAIL_SENDER_NAME");
			
			ListResult<SysConfig> result = this.SysConfigService.queryListByParam(list);
			
			EmailVO vo = new EmailVO();
			
			for(SysConfig SysConfig : result.getData())
			{
				if(SysConfig.getConf()==SysConfigEntry.MAIL_SMTP_SERVER)
				{
					vo.setSys_smtp(SysConfig.getConfValue());
				}
				if(SysConfig.getConf()==SysConfigEntry.MAIL_PORT)
				{
					vo.setSys_port(SysConfig.getConfValue());
				}
				if(SysConfig.getConf()==SysConfigEntry.MAIL_ADDRESS)
				{
					vo.setSys_email_address(SysConfig.getConfValue());
				}
				if(SysConfig.getConf()==SysConfigEntry.MAIL_PASSWORD)
				{
					vo.setSys_email_pwd(SysConfig.getConfValue());
				}
				if(SysConfig.getConf()==SysConfigEntry.MAIL_SENDER_NAME)
				{
					vo.setSys_send_name(SysConfig.getConfValue());
				}
			}
			context.put("vo", vo);
		}

}
