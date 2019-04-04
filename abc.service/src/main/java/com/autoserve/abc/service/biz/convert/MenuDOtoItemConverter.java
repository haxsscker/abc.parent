package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.MenuDO;
import com.autoserve.abc.service.biz.entity.MenuItem;
import com.autoserve.abc.service.exception.BusinessException;

public class MenuDOtoItemConverter {
    public static MenuItem convert(MenuDO menu) {
        if (menu == null)
            return new MenuItem();
        MenuItem item = new MenuItem();
        item.setBigIcon(menu.getMenuBigicon());
        item.setIconCls(menu.getMenuSmallicon());
        item.setIsVisible(menu.getMenuVisible());
        item.setMenuId(menu.getMenuId());
        item.setMenuName(menu.getMenuName());
        item.setMenuSort(menu.getMenuSort());
        item.setMenuUrl(menu.getMenuUrl());
        item.setParentId(menu.getMenuParent());
        return item;
    }

    public static List<MenuItem> convertList(List<MenuDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<MenuItem> result = new ArrayList<MenuItem>();
        for (MenuDO mdo : list) {
            result.add(convert(mdo));
        }
        return result;
    }
}
