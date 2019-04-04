package com.autoserve.abc.web.module.screen.account.myAccount.json;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.convert.UserConverter;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.SafeUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.octo.captcha.service.image.ImageCaptchaService;

public class BasicInformationEdit {
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
        User user = (User) session.getAttribute("user");
        if (user != null) {
            PlainResult<UserDO> userDO = this.userService.findById(user.getUserId());
            context.put("user", userDO.getData());
        } else {
            nav.redirectToLocation(deployConfigService.getLoginUrl(request));
            return null;
        }

        UserDO userDO = new UserDO();
        userDO.setUserId(user.getUserId());

        //获得提交的参数
        String userRealName = params.getString("userRealName");
        String userDocNo = params.getString("userDocNo");
        String userEmail = params.getString("userEmail");
        String userPhone = params.getString("userPhone");
        String userPwd = params.getString("userPwd");
        String Verification = params.getString("Verification");
        String emailNum = params.getString("emailNum");
        String imageUrl = params.getString("imageUrl");
        //交易密码
        String userDealPwd2 = params.getString("userDealPwd2");

        //判断要修改的参数
        if (imageUrl != null && !"".equals(imageUrl)) { //设置头像
            userDO.setUserHeadImg(imageUrl);
            BaseResult resu = this.userService.modifyUserSelective(userDO);
            result.setSuccess(resu.isSuccess());
        }
        
        

        if (userRealName != null) {
            userDO.setUserRealName(userRealName); //设置真实姓名
            result.setMessage(userRealName);
            BaseResult resu = this.userService.modifyRealAuthInfo(userDO);
            result.setSuccess(resu.isSuccess());
        }

        if (userDocNo != null) {
            userDO.setUserDocType("身份证");
            userDO.setUserDocNo(userDocNo.toUpperCase()); //设置证件号码   		
            result.setMessage(userDocNo);
            BaseResult resu = this.userService.modifyRealAuthInfo(userDO);
            result.setSuccess(resu.isSuccess());
        }
        
        if (userEmail != null) {
            userDO.setUserEmail(userEmail); //修改绑定的邮箱
            //    		boolean isResponseCorrect = imageCaptchaService.validateResponseForID(request.getSession().getId(), emailNum);
            String securityfromSession = (String) session.getAttribute("securityCode");
            if (securityfromSession == null || emailNum == null || !securityfromSession.equalsIgnoreCase(emailNum)) {
                if (securityfromSession == null) {
                    result.setMessage("验证码已失效，请重新获取");
                } else {
                    result.setMessage("验证码错误");
                }
                result.setSuccess(false);
            } else {
                BaseResult resu = this.userService.modifyUserSelective(userDO);
                result.setSuccess(resu.isSuccess());
            }
        }

        if (userPhone != null) {
            userDO.setUserPhone(userPhone); //修改绑定的手机
            result.setMessage(SafeUtil.hideMobile(userPhone));
            String validCode = (String) session.getAttribute("securityCode");
            if (Verification.equals(validCode)) {
                userDO.setUserMobileVerifyDate(new Date());
                BaseResult resu = this.userService.modifyUserSelective(userDO);
                result.setSuccess(resu.isSuccess());
            } else {
                result.setMessage("验证码不正确！");
                result.setSuccess(false);
            }

        }
        //修改密码
        if (userPwd != null) {
            String validCode = (String) session.getAttribute("securityCode");
            if (!Verification.equals(validCode)) {
                result.setMessage("验证码不正确！");
                result.setSuccess(false);
                return result;
            }
            userPwd = CryptUtils.md5(userPwd); //MD5加密修改后的登录密码
            userDO.setUserPwd(userPwd); //修改登录密码
            result.setMessage("******");
            BaseResult resu = this.userService.modifyUserSelective(userDO);
            result.setSuccess(resu.isSuccess());
        }
        //修改交易密码
        if (userDealPwd2 != null) {
            String validCode = (String) session.getAttribute("securityCode");
            if (!Verification.equals(validCode)) {
                result.setMessage("验证码不正确！");
                result.setSuccess(false);
                return result;
            }
            userDealPwd2 = CryptUtils.md5(userDealPwd2); //MD5加密修改后的交易密码
            userDO.setUserDealPwd(userDealPwd2);
            result.setMessage("******");
            BaseResult resu = this.userService.modifyUserSelective(userDO);
            result.setSuccess(resu.isSuccess());
        }

        userDO.setUserId(user.getUserId());
        PlainResult<UserDO> resu = this.userService.findById(user.getUserId());
        //user数据重新存入session
        session.setAttribute("user", UserConverter.toUser(resu.getData()));

        //如果用户真实姓名、身份证号、手机号都不为空，就认为该用户已经实名认证
        //    	 userDocNo = resu.getData().getUserDocNo();
        //    	 userRealName =  resu.getData().getUserRealName();
        //    	 userPhone = resu.getData().getUserPhone();
        //    	 if(userRealName!=null && !"".equals(userRealName) && userDocNo!=null && !"".equals(userDocNo)  && userPhone!=null && !"".equals(userPhone)){
        //    		 userDO.setUserRealnameIsproven(1);    //是否实名认证 1：是 0：否
        //    		 this.userService.modifyUserSelective(userDO);
        //    	 }

        return result;
    }
}
