package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.module.screen.BaseController;
import com.autoserve.abc.web.util.DateUtil;

public class ExportCustomerCapitalFlow extends BaseController{
	@Resource
	private DealRecordService dealrecordservice;
	
	private static Logger logger = LoggerFactory.getLogger(ExportCustomerCapitalFlow.class);
	
	public void execute(ParameterParser params){
		DealRecordDO dealrecordo = new DealRecordDO();
		String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    if ("userName".equals(field)) {
                    	dealrecordo.setPayUserName(value);
                    }
                    if ("realName".equals(field)) {
                    	dealrecordo.setPayRealName(value);
                    }
                    if ("startTradeDate".equals(field)) {
                    	dealrecordo.setStartTradeDate(DateUtil.parseDate(value, DateUtil.DATE_TIME_FORMAT));
                    }
                    if ("endTradeDate".equals(field)) {
                    	dealrecordo.setEndTradeDate(DateUtil.parseDate(value, DateUtil.DATE_TIME_FORMAT));
                    }
                    //-2表示全部
                    if ("drState".equals(field) && !"-2".equals(value)) {
                    	dealrecordo.setDrState(Integer.valueOf(value));
                    }
                    if ("drType".equals(field) && !"-2".equals(value)) {
                    	dealrecordo.setDrType(Integer.valueOf(value));
                    }
                }
            } catch (Exception e) {
                logger.error("搜索查询 查询参数解析出错", e);
            }
        }
        PageResult<DealRecordDO> result = dealrecordservice.queryDealZzlsByParams(dealrecordo,	null);
		if(result.getData().size() > 0){
			for(DealRecordDO dealrecord:result.getData()){
				//交易日期
				if(null != dealrecord.getDrOperateDate()){
					dealrecord.setDrOperateDateStr(new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT).format(dealrecord.getDrOperateDate()));
				}else{
					dealrecord.setDrOperateDateStr("-");
				}
				//交易金额
				dealrecord.setDrMoneyAmountStr(dealrecord.getDrMoneyAmount().toString());
				//类型
				dealrecord.newCreateDrTypeStr();
				//状态
				if(null == dealrecord.getDrState()){
					dealrecord.setDrStateStr("未知");
				}else if(dealrecord.getDrState()==0){
					dealrecord.setDrStateStr("等待响应");
				}else if(dealrecord.getDrState()==1){
					dealrecord.setDrStateStr("成功");
				}else {
					dealrecord.setDrStateStr("失败");
				}
			}
			exportDealrecord(result.getData());
		}
	}
	
	//资金流水
    private void exportDealrecord(List<DealRecordDO> dealrecordoList){
		List<String> fieldName=Arrays.asList(new String[]{"付款方用户名 ","付款方姓名 ","付款方账号 ","收款方用户名","收款方姓名 ","收款方账号","交易日期","交易金额","交易订单号", "交易类型", "状态"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		List<String> temp = null;
		for(DealRecordDO dealrecord:dealrecordoList){
			temp = new ArrayList<String>();
			temp.add(dealrecord.getPayUserName());
			temp.add(dealrecord.getPayRealName());
			temp.add(dealrecord.getDrPayAccount());
			temp.add(dealrecord.getReceiveUserName());
			temp.add(dealrecord.getReceiveRealName());
			temp.add(dealrecord.getDrReceiveAccount());
			temp.add(dealrecord.getDrOperateDateStr());
			temp.add(dealrecord.getDrMoneyAmountStr());
			temp.add(dealrecord.getDrInnerSeqNo());	
			temp.add(dealrecord.getDrTypeStr());
			temp.add(dealrecord.getDrStateStr());
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"资金流水.xls");
    }
}
