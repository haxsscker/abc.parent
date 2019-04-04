package com.autoserve.abc.web.module.screen.banel.json;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.MonthReportDO;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.banel.MonthRptService;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 
 */
public class ActionMonthRptView {
	private static Logger logger = LoggerFactory.getLogger(ActionMonthRptView.class);
    @Resource
    private MonthRptService monthRptService;
    
    public Map<String, Object> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        MonthReportDO monthReportDO = new MonthReportDO();
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {//搜索
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
                
                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    if ("rpt_year".equals(field)) {
                    	monthReportDO.setRptYear(value);
                    } 
                }
            } catch (Exception e) {
                logger.error("月度统计－搜索查询 查询参数解析出错", e);
            }
        }
        PageResult<MonthReportDO> result = monthRptService.queryListBySearchParam(monthReportDO,pageCondition);
        for(MonthReportDO mRDO : result.getData()){
        	mRDO.setCreateTime(mRDO.getCreateTime().substring(0, mRDO.getCreateTime().length()-2));
        	mRDO.setModifyTime(mRDO.getModifyTime()==null ? "未修改" : mRDO.getModifyTime().substring(0, mRDO.getModifyTime().length()-2));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("total", result.getTotalCount());
        map.put("rows", result.getData());
        return map;
    }
    
}
