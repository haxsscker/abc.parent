package com.autoserve.abc.web.module.screen.webnotify;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.CardType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.cash.TiedCardService;
import com.autoserve.abc.service.biz.intf.user.UserService;

import org.apache.commons.lang.StringUtils;

public class BankCardBgRetUrlAction {
	@Resource 
	private HttpSession session;
	@Resource 
	private HuifuPayService payMent;
	@Resource
	private BankInfoService bankInfoSer;
	@Resource
	private AccountInfoService accountInfoSer;
	@Resource
	private HttpServletRequest req;
	@Resource
	private HttpServletResponse resp;
	@Resource
	private UserService userService;
	@Resource
	private TiedCardService tiedCardService;
	public void execute(Context context,ParameterParser params,Navigator nav){
		 System.out.println("测试回调次数");
         HuiFuData  data=payMent.bankCardResult(req);
		if (!data.getRespCode().equals("000")) {
			String errorMsg="";
				try {
					 errorMsg = "绑卡失败,失败原因："
							+ URLDecoder.decode(data.getRespDesc(), "utf-8");
					context.put("resultCode", data.getRespCode());
					context.put("resultDesc", errorMsg);
					nav.redirectToLocation("/error");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		}else{
			BankInfo bankInfo = new BankInfo();
			
			AccountInfoDO  infoDO = accountInfoSer.queryByAccountNo(data.getUsrCustId());
			bankInfo.setBankUserId(infoDO.getAccountUserId());
	    	bankInfo.setBankLawer(infoDO.getAccountUserName());
	    	bankInfo.setBankUserType(infoDO.getAccountUserType());
	    	bankInfo.setCardType(CardType.DEBIT_CARD);
	    	bankInfo.setBankNo(data.getOpenAcctId());
	    	bankInfo.setBankCode(data.getOpenBankId());
	    	bankInfo.setCardStatus(CardStatus.STATE_ENABLE);
	    	bankInfo.setBindDate(new Date());
	    	tiedCardService.tiedCard(bankInfo);
	       
			//修改用户是否绑定银行卡标识
			UserDO userDo=new UserDO();
		    userDo.setUserId(infoDO.getAccountUserId());
			userDo.setUserBankcardIsbinded(1);
			userService.modifyUserSelective(userDo);
		}
		String trxId=data.getTrxId();
		try {
            if (StringUtils.isNotBlank(trxId)) {
                PrintWriter out = resp.getWriter();
                out.print("RECV_ORD_ID_".concat(trxId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}
