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

/**
 * 得到今日累计登录时长和所获积分信息
 */
public class PullOnlineInfo {
	
    @Autowired
    private HttpSession	session;

    @Resource
    private OnlineService onlineService;
    
    public JsonPlainVO<OnlineDO> execute(ParameterParser params) {
    	JsonPlainVO<OnlineDO> result = new JsonPlainVO<OnlineDO>();
    	User user = (User) session.getAttribute("user");
    	PlainResult<OnlineDO> plainResult = onlineService.getOnlineInfo(user, new Date());
    	result.setData(plainResult.getData());
        return result;
    }
}
