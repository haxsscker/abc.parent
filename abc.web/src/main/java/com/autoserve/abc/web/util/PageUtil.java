package com.autoserve.abc.web.util;

import com.alibaba.citrus.util.Paginator;

public class PageUtil {

    /**
     * 构建 Paginator 分页对象
     *
     * @param currentPage 当前页
     * @param pageSize 每页数量
     * @param total 总数
     * @return
     */
    public static Paginator pager(int currentPage, int pageSize, int total) {
        // 设置分页
        Paginator pg = new Paginator(pageSize, total);
        pg.setPage(currentPage);

        return pg;
    }

}
