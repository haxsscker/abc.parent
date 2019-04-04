package com.autoserve.abc.web.module.screen.user.json;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.convert.UserConverter;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.UserVOConverter;
import com.autoserve.abc.web.util.MySqlUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.user.UserVO;

/**
 * @author RJQ 2014/12/27 16:10.
 */
public class GetUserList {
    @Autowired
    private UserService userService;

    public JsonPageVO<UserVO> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        UserDO userDO = new UserDO();
        //搜索查询条件
        userDO.setUserName(MySqlUtil.transferString(params.getString("userName")));
        String loanType = params.getString("loanType");
        //edit by 夏同同  20160415 客户要求个人和企业用户都查，故注释下面代码
        //if (!StringUtil.isEmpty(loanType)) {
            //            if (Integer.valueOf(loanType) == UserType.PERSONAL.type)
            //userDO.setUserType(Integer.valueOf(loanType));
        //}
        userDO.setUserState(EntityState.STATE_ENABLE.getState());
        userDO.setUserBusinessState(UserBusinessState.BUSINESSSTATE.getState());
        JsonPageVO<UserVO> pageVO = new JsonPageVO<UserVO>();
        PageResult<UserDO> result = userService.queryList(userDO, pageCondition);
        List<UserDO> userDOs = result.getData();
        if (userDOs == null || userDOs.size() == 0) {
            pageVO.setRows(new ArrayList<UserVO>());
            pageVO.setTotal(0);
            return pageVO;
        }
        pageVO.setRows(UserVOConverter.convertToList(UserConverter.convertList(userDOs)));
        pageVO.setTotal(result.getTotalCount());
        return pageVO;
    }
}
