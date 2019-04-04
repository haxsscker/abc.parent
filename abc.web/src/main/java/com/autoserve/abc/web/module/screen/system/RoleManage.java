package com.autoserve.abc.web.module.screen.system;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.service.biz.enums.ButtonType;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.service.biz.intf.authority.ButtonService;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;

public class RoleManage {
    @Resource
    private AuthorityService authorityService;

    @Resource
    private ButtonService    buttonService;

    public void execute(Context context, ParameterParser params) {
        LoginUserInfo loginUserInfo = LoginUserInfoHelper.getLoginUserInfo();
        Integer empId = loginUserInfo.getEmpId();
        Integer menuId = params.getInt("MenuId");
        List<ButtonDO> list = authorityService.queryAllocatedButton(empId, menuId).getData();
        boolean flag = false;
        for (ButtonDO bdo : list) {
            if (bdo.getBtnTag().equals(ButtonType.Authorization.tag)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            ButtonDO bdo = buttonService.queryByTag(ButtonType.Authorization.tag).getData();
            list.add(bdo);
        }
        context.put("buttonTags", list);
    }
}
