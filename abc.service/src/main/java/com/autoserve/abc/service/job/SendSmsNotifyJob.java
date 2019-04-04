package com.autoserve.abc.service.job;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.dao.dataobject.SmsNotifyDO;
import com.autoserve.abc.dao.intf.SmsNotifyDao;
import com.autoserve.abc.service.message.sms.SendMsgService;

/**
 * 发送短信通知处理job
 * @author ding747
 *
 */
public class SendSmsNotifyJob implements BaseJob {
    
	public static final Integer repeatCount = 3;
	
	private static final Logger logger = LoggerFactory.getLogger(SendSmsNotifyJob.class);
	
	@Resource
	private SendMsgService sendMsgService;
	
	@Resource
	private SmsNotifyDao smsNotifyDao;
	
	@Override
    public void run() {
    	
    	try {
    		logger.info("发送短信job开始 "+new Date());
    		
    		List<SmsNotifyDO> smsNotifys = smsNotifyDao.selectUnSendedSms(SendSmsNotifyJob.repeatCount);
    		//System.out.println("****************" + smsNotifys.size() + "****************");
    		
    		for (SmsNotifyDO smsNotifyDO : smsNotifys) {
    			boolean isSuccess = sendMsgService.sendMsg(smsNotifyDO.getReceivePhone(), smsNotifyDO.getContent(), "","1");
    			smsNotifyDO.setSendStatus(isSuccess?1:2);
    			smsNotifyDO.setSendCount(smsNotifyDO.getSendCount()+1);
    			smsNotifyDO.setSendTime(new Date());
    			smsNotifyDao.update(smsNotifyDO);
			}
    		
    		logger.info("发送短信job结束"+new Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("发送短信处理执行异常 "+new Date());
		}
    	
    }


}
