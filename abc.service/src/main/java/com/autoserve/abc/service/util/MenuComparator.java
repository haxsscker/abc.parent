package com.autoserve.abc.service.util;

import com.autoserve.abc.dao.dataobject.MenuDO;

import java.util.Comparator;

/**
 * 类MenuComparator.java的实现描述：对菜单的menu_Sort排序
 * 
 * @author pp 2014年11月18日 下午2:56:55
 */
public class MenuComparator implements Comparator<MenuDO> {

    @Override
    public int compare(MenuDO one, MenuDO two) {
        int oneSort = one.getMenuSort();
        int twoSort = two.getMenuSort();
        int result = oneSort < twoSort ? 1 : (oneSort == twoSort ? 0 : -1);
        return result;
    }

}
