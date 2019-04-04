package com.autoserve.abc.web.module.screen.score.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LevelDO;
import com.autoserve.abc.service.biz.intf.score.LevelService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/2 14:14.
 */
public class GetLevelManageList {
	@Resource
	private LevelService levelService;

	private static Logger logger = LoggerFactory
			.getLogger(GetLevelManageList.class);

	public JsonPageVO<LevelDO> execute(ParameterParser params) {
		Integer rows = params.getInt("rows");
		Integer page = params.getInt("page");
		PageCondition pageCondition = new PageCondition(page, rows);

		LevelDO levelDO = new LevelDO();
		String searchForm = params.getString("searchForm");
		if (StringUtils.isNotBlank(searchForm)) {// 搜索
			try {
				JSONObject searchFormJson = JSON.parseObject(searchForm);
				JSONArray itemsArray = JSON.parseArray(String
						.valueOf(searchFormJson.get("Items")));

				for (Object item : itemsArray) {
					JSONObject itemJson = JSON
							.parseObject(String.valueOf(item));
					String field = String.valueOf(itemJson.get("Field"));
					String value = String.valueOf(itemJson.get("Value"));

					if ("levName".equals(field)) {
						levelDO.setLevName(value);
					}
				}
			} catch (Exception e) {
				logger.error("等级管理－搜索查询 查询参数解析出错", e);
			}
		}

		// 仅查询状态为启用的积分类型
		PageResult<LevelDO> pageResult = levelService.findAllLevel(levelDO,
				pageCondition);
		JsonPageVO<LevelDO> pageVO = new JsonPageVO<LevelDO>();
		pageVO.setRows(pageResult.getData());
		pageVO.setTotal(pageResult.getTotalCount());
		return pageVO;
	}
}
