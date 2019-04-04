package com.autoserve.abc.web.module.screen.apply.json;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 意向申请
 * @author DS
 *
 * 2015年1月21日下午1:20:35
 */
public class ApplySubmit {
	
	@Autowired
    private UserService         userService;
	@Autowired
    private DeployConfigService deployConfigService;
	@Autowired
    private HttpServletRequest  request;
	@Autowired
    private HttpSession         session;
    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private LoanIntentApplyService  loanintentapplyservice;
    @Autowired
    private SysConfigService  	sysConfigService;

    public BaseResult execute(@Params LoanIntentApply loanIntentApply, Context context,  Navigator nav, ParameterParser params) {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request)).end();
    		return null;
    	}  
    	BaseResult message = new BaseResult();
    	//个体每日融资项目数
    	PlainResult<SysConfig> sysConfigInfo =sysConfigService.querySysConfig(SysConfigEntry.LOAN_LIMIT_PER_DAY);
    	SysConfig sysConfig=sysConfigInfo.getData();
    	//查询个体每日融资项目数
    	PlainResult<Integer> plainResult=loanintentapplyservice.countApplyLoanIntentforNow(user.getUserId());
    	if(sysConfig!=null && sysConfig.getConfValue()!=null && !"".equals(sysConfig.getConfValue())){
    		Integer count=plainResult.getData();
    		if(count>=Integer.parseInt(sysConfig.getConfValue())){
    			message.setSuccess(false);
        		message.setMessage("今日融资项目数大于上限"+sysConfig.getConfValue()+"次");
        		return message;
    		}
    	}
    	int type=params.getInt("type");
    	String securityCode = params.getString("securityCode");
    	String securityfromSession=(String)session.getAttribute("securityCode");
    	if (securityfromSession==null || securityCode==null || !securityfromSession.equalsIgnoreCase(securityCode)) { 
    		if(securityfromSession==null){
    			message.setMessage("验证码已失效，请重新获取");
        	}else{
        		message.setMessage("验证码错误");
        	}   
    		message.setSuccess(false);
    		return message;
    	} else{    		
    		// 该用户的可用信用额度
    		BigDecimal userCreditSett = userService.findById(user.getUserId()).getData().getUserCreditSett();
    		
			if (userCreditSett == null
					|| userCreditSett.compareTo(loanIntentApply
							.getIntentMoney()) < 0) {
				message.setSuccess(false);
				message.setMessage("可用信用额度不足");
				return message;
			}
    		
    		Integer unit=params.getInt("intentPeriodUnit");
    		if(unit!=null){
    			loanIntentApply.setIntentPeriodUnit(LoanPeriodUnit.valueOf(unit));
    		}   		
    		//标类型
        	loanIntentApply.setIntentCategory(LoanCategory.valueOf(type));
        	//融资利率
        	loanIntentApply.setIntentRate(new BigDecimal(11));
	    	//申请时间
	    	loanIntentApply.setIntentTime(new Date());
	    	//状态
	    	loanIntentApply.setIntentState(LoanState.WAIT_INTENT_REVIEW);  	
	    	message =  loanintentapplyservice.createLoanIntentApply(loanIntentApply);
	    	return message;
		}
    }
    
}
