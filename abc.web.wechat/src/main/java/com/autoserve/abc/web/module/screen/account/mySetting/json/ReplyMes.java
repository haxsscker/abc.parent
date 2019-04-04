package com.autoserve.abc.web.module.screen.account.mySetting.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.SysMessageReplyDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.intf.sys.SysMessageReplyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class ReplyMes {
    @Resource
    private HttpSession            session;

    @Resource
    private SysMessageReplyService sysMessageReplyService;

    @Resource
    private SysMessageInfoService  sysMessageInfoService;

    public JsonBaseVO execute(Context context, Navigator nav, ParameterParser params) {
        //参数获取
        Integer messageId = params.getInt("messageId");
        String replyContent = params.getString("content");
        // 获取用户信息
        User user = (User) session.getAttribute("user");
        context.put("user", user);
        //封装更新对象
        SysMessageReplyDO ado = new SysMessageReplyDO();
        ado.setSysMessageId(messageId);
        ado.setSysUserId(user.getUserId());
        ado.setSysUserType("3");
        ado.setSysReplyContent(replyContent);
        ado.setSysUserName(user.getUserName());

        //修改信息记录为已回复
        SysMessageInfoDO sysMessageInfoDo = new SysMessageInfoDO();
        sysMessageInfoDo.setSysMessageId(messageId);
        sysMessageInfoDo.setSysMessageState("1");
        sysMessageInfoService.updateMessage(sysMessageInfoDo);
        //执行更新
        JsonBaseVO vo = new JsonBaseVO();
        try {
            BaseResult result = sysMessageReplyService.createMessage(ado);
            vo.setMessage(result.getMessage());
        } catch (Exception e) {
            vo.setMessage("回复失败！");
        }
        return vo;

    }
}
