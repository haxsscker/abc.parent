package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.CardType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;

public class WithdrawMoney {
	@Autowired
    private HttpSession session;
	@Autowired
    private AccountInfoService accountInfoService;
	@Autowired
    private DealRecordService dealrecordservice;
	@Resource
    private DoubleDryService doubleDryService;
	@Resource
	private BankInfoService	bankInfoService;
	@Resource
	private ToCashService tocashservice;
	@Resource
	private HttpServletRequest request;
	@Resource
	private UserService userService;
	@Resource
	private SysConfigService sysConfigService;
	@Autowired
	private DeployConfigService deployConfigService;
    public void execute(Context context, ParameterParser params,Navigator nav) {   	
    	User user=(User)session.getAttribute("user");
    	int monthtimes = Integer.valueOf(params.getString("monthtimes"));
    	String regex = "|";
    	
    	if(user==null){
    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
    		return;
    	}  
    	UserIdentity userIdentity =new UserIdentity();
    	userIdentity.setUserId(user.getUserId());
    	if(user.getUserType()==null || user.getUserType().getType()==1){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());	
    	 PlainResult<Account> result = accountInfoService.queryByUserId(userIdentity);
    	 Account acc= result.getData();
    	 
    	 Double money = params.getDouble("TransAmt");
     	 String mey =params.getString("TransAmt");
     	
     	if(money!=null && acc!=null){
     		//String param = params.getString("param");
	 		//生成订单号
	     	//InnerSeqNo innerseqno = InnerSeqNo.getInstance();
	     	Map<String,Object> map = new HashMap<String,Object>();
	 		//快速提现
			//String CardNo = params.getString("CardNo");
	     	//map.put("OrderNo", innerseqno.toString());
	     	map.put("Amount", mey);
	     	//map.put("FeeMax", "");
	     	//map.put("FeeRate", "");
	     	map.put("monthtimes", monthtimes);
	
	 		//提现金额
	 		BigDecimal cashMoney=new BigDecimal(money);
	 		
	 		//计算手续费比例,存入map
	 		BaseResult resultx=tocashservice.calculationPlatformFee(user.getUserId(), cashMoney, map);
	 		if(!resultx.isSuccess()){
	        	context.put("Message", resultx.getMessage());
	        	nav.forwardTo("/error").end();
	 		}
	 		
	    	PlainResult<DealReturn> dealReturn = tocashservice.toCashOther(user.getUserId(), user.getUserType(), cashMoney, map);
	     	
	    	//在此处构造接口参数
	        PlainResult<Map> returnData = new PlainResult<Map>();
	        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
	        paramsMap.put("MerBillNo", dealReturn.getData().getDrInnerSeqNo());
	        paramsMap.put("biz_type", "Drawings");
	        paramsMap.put("PlaCustId", acc.getAccountNo());
	        paramsMap.put("TransAmt", FormatHelper.changeY2F(params.getString("TransAmt")));
	        
	        PlainResult<SysConfig> withdrawrate = sysConfigService
					.querySysConfig(SysConfigEntry.WITHDRAW_RATE);
			if (!withdrawrate.isSuccess()) {
				context.put("Message", "用户提现手续费费率查询失败");
				nav.forwardTo("/error").end();
			}
	        if(monthtimes>0){
	        	paramsMap.put("FeeType", "0");
		        paramsMap.put("MerFeeAmt", "0");
	        }else if(monthtimes==0){
	        	paramsMap.put("FeeType", "1");
		        paramsMap.put("MerFeeAmt", FormatHelper.changeY2F(cashMoney.multiply(new BigDecimal(withdrawrate.getData().getConfValue())).divide(new BigDecimal("100"))));
	        }	        
	        paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("withdrawReturnUrl"));
	        paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("withdrawsNotifyUrl"));
	        paramsMap.put("MerPriv", String.valueOf(user.getUserId()));
	        paramsMap.put("TransTyp", params.getString("TransTyp"));
	        Map<String, String> resultMap = ExchangeDataUtils.getSubmitData(paramsMap);
	        returnData.setSuccess(true);
	        returnData.setData(resultMap);
	    	context.put("SubmitURL", returnData.getData().remove("requestUrl"));
	    	context.put("paramMap", returnData);
     	}	
    }
}
