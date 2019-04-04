package com.autoserve.abc.web.module.screen.account.myAccount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class OrderNoDetails {
	@Autowired
    private HttpSession session;
	@Autowired
    private UserService         userservice;
	@Resource
    private DoubleDryService doubleDryService;
	@Resource
    private RechargeService rechargeservice;

    public void execute(Context context, ParameterParser params) throws ParseException {
    	String OrderNo = (String)params.get("OrderNo");
    	String LoanNo = (String)params.get("LoanNo");
    	String Action = (String)params.get("State");
    	
    	Map<String,String> map = new HashMap<String, String>();
    	if(LoanNo!=null && !"".equals(LoanNo)){
    		map.put("LoanNo", LoanNo);
    	}
    	if(OrderNo!=null && !"".equals(OrderNo)){
    		map.put("OrderNo", OrderNo);
    	}   	
    	map.put("Action", Action);
    	PlainResult<Map> result = doubleDryService.balanceAccount(map);
    	SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
    	SimpleDateFormat sdf2 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    	
    	if(result.getData()!=null && "".equals(Action)){
	    	String TransferTime = sdf2.format(sdf.parse((String) result.getData().get("TransferTime")));
	    	String ActTime = sdf2.format(sdf.parse((String) result.getData().get("ActTime")));
	    	
	    	String SecondaryTime = "";
	    	if(result.getData().get("SecondaryTime") != null && !result.getData().get("SecondaryTime").equals("")) {
	    		SecondaryTime = sdf2.format(sdf.parse((String) result.getData().get("SecondaryTime")));
	    	}
	    	context.put("TransferTime", TransferTime);
        	context.put("ActTime", ActTime);
        	context.put("SecondaryTime", SecondaryTime);
    	}
    	if(result.getData()!=null&&Action.equals("1")){
	    	Date rechargetime = sdf.parse((String) result.getData().get("RechargeTime"));
	    	String RechargeTime = sdf2.format(rechargetime);
	    	context.put("RechargeTime", RechargeTime);
    	}
    	if(result.getData()!=null&&Action.equals("2")){
    		String WithdrawsTime = "";
    		String PlatformAuditTime ="";
    		String WithdrawsBackTime ="";
    		WithdrawsTime = (String) result.getData().get("WithdrawsTime");
    		PlatformAuditTime = (String) result.getData().get("PlatformAuditTime");
    		WithdrawsBackTime = (String) result.getData().get("WithdrawsBackTime");
    		if(WithdrawsTime!=null&&!"".equals(WithdrawsTime)){
    			WithdrawsTime = sdf2.format(sdf.parse(WithdrawsTime));
    		}
        	if(PlatformAuditTime!=null&&!"".equals(PlatformAuditTime)){
        		PlatformAuditTime = sdf2.format(sdf.parse(PlatformAuditTime));
    		}
        	if(WithdrawsBackTime!=null&&!"".equals(WithdrawsBackTime)){
        		WithdrawsBackTime = sdf2.format(sdf.parse(WithdrawsBackTime));
			}
        	context.put("WithdrawsTime", WithdrawsTime);
        	context.put("PlatformAuditTime", PlatformAuditTime);
        	context.put("WithdrawsBackTime", WithdrawsBackTime);
        }
    	
    	context.put("Action", Action);
    	context.put("result", result.getData());
    }
}
