package com.autoserve.abc.web.module.screen.area.json;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.AreaDO;
import com.autoserve.abc.service.biz.intf.common.AreaService;
/**
 * 获取二级区域
 * @author DS
 *
 */
public class TwoLevelArea {
	@Resource
	private AreaService areaService;
	 public  List<AreaDO> execute(Context context, ParameterParser params) {
		 //获取一级区域号
		 String  superAreaCode=params.getString("superAreaCode");
		 List<AreaDO> lists= areaService.querySecondArea(superAreaCode);
		 return lists;
	 }

}
