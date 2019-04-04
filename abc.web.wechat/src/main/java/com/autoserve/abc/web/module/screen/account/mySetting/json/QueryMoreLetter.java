package com.autoserve.abc.web.module.screen.account.mySetting.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.common.PageCondition.Order;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.module.screen.account.mySetting.SysMessageInfoVO;
import com.autoserve.abc.web.util.Pagebean;
import com.autoserve.abc.web.vo.JsonListVO;

/**
 * 查询更多站内信
 * 
 * @author
 */
public class QueryMoreLetter {
    private final static Logger   logger = LoggerFactory.getLogger(QueryMoreLetter.class); //加入日志
    private SimpleDateFormat      dateFormat;
    @Autowired
    private HttpSession           session;
    @Resource
    private SysMessageInfoService messageInfoService;
    @Resource
    private UserService           userService;

    public JsonListVO<String> execute(Context context, Navigator nav, ParameterParser params) {

        User user = (User) session.getAttribute("user");
        Integer currentPage = params.getInt("currentPage");
        Integer pageSize = params.getInt("pageSize");
        if (currentPage == 0)
            currentPage = 1;//默认情况
        if (pageSize == 0)
            pageSize = 10;//默认情况
        //      List<SysMessageInfoDO> rechargeRecorDO = null;
        if (user != null) {
            PageCondition pageCondition = new PageCondition(currentPage, pageSize, "sysMessageDate", Order.DESC);
            PageResult<SysMessageInfoDO> pageResult = messageInfoService.queryByUserId(user.getUserId(), pageCondition);
            List<SysMessageInfoDO> messageInfoDOList = pageResult.getData();
            List<SysMessageInfoVO> messageInfoVOList = new ArrayList<SysMessageInfoVO>();
            List<SysMessageInfoVO> messageInfoVOList2 = new ArrayList<SysMessageInfoVO>();
            List<UserDO> userList = new ArrayList<UserDO>();
            for (SysMessageInfoDO sysMessageInfoDO : messageInfoDOList) {
                SysMessageInfoVO vo = new SysMessageInfoVO();
                vo.setSysDelSign(sysMessageInfoDO.getSysDelSign());
                vo.setSysMessageContent(sysMessageInfoDO.getSysMessageContent());//
                vo.setSysMessageDate(sysMessageInfoDO.getSysMessageDate());
                vo.setSysMessageId(sysMessageInfoDO.getSysMessageId());
                vo.setSysMessageState(sysMessageInfoDO.getSysMessageState());
                vo.setSysMessageTitle(sysMessageInfoDO.getSysMessageTitle());
                vo.setSysToUser(sysMessageInfoDO.getSysToUser());
                vo.setSysToUserType(sysMessageInfoDO.getSysToUserType());
                vo.setSysUserId(sysMessageInfoDO.getSysUserId());
                vo.setSysUserType(sysMessageInfoDO.getSysUserType());
                messageInfoVOList.add(vo);
                PlainResult<UserDO> plainResult = userService.findById(sysMessageInfoDO.getSysToUser());
                userList.add(plainResult.getData());

            }
            for (int i = 0; i < messageInfoDOList.size(); i++) {
                SysMessageInfoVO vo = messageInfoVOList.get(i);
                vo.setSysToUserName(userList.get(i).getUserName());
                messageInfoVOList2.add(vo);
            }
            //分页处理
            Pagebean<SysMessageInfoVO> pagebean = new Pagebean<SysMessageInfoVO>(currentPage, pageResult.getPageSize(),
                    messageInfoVOList2, pageResult.getTotalCount());
            context.put("pagebean", pagebean);
            String html = "";
            html = addHtml(messageInfoDOList);
            List<String> list = new ArrayList<String>();
            list.add(html);
            JsonListVO<String> jvo = new JsonListVO<String>();
            jvo.setRows(list);
            return jvo;
        } else {
            nav.forwardTo("/login/login").end();

        }
        return null;

    }

    private String addHtml(List<SysMessageInfoDO> messageInfoDOList) {
        StringBuffer html = new StringBuffer();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (SysMessageInfoDO sysMessageInfoDO : messageInfoDOList) {
            html.append("<div class='row xiaoxi_list bg_white mt15'>");
            html.append("<div class='col-sm-2 col-xs-2 text-center'>");
            html.append("<img src='/images/laba.png' width='30'/>");
            html.append("</div>");
            html.append("<div class='col-sm-10 col-xs-10'>");
            html.append("<a href='/account/mySetting/messageContent?messageid=" + sysMessageInfoDO.getSysMessageId()
                    + "'>");
            html.append("<h4 class='clearfix'>");
            if ("1".equals(sysMessageInfoDO.getSysRead())) {
                html.append(sysMessageInfoDO.getSysMessageTitle() + "(已读)");
            } else {
                html.append(sysMessageInfoDO.getSysMessageTitle());
            }
            html.append("<span class='pull-right mr5'>" + dateFormat.format(sysMessageInfoDO.getSysMessageDate())
                    + "</span>");
            html.append("</h4>");
            html.append("</a>");
            html.append("<p>");
            if (sysMessageInfoDO.getSysMessageContent().length() > 15) {
                html.append(sysMessageInfoDO.getSysMessageContent().substring(0, 15) + "...");
            } else {
                html.append(sysMessageInfoDO.getSysMessageContent());
            }
            html.append("<span class='pull-right dele'>");
            html.append("<a href='javascript:;' onclick=\"deleteMessage('" + sysMessageInfoDO.getSysMessageId()
                    + "');\" >删除</a>");
            html.append("</span>");
            html.append("</p>");
            html.append("</a>");
            html.append("</div>");
            html.append("</div>");
        	}
        
        return html.toString();
    }
}
