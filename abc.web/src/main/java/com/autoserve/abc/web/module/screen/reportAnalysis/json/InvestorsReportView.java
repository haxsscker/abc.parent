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

public class InvestorsReportView {
	private static final Logger logger = LoggerFactory
			.getLogger(InvestorsReportView.class);
	@Resource
	private InvestQueryService investQueryService;

	public JsonPageVO<InvestorsReportDO> execute(ParameterParser params) {
		JsonPageVO<InvestorsReportDO> resultVO = new JsonPageVO<InvestorsReportDO>();
		Integer rows = params.getInt("rows");
		Integer page = params.getInt("page");
		PageCondition pageCondition = new PageCondition(page, rows);
		InvestorsReportDO investorsReportDO = new InvestorsReportDO();
		String loan_no = params.getString("loan_no");
		investorsReportDO.setLoan_no(loan_no);

		String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
	        try {
	            JSONObject searchFormJson = JSON.parseObject(searchForm);
	            JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
	
	            for (Object item : itemsArray) {
	                JSONObject itemJson = JSON.parseObject(String.valueOf(item));
	                String field = String.valueOf(itemJson.get("Field"));
	                String value = String.valueOf(itemJson.get("Value"));
	
	                // 项目名称
	                if ("loan_no".equals(field)) {
	                	investorsReportDO.setLoan_no(value);
	                }
	                // 注册开始时间
	                else if ("startInvestDate".equals(field)) {
	                	investorsReportDO.setStartInvestDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
	                }// 注册结束时间
	                else if ("endInvestDate".equals(field)) {
	                	investorsReportDO.setEndInvestDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
	                }
	            }
	        } catch (Exception e) {
	            logger.error("项目投资人项目明细－搜索查询 查询参数解析出错", e);
	        }
        }
		
		PageResult<InvestorsReportDO> result = investQueryService
				.queryInvestorsReport(investorsReportDO, pageCondition);
		
		
		resultVO.setTotal(result.getTotalCount());
		resultVO.setRows(result.getData());
		return resultVO;
	}
}
