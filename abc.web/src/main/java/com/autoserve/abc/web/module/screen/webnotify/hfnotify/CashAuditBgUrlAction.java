package com.autoserve.abc.web.module.screen.webnotify.hfnotify;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;

public class CashAuditBgUrlAction {
	 private final static Logger logger = LoggerFactory.getLogger(CashAuditBgUrlAction.class);
	    @Resource
	    private ToCashService toCashService;
	    @Resource
	    private HttpServletResponse resp;
	    @Resource
	    private HttpServletRequest  resq;
	    @Resource 
		private HuifuPayService payMent;  

	    public void execute(Context context, ParameterParser params) {
			System.out.println("测试回调次数");
			HuiFuData  data=payMent.cashAuditResult(resq);
			if (!data.getRespCode().equals("000")) {}
			
			
			try {
	            if (StringUtils.isNotBlank(data.getRecvOrdId())) {
	                PrintWriter out = resp.getWriter();
	                out.print(data.getRecvOrdId());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			//nav.redirectToLocation("/receive");
		}
	       
}
