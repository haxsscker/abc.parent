package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.MenuDO;
import com.autoserve.abc.service.biz.entity.AttrOfMenuNode;
import com.autoserve.abc.service.biz.entity.MenuNode;

public class MenuDOtoNodeConverter {

    public static MenuNode convert(MenuDO menu) {
        if (menu == null)
            return new MenuNode();
        MenuNode node = new MenuNode();
        node.setId(menu.getMenuId());
        node.setText(menu.getMenuName());
        node.setIconCls(menu.getMenuSmallicon());
        AttrOfMenuNode att = new AttrOfMenuNode();
        att.setParentid(menu.getMenuParent());
        att.setSortnum(menu.getMenuSort());
        att.setUrl(menu.getMenuUrl());
        node.setAttributes(att);
        return node;
    }

    public static List<MenuNode> convertList(List<MenuDO> list) {
        if (list == null || list.isEmpty())
            return new ArrayList<MenuNode>();
        List<MenuNode> result = new ArrayList<MenuNode>();
        for (MenuDO mdo : list) {
            result.add(convert(mdo));
        }
        return result;
    }
}
