package com.autoserve.abc.web.module.screen.webnotify.hfnotify;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;

import org.apache.commons.lang.StringUtils;

public class CreditBgRetUrlAction {
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
	
	public void execute(Context context,ParameterParser params,Navigator nav){
		Map<String,String>  map =payMent.crditBgRetUrl(req);
		if (!map.get("RespCode").equals("000")) {
			String errorMsg="";
				try {
					 errorMsg = "放款失败,失败原因："
							+ URLDecoder.decode(map.get("RespDesc"), "utf-8");
				context.put("resultCode", map.get("RespCode"));
				context.put("resultDesc", errorMsg);
				nav.redirectToLocation("/error");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		}
		String trxId=map.get("TrxId");
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
