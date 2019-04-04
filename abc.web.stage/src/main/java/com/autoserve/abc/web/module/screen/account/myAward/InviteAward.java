package com.autoserve.abc.web.module.screen.account.myAward;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InviteJDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;
/**
 * 
 * @author DS
 *
 * 2015下午5:13:07
 */
public class InviteAward {
	@Autowired
    private HttpSession session;
	@Autowired
	private DeployConfigService deployConfigService;
	@Resource
	private UserService userService;
	@Resource
	private HttpServletRequest  request;
	@Resource
	private InviteService inviteService;
	@Resource
	private ScoreService scoreService;
	@Resource
	private RedService redService;

    public void execute(Context context,Navigator nav,ParameterParser params) {
    	User user=(User) session.getAttribute("user");
    	if(user==null){
    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
    		return;
    	}
    	
    	//推荐展示
    	int currentPage = params.getInt("currentPage");
    	if(currentPage==0)currentPage=1;
    	int pageSize=10;
    	PageCondition pageCondition = new PageCondition(currentPage,pageSize);
    	InviteJDO inviteJDO=new InviteJDO();
    	inviteJDO.setInviteUserId(user.getUserId());
    	PageResult<InviteJDO>  result=inviteService.queryList(inviteJDO, pageCondition);
		Pagebean<InviteJDO>  pagebean = new Pagebean<InviteJDO>(currentPage,pageSize,result.getData(),result.getTotalCount());
		context.put("pagebean", pagebean);
		
//		UserDO userDO= new UserDO();
//		userDO.setUserRecommendUserid(user.getUserId());
//		PageResult<UserDO> result = userService.queryList(userDO, pageCondition);
//		Pagebean<UserDO>  pagebean = new Pagebean<UserDO>(currentPage,pageSize,result.getData(),result.getTotalCount());
//		context.put("pagebean", pagebean);
//		//邀请奖励(积分)
//		PlainResult<ScoreDO> scoreDOResult=scoreService.findByScoreCode(ScoreType.RECOMMEND_SCORE.getCode());
//		if(scoreDOResult.getData()!=null){
//			context.put("score", scoreDOResult.getData().getScoreValue());
//		}
//		// 查询推荐红包
//		BigDecimal RedAmounts=new BigDecimal(0);
//		Red redParam = new Red();
//        redParam.setRedType(RedenvelopeType.INVIT_RED);
//        redParam.setRedState(RedState.EFFECTIVE);
//        ListResult<Red> redResult = redService.queryList(redParam,null);
//        for(Red red:redResult.getData()){
//        	RedAmounts=RedAmounts.add(new BigDecimal(red.getRedAmount()));
//        }
//        context.put("RedAmounts", RedAmounts);
				
		//邀请地址
		InviteUserType userType=InviteUserType.PERSONAL;
		switch (user.getUserType()) {
		case PERSONAL:
			userType=InviteUserType.PERSONAL;
			break;
		case PARTNER:
			userType=InviteUserType.PARTNER;
			break;
		default:
			break;
		}
		PlainResult<String> inviteString=inviteService.generateInviteUrl(user.getUserId(), userType);
		context.put("inviteString", inviteString.getData());
    }
}
