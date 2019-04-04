package com.autoserve.abc.web.module.screen.account.myAward.json;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InviteJDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.score.ScoreConfigService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.Pagebean;

public class QueryInvite {
	    @Autowired
	    private HttpSession        session;
	    @Resource
		private InviteService inviteService;
	    @Resource
	    private ScoreConfigService scoreConfigService;
	    //日期格式化  
	    private SimpleDateFormat   dateFormat;

	    public String execute(Context context, ParameterParser params) {
	        User user = (User) session.getAttribute("user");
	        int pageSize = 10;
	        int currentPage = params.getInt("currentPage") + 1;
	        StringBuffer html = new StringBuffer();
	        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
	        InviteJDO inviteJDO=new InviteJDO();
			inviteJDO.setInviteUserId(user.getUserId());
	        PageResult<InviteJDO>  result=inviteService.queryList(inviteJDO, pageCondition);
	        List<InviteJDO> invitJList = result.getData();
	        Pagebean<InviteJDO> pagebean = new Pagebean<InviteJDO>(currentPage, pageSize, invitJList,
	        		result.getTotalCount());
	        context.put("pagebean", pagebean);
	        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        for (InviteJDO invite : invitJList) {
	        	
	            html.append("<tr>");
	            html.append("<td class='wdjf_td1  text-center '>"+invite.getInviteeName()+"</td>");
	            html.append("<td class='wdjf_td1  text-center'><span class='c-jyjl-money1'>"+dateFormat.format(invite.getRegisterDate())+"</span></td>");
	            html.append("<td class='wdjf_td1  text-center color_red'>"+invite.getUserBusinessState()+"</td>");
	            html.append("<td class='wdjf_td1  text-center '>"+invite.getInviteRewardScore()+"</td>");
	            
	            html.append("</tr>");
		    }
	            return html.toString();

	    }

}
