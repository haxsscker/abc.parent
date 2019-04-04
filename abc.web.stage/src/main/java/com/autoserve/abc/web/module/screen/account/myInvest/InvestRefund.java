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
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;
/**
 * 
 * @author DS
 *
 * 2015年1月28日上午9:47:03
 */
public class InvestRefund {
	@Autowired
	private DeployConfigService deployConfigService;
	@Autowired
	private HttpSession session;
	@Autowired
	private IncomePlanService incomePlanService;
	@Autowired
	
	@Resource
	private HttpServletRequest request;
	public void execute(Context context, ParameterParser params,Navigator nav) {
		//登录URL
		User user=(User)session.getAttribute("user");
		if(user==null){
			nav.forwardTo(deployConfigService.getLoginUrl(request));
			return;
		}  
		Integer currentPage = params.getInt("currentPage");
		Integer pageSize = params.getInt("pageSize");
		//开始时间
		String startDate=params.getString("startDate");
		//结束时间
		String endDate=params.getString("endDate");
		//时间区间
		String Recent=params.getString("Recent");
		InvestSearchJDO investSearchJDO=new InvestSearchJDO();
		//移除被转让的收益计划记录
		investSearchJDO.setRemoveTransfer(true);
		IncomePlan incomePlan=new IncomePlan();
		incomePlan.setBeneficiary(user.getUserId());
		pageSize=5;//默认情况
		//edit by 夏同同  20160518 首次进入回款计划页面时，直接展示离当前日期最近的数据
		if(currentPage==0){
			if((startDate == null || "".equals(startDate)) && (endDate == null || "".equals(endDate))
					&& (Recent == null || "".equals(Recent))){
				investSearchJDO.setCurrentDate(new Date());
				//查询当前日期之前的数据
				int count = incomePlanService.queryCountListBeforeToday(incomePlan, investSearchJDO);
				currentPage = (int)(count/pageSize) + 1;
			}else{
				currentPage = 1;
			}
		}
			
		
		PageCondition pageCondition=new PageCondition(currentPage,pageSize);
		Calendar calendar = Calendar.getInstance();
		if(Recent!=null&&!"".equals(Recent)){
			if("0".equals(Recent) ){
				calendar.add(Calendar.WEEK_OF_YEAR, 1);
			}else{
				calendar.add(Calendar.MONTH,  Integer.valueOf(Recent));
			}
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			startDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
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
		PageResult<IncomePlan> pageResult=incomePlanService.queryIncomePlanToDayList(incomePlan, investSearchJDO,pageCondition);
		//分页处理
		Pagebean<IncomePlan> pagebean=new Pagebean<IncomePlan>(currentPage,pageSize, pageResult.getData(),pageResult.getTotalCount());
		context.put("Recent", Recent);
		context.put("pagebean", pagebean);
		context.put("startDate", startDate);
		context.put("endDate", endDate);
	}
}
