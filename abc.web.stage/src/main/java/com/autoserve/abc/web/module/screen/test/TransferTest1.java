package com.autoserve.abc.web.module.screen.test;

import java.util.Date;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.TransferActionStste;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.MapResult;
import com.autoserve.abc.service.util.Md5Util;
import com.autoserve.abc.service.util.SystemGetPropeties;

public class TransferTest1 {
	 @Resource
	 private DoubleDryService    doubleDryService;
	 
	 public void execute(Context context, ParameterParser params) {
		 BaseResult baseResult=new BaseResult();
		 //付款人乾多多标识
		 String LoanOutMoneymoremore=params.getString("LoanOutMoneymoremore");
		 //收款人乾多多标识
		 String LoanInMoneymoremore=params.getString("LoanInMoneymoremore");
		 //金额
		 String Amount=params.getString("Amount");
		//时间字符串
        StringBuffer str = new StringBuffer();
        str.append(new Date().toString());
		 String loanJsonListStr=doubleDryService.loanJsonList(
				 LoanInMoneymoremore,LoanOutMoneymoremore,
				 Amount, SystemGetPropeties.getStrString("PlatformMoneymoremore"),
                 Md5Util.md5(str.toString()), null);
		 //调用转账接口
	     MapResult<String, String> resultMap = this.doubleDryService.transfer(loanJsonListStr, TransferActionStste.INVEST.state, "2", "2", "1", null);
	     String resultCode = resultMap.getData().get("ResultCode");
	     if (!"88".equals(resultCode)) {        	
        	baseResult.setSuccess(false);        	
	    }else{
        	baseResult.setSuccess(true);
	    }
	     baseResult.setMessage(resultMap.getData().get("Message"));
	     context.put("result", baseResult);
	 }
}
