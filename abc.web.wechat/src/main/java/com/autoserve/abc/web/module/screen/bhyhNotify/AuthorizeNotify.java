package com.autoserve.abc.web.module.screen.bhyhNotify;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.UserAuthorizeFlag;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.XmlHelper;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class AuthorizeNotify {
    private final static Logger logger = LoggerFactory.getLogger(AuthorizeNotify.class);
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;
    @Resource
    private UserService         userService;
    @Resource
    private HttpSession         session;
    @Resource
	private UserDao userDao;

    public void execute(Context context,Navigator nav,ParameterParser params) {
    	logger.info("===================授权/解授权异步返回===================");
//        Map<String, String> returnMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
        String ResultCode = params.get("RpCode") == null ? null : (String)params.get("RpCode");
        String  merBillNo = (String)params.get("MerBillNo");
        try {
        	if(null != ResultCode && ResultCode.equals("000000")){
        		String RespData=resq.getParameter("RespData");
        		logger.info("RespData解码前========"+RespData);
        		RespData=FormatHelper.GBKDecodeStr(RespData);
        		logger.info("RespData解码后========"+RespData);
        		String xml=XmlHelper.getRepXml(RespData);
        		logger.info(xml);
        		String respDataJsonStr = XmlHelper.xmlToJsonStr(xml,"RespData");
        		int respDataCount = respDataJsonStr.length() - respDataJsonStr.replace("auth_typ", "").length();
        		String authorizeInvestType="";
        		Date authorizeInvestStartDate=null;
        		Date authorizeInvestEndDate=null;
        		BigDecimal authorizeInvestAmount=BigDecimal.ZERO;
        		String authorizeFeeType="";
        		Date authorizeFeeStartDate=null;
        		Date authorizeFeeEndDate=null;
        		BigDecimal authorizeFeeAmount=BigDecimal.ZERO;
        		String authorizeRepayType="";
        		Date authorizeRepayStartDate=null;
        		Date authorizeRepayEndDate=null;
        		BigDecimal authorizeRepayAmount=BigDecimal.ZERO;
        		int userAuthorizeFlag=UserAuthorizeFlag.ENABLE.getState();
        		if(respDataCount>"auth_typ".length()){
        			List <Map> respDataListMap=JSON.parseArray(respDataJsonStr, Map.class);
        			for(Map mp : respDataListMap){
        				if("11".equals(mp.get("auth_typ").toString())){//11、投标 
        					authorizeInvestType=mp.get("auth_typ").toString()+"";
        					authorizeInvestStartDate=FormatHelper.formatStr2Date(mp.get("start_dt").toString(), "yyyyMMdd");
        					authorizeInvestEndDate=FormatHelper.formatStr2Date(mp.get("end_dt").toString(), "yyyyMMdd");
        					authorizeInvestAmount=new BigDecimal(FormatHelper.changeF2Y(mp.get("auth_amt").toString()));
        				}else if("59".equals(mp.get("auth_typ").toString())){//59、缴费 
        					authorizeFeeType=mp.get("auth_typ").toString();
        					authorizeFeeStartDate=FormatHelper.formatStr2Date(mp.get("start_dt").toString(), "yyyyMMdd");
        					authorizeFeeEndDate=FormatHelper.formatStr2Date(mp.get("end_dt").toString(), "yyyyMMdd");
        					authorizeFeeAmount=new BigDecimal(FormatHelper.changeF2Y(mp.get("auth_amt").toString()));
        				}else if("60".equals(mp.get("auth_typ").toString())){//60、还款
        					authorizeRepayType=mp.get("auth_typ").toString();
        					authorizeRepayStartDate=FormatHelper.formatStr2Date(mp.get("start_dt").toString(), "yyyyMMdd");
        					authorizeRepayEndDate=FormatHelper.formatStr2Date(mp.get("end_dt").toString(), "yyyyMMdd");
        					authorizeRepayAmount=new BigDecimal(FormatHelper.changeF2Y(mp.get("auth_amt").toString()));
        				}
        			}
        		}else{
        			Map respDataMap=JSON.parseObject(respDataJsonStr, Map.class);
        			if("11".equals(respDataMap.get("auth_typ").toString())){//11、投标 
    					authorizeInvestType=respDataMap.get("auth_typ").toString();
    					authorizeInvestStartDate=FormatHelper.formatStr2Date(respDataMap.get("start_dt").toString(), "yyyyMMdd");
    					authorizeInvestEndDate=FormatHelper.formatStr2Date(respDataMap.get("end_dt").toString(), "yyyyMMdd");
    					authorizeInvestAmount=new BigDecimal(FormatHelper.changeF2Y(respDataMap.get("auth_amt").toString()));
    				}else if("59".equals(respDataMap.get("auth_typ").toString())){//59、缴费 
    					authorizeFeeType=respDataMap.get("auth_typ").toString();
    					authorizeFeeStartDate=FormatHelper.formatStr2Date(respDataMap.get("start_dt").toString(), "yyyyMMdd");
    					authorizeFeeEndDate=FormatHelper.formatStr2Date(respDataMap.get("end_dt").toString(), "yyyyMMdd");
    					authorizeFeeAmount=new BigDecimal(FormatHelper.changeF2Y(respDataMap.get("auth_amt").toString()));
    				}else if("60".equals(respDataMap.get("auth_typ").toString())){//60、还款
    					authorizeRepayType=respDataMap.get("auth_typ").toString();
    					authorizeRepayStartDate=FormatHelper.formatStr2Date(respDataMap.get("start_dt").toString(), "yyyyMMdd");
    					authorizeRepayEndDate=FormatHelper.formatStr2Date(respDataMap.get("end_dt").toString(), "yyyyMMdd");
    					authorizeRepayAmount=new BigDecimal(FormatHelper.changeF2Y(respDataMap.get("auth_amt").toString()));
    				}else if("0".equals(respDataMap.get("auth_typ").toString())){//解授权
    					userAuthorizeFlag=UserAuthorizeFlag.DISABLE.getState();
    				}
        		}
        		UserDO user = new UserDO();
        		user.setAuthorizeSeqNo(merBillNo);
        		user.setAuthorizeInvestType(authorizeInvestType);
        		user.setAuthorizeInvestStartDate(authorizeInvestStartDate);
        		user.setAuthorizeInvestEndDate(authorizeInvestEndDate);
        		user.setAuthorizeInvestAmount(authorizeInvestAmount);
        		user.setAuthorizeFeeType(authorizeFeeType);
        		user.setAuthorizeFeeStartDate(authorizeFeeStartDate);
        		user.setAuthorizeFeeEndDate(authorizeFeeEndDate);
        		user.setAuthorizeFeeAmount(authorizeFeeAmount);
        		user.setAuthorizeRepayType(authorizeRepayType);
        		user.setAuthorizeRepayStartDate(authorizeRepayStartDate);
        		user.setAuthorizeRepayEndDate(authorizeRepayEndDate);
        		user.setAuthorizeRepayAmount(authorizeRepayAmount);
        		user.setUserAuthorizeFlag(userAuthorizeFlag);
        		userDao.updateAuthorizeBySeqNo(user);
        	}
        	resp.getWriter().print("SUCCESS");
        } catch (Exception e) {
            logger.error("[AuthorizeNotify] error: ", e);
        }
    }
}
