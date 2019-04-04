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
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.RedReportDO;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.util.DateUtil;

public class PlateAccountViewExcel {
	
	@Resource
    private DealRecordService dealRecordService;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	DealRecordDO dealRecord = new DealRecordDO();
    	SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT);
        Date startTradeDate = params.getDate("startTradeDate", sdf);
        Date endTradeDate = params.getDate("endTradeDate", sdf);
    	if(null != startTradeDate){
    		dealRecord.setStartTradeDate(startTradeDate);
        }
        if(null != endTradeDate){
        	dealRecord.setEndTradeDate(endTradeDate);
        }
    	
    	PageResult<DealRecordDO> result = dealRecordService.queryRecord(dealRecord, new PageCondition(0, dealRecordService.queryCountRecordReport(dealRecord)));
    	
		List<String> fieldName=Arrays.asList(new String[]{"交易类型","付款账号","收款账号","交易金额","交易流水号","交易状态","交易时间"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(DealRecordDO deal:result.getData()){
			List<String> temp = new ArrayList<String>();
			switch(deal.getDrDetailType()){
			case 3:temp.add("还款利息");
			break;
			case 4:temp.add("超期罚金");
			break;
			case 5:temp.add("平台服务费");
			break;
			case 6:temp.add("充值金额");
			break;
			case 7:temp.add("提现金额");
			break;
			case 8:temp.add("退款金额");
			break;
			case 9:temp.add("划转金额");
			break;
			case 11:temp.add("平台手续费");
			break;
			case 12:temp.add("担保服务费");
			break;
			case 13:temp.add("转让金额");
			break;
			case 14:temp.add("转让手续费");
			break;
			case 15:temp.add("收购金额");
			break;
			case 16:temp.add("红包金额");
			break;
			case 17:temp.add("流标金额");
			break;
			case 18:temp.add("流标退回金额");
			break;
			case 20:temp.add("违约罚金");
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
		ExportExcel(fieldName,fieldData,"平台资金管理明细表.xls");

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
