package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;

public class RechargeMoney {
	private final static Logger logger = LoggerFactory
			.getLogger(RechargeMoney.class); // 鍔犲叆鏃ュ織

	@Autowired
	private UserService userservice;
	@Autowired
	private HttpSession session;
	@Autowired
	private DealRecordService dealrecordservice;
	@Autowired
	private AccountInfoService accountinfoservice;
	@Autowired
	private RechargeService rechargeservice;
	
	public void execute(Context context, ParameterParser params) {
    	User user=(User)session.getAttribute("user");
    	user = userservice.findEntityById(user.getUserId()).getData();
    	
    	UserIdentity userIdentity = new UserIdentity();
    	if(user.getUserType()==null||user.getUserType()==UserType.PERSONAL){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());
    	userIdentity.setUserId(user.getUserId());
    	PlainResult<Account> account =  accountinfoservice.queryByUserId(userIdentity) ;
    	
    	String money = FormatHelper.changeY2F(params.getString("money"));
    	//充值类型
    	//String rechargeType=params.getString("RechargeType");
    	//手续费类型
//    	String feeType="";
//    	if(rechargeType==null){
//    		rechargeType="";
//    	}else{
//    		//充值成功时从充值人账户全额扣除
//    		feeType="1";  
//    	}
    	
    	if(money!=null&&!"".equals(money)&&account!=null){

    	//获取开户号
    	String userOpenNo = account.getData().getAccountNo();
    	if(userOpenNo==null||"".equals(userOpenNo)){//尚未开户
    		
    		
    	}
    	
    	Map<String,String> map = new HashMap<String,String>();
    	BigDecimal bigdecimal = new BigDecimal(params.getString("money"));
    	PlainResult<DealReturn> DealReturn = rechargeservice.recharge(user.getUserId(), user.getUserType(), bigdecimal, map);
    	    	
    	//在此处构造接口参数（之前是在rechargeservice.recharge()里构造，现在废弃）
        PlainResult<Map> returnData = new PlainResult<Map>();
        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
        paramsMap.put("Transid", "CBHBNetLoanRecharge");
        paramsMap.put("MerBillNo", DealReturn.getData().getDrInnerSeqNo());
        paramsMap.put("PlaCustId", account.getData().getAccountNo());
        paramsMap.put("FeeType", "0");
        paramsMap.put("MerFeeAmt", "0");
        paramsMap.put("FrontUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("rechargeReturnUrl"));
        paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("rechargeNotifyUrl"));    	
        paramsMap.put("TransAmt", money);
        paramsMap.put("MerPriv", String.valueOf(user.getUserId()));
        paramsMap.put("TransTyp", params.getString("TransTyp"));
        Map<String, String> resultMap = ExchangeDataUtils.getMobileSubmitData(paramsMap);
        resultMap.remove("MerBillNo");
      //微信端通过页面get方式请求会把参数再次编码，所以这边先解码，避免2次编码，app端就不用解码了
    	String NetLoanInfo = URLDecoder.decode((String) resultMap.get("NetLoanInfo"));
//    	logger.info(NetLoanInfo);
    	resultMap.put("NetLoanInfo",NetLoanInfo);
        returnData.setSuccess(true);
        returnData.setData(resultMap);
    	context.put("SubmitURL", returnData.getData().remove("requestUrl"));
    	context.put("paramMap", returnData);
    	
    	}
    }
}
