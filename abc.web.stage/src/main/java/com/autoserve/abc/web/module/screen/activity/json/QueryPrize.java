package com.autoserve.abc.web.module.screen.activity.json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.autoserve.abc.dao.dataobject.ActPrizeDO;
import com.autoserve.abc.service.biz.intf.activity.ActivityService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.vo.JsonPlainVO;

/**
 * @author 参加活动
 */
public class QueryPrize {

	@Autowired
	private ActivityService activityService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpSession session;

	public JsonPlainVO execute(Context context, Navigator nav,
			ParameterParser params, TurbineRunData rundata) {
		JsonPlainVO result = new JsonPlainVO();

		String actId = params.getString("actId");

		ListResult<ActPrizeDO> prizes = activityService.findAllPrize(Integer.parseInt(actId));
		
		result.setData(prizes.getData());

		return result;
	}
}
