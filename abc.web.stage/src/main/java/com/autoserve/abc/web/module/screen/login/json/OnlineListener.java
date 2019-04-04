package com.autoserve.abc.web.module.screen.login.json;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.OnlineDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.OnlineService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonPlainVO;

public class OnlineListener {

	@Autowired
	private HttpSession session;
	
	@Resource
    private OnlineService onlineService;

	public JsonPlainVO<OnlineDO> execute(ParameterParser params) {
		JsonPlainVO<OnlineDO> result = new JsonPlainVO<OnlineDO>();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			result.setSuccess(false);
			return result;
		}
		OnlineDO onlineDO = new OnlineDO();
		onlineDO.setUserId(user.getUserId());
		onlineDO.setDayDate(new Date());
		onlineDO.setOnlineLength(0);
		onlineDO.setScoreNum(0);
		PlainResult<OnlineDO> plainResult = onlineService.addOnlineLength(onlineDO, params.getInt("interval"));
		result.setData(plainResult.getData());
		result.setMessage(plainResult.getMessage());
		return result;
	}
}
