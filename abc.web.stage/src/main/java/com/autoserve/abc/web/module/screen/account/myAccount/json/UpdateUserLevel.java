package com.autoserve.abc.web.module.screen.account.myAccount.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.autoserve.abc.dao.dataobject.AssessLevelDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserAssessService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.vo.JsonPlainVO;

/**
 * @author 参加活动
 */
public class UpdateUserLevel {

	@Resource
	private UserAssessService userAssessService;
	@Resource
	private UserService userService;
	@Autowired
	private DeployConfigService deployConfigService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpSession session;

	public JsonPlainVO execute(Context context, Navigator nav,
			ParameterParser params, TurbineRunData rundata) {
		JsonPlainVO result = new JsonPlainVO();
		
		User user = (User) session.getAttribute("user");
		if (null == user || null == user.getUserPhone())
		{
			result.setSuccess(false);
			result.setMessage("您尚未登陆");
			result.setRedirectUrl(deployConfigService.getLoginUrl(request));
			return result;
		}
		
		int assScore = params.getInt("assScore");
		try
		{
			userAssessService.updateUserAssess(assScore, user.getUserId());
			
			PlainResult<UserDO> userDO = this.userService.findById(user.getUserId());
	    	AssessLevelDO assLevelDO = null;
	    	if (0 != userDO.getData().getAssId())
	    	{
	    		assLevelDO = userAssessService.findById(userDO.getData().getAssId());
	    	}
			result.setSuccess(true);
			result.setMessage("感谢您完成风险评估！您的评估等级为"+assLevelDO.getAssName());
		}
		catch (Exception e)
		{
			result.setSuccess(false);
			result.setMessage("投资风险评估失败");
			return result;
		}
		
		return result;
	}
}
