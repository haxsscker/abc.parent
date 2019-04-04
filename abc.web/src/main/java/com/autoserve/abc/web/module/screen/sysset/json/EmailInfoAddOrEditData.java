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

public class EmailInfoAddOrEditData {

	@Resource
	private SysConfigService SysConfigService;

	public JsonBaseVO execute(ParameterParser params) {
		BaseResult result = null;
		List<SysConfig> list = new ArrayList<SysConfig>();
		String sys_smtp = params.getString("sys_smtp");
		SysConfig smtp = new SysConfig();
		smtp.setConf(SysConfigEntry.MAIL_SMTP_SERVER);
		smtp.setConfValue(sys_smtp);
		list.add(smtp);

		String sys_port = params.getString("sys_port");
		SysConfig port = new SysConfig();
		port.setConf(SysConfigEntry.MAIL_PORT);
		port.setConfValue(sys_port);
		list.add(port);

		String sys_email_address = params.getString("sys_email_address");
		SysConfig email_address = new SysConfig();
		email_address.setConf(SysConfigEntry.MAIL_ADDRESS);
		email_address.setConfValue(sys_email_address);
		list.add(email_address);

		String sys_email_pwd = params.getString("sys_email_pwd");
		SysConfig email_pwd = new SysConfig();
		email_pwd.setConf(SysConfigEntry.MAIL_PASSWORD);
		email_pwd.setConfValue(sys_email_pwd);
		list.add(email_pwd);

		String sys_send_name = params.getString("sys_send_name");
		SysConfig send_name = new SysConfig();
		send_name.setConf(SysConfigEntry.MAIL_SENDER_NAME);
		send_name.setConfValue(sys_send_name);
		list.add(send_name);

		for (SysConfig SysConfig : list) {

			result=this.SysConfigService.modifySysConfig(SysConfig.getConf(),
					SysConfig.getConfValue());
		}

		return ResultMapper.toBaseVO(result);
	}
}
