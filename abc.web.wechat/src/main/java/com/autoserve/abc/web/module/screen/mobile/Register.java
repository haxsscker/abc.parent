package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.message.sms.SendMsgService;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.util.GenerateUtil;

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

	public JsonMobileVO execute(Context context, ParameterParser params) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		
		try {
			String catalog = params.getString("catalog");
			
			if ("1".equals(catalog)) {
				//判断用户是否可用
				String username = params.getString("username");
				if(username == null || "".equals(username)) {
					result.setResultCode("201");
					result.setResultMessage("用户名不能为空");
				} else {
					UserDO userDO = new UserDO();
					userDO.setUserName(username);
					PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
					if(pageResult.getTotalCount() <= 0) {
						result.setResultCode("200");
					} else {
						result.setResultCode("201");
						result.setResultMessage("用户名已注册");
					}
				}
			} else if ("2".equals(catalog)) {
				//发送手机验证码
				String personName = params.getString("username");
				String telephone = params.getString("phone");
				
				if(telephone == null || "".equals(telephone)) {
					result.setResultCode("201");
					result.setResultMessage("手机号码不能为空");
				} else {
					UserDO userDO = new UserDO();
					userDO.setUserPhone(telephone);
					PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
					if(pageResult.getTotalCount() > 0) {
						result.setResultCode("201");
						result.setResultMessage("验证手机号码已注册");
						return result;
					}
					
					String validCode = GenerateUtil.generateValidCode();
			    	String content = personName + ",您的手机验证码：" + validCode + ",有效时间5分钟，感谢使用新华久久贷";
//			    	boolean isSend = sendMsgService.sendMsg(telephone, content, personName);
					
//					result.setResultCode("200");
//					result.setResultMessage("短信发送成功");
//			    	
//			    	if(isSend) {
			    		result.setResultCode("200");
						result.setResultMessage("短信发送成功");
						Constants.mobileCodeMap.remove(telephone);
				    	Constants.mobileCodeMap.put(telephone, "111111");
//			    	} else {
//			    		result.setResultCode("201");
//						result.setResultMessage("短信发送失败");
//						Constants.mobileCodeMap.remove(telephone);
//			    	}
				}
			} else if ("3".equals(catalog)) {
				//申请注册
				String userName = params.getString("username");
		    	String userPwd = params.getString("password");
		    	String userPhone = params.getString("phone");
		    	String verifyCode = params.getString("verifyCode");
		    	
		    	String validCode = Constants.mobileCodeMap.get(userPhone);
		    	if(validCode == null || "".equals(validCode)) {
		    		result.setResultCode("201");
					result.setResultMessage("请先获取手机验证码");
					return result;
		    	}
		    	if (!verifyCode.equals(validCode)) {
		    		result.setResultCode("201");
					result.setResultMessage("手机验证码不正确");
					return result;
		    	}
		    	UserDO userDO=new UserDO();
	        	userDO.setUserName(userName);
//	        	userDO.setUserEmail(userEmail);
	        	userDO.setUserPwd(CryptUtils.md5(userPwd));
	        	userDO.setUserDealPwd(CryptUtils.md5(userPwd));
	        	userDO.setUserPhone(userPhone);
//	        	userDO.setUserRecommendUserid(userRecommendUserid);
	        	//是否绑定手机号
	        	userDO.setUserMobileIsbinded(1);
	        	//是否绑定邮箱
	        	userDO.setUserEmailIsbinded(1);
	        	//注册日期
	        	userDO.setUserRegisterDate(new Date());
	        	//启用账户
	        	userDO.setUserState(1);
	        	//用户类型 1.个人  2.企业
	        	userDO.setUserType(1);
	        	//信用额度(初始值10000)
	        	userDO.setUserLoanCredit(new BigDecimal(10000));
	        	userDO.setUserCreditSett(new BigDecimal(10000));       	
	        	BaseResult baseResult = userService.createUser(userDO);
		    	
		    	if(baseResult.isSuccess()) {
		    		result.setResultCode("200");
					result.setResultMessage("注册成功");
					Constants.mobileCodeMap.remove(userPhone);
		    	}else {
		    		result.setResultCode("201");
					result.setResultMessage(baseResult.getMessage());
		    	}
			} else if("4".equals(catalog)) {
				//找回密码---发送验证短信
				String userPhone = params.getString("phone");
				
				if(userPhone == null || "".equals(userPhone)) {
					result.setResultCode("201");
					result.setResultMessage("手机号码不能为空");
					return result;
				}
				
				UserDO userDO = new UserDO();
				userDO.setUserPhone(userPhone);
				PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
				if(pageResult.getTotalCount() <= 0) {
					result.setResultCode("201");
					result.setResultMessage("不存在注册的手机号码");
					return result;
				}
				result.setResultCode("200");
				result.setResultMessage("短信发送成功");
		    	Constants.mobileCodeMap.remove(userPhone);
		    	Constants.mobileCodeMap.put(userPhone, "111111");
			} else if ("5".equals(catalog)) {
				//找回密码---验证手机号码
				String userPhone = params.getString("phone");
		    	String verifyCode = params.getString("verifyCode");
		    	
		    	String validCode = Constants.mobileCodeMap.get(userPhone);
		    	if(validCode == null || "".equals(validCode)) {
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
				PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
				if(pageResult.getTotalCount() <= 0) {
					result.setResultCode("201");
					result.setResultMessage("不存在注册的手机号码");
				} else {
					userDO = pageResult.getData().get(0);
					result.setResultCode("200");
					result.setResult(userDO.getUserId());
					Constants.mobileCodeMap.remove(userPhone);
				}
			} else if ("6".equals(catalog)) {
				//找回密码---设置新密码
				Integer userId = params.getInt("userId");
		    	String newPwd = params.getString("newPwd");
		    	
		    	if(userId == null || "".equals(userId)) {
					result.setResultCode("201");
					result.setResultMessage("请求用户id不能为空");
					return result;
				}
		    	PlainResult<UserDO> plainResult = userService.findById(userId);
		    	if(!plainResult.isSuccess()) {
		    		result.setResultCode("201");
					result.setResultMessage("找回密码失败");
		    		return result;
		    	}
		    	UserDO userDO = plainResult.getData();
	    		userDO.setUserPwd(CryptUtils.md5(newPwd));
	    		BaseResult baseResult = userService.modifyUserSelective(userDO);
	    		if(!baseResult.isSuccess()) {
	    			result.setResultCode("201");
					result.setResultMessage("找回密码失败");
	    			return result;
	    		}
	    		result.setResultCode("200");
	    		result.setResultMessage("设置新密码成功");
			} else {
				result.setResultCode("201");
				result.setResultMessage("请求参数异常");
			}
		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("请求异常");
		}
		return result;
	}

}
