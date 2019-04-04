package com.autoserve.abc.web.module.screen.login.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.util.StringUtil;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * @author DS 登录
 */
public class DoLogin {

    @Resource
    private UserDao             userDao;

    @Autowired
    private UserService         UserService;

    @Autowired
    private DeployConfigService deployConfigService;

    @Autowired
    private HttpServletRequest  request;

    @Autowired
    private HttpSession         session;

    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private HttpServletResponse response;

    public static final String  loginSecurityCodeKey = "loginSecurityCodeKey";

    public JsonBaseVO execute(Context context, Navigator nav, ParameterParser params, TurbineRunData rundata) {
    	JsonBaseVO result = new JsonBaseVO();

		String userName = params.getString("userName");
		String passWord = params.getString("passWord");
		//改为滑动验证
		/*String securityCode = params.getString("securityCode");

		String securityfromSession = (String) session
				.getAttribute("securityCode");
		boolean flag = securityfromSession == null || securityCode == null
				|| !securityfromSession.equalsIgnoreCase(securityCode);
		//TODO
		//flag = false;
		if (flag) {
			if (securityfromSession == null) {
				result.setMessage("验证码已失效，请重新获取");
			} else {
				result.setMessage("验证码错误");
			}
			result.setSuccess(false);
			return result;
		}*/

		PlainResult<User> findResult = UserService.login(userName, CryptUtils.md5(passWord),
				IPUtil.getUserIpAddr(request),"Wechat");
		if (findResult.isSuccess()) {
			session.setAttribute("user", findResult.getData());
			result.setMessage("登录成功");
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
			result.setMessage(findResult.getMessage());
		}
		return result;
    }

   
}
