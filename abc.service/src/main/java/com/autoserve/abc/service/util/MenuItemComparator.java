package com.autoserve.abc.service.util;

import java.util.Comparator;

import com.autoserve.abc.service.biz.entity.MenuItem;

public class MenuItemComparator implements Comparator<MenuItem> {

    @Override
    public int compare(MenuItem o1, MenuItem o2) {
        int oneParent = o1.getParentId();
        int twoParent = o2.getParentId();
        int result = 0;
        result = twoParent < oneParent ? 1 : (twoParent == oneParent ? 0 : -1);
        int oneSort = o1.getMenuSort();
        int twoSort = o2.getMenuSort();
        if (result == 0)
            result = oneSort < twoSort ? 1 : (oneSort == twoSort ? 0 : -1);
        return result;
    }
}
