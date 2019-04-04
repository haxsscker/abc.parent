package com.autoserve.abc.service.job;

import java.util.Date;
import java.util.List;

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
 * 发送生日短信通知处理job
 * @author ding747
 *
 */
public class SendBirthdaySmsJob implements BaseJob {
	
	private static final Logger logger = LoggerFactory.getLogger(SendBirthdaySmsJob.class);
	
	@Resource
	private SendMsgService sendMsgService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Resource
	private UserDao userDao;
	
	@Override
    public void run() {
    	
    	try {
    		logger.info("发送生日job开始 "+new Date());
    		
    		List<UserDO> users = userDao.findBirthdayUser();
    		
    		PlainResult<SmsNotifyCfg> smsResult = sysConfigService
					.querySmsNotifyConfig(SysConfigEntry.SMS_NOTIFY_BIRTHDAY);
    		
    		for (UserDO u : users) {
    			if (smsResult.isSuccess()
    					&& smsResult.getData().getSwitchState() == 1) {
    				SmsNotifyCfg smsNotifyCfg = smsResult.getData();
    				String content = smsNotifyCfg.getContentTemplate();
    				
    				System.out.println(u.getUserPhone() + "|" + content);
    				boolean isSuccess = sendMsgService.sendMsg(u.getUserPhone(), content, "","1");
    				
    				/**消息推送给app**/
    				try{
        				JpushUtils.sendPush_alias(u.getUserName(),content);
    				}catch(Exception e){
    					logger.error(e.getMessage());
    				}
    			}
			}
    		
    		logger.info("发送生日job结束"+new Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("发送生日处理执行异常 "+new Date());
		}
    	
    }


}
