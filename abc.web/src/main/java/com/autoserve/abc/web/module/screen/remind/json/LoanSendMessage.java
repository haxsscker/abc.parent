package com.autoserve.abc.web.module.screen.remind.json;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.remind.RemindService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.message.sms.SendMsgService;
import com.autoserve.abc.service.util.Jpush.JpushUtils;
import com.autoserve.abc.web.module.screen.projectmanage.json.AbstractLoanProjectListView;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 短信
 */
public class LoanSendMessage extends AbstractLoanProjectListView {
	private static final Logger logger = LoggerFactory
			.getLogger(LoanSendMessage.class);
    @Resource
    private RemindService remindService;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private SendMsgService sendMsgService;

    public JsonBaseVO execute(ParameterParser params) {
    	JsonBaseVO vo = new JsonBaseVO();

        String userPhone=params.getString("userPhone");
    	String loanNo=params.getString("loanNo");
    	String userName=params.getString("userName");
    	String loanExpireDate=params.getString("loanExpireDate");
    	
    	String content ="温馨提示：您的项目名称为'"+loanNo+"'的项目将于'"+loanExpireDate+"'过期";
    	boolean isSend = sendMsgService.sendMsg(userPhone, content, userName,"1");
    	/**消息推送给app**/
    	try{
    		JpushUtils.sendPush_alias(userName,content);
    	}catch(Exception e){
    		logger.error(e.getMessage());
    	}
    	
    	if(isSend)
    	{
    		vo.setMessage("短信发送成功");
    		vo.setSuccess(true);
    	}
    	else{
    		vo.setMessage("短信发送失败");
    		vo.setSuccess(false);
    	}
    	
		return vo;
    }

}
