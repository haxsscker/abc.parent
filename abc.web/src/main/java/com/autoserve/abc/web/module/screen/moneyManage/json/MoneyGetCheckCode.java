package com.autoserve.abc.web.module.screen.moneyManage.json;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.autoserve.abc.service.message.sms.SendMsgService;
import com.autoserve.abc.service.util.UuidUtil;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类实现描述 发送短信
 *
 * @author liuwei 2014年12月30日 下午6:01:01
 */
public class MoneyGetCheckCode {

    @Resource
    private SendMsgService sendMsgService;
    @Resource
    private HttpSession    HttpSession;

    public JsonBaseVO execute() {
        String code = UuidUtil.generateUuid().substring(0, 4);

        boolean flag = this.sendMsgService.sendMsg(LoginUserUtil.getEmpMobile(), code, LoginUserUtil.getEmpName(),"2");
        if (flag) {
            HttpSession.setAttribute("VCode", code);
            HttpSession.setAttribute("VDate", new Date());
        }

        JsonBaseVO vo = new JsonBaseVO();
        vo.setSuccess(true);
        vo.setMessage("验证码已发送手机！");
        System.out.println("验证码已发送手机！" + code);
        return vo;
    }
}
