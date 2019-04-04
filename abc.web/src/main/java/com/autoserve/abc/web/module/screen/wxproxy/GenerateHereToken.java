package com.autoserve.abc.web.module.screen.wxproxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.wxproxy.WxProxyService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPlainVO;

public class GenerateHereToken {
	@Resource
	private WxProxyService wxProxyService;
	
 private final static Logger logger = LoggerFactory.getLogger(GenerateHereToken.class);
 public JsonPlainVO<Map<String, String>> execute(Context context, ParameterParser params) {
	   PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
       Map<String, String> param = new HashMap<String, String>();
       String token = getCharAndNumr(8);
       String backUrl =SystemGetPropeties.getStrString("wxBackUrl");
       
       return ResultMapper.toPlainVO(result);	
  }
	 
	 
	 
	 public static String getCharAndNumr(int length)     
	 {     
	     String val = "";     
	              
	     Random random = new Random();     
	     for(int i = 0; i < length; i++)     
	     {     
	         String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字     
	         if("char".equalsIgnoreCase(charOrNum)) // 字符串     
	         {     
	             int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母     
	             val += (char) (choice + random.nextInt(26));     
	         }     
	         else if("num".equalsIgnoreCase(charOrNum)) // 数字     
	         {     
	             val += String.valueOf(random.nextInt(10));     
	         }     
	     }     
	              
	     return val;     
	 }   
	       
}
