package com.autoserve.abc.web.module.screen.mobileIos;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InviteJDO;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class InviteAwardList {
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
	
	public JsonMobileVO execute(Context context,ParameterParser params) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Integer userId = params.getInt("userId");
			int currentPage = params.getInt("showPage");
			int pageSize = params.getInt("pageSize");
			
			PageCondition pageCondition = new PageCondition(currentPage, pageSize);
			InviteJDO inviteJDO=new InviteJDO();
	    	inviteJDO.setInviteUserId(userId);
	    	PageResult<InviteJDO>  inviteJPageResult=inviteService.queryList(inviteJDO, pageCondition);
	    	
	    	List<InviteJDO> inviteJList = inviteJPageResult.getData();
			List<Map<String, Object>> list = Lists.newArrayList();
			
			for(InviteJDO invite:inviteJList){
				Map<String, Object> map = Maps.newHashMap();
				map.put("inviteeName", invite.getInviteeName()); //邀请人
				map.put("registerDate", DateUtil.formatDate(invite.getRegisterDate(), DateUtil.DEFAULT_DAY_STYLE)); //注册时间
				map.put("userBusinessState", invite.getUserBusinessState()); //邀请状态
				map.put("inviteRewardScore", invite.getInviteRewardScore()); //用户奖励(积分)
				map.put("inviteRewardMoney", invite.getInviteRewardMoney()); //用户奖励(红包)
				list.add(map);
			}
			resultMap.put("list", list);
			resultMap.put("pageCount", inviteJPageResult.getTotalCount());
			result.setResultCode("200");
			result.setResult(JSON.toJSON(resultMap));

		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("请求异常");
		}
		return result;
	}

}
