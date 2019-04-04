package com.autoserve.abc.web.module.screen.account.myAccount;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.util.CollectionUtil;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.Pagebean;

public class CashSerial {
	@Autowired
    private HttpSession session;
	@Autowired
    private ToCashService tocashservice;
	@Autowired
    private RechargeService rechargeservice;
	@Resource
    private DealRecordService dealrecordservice;
	@Resource
    private AccountInfoService  accountinfoservice;
	@Resource
    private DoubleDryService doubleDryService;

    public void execute(Context context, ParameterParser params, Navigator nav) {
    	User user=(User)session.getAttribute("user");
    	String period = params.getString("period");
    	String cashType=params.getString("cashType");
    	String startDate=params.getString("startDate");
    	String endDate=params.getString("endDate");
    	String queryTyp=params.getString("queryTyp");
    	String two_period=params.getString("two_period");
    	UserIdentity userIdentity = new UserIdentity();
    	if(user.getUserType()==null||user.getUserType()==UserType.PERSONAL){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());
    	userIdentity.setUserId(user.getUserId());
    	PlainResult<Account> account =  accountinfoservice.queryByUserId(userIdentity) ;
    	if(!account.isSuccess()){
    		nav.redirectToLocation("/account/myAccount/bindAccount");
    	}
    	
    	
    	int currentPage = params.getInt("currentPage");
    	if(currentPage==0)currentPage=1;
    	int pageSize=10;
    	PageCondition pageCondition = new PageCondition(currentPage,pageSize);
    	
    	Calendar calendar = Calendar.getInstance();
    	if(period!=null&&!"".equals(period)){
	    	if("0".equals(period) ){
	    		calendar.add(Calendar.WEEK_OF_YEAR, -1);
			}else{
				calendar.add(Calendar.MONTH, 0 - Integer.valueOf(period));
			}
	    	startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	    	endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    	}
    	
    	if(cashType.equals("SZMX")){
    		DealRecordDO dealrecorddo = new DealRecordDO();
    		dealrecorddo.setDrPayAccount(account.getData().getAccountNo());
    		PageResult<DealRecordDO> result = dealrecordservice.queryDealByParams(dealrecorddo, pageCondition, startDate, endDate);
    		for(DealRecordDO dealRecordDO:result.getData()){
    			/*if(dealRecordDO.getDrDetailType()==11){  //平台手续费
    				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo());
    			}else if(dealRecordDO.getDrDetailType()==12){  //担保服务费
    				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo());
    			}else if(dealRecordDO.getDrDetailType()==14){ //转让手续费
    				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo());
    			}*/
    			//dealRecordDO.createDrTypeStr(account.getData().getAccountNo());
    			dealRecordDO.newCreateDrTypeStr();
    		}
    		Pagebean<DealRecordDO>  pagebean = new Pagebean<DealRecordDO>(currentPage,pageSize,result.getData(),result.getTotalCount());
    		
    		context.put("payAccount", account.getData().getAccountNo());
    		context.put("pagebean", pagebean);
    	}
    	 if(cashType.equals("CZJL")){
    		 RechargeRecordDO rechargerecorddo = new RechargeRecordDO();
    		 rechargerecorddo.setRechargeUserId(user.getUserId());
    		 //rechargerecorddo.setRechargeState(1);   //成功
    		 PageResult<RechargeRecordDO> result = rechargeservice.queryRechargeRecordByparam(rechargerecorddo, pageCondition, startDate, endDate);
    		 Pagebean<RechargeRecordDO>  pagebean = new Pagebean<RechargeRecordDO>(currentPage,pageSize,result.getData(),result.getTotalCount());
    		 context.put("pagebean", pagebean);
    	 }
    	 if(cashType.equals("XXCZJL")){//线下充值记录
    		 Map<String,String> map = new HashMap<String,String>();
    		 map.put("AccountNo", account.getData().getAccountNo());
    		 map.put("StartDate", startDate);
    		 map.put("EndDate", endDate);
    		 map.put("QueryTyp", StringUtils.isNotEmpty(queryTyp)?queryTyp:"2");//1历史记录查询 2当前记录查询
    		 map.put("PageNo", String.valueOf(currentPage));
    		 Map<String, Object> resultMap = doubleDryService.queryChargeDetail(map);
    		 Pagebean<RechargeRecordDO>  pagebean = null;
    		 if(CollectionUtils.isNotEmpty((Collection) resultMap.get("recordList"))){
    			pagebean = new Pagebean<RechargeRecordDO>(currentPage,10,(List <RechargeRecordDO>)resultMap.get("recordList")
    					 ,Integer.valueOf(resultMap.get("recordCount").toString()));
    		 }else{
    			 pagebean = new Pagebean<RechargeRecordDO>(currentPage,10,null,0);
    		 }
    		 context.put("pagebean", pagebean);
    		 Date date = new Date();//获取当前时间
    		 Calendar calendar1 = Calendar.getInstance();
    		 calendar1.setTime(date);    
    		 calendar1.add(Calendar.DATE, -2);//当前时间减去2天    
    		 Date xxczEndDate=calendar1.getTime();
    		 context.put("two_period", two_period);
    		 context.put("xxczEndDate", xxczEndDate);
    		 context.put("queryTyp", queryTyp);
    		 context.put("pagebean", pagebean);
    	 }
    	 if(cashType.equals("TXJL")){
    		 TocashRecordDO tocashrecorddo = new TocashRecordDO();
    		 tocashrecorddo.setTocashUserId(user.getUserId());
    		// tocashrecorddo.setTocashState(1);  //提现成功
    		 PageResult<TocashRecordDO> result = tocashservice.queryListByUserId(tocashrecorddo, pageCondition, startDate, endDate);
    		 Pagebean<TocashRecordDO>  pagebean = new Pagebean<TocashRecordDO>(currentPage,pageSize,result.getData(),result.getTotalCount());
    		 context.put("pagebean", pagebean);
    	 }
    	context.put("period", period);
    	context.put("cashType", cashType);
    	context.put("startDate", startDate);
    	context.put("endDate", endDate);
    }
}
