package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.SimpleDateFormat;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InvestorsReportDO;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;

public class TransferInvestorsReportView {
	private static final Logger logger = LoggerFactory
			.getLogger(TransferInvestorsReportView.class);
	@Resource
	private InvestQueryService investQueryService;

	public JsonPageVO<InvestorsReportDO> execute(ParameterParser params) {
		
		JsonPageVO<InvestorsReportDO> resultVO = new JsonPageVO<InvestorsReportDO>();
		Integer rows = params.getInt("rows");
		Integer page = params.getInt("page");
		InvestorsReportDO investorsReportDo = new InvestorsReportDO();
		
		String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
	        try {
	            JSONObject searchFormJson = JSON.parseObject(searchForm);
	            JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
	
	            for (Object item : itemsArray) {
	                JSONObject itemJson = JSON.parseObject(String.valueOf(item));
	                String field = String.valueOf(itemJson.get("Field"));
	                String value = String.valueOf(itemJson.get("Value"));
	
	                // 注册开始时间
	                if ("startInvestDate".equals(field)) {
	                	investorsReportDo.setStartInvestDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
	                }// 注册结束时间
	                else if ("endInvestDate".equals(field)) {
	                	investorsReportDo.setEndInvestDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
	                }
	            }
	        } catch (Exception e) {
	            logger.error("项目投资人项目明细－搜索查询 查询参数解析出错", e);
	        }
        }
		
		PageCondition pageCondition = new PageCondition(page, rows);
		PageResult<InvestorsReportDO> result = investQueryService.queryTransferInvestorsReport(investorsReportDo,pageCondition);
		
		resultVO.setTotal(result.getTotalCount());
		resultVO.setRows(result.getData());
		return resultVO;
	}
}
