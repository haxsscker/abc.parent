package com.autoserve.abc.web.module.screen.message.json;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.service.biz.convert.SysMessageInfoConverter;
import com.autoserve.abc.service.biz.entity.SysMessageInfo;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MessageInfoList {
	@Resource
	private SysMessageInfoService messageInfoService;

	public Map<String, Object> execute(ParameterParser params) {
		Map<String, Object> vo = Maps.newHashMap();
		Integer rows = params.getInt("rows");
		Integer page = params.getInt("page");
		PageCondition pageCondition = new PageCondition(page, rows);
		SysMessageInfoDO message = new SysMessageInfoDO();
		// 搜索条件
		String title = params.getString("sysMessageTitle");
		String toUserName = params.getString("sysToUserName");
		if(StringUtils.isNotEmpty(title)){
			message.setSysMessageTitle(title);
		}
		if(StringUtils.isNotEmpty(toUserName)){
			message.setSysToUserName(toUserName);
		}
		
		PageResult<SysMessageInfoDO> pageResult = messageInfoService
				.queryMessage(message, pageCondition);
		List<SysMessageInfoDO> list = pageResult.getData();
		List<SysMessageInfo> voList = Lists.newArrayList();
		for(SysMessageInfoDO messageInfoDO:list){
			SysMessageInfo messageInfo = SysMessageInfoConverter.convert(messageInfoDO);
			voList.add(messageInfo);
		}
		vo.put("rows", voList);
		vo.put("total", pageResult.getTotalCount());
		return vo;
	}
}
