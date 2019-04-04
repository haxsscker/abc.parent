package com.autoserve.abc.web.module.screen.system.json;

import java.util.List;

import javax.annotation.Resource;

import com.autoserve.abc.service.biz.entity.MenuItem;
import com.autoserve.abc.service.biz.intf.authority.MenuService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.menu.MenuItemVO;

/**
 * .java的实现描述：TODO 类实现描述
 * 
 * @author pp 2014-11-24 下午03:21:48
 */
public class GetMenuItem {

    @Resource
    private MenuService menuService;

    public MenuItemVO execute() {
        PlainResult<MenuItem> root = menuService.queryAllMenu();
        List<MenuItem> list = root.getData().getChildren();
        MenuItemVO vo = new MenuItemVO();
        vo.setTotal(list.size());
        vo.setRows(list);
        return vo;
    }
}
