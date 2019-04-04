package com.autoserve.abc.web.module.screen.account.myAccount.json;

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

public class UserFreeLogin {
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
    private HttpServletResponse response;
	public JsonBaseVO execute(Context context, Navigator nav, ParameterParser params, TurbineRunData rundata) {
		JsonBaseVO result = new JsonBaseVO();
		//获取用户传来的
        String userName = params.getString("userName");
        String passWord = params.getString("passWord");
        String securityCode = params.getString("securityCode");
        int userFreeLogin = params.getInt("userFreeLogin");
        if(userFreeLogin==1){
        	userFreeLogin=0;
        }else{
        	userFreeLogin=1;
        }
        if (StringUtil.isBlank(userName) || StringUtil.isBlank(passWord)) {
            result.setMessage("登录名或密码错误");
            result.setSuccess(false);

        } else {
       	 String securityfromSession = (String) session.getAttribute("securityCode");
            if (securityfromSession == null || securityCode == null
                    || !securityfromSession.equalsIgnoreCase(securityCode)) {
                if (securityfromSession == null) {
                    result.setMessage("验证码已失效，请重新获取");
                } else {
                    result.setMessage("验证码错误");
                }
                result.setSuccess(false);
            } else {
                //用户名
                PlainResult<User> findResult = UserService.login(userName, CryptUtils.md5(passWord),
                        IPUtil.getUserIpAddr(request),"Wechat");
                if (findResult.isSuccess()) {
                 //获取要自动登陆的userId
               	 int userId = findResult.getData().getUserId();
               	 	//更改自动登陆状态
               	 UserDO udo=new UserDO();
               	 udo.setUserId(userId);
               	 udo.setUserFreeLogin(userFreeLogin);
               	 userDao.updateByPrimaryKeySelective(udo);
                 result.setMessage("更改成功");
                 result.setSuccess(true);
                } else {
                    //手机号
                    UserDO userDO = new UserDO();
                    userDO.setUserPhone(userName);
                    PageResult<UserDO> pageResult = UserService
                            .queryListMobile(userDO, null, null, new PageCondition());
                    if (pageResult.getData().size() != 0) {
                        String userName1 = pageResult.getData().get(0).getUserName();
                        PlainResult<User> findResult1 = UserService.login(userName1, CryptUtils.md5(passWord),
                                IPUtil.getUserIpAddr(request),"Wechat");
                        if (findResult1.isSuccess()) {
                        	 //获取要自动登陆的userId
                          	 int userId = findResult.getData().getUserId();
                          	 	//更改自动登陆状态
                          	 UserDO udo=new UserDO();
                          	 udo.setUserId(userId);
                          	 udo.setUserFreeLogin(userFreeLogin);
                          	 userDao.updateByPrimaryKeySelective(udo);
                    		 
                             result.setMessage("更改成功");
                             result.setSuccess(true);
                        } else {
                            //邮箱
                            UserDO userDO2 = new UserDO();
                            userDO2.setUserEmail(userName);
                            PageResult<UserDO> pageResult2 = UserService.queryListEmail(userDO2, null, null,
                                    new PageCondition());
                            if (pageResult2.getData().size() != 0) {
                                String userName2 = pageResult2.getData().get(0).getUserName();
                                PlainResult<User> findResult2 = UserService.login(userName2, CryptUtils.md5(passWord),
                                        IPUtil.getUserIpAddr(request),"Wechat");
                                if (findResult2.isSuccess()) {
                                	 //获取要自动登陆的userId
                                  	 int userId = findResult.getData().getUserId();
                                  	 	//更改自动登陆状态
                                  	 UserDO udo=new UserDO();
                                  	 udo.setUserId(userId);
                                  	 udo.setUserFreeLogin(userFreeLogin);
                                  	 userDao.updateByPrimaryKeySelective(udo);
                                    result.setMessage("更改成功");
                                    result.setSuccess(true);
                                } else {
                                    result.setMessage("登录名或密码错误");
                                    result.setSuccess(false);
                                }
                            } else {
                                result.setMessage("登录名或密码错误");
                                result.setSuccess(false);
                            }
                        }
                    } else {
                        //邮箱
                        UserDO userDO3 = new UserDO();
                        userDO3.setUserEmail(userName);
                        PageResult<UserDO> pageResult3 = UserService.queryListEmail(userDO3, null, null,
                                new PageCondition());
                        if (pageResult3.getData().size() != 0) {
                            String userName3 = pageResult3.getData().get(0).getUserName();
                            PlainResult<User> findResult3 = UserService.login(userName3, CryptUtils.md5(passWord),
                                    IPUtil.getUserIpAddr(request),"Wechat");
                            if (findResult3.isSuccess()) {
                            	 //获取要自动登陆的userId
                              	 int userId = findResult.getData().getUserId();
                              	 	//更改自动登陆状态
                              	 UserDO udo=new UserDO();
                              	 udo.setUserId(userId);
                              	 udo.setUserFreeLogin(userFreeLogin);
                              	 userDao.updateByPrimaryKeySelective(udo);
                                result.setMessage("更改成功");
                                result.setSuccess(true);
                            } else {
                                result.setMessage("登录名或密码错误");
                                result.setSuccess(false);
                            }
                        } else {
                            result.setMessage("登录名或密码错误");
                            result.setSuccess(false);
                            UserDO userDo = userDao.findUserByNameAndPass(userName, CryptUtils.md5(passWord));
                            if (userDo != null) {
                                if (userDo.getUserState() != EntityState.STATE_ENABLE.getState()) {
                                    result.setMessage("用户已停用！");
                                    result.setSuccess(false);
                                } else {
                                    result.setMessage("登录名或密码错误");
                                    result.setSuccess(false);
                                }
                            }

                        }
                    }

                }
            }
        }
        return result;
		
	}
}
