package com.autoserve.abc.web.module.screen.score;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.ScoreDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.UserVOConverter;
import com.autoserve.abc.web.vo.user.UserVO;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author RJQ 2014/12/16 16:18.
 */
public class ScoreEditView {
    @Resource
    private UserService userService;

    @Resource
    private ScoreService scoreService;

    public void execute(Context context, ParameterParser params) {
        Integer userId = params.getInt("userId");
        if (userId != 0) {
            PlainResult<User> plainResult = userService.findEntityById(userId);
            if (plainResult.isSuccess()) {
                User user = plainResult.getData();
                UserVO vo = UserVOConverter.convertToUserVO(user);
                context.put("user", vo);
            }
        }

        ListResult<ScoreDO> listResult = scoreService.findAllList();
        if (listResult.isSuccess()) {
            List<ScoreDO> scoreDOs = listResult.getData();
            context.put("scores", scoreDOs);
        }
    }
}
