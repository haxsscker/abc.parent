package com.autoserve.abc.web.module.screen.account.myAccount.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.DeployConfigService;
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
	
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
    	}  
    	context.put("user", user);
    	UserIdentity userIdentity = new UserIdentity();
    	if(user.getUserType()==null||"".equals(user.getUserType())||user.getUserType()==UserType.PERSONAL){
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

    	if(cashType!=null && "SZMX".equals(cashType)){
    		DealRecordDO dealrecorddo = new DealRecordDO();
    		dealrecorddo.setDrPayAccount(account.getData().getAccountMark());
    		SZMXRecord(dealrecorddo,account.getData().getAccountMark(), startDate, endDate);
    	}
    	if(cashType!=null && "CZJL".equals(cashType)){
    		RechargeRecordDO rechargerecorddo = new RechargeRecordDO();
   		 	rechargerecorddo.setRechargeUserId(user.getUserId());
   		 	rechargerecorddo.setRechargeState(1);   //成功
    		CZJLRecord(rechargerecorddo,startDate,endDate);
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
    	//String platformAcount = SystemGetPropeties.getStrString("MerCustId");
		PageResult<DealRecordDO> result = dealrecordservice.queryDealByParams(dealrecorddo,
				new PageCondition(0,dealrecordservice.queryDealByParams(dealrecorddo,new PageCondition(), startDate, endDate).getTotalCount()), startDate, endDate);		
//		for(DealRecordDO dealRecordDO:result.getData()){
//			if(dealRecordDO.getDrDetailType()==11){  //平台手续费
//				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()+"333");
//			}else if(dealRecordDO.getDrDetailType()==12){  //担保服务费
//				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()+"111");
//			}else if(dealRecordDO.getDrDetailType()==14){ //转让手续费
//				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()+"222");
//			}
//		}		
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
			default:temp.add("-");
			}
			//交易对方
			if((dealrecord.getDrType()==2 && accountNo.equals(dealrecord.getDrReceiveAccount())) ||
					(dealrecord.getDrType()==3 && accountNo.equals(dealrecord.getDrReceiveAccount()))
					|| (dealrecord.getDrType()==10 && accountNo.equals(dealrecord.getDrReceiveAccount()))){
				temp.add(dealrecord.getDrPayAccount());
			}else{
				temp.add(dealrecord.getDrReceiveAccount());
			}	
			//类型
			switch(dealrecord.getDrType()){
			case 0:temp.add("投资");
			break;
			case 1:temp.add("撤投");
			break;
			case 2:
				if(dealrecord.getDrPayAccount().equals(accountNo) && !dealrecord.getDrReceiveAccount().startsWith("p")){
					temp.add("投资");
				}else if(dealrecord.getDrPayAccount().equals(accountNo) && dealrecord.getDrReceiveAccount().startsWith("p")){
				   temp.add("手续费");
				}else if(dealrecord.getDrReceiveAccount().equals(accountNo)){
					temp.add("借款");
				}
				break;
			case 3:
				if(dealrecord.getDrPayAccount().equals(accountNo)){
					temp.add("还款");
				}else if(dealrecord.getDrReceiveAccount().equals(accountNo)){
					temp.add("收款");
				}				
				break;
			case 4:temp.add("充值");
			break;
			case 5:temp.add("提现");
			break;
			case 6:temp.add("退款");
			break;
			case 7:temp.add("收购");
			break;
			case 8:temp.add("流标");
			break;
			default:temp.add("-");
			}
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
