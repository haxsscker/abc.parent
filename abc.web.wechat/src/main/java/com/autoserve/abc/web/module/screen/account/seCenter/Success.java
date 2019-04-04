package com.autoserve.abc.web.module.screen.account.seCenter;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class Success {
    @Resource
    private HttpSession session;
    @Resource
    private UserService userService;
	public void execute(@Param("title") String title, Context context) {
		context.put("title", title);
		User user = (User) session.getAttribute("user");
		String id="";
		JsonBaseVO result = CheckInformation(user,id);
		if(result.getRedirectUrl() != null && result.getRedirectUrl() != ""){
			System.out.println(result.getRedirectUrl());
			context.put("redirectUrl",result.getRedirectUrl());
		}
	}
	public JsonBaseVO CheckInformation(User user, String id) {
        JsonBaseVO result = new JsonBaseVO();
            PlainResult<UserDO> userDoResult = userService.findById(user.getUserId());
            //实名认证标志
            //			Integer UserRealnameIsproven= userDoResult.getData().getUserRealnameIsproven();
            //真实姓名
            String userRealName = userDoResult.getData().getUserRealName();
            //身份证号
            String userDocNo = userDoResult.getData().getUserDocNo();
            //手机认证
            String mobilePhone ="";
            mobilePhone = userDoResult.getData().getUserPhone();
            //邮箱验证
            String email="";
            email=userDoResult.getData().getUserEmail();
            	
                if (userRealName == null || "".equals(userRealName) || userDocNo == null || "".equals(userDocNo)) {
                    result.setMessage("您还没有录入实名认证信息,请先录入！");
                    result.setRedirectUrl("/account/seCenter/realNameCertify");
                }
                else if(mobilePhone == null || "".equals(mobilePhone)){
                    result.setMessage("您还未进行手机验证，请验证手机！");
                    result.setRedirectUrl("/account/seCenter/phoneVerify");
                }else if(email == null || "".equals(email)){
                    result.setMessage("您还未进行邮箱验证，请验证邮箱！");
                    result.setRedirectUrl("/account/seCenter/emailVerify");
                }else if (userDoResult.getData().getUserBusinessState() == null
                        || userDoResult.getData().getUserBusinessState() < 2 ) {
                    result.setMessage("您还未开户，请先去开户！");
                    result.setRedirectUrl("/account/myAccount/openAccount");
                } else {
                    result.setMessage("");
                    result.setRedirectUrl("");
                }
        
        return result;

    }
	
	
	
}
