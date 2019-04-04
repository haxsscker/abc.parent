package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.WeChatMenuDO;
import com.autoserve.abc.service.biz.entity.WeChatMenuItem;
import com.autoserve.abc.service.exception.BusinessException;

public class WeChatMenuDOtoItemConverter {

    public static WeChatMenuItem convert(WeChatMenuDO menu) {
        if (menu == null)
            return new WeChatMenuItem();
        WeChatMenuItem item = new WeChatMenuItem();
        item.setIsVisible(menu.getMenuVisible());
        item.setMenuId(menu.getMenuId());
        item.setMenuName(menu.getMenuName());
        item.setMenuSort(menu.getMenuSort());
        item.setMenuUrl(menu.getMenuUrl());
        item.setParentId(menu.getMenuParent());
        item.setMenuKey(menu.getMenuKey());
        item.setMenuType(menu.getMenuType());
        item.setMenuImage(menu.getMenuImage());
        item.setMenuSafe(menu.getMenuSafe());
        return item;
    }

    public static List<WeChatMenuItem> convertList(List<WeChatMenuDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<WeChatMenuItem> result = new ArrayList<WeChatMenuItem>();
        for (WeChatMenuDO mdo : list) {
            result.add(convert(mdo));
        }
        return result;
    }
}
