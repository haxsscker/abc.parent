package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.LoginLogJDO;
import com.autoserve.abc.service.biz.enums.LoginState;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.sysset.LoginLogVO;

public class LoginLogVOConverter {

    public static LoginLogVO convertToVO(LoginLogJDO loginJDO) {
        LoginLogVO vo = new LoginLogVO();

        vo.setId(loginJDO.getId());
        vo.setIp(loginJDO.getIp());
        vo.setEmployeeId(loginJDO.getEmployeeId());
        vo.setEmployeeName(loginJDO.getEmployeeName());
        vo.setLoginTime(new DateTime(loginJDO.getLoginTime()).toString(DateUtil.DATE_TIME_FORMAT));
        vo.setLogoutTime(new DateTime(loginJDO.getLogoutTime()).toString(DateUtil.DATE_TIME_FORMAT));

        LoginState loginState = LoginState.valueOf(loginJDO.getLoginState());
        //判断是否已经用户的cookie已经失效，如果已经失效则将状态置为：登出
        //        if (LoginState.LOGIN_SUCCESS.equals(loginState)) {
        //            DateTime loginTime = new DateTime(loginJDO.getLoginTime());	
        //            if (Seconds.secondsBetween(loginTime, DateTime.now()).getSeconds() > LoginCookieTokenManager.defaultCookieMaxValidSeconds) {
        //                loginState = LoginState.LOGOUT;
        //            }
        //        }

        vo.setLoginState(loginState.getDes());
        return vo;
    }

    public static List<LoginLogVO> convertToVOList(List<LoginLogJDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        List<LoginLogVO> result = new ArrayList<LoginLogVO>();
        for (LoginLogJDO logJDO : list) {
            result.add(convertToVO(logJDO));
        }

        return result;
    }
}
