package com.autoserve.abc.service.util;

import java.util.Comparator;

import com.autoserve.abc.service.biz.entity.WeChatMenuItem;

public class WeChatMenuItemComparator implements Comparator<WeChatMenuItem> {

    @Override
    public int compare(WeChatMenuItem o1, WeChatMenuItem o2) {
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
