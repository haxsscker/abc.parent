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
import com.autoserve.abc.dao.dataobject.LoanReportDO;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;

public class LoanReportView {
	private static final Logger logger = LoggerFactory
			.getLogger(LoanReportView.class);
	@Resource
	private LoanQueryService loanQueryService;

	public JsonPageVO<LoanReportDO> execute(ParameterParser params) {
		
        LoanReportDO loanReportDO = new LoanReportDO();
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
	        try {
	            JSONObject searchFormJson = JSON.parseObject(searchForm);
	            JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
	
	            for (Object item : itemsArray) {
	                JSONObject itemJson = JSON.parseObject(String.valueOf(item));
	                String field = String.valueOf(itemJson.get("Field"));
	                String value = String.valueOf(itemJson.get("Value"));
	                
	                if ("startLoanDate".equals(field)) {
	                	loanReportDO.setStartLoanDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
	                }// 注册结束时间
	                else if ("endLoanDate".equals(field)) {
	                	loanReportDO.setEndLoanDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
	                }
	            }
	        } catch (Exception e) {
	            logger.error("项目投资人项目明细－搜索查询 查询参数解析出错", e);
	        }
        }
		JsonPageVO<LoanReportDO> resultVO = new JsonPageVO<LoanReportDO>();
		Integer rows = params.getInt("rows");
		Integer page = params.getInt("page");
		PageCondition pageCondition = new PageCondition(page, rows);
		PageResult<LoanReportDO> result = loanQueryService
				.queryLoanReport(loanReportDO,pageCondition);
		
		
		resultVO.setTotal(result.getTotalCount());
		for(LoanReportDO loan : result.getData()){
			loan.setLoan_invest_starttime(DateUtil.formatDate(loan.getLoan_invest_starttime(),DateUtil.DATE_TIME_FORMAT));
			loan.setLoan_invest_endtime(DateUtil.formatDate(loan.getLoan_invest_endtime(),DateUtil.DATE_TIME_FORMAT));
			loan.setLoan_invest_fulltime(DateUtil.formatDate(loan.getLoan_invest_fulltime(),DateUtil.DATE_TIME_FORMAT));
			loan.setLoan_expire_date(DateUtil.formatDate(loan.getLoan_expire_date(),DateUtil.DATE_TIME_FORMAT));
		}
		resultVO.setRows(result.getData());
		return resultVO;
	}
}
