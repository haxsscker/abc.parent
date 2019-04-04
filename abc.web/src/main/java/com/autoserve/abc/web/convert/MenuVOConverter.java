package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.MenuDO;
import com.autoserve.abc.web.vo.menu.MenuVO;

public class MenuVOConverter {

    public static MenuDO convert(MenuVO vo) {
        MenuDO menu = new MenuDO();
        menu.setMenuId(vo.getMenuId());
        menu.setMenuBigicon(vo.getBigIcon());
        menu.setMenuName(vo.getMenuName());
        menu.setMenuParent(vo.getParentId());
        menu.setMenuSmallicon(vo.getIconCls());
        menu.setMenuSort(vo.getMenuSort());
        menu.setMenuUrl(vo.getMenuUrl());
        menu.setMenuVisible(vo.getIsVisible());
        return menu;

    }
}
