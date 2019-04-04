package com.autoserve.abc.web.module.screen.score;

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
 * @author RJQ 2014/12/15 21:41.
 */
public class ScoreLookUpView {

    @Resource
    private UserService userService;

    public void execute(Context context, @Param("cuiId") Integer userId) {
        PlainResult<UserDO> plainResult = userService.findById(userId);
        if (plainResult.isSuccess()) {
            User user = UserConverter.toUser(plainResult.getData());
            UserVO vo = UserVOConverter.convertToUserVO(user);
            context.put("user", vo);
        }
    }
}
