package com.autoserve.abc.web.module.screen.account.myAward.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class ConvertCredits {
	@Resource
	private HttpSession session;
	@Resource
	private ScoreService scoreService;
	public BaseResult execute(Context context,ParameterParser params){
		
		User user  = (User)session.getAttribute("user");
		if(user==null) return null;
		
		Integer userId = user.getUserId();
		
		String score =params.getString("score");
		BaseResult result = scoreService.convertRedEnvelope(userId, Integer.valueOf(score));
		
		return result;
	}
}
