package com.autoserve.abc.service.job;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.autoserve.abc.dao.dataobject.SmsNotifyCfg;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.message.sms.SendMsgService;
import com.autoserve.abc.service.util.Jpush.JpushUtils;

/**
 * 发送短信通知处理job
 * 
 * @author sxd
 * 
 */
public class SendValidRedSmsJob implements BaseJob {

	private static final Logger logger = LoggerFactory
			.getLogger(SendValidRedSmsJob.class);

	@Resource
	private SendMsgService sendMsgService;

	@Autowired
	private SysConfigService sysConfigService;

	@Resource
	private UserDao userDao;

	@Override
	public void run() {

		try {

			logger.info("Send valid redsend Job begin " + new Date());

			List<UserDO> users = userDao.findRedSendValidUser();
			PlainResult<SmsNotifyCfg> smsResult = sysConfigService
					.querySmsNotifyConfig(SysConfigEntry.SMS_NOTIFY_VALID_REDSEND);

			Set<String> phoneSet = new HashSet<String>();
			for (UserDO u : users) {
				phoneSet.add(u.getUserPhone());
				/**消息推送给app**/
				try{
					JpushUtils.sendPush_alias(u.getUserName(),smsResult
							.getData().getContentTemplate());
				}catch(Exception e){
					logger.error(e.getMessage());
				}
			}
			
			String[] ps = new String[phoneSet.size()];
			String[] toArray = phoneSet.toArray(ps);

			sendMsgService.sendMsg(toArray, smsResult
					.getData().getContentTemplate());

			logger.info("Send valid redsend Job end " + new Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Send valid redsend Job error " + new Date());
		}

	}

}
