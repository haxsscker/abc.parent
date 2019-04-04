package com.autoserve.abc.web.module.screen.selfprove;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.convert.UserConverter;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.UserVOConverter;
import com.autoserve.abc.web.vo.user.UserVO;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/17 22:27.
 */
public class BaseInfoView {
    @Resource
    private UserService userService;

    public void execute(Context context, @Param("cinId") Integer userId) {
        if (userId != 0) {
            PlainResult<UserDO> result = userService.findById(userId);
            if (result.isSuccess()) {
                User user = UserConverter.toUser(result.getData());
                UserVO vo = UserVOConverter.convertToUserVO(user);
                context.put("user", vo);
            }
        }
    }
}
