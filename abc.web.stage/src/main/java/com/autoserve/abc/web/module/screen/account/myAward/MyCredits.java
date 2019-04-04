package com.autoserve.abc.web.module.screen.account.myAward;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreHistoryDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.score.ScoreHistoryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;

public class MyCredits {
	@Autowired
	private HttpSession session;
	@Resource
	private HttpServletRequest request;
	@Resource
	private UserService userService;
	@Resource
	private ScoreHistoryService scoreHistoryService;
	
	@Autowired
	private DeployConfigService deployConfigService;
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	User user = (User) session.getAttribute("user");
    	if(user==null){
    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
    		return;
    	}
    	
    	int currentPage = params.getInt("currentPage");
    	if(currentPage==0)currentPage=1;
    	int pageSize=10;
    	PageCondition pageCondition = new PageCondition(currentPage,pageSize);
    	
    	
//    	PlainResult<UserDO> userResult = userService.findById(user.getUserId());
    	PlainResult<User> userResult = userService.findEntityById(user.getUserId());
    	
    	//获取积分记录
    	ScoreHistoryDO tmpDao= new ScoreHistoryDO();
    	tmpDao.setShUserId(user.getUserId());
		PageResult<ScoreHistoryWithValueDO> result = scoreHistoryService.queryScoreHistoryList(tmpDao, pageCondition);
		Pagebean<ScoreHistoryWithValueDO>  pagebean = new Pagebean<ScoreHistoryWithValueDO>(currentPage,pageSize,result.getData(),result.getTotalCount());
		
    	
    	context.put("userPojo", userResult.getData());
    	context.put("pagebean", pagebean);
    }
}
