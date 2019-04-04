package com.autoserve.abc.web.module.screen.infomation.json;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.autoserve.abc.dao.dataobject.MonthReportDO;
import com.autoserve.abc.service.biz.intf.banel.MonthRptService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPlainVO;

/**
 * 信息披露数据
 */
public class InformationData {

	@Resource
    private MonthRptService     monthRptService;
    @Autowired
    private HttpServletRequest  request;
    @Autowired
    private HttpServletResponse response;

    public JsonPlainVO<Map<String, Object>> execute(Context context, Navigator nav, ParameterParser params, TurbineRunData rundata) {
    	JsonPlainVO<Map<String, Object>> result = new JsonPlainVO<Map<String, Object>>();
    	Map<String, Object> dataMap = new HashMap<String, Object>(); 
    	String infoType = params.getString("infoType");
    	String year = params.getString("year");
    	Calendar date = Calendar.getInstance();
    	if(StringUtils.isBlank(year)){
    		year = String.valueOf(date.get(Calendar.YEAR));
    	}
		try {
			//月度报告
			if("YDBG".equals(infoType)){
				ListResult<MonthReportDO> result1 = monthRptService.queryListByYear(year);
				dataMap.put("rows", result1.getData());
				for(MonthReportDO mrd : result1.getData()){
					mrd.setCreateTime(DateUtil.formatDate(mrd.getCreateTime(),DateUtil.DATE_FORMAT));
					mrd.setModifyTime(DateUtil.formatDate(mrd.getModifyTime(),DateUtil.DATE_FORMAT));
				}
				String adminUrl = SystemGetPropeties.getBossString("adminUrl");
				dataMap.put("adminUrl", adminUrl);
				dataMap.put("year", year);
				result.setData(dataMap);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
    }

   
}
