package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.WeChatMenuDO;
import com.autoserve.abc.web.vo.menu.WeChatMenuVO;

public class WeChatMenuVOConverter {

    public static WeChatMenuDO convert(WeChatMenuVO vo) {
        WeChatMenuDO menu = new WeChatMenuDO();
        menu.setMenuId(vo.getMenuId());
        menu.setMenuName(vo.getMenuName());
        menu.setMenuParent(vo.getParentId());
        menu.setMenuSort(vo.getMenuSort());
        menu.setMenuUrl(vo.getMenuUrl());
        menu.setMenuVisible(vo.getIsVisible());
        menu.setMenuKey(vo.getMenuKey());
        menu.setMenuType(vo.getMenuType());
        menu.setMenuImage(vo.getMenuImage());
        menu.setMenuSafe(vo.getMenuSafe());
        return menu;

    }

}
