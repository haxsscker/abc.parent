package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Invite;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.enums.RewardState;
import com.autoserve.abc.service.biz.enums.ValidState;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.message.sms.SendMsgService;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.util.GenerateUtil;
import com.google.common.collect.Maps;

/**
 * 手机端用户注册
 * 
 * @author Bo.Zhang
 *
 */
public class Register {

	@Resource
	private UserService userService;

	@Resource
	private SendMsgService sendMsgService;
	@Resource
	private InviteService inviteService;
	@Resource
	private HttpSession session;
	public JsonMobileVO execute(Context context, ParameterParser params)
			throws IOException {
		JsonMobileVO result = new JsonMobileVO();

		try {
			String catalog = params.getString("catalog");

			if ("1".equals(catalog)) {
				// mobile/register.json?catalog=1&username=zk003wg				// 判断用户是否可用
				String username = params.getString("username");
				if (username == null || "".equals(username)) {
					result.setResultCode("201");
					result.setResultMessage("用户名不能为空");
				} else if (!isUserName(username)) {
					result.setResultCode("201");
					result.setResultMessage("用户名为6-20位字母或数字，不能为纯数字");
				} else {
					UserDO userDO = new UserDO();
					userDO.setUserName(username);
					ListResult<UserDO> listResult = userService.queryList(
							userDO);
					if (listResult.getData().size()==0) {
						result.setResultCode("200");
					} else {
						result.setResultCode("201");
						result.setResultMessage("用户名已注册");
					}
				}
			} else if ("2".equals(catalog)) {
				
				// 注册发送手机验证码
				// /mobile/register.json?username=zk&phone=13012328976
				String personName = params.getString("username");
				String telephone = params.getString("phone");

				if (telephone == null || "".equals(telephone)) {
					result.setResultCode("201");
					result.setResultMessage("手机号码不能为空");
				} else {
					UserDO userDO = new UserDO();
					userDO.setUserPhone(telephone);
					PageResult<UserDO> pageResult = userService.queryList(
							userDO, new PageCondition());
					if (pageResult.getTotalCount() > 0) {
						result.setResultCode("201");
						result.setResultMessage("验证手机号码已注册");
						return result;
					}else{
						//防止重复操作
//						String token = params.getString("token");
//						if(null == token || "".equals(token)){
//							result.setResultCode("201");
//							result.setResultMessage("token未获取");
//							return result;
//						}
//						String tokens =session.getAttribute("token").toString();
//						if(!token.equals(tokens)){
//							result.setResultCode("201");
//							result.setResultMessage("请不要频繁操作！");
//							return result;
//						}
//						session.removeAttribute("token");
						
						String validCode = GenerateUtil.generateValidCode();
//						String validCode = "111111";
						String content = "您的手机验证码：" + validCode
								+ ",有效时间5分钟，感谢使用新华久久贷";
						boolean isSend = sendMsgService.sendMsg(telephone, content,
								personName, "2");

						result.setResultCode("200");
						result.setResultMessage("短信发送成功");

						if (isSend) {
							result.setResultCode("200");
							result.setResultMessage("短信发送成功");
							Constants.mobileCodeMap.remove(telephone);
							Constants.mobileCodeMap.put(telephone, validCode);
						} else {
							result.setResultCode("201");
							result.setResultMessage("短信发送失败");
							Constants.mobileCodeMap.remove(telephone);
						}
					}

					
				}
			} else if ("3".equals(catalog)) {
				// mobile/register.json?catalog=3&username=zk002&password=123456&phone=13012344587&invitationPhone=18555680113
				// 申请注册
				String userName = params.getString("username");
				String userPwd = params.getString("password");
				String userPhone = params.getString("phone");
				String verifyCode = params.getString("verifyCode");
				String InvitationPhone = params.getString("invitationPhone");// 推荐人手机号
				String validCode = Constants.mobileCodeMap.get(userPhone);
				if (validCode == null || "".equals(validCode)) {
					result.setResultCode("201");
					result.setResultMessage("请先获取手机验证码");
					return result;
				}
				if (!verifyCode.equals(validCode)) {
					result.setResultCode("201");
					result.setResultMessage("手机验证码不正确");
					return result;
				}
				UserDO userDO = new UserDO();
				userDO.setUserUuid(InnerSeqNo.getInstance().getUniqueNo());
				userDO.setUserName(userName);
				userDO.setUserPwd(CryptUtils.md5(userPwd));
				userDO.setUserDealPwd(CryptUtils.md5(userPwd));
				userDO.setUserPhone(userPhone);
				userDO.setUserMobileIsbinded(1);
				userDO.setUserEmailIsbinded(1);
				userDO.setUserRegisterDate(new Date());
				userDO.setUserState(1);
				userDO.setUserType(1);
				userDO.setUserLoanCredit(new BigDecimal(10000));
				userDO.setUserCreditSett(new BigDecimal(10000));
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
				BaseResult baseResult = userService.createUser(userDO);

				if (baseResult.isSuccess()) {
					result.setResultCode("200");
					result.setResultMessage("注册成功");
					Constants.mobileCodeMap.remove(userPhone);
					// 添加邀请记录
					if (invitationUserId != null) {
						Invite invite = new Invite();
						invite.setInviteUserId(invitationUserId);
						invite.setInviteInviteeId(userDO.getUserId());
						if (invitationUserType != null
								&& invitationUserType == 1) {
							invite.setInviteUserType(InviteUserType.PERSONAL);
						} else if (invitationUserType != null
								&& invitationUserType == 2) {
							invite.setInviteUserType(InviteUserType.PARTNER);
						}
						invite.setInviteIsValid(ValidState.VALID_STATE);
						invite.setInviteRewardState(RewardState.USED);
						inviteService.createInvitation(invite);
					}
				} else {
					result.setResultCode("201");
					result.setResultMessage(baseResult.getMessage());
				}
			} else if ("4".equals(catalog)) {
				
				
				// 找回密码---发送验证短信
				//mobile/register.json?catalog=4&phone=18256915642
				String userPhone = params.getString("phone");

				if (userPhone == null || "".equals(userPhone)) {
					result.setResultCode("201");
					result.setResultMessage("手机号码不能为空");
					return result;
				}

				UserDO userDO = new UserDO();
				userDO.setUserPhone(userPhone);
				PageResult<UserDO> pageResult = userService.queryList(userDO,
						new PageCondition());
				if (pageResult.getTotalCount() <= 0) {
					result.setResultCode("201");
					result.setResultMessage("不存在注册的手机号码");
					return result;
				}else{
					//防止重复操作
//					String token = params.getString("token");
//					if(null == token || "".equals(token)){
//						result.setResultCode("201");
//						result.setResultMessage("token未获取");
//						return result;
//					}
//					String tokens =session.getAttribute("token").toString();
//					if(!token.equals(tokens)){
//						result.setResultCode("201");
//						result.setResultMessage("请不要频繁操作！");
//						return result;
//					}
//					session.removeAttribute("token");
					
					String validCode = GenerateUtil.generateValidCode();
					String content = "您的手机验证码：" + validCode + ",有效时间5分钟，感谢使用新华久久贷";
					boolean isSend = sendMsgService.sendMsg(userPhone, content, "",
							"2");
					if (isSend) {
						result.setResultCode("200");
						result.setResultMessage("短信发送成功");
						Constants.mobileCodeMap.put(userPhone, validCode);
					}
				}
				
			}  else if ("6".equals(catalog)) {
				// 找回密码---设置新密码
				// /mobile/register.json?catalog=6&phone=18256915642&newPwd=111111&verifyCode=
				
				String newPwd = params.getString("newPwd");
				String userPhone = params.getString("phone");
				String verifyCode = params.getString("verifyCode");
				
				String validCode = Constants.mobileCodeMap.get(userPhone);
				Constants.mobileCodeMap.remove(userPhone);
				if (validCode == null || "".equals(validCode)) {
					result.setResultCode("201");
					result.setResultMessage("请先获取手机验证码");
					return result;
				}
				if (!verifyCode.equals(validCode)) {
					result.setResultCode("201");
					result.setResultMessage("手机验证码不正确");
					return result;
				}
				
				UserDO userDO = new UserDO();
				userDO.setUserPhone(userPhone);
				PageResult<UserDO> pageResult = userService.queryList(userDO,
						new PageCondition(1, 1));
				Integer userId = pageResult.getData().get(0).getUserId();
				//设置新密码
				UserDO userDO2 = new UserDO();
				userDO2.setUserId(userId);
				userDO2.setUserPwd(CryptUtils.md5(newPwd));
				BaseResult baseResult = userService.modifyUserSelective(userDO2);
				if (!baseResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage("找回密码失败");
					return result;
				}
				result.setResultCode("200");
				result.setResultMessage("设置新密码成功");
			} else if ("7".equals(catalog)) {
				// 查询手机号(服务码)是否存在
				return mobileIsExist(params);
			} else if ("8".equals(catalog)) {
				
				// 找回支付密码---发送验证短信
				//mobile/register.json?catalog=4&phone=18256915642
				String userPhone = params.getString("phone");

				if (userPhone == null || "".equals(userPhone)) {
					result.setResultCode("201");
					result.setResultMessage("手机号码不能为空");
					return result;
				}else{
					
					//防止重复操作
//					String token = params.getString("token");
//					if(null == token || "".equals(token)){
//						result.setResultCode("201");
//						result.setResultMessage("token未获取");
//						return result;
//					}
//					String tokens =session.getAttribute("token").toString();
//					if(!token.equals(tokens)){
//						result.setResultCode("201");
//						result.setResultMessage("请不要频繁操作！");
//						return result;
//					}
//					session.removeAttribute("token");
					
					UserDO userDO = new UserDO();
					userDO.setUserPhone(userPhone);
					PageResult<UserDO> pageResult = userService.queryList(userDO,
							new PageCondition());
					if (pageResult.getTotalCount() <= 0) {
						result.setResultCode("201");
						result.setResultMessage("不存在注册的手机号码");
						return result;
					}
					String validCode = GenerateUtil.generateValidCode();
					String content = "您的手机验证码：" + validCode + ",有效时间5分钟，感谢使用新华久久贷";
					boolean isSend = sendMsgService.sendMsg(userPhone, content, "",
							"2");
					if (isSend) {
						result.setResultCode("200");
						result.setResultMessage("短信发送成功");
						Constants.mobileCodeMap.put(userPhone, validCode);
					}
				}
			}else if ("9".equals(catalog)) {
				// 找回支付密码---设置新密码
				// /mobile/register.json?catalog=6&phone=18256915642&newPwd=111111&verifyCode=
				
				String newPwd = params.getString("newDealPsw");
				String userPhone = params.getString("phone");
				String verifyCode = params.getString("verifyCode");
				
				String validCode = Constants.mobileCodeMap.get(userPhone);
				Constants.mobileCodeMap.remove(userPhone);
				if (validCode == null || "".equals(validCode)) {
					result.setResultCode("201");
					result.setResultMessage("请先获取手机验证码");
					return result;
				}
				if (!verifyCode.equals(validCode)) {
					result.setResultCode("201");
					result.setResultMessage("手机验证码不正确");
					return result;
				}
				
				UserDO userDO = new UserDO();
				userDO.setUserPhone(userPhone);
				PageResult<UserDO> pageResult = userService.queryList(userDO,
						new PageCondition(1, 1));
				Integer userId = pageResult.getData().get(0).getUserId();
				//设置新密码
				UserDO userDO2 = new UserDO();
				userDO2.setUserId(userId);
				userDO2.setUserDealPwd(CryptUtils.md5(newPwd));
				BaseResult baseResult = userService.modifyUserSelective(userDO2);
				if (!baseResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage("找回密码失败");
					return result;
				}
				result.setResultCode("200");
				result.setResultMessage("设置新密码成功");
			}else if ("10".equals(catalog)) {//防止重复提交生成token
				String token = InnerSeqNo.getInstance().getUniqueNo();
				System.out.println("========== Token:"+token);
				session.setAttribute("token", token);
				result.setResult(token);
				result.setResultCode("200");
				result.setResultMessage("Success");
			} else if("11".equals(catalog)){
				//更换手机
				// /mobile/register.json?catalog=11&phone=13012322525&verifyCode=165660
				String userPhone = params.getString("phone");
				String verifyCode = params.getString("verifyCode");
				
				String validCode = Constants.mobileCodeMap.get(userPhone);
				
				if (validCode == null || "".equals(validCode)) {
					result.setResultCode("201");
					result.setResultMessage("请先获取手机验证码");
					return result;
				}
				if (!verifyCode.equals(validCode)) {
					result.setResultCode("201");
					result.setResultMessage("手机验证码不正确");
					return result;
				}
				UserDO userDO = new UserDO();
				User user = (User) session.getAttribute("user");
				userDO.setUserId(user.getUserId());
				userDO.setUserPhone(userPhone);  //修改绑定的手机
				BaseResult resu = this.userService.modifyUserSelective(userDO);
    			result.setSuccess(resu.isSuccess()); 
    			Constants.mobileCodeMap.remove(userPhone);
			}else {
				result.setResultCode("201");
				result.setResultMessage("catalog not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode("500");
			result.setResultMessage("error");
		}
		return result;
	}
	/**
	 * 判断该手机号在系统中是否存在
	 * @param params
	 * @return
	 */
	private JsonMobileVO mobileIsExist(ParameterParser params) {
		// /mobile/register.json?catalog=7&invitationPhone=13012341234
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = Maps.newHashMap();
		vo.setResult(resultMap);
		String userPhone = params.getString("invitationPhone");
		UserDO userDO = new UserDO();
		userDO.setUserPhone(userPhone);
		PageResult<UserDO> pageResult = userService.queryList(userDO,
				new PageCondition(1, 1));
		if (pageResult.getData().size() != 0) {
			resultMap.put("exist", "true");
		} else {
			resultMap.put("exist", "false");
		}
		return vo;
	}
	
	/**
	 * 用户名合法性验证
	 * @param userName
	 * @return
	 */
	public static boolean isUserName(String userName) {
		if (StringUtils.isBlank(userName)) {
			return false;
		}
		Pattern p = Pattern.compile("^(?![0-9]+$)[0-9A-Za-z]{6,20}$");
		Matcher m = p.matcher(userName);
		return m.matches();
	}

	 public static void main(String[] args) {
		 System.out.println(Register.isUserName("asdfasdf"));
	 }

}
