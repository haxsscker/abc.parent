/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
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
import com.autoserve.abc.dao.dataobject.RedReportDO;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;


public class RedReportView {
    private static final Logger      logger = LoggerFactory.getLogger(RedReportView.class);
    @Resource
    private RedService redService;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public JsonPageVO<RedReportDO> execute(ParameterParser params) {
        RedReportDO redReportDO = new RedReportDO();
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
	        try {
	            JSONObject searchFormJson = JSON.parseObject(searchForm);
	            JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
	
	            for (Object item : itemsArray) {
	                JSONObject itemJson = JSON.parseObject(String.valueOf(item));
	                String field = String.valueOf(itemJson.get("Field"));
	                String value = String.valueOf(itemJson.get("Value"));
	                // 开始时间
	                if ("startRedDate".equals(field)) {
	                	redReportDO.setStartRedDate(sdf.parse(value));
	                }// 结束时间
	                else if ("endRedDate".equals(field)) {
	                	redReportDO.setEndRedDate(sdf.parse(value));
	                }
	                //红包使用日期
	                else if("startRedUseDate".equals(field)){
	                	redReportDO.setStartRedUseTime(sdf.parse(value));
	                }
	                //红包使用日期
	                else if("endRedUseDate".equals(field)){
	                	redReportDO.setEndRedUseTime(sdf.parse(value));
	                }
	                //派发类型
	                else if("type".equals(field)){
	                	redReportDO.setRs_type(value);
	                }
	                //状态
	                else if("state".equals(field)){
	                	redReportDO.setRs_state(value);
	                }
	                //项目名
	                else if("loan_no".equals(field)){
	                	redReportDO.setLoan_no(value);
	                }
	            }
	        } catch (Exception e) {
	            logger.error("项目投资人项目明细－搜索查询 查询参数解析出错", e);
	        }
        }
        JsonPageVO<RedReportDO> resultVO = new JsonPageVO<RedReportDO>();
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        PageResult<RedReportDO> result = redService.redReport(redReportDO, pageCondition);
        resultVO.setTotal(result.getTotalCount());
        resultVO.setRows(result.getData());
        return resultVO;
    }
}
