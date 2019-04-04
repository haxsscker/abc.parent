package com.autoserve.abc.web.module.screen.moneyManage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.ToCashState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class CashMoeny {
	
    @Resource
    private UserAccountService userAccountService;
    @Resource
    private AccountInfoService accountService;
    @Resource
	private ToCashService tocashservice;

public JsonBaseVO execute( ParameterParser params) {
	 JsonBaseVO result = new JsonBaseVO();
	 LoginUserInfo user = LoginUserInfoHelper.getLoginUserInfo();
        if (user == null) {
            //TODO 
        }
        UserIdentity ui = new UserIdentity();
        ui.setUserId(user.getEmpId());
        ui.setUserType(UserType.PLATFORM);
//        AccountInfoDO aif = accountService.queryByUserIdentity(ui).getData();
        String tocashAmount =FormatHelper.changeY2F(params.getString("tocashAmount"));
        Map<String,String> map = new HashMap<String,String>();
        
        //提现金额
 		BigDecimal cashMoney=new BigDecimal(params.getString("tocashAmount"));
 		Map<String, String> res= tocashservice.toBackCash(user.getEmpId(), ui.getUserType(), cashMoney, map);
 		String reult=res.get("resultData");
    	String id=res.get("id");
    	String merBillNo = res.get("MerBillNo");
    	if("sucess".equals(reult)){
	 		//InnerSeqNo seqNo = InnerSeqNo.getInstance();
	        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
	        paramsMap.put("MerBillNo", merBillNo);
	        paramsMap.put("biz_type", "MercWithdraw");
	        paramsMap.put("TransAmt", tocashAmount);
	        paramsMap.put("MerPriv", String.valueOf(user.getEmpId()));
	        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
	        if (resultMap != null) {
	        	String resultCode=resultMap.get("RespCode");
	        	String resultDesc=resultMap.get("RespDesc");
	        	String TransId=resultMap.get("TransId");
	        	String MerBillNo=resultMap.get("MerBillNo");
	        	TocashRecordDO toCashDo = new TocashRecordDO();
	        	toCashDo.setTocashId(Integer.parseInt(id));
	        	toCashDo.setTocashOutSeqNo(TransId);
	        	toCashDo.setTocashSeqNo(MerBillNo);
	        	if("000000".equals(resultCode)){
	                toCashDo.setTocashState(ToCashState.SUCCESS.getState());
	                tocashservice.updateCash(user.getEmpId(),cashMoney,toCashDo);
	        		result.setSuccess(true);
	                result.setMessage(resultDesc);
	        	}else{
	        		toCashDo.setTocashState(ToCashState.FAILURE.getState());
	        		tocashservice.updateCash(user.getEmpId(),cashMoney,toCashDo);
	        		result.setSuccess(false);
	                result.setMessage(resultDesc);
	        	}
	        }
        }
        return result;
}


}
