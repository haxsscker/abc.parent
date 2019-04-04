package com.autoserve.abc.web.module.screen.account.myLoan.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.autoserve.abc.web.module.screen.account.myLoan.Errorbean;
import com.octo.captcha.service.image.ImageCaptchaService;

public class CheckCode {
	@Autowired
	private HttpSession session;
	@Resource
	private UserService userService;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private HttpServletRequest request;

	public Errorbean execute(Context context, ParameterParser params) {
		User user = (User) session.getAttribute("user");
		PlainResult<UserDO> result = userService.findById(user.getUserId());
		UserDO userDO = result.getData();
		Errorbean error = new Errorbean();
		String payPs = params.getString("payPs");
		String imageCode = params.getString("imageCode");
		payPs = CryptUtils.md5(payPs);
		if (!payPs.equals(userDO.getUserDealPwd())) {
			error.setIsPsRight(0);
		} else {
			error.setIsPsRight(1);
		}
		// boolean isResponseCorrect =
		// imageCaptchaService.validateResponseForID(request.getSession().getId(),
		// imageCode);
		String securityfromSession = (String) session
				.getAttribute("securityCode");
		if (securityfromSession == null
				|| imageCode == null
				|| !securityfromSession.equalsIgnoreCase(Md5Encrypt.md5(imageCode))) {
			error.setIsImageCodeRight(0);
		} else {
			error.setIsImageCodeRight(1);
		}
		return error;
	}
}
