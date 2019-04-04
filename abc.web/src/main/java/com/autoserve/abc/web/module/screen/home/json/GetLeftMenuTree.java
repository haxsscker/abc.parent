package com.autoserve.abc.web.module.screen.home.json;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.MenuNode;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;

public class GetLeftMenuTree {
    @Resource
    private AuthorityService authorityService;

    //TODO

    /**
     * 显示左侧菜单栏
     * @param params
     * @return
     */
    public List<MenuNode> execute(ParameterParser params) {
        //TODO 先显示所有菜单  用于前端调试
        LoginUserInfo loginUserInfo= LoginUserInfoHelper.getLoginUserInfo();
        Integer empId=loginUserInfo.getEmpId();
        PlainResult<MenuNode> root = authorityService.queryMenuByEmpId(empId);
        List<MenuNode> list = root.getData().getChildren();
        return list;
    }

}
