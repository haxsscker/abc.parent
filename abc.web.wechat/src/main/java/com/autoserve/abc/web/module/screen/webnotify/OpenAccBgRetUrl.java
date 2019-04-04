package com.autoserve.abc.web.module.screen.webnotify;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class OpenAccBgRetUrl {
	
	private final static Logger logger = LoggerFactory.getLogger(OpenAcctRetUrl.class);
	@Resource
	private AccountInfoService   accountInfoService;
	@Resource
	private HttpServletResponse resp;
	@Resource
	private HttpServletRequest  request;
	@Resource
	HuifuPayService huifuPayService;
	@Autowired
	private UserService     UserService;
	@Resource
    private BankInfoService bankinfoservice;
	@Resource
	private UserService userService;
	
	public void execute(Context context, Navigator nav, ParameterParser params){
		BaseResult result = new BaseResult();
		AccountInfoDO account = new AccountInfoDO();
		String cmdId = params.getString("cmdId").trim();
        String merCustId = params.getString("merCustId").trim();
        String usrId = params.getString("UsrId").trim();
        String usrCustId = params.getString("UsrCustID");
        String AuditStat = params.getString("AuditStat");
        String hereUserId = usrId.split("_")[1];
		String recvOrdId = "RECV_ORD_ID_"+params.getString("TrxId");
		String trxId = params.getString("TrxId");
		String RespCode  = params.getString("RespCode");
		String AuditDesc=params.getString("AuditDesc")==null?"":params.getString("AuditDesc");
		try {
			AuditDesc = java.net.URLDecoder.decode(AuditDesc,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String ordId = recvOrdId;
		context.put("recvOrdId", recvOrdId);
		context.put("trxId",trxId);
		context.put("ordId",ordId);
		PlainResult<UserDO> userDO = UserService.queryUserByUserName(hereUserId);
		
		// 如果审核不通过，那么更改账户开户状态为开户失败，并更改账户表的审核意见
		if(params.getString("RespCode").equals("217")){
			if(AuditStat.equals("R")){
				userService.modifyUserBusinessState(userDO.getData().getUserId(),UserType.ENTERPRISE, UserBusinessState.ACCOUNT_FAILURE);
				AccountInfoDO accountInfo = accountInfoService.queryByAccountMark(userDO.getData().getUserId(), userDO.getData().getUserType());
				if(accountInfo != null){
	    			Account accountInfoNow = new Account();
	    			accountInfoNow.setAccountUserId(userDO.getData().getUserId());  //商户私有域对应数据库字段userId
	    			accountInfoNow.setAccountUserName(userDO.getData().getUserName());
	    			accountInfoNow.setAccountMark(usrId); //用户号
	    			accountInfoNow.setAccountNo(usrCustId); //用户客户号
	    			accountInfoNow.setAccountLegalName(userDO.getData().getUserRealName());  //真实名称
	    			accountInfoNow.setAccountUserEmail(userDO.getData().getUserEmail());
	    			accountInfoNow.setAccountUserType(UserType.ENTERPRISE);
	    			accountInfoNow.setAccountAuditDesc(AuditDesc);
	    			result = accountInfoService.modifyAccountInfo(accountInfoNow);
	    		}
			}
		}
		
    	if(params.getString("RespCode").equals("000")){
    		AccountInfoDO accountInfo = accountInfoService.queryByAccountMark(userDO.getData().getUserId(), userDO.getData().getUserType());
    		if(accountInfo != null){
    			Account accountInfoNow = new Account();
    			accountInfoNow.setAccountUserId(userDO.getData().getUserId());  //商户私有域对应数据库字段userId
    			accountInfoNow.setAccountUserName(userDO.getData().getUserName());
    			accountInfoNow.setAccountMark(usrId); //用户号
    			accountInfoNow.setAccountNo(usrCustId); //用户客户号
    			accountInfoNow.setAccountLegalName(userDO.getData().getUserRealName());  //真实名称
    			accountInfoNow.setAccountUserEmail(userDO.getData().getUserEmail());
    			accountInfoNow.setAccountUserType(UserType.ENTERPRISE);
    			// 判断回调，如果有AccountNo 则说明是审核通过的，更改状态为 
                if(null != accountInfoNow.getAccountNo() && !accountInfoNow.getAccountNo().equals("")){
                	userService.modifyUserBusinessState(userDO.getData().getUserId(),UserType.ENTERPRISE, UserBusinessState.ACCOUNT_OPENED);
                }
    			result = accountInfoService.modifyAccountInfo(accountInfoNow);
    		}else{
    			account.setAccountUserId(userDO.getData().getUserId());  //商户私有域对应数据库字段userId
    			account.setAccountUserName(userDO.getData().getUserName());
        		account.setAccountMark(usrId); //用户号
    			account.setAccountNo(usrCustId); //用户客户号
    			account.setAccountUserEmail(userDO.getData().getUserEmail());
    			account.setAccountLegalName(userDO.getData().getUserRealName());  //真实名称
    			account.setAccountUserType(UserType.ENTERPRISE.type);
    			result = accountInfoService.openAccount(account);
    			BankInfo bankInfo = new BankInfo();
    			bankInfo.setBankUserId(userDO.getData().getUserId());
    			bankInfo.setBankUserType(UserType.ENTERPRISE.type);
    			bankInfo.setBankCode(params.getString("OpenBankId"));
    			bankInfo.setBankLawer(userDO.getData().getUserName());
    			bankInfo.setBankNo(params.getString("CardId"));
    			bankInfo.setCardStatus(CardStatus.STATE_ENABLE);
    			bankinfoservice.createBankInfo(bankInfo);
    		}
    		
    	}else{
    		System.out.println("开户失败！");
    		logger.error("[OpenAccount] error: ", "开户失败");
    	}
    	nav.forwardTo("/receive");
		
	}
}
