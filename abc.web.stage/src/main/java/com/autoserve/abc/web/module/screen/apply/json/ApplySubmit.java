package com.autoserve.abc.web.module.screen.apply.json;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.FeeSettingDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.FeeSettingDao;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.DateUtils;

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
    private LoanIntentApplyService  loanintentapplyservice;
    @Autowired
    private SysConfigService  	sysConfigService;
    @Autowired
    private FeeSettingDao feeSettingDao;
    @Autowired
    private AccountInfoService  accountInfoService;

    public BaseResult execute(@Params LoanIntentApply loanIntentApply, Context context,  Navigator nav, ParameterParser params) throws ParseException {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request)).end();
    		return null;
    	}  
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date currentTime=formatter.parse(formatter.format(new Date())); //这是获取当前时间
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
    	if (securityfromSession==null || securityCode==null || 
    			!securityfromSession.equalsIgnoreCase(Md5Encrypt.md5(securityCode))) { 
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
			
			PlainResult<UserDO> userDoResult=userService.findById(user.getUserId());
			//缴费授权
			Date authorizeFeeStartDate=userDoResult.getData().getAuthorizeFeeStartDate();
			Date authorizeFeeEndDate=userDoResult.getData().getAuthorizeFeeEndDate();
			BigDecimal authorizeFeeAmount=null != userDoResult.getData().getAuthorizeFeeAmount()?userDoResult.getData().getAuthorizeFeeAmount():BigDecimal.ZERO;
			//还款授权
			Date authorizeRepayStartDate=userDoResult.getData().getAuthorizeRepayStartDate();
			Date authorizeRepayEndDate=userDoResult.getData().getAuthorizeRepayEndDate();
			BigDecimal authorizeRepayAmount=null != userDoResult.getData().getAuthorizeRepayAmount()?userDoResult.getData().getAuthorizeRepayAmount():BigDecimal.ZERO;
			//账户判断
			userDoResult.getData().setAccountCategory(AccountCategory.LOANACCOUNT.getType());
			BaseResult res=accountInfoService.isOpenAccount(userDoResult.getData());
			if(!res.isSuccess()){
				message.setSuccess(false);
				message.setMessage(res.getMessage());
				return message;
			}
			//授权判断
			if(!"59".equals(userDoResult.getData().getAuthorizeFeeType())){
				message.setSuccess(false);
				message.setMessage("您还未开启缴费授权，请先去授权！");
				return message;
			}else if(!"有效".equals(AuthorizeUtil.isAuthorize(authorizeFeeStartDate,authorizeFeeEndDate))){
				message.setSuccess(false);
				message.setMessage("缴费授权"+AuthorizeUtil.isAuthorize(authorizeFeeStartDate,authorizeFeeEndDate)+",请去修改！");
				return message;
			}else if(!"60".equals(userDoResult.getData().getAuthorizeRepayType())){
				message.setSuccess(false);
				message.setMessage("您还未开启还款授权，请先去授权！");
				return message;
			}else if(!"有效".equals(AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate))){
				message.setSuccess(false);
				message.setMessage("还款授权"+AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate)+",请去修改！");
				return message;
			}else if(loanIntentApply.getIntentMoney().doubleValue()>authorizeRepayAmount.doubleValue()){
				message.setSuccess(false);
				message.setMessage("借款金额超过您的还款授权金额,请去修改！");
				return message;
			}
			
    		Integer unit=params.getInt("intentPeriodUnit");
    		if(unit!=null){
    			loanIntentApply.setIntentPeriodUnit(LoanPeriodUnit.valueOf(unit));
    		}   
    		BigDecimal base = loanIntentApply.getIntentMoney();
			int intentPeriod = loanIntentApply.getIntentPeriod();
			Date currentDay = new Date();
			Date expireDay = currentDay;
			if(unit==3){
				expireDay = DateUtils.addDate(currentDay, intentPeriod);
			}else if(unit==2){
				expireDay = DateUtils.addMonth(currentDay, intentPeriod);
			}else if(unit==1){
				expireDay = DateUtils.addYear(currentDay, intentPeriod);
			}
			int day = (int)DateUtils.getDistanceOfTwoDate(currentDay, expireDay);
			BigDecimal fee =BigDecimal.ZERO;
			FeeSettingDO feeSetting = feeSettingDao.findByFeeTypeLoanCatogory(FeeType.PLA_SERVE_FEE.type, type, base);
			if(null != feeSetting){
				switch (feeSetting.getFsChargeType()) {
		            case 1: {
		                //按笔收费
		                fee = feeSetting.getFsAccurateAmount();
		                break;
		            }
		            case 2: {
		                //按比例收费
		                double temp = base.doubleValue() * feeSetting.getFsRate() / 100;
		                fee = new BigDecimal(temp).setScale(2, BigDecimal.ROUND_HALF_UP);
		                break;
		            }
				}
			}
			if(fee.doubleValue()>authorizeFeeAmount.doubleValue()){
				message.setSuccess(false);
				message.setMessage("平台预计收取服务费（"+fee.doubleValue()+"元）超过您的缴费授权金额,请去修改！");
				return message;
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
