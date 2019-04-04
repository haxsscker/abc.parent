package com.autoserve.abc.web.module.screen.account.myAccount.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;
/**
 * 
 * @author DS
 *
 * 2015上午10:13:43
 */
public class CashSerialExcel {
	@Autowired
	private DeployConfigService deployConfigService;
	@Autowired
    private HttpSession session;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
    private AccountInfoService  accountinfoservice;
	@Resource
    private DealRecordService dealrecordservice;
	@Autowired
    private RechargeService rechargeservice;
	@Autowired
    private ToCashService tocashservice;
	@Resource
    private DoubleDryService doubleDryService;
	
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
    	}  
    	context.put("user", user);
    	UserIdentity userIdentity = new UserIdentity();
    	if(user.getUserType()==null||user.getUserType()==UserType.PERSONAL){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());
    	userIdentity.setUserId(user.getUserId());
    	PlainResult<Account> account =  accountinfoservice.queryByUserId(userIdentity) ;
    	
    	String cashType=(String)params.getString("cashType");
    	//开始时间
    	String startDate=params.getString("startDate");
    	//结束时间
    	String endDate=params.getString("endDate");
    	//查询方式
    	String queryTyp=params.getString("queryTyp");
    	//页码
    	String currentPage=params.getString("currentPage");
    	if(cashType!=null && "SZMX".equals(cashType)){
    		DealRecordDO dealrecorddo = new DealRecordDO();
    		dealrecorddo.setDrPayAccount(account.getData().getAccountNo());
    		SZMXRecord(dealrecorddo,account.getData().getAccountNo(), startDate, endDate);
    	}
    	if(cashType!=null && "CZJL".equals(cashType)){
    		RechargeRecordDO rechargerecorddo = new RechargeRecordDO();
   		 	rechargerecorddo.setRechargeUserId(user.getUserId());
   		 	rechargerecorddo.setRechargeState(1);   //成功
    		CZJLRecord(rechargerecorddo,startDate,endDate);
    	}
    	 if(cashType.equals("XXCZJL")){//线下充值记录
    		 queryTyp=StringUtils.isNotEmpty(queryTyp)?queryTyp:"2";//1历史记录查询 2当前记录查询
    		 XXCZJLRecord(account.getData().getAccountNo(),startDate,endDate,queryTyp,currentPage);
    	 }
    	if(cashType!=null && "TXJL".equals(cashType)){
    		TocashRecordDO tocashrecorddo = new TocashRecordDO();
   		 	tocashrecorddo.setTocashUserId(user.getUserId());
   		 	tocashrecorddo.setTocashState(1);  //提现成功
    		TXJLRecord(tocashrecorddo,startDate,endDate);
    	}
    }
    
    //收支明细
    public void SZMXRecord(DealRecordDO dealrecorddo,String accountNo, String startDate, String endDate){
		PageResult<DealRecordDO> result = dealrecordservice.queryDealByParams(dealrecorddo,
				new PageCondition(0,dealrecordservice.queryDealByParams(dealrecorddo,new PageCondition(), startDate, endDate).getTotalCount()), startDate, endDate);		
		/*for(DealRecordDO dealRecordDO:result.getData()){
			if(dealRecordDO.getDrDetailType()==11){  //平台手续费
				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo());
			}else if(dealRecordDO.getDrDetailType()==12){  //担保服务费
				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo());
			}else if(dealRecordDO.getDrDetailType()==14){ //转让手续费
				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo());
			}
		}*/		
		List<String> fieldName=Arrays.asList(new String[]{"交易日期","交易订单号","交易金额", "交易对方", "交易类型", "状态"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(DealRecordDO dealrecord:result.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dealrecord.getDrOperateDate()));
			//交易订单号
			temp.add(dealrecord.getDrInnerSeqNo());
			//交易金额
			switch(dealrecord.getDrType()){
			case 0://投资冻结
				temp.add("-"+dealrecord.getDrMoneyAmount().toString());
				break;
			case 2:
			case 3:
				if(dealrecord.getDrPayAccount().equals(accountNo)){
					temp.add("-"+dealrecord.getDrMoneyAmount().toString());
				}else if(dealrecord.getDrReceiveAccount().equals(accountNo)){
					temp.add(dealrecord.getDrMoneyAmount().toString());
				}
				break;
			case 6:
				temp.add(dealrecord.getDrMoneyAmount().toString());
				break;
			case 7:
				temp.add("-"+dealrecord.getDrMoneyAmount().toString());
				break;
			case 8:
				temp.add(dealrecord.getDrMoneyAmount().toString());
				break;
			case 10://红包返还
				temp.add(dealrecord.getDrMoneyAmount().toString());
				break;
			default:temp.add("-");
			}
			//交易对方
			if((dealrecord.getDrType()==2 && accountNo.equals(dealrecord.getDrReceiveAccount())) ||
					(dealrecord.getDrType()==3 && accountNo.equals(dealrecord.getDrReceiveAccount()))
					|| (dealrecord.getDrType()==10 && accountNo.equals(dealrecord.getDrReceiveAccount()))){
				temp.add(dealrecord.getDrPayAccount());
			}else if(dealrecord.getDrType()==9){ // 自主转账
	         	if(accountNo.equals(dealrecord.getDrPayAccount())){
	         		temp.add(dealrecord.getDrReceiveAccount());
	         	}else{
	         		temp.add(dealrecord.getDrPayAccount());
	         	}
			}else{
				temp.add(dealrecord.getDrReceiveAccount());
			}	
			//类型
			dealrecord.createDrTypeStr(accountNo);
			temp.add(dealrecord.getDrTypeStr());
			switch(dealrecord.getDrState()){
			case 0:temp.add("进行中");
			break;
			case 1:temp.add("成功");
			break;
			default:temp.add("失败");
			}
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"收支明细.xls");
    }
    //充值记录
    public void CZJLRecord(RechargeRecordDO rechargerecorddo,String startDate,String endDate){
    	PageResult<RechargeRecordDO> result = rechargeservice.queryRechargeRecordByparam(rechargerecorddo, 
    			new PageCondition(0,rechargeservice.queryRechargeRecordByparam(rechargerecorddo, new PageCondition(), startDate, endDate).getTotalCount()), startDate, endDate);
    	List<String> fieldName=Arrays.asList(new String[]{"交易日期","交易订单号","充值金额","交易类型","状态"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(RechargeRecordDO rechargerecord:result.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rechargerecord.getRechargeDate()));
			temp.add(rechargerecord.getRechargeSeqNo());
			temp.add(rechargerecord.getRechargeAmount().toString());
			temp.add("充值");
			switch(rechargerecord.getRechargeState()){
			case 0:temp.add("进行中");
			break;
			case 1:temp.add("成功");
			break;
			default:temp.add("失败");
			}
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"充值记录.xls");
    }
    //线下充值记录
    public void XXCZJLRecord(String accountNo,String startDate,String endDate,String queryTyp,String currentPage){
    	List<String> fieldName=Arrays.asList(new String[]{"交易日期","交易订单号","充值金额","充值手续费","状态"});
    	List<List<String>> fieldData = new ArrayList<List<String>>();
    	Map<String,String> map = new HashMap<String,String>();
		 map.put("AccountNo", accountNo);
		 map.put("StartDate", startDate);
		 map.put("EndDate", endDate);
		 map.put("QueryTyp", queryTyp);//1历史记录查询 2当前记录查询
		 map.put("PageNo", currentPage);
		 Map<String, Object> resultMap = doubleDryService.queryChargeDetail(map);
		 if(CollectionUtils.isNotEmpty((Collection) resultMap.get("recordList"))){
 			List<RechargeRecordDO> list=(List<RechargeRecordDO>)resultMap.get("recordList");
 			for(RechargeRecordDO rechargerecord:list){
 				List<String> temp = new ArrayList<String>();
 				temp.add(new SimpleDateFormat("yyyy-MM-dd").format(rechargerecord.getRechargeDate()));
 				temp.add(rechargerecord.getRechargeSeqNo());
 				temp.add(String.valueOf(rechargerecord.getRechargeAmount().doubleValue()));
 				temp.add(String.valueOf(rechargerecord.getRechargeFeeAmt().doubleValue()));
 				temp.add(rechargerecord.getRechargeStateDes());
 				fieldData.add(temp);
 			}
 		 }
		ExportExcel(fieldName,fieldData,"线下充值记录.xls");
    }
    //提现记录
    public void TXJLRecord(TocashRecordDO tocashrecorddo,String startDate,String endDate){
    	PageResult<TocashRecordDO> result = tocashservice.queryListByUserId(tocashrecorddo,
    			new PageCondition(0,tocashservice.queryListByUserId(tocashrecorddo, new PageCondition(), startDate, endDate).getTotalCount()), startDate, endDate);
    	List<String> fieldName=Arrays.asList(new String[]{"交易日期","交易订单号","提现金额","交易类型","状态"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(TocashRecordDO tocashrecord:result.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tocashrecord.getTocashDate()));
			temp.add(tocashrecord.getTocashSeqNo());
			temp.add(tocashrecord.getTocashAmount().toString());
			temp.add("提现");
			switch(tocashrecord.getTocashState()){
			case 0:temp.add("进行中");
			break;
			case 1:temp.add("成功");
			break;
			default:temp.add("失败");
			}
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"提现记录.xls");
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
