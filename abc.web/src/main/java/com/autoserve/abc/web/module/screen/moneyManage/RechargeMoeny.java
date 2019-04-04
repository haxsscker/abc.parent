package com.autoserve.abc.web.module.screen.moneyManage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.RechargeState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class RechargeMoeny {
	
	 @Resource
	    private UserAccountService userAccountService;
	    @Resource
	    private AccountInfoService accountService;
	    @Autowired
	    private RechargeService rechargeservice;
	
	public JsonBaseVO execute( ParameterParser params) {
		    JsonBaseVO result = new JsonBaseVO();
		    LoginUserInfo user = LoginUserInfoHelper.getLoginUserInfo();
	        if (user == null) {
	            //TODO 
	        }
	        UserIdentity ui = new UserIdentity();
	        ui.setUserId(user.getEmpId());
	        ui.setUserType(UserType.PLATFORM);
	        String rechargeAmount =FormatHelper.changeY2F(params.getString("rechargeAmount"));
	        String merAccTyp =params.getString("merAccTyp");
	        Map<String,String> map = new HashMap<String,String>();
	        
	    	BigDecimal moneyAmount = new BigDecimal(params.getString("rechargeAmount"));
	    	Map<String, String> res=rechargeservice.backRecharge(user.getEmpId(), UserType.PLATFORM, moneyAmount, map);
	    	String reult=res.get("resultData");
	    	String id=res.get("id");
	    	String merBillNo = res.get("MerBillNo");
	    	if("sucess".equals(reult)){
		        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
		        //InnerSeqNo seqNo = InnerSeqNo.getInstance();
		        paramsMap.put("MerBillNo", merBillNo);
		        paramsMap.put("biz_type", "MercRecharge");
		        paramsMap.put("TransAmt", rechargeAmount);
		        paramsMap.put("MerAccTyp", merAccTyp);
		        paramsMap.put("MerPriv", String.valueOf(user.getEmpId()));
		        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
		        if (resultMap != null) {
		        	String resultCode=resultMap.get("RespCode");
		        	String resultDesc=resultMap.get("RespDesc");
		        	String TransId=resultMap.get("TransId");
		        	RechargeRecordDO recharge = new RechargeRecordDO();
		        	recharge.setRechargeId(Integer.parseInt(id)); //id
		        	recharge.setRechargeOutSeqNo(TransId);   //平台交易流水号
		        	recharge.setRechargeSeqNo(merBillNo);
		        	if("000000".equals(resultCode)){
		        		recharge.setRechargeState(RechargeState.SUCCESS.getState());
		        		rechargeservice.updateRecharge(user.getEmpId(),moneyAmount,recharge);
		        		result.setSuccess(true);
		                result.setMessage(resultDesc);
		        	}else{
		        		recharge.setRechargeState(RechargeState.FAILURE.getState());
		        		rechargeservice.updateRecharge(user.getEmpId(),moneyAmount,recharge);
		        		result.setSuccess(false);
		                result.setMessage(resultDesc);
		        	}
		        }
	    	}
	        return result;
	}

}
