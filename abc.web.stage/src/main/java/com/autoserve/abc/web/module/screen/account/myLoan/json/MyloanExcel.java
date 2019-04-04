package com.autoserve.abc.web.module.screen.account.myLoan.json;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowClean;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowReceived;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowTender;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanType;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.util.NumberUtil;
/**
 * 
 * @author DS
 *
 * 2015下午1:17:10
 */
public class MyloanExcel {
	@Autowired
    private HttpSession session;
	@Resource
	private LoanService loanService;
	@Resource
    private PaymentPlanService paymentPlanService;
	@Resource
	private HttpServletResponse response;
    public void execute(Context context, ParameterParser params) {
    	User user = (User) session.getAttribute("user");
    	Integer LoanStateType = params.getInt("LoanStateType");
    	String startDate = params.getString("startDate");
    	String endDate = params.getString("endDate");
    	int Recent = params.getInt("Recent");
    	String loanName = params.getString("loanName");
    	//1.查询投标中的，loan的招标中
    	if(LoanStateType==0)LoanStateType=9;//默认情况
    	InvestSearchJDO searchDO;
    	if(LoanStateType==9)//招标中
    	{
    		searchDO =new InvestSearchJDO();
    		searchDO.setUserId(user.getUserId());
    		searchDO.setLoanState(2);
    		searchDO.setRecent(Recent);
    		if(loanName!=null && !"".equals(loanName))
    		{
    			searchDO.setLoanName(loanName);
    			context.put("loanName", loanName);
    		}
    		if(startDate!=null && endDate!=null)
    		{
    			try
				{
    				searchDO.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
    				searchDO.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
    				context.put("startDate", startDate);
    				context.put("endDate", endDate);
				} catch (ParseException e)
				{
					System.out.println("日期格式错误");
					e.printStackTrace();
				}
    		}
    		MyBorrowTender(searchDO);
    	}
    	//2.查询还款中，loan的还款中
    	if(LoanStateType==15)
    	{
    		searchDO =new InvestSearchJDO();
    		searchDO.setUserId(user.getUserId());
    		searchDO.setLoanState(1);
    		searchDO.setRecent(Recent);
    		if(loanName!=null && !"".equals(loanName))
    		{
    			searchDO.setLoanName(loanName);
    			context.put("loanName", loanName);
    		}
    		if(startDate!=null && endDate!=null)
    		{
    			try
				{
    				searchDO.setStartDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate));
    				searchDO.setEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate));
				} catch (ParseException e)
				{
					System.out.println("日期格式错误");
					e.printStackTrace();
				}
    		}
    		MyBorrowReceived(searchDO);
    	}
    	//3.查询已结清的，loan的已结清
    	if(LoanStateType==16)
    	{
    		searchDO =new InvestSearchJDO();
    		searchDO.setUserId(user.getUserId());
    		searchDO.setLoanState(3);
    		searchDO.setRecent(Recent);
    		if(loanName!=null && !"".equals(loanName))
    		{
    			searchDO.setLoanName(loanName);
    			context.put("loanName", loanName);
    		}
    		if(startDate!=null && endDate!=null)
    		{
    			try
				{
    				searchDO.setStartDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate));
    				searchDO.setEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate));
				} catch (ParseException e)
				{
					System.out.println("日期格式错误");
					e.printStackTrace();
				}
    		}
    		MyBorrowClean(searchDO);
    	}
    	
    }
    
    //投标中
    public void MyBorrowTender(InvestSearchJDO searchDO){
    	PageResult<MyBorrowTender>  pageResult = loanService.queryMyBorrowTender(searchDO, 
    			new PageCondition(0,loanService.queryMyBorrowTender(searchDO,new PageCondition()).getTotalCount()));
    	List<String> fieldName=Arrays.asList(new String[]{"项目名称","年利率","项目金额","期限","申请时间","投标成交时间","投资金额","投资进度","到期日","状态"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(MyBorrowTender myBorrowTender:pageResult.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(myBorrowTender.getLoanNo());
			temp.add(myBorrowTender.getLoanRate().toString()+"%");
			temp.add(myBorrowTender.getLoanMoney().toString());
			if(myBorrowTender.getLoanPeriodType()==1){
				temp.add(myBorrowTender.getLoanPeriod()+LoanPeriodUnit.YEAR.getPrompt());
			}else if(myBorrowTender.getLoanPeriodType()==2){
				temp.add(myBorrowTender.getLoanPeriod()+LoanPeriodUnit.MONTH.getPrompt());
			}else if(myBorrowTender.getLoanPeriodType()==3){
				temp.add(myBorrowTender.getLoanPeriod()+LoanPeriodUnit.DAY.getPrompt());
			}
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myBorrowTender.getApplyDate()));
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myBorrowTender.getSuccessDate()));
			if(myBorrowTender.getInvestMoney() == null) {
				myBorrowTender.setInvestMoney(new BigDecimal("0"));
			}
			temp.add(myBorrowTender.getInvestMoney().toString());
			BigDecimal percent = Arith.calcPercent(myBorrowTender.getInvestMoney(), myBorrowTender.getLoanMoney(), LoanState.valueOf(myBorrowTender.getState()));
			temp.add(percent.intValue()+"%");
			temp.add(new SimpleDateFormat("yyyy-MM-dd").format(myBorrowTender.getEndDate()));
			temp.add(LoanState.valueOf(myBorrowTender.getState()).prompt);
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"我的借款-投标中.xls");
    }
   
    //还款中
    public void MyBorrowReceived(InvestSearchJDO searchDO){
    	PageResult<MyBorrowReceived>  pageResult = loanService.queryMyBorrowReceived(searchDO, 
    			new PageCondition(0,loanService.queryMyBorrowReceived(searchDO,new PageCondition()).getTotalCount()));
    	List<String> fieldName=Arrays.asList(new String[]{"项目名称","年利率","借款金额","借款日期","下次还款日","期数","应还金额","到期日","状态"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(MyBorrowReceived myBorrowReceived:pageResult.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(myBorrowReceived.getLoanNo());
			temp.add(myBorrowReceived.getLoanRate().toString()+"%");
			temp.add(myBorrowReceived.getLoanMoney().toString());
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myBorrowReceived.getLoantime()));
			temp.add(new SimpleDateFormat("yyyy-MM-dd").format(myBorrowReceived.getPaytime()));
			temp.add(myBorrowReceived.getLoanPeriod()+"期");
			temp.add(myBorrowReceived.getPayMoney().toString());
			temp.add(new SimpleDateFormat("yyyy-MM-dd").format(myBorrowReceived.getEndDate()));
			temp.add("还款中");
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"我的借款-还款中.xls");
    }
    
    //已结清
    public void MyBorrowClean(InvestSearchJDO searchDO){
    	PageResult<MyBorrowClean>   pageResult = loanService.queryMyBorrowClean(searchDO,
    			new PageCondition(0,loanService.queryMyBorrowClean(searchDO,new PageCondition()).getTotalCount()));
    	List<String> fieldName=Arrays.asList(new String[]{"项目名称","年利率","项目金额","还款本金","还款利息","还款罚息","结清日期"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(MyBorrowClean myBorrowClean:pageResult.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(myBorrowClean.getLoanNo());
			temp.add(myBorrowClean.getLoanRate().toString()+"%");
			temp.add(myBorrowClean.getLoanMoney().toString());
			temp.add(myBorrowClean.getPayCapital().toString());
			temp.add(myBorrowClean.getPayInterest().toString());
			temp.add(myBorrowClean.getPayFine().toString());
			temp.add(new SimpleDateFormat("yyyy-MM-dd").format(myBorrowClean.getEndDate()));
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"我的借款-已结清.xls");
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
