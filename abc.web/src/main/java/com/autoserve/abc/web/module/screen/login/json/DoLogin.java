package com.autoserve.abc.web.module.screen.login.json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.util.StringUtil;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.pipeline.LoginCookieTokenManager;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 登录
 */
public class DoLogin {

    @Autowired
    private EmployeeService     employeeService;

    @Autowired
    private DeployConfigService deployConfigService;

    @Autowired
    private HttpServletRequest  request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpSession         session;

    @Autowired
    private ImageCaptchaService imageCaptchaService;

    public static final String  loginSecurityCodeKey = "loginSecurityCodeKey";

    public JsonBaseVO execute(@Params LoginVO loginVo, Context context, Navigator nav, ParameterParser params,
                              TurbineRunData rundata) {
        JsonBaseVO result = new JsonBaseVO();

        String loginUrl = deployConfigService.getLoginUrl(request);
        String homeUrl = deployConfigService.getHomeUrl(request);

        String userName = params.getString("userName");
        String passWord = params.getString("passWord");
        params.getString("securityCode", "abcde").toLowerCase();
        request.getSession().getId();
        String securityCode = params.getString("securityCode", "abcde").toLowerCase();
        String sessionId = request.getSession().getId();
        Boolean isResponseCorrect = false;
        try {
            isResponseCorrect = imageCaptchaService.validateResponseForID(sessionId, securityCode);
            //TODO
//            isResponseCorrect = true;
            
        } catch (CaptchaServiceException e) {
            nav.redirectToLocation(loginUrl);
        }
        if (!isResponseCorrect) {
            result.setMessage("验证码错误");
            result.setSuccess(false);
            nav.redirectToLocation(loginUrl + "?loginState=" + CryptUtils.md5("securityCodeError"));
        }
        if (StringUtil.isBlank(userName) || StringUtil.isBlank(passWord)) {
            result.setMessage("登录名或密码错误");
            result.setSuccess(false);
            nav.redirectToLocation(loginUrl + "?loginState=" + CryptUtils.md5("userNameOrPasswordError"));
        } else {
            //            if (StringUtil.isBlank(securityCode) || (session.getAttribute(loginSecurityCodeKey) == null)
            //                    || !securityCode.equalsIgnoreCase(session.getAttribute(loginSecurityCodeKey).toString())) {
            //                result.setMessage("验证码错误");
            //                result.setSuccess(false);
            //                nav.redirectToLocation(loginUrl);
            //            }
            //            else {
            //                        if (StringUtil.isBlank(securityCode) || (session.getAttribute(loginSecurityCodeKey) == null)
            //                                || !securityCode.equalsIgnoreCase(session.getAttribute(loginSecurityCodeKey).toString())) {
            //                            result.setMessage("验证码错误");
            //                            result.setSuccess(false);
            //                            nav.redirectToLocation(loginUrl);
            //                        }
            //                        else {
            PlainResult<Employee> findResult = employeeService.login(userName, CryptUtils.md5(passWord),
                    IPUtil.getUserIpAddr(request));
            if (findResult.isSuccess()) {
                // 更新cookie的登录信息
                LoginCookieTokenManager.addLoginUserCookies(findResult.getData().getEmpId(), LoginCookieTokenManager.defaultCookieMaxValidSeconds, request, response);

                result.setMessage("登录成功");
                result.setSuccess(true);

                // 重定向
                String redirectUrl = params.getString("redirectUrl");
                if (redirectUrl == null
                        || redirectUrl.indexOf("redirectUrl",
                                redirectUrl.indexOf("redirectUrl") + "redirectUrl".length()) > 0) {
                    nav.redirectToLocation(homeUrl);
                } else {
                    nav.redirectToLocation(redirectUrl);
                }
            } else {
                result.setMessage(findResult.getMessage());
                if ("用户不在启用状态请联系管理员！".equals(findResult.getMessage())) {
                    nav.redirectToLocation(loginUrl + "?loginState=" + CryptUtils.md5("notEnable"));
                } else if("用户已被删除！".equals(findResult.getMessage())){
                	 nav.redirectToLocation(loginUrl + "?loginState=" + CryptUtils.md5("delete"));
                }
                else {
                    nav.redirectToLocation(loginUrl + "?loginState=" + CryptUtils.md5("userNameOrPasswordError"));
                }

            }
            //            }
            //                        }
        }

        return result;
    }
}
