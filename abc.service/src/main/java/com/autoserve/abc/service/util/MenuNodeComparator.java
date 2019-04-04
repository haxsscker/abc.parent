package com.autoserve.abc.service.util;

import com.autoserve.abc.service.biz.entity.MenuNode;

import java.util.Comparator;

/**
 * 树节点排序工具 类MenuNodeComparator.java的实现描述：TODO 类实现描述
 * 
 * @author pp 2014年11月29日 下午2:50:51
 */
public class MenuNodeComparator implements Comparator<MenuNode> {

    /*
     * 先按照MenuNode.getAttributes.parentId 升序排序 如果相等 。 则按照sortnum降序排列
     */
    @Override
    public int compare(MenuNode o1, MenuNode o2) {
        int oneParent = o1.getAttributes().getParentid();
        int twoParent = o2.getAttributes().getParentid();
        int result = 0;
        result = twoParent < oneParent ? 1 : (twoParent == oneParent ? 0 : -1);
        int oneSort = o1.getAttributes().getSortnum();
        int twoSort = o2.getAttributes().getSortnum();
        if (result == 0)
            result = oneSort < twoSort ? 1 : (oneSort == twoSort ? 0 : -1);
        return result;
    }
}
