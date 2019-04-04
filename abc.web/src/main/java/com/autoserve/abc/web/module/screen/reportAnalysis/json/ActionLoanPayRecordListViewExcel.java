package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.convert.AccountConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.DateUtil;

public class ActionLoanPayRecordListViewExcel {
	
	@Resource
    private DealRecordService dealRecordService;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
    private AccountInfoService accountInfoService;
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	Integer empId = LoginUserUtil.getEmpId();
		// 担保机构账户
        AccountInfoDO accountDo =  accountInfoService.queryByAccountMark(empId,UserType.PARTNER.type);
        Account guarAccount = AccountConverter.toUserAccount(accountDo);
		DealRecordDO dealRecord = new DealRecordDO();
		dealRecord.setDrPayAccount(guarAccount.getAccountNo());
		dealRecord.setDrReceiveAccount(guarAccount.getAccountNo());
		
    	SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT);
        Date startTradeDate = params.getDate("startTradeDate", sdf);
        Date endTradeDate = params.getDate("endTradeDate", sdf);
    	if(null != startTradeDate){
    		dealRecord.setStartTradeDate(startTradeDate);
        }
        if(null != endTradeDate){
        	dealRecord.setEndTradeDate(endTradeDate);
        }
    	
    	PageResult<DealRecordDO> result = dealRecordService.queryGuarRecord(dealRecord, new PageCondition(0, dealRecordService.queryCountRecordReport(dealRecord)));
    	
		List<String> fieldName=Arrays.asList(new String[]{"交易类型","付款账号","收款账号","交易金额","交易流水号","交易状态","交易时间"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(DealRecordDO deal:result.getData()){
			List<String> temp = new ArrayList<String>();
			switch(deal.getDrType()){
			case 3:
				if(deal.getDrDetailType()==DealDetailType.PAYBACK_INTEREST.getType()){
					temp.add("还款利息");
				}else if(deal.getDrDetailType()==DealDetailType.PAYBACK_CAPITAL.getType()){
					temp.add("还款本金");
				}else if(deal.getDrDetailType()==DealDetailType.PAYBACK_OVERDUE_FINE.getType()){
					temp.add("还款罚息");
				}else if(deal.getDrDetailType()==DealDetailType.PAYBACK_BREACH_FINE.getType()){
					temp.add("还款罚金");
				}else if(deal.getDrDetailType()==DealDetailType.PLA_SERVE_FEE.getType()){
					temp.add("平台服务费");
				}
			break;
			default:temp.add("-");
			}
			temp.add(deal.getDrPayAccount());
			temp.add(deal.getDrReceiveAccount());
			temp.add(deal.getDrMoneyAmountStr());
			temp.add(deal.getDrInnerSeqNo());
			if("0".equals(deal.getDrStateStr())){
				temp.add("等待响应");
			}else if("1".equals(deal.getDrStateStr())){
				temp.add("成功");
			}else if("2".equals(deal.getDrStateStr())){
				temp.add("失败");
			}else{
				temp.add("-");
			}
			temp.add(deal.getDrOperateDateStr());
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"机构用户交易明细表.xls");

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
