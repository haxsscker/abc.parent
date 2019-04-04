package com.autoserve.abc.web.module.screen.account.mySetting.json;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.intf.user.UserService;

public class UpdateLetter {
    private final static Logger   logger = LoggerFactory.getLogger(QueryMoreLetter.class); //加入日志
    private SimpleDateFormat      dateFormat;
    @Autowired
    private HttpSession           session;
    @Resource
    private SysMessageInfoService messageInfoService;
    @Resource
    private UserService           userService;

    @SuppressWarnings("null")
    public int execute(Context context, Navigator nav, ParameterParser params) {
        logger.info("updateLetter");
        int data = 0;
        User user = (User) session.getAttribute("user");
        int messageId = params.getInt("messageId");
        SysMessageInfoDO sysMessageInfoDO = new SysMessageInfoDO();
        if (user != null) {

            sysMessageInfoDO.setSysMessageId(messageId);
            sysMessageInfoDO.setSysRead("1");
            data = messageInfoService.updateMessage(sysMessageInfoDO);

            return data;
        } else {
            nav.forwardTo("/login/login").end();
        }
        return data;
    }
}
