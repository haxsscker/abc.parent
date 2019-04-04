package com.autoserve.abc.web.module.screen.register;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.convert.UserConverter;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Invite;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.enums.RewardState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.ValidState;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.autoserve.abc.web.helper.DeployConfigService;

/**
 * 快速注册
 */
public class PhoneRegister {

	@Resource
	private UserService userService;
	@Resource
	private HttpSession session;
	@Resource
	private InviteService inviteService;
	@Autowired
	private SysConfigService sysConfigService;
	@Resource
	private HttpServletRequest request;
	@Autowired
	private DeployConfigService deployConfigService;

	public void execute(@Params User user, Context context,
			ParameterParser params, Navigator nav) {
		String securityCode = (String) session.getAttribute("securityCode");
		String Verification = params.getString("Verification");
		if (securityCode == null || "".equals(securityCode)) {
			context.put("securityError", "验证码已失效，请重新获取！");
			context.put("userx", user);
			nav.forwardTo("/register/fastregister").end();
			return;
		} else if (null == Verification || !securityCode.equals(Md5Encrypt.md5(Verification))) {
			context.put("securityError", "验证码错误！");
			context.put("userx", user);
			nav.forwardTo("/register/fastregister").end();
			return;
		} else {
			String userPwd = "123456";
			String userPhone = params.getString("userPhone");

			UserDO userDO = new UserDO();
			userDO.setUserPhone(userPhone);
			PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
			if (pageResult.getData().size() != 0) {
				context.put("securityError", "该手机号已注册！");
				context.put("userx", user);
				nav.forwardTo("/register/fastregister").end();
				return;
			}

			// 推荐人手机号
			String InvitationPhone = params.getString("InvitationPhone");
			userDO.setUserName(userPhone);
			userDO.setUserPwd(CryptUtils.md5(userPwd));
			userDO.setUserDealPwd(CryptUtils.md5(userPwd));
			// 邀请人id，邀请人用户类型
			Integer invitationUserId = null;
			Integer invitationUserType = null;

			if (InvitationPhone != null && !"".equals(InvitationPhone)) {
				UserDO udo = new UserDO();
				udo.setUserPhone(InvitationPhone);
				List<UserDO> users = userService.queryList(udo,
						new PageCondition()).getData();
				if (users.size() > 0) {
					invitationUserId = users.get(0).getUserId();
					invitationUserType = users.get(0).getUserType();
					userDO.setUserRecommendUserid(invitationUserId);
				}
			}

			// 设置用户对应的uuid
			userDO.setUserUuid(InnerSeqNo.getInstance().getUniqueNo());
			// 是否绑定手机号
			userDO.setUserMobileIsbinded(1);
			// 手机认证日期
			userDO.setUserMobileVerifyDate(new Date());
			// 是否绑定邮箱
			userDO.setUserEmailIsbinded(0);
			// 注册日期
			userDO.setUserRegisterDate(new Date());
			// 启用账户
			userDO.setUserState(1);
			// 用户类型 1.个人 2.企业
			userDO.setUserType(1);
			// 信用额度(后台设置)
			PlainResult<SysConfig> sysConfigInfo = sysConfigService
					.querySysConfig(SysConfigEntry.MIN_CREDIT_LIMIT);
			SysConfig sysConfig = sysConfigInfo.getData();
			if (sysConfig != null && sysConfig.getConfValue() != null
					&& !"".equals(sysConfig.getConfValue())) {
				userDO.setUserLoanCredit(new BigDecimal(sysConfig
						.getConfValue()));
				userDO.setUserCreditSett(new BigDecimal(sysConfig
						.getConfValue()));
			}
			BaseResult result = userService.createUser(userDO);
			if (!result.isSuccess()) {
				context.put("securityError", result.getMessage());
				context.put("userx", user);
				nav.forwardTo("/register/fastregister").end();
				return;
			} else {
				// 添加邀请记录
				if (invitationUserId != null) {
					Invite invite = new Invite();
					invite.setInviteUserId(invitationUserId);
					invite.setInviteInviteeId(userDO.getUserId());
					if (invitationUserType != null && invitationUserType == 1) {
						invite.setInviteUserType(InviteUserType.PERSONAL);
					} else if (invitationUserType != null
							&& invitationUserType == 2) {
						invite.setInviteUserType(InviteUserType.PARTNER);
					}
					invite.setInviteIsValid(ValidState.VALID_STATE);
					invite.setInviteRewardState(RewardState.USED);
					inviteService.createInvitation(invite);
				}

				// 注册成功后直接登录
				User u = UserConverter.toUser(userDO);
				session.setAttribute("user", u);

				// String homeUrl = deployConfigService.getHomeUrl(request);
				// nav.redirectToLocation(homeUrl);

				// 跳转到注册第二部
				nav.redirectToLocation("/account/myAccount/accountOverview");
			}
		}

	}

}
