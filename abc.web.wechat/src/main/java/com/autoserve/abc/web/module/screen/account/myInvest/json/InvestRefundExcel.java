package com.autoserve.abc.web.module.screen.account.myInvest.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.helper.DeployConfigService;
/**
 * 
 * @author DS
 *
 * 2015下午8:05:36
 */
public class InvestRefundExcel {
	@Autowired
	private DeployConfigService deployConfigService;
	@Autowired
    private HttpSession session;
	@Autowired
	private IncomePlanService incomePlanService;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
    	}
	  //开始时间
    	String startDate=params.getString("startDate");
    	//结束时间
    	String endDate=params.getString("endDate");
    	//时间区间
    	String Recent=params.getString("Recent");
    	IncomePlan incomePlan=new IncomePlan();
    	incomePlan.setBeneficiary(user.getUserId());
    	InvestSearchJDO investSearchJDO=new InvestSearchJDO();
    	if(Recent!=null && !"".equals(Recent)){   		
    		investSearchJDO.setRecent(Integer.parseInt(Recent));
    		context.put("recent"+Recent, "riqi-current");
    	}else{
    		investSearchJDO.setRecent(null);
    	}
    	if(startDate!=null){
			try {
				Date start=new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
				investSearchJDO.setStartDate(start);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(endDate!=null){
			try {
				Date end=new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
				investSearchJDO.setEndDate(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		MyCreditReceived(incomePlan,investSearchJDO);
    }
    
    //回款计划
    public void MyCreditReceived(IncomePlan incomePlan,InvestSearchJDO investSearchJDO){
    	 PageResult<IncomePlan> pageResult=incomePlanService.queryIncomePlanList(incomePlan,investSearchJDO,
    			 new PageCondition(0,incomePlanService.queryIncomePlanList(incomePlan,investSearchJDO,new PageCondition()).getTotalCount()));	
		List<String> fieldName=Arrays.asList(new String[]{"时间","已回款","已回利息","已回本金","未回款","未回利息","未回本金","总额"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(IncomePlan incomePlan2:pageResult.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(new SimpleDateFormat("yyyy-MM-dd").format(incomePlan2.getPaytime()));
			temp.add(incomePlan2.getCollectTotal().toString());
			temp.add(incomePlan2.getCollectInterest().toString());
			temp.add(incomePlan2.getCollectCapital().toString());
			temp.add(incomePlan2.getPayTotalMoney().subtract(incomePlan2.getCollectTotal()).toString());
			temp.add(incomePlan2.getPayInterest().subtract(incomePlan2.getCollectInterest()).toString());
			temp.add(incomePlan2.getPayCapital().subtract(incomePlan2.getCollectCapital()).toString());
			temp.add(incomePlan2.getPayTotalMoney().toString());
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"回款计划.xls");
    }
    //导出
    public void ExportExcel(List<?> fieldName,List<?> fieldData,String name){
    	ExcelFileGenerator excelFileGenerator=new ExcelFileGenerator(fieldName, fieldData);
		try {
			response.setCharacterEncoding("gb2312");
			response.setHeader("Content-Disposition", "attachment;filename="+new String(name.getBytes("GB2312"),"iso8859-1"));
			response.setContentType("application/ynd.ms-excel;charset=UTF-8");
			excelFileGenerator.expordExcel(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
