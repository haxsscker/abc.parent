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
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.UserInvestInfoReportDO;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;

public class UserInvestInfoReportView {
	private static final Logger logger = LoggerFactory
			.getLogger(UserInvestInfoReportView.class);
	@Resource
	private UserService userService;

	public JsonPageVO<UserInvestInfoReportDO> execute(ParameterParser params) {
		//前端传入的参数
		UserDO userDO = new UserDO();
		String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
	        try {
	            JSONObject searchFormJson = JSON.parseObject(searchForm);
	            JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
	
	            for (Object item : itemsArray) {
	                JSONObject itemJson = JSON.parseObject(String.valueOf(item));
	                String field = String.valueOf(itemJson.get("Field"));
	                String value = String.valueOf(itemJson.get("Value"));
	
	                // 客户名称
	                if ("name".equals(field)) {
	                	userDO.setUserRealName(value);
	                }
	                // 注册开始时间
	                else if ("startRegisterDate".equals(field)) {
	                	userDO.setStartRegisterDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
	                }// 注册结束时间
	                else if ("endRegisterDate".equals(field)) {
	                	userDO.setEndRegisterDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
	                }
	            }
	        } catch (Exception e) {
	            logger.error("注册用户明细－搜索查询 查询参数解析出错", e);
	        }
        }
		
		JsonPageVO<UserInvestInfoReportDO> resultVO = new JsonPageVO<UserInvestInfoReportDO>();
		Integer rows = params.getInt("rows");
		Integer page = params.getInt("page");
		PageCondition pageCondition = new PageCondition(page, rows);
		PageResult<UserInvestInfoReportDO> result = userService
				.queryUserInvestInfo(userDO,pageCondition);
		
		
		resultVO.setTotal(result.getTotalCount());
		resultVO.setRows(result.getData());
		return resultVO;
	}
}
