package com.autoserve.abc.web.module.screen.remind.json;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.remind.RemindService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.message.mail.SendMailService;
import com.autoserve.abc.web.module.screen.projectmanage.json.AbstractLoanProjectListView;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 
 */
public class LoanExpireSendMail extends AbstractLoanProjectListView {
	
    @Resource
    private RemindService remindService;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private SendMailService sendMailService;
    

    private static Logger   logger = LoggerFactory.getLogger(LoanExpireSendMail.class);

    public JsonBaseVO execute(ParameterParser params) {
    	
    	String empId = params.getString("empId");
    	
    	String loanNo = params.getString("loanNo");
    	String loanExpireDate = params.getString("loanExpireDate");
    	JsonBaseVO vo = new JsonBaseVO();
    	
    	// 查询用户服务
    	PlainResult<UserDO> userResult = userService.findById(Integer.valueOf(empId));
    	if(null != userResult.getData() && null != userResult.getData().getUserEmail()){
    		
    		String email = userResult.getData().getUserEmail();
    		String subject = "来自新华久久贷的邮件";
    		String content = "尊敬的客户：<br/>您好，这是来自新华久久贷的邮件，您的项目名称为'"+loanNo+"'的项目将于'"+loanExpireDate+"'过期<br/><br/>详细信息请您登陆新华久久贷网站(http://www.xh99d.com)中查看  <br/>新华久久贷";
    		BaseResult result = sendMailService.sendMail(email,content,subject);
    		vo.setSuccess(result.isSuccess());
    		vo.setMessage(result.getMessage());
    	}else{
    		vo.setSuccess(false);
    		vo.setMessage("没有找到该用户的邮箱!");
    	}
    	System.out.println(vo.isSuccess());
    	
		return vo;
    }

}
