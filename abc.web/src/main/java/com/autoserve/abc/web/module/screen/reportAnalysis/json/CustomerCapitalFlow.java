package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;


public class CustomerCapitalFlow {
	@Resource
	private DealRecordService dealrecordservice;
	private static Logger logger = LoggerFactory.getLogger(CustomerCapitalFlow.class);
	public JsonPageVO<DealRecordDO> execute(Context context,@Param("page") int page, @Param("rows") int rows, ParameterParser params){
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
		PageResult<DealRecordDO> result = dealrecordservice.queryDealZzlsByParams(dealrecordo,	new PageCondition(page,rows));
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
		}
		return ResultMapper.toPageVO(result);
		
	}
}