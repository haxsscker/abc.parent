package com.autoserve.abc.web.module.screen.account.myInvest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditClean;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditReceived;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditTender;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;
/**
 * 
 * @author DS
 *
 * 2015年1月28日下午6:13:29
 */
public class InvestClaim {
	@Autowired
	private DeployConfigService deployConfigService;
	@Autowired
    private HttpSession session;
	@Autowired
	private InvestQueryService investQueryService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private PaymentPlanService paymentPlanService;
	@Resource
	private HttpServletRequest request;

    public void execute(Context context, ParameterParser params,Navigator nav) {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
    	}  
    	context.put("user", user);
    	String type=(String)params.getString("type");
    	//开始时间
    	String startDate=params.getString("startDate");
    	//结束时间
    	String endDate=params.getString("endDate");
    	//时间区间
    	String Recent=params.getString("Recent");
    	//项目名称
    	String loanno=params.getString("loanno");
        Integer currentPage = params.getInt("currentPage");
	    Integer pageSize = params.getInt("pageSize");
	    if(currentPage==0)
	    	currentPage=1;//默认情况
	    if(pageSize==0)
		     pageSize=10;//默认情况
	    PageCondition pageCondition=new PageCondition(currentPage,pageSize);
    	//查询条件
    	InvestSearchJDO investSearchJDO=new InvestSearchJDO();
    	investSearchJDO.setUserId(user.getUserId());
    	investSearchJDO.setLoanName(loanno);
    	
    	Calendar calendar = Calendar.getInstance();
    	if(Recent!=null&&!"".equals(Recent)){
	    	if("0".equals(Recent) ){
	    		calendar.add(Calendar.WEEK_OF_YEAR, -1);
			}else{
				calendar.add(Calendar.MONTH, 0 - Integer.valueOf(Recent));
			}
	    	startDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	    	endDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
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
    	//回款中
    	if(type!=null && "HKZ".equals(type)){   		
    		//未支付
    		PageResult<MyCreditReceived> pageResult=investQueryService.queryMyCreditReceived(investSearchJDO, pageCondition);
    		//分页处理
    		Pagebean<MyCreditReceived> pagebean=new Pagebean<MyCreditReceived>(currentPage,pageSize, pageResult.getData(),pageResult.getTotalCount());
	  		context.put("pagebean", pagebean); 
    	}
    	//投标中
    	else if(type!=null && "TBZ".equals(type)){
   		//待收益
    		PageResult<MyCreditTender>  pageResult=investQueryService.queryMyCreditTender(investSearchJDO, pageCondition);
    		//分页处理
    		Pagebean<MyCreditTender> pagebean=new Pagebean<MyCreditTender>(currentPage,pageSize, pageResult.getData(),pageResult.getTotalCount());
	  		context.put("pagebean", pagebean); 
    	}
    	//已结清
    	else if(type!=null && "YJQ".equals(type)){
    		PageResult<MyCreditClean> pageResult=investQueryService.queryMyCreditClean(investSearchJDO, pageCondition);
    		//分页处理
    		Pagebean<MyCreditClean> pagebean=new Pagebean<MyCreditClean>(currentPage,pageSize, pageResult.getData(),pageResult.getTotalCount());
	  		context.put("pagebean", pagebean); 	
    	}
    	context.put("Recent", Recent);
    	context.put("type", type);
    	context.put("startDate", startDate);
  		context.put("endDate", endDate);
  		context.put("loanno", loanno);
    }
}
