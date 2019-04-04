package com.autoserve.abc.web.module.screen.account.myAccount.json;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.octo.captcha.service.image.ImageCaptchaService;

public class PhoneCodeValid {
    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private HttpServletRequest  request;
    @Autowired
    private DeployConfigService deployConfigService;
    @Autowired
    private HttpSession         session;
    @Resource
    private UserService         userService;
    @Resource
    private RealnameAuthService realnameAuthService;

    public JsonBaseVO execute(Context context, Navigator nav, ParameterParser params) {
        JsonBaseVO result = new JsonBaseVO();
        String Verification = params.getString("Verification");
        String validCode = (String) session.getAttribute("securityCode");
        if (Verification.equals(validCode)) {
            result.setSuccess(true);
        } else {
            result.setMessage("验证码不正确！");
            result.setSuccess(false);
        }
        return result;
    }
}
