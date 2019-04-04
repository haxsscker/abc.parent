package com.autoserve.abc.web.module.screen.account.myLoan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.common.PageCondition.Order;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowClean;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowReceived;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowTender;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.Pagebean;

public class Myloan {
	@Autowired
    private HttpSession session;
	@Resource
	private LoanService loanService;
	@Resource
    private PaymentPlanService paymentPlanService;
    public void execute(Context context, ParameterParser params) {
    	User user = (User) session.getAttribute("user");
    	Integer currentPage = params.getInt("currentPage");
    	Integer LoanStateType = params.getInt("LoanStateType");
    	String startDate = params.getString("startDate");
    	String endDate = params.getString("endDate");
    	
    	String Recent = params.getString("Recent");
    	
    	//根据 Recent得到startDate和endDate
    	Calendar calendar = Calendar.getInstance();
    	if(Recent != null && !"".equals(Recent)) {
    		if("0".equals(Recent) ){
	    		calendar.add(Calendar.WEEK_OF_YEAR, -1);
			}else{
				calendar.add(Calendar.MONTH, 0 - Integer.valueOf(Recent));
			}
	    	startDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	    	endDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    	}
    	
    	
    	String loanName = params.getString("loanName");
    	//1.查询投标中的，loan的招标中
    	if(LoanStateType==0)LoanStateType=9;//默认情况
    	InvestSearchJDO searchDO;
    	int pageSize = 5;
    	if(currentPage==0)currentPage=1;//默认查询条件
    	if(LoanStateType==9)//招标中
    	{
    		searchDO =new InvestSearchJDO();
    		searchDO.setUserId(user.getUserId());
    		searchDO.setLoanState(2);
    		if(loanName!=null && !"".equals(loanName))
    		{
    			searchDO.setLoanName(loanName);
    			context.put("loanName", loanName);
    		}
    		
    		if(startDate != null) {
    			try {
    				searchDO.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
    				context.put("startDate", startDate);
				} catch (Exception e) {
					System.out.println("日期格式错误");
					e.printStackTrace();
				}
    		}
    		
    		if(endDate != null) {
    			try {
    				searchDO.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
    				context.put("endDate", endDate);
				} catch (Exception e) {
					System.out.println("日期格式错误");
					e.printStackTrace();
				}
    		}
    		
    		//按照申请时间倒序
    		PageCondition pageCondition =new PageCondition(currentPage,pageSize,"applyDate",Order.DESC);
    		PageResult<MyBorrowTender>  result = loanService.queryMyBorrowTender(searchDO, pageCondition);
    		List<MyBorrowTender> myBorrowTenderList = result.getData();
    		Pagebean<MyBorrowTender> pagebean =new Pagebean<MyBorrowTender>(currentPage, pageSize, myBorrowTenderList, result.getTotalCount());
    		context.put("pagebean", pagebean);
    		context.put("LoanStateType", LoanStateType);
    		context.put("Recent", Recent);
    		
    	}
    	//2.查询还款中，loan的还款中
    	if(LoanStateType==15)
    	{
    		searchDO =new InvestSearchJDO();
    		searchDO.setUserId(user.getUserId());
    		searchDO.setLoanState(1);
    		if(loanName!=null && !"".equals(loanName))
    		{
    			searchDO.setLoanName(loanName);
    			context.put("loanName", loanName);
    		}
    		if(startDate != null) {
    			try {
    				searchDO.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
    				context.put("startDate", startDate);
				} catch (Exception e) {
					System.out.println("日期格式错误");
					e.printStackTrace();
				}
    		}
    		
    		if(endDate != null) {
    			try {
    				searchDO.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
    				context.put("endDate", endDate);
				} catch (Exception e) {
					System.out.println("日期格式错误");
					e.printStackTrace();
				}
    		}
    		PageCondition pageCondition =new PageCondition(currentPage,pageSize,"applyDate",Order.DESC);
    		PageResult<MyBorrowReceived>  result = loanService.queryMyBorrowReceived(searchDO, pageCondition);
    		List<MyBorrowReceived> myBorrowReceivedList = result.getData();
    		Pagebean<MyBorrowReceived> pagebean =new Pagebean<MyBorrowReceived>(currentPage, pageSize, myBorrowReceivedList, result.getTotalCount());
    		context.put("pagebean", pagebean);
    		context.put("LoanStateType", LoanStateType);
    		context.put("Recent", Recent);
    		
    	}
    	//3.查询已结清的，loan的已结清
    	if(LoanStateType==16)
    	{
    		searchDO =new InvestSearchJDO();
    		searchDO.setUserId(user.getUserId());
    		searchDO.setLoanState(3);
    		if(loanName!=null && !"".equals(loanName))
    		{
    			searchDO.setLoanName(loanName);
    			context.put("loanName", loanName);
    		}
    		if(startDate != null) {
    			try {
    				searchDO.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
    				context.put("startDate", startDate);
				} catch (Exception e) {
					System.out.println("日期格式错误");
					e.printStackTrace();
				}
    		}
    		
    		if(endDate != null) {
    			try {
    				searchDO.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
    				context.put("endDate", endDate);
				} catch (Exception e) {
					System.out.println("日期格式错误");
					e.printStackTrace();
				}
    		}
    		PageCondition pageCondition =new PageCondition(currentPage,pageSize,"applyDate",Order.DESC);
    		PageResult<MyBorrowClean>   result = loanService.queryMyBorrowClean(searchDO, pageCondition);
    		List<MyBorrowClean> myBorrowCleanlList = result.getData();
    		Pagebean<MyBorrowClean> pagebean =new Pagebean<MyBorrowClean>(currentPage, pageSize, myBorrowCleanlList, result.getTotalCount());
    		context.put("pagebean", pagebean);
    		context.put("LoanStateType", LoanStateType);
    		context.put("Recent", Recent);
    		
    	}
    	
    }
}
