package com.autoserve.abc.web.module.screen.account.mySetting;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.SysMessageReplyDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.intf.sys.SysMessageReplyService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.module.screen.account.mySetting.SysMessageInfoVO;

public class MessageContent {
    private final static Logger   logger = LoggerFactory.getLogger(MessageContent.class); //加入日志

    @Autowired
    private HttpSession           session;
    @Resource
    private SysMessageInfoService messageInfoService;
    @Resource
    private UserService           userService;
    @Resource
    private SysMessageReplyService sysMessageReplyService;
    public void execute(Context context, Navigator nav, ParameterParser params) {

        //获取messageId
        Integer messageid = params.getInt("messageid");
        User user = (User) session.getAttribute("user");

        //执行消息状态更新
        SysMessageInfoDO smdo = new SysMessageInfoDO();
        PageCondition p = new PageCondition();

        smdo.setSysMessageId(messageid);
        smdo.setSysRead("1");
        messageInfoService.updateMessage(smdo);
        
        PageResult<SysMessageInfoDO> result = messageInfoService.queryMessage(smdo, new PageCondition());
        if (result.getData().size() > 0) {
            context.put("message", result.getData().get(0));
            //查询回复消息
            PageResult<SysMessageReplyDO> replyResult = sysMessageReplyService.queryAllReplyByMesageid(messageid, p);
            context.put("replyList", replyResult.getData());
        }

        
        //查询消息内容
        if (user != null) {
            SysMessageInfoDO sysMessageInfoDO = messageInfoService.queryMesageById(messageid);
            //VO、DO转换
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
            vo.setSysRead(sysMessageInfoDO.getSysRead());

            context.put("sysMessageInfo", vo);
        } else {
            nav.forwardTo("/login/login").end();

        }

    }
}
